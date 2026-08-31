package com.example.rms.domain.repository

import com.example.rms.domain.model.Client
import com.example.rms.domain.model.DashboardKpi
import com.example.rms.domain.model.MonthlyReceipt
import com.example.rms.domain.model.Payment
import com.example.rms.domain.model.PaymentMethodBreakdown
import com.example.rms.domain.model.Receivable
import com.example.rms.domain.model.ReceivableStatus
import com.example.rms.domain.model.TopClient
import com.example.rms.domain.model.DelinquencyPoint
import kotlinx.coroutines.flow.Flow

interface ClientRepository {
    suspend fun getClients(): Flow<Result<List<Client>>>
    suspend fun getClient(id: String): Flow<Result<Client>>
    suspend fun createClient(name: String, cpfCnpj: String, email: String, phone: String): Flow<Result<Client>>
    suspend fun updateClient(id: String, name: String, cpfCnpj: String, email: String, phone: String): Flow<Result<Client>>
    suspend fun deleteClient(id: String): Flow<Result<Unit>>
}

interface ReceivableRepository {
    suspend fun getReceivables(): Flow<Result<List<Receivable>>>
    suspend fun getReceivable(id: String): Flow<Result<Receivable>>
    suspend fun getDashboardKpi(): Flow<Result<DashboardKpi>>
    suspend fun createReceivable(clientId: String, description: String, amount: java.math.BigDecimal, dueDate: java.time.LocalDate): Flow<Result<Receivable>>
    suspend fun updateStatus(id: String, status: ReceivableStatus): Flow<Result<Receivable>>
    suspend fun cancel(id: String): Flow<Result<Receivable>>
    suspend fun getRecentActivity(): Flow<Result<List<Payment>>>
}

interface PaymentRepository {
    suspend fun getPayments(): Flow<Result<List<Payment>>>
    suspend fun getPayment(id: String): Flow<Result<Payment>>
    suspend fun createPayment(receivableId: String, amount: java.math.BigDecimal, paymentMethod: com.example.rms.domain.model.PaymentMethod, transactionReference: String? = null, notes: String? = null): Flow<Result<Payment>>
    suspend fun getStatistics(): Flow<Result<StatisticsData>>
}

data class StatisticsData(
    val monthlyReceipts: List<MonthlyReceipt>,
    val paymentMethods: List<PaymentMethodBreakdown>,
    val delinquency: List<DelinquencyPoint>,
    val topClients: List<TopClient>
)