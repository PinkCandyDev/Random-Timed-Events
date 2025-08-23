package me.pinkcandy.randomTimedEvents.Events;

import me.pinkcandy.randomTimedEvents.Managers.EventInterface;
import me.pinkcandy.randomTimedEvents.RandomTimedEvents;
import me.pinkcandy.randomTimedEvents.Timers.EventTimer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.ThreadLocalRandom;

public class RandomTeleport implements EventInterface {

    private final RandomTimedEvents plugin;
    private int radius;
    private BukkitTask teleportTask;

    public RandomTeleport(RandomTimedEvents plugin) {
        this.plugin = plugin;
    }

    @Override
    public void Start(int time) {
        new EventTimer(plugin, this, time);
        this.radius = plugin.getConfig().getInt("events.RandomTeleport.radius", 16);
        int frequency = plugin.getConfig().getInt("events.RandomTeleport.frequency", 15);

        teleportTask = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                chorusTeleport(player);
            }
        }, 0L, frequency * 20L);
    }

    public void chorusTeleport(Player player) {
        for (int i = 0; i < 16; i++) {
            double rx = player.getLocation().getX() + (ThreadLocalRandom.current().nextDouble() - 0.5D) * radius;
            double ry = player.getLocation().getY() + (ThreadLocalRandom.current().nextInt(16) - 8);
            double rz = player.getLocation().getZ() + (ThreadLocalRandom.current().nextDouble() - 0.5D) * radius;

            Location newLoc = new Location(player.getWorld(), rx, ry, rz);

            if (isSafeLocation(newLoc)) {
                player.teleport(newLoc);
                plugin.getLogger().info("Teleported " + player.getName() + " to " + newLoc);
                player.getWorld().playSound(player.getLocation(), Sound.ITEM_CHORUS_FRUIT_TELEPORT, 1.0F, 1.0F);
                break;
            } else {
                boolean teleported = false;
                int startY = newLoc.getBlockY();
                for (int y = startY; y < startY + radius; y++) {
                    Location checkLoc = newLoc.clone();
                    checkLoc.setY(y);
                    if (isSafeLocation(checkLoc)) {
                        player.teleport(checkLoc);
                        plugin.getLogger().info("Teleported " + player.getName() + " to " + newLoc);
                        player.getWorld().playSound(checkLoc, Sound.ITEM_CHORUS_FRUIT_TELEPORT, 1.0F, 1.0F);
                        teleported = true;
                        break;
                    }
                }
                if (teleported)
                {
                    break;
                }
            }
        }
    }

    private boolean isSafeLocation(Location loc) {
        return loc.getBlock().getRelative(BlockFace.DOWN).getType().isSolid() && loc.getBlock().getType().isAir() && loc.getBlock().getRelative(BlockFace.UP).getType().isAir();
    }

    @Override
    public void Stop() {
        if (teleportTask != null) {
            teleportTask.cancel();
            teleportTask = null;
        }
    }

    @Override
    public String getName() {
        return "RandomTeleport";
    }
}
