package com.reve.HUD.events;

import com.reve.HUD.HUDPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;
import org.bukkit.util.Vector;

public class PlayerJumpEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private final PlayerStatisticIncrementEvent playerStatisticIncrementEvent;
    private boolean isCancelled = false;
    public PlayerJumpEvent(Player player, PlayerStatisticIncrementEvent playerStatisticIncrementEvent) {
        super(player);
        this.playerStatisticIncrementEvent = playerStatisticIncrementEvent;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        isCancelled = cancel;
        if (cancel) {
            player.setVelocity(new Vector());
            playerStatisticIncrementEvent.setCancelled(true);
        }
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() {
        return handlers;
        //required!!
    }
    public static class PlayerJumpEventListener implements Listener {
        HUDPlugin plugin;
        public PlayerJumpEventListener(HUDPlugin plugin){
            this.plugin = plugin;
        }
        @EventHandler
        public void onPlayerStatisticIncrement(PlayerStatisticIncrementEvent event) {
            if (event.getStatistic().equals(Statistic.JUMP)) {
                //calls PlayerJumpEvent
                Bukkit.getServer().getPluginManager().callEvent(new PlayerJumpEvent(event.getPlayer(), event));
            }
        }
    }
}
