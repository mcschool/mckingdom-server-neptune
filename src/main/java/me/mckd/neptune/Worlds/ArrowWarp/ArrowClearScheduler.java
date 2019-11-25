package me.mckd.neptune.Worlds.ArrowWarp;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ArrowClearScheduler extends BukkitRunnable {

    public Player player;
    public int count;

    public ArrowClearScheduler(Player player) {
        this.player = player;
        this.count = 10;
    }

    @Override
    public void run() {
    }
}
