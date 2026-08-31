package com.example.rms.data.repository

import com.example.rms.data.remote.ApiService
import com.example.rms.data.remote.ClientRequest
import com.example.rms.data.remote.LoginRequest
import com.example.rms.data.remote.LoginResponse
import com.example.rms.data.remote.AuthRepository
import com.example.rms.data.remote.RetrofitClient
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
import kotlinx.coroutines.flow.flowOf
import retrofit2.HttpException
import java.io.IOException
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

class RealClientRepository(private val api: ApiService) : ClientRepository {
    override suspend fun getClients(): Flow<Result<List<Client>>> = flow {
        try {
            val response = api.getClients("Bearer ${com.example.rms.data.remote.AuthManager.token}")
            if (response.isSuccessful) {
                emit(Result.success(response.body()?.map { it.toDomain() } ?: emptyList()))
            } else {
                emit(Result.failure(Exception("Failed to fetch clients: ${response.code()}")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override suspend fun getClient(id: String): Flow<Result<Client>> = flow {
        try {
            val response = api.getClient("Bearer ${com.example.rms.data.remote.AuthManager.token}", id)
            if (response.isSuccessful) {
                emit(Result.success(response.body()?.toDomain() ?: throw Exception("Client not found")))
            } else {
                emit(Result.failure(Exception("Client not found")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override suspend fun createClient(name: String, cpfCnpj: String, email: String, phone: String): Flow<Result<Client>> = flow {
        try {
            val response = api.createClient(
                "Bearer ${com.example.rms.data.remote.AuthManager.token}",
                ClientRequest(name, cpfCnpj, email, phone)
            )
            if (response.isSuccessful) {
                emit(Result.success(response.body()?.toDomain() ?: throw Exception("Client not found")))
            } else {
                emit(Result.failure(Exception("Failed to create client")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override suspend fun updateClient(id: String, name: String, cpfCnpj: String, email: String, phone: String): Flow<Result<Client>> = flow {
        try {
            val response = api.updateClient(
                "Bearer ${com.example.rms.data.remote.AuthManager.token}",
                id,
                ClientRequest(name, cpfCnpj, email, phone)
            )
            if (response.isSuccessful) {
                emit(Result.success(response.body()?.toDomain() ?: throw Exception("Client not found")))
            } else {
                emit(Result.failure(Exception("Client not found")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override suspend fun deleteClient(id: String): Flow<Result<Unit>> = flow {
        try {
            val response = api.deleteClient("Bearer ${com.example.rms.data.remote.AuthManager.token}", id)
            if (response.isSuccessful) {
                emit(Result.success(Unit))
            } else {
                emit(Result.failure(Exception("Failed to delete client")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}

class RealReceivableRepository(private val api: ApiService) : ReceivableRepository {
    override suspend fun getReceivables(): Flow<Result<List<Receivable>>> = flow {
        try {
            val response = api.getReceivables("Bearer ${com.example.rms.data.remote.AuthManager.token}")
            if (response.isSuccessful) {
                val clientsResponse = api.getClients("Bearer ${com.example.rms.data.remote.AuthManager.token}")
                val clientMap = if (clientsResponse.isSuccessful) {
                    (clientsResponse.body() ?: emptyList()).associateBy { it.id.toString() }
                } else {
                    emptyMap()
                }
                emit(Result.success(
                    (response.body() ?: emptyList()).map { resp ->
                        resp.toDomain(clientMap[resp.clientId.toString()]?.name ?: "Unknown Client")
                    }
                ))
            } else {
                emit(Result.failure(Exception("Failed to fetch receivables: ${response.code()}")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override suspend fun getReceivable(id: String): Flow<Result<Receivable>> = flow {
        try {
            val response = api.getReceivable("Bearer ${com.example.rms.data.remote.AuthManager.token}", id)
            if (response.isSuccessful) {
                val clientsResponse = api.getClients("Bearer ${com.example.rms.data.remote.AuthManager.token}")
                val clientMap = if (clientsResponse.isSuccessful) {
                    (clientsResponse.body() ?: emptyList()).associateBy { it.id.toString() }
                } else {
                    emptyMap()
                }
                emit(Result.success(response.body()?.toDomain(clientMap[response.body()?.clientId.toString()]?.name ?: "Unknown Client") ?: throw Exception("Not found")))
            } else {
                emit(Result.failure(Exception("Receivable not found")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override suspend fun getDashboardKpi(): Flow<Result<com.example.rms.domain.model.DashboardKpi>> = flow {
        try {
            val response = api.getReceivables("Bearer ${com.example.rms.data.remote.AuthManager.token}")
            if (response.isSuccessful) {
                val receivables = response.body() ?: emptyList()
                val total = receivables.sumOf { it.originalAmount.toDouble() }
                val pending = receivables.filter { it.status == ReceivableStatus.PENDING || it.status == ReceivableStatus.PARTIAL }
                    .sumOf { it.remainingBalance.toDouble() }
                val overdue = receivables.filter { it.status == ReceivableStatus.OVERDUE }
                    .sumOf { it.remainingBalance.toDouble() }
                val received = total - pending - overdue
                emit(Result.success(
                    com.example.rms.domain.model.DashboardKpi(
                        totalReceivables = BigDecimal(total.toString()),
                        pendingValue = BigDecimal(pending.toString()),
                        receivedValue = BigDecimal(received.toString()),
                        overdueValue = BigDecimal(overdue.toString()),
                        collectionRate = if (total > 0) BigDecimal(String.format("%.1f", (received / total * 100))) else BigDecimal.ZERO
                    )
                ))
            } else {
                emit(Result.failure(Exception("Failed to fetch dashboard")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override suspend fun createReceivable(clientId: String, description: String, amount: BigDecimal, dueDate: LocalDate): Flow<Result<Receivable>> = flow {
        try {
            val response = api.createReceivable(
                "Bearer ${com.example.rms.data.remote.AuthManager.token}",
                com.example.rms.data.remote.ReceivableRequest(clientId, description, amount.toString(), dueDate.toString())
            )
            if (response.isSuccessful) {
                emit(Result.success(response.body()?.toDomain("Unknown Client") ?: throw Exception("Failed to create")))
            } else {
                emit(Result.failure(Exception("Failed to create receivable")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override suspend fun updateStatus(id: String, status: ReceivableStatus): Flow<Result<Receivable>> = flow {
        try {
            val response = api.updateStatus(
                "Bearer ${com.example.rms.data.remote.AuthManager.token}",
                id,
                com.example.rms.data.remote.StatusChangeRequest(status)
            )
            if (response.isSuccessful) {
                val clientsResponse = api.getClients("Bearer ${com.example.rms.data.remote.AuthManager.token}")
                val clientMap = if (clientsResponse.isSuccessful) {
                    (clientsResponse.body() ?: emptyList()).associateBy { it.id.toString() }
                } else {
                    emptyMap()
                }
                emit(Result.success(response.body()?.toDomain(clientMap[response.body()?.clientId.toString()]?.name ?: "Unknown Client") ?: throw Exception("Failed to update")))
            } else {
                emit(Result.failure(Exception("Failed to update status")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override suspend fun cancel(id: String): Flow<Result<Receivable>> = flow {
        updateStatus(id, ReceivableStatus.CANCELLED)
    }

    override suspend fun getRecentActivity(): Flow<Result<List<Payment>>> = flow {
        try {
            val response = api.getPayments("Bearer ${com.example.rms.data.remote.AuthManager.token}")
            if (response.isSuccessful) {
                emit(Result.success((response.body() ?: emptyList()).map { it.toDomain() }))
            } else {
                emit(Result.failure(Exception("Failed to fetch payments")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}

class RealPaymentRepository(private val api: ApiService) : PaymentRepository {
    override suspend fun getPayments(): Flow<Result<List<Payment>>> = flow {
        try {
            val response = api.getPayments("Bearer ${com.example.rms.data.remote.AuthManager.token}")
            if (response.isSuccessful) {
                emit(Result.success((response.body() ?: emptyList()).map { it.toDomain() }))
            } else {
                emit(Result.failure(Exception("Failed to fetch payments")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override suspend fun getPayment(id: String): Flow<Result<Payment>> = flow {
        emit(Result.failure(Exception("Not implemented")))
    }

    override suspend fun createPayment(receivableId: String, amount: BigDecimal, paymentMethod: com.example.rms.domain.model.PaymentMethod, transactionReference: String?, notes: String?): Flow<Result<Payment>> = flow {
        try {
            val response = api.createPayment(
                "Bearer ${com.example.rms.data.remote.AuthManager.token}",
                com.example.rms.data.remote.PaymentRequest(
                    receivableId = receivableId,
                    amount = amount.toString(),
                    paymentMethod = paymentMethod.name,
                    transactionReference = transactionReference,
                    notes = notes
                )
            )
            if (response.isSuccessful) {
                emit(Result.success(response.body()?.toDomain() ?: throw Exception("Failed to create payment")))
            } else {
                emit(Result.failure(Exception("Failed to create payment")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override suspend fun getStatistics(): Flow<Result<StatisticsData>> = flow {
        emit(Result.success(StatisticsData(emptyList(), emptyList(), emptyList(), emptyList())))
    }
}