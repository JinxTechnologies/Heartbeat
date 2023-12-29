package ac.jinx.heartbeat.uptime;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Uptime {

    private final String heartbeat_URL;
    private boolean first = true;

    public Uptime(String url) {
        this.heartbeat_URL = url;
    }

    public Runnable run() {
        return () -> {
            try {
                // Create a URL object with the specified URL
                URL apiUrl = new URL(heartbeat_URL);

                // Open a connection to the URL
                HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();

                // Set the request method to GET
                connection.setRequestMethod("GET");

                // Get the response code
                int responseCode = connection.getResponseCode();

                // Check if the request was successful (status code 200)
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Read the response content
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    StringBuilder response = new StringBuilder();

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    if (first) {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Heartbeat was successfully setup!");
                    }
                } else {
                    if (first) {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Heartbeat was unsuccessfully setup! Please check the link you provided!");
                    } else
                        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Failed to send heartbeat to betterstack :(");
                }

                // Close the connection
                connection.disconnect();

            } catch (Exception e) {
                e.printStackTrace();
                if (first) {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Heartbeat was unsuccessfully setup! Please check the link you provided!");
                }
            }

            first = false;
        };
    }

}
