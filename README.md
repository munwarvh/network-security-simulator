# Forescout Network Challenge 2023

A comprehensive Java-based network management system featuring dual-stack (IPv4/IPv6) host management, network operations, and malware spread pattern simulation for cybersecurity analysis.

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [Architecture](#architecture)
- [Testing](#testing)
- [Technologies](#technologies)
- [License](#license)

## 🎯 Overview

This project was developed as a solution for the Forescout Challenge 2023. It implements a robust network management system that handles both IPv4 and IPv6 networks, provides comprehensive host management capabilities, and includes a malware spread pattern simulator for cybersecurity analysis.

The system is designed with object-oriented principles, ensuring type safety, extensibility, and maintainability.

## ✨ Features

### Network Management
- **Dual-Stack Support**: Complete IPv4 and IPv6 network implementation
- **Host Management**: Add, remove, and query hosts by IP address or hostname
- **Type Safety**: Enforced network type constraints (IPv4-only, IPv6-only, or mixed networks)
- **Duplicate Prevention**: Automatic validation to prevent duplicate IP addresses or hostnames

### Network Types
1. **IPv4Network**: Accepts only IPv4 hosts
2. **IPv6Network**: Accepts only IPv6 hosts
3. **MixedNetwork**: Supports both IPv4 and IPv6 hosts simultaneously

### Security Analysis
- **Malware Spread Simulator**: Models infection propagation patterns across network devices
- **Time-based Simulation**: Tracks malware spread over configurable time periods
- **Statistical Analysis**: Calculates infection rates and device counts

### Host Operations
- Query hosts by IP address (type-safe)
- Query hosts by hostname (case-insensitive)
- Remove hosts by IP, hostname, or host object
- Get total host count

## 📁 Project Structure

```
Forescout-challenge-2023/
├── README.md
├── .gitignore
├── FORESCOUNT CHALLENGE SOLUTIONS.docx
├── Forescout-challenge-2023.pdf
└── Networking/
    ├── pom.xml
    └── src/
        ├── main/
        │   └── java/
        │       └── com/
        │           └── forescout/
        │               └── challenge/
        │                   ├── Host.java                          # Host entity with IP and hostname
        │                   ├── Network.java                       # Base network class with core functionality
        │                   ├── IPv4Network.java                   # IPv4-only network implementation
        │                   ├── IPv6Network.java                   # IPv6-only network implementation
        │                   ├── MixedNetwork.java                  # Dual-stack network implementation
        │                   ├── MalwareSpreadPatternSimulator.java # Infection propagation simulator
        │                   └── Main.java                          # Demo application
        └── test/
            └── java/
                └── com/
                    └── forescout/
                        └── challenge/
                            └── NetworkTest.java                   # Comprehensive unit tests
```

## 🔧 Prerequisites

- **Java Development Kit (JDK)**: Version 8 or higher
- **Apache Maven**: Version 3.6 or higher
- **Git**: For version control

## 📥 Installation

### 1. Clone the Repository

```bash
git clone https://github.com/YOUR_USERNAME/forescout-network-challenge-2023.git
cd forescout-network-challenge-2023/Networking
```

### 2. Build the Project

```bash
mvn clean install
```

### 3. Run Tests

```bash
mvn test
```

## 🚀 Usage

### Running the Malware Spread Simulator

```bash
cd Networking
mvn exec:java -Dexec.mainClass="com.forescout.challenge.MalwareSpreadPatternSimulator"
```

### Basic Network Operations Example

```java
import com.forescout.challenge.*;
import java.net.InetAddress;

// Create a mixed network (supports both IPv4 and IPv6)
Network network = new MixedNetwork();

// Add IPv4 host
InetAddress ipv4Address = InetAddress.getByName("192.168.1.10");
Host host1 = new Host(ipv4Address, "webserver-01");
network.addHost(host1);

// Add IPv6 host
InetAddress ipv6Address = InetAddress.getByName("2001:db8::1");
Host host2 = new Host(ipv6Address, "webserver-02");
network.addHost(host2);

// Query by IP address
Host foundHost = network.getHostByIp(ipv4Address);
System.out.println("Found: " + foundHost.getHostName());

// Query by hostname (case-insensitive)
Host foundByName = network.getHostByName("WEBSERVER-01");
System.out.println("IP: " + foundByName.getAddress().getHostAddress());

// Get total host count
System.out.println("Total hosts: " + network.hostCount());

// Remove host
network.removeHostByName("webserver-01");
```

### IPv4-Only Network Example

```java
Network ipv4Network = new IPv4Network();

// This works
InetAddress ipv4 = InetAddress.getByName("10.0.0.1");
ipv4Network.addHost(new Host(ipv4, "server-ipv4"));

// This throws IllegalArgumentException
InetAddress ipv6 = InetAddress.getByName("2001:db8::1");
ipv4Network.addHost(new Host(ipv6, "server-ipv6")); // Exception!
```

### IPv6-Only Network Example

```java
Network ipv6Network = new IPv6Network();

// This works
InetAddress ipv6 = InetAddress.getByName("fe80::1");
ipv6Network.addHost(new Host(ipv6, "server-ipv6"));

// This throws IllegalArgumentException
InetAddress ipv4 = InetAddress.getByName("192.168.1.1");
ipv6Network.addHost(new Host(ipv4, "server-ipv4")); // Exception!
```

## 🏗️ Architecture

### Class Hierarchy

```
Network (Base Class)
├── IPv4Network (IPv4-only)
├── IPv6Network (IPv6-only)
└── MixedNetwork (Dual-stack)

Host (Entity Class)
└── Supports both Inet4Address and Inet6Address
```

### Design Patterns

- **Inheritance**: Network type specialization through class hierarchy
- **Encapsulation**: Private host collections with controlled access
- **Type Safety**: Leveraging Java's type system for IPv4/IPv6 validation
- **Defensive Programming**: Null checks and validation throughout

### Key Design Decisions

1. **Type-Safe IP Comparison**: Uses `getClass().equals()` to ensure IPv4 addresses don't match IPv6 addresses
2. **Case-Insensitive Hostname Lookup**: Uses `equalsIgnoreCase()` for flexible hostname queries
3. **Duplicate Prevention**: Validates both IP and hostname uniqueness before adding hosts
4. **Immutable Collections**: Returns defensive copies where appropriate

## 🧪 Testing

The project includes comprehensive unit tests covering:

- IPv4 host operations (add, remove, query)
- IPv6 host operations (add, remove, query)
- Mixed network functionality
- Duplicate prevention validation
- Type safety enforcement
- Edge cases and error conditions

### Run All Tests

```bash
mvn test
```

### Test Coverage

- **NetworkTest.java**: Parameterized tests for multiple IP addresses
- Tests for all CRUD operations
- Validation tests for illegal operations
- Mixed IP protocol handling tests

## 🛠️ Technologies

- **Language**: Java 8
- **Build Tool**: Apache Maven 3
- **Testing Framework**: JUnit 5 (Jupiter)
- **Java APIs**: 
  - `java.net.InetAddress` for IP address handling
  - `java.util.stream` for functional operations
  - `java.util.logging` for logging

## 📊 Malware Spread Simulation

The `MalwareSpreadPatternSimulator` models how malware spreads across network devices over time. It uses a timer-based approach where:

- Each infected device has a timer value
- When a timer reaches 0, the device infects new devices
- The simulation tracks infection rates over configurable time periods

### Example Output

```
Infected devices after 80 minutes: [calculated count]
Infected devices after 256 minutes: [calculated count]
```

## 🤝 Contributing

This project was developed as a coding challenge solution. If you'd like to suggest improvements:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/improvement`)
3. Commit your changes (`git commit -am 'Add improvement'`)
4. Push to the branch (`git push origin feature/improvement`)
5. Create a Pull Request


## 👤 Author
Munwar Verunukunnel Hassan

Developed as a solution for the Forescout Challenge 2023

## 🙏 Acknowledgments

- Java networking API documentation
- JUnit team for the excellent testing framework

---

**Note**: This project demonstrates practical application of object-oriented design principles, network programming concepts, and cybersecurity simulation techniques.

