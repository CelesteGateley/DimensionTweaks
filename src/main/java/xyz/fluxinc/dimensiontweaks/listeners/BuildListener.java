package xyz.fluxinc.dimensiontweaks.listeners;

import org.bukkit.block.Biome;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import xyz.fluxinc.dimensiontweaks.DimensionTweaks;

import java.util.HashMap;

public class BuildListener implements Listener {

    DimensionTweaks instance;
    public BuildListener(DimensionTweaks instance) { this.instance = instance; }

    @EventHandler
    public void netherCeilingBreak(BlockBreakEvent event) {
        if (event.getPlayer().hasPermission("dimensiontweaks.netherceiling.bypass") || event.getPlayer().isOp()) { return; }
        // Handle Preventing Accessing the Nether Ceiling
        Biome loc = event.getPlayer().getWorld().getBiome(event.getBlock().getX(), event.getBlock().getZ());
        if (event.getBlock().getBiome() != Biome.NETHER) { return; }
        if (event.getBlock().getY() < 126) { return; }
        event.setCancelled(true);
        HashMap<String, String> var = new HashMap<>();
        var.put("player", event.getPlayer().getName());
        var.put("display", event.getPlayer().getDisplayName());
        var.put("permission", "dimensiontweaks.netherceiling.bypass");
        event.getPlayer().sendMessage(instance.generateMessage("netherBreakDenied", var));
    }
}
