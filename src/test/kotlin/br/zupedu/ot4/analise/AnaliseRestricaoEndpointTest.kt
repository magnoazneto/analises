package br.zupedu.ot4.analise

import br.zupedu.ot4.AnaliseRequest
import br.zupedu.ot4.AnaliseResponse
import br.zupedu.ot4.AnalisesServiceGrpc
import br.zupedu.ot4.analise.kafka.AnaliseKafkaClient
import io.grpc.ManagedChannel
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import io.micronaut.grpc.server.GrpcServerChannel
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.Mockito

import javax.inject.Inject

@MicronautTest(transactional = false)
internal class AnaliseRestricaoEndpointTest(
    val analiseRepository: AnaliseRepository,
    val grpcClient: AnalisesServiceGrpc.AnalisesServiceBlockingStub
){
    @Inject
    lateinit var kafkaClient: AnaliseKafkaClient

    private lateinit var request : AnaliseRequest.Builder

    @BeforeEach
    internal fun setUp() {
        request = AnaliseRequest.newBuilder()
            .setIdProposta(1L)
            .setNome("Magno")
            .setDocumento("33186817684")
    }

    @ParameterizedTest
    @ValueSource(strings = ["33186817684", "40578401762"])
    fun `deve criar uma analise com dados validos`(documento: String) {
        val response : AnaliseResponse = grpcClient.analisaRestricao(request.setDocumento(documento).build())
        assertEquals(1L, response.idProposta)
    }

    @ParameterizedTest
    @ValueSource(strings = ["000", "", "12345678901"])
    fun `nao deve realizar analise com documento invalido ou em branco`(documento: String) {
        assertThrows<StatusRuntimeException> {
            grpcClient.analisaRestricao(request.setDocumento(documento).build())
        }.let { error ->
            assertEquals(Status.INVALID_ARGUMENT.code, error.status.code)
        }
    }

    @Test
    fun `nao deve realizar analise com nome em branco`() {
        assertThrows<StatusRuntimeException> {
            grpcClient.analisaRestricao(request.setNome("").build())
        }.let { error ->
            assertEquals(Status.INVALID_ARGUMENT.code, error.status.code)
        }
    }

    @ParameterizedTest
    @ValueSource(longs = [0, -1, -2000])
    fun `nao deve realizar analise com idProposta menor ou igual a 0`(id: Long) {
        assertThrows<StatusRuntimeException> {
            grpcClient.analisaRestricao(request.setIdProposta(id).build())
        }.let { error ->
            assertEquals(Status.INVALID_ARGUMENT.code, error.status.code)
        }
    }

    @Factory
    class Clients(@GrpcChannel(GrpcServerChannel.NAME) val channel: ManagedChannel) {
        @Bean
        fun blockingStrub() = AnalisesServiceGrpc.newBlockingStub(channel)
    }

    @MockBean(AnaliseKafkaClient::class)
    fun kafkaClient(): AnaliseKafkaClient? {
        return Mockito.mock(AnaliseKafkaClient::class.java)
    }

}