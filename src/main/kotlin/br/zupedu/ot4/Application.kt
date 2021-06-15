package br.zupedu.ot4

import io.micronaut.runtime.Micronaut.*
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License

@OpenAPIDefinition(
	info = Info(
		title = "Analises",
		version = "1.0",
		description = "My API",
		license = License(name = "Apache 2.0", url = "https://foo.bar"),
		contact = Contact(url = "https://github.com/magnoazneto", name = "Magno Azevedo", email = "magnoazneto@gmail.com")
	)
)
object Application {

	@JvmStatic
	fun main(args: Array<String>) {
		build()
			.args(*args)
			.packages("br.zupedu.ot4")
			.start()
	}
}
