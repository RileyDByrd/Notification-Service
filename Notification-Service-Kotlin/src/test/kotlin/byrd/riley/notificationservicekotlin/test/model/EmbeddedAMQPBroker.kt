package byrd.riley.notificationservicekotlin.test.model

import org.apache.qpid.server.SystemLauncher
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext

class EmbeddedAMQPBroker: BeforeAllCallback, AfterAllCallback {
    companion object {
        private const val CONFIG_FILE_NAME = "qpid-config.json"
        private const val BROKER_PORT = 5672
        private val INITIAL_CONFIGURATION_LOCATION = Companion::class.java.classLoader.getResource(CONFIG_FILE_NAME)
    }

    private val broker = SystemLauncher()

    override fun beforeAll(context: ExtensionContext?) {
        val attributes = mapOf<String, Any>(
            "type" to "Memory",
            "initialConfigurationLocation" to INITIAL_CONFIGURATION_LOCATION!!.toExternalForm(),
            "qpid.amqp_port" to BROKER_PORT.toString()
        )

        broker.startup(attributes)
    }

    override fun afterAll(context: ExtensionContext?) {
        broker.shutdown()
    }
}