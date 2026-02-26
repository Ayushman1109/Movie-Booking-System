const availableMovies = [
    {
        name: 'The Dark Knight',
        language: 'English',
        rating: '9.0',
        poster: 'https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcQkUywIUXDjHSQJIaNHYVs08osgBpF5Ot-xmB_omyEZeeRP9Xug',
        cost: 20
    },
    {
        name: 'Andhadhun',
        language: 'Hindi',
        rating: '8.2',
        poster: 'https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcS3o9KABKNm4eOD8-ej_c1uNaAh7bKXOZtHow2Wx_s0YUg6urGw',
        cost: 12
    },
    {
        name: 'Inception',
        language: 'English',
        rating: '8.8',
        poster: 'https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcQovCe0H45fWwAtV31ajOdXRPTxSsMQgPIQ3lcZX_mAW0jXV3kH',
        cost: 35
    },
    {
        name: '3 Idiots',
        language: 'Hindi',
        rating: '8.4',
        poster: 'https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcQV7sONOx4fl1xq9CbdWUmcTamWwzrPMzqKhZOGHh-V0zHpn0Ly',
        cost: 15
    },
    {
        name: 'Interstellar',
        language: 'English',
        rating: '8.6',
        poster: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT9oW0XQlu1lo1G_49M-YwGzKR6rUg-CtflZj07HfbT8d2GwKWg',
        cost: 25
    },
    {
        name:'Don',
        language: 'Hindi',
        rating: '7.8',
        poster: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ8KDzNOeKG_Rdsy4f_P_k04aIhzKAqQE5cNLPUaFC23UP_nv8N',
        cost: 10
    }
];

document.addEventListener('DOMContentLoaded', () => {
    const moviesContainer = document.getElementById('movies-container');
    if (moviesContainer) {
        renderMovies(availableMovies);
    }
});

function renderMovies(movies) {
    const moviesContainer = document.getElementById('movies-container');
    moviesContainer.innerHTML = '';
    
    movies.forEach((movie, index) => {
        const movieElement = document.createElement('div');
        movieElement.classList.add('movie');
        movieElement.innerHTML = `
            <h3>${movie.name}</h3>
            <img src="${movie.poster}" alt="${movie.name} poster" class="movie-poster">
            <p> ${movie.language} &middot; ${movie.rating}</p>
            <p>Cost: $${movie.cost}</p>
            <button class="book-button" data-index="${index}">Book Now</button>
        `;
        moviesContainer.appendChild(movieElement);
    });

    document.querySelectorAll('.book-button').forEach(button => {
        button.addEventListener('click', book);
    });
}

function book(event) {
    const movieIndex = event.target.getAttribute('data-index');
    const selectedMovie = availableMovies[movieIndex];
    localStorage.setItem('selectedMovie', JSON.stringify(selectedMovie));
    window.location.href = 'book.html';
}