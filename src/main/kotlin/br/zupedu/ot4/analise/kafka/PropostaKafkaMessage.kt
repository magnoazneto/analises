package br.zupedu.ot4.analise.kafka

import br.zupedu.ot4.analise.Analise
import br.zupedu.ot4.analise.StatusRestricao

data class PropostaKafkaMessage(
    val documento: String,
    val nome: String,
    val statusRestricao: StatusRestricao,
    val idProposta: Long
) {
    constructor(analise: Analise) : this(
        analise.documento,
        analise.nome,
        analise.status,
        analise.ipProposta
    )
}