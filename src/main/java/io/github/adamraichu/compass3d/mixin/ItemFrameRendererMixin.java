package io.github.adamraichu.compass3d.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.adamraichu.compass3d.Compass3DMod;
import io.github.adamraichu.compass3d.RegexGroup;
import io.github.adamraichu.compass3d.Utils;
import io.github.adamraichu.compass3d.config.ConfigOptions;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.ItemFrameEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;

@Mixin(ItemFrameEntityRenderer.class)
public abstract class ItemFrameRendererMixin {
  private static ConfigOptions config = AutoConfig.getConfigHolder(ConfigOptions.class).get();

  @Shadow
  public abstract int getLight(ItemFrameEntity itemFrameEntity, int glowLight, int regularLight);

  @Inject(method = "Lnet/minecraft/client/render/entity/ItemFrameEntityRenderer;render(Lnet/minecraft/entity/decoration/ItemFrameEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At("HEAD"), cancellable = true)
  private void render(ItemFrameEntity itemFrameEntity, float f, float g, MatrixStack matrixStack,
      VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
    if (config.disableMod || !config.renderFramedCompasses) {
      return;
    }

    MinecraftClient client = MinecraftClient.getInstance();
    ItemStack heldItem = itemFrameEntity.getHeldItemStack();
    if (heldItem.isEmpty()) {
      return;
    }

    boolean isCompass = Utils.isObject(heldItem, RegexGroup.MINECRAFT_COMPASS);
    boolean isLodestoneCompass = Utils.isObject(heldItem, RegexGroup.MINECRAFT_LODESTONE_COMPASS);
    boolean isRecoveryCompass = Utils.isObject(heldItem, RegexGroup.MINECRAFT_RECOVERY_COMPASS);
    boolean isNetheriteCompass = Utils.isObject(heldItem, RegexGroup.MODDED_NETHERITE_COMPASS);
    boolean isOreCompass = Utils.isObject(heldItem, RegexGroup.MODDED_ORE_COMPASS);
    boolean isDarkCompass = Utils.isObject(heldItem, RegexGroup.MODDED_DARK_COMPASS);
    boolean isPortalCompass = Utils.isObject(heldItem, RegexGroup.MODDED_PORTAL_COMPASS);

    // Only enable recovery compass if Framed Recovery Compass Fix is installed.
    if (!(isLodestoneCompass || isCompass || (isRecoveryCompass && Compass3DMod.getShowFramedRecoveryArrows()) ||
        isNetheriteCompass || isOreCompass || isDarkCompass || isPortalCompass))
      return;

    ItemStack displayItem = Utils.getDisplayItem(heldItem, itemFrameEntity.getBlockY(), config);
    if (displayItem == null)
      return;

    ItemRenderer renderer = client.getItemRenderer();

    matrixStack.push();
    // This snippet is copied/edited from ItemFrameEntityRenderer.render()
    matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F - itemFrameEntity.getYaw()));

    if (itemFrameEntity.isInvisible()) {
      matrixStack.translate(0.0F, 0.0F, 0.5F);
    } else {
      matrixStack.translate(0.0F, 0.0F, 0.4375F);
    }

    matrixStack.translate(.01f, .01f, .01f);

    matrixStack.scale(0.5F, 0.5F, 0.5F);
    matrixStack.scale(config.framedScale, config.framedScale, config.framedScale);
    matrixStack.translate((((float) config.framedTranslateX / -16)), (((float) config.framedTranslateY / 16)),
        (((float) config.framedTranslateZ / -16)));
    renderer.renderItem(displayItem, ModelTransformationMode.FIXED, this.getLight(itemFrameEntity, 15728880, i),
        OverlayTexture.DEFAULT_UV, matrixStack, vertexConsumerProvider, itemFrameEntity.getWorld(),
        itemFrameEntity.getId());

    matrixStack.pop();

  }
}
