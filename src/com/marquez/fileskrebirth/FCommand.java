package com.marquez.fileskrebirth;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FCommand implements CommandExecutor{
	
	public static HashMap<Player, List<Location>> hash = new HashMap<Player, List<Location>>();
	public static String prefix = "§b§l[FileSK] ";
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player)sender;
			if(label.equalsIgnoreCase("filesk")) {
				String[] update = Main.updateCheck();
				if(update[0].equals(Main.version)) update = null;
				if(p.isOp() && update != null) {
					p.sendMessage(prefix + "§b§lFileSK의 최신버전이 존재합니다!");
					p.sendMessage(prefix + "§a최신버전§7:§ev" + update[0] + " §c현재버전§7:§ev" + Main.version);
					p.sendMessage(prefix + "§6다운로드 링크 §7: §f" + update[1]);
					p.sendMessage(prefix + "§7(해당 메세지는 OP에게만 출력됩니다)");
				}else {
					p.sendMessage(prefix + "§a현재버전§7:§ev" + Main.version);
				}
			}
		}
		return true;
	}
}
