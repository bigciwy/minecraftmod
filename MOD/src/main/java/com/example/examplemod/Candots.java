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

public class Candots extends Mob {
    private int attackCooldown = 0;
    private int currentAttack = 0;
    private static final int ATTACK_COOLDOWN = 70;
    private int attackBuffCount = 0;
    private float baseDamage = 5.0f;

    public Candots(EntityType<? extends Candots> entityType, Level level) {
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
            case 0: // Spit smoke (blindness)
                spitSmoke(player);
                break;
            case 1: // Buff attack stat
                buffAttack();
                break;
            case 2: // Enhanced punch based on buff count
                enhancedPunch(player);
                break;
        }
    }

    private void spitSmoke(Player player) {
        // Apply blindness effect to player
        if (this.distanceTo(player) < 15.0) {
            player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 80, 0));
        }
    }

    private void buffAttack() {
        // Increase own damage
        attackBuffCount++;
        this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, Math.min(attackBuffCount - 1, 2)));
    }

    private void enhancedPunch(Player player) {
        // Deal damage based on buff count
        float damage = baseDamage + (attackBuffCount * 2.0f);
        if (this.distanceTo(player) < 2.5) {
            player.hurt(this.damageSources().mobAttack(this), damage);
        }
    }

    @Override
    public int getMaxHealth() {
        return 30;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("AttackCooldown", attackCooldown);
        tag.putInt("AttackBuffCount", attackBuffCount);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("AttackCooldown")) attackCooldown = tag.getInt("AttackCooldown");
        if (tag.contains("AttackBuffCount")) attackBuffCount = tag.getInt("AttackBuffCount");
    }
}
