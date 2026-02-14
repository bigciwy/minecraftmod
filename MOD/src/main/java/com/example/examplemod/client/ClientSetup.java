package com.example.examplemod.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraft.client.renderer.entity.EntityRenderers;
import com.example.examplemod.ExampleMod;
import com.example.examplemod.client.renderer.*;

@Mod.EventBusSubscriber(modid = ExampleMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // Client setup events
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.Register event) {
        // Register all entity renderers
        event.register(com.example.examplemod.ModEntities.RE_PIPPI.get(), RE_PIPPIRenderer::new);
        event.register(com.example.examplemod.ModEntities.ZAMIR.get(), ZamirRenderer::new);
        event.register(com.example.examplemod.ModEntities.COOOLTIIIII.get(), COOOLTIIIIRenderer::new);
        event.register(com.example.examplemod.ModEntities.CANDOTS.get(), CandotsRenderer::new);
        event.register(com.example.examplemod.ModEntities.PALLINA.get(), PALLINARenderer::new);
        event.register(com.example.examplemod.ModEntities.ALDO.get(), AldoRenderer::new);
        event.register(com.example.examplemod.ModEntities.STEFANO.get(), STEFANORenderer::new);
        event.register(com.example.examplemod.ModEntities.FABERO_CASTELO.get(), FaberoCasteloRenderer::new);
        event.register(com.example.examplemod.ModEntities.ELIXIR_GOLEM.get(), ElixirGolemRenderer::new);
        event.register(com.example.examplemod.ModEntities.NONEGRO.get(), NonnegroRenderer::new);
        event.register(com.example.examplemod.ModEntities.CUSTOM_NPC.get(), com.example.examplemod.client.renderer.CustomNPCRenderer::new);
        event.register(com.example.examplemod.ModEntities.CUSTOM_MOB.get(), com.example.examplemod.client.renderer.CustomMobRenderer::new);
    }
}
