package me.pinkcandy.randomTimedEvents.Events;

import me.pinkcandy.randomTimedEvents.Managers.EventInterface;
import me.pinkcandy.randomTimedEvents.RandomTimedEvents;
import me.pinkcandy.randomTimedEvents.Timers.EventTimer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class LowGravity implements EventInterface {
    private final RandomTimedEvents plugin;
    private BukkitTask gravityTask;

    public LowGravity(RandomTimedEvents plugin) {
        this.plugin = plugin;
    }

    @Override
    public void Start(int time) {
        new EventTimer(plugin, this, time);

        gravityTask = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {

                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, 40, 2, false, false, false));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 0, false, false, false));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 40, 0, false, false, false));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 40, 0, false, false, false));

                Vector velocity = player.getVelocity();
                velocity.setX(velocity.getX() * 1.19);
                velocity.setZ(velocity.getZ() * 1.19);
                player.setVelocity(velocity);

            }
        }, 0L, 2L);
    }

    @Override
    public void Stop() {
        if (gravityTask != null) {
            gravityTask.cancel();
            gravityTask = null;
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.removePotionEffect(PotionEffectType.JUMP_BOOST);
            player.removePotionEffect(PotionEffectType.SPEED);
            player.removePotionEffect(PotionEffectType.SLOWNESS);
            player.removePotionEffect(PotionEffectType.SLOW_FALLING);
        }
    }

    @Override
    public String getName() {
        return "LowGravity";
    }
}
