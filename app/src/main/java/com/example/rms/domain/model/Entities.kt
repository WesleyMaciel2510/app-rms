package com.example.rms.domain.model

import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

enum class ReceivableStatus {
    PENDING, PARTIAL, PAID, OVERDUE, CANCELLED
}

data class Client(
    val id: String,
    val name: String,
    val cpfCnpj: String,
    val email: String,
    val phone: String,
    val status: ClientStatus = ClientStatus.ACTIVE
)

enum class ClientStatus {
    ACTIVE, INACTIVE, BLOCKED
}

data class Receivable(
    val id: String,
    val clientId: String,
    val clientName: String,
    val description: String,
    val originalAmount: BigDecimal,
    val remainingBalance: BigDecimal,
    val dueDate: LocalDate,
    val status: ReceivableStatus,
    val createdAt: LocalDateTime? = null
)

data class Payment(
    val id: String,
    val receivableId: String,
    val amount: BigDecimal,
    val paymentMethod: PaymentMethod,
    val paymentDate: LocalDateTime,
    val transactionReference: String? = null,
    val notes: String? = null,
    val documentId: String? = null
)

enum class PaymentMethod {
    PIX,
    CREDIT_CARD,
    DEBIT_CARD,
    BOLETO,
    CASH,
    TED,
    DIGITAL_WALLET
}

data class Document(
    val id: String,
    val receivableId: String?,
    val paymentId: String?,
    val fileName: String,
    val fileUrl: String,
    val uploadedAt: LocalDateTime
)

data class DashboardKpi(
    val totalReceivables: BigDecimal,
    val pendingValue: BigDecimal,
    val receivedValue: BigDecimal,
    val overdueValue: BigDecimal,
    val collectionRate: BigDecimal
)

data class MonthlyReceipt(
    val month: String,
    val total: BigDecimal
)

data class PaymentMethodBreakdown(
    val method: String,
    val percentage: Double
)

data class TopClient(
    val clientId: String,
    val clientName: String,
    val totalAmount: BigDecimal
)

data class DelinquencyPoint(
    val month: String,
    val overdueAmount: BigDecimal
)