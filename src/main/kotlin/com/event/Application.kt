package com.event

import io.micronaut.runtime.Micronaut
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.extensions.Extension
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty
import io.swagger.v3.oas.annotations.info.Info

@OpenAPIDefinition(
	info = Info(
		title = "Event Ticketing System",
		version = "1.0",
		description = "This is a simple event ticketing system API",
	),
	extensions = [
		Extension(
			name= "api-definition",
			properties = [
				ExtensionProperty(name = "api_framework", value = "Micronaut Kotlin"),
			]
		)
	]
)
object Application {

	@JvmStatic
	fun main(args: Array<String>) {

		Micronaut.build()
			.packages("com.event")
			.mainClass(Application.javaClass)
			.start()
	}
}

