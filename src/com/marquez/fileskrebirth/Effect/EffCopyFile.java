package com.marquez.fileskrebirth.Effect;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

public class EffCopyFile extends Effect{
	private Expression<String> origin;
	private Expression<String> file;

	public boolean init(final Expression<?>[] arg0, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
		this.origin = (Expression<String>)arg0[0];
		this.file = (Expression<String>)arg0[1];
		return true;
	}

	public String toString(@Nullable final Event arg0, final boolean arg1) {
		return "copy file";
	}

	protected void execute(final Event arg0) {
		final File origin = new File(this.origin.getSingle(arg0));
		final File file = new File(this.file.getSingle(arg0));
		if(origin == null || !origin.exists()) {
			return;
		}
		copy(origin, file);
	}
	
	public static void copyFile(File origin, File target) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			if(!target.exists()) target.createNewFile();
			fis = new FileInputStream(origin);
			fos = new FileOutputStream(target);
			byte[] b = new byte[4096];
			int cnt = 0;
			while((cnt=fis.read(b)) != -1){
				fos.write(b, 0, cnt);
			}
			fis.close();
			fos.close();
		} catch (Exception e) {
			Skript.error(e.getMessage());
		}
	}
	
	public static void copy(File sourceF, File targetF){
		if(!sourceF.isDirectory()) {
			copyFile(sourceF, targetF);
			return;
		}
		if(!targetF.exists()) targetF.mkdir();
		File[] ff = sourceF.listFiles();
		for (File file : ff) {
			File temp = new File(targetF.getAbsolutePath() + File.separator + file.getName());
			if(file.isDirectory()) {
				temp.mkdir();
				copy(file, temp);
			}else {
				copyFile(file, temp);
			}
		}

	}
}