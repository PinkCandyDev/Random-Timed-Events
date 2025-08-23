package me.pinkcandy.randomTimedEvents.Events;

import me.pinkcandy.randomTimedEvents.Managers.EventInterface;
import me.pinkcandy.randomTimedEvents.RandomTimedEvents;
import me.pinkcandy.randomTimedEvents.Timers.EventTimer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

public class WallHack implements EventInterface, Listener {

    private final RandomTimedEvents plugin;
    private BukkitTask glowingTask;

    public WallHack(RandomTimedEvents plugin) {
        this.plugin = plugin;
    }

    @Override
    public void Start(int time) {
        new EventTimer(plugin, this, time);
        glowingTask = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (World world : Bukkit.getWorlds()) {
                for (Entity entity : world.getEntities()) {
                    if (entity instanceof LivingEntity livingEntity) {
                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 40, 0, false, false));
                    }
                }
            }
        }, 0L, 20L);
    }

    @Override
    public void Stop() {
        if (glowingTask != null) {
            glowingTask.cancel();
            glowingTask = null;
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.removePotionEffect(PotionEffectType.GLOWING);
        }
    }

    @Override
    public String getName() {
        return "WallHack";
    }
}
