package br.zupedu.ot4.analise

import br.zupedu.ot4.AnaliseResponse
import br.zupedu.ot4.AnaliseResponse.*
import io.micronaut.validation.Validated
import javax.inject.Inject
import javax.inject.Singleton
import javax.validation.Valid

@Validated
@Singleton
class AnaliseRestricaoService(
    @Inject val analiseRepository: AnaliseRepository
) {
    fun novaAnalise(@Valid request: SolicitacaoRequest) : AnaliseResponse {
        val resultadoRestricao = existeRestricao(request.documento)
        val novaAnalise = request.toModel(resultadoRestricao).let { analiseRepository.save(it) }

        return newBuilder()
            .setIdProposta(novaAnalise.ipProposta)
            .setResultadoAnalise(novaAnalise.status.paraTipoGrpc())
            .build()
    }

    /**
     * Apenas para simular uma consulta de restrição por Documento
     */
    private fun existeRestricao(documento: String): StatusRestricao {
        return if (documento[0].toInt() % 2 == 0) {
            StatusRestricao.COM_RESTRICAO
        } else { StatusRestricao.SEM_RESTRICAO }
    }
}