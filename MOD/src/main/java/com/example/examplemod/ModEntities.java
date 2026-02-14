package com.example.examplemod;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ExampleMod.MOD_ID);

    // Quest Giver
    public static final RegistryObject<EntityType<RE_PIPPI>> RE_PIPPI = ENTITIES.register("re_pippi",
            () -> EntityType.Builder.of(RE_PIPPI::new, MobCategory.CREATURE)
                    .sized(0.6f, 1.8f)
                    .build("re_pippi"));

    // Bosses
    public static final RegistryObject<EntityType<Zamir>> ZAMIR = ENTITIES.register("zamir",
            () -> EntityType.Builder.of(Zamir::new, MobCategory.MONSTER)
                    .sized(0.8f, 2.5f)
                    .build("zamir"));

    public static final RegistryObject<EntityType<COOOLTIIIII>> COOOLTIIIII = ENTITIES.register("coooltiiiii",
            () -> EntityType.Builder.of(COOOLTIIIII::new, MobCategory.MONSTER)
                    .sized(1.2f, 1.3f)
                    .build("coooltiiiii"));

    public static final RegistryObject<EntityType<Candots>> CANDOTS = ENTITIES.register("candots",
            () -> EntityType.Builder.of(Candots::new, MobCategory.MONSTER)
                    .sized(0.6f, 1.8f)
                    .build("candots"));

    public static final RegistryObject<EntityType<PALLINA>> PALLINA = ENTITIES.register("pallina",
            () -> EntityType.Builder.of(PALLINA::new, MobCategory.MONSTER)
                    .sized(0.6f, 1.8f)
                    .build("pallina"));

    public static final RegistryObject<EntityType<Aldo>> ALDO = ENTITIES.register("aldo",
            () -> EntityType.Builder.of(Aldo::new, MobCategory.MONSTER)
                    .sized(0.55f, 1.7f)
                    .build("aldo"));

    public static final RegistryObject<EntityType<STEFANO>> STEFANO = ENTITIES.register("stefano",
            () -> EntityType.Builder.of(STEFANO::new, MobCategory.MONSTER)
                    .sized(0.6f, 1.3f)
                    .build("stefano"));

    // Helper Entities
    public static final RegistryObject<EntityType<FaberoCastelo>> FABERO_CASTELO = ENTITIES.register("fabero_castelo",
            () -> EntityType.Builder.of(FaberoCastelo::new, MobCategory.MONSTER)
                    .sized(0.5f, 0.9f)
                    .build("fabero_castelo"));

    public static final RegistryObject<EntityType<ElixirGolem>> ELIXIR_GOLEM = ENTITIES.register("elixir_golem",
            () -> EntityType.Builder.of(ElixirGolem::new, MobCategory.CREATURE)
                    .sized(0.4f, 0.6f)
                    .build("elixir_golem"));

    public static final RegistryObject<EntityType<Nonegro>> NONEGRO = ENTITIES.register("nonegro",
            () -> EntityType.Builder.of(Nonegro::new, MobCategory.MONSTER)
                    .sized(0.6f, 1.95f)
                    .build("nonegro"));

    // Legacy custom entities
    public static final RegistryObject<EntityType<CustomNPC>> CUSTOM_NPC = ENTITIES.register("custom_npc",
            () -> EntityType.Builder.of(CustomNPC::new, MobCategory.CREATURE)
                    .sized(0.6f, 1.8f)
                    .build("custom_npc"));

    public static final RegistryObject<EntityType<CustomMob>> CUSTOM_MOB = ENTITIES.register("custom_mob",
            () -> EntityType.Builder.of(CustomMob::new, MobCategory.MONSTER)
                    .sized(0.8f, 2.0f)
                    .build("custom_mob"));

    @Mod.EventBusSubscriber(modid = ExampleMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Events {
        @SubscribeEvent
        public static void onCommonSetup(FMLCommonSetupEvent event) {
            // Perform any entity setup here if needed
        }
    }
}
