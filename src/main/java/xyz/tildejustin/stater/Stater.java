package xyz.tildejustin.stater;

import net.fabricmc.api.ModInitializer;

import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Stater implements ModInitializer {
	static Logger LOGGER = Logger.getLogger("state");
	public static long log_interval = 1000;

	public static void log(String message) {
		LOGGER.info(message);
	}

	@Override
	public void onInitialize() {
		log_interval = 50;
	}
}
