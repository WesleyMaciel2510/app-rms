package com.example.rms.data.remote

import com.example.rms.domain.model.Client
import com.example.rms.domain.model.DashboardKpi
import com.example.rms.domain.model.Payment
import com.example.rms.domain.model.Receivable
import com.example.rms.domain.model.ReceivableStatus
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthService {
    @POST("/api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}

interface ClientsApi {
    @GET("/clients")
    suspend fun getClients(@Header("Authorization") token: String): Response<List<ClientResponse>>

    @GET("/clients/{id}")
    suspend fun getClient(@Header("Authorization") token: String, @Path("id") id: String): Response<ClientResponse>

    @POST("/clients")
    suspend fun createClient(
        @Header("Authorization") token: String,
        @Body request: ClientRequest
    ): Response<ClientResponse>

    @PUT("/clients/{id}")
    suspend fun updateClient(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body request: ClientRequest
    ): Response<ClientResponse>

    @DELETE("/clients/{id}")
    suspend fun deleteClient(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<Unit>
}

data class ClientRequest(
    val name: String,
    val cpfCnpj: String,
    val email: String,
    val phone: String
)

interface ReceivablesApi {
    @GET("/receivables")
    suspend fun getReceivables(@Header("Authorization") token: String): Response<List<ReceivableResponse>>

    @GET("/receivables/{id}")
    suspend fun getReceivable(@Header("Authorization") token: String, @Path("id") id: String): Response<ReceivableResponse>

    @POST("/receivables")
    suspend fun createReceivable(
        @Header("Authorization") token: String,
        @Body request: ReceivableRequest
    ): Response<ReceivableResponse>

    @PATCH("/receivables/{id}/status")
    suspend fun updateStatus(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body request: StatusChangeRequest
    ): Response<ReceivableResponse>

    @DELETE("/receivables/{id}")
    suspend fun deleteReceivable(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<Unit>
}

data class ReceivableRequest(
    val clientId: String,
    val description: String,
    val originalAmount: String,
    val dueDate: String
)

data class StatusChangeRequest(
    val status: ReceivableStatus
)

interface PaymentsApi {
    @GET("/payments")
    suspend fun getPayments(@Header("Authorization") token: String): Response<List<PaymentResponse>>

    @POST("/payments")
    suspend fun createPayment(
        @Header("Authorization") token: String,
        @Body request: PaymentRequest
    ): Response<PaymentResponse>
}

data class PaymentRequest(
    val receivableId: String,
    val amount: String,
    val paymentMethod: String,
    val transactionReference: String? = null,
    val notes: String? = null
)