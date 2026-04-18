/* ── Admin Movies JS ────────────────────────────────────────────────────── */

async function fetchMovies() {
    const res = await fetch(`${API_BASE}/movie`, { headers: authHeaders() });
    if (!res.ok) throw new Error('Failed to fetch movies');
    return res.json();
}

function renderMovies(movies) {
    const container = document.getElementById('movies-list');
    if (!movies || movies.length === 0) {
        container.innerHTML = '<div class="empty-state">No movies found.</div>';
        return;
    }

    container.innerHTML = `
        <div class="admin-table-wrapper">
            <table class="admin-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Language</th>
                        <th>Duration</th>
                        <th>Rating</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    ${movies.map(m => `
                        <tr>
                            <td>${m.id}</td>
                            <td>${m.name}</td>
                            <td>${m.language}</td>
                            <td>${m.durationInMinutes} min</td>
                            <td>${m.rating ?? '—'}</td>
                            <td class="actions">
                                <button class="btn btn-warning" onclick="openEditPanel(${m.id}, ${JSON.stringify(m).replace(/"/g, '&quot;')})">Edit</button>
                                <button class="btn btn-danger" onclick="deleteMovie(${m.id})">Delete</button>
                            </td>
                        </tr>
                    `).join('')}
                </tbody>
            </table>
        </div>
    `;
}

async function loadMovies() {
    try {
        const movies = await fetchMovies();
        renderMovies(movies);
    } catch (err) {
        document.getElementById('movies-list').innerHTML = '<div class="empty-state">Failed to load movies.</div>';
    }
}

async function createMovie(event) {
    event.preventDefault();
    const body = {
        name: document.getElementById('name').value.trim(),
        language: document.getElementById('language').value.trim(),
        durationInMinutes: parseInt(document.getElementById('duration').value),
        rating: document.getElementById('rating').value ? parseInt(document.getElementById('rating').value) : null,
        posterUrl: document.getElementById('posterUrl').value.trim() || null
    };

    try {
        const res = await fetch(`${API_BASE}/movie/create`, {
            method: 'POST',
            headers: authHeaders(),
            body: JSON.stringify(body)
        });
        if (!res.ok) {
            const err = await res.text();
            showAlert('alert', `Error: ${err}`, 'error');
            return;
        }
        showAlert('alert', 'Movie created successfully!', 'success');
        document.getElementById('create-form').reset();
        loadMovies();
    } catch (err) {
        showAlert('alert', 'Could not connect to server.', 'error');
    }
}

function openEditPanel(id, movie) {
    document.getElementById('edit-id').value = id;
    document.getElementById('edit-name').value = movie.name;
    document.getElementById('edit-language').value = movie.language;
    document.getElementById('edit-duration').value = movie.durationInMinutes;
    document.getElementById('edit-rating').value = movie.rating ?? '';
    document.getElementById('edit-posterUrl').value = movie.posterUrl ?? '';

    const panel = document.getElementById('edit-panel');
    panel.classList.add('show');
    panel.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
}

function closeEditPanel() {
    document.getElementById('edit-panel').classList.remove('show');
}

async function updateMovie(event) {
    event.preventDefault();
    const id = document.getElementById('edit-id').value;
    const body = {
        name: document.getElementById('edit-name').value.trim(),
        language: document.getElementById('edit-language').value.trim(),
        durationInMinutes: parseInt(document.getElementById('edit-duration').value),
        rating: document.getElementById('edit-rating').value ? parseInt(document.getElementById('edit-rating').value) : null,
        posterUrl: document.getElementById('edit-posterUrl').value.trim() || null
    };

    try {
        const res = await fetch(`${API_BASE}/movie/update/${id}`, {
            method: 'PUT',
            headers: authHeaders(),
            body: JSON.stringify(body)
        });
        if (!res.ok) {
            const err = await res.text();
            showAlert('alert', `Error: ${err}`, 'error');
            return;
        }
        showAlert('alert', 'Movie updated successfully!', 'success');
        closeEditPanel();
        loadMovies();
    } catch (err) {
        showAlert('alert', 'Could not connect to server.', 'error');
    }
}

async function deleteMovie(id) {
    if (!confirm(`Delete movie #${id}? This action cannot be undone.`)) return;
    try {
        const res = await fetch(`${API_BASE}/movie/delete/${id}`, {
            method: 'DELETE',
            headers: authHeaders()
        });
        if (!res.ok) {
            const err = await res.text();
            showAlert('alert', `Error: ${err}`, 'error');
            return;
        }
        showAlert('alert', 'Movie deleted successfully!', 'success');
        loadMovies();
    } catch (err) {
        showAlert('alert', 'Could not connect to server.', 'error');
    }
}

document.addEventListener('DOMContentLoaded', loadMovies);
