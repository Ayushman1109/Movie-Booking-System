function backToHome() {
    window.location.href = 'index.html';
}

document.getElementById('back-button').addEventListener('click', backToHome);

function loadNavbar() {
    const navbarHTML = `
        <nav class="navbar">
            <ul class="nav-links">
                <li><a href="index.html">Home</a></li>
                <li><a href="movies.html">Movies</a></li>
                <li><a href="tickets.html">Bookings</a></li>
                <li><a href="contact.html">Contact Us</a></li>
            </ul>
        </nav>
    `;

    const placeholder = document.getElementById('navbar-placeholder');

    if (placeholder) {
        placeholder.innerHTML = navbarHTML;
    }
}

document.addEventListener('DOMContentLoaded', loadNavbar);