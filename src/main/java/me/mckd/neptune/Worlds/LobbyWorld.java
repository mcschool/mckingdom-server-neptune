package me.mckd.neptune.Worlds;

import me.mckd.neptune.Neptune;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LobbyWorld implements Listener {
    private Neptune plugin;
    public LobbyWorld(Neptune plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();
        if (player.getWorld().getName().equals("lobby")) {
            player.setGameMode(GameMode.ADVENTURE);
            player.getInventory().clear();

            ItemStack arrow = new ItemStack(Material.ARROW);
            ItemMeta arrowMeta = arrow.getItemMeta();
            arrowMeta.setDisplayName("ArrowWarp");
            arrow.setItemMeta(arrowMeta);
            player.getInventory().setItem(9, arrow);

            ItemStack bed = new ItemStack(Material.BED);
            ItemMeta itemMeta = bed.getItemMeta();
            itemMeta.setDisplayName("ホームに戻る");
            bed.setItemMeta(itemMeta);
            player.getInventory().setItem(34, bed);
        }
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e){
        Player player=e.getPlayer();
        player.performCommand("mvtp lobby");
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (!player.getWorld().getName().equals("lobby")) return;
        if (e.getCurrentItem().getType() == Material.ARROW) {
            player.performCommand("mvtp arrow");
        }
    }

    @EventHandler
    public void onBlockBreak (BlockBreakEvent e) {
        if (!e.getPlayer().getWorld().getName().equals("lobby")) {
            return;
        }
        Player player = e.getPlayer();
        if (player.getGameMode()==GameMode.SURVIVAL){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerGameModeChange(PlayerGameModeChangeEvent e) {
        if (!e.getPlayer().getWorld().getName().equals("lobby")) {
            return;
        }
        Player player = e.getPlayer();
        if(e.getNewGameMode() == GameMode.SPECTATOR) {
            player. sendMessage ("キンシ！");
            e.setCancelled(true);
        }
    }
}
