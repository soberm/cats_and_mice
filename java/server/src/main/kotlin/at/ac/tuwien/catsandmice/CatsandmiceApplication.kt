package at.ac.tuwien.catsandmice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CatsandmiceApplication

fun main(args: Array<String>) {
	runApplication<CatsandmiceApplication>(*args)
}
