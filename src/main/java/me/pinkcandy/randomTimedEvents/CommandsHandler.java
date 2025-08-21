package me.pinkcandy.randomTimedEvents;

import me.pinkcandy.randomTimedEvents.Timers.CountdownTimer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandsHandler implements CommandExecutor {

    private final JavaPlugin plugin;

    public CommandsHandler(JavaPlugin plugin) {
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
        CountdownTimer countdownTimer = new CountdownTimer(plugin);
        if (action.equals("timer")) {
            sender.sendMessage("§a" + countdownTimer.getSecondsLeft() + " seconds left until the next event starts.");
            return true;
        } else if (action.equals("restart")) {
            countdownTimer.restart();
            sender.sendMessage("§aCountdown timer restarted.");
            return true;
        } else if (action.equals("pause")) {
            countdownTimer.cancelTask();
            sender.sendMessage("§aCountdown timer paused.");
            return true;
        } else if (action.equals("resume")) {
            countdownTimer.resume();
            sender.sendMessage("§aCountdown timer resumed.");
            return true;
        }

        return false;
    }
}
