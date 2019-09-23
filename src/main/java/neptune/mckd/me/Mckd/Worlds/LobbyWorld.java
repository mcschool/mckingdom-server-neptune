package neptune.mckd.me.Mckd.Worlds;

import neptune.mckd.me.Mckd.Neptune;
import org.bukkit.event.Listener;

public class LobbyWorld implements Listener {

    Neptune plugin;

    public LobbyWorld(Neptune plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
}
