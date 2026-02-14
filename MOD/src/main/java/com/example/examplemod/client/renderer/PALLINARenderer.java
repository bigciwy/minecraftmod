package com.example.examplemod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.resources.ResourceLocation;
import com.example.examplemod.ExampleMod;
import com.example.examplemod.PALLINA;

public class PALLINARenderer extends MobRenderer<PALLINA, HumanoidModel<PALLINA>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ExampleMod.MOD_ID, "textures/entity/pallina.png");

    public PALLINARenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(PALLINA entity) {
        return TEXTURE;
    }

    @Override
    protected void scale(PALLINA entity, PoseStack poseStack, float partialTickTime) {
        poseStack.scale(1.0f, 1.0f, 1.0f);
    }
}
