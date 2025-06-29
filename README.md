# 🏁 FlagForge – Feature Flag Management System

**FlagForge** is a scalable, rule-based feature flag management system designed with enterprise-grade architecture patterns, production-ready feature sets, and developer-first ergonomics.

---

## 🚧 Why FlagForge?

- Toggle features dynamically without redeployments.
- Target feature rollouts based on tenant ID or user ID.

---

## 🏗️ Tech Stack

- **Language:** Java 21
- **Framework:** Spring Boot 3.x
- **Database:** PostgreSQL
- **Patterns:** Domain-Driven Design (DDD), Repository Pattern
- **Build Tool:** Gradle

---

## 💡 Key Features

### ✅ Feature Management

- Create, update, delete flags
- Enable/disable flags globally or per tenant/user
- Define targeting rules (e.g., userId = 123, tenantId = "acme-corp")

### 🧠 Rule Engine

- Dynamic rule evaluation using pluggable strategy pattern
- Supports user-based and tenant-based targeting

### ⚙️ Production-Ready Design

- DB indexing for high-performance flag lookups
- HTTP status consistency and error handling
