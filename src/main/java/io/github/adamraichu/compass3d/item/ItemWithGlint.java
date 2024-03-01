package io.github.adamraichu.compass3d.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemWithGlint extends Item {
  public ItemWithGlint(Settings settings) {
    super(settings);
  }

  @Override
  public boolean hasGlint(ItemStack stack) {
    return true;
  }
}
