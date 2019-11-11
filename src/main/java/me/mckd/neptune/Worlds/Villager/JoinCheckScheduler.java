package me.mckd.neptune.Worlds.Villager;

import me.mckd.neptune.Neptune;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class JoinCheckScheduler extends BukkitRunnable {

    Neptune plugin;
    World world;

    public JoinCheckScheduler (Neptune plugin, World world) {
        this.plugin = plugin;
        this.world = world;
    }

    @Override
    public void run() {
        List<Player> players = this.world.getPlayers();
        int playerCount = players.size();
        if (playerCount > 1) {
            VillagerWorld v = new VillagerWorld(plugin);
            v.camePlayers();
            this.cancel();
        }
        for (Player p: players) {
            p.sendMessage("まだ集まっていません...");
        }
    }
}
