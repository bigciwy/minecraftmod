package com.example.examplemod;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollingGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;

public class CustomNPC extends PathfinderMob {

    public CustomNPC(EntityType<? extends CustomNPC> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new WaterAvoidingRandomStrollingGoal(this, 1.0));
        this.goalSelector.addGoal(1, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8.0f));
    }

    @Override
    public void tick() {
        super.tick();
    }

    public void interactWithPlayer(Player player) {
        // Custom interaction logic
        player.displayClientMessage(net.minecraft.network.chat.Component.literal("Hello from Custom NPC!"), false);
    }
}
