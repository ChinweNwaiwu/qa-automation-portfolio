# QA Automation Portfolio

A portfolio-grade Java test framework demonstrating browser automation, REST API testing, Postman collections, and SQL validation. The project uses public demonstration services and contains no private credentials or production data.

**Author:** Scholar Nwaiwu  
**Focus:** Quality assurance engineering, risk-based testing, automation design, and clear test evidence

## What this project demonstrates

| Layer | Tools | Coverage |
|---|---|---|
| Web UI | Java 17, Selenium WebDriver, JUnit 5, AssertJ | Authentication, cart behaviour, numeric sorting, page objects, explicit waits, failure screenshots |
| REST API | REST Assured, JUnit 5, Postman/Newman | Positive, creation, negative, response contract, and status-code checks |
| Database | JDBC, H2, SQL | Joins, aggregates, business-rule filtering, orphan detection, and foreign-key integrity |
| Delivery | Maven Wrapper, GitHub Actions | Tagged suites, headless execution, matrix CI, and downloadable test evidence |

## Systems under test

- [SauceDemo](https://www.saucedemo.com/) — browser UI test application
- [JSONPlaceholder](https://jsonplaceholder.typicode.com/) — REST API test service
- Embedded H2 database — deterministic SQL validation

These public systems are not owned by this repository. Their availability and behaviour can change independently.

## Automated coverage

The Java suite contains **nine focused tests**:

- Three Selenium UI scenarios
- Three REST API scenarios
- Three database scenarios

The Postman collection provides three additional executable API requests with assertions.

See the supporting quality artefacts:

- [Concise test plan](docs/TEST_PLAN.md)
- [Test case catalogue](docs/TEST_CASES.md)
- [Defect reports](docs/DEFECT_REPORTS.md)

## Project structure

```text
qa-automation-portfolio/
├── .github/workflows/qa-tests.yml
├── docs/
│   ├── DEFECT_REPORTS.md
│   ├── TEST_CASES.md
│   └── TEST_PLAN.md
├── postman/
│   ├── JSONPlaceholder-QA-Portfolio.postman_collection.json
│   └── QA-Portfolio.postman_environment.json
├── src/test/java/dev/scholarqa/
│   ├── api/
│   ├── config/
│   ├── database/
│   └── ui/
├── src/test/resources/
│   ├── sql/
│   └── config.properties
├── pom.xml
└── README.md
```

## Prerequisites

- Java 17 or later
- Chrome or Chromium
- Git

Maven does not need to be installed globally; the repository includes the Maven Wrapper.

## Run the tests

Run all Java checks in headless mode:

```bash
./mvnw -Dheadless=true test
```

Run one test layer:

```bash
./mvnw -Dgroups=ui -Dheadless=true test
./mvnw -Dgroups=api test
./mvnw -Dgroups=database test
```

Run Chrome visibly while developing locally:

```bash
./mvnw -Dgroups=ui -Dheadless=false test
```

On Windows PowerShell, replace `./mvnw` with `./mvnw.cmd`.

## Run the Postman collection

Import both JSON files from `postman/` into Postman, select the **QA Portfolio - Public Demo** environment, and run the collection.

With Node.js installed, run it from the command line:

```bash
npx newman run postman/JSONPlaceholder-QA-Portfolio.postman_collection.json \
  --environment postman/QA-Portfolio.postman_environment.json
```

## Configuration

Defaults live in `src/test/resources/config.properties`. Values can be overridden without changing source code:

| Environment variable | Java system property | Purpose |
|---|---|---|
| `UI_BASE_URL` | `ui.base.url` | UI host |
| `API_BASE_URL` | `api.base.url` | API host |
| `UI_USERNAME` | `ui.username` | Public demo user |
| `UI_PASSWORD` | `ui.password` | Public demo password |
| `HEADLESS` | `headless` | Headless Chrome toggle |
| `TIMEOUT_SECONDS` | `timeout.seconds` | Explicit-wait timeout |

Example:

```bash
./mvnw -Dui.base.url=https://www.saucedemo.com -Dheadless=true test
```

## Design decisions

- Page objects separate locators and user actions from assertions.
- Explicit waits replace fixed sleeps.
- Tests are independent and create a fresh browser or database state.
- Tags keep UI, API, and database feedback independently runnable.
- REST request/response details are logged only when validation fails.
- UI failures capture screenshots without masking the original exception.
- Database checks use prepared statements and deterministic seed data.
- Retries are disabled so intermittent behaviour remains visible.

## CI evidence

GitHub Actions runs UI, API, database, and Postman suites separately. Surefire reports, screenshots, and Newman JUnit output are uploaded even when a job fails, making investigation evidence available from the workflow run.

## Responsible use

The automation intentionally limits traffic to a small number of functional checks against services designed for testing. Do not point this suite at production systems without authorization, suitable test data, and an agreed execution plan.

## License

This project is available under the [MIT License](LICENSE).
