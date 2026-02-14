package com.example.examplemod;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.nbt.CompoundTag;

public class COOOLTIIIII extends Mob {
    private int attackCooldown = 0;
    private int currentAttack = 0;
    private static final int ATTACK_COOLDOWN = 80; // 4 seconds
    private int sleepCooldown = 0;
    private int golemSpawnCount = 0;
    private boolean inCombat = false;

    public COOOLTIIIII(EntityType<? extends COOOLTIIIII> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(1, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, true) {
            @Override
            public boolean canUse() {
                Player player = COOOLTIIIII.this.level().getNearestPlayer(COOOLTIIIII.this.getX(), COOOLTIIIII.this.getY(), 
                    COOOLTIIIII.this.getZ(), 32, false);
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
        
        if (sleepCooldown > 0) {
            sleepCooldown--;
            this.setNoAi(true);
        } else {
            this.setNoAi(false);
        }
        
        if (!this.level().isClientSide && inCombat && this.getTarget() != null && sleepCooldown <= 0) {
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
            case 0: // Jump and smash
                jumpSmash(player);
                break;
            case 1: // Spawn elixir golems
                spawnElixirGolems(player);
                break;
            case 2: // Rolling attack, then sleep
                rollingAttack(player);
                break;
        }
    }

    private void jumpSmash(Player player) {
        // Jump towards player and deal damage
        this.setDeltaMovement(
            (player.getX() - this.getX()) * 0.3,
            1.0,
            (player.getZ() - this.getZ()) * 0.3
        );
        
        if (this.distanceTo(player) < 3.0) {
            player.hurt(this.damageSources().mobAttack(this), 7.0f);
        }
    }

    private void spawnElixirGolems(Player player) {
        // Spawn 2-3 elixir golems that move slowly towards player
        if (golemSpawnCount < 3) {
            ElixirGolem golem = new ElixirGolem(ModEntities.ELIXIR_GOLEM.get(), this.level());
            golem.moveTo(
                this.getX() + (Math.random() - 0.5) * 5,
                this.getY(),
                this.getZ() + (Math.random() - 0.5) * 5
            );
            golem.setTarget(player);
            this.level().addFreshEntity(golem);
            golemSpawnCount++;
        }
    }

    private void rollingAttack(Player player) {
        // Roll towards player and deal damage
        double dx = player.getX() - this.getX();
        double dz = player.getZ() - this.getZ();
        double distance = Math.sqrt(dx * dx + dz * dz);
        
        if (distance > 0) {
            this.setDeltaMovement(
                (dx / distance) * 1.8,
                0.1,
                (dz / distance) * 1.8
            );
        }
        
        if (this.distanceTo(player) < 2.5) {
            player.hurt(this.damageSources().mobAttack(this), 7.0f);
            sleepCooldown = 100; // Sleep for 5 seconds
        }
    }

    @Override
    public int getMaxHealth() {
        return 35;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("AttackCooldown", attackCooldown);
        tag.putInt("CurrentAttack", currentAttack);
        tag.putInt("SleepCooldown", sleepCooldown);
        tag.putInt("GolemSpawnCount", golemSpawnCount);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("AttackCooldown")) attackCooldown = tag.getInt("AttackCooldown");
        if (tag.contains("CurrentAttack")) currentAttack = tag.getInt("CurrentAttack");
        if (tag.contains("SleepCooldown")) sleepCooldown = tag.getInt("SleepCooldown");
        if (tag.contains("GolemSpawnCount")) golemSpawnCount = tag.getInt("GolemSpawnCount");
    }
}
