## Why

The Receivables Management System requires a modern, responsive Android frontend UI that allows Brazilian small businesses to manage clients, track receivables, record payments, view financial statistics, scan payment codes, and view payment receipts. Introducing the Kotlin Jetpack Compose frontend application fulfills the UI/UX specification while keeping a clean separation from backend APIs and business rules.

## What Changes

- Add Kotlin plugin and Jetpack Compose dependencies to the Android project build configuration.
- Add application layer architecture structure (`presentation`, `domain`, `data`, `core`) with modern Unidirectional Data Flow (UDF).
- Implement Unauthenticated Stack: `LoginScreen`, `CreateAccountScreen`, `RecoveryPasswordScreen`.
- Implement Authenticated Stack: `HomeScreen` (with dashboard KPIs & receivables summary), `ScanScreen` (QR/barcode camera interface & manual code entry), `StatisticsScreen` (charts and monthly metrics), `SendMoneyScreen` (contact/recipient selection and transfer shell), `ReceiptScreen` (transaction success/failure view), and `ReceivableDetail` screen.
- Implement design system tokens (Material 3 theme, colors, typography, spacing, shapes, accessibility) and shared components.
- Implement state management, ViewModel patterns, data mapping, and repository interfaces matching backend contracts.

## Capabilities

### New Capabilities
- `e-wallet-ui`: Android frontend UI components, screen flows, Jetpack Compose design tokens, navigation, and state presentation layer for the E-Wallet / Receivables Management application.

### Modified Capabilities

## Impact

- Builds on top of `:app` module in `com.example.kotlin`.
- Updates `gradle/libs.versions.toml`, `build.gradle.kts`, and `app/build.gradle.kts` to enable Kotlin and Jetpack Compose dependencies.
- Establishes core Android presentation, domain model, repository interfaces, and network/security mapping layers.
