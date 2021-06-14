package br.zupedu.ot4.analise

import br.zupedu.ot4.AnaliseRequest
import br.zupedu.ot4.shared.annotations.CPForCNPJ
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

@Introspected
data class SolicitacaoRequest(
    @field:CPForCNPJ val documento: String,
    @field:NotBlank val nome: String,
    @field:Positive val ipProposta: Long
) {
    constructor(request: AnaliseRequest) : this(
        documento = request.documento,
        nome = request.nome,
        ipProposta = request.idProposta
    )

    fun toModel(status: StatusRestricao): Analise {
        return Analise(documento, nome, ipProposta, status)
    }
}