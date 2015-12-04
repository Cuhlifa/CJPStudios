package net.hisocraft.mc.lobbytools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.hisocraft.mc.lobbytools.scoreboard.LargeLobbyBoard;
import net.hisocraft.mc.lobbytools.scoreboard.lobbyBoard;
import net.hisocraft.mc.lobbytools.utils.CustomEntityFirework;
import net.hisocraft.mc.lobbytools.utils.Utils;
import net.hisocraft.mc.lobbytools.utils.configutils.ConfigManager;
import net.hisocraft.mc.lobbytools.utils.database.MySQL;
import net.minecraft.server.v1_8_R3.EntityEnderDragon;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_8_R3.WorldServer;
import net.hisocraft.mc.lobbytools.utils.ping.MinecraftPing;
import net.hisocraft.mc.lobbytools.utils.ping.MinecraftPingOptions;
import net.hisocraft.mc.lobbytools.utils.ping.MinecraftPingReply;
import net.hisocraft.mc.lobbytools.utils.ping.MinecraftPingUtil;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.SkullType;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.material.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

import src.john01dav.sqlperms.SQLPerms;
import src.john01dav.sqltokens.SQLTokens;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

/*import de.albionco.comugamers.tokens.SQLTokens;
import de.albionco.comugamers.tokens.database.DatabaseManager;
import de.albionco.comugamers.tokens.database.DataStore;*/


public class main extends JavaPlugin implements Listener
{
	
public static main plugin;
	
public static ArrayList<Player> onlinePlayers = new ArrayList<Player>();
	
public static ArrayList<Player> playersOn = new ArrayList<Player>();
public static ArrayList<Player> playersRankOn = new ArrayList<Player>();
public static ArrayList<Player> playersOff = new ArrayList<Player>();
public static ArrayList<Player> speedOn = new ArrayList<Player>();
public static ArrayList<Player> jumpOn = new ArrayList<Player>();
public static ArrayList<Player> nightOn = new ArrayList<Player>();
public static ArrayList<Player> dbJumpOn = new ArrayList<Player>();
public static ArrayList<Player> flyOn = new ArrayList<Player>();

public static ArrayList<String> playersOnstr = new ArrayList<String>();
public static ArrayList<String> playersRankOnstr = new ArrayList<String>();
public static ArrayList<String> playersOffstr = new ArrayList<String>();
public static ArrayList<String> speedOnstr = new ArrayList<String>();
public static ArrayList<String> jumpOnstr = new ArrayList<String>();
public static ArrayList<String> nightOnstr = new ArrayList<String>();
public static ArrayList<String> dbJumpOnstr = new ArrayList<String>();
public static ArrayList<String> flyOnstr = new ArrayList<String>();

public static ArrayList<String> somethingChange = new ArrayList<String>();

int a = 1;
public static SQLTokens sqlTokens = null;
public static SQLPerms sqlPerms = null;

public static int lobby;
public static Inventory optionsInv;
public static Inventory servers;
int currentIndexMessage = 0;

public Location soulExchanger1 = new Location(Bukkit.getWorld("World"),-2545,50,771);
public Location soulExchanger2;

public static boolean statsDbOn;
public static MySQL mysql = null;
public static Connection connection = null;

public boolean playsound = true;

public static int NETWORK_PLAYERS = 0;

public boolean scbCycle = false;

int ScoreUpdate;
int ServerCycle;


@EventHandler
public void fixArraysQuit(PlayerQuitEvent e)
{
Player p = e.getPlayer();
	
  if(playersOn.contains(p)){playersOnstr.add(p.getName());}
  if(playersRankOn.contains(p)){playersRankOnstr.add(p.getName());}
  if(playersOff.contains(p)){playersOffstr.add(p.getName());}
  if(jumpOn.contains(p)){jumpOnstr.add(p.getName());}
  if(nightOn.contains(p)){nightOnstr.add(p.getName());}
  if(dbJumpOn.contains(p)){dbJumpOnstr.add(p.getName());}
}
@EventHandler(priority = EventPriority.HIGHEST)
public void fixArraysJoin(PlayerJoinEvent e)
{
Player p = e.getPlayer();
	
  if(playersOnstr.contains(p.getName())){playersOn.add(p);}
  if(playersRankOnstr.contains(p.getName())){playersRankOn.add(p);}
  if(playersOffstr.contains(p.getName())){playersOff.add(p);}
  if(jumpOnstr.contains(p.getName())){jumpOn.add(p);}
  if(nightOnstr.contains(p.getName())){nightOn.add(p);}
  if(dbJumpOnstr.contains(p.getName())){dbJumpOn.add(p);}
}

public ItemStack nolore(Material material, int amount, int shrt, String displayName)
	{
	  ItemStack item = new ItemStack(material, amount, (short)shrt);
	  ItemMeta meta = item.getItemMeta();
	  meta.setDisplayName(displayName);
	  
	  item.setItemMeta(meta);
	  return item;
	}
public ItemStack unlore(Material material, int amount, int shrt, String displayName, String lore1)
{
  ItemStack item = new ItemStack(material, amount, (short)shrt);
  ItemMeta meta = item.getItemMeta();
  meta.setDisplayName(displayName);
  ArrayList<String> lore = new ArrayList<String>();
  lore.add(lore1);
  meta.setLore(lore);
  
  item.setItemMeta(meta);
  return item;
}
public ItemStack head(int amount, String displayName, String nick)
{
  ItemStack item = new ItemStack(Material.SKULL_ITEM, amount, (short) SkullType.PLAYER.ordinal());
  SkullMeta meta = (SkullMeta) item.getItemMeta();
  
  meta.setOwner(nick);
  meta.setDisplayName(displayName);
  
  item.setItemMeta(meta);
  return item;
}
public ItemStack head3lores(int amount, String displayName, String nick, String lore1, String lore2, String lore3)
{
  ItemStack item = new ItemStack(Material.SKULL_ITEM, amount, (short) SkullType.PLAYER.ordinal());
  SkullMeta meta = (SkullMeta) item.getItemMeta();
  
  meta.setOwner(nick);
  meta.setDisplayName(displayName);
  
  ArrayList<String> lore = new ArrayList<String>();
  lore.add(lore1);
  lore.add(lore2);
  lore.add(lore3);
  meta.setLore(lore);
  
  item.setItemMeta(meta);
  return item;
}
public ItemStack sixlores(Material material, int amount, int shrt, String displayName, String lore1, String lore2, String lore3, String lore4, String lore5, String lore6)
{
  ItemStack item = new ItemStack(material, amount, (short)shrt);
  ItemMeta meta = item.getItemMeta();
  ArrayList<String> lore = new ArrayList<String>();
  lore.add(lore1);
  lore.add(lore2);
  lore.add(lore3);
  lore.add(lore4);
  lore.add(lore5);
  lore.add(lore6);
  meta.setDisplayName(displayName);
  meta.setLore(lore);
  
  item.setItemMeta(meta);
  return item;
}

private static ItemStack withlore(Material material, int amount, int shrt, String displayName, ArrayList<String> lore)
{
  ItemStack item = new ItemStack(material, amount, (short)shrt);
  ItemMeta meta = item.getItemMeta();
  meta.setLore(lore);
  meta.setDisplayName(displayName);
  
  item.setItemMeta(meta);
  return item;
}

	public static ItemStack addGlow(ItemStack item){ 
		  net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
		  NBTTagCompound tag = null;
		  if (!nmsStack.hasTag()) {
		      tag = new NBTTagCompound();
		      nmsStack.setTag(tag);
		  }
		  if (tag == null) tag = nmsStack.getTag();
		  NBTTagList ench = new NBTTagList();
		  tag.set("ench", ench);
		  nmsStack.setTag(tag);
		  return CraftItemStack.asCraftMirror(nmsStack);
		}
	
	public void sendToServer(Player player, String targetServer)
	  {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(out);
        try
        {
          dos.writeUTF("Connect");
          dos.writeUTF(targetServer);
        }
        catch (Exception ex)
        {
          Bukkit.getServer().getLogger().severe("Error Intentando enviar al jugador "+player.getName()+" al servidor "+targetServer);
          return;
        }
        player.sendPluginMessage(this.plugin, "BungeeCord", out.toByteArray());
	  }
	
	/*@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
	    if (!channel.equals("BungeeCord")) {
	        return;
	    }
	   
	    ByteArrayDataInput in = ByteStreams.newDataInput(message);
	    String subchannel = in.readUTF();
	   
	    if (subchannel.equals("PlayerCount")) {
	        String server = in.readUTF();
	        int playerCount = in.readInt();
	       
	        //player.sendMessage("Player count on server " + server + " is equal to " + playerCount);
	        this.NETWORK_PLAYERS=playerCount;
	       
	    }
	    
	}*/

	/*
	public static void getBungeeCount(Player player, String server) {
	    
	    if (server == null) {
	        server = "ALL";
	    }
	   
	    ByteArrayDataOutput out = ByteStreams.newDataOutput();
	    out.writeUTF("PlayerCount");
	    out.writeUTF(server);

	    player.sendPluginMessage(main.plugin, "BungeeCord", out.toByteArray());
	   
	}*/
	
	/*@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		if(!(channel.equals("BungeeCord"))) {
			return;
		}
		DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
		try {
			String server = null;
			String subChannel = in.readUTF();
			if(subChannel.equalsIgnoreCase("PlayerCount")) {
				server = in.readUTF();
				bungeeServerCount = in.readInt();
			}
			in.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}*/

	    /*public void getCount(String serverName) {
	       
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(b);
			try {
				out.writeUTF("PlayerCount");
				out.writeUTF(serverName);
				out.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
			try {
				Player player = Bukkit.getOnlinePlayers()[0];
				player.sendPluginMessage(this, "BungeeCord", b.toByteArray());
			} catch(Exception e) {
				//TODO
			}
	       
	    }*/
	
  public void setOptionsMenu(Player p)
  {
	optionsInv = Bukkit.createInventory(null, 54,ConfigManager.get("Config.yml").getString("OptionsMenu.title").replaceAll("&", "§").replaceAll("%p", p.getName()));
    somethingChange.add(p.getName());
	
	if(playersOff.contains(p))
	{
		optionsInv.setItem(1, nolore(Material.SNOW_BALL, 1, 0, "§b§lVisbilidad: §a§lninguno"));
		optionsInv.setItem(1, addGlow(optionsInv.getItem(1)));
		optionsInv.setItem(10, nolore(Material.INK_SACK, 1, 10, "§a§lACTIVADO"));
	}
	else
	{
		optionsInv.setItem(1, nolore(Material.SNOW_BALL, 1, 0, "§b§lVisbilidad: §c§lninguno"));
		optionsInv.setItem(10, nolore(Material.INK_SACK, 1, 8, "§c§lDESACTIVADO"));
	}
	if(playersRankOn.contains(p))
	{
		optionsInv.setItem(4, nolore(Material.ENDER_PEARL, 1, 0, "§b§lVisbilidad: §a§lrango"));
		optionsInv.setItem(4, addGlow(optionsInv.getItem(4)));
		optionsInv.setItem(13, nolore(Material.INK_SACK, 1, 10, "§a§lACTIVADO"));
	}
	else
	{
		optionsInv.setItem(4, nolore(Material.ENDER_PEARL, 1, 0, "§b§lVisbilidad: §c§lrango"));
		optionsInv.setItem(13, nolore(Material.INK_SACK, 1, 8, "§c§lDESACTIVADO"));
	}
	if(playersOn.contains(p))
	{
		optionsInv.setItem(7, nolore(Material.EYE_OF_ENDER, 1, 0, "§b§lVisbilidad: §a§ltodos"));
		optionsInv.setItem(7, addGlow(optionsInv.getItem(7)));
		optionsInv.setItem(16, nolore(Material.INK_SACK, 1, 10, "§a§lACTIVADO"));
	}
	else
	{
		optionsInv.setItem(7, nolore(Material.EYE_OF_ENDER, 1, 0, "§b§lVisbilidad: §c§ltodos"));
		optionsInv.setItem(16, nolore(Material.INK_SACK, 1, 8, "§c§lDESACTIVADO"));
	}
	
	if(jumpOn.contains(p))
	{
		optionsInv.setItem(20, nolore(Material.GOLD_BOOTS, 1, 0, "§b§lAumento Salto: §a§lactivado"));
		optionsInv.setItem(20, addGlow(optionsInv.getItem(20)));
		optionsInv.setItem(29, nolore(Material.INK_SACK, 1, 10, "§a§lACTIVADO"));
	}
	else
	{
		optionsInv.setItem(20, nolore(Material.GOLD_BOOTS, 1, 0, "§b§lAumento Salto: §c§ldesactivado"));
		optionsInv.setItem(29, nolore(Material.INK_SACK, 1, 8, "§c§lDESACTIVADO"));	
	}
	
	if(speedOn.contains(p))
	{
		optionsInv.setItem(22, nolore(Material.SUGAR, 1, 0, "§b§lAumento Velocidad: §a§lactivado"));
		optionsInv.setItem(22, addGlow(optionsInv.getItem(22)));
		optionsInv.setItem(31, nolore(Material.INK_SACK, 1, 10, "§a§lACTIVADO"));
	}
	else
	{
		optionsInv.setItem(22, nolore(Material.SUGAR, 1, 0, "§b§lAumento Velocidad: §c§ldesactivado"));
		optionsInv.setItem(31, nolore(Material.INK_SACK, 1, 8, "§c§lDESACTIVADO"));	
	}
	
	if(nightOn.contains(p))
	{
		optionsInv.setItem(24, nolore(Material.COAL, 1, 0, "§b§lVision Nocturna: §a§lactivado"));
		optionsInv.setItem(24, addGlow(optionsInv.getItem(24)));
		optionsInv.setItem(33, nolore(Material.INK_SACK, 1, 10, "§a§lACTIVADO"));
	}
	else
	{
		optionsInv.setItem(24, nolore(Material.COAL, 1, 0, "§b§lVision Nocturna: §c§ldesactivado"));
		optionsInv.setItem(33, nolore(Material.INK_SACK, 1, 8, "§c§lDESACTIVADO"));	
	}
	
	if(dbJumpOn.contains(p)&&p.hasPermission("lobby.doublejump"))
	{
		optionsInv.setItem(39, nolore(Material.SLIME_BALL, 1, 0, "§a§lVIP §b§lDoble Salto: §a§lactivado"));
		optionsInv.setItem(39, addGlow(optionsInv.getItem(39)));
		optionsInv.setItem(48, nolore(Material.INK_SACK, 1, 10, "§a§lACTIVADO"));
	}
	else
	{
		optionsInv.setItem(39, nolore(Material.SLIME_BALL, 1, 0, "§a§lVIP §b§lDoble Salto: §c§ldesactivado"));
		optionsInv.setItem(48, nolore(Material.INK_SACK, 1, 8, "§c§lDESACTIVADO"));	
	}
	if(flyOn.contains(p)&&p.hasPermission("lobby.fly"))
	{
		optionsInv.setItem(41, nolore(Material.FEATHER, 1, 0, "§a§lVIP §b§lModo Vuelo: §a§lactivado"));
		optionsInv.setItem(41, addGlow(optionsInv.getItem(41)));
		optionsInv.setItem(50, nolore(Material.INK_SACK, 1, 10, "§a§lACTIVADO"));
	}
	else
	{
		optionsInv.setItem(41, nolore(Material.FEATHER, 1, 0, "§a§lVIP §b§lModo Vuelo: §c§ldesactivado"));
		optionsInv.setItem(50, nolore(Material.INK_SACK, 1, 8, "§c§lDESACTIVADO"));	
	}
	
  }	
	
	public void onEnable()
	{
        plugin = this;
		
		getServer().getPluginManager().registerEvents(this, this);
		ConfigManager.save("Config.yml");
		ConfigManager.load("Config.yml");
		ConfigManager.save("Scoreboard.yml");
		ConfigManager.load("Scoreboard.yml");
		
		Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
		lobby=ConfigManager.get("Config.yml").getInt("LobbyMenu.lobby");
		
		servers = Bukkit.createInventory(null, 54,ConfigManager.get("Config.yml").getString("ServerMenu.title").replaceAll("&", "§"));		
				
		ArrayList<String> itemLore1 = new ArrayList<String>();
		for(String line : ConfigManager.get("Config.yml").getStringList("ServerMenu.items.1.lore")){
			itemLore1.add(line.replaceAll("&", "§").replaceAll(">", "►"));
		}
		servers.setItem(19,withlore(
				 Material.getMaterial(ConfigManager.get("Config.yml").getInt("ServerMenu.items.1.id")),1
				,ConfigManager.get("Config.yml").getInt("ServerMenu.items.1.subid")
				,ConfigManager.get("Config.yml").getString("ServerMenu.items.1.name")
				   .replaceAll("&", "§")
				,itemLore1
				));
		
		ArrayList<String> itemLore2 = new ArrayList<String>();
		for(String line : ConfigManager.get("Config.yml").getStringList("ServerMenu.items.2.lore")){
				itemLore2.add(line.replaceAll("&", "§").replaceAll(">", "►"));
		}
		servers.setItem(21,withlore(
				 Material.getMaterial(ConfigManager.get("Config.yml").getInt("ServerMenu.items.2.id")),1
				,ConfigManager.get("Config.yml").getInt("ServerMenu.items.2.subid")
				,"§e§lNUEVO §r"+ConfigManager.get("Config.yml").getString("ServerMenu.items.2.name")
					 .replaceAll("&", "§")
				,itemLore2
				));
		servers.setItem(21,addGlow(servers.getItem(21)));
			
		ArrayList<String> itemLore3 = new ArrayList<String>();
		for(String line : ConfigManager.get("Config.yml").getStringList("ServerMenu.items.3.lore")){
				itemLore3.add(line.replaceAll("&", "§").replaceAll(">", "►"));
		}
		servers.setItem(23,withlore(
					Material.getMaterial(ConfigManager.get("Config.yml").getInt("ServerMenu.items.3.id")),1
				,ConfigManager.get("Config.yml").getInt("ServerMenu.items.3.subid")
				,"§e§lNUEVO §r"+ConfigManager.get("Config.yml").getString("ServerMenu.items.3.name")
					 .replaceAll("&", "§")
				,itemLore3
				));
		servers.setItem(23,addGlow(servers.getItem(23)));
		
		ArrayList<String> itemLore4 = new ArrayList<String>();
		for(String line : ConfigManager.get("Config.yml").getStringList("ServerMenu.items.4.lore")){
			itemLore4.add(line.replaceAll("&", "§").replaceAll(">", "►"));
		}
		servers.setItem(25,withlore(
				 Material.getMaterial(ConfigManager.get("Config.yml").getInt("ServerMenu.items.4.id")),1
				,ConfigManager.get("Config.yml").getInt("ServerMenu.items.4.subid")
				,ConfigManager.get("Config.yml").getString("ServerMenu.items.4.name")
				   .replaceAll("&", "§")
				,itemLore4
				));
		
		ArrayList<String> itemLore5 = new ArrayList<String>();
		for(String line : ConfigManager.get("Config.yml").getStringList("ServerMenu.items.5.lore")){
			itemLore5.add(line.replaceAll("&", "§").replaceAll(">", "►"));
		}
		servers.setItem(48,withlore(
				 Material.getMaterial(ConfigManager.get("Config.yml").getInt("ServerMenu.items.5.id")),1
				,ConfigManager.get("Config.yml").getInt("ServerMenu.items.5.subid")
				,ConfigManager.get("Config.yml").getString("ServerMenu.items.5.name")
				   .replaceAll("&", "§")
				,itemLore5
				));
		
		ArrayList<String> itemLore6 = new ArrayList<String>();
		for(String line : ConfigManager.get("Config.yml").getStringList("ServerMenu.items.6.lore")){
			itemLore6.add(line.replaceAll("&", "§").replaceAll(">", "►"));
		}
		servers.setItem(50,withlore(
				 Material.getMaterial(ConfigManager.get("Config.yml").getInt("ServerMenu.items.6.id")),1
				,ConfigManager.get("Config.yml").getInt("ServerMenu.items.6.subid")
				,ConfigManager.get("Config.yml").getString("ServerMenu.items.6.name")
				   .replaceAll("&", "§")
				,itemLore6
				));
		
		/*sv.setItem(9, nolore(Material.BOOKSHELF, 1, 0, ConfigManager.get("Config.yml").getString("ServerMenu.items.libreria").replaceAll("&", "§")));		
		
		sv.setItem(12, nolore(Material.WORKBENCH, 1, 0, ConfigManager.get("Config.yml").getString("ServerMenu.items.workbench").replaceAll("&", "§")));
		//sv.setItem(13, nolore(Material.GRASS, 1, 0, ConfigManager.get("Config.yml").getString("ServerMenu.items.cesped").replaceAll("&", "§")));
		sv.setItem(14, nolore(Material.DIAMOND_CHESTPLATE, 1, 0, ConfigManager.get("Config.yml").getString("ServerMenu.items.pechera").replaceAll("&", "§")));
		
		sv.setItem(17, nolore(Material.PISTON_BASE, 1, 0, ConfigManager.get("Config.yml").getString("ServerMenu.items.piston").replaceAll("&", "§")));*/
					
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this,new Runnable()
	     {
			public void run()
			{						  
			  List<String> messages = ConfigManager.get("Config.yml").getStringList("ActionBarMessage.messages");
			  int indexTotal = messages.size()-1;
	               
			  		if(currentIndexMessage!=indexTotal)
			  		{
			  			for(Player p : Bukkit.getOnlinePlayers())
			  			{
				  			Utils.sendActionBar(p, messages.get(currentIndexMessage)
				  					.replaceAll("&", "§")
				  					.replaceAll("%player%", p.getName())
				  					);
			  			}
			  			currentIndexMessage++;
			  		}
			  		else
			  		{
			  			for(Player p : Bukkit.getOnlinePlayers())
			  			{
			  			Utils.sendActionBar(p, messages.get(currentIndexMessage)
			  					.replaceAll("&", "§")
			  					.replaceAll("%player%", p.getName())
			  					);
			  			}
			  			currentIndexMessage=0;
			  		}
			  			
			}
		 }, 0L, ConfigManager.get("Config.yml").getInt("ActionBarMessage.time")*20);
		
		//autoRestart();
		
        try {
            mysql = new MySQL(this, ConfigManager.get("Config.yml").getString("MySQL.ip"), ConfigManager.get("Config.yml").getString("MySQL.port"),
				ConfigManager.get("Config.yml").getString("MySQL.database"), ConfigManager.get("Config.yml").getString("MySQL.user"),
				ConfigManager.get("Config.yml").getString("MySQL.pass"));
            connection = mysql.openConnection();
            statsDbOn = true;
           
        } catch (Exception e){
            statsDbOn = false;
        	getLogger().severe("No se ha podido establezer la conexion a la base de datos");
        }
		
		
		try{
        for(Plugin plugin : Bukkit.getServer().getPluginManager().getPlugins()){
        	   if(plugin instanceof SQLTokens){
                 this.sqlTokens= ((src.john01dav.sqltokens.SQLTokens) plugin);     	     
        	     break;
        	   }
        	}
        	if(this.sqlTokens == null){
        	}
		}
        catch(Exception ex){Bukkit.getLogger().severe("Error ocurrido mientras se intentaba activar el plugin: "
        		+ "\nNo se puedo establecer una conexion con la base de datos MySQL");}
		
		for(Plugin plugin : Bukkit.getServer().getPluginManager().getPlugins()){
		    if(plugin instanceof SQLPerms){
		        sqlPerms = ((SQLPerms) plugin); break;
		    }
		}
		
	}
	
    @Override
    public void onDisable()
    {
    	try 
    	{
		if (connection!=null || !connection.isClosed()){connection.close();
		}
	    
    	} 
    	catch (SQLException e) 
    	{
		e.printStackTrace();
		getLogger().severe("=======================================================");
		getLogger().severe("");
		getLogger().severe("No se ha podido cerrar la conexion con la base de datos");
		getLogger().severe("");
		getLogger().severe("=======================================================");
	    }  
   	
    }
	
    @EventHandler
    public void setScore(PlayerJoinEvent ev){
    	if(scbCycle==false){
    		autoUpdateScb();
    	}
    	else{
    	LargeLobbyBoard.update(ev.getPlayer());
    	scbCycle=true;
    	}
    }
    
    public void autoUpdateScb()
    {
		ScoreUpdate = Bukkit.getScheduler().scheduleSyncRepeatingTask(this,new Runnable()
	     {
			public void run()
			{
				for(Player plys : Bukkit.getOnlinePlayers())
				 {
					LargeLobbyBoard.update(plys);
				 }
			}
	     },0, ConfigManager.get("Config.yml").getInt("ScoreboardUpdate.seconds")*20);
    }
    
	/*public void shutDown()
	{
        Player[] players = Bukkit.getOnlinePlayers();
        for (Player player : players) {
            player.kickPlayer(ConfigManager.get("Config.yml").getString("ServerRestart.message")
					 .replaceAll("&", "§"));
        }
		
		Bukkit.getServer().shutdown();
	}*/
	
	
	/*public void autoRestart()
	{
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this,new Runnable()
	     {
			public void run()
			{						   			
			  int currentLobby = ConfigManager.get("Config.yml").getInt("LobbyMenu.lobby");
			  int toLobby=1;
				for(Player players : Bukkit.getOnlinePlayers())
				{
					switch(toLobby)
					{
						case 1:
						{
						 if(currentLobby!=1)
						 {
							 players.sendMessage(ConfigManager.get("Config.yml").getString("ServerRestart.message")
									 .replaceAll("&", "§")
									 .replaceAll("%p", players.getName()));
							 sendToServer(players,"lobby");
							 toLobby++;
						 }
						 else
						 {
							 sendToServer(players,"lobby2");
							 toLobby++; 
						 }
						}
						case 2:
						{
						 if(currentLobby!=2)
						 {
							 players.sendMessage(ConfigManager.get("Config.yml").getString("ServerRestart.message")
									 .replaceAll("&", "§")
									 .replaceAll("%p", players.getName()));
							 sendToServer(players,"lobby2");
							 toLobby++;
						 }
						 else
						 {
							 sendToServer(players,"lobby3");
							 toLobby++; 
						 }
						}
						case 3:
						{
						 if(currentLobby!=3)
						 {
							 players.sendMessage(ConfigManager.get("Config.yml").getString("ServerRestart.message")
									 .replaceAll("&", "§")
									 .replaceAll("%p", players.getName()));
							 sendToServer(players,"lobby3");
							 toLobby++;
						 }
						 else
						 {
							 sendToServer(players,"lobby4");
							 toLobby++; 
						 }
						}
						case 4:
						{
						 if(currentLobby!=5)
						 {
							 players.sendMessage(ConfigManager.get("Config.yml").getString("ServerRestart.message")
									 .replaceAll("&", "§")
									 .replaceAll("%p", players.getName()));
							 sendToServer(players,"lobby4");
							 toLobby++;
						 }
						 else
						 {
							 sendToServer(players,"lobby5");
							 toLobby++; 
						 }
						}
						case 5:
						{
						 if(currentLobby!=5)
						 {
							 players.sendMessage(ConfigManager.get("Config.yml").getString("ServerRestart.message")
									 .replaceAll("&", "§")
									 .replaceAll("%p", players.getName()));
							 sendToServer(players,"lobby5");
							 toLobby++;
						 }
						 else
						 {
							 sendToServer(players,"lobby6");
							 toLobby++; 
						 }
						}
						case 6:
						{
						 if(currentLobby!=6)
						 {
							 players.sendMessage(ConfigManager.get("Config.yml").getString("ServerRestart.message")
									 .replaceAll("&", "§")
									 .replaceAll("%p", players.getName()));
							 sendToServer(players,"lobb6");
							 toLobby++;
						 }
						 else
						 {
							 sendToServer(players,"lobby7");
							 toLobby++; 
						 }
						}
						case 8:
						{
						 if(currentLobby!=8)
						 {
							 players.sendMessage(ConfigManager.get("Config.yml").getString("ServerRestart.message")
									 .replaceAll("&", "§")
									 .replaceAll("%p", players.getName()));
							 sendToServer(players,"lobby8");
							 toLobby=1;
						 }
						 else
						 {
							 sendToServer(players,"lobby");
							 toLobby=1; 
						 }
						}											
					}
				}
				//Bukkit.getServer().dispatchCommand(getServer().getConsoleSender(), "stop");
				Bukkit.shutdown();
				shutDown();
			}
		 }, ConfigManager.get("Config.yml").getInt("ServerRestart.task")*60*20, ConfigManager.get("Config.yml").getInt("ServerRestart.task")*60*20);
	}*/
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] a)
	{	  

		   if (cmd.getName().equalsIgnoreCase("setspawn"))
		   {
	            
			 if(sender instanceof Player)
			 {
			  Player player = (Player)sender;
				 if(player.hasPermission("lobby.adm"))
	          {
	           ConfigManager.get("Config.yml").set("ServerSettings.spawn.world", player.getLocation().getWorld().getName());
	           ConfigManager.get("Config.yml").set("ServerSettings.spawn.x", player.getLocation().getX());
	           ConfigManager.get("Config.yml").set("ServerSettings.spawn.y", player.getLocation().getY());
	           ConfigManager.get("Config.yml").set("ServerSettings.spawn.z", player.getLocation().getZ());	           
	           ConfigManager.get("Config.yml").set("ServerSettings.spawn.pitch", player.getLocation().getPitch());
	           ConfigManager.get("Config.yml").set("ServerSettings.spawn.yaw", player.getLocation().getYaw());
	           ConfigManager.save("Config.yml");
	           player.sendMessage("§e§l>> §fPunto de spawn añadido §b§nexitosamente");
	          }
			 }
	          
	 	   
		   }
     				   
		   if (cmd.getName().equalsIgnoreCase("fly"))
		   {
              if(sender instanceof Player)
              {
            	  Player p = (Player)sender;
            	  if(p.hasPermission("lobby.fly")){
			   
			    if(flyOn.contains(p)){p.setAllowFlight(false);p.setFlying(false);
			    flyOn.remove(p);}
			    
			    else{flyOn.add(p);p.setAllowFlight(true)
			    ;p.setFlying(true);}
			   
			     }
              }			   
		   }
		   
		   if (cmd.getName().equalsIgnoreCase("lobbies"))
		   {
			   if (sender instanceof Player)
			   {
				   Player p = (Player)sender;
				   Inventory lb = Bukkit.createInventory(null, 27,ConfigManager.get("Config.yml").getString("LobbyMenu.title").replaceAll("&", "§").replaceAll("%p", p.getName()));

				   
				   p.playSound(p.getLocation(),Sound.NOTE_PLING, 10000, 1);
				   
				      if (ConfigManager.get("Config.yml").getInt("LobbyMenu.lobby") == 1)
				        {				    	  
				    	  lb.setItem(11, nolore(Material.SLIME_BALL, 1, 0, "§c§l§nLobby 1"));
				    	  lb.setItem(11, addGlow(lb.getItem(11)));
				          lb.setItem(12, nolore(Material.SLIME_BALL, 2, 0, "§a§lLobby 2"));
				          lb.setItem(13, nolore(Material.SLIME_BALL, 3, 0, "§a§lLobby 3"));
				          //lb.setItem(14, nolore(Material.BOOK, 4, 0, "§a§lLobby 4"));
				          //lb.setItem(15, nolore(Material.BOOK, 5, 0, "§a§lLobby 5"));
				          //lb.setItem(15, nolore(Material.BOOK, 6, 0, "§a§lLobby 6"));
				          //lb.setItem(16, nolore(Material.BOOK, 7, 0, "§a§lLobby 7"));
				          //lb.setItem(22, nolore(Material.BOOK, 8, 0, "§a§lLobby 8"));
				        }
				        if (ConfigManager.get("Config.yml").getInt("LobbyMenu.lobby") == 2)
				        {
				          lb.setItem(11, nolore(Material.SLIME_BALL, 1, 0, "§a§lLobby 1"));
				          lb.setItem(12, nolore(Material.SLIME_BALL, 2, 0, "§c§l§nLobby 2"));
				          lb.setItem(12, addGlow(lb.getItem(12)));
				          lb.setItem(13, nolore(Material.SLIME_BALL, 3, 0, "§a§lLobby 3"));
				          //lb.setItem(14, nolore(Material.SLIME_BALL, 4, 0, "§a§lLobby 4"));
				          //lb.setItem(15, nolore(Material.BOOK, 5, 0, "§a§lLobby 5"));
				          //lb.setItem(15, nolore(Material.BOOK, 6, 0, "§a§lLobby 6"));
				          //lb.setItem(16, nolore(Material.BOOK, 7, 0, "§a§lLobby 7"));
				          //lb.setItem(22, nolore(Material.BOOK, 8, 0, "§a§lLobby 8"));
				        }
				        if (ConfigManager.get("Config.yml").getInt("LobbyMenu.lobby") == 3)
				        {
					      lb.setItem(11, nolore(Material.SLIME_BALL, 1, 0, "§a§lLobby 1"));
					      lb.setItem(12, nolore(Material.SLIME_BALL, 2, 0, "§c§l§nLobby 2"));
				          lb.setItem(13, nolore(Material.SLIME_BALL, 3, 0, "§c§l§nLobby 3"));
				          lb.setItem(13, addGlow(lb.getItem(13)));
				          //lb.setItem(14, nolore(Material.BOOK, 4, 0, "§a§lLobby 4"));
				          //lb.setItem(15, nolore(Material.BOOK, 5, 0, "§a§lLobby 5"));
				         // lb.setItem(15, nolore(Material.BOOK, 6, 0, "§a§lLobby 6"));
				          //lb.setItem(16, nolore(Material.BOOK, 7, 0, "§a§lLobby 7"));
				          //lb.setItem(22, nolore(Material.BOOK, 8, 0, "§a§lLobby 8"));
				        }
				        /*if (ConfigManager.get("Config.yml").getInt("LobbyMenu.lobby") == 4)
				        {
				          lb.setItem(11, nolore(Material.BOOK, 1, 0, "§a§lLobby 1"));
				          lb.setItem(12, nolore(Material.BOOK, 2, 0, "§a§lLobby 2"));
				          lb.setItem(13, nolore(Material.BOOK, 3, 0, "§a§lLobby 3"));
				          lb.setItem(14, nolore(Material.BOOK, 4, 0, "§c§l§nLobby 4"));
				          lb.setItem(14, addGlow(lb.getItem(14)));
				          lb.setItem(15, nolore(Material.BOOK, 5, 0, "§a§lLobby 5"));
				          //lb.setItem(15, nolore(Material.BOOK, 6, 0, "§a§lLobby 6"));
				          //lb.setItem(16, nolore(Material.BOOK, 7, 0, "§a§lLobby 7"));
				          //lb.setItem(22, nolore(Material.BOOK, 8, 0, "§a§lLobby 8"));
				        }
				        if (ConfigManager.get("Config.yml").getInt("LobbyMenu.lobby") == 5)
				        {
				          lb.setItem(11, nolore(Material.BOOK, 1, 0, "§a§lLobby 1"));
				          lb.setItem(12, nolore(Material.BOOK, 2, 0, "§a§lLobby 2"));
				          lb.setItem(13, nolore(Material.BOOK, 3, 0, "§a§lLobby 3"));
				          lb.setItem(14, nolore(Material.BOOK, 4, 0, "§a§lLobby 4"));
				          lb.setItem(15, nolore(Material.BOOK, 5, 0, "§c§l§nLobby 5"));
				          lb.setItem(15, addGlow(lb.getItem(15)));
				          //lb.setItem(15, nolore(Material.BOOK, 6, 0, "§a§lLobby 6"));
				          //lb.setItem(16, nolore(Material.BOOK, 7, 0, "§a§lLobby 7"));
				          //lb.setItem(22, nolore(Material.BOOK, 8, 0, "§a§lLobby 8"));
				        }
				     /*   if (ConfigManager.get("Config.yml").getInt("LobbyMenu.lobby") == 6)
				        {
				          lb.setItem(10, nolore(Material.BOOK, 1, 0, "§a§lLobby 1"));
				          lb.setItem(11, nolore(Material.BOOK, 2, 0, "§a§lLobby 2"));
				          lb.setItem(12, nolore(Material.BOOK, 3, 0, "§a§lLobby 3"));
				          lb.setItem(13, nolore(Material.BOOK, 4, 0, "§a§lLobby 4"));
				          lb.setItem(14, nolore(Material.BOOK, 5, 0, "§a§lLobby 5"));
				          lb.setItem(15, nolore(Material.BOOK, 6, 0, "§c§l§nLobby 6"));
				          lb.setItem(15, addGlow(lb.getItem(15)));
				          lb.setItem(16, nolore(Material.BOOK, 7, 0, "§a§lLobby 7"));
				          lb.setItem(22, nolore(Material.BOOK, 8, 0, "§a§lLobby 8"));
				        }
				        if (ConfigManager.get("Config.yml").getInt("LobbyMenu.lobby") == 7)
				        {
				          lb.setItem(10, nolore(Material.BOOK, 1, 0, "§a§lLobby 1"));
				          lb.setItem(11, nolore(Material.BOOK, 2, 0, "§a§lLobby 2"));
				          lb.setItem(12, nolore(Material.BOOK, 3, 0, "§a§lLobby 3"));
				          lb.setItem(13, nolore(Material.BOOK, 4, 0, "§a§lLobby 4"));
				          lb.setItem(14, nolore(Material.BOOK, 5, 0, "§a§lLobby 5"));
				          lb.setItem(15, nolore(Material.BOOK, 6, 0, "§a§lLobby 6"));
				          lb.setItem(16, nolore(Material.BOOK, 7, 0, "§c§l§nLobby 7"));
				          lb.setItem(16, addGlow(lb.getItem(16)));
				          lb.setItem(22, nolore(Material.BOOK, 8, 0, "§a§lLobby 8"));
				        }
				        if (ConfigManager.get("Config.yml").getInt("LobbyMenu.lobby") == 8)
				        {
				          lb.setItem(10, nolore(Material.BOOK, 1, 0, "§a§lLobby 1"));
				          lb.setItem(11, nolore(Material.BOOK, 2, 0, "§a§lLobby 2"));
				          lb.setItem(12, nolore(Material.BOOK, 3, 0, "§a§lLobby 3"));
				          lb.setItem(13, nolore(Material.BOOK, 4, 0, "§a§lLobby 4"));
				          lb.setItem(14, nolore(Material.BOOK, 5, 0, "§a§lLobby 5"));
				          lb.setItem(15, nolore(Material.BOOK, 6, 0, "§a§lLobby 6"));
				          lb.setItem(16, nolore(Material.BOOK, 7, 0, "§a§lLobby 7"));
				          lb.setItem(22, nolore(Material.BOOK, 8, 0, "§c§l§nLobby 8"));
				          lb.setItem(22, addGlow(lb.getItem(22)));
				        }*/
				   
				   p.openInventory(lb);
			   
			   }			   
		   }
		   
		   if (cmd.getName().equalsIgnoreCase("perfil"))
		   {
			   if (sender instanceof Player)
			   {
				   Player p = (Player)sender;
				   Inventory prof = Bukkit.createInventory(null, 45,ConfigManager.get("Config.yml").getString("Perfil.menuTitle").replaceAll("&", "§").replaceAll("%p", p.getName()));
				   
				   
				   prof.setItem(13, head3lores(1,"§a§lInformacion de §b§l"+p.getName(),p.getName(),"§eGemas: §7Cargando...","§eRango: §7Cargando...","§eAmigos: §7Proximamente..."));
				   
			        try
			        {
			        Double bal = net.hisocraft.mc.lobbytools.main.sqlTokens.getDataStore().getBalance(p);
			        Integer balance = bal.intValue();
			        String rank = net.hisocraft.mc.lobbytools.main.sqlPerms.getDataStore().getRank(p.getUniqueId(), "default");
			        
			        prof.setItem(13, head3lores(1,"§a§lInformacion de §b§l"+p.getName(),p.getName(),"§eGemas: §f"+balance,"§eRango: §f"+rank,"§eAmigos: §7Proximamente..."));
			        }
			        catch(NoClassDefFoundError | Exception ex)
			        {
			        	String rank = net.hisocraft.mc.lobbytools.main.sqlPerms.getDataStore().getRank(p.getUniqueId(), "default");
			        	prof.setItem(13, head3lores(1,"§a§lInformacion de §b§l"+p.getName(),p.getName(),"§eGemas: §cERROR:DATABASE CAIDA","§eRango: §f"+rank,"§eAmigos: §7Proximamente..."));
			        }
				   
				   p.playSound(p.getLocation(),Sound.NOTE_PLING, 10000, 1);
				   
				   prof.setItem(29, nolore(Material.PAPER, 1, 0, "§a§lEstadisticas"));
				   prof.setItem(33, nolore(Material.EXP_BOTTLE, 1, 0, "§a§lLogros"));
				   
				   p.openInventory(prof);
			   
			   }			   
		   }
		   if (cmd.getName().equalsIgnoreCase("stats"))
		   {
			   if (sender instanceof Player)
			   {
				   Player p = (Player)sender;
				   Inventory stats = Bukkit.createInventory(null, 36,ConfigManager.get("Config.yml").getString("Estadisticas.menuTitle").replaceAll("&", "§").replaceAll("%p", p.getName()));
				   	
				   String jugadas = null;
				   String ganadas = null;
				   String perdidas = null;
				   String asesinatos = null;
				   String muertes = null;
				   String almas = null;
				   
				   stats.setItem(13, sixlores(Material.GRASS, 1, 0, "§a§lSkywars"
						   ,"§ePartidas Ganadas: §7Cargando..."
						   ,"§ePartidas Perdidas: §7Cargando..."
						   ,"§eAsesinatos: §7Cargando..."
						   ,"§eMuertes: §7Cargando..."
						   ,"§ePartidas Jugadas: §7Cargando..."
						   ,"§eAlmas: §7Cargando..."
						   ));
				   
				   
				   if(statsDbOn == true)
				   {
				   int played = mysql.getPlayed(p);
				   int wins = mysql.getWins(p);
				   int losed = played-wins;
				   int kills = mysql.getKills(p);
				   int deaths = mysql.getDeaths(p);
				   
				   
				   jugadas = ""+played;
				   ganadas = ""+wins;
				   perdidas = ""+losed;
				   asesinatos = ""+kills;
				   muertes = ""+deaths;
				   
				   
				   stats.setItem(13, sixlores(Material.GRASS, 1, 0, "§a§lSkywars"
						   ,"§ePartidas Ganadas: §f"+ganadas
						   ,"§ePartidas Perdidas: §f"+perdidas
						   ,"§eAsesinatos: §f"+asesinatos
						   ,"§eMuertes: §f"+muertes
						   ,"§ePartidas Jugadas: §f"+jugadas
						   ,"§eAlmas: §f"+almas
						   ));
				   
				   }
				   else{
					   stats.setItem(13, sixlores(Material.GRASS, 1, 0, "§a§lSkywars"
							   ,"§ePartidas Ganadas: §cERROR: BASE DE DATOS CAIDA"
							   ,"§ePartidas Perdidas: §cERROR: BASE DE DATOS CAIDA"
							   ,"§eAsesinatos: §cERROR: BASE DE DATOS CAIDA"
							   ,"§eMuertes: §cERROR: BASE DE DATOS CAIDA"
							   ,"§ePartidas Jugadas: §cERROR: BASE DE DATOS CAIDA"
							   ,"§ePartidas Jugadas: §f"+almas
							   ));
				   }

				   stats.setItem(30, nolore(Material.ARROW, 1, 0, "§c§lAtras"));
				   stats.setItem(31, nolore(Material.BOOK, 1, 0, "§b§lEstadisticas Totales §r§7(Proximamente)"));
				   
				   p.openInventory(stats);
			   
			   }			   
		   }

		   if (cmd.getName().equalsIgnoreCase("servers"))
		   {
			   if (sender instanceof Player)
			   {
				   Player p = (Player)sender;
				   
				   p.openInventory(servers);
				   p.playSound(p.getLocation(),Sound.NOTE_PLING, 10000, 1);
			   
			   }
		   }
		   
		   if (cmd.getName().equalsIgnoreCase("options"))
		   {
			   if(!(sender instanceof Player)){}
			   else
			   {
				   Player p = (Player)sender;
				   
				   this.setOptionsMenu(p);
				   p.openInventory(optionsInv);
				   p.playSound(p.getLocation(),Sound.NOTE_PLING, 10000, 1);
				   			   
			   }			   
		   }
		   if (cmd.getName().equalsIgnoreCase("superstick"))
		   {
			   if(!(sender instanceof Player)){}
			   else
			   {Player p = (Player)sender;
   
			   if(p.hasPermission("lobbby.adm")){
			    
				   p.getInventory().addItem(nolore(Material.STICK, 1, 0, "§a§l§kii §b§l§nSuperStick §r§7(Click derecho en entidad para eliminarla)"));
			     }
			   }			   
		   }
		  if(cmd.getName().equalsIgnoreCase("updatescb"))
		   {
			 if(!(sender instanceof Player)){
			   
					for(Player plys : Bukkit.getOnlinePlayers())
					 {
						LargeLobbyBoard.update(plys);
					 }
			   
		   }
			 else{sender.sendMessage("Este comando solo puede realizarlo la consola!");}
		  }
		   
	return true;}

/*public void particles()
{
    Location loc = soulExchanger1;
    int radius = 4;
    for(double y = 0; y <= 3; y+=0.15) {
        double x = radius * Math.cos(y);
        double z = radius * Math.sin(y);
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.SPELL_WITCH,false, (float) (loc.getX() + x), (float) (loc.getY() + y), (float) (loc.getZ() + z), 0, 0, 0, 0, 1);
        for(Player online : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer)online).getHandle().playerConnection.sendPacket(packet);
        }
    }
    



}*/
	
		
@EventHandler
public void quitDamage(EntityDamageEvent e)
{
if(ConfigManager.get("Config.yml").getBoolean("ServerSettings.canceldamage")==true)
{
	
  if(e.getCause()!=DamageCause.VOID)
  {
	if(e.getEntityType()!=EntityType.ARMOR_STAND)
	{
	  e.setCancelled(true);
	}
  }
}
}

@EventHandler
public void voidSpawn(EntityDamageEvent e)
{
 Entity en = e.getEntity();
	
 if(e.getCause()==DamageCause.VOID&&en instanceof Player){
 e.setCancelled(true);
 
 World world = Bukkit.getServer().getWorld(ConfigManager.get("Config.yml").getString("ServerSettings.spawn.world"));
 double x = ConfigManager.get("Config.yml").getDouble("ServerSettings.spawn.x");
 double y = ConfigManager.get("Config.yml").getDouble("ServerSettings.spawn.y");
 double z = ConfigManager.get("Config.yml").getDouble("ServerSettings.spawn.z");

 double d = ConfigManager.get("Config.yml").getDouble("ServerSettings.spawn.pitch");
 float pitch = (float) d;

 double ya = ConfigManager.get("Config.yml").getDouble("ServerSettings.spawn.yaw");
 float yaw = (float) ya;


 if(en instanceof Player){
 en.teleport(new Location(world,x,y,z,yaw,pitch));
 }

  }
 }

@EventHandler
public void quitHunger(FoodLevelChangeEvent e)
{
Entity en = e.getEntity();
if(en instanceof Player){
if(ConfigManager.get("Config.yml").getBoolean("ServerSettings.cancelhunger")==true){e.setCancelled(true);}	
}
}

@EventHandler
public void joinSettings(PlayerJoinEvent e)
{
Player p = e.getPlayer();
int gamemode = ConfigManager.get("Config.yml").getInt("PlayerJoinSettings.gamemode");

p.setFoodLevel(ConfigManager.get("Config.yml").getInt("PlayerJoinSettings.food"));
p.setLevel(ConfigManager.get("Config.yml").getInt("PlayerJoinSettings.explevel"));
p.setExp(ConfigManager.get("Config.yml").getInt("PlayerJoinSettings.expbar"));
p.setMaxHealth(ConfigManager.get("Config.yml").getInt("PlayerJoinSettings.hearts")*2);
p.setHealth(ConfigManager.get("Config.yml").getInt("PlayerJoinSettings.hearts")*2);
p.getInventory().setHeldItemSlot(ConfigManager.get("Config.yml").getInt("PlayerJoinSettings.hotbar"));

World world = Bukkit.getServer().getWorld(ConfigManager.get("Config.yml").getString("ServerSettings.spawn.world"));
double x = ConfigManager.get("Config.yml").getDouble("ServerSettings.spawn.x");
double y = ConfigManager.get("Config.yml").getDouble("ServerSettings.spawn.y");
double z = ConfigManager.get("Config.yml").getDouble("ServerSettings.spawn.z");

double d = ConfigManager.get("Config.yml").getDouble("ServerSettings.spawn.pitch");
float pitch = (float) d;

double ya = ConfigManager.get("Config.yml").getDouble("ServerSettings.spawn.yaw");
float yaw = (float) ya;

p.teleport(new Location(world,x,y,z,yaw,pitch));


switch(gamemode)
{
  case 0:
  {p.setGameMode(GameMode.SURVIVAL);}
  case 1:
  {p.setGameMode(GameMode.CREATIVE);}
  case 2:
  {p.setGameMode(GameMode.ADVENTURE);}
}


}

@EventHandler
public void cancelWeateher(WeatherChangeEvent e)
{
if(ConfigManager.get("Config.yml").getBoolean("ServerSettings.rain")==false){e.setCancelled(true);}
}

@EventHandler
public void noBlockBraking(BlockBreakEvent e)
{
 Player p = e.getPlayer();
 if(!p.hasPermission("lobby.brakeblocks")&&ConfigManager.get("Config.yml").getBoolean("PlayerSettings.brakeblocks")==false){e.setCancelled(true);}	
}

@EventHandler
public void noBlockPlacing(BlockPlaceEvent e)
{
 Player p = e.getPlayer();
 if(!p.hasPermission("lobby.placeblocks")&&ConfigManager.get("Config.yml").getBoolean("PlayerSettings.placeblocks")==false){e.setCancelled(true);}
 else if(
		    p.getItemInHand().isSimilar(
		     nolore(Material.COMPASS, ConfigManager.get("Config.yml").getInt("JoinItems.items.brujula.amount")
			, ConfigManager.get("Config.yml").getInt("JoinItems.items.brujula.subid")
			, ConfigManager.get("Config.yml").getString("JoinItems.items.brujula.name").replaceAll("&", "§").replaceAll("%p", p.getName()))) 
		 
			|| 		 
			p.getItemInHand().getType()==Material.ENDER_CHEST&&p.getItemInHand().getItemMeta().getDisplayName()
			.equals(ConfigManager.get("Config.yml").getString("JoinItems.items.cofre.name").replaceAll("&", "§").replaceAll("%p", p.getName()))
		 
		   || 		 
		   p.getItemInHand().isSimilar(
			 nolore(Material.REDSTONE_COMPARATOR, ConfigManager.get("Config.yml").getInt("JoinItems.items.comparador.amount")
			, ConfigManager.get("Config.yml").getInt("JoinItems.items.comparador.subid")
			, ConfigManager.get("Config.yml").getString("JoinItems.items.comparador.name").replaceAll("&", "§").replaceAll("%p", p.getName())))
			
		  ||
		   p.getItemInHand().getType()==Material.SKULL_ITEM&&p.getItemInHand().getItemMeta().getDisplayName().contains(p.getName())
				 		 
		 )
 {e.setCancelled(true);}
}

@EventHandler
public void cancelPickUp(PlayerPickupItemEvent e)
{
 Player p = e.getPlayer();
 if(!p.hasPermission("lobby.pickup")&&ConfigManager.get("Config.yml").getBoolean("PlayerSettings.itempickup")==false){e.setCancelled(true);}	
}

@EventHandler
public void cancelDrop(PlayerDropItemEvent e)
{
 Player p = e.getPlayer();
 if(ConfigManager.get("Config.yml").getBoolean("PlayerSettings.itemdrop")==false){e.setCancelled(true);}	

}

@EventHandler
public void cancelInvMove(InventoryClickEvent e)
{
  Player p = (Player) e.getWhoClicked();
	if(e.getInventory().getType().equals(InventoryType.PLAYER)){}
	{
		if(!p.hasPermission("lobby.inmove") && (ConfigManager.get("Config.yml").getBoolean("PlayerSettings.inventorymove")==false))
				//&&(e.getCurrentItem() != null)&& (e.getCurrentItem().getType() != null))
		{
		  e.setCancelled(true);
	    }

		
	}
		
}	

@EventHandler
public void drag(InventoryDragEvent e)
{
Player p = (Player) e.getWhoClicked();
if (e.getType().equals(InventoryType.CREATIVE))
{
  if(!p.hasPermission("lobby.inmove") && ConfigManager.get("Config.yml").getBoolean("PlayerSettings.inventorymove")==false){e.setCancelled(true);}

}


}


@EventHandler
public void customJoinMessage(PlayerJoinEvent e)
{
 Player p = e.getPlayer();
 
 if(!p.hasPermission("lobby.join.vip")&&!p.hasPermission("lobby.join.elite")&&!p.hasPermission("lobby.join.ultra")&&!p.hasPermission("lobby.join.staff")&&!p.hasPermission("lobby.join.mataco")&&!p.hasPermission("lobby.join.youtuber")&&p.isOp()==false)
 {
	  e.setJoinMessage(null);
	  e.getPlayer().getInventory().setHelmet(new ItemStack(Material.AIR, 1));
	  for(Player players : Bukkit.getOnlinePlayers())
	  {
		if(!ConfigManager.get("Config.yml").getString("Messages.joinmessage.user").contains("%none"))
		{
		  players.sendMessage(ConfigManager.get("Config.yml").getString("Messages.joinmessage.user").replaceAll("&", "§").replaceAll("%p", p.getName()));
		}
	  } 
 } 
 else if(p.hasPermission("lobby.join.vip")&&p.isOp()==false)
 {
	  e.setJoinMessage(null);
	  e.getPlayer().getInventory().setHelmet(nolore(Material.STAINED_GLASS, 1, 5, "§a§lVIP"));
	  for(Player players : Bukkit.getOnlinePlayers())
	  {
		players.sendMessage(ConfigManager.get("Config.yml").getString("Messages.joinmessage.vip").replaceAll("&", "§").replaceAll("%p", p.getName()));
	  }
 }
 else if(p.hasPermission("lobby.join.elite")&&p.isOp()==false)
 {
	  e.setJoinMessage(null);
	  e.getPlayer().getInventory().setHelmet(nolore(Material.STAINED_GLASS, 1, 3, "§b§lELITE"));
	  for(Player players : Bukkit.getOnlinePlayers())
	  {
		players.sendMessage(ConfigManager.get("Config.yml").getString("Messages.joinmessage.elite").replaceAll("&", "§").replaceAll("%p", p.getName()));
	  }
 }
 else if(p.hasPermission("lobby.join.mataco")&&p.isOp()==false)
 {
	  e.setJoinMessage(null);
	  e.getPlayer().getInventory().setHelmet(nolore(Material.STAINED_GLASS, 1, 2, "§d§lMATACO"));
	  for(Player players : Bukkit.getOnlinePlayers())
	  {
		players.sendMessage(ConfigManager.get("Config.yml").getString("Messages.joinmessage.mataco").replaceAll("&", "§").replaceAll("%p", p.getName()));
	  }
 }
 else if(p.hasPermission("lobby.join.ultra")&&p.isOp()==false)
 {
	  e.setJoinMessage(null);
	  e.getPlayer().getInventory().setHelmet(nolore(Material.STAINED_GLASS, 1, 2, "§d§lULTRA"));
	  for(Player players : Bukkit.getOnlinePlayers())
	  {
		players.sendMessage(ConfigManager.get("Config.yml").getString("Messages.joinmessage.ultra").replaceAll("&", "§").replaceAll("%p", p.getName()));
	  }
 }
 else if(p.hasPermission("lobby.join.youtuber")&&p.isOp()==false)
 {
	  e.setJoinMessage(null);
	  e.getPlayer().getInventory().setHelmet(nolore(Material.STAINED_GLASS, 1, 14, "§c§lYOUTUBER"));
	  for(Player players : Bukkit.getOnlinePlayers())
	  {
		players.sendMessage(ConfigManager.get("Config.yml").getString("Messages.joinmessage.ultimate").replaceAll("&", "§").replaceAll("%p", p.getName()));
	  }
 }
 else if(p.hasPermission("lobby.join.staff") || p.isOp()==true)
 {
	  e.setJoinMessage(null);
	  e.getPlayer().getInventory().setHelmet(nolore(Material.BEACON, 1, 0, "§c§lSTAFF"));
	  for(Player players : Bukkit.getOnlinePlayers())
	  {
		players.sendMessage(ConfigManager.get("Config.yml").getString("Messages.joinmessage.staff").replaceAll("&", "§").replaceAll("%p", p.getName()));
	  }
	  
 }

}

@EventHandler
public void cancelLeaveMessage(PlayerQuitEvent e)
{e.setQuitMessage(null);e.getPlayer().setPlayerListName(e.getPlayer().getName());}

@SuppressWarnings("deprecation")
@EventHandler
public void setTitleAndTab(PlayerJoinEvent e)
{
	Player p = e.getPlayer();
	Utils.sendTabTitle(p, ConfigManager.get("Config.yml").getString("Tab.header").replaceAll("&", "§").replaceAll("%p", p.getName()) 
			, ConfigManager.get("Config.yml").getString("Tab.footer").replaceAll("&", "§").replaceAll("%p", p.getName())
			);
	
	Utils.sendFullTitle(p
			, ConfigManager.get("Config.yml").getInt("Messages.jointitle.fadein")*10
			, ConfigManager.get("Config.yml").getInt("Messages.jointitle.stay")*10
			, ConfigManager.get("Config.yml").getInt("Messages.jointitle.fadeout")*10
			, ConfigManager.get("Config.yml").getString("Messages.jointitle.title").replaceAll("&", "§").replaceAll("%p", p.getName())
			, ConfigManager.get("Config.yml").getString("Messages.jointitle.subtitle").replaceAll("&", "§").replaceAll("%p", p.getName())
			);
}

@EventHandler
public void customJoinItems(PlayerJoinEvent e)
{
	Player p = e.getPlayer();
	
    /*ItemStack book = new ItemStack(Material.WRITTEN_BOOK, ConfigManager.get("Config.yml").getInt("JoinItems.items.libro.amount"));
    
    BookMeta meta = (BookMeta) book.getItemMeta();
    meta.setTitle(ConfigManager.get("Config.yml").getString("JoinItems.items.libro.name").replaceAll("&", "§").replaceAll("%p", p.getName()));
    meta.setAuthor(ConfigManager.get("Config.yml").getString("JoinItems.items.libro.author").replaceAll("&", "§").replaceAll("%p", p.getName()));
    
    meta.addPage(ConfigManager.get("Config.yml").getString("Book.page1").replaceAll("&", "§").replaceAll("%p", p.getName()));
    meta.addPage(ConfigManager.get("Config.yml").getString("Book.page2").replaceAll("&", "§").replaceAll("%p", p.getName()));
    meta.addPage(ConfigManager.get("Config.yml").getString("Book.page3").replaceAll("&", "§").replaceAll("%p", p.getName()));
    meta.addPage(ConfigManager.get("Config.yml").getString("Book.page4").replaceAll("&", "§").replaceAll("%p", p.getName()));
    meta.addPage(ConfigManager.get("Config.yml").getString("Book.page5").replaceAll("&", "§").replaceAll("%p", p.getName()));
    meta.addPage(ConfigManager.get("Config.yml").getString("Book.page6").replaceAll("&", "§").replaceAll("%p", p.getName()));
    meta.addPage(ConfigManager.get("Config.yml").getString("Book.page7").replaceAll("&", "§").replaceAll("%p", p.getName()));
    meta.addPage(ConfigManager.get("Config.yml").getString("Book.page8").replaceAll("&", "§").replaceAll("%p", p.getName()));
    
    book.setItemMeta(meta);*/
    
    if(ConfigManager.get("Config.yml").getBoolean("JoinItems.clear")==true){p.getInventory().clear();}
	
	//e.getPlayer().getInventory().setItem(ConfigManager.get("Config.yml").getInt("JoinItems.items.libro.slot"),book);
	
	p.getInventory().setItem(ConfigManager.get("Config.yml").getInt("JoinItems.items.brujula.slot")
			, nolore(Material.COMPASS, ConfigManager.get("Config.yml").getInt("JoinItems.items.brujula.amount")
			, ConfigManager.get("Config.yml").getInt("JoinItems.items.brujula.subid")
			, ConfigManager.get("Config.yml").getString("JoinItems.items.brujula.name").replaceAll("&", "§").replaceAll("%p", p.getName()))
			);
	
	p.getInventory().setItem(ConfigManager.get("Config.yml").getInt("JoinItems.items.cofre.slot")
			, nolore(Material.ENDER_CHEST, ConfigManager.get("Config.yml").getInt("JoinItems.items.cofre.amount")
			, ConfigManager.get("Config.yml").getInt("JoinItems.items.cofre.subid")
			, ConfigManager.get("Config.yml").getString("JoinItems.items.cofre.name").replaceAll("&", "§").replaceAll("%p", p.getName()))
			);
	
	p.getInventory().setItem(ConfigManager.get("Config.yml").getInt("JoinItems.items.comparador.slot")
			, nolore(Material.REDSTONE_COMPARATOR, ConfigManager.get("Config.yml").getInt("JoinItems.items.comparador.amount")
			, ConfigManager.get("Config.yml").getInt("JoinItems.items.comparador.subid")
			, ConfigManager.get("Config.yml").getString("JoinItems.items.comparador.name").replaceAll("&", "§").replaceAll("%p", p.getName()))
			);
	p.getInventory().setItem(ConfigManager.get("Config.yml").getInt("JoinItems.items.estrella.slot")
			, nolore(Material.NETHER_STAR, ConfigManager.get("Config.yml").getInt("JoinItems.items.estrella.amount")
			, ConfigManager.get("Config.yml").getInt("JoinItems.items.estrella.subid")
			, ConfigManager.get("Config.yml").getString("JoinItems.items.estrella.name").replaceAll("&", "§").replaceAll("%p", p.getName()))
			);
	p.getInventory().setItem(ConfigManager.get("Config.yml").getInt("JoinItems.items.cabeza.slot")
			, head(ConfigManager.get("Config.yml").getInt("JoinItems.items.cabeza.amount")
			, ConfigManager.get("Config.yml").getString("JoinItems.items.cabeza.name").replaceAll("&", "§").replaceAll("%p", p.getName())
			, p.getName())
			);
	p.getInventory().setItem(ConfigManager.get("Config.yml").getInt("JoinItems.items.rdmitem.slot")
			, nolore(Material.STAINED_GLASS_PANE, ConfigManager.get("Config.yml").getInt("JoinItems.items.rdmitem.amount")
			, 5
			, ConfigManager.get("Config.yml").getString("JoinItems.items.rdmitem.nameGreen").replaceAll("&", "§").replaceAll("%p", p.getName()))
			);
		
}


@EventHandler
public void ItemClickListener(PlayerInteractEvent e)
{
Player p = e.getPlayer();	
    
  if(e.getAction()==Action.RIGHT_CLICK_AIR || e.getAction()==Action.RIGHT_CLICK_BLOCK)
	{
	  if(p.getItemInHand().isSimilar(
			  nolore(Material.COMPASS, ConfigManager.get("Config.yml").getInt("JoinItems.items.brujula.amount")
			, ConfigManager.get("Config.yml").getInt("JoinItems.items.brujula.subid")
			, ConfigManager.get("Config.yml").getString("JoinItems.items.brujula.name").replaceAll("&", "§").replaceAll("%p", p.getName()))))
	  {
		//e.setCancelled(true);
		/*Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), 
				ConfigManager.get("Config.yml").getString("JoinItems.items.brujula.command").replaceAll("%p", p.getName())
				);*/
		p.performCommand("servers");
	  }	
	  
	  else if(p.getItemInHand().isSimilar(
			  nolore(Material.ENDER_CHEST, ConfigManager.get("Config.yml").getInt("JoinItems.items.cofre.amount")
			, ConfigManager.get("Config.yml").getInt("JoinItems.items.cofre.subid")
			, ConfigManager.get("Config.yml").getString("JoinItems.items.cofre.name").replaceAll("&", "§").replaceAll("%p", p.getName()))))
	  {
		//e.setCancelled(true);
		p.performCommand("uc menu");
	  }
	  
	  else if(p.getItemInHand().getType()==Material.SKULL_ITEM&&p.getItemInHand().getItemMeta().getDisplayName().contains(p.getName()))
	  {
		//e.setCancelled(true);
		p.performCommand("perfil");
	  }	
	  	  
	  else if(p.getItemInHand().isSimilar(
			      nolore(Material.REDSTONE_COMPARATOR, ConfigManager.get("Config.yml").getInt("JoinItems.items.comparador.amount")
				, ConfigManager.get("Config.yml").getInt("JoinItems.items.comparador.subid")
				, ConfigManager.get("Config.yml").getString("JoinItems.items.comparador.name").replaceAll("&", "§").replaceAll("%p", p.getName()))))
	  {
		  p.performCommand("options");
	  }
	  
	 /* else if(p.getItemInHand().isSimilar(
		      nolore(Material.EMERALD, ConfigManager.get("Config.yml").getInt("JoinItems.items.tienda.amount")
			, ConfigManager.get("Config.yml").getInt("JoinItems.items.tienda.subid")
			, ConfigManager.get("Config.yml").getString("JoinItems.items.tienda.name").replaceAll("&", "§").replaceAll("%p", p.getName()))))
      {
		  //p.sendMessage("§c§l>> §r§fFuncion no disponible §b§naun.");
		  //p.playSound(p.getLocation(), Sound.ITEM_BREAK, 10000,(float)0.6);
		  p.performCommand("inventory tienda");
      }	*/
	    
	  else if(p.getItemInHand().isSimilar(
			      nolore(Material.NETHER_STAR, ConfigManager.get("Config.yml").getInt("JoinItems.items.estrella.amount")
				, ConfigManager.get("Config.yml").getInt("JoinItems.items.estrella.subid")
				, ConfigManager.get("Config.yml").getString("JoinItems.items.estrella.name").replaceAll("&", "§").replaceAll("%p", p.getName()))))
	  {
		p.performCommand("lobbies");
	  }	
	  
	  else if(p.getItemInHand().isSimilar(
			      nolore(Material.STAINED_GLASS_PANE, ConfigManager.get("Config.yml").getInt("JoinItems.items.rdmitem.amount")
				, 5
				, ConfigManager.get("Config.yml").getString("JoinItems.items.rdmitem.nameGreen").replaceAll("&", "§").replaceAll("%p", p.getName()))))
	  {

		p.getInventory().setItemInHand(nolore(Material.STAINED_GLASS_PANE, 1, 3, ConfigManager.get("Config.yml").getString("JoinItems.items.rdmitem.nameBlue").replaceAll("&", "§").replaceAll("%p", p.getName())));
	    
	    FireworkEffect.Builder builder = FireworkEffect.builder(); 
	    FireworkEffect effect = builder.flicker(true).trail(false).with(FireworkEffect.Type.BURST).withColor(Color.LIME).build();
	    
	    CustomEntityFirework.spawn(p.getLocation(), effect, p);
        
        p.sendMessage(ConfigManager.get("Config.yml").getString("RandomItemMessage.emerald").replaceAll("&", "§").replaceAll("%p", p.getName()));
	  }	
	  
	  else if(p.getItemInHand().isSimilar(
		      nolore(Material.STAINED_GLASS_PANE, ConfigManager.get("Config.yml").getInt("JoinItems.items.rdmitem.amount")
			, 3
			, ConfigManager.get("Config.yml").getString("JoinItems.items.rdmitem.nameBlue").replaceAll("&", "§").replaceAll("%p", p.getName()))))
  {

	p.getInventory().setItemInHand(nolore(Material.STAINED_GLASS_PANE, 1, 4, ConfigManager.get("Config.yml").getString("JoinItems.items.rdmitem.nameYellow").replaceAll("&", "§").replaceAll("%p", p.getName())));
	  
    FireworkEffect.Builder builder = FireworkEffect.builder(); 
    FireworkEffect effect = builder.flicker(true).trail(false).with(FireworkEffect.Type.BURST).withColor(Color.AQUA).build();
    
    CustomEntityFirework.spawn(p.getLocation(), effect, p);
	
    p.sendMessage(ConfigManager.get("Config.yml").getString("RandomItemMessage.diamond").replaceAll("&", "§").replaceAll("%p", p.getName()));
	
  }	
	  
	  else if(p.getItemInHand().isSimilar(
		      nolore(Material.STAINED_GLASS_PANE, ConfigManager.get("Config.yml").getInt("JoinItems.items.rdmitem.amount")
			, 4
			, ConfigManager.get("Config.yml").getString("JoinItems.items.rdmitem.nameYellow").replaceAll("&", "§").replaceAll("%p", p.getName()))))
  {

	p.getInventory().setItemInHand(new ItemStack(Material.AIR , 1));
	
	p.sendMessage(ConfigManager.get("Config.yml").getString("RandomItemMessage.gold").replaceAll("&", "§").replaceAll("%p", p.getName()));
	
    FireworkEffect.Builder builder = FireworkEffect.builder(); 
    FireworkEffect effect = builder.flicker(true).trail(false).with(FireworkEffect.Type.BURST).withColor(Color.YELLOW).build();
    
    CustomEntityFirework.spawn(p.getLocation(), effect, p);
	
    //p.sendMessage(ConfigManager.get("Config.yml").getString("RandomItemMessage.gold").replaceAll("&", "§").replaceAll("%p", p.getName()));
    
  }		
  	
	
	}
}

//==============================================DOUBLE_JUMP===================================================

@EventHandler
public void DBonMove(PlayerMoveEvent event)
{
Player p = event.getPlayer();
PlayerInventory inv = p.getInventory();

	if((p.getGameMode()!=GameMode.CREATIVE)
		&&(p.getLocation().subtract(0,1,0).getBlock().getType()!=Material.AIR)
		&&(!p.isFlying())
		&&(!flyOn.contains(p))
		&&(dbJumpOn.contains(p))
		&&(p.hasPermission("lobby.doublejump"))
		)
	{
	  p.setAllowFlight(true);
	  
	}	

}

@EventHandler
public void DBonPlayerTooglerFligh(PlayerToggleFlightEvent e)
{
	Player p = e.getPlayer();
		
	if(p.getGameMode()==GameMode.CREATIVE || flyOn.contains(p))
		return;
	if(dbJumpOn.contains(p)&&p.hasPermission("lobby.doublejump"))
	{
	e.setCancelled(true);
	p.setAllowFlight(false);
	p.setFlying(false);
	p.setVelocity(p.getLocation().getDirection()
			.multiply(ConfigManager.get("Config.yml").getDouble("DoubleJump.multiply"))
			.setY(ConfigManager.get("Config.yml").getDouble("DoubleJump.up")));
	p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 10000, 1);
	}
	//else{e.setCancelled(true);}
		
}

//==============================================DOUBLE_JUMP===================================================


/*@EventHandler
public void stacker(PlayerInteractEntityEvent e)
{
	Player p = e.getPlayer();	
	Entity en = e.getRightClicked();
	
	if(p.getItemInHand().getType()==Material.AIR)
	{
	 p.setPassenger(en);		
	}

}

@EventHandler
public void stackerEject(EntityDamageByEntityEvent e)
{
 Entity p = e.getDamager();
 Entity en = e.getEntity();
 
 if(p instanceof Player)
 {
	 e.setCancelled(true);
	 p.eject();
 }

}*/

@EventHandler
public void blockCommands(PlayerCommandPreprocessEvent event)  
{
 Player p = event.getPlayer(); 
	if(       
    		   (event.getMessage().equals("/?") && !event.getPlayer().isOp())
    		||(event.getMessage().equals("/help") && !event.getPlayer().isOp())
    		||(event.getMessage().equals("/bukkit:plugins") && !event.getPlayer().isOp())
    		||(event.getMessage().equals("/help") && !event.getPlayer().isOp())
    		||(event.getMessage().equals("/bukkit:help") && !event.getPlayer().isOp())
    		||(event.getMessage().equals("/icanhasebukkit") && !event.getPlayer().isOp())
    		||(event.getMessage().equals("/ver") && !event.getPlayer().isOp())
    		||(event.getMessage().equals("/version") && !event.getPlayer().isOp())
    		||(event.getMessage().equals("/bukkit:ver") && !event.getPlayer().isOp())
    		||(event.getMessage().equals("/bukkit:about") && !event.getPlayer().isOp())
    		||(event.getMessage().equals("/bukkit:pl") && !event.getPlayer().isOp())
    		||(event.getMessage().contains("/bukkit:msg") && !event.getPlayer().isOp())
    		||(event.getMessage().contains("/bukkit:kill") && !event.getPlayer().isOp())
    		||(event.getMessage().contains("/bukkit:tell") && !event.getPlayer().isOp())
    		||(event.getMessage().equals("/tell") && !event.getPlayer().isOp())
    		||(event.getMessage().equals("/msg") && !event.getPlayer().isOp())
    		||(event.getMessage().equals("/bukkit:w") && !event.getPlayer().isOp())
    		||(event.getMessage().contains("/bukkit:me") && !event.getPlayer().isOp())
    		||(event.getMessage().contains("/me") && !event.getPlayer().isOp())
    		||(event.getMessage().equals("/pl") && !event.getPlayer().isOp())
    		||(event.getMessage().equals("/plugins") && !event.getPlayer().isOp())
    		||(event.getMessage().equals("/w") && !event.getPlayer().isOp())
    		||(event.getMessage().equals("/about") && !event.getPlayer().isOp())
    		||(event.getMessage().contains("/kill") && !event.getPlayer().isOp())
    	    ||(event.getMessage().equalsIgnoreCase("/fly")&&!event.getPlayer().hasPermission("lobby.fly"))
    	    ||(event.getMessage().equalsIgnoreCase("/setspawn")&&!event.getPlayer().hasPermission("lobby.adm"))
    	    ||(event.getMessage().equalsIgnoreCase("/superstick")&&!event.getPlayer().hasPermission("lobby.adm"))
    	    ||(event.getMessage().equalsIgnoreCase("/updatescb")&&!event.getPlayer().hasPermission("lobby.adm"))
    	    ||(event.getMessage().contains("/almas")&&!event.getPlayer().hasPermission("hiso.admin")) 
      ) 
    
    {
		event.setCancelled(true);
		p.sendMessage("Unknown command. Type \"/help\" for help.");
		
    }

}


@EventHandler
public void allow (BlockCanBuildEvent e)
{	
	e.setBuildable(true);	
}

@EventHandler
public void motd(PlayerJoinEvent e)
{
 Player p = e.getPlayer();
 for (String line : ConfigManager.get("Config.yml").getStringList("Motd.message")) {
	  p.sendMessage(line.replaceAll("&", "§").replaceAll("%p", p.getName()));
	}
		 
		 //.replaceAll("&", "§").replaceAll("%p", p.getName()));
}

/*@EventHandler
public void onClickProfileMenu(InventoryClickEvent e)
{
    if(e.getInventory().getName().equalsIgnoreCase(ConfigManager.get("Config.yml").getString("LobbyMenu.title").replaceAll("&", "§").replaceAll("%p", e.getWhoClicked().getName())))
    {
        if(e.getCurrentItem() != null && e.getCurrentItem().getType() != null)
        {
            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();
            
            if(e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName())
            {
            	
            }
        }
    }
}*/

@EventHandler
public void onClickProfileMenu(InventoryClickEvent e)
{
    if(e.getInventory().getName().equalsIgnoreCase(ConfigManager.get("Config.yml").getString("Perfil.menuTitle").replaceAll("&", "§").replaceAll("%p", e.getWhoClicked().getName())))
    {
        if(e.getCurrentItem() != null && e.getCurrentItem().getType() != null)
        {
            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();
            
            if(e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName())
            {
            	if(e.getSlot()==13){}
            	if(e.getSlot()==29){p.performCommand("stats");}
            	if(e.getSlot()==33){
            		p.sendMessage("§a§lLobby §8| §bFuncion §c§lNo disponible aun");
                p.playSound(p.getLocation(), Sound.ITEM_BREAK, 10000,(float)0.6);}
            }
        }
    }
}

@EventHandler
public void onClickStatsMenu(InventoryClickEvent e)
{
    if(e.getInventory().getName().equalsIgnoreCase(ConfigManager.get("Config.yml").getString("Estadisticas.menuTitle").replaceAll("&", "§").replaceAll("%p", e.getWhoClicked().getName())))
    {
        if(e.getCurrentItem() != null && e.getCurrentItem().getType() != null)
        {
            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();
            
            if(e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName())
            {
            	if(e.getSlot()==30){p.performCommand("perfil");}
            	if(e.getSlot()==31){
            	p.sendMessage("§a§lLobby §8| §bFuncion §c§lNo disponible aun");
                p.playSound(p.getLocation(), Sound.ITEM_BREAK, 10000,(float)0.6);
                }
            }
        }
    }
}

@EventHandler
public void onClickLobbyMenu(InventoryClickEvent e)
{
    if(e.getInventory().getName().equalsIgnoreCase(ConfigManager.get("Config.yml").getString("LobbyMenu.title").replaceAll("&", "§").replaceAll("%p", e.getWhoClicked().getName())))
    {
        if(e.getCurrentItem() != null && e.getCurrentItem().getType() != null)
        {
            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();
            
            if(e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName())
            {
        		if(e.getCurrentItem().getItemMeta().getDisplayName().contains("1"))
        		{
        			if(ConfigManager.get("Config.yml").getInt("LobbyMenu.lobby")==1)
        			{
        				p.playSound(p.getLocation(), Sound.ITEM_BREAK, 10000,(float)0.6);
        				p.sendMessage(ConfigManager.get("Config.yml").getString("LobbyMenu.errormessage").replaceAll("&", "§").replaceAll("%p", p.getName()));
        			}
        			else{
        			sendToServer(p, ConfigManager.get("Config.yml").getString("LobbyMenu.servers.lobby1"));
        			p.sendMessage(ConfigManager.get("Config.yml").getString("LobbyMenu.tpmessage").replaceAll("&", "§").replaceAll("%p", p.getName()));
        			}
        		}
        		if(e.getCurrentItem().getItemMeta().getDisplayName().contains("2"))
        		{
        			if(ConfigManager.get("Config.yml").getInt("LobbyMenu.lobby")==2)
        			{
        				p.playSound(p.getLocation(), Sound.ITEM_BREAK, 10000,(float)0.6);
        				p.sendMessage(ConfigManager.get("Config.yml").getString("LobbyMenu.errormessage").replaceAll("&", "§").replaceAll("%p", p.getName()));	
        			}
        			else{
        			sendToServer(p, ConfigManager.get("Config.yml").getString("LobbyMenu.servers.lobby2"));
        			p.sendMessage(ConfigManager.get("Config.yml").getString("LobbyMenu.tpmessage").replaceAll("&", "§").replaceAll("%p", p.getName()));
        			}
        		}
        		if(e.getCurrentItem().getItemMeta().getDisplayName().contains("3"))
        		{
        			if(ConfigManager.get("Config.yml").getInt("LobbyMenu.lobby")==3)
        			{
        				p.playSound(p.getLocation(), Sound.ITEM_BREAK, 10000,(float)0.6);
        				p.sendMessage(ConfigManager.get("Config.yml").getString("LobbyMenu.errormessage").replaceAll("&", "§").replaceAll("%p", p.getName()));    		
        			}
        			else{
        			sendToServer(p, ConfigManager.get("Config.yml").getString("LobbyMenu.servers.lobby3"));
        			p.sendMessage(ConfigManager.get("Config.yml").getString("LobbyMenu.tpmessage").replaceAll("&", "§").replaceAll("%p", p.getName()));
        			}
        		}
        		/*if(e.getCurrentItem().getItemMeta().getDisplayName().contains("4"))
        		{
        			if(ConfigManager.get("Config.yml").getInt("LobbyMenu.lobby")==4)
        			{
        				p.playSound(p.getLocation(), Sound.ITEM_BREAK, 10000,(float)0.6);
        				p.sendMessage(ConfigManager.get("Config.yml").getString("LobbyMenu.errormessage").replaceAll("&", "§").replaceAll("%p", p.getName()));
        			}
        			else{
        				p.performCommand("bts joinserver lobby4");
        			p.sendMessage(ConfigManager.get("Config.yml").getString("LobbyMenu.tpmessage").replaceAll("&", "§").replaceAll("%p", p.getName()));
        			}
        		}
        		if(e.getCurrentItem().getItemMeta().getDisplayName().contains("5"))
        		{
        			if(ConfigManager.get("Config.yml").getInt("LobbyMenu.lobby")==5)
        			{
        				p.playSound(p.getLocation(), Sound.ITEM_BREAK, 10000,(float)0.6);
        				p.sendMessage(ConfigManager.get("Config.yml").getString("LobbyMenu.errormessage").replaceAll("&", "§").replaceAll("%p", p.getName()));
        			}
        			else{
        				p.performCommand("bts joinserver lobby5");
        			p.sendMessage(ConfigManager.get("Config.yml").getString("LobbyMenu.tpmessage").replaceAll("&", "§").replaceAll("%p", p.getName()));
        			}
        		}
        		if(e.getCurrentItem().getItemMeta().getDisplayName().contains("6"))
        		{
        			if(ConfigManager.get("Config.yml").getInt("LobbyMenu.lobby")==6)
        			{
        				p.playSound(p.getLocation(), Sound.ITEM_BREAK, 10000,(float)0.6);
        				p.sendMessage(ConfigManager.get("Config.yml").getString("LobbyMenu.errormessage").replaceAll("&", "§").replaceAll("%p", p.getName()));
        			}
        			else{
        				p.performCommand("bts joinserver lobby6");
        			p.sendMessage(ConfigManager.get("Config.yml").getString("LobbyMenu.tpmessage").replaceAll("&", "§").replaceAll("%p", p.getName()));
        			}
        		}
        		if(e.getCurrentItem().getItemMeta().getDisplayName().contains("7"))
        		{
        			if(ConfigManager.get("Config.yml").getInt("LobbyMenu.lobby")==7)
        			{
        				p.playSound(p.getLocation(), Sound.ITEM_BREAK, 10000,(float)0.6);
        				p.sendMessage(ConfigManager.get("Config.yml").getString("LobbyMenu.errormessage").replaceAll("&", "§").replaceAll("%p", p.getName()));
        			}
        			else{
        				p.performCommand("bts joinserver lobby7");
        			p.sendMessage(ConfigManager.get("Config.yml").getString("LobbyMenu.tpmessage").replaceAll("&", "§").replaceAll("%p", p.getName()));
        			}
        		}
        		if(e.getCurrentItem().getItemMeta().getDisplayName().contains("8"))
        		{
        			if(ConfigManager.get("Config.yml").getInt("LobbyMenu.lobby")==8)
        			{
        				p.playSound(p.getLocation(), Sound.ITEM_BREAK, 10000,(float)0.6);
        				p.sendMessage(ConfigManager.get("Config.yml").getString("LobbyMenu.errormessage").replaceAll("&", "§").replaceAll("%p", p.getName()));
        			}
        			else{
        				p.performCommand("bts joinserver lobby8");
        			p.sendMessage(ConfigManager.get("Config.yml").getString("LobbyMenu.tpmessage").replaceAll("&", "§").replaceAll("%p", p.getName()));
        			}
        		}*/
            }
                      
        }
    }
}

@EventHandler
public void cancelBlockInteract(PlayerInteractEvent e)
{
	Player p = e.getPlayer();
	if(e.getAction()==Action.RIGHT_CLICK_BLOCK)
	{
	 if(ConfigManager.get("Config.yml").getBoolean("PlayerSettings.blockinteract")==false)
	 {
		if(p.hasPermission("lobby.blockinteract"))
		{
		 return;	
		}
		else
		{
			e.setCancelled(true);
		}
	 }
	}

}

@EventHandler
public void signsEffect(PlayerInteractEvent ev)
{
  Player p = ev.getPlayer();
  
  if(ev.getAction()==Action.RIGHT_CLICK_BLOCK)
  {
	  if(ev.getClickedBlock().getType()==Material.WALL_SIGN || ev.getClickedBlock().getType()==Material.SIGN_POST)
	  {  
	  Block cartel = ev.getClickedBlock().getLocation().getBlock();		  
	  Sign s = (Sign) cartel.getState().getData();
	  Block arcilla = cartel.getRelative(s.getAttachedFace());
	  
	    if(arcilla.getType()==Material.STAINED_CLAY || arcilla.getType()==Material.STAINED_GLASS)
	    {
	    	p.playSound(p.getLocation(), Sound.CLICK, 10000, 1);
	    	
	    	/*p.playEffect(cartel.getLocation(), Effect.CRIT, 0);
	    	p.playEffect(cartel.getLocation(), Effect.CRIT, 0);
	    	p.playEffect(cartel.getLocation(), Effect.CRIT, 0);
	    	p.playEffect(cartel.getLocation(), Effect.CRIT, 0);
	    	p.playEffect(cartel.getLocation(), Effect.CRIT, 0);
	    	p.playEffect(cartel.getLocation(), Effect.CRIT, 0);
	    	p.playEffect(cartel.getLocation(), Effect.CRIT, 0);
	    	p.playEffect(cartel.getLocation(), Effect.CRIT, 0);*/
	    	
	    	
	    	PacketPlayOutWorldParticles critParticles = new PacketPlayOutWorldParticles(EnumParticle.CRIT, false, (float)arcilla.getLocation().getX(),(float)arcilla.getLocation().getY(),(float)arcilla.getLocation().getZ(), (float)0.3, (float)0.3, (float)0.3, 0, 10, null);
	    	((CraftPlayer) p).getHandle().playerConnection.sendPacket(critParticles); 
	    	
	    	//PacketPlayOutWorldEvent packet = new PacketPlayOutWorldEvent(1001, 1, 1, 1, 11, false);
	    } 	  	  
	  }	  
  }
		
}

@EventHandler
public void onClickServersMenu(InventoryClickEvent e)
{
    if(e.getInventory().getName().equalsIgnoreCase(ConfigManager.get("Config.yml").getString("ServerMenu.title").replaceAll("&", "§").replaceAll("%p", e.getWhoClicked().getName())))
    {
        if(e.getCurrentItem() != null && e.getCurrentItem().getType() != null)
        {
            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();
            
            if(e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName())
            {
        	  e.setCancelled(true);	
      	           	    
        	    if(e.getSlot()==48)
        		{
        	    	World world = Bukkit.getServer().getWorld(ConfigManager.get("Config.yml").getString("ServerSettings.spawn.world"));
        	    	double x = ConfigManager.get("Config.yml").getDouble("ServerSettings.spawn.x");
        	    	double y = ConfigManager.get("Config.yml").getDouble("ServerSettings.spawn.y");
        	    	double z = ConfigManager.get("Config.yml").getDouble("ServerSettings.spawn.z");

        	    	double d = ConfigManager.get("Config.yml").getDouble("ServerSettings.spawn.pitch");
        	    	float pitch = (float) d;

        	    	double ya = ConfigManager.get("Config.yml").getDouble("ServerSettings.spawn.yaw");
        	    	float yaw = (float) ya;

        	    	p.teleport(new Location(world,x,y,z,yaw,pitch));
        		}
        	    else if (e.getSlot()==50)
           	    {
        	    	World world = Bukkit.getServer().getWorld(ConfigManager.get("Config.yml").getString("ServerSettings.spawn.world")); 
        	    	p.teleport(new Location(world,51.570,51,10.402,(float)-37.8,(float)-1.5));
           	    }
       	       else if (e.getSlot()==19)
        	    {
       	    	   sendToServer(p, ConfigManager.get("Config.yml").getString("ServerMenu.items.1.server"));
        	    }
        	    else if (e.getSlot()==21)
        	    {
        	    	sendToServer(p, ConfigManager.get("Config.yml").getString("ServerMenu.items.2.server"));
        	    }
        	    else if (e.getSlot()==23)
        	    {
        	    	sendToServer(p, ConfigManager.get("Config.yml").getString("ServerMenu.items.3.server"));
        	    }
        	    else if (e.getSlot()==25)
        	    {
        	    	sendToServer(p, ConfigManager.get("Config.yml").getString("ServerMenu.items.4.server"));
        	    }
            
            }          
        }
    }
}

@EventHandler
public void onClickOptionsMenu(InventoryClickEvent e)
{
    if(e.getInventory().getName().equalsIgnoreCase(ConfigManager.get("Config.yml").getString("OptionsMenu.title").replaceAll("&", "§").replaceAll("%p", e.getWhoClicked().getName())))
    {
        if(e.getCurrentItem() != null && e.getCurrentItem().getType() != null)
        {
            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();
            
            if(e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName())
            {
        	  e.setCancelled(true);	
            	
        	  if(e.getSlot()==10 && e.getCurrentItem().getItemMeta().getDisplayName()=="§c§lDESACTIVADO")
        	  {
        		 playersOff.add(p);
        		 playersRankOn.remove(p);
        		 playersOn.remove(p);
        		 
        		 for(Player players : Bukkit.getOnlinePlayers())
        		 {
        			 p.hidePlayer(players);
        		 }
        		 
				 p.playSound(p.getLocation(),Sound.ORB_PICKUP, 10000, (float)1.5);
        		 //p.closeInventory();
        		 setOptionsMenu(p);
        		 p.openInventory(optionsInv);
      	      }           
            } 
        	  if(e.getSlot()==13 && e.getCurrentItem().getItemMeta().getDisplayName()=="§c§lDESACTIVADO")
        	  {
        		 playersOff.remove(p);
        		 playersRankOn.add(p);
        		 playersOn.remove(p);
        		 
        		 for(Player players : Bukkit.getOnlinePlayers())
        		 {
        			if(players.hasPermission("lobby.visibility.rank"))
        			{p.showPlayer(players);}
        			else{p.hidePlayer(players);}
        		 }
        		 
				 p.playSound(p.getLocation(),Sound.ORB_PICKUP, 10000, (float)1.5);
        		 //p.closeInventory();
        		 setOptionsMenu(p);
        		 p.openInventory(optionsInv);
      	        }                      	
        	  if(e.getSlot()==16 && e.getCurrentItem().getItemMeta().getDisplayName()=="§c§lDESACTIVADO")
        	  {
        		 playersOff.remove(p);
        		 playersRankOn.remove(p);
        		 playersOn.add(p);
        		 
        		 for(Player players : Bukkit.getOnlinePlayers())
        		 {
                   p.showPlayer(players);
        		 }
        		 
				 p.playSound(p.getLocation(),Sound.ORB_PICKUP, 10000, (float)1.5);
        		 //p.closeInventory();
        		 setOptionsMenu(p);
        		 p.openInventory(optionsInv);
      	       }
        	  if(e.getSlot()==29 && e.getCurrentItem().getItemMeta().getDisplayName()=="§c§lDESACTIVADO")
        	  {
                 jumpOn.add(p);
        		 
                 p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 1,true));
        		 
				 p.playSound(p.getLocation(),Sound.ORB_PICKUP, 10000, (float)1.5);

        		 setOptionsMenu(p);
        		 p.openInventory(optionsInv);
      	       }
        	  if(e.getSlot()==29 && e.getCurrentItem().getItemMeta().getDisplayName()=="§a§lACTIVADO")
        	  {
                 jumpOn.remove(p);
        		 
                 for(PotionEffect effect : p.getActivePotionEffects())
                 {
                     p.removePotionEffect(effect.getType().JUMP);
                 }
        		 
				 p.playSound(p.getLocation(),Sound.ORB_PICKUP, 10000, (float)1.5);

        		 setOptionsMenu(p);
        		 p.openInventory(optionsInv);
      	       }
        	  if(e.getSlot()==31 && e.getCurrentItem().getItemMeta().getDisplayName()=="§c§lDESACTIVADO")
        	  {
                 speedOn.add(p);
        		 
                 p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1,true));
        		 
				 p.playSound(p.getLocation(),Sound.ORB_PICKUP, 10000, (float)1.5);

        		 setOptionsMenu(p);
        		 p.openInventory(optionsInv);
      	       }
        	  if(e.getSlot()==31 && e.getCurrentItem().getItemMeta().getDisplayName()=="§a§lACTIVADO")
        	  {
                 speedOn.remove(p);
        		 
                 for(PotionEffect effect : p.getActivePotionEffects())
                 {
                     p.removePotionEffect(effect.getType().SPEED);
                 }
        		 
				 p.playSound(p.getLocation(),Sound.ORB_PICKUP, 10000, (float)1.5);

        		 setOptionsMenu(p);
        		 p.openInventory(optionsInv);
      	       }
        	  if(e.getSlot()==33 && e.getCurrentItem().getItemMeta().getDisplayName()=="§c§lDESACTIVADO")
        	  {
                 nightOn.add(p);
                       		 
                 p.playSound(p.getLocation(),Sound.ORB_PICKUP, 10000, (float)1.5);
                 
                 p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 1,true));
                 
        		 setOptionsMenu(p);
        		 p.openInventory(optionsInv);
      	       }
        	  if(e.getSlot()==33 && e.getCurrentItem().getItemMeta().getDisplayName()=="§a§lACTIVADO")
        	  {
                 nightOn.remove(p);
        		 
				 p.playSound(p.getLocation(),Sound.ORB_PICKUP, 10000, (float)1.5);
				 
                 for(PotionEffect effect : p.getActivePotionEffects())
                 {
                     p.removePotionEffect(effect.getType().NIGHT_VISION);
                 }

        		 setOptionsMenu(p);
        		 p.openInventory(optionsInv);
      	       }
        	  if(e.getSlot()==48 && e.getCurrentItem().getItemMeta().getDisplayName()=="§c§lDESACTIVADO")
        	  {
                if(p.hasPermission("lobby.doublejump"))
                {
        		 dbJumpOn.add(p);
                 flyOn.remove(p);
                 p.setAllowFlight(false);p.setFlying(false);
        		 
                 p.playSound(p.getLocation(),Sound.ORB_PICKUP, 10000, (float)1.5);
                 
        		 setOptionsMenu(p);
        		 p.openInventory(optionsInv);
                }
                else{
                	p.playSound(p.getLocation(), Sound.ITEM_BREAK, 10000,(float)0.6);
                	p.sendMessage(ConfigManager.get("Config.yml").getString("OptionsMenu.messages.noperm").replaceAll("&", "§").replaceAll("%p", p.getName()));
                	
                }
      	       }
        	  if(e.getSlot()==48 && e.getCurrentItem().getItemMeta().getDisplayName()=="§a§lACTIVADO")
        	  {
                if(p.hasPermission("lobby.doublejump"))
                {
        		 dbJumpOn.remove(p);
                 flyOn.remove(p);
                 p.setAllowFlight(false);
        		 
				 p.playSound(p.getLocation(),Sound.ORB_PICKUP, 10000, (float)1.5);

        		 setOptionsMenu(p);
        		 p.openInventory(optionsInv);
                }
                else{
                	p.playSound(p.getLocation(), Sound.ITEM_BREAK, 10000,(float)0.6);
                	p.sendMessage(ConfigManager.get("Config.yml").getString("OptionsMenu.messages.noperm").replaceAll("&", "§").replaceAll("%p", p.getName()));              	
                }
      	       }
        	  if(e.getSlot()==50 && e.getCurrentItem().getItemMeta().getDisplayName()=="§c§lDESACTIVADO")
        	  {
        		 if(p.hasPermission("lobby.fly")){
        		  dbJumpOn.remove(p);
                 p.performCommand("fly");
        		 
                 p.playSound(p.getLocation(),Sound.ORB_PICKUP, 10000, (float)1.5);
                 
        		 setOptionsMenu(p);
        		 p.openInventory(optionsInv);
        		 }
                 else{
                 	p.playSound(p.getLocation(), Sound.ITEM_BREAK, 10000,(float)0.6);
                 	p.sendMessage(ConfigManager.get("Config.yml").getString("OptionsMenu.messages.noperm").replaceAll("&", "§").replaceAll("%p", p.getName()));
                 	
                 }
      	       }
        	  if(e.getSlot()==50 && e.getCurrentItem().getItemMeta().getDisplayName()=="§a§lACTIVADO")
        	  {
        		if(p.hasPermission("lobby.fly")){
        		 dbJumpOn.remove(p);
                 p.performCommand("fly");
        		 
				 p.playSound(p.getLocation(),Sound.ORB_PICKUP, 10000, (float)1.5);

        		 setOptionsMenu(p);
        		 p.openInventory(optionsInv);
        		}
                else{
                	p.playSound(p.getLocation(), Sound.ITEM_BREAK, 10000,(float)0.6);
                	p.sendMessage(ConfigManager.get("Config.yml").getString("OptionsMenu.messages.noperm").replaceAll("&", "§").replaceAll("%p", p.getName()));
                	
                } 
        	  }        
         }
    }
}

@EventHandler(priority = EventPriority.LOWEST)
public void SettingsOnJoin(PlayerJoinEvent e)
{
  Player p = e.getPlayer();  
	if(!somethingChange.contains(p.getName()))
	{
		playersOn.add(p);
	}
   if(!jumpOn.contains(p))
   {
       for(PotionEffect effect : p.getActivePotionEffects())
       {
           p.removePotionEffect(effect.getType().JUMP);
       }
   }
   if(!speedOn.contains(p))
   {
       for(PotionEffect effect : p.getActivePotionEffects())
       {
           p.removePotionEffect(effect.getType().SPEED);
       }
   }
   if(!nightOn.contains(p))
   {
       for(PotionEffect effect : p.getActivePotionEffects())
       {
           p.removePotionEffect(effect.getType().NIGHT_VISION);
       }
   }
		
if(playersOff.contains(p)){
  for(Player players: Bukkit.getOnlinePlayers())
  {
	p.hidePlayer(players);
	if(playersOff.contains(players)){players.hidePlayer(p);}
	else if(playersRankOn.contains(players)&&!p.hasPermission("lobby.visibility.rank")){players.hidePlayer(p);}
  }
}
if(playersRankOn.contains(p)){
	  for(Player players: Bukkit.getOnlinePlayers())
	  {
		if(players.hasPermission("lobby.visibility.rank"))
		{
			p.showPlayer(players);
		}
		else
		{
			p.hidePlayer(players);
		}
		
		if(playersOff.contains(players)){players.hidePlayer(p);}
		else if(playersRankOn.contains(players)&&!p.hasPermission("lobby.visibility.rank")){players.hidePlayer(p);}
	  }
	}
if(playersOn.contains(p)){
	  for(Player players: Bukkit.getOnlinePlayers())
	  {
		p.showPlayer(players);
		  
		if(playersOff.contains(players)){players.hidePlayer(p);}
		else if(playersRankOn.contains(players)&&!p.hasPermission("lobby.visibility.rank")){players.hidePlayer(p);}
	  }
	}
	
}


/*@EventHandler
public void onChat(PlayerChatEvent e)
{
 Player p = e.getPlayer();
 
 for(Player players : Bukkit.getOnlinePlayers())
 {
	 if(!chatOn.contains(players))
	 {
		 e.getRecipients().remove(players);
	 }
 }
 
 if(!chatOn.contains(p))
 {
  e.setCancelled(true);
  p.sendMessage("§a§l>> §c§lNO §fpuedes hablar si tienes el chat §b§ndesactivado");
 }
 
}*/

@EventHandler
public void getSuperStick(PlayerInteractEntityEvent event)
{
 Player p = event.getPlayer();
 if(p.getItemInHand().isSimilar(nolore(Material.STICK, 1, 0, "§a§l§kii §b§l§nSuperStick §r§7(Click derecho en entidad para eliminarla)")))
 {
   Entity en = event.getRightClicked();
   if(!(en instanceof Player))
   {
	   en.remove();
   }
	 
 }

}

@EventHandler
public void onExplode(EntityExplodeEvent e)
{
	Iterator<Block> bi = e.blockList().iterator();
	while (bi.hasNext())
	{
	if (bi.next().getType() != Material.TNT) bi.remove();
	}
	
}

@EventHandler
public void onInteractLuanchPads(PlayerInteractEvent e)
{
 Player p = e.getPlayer();
 	if(e.getAction()==Action.PHYSICAL && e.getClickedBlock().getType()==Material.GOLD_PLATE 
 		&& e.getClickedBlock().getLocation().getBlock().getRelative(BlockFace.DOWN).getType()==Material.REDSTONE_BLOCK)
 	{
 	 e.setCancelled(true);
 	   p.setVelocity(p.getLocation().getDirection()
 				.multiply(ConfigManager.get("Config.yml").getDouble("LaunchPads.foward"))
 				.setY(ConfigManager.get("Config.yml").getDouble("LaunchPads.up")));
 		p.playSound(p.getLocation(), Sound.FIREWORK_LARGE_BLAST, 10000, 1);
 	}
	
}

/*@EventHandler
public void InteractEmeraldLaunch(PlayerMoveEvent e)
{
	if(e.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType()==Material.EMERALD_BLOCK)
	{
		final Player p = e.getPlayer();
		 //e.setCancelled(true);
		 p.setVelocity(p.getVelocity()
		    .setZ(-ConfigManager.get("Config.yml").getDouble("EmeraldLaunch.foward"))
		 	.setY(ConfigManager.get("Config.yml").getDouble("EmeraldLaunch.up")));
		 
		 if(playsound==true)
		 {
			 playsound=false;
			 p.playSound(p.getLocation(), Sound.FIREWORK_LAUNCH, 10000, 1);
		 }
		 		 
		 Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			  public void run() {
				  playsound=true;
			  }
			}, 5L);
		 
		 	
	}
}*/

@EventHandler
public void onSheepEatGrass(EntityChangeBlockEvent e) {
EntityType a = EntityType.SHEEP;
if(e.getTo()==Material.DIRT){
e.setCancelled(true);	
 }
}

}
