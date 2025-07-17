# 🎟️ Event Ticket Platform

A backend platform for creating and managing events, selling tickets, validating entries, and generating reports — built using **Spring Boot**, **PostgreSQL**, **MapStruct**, and **Keycloak**.

> 📘 Project inspired by [Devtiro](https://www.devtiro.com) and licensed under [CC BY-NC-SA 4.0](https://creativecommons.org/licenses/by-nc-sa/4.0/)

---

## ✅ Features Completed

### 🔧 Project Setup
- ✅ Spring Boot project initialized (Maven, Java 21, Spring Boot 3.4.4)
- ✅ PostgreSQL configured using Docker
- ✅ Keycloak integrated for user authentication
- ✅ MapStruct configured with Lombok support
- ✅ Domain entities created: `Event`, `User`, `TicketType`, `Ticket`, `QrCode`, `TicketValidation`

### 📦 Domain Layer
- ✅ Enum definitions for status and validation logic
- ✅ JPA mappings with relationships and audit fields

### 🧑‍💻 Business Logic
- ✅ Create Event: `POST /api/v1/events`
- ✅ Update Event: `PUT /api/v1/events/{eventId}`
- ✅ MapStruct mappers for DTO ↔ Entity transformations
- ✅ Basic controller, service, and repository layers

---

## 🚧 Work in Progress

### Upcoming Features
- [x] List Events: `GET /api/v1/events`
- [x] Retrieve Event: `GET /api/v1/events/{eventId}`
- [x] Delete Event: `DELETE /api/v1/events/{eventId}`
- [x] Ticket Type Management:
  - [x] Create / Update / Delete Ticket Types
  - [x] Ticket type listing endpoints
- [x] Ticket Purchase flow for attendees
- [x] QR Code generation and ticket validation
- [x] Sales dashboard & reporting features

---

## 🛠️ Technologies Used

- **Spring Boot** (REST API)
- **PostgreSQL** (Database)
- **Keycloak** (Auth server)
- **MapStruct** (DTO mapping)
- **Lombok** (Boilerplate reduction)
- **Docker Compose** (Database & Keycloak setup)

---

## 🧪 Running Locally

```bash
# Start PostgreSQL and Keycloak
docker-compose up

# Run the Spring Boot backend
./mvnw spring-boot:run
