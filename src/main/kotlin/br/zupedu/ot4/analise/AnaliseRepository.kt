package br.zupedu.ot4.analise

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
interface AnaliseRepository : JpaRepository<Analise, Long>{
}