package com.marquez.fileskrebirth.Expression;

import java.io.File;
import java.io.IOException;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.marquez.fileskrebirth.utils.FileUtil;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprGetIntValueFile extends SimpleExpression<Integer>{
	private Expression<String> key;
	private Expression<String> file;

	public Class<? extends Integer> getReturnType() {
        return Integer.class;
    }

	public boolean isSingle() {
		return true;
	}

	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		this.key = (Expression<String>)arg0[0];
		this.file = (Expression<String>)arg0[1];
		return true;
	}

	public String toString(@Nullable Event arg0, boolean arg1) {
		return "get file int";
	}

	@Nullable
	protected Integer[] get(Event arg0) {
		final File file = new File(this.file.getSingle(arg0));
		final String key = (String)this.key.getSingle(arg0);
		try{
			return new Integer[] { Integer.valueOf(FileUtil.getFile(file, key)) };
		}catch(IOException e) {
			Skript.error(e.getMessage());
		}
		return null;
	}
}