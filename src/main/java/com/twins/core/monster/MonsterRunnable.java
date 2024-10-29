package com.twins.core.monster;

import com.twins.core.CorePlugin;
import com.twins.core.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class MonsterRunnable extends BukkitRunnable {

    private final EntityType[] types = {EntityType.COW, EntityType.PIG, EntityType.CHICKEN};

    public static final Set<UUID> ANIMALS = new HashSet<>();

    public static final Set<UUID> WAVE_ANIMALS = new HashSet<>();

    public static int START_X;

    public static int END_X;

    public static int START_Z;

    public static int END_Z;

    private int waveCounter = 0;

    @Override
    public void run() {

        if (START_X == 0) {

            START_X = -1000 + CorePlugin.INSTANCE.getRandom().nextInt(1750);
            END_X = START_X + 32;

            START_Z = START_X;
            END_Z = START_Z + 32;

        }

        System.out.println("startx: " + START_X);
        System.out.println("startz: " + START_Z);
        System.out.println("endx: " + END_X);
        System.out.println("endz: " + END_Z);

        Chunk[] chunks = CorePlugin.INSTANCE.getRustWorld().getLoadedChunks();
        Chunk chunk = chunks[CorePlugin.INSTANCE.getRandom().nextInt(chunks.length)];

        Location location = LocationUtils.getRandomLocationInChunk(chunk);

        if (location == null) {
            return;
        }

        if (START_X != 0) {

            if (LocationUtils.isLocationInsideArea(location, START_X, END_X, START_Z, END_Z)) {

                if (WAVE_ANIMALS.size() >= 100) {
                    return;
                }

                spawnAnimal(location, false);

            } else {

                if (ANIMALS.size() >= 100) {
                    return;
                }

                spawnAnimal(location, true);

            }

        }

        if (waveCounter == 10) {
            START_X = 0; // change the wave area on next @run call method
            waveCounter = 0;
        } else {
            waveCounter++;
        }

    }

    private void spawnAnimal(Location location, boolean isNotWave) {

        if (isNotWave) {

            int count = 0;

            for (Entity entity : location.getChunk().getEntities()) {
                if (entity.getType() == EntityType.PIG || entity.getType() == EntityType.CHICKEN || entity.getType() == EntityType.COW) {
                    count++;
                }
            }

            if (count > 1) {
                return;
            }

            LivingEntity entity = (LivingEntity) CorePlugin.INSTANCE.getRustWorld().spawnEntity(location, types[CorePlugin.INSTANCE.getRandom().nextInt(3)]);
            entity.setRemoveWhenFarAway(false);
            ANIMALS.add(entity.getUniqueId());
            System.out.println("mobSpawn: " + location + "; type: " + entity.getType());

        } else {

            int count = 0;

            for (Entity entity : location.getChunk().getEntities()) {
                if (entity.getType() == EntityType.PIG || entity.getType() == EntityType.CHICKEN || entity.getType() == EntityType.COW) {
                    count++;
                }
            }

            if (count > 1) {
                return;
            }

            LivingEntity entity = (LivingEntity) CorePlugin.INSTANCE.getRustWorld().spawnEntity(location, types[CorePlugin.INSTANCE.getRandom().nextInt(3)]);
            entity.setRemoveWhenFarAway(false);
            WAVE_ANIMALS.add(entity.getUniqueId());
            System.out.println("mobSpawn wave: " + location + "; type: " + entity.getType());

        }

    }

}
