package io.github.adamraichu.compass3d;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.adamraichu.compass3d.config.ConfigOptions;
import io.github.adamraichu.compass3d.config.ConfigOptions.ArrowSettings;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.GlobalPos;

public class Utils {
  public static boolean isObject(ItemStack stack, RegexGroup group) {
    if (stack == null)
      return false;

    Pattern pattern = Pattern.compile(group.regex);
    Matcher matcher = pattern.matcher(stack.getTranslationKey());

    return matcher.find();
  }

  /**
   * Given a compass nbt, get the arrow to render.
   *
   * @param compound An NBTCompound containing compass data
   * @param config   The current config options for compass3d
   * @return An ItemStack for the display item, or null if there is none available
   */
  public static ItemStack getDisplayItem(NbtCompound compound, ItemStack stack, ConfigOptions config) {
    ItemStack displayItemStack;
    int compassY;

    boolean isCompass = isObject(stack, RegexGroup.MINECRAFT_COMPASS);
    boolean isLodestoneCompass = isObject(stack, RegexGroup.MINECRAFT_LODESTONE_COMPASS);
    boolean isRecoveryCompass = isObject(stack, RegexGroup.MINECRAFT_RECOVERY_COMPASS);
    boolean isNetheriteCompass = isObject(stack, RegexGroup.MODDED_NETHERITE_COMPASS);

    // Get player Y level
    MinecraftClient instance = MinecraftClient.getInstance();
    ClientPlayerEntity player = instance.player;
    int playerY = ((int) Math.round(player.getY()));

    Identifier dimensionId = player.getWorld().getDimensionKey().getValue();
    String dimensionString = dimensionId.getNamespace() + ":" + dimensionId.getPath();

    // Get compass Y level
    if (isLodestoneCompass) {
      if (config.lodestoneCompass.equals(ArrowSettings.DISABLED)) {
        return null;
      }
      if (!compound.getString("LodestoneDimension").equals(dimensionString)) {
        // LodestoneDimension does not equal player dimension.
        return null;
      }
      compassY = compound.getCompound("LodestonePos").getInt("Y");
    } else if (isCompass) {
      if (config.vanillaCompass.equals(ArrowSettings.DISABLED)) {
        return null;
      }
      if (!dimensionString.equals("minecraft:overworld")) {
        // Is a regular compass, but player is not in the overworld.
        return null;
      }
      compassY = instance.world.getSpawnPos().getY();
    } else if (isRecoveryCompass) {
      if (config.recoveryCompass.equals(ArrowSettings.DISABLED)) {
        return null;
      }
      Optional<GlobalPos> _lastDeathPos = player.getLastDeathPos();
      if (!_lastDeathPos.isPresent()) {
        // Player has not died yet
        return null;
      }

      GlobalPos lastDeathPos = _lastDeathPos.get();
      Identifier lastDeathDimensionId = lastDeathPos.getDimension().getValue();

      if (!lastDeathDimensionId.equals(dimensionId)) {
        // Player has died in a different dimension
        return null;
      }

      compassY = lastDeathPos.getPos().getY();
    } else if (isNetheriteCompass) {
      // (Used to shorten names)

      GlobalPos trackedPos = dorkix.mods.netherite_compass.item.NetheriteCompass.getTrackedPos(compound);

      if (java.util.Objects.isNull(trackedPos)) {
        return null;
      }
      if (!trackedPos.getDimension().getValue().equals(dimensionId)) {
        return null;
      }

      compassY = trackedPos.getPos().getY();
    } else {
      // This case should never happen as-is, but that may change in the future.
      Compass3DMod.LOGGER.warn("Received impossible case in getDisplayItem()");
      return null;
    }

    boolean useRecoveryArrows = config.recoveryCompass.equals(ArrowSettings.MATCH_COMPASS_STYLE)
        && isRecoveryCompass;
    boolean useNetheriteArrows = config.netheriteCompass.equals(ArrowSettings.MATCH_COMPASS_STYLE)
        && isNetheriteCompass;

    // Compare player and compass Y levels
    if (playerY < compassY) {
      if (useRecoveryArrows) {
        displayItemStack = Compass3DMod.RECOVERY_UP_ARROW.getDefaultStack();
      } else if (useNetheriteArrows) {
        displayItemStack = Compass3DMod.MODDED_NETHERITE_UP_ARROW.getDefaultStack();
      } else {
        displayItemStack = Compass3DMod.UP_ARROW.getDefaultStack();
      }
    } else if (playerY > compassY) {
      if (useRecoveryArrows) {
        displayItemStack = Compass3DMod.RECOVERY_DOWN_ARROW.getDefaultStack();
      } else if (useNetheriteArrows) {
        displayItemStack = Compass3DMod.MODDED_NETHERITE_DOWN_ARROW.getDefaultStack();
      } else {
        displayItemStack = Compass3DMod.DOWN_ARROW.getDefaultStack();
      }
    } else {
      // Player is at the right height
      return null;
    }

    displayItemStack.setCount(1);
    return displayItemStack;
  }
}
