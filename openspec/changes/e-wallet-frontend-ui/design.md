## Context

The current repository is an Android application shell (`:app` module, `com.example.kotlin`) with Gradle build configuration and version catalog (`libs.versions.toml`). There is no source code yet in `app/src/main/java/com/example/kotlin/` and no launcher Activity declared.

To fulfill the E-Wallet / Receivables Management UI specification, we need to:
1. Apply the Kotlin Android plugin (`org.jetbrains.kotlin.android`) and Jetpack Compose dependencies in the build configuration.
2. Build a modern layered architecture (`presentation`, `domain`, `data`, `core`) with Unidirectional Data Flow (UDF).
3. Connect the frontend navigation graph, theme/design tokens, reusable UI component library, and state management ViewModels.

## Goals / Non-Goals

**Goals:**
- Configure Kotlin and Jetpack Compose toolchain in `libs.versions.toml` and Gradle scripts.
- Implement presentation layer for unauthenticated stack (`Login`, `CreateAccount`, `RecoveryPassword`) and authenticated stack (`Home`, `Scan`, `Statistics`, `SendMoney`, `Receipt`, `ReceivableDetail`, `RegisterPayment`).
- Implement Material 3 custom E-Wallet design system (colors, typography, spacing, shapes, dark mode, status chips).
- Implement clean presentation-domain-data boundary using immutable UI state and sealed UI events in ViewModels.
- Implement domain models (`Receivable`, `Payment`, `Client`, `Money`, `ReceivableStatus`) and repository interfaces matching backend contracts.
- Ensure 100% adherence to AGP 9.3.2 / Gradle 9.5.0 Kotlin DSL requirements.

**Non-Goals:**
- Creating real backend HTTP services or live AWS S3 file upload endpoints (mock/stub repositories will be used for testing UI state flow until backend endpoints are integrated).
- Creating direct outbound money transfer APIs for `SendMoney` beyond the interaction shell specified in the document.

## Decisions

### Decision 1: Kotlin & Compose Stack Integration in Gradle
- **Choice**: Add `org.jetbrains.kotlin.android` (2.0.21 or compatible), `androidx-activity-compose`, `androidx-compose-bom`, `androidx-navigation-compose`, and Material 3 dependencies to `gradle/libs.versions.toml` and apply them in `:app/build.gradle.kts`.
- **Rationale**: Jetpack Compose is the standard modern Android UI framework specified by the product UI specification.
- **Alternatives Considered**: XML View system (rejected: specification explicitly mandates Jetpack Compose and Material 3).

### Decision 2: Layered Architecture with Presentation / Domain / Data Separation
- **Choice**: Package structure:
  - `presentation/<feature>/`: Composables, ViewModels, UI State, UI Events.
  - `domain/model/`, `domain/repository/`, `domain/usecase/`: Pure Kotlin business models, repository interfaces, and use cases.
  - `data/remote/`, `data/repository/`, `data/mapper/`: DTOs, repository implementations, and mapping logic.
  - `core/ui/`, `core/navigation/`, `core/formatting/`, `core/security/`: Theme, reusable components, Money/Date formatters, navigation routes.
- **Rationale**: Isolates UI state from network/database representation, preventing DTO leaks into Compose and facilitating unit testing.

### Decision 3: Single Activity Architecture with Navigation Compose
- **Choice**: `MainActivity` serves as the sole entry point hosting `NavHost` with sealed `AppRoute` interface routes.
- **Rationale**: Modern Android recommendation; simplifies back stack management and authentication state transitions.

### Decision 4: Immutable UI State & StateFlow ViewModels
- **Choice**: Expose immutable `StateFlow<ScreenUiState>` from ViewModels and accept user actions via sealed `ScreenEvent` interfaces.
- **Rationale**: Ensures deterministic UI rendering, supports previewing, and simplifies state verification in unit tests.

### Decision 5: Formatting and Domain Data Types
- **Choice**: Represent monetary values using a custom `Money` class wrapping `BigDecimal` and `Currency.BRL`. Provide a `MoneyFormatter` utility for Brazilian `R$ X.XXX,XX` format. Represent dates using standard Java/Kotlin time types formatted as `dd/MM/yyyy`.
- **Rationale**: Floating point calculations cause precision errors in financial calculations; presentation layer formatting ensures clean UI separation.

## Risks / Trade-offs

- **[Risk]** Kotlin plugin missing in project configuration.
  → **Mitigation**: Update `gradle/libs.versions.toml` with `org.jetbrains.kotlin.android` and apply plugin in `build.gradle.kts` and `app/build.gradle.kts` before adding Kotlin source files.
- **[Risk]** Camera API permissions in ScanScreen on emulator/device without camera.
  → **Mitigation**: Provide clear UI permission states and manual code entry fallback.
- **[Risk]** Backend API contracts for SendMoney and QR code scanner not yet finalized.
  → **Mitigation**: Treat SendMoney as an interaction shell and design the scanner adapter with a generic payload contract ready for backend integration.

## Migration Plan

1. Update `gradle/libs.versions.toml` and root `build.gradle.kts` / `app/build.gradle.kts` to support Kotlin & Compose.
2. Create package structure under `app/src/main/java/com/example/kotlin/`.
3. Build `core/ui` theme and component library.
4. Build `domain` models, repository interfaces, and use cases.
5. Build `presentation` screens, ViewModels, navigation graph, and `MainActivity`.
6. Verify build using `.\gradlew.bat assembleDebug` and unit tests with `.\gradlew.bat testDebugUnitTest`.
