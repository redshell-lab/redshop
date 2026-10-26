# RedShop

**An intentionally vulnerable Java web application for learning Application Security and DevSecOps.**

RedShop is an educational web application built with Java and Spring Boot. It provides a practical environment for exploring web application architecture, identifying security vulnerabilities, implementing security controls, and integrating automated security testing into a CI/CD pipeline.

The project is designed to connect software development with application security. Instead of learning security concepts only through theory, you can examine application code, reproduce vulnerabilities in a controlled lab, apply fixes, and validate the results using security testing tools.

> **Warning:** RedShop is intended for educational and authorized security testing purposes only. It may contain intentionally vulnerable code and insecure configurations. Do not expose it to the public internet, deploy it in production, or use real credentials or sensitive data.

## Table of Contents

- [Project Goals](#project-goals)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Security Learning Scenarios](#security-learning-scenarios)
- [DevSecOps and CI/CD](#devsecops-and-cicd)
- [Getting Started](#getting-started)
- [Working with the Project](#working-with-the-project)
- [Security Notice](#security-notice)
- [Contributing](#contributing)
- [Author](#author)
- [License](#license)

## Project Goals

RedShop aims to provide a hands-on environment for learning how application security fits into the software development lifecycle.

The main goals are to:

- Understand the architecture of a Java and Spring Boot web application.
- Learn how HTTP requests are processed across application layers.
- Identify common web application vulnerabilities through source code review and dynamic testing.
- Compare vulnerable implementations with secure alternatives.
- Integrate security testing into CI/CD pipelines.
- Generate and review security reports for application code, dependencies, secrets, containers, and running applications.
- Understand how security findings can support remediation and continuous improvement.

## Features

### Application Development

RedShop serves as a practical example of a Java web application, covering concepts such as:

- Java classes, objects, interfaces, and dependency injection.
- Spring MVC controllers and request handling.
- Service and repository layers.
- JPA and Hibernate for persistence.
- Thymeleaf templates and server-side rendering.
- REST APIs and JSON responses.
- Spring Security, authentication, and authorization.
- Unit testing with JUnit and Mockito.
- Application logging, security events, and audit logging.

### Application Security

The project can be used to explore application security concepts through code analysis, controlled vulnerability reproduction, and remediation exercises.

### DevSecOps

RedShop is also used as a practical target for demonstrating automated security checks in a GitLab CI/CD pipeline.

## Technology Stack

| Area | Technologies |
|---|---|
| Programming language | Java |
| Application framework | Spring Boot |
| Web layer | Spring MVC, Thymeleaf |
| Persistence | Spring Data JPA, Hibernate |
| Authentication and authorization | Spring Security |
| Build and dependency management | Maven |
| Containerization | Docker |
| CI/CD | GitLab CI/CD |
| Static application security testing | OpenGrep |
| Software composition analysis and SBOM | Syft, Grype |
| Secret detection | Gitleaks |
| Container security | Trivy |
| Dynamic application security testing | OWASP ZAP |

The tools used in the pipeline may vary as the lab evolves. Refer to the repository's configuration files for the currently implemented stages and tool versions.

## Security Learning Scenarios

RedShop can be used to study common vulnerability classes, including:

| Vulnerability | Learning objective |
|---|---|
| Cross-Site Scripting (XSS) | Understand unsafe rendering of user-controlled content and context-aware output encoding. |
| Insecure Direct Object Reference (IDOR) | Learn why object ownership and authorization must be enforced on the server. |
| Path Traversal | Understand unsafe file path handling and how to constrain file access. |
| Command Injection | Examine the risks of passing untrusted input to operating system commands. |
| Broken Access Control | Learn how to enforce permissions and protect sensitive operations. |
| Mass Assignment | Understand why request data should be restricted to explicitly permitted fields. |

These scenarios are intended for controlled learning exercises. The availability and implementation details of individual scenarios depend on the current code in the repository.

For each exercise, the recommended workflow is:

1. **Reproduce:** Observe the vulnerable behavior in an isolated lab.
2. **Analyze:** Trace the input from the HTTP request to the vulnerable code path.
3. **Remediate:** Implement an appropriate security control.
4. **Retest:** Repeat the original test and verify that the root cause has been addressed.

The objective is not merely to make a particular payload stop working, but to understand and fix the underlying security weakness.

## DevSecOps and CI/CD

RedShop can be used as the application target in a GitLab CI/CD security pipeline.

The pipeline demonstrates how security checks can be incorporated into the development workflow rather than being performed only at the end of a project.

Depending on the configured pipeline, security activities may include:

- **SAST:** Inspect source code for potentially insecure coding patterns with OpenGrep.
- **Software Composition Analysis (SCA):** Generate a Software Bill of Materials (SBOM) with Syft and scan dependencies with Grype.
- **Secret Detection:** Identify potentially exposed credentials and secrets with Gitleaks.
- **Container Security:** Scan container images and related artifacts with Trivy.
- **DAST:** Test a running application with OWASP ZAP.
- **Artifact Management:** Retain generated reports for review and further analysis.

A typical learning workflow is:

```text
Source Code
    |
    v
Build and Test
    |
    v
Static Analysis (SAST)
    |
    v
Dependency Analysis and SBOM
    |
    v
Secret and Container Scanning
    |
    v
Deploy to an Isolated Lab
    |
    v
Dynamic Application Security Testing
    |
    v
Review Findings and Remediate
```

This diagram illustrates the learning workflow; it does not guarantee that every stage is enabled or runs in this exact order in the current pipeline.

**Important:** A successful pipeline does not necessarily mean that the application is secure. Security findings must be reviewed in context, and the coverage and limitations of each tool must be considered.

## Getting Started

### Prerequisites

Before running RedShop, prepare an isolated development environment with the components required by the repository configuration.

Common prerequisites include:

- Git
- A compatible JDK (Java 21 is used in the development environment)
- Maven, or the project's Maven Wrapper if provided
- Docker, if you intend to run the application or supporting services in containers

Additional requirements, such as database configuration and environment variables, depend on the current application setup.

### Clone the Repository

```bash
git clone https://github.com/redshell-lab/redshop.git
cd redshop
```

### Review the Project Configuration

Before starting the application, inspect the repository for its Maven build configuration, application settings, environment variables, and database requirements.

Do not commit local credentials, API tokens, private keys, or other secrets to version control.

### Build and Test

If the repository's Maven configuration supports a local build, you can use:

```bash
./mvnw test
./mvnw package
```

On Windows, use `mvnw.cmd` if the Maven Wrapper is present. Otherwise, use an installed Maven version.

The exact startup and database initialization steps should follow the configuration provided in the repository. No default credentials or production-ready configuration are assumed by this README.

## Working with the Project

RedShop is intended to evolve alongside its educational material.

When working on an exercise, consider the following practices:

- Keep experiments in a dedicated local or isolated lab environment.
- Use synthetic accounts and test data.
- Document the vulnerable behavior and its root cause.
- Implement the remediation as a distinct, understandable change.
- Add tests that verify both the expected functionality and the relevant security control.
- Review security scanner findings instead of relying solely on pipeline pass/fail status.
- Keep dependencies and security tooling appropriately maintained.

## Security Notice

RedShop is an educational security lab, not a production-ready e-commerce application.

If you run the application locally or in a virtualized environment:

- Do not expose vulnerable endpoints to untrusted networks.
- Do not use real customer information, passwords, or production secrets.
- Restrict network access to the lab.
- Run the application and any diagnostic processes with least privilege.
- Use disposable or synthetic data wherever possible.
- Review and remove intentionally vulnerable code before adapting the project for any real application.

All testing must be performed against systems you own or are explicitly authorized to assess.

## Contributing

Contributions that improve the educational value of RedShop are welcome.

Potential areas of contribution include:

- Additional security exercises.
- Secure and insecure code comparisons.
- Unit and security regression tests.
- Improvements to CI/CD security checks.
- Documentation and troubleshooting guides.
- Reproducible demonstrations of vulnerability detection and remediation.

When proposing a security exercise, please explain its learning objective, affected code path, reproduction conditions, expected result, and recommended remediation.

## Author

**Ehsan Ebadi**

RedShell — Application Security and DevSecOps learning resources.

- Website: [https://redshell.ir](https://redshell.ir)
- GitHub: [https://github.com/redshell-lab](https://github.com/redshell-lab)

## License

No license terms are specified in this README. Check the repository for an existing `LICENSE` file before using, modifying, or redistributing the project. If no license has been added, consider selecting an appropriate license and committing it to the repository.
