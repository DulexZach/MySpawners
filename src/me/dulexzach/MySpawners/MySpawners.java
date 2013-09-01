package me.dulexzach.MySpawners;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class MySpawners  extends JavaPlugin implements Listener{
	
	public final Logger logger = Logger.getLogger("Minecraft");
	
	public List<Player> cooldown = new ArrayList<Player>();
	
	@Override
	public void onDisable(){
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName () + " Has been DISABLED.");
	}
	
	@Override
	public void onEnable(){

		getServer().getPluginManager().registerEvents(this,this);
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName () + " Has been ENABLED!");
		getConfig().options().copyDefaults(true);
		saveConfig();
			
	
		}
	

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {	
		if(label.equalsIgnoreCase("MySpawners")){
			Player player = (Player) sender;
			player.sendMessage(ChatColor.GOLD + "You are currently using version 0.1 of MySpawners.");
			player.sendMessage(ChatColor.GOLD + "-Made by DulexZach (http://www.youtube.com/user/DulexZach)");
		}
		return false;
	}
	
	
	@EventHandler(priority=EventPriority.NORMAL)
	public void onBlockBreak(BlockBreakEvent e) {
		
		final Player player = e.getPlayer();
		
		if(player.hasPermission("myspawners.drop")) {
		Material block = e.getBlock().getType();
	
		if(block.equals(Material.MOB_SPAWNER)){
			
			if(player.getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH)){
				if(!cooldown.contains(player)){
				e.getPlayer().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.MOB_SPAWNER,1));
				cooldown.add(player);
			}
				getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable(){

					public void run() {
						cooldown.remove(player);
					}
					
				}, this.getConfig().getInt("Cooldown"));
			}

			
		}
		
        }
		
	}	
	
	}

