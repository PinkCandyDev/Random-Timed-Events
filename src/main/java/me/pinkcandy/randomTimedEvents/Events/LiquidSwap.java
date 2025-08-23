package me.pinkcandy.randomTimedEvents.Events;

import me.pinkcandy.randomTimedEvents.Managers.EventInterface;
import me.pinkcandy.randomTimedEvents.RandomTimedEvents;
import me.pinkcandy.randomTimedEvents.Timers.EventTimer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class LiquidSwap implements EventInterface, Listener {

    private final RandomTimedEvents plugin;
    private boolean isActive = false;
    private BukkitTask waterCheckerTask;

    public LiquidSwap(RandomTimedEvents plugin) {
        this.plugin = plugin;
    }

    @Override
    public void Start(int time) {
        this.isActive = true;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        new EventTimer(plugin, this, time);
        startWaterChecker();
    }

    private void handleWaterPlayer(Player player) {
        Block block = player.getLocation().getBlock();

        if (block.getType() == Material.WATER || block.getType() == Material.BUBBLE_COLUMN) {
            if (player.getFireTicks() <= 0) {
                player.setFireTicks(120);
            }
            player.damage(2.0);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!isActive) return;
        handleWaterPlayer(event.getPlayer());
    }

    @EventHandler
    public void onLavaDamage(EntityDamageEvent event) {
        if (!isActive) return;
        if (!(event.getEntity() instanceof Player)) return;

        Player player = (Player) event.getEntity();

        if (event.getCause() == EntityDamageEvent.DamageCause.LAVA) {
            event.setCancelled(true);
            player.setFireTicks(0); // Gasi ogień
        }
    }

    private void startWaterChecker() {
        waterCheckerTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (!isActive) return;
                for (Player player : Bukkit.getOnlinePlayers()) {
                    handleWaterPlayer(player);
                }
            }
        }.runTaskTimer(plugin, 0L, 10L); // co 1 sekundę
    }

    @Override
    public void Stop() {
        this.isActive = false;
        HandlerList.unregisterAll(this);
        if (waterCheckerTask != null) {
            waterCheckerTask.cancel();
            waterCheckerTask = null;
        }
    }

    @Override
    public String getName() {
        return "LiquidSwap";
    }
}