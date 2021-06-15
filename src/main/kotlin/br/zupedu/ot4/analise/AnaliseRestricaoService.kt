package br.zupedu.ot4.analise

import br.zupedu.ot4.AnaliseResponse
import br.zupedu.ot4.AnaliseResponse.*
import br.zupedu.ot4.analise.kafka.AnaliseKafkaClient
import br.zupedu.ot4.analise.kafka.PropostaKafkaMessage
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton
import javax.validation.Valid

@Validated
@Singleton
class AnaliseRestricaoService(
    @Inject val analiseRepository: AnaliseRepository,
    @Inject val kafkaClient: AnaliseKafkaClient
) {
    private val LOGGER = LoggerFactory.getLogger(this::class.java)

    /**
     * Realiza uma nova Analise de Restrição.
     * @param request A request no padrão do sistema. Vai ser validada.
     * @return o objeto de resposta do tipo Protobuf
     */
    fun novaAnalise(@Valid request: SolicitacaoRequest) : AnaliseResponse {
        val resultadoRestricao = existeRestricao(request.documento)
        LOGGER.info("Proposta de id ${request.idProposta} analisada como $resultadoRestricao")

        val novaAnalise: Analise = request.toModel(resultadoRestricao).let { analiseRepository.save(it) }
        if(resultadoRestricao == StatusRestricao.SEM_RESTRICAO){
            kafkaClient.enviarPropostaElegivel(novaAnalise.ipProposta, PropostaKafkaMessage(novaAnalise))
        }

        return newBuilder()
            .setIdProposta(novaAnalise.ipProposta)
            .setResultadoAnalise(novaAnalise.status.paraTipoGrpc())
            .build()
    }

    /**
     * Apenas para simular uma consulta de restrição por Documento
     * @return um StatusRestricao no padrão do sistema
     */
    private fun existeRestricao(documento: String): StatusRestricao {
        return if (documento[0].toInt() % 2 == 0) {
            StatusRestricao.COM_RESTRICAO
        } else { StatusRestricao.SEM_RESTRICAO }
    }
}