# Setup Guide for PFNET Load Balancer Repository

This guide explains the steps required to set up and run the PFNET Load Balancer components.

## Prerequisites

1. **Java Development Kit (JDK):** Ensure JDK 11 or later is installed.
   ```bash
   java -version
   ```
   
2. **Node.js and npm:** Install Node.js (v14 or later) for running the dashboard.
   ```bash
   node -v
   npm -v
   ```

3. **Dependencies:** Install any required libraries or tools specified in the respective projects.

## Steps to Set Up

### 1. Clone the Repository

Clone this repository to your local machine:
```bash
git clone https://github.com/yourusername/pfnet-load-balancer.git
cd pfnet-load-balancer
```

### 2. Configure Properties

Edit the `LoadBalancerConfig.properties` file to specify the configuration:
```properties
maxTasks=1000
maxRetries=3
checkInterval=5000
```
Customize these values as per your requirements.

### 3. Compile and Run Java Services

#### Compile Java Code:
```bash
javac -d bin -sourcepath src src/com/pfnet/loadbalancer/*.java
```

#### Run Services:

- **HealthCheckService:**
  ```bash
  java -cp bin com.pfnet.loadbalancer.HealthCheckService
  ```

- **TaskRetryManager:**
  ```bash
  java -cp bin com.pfnet.loadbalancer.TaskRetryManager
  ```

### 4. Set Up the Dashboard

#### Install Dependencies:
Navigate to the dashboard directory and install dependencies:
```bash
cd dashboard
npm install
```

#### Run the Dashboard:
```bash
node LoadBalancerDashboard.js
```
Access the dashboard in your browser at `http://localhost:3000`.

### 5. Validate the Setup

- **Java Services:** Ensure the services are running without errors.
- **Dashboard:** Open the URL and verify the metrics are displayed correctly.

## Troubleshooting

- **Common Errors:**
  - Missing dependencies: Check if all required tools are installed.
  - Configuration issues: Ensure the `LoadBalancerConfig.properties` file has valid settings.

- **Logs:** Check console output for any error messages.

If you encounter any issues, refer to the documentation or contact the repository maintainer.

---

Happy coding! 🎉
