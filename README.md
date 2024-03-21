# VulZero

## Overview
VulZero is a vulnerability disclosure platform designed to facilitate the reporting and management of security vulnerabilities in software systems. The platform allows hackers, companies, and internal teams to collaborate on identifying, reporting, and resolving vulnerabilities.

## Features
- **User Registration**: Users can register with different roles such as Hacker, Company, Internal Team, and Manager.
- **User Authentication**: Registered users can log in securely using their credentials.
- **Vulnerability Reporting**: Users can submit reports detailing security vulnerabilities they have discovered.
- **Report Management**: Managers can oversee and manage disclosed vulnerability reports.
- **Scoring System**: Hackers earn points based on the severity and impact of the vulnerabilities they report.
- **Leaderboard**: Displays the ranking of hackers based on their earned points.
- **Program Creation and Management**: Companies can create and manage vulnerability disclosure programs.

## Technologies Used
- **Java**: Core programming language used for backend development.
- **SQLite**: Lightweight relational database for data storage.
- **JDBC**: Java Database Connectivity for interacting with the SQLite database.
- **Apache Commons Codec**: Library for secure password hashing.

## Setup
1. Clone the repository to your local machine.
2. Ensure you have Java and Maven installed.
3. Set up the SQLite database by executing the provided SQL scripts (`create_tables.sql`).
4. Configure the database connection in `DatabaseService.java`.
5. Build the project using Maven: `mvn clean install`.
6. Run the application: `java -jar VulZero.jar`.

## Usage
1. Register as a user with your desired role.
2. Log in with your credentials.
3. Submit vulnerability reports or manage reports if you're a manager.
4. Earn points for reporting vulnerabilities.
5. View the leaderboard to see your ranking among other hackers.

## Contributors
- [Sanjana Choudhary](https://github.com/Sanjana088)
