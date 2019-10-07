package me.mckd.neptune.Worlds;

import me.mckd.neptune.Neptune;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BuildWorld implements Listener{

    Neptune plugin;

    public BuildWorld(Neptune plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        // buildワールドじゃない場合プログラム終了
        if (!e.getPlayer().getWorld().getName().equals("build")) {
            return;
        }
        // プレーヤー情報をplayerという変数に保存
        Player player = e.getPlayer();
        // ゲームモードがサバイバルだった場合
        // If game mode is survival
        if (player.getGameMode() == GameMode.SURVIVAL) {
            // ブロックを壊す。をキャンセル
            e.setCancelled(true);
        }
    }


}
