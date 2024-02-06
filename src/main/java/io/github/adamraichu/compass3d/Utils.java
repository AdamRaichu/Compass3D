package io.github.adamraichu.compass3d;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.adamraichu.compass3d.config.ConfigOptions;
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

    // Get player Y level
    MinecraftClient instance = MinecraftClient.getInstance();
    ClientPlayerEntity player = instance.player;
    int playerY = ((int) Math.round(player.getY()));

    Identifier dimensionId = player.getWorld().getDimensionKey().getValue();
    String dimensionString = dimensionId.getNamespace() + ":" + dimensionId.getPath();

    // Get compass Y level
    if (isLodestoneCompass) {
      if (!compound.getString("LodestoneDimension").equals(dimensionString)) {
        // LodestoneDimension does not equal player dimension.
        return null;
      }
      compassY = compound.getCompound("LodestonePos").getInt("Y");
    } else if (isCompass) {
      if (!dimensionString.equals("minecraft:overworld")) {
        // Is a regular compass, but player is not in the overworld.
        return null;
      }
      compassY = instance.world.getSpawnPos().getY();
    } else if (isRecoveryCompass) {
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
    } else {
      // This case should never happen
      return null;
    }

    boolean useRecoveryArrows = config.useBlueRecoveryArrows && isRecoveryCompass;

    // Compare player and compass Y levels
    if (playerY < compassY) {
      if (useRecoveryArrows) {
        displayItemStack = Compass3DMod.RECOVERY_UP_ARROW.getDefaultStack();
      } else {
        displayItemStack = Compass3DMod.UP_ARROW.getDefaultStack();
      }
    } else if (playerY > compassY) {
      if (useRecoveryArrows) {
        displayItemStack = Compass3DMod.RECOVERY_DOWN_ARROW.getDefaultStack();
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