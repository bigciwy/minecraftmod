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

public class PALLINA extends Mob {
    private int attackCooldown = 0;
    private int currentAttack = 0;
    private static final int ATTACK_COOLDOWN = 75;
    private int faberoSpawnCount = 0;
    private int lastAttackTime = 0;

    public PALLINA(EntityType<? extends PALLINA> entityType, Level level) {
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
        
        if (!this.level().isClientSide && this.getTarget() != null) {
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
            case 0: // Kick ball (boomerang)
                kickBall(player);
                break;
            case 1: // Summon Fabero
                summonFabero(player);
                break;
            case 2: // Bicycle kick (stun)
                bicycleKick(player);
                break;
        }
    }

    private void kickBall(Player player) {
        // Kick ball that returns like a boomerang
        if (this.distanceTo(player) < 20.0) {
            player.hurt(this.damageSources().mobAttack(this), 6.0f);
        }
    }

    private void summonFabero(Player player) {
        // Summon Fabero Castelo that explodes
        if (faberoSpawnCount < 2) {
            FaberoCastelo fabero = new FaberoCastelo(ModEntities.FABERO_CASTELO.get(), this.level());
            fabero.moveTo(
                this.getX() + (Math.random() - 0.5) * 4,
                this.getY(),
                this.getZ() + (Math.random() - 0.5) * 4
            );
            fabero.setTarget(player);
            this.level().addFreshEntity(fabero);
            faberoSpawnCount++;
        }
    }

    private void bicycleKick(Player player) {
        // Stun the player for 5-10 seconds
        if (this.distanceTo(player) < 3.0) {
            int stunDuration = 100 + this.random.nextInt(100); // 5-10 seconds
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, stunDuration, 4));
        }
    }

    @Override
    public int getMaxHealth() {
        return 32;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("AttackCooldown", attackCooldown);
        tag.putInt("FaberoSpawnCount", faberoSpawnCount);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("AttackCooldown")) attackCooldown = tag.getInt("AttackCooldown");
        if (tag.contains("FaberoSpawnCount")) faberoSpawnCount = tag.getInt("FaberoSpawnCount");
    }
}
