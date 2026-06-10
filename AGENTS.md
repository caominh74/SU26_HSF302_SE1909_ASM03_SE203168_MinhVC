Overview
--------
This document gives concise, actionable guidance for AI coding agents working in this Spring Boot parking-management project so you can be productive immediately.

Quick checklist
---------------
- Read the main application entry: `src/main/java/org/minhvc/springwebmvc/parkingmanagement/Su26Hsf302Se1909Asm03Se203168MinhVcApplication.java`
- Inspect servlet initializer: `ServletInitializer.java` (WAR-compatible SpringBootServletInitializer)
- Review JPA entities in `src/main/java/.../entities/` to understand the domain model (ParkingSession, ParkingSlot, User, Reservation, Zone, PricingPolicy)
- Review Spring Data repos in `src/main/java/.../repositories/` and service interfaces in `src/main/java/.../services/`
- Use the Maven wrapper on Windows: `mvnw.cmd` (project root) for build/run/test tasks

Big-picture architecture (what to know)
--------------------------------------
- Single-module Spring Boot application using annotation-based configuration. Entry point: `Su26Hsf302Se1909Asm03Se203168MinhVcApplication.java`.
- Web layer is Spring Web MVC serving server-side templates (there is a `src/main/resources/templates` folder). No explicit controllers were found in the scanned files—expect controllers to be added under the same package when present.
- Persistence layer uses Spring Data JPA (`IParkingSessionRepository`, `IParkingSlotRepository`) and standard JPA entities under `entities/`. Repositories extend `JpaRepository<..., Integer>`.
- Service layer exposes business methods via interfaces in `services/` (example: `IParkingSessionService` declares findAll/findById/findBySessionId patterns).
- Entities use JPA annotations and Hibernate-specific annotations such as `@ColumnDefault` and `@Nationalized` — expect Hibernate dialect behavior and DB defaults when running against a relational DB.

Project-specific conventions and examples
---------------------------------------
- Interface prefixes: repository and service interfaces are prefixed with "I" (e.g., `IParkingSessionRepository`, `IParkingSessionService`). Implementations — if present — will follow that interface naming.
- Domain mapping patterns: Many-to-one relationships are used broadly. Example: `ParkingSession` has many-to-one relations to `Vehicle`, `ParkingSlot`, and `User` — treat IDs as Integer primary keys.
- Default values are declared in entities with `@ColumnDefault` (search `@ColumnDefault` in `entities/`). Do not assume Java-side defaults only — DB defaults may be expected.
- Use of Lombok-like getters/setters is present (confirm actual Lombok dependency before generating/compiling). Check entity annotations for `@Getter`/`@Setter`.

Build / Run / Test workflows (concrete commands)
-----------------------------------------------
- On Windows (PowerShell), use the Maven wrapper shipped with project root:

    D:\University\HSF302\SU26_HSF302_SE1909_ASM03_SE203168_MinhVC> .\mvnw.cmd clean package

- Run the app with the wrapper:

    .\mvnw.cmd spring-boot:run

- Run tests:

    .\mvnw.cmd test

- Create a runnable jar (skip tests when iterating):

    .\mvnw.cmd -DskipTests package

- Remote debug (example JVM arg using wrapper):

    .\mvnw.cmd spring-boot:run -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"

Notes on configuration and integration points
-------------------------------------------
- `src/main/resources/application.properties` currently only contains `spring.application.name=SU26_HSF302_SE1909_ASM03_SE203168_MinhVc` — no datasource configured in the repo. Agents should:
  - Look for environment variables or external config/profiles when running the app (check CI or developer docs).
  - Expect to provide a JDBC URL, username, and password or use an in-memory DB when running locally for experiments.
- Persistence: JPA + Hibernate. Repositories are Spring Data JPA interfaces — common pattern: create query methods on the interface or use JPQL/native queries if needed.
- Templates: `src/main/resources/templates` exists — server-side views (likely Thymeleaf); when adding controllers, return view names not JSON unless REST controllers are explicitly used.

Where to make common changes
----------------------------
- Add controllers: `src/main/java/org/minhvc/springwebmvc/parkingmanagement/controller/` (create this package if missing)
- Add service implementations: `src/main/java/org/minhvc/springwebmvc/parkingmanagement/services/impl/` and register via `@Service`
- Add custom repository queries by adding methods to interfaces in `repositories/` or by creating custom repository implementations.

Searchable anchors (quick grep targets)
-------------------------------------
- "@SpringBootApplication" -> main entry
- "@Entity" -> domain model
- "@Repository" / "extends JpaRepository" -> data access
- "@Service" or interface names starting with `I` in `services/`
- "@ColumnDefault" / "@Nationalized" -> entity DB-related behavior

Troubleshooting tips for agents
-------------------------------
- If build fails due to Lombok, ensure IDE/compiler has Lombok enabled or remove Lombok usage by generating getters/setters.
- If the app cannot connect to a DB, provide a profile with an in-memory H2 datasource or set standard Spring properties (spring.datasource.url/username/password).
- If templates fail, check for expected view resolver (Thymeleaf is common) and matching template names in `templates/`.

Files of interest (explicit)
---------------------------
- `src/main/java/org/minhvc/springwebmvc/parkingmanagement/Su26Hsf302Se1909Asm03Se203168MinhVcApplication.java`
- `src/main/java/org/minhvc/springwebmvc/parkingmanagement/ServletInitializer.java`
- `src/main/java/org/minhvc/springwebmvc/parkingmanagement/entities/` (all entity files)
- `src/main/java/org/minhvc/springwebmvc/parkingmanagement/repositories/` (all repo interfaces)
- `src/main/java/org/minhvc/springwebmvc/parkingmanagement/services/IParkingSessionService.java`
- `src/main/resources/application.properties`

End of file

