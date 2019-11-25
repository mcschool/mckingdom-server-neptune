package me.mckd.neptune.Worlds.Villager;

import me.mckd.neptune.Neptune;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
        // new SpawnDiamondScheduler(this.plugin, player.getWorld()).runTaskTimer(this.plugin, 0, 80);

        World world =player.getWorld();
        player.getInventory().clear();
        player.sendMessage(String.valueOf(world.getPlayers().size()));
        // n人以上集まったらゲームスタート
        if (world.getPlayers().size() >= 2) {
            // new JoinCheckScheduler(this.plugin, world).runTaskTimer(this.plugin, 0, 20);
            // this.start();
            new SpawnDiamondScheduler(this.plugin,player.getWorld()).runTaskTimer(this.plugin, 0 , 100);
            new SpawnIronScheduler(this.plugin, player.getWorld()).runTaskTimer(this.plugin, 0, 20);
        }
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

    @EventHandler
    public void onEntityExplodeEvent(EntityExplodeEvent e){
        World world = e.getEntity().getWorld();
        if (!world.getName().equals("villager")) {
            return;
        }
        e.setCancelled(true);
    }

    // 11.25
    /*
    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent e) {
        Player player = e.getPlayer();
        if (!player.getWorld().getName().equals("villager")) {
            return;
        }

        Entity entity = e.getRightClicked();
        player.sendMessage(entity.getType().toString());
        if (entity instanceof Villager) {
            player.sendMessage("a");
            e.setCancelled(true);
            this.openGui(player);
        }
    }
     */

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e) {
        Player player = (Player) e.getPlayer();
        if (!player.getWorld().getName().equals("villager")) {
            return;
        }
        if (e.getInventory().getType() == InventoryType.MERCHANT) {
            e.setCancelled(true);
            this.openGui(player);
        }
    }

    public void openGui(Player player) {
        player.sendMessage("b");
        Inventory inv;
        inv = Bukkit.createInventory(null, 45, "SHOP");
        inv.clear();

        // アイテムを並べる
        inv.setItem(0, this.setItemForGui(Material.IRON_AXE, "10", 1));

        // インベントリ開く
        player.openInventory(inv);
    }

    public ItemStack setItemForGui(Material material, String name, int count) {
        // アイテムスタックを設定
        ItemStack itemStack = new ItemStack(material, count);
        // アイテムのメタ情報を取得
        ItemMeta itemMeta  = itemStack.getItemMeta();
        // アイテムの名前を設定
        itemMeta.setDisplayName(name);
        // アイテムのメタ情報を上書き
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
