package com.kirill.endprisim;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;

public final class EndPrism extends JavaPlugin implements Listener {

    public Logger logger = Logger.getLogger("Minecraft");

    public void onEnable()
    {
        this.logger.info("EnderPrismInventory Enabled.");
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    public void onDisable()
    {
        this.logger.info("EnderPrismInventory Disabled.");
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e)
    {
        if ((e.getEntity() instanceof Player))
        {
            Player p = e.getEntity();
            if (!p.hasPermission("inv.keep"))
            {
                Location cstl1 = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ());
                Location cstl2 = new Location(p.getWorld(), cstl1.getX(), cstl1.getY(), cstl1.getZ() + 1.0D);
                cstl1.getBlock().setType(Material.ENDER_CHEST);
                cstl2.getBlock().setType(Material.ENDER_CHEST);
                Block b = cstl1.getBlock();
                Chest c = (Chest)b.getState();
                DoubleChest chest = (DoubleChest)c.getInventory().getHolder();
                Inventory inv = chest.getInventory();
                inv.setContents(p.getInventory().getContents());
                inv.addItem(p.getInventory().getArmorContents());
                p.getInventory().clear();
            }
        }
    }
}
