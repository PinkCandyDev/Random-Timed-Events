package me.pinkcandy.randomTimedEvents;

import me.pinkcandy.randomTimedEvents.Timers.CountdownTimer;
import org.bukkit.plugin.java.JavaPlugin;

public final class RandomTimedEvents extends JavaPlugin {

    private CountdownTimer countdownTimer;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        countdownTimer = new CountdownTimer(this);
        countdownTimer.start();
        this.getCommand("rte").setExecutor(new CommandsHandler(this));
    }

    public CountdownTimer getCountdownTimer() {
        return countdownTimer;
    }

    @Override
    public void onDisable() {

    }
}
