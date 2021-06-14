package br.zupedu.ot4.analise

import br.zupedu.ot4.AnaliseRequest
import br.zupedu.ot4.AnaliseResponse
import br.zupedu.ot4.AnalisesServiceGrpc
import br.zupedu.ot4.shared.errors.ErrorHandler
import io.grpc.stub.StreamObserver
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@ErrorHandler
class AnaliseRestricaoEndpoint(
    @Inject val analiseRestricaoService: AnaliseRestricaoService
) : AnalisesServiceGrpc.AnalisesServiceImplBase() {

    override fun analisaRestricao(
        request: AnaliseRequest,
        responseObserver: StreamObserver<AnaliseResponse>
    ) {
        val response = analiseRestricaoService.novaAnalise(SolicitacaoRequest(request))
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }
}