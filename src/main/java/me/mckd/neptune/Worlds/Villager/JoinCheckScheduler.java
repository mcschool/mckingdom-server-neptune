package me.mckd.neptune.Worlds.Villager;

import me.mckd.neptune.Neptune;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class JoinCheckScheduler extends BukkitRunnable {

    Neptune plugin;
    World world;
    int count = 20;

    public JoinCheckScheduler (Neptune plugin, World world) {
        this.plugin = plugin;
        this.world = world;
    }

    @Override
    public void run() {
        List<Player> players= this.world.getPlayers();
        for (Player p:players){
            p.sendMessage(String.valueOf(this.count));
        }
        if (this.count< 1){
            int playerCount = players.size();
            if (playerCount<1) {
                for (int i = 0; i < players.size(); i++) {
                    Location location;
                    Player p = players.get(0);
                    location = new Location(this.world, -822, 201, -263);
                    this.setHelmet(p, Color.RED);
                    p.teleport(location);
                }
            }
        } else {
            for (Player p : players) {
                p.sendMessage("Waiting for players");
                p.sendMessage("Patience please");
            }

        }
        this.cancel();
    }

    public void setHelmet(Player player, Color color) {
        // 色付きのヘルメットを装備するプログラム
    }

}
