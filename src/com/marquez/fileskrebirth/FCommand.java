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
	public static String prefix = "��b��l[FileSK] ";
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player)sender;
			if(label.equalsIgnoreCase("filesk")) {
				String[] update = Main.updateCheck();
				if(update[0].equals(Main.version)) update = null;
				if(p.isOp() && update != null) {
					p.sendMessage(prefix + "��b��lFileSK�� �ֽŹ����� �����մϴ�!");
					p.sendMessage(prefix + "��a�ֽŹ�����7:��ev" + update[0] + " ��c���������7:��ev" + Main.version);
					p.sendMessage(prefix + "��6�ٿ�ε� ��ũ ��7: ��f" + update[1]);
					p.sendMessage(prefix + "��7(�ش� �޼����� OP���Ը� ��µ˴ϴ�)");
				}else {
					p.sendMessage(prefix + "��a���������7:��ev" + Main.version);
				}
			}
		}
		return true;
	}
}
