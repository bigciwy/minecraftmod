package com.example.examplemod;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.nbt.CompoundTag;

public class ElixirGolem extends Mob {
    private int lifespan = 0;
    private static final int MAX_LIFESPAN = 600; // 30 seconds

    public ElixirGolem(EntityType<? extends ElixirGolem> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 0.5, true)); // Very slow speed
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void tick() {
        super.tick();
        
        lifespan++;
        
        // Despawn after 30 seconds
        if (lifespan >= MAX_LIFESPAN && !this.level().isClientSide) {
            this.remove(net.minecraft.world.entity.Entity.RemovalReason.DISCARDED);
        }
    }

    @Override
    public int getMaxHealth() {
        return 999; // Cannot be defeated
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Lifespan", lifespan);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Lifespan")) lifespan = tag.getInt("Lifespan");
    }
}
