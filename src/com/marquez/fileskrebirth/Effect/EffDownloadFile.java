package com.marquez.fileskrebirth.Effect;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

public class EffDownloadFile extends Effect{
	private Expression<String> url;
	private Expression<String> file;

	public boolean init(final Expression<?>[] arg0, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
		this.url = (Expression<String>)arg0[0];
		this.file = (Expression<String>)arg0[1];
		return true;
	}

	public String toString(@Nullable final Event arg0, final boolean arg1) {
		return "download file";
	}

	protected void execute(final Event arg0) {
		final String url = this.url.getSingle(arg0);
		final File file = new File(this.file.getSingle(arg0));
		if(file == null || file.exists()) {
			return;
		}
		new Thread() {
			public void run() {
				try{
					download(url, file);
				}catch(Exception e) {
					Skript.error(e.getMessage());
				}
			}
		}.start();
	}

	public void download(String url, File output) throws Exception{
		URL u = new URL(url);
		InputStream in = u.openStream();
		OutputStream out = new FileOutputStream(output);
		int data;
		while((data = in.read()) != -1) out.write(data);
		in.close();
		out.close();
	}
}