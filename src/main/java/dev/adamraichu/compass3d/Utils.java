package dev.adamraichu.compass3d;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dev.adamraichu.compass3d.config.ConfigOptions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

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
  public static ItemStack getDisplayItem(NbtCompound compound, ConfigOptions config) {
    ItemStack displayItemStack = null;
    int compassY = compound.getCompound("LodestonePos").getInt("Y");
    MinecraftClient instance = MinecraftClient.getInstance();
    ClientPlayerEntity player = instance.player;
    int playerY = ((int) Math.round(player.getY()));

    if (playerY < compassY) {
      displayItemStack = Compass3DMod.UP_ARROW.getDefaultStack();
    } else if (playerY > compassY) {
      displayItemStack = Compass3DMod.DOWN_ARROW.getDefaultStack();
    } else {
      return null;
    }

    displayItemStack.setCount(1);
    return displayItemStack;
  }
}