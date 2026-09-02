# Test Case Catalogue

| ID | Layer | Scenario | Expected result | Automated |
|---|---|---|---|---|
| UI-001 | UI | Log in with the standard public-demo account | Products page is displayed | Yes |
| UI-002 | UI | Add Sauce Labs Backpack to cart | Cart contains exactly the selected product | Yes |
| UI-003 | UI | Log in with the locked-out account | Clear locked-out error appears | Yes |
| UI-004 | UI | Sort inventory by price, low to high | Displayed numeric prices are ascending | Yes |
| API-001 | API | Get user 1 | HTTP 200 and required contract fields returned | Yes |
| API-002 | API | Create a post | HTTP 201 and submitted values echoed with ID | Yes |
| API-003 | API | Get an unknown user | HTTP 404 and empty JSON object returned | Yes |
| DB-001 | SQL | Calculate Alice's non-cancelled order value | Two orders total 199.98 | Yes |
| DB-002 | SQL | Search for orders without customers | Orphan count equals zero | Yes |
| DB-003 | SQL | Insert order for unknown customer | Foreign-key constraint rejects the insert | Yes |

Detailed automated steps are implemented as readable JUnit tests. This catalogue stays intentionally concise and links requirements to executable evidence.
