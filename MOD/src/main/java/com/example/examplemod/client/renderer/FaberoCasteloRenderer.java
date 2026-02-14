package com.example.examplemod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.resources.ResourceLocation;
import com.example.examplemod.ExampleMod;
import com.example.examplemod.FaberoCastelo;

public class FaberoCasteloRenderer extends MobRenderer<FaberoCastelo, HumanoidModel<FaberoCastelo>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ExampleMod.MOD_ID, "textures/entity/fabero_castelo.png");

    public FaberoCasteloRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.4f);
    }

    @Override
    public ResourceLocation getTextureLocation(FaberoCastelo entity) {
        return TEXTURE;
    }

    @Override
    protected void scale(FaberoCastelo entity, PoseStack poseStack, float partialTickTime) {
        poseStack.scale(0.6f, 0.8f, 0.6f); // Smaller summoned entity
    }
}
