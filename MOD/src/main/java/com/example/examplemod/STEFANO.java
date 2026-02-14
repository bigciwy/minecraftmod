package com.example.examplemod;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;

public class STEFANO extends Mob {
    private int attackCooldown = 0;
    private int currentPhase = 1; // 1, 2, or 3
    private int phaseTransitionTime = 400; // 20 seconds per phase
    private int currentAttack = 0;
    private static final int ATTACK_COOLDOWN = 60;
    private int nonNegroSpawnCount = 0;
    private int healthThreshold1 = 60; // 2/3 health
    private int healthThreshold2 = 30; // 1/3 health

    public STEFANO(EntityType<? extends STEFANO> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(1, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void tick() {
        super.tick();
        
        // Update phase based on health
        if (this.getHealth() <= healthThreshold2) {
            currentPhase = 3;
        } else if (this.getHealth() <= healthThreshold1) {
            currentPhase = 2;
        }
        
        if (!this.level().isClientSide && this.getTarget() != null) {
            if (attackCooldown <= 0) {
                performPhaseAttack(this.getTarget());
                attackCooldown = ATTACK_COOLDOWN;
            } else {
                attackCooldown--;
            }
        }
    }

    private void performPhaseAttack(Player player) {
        switch (currentPhase) {
            case 1:
                phase1Attack(player);
                break;
            case 2:
                phase2Attack(player);
                break;
            case 3:
                phase3Attack(player);
                break;
        }
    }

    private void phase1Attack(Player player) {
        // Phase 1: Make random noises that empower player
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 100, 0));
    }

    private void phase2Attack(Player player) {
        currentAttack = (currentAttack + 1) % 3;
        
        switch (currentAttack) {
            case 0: // Slow noises
                slowNoises(player);
                break;
            case 1: // Throw hair balls
                throwHairBalls(player);
                break;
            case 2: // Train station hazard
                trainStationAttack(player);
                break;
        }
    }

    private void phase3Attack(Player player) {
        currentAttack = (currentAttack + 1) % 3;
        
        switch (currentAttack) {
            case 0: // Summon Nonegro zombies
                summonNonegro(player);
                break;
            case 1: // Enhanced slow
                enhancedSlowNoises(player);
                break;
            case 2: // Powerful hair balls
                powerfulHairBalls(player);
                break;
        }
    }

    private void slowNoises(Player player) {
        // Slowness effect
        if (this.distanceTo(player) < 25.0) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 150, 1));
        }
    }

    private void throwHairBalls(Player player) {
        // Stun for 3 seconds
        if (this.distanceTo(player) < 20.0) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 4));
        }
    }

    private void trainStationAttack(Player player) {
        // Deal damage (simulating train damage)
        if (this.distanceTo(player) < 30.0) {
            player.hurt(this.damageSources().mobAttack(this), 4.0f);
        }
    }

    private void summonNonegro(Player player) {
        // Summon zombie-like creatures
        if (nonNegroSpawnCount < 4) {
            Nonegro nonegro = new Nonegro(ModEntities.NONEGRO.get(), this.level());
            nonegro.moveTo(
                this.getX() + (Math.random() - 0.5) * 6,
                this.getY(),
                this.getZ() + (Math.random() - 0.5) * 6
            );
            nonegro.setTarget(player);
            this.level().addFreshEntity(nonegro);
            nonNegroSpawnCount++;
        }
    }

    private void enhancedSlowNoises(Player player) {
        // Stronger slowness
        if (this.distanceTo(player) < 25.0) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 2));
        }
    }

    private void powerfulHairBalls(Player player) {
        // Deal damage and stun
        if (this.distanceTo(player) < 20.0) {
            player.hurt(this.damageSources().mobAttack(this), 6.0f);
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 4));
        }
    }

    @Override
    public int getMaxHealth() {
        return 90;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("CurrentPhase", currentPhase);
        tag.putInt("AttackCooldown", attackCooldown);
        tag.putInt("NonNegroSpawnCount", nonNegroSpawnCount);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("CurrentPhase")) currentPhase = tag.getInt("CurrentPhase");
        if (tag.contains("AttackCooldown")) attackCooldown = tag.getInt("AttackCooldown");
        if (tag.contains("NonNegroSpawnCount")) nonNegroSpawnCount = tag.getInt("NonNegroSpawnCount");
    }
}
