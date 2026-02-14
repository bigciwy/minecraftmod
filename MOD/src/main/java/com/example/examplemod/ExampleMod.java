package com.example.examplemod;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.javafxmod.FXModLanguageProvider;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafxmod.FXModLanguageProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraftforge.fml.IModInfoProvider;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.config.IConfigSpec;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.event.lifecycle.FMLSetupEvent;
import net.minecraftforge.fml.javafxmod.FXModLanguageProvider;

@Mod("examplemod")
public class ExampleMod {
    private static final Logger LOGGER = LoggerFactory.getLogger("examplemod");
    public static final String MOD_ID = "examplemod";

    public ExampleMod() {
        IEventBus modEventBus = net.minecraftforge.fml.ModLoadingContext.getInstance().getModEventBus();

        // Register to the mod event bus so that run function get called
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        net.minecraftforge.api.distmarker.OnlyIn(Dist.CLIENT);

        ModEntities.ENTITIES.register(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Common setup event received!");
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("Client setup event received!");
        }
    }
}
