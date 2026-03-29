#  Contact Manager

A Java console application to manage contacts with full CRUD operations, connected to a MySQL database.

##  Technologies Used
- Java
- MySQL
- JDBC (MySQL Connector)

##  Features
- Add a new contact (name, phone, age)
- View all contacts
- Search a contact by name
- Update a contact's phone and age
- Delete a contact

##  How to Run

### Prerequisites
- Java JDK installed
- MySQL Workbench installed
- MySQL Connector JAR added to the project

### Database Setup
Run this in MySQL Workbench:
```sql
CREATE DATABASE contact_manager;
USE contact_manager;

CREATE TABLE contacts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    phone VARCHAR(20),
    age INT
);
```

### Configuration
Create a `config.properties` file in the project root:
```properties
db.url=jdbc:mysql://localhost:3306/contact_manager
db.user=root
db.password=yourpassword
```

## 👨‍💻 Author
- GitHub: [@bouabidihind7-blip](https://github.com/bouabidihind7-blip)
