package me.mckd.neptune.Worlds.ArrowWarp;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static org.bukkit.Bukkit.getWorld;

public class ArrowClearScheduler extends BukkitRunnable {

    public Player player;
    public int count;

    public ArrowClearScheduler(Player player) {
        this.player = player;
        this.count = 10;
    }

    @Override
    public void run() {
        this.count--;
        this.player.sendTitle(String.valueOf(this.count),"",0,20,0);
        if(this.count <= 0) {
            Location location = new Location(Bukkit.getWorld("arrow"), -1546,197,723);
            player.teleport(location);
            player.setGameMode (GameMode.SURVIVAL);
            this.cancel();

        }
    }
}
