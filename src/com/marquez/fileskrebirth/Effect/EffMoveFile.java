package com.marquez.fileskrebirth.Effect;

import java.io.File;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

public class EffMoveFile extends Effect{
	private Expression<String> origin;
	private Expression<String> file;

	public boolean init(final Expression<?>[] arg0, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
		this.origin = (Expression<String>)arg0[0];
		this.file = (Expression<String>)arg0[1];
		return true;
	}

	public String toString(@Nullable final Event arg0, final boolean arg1) {
		return "move file";
	}

	protected void execute(final Event arg0) {
		final File origin = new File(this.origin.getSingle(arg0));
		final File file = new File(this.file.getSingle(arg0));
		if(origin == null || !origin.exists()) {
			return;
		}
		if(!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		origin.renameTo(file);
	}
}