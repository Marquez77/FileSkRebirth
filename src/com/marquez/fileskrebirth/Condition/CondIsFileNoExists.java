package com.marquez.fileskrebirth.Condition;

import java.io.File;
import javax.annotation.*;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class CondIsFileNoExists extends Condition{

	private Expression<String> file;
	
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		this.file = (Expression<String>)arg0[0];
		return true;
	}

	public String toString(@Nullable Event arg0, boolean arg1) {
		return "file is not exists";
	}

	public boolean check(Event arg0) {
		final String file = this.file.getSingle(arg0);
		return !new File(file).exists();
	}
}
