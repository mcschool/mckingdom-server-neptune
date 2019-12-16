package me.mckd.neptune.Worlds.Villager;

import me.mckd.neptune.Neptune;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
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
    String worldName = "Villager";
    String gameStatus = "preparation";

    Boolean isRedTeamVillagerDied = false;
    Boolean isBlueTeamVillagerDied = false;
    Boolean isGreenTeamVillagerDied = false;
    Boolean isYelowTeamVillagerDied = false;

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

        World world = player.getWorld();
        player.getInventory().clear();
        player.sendMessage(String.valueOf(world.getPlayers().size()));
        // n人以上集まったらゲームスタート
        if (world.getPlayers().size() >= 1) {
            // new JoinCheckScheduler(this.plugin, world).runTaskTimer(this.plugin, 0, 20);
            // this.start();
            new SpawnDiamondScheduler(this.plugin, player.getWorld()).runTaskTimer(this.plugin, 0, 100);
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
    public void onEntityExplodeEvent(EntityExplodeEvent e) {
        World world = e.getEntity().getWorld();
        if (!world.getName().equals("villager")) {
            return;
        }
        e.setCancelled(true);
    }

    // 11.25
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
        Inventory inv;
        inv = Bukkit.createInventory(null, 45, "SHOP");
        inv.clear();

        inv.setItem(0, this.setItemForGui(Material.IRON_AXE, "10", 1));
        inv.setItem(1, this.setItemForGui(Material.DIRT, "1", 16));
        player.openInventory(inv);
    }


    public ItemStack setItemForGui(Material material, String name, int count) {
        ItemStack itemStack = new ItemStack(material, count);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        /*
        Player player = e.getPlayer();
        if (!player.getWorld().getName().equals("villager")) {
            return;
        }
        if (e.getBlock().getType() == Material.REDSTONE_BLOCK) {
            this.gameFinish();
            return;
        }
        if (e.getBlock().getType() == Material.ENCHANTMENT_TABLE) {
            this.gameStart();
        }
        */
    }
    public void gameStart(){
        /*
        World world=Bukkit.getWorld("villager");
        this.gameStatus="start";
        this.isBlueTeamVillagerDied=false;
        this.isGreenTeamVillagerDied=false;
        this.isRedTeamVillagerDied=false;
        this.isYelowTeamVillagerDied=false;

        Location locationRed=new Location(world, 100, 100, 100);
        Villager villagerRed=(Villager) world.spawnEntity(locationRed, EntityType.Villager);

        List<Player> players=world.getPlayers();
        for (int i=0; 1<players.size();i++) {
            Player p = players.get(i);
            if (i % 4 == 0) ;

            Location location = new Location(world, 100, 100, 100);
            p.teleport(location);
            
        }
        */

    }

}








