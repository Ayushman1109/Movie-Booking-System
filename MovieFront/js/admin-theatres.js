/* ── Admin Theatres JS ──────────────────────────────────────────────────── */

async function loadTheatres() {
    try {
        const res = await fetch(`${API_BASE}/theatre`, { headers: authHeaders() });
        const theatres = await res.json();
        renderTheatres(theatres);
    } catch (err) {
        document.getElementById('theatres-list').innerHTML = '<div class="empty-state">Failed to load theatres.</div>';
    }
}

function renderTheatres(theatres) {
    const container = document.getElementById('theatres-list');
    if (!theatres || theatres.length === 0) {
        container.innerHTML = '<div class="empty-state">No theatres found.</div>';
        return;
    }

    container.innerHTML = `
        <div class="admin-table-wrapper">
            <table class="admin-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Address</th>
                        <th>Halls</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    ${theatres.map(t => `
                        <tr>
                            <td>${t.id}</td>
                            <td>${t.name}</td>
                            <td>${t.address}</td>
                            <td>${t.halls ? t.halls.length : 0}</td>
                            <td class="actions">
                                <button class="btn btn-warning" onclick="openEditPanel(${t.id}, ${JSON.stringify(t).replace(/"/g, '&quot;')})">Edit</button>
                                <button class="btn btn-danger" onclick="deleteTheatre(${t.id})">Delete</button>
                            </td>
                        </tr>
                    `).join('')}
                </tbody>
            </table>
        </div>
    `;
}

async function createTheatre(event) {
    event.preventDefault();
    const body = {
        name: document.getElementById('name').value.trim(),
        address: document.getElementById('address').value.trim()
    };

    try {
        const res = await fetch(`${API_BASE}/theatre/create`, {
            method: 'POST',
            headers: authHeaders(),
            body: JSON.stringify(body)
        });
        if (!res.ok) {
            const err = await res.text();
            showAlert('alert', `Error: ${err}`, 'error');
            return;
        }
        showAlert('alert', 'Theatre created successfully!', 'success');
        document.getElementById('create-form').reset();
        loadTheatres();
    } catch (err) {
        showAlert('alert', 'Could not connect to server.', 'error');
    }
}

function openEditPanel(id, theatre) {
    document.getElementById('edit-id').value = id;
    document.getElementById('edit-name').value = theatre.name;
    document.getElementById('edit-address').value = theatre.address;

    const panel = document.getElementById('edit-panel');
    panel.classList.add('show');
    panel.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
}

function closeEditPanel() {
    document.getElementById('edit-panel').classList.remove('show');
}

async function updateTheatre(event) {
    event.preventDefault();
    const id = document.getElementById('edit-id').value;
    const body = {
        name: document.getElementById('edit-name').value.trim(),
        address: document.getElementById('edit-address').value.trim()
    };

    try {
        const res = await fetch(`${API_BASE}/theatre/update/${id}`, {
            method: 'PUT',
            headers: authHeaders(),
            body: JSON.stringify(body)
        });
        if (!res.ok) {
            const err = await res.text();
            showAlert('alert', `Error: ${err}`, 'error');
            return;
        }
        showAlert('alert', 'Theatre updated successfully!', 'success');
        closeEditPanel();
        loadTheatres();
    } catch (err) {
        showAlert('alert', 'Could not connect to server.', 'error');
    }
}

async function deleteTheatre(id) {
    if (!confirm(`Delete theatre #${id}? All associated halls will also be removed.`)) return;
    try {
        const res = await fetch(`${API_BASE}/theatre/${id}`, {
            method: 'DELETE',
            headers: authHeaders()
        });
        if (!res.ok) {
            const err = await res.text();
            showAlert('alert', `Error: ${err}`, 'error');
            return;
        }
        showAlert('alert', 'Theatre deleted successfully!', 'success');
        loadTheatres();
    } catch (err) {
        showAlert('alert', 'Could not connect to server.', 'error');
    }
}

document.addEventListener('DOMContentLoaded', loadTheatres);
