/* ── Admin Shows JS ─────────────────────────────────────────────────────── */

function toLocalDatetimeValue(isoString) {
    if (!isoString) return '';
    // Convert ISO to datetime-local format (YYYY-MM-DDTHH:MM)
    return isoString.substring(0, 16);
}

function toISOFormatted(localValue) {
    // Convert datetime-local value to ISO-like format expected by backend
    return localValue ? localValue + ':00' : null;
}

async function populateDropdowns(movieSelId, hallSelId) {
    try {
        const [moviesRes, hallsRes] = await Promise.all([
            fetch(`${API_BASE}/movie`, { headers: authHeaders() }),
            fetch(`${API_BASE}/hall`, { headers: authHeaders() })
        ]);
        const movies = await moviesRes.json();
        const halls = await hallsRes.json();

        [movieSelId, hallSelId].forEach((selId, i) => {
            const sel = document.getElementById(selId);
            if (!sel) return;
            if (i === 0) {
                movies.forEach(m => {
                    const opt = document.createElement('option');
                    opt.value = m.id;
                    opt.textContent = `#${m.id} – ${m.name}`;
                    sel.appendChild(opt);
                });
            } else {
                halls.forEach(h => {
                    const opt = document.createElement('option');
                    opt.value = h.id;
                    opt.textContent = `Hall #${h.id} (${h.totalSeats} seats)`;
                    sel.appendChild(opt);
                });
            }
        });
    } catch (e) {
        console.error('Failed to populate dropdowns', e);
    }
}

async function loadShows() {
    try {
        const res = await fetch(`${API_BASE}/show`, { headers: authHeaders() });
        const shows = await res.json();
        renderShows(shows);
    } catch (err) {
        document.getElementById('shows-list').innerHTML = '<div class="empty-state">Failed to load shows.</div>';
    }
}

function formatDateTime(val) {
    if (!val) return '—';
    return new Date(val).toLocaleString();
}

function renderShows(shows) {
    const container = document.getElementById('shows-list');
    if (!shows || shows.length === 0) {
        container.innerHTML = '<div class="empty-state">No shows found.</div>';
        return;
    }

    container.innerHTML = `
        <div class="admin-table-wrapper">
            <table class="admin-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Movie</th>
                        <th>Hall</th>
                        <th>Price</th>
                        <th>Start</th>
                        <th>End</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    ${shows.map(s => `
                        <tr>
                            <td>${s.id}</td>
                            <td>${s.movie ? s.movie.name : s.movieId ?? '—'}</td>
                            <td>${s.hall ? `Hall #${s.hall.id}` : s.hallId ?? '—'}</td>
                            <td>₹${s.price}</td>
                            <td>${formatDateTime(s.start)}</td>
                            <td>${formatDateTime(s.end)}</td>
                            <td class="actions">
                                <button class="btn btn-warning" onclick="openEditPanel(${s.id}, ${JSON.stringify(s).replace(/"/g, '&quot;')})">Edit</button>
                                <button class="btn btn-danger" onclick="deleteShow(${s.id})">Delete</button>
                            </td>
                        </tr>
                    `).join('')}
                </tbody>
            </table>
        </div>
    `;
}

async function createShow(event) {
    event.preventDefault();
    const body = {
        movieId: parseInt(document.getElementById('movieId').value),
        hallId: parseInt(document.getElementById('hallId').value),
        price: parseInt(document.getElementById('price').value),
        start: toISOFormatted(document.getElementById('start').value),
        intervalTime: parseInt(document.getElementById('intervalTime').value)
    };

    try {
        const res = await fetch(`${API_BASE}/show/create`, {
            method: 'POST',
            headers: authHeaders(),
            body: JSON.stringify(body)
        });
        if (!res.ok) {
            const err = await res.text();
            showAlert('alert', `Error: ${err}`, 'error');
            return;
        }
        showAlert('alert', 'Show created successfully!', 'success');
        document.getElementById('create-form').reset();
        loadShows();
    } catch (err) {
        showAlert('alert', 'Could not connect to server.', 'error');
    }
}

function openEditPanel(id, show) {
    document.getElementById('edit-id').value = id;
    document.getElementById('edit-movieId').value = show.movie ? show.movie.id : (show.movieId ?? '');
    document.getElementById('edit-hallId').value = show.hall ? show.hall.id : (show.hallId ?? '');
    document.getElementById('edit-price').value = show.price;
    document.getElementById('edit-start').value = toLocalDatetimeValue(show.start);
    document.getElementById('edit-intervalTime').value = show.intervalTime ?? 15;

    const panel = document.getElementById('edit-panel');
    panel.classList.add('show');
    panel.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
}

function closeEditPanel() {
    document.getElementById('edit-panel').classList.remove('show');
}

async function updateShow(event) {
    event.preventDefault();
    const id = document.getElementById('edit-id').value;
    const body = {
        movieId: parseInt(document.getElementById('edit-movieId').value),
        hallId: parseInt(document.getElementById('edit-hallId').value),
        price: parseInt(document.getElementById('edit-price').value),
        start: toISOFormatted(document.getElementById('edit-start').value),
        intervalTime: parseInt(document.getElementById('edit-intervalTime').value)
    };

    try {
        const res = await fetch(`${API_BASE}/show/update/${id}`, {
            method: 'PUT',
            headers: authHeaders(),
            body: JSON.stringify(body)
        });
        if (!res.ok) {
            const err = await res.text();
            showAlert('alert', `Error: ${err}`, 'error');
            return;
        }
        showAlert('alert', 'Show updated successfully!', 'success');
        closeEditPanel();
        loadShows();
    } catch (err) {
        showAlert('alert', 'Could not connect to server.', 'error');
    }
}

async function deleteShow(id) {
    if (!confirm(`Delete show #${id}? This action cannot be undone.`)) return;
    try {
        const res = await fetch(`${API_BASE}/show/delete/${id}`, {
            method: 'DELETE',
            headers: authHeaders()
        });
        if (!res.ok) {
            const err = await res.text();
            showAlert('alert', `Error: ${err}`, 'error');
            return;
        }
        showAlert('alert', 'Show deleted successfully!', 'success');
        loadShows();
    } catch (err) {
        showAlert('alert', 'Could not connect to server.', 'error');
    }
}

document.addEventListener('DOMContentLoaded', () => {
    populateDropdowns('movieId', 'hallId');
    populateDropdowns('edit-movieId', 'edit-hallId');
    loadShows();
});
