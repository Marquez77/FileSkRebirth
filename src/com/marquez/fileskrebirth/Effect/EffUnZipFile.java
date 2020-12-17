package com.marquez.fileskrebirth.Effect;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

public class EffUnZipFile extends Effect{
	private Expression<String> file;

	public boolean init(final Expression<?>[] arg0, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
		this.file = (Expression<String>)arg0[0];
		return true;
	}

	public String toString(@Nullable final Event arg0, final boolean arg1) {
		return "unzip file";
	}

	protected void execute(final Event arg0) {
		final File file = new File(this.file.getSingle(arg0));
		if(file == null || !file.exists()) {
			return;
		}
		File unzip = new File(file.getParent() + "/" + file.getName().replace(".zip", ""));
		if(file.getParent() == null) unzip = new File(file.getName().replace(".zip", ""));
		
		unzip.mkdirs();
		
		try{
			unzip(file, unzip);
		}catch(Exception e) {
			Skript.error(e.getMessage());
		}
	}

	public static void unzip(File zipFile, File targetDir) throws Exception {
		FileInputStream fis = null;
		ZipInputStream zis = null;
		ZipEntry zentry = null;
		try {
			fis = new FileInputStream(zipFile);
			zis = new ZipInputStream(fis);
			while ((zentry = zis.getNextEntry()) != null) {
				String fileNameToUnzip = zentry.getName();
				File targetFile = new File(targetDir, fileNameToUnzip);
				if (zentry.isDirectory()) {
					targetFile.getAbsoluteFile().mkdirs();
				} else {
					targetFile.getParentFile().mkdirs();
					unzipEntry(zis, targetFile);
				}
			}
		} finally {
			if (zis != null) {
				zis.close();
			}
			if (fis != null) {
				fis.close();
			}
		}
	}
	
	protected static File unzipEntry(ZipInputStream zis, File targetFile) throws Exception {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(targetFile);
			byte[] buffer = new byte[2048];
			int len = 0;
			while ((len = zis.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
			}
		} finally {
			if (fos != null) {
				fos.close();
			}
		}
		return targetFile;
	}
}