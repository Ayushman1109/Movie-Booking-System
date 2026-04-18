const API_BASE = 'http://localhost:8080';

function getToken() {
    return localStorage.getItem('jwtToken');
}

function getCurrentUserName() {
    return localStorage.getItem('currentUserName');
}

function logout() {
    localStorage.removeItem('jwtToken');
    localStorage.removeItem('currentUserName');
    window.location.href = '../auth.html';
}

function isAdmin() {
    const token = getToken();
    if (!token) return false;
    try {
        const payload = JSON.parse(atob(token.split('.')[1]));
        const role = payload.role ||
            (Array.isArray(payload.authorities) && payload.authorities[0]);
        return role === 'ADMIN' || role === 'ROLE_ADMIN';
    } catch (e) { return false; }
}

function requireAdmin() {
    if (!getToken()) {
        window.location.href = '../auth.html';
        return false;
    }
    if (!isAdmin()) {
        alert('Access denied. Admin privileges required.');
        window.location.href = '../../index.html';
        return false;
    }
    return true;
}

function authHeaders() {
    return {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${getToken()}`
    };
}

function showAlert(containerId, message, type = 'success') {
    const el = document.getElementById(containerId);
    if (!el) return;
    el.textContent = message;
    el.className = `alert alert-${type} show`;
    setTimeout(() => {
        el.classList.remove('show');
    }, 4000);
}

function loadAdminNavbar() {
    const navbarHTML = `
        <nav class="navbar">
            <ul class="nav-links">
                <li><a href="../../index.html">Home</a></li>
                <li><a href="index.html">Admin</a></li>
                <li><a href="movies.html">Movies</a></li>
                <li><a href="shows.html">Shows</a></li>
                <li><a href="theatres.html">Theatres</a></li>
                <li><a href="halls.html">Halls</a></li>
            </ul>
        </nav>
    `;
    const placeholder = document.getElementById('navbar-placeholder');
    if (placeholder) placeholder.innerHTML = navbarHTML;
}

function loadAuthBadge() {
    const badge = document.getElementById('auth-badge-placeholder');
    if (!badge) return;

    const userName = getCurrentUserName();
    if (userName) {
        badge.innerHTML = `
            <div class="auth-badge">
                <span class="auth-badge-user">👤 ${userName}</span>
                <a href="#" onclick="logout(); return false;" class="auth-badge-link">Logout</a>
            </div>`;
    } else {
        badge.innerHTML = `
            <div class="auth-badge">
                <a href="../auth.html" class="auth-badge-link">Login / Register</a>
            </div>`;
    }
}

document.addEventListener('DOMContentLoaded', () => {
    requireAdmin();
    loadAdminNavbar();
    loadAuthBadge();
});
