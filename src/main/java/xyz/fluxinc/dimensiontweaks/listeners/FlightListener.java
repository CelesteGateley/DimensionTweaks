package xyz.fluxinc.dimensiontweaks.listeners;

import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import xyz.fluxinc.dimensiontweaks.DimensionTweaks;

import java.util.HashMap;

public class FlightListener implements Listener {

    private DimensionTweaks instance;
    private World affectedWorld;
    private boolean creativeBypass;

    public FlightListener(DimensionTweaks instance, World world, boolean creativeBypass) {
        this.instance = instance;
        this.affectedWorld = world;
        this.creativeBypass = creativeBypass;
    }

    @EventHandler
    public void onFlight(PlayerToggleFlightEvent event) {
        if (!checkFlight(event.getPlayer())) { return; }
        if (!event.isFlying()) { return; }
        denyFlight(event.getPlayer());
    }

    @EventHandler
    public void onFlightMove(PlayerMoveEvent event) {
        if (!checkFlight(event.getPlayer())) { return; }
        if (!event.getPlayer().getAllowFlight()) { return; }
        event.setCancelled(true);
        denyFlight(event.getPlayer());
    }

    private boolean checkFlight(Player player) {
        if (player.hasPermission("dimensiontweaks.flight.bypass.*")
                || player.hasPermission("dimensiontweaks.flight.bypass." + affectedWorld.getName().toLowerCase())
                || player.isOp()) { return false; }
        if (player.getGameMode() == GameMode.CREATIVE && creativeBypass) { return false; }
        if (player.getLocation().getWorld() != affectedWorld) { return false; }
        return true;
    }

    private void denyFlight(Player player) {
        player.setAllowFlight(false);
        HashMap<String, String> var = new HashMap<>();
        var.put("world", affectedWorld.getName().toLowerCase());
        var.put("permission", "dimensiontweaks.flight.bypass." + affectedWorld.getName().toLowerCase());
        player.sendMessage(instance.generateMessage("flightDenied", var));
    }

}
