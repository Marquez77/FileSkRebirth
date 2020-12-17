package com.marquez.fileskrebirth.Expression;

import java.io.File;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprFileName extends SimpleExpression<String>{
	private Expression<String> file;
	
	public Class<? extends String> getReturnType() {
        return String.class;
    }

	public boolean isSingle() {
		return true;
	}

	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		this.file = (Expression<String>)arg0[0];
		return true;
	}

	public String toString(@Nullable Event arg0, boolean arg1) {
		return "file name";
	}

	@Nullable
	protected String[] get(Event arg0) {
		final File file = new File(this.file.getSingle(arg0));
		if(file == null || !file.exists()) {
			return null;
		}
		return new String[] { file.getName() };
	}
}