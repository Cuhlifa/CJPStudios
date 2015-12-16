package com.kirill.endprisim;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;


public class EndPrisim extends JavaPlugin implements Listener{
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
        PlayerInventory inv = p.getInventory();
    return true;
    }
    @EventHandler(priority= EventPriority.HIGH)
    public void onPlayerDeath(PlayerDeathEvent event) {

        Player player = (Player)event.getEntity();
        List<ItemStack> dropped_items = event.getDrops();


        // deploy chest, putting items that don't fit in chest into dropped_items list
        dropped_items = plugin.chestManager.deployChest(player, dropped_items);

        // clear dropped items
        event.getDrops().clear();

        // drop any items that don't fit in chest
        event.getDrops().addAll(dropped_items);
        return;
    }


}
