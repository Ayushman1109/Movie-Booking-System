let selectedSeats = new Set();
let currentShows = [];

document.addEventListener('DOMContentLoaded', () => {
    if (document.getElementById('book-container')) {
        initializeBookingPage();
    }
});

async function initializeBookingPage() {
    const token = localStorage.getItem('jwtToken');
    if (!token) {
        document.getElementById('book-container').innerHTML =
            '<p>Please <a href="auth.html">login</a> to book tickets.</p>';
        return;
    }

    const savedMovieString = localStorage.getItem('selectedMovie');
    if (!savedMovieString) {
        document.getElementById('book-container').innerHTML =
            '<p>No movie selected. <a href="movies.html">Browse movies</a></p>';
        return;
    }

    const selectedMovie = JSON.parse(savedMovieString);

    try {
        const response = await fetch(`${API_BASE}/show`, {
            headers: { 'Authorization': `Bearer ${token}` }
        });

        if (response.status === 401 || response.status === 403) {
            localStorage.removeItem('jwtToken');
            localStorage.removeItem('currentUserName');
            window.location.href = 'auth.html';
            return;
        }

        if (!response.ok) throw new Error('Failed to fetch shows');
        const allShows = await response.json();
        currentShows = allShows.filter(s => String(s.movie?.id) === String(selectedMovie.id));
        renderBookDetails(selectedMovie, currentShows);
    } catch (err) {
        document.getElementById('book-container').innerHTML =
            `<p>Error loading shows: ${err.message}</p>`;
    }
}

function formatDateTime(dt) {
    const d = new Date(dt);
    return d.toLocaleString('en-IN', {
        weekday: 'short', month: 'short', day: 'numeric',
        hour: '2-digit', minute: '2-digit'
    });
}

function renderBookDetails(movie, shows) {
    const bookContainer = document.getElementById('book-container');

    if (!shows.length) {
        bookContainer.innerHTML = `<div class="book-header"><h2>${movie.name}</h2><p class="no-shows">No shows available for this movie.</p></div>`;
        return;
    }

    const showsHTML = shows.map((show, i) => {
        const freeCount = (show.availSeats || []).filter(s => s === 0).length;
        const totalSeats = (show.availSeats || []).length;
        const start = formatDateTime(show.start);
        const end   = new Date(show.end).toLocaleTimeString('en-IN', { hour: '2-digit', minute: '2-digit' });
        return `
            <label class="show-card" for="show-${show.id}">
                <input type="radio" name="show" id="show-${show.id}" value="${show.id}"
                    data-price="${show.price}" data-idx="${i}" ${i === 0 ? 'checked' : ''}>
                <div class="show-card-inner">
                    <div class="show-time">
                        <span class="show-start">${start}</span>
                        <span class="show-arrow">→</span>
                        <span class="show-end">${end}</span>
                    </div>
                    <div class="show-meta">
                        <span class="show-price">₹${show.price}</span>
                        <span class="show-seats ${freeCount === 0 ? 'sold-out' : freeCount < 10 ? 'low-seats' : ''}">
                            ${freeCount === 0 ? 'Sold Out' : `${freeCount} / ${totalSeats} seats`}
                        </span>
                    </div>
                </div>
            </label>
        `;
    }).join('');

    bookContainer.innerHTML = `
        <div class="book-header">
            <h2>${movie.name}</h2>
        </div>
        <div class="section-title">Select a Show</div>
        <div class="shows-list">${showsHTML}</div>
        <div class="section-title">Select Seats</div>
        <div class="seat-legend">
            <span class="legend-item"><span class="legend-box available"></span> Available</span>
            <span class="legend-item"><span class="legend-box booked"></span> Booked</span>
            <span class="legend-item"><span class="legend-box selected-legend"></span> Selected</span>
        </div>
        <div id="seat-grid"></div>
        <div id="selected-seats-display" class="selected-seats-display">No seats selected</div>
        <div id="total-cost" class="total-cost">Total: ₹0.00</div>
        <div id="booking-error" class="form-error hidden"></div>
        <button id="confirm-booking" onclick="confirmBooking('${movie.name.replace(/'/g, "\\'")}')">Confirm Booking</button>
    `;

    document.querySelectorAll('input[name="show"]').forEach(radio => {
        radio.addEventListener('change', () => {
            selectedSeats.clear();
            renderSeatGrid();
        });
    });

    renderSeatGrid();
}

function renderSeatGrid() {
    const grid = document.getElementById('seat-grid');
    const selectedRadio = document.querySelector('input[name="show"]:checked');
    if (!selectedRadio) return;

    const showIdx = parseInt(selectedRadio.dataset.idx, 10);
    const show = currentShows[showIdx];
    const availSeats = show.availSeats || [];

    const cols = 10;
    let html = '<div class="seat-grid-wrapper">';
    availSeats.forEach((status, idx) => {
        const seatNum = idx;
        const isBooked = status === 1;
        const isSelected = selectedSeats.has(seatNum);
        let cls = 'seat';
        if (isBooked) cls += ' booked';
        else if (isSelected) cls += ' selected';
        else cls += ' available';

        if (idx % cols === 0 && idx !== 0) html += '<div class="seat-row-break"></div>';
        html += `<button class="${cls}" data-seat="${seatNum}" title="Seat ${seatNum + 1}"
            ${isBooked ? 'disabled' : ''} onclick="toggleSeat(${seatNum}, this)">
            ${seatNum + 1}
        </button>`;
    });
    html += '</div>';
    grid.innerHTML = html;
    updateSummary();
}

function toggleSeat(seatNum, el) {
    if (el.disabled) return;
    if (selectedSeats.has(seatNum)) {
        selectedSeats.delete(seatNum);
        el.classList.remove('selected');
        el.classList.add('available');
    } else {
        selectedSeats.add(seatNum);
        el.classList.remove('available');
        el.classList.add('selected');
    }
    updateSummary();
}

function updateSummary() {
    const radio = document.querySelector('input[name="show"]:checked');
    const price = radio ? parseFloat(radio.dataset.price) : 0;
    const total = price * selectedSeats.size;

    const display = document.getElementById('selected-seats-display');
    const costEl  = document.getElementById('total-cost');
    if (!display || !costEl) return;

    if (selectedSeats.size === 0) {
        display.textContent = 'No seats selected';
    } else {
        const nums = [...selectedSeats].sort((a, b) => a - b).map(n => n + 1).join(', ');
        display.textContent = `Selected seats: ${nums}`;
    }
    costEl.textContent = `Total: ₹${total.toFixed(2)}`;
}

async function confirmBooking(movieName) {
    const token       = localStorage.getItem('jwtToken');
    const selectedShow = document.querySelector('input[name="show"]:checked');
    const errorEl     = document.getElementById('booking-error');
    errorEl.classList.add('hidden');

    if (!selectedShow) {
        errorEl.textContent = 'Please select a show.';
        errorEl.classList.remove('hidden');
        return;
    }
    if (selectedSeats.size === 0) {
        errorEl.textContent = 'Please select at least one seat.';
        errorEl.classList.remove('hidden');
        return;
    }

    const seats = [...selectedSeats].sort((a, b) => a - b);

    try {
        const response = await fetch(`${API_BASE}/tickets/book`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                showId: parseInt(selectedShow.value, 10),
                movieName: movieName,
                seatNumbers: seats
            })
        });

        if (!response.ok) {
            const msg = await response.text();
            throw new Error(msg || 'Booking failed');
        }

        window.location.href = 'tickets.html';
    } catch (err) {
        errorEl.textContent = `Booking failed: ${err.message}`;
        errorEl.classList.remove('hidden');
    }
}