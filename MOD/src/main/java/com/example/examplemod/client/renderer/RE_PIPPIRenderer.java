package com.example.examplemod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.resources.ResourceLocation;
import com.example.examplemod.ExampleMod;
import com.example.examplemod.RE_PIPPI;

public class RE_PIPPIRenderer extends MobRenderer<RE_PIPPI, HumanoidModel<RE_PIPPI>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ExampleMod.MOD_ID, "textures/entity/re_pippi.png");

    public RE_PIPPIRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(RE_PIPPI entity) {
        return TEXTURE;
    }

    @Override
    protected void scale(RE_PIPPI entity, PoseStack poseStack, float partialTickTime) {
        poseStack.scale(1.0f, 1.0f, 1.0f);
    }
}
