package xyz.fluxinc.dimensiontweaks.listeners;

import org.bukkit.block.Biome;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import xyz.fluxinc.dimensiontweaks.DimensionTweaks;

import java.util.HashMap;

public class MoveListener implements Listener {

    DimensionTweaks instance;
    public MoveListener(DimensionTweaks instance) { this.instance = instance; }

    @EventHandler
    public void preventNetherCeilingAccess(PlayerMoveEvent event) {
        if (event.getPlayer().hasPermission("dimensiontweaks.netherceiling.bypass") || event.getPlayer().isOp()) { return; }
        // Handle Preventing Accessing the Nether Ceiling
        Biome loc = event.getPlayer().getWorld().getBiome((int)event.getTo().getX(), (int)event.getTo().getZ());
        if (loc != Biome.NETHER) { return; }
        if (event.getTo().getY() < 127) { return; }
        event.setCancelled(true);
        HashMap<String, String> var = new HashMap<>();
        var.put("player", event.getPlayer().getName());
        var.put("display", event.getPlayer().getDisplayName());
        var.put("permission", "dimensiontweaks.netherceiling.bypass");
        event.getPlayer().sendMessage(instance.generateMessage("netherAccessDenied", var));
    }
}
