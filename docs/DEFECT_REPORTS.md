# Defect Reports

These reports document reproducible behaviours exposed by SauceDemo's deliberately problematic test personas. They demonstrate defect-writing technique; they are not allegations about a production commerce system.

## SD-001 — Inventory images do not correspond to product names

| Field | Detail |
|---|---|
| Environment | SauceDemo public site; Chrome; `problem_user` persona |
| Severity / Priority | Medium / P1 |
| Reproducibility | Consistent with the designated problem persona |
| Preconditions | User is on the login page |

**Steps**

1. Log in as `problem_user` using the public demo password.
2. Review the image displayed beside each inventory item.
3. Compare each image with its associated product name.

**Expected:** Every inventory item displays the corresponding product image.

**Actual:** Multiple products display an unrelated dog image rather than the image matching the product name.

**User impact:** Customers may lose confidence in the catalogue and select the wrong item.

---

## SD-002 — Product layout has visible alignment inconsistencies

| Field | Detail |
|---|---|
| Environment | SauceDemo public site; Chrome; `visual_user` persona |
| Severity / Priority | Low / P2 |
| Reproducibility | Consistent with the designated visual persona |
| Preconditions | User is on the login page |

**Steps**

1. Log in as `visual_user` using the public demo password.
2. Review the inventory cards from top to bottom.
3. Compare image, description, price, and button alignment across products.

**Expected:** Inventory cards follow a consistent grid with aligned content and controls.

**Actual:** Product-card elements display inconsistent spacing/alignment, reducing visual consistency.

**User impact:** The catalogue appears less polished and becomes harder to scan quickly.

## Triage notes

- Attach a screenshot and DOM evidence when recording these in a defect tracker.
- Confirm on a second browser before assigning final severity.
- Keep functional and visual problems separate so they can be routed and retested independently.
