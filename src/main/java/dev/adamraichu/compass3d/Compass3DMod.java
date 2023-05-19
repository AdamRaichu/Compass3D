package dev.adamraichu.compass3d;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.adamraichu.compass3d.config.ConfigOptions;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * Compass3D Mod
 */
@Environment(EnvType.CLIENT)
public class Compass3DMod implements ClientModInitializer {
	public static final String MOD_ID = "compass3d";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		AutoConfig.register(ConfigOptions.class, GsonConfigSerializer::new);

		LOGGER.info(LOGGER.getName() + " config initialized.");
	}
}
