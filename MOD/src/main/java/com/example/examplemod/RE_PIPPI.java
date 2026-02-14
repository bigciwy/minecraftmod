package com.example.examplemod;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

public class RE_PIPPI extends PathfinderMob {
    private int questGivenCooldown = 0;
    private static final int QUEST_COOLDOWN = 100; // 5 seconds

    public RE_PIPPI(EntityType<? extends RE_PIPPI> entityType, Level level) {
        super(entityType, level);
        this.setNoAi(true); // Ghost doesn't move
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new LookAtPlayerGoal(this, Player.class, 16.0f));
    }

    @Override
    public void tick() {
        super.tick();
        if (questGivenCooldown > 0) {
            questGivenCooldown--;
        }
    }

    @Override
    public void interactWithPlayer(Player player) {
        if (this.level().isClientSide) return;
        
        if (questGivenCooldown <= 0) {
            player.displayClientMessage(
                Component.literal("§d§lRE_PIPPI:§r My friends are scattered across the world... Find them all and bring them back to me!"),
                false
            );
            // Give quest map (would be implemented with quest system)
            giveQuestMap(player);
            questGivenCooldown = QUEST_COOLDOWN;
        }
    }

    private void giveQuestMap(Player player) {
        // This would interact with a quest system
        // For now, just send a message to the player
        player.displayClientMessage(
            Component.literal("§5You received a quest map to find all NPCs!"),
            true
        );
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("QuestCooldown", questGivenCooldown);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("QuestCooldown")) {
            questGivenCooldown = tag.getInt("QuestCooldown");
        }
    }
}
