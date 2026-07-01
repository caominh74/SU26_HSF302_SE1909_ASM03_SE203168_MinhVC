# AGENTS.md

## Project Context

- This is a Java Spring Boot university assignment using Spring MVC and Thymeleaf. It is not a REST application.
- Two projects share the same database:
  - Main project: `D:\University\SU26_HSF302_SE1909_ASM03_SE203168_MinhVC`
  - Friend's reference project: `D:\University\TuNLA`
- A second version of the friend's project contains the login feature:
  - Login reference project: `D:\University\TuNLA_Login_Feature`
- The assigned feature is Parking Session management:
  - Main table/entity: `ParkingSessions`
  - Sub-table/entity: `ParkingSlots`
- Base package: `org.minhvc.springwebmvc.parkingmanagement`
- Use only the existing sub-packages, including:
  - `controllers`
  - `entities`
  - `repositories`
  - `services`

## Required Working Rules

1. Do not create additional Java packages. Put changes only in the existing package structure.
2. Treat `D:\University\TuNLA` as the structural reference. When the main project's structure, implementation, naming, or style is unclear or inconsistent, inspect the corresponding implementation in the reference project before writing code.
   - For login, logout, authentication, authorization, user-session, or role-based access work, use `D:\University\TuNLA_Login_Feature` as the primary reference and guideline.
   - Continue applying all rules in this file when following the login reference. Do not copy unrelated features, packages, routes, or frontend work.
3. Do not assume the main project's existing code is correct. Verify relevant behavior and patterns against the reference project before fixing or extending them.
4. Work only on `ParkingSessions` and its `ParkingSlots` sub-feature. Do not add unrelated entities, controllers, services, repositories, or routes. In particular, do not implement `Reservations` logic because it belongs to the friend's feature.
5. Follow the established controller style:
   - Use `@Controller`, never `@RestController`.
   - Return `ModelAndView`.
   - Use explicit `@RequestMapping` declarations with a specified HTTP method.
   - After create, update, or delete, redirect to `/EntityName/index`.
6. Do not build or modify frontend views unless the user explicitly requests frontend work. Prioritize backend correctness.
7. Preserve unrelated user changes in the working tree. Do not overwrite or revert them.
8. Before completing backend changes, compile and run the relevant tests when practical.

## Architecture

- Controllers are under `src/main/java/org/minhvc/springwebmvc/parkingmanagement/controllers/`.
- JPA entities are under `src/main/java/org/minhvc/springwebmvc/parkingmanagement/entities/`.
- Spring Data repositories are under `src/main/java/org/minhvc/springwebmvc/parkingmanagement/repositories/`.
- Service interfaces and implementations are under `src/main/java/org/minhvc/springwebmvc/parkingmanagement/services/`.
- Thymeleaf templates are under `src/main/resources/templates/` but must not be changed unless requested.
- Repository and service interfaces use the `I` prefix, such as `IParkingSessionsRepository` and `IParkingSessionsService`.
- Primary keys use `Integer`.
- The application uses SQL Server and Spring Data JPA/Hibernate.

## Build and Verification

Run commands from:

```text
D:\University\SU26_HSF302_SE1909_ASM03_SE203168_MinhVC
```

Use the included Maven wrapper:

```powershell
.\mvnw.cmd test
.\mvnw.cmd clean package
.\mvnw.cmd spring-boot:run
```

Before modifying database mappings, compare the JPA entity with `DB_Script.sql` and remember that both projects use the same database.

## Current Phase

The project is in a review-and-adjustment phase. Inspect what has already been built, compare uncertain patterns with the reference project, and wait for the user's specific task before expanding functionality.
