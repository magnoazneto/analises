package br.zupedu.ot4.analise

import br.zupedu.ot4.AnaliseResponse

enum class StatusRestricao {
    COM_RESTRICAO {
        override fun paraTipoGrpc(): AnaliseResponse.ResultadoAnalise {
            return AnaliseResponse.ResultadoAnalise.COM_RESTRICAO
        }
    }, SEM_RESTRICAO {
        override fun paraTipoGrpc(): AnaliseResponse.ResultadoAnalise {
            return AnaliseResponse.ResultadoAnalise.SEM_RESTRICAO
        }
    };
    abstract fun paraTipoGrpc() : AnaliseResponse.ResultadoAnalise
}