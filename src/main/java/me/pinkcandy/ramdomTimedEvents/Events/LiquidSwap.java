package me.pinkcandy.ramdomTimedEvents.Events;

import me.pinkcandy.ramdomTimedEvents.Managers.EventInterface;
import me.pinkcandy.ramdomTimedEvents.Timers.EventTimer;
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

    private final JavaPlugin plugin;
    private boolean isActive = false;
    private BukkitTask waterCheckerTask;

    public LiquidSwap(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void Start(int time) {
        Bukkit.getLogger().info("Event LiquidSwap startuje na " + time + " sekund.");
        this.isActive = true;

        // Rejestracja eventów
        Bukkit.getPluginManager().registerEvents(this, plugin);

        // Odpal Timer i po jego zakończeniu wywołaj Stop
        new EventTimer(plugin, this, time);

        // Uruchamiamy sprawdzanie graczy stojących w wodzie
        startWaterChecker();
    }

    @Override
    public String getName() {
        return "LiquidSwap";
    }

    @Override
    public void Stop() {
        this.isActive = false;
        HandlerList.unregisterAll(this);
        if (waterCheckerTask != null) {
            waterCheckerTask.cancel();
            waterCheckerTask = null;
        }
        Bukkit.getLogger().info("Event LiquidSwap zakończony.");
    }

    /**
     * Podpala gracza i zadaje obrażenia, jeśli znajduje się w wodzie.
     */
    private void handleWaterPlayer(Player player) {
        Block block = player.getLocation().getBlock();

        if (block.getType() == Material.WATER || block.getType() == Material.BUBBLE_COLUMN) {
            if (player.getFireTicks() <= 0) {
                player.setFireTicks(120); // ustawia 3 sekundy ognia (60 ticków)
            }
            player.damage(2.0); // Obrażenia jak lawa
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
}