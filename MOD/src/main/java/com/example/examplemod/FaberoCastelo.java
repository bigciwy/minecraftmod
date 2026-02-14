package com.example.examplemod;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.nbt.CompoundTag;

public class FaberoCastelo extends Mob {
    private int explosionCooldown = 0;
    private static final int EXPLOSION_TIMER = 200; // Explode after 10 seconds
    private boolean hasExploded = false;

    public FaberoCastelo(EntityType<? extends FaberoCastelo> entityType, Level level) {
        super(entityType, level);
        this.maxUpStep = 0.5f;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1.5, true));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void tick() {
        super.tick();
        
        if (!hasExploded) {
            explosionCooldown++;
            
            // Check if close to player
            Player nearestPlayer = this.level().getNearestPlayer(this.getX(), this.getY(), this.getZ(), 2.0, false);
            
            if (explosionCooldown >= EXPLOSION_TIMER || nearestPlayer != null) {
                explode();
            }
        }
    }

    private void explode() {
        if (!this.level().isClientSide) {
            hasExploded = true;
            
            // Damage nearby players
            Player nearestPlayer = this.level().getNearestPlayer(this.getX(), this.getY(), this.getZ(), 5.0, false);
            if (nearestPlayer != null) {
                nearestPlayer.hurt(this.damageSources().mobAttack(this), 5.0f);
            }
            
            // Remove entity
            this.remove(net.minecraft.world.entity.Entity.RemovalReason.KILLED);
        }
    }

    @Override
    public int getMaxHealth() {
        return 5;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("ExplosionCooldown", explosionCooldown);
        tag.putBoolean("HasExploded", hasExploded);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("ExplosionCooldown")) explosionCooldown = tag.getInt("ExplosionCooldown");
        if (tag.contains("HasExploded")) hasExploded = tag.getBoolean("HasExploded");
    }
}
