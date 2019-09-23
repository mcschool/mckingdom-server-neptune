package me.mckd.neptune.Worlds;

import me.mckd.neptune.Neptune;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class LobbyWorld implements Listener {
    private Neptune plugin;
    public LobbyWorld(Neptune plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();
        player.sendMessage("ロビーだよ");
        if (player.getWorld().getName().equals("lobby")) {
            player.setGameMode(GameMode.ADVENTURE);
        }
    }
}
