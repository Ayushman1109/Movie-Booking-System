#  Movie Booking System

A secure, RESTful API for a movie theatre booking platform built with Spring Boot. 
This system handles user authentication, role-based access control (RBAC), theatre, hall, show, movie management and ticket booking.

## Tech Stack
* **Containerization:** Docker & Docker Compose
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
* **Automated Data Seeding:** Instantly populates the database with dummy theatres, movies, and bookable shows on first run via `data.sql`.

## Prerequisites
To run this application, you need:
* **Docker Desktop** (or equivalent for Mac/Linux, **must be running** to execute Docker commands)
* Alternatively, for manual setup without Docker:
  * Java Development Kit (JDK) 25 or higher
  * Maven
  * PostgreSQL 18+ installed and running locally

## Environment Variables
For security, this project relies on environment variables.  
Create a New File called env.properties in `MovieBack/src/main/resources` and add the following variables.

| Variable Name | Description | Example |
| :--- | :--- | :--- |
| `SECRET_KEY` | A 256-bit Base64 encoded secret key for signing tokens | `404E6352...` |
| `DB_URL` | URL to your database connection | `jdbc:postgresql://database:5432/movie_db` (Docker) or `jdbc:postgresql://localhost:5432/movie_db` (Local) |
| `DB_USER` | Database username | `postgres` |
| `DB_PASSWORD` | Database password | `samplepass` |
| `ADMIN_USERNAME` | Default admin username (created on startup) | `superadmin` |
| `ADMIN_EMAIL` | Default admin email | `admin@movie.com` |
| `ADMIN_PASSWORD` | Default admin password | `adminpassword123` |

## Getting Started

### Method 1: Using Docker (Recommended)
The easiest way to run the entire application stack (Database, Backend API, and Frontend HTML/JS) is using Docker Compose.

1. **Clone the repository:**
   ```bash
   git clone https://github.com/yourusername/movie-booking-system.git
   cd movie-booking-system
   ```
2. **Setup the env.properties file:**
   * Navigate to `MovieBack/src/main/resources` and create `env.properties` following the instructions above.
3. **Start the application:**
   ```bash
   docker-compose up --build
   ```
4. **Access the application:**
   * **Frontend Website:** Open your browser to http://localhost
   * **Backend API:** Available at http://localhost:8080
   * **Database:** Exposed on localhost:5432
5. **Access the Admin Panel:**
   * On the frontend website, log in using the `ADMIN_USERNAME` and `ADMIN_PASSWORD` you configured in `env.properties`.
   * Once logged in as an admin, an "Admin" link will appear in the top navigation bar.

### Method 2: Manual Local Setup (Without Docker)

1. **Setup the env.properties file:**
   * Navigate to `MovieBack/src/main/resources` and follow the instructions in the environment variables heading to create the file. Be sure `DB_URL` points to `localhost`.
2. **Setup the Database:**
   * Create a PostgreSQL database with the same name as in the `DB_URL`.
3. **Run the Backend Application:**
   * Open the backend project (`MovieBack`) in your IDE or run `mvn spring-boot:run` in that directory.
4. **Run the Frontend Application:**
   * Open the frontend project (`MovieFront`) using a Live Server extension in VS Code or any static HTTP server.
   * Start by opening `index.html`.
5. **Access the Admin Panel:**
   * Log in using the `ADMIN_USERNAME` and `ADMIN_PASSWORD` you configured.