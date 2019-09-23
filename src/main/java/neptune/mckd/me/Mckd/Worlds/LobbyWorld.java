package neptune.mckd.me.Mckd.Worlds;

import neptune.mckd.me.Mckd.Neptune;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class LobbyWorld implements Listener {

    Neptune plugin;

    public LobbyWorld(Neptune plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler

    public void onPlayerChangeWorld(PlayerChangedWorldEvent event){
       Player player=event.getPlayer();
        if (player.getWorld().getName().equals("lobby")){
            player.setGameMode(GameMode.ADVENTURE);
        }
    }
}


