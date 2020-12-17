package com.marquez.fileskrebirth.Effect;

import java.io.File;
import java.io.IOException;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.marquez.fileskrebirth.utils.FileUtil;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

public class EffSetFile extends Effect{
	private Expression<Integer> line;
	private Expression<String> text;
	private Expression<String> file;

	public boolean init(final Expression<?>[] arg0, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
		this.line = (Expression<Integer>)arg0[0];
		this.file = (Expression<String>)arg0[1];
		this.text = (Expression<String>)arg0[2];
		return true;
	}

	public String toString(@Nullable final Event arg0, final boolean arg1) {
		return "set file text";
	}

	protected void execute(final Event arg0) {
		final Integer line = (Integer)this.line.getSingle(arg0);
		final String text = (String)this.text.getSingle(arg0);
		final File file = new File(this.file.getSingle(arg0));
		if(line == null || text == null || file == null || !file.exists()) {
			return;
		}
		try{
			String[] list = FileUtil.readFiles(file);
			if(line > list.length) {
				return;
			}
			list[line-1] = text;
			FileUtil.writeFile(file, list);
		}catch(IOException e) {
			Skript.error(e.getMessage());
		}
	}
}