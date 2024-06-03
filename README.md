<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <h1>Spring JWT Demo</h1>
    <p>This project is a Users Management Portal built with Spring Boot and React.js. It includes user authentication and authorization features implemented using Spring Security and JWT. The portal allows for user role management and secures endpoints based on user roles.</p>
    <h2>Features</h2>
    <ul>
        <li><strong>User Authentication</strong>: Secure login and registration using JWT.</li>
        <li><strong>User Authorization</strong>: Role-based access control to different endpoints.</li>
        <li><strong>User Management</strong>: Add, edit, delete, and view user details.</li>
        <li><strong>Role Management</strong>: Assign and manage user roles.</li>
        <li><strong>React.js Frontend</strong>: Responsive and dynamic user interface.</li>
        <li><strong>MySQL Database</strong>: Persistent storage for user data.</li>
    </ul>
    <h2>Technologies Used</h2>
    <ul>
        <li><strong>Backend</strong>:
            <ul>
                <li>Spring Boot</li>
                <li>Spring Security</li>
                <li>JWT (JSON Web Tokens)</li>
                <li>MySQL</li>
            </ul>
        </li>
        <li><strong>Frontend</strong>:
            <ul>
                <li>React.js</li>
                <li>Axios</li>
                <li>Bootstrap</li>
            </ul>
        </li>
    </ul>
    <h2>Getting Started</h2>
    <h3>Prerequisites</h3>
    <ul>
        <li>Java 11 or higher</li>
        <li>Node.js and npm</li>
        <li>MySQL</li>
        <li>Maven</li>
    </ul>
    <h3>Installation</h3>
    <h4>Backend</h4>
    <ol>
        <li>Clone the repository:
            <pre><code>git clone https://github.com/PhuongTay1109/spring-jwt-demo
cd backend/UserManagement
            </code></pre>
        </li>
        <li>Configure the database in <code>application.properties</code>:
            <pre><code>spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
            </code></pre>
        </li>
        <li>Build the project:
            <pre><code>mvn clean install
            </code></pre>
        </li>
        <li>Run the application:
            <pre><code>mvn spring-boot:run
            </code></pre>
        </li>
    </ol>
    <h4>Frontend</h4>
    <ol>
        <li>Navigate to the frontend directory:
            <pre><code>cd ../frontend
            </code></pre>
        </li>
        <li>Install dependencies:
            <pre><code>npm install
            </code></pre>
        </li>
        <li>Start the React development server:
            <pre><code>npm start
            </code></pre>
        </li>
    </ol>
    <h2>Usage</h2>
    <ol>
        <li>Open the application in your browser at <a href="http://localhost:3000">http://localhost:3000</a>.</li>
        <li>Register a new user or login with existing credentials.</li>
        <li>Access user management features according to your role.</li>
    </ol>
</body>
</html>
