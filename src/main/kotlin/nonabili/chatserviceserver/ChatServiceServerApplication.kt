package nonabili.chatserviceserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ChatServiceServerApplication

fun main(args: Array<String>) {
    runApplication<ChatServiceServerApplication>(*args)
}
