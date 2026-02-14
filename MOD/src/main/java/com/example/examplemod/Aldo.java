package com.example.examplemod;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

public class Aldo extends Mob {
    private int hideCooldown = 0;
    private static final int HIDE_DURATION = 600; // 30 seconds
    private boolean isHidden = false;
    private int timesFound = 0;
    private static final int BOSS_ENCOUNTER_ROUNDS = 3;
    private boolean inBattle = false;

    public Aldo(EntityType<? extends Aldo> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, true) {
            @Override
            public boolean canUse() {
                Player player = Aldo.this.level().getNearestPlayer(Aldo.this.getX(), Aldo.this.getY(), 
                    Aldo.this.getZ(), 40, false);
                if (player != null && !inBattle) {
                    inBattle = true;
                    startHiding();
                    return false;
                }
                return false;
            }
        });
    }

    @Override
    public void tick() {
        super.tick();
        
        if (inBattle && !this.level().isClientSide) {
            if (hideCooldown > 0) {
                hideCooldown--;
                
                // Check if player found Aldo
                Player nearestPlayer = this.level().getNearestPlayer(this.getX(), this.getY(), this.getZ(), 3.0, false);
                if (nearestPlayer != null && isHidden) {
                    foundByPlayer(nearestPlayer);
                }
            } else if (hideCooldown <= 0 && isHidden) {
                // Damage player for not finding Aldo in time
                Player nearestPlayer = this.level().getNearestPlayer(this.getX(), this.getY(), this.getZ(), 30.0, false);
                if (nearestPlayer != null) {
                    nearestPlayer.hurt(this.damageSources().mobAttack(this), 5.0f);
                    nearestPlayer.displayClientMessage(
                        Component.literal("§cYou didn't find Aldo in time! You took damage!"),
                        true
                    );
                }
                startHiding();
            }
        }
    }

    private void startHiding() {
        isHidden = true;
        hideCooldown = HIDE_DURATION;
        this.setInvisible(true);
        this.setNoAi(true);
    }

    private void foundByPlayer(Player player) {
        timesFound++;
        this.setInvisible(false);
        this.setNoAi(false);
        isHidden = false;
        
        player.displayClientMessage(
            Component.literal("§aYou found Aldo! (" + timesFound + "/" + BOSS_ENCOUNTER_ROUNDS + ")"),
            true
        );

        if (timesFound >= BOSS_ENCOUNTER_ROUNDS) {
            player.displayClientMessage(
                Component.literal("§6You defeated Aldo!"),
                false
            );
            this.remove(net.minecraft.world.entity.Entity.RemovalReason.KILLED);
        } else {
            hideCooldown = 100; // Wait a bit before hiding again
        }
    }

    @Override
    public int getMaxHealth() {
        return 20;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("HideCooldown", hideCooldown);
        tag.putInt("TimesFound", timesFound);
        tag.putBoolean("IsHidden", isHidden);
        tag.putBoolean("InBattle", inBattle);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("HideCooldown")) hideCooldown = tag.getInt("HideCooldown");
        if (tag.contains("TimesFound")) timesFound = tag.getInt("TimesFound");
        if (tag.contains("IsHidden")) isHidden = tag.getBoolean("IsHidden");
        if (tag.contains("InBattle")) inBattle = tag.getBoolean("InBattle");
    }
}
