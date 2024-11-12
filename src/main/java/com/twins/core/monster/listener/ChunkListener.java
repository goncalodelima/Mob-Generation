package com.twins.core.monster.listener;

import com.twins.core.monster.MonsterRunnable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;

public class ChunkListener implements Listener {

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event) {

        for (Entity entity : event.getChunk().getEntities()) {

            if (entity.getType() == EntityType.PIG || entity.getType() == EntityType.CHICKEN || entity.getType() == EntityType.COW) {

                entity.remove();

                MonsterRunnable.ANIMALS.remove(entity.getUniqueId());
                MonsterRunnable.WAVE_ANIMALS.remove(entity.getUniqueId());

            }

        }

    }

}
