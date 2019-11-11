package me.mckd.neptune.Worlds.Villager;

import me.mckd.neptune.Neptune;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class VillagerWorld implements Listener {

    Neptune plugin;
    String worldName = "villager";

    public VillagerWorld(Neptune plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (!e.getPlayer().getWorld().getName().equals(this.worldName)) {
            return;
        }

        if (player.getLocation().getY() < 180) {
            Location location = new Location(player.getWorld(), -821, 201, -308);
            player.sendTitle("ReSpawned!", "", 20, 40, 20);
            player.teleport(location);
        }
    }

}
