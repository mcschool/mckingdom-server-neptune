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
        if (playerCount >= 1) {
            this.start();
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e)  {
        Player player = e.getPlayer();
        player.sendMessage("aaaaa");
        if(!player.getWorld().getName().equals("exit")) {
            return;
        }
        if (e.getBlock().getType() == Material.LOG_2) {
            Block block = e.getBlock();
            Location location = block.getLocation();
            location.setY(location.getY() + 1);
            location.getBlock();
            player.sendMessage(location.getBlock().getType().toString());
        }
    }

    private void start() {
        World world = Bukkit.getWorld("exit");
        List<Player> players = world.getPlayers();
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
