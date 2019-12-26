package me.mckd.neptune.Worlds.Beginers;

import me.mckd.neptune.Neptune;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class BeginersWorld implements Listener {

    Neptune plugin;
    String worldName = "beginers";

    public BeginersWorld(Neptune plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onChangedWorld(PlayerChangedWorldEvent e) {
        if (!e.getPlayer().getWorld().getName().equals("beginers")) {
            return;
        }
        Player player = e.getPlayer();
        World world = player.getWorld();
        List<Player> players = world.getPlayers();
        if (players.size() == 1) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.sendMessage("モンスターがでたよ");
                }
            }.runTaskLater(this.plugin, 20);
        }
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
            //p.sendMessage("こんちわ");

            Sign sign  = (Sign) b.getState();
            String line = sign.getLine(1);
            if ( line.equals("sword") ){
                ItemStack item = new ItemStack(Material.WOOD_SWORD);
                p.getInventory().addItem(item);
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        World world = e.getEntity().getWorld();
        if (world.getName().equals("beginers")) {
            // もし敵が倒されたワールドが beginers じゃなかったらプログラム終了
            return;
        }
        Player player = e.getEntity().getKiller();
        if (e.getEntityType() == EntityType.ZOMBIE) {
            player.sendMessage("ゾンビを倒した！");
        }
        if (e.getEntityType() == EntityType.SKELETON) {
            player.sendMessage("スケルトンを倒した！");
        }
    }
}
