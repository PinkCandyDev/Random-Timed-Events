package me.pinkcandy.randomTimedEvents;

import me.pinkcandy.randomTimedEvents.Timers.CountdownTimer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandsHandler implements CommandExecutor {


    private final RandomTimedEvents plugin;

    public CommandsHandler(RandomTimedEvents plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("rte")) {
            return false;
        }

        if (args.length == 0) {
            return false;
        }

        String action = args[0];
        CountdownTimer countdownTimer = plugin.getCountdownTimer();


        switch (action.toLowerCase()) {
            case "timer":
                int time = countdownTimer.getSecondsLeft();
                boolean isRunning = countdownTimer.getTimerState();
                if (isRunning && time > 0) {
                    sender.sendMessage("§a" + time + " seconds left until the next event starts.");
                }
                else if (!isRunning && time > 0)
                {
                    sender.sendMessage("§eTimer is paused with " + time + " seconds left until the next event starts.");
                }
                if (!isRunning && time <= 0)
                {
                    sender.sendMessage("§eEvent is starting or arleady started.");
                }
                return true;
            case "restart":
                countdownTimer.restart();
                sender.sendMessage("§aCountdown timer restarted.");
                return true;
            case "pause":
                countdownTimer.cancelTask();
                sender.sendMessage("§aCountdown timer paused at " + countdownTimer.getSecondsLeft() + " seconds");
                return true;
            case "resume":
                countdownTimer.resume();
                sender.sendMessage("§aCountdown timer resumed.");
                return true;
            case "stop":


        }

        return false;
    }
}
