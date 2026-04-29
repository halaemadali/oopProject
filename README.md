# oopProject
# Hotel Reservation System
### CSE241 Object-Oriented Programming
**Ain Shams University — Faculty of Engineering**
**2nd Semester 2025/2026**
*Team 7*

---

## Project Overview
A desktop Hotel Reservation System built in Java, applying 
core OOP principles. This is Milestone 1 focusing on the 
complete backend structure.

---

## Team Members
| Name | Student ID | Contribution |
|------|-----------|--------------|
| Salma Khaled Shafek | 25p0196 | Reservation, Invoice |
| Mariam MohamedRamzy | 25p0129 | Guest, HotelDateBase |
| Hala Emad Ali Abdelkawy | 25p0130 | Room, RoomType, Amenity |
| Sara Ahmed Mostafa ElNahass | 25p0013 | Staff, Admin, Receptionist |
| Hagar Hossam El Deen Abd El Majeed | 25p0116 | Room, RoomType, Amenity |

---

## How to Run
1. Clone the repository
2. Open in IntelliJ IDEA
3. Navigate to src/Main.java
4. Right click Main.java → Run

**Requirements:**
- Java JDK 17 or higher
- IntelliJ IDEA

## Project Structure
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
**Guest:**
- Register and login
- Browse available rooms by type, view and dates
- Make, view and cancel reservations
- Select amenities for reservation
- Checkout and pay invoice

**Admin:**
- Full CRUD on rooms, room types and amenities
- Add amenities to rooms
- Add receptionist accounts

**Receptionist:**
- View all guests, rooms and reservations
- Confirm and cancel reservations
- Check in and check out guests
- Process payments

---

## Pre-loaded Test Data
The system launches with dummy data for immediate testing:
- 29 guests
- 130 rooms
- 4 room types (Single, Double, Triple, Suite)
- 5 amenities (WiFi, TV, Mini Bar, Air Conditioning, Room Service)
- 2 admins
- 2 receptionists
- 40 reservations

**Test Credentials:**
- Admin: username = `admin1` password = `admin123`
- Receptionist: username = `receptionist1` password = `recep123`
- Guest: username = `hana_hassan_0` password = `pass00`

---

---

