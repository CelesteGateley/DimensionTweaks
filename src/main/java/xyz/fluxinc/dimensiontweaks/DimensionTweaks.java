package xyz.fluxinc.dimensiontweaks;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.fluxinc.dimensiontweaks.listeners.FlightListener;
import xyz.fluxinc.dimensiontweaks.listeners.NetherCeilingListener;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public final class DimensionTweaks extends JavaPlugin {

    private YamlConfiguration lang;

    @Override
    public void onEnable() {
        // Load the default config file
        saveDefaultConfig();


        // Load Lang File
        lang = new YamlConfiguration();
        saveResource("lang.yml", false);
        try {
            lang.load(new File(getDataFolder(), "lang.yml"));
        } catch (IOException | InvalidConfigurationException e) {
            getLogger().severe("[AdminFun] Invalid Lang File, Disabling!!");
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Nether Ceiling Features
        if (getConfig().getBoolean("deny-nether-ceiling")) {
            getServer().getPluginManager().registerEvents(new NetherCeilingListener(this, getConfig().getBoolean("creative-bypass-nether")), this);
        }

        // Flight Denial Features
        for (String world : getConfig().getStringList("deny-flight-in-worlds")) {
            getServer().getPluginManager().registerEvents(new FlightListener(this, getServer().getWorld(world), getConfig().getBoolean("creative-bypass-flight")), this);
        }


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public String generateMessage(String langKey, Map<String, String> variables) {
        String prefix = lang.getString("prefix");
        String msg = lang.getString(langKey);
        if (prefix == null || msg == null) { getLogger().severe("Invalid Lang File, missing elements prefix or " + langKey); return ""; }
        for (Map.Entry<String,String> pair : variables.entrySet()) { msg = msg.replaceAll("%" + pair.getKey() + "%", pair.getValue()); }
        return ChatColor.translateAlternateColorCodes('&', prefix + msg);
    }

    public String generateMessage(String langKey) {
        String prefix = lang.getString("prefix");
        String msg = lang.getString(langKey);
        if (prefix == null || msg == null) { getLogger().severe("Invalid Lang File, missing elements prefix or " + langKey); return ""; }
        return ChatColor.translateAlternateColorCodes('&', prefix + " " + msg);
    }
}
