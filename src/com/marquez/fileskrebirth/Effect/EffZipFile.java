package com.marquez.fileskrebirth.Effect;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

public class EffZipFile extends Effect{
	private Expression<String> file;

	public boolean init(final Expression<?>[] arg0, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
		this.file = (Expression<String>)arg0[0];
		return true;
	}

	public String toString(@Nullable final Event arg0, final boolean arg1) {
		return "zip file";
	}

	protected void execute(final Event arg0) {
		final File file = new File(this.file.getSingle(arg0));
		if(file == null || !file.exists()) {
			return;
		}
		String zip = file.getParent() + "/" + file.getName() + ".zip";
		if(file.getParent() == null) zip = file.getName() + ".zip";
		try{
			zip(file.getPath(), zip);
		}catch(Exception e) {
			Skript.error(e.getMessage());
		}
	}

	public static void zip(String sourcePath, String output) throws Exception {
		File sourceFile = new File(sourcePath);
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		ZipOutputStream zos = null;
		try {
			fos = new FileOutputStream(output);
			bos = new BufferedOutputStream(fos);
			zos = new ZipOutputStream(bos);
			zos.setLevel(8);
			zipEntry(sourceFile, sourcePath, zos);
			zos.finish();
		} finally {
			if (zos != null) {
				zos.close();
			}
			if (bos != null) {
				bos.close();
			}
			if (fos != null) {
				fos.close();
			}
		}
	}
	
	private static void zipEntry(File sourceFile, String sourcePath, ZipOutputStream zos) throws Exception {
		if (sourceFile.isDirectory()) {
			if (sourceFile.getName().equalsIgnoreCase(".metadata")) {
				return;
			}
			File[] fileArray = sourceFile.listFiles();
			for (int i = 0; i < fileArray.length; i++) {
				zipEntry(fileArray[i], sourcePath, zos);
			}
		} else {
			BufferedInputStream bis = null;
			try {
				String sFilePath = sourceFile.getPath();
				String zipEntryName = sFilePath.substring(sourcePath.length() + 1, sFilePath.length());
				bis = new BufferedInputStream(new FileInputStream(sourceFile));
				ZipEntry zentry = new ZipEntry(zipEntryName);
				zentry.setTime(sourceFile.lastModified());
				zos.putNextEntry(zentry);
				byte[] buffer = new byte[2048];
				int cnt = 0;
				while ((cnt = bis.read(buffer, 0, 2048)) != -1) {
					zos.write(buffer, 0, cnt);
				}
				zos.closeEntry();
			} finally {
				if (bis != null) {
					bis.close();
				}
			}
		}
	}
}