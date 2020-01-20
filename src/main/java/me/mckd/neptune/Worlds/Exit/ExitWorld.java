package me.mckd.neptune.Worlds.Exit;

import me.mckd.neptune.Neptune;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class ExitWorld implements Listener {

    Neptune plugin;
    String worldName = "exit";
    int count = 5;
    boolean theWorld = false;
    boolean started = false;

    public ExitWorld(Neptune plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerChangedWorldEvent(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();
        if (!player.getWorld().getName().equals(this.worldName)) {
            return;
        }

        player.setGameMode(GameMode.SURVIVAL);
        player.getInventory().clear();

        // 今ワールドにいるプレーヤーの人数を調べる
        List<Player> players = player.getWorld().getPlayers();
        int playerCount = players.size();
        String playerNum = String.valueOf(playerCount);
        for (Player p:players) {
            p.sendMessage("今"+playerNum + "人になりました");
        }
        // n人以上集まったらゲームを開始する
        if (playerCount == 1) {
            this.started = false;
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e)  {
        Player player = e.getPlayer();
        if(!player.getWorld().getName().equals("exit")) {
            return;
        }
        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }
        if (e.getBlock().getType() == Material.LOG_2) {
            // 壊したブロックが原木だった場合
            e.setCancelled(true);
            Block block = e.getBlock();
            World world = block.getWorld();
            block.setType(Material.REDSTONE_BLOCK);
            // カウントを1減らす
            this.count--;
            List<Player> players = e.getBlock().getWorld().getPlayers();
            // 全プレーヤーにメッセージ
            for (Player p: players) {
                if (this.count == 0) {
                    p.sendTitle("全部壊した！", "", 20, 20, 20);
                } else {
                    p.sendTitle("あと" + String.valueOf(this.count) + "個", "", 20, 20, 20);
                }
            }
            // 原木を全部壊したら全員にメッセージ
            if (this.count == 0) {
                world.getBlockAt(new Location(world, -1026, 4, -1141)).setType(Material.AIR);
                world.getBlockAt(new Location(world, -1026, 5, -1141)).setType(Material.AIR);
            }
        } else {
            e.setCancelled(true);
        }
    }



    // 11.25
    private void start() {
        this.started = true;
        World world = Bukkit.getWorld("exit");
        List<Player> players =world.getPlayers();

        world.getBlockAt(new Location(world,-1025,4,-1119)).setType(Material.LOG_2);
        world.getBlockAt(new Location(world,-1004,4,-1135)).setType(Material.LOG_2);
        world.getBlockAt(new Location(world,-1001,4,-1087)).setType(Material.LOG_2);
        world.getBlockAt(new Location(world,-1031,5,-1091)).setType(Material.LOG_2);
        world.getBlockAt(new Location(world,-1053,4,-1087)).setType(Material.LOG_2);

        world.getBlockAt(new Location(world,-1026,4,-1141)).setType(Material.IRON_FENCE);
        world.getBlockAt(new Location(world,-1026,5,-1141)).setType(Material.IRON_FENCE);

        this.count = 5;

        for(int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            if (i == 0) {
                player.setDisplayName("A");
                player.setCustomName("A");
                Location location = new Location(world, -1026, 5, -1138);
                player.teleport(location);

                player.sendTitle(
                        "あなたは鬼です",
                        "全員を捕まえてください",
                20,
                40,
                20

                );



                this.setStatus(player);
                new BukkitRunnable() {
                    @Override
                    public  void  run() {
                        player.sendMessage("potion");
                        ItemStack potion = new ItemStack(Material.POTION);
                        PotionType potionType = PotionType.SPEED;
                        PotionData potionData = new PotionData(
                                potionType,
                                false,
                                false
                        );
                        PotionMeta meta = (PotionMeta) potion.getItemMeta();
                        meta.setBasePotionData(potionData);
                        potion.setItemMeta(meta);
                        player.getInventory().addItem(potion);

                        ItemStack watch = new ItemStack(Material.WATCH);
                        ItemMeta watchMeta = watch.getItemMeta();
                        watchMeta.setDisplayName("ザ・ワールド");
                        watch.setItemMeta(watchMeta);
                        player.getInventory().addItem(watch);
                    }
                }.runTaskLater(this.plugin,200);
            }else {
                player.setDisplayName("B");
                player.setCustomName("B");
                Location location = new Location(world,-1024,5,-1089);
                player.teleport(location);
                player.sendTitle(
                        "あなたは逃げる人です",
                        "鬼に捕まえられないようにしながら原木を壊してください",
                        20,
                        40,
                        20
                );
                // 装備を整える関数を実行
                this.setEquipment(player);

                new BukkitRunnable() {
                    @Override
                    public void  run() {
                        ItemStack potion = new ItemStack(Material.POTION);
                        PotionType potionType = PotionType.SPEED;
                        PotionData potionData = new PotionData(
                                potionType,
                                false,
                                false

                        );
                        PotionMeta meta = (PotionMeta) potion.getItemMeta();
                        meta.setBasePotionData(potionData);
                        potion.setItemMeta(meta);
                        player.getInventory().addItem(potion);

                    }
                }.runTaskLater(this.plugin,200);
            }
        }

        List<Entity> entities = world.getEntities();
        for (Entity entity: entities) {
            if (entity instanceof Item) {
                entity.remove();
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {

        if(!e.getEntity().getWorld().getName().equals("exit")) {
            return;
        }

        if (e.getEntity() instanceof Player) {
            if (e.getDamager() instanceof Player) {
                Player damager = (Player) e.getDamager();
                Player player = (Player) e.getEntity();
                if (isOni(damager)) {

                    Location location = new Location(e.getEntity().getWorld(), -997, 29, -1080);
                    player.teleport(location);
                }
            }
        }
        World world = e.getEntity().getWorld();
        if( this.isAllCatched(world) ){
            this.oniWon(world);
        }
    }

    public void setEquipment(Player player) {
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        ItemStack chestPlate = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);

        player.getEquipment().setHelmet(helmet);
        player.getEquipment().setChestplate(chestPlate);
        player.getEquipment().setBoots(boots);
        player.getEquipment().setLeggings(leggings);
    }


    public void setStatus(Player player) {
    }

    public Boolean isOni(Player player) {
        if (player.getDisplayName().equals("A")) {
            return true;
        }
        return false;




    }
    @EventHandler
    public  void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if(!player.getWorld().getName().equals("exit")) {
            return;
        }
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Block block = e.getClickedBlock();
            if (block.getType() == Material.ENDER_CHEST) {
                new ExitFinishScheduler(player.getWorld()).runTaskTimer(this.plugin, 0, 20);
                player.sendMessage("ENDER CLICKED");
                e.setCancelled(true);
            }
            if(block.getType() == Material.SIGN_POST) {
                Sign sign;
                sign = (Sign) block.getState();
                String line = sign.getLine(1);
                if(line.equals("start")) {
                    if(this.started) {
                        player.sendMessage("現在プレイ中のゲームが終わるまでお待ち下。");
                    }else{
                        List<Player> players = player.getWorld().getPlayers();
                        int playerCount =players.size();
                        if(playerCount == 1) {
                            player.sendMessage("２人以上になるまでお待ち下さい。");
                        }
                        if (playerCount >=2) {
                            start();
                        }
                    }
                }
            }
        }
        if(e.getAction().equals(Action.RIGHT_CLICK_AIR)) {

            if(e.getMaterial()  == Material.WATCH) {
                theWorld = true;
                player.getInventory().remove(player.getItemInHand());
                List<Player>players = player.getWorld().getPlayers();
                for (Player p: players) {
                    p.sendTitle("ザ・ワールド！","",20,180,20);
                }
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        theWorld = false;
                    }
                }.runTaskLater(this.plugin,100);
            }

            if(e.getMaterial()  == Material.DIAMOND_SWORD) {
                whereAreYou(player);
            }

        }
    }

    @EventHandler
    public  void onPlayerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if(!e.getPlayer().getWorld().getName().equals(this.worldName) ) {
            return;
        }
        if(theWorld) {
            if(player.getDisplayName().equals("B") ) {
                e.setCancelled(true);
            }
        }
    }

    private boolean isAllCatched(World world){

        List<Player> players = world.getPlayers();
        for (Player player: players) {

            Location loc = player.getLocation();
            if(player.getDisplayName().equals("B") ) {
                if (loc.getY() < 20) {
                    return false;
                }
            }
        }
        return  true;
    }

    private void oniWon(World world){

        List<Player> players = world.getPlayers();

        for(Player p: players) {
            p.sendTitle("鬼の勝ち！","",20,20,20);

            new BukkitRunnable() {
                @Override
                public void  run() {
                    p.performCommand("mvtp lobby");

                }
            }.runTaskLater(this.plugin,60);
        }
    }

    private void whereAreYou(Player player){
        if( inField(player) ){
            player.sendMessage("Fieldにいます。");
        }else{
            player.sendMessage("Fieldにいません。");
        }
        if( inGoal(player) ){
            player.sendMessage("Goalにいます。");
        }else{
            player.sendMessage("Goalにいません。");
        }
        if( inJail(player) ){
            player.sendMessage("Jailにいます。");
        }else{
            player.sendMessage("Jailにいません。");
        }
    }

    private boolean inField(Player player){
        Location loc = player.getLocation();
        int minX = 0;
        int maxX = 0;
        int minY = 0;
        int maxY = 0;
        int minZ = 0;
        int maxZ = 0;
        if( minX <= loc.getX() && loc.getX()<=maxX ){
            if( minY <= loc.getY() && loc.getY()<=maxY ){
                if( minZ <= loc.getZ() && loc.getZ()<=maxZ ){
                    return true;
                }
            }
        }
        return false;
    }


    private boolean inGoal(Player player){
        Location loc = player.getLocation();
        int minX = 0;
        int maxX = 0;
        int minY = 0;
        int maxY = 0;
        int minZ = 0;
        int maxZ = 0;
        if( minX <= loc.getX() && loc.getX()<=maxX ){
            if( minY <= loc.getY() && loc.getY()<=maxY ){
                if( minZ <= loc.getZ() && loc.getZ()<=maxZ ){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean inJail(Player player){
        Location loc = player.getLocation();
        int minX = 0;
        int maxX = 0;
        int minY = 0;
        int maxY = 0;
        int minZ = 0;
        int maxZ = 0;
        if( minX <= loc.getX() && loc.getX()<=maxX ){
            if( minY <= loc.getY() && loc.getY()<=maxY ){
                if( minZ <= loc.getZ() && loc.getZ()<=maxZ ){
                    return true;
                }
            }
        }
        return false;
    }

}

