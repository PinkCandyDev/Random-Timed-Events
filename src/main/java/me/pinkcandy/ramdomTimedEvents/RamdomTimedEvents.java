package me.pinkcandy.ramdomTimedEvents;

import me.pinkcandy.ramdomTimedEvents.Timers.CountdownTimer;
import org.bukkit.plugin.java.JavaPlugin;

public final class RamdomTimedEvents extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        CountdownTimer countdownTimer = new CountdownTimer(this);
        countdownTimer.start();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
