package br.zupedu.ot4.analise

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Analise(
    val documento: String,
    val nome: String,
    val ipProposta: Long,
    @field:Enumerated(EnumType.STRING)
    val status: StatusRestricao
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
    val criadaEm = LocalDateTime.now()
}