package me.mckd.neptune.Worlds.Exit;

import me.mckd.neptune.Neptune;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.util.List;

public class ExitWorld implements Listener {

    Neptune plugin;
    String worldName = "exit";
    int count = 5;

    public ExitWorld(Neptune plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerChangedWorldEvent(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();
        player.sendMessage("aaaaaa");
        if (!player.getWorld().getName().equals(this.worldName)) {
            return;
        }

        // 今ワールドにいるプレーヤーの人数を調べる
        List<Player> players = player.getWorld().getPlayers();
        int playerCount = players.size();
        String playerNum = String.valueOf(playerCount);
        for (Player p:players) {
            p.sendMessage("今"+playerNum + "人になった？");
        }
        // n人以上集まったらゲームを開始する
        if (playerCount >= 1) {
            this.start();
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e)  {
        Player player = e.getPlayer();
        if(!player.getWorld().getName().equals("exit")) {
            return;
        }
        if (e.getBlock().getType() == Material.LOG_2) {
            Block block = e.getBlock();
            block.setType(Material.REDSTONE_BLOCK);
            this.count--;
            List<Player> players = e.getBlock().getWorld().getPlayers();
            for (Player p: players) {
                p.sendTitle("あと" + String.valueOf(this.count) + "個", "", 20, 20, 20);
            }
        }
    }

    // 11.25
    // ゲームを開始するプログラム
    private void start() {
        World world = Bukkit.getWorld("exit");
        List<Player> players = world.getPlayers();

        // レッドストーンに置き換わった原木を元に戻す
        world.getBlockAt(new Location(world, -1024, 4, -1118)).setType(Material.LOG_2);
        world.getBlockAt(new Location(world, -1003, 4, -1134)).setType(Material.LOG_2);
        world.getBlockAt(new Location(world, -1000, 4, -1086)).setType(Material.LOG_2);
        world.getBlockAt(new Location(world, -1030, 5, -1090)).setType(Material.LOG_2);
        world.getBlockAt(new Location(world, -1052, 4, -1086)).setType(Material.LOG_2);

        // レッドストーンランプカウントリセット
        this.count = 5;

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            if (i == 0) {
                player.setDisplayName("A");
                Location location = new Location(world, -1026, 5, -1138);
                player.teleport(location);
            } else {
                player.setDisplayName("B");
                Location location = new Location(world, -1024, 5, -1089);
                player.teleport(location);
            }
        }
    }
}
