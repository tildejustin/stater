package xyz.tildejustin.stater;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.spongepowered.include.com.google.gson.Gson;
import org.spongepowered.include.com.google.gson.GsonBuilder;

import java.io.*;
import java.util.logging.Logger;

public class Stater implements ModInitializer {
	static Logger LOGGER = Logger.getLogger("stater");
	static File CONFIG_FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), "stater.json");
	public static Config config;
	public static void log(String message) {
		LOGGER.info(message);
	}

	@Override
	public void onInitialize() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try {
			if (CONFIG_FILE.createNewFile()) {
				try (Writer writer = new FileWriter(CONFIG_FILE)) {
					gson.toJson(new Config(1000, true), writer);
					log("wrote config file");
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		BufferedReader bufferedReader;
		try {
			bufferedReader = new BufferedReader(new FileReader(CONFIG_FILE));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		config = gson.fromJson(bufferedReader, Config.class);
		log("log interval: " + config.getInterval() + ", logging state: " + config.shouldLog());

	}

	public static class Config {
		private final long interval;
		private final boolean logging;

		@SuppressWarnings("unused")
		Config() {
			this.interval = 1000;
			this.logging = true;
		}

		Config(long interval, boolean logging) {
			this.interval = interval;
			this.logging = logging;
		}

		public long getInterval() {
			return interval;
		}

		public boolean shouldLog() {
			return logging;
		}
	}
}
