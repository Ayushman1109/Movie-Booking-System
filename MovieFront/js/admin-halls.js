/* ── Admin Halls JS ─────────────────────────────────────────────────────── */

let theatreMap = {};

async function populateTheatreDropdowns() {
    try {
        const res = await fetch(`${API_BASE}/theatre`, { headers: authHeaders() });
        const theatres = await res.json();
        theatreMap = {};
        theatres.forEach(t => { theatreMap[t.id] = t.name; });

        ['theatreId', 'bulkTheatreId'].forEach(selId => {
            const sel = document.getElementById(selId);
            if (!sel) return;
            // Clear existing options except the placeholder
            while (sel.options.length > 1) sel.remove(1);
            theatres.forEach(t => {
                const opt = document.createElement('option');
                opt.value = t.id;
                opt.textContent = `#${t.id} – ${t.name}`;
                sel.appendChild(opt);
            });
        });
    } catch (e) {
        console.error('Failed to load theatres for dropdown', e);
    }
}

async function loadHalls() {
    try {
        const res = await fetch(`${API_BASE}/hall`, { headers: authHeaders() });
        const halls = await res.json();
        renderHalls(halls);
    } catch (err) {
        document.getElementById('halls-list').innerHTML = '<div class="empty-state">Failed to load halls.</div>';
    }
}

function renderHalls(halls) {
    const container = document.getElementById('halls-list');
    if (!halls || halls.length === 0) {
        container.innerHTML = '<div class="empty-state">No halls found.</div>';
        return;
    }

    container.innerHTML = `
        <div class="admin-table-wrapper">
            <table class="admin-table">
                <thead>
                    <tr>
                        <th>Hall ID</th>
                        <th>Theatre</th>
                        <th>Total Seats</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    ${halls.map(h => `
                        <tr>
                            <td>${h.id}</td>
                            <td>${h.theatre ? `#${h.theatre.id} – ${h.theatre.name}` : (theatreMap[h.theatreId] ? `#${h.theatreId} – ${theatreMap[h.theatreId]}` : (h.theatreId ?? '—'))}</td>
                            <td>${h.totalSeats}</td>
                            <td class="actions">
                                <button class="btn btn-warning" onclick="openEditPanel(${h.id}, ${h.totalSeats})">Edit</button>
                                <button class="btn btn-danger" onclick="deleteHall(${h.id}, ${h.theatre ? h.theatre.id : (h.theatreId ?? 0)})">Delete</button>
                            </td>
                        </tr>
                    `).join('')}
                </tbody>
            </table>
        </div>
    `;
}

async function addHall(event) {
    event.preventDefault();
    const theatreId = document.getElementById('theatreId').value;
    const totalSeats = document.getElementById('totalSeats').value;

    try {
        const res = await fetch(`${API_BASE}/hall/create/${theatreId}?totalSeats=${totalSeats}`, {
            method: 'POST',
            headers: authHeaders()
        });
        if (!res.ok) {
            const err = await res.text();
            showAlert('alert', `Error: ${err}`, 'error');
            return;
        }
        showAlert('alert', 'Hall added successfully!', 'success');
        document.getElementById('create-form').reset();
        loadHalls();
    } catch (err) {
        showAlert('alert', 'Could not connect to server.', 'error');
    }
}

async function addBulkHalls(event) {
    event.preventDefault();
    const theatreId = document.getElementById('bulkTheatreId').value;
    const rawSeats = document.getElementById('bulkSeats').value;
    const seatsList = rawSeats.split(',')
        .map(s => parseInt(s.trim()))
        .filter(n => !isNaN(n) && n > 0);

    if (seatsList.length === 0) {
        showAlert('alert', 'Please enter valid seat counts separated by commas.', 'error');
        return;
    }

    try {
        const res = await fetch(`${API_BASE}/hall/createBulk/${theatreId}`, {
            method: 'POST',
            headers: authHeaders(),
            body: JSON.stringify(seatsList)
        });
        if (!res.ok) {
            const err = await res.text();
            showAlert('alert', `Error: ${err}`, 'error');
            return;
        }
        const created = await res.json();
        showAlert('alert', `${created.length} hall(s) added successfully!`, 'success');
        document.getElementById('bulk-form').reset();
        loadHalls();
    } catch (err) {
        showAlert('alert', 'Could not connect to server.', 'error');
    }
}

function openEditPanel(hallId, currentSeats) {
    document.getElementById('edit-id').value = hallId;
    document.getElementById('edit-totalSeats').value = currentSeats;

    const panel = document.getElementById('edit-panel');
    panel.classList.add('show');
    panel.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
}

function closeEditPanel() {
    document.getElementById('edit-panel').classList.remove('show');
}

async function updateHall(event) {
    event.preventDefault();
    const id = document.getElementById('edit-id').value;
    const totalSeats = document.getElementById('edit-totalSeats').value;

    try {
        const res = await fetch(`${API_BASE}/hall/update/${id}?totalSeats=${totalSeats}`, {
            method: 'PUT',
            headers: authHeaders()
        });
        if (!res.ok) {
            const err = await res.text();
            showAlert('alert', `Error: ${err}`, 'error');
            return;
        }
        showAlert('alert', 'Hall updated successfully!', 'success');
        closeEditPanel();
        loadHalls();
    } catch (err) {
        showAlert('alert', 'Could not connect to server.', 'error');
    }
}

async function deleteHall(hallId, theatreId) {
    if (!confirm(`Delete hall #${hallId}? This action cannot be undone.`)) return;
    try {
        const res = await fetch(`${API_BASE}/hall/delete/${hallId}?theatreId=${theatreId}`, {
            method: 'DELETE',
            headers: authHeaders()
        });
        if (!res.ok) {
            const err = await res.text();
            showAlert('alert', `Error: ${err}`, 'error');
            return;
        }
        showAlert('alert', 'Hall deleted successfully!', 'success');
        loadHalls();
    } catch (err) {
        showAlert('alert', 'Could not connect to server.', 'error');
    }
}

document.addEventListener('DOMContentLoaded', () => {
    populateTheatreDropdowns();
    loadHalls();
});
