# 🚀 rest-mvc-boilerplate

A **Java Spring Boot** boilerplate project for quickly building RESTful APIs using a layered MVC architecture.

## 📖 Overview

This project provides a clean, opinionated foundation for Spring Boot REST applications, following best practices with clear separation of concerns:

- **Controllers** – handle HTTP requests
- **Services** – contain business logic
- **Repositories** – handle data access
- **Models/Entities** – represent domain data

It is designed to help you start new Spring Boot projects faster by providing a simple, maintainable structure.

---

## ⚙️ Features

✅ Spring Boot starter template  
✅ Layered MVC structure  
✅ Simple REST example
✅ Easy to extend with new entities/resources  
✅ Maven-based build

---

## 🛠️ Technologies Used

- Java 17+
- Spring Boot
- Spring Web
- Spring Data JPA
- Maven

---

## 🚀 Getting Started

### ✅ Prerequisites

- Java 17 or higher
- Maven 3.x

### ✅ Clone the Repository

```bash
git clone https://github.com/kurata/rest-mvc-boilerplate.git
cd rest-mvc-boilerplate
```

### ✅ Build the Project

```bash
mvn clean install
```

### ✅ Run the Application

```bash
mvn spring-boot:run
```

By default, the application runs at:

```
http://localhost:8080
```

---

## 📚 Example Endpoint

> Example (if you keep the sample CustomerController):
- GET /api/customers – list all customers
- POST /api/customers – create a new customer
- GET /api/customers/{id} – get customer by ID
- PUT /api/customers/{id} – update customer
- DELETE /api/customers/{id} – delete customer

*(Endpoints may vary depending on how you customize the boilerplate.)*


---

## 🐳 Running with Docker
Optional: You can dockerize your project.

Example steps:

### 1️⃣ Build the Docker image

```bash
docker build -t rest-mvc-boilerplate .
```

### 2️⃣ Run the container
```bash
docker run -p 8080:8080 rest-mvc-boilerplate
```
You can also pass environment variables:
```bash
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://db:3306/mydb \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_DATASOURCE_PASSWORD=secret \
  rest-mvc-boilerplate
```
## 🤝 Contributing
Contributions are welcome!

1. Fork the repository
2. Create your feature branch (git checkout -b feature/my-feature)
3. Commit your changes (git commit -m 'Add my feature')
4. Push to the branch (git push origin feature/my-feature)
5. Open a Pull Request

## 📄 License
This project is licensed under the MIT License.
