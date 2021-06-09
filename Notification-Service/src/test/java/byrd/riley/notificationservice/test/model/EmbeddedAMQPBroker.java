package byrd.riley.notificationservice.test.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.qpid.server.SystemLauncher;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class EmbeddedAMQPBroker implements BeforeAllCallback, AfterAllCallback {
	private static final String CONFIG_FILE_NAME = "qpid-config.json";
	
	public static final int BROKER_PORT = 5672;
	
	private final SystemLauncher broker = new SystemLauncher();


	@Override
	public void beforeAll(ExtensionContext context) throws Exception {
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("type", "Memory");
		attributes.put(
			"initialConfigurationLocation",
			this.getClass().getClassLoader().getResource(CONFIG_FILE_NAME).toExternalForm()
		);
		attributes.put("qpid.amqp_port", String.valueOf(BROKER_PORT));

		broker.startup(attributes);
	}

	@Override
	public void afterAll(ExtensionContext context) throws Exception {
		broker.shutdown();
	}
}
