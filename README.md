🚀 BackEnd Deployment Instructions
🧩 Spring Boot Backend Deployment (Java 8)

Navigate to the Backend Project Folder.
Build the Project Using Maven:mvn clean package.
After the build completes, the .war file will be located inside the target/ directory.
Copy the generated WAR file to the webapps directory of your Tomcat server.
Start Tomcat to deploy the backend.
The backend is configured to allow cross-origin requests from:http://localhost:3000.


🎨 React Frontend Setup & Run Instructions

Navigate to the Frontend Project Folder.
Install Dependencies:npm install.
Install Required Packages:npm install axios cors.
Start the Frontend Development Server:npm run dev.
The frontend will run at:http://localhost:3000.

