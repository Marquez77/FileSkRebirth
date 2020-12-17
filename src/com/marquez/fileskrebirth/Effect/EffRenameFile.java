package com.marquez.fileskrebirth.Effect;

import java.io.File;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

public class EffRenameFile extends Effect{
	private Expression<String> rename;
	private Expression<String> file;

	public boolean init(final Expression<?>[] arg0, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
		this.rename = (Expression<String>)arg0[0];
		this.file = (Expression<String>)arg0[1];
		return true;
	}

	public String toString(@Nullable final Event arg0, final boolean arg1) {
		return "rename file";
	}

	protected void execute(final Event arg0) {
		final String rename = this.rename.getSingle(arg0);
		final File file = new File(this.file.getSingle(arg0));
		if(file == null || !file.exists()) {
			return;
		}
		File changed = new File(file.getParent() + "/" + rename);
		file.renameTo(changed);
	}
}