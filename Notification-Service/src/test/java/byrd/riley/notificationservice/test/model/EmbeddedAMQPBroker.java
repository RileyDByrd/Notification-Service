package byrd.riley.notificationservice.test.model;

import java.net.URL;
import java.util.Map;
import java.util.Objects;

import org.apache.qpid.server.SystemLauncher;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class EmbeddedAMQPBroker implements BeforeAllCallback, AfterAllCallback {
	private static final String CONFIG_FILE_NAME = "qpid-config.json";
	private static final URL INITIAL_CONFIGURATION_LOCATION = EmbeddedAMQPBroker.class.getClassLoader().getResource(CONFIG_FILE_NAME);
	private static final int BROKER_PORT = 5672;
	
	private final SystemLauncher broker = new SystemLauncher();


	@Override
	public void beforeAll(ExtensionContext context) throws Exception {
		Objects.requireNonNull(INITIAL_CONFIGURATION_LOCATION);

		Map<String, Object> attributes = Map.of(
			"type", "Memory",
			"initialConfigurationLocation", INITIAL_CONFIGURATION_LOCATION.toExternalForm(),
			"qpid.amqp_port", String.valueOf(BROKER_PORT)
		);

		broker.startup(attributes);
	}

	@Override
	public void afterAll(ExtensionContext context) { broker.shutdown(); }
}
