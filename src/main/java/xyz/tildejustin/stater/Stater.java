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
	public static long log_interval = 1000;

	public static void log(String message) {
		LOGGER.info(message);
	}

	@Override
	public void onInitialize() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try {
			if (CONFIG_FILE.createNewFile()) {
				try (Writer writer = new FileWriter(CONFIG_FILE)) {
					gson.toJson(new Config(log_interval), writer);
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
		Config config = gson.fromJson(bufferedReader, Config.class);
		Stater.log_interval = config.getInterval();
		log("read config file, log interval set to " + config.getInterval());

	}

	static class Config {
		private final long interval;

		@SuppressWarnings("unused")
		Config() {
			this.interval = Stater.log_interval;
		}

		Config(long interval) {
			this.interval = interval;
		}

		public long getInterval() {
			return interval;
		}
	}
}
