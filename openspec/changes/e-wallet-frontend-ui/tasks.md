## 1. Build Setup and Kotlin/Compose Toolchain

- [x] 1.1 Add Kotlin Android plugin (`org.jetbrains.kotlin.android`) and Compose dependencies to `gradle/libs.versions.toml`, `build.gradle.kts`, and `app/build.gradle.kts`, then verify build using `.\gradlew.bat assembleDebug`
- [x] 1.2 Create application package structure (`presentation`, `domain`, `data`, `core`) under `app/src/main/java/com/example/kotlin/` and verify package directories are created

## 2. Core Design System, Themes, and Utilities

- [x] 2.1 Implement `core/ui/theme` (EWalletTheme, ColorScheme, EWalletTypography, EWalletShapes) with purple visual identity, semantic tokens, and dark mode support, verifying theme renders without compile errors
- [x] 2.2 Implement `core/formatting` utilities (`Money`, `MoneyFormatter`, `DateFormatter`) with Brazilian locale support and verify unit tests pass with `.\gradlew.bat testDebugUnitTest`
- [x] 2.3 Implement reusable component library (`EWalletScaffold`, `EWalletTopBar`, `EWalletBottomBar`, `FinancialBalanceCard`, `KpiCard`, `ReceivableCard`, `ReceivableStatusChip`, `PrimaryButton`, `SecondaryButton`, `AmountInput`, `MoneyText`, `SearchField`, `PaymentMethodSelector`, `EmptyState`, `ErrorState`, `LoadingSkeleton`, `ConfirmationBottomSheet`, `ReceiptSuccessCard`, `TransactionTimeline`, `DocumentUploadCard`) and verify preview/compilation

## 3. Domain and Data Layer Implementation

- [x] 3.1 Implement domain models (`Receivable`, `Payment`, `Client`, `ReceivableStatus`, `PaymentMethod`) and repository interfaces (`ReceivableRepository`, `PaymentRepository`, `ClientRepository`) in `domain/`
- [x] 3.2 Implement mock data repositories and mappers in `data/` to support UI state flow and verify unit tests cover model/DTO mappers

## 4. Navigation Architecture and Unauthenticated Screen Flow

- [x] 4.1 Implement `core/navigation` typed routes (`AppRoute`), authentication state navigation guards, and `MainActivity` setup
- [x] 4.2 Implement `LoginScreen`, `LoginViewModel`, and `LoginUiState` with email/password validation and error states, verifying ViewModel state transitions with unit tests
- [x] 4.3 Implement `CreateAccountScreen` and `CreateAccountViewModel` with progressive field validation and verify UI renders properly
- [x] 4.4 Implement `RecoveryPasswordScreen` and `RecoveryPasswordViewModel` with password recovery flow and verify state handling

## 5. Authenticated Screen Flow

- [x] 5.1 Implement `HomeScreen`, `HomeViewModel`, and `HomeUiState` displaying hero balance card, KPI cards (Total, Pending, Received, Overdue, Collection Rate), quick actions, attention-required receivables, and pull-to-refresh
- [x] 5.2 Implement `ScanScreen` and `ScanViewModel` with camera permission handling, QR/barcode scanning interface, manual code entry, and validation flow
- [x] 5.3 Implement `StatisticsScreen` and `StatisticsViewModel` displaying monthly receipts, payment method breakdown, delinquency evolution, top clients, and date filters
- [x] 5.4 Implement `SendMoneyScreen` and `SendMoneyViewModel` with recipient search, numeric keypad amount entry, and transfer review/confirmation sheet
- [x] 5.5 Implement `ReceiptScreen` and `ReceiptViewModel` with transaction success/failure views, receipt details, and reference IDs
- [x] 5.6 Implement `ReceivableDetailScreen` and `ReceivableDetailViewModel` with receivable details, transaction timeline, payment registration action, and cancellation
- [x] 5.7 Implement `RegisterPaymentScreen` / payment flow dialog with amount validation, payment method selector, and proof document upload indicator

## 6. Verification and Testing

- [x] 6.1 Execute JVM unit tests for ViewModels, domain mappers, and Money/Date formatters using `.\gradlew.bat testDebugUnitTest` and verify all tests pass
- [x] 6.2 Execute assemble build using `.\gradlew.bat assembleDebug` and verify APK output is generated without warnings or errors
