package ac.jinx.heartbeat;

import ac.jinx.heartbeat.uptime.Uptime;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Heartbeat extends JavaPlugin {

    @Override
    public void onEnable() {

        saveResource("config.yml", false);


        final String url = getConfig().getString("heartbeat.url");
        if (url == null || url.equalsIgnoreCase("put your url here")) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Please configure heartbeat!");
            return;
        }

        final Uptime uptime = new Uptime(url);

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, uptime.run(), 0, 20 * 10);

    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
    }
}
