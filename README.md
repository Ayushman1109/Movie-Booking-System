#  Movie Booking System

A secure, RESTful API for a movie theatre booking platform built with Spring Boot. 
This system handles user authentication, role-based access control (RBAC), theatre, hall, show, movie management and ticket booking.

## Tech Stack
* **Language:** Java 25+
* **Framework:** Spring Boot
* **Security:** Spring Security & JSON Web Tokens (JWT)
* **Database:** PostgreSQL & Spring Data JPA (Hibernate)
* **Validation:** Hibernate Validator
* **Utilities:** Lombok, Maven

## Key Features
* **Stateless Authentication:** JWT-based login and registration.
* **Role-Based Access Control (RBAC):** Distinct `USER` and `ADMIN` privileges. Admins can manage movies, theatres, halls and shows. Users can browse shows and book tickets.
* **Secure Configuration:** Sensitive credentials and secret keys are managed via Environment Variables.
* **Robust Input Validation:** Prevents bad data at the controller level using DTO validation (`@NotBlank`, `@Email`, etc.).
* **Global Exception Handling:** Clean, structured JSON error responses for validation failures and unauthorized access.
* **Automated Bootstrap:** Automatically provisions a default Superadmin on initial startup.

## Prerequisites
To run this application locally, you need:
* Java Development Kit (JDK) 25 or higher
* Maven
* PostgreSQL installed and running locally
* An API Client (Postman, Insomnia, etc.)

## Environment Variables
For security, this project relies on environment variables.  
Create a New File called env.properties in `Movie Booking System\MovieBack\src\main\resources` and add the following variables

| Variable Name | Description | Example |
| :--- | :--- | :--- |
| `JWT_SECRET_KEY` | A 256-bit Base64 encoded secret key for signing tokens | `404E6352...` |
| `DB_URL` | URL to your database connection | `jdbc:postgresql://localhost:5432/movie` |
| `DB_USER` | Database username | `postgres` |
| `DB_PASSWORD` | Database password | `samplepass` |
| `ADMIN_USERNAME` | Default admin username (created on startup) | `superadmin` |
| `ADMIN_EMAIL` | Default admin email | `admin@movie.com` |
| `ADMIN_PASSWORD` | Default admin password | `adminpassword123` |


## Getting Started

1. **Clone the repository:**
   ```bash
   git clone https://github.com/yourusername/movie-booking-system.git
   cd movie-booking-system
2. **Setup the env.properties file:**
   * Follow the instructions in the environment variables heading to create the file.
3. **Setup the Database:**
   * Create a PostgreSQL database with the same name as in the DB_URL in the env.properties file.
4. **Run the Application:**
   * Open the project in IntelliJ IDEA/Eclipse/VS Code.
   * Navigate to `src/main/java/com/ayushman/movie/MovieBookingSystemApplication.java`.
   * Click the green **Run** button next to the `main` method.