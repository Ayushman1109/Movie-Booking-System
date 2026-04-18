document.addEventListener('DOMContentLoaded', async () => {
    const ticketsContainer = document.getElementById('tickets-container');
    const token = localStorage.getItem('jwtToken');

    if (!token) {
        ticketsContainer.innerHTML = '<p>Please <a href="auth.html">login</a> to view your tickets.</p>';
        return;
    }

    await loadTickets(token);
});

async function loadTickets(token) {
    const ticketsContainer = document.getElementById('tickets-container');
    try {
        const response = await fetch(`${API_BASE}/tickets`, {
            headers: { 'Authorization': `Bearer ${token}` }
        });

        if (response.status === 401 || response.status === 403) {
            localStorage.removeItem('jwtToken');
            localStorage.removeItem('currentUserName');
            window.location.href = 'auth.html';
            return;
        }

        if (!response.ok) throw new Error('Failed to fetch tickets');
        const tickets = await response.json();
        renderTickets(tickets);
    } catch (err) {
        ticketsContainer.innerHTML = `<p>Error loading tickets: ${err.message}</p>`;
    }
}

function renderTickets(tickets) {
    const ticketsContainer = document.getElementById('tickets-container');
    ticketsContainer.innerHTML = '';

    if (!tickets.length) {
        ticketsContainer.innerHTML = '<p>You have no bookings yet. <a href="movies.html">Browse movies</a></p>';
        return;
    }

    tickets.forEach(ticket => {
        const showTime = ticket.show?.start
            ? new Date(ticket.show.start).toLocaleString()
            : 'N/A';
        const bookingElement = document.createElement('div');
        bookingElement.classList.add('booking');
        bookingElement.innerHTML = `
            <h3>${ticket.movieName}</h3>
            <p><strong>Showtime:</strong> ${showTime}</p>
            <p><strong>Seats:</strong> ${ticket.seatNumbers?.join(', ') || 'N/A'}</p>
            <p><strong>Cost:</strong> $${ticket.cost}</p>
            <button onclick="cancelTicket(${ticket.id})">Cancel Ticket</button>
        `;
        ticketsContainer.appendChild(bookingElement);
    });
}

async function cancelTicket(ticketId) {
    const token = localStorage.getItem('jwtToken');
    if (!confirm('Are you sure you want to cancel this ticket?')) return;

    try {
        const response = await fetch(`${API_BASE}/tickets/delete/${ticketId}`, {
            method: 'DELETE',
            headers: { 'Authorization': `Bearer ${token}` }
        });

        if (!response.ok) throw new Error('Failed to cancel ticket');
        await loadTickets(token);
    } catch (err) {
        alert(`Error cancelling ticket: ${err.message}`);
    }
}