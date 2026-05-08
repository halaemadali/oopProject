# oopProject
# Hotel Reservation System

### CSE241 Object-Oriented Programming  
**Ain Shams University — Faculty of Engineering**  
**2nd Semester 2025/2026**  
*Team 7*

---

## Project Overview

A desktop Hotel Reservation System built in Java using JavaFX and SQLite, applying core Object-Oriented Programming principles and software design concepts.

- **Milestone 1** focused on the complete backend implementation and OOP architecture.
- **Milestone 2** introduced the graphical user interface (GUI), database integration using full system interaction through JavaFX.

The system supports hotel reservation management for guests, receptionists, and administrators through separate dashboards and functionalities.

---

## Team Members

| Name | Student ID | Backend Contribution | Frontend / Milestone 2 Contribution |
|------|-------------|----------------------|-------------------------------------|
| Salma Khaled Shafek | 25p0196 | Reservation, Invoice | Reservation screens, Admin Dashboard |
| Mariam Mohamed Ramzy | 25p0129 | Guest, HotelDatabase | Login and Register, CSS Styling |
| Hala Emad Ali Abdelkawy | 25p0130 | Room, RoomType, Amenity | Receptionist Dashboard |
| Sara Ahmed Mostafa ElNahass | 25p0013 | Staff, Admin, Receptionist | Check out and Welcome Screens |
| Hagar Hossam El Deen Abd El Majeed | 25p0116 | Room, RoomType, Amenity | Guest Dashboard |

---

## Milestone 2 Features

### GUI Features
- JavaFX graphical interface
- Multiple dashboards for:
  - Guest
  - Admin
  - Receptionist
- Scene navigation using FXML
- Styled UI with CSS and responsive layouts
- Background images and custom hotel branding


### Authentication System
- Login and registration system
- Role-based access control
- Session navigation between screens

---

## How to Run

1. Clone the repository
2. Open the project in IntelliJ IDEA
3. Make sure JavaFX SDK is configured
4. Navigate to `src/com/hotel/Main.java`
5. Right click `Main.java` → **Run**

### Requirements
- Java JDK 17 or higher
- IntelliJ IDEA
- JavaFX SDK

---

## Project Structure

```text
src/
 ├── com.hotel.controllers
 ├── com.hotel.database
 ├── com.hotel.enums
 ├── com.hotel.exceptions
 ├── com.hotel.interfaces
 ├── com.hotel.models
 ├── Resources/
 │    ├── fxml
 │    ├── css
 │    └── images
 └── Main.java
```

---

## OOP Concepts Used

- **Inheritance** — Admin and Receptionist extend Staff
- **Abstraction** — Staff is an abstract class
- **Encapsulation** — all fields are private with getters/setters
- **Polymorphism** — Staff methods overridden in subclasses
- **Interfaces** — Manageable and Payable define contracts
- **Custom Exceptions** — meaningful error handling throughout

---

## Key Features

### Guest
- Register and login
- Browse available rooms by type, view, and dates
- Make, view, and cancel reservations
- Select amenities for reservations
- Checkout and pay invoices

### Admin
- Full CRUD on rooms, room types, and amenities
- Add amenities to rooms
- Add receptionist accounts
- Manage hotel data through GUI dashboards

### Receptionist
- View all guests, rooms, and reservations
- Confirm and cancel reservations
- Check-in and check-out guests
- Process payments

---

## Pre-loaded Test Data

The system launches with dummy data for immediate testing:

- 29 guests
- 130 rooms
- 4 room types (Single, Double, Triple, Suite)
- 5 amenities
  - WiFi
  - TV
  - Mini Bar
  - Air Conditioning
  - Room Service
- 2 admins
- 2 receptionists
- 40 reservations

---

## Test Credentials

### Admin
- Username: `admin1`
- Password: `admin123`

### Receptionist
- Username: `receptionist1`
- Password: `recep123`

### Guest
- Username: `hana_hassan_0`
- Password: `pass00`

---



