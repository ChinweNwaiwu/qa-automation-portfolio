# Concise Test Plan

## 1. Objective

Demonstrate a risk-based quality approach across the browser UI, REST API, and relational-data layers of public demonstration systems. The suite is designed as portfolio evidence and must not be interpreted as production certification for SauceDemo or JSONPlaceholder.

## 2. Systems under test

- **UI:** SauceDemo (`https://www.saucedemo.com`)
- **API:** JSONPlaceholder (`https://jsonplaceholder.typicode.com`)
- **Database:** Embedded H2 schema supplied with this repository

## 3. In scope

- Successful and unsuccessful authentication
- Shopping-cart addition and product-price sorting
- API status codes, required response fields, creation response, and not-found behaviour
- SQL joins, aggregates, referential integrity, and business-rule filtering
- Chrome headless execution on Java 17
- Postman/Newman execution for a portable API demonstration

## 4. Out of scope

- Payment processing and real financial transactions
- Load, penetration, localization, and mobile-native testing
- Full cross-browser coverage
- Production data or private credentials
- Exhaustive coverage of either public demonstration service

## 5. Risks and priorities

| Risk | Impact | Priority | Coverage |
|---|---:|---:|---|
| Authentication failure blocks all shopping activity | High | P0 | Positive and locked-user UI tests |
| Cart state does not reflect the selected product | High | P0 | Add-to-cart assertion |
| Incorrect sorting misleads purchasing decisions | Medium | P1 | Numeric ascending-price assertion |
| API contract changes break consumers | High | P0 | Status, type, and required-field checks |
| Invalid relationships corrupt reporting | High | P0 | Foreign-key and orphan-record checks |
| Cancelled orders inflate customer value | Medium | P1 | SQL aggregate validation |

## 6. Entry and exit criteria

**Entry:** Public endpoints are reachable; Java 17 and Maven are available; Chrome can run locally or in CI.

**Exit:** All P0 and P1 automated checks pass; failures have request/response logs or UI screenshots; known limitations are documented.

## 7. Evidence

- Maven Surefire XML and text reports in `target/surefire-reports`
- Screenshots for failed browser tests in `screenshots`
- Newman CLI output in CI
- GitHub Actions run history

## 8. Maintenance approach

Page objects isolate UI locators, configuration supports environment overrides, and test tags allow UI, API, and database suites to run independently. Public demo instability is investigated before any retry is considered; retries are intentionally disabled so intermittent behaviour remains visible.
