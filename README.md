# Random Timed Events Plugin

**Version:** 1.0.0  
**Compatible with:** Bukkit/Spigot/Paper 1.14.4–1.21.8

---

## Overview
Random Timed Events Plugin (RTE) automatically triggers fun or challenging events on your Minecraft server at configurable intervals.  
It adds excitement and unpredictability to gameplay while giving server admins full control over which events are active.

---

## Features
- **Random Event System**: automatically triggers events at configurable intervals  
- **BossBar Countdown**: displays time until the next event
![BossBar Example](https://raw.githubusercontent.com/PinkCandyDev/Random-Timed-Events/main/bossbar.png)  
- **Customizable Timers**:
  - `intrivial`: time between the end of one event and the announcement of the next  
  - `announcement`: duration of the boss bar countdown before event starts  
- Fully configurable via `config.yml`

---

## Commands
| Command | Description |
|---------|-------------|
| `/rte timer` | Check remaining time until next event |
| `/rte restart` | Restart the countdown timer |
| `/rte pause` | Pause the countdown timer |
| `/rte resume` | Resume the countdown timer |
| `/rte stop` | Stop the currently running event |
| `/rte start <EventName>` | Force start a specific event immediately |
| `/rte help` | Display a list of available commands |

---

## Random Events
- **ADHDInventory**: shuffles player's inventory when switching items  
- **AdventureTime**: prevents block placing/breaking  
- **BePacifist**: Blocks players from damaging mobs and players
- **DeadGoBoom**: creates configurable explosions on deaths  
- **LiquidSwap**: damages players in water and blocks lava damage  
- **LowGravity**: applies jump boosts and slow falling  
- **MoveOrDie**: damages stationary players  
- **NoJumping**: prevents jumping  
- **NoRegeneration**: disables natural health regen (configurable)  
- **RandomTeleport**: teleports players randomly  
- **WallHack**: grants temporary x-ray vision  
- **YouAreZombie**: applies zombie-like effects

---

## Installation
1. Download the `.jar` file from [GitHub Releases](https://github.com/PinkCandyDev/Random-Timed-Events/releases/tag/Release).  
2. Place the `.jar` in your server's `plugins` folder.  
3. Start or restart your server.  
4. Configure timers and events in `config.yml` as desired.

---

## Notes on Versions
- **Old line (1.14.4–1.20.4):** `rte-1.0.0-1.14.4+.jar`  
- **New line (1.20.5–1.21.8):** `rte-1.0.0-1.20.5+.jar`  
- Ensure you use the correct `.jar` for your server version.  

---

## License
This plugin is licensed under **GNU General Public License v3.0 only** (GPL v3.0).  
You are free to use, modify, and distribute it under the same license.
