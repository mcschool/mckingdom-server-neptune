package me.mckd.neptune.Worlds.Beginers;

import me.mckd.neptune.Neptune;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class BeginersWorld implements Listener {

    Neptune plugin;
    String worldName = "beginers";

    public BeginersWorld(Neptune plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player player  = e.getPlayer();
        if (!player.getWorld().getName().equals("beginers")) {
            return;
        }
        if (player.getGameMode() == GameMode.CREATIVE) {

        } else {
            player.sendMessage("壊すな！！このやろーーー");
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void signClick(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if (!p.getWorld().getName().equals("beginers")) {
            return;
        }
        Block b = e.getClickedBlock();
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)&&b.getType()== Material.SIGN_POST){
            p.sendMessage("こんちわ");
        }
    }
}
