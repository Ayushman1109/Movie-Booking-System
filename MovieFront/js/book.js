document.addEventListener('DOMContentLoaded', () => {
    const bookContainer = document.getElementById('book-container');
    if (bookContainer) {
        initializeBookingPage();
    }
});

function initializeBookingPage() {
    const savedMovieString = localStorage.getItem('selectedMovie');
    
    if (savedMovieString) {
        const bookDetails = JSON.parse(savedMovieString);
        renderBookDetails(bookDetails);
    } else {
        document.getElementById('book-container').innerHTML = '<p>No movie selected. <a href="index.html">Go back</a></p>';
    }
}

function renderBookDetails(details) {
    const bookContainer = document.getElementById('book-container');
    bookContainer.innerHTML = `
        <h2>${details.name}</h2>
        <img src="${details.poster}" alt="${details.name} poster" class="book-poster">
        <p>Language: ${details.language}</p>
        <p>Rating: ${details.rating}</p>
        <label for="ticket-quantity">Number of tickets:</label>
        <input type="number" id="ticket-quantity" placeholder="Enter number of tickets" min="1">
        <br><br>
        <div id="total-cost">Total Cost: $0.00</div>
        <button id="confirm-booking">Confirm Booking</button>
    `;
    
    const movieCost = parseFloat(details.cost);
    document.getElementById('ticket-quantity').addEventListener('input', () => calculateTotal(movieCost));
}

function calculateTotal(price) {
    const quantityInput = document.getElementById('ticket-quantity');
    const totalCostDiv = document.getElementById('total-cost');
    const quantity = parseInt(quantityInput.value, 10);
    
    if (!isNaN(quantity) && quantity >= 1) {
        const total = quantity * price;
        totalCostDiv.innerHTML = `Total Cost: $${total.toFixed(2)}`;
    } else {
        totalCostDiv.innerHTML = 'Total Cost: $0.00';
    }
}