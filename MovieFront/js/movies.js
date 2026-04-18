document.addEventListener('DOMContentLoaded', async () => {
    const moviesContainer = document.getElementById('movies-container');
    if (!moviesContainer) return;

    const token = localStorage.getItem('jwtToken');
    if (!token) {
        moviesContainer.innerHTML = '<p>Please <a href="auth.html">login</a> to view movies.</p>';
        return;
    }

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
        const shows = await response.json();
        renderMoviesFromShows(shows);
    } catch (err) {
        moviesContainer.innerHTML = `<p>Error loading movies: ${err.message}</p>`;
    }
});

function renderMoviesFromShows(shows) {
    const moviesContainer = document.getElementById('movies-container');
    moviesContainer.innerHTML = '';

    if (!shows.length) {
        moviesContainer.innerHTML = '<p>No movies available at the moment.</p>';
        return;
    }

    // Group shows by movie id, keeping one representative show per movie
    const movieMap = new Map();
    shows.forEach(show => {
        if (show.movie && !movieMap.has(show.movie.id)) {
            movieMap.set(show.movie.id, show.movie);
        }
    });

    movieMap.forEach(movie => {
        const posterHTML = movie.posterUrl
            ? `<img src="${movie.posterUrl}" alt="${movie.name} poster" class="movie-poster">`
            : '';
        const movieElement = document.createElement('div');
        movieElement.classList.add('movie');
        movieElement.innerHTML = `
            ${posterHTML}
            <h3>${movie.name}</h3>
            <p>${movie.language} &middot; Rating: ${movie.rating}/10 &middot; ${movie.durationInMinutes} min</p>
            <button class="book-button" data-id="${movie.id}" data-name="${encodeURIComponent(movie.name)}">Book Now</button>
        `;
        moviesContainer.appendChild(movieElement);
    });

    document.querySelectorAll('.book-button').forEach(button => {
        button.addEventListener('click', book);
    });
}

function book(event) {
    const movieId   = event.target.getAttribute('data-id');
    const movieName = decodeURIComponent(event.target.getAttribute('data-name'));
    localStorage.setItem('selectedMovie', JSON.stringify({ id: movieId, name: movieName }));
    window.location.href = 'book.html';
}