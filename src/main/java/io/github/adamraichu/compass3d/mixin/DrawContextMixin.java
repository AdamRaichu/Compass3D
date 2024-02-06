package io.github.adamraichu.compass3d.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import io.github.adamraichu.compass3d.RegexGroup;
import io.github.adamraichu.compass3d.Utils;
import io.github.adamraichu.compass3d.config.ConfigOptions;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

@Mixin(DrawContext.class)
public abstract class DrawContextMixin {
  @Shadow
  public abstract void drawItemWithoutEntity(ItemStack stack, int x, int y);

  private float smallScale = 10f;
  private float smallTranslateX = 12f;
  private float smallTranslateY = 12f;
  private float smallTranslateZ = 10f;

  boolean adjustSize = false;

  @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isItemBarVisible()Z"), method = "drawItemInSlot(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V")
  private void renderCompassItemOverlay(TextRenderer renderer, ItemStack stack, int x, int y,
      @Nullable String countLabel, CallbackInfo info) {

    ConfigOptions config = AutoConfig.getConfigHolder(ConfigOptions.class).getConfig();
    if (config.disableMod)
      return;

    boolean isCompass = Utils.isObject(stack, RegexGroup.MINECRAFT_COMPASS);
    boolean isLodestoneCompass = Utils.isObject(stack, RegexGroup.MINECRAFT_LODESTONE_COMPASS);
    boolean isRecoveryCompass = Utils.isObject(stack, RegexGroup.MINECRAFT_RECOVERY_COMPASS);

    if (!(isLodestoneCompass || isCompass || isRecoveryCompass))
      return;

    NbtCompound compound = stack.getNbt();
    if (compound == null && isLodestoneCompass)
      return;

    ItemStack displayItem = Utils.getDisplayItem(compound, stack, config);
    if (displayItem == null)
      return;

    if (stack.getCount() == 1) {
      // Normal icon location
      smallScale = config.scale;
      smallTranslateX = config.translateX;
      smallTranslateY = config.translateY;
      smallTranslateZ = config.translateZ * 10;
    } else {
      // Stackable compasses are enabled, so change icon location to avoid item
      // counter
      smallScale = config.stackedScale;
      smallTranslateX = config.stackedTranslateX;
      smallTranslateY = config.stackedTranslateY;
      smallTranslateZ = config.stackedTranslateZ * 10;
    }

    adjustSize = true;
    drawItemWithoutEntity(displayItem, x, y);
    adjustSize = false;
  }

  @ModifyArgs(method = "drawItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;IIII)V", at = @At(value = "INVOKE", target = "net/minecraft/client/util/math/MatrixStack.translate(FFF)V"))
  private void injectedTranslateXYZ(Args args) {
    if (adjustSize) {
      args.set(0, (float) args.get(0) - 8.0F + smallTranslateX);
      args.set(1, (float) args.get(1) - 8.0F + smallTranslateY);
      args.set(2, 100.0F + smallTranslateZ);
    }
  }

  @ModifyArgs(method = "drawItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;IIII)V", at = @At(value = "INVOKE", target = "net/minecraft/client/util/math/MatrixStack.scale(FFF)V"))
  private void injectedScale(Args args) {
    if (adjustSize) {
      args.set(0, smallScale);
      args.set(1, smallScale);
      args.set(2, smallScale);
    }
  }

}
