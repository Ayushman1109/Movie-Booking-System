userTickets = [
    {
        movieName: '3 Idiots',
        showtime: '2024-07-01 19:00',
        seats: ['A1', 'A2'],
        cost: 30
    },
    {
        movieName: 'Interstellar',
        showtime: '2024-07-02 21:00',
        seats: ['B5', 'B6', 'B7'],
        cost: 75
    }
]

function renderTickets(tickets) {
    const ticketsContainer = document.getElementById('tickets-container');
    ticketsContainer.innerHTML = '';
    tickets.forEach(booking => {
        const bookingElement = document.createElement('div');
        bookingElement.classList.add('booking');
        bookingElement.innerHTML = `
            <h3>${booking.movieName}</h3>
            <p><strong>Showtime:</strong> ${booking.showtime}</p>
            <p><strong>Seats:</strong> ${booking.seats.join(', ')}</p>
            <p><strong>Cost:</strong> $${booking.cost}</p>
            <button onclick="cancelTicket('${booking.movieName}', '${booking.showtime}')">Cancel Ticket</button>
        `;
        ticketsContainer.appendChild(bookingElement);
    });
}

function cancelTicket(movieName, showtime) {
    const index = userTickets.findIndex(ticket => ticket.movieName === movieName && ticket.showtime === showtime);
    if (index !== -1) {
        userTickets.splice(index, 1);
        renderTickets(userTickets);
    }
}

renderTickets(userTickets);