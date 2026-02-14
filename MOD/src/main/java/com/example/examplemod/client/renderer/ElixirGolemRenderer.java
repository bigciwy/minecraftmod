package com.example.examplemod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.resources.ResourceLocation;
import com.example.examplemod.ExampleMod;
import com.example.examplemod.ElixirGolem;

public class ElixirGolemRenderer extends MobRenderer<ElixirGolem, HumanoidModel<ElixirGolem>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ExampleMod.MOD_ID, "textures/entity/elixir_golem.png");

    public ElixirGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.3f);
    }

    @Override
    public ResourceLocation getTextureLocation(ElixirGolem entity) {
        return TEXTURE;
    }

    @Override
    protected void scale(ElixirGolem entity, PoseStack poseStack, float partialTickTime) {
        poseStack.scale(0.4f, 0.5f, 0.4f); // Very tiny golem
    }
}
