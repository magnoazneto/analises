package br.zupedu.ot4.analise.kafka

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.Topic

@KafkaClient
interface AnaliseKafkaClient {

    @Topic("propostas-elegiveis")
    fun enviarPropostaElegivel(@KafkaKey idProposta: Long, proposta: PropostaKafkaMessage)
}