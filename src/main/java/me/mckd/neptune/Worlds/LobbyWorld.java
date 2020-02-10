package me.mckd.neptune.Worlds;

import me.mckd.neptune.Neptune;
import me.mckd.neptune.Worlds.Beginers.BeginersWorld;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class LobbyWorld implements Listener {
    private Neptune plugin;
    public LobbyWorld(Neptune plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();
        player.sendMessage("a");
        if (player.getWorld().getName().equals("lobby")) {
            player.sendMessage("b");
            player.setGameMode(GameMode.ADVENTURE);
            player.getInventory().clear();

            ItemStack arrow = new ItemStack(Material.ARROW);
            ItemMeta arrowMeta = arrow.getItemMeta();
            arrowMeta.setDisplayName("ArrowWarp");
            arrow.setItemMeta(arrowMeta);
            player.getInventory().setItem(0, arrow);

            // VSAにいくためのブーツ渡す
            ItemStack boots = new  ItemStack(Material.DIAMOND_BOOTS);
            ItemMeta bootsMeta = boots.getItemMeta();
            bootsMeta.setDisplayName("VSAに行く");
            boots.setItemMeta(bootsMeta);
            player.getInventory().setItem(1,boots);

            // Exitに行くためのグローストーン渡す
            ItemStack glow = new ItemStack(Material.GLOWSTONE);
            ItemMeta glowMeta = glow.getItemMeta();
            glowMeta.setDisplayName("Exitに行く");
            glow.setItemMeta(glowMeta);
            player.getInventory().setItem(2, glow);

            // VillagerWarsに行くためのエメラルド渡す
            ItemStack emerald = new ItemStack(Material.EMERALD);
            ItemMeta emeraldMeta = emerald.getItemMeta();
            emeraldMeta.setDisplayName("VillagerWarsに行く");
            emerald.setItemMeta(emeraldMeta);
            player.getInventory().setItem(3, emerald);

            // beginers
            ItemStack totem = new ItemStack(Material.TOTEM);
            ItemMeta totemMETA = totem.getItemMeta();
            totemMETA.setDisplayName("beginers 楽しんでね");
            totem.setItemMeta(totemMETA);
            player.getInventory().setItem(4,totem);



            ItemStack bed = new ItemStack(Material.BED);
            ItemMeta itemMeta = bed.getItemMeta();
            itemMeta.setDisplayName("ホームに戻る");
            bed.setItemMeta(itemMeta);
            player.getInventory().setItem(8, bed);
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
        if (e.getCurrentItem().getType() == Material.DIAMOND_BOOTS) {
            player.performCommand("mvtp vsa");
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

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (!player.getWorld().getName().equals("lobby")) {
            return;
        }
        // 空中を右クリックした場合
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            // かつ矢を持っていた場合
            if (e.getMaterial() == Material.ARROW) {
                player.performCommand("mvtp arrow");
            }
            // かつダイヤモンドブーツを持っていた場合
            if (e.getMaterial() == Material.DIAMOND_BOOTS) {
                player.performCommand("mvtp vsa");
            }
            // かつグローストーンを持っていた場合
            if (e.getMaterial() == Material.GLOWSTONE) {
                player.performCommand("mvtp exit");
            }
            // かつエメラルドを持っていた場合
            if (e.getMaterial() == Material.EMERALD) {
                player.performCommand("mvtp villager");
            }
            //BEGINERS
            if (e.getMaterial() == Material.TOTEM) {
                player.performCommand("mvtp beginers");

            }
        }
    }
}
