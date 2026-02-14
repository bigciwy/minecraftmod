package com.example.examplemod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.resources.ResourceLocation;
import com.example.examplemod.ExampleMod;
import com.example.examplemod.Aldo;

public class AldoRenderer extends MobRenderer<Aldo, HumanoidModel<Aldo>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ExampleMod.MOD_ID, "textures/entity/aldo.png");

    public AldoRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(Aldo entity) {
        return TEXTURE;
    }

    @Override
    protected void scale(Aldo entity, PoseStack poseStack, float partialTickTime) {
        poseStack.scale(0.9f, 1.0f, 0.9f);
    }
}
