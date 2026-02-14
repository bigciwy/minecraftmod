package com.example.examplemod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.resources.ResourceLocation;
import com.example.examplemod.ExampleMod;
import com.example.examplemod.Candots;

public class CandotsRenderer extends MobRenderer<Candots, HumanoidModel<Candots>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ExampleMod.MOD_ID, "textures/entity/candots.png");

    public CandotsRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(Candots entity) {
        return TEXTURE;
    }

    @Override
    protected void scale(Candots entity, PoseStack poseStack, float partialTickTime) {
        poseStack.scale(0.9f, 1.0f, 0.9f);
    }
}
