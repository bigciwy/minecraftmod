package com.example.examplemod;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.nbt.CompoundTag;

public class Zamir extends Mob {
    private int attackCooldown = 0;
    private int currentAttack = 0;
    private static final int ATTACK_COOLDOWN = 60; // 3 seconds between attacks
    private int detectionRange = 32;
    private boolean inCombat = false;

    public Zamir(EntityType<? extends Zamir> entityType, Level level) {
        super(entityType, level);
        this.maxUpStep = 1.5f;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1.2, true));
        this.goalSelector.addGoal(1, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, true) {
            @Override
            public boolean canUse() {
                Player player = Zamir.this.level().getNearestPlayer(Zamir.this.getX(), Zamir.this.getY(), Zamir.this.getZ(), 
                    detectionRange, false);
                if (player != null) {
                    inCombat = true;
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void tick() {
        super.tick();
        
        if (!this.level().isClientSide && inCombat && this.getTarget() != null) {
            if (attackCooldown <= 0) {
                performAttack(this.getTarget());
                attackCooldown = ATTACK_COOLDOWN;
            } else {
                attackCooldown--;
            }
        }
    }

    private void performAttack(Player player) {
        currentAttack = (currentAttack + 1) % 3;
        
        switch (currentAttack) {
            case 0: // Charge attack - ride towards player
                chargeAttack(player);
                break;
            case 1: // Throw bombs
                throwBombs(player);
                break;
            case 2: // Create knockback aura
                knockbackAura(player);
                break;
        }
    }

    private void chargeAttack(Player player) {
        // Move towards player rapidly and deal damage
        double dx = player.getX() - this.getX();
        double dz = player.getZ() - this.getZ();
        double distance = Math.sqrt(dx * dx + dz * dz);
        
        if (distance > 0) {
            this.setDeltaMovement(
                (dx / distance) * 1.5,
                0.3,
                (dz / distance) * 1.5
            );
        }
        
        if (this.distanceTo(player) < 2.0) {
            player.hurt(this.damageSources().mobAttack(this), 8.0f);
        }
    }

    private void throwBombs(Player player) {
        // Spawn explosive projectiles (simplified as damage)
        if (this.distanceTo(player) < 20.0) {
            player.hurt(this.damageSources().mobAttack(this), 4.0f);
        }
    }

    private void knockbackAura(Player player) {
        // Create knockback effect
        double dx = player.getX() - this.getX();
        double dz = player.getZ() - this.getZ();
        double distance = Math.sqrt(dx * dx + dz * dz);
        
        if (distance < 12.0) {
            player.knockBack(1.0, dx / distance, dz / distance);
            player.hurt(this.damageSources().mobAttack(this), 3.0f);
        }
    }

    @Override
    public int getMaxHealth() {
        return 40;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("AttackCooldown", attackCooldown);
        tag.putInt("CurrentAttack", currentAttack);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("AttackCooldown")) {
            attackCooldown = tag.getInt("AttackCooldown");
        }
        if (tag.contains("CurrentAttack")) {
            currentAttack = tag.getInt("CurrentAttack");
        }
    }
}
