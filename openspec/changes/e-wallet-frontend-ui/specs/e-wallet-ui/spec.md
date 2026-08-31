## Purpose

Provides the Android frontend UI components, screen flows, Jetpack Compose design tokens, navigation architecture, and state presentation layer for the E-Wallet / Receivables Management application, enabling Brazilian small businesses to manage clients, track receivables, record payments, and view financial statistics through a polished mobile experience.

## ADDED Requirements

### Requirement: Unauthenticated authentication screens

The system SHALL provide three unauthenticated screens for user authentication: Login, Create Account, and Password Recovery.

#### Scenario: User navigates to Login screen when unauthenticated
- **WHEN** app launches without a valid session
- **THEN** LoginScreen is displayed with email/password fields, password visibility toggle, "Forgot password?" link, and "Create account" link

#### Scenario: User successfully authenticates
- **WHEN** user enters valid credentials and submits
- **THEN** app navigates to HomeScreen, clears unauthenticated back stack, and establishes authenticated session

#### Scenario: User initiates account creation
- **WHEN** user taps "Create account" on LoginScreen
- **THEN** CreateAccountScreen is displayed with fields for Name, CPF/CNPJ, Email, Phone, Password, and Confirm Password

#### Scenario: User initiates password recovery
- **WHEN** user taps "Forgot password?" on LoginScreen
- **THEN** RecoveryPasswordScreen is displayed with email field and Continue button

### Requirement: Authenticated HomeScreen with dashboard KPIs

The system SHALL provide a HomeScreen that serves as the financial command center displaying dashboard KPIs, quick actions, receivables requiring attention, and recent activity.

#### Scenario: HomeScreen displays financial overview card
- **WHEN** HomeScreen loads with authenticated session
- **THEN** a hero balance card shows Total Receivables amount with purple gradient/tonal background and white primary text

#### Scenario: HomeScreen displays KPI cards
- **WHEN** HomeScreen loads
- **THEN** KPI cards display: Total Receivables, Pending Value, Received Value, Overdue Value, Collection Rate

#### Scenario: HomeScreen displays quick actions
- **WHEN** HomeScreen loads
- **THEN** quick action buttons display: Scan, Register Payment, Receivables, Statistics

#### Scenario: HomeScreen displays attention-required receivables
- **WHEN** overdue or pending receivables exist
- **THEN** an "Attention required" section shows receivable cards with status chips (OVERDUE, PARTIAL, PENDING)

#### Scenario: HomeScreen displays recent activity
- **WHEN** transaction history exists
- **THEN** recent activity list shows chronological events with type, amount, payment method, and timestamp

#### Scenario: HomeScreen supports pull-to-refresh
- **WHEN** user pulls down on HomeScreen
- **THEN** data refreshes and shows updated KPIs and receivables

#### Scenario: HomeScreen shows empty state when no receivables
- **WHEN** user has no receivables
- **THEN** empty state displays "No receivables yet" with "Create receivable" action

### Requirement: ScanScreen for QR/barcode scanning

The system SHALL provide a ScanScreen with camera-based QR code and barcode scanning capability, plus manual code entry fallback.

#### Scenario: ScanScreen requests camera permission
- **WHEN** user enters ScanScreen
- **THEN** camera permission is requested only when entering scanner functionality with explanation

#### Scenario: ScanScreen provides scan area overlay
- **WHEN** camera is active
- **THEN** scan area box with alignment instructions is displayed

#### Scenario: ScanScreen provides manual entry fallback
- **WHEN** user taps "Enter code manually"
- **THEN** manual code entry input is available

#### Scenario: ScanScreen validates before action
- **WHEN** code is scanned
- **THEN** decoded value is validated before any financial action is taken; no automatic execution occurs

#### Scenario: ScanScreen handles permission denied
- **WHEN** camera permission is denied
- **THEN** fallback UI explains why camera access is needed and provides manual entry alternative

### Requirement: StatisticsScreen with financial charts

The system SHALL provide a StatisticsScreen displaying financial performance trends including monthly receipts, payment methods distribution, delinquency evolution, and top clients.

#### Scenario: StatisticsScreen displays monthly receipts chart
- **WHEN** StatisticsScreen loads with data
- **THEN** bar/line chart shows receipts by month with clear labels and textual summary

#### Scenario: StatisticsScreen displays payment methods distribution
- **WHEN** StatisticsScreen loads with data
- **THEN** pie/bar chart shows payment method breakdown (PIX, Card, Boleto, etc.) with percentages

#### Scenario: StatisticsScreen displays delinquency evolution
- **WHEN** StatisticsScreen loads with data
- **THEN** line chart shows delinquency trend over time

#### Scenario: StatisticsScreen displays top clients
- **WHEN** StatisticsScreen loads with data
- **THEN** ranked list shows top clients by receivable volume

#### Scenario: StatisticsScreen supports date filtering
- **WHEN** user selects Month/Quarter/Year filter
- **THEN** charts update to reflect selected time period (only filters backend supports)

#### Scenario: StatisticsScreen shows empty state when insufficient data
- **WHEN** insufficient transaction data exists
- **THEN** empty state displays "Not enough data" with explanation

### Requirement: SendMoneyScreen as interaction shell

The system SHALL provide a SendMoneyScreen implementing the send/payment interaction flow with contact selection, amount entry, and confirmation step. This screen is initially a frontend interaction shell pending backend transfer API definition.

#### Scenario: SendMoneyScreen displays recipient selection
- **WHEN** user enters SendMoneyScreen
- **THEN** contact search field and recent contacts list are displayed

#### Scenario: SendMoneyScreen displays numeric keypad
- **WHEN** user interacts with amount field
- **THEN** numeric keypad (1-9, 0, decimal, backspace) is displayed for amount entry

#### Scenario: SendMoneyScreen requires confirmation before execution
- **WHEN** user enters amount and taps Continue
- **THEN** confirmation screen shows recipient, amount, and Cancel/Confirm buttons before any irreversible operation

### Requirement: ReceiptScreen for transaction results

The system SHALL provide a ReceiptScreen displaying success or failure state for payment/financial transactions with all relevant details.

#### Scenario: ReceiptScreen displays success state
- **WHEN** transaction completes successfully
- **THEN** success icon, "Transfer successful" headline, amount, client, payment method, date/time, and reference are displayed with success semantic color

#### Scenario: ReceiptScreen displays failure state
- **WHEN** transaction fails
- **THEN** error icon, "Payment could not be completed" headline, explanation that no money was received, and Try Again/Back buttons are displayed

#### Scenario: ReceiptScreen shows transaction details
- **WHEN** user taps "See details" on success receipt
- **THEN** detailed transaction information is displayed (Payment ID, Receivable ID, Amount, Method, Date, Reference, Notes)

### Requirement: ReceivableDetailScreen for receivable management

The system SHALL provide a ReceivableDetailScreen showing complete receivable information and available actions.

#### Scenario: ReceivableDetailScreen displays receivable information
- **WHEN** user navigates to ReceivableDetailScreen
- **THEN** client name, description, original amount, remaining balance, due date, and status are displayed

#### Scenario: ReceivableDetailScreen shows payment history timeline
- **WHEN** ReceivableDetailScreen loads
- **THEN** chronological timeline shows events: RECEIVABLE_CREATED, PAYMENT_RECEIVED, CANCELLATION, MANUAL_ADJUSTMENT with amount, method, and timestamp

#### Scenario: ReceivableDetailScreen provides payment registration action
- **WHEN** receivable status is PENDING, PARTIAL, or OVERDUE
- **THEN** "Register payment" action is available

#### Scenario: ReceivableDetailScreen provides cancel action
- **WHEN** receivable status allows cancellation
- **THEN** "Cancel receivable" action is available

### Requirement: RegisterPayment flow with payment method selection

The system SHALL provide a payment registration flow with amount entry, payment method selection, date, reference, notes, and proof document upload.

#### Scenario: Payment form validates amount
- **WHEN** user enters payment amount
- **THEN** amount must be required, greater than zero, and must not exceed remaining balance

#### Scenario: Payment method selector shows supported methods
- **WHEN** user opens payment method selector
- **THEN** options display: PIX, Credit Card, Debit Card, Boleto Bancário, Cash, TED, Digital Wallet

#### Scenario: Proof document upload shows progress
- **WHEN** user selects document for upload
- **THEN** filename is shown with upload progress indicator and retry on failure

#### Scenario: Payment form respects backend business rules
- **WHEN** payment is submitted
- **THEN** validation enforces remaining balance limits and status transitions per backend domain specification

### Requirement: Design system tokens and theming

The system SHALL implement a Material 3 based design system with semantic color tokens, typography, spacing, shapes, and dark mode support.

#### Scenario: Application uses semantic color tokens
- **WHEN** any composable renders
- **THEN** colors reference MaterialTheme.colorScheme semantic tokens (primary, success, warning, error, surface, background, text) instead of hardcoded values

#### Scenario: Application supports dark mode
- **WHEN** system theme changes to dark
- **THEN** dark color scheme applies with preserved financial hierarchy, maintained contrast, and readable charts

#### Scenario: Typography follows financial hierarchy
- **WHEN** financial values are displayed
- **THEN** display financial values use 32-40sp Bold, screen titles 24-28sp Bold, section titles 18-20sp SemiBold

#### Scenario: Spacing uses 8dp base grid
- **WHEN** layout spacing is applied
- **THEN** spacing tokens follow 4dp, 8dp, 12dp, 16dp, 24dp, 32dp, 40dp increments

#### Scenario: Shapes follow rounded surface specification
- **WHEN** cards, buttons, and inputs are rendered
- **THEN** cards use 16-20dp, primary buttons 14-18dp, text fields 12-16dp, bottom sheets 24dp top corners

### Requirement: Navigation architecture with authentication guards

The system SHALL implement a typed navigation graph with sealed route interface and authentication state guards.

#### Scenario: Unauthenticated users cannot access protected screens
- **WHEN** unauthenticated user attempts deep link to HomeScreen
- **THEN** navigation redirects to LoginScreen

#### Scenario: Authentication replaces unauthenticated stack
- **WHEN** user successfully logs in
- **THEN** unauthenticated destinations (Login, CreateAccount, RecoveryPassword) are removed from back stack

#### Scenario: Logout clears authenticated navigation state
- **WHEN** user logs out
- **THEN** all authenticated destinations are cleared and LoginScreen becomes the root destination

### Requirement: Money and date formatting for Brazilian locale

The system SHALL format all monetary values using BigDecimal with Brazilian formatting (R$ X.XXX,XX) and dates as dd/MM/yyyy.

#### Scenario: Monetary values display with Brazilian formatting
- **WHEN** any financial amount is displayed
- **THEN** format is "R$ X.XXX,XX" with period as thousands separator and comma as decimal separator

#### Scenario: Dates display in Brazilian format
- **WHEN** any date is displayed
- **THEN** format is "dd/MM/yyyy" (e.g., 15/09/2026)

### Requirement: Receivable status chips with semantic mapping

The system SHALL provide a reusable ReceivableStatusChip component mapping each status to icon and semantic color.

#### Scenario: PENDING status displays correctly
- **WHEN** receivable status is PENDING
- **THEN** chip shows Schedule icon with attention/warning semantic color and "PENDING" text

#### Scenario: PARTIAL status displays correctly
- **WHEN** receivable status is PARTIAL
- **THEN** chip shows Pie/Progress icon with informational semantic color and "PARTIAL" text

#### Scenario: PAID status displays correctly
- **WHEN** receivable status is PAID
- **THEN** chip shows CheckCircle icon with success semantic color and "PAID" text

#### Scenario: OVERDUE status displays correctly
- **WHEN** receivable status is OVERDUE
- **THEN** chip shows Warning icon with error semantic color and "OVERDUE" text

#### Scenario: CANCELLED status displays correctly
- **WHEN** receivable status is CANCELLED
- **THEN** chip shows Cancel icon with muted semantic color and "CANCELLED" text

#### Scenario: Status never communicated by color alone
- **WHEN** any status chip renders
- **THEN** text label is always present alongside icon and semantic color

### Requirement: Error handling with user-friendly messages

The system SHALL translate API errors into user-facing messages without exposing technical details.

#### Scenario: Network error shows retry action
- **WHEN** network request fails
- **THEN** inline error displays "Unable to load your receivables" with "Try again" button

#### Scenario: Authentication error shows clear message
- **WHEN** API returns 401 unauthorized
- **THEN** user sees "Email or password is incorrect" without HTTP status code

#### Scenario: Validation errors show inline
- **WHEN** form validation fails
- **THEN** field-level error messages appear progressively (not all at once) and form values are preserved

### Requirement: Loading patterns per context

The system SHALL use appropriate loading patterns: skeletons for initial loads, inline button progress for actions, refresh indicators for pull-to-refresh.

#### Scenario: Initial screen load shows skeleton placeholders
- **WHEN** HomeScreen or StatisticsScreen loads initially
- **THEN** skeleton placeholders appear for cards, charts, and lists instead of full-screen spinner

#### Scenario: Button actions show inline progress
- **WHEN** user taps primary action button
- **THEN** button shows progress indicator and disables during request

#### Scenario: Pull-to-refresh shows refresh indicator
- **WHEN** user pulls to refresh
- **THEN** refresh indicator appears at top of scrollable content

### Requirement: Accessibility compliance

The system SHALL meet minimum accessibility requirements: contrast, touch targets, content descriptions, dynamic font scaling, screen reader semantics, and color-independent status communication.

#### Scenario: Touch targets meet minimum size
- **WHEN** any interactive element renders
- **THEN** touch target is at least 48dp

#### Scenario: Icons have appropriate content descriptions
- **WHEN** meaningful icon renders
- **THEN** content description is provided; decorative icons are marked appropriately

#### Scenario: Status communicated by text and icon
- **WHEN** status chip renders
- **THEN** text label is present alongside icon and semantic color

### Requirement: Component system with reusable composables

The system SHALL provide a library of reusable Compose components parameterized by state/data without direct repository/ViewModel dependencies.

#### Scenario: Reusable components exist for all common UI patterns
- **WHEN** building screens
- **THEN** components available: EWalletScaffold, EWalletTopBar, EWalletBottomBar, FinancialBalanceCard, KpiCard, ReceivableCard, StatusChip, PrimaryButton, SecondaryButton, AmountInput, MoneyText, SearchField, PaymentMethodSelector, EmptyState, ErrorState, LoadingSkeleton, ConfirmationBottomSheet, ReceiptSuccessCard, TransactionTimeline, DocumentUploadCard