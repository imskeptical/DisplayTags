package me.itsskeptical.displaytags.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import me.itsskeptical.displaytags.DisplayTags;
import org.bukkit.Bukkit;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.function.Consumer;

public class UpdateChecker {
    DisplayTags plugin;
    String latest;

    String apiUrl;
    String projectId;

    public UpdateChecker(DisplayTags plugin, String projectId) {
        this.plugin = plugin;
        this.projectId = projectId;
        this.apiUrl = "https://api.modrinth.com/v2";
    }

    public void getLatestVersion(Consumer<String> consumer) {
        if (latest != null) consumer.accept(latest);

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                URL url = URI.create(apiUrl + "/project/" + projectId + "/version").toURL();
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

                InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                JsonArray versions = JsonParser.parseReader(reader).getAsJsonArray();
                String latestVersion = versions
                        .get(0)
                        .getAsJsonObject()
                        .get("version_number")
                        .getAsString();
                if (latestVersion != null) {
                    latest = latestVersion;
                    consumer.accept(latestVersion);
                }
            } catch (Exception e) {
                plugin.getLogger().severe("Unable to fetch latest version: " + e.getMessage());
            }
        });
    }
}
