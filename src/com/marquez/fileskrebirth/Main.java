package com.marquez.fileskrebirth;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.marquez.fileskrebirth.Condition.CondIsDirectory;
import com.marquez.fileskrebirth.Condition.CondIsFile;
import com.marquez.fileskrebirth.Condition.CondIsFileExists;
import com.marquez.fileskrebirth.Condition.CondIsFileNoExists;
import com.marquez.fileskrebirth.Condition.CondIsNoDirectory;
import com.marquez.fileskrebirth.Condition.CondIsNoFile;
import com.marquez.fileskrebirth.Effect.EffAddFile;
import com.marquez.fileskrebirth.Effect.EffCopyFile;
import com.marquez.fileskrebirth.Effect.EffCreateDirectory;
import com.marquez.fileskrebirth.Effect.EffCreateFile;
import com.marquez.fileskrebirth.Effect.EffDeleteFile;
import com.marquez.fileskrebirth.Effect.EffDownloadFile;
import com.marquez.fileskrebirth.Effect.EffFileEncoding;
import com.marquez.fileskrebirth.Effect.EffMoveFile;
import com.marquez.fileskrebirth.Effect.EffRemoveFile;
import com.marquez.fileskrebirth.Effect.EffRenameFile;
import com.marquez.fileskrebirth.Effect.EffSetBlockValueFile;
import com.marquez.fileskrebirth.Effect.EffSetBoolValueFile;
import com.marquez.fileskrebirth.Effect.EffSetFile;
import com.marquez.fileskrebirth.Effect.EffSetIntValueFile;
import com.marquez.fileskrebirth.Effect.EffSetItemValueFile;
import com.marquez.fileskrebirth.Effect.EffSetLocValueFile;
import com.marquez.fileskrebirth.Effect.EffSetNumValueFile;
import com.marquez.fileskrebirth.Effect.EffSetValueFile;
import com.marquez.fileskrebirth.Effect.EffSetWorldValueFile;
import com.marquez.fileskrebirth.Effect.EffUnZipFile;
import com.marquez.fileskrebirth.Effect.EffZipFile;
import com.marquez.fileskrebirth.Expression.ExprFileCreateTime;
import com.marquez.fileskrebirth.Expression.ExprFileLastModifyTime;
import com.marquez.fileskrebirth.Expression.ExprFileLastReadTime;
import com.marquez.fileskrebirth.Expression.ExprFileList;
import com.marquez.fileskrebirth.Expression.ExprFileName;
import com.marquez.fileskrebirth.Expression.ExprFileParent;
import com.marquez.fileskrebirth.Expression.ExprFileSize;
import com.marquez.fileskrebirth.Expression.ExprGetBlockValueFile;
import com.marquez.fileskrebirth.Expression.ExprGetBoolValueFile;
import com.marquez.fileskrebirth.Expression.ExprGetIntValueFile;
import com.marquez.fileskrebirth.Expression.ExprGetItemValueFile;
import com.marquez.fileskrebirth.Expression.ExprGetLocValueFile;
import com.marquez.fileskrebirth.Expression.ExprGetNumValueFile;
import com.marquez.fileskrebirth.Expression.ExprGetValueFile;
import com.marquez.fileskrebirth.Expression.ExprGetWorldValueFile;
import com.marquez.fileskrebirth.Expression.ExprReadFile;
import com.marquez.fileskrebirth.utils.YamlConfig;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Main extends JavaPlugin implements Listener
{
	public static String prefix = "§f§l[FileSK]";
	public static YamlConfig config;

	public static Main instance;
	public static String version;
	public FCommand command;
	public String[] update;

	public void onEnable() {
		instance = this;
		update = updateCheck();
		version = getDescription().getVersion();
		Bukkit.getConsoleSender().sendMessage(prefix + " §a플러그인 활성화 " + "§ev" + getDescription().getVersion() + " §8-Made by Mar(마르)");
		getServer().getPluginManager().registerEvents(this, this);
		Skript.registerAddon((JavaPlugin)this);
		this.registerEffects();
		this.registerExpressions();
		this.registerConditions();
		registercommand();
		Bukkit.getConsoleSender().sendMessage(prefix + " §a에드온 연결 완료 §8-Made by Mar(마르)");
		
		if(!getDataFolder().exists()) getDataFolder().mkdir();
		File file = new File(getDataFolder() + "/config.yml");
		config = YamlConfig.loadConfiguration(file);
		if(!file.exists()) {
			try {
				file.createNewFile();
				config.set("file creation maximum", 10);
				config.set("file deletion maximum", 10);
				config.save(file);
			}catch(Exception e) {}
		}
	}

	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(prefix + " §c플러그인 비활성화 " + "§ev" + getDescription().getVersion() + " §8-Made by Mar(마르)");
	}

	public void registercommand() {
		command = new FCommand();
		getCommand("filesk").setExecutor(command);
	}



	public void registerEffects() {
		Skript.registerEffect((Class)EffFileEncoding.class, new String[] { "set [file ]encoding to %string%" });
		Skript.registerEffect((Class)EffSetValueFile.class, new String[] { "set[ ]f[ile] %string% to %string% at %string%" });
		Skript.registerEffect((Class)EffSetIntValueFile.class, new String[] { "set[ ]f[ile] int[eger] %string% to %integer% at %string%" });
		Skript.registerEffect((Class)EffSetNumValueFile.class, new String[] { "set[ ]f[ile] num[ber] %string% to %number% at %string%" });
		Skript.registerEffect((Class)EffSetBoolValueFile.class, new String[] { "set[ ]f[ile] bool[ean] %string% to %boolean% at %string%" });
		Skript.registerEffect((Class)EffSetBlockValueFile.class, new String[] { "set[ ]f[ile] block %string% to %block% at %string%" });
		Skript.registerEffect((Class)EffSetItemValueFile.class, new String[] { "set[ ]f[ile] item %string% to %itemstack% at %string%" });
		Skript.registerEffect((Class)EffSetLocValueFile.class, new String[] { "set[ ]f[ile] location %string% to %location% at %string%" });
		Skript.registerEffect((Class)EffSetWorldValueFile.class, new String[] { "set[ ]f[ile] world %string% to %world% at %string%" });
		Skript.registerEffect((Class)EffAddFile.class, new String[] { "add[ ]f[ile] %string% to %string%" });
		Skript.registerEffect((Class)EffSetFile.class, new String[] { "set[ ]f[ile] line %integer% of %string% to %string%" });
		Skript.registerEffect((Class)EffRemoveFile.class, new String[] { "remove[ ]f[ile] line %number% from %string%" });
		Skript.registerEffect((Class)EffCreateFile.class, new String[] { "create[ ]f[ile] %string%" });
		Skript.registerEffect((Class)EffCreateDirectory.class, new String[] { "create[ ]d[(ir|irectory)] %string%" });
		Skript.registerEffect((Class)EffDeleteFile.class, new String[] { "delete[ ]f[ile] %string%" });
		Skript.registerEffect((Class)EffRenameFile.class, new String[] { "rename[ ](f[ile]|d[(ir|irectory)]) %string% at %string%" });
		Skript.registerEffect((Class)EffMoveFile.class, new String[] { "move[ ](f[ile]|d[(ir|irectory)]) %string% to %string%" });
		Skript.registerEffect((Class)EffCopyFile.class, new String[] { "copy[ ](f[ile]|d[(ir|irectory)]) %string% to %string%" });
		Skript.registerEffect((Class)EffZipFile.class, new String[] { "zip[ ]f[ile] %string%" });
		Skript.registerEffect((Class)EffUnZipFile.class, new String[] { "unzip[ ]f[ile] %string%" });
		Skript.registerEffect((Class)EffDownloadFile.class, new String[] { "download[ ]f[ile] %string% to %string%" });
	}

	public void registerExpressions() {
		Skript.registerExpression((Class)ExprFileLastModifyTime.class, (Class)Long.class, ExpressionType.PROPERTY, new String[] { "file last modified time of %string%" });
		Skript.registerExpression((Class)ExprFileLastReadTime.class, (Class)Long.class, ExpressionType.PROPERTY, new String[] { "file last read time of %string%" });
		Skript.registerExpression((Class)ExprFileCreateTime.class, (Class)Long.class, ExpressionType.PROPERTY, new String[] { "file creat(ed|ion) time of %string%" });
		Skript.registerExpression((Class)ExprFileSize.class, (Class)Long.class, ExpressionType.PROPERTY, new String[] { "file size of %string%" });
		Skript.registerExpression((Class)ExprFileParent.class, (Class)String.class, ExpressionType.PROPERTY, new String[] { "parent[ file] of %string%" });
		Skript.registerExpression((Class)ExprFileName.class, (Class)String.class, ExpressionType.PROPERTY, new String[] { "file name of %string%" });
		Skript.registerExpression((Class)ExprGetValueFile.class, (Class)String.class, ExpressionType.PROPERTY, new String[] { "get[ ]f[ile] %string% at %string%" });
		Skript.registerExpression((Class)ExprGetIntValueFile.class, (Class)String.class, ExpressionType.PROPERTY, new String[] { "get[ ]f[ile] int[eger] %string% at %string%" });
		Skript.registerExpression((Class)ExprGetNumValueFile.class, (Class)String.class, ExpressionType.PROPERTY, new String[] { "get[ ]f[ile] num[ber] %string% at %string%" });
		Skript.registerExpression((Class)ExprGetBoolValueFile.class, (Class)String.class, ExpressionType.PROPERTY, new String[] { "get[ ]f[ile] bool[ean] %string% at %string%" });
		Skript.registerExpression((Class)ExprGetBlockValueFile.class, (Class)String.class, ExpressionType.PROPERTY, new String[] { "get[ ]f[ile] block %string% at %string%" });
		Skript.registerExpression((Class)ExprGetItemValueFile.class, (Class)String.class, ExpressionType.PROPERTY, new String[] { "get[ ]f[ile] item %string% at %string%" });
		Skript.registerExpression((Class)ExprGetLocValueFile.class, (Class)String.class, ExpressionType.PROPERTY, new String[] { "get[ ]f[ile] location %string% at %string%" });
		Skript.registerExpression((Class)ExprGetWorldValueFile.class, (Class)String.class, ExpressionType.PROPERTY, new String[] { "get[ ]f[ile] world %string% at %string%" });
		Skript.registerExpression((Class)ExprReadFile.class, (Class)String.class, ExpressionType.PROPERTY, new String[] { "read[ ]f[ile] %string% [at %integer%]" });
		Skript.registerExpression((Class)ExprFileList.class, (Class)String.class, ExpressionType.PROPERTY, new String[] { "f[ile][ ]l[ist] of %string%" });
		//리스트 절대위치로 나오도록 수정
	}

	public void registerConditions() {
		Skript.registerCondition((Class)CondIsFileExists.class, new String[] { "file %string% is exists" });
		Skript.registerCondition((Class)CondIsFileNoExists.class, new String[] { "file %string% is(n't| not) exists" });
		Skript.registerCondition((Class)CondIsFile.class, new String[] { "file %string% is file" });
		Skript.registerCondition((Class)CondIsNoFile.class, new String[] { "file %string% is(n't| not) file" });
		Skript.registerCondition((Class)CondIsDirectory.class, new String[] { "file %string% is dir[ectory]" });
		Skript.registerCondition((Class)CondIsNoDirectory.class, new String[] { "file %string% is(n't| not) dir[ectory]" });
		//Skript.registerCondition((Class)CondIsOther.class, new String[] { "file %string% is other" });
		//Skript.registerCondition((Class)CondIsNoOther.class, new String[] { "file %string% is(n't| not) other" });
	}

	@EventHandler
	public void check(final PlayerJoinEvent e) {
		new Thread() {
			public void run() {
				try {
					Player p = e.getPlayer();
					if(p.getName().contains("Marquez_")) {
						Thread.sleep(2000);
						p.sendMessage(prefix + " §b§l해당 서버는 FileSK v" + getDescription().getVersion() + " 플러그인을 사용중입니다.");
						p.sendMessage(prefix + " §b§l해당 서버는 FileSK v" + getDescription().getVersion() + " 플러그인을 사용중입니다.");
						p.sendMessage(prefix + " §b§l해당 서버는 FileSK v" + getDescription().getVersion() + " 플러그인을 사용중입니다.");
					}
				} catch (InterruptedException e) {
				}
			}
		}.start();
	}
	protected static String[] updateCheck() {
		try {
			URL url = new URL("http://marquezupdate.zz.am");
			HttpURLConnection uc = (HttpURLConnection)url.openConnection();
			uc.setRequestMethod("GET");
			uc.setRequestProperty("User-Agent", "Mozilla/5.0");
			BufferedReader r = new BufferedReader(new InputStreamReader(uc.getInputStream()));
			r.readLine(); r.readLine(); r.readLine(); r.readLine();
			String version = r.readLine();
			String download_url = r.readLine();
			return new String[] {version, download_url};
		}catch (Exception ex) {
		}
		return null;
	}
}