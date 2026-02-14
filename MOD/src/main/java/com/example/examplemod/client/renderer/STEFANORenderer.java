package com.example.examplemod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.resources.ResourceLocation;
import com.example.examplemod.ExampleMod;
import com.example.examplemod.STEFANO;

public class STEFANORenderer extends MobRenderer<STEFANO, HumanoidModel<STEFANO>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ExampleMod.MOD_ID, "textures/entity/stefano.png");

    public STEFANORenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(STEFANO entity) {
        return TEXTURE;
    }

    @Override
    protected void scale(STEFANO entity, PoseStack poseStack, float partialTickTime) {
        poseStack.scale(0.8f, 0.7f, 0.8f); // Short with big hair effect via scaling
    }
}
