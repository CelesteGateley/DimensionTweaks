package xyz.fluxinc.dimensiontweaks.listeners;

import org.bukkit.GameMode;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPistonEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import xyz.fluxinc.dimensiontweaks.DimensionTweaks;

import java.util.HashMap;

public class NetherCeilingListener implements Listener {

    private DimensionTweaks instance;
    private boolean creativeBypass;
    private static final int breakLimit = 124;
    private static final int moveLimit = 125;
    public NetherCeilingListener(DimensionTweaks instance, boolean creativeBypass) { this.instance = instance; this.creativeBypass = creativeBypass; }

    @EventHandler
    public void preventNetherCeilingAccess(PlayerMoveEvent event) {
        if (verifyDenial(event.getTo().getBlock(), moveLimit, event.getPlayer())) { return; }
        event.setCancelled(true);
        sendMessage(event.getPlayer());
    }

    @EventHandler
    public void netherCeilingBreak(BlockBreakEvent event) {
        if (verifyDenial(event.getBlock(), breakLimit, event.getPlayer())) { return; }
        event.setCancelled(true);
        sendMessage(event.getPlayer());
    }

    @EventHandler
    public void netherCeilingTeleport(PlayerTeleportEvent event) {
        if (verifyDenial(event.getTo().getBlock(), moveLimit, event.getPlayer())) { return; }
        event.setCancelled(true);
        sendMessage(event.getPlayer());
    }

    @EventHandler
    public void netherCeilingPortal(PlayerPortalEvent event) {
        if (event.getCause() != PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) { return; }
        if (verifyDenial(event.getTo().getBlock(), moveLimit, event.getPlayer())) { return; }
        event.setCancelled(true);
        sendMessage(event.getPlayer());
    }

    @EventHandler
    public void netherCeilingPiston(BlockPistonEvent event) {
        if (verifyDenial(event.getBlock(), breakLimit - 2)) { return; }
        event.setCancelled(true);
    }

    private boolean verifyDenial(Block block, int heightLimit, Player player) {
        boolean permissionCheck = player.hasPermission("dimensiontweaks.netherceiling.bypass");
        boolean gamemodeCheck = player.getGameMode() == GameMode.CREATIVE && creativeBypass;

        return verifyDenial(block, heightLimit) || permissionCheck || gamemodeCheck;
    }

    private boolean verifyDenial(Block block, int heightLimit) {
        boolean biomeCheck = block.getBiome() != Biome.NETHER;
        boolean limitCheck = block.getY() < heightLimit;
        return biomeCheck || limitCheck;
    }

    private void sendMessage(Player player) {
        HashMap<String, String> var = new HashMap<>();
        var.put("player", player.getName());
        var.put("display", player.getDisplayName());
        var.put("permission", "dimensiontweaks.netherceiling.bypass");
        player.sendMessage(instance.generateMessage("netherAccessDenied", var));
    }
}
