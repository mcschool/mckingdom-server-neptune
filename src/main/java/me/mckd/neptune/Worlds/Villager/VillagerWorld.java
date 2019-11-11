package me.mckd.neptune.Worlds.Villager;

import me.mckd.neptune.Neptune;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class VillagerWorld implements Listener {

    Neptune plugin;
    String worldName = "villager";

    public VillagerWorld(Neptune plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();
        if (!e.getPlayer().getWorld().getName().equals(this.worldName)) {
            return;
        }
        // スケジューラーを開始する
        new SpawnDiamondScheduler(this.plugin, player.getWorld()).runTaskTimer(this.plugin, 0, 80);
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

    public void camePlayers() {
        World world = Bukkit.getWorld(this.worldName);
        List<Player> players = world.getPlayers();
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            p.sendMessage("揃ったからゲーム始まるよーー");
            // ここでそれぞれのチームに分けるプログラム
            this.setEquipmentForPlayer(p);
        }
    }

    public void setEquipmentForPlayer(Player player) {
        // 鉄の剣渡す
        // 皮のヘルメットを渡す
        // 皮のチェストプレート渡す
        // 皮のブーツ渡す
        // ヘルメット渡す
        ItemStack item = new ItemStack(Material.IRON_HELMET);
        player.getInventory().addItem(item);
    }

}
