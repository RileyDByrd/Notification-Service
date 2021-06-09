package byrd.riley.notificationservicekotlin.test.model

import org.apache.qpid.server.SystemLauncher
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext

class EmbeddedAMQPBroker: BeforeAllCallback, AfterAllCallback {
    companion object {
        const val CONFIG_FILE_NAME = "qpid-config.json"
        const val BROKER_PORT = 5672
    }

    val broker = SystemLauncher()


    override fun beforeAll(context: ExtensionContext?) {
        val attributes = mapOf<String, Any>(
            Pair("type", "Memory"),
            Pair(
                "initialConfigurationLocation",
                this::class.java.classLoader.getResource(CONFIG_FILE_NAME).toExternalForm()
            ),
            Pair("qpid.amqp_port", BROKER_PORT.toString())
        )

        broker.startup(attributes)
    }

    override fun afterAll(context: ExtensionContext?) {
        broker.shutdown()
    }
}