package com.event

import io.micronaut.runtime.Micronaut
object Application {

	@JvmStatic
	fun main(args: Array<String>) {

		Micronaut.build()
			.packages("com.event")
			.mainClass(Application.javaClass)
			.start()
	}
}

