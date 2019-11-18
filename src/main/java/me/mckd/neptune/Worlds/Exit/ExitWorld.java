package me.mckd.neptune.Worlds.Exit;

import me.mckd.neptune.Neptune;
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
        List<Player> players = player.getWorld().getPlayers();
        int playerCount = players.size();
        String playerNum = String.valueOf(playerCount);
        for (Player p:players) {
            p.sendMessage("今"+playerNum + "人になった？");
        }

    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e)  {
        Player player = e.getPlayer();
        player.sendMessage("aaaaa");
        if(!player.getWorld().getName().equals("exit")) {
            return;
        }
        Block block = e.getBlock();
        player.sendMessage("ブロック壊した"+ block.getType().toString() );


    }


}
