package me.pinkcandy.randomTimedEvents;

import me.pinkcandy.randomTimedEvents.Timers.CountdownTimer;
import org.bukkit.plugin.java.JavaPlugin;

public final class RamdomTimedEvents extends JavaPlugin {

    private final JavaPlugin plugin;

    public RamdomTimedEvents() {
        this.plugin = this;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.getCommand("rta").setExecutor(new CommandsHandler(plugin));
        CountdownTimer countdownTimer = new CountdownTimer(this);
        countdownTimer.start();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
