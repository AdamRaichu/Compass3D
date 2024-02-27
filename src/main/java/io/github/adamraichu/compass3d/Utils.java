package io.github.adamraichu.compass3d;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jetbrains.annotations.Nullable;

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
  public static ItemStack getDisplayItem(NbtCompound compound, ItemStack stack, int referencePos,
      ConfigOptions config) {
    ItemStack displayItemStack;
    int compassY;

    boolean isCompass = isObject(stack, RegexGroup.MINECRAFT_COMPASS);
    boolean isLodestoneCompass = isObject(stack, RegexGroup.MINECRAFT_LODESTONE_COMPASS);
    boolean isRecoveryCompass = isObject(stack, RegexGroup.MINECRAFT_RECOVERY_COMPASS);
    boolean isNetheriteCompass = isObject(stack, RegexGroup.MODDED_NETHERITE_COMPASS);
    boolean isOreCompass = isObject(stack, RegexGroup.MODDED_ORE_COMPASS);
    boolean isDarkCompass = isObject(stack, RegexGroup.MODDED_DARK_COMPASS);
    boolean isPortalCompass = Utils.isObject(stack, RegexGroup.MODDED_PORTAL_COMPASS);

    // Get player Y level
    MinecraftClient instance = MinecraftClient.getInstance();
    ClientPlayerEntity player = instance.player;

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
      if (config.netheriteCompass.equals(ArrowSettings.DISABLED)) {
        return null;
      }
      // same as for ore compass, but for netherite compass
      GlobalPos trackedPos = dorkix.mods.netherite_compass.item.NetheriteCompass.getTrackedPos(compound);

      if (globalPosDimEquals(trackedPos, dimensionId)) {
        compassY = trackedPos.getPos().getY();
      } else {
        return null;
      }
    } else if (isOreCompass) {
      if (config.oreCompass.equals(ArrowSettings.DISABLED)) {
        return null;
      }
      // same as for netherite compass, but for ore compass
      GlobalPos trackedPos = com.technobecet.minerscompass.item.custom.OreCompass.getTrackedPos(compound);

      if (globalPosDimEquals(trackedPos, dimensionId)) {
        compassY = trackedPos.getPos().getY();
      } else {
        return null;
      }
    } else if (isDarkCompass) {
      if (config.darkCompass.equals(ArrowSettings.DISABLED)) {
        return null;
      }

      GlobalPos trackedPos = net.theblindbandit6.darkcompass.item.custom.DarkCompassItem
          .createDarkPos(player.clientWorld, compound);

      if (globalPosDimEquals(trackedPos, dimensionId)) {
        // IMPORTANT: This value is adjusted because the position in the compass is of
        // the block, not the air.
        compassY = trackedPos.getPos().getY() + 1;
      } else {
        return null;
      }
    } else if (isPortalCompass) {
      if (config.portalCompass.equals(ArrowSettings.DISABLED)) {
        return null;
      }

      GlobalPos trackedPos = dev.maxoduke.mods.portallinkingcompass.item.PortalLinkingCompassItem
          .pointToTarget(instance.world, stack, null);

      if (globalPosDimEquals(trackedPos, dimensionId)) {
        compassY = trackedPos.getPos().getY();
      } else {
        return null;
      }
    } else {
      // This case should never happen as-is, but that may change in the future.
      Compass3DMod.LOGGER.warn("Received impossible case in getDisplayItem()");
      return null;
    }

    boolean useRecoveryArrows = config.recoveryCompass.equals(ArrowSettings.MATCH_COMPASS_STYLE) && isRecoveryCompass;
    boolean useNetheriteArrows = config.netheriteCompass.equals(ArrowSettings.MATCH_COMPASS_STYLE)
        && isNetheriteCompass;
    boolean useOreArrows = config.oreCompass.equals(ArrowSettings.MATCH_COMPASS_STYLE) && isOreCompass;
    boolean useDarkArrows = config.darkCompass.equals(ArrowSettings.MATCH_COMPASS_STYLE) && isDarkCompass;
    boolean usePortalArrows = config.portalCompass.equals(ArrowSettings.MATCH_COMPASS_STYLE) && isPortalCompass;

    // Compare player and compass Y levels
    if (referencePos < compassY) {
      if (useRecoveryArrows) {
        displayItemStack = Compass3DMod.RECOVERY_UP_ARROW.getDefaultStack();
      } else if (useNetheriteArrows) {
        displayItemStack = Compass3DMod.MODDED_NETHERITE_UP_ARROW.getDefaultStack();
      } else if (useOreArrows) {
        displayItemStack = Compass3DMod.MODDED_ORE_UP_ARROW.getDefaultStack();
      } else if (useDarkArrows) {
        displayItemStack = Compass3DMod.MODDED_DARK_UP_ARROW.getDefaultStack();
      } else if (usePortalArrows) {
        displayItemStack = Compass3DMod.MODDED_PORTAL_UP_ARROW.getDefaultStack();
      } else {
        displayItemStack = Compass3DMod.UP_ARROW.getDefaultStack();
      }
    } else if (referencePos > compassY) {
      if (useRecoveryArrows) {
        displayItemStack = Compass3DMod.RECOVERY_DOWN_ARROW.getDefaultStack();
      } else if (useNetheriteArrows) {
        displayItemStack = Compass3DMod.MODDED_NETHERITE_DOWN_ARROW.getDefaultStack();
      } else if (useOreArrows) {
        displayItemStack = Compass3DMod.MODDED_ORE_DOWN_ARROW.getDefaultStack();
      } else if (useDarkArrows) {
        displayItemStack = Compass3DMod.MODDED_DARK_DOWN_ARROW.getDefaultStack();
      } else if (usePortalArrows) {
        displayItemStack = Compass3DMod.MODDED_PORTAL_DOWN_ARROW.getDefaultStack();
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

  public static boolean globalPosDimEquals(@Nullable GlobalPos pos, Identifier dimensionId) {
    if (Objects.isNull(pos)) {
      return false;
    }
    if (!pos.getDimension().getValue().equals(dimensionId)) {
      return false;
    }
    return true;
  }
}
