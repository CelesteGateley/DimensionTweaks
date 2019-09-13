package xyz.fluxinc.dimensiontweaks.listeners;

import org.bukkit.GameMode;
import org.bukkit.block.Biome;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import xyz.fluxinc.dimensiontweaks.DimensionTweaks;

import java.util.HashMap;

public class NetherCeilingListener implements Listener {

    private DimensionTweaks instance;
    private boolean creativeBypass;
    public NetherCeilingListener(DimensionTweaks instance, boolean creativeBypass) { this.instance = instance; this.creativeBypass = creativeBypass; }

    @EventHandler
    public void preventNetherCeilingAccess(PlayerMoveEvent event) {
        if (event.getPlayer().hasPermission("dimensiontweaks.netherceiling.bypass") || event.getPlayer().isOp()) { return; }
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE && creativeBypass) { return; }
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

    @EventHandler
    public void netherCeilingBreak(BlockBreakEvent event) {
        if (event.getPlayer().hasPermission("dimensiontweaks.netherceiling.bypass") || event.getPlayer().isOp()) { return; }
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE && creativeBypass) { return; }
        // Handle Preventing Accessing the Nether Ceiling
        if (event.getBlock().getBiome() != Biome.NETHER) { return; }
        if (event.getBlock().getY() < 126) { return; }
        event.setCancelled(true);
        HashMap<String, String> var = new HashMap<>();
        var.put("player", event.getPlayer().getName());
        var.put("display", event.getPlayer().getDisplayName());
        var.put("permission", "dimensiontweaks.netherceiling.bypass");
        event.getPlayer().sendMessage(instance.generateMessage("netherBreakDenied", var));
    }

    @EventHandler
    public void netherCeilingTeleport(PlayerTeleportEvent event) {
        if (event.getPlayer().hasPermission("dimensiontweaks.netherceiling.bypass") || event.getPlayer().isOp()) { return; }
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE && creativeBypass) { return; }
        // Handle Preventing Accessing the Nether Ceiling
        Biome loc = event.getTo().getWorld().getBiome(event.getTo().getBlock().getX(), event.getTo().getBlock().getZ());
        if (loc != Biome.NETHER) { return; }
        if (event.getTo().getBlock().getY() < 126) { return; }
        event.setCancelled(true);
        HashMap<String, String> var = new HashMap<>();
        var.put("player", event.getPlayer().getName());
        var.put("display", event.getPlayer().getDisplayName());
        var.put("permission", "dimensiontweaks.netherceiling.bypass");
        event.getPlayer().sendMessage(instance.generateMessage("netherAccessDenied", var));
    }

    @EventHandler
    public void netherCeilingPortal(PlayerPortalEvent event) {
        if (event.getPlayer().hasPermission("dimensiontweaks.netherceiling.bypass") || event.getPlayer().isOp()) { return; }
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE && creativeBypass) { return; }
        if (event.getCause() != PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) { return; }
        // Handle Preventing Accessing the Nether Ceiling
        Biome loc = event.getTo().getWorld().getBiome(event.getTo().getBlock().getX(), event.getTo().getBlock().getZ());
        if (loc != Biome.NETHER) { return; }
        if (event.getTo().getBlock().getY() < 126) { return; }
        event.setCancelled(true);
        HashMap<String, String> var = new HashMap<>();
        var.put("player", event.getPlayer().getName());
        var.put("display", event.getPlayer().getDisplayName());
        var.put("permission", "dimensiontweaks.netherceiling.bypass");
        event.getPlayer().sendMessage(instance.generateMessage("netherAccessDenied", var));
    }
}
