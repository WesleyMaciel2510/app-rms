package com.example.rms.data.remote

import com.example.rms.domain.model.Client
import com.example.rms.domain.model.DashboardKpi
import com.example.rms.domain.model.Payment
import com.example.rms.domain.model.Receivable
import com.example.rms.domain.model.ReceivableStatus
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate
import java.util.UUID

data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResponse(
    val accessToken: String,
    val tokenType: String = "Bearer",
    val subject: String,
    val issuer: String,
    val issuedAt: Instant,
    val expiresAt: Instant,
    val authorities: List<String>
)

data class ClientResponse(
    val id: UUID,
    val name: String,
    val cpfCnpj: String,
    val email: String,
    val phone: String,
    val status: String,
    val createdAt: Instant? = null,
    val deletedAt: Instant? = null
) {
    fun toDomain(): Client = Client(
        id = id.toString(),
        name = name,
        cpfCnpj = cpfCnpj,
        email = email,
        phone = phone
    )
}

data class ReceivableResponse(
    val id: UUID,
    val clientId: UUID,
    val description: String,
    val originalAmount: BigDecimal,
    val remainingBalance: BigDecimal,
    val dueDate: LocalDate,
    val status: ReceivableStatus,
    val createdAt: Instant? = null,
    val deletedAt: Instant? = null
) {
    fun toDomain(clientName: String): Receivable = Receivable(
        id = id.toString(),
        clientId = clientId.toString(),
        clientName = clientName,
        description = description,
        originalAmount = originalAmount,
        remainingBalance = remainingBalance,
        dueDate = dueDate,
        status = status
    )
}

data class PaymentResponse(
    val id: UUID,
    val receivableId: UUID,
    val amount: BigDecimal,
    val paymentMethod: String,
    val paymentDate: Instant,
    val transactionReference: String? = null,
    val notes: String? = null
) {
    fun toDomain(): Payment = Payment(
        id = id.toString(),
        receivableId = receivableId.toString(),
        amount = amount,
        paymentMethod = when (paymentMethod.uppercase()) {
            "PIX" -> com.example.rms.domain.model.PaymentMethod.PIX
            "CREDIT_CARD" -> com.example.rms.domain.model.PaymentMethod.CREDIT_CARD
            "DEBIT_CARD" -> com.example.rms.domain.model.PaymentMethod.DEBIT_CARD
            "BOLETO" -> com.example.rms.domain.model.PaymentMethod.BOLETO
            "CASH" -> com.example.rms.domain.model.PaymentMethod.CASH
            "TED" -> com.example.rms.domain.model.PaymentMethod.TED
            "DIGITAL_WALLET" -> com.example.rms.domain.model.PaymentMethod.DIGITAL_WALLET
            else -> com.example.rms.domain.model.PaymentMethod.PIX
        },
        paymentDate = java.time.LocalDateTime.ofInstant(paymentDate, java.time.ZoneId.systemDefault()),
        transactionReference = transactionReference,
        notes = notes
    )
}

data class DashboardKpiResponse(
    val totalReceivables: BigDecimal,
    val pendingValue: BigDecimal,
    val receivedValue: BigDecimal,
    val overdueValue: BigDecimal,
    val collectionRate: BigDecimal
) {
    fun toDomain(): DashboardKpi = DashboardKpi(
        totalReceivables = totalReceivables,
        pendingValue = pendingValue,
        receivedValue = receivedValue,
        overdueValue = overdueValue,
        collectionRate = collectionRate
    )
}