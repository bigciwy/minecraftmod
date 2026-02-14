package com.example.examplemod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.resources.ResourceLocation;
import com.example.examplemod.ExampleMod;
import com.example.examplemod.COOOLTIIIII;

public class COOOLTIIIIRenderer extends MobRenderer<COOOLTIIIII, HumanoidModel<COOOLTIIIII>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ExampleMod.MOD_ID, "textures/entity/coooltiiiii.png");

    public COOOLTIIIIRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.6f);
    }

    @Override
    public ResourceLocation getTextureLocation(COOOLTIIIII entity) {
        return TEXTURE;
    }

    @Override
    protected void scale(COOOLTIIIII entity, PoseStack poseStack, float partialTickTime) {
        poseStack.scale(1.5f, 1.1f, 1.5f); // Large and wide
    }
}
