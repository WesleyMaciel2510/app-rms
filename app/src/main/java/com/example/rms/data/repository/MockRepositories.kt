package com.example.rms.data.repository

import com.example.rms.domain.model.Client
import com.example.rms.domain.model.Receivable
import com.example.rms.domain.model.ReceivableStatus
import com.example.rms.domain.model.Payment
import com.example.rms.domain.repository.ClientRepository
import com.example.rms.domain.repository.ReceivableRepository
import com.example.rms.domain.repository.PaymentRepository
import com.example.rms.domain.repository.StatisticsData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

class MockClientRepository : ClientRepository {
    private val clients = mutableListOf(
        Client("1", "Joao Silva", "123.456.789-00", "joao@email.com", "(11) 99999-9999"),
        Client("2", "Maria Santos", "987.654.321-00", "maria@email.com", "(11) 98888-8888")
    )

    override suspend fun getClients(): Flow<Result<List<Client>>> = flow {
        emit(Result.success(clients.toList()))
    }

    override suspend fun getClient(id: String): Flow<Result<Client>> = flow {
        val client = clients.find { it.id == id }
        if (client != null) emit(Result.success(client))
        else emit(Result.failure(Exception("Client not found")))
    }

    override suspend fun createClient(name: String, cpfCnpj: String, email: String, phone: String): Flow<Result<Client>> = flow {
        val newClient = Client((clients.size + 1).toString(), name, cpfCnpj, email, phone)
        clients.add(newClient)
        emit(Result.success(newClient))
    }

    override suspend fun updateClient(id: String, name: String, cpfCnpj: String, email: String, phone: String): Flow<Result<Client>> = flow {
        val index = clients.indexOfFirst { it.id == id }
        if (index >= 0) {
            clients[index] = clients[index].copy(name = name, cpfCnpj = cpfCnpj, email = email, phone = phone)
            emit(Result.success(clients[index]))
        } else {
            emit(Result.failure(Exception("Client not found")))
        }
    }

    override suspend fun deleteClient(id: String): Flow<Result<Unit>> = flow {
        val removed = clients.removeAll { it.id == id }
        emit(if (removed) Result.success(Unit) else Result.failure(Exception("Client not found")))
    }
}

class MockReceivableRepository(private val clientRepository: ClientRepository) : ReceivableRepository {
    private val receivables = mutableListOf(
        Receivable("1", "1", "Joao Silva", "Website Development", BigDecimal("2500.00"), BigDecimal("1500.00"), LocalDate.of(2026, 9, 15), ReceivableStatus.PARTIAL),
        Receivable("2", "2", "Maria Santos", "Consultoria", BigDecimal("3000.00"), BigDecimal("3000.00"), LocalDate.of(2026, 10, 1), ReceivableStatus.PENDING)
    )

    override suspend fun getReceivables(): Flow<Result<List<Receivable>>> = flow {
        emit(Result.success(receivables.toList()))
    }

    override suspend fun getReceivable(id: String): Flow<Result<Receivable>> = flow {
        val receivable = receivables.find { it.id == id }
        if (receivable != null) emit(Result.success(receivable))
        else emit(Result.failure(Exception("Receivable not found")))
    }

    override suspend fun getDashboardKpi(): Flow<Result<com.example.rms.domain.model.DashboardKpi>> = flow {
        val total = receivables.sumOf { it.originalAmount.toDouble() }
        val pending = receivables.filter { it.status == ReceivableStatus.PENDING || it.status == ReceivableStatus.PARTIAL }
            .sumOf { it.remainingBalance.toDouble() }
        val overdue = receivables.filter { it.status == ReceivableStatus.OVERDUE }
            .sumOf { it.remainingBalance.toDouble() }
        val received = total - pending - overdue
        emit(Result.success(com.example.rms.domain.model.DashboardKpi(
            totalReceivables = BigDecimal(total.toString()),
            pendingValue = BigDecimal(pending.toString()),
            receivedValue = BigDecimal(received.toString()),
            overdueValue = BigDecimal(overdue.toString()),
            collectionRate = BigDecimal("85.5")
        )))
    }

    override suspend fun createReceivable(clientId: String, description: String, amount: BigDecimal, dueDate: LocalDate): Flow<Result<Receivable>> = flow {
        var clientName = "Unknown Client"
        clientRepository.getClient(clientId).collect { result ->
            result.getOrNull()?.let { clientName = it.name }
        }
        val newReceivable = Receivable(
            id = (receivables.size + 1).toString(),
            clientId = clientId,
            clientName = clientName,
            description = description,
            originalAmount = amount,
            remainingBalance = amount,
            dueDate = dueDate,
            status = ReceivableStatus.PENDING
        )
        receivables.add(newReceivable)
        emit(Result.success(newReceivable))
    }

    override suspend fun updateStatus(id: String, status: ReceivableStatus): Flow<Result<Receivable>> = flow {
        val index = receivables.indexOfFirst { it.id == id }
        if (index >= 0) {
            receivables[index] = receivables[index].copy(status = status)
            emit(Result.success(receivables[index]))
        } else {
            emit(Result.failure(Exception("Receivable not found")))
        }
    }

    override suspend fun cancel(id: String): Flow<Result<Receivable>> = flow {
        updateStatus(id, ReceivableStatus.CANCELLED)
    }

    override suspend fun getRecentActivity(): Flow<Result<List<Payment>>> = flow {
        emit(Result.success(emptyList()))
    }
}

class MockPaymentRepository : PaymentRepository {
    private val payments = mutableListOf<Payment>()

    override suspend fun getPayments(): Flow<Result<List<Payment>>> = flow {
        emit(Result.success(payments.toList()))
    }

    override suspend fun getPayment(id: String): Flow<Result<Payment>> = flow {
        val payment = payments.find { it.id == id }
        if (payment != null) emit(Result.success(payment))
        else emit(Result.failure(Exception("Payment not found")))
    }

    override suspend fun createPayment(receivableId: String, amount: BigDecimal, paymentMethod: com.example.rms.domain.model.PaymentMethod, transactionReference: String?, notes: String?): Flow<Result<Payment>> = flow {
        val newPayment = Payment(
            id = (payments.size + 1).toString(),
            receivableId = receivableId,
            amount = amount,
            paymentMethod = paymentMethod,
            paymentDate = LocalDateTime.now(),
            transactionReference = transactionReference,
            notes = notes
        )
        payments.add(newPayment)
        emit(Result.success(newPayment))
    }

    override suspend fun getStatistics(): Flow<Result<StatisticsData>> = flow {
        emit(Result.success(
            StatisticsData(
                monthlyReceipts = emptyList(),
                paymentMethods = emptyList(),
                delinquency = emptyList(),
                topClients = emptyList()
            )
        ))
    }
}