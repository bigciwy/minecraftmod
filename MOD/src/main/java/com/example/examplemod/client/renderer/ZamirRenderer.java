package com.example.examplemod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.resources.ResourceLocation;
import com.example.examplemod.ExampleMod;
import com.example.examplemod.Zamir;

public class ZamirRenderer extends MobRenderer<Zamir, HumanoidModel<Zamir>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ExampleMod.MOD_ID, "textures/entity/zamir.png");

    public ZamirRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(Zamir entity) {
        return TEXTURE;
    }

    @Override
    protected void scale(Zamir entity, PoseStack poseStack, float partialTickTime) {
        poseStack.scale(1.2f, 1.3f, 1.2f); // Make Zamir bigger
    }
}
