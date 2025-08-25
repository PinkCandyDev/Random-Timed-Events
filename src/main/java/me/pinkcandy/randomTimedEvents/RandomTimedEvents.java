package me.pinkcandy.randomTimedEvents;

import me.pinkcandy.randomTimedEvents.Timers.CountdownTimer;
import me.pinkcandy.randomTimedEvents.Timers.EventTimer;
import org.bukkit.plugin.java.JavaPlugin;

public final class RandomTimedEvents extends JavaPlugin {

    private CountdownTimer countdownTimer;
    private EventTimer eventTimer;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        eventTimer = new EventTimer(this);
        countdownTimer = new CountdownTimer(this);
        countdownTimer.start();
        this.getCommand("rte").setExecutor(new CommandsHandler(this));
    }

    public CountdownTimer getCountdownTimer() {
        return countdownTimer;
    }

    public EventTimer getEventTimer() {
        return eventTimer;
    }

    @Override
    public void onDisable() {

    }
}
