# Digital Evidence and Case Management System
Java Servlet + JSP + JDBC + MySQL (Tomcat 10.1 / Java 11+)

## Setup
1. Install JDK 11+, Apache Tomcat 10.1, MySQL 8+, and Eclipse Enterprise Java.
2. Create database: run `database/schema.sql` in MySQL Workbench.
3. Edit `src/main/java/com/decms/util/DB.java` with your MySQL username/password.
4. Import this folder into Eclipse as **Dynamic Web Project** (or create project and copy `src` and `WebContent`).
5. Ensure project targets Tomcat 10.1 and Java 11+. Add MySQL Connector/J to `WEB-INF/lib` (download from MySQL; do not commit credentials).
6. Deploy to Tomcat and open `http://localhost:8080/DigitalEvidenceCaseManagement/`.
7. Login: `admin` / `admin123` (demo credentials; change before any real use).

## Features
- Login/session authentication
- Dashboard counts
- Case create/read/update/delete
- Suspect create/read/update/delete
- Evidence metadata upload (file saved under `uploads/`; SHA-256 recorded)
- Investigation reports CRUD
- Case status tracking

## Important demo limitations
This is a college demonstration starter. It stores uploaded files locally and uses a simple demo login. Before any real deployment, add password hashing, role-based access, CSRF protection, upload size/type validation, secure storage, audit logging, backups, and legal/chain-of-custody controls. Do not use real sensitive evidence or personal data in this demo.
