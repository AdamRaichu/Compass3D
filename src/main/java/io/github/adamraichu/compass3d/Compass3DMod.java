package io.github.adamraichu.compass3d;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.adamraichu.compass3d.config.ConfigOptions;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

/**
 * Compass3D Mod
 */
@Environment(EnvType.CLIENT)
public class Compass3DMod implements ClientModInitializer {
	public static final String MOD_ID = "compass3d";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static Item UP_ARROW = null;
	public static Item DOWN_ARROW = null;

	@Override
	public void onInitializeClient() {
		AutoConfig.register(ConfigOptions.class, GsonConfigSerializer::new);
		LOGGER.info(LOGGER.getName() + " config initialized.");

		FabricItemSettings settings = new FabricItemSettings();
		UP_ARROW = new Item(settings);
		DOWN_ARROW = new Item(settings);

		Registry.register(Registries.ITEM, new Identifier(MOD_ID, "up_arrow"), UP_ARROW);
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, "down_arrow"), DOWN_ARROW);
		LOGGER.info("Custom items registered (for textures).");
	}
}
