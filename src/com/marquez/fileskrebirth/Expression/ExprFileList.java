package com.marquez.fileskrebirth.Expression;

import ch.njol.util.*;
import ch.njol.skript.lang.*;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;

import java.io.File;

import javax.annotation.*;
import org.bukkit.event.*;

public class ExprFileList extends SimpleExpression<String>{
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
		return "file list";
	}

	@Nullable
	protected String[] get(Event arg0) {
		final File file = new File(this.file.getSingle(arg0));
		if(file == null || !file.exists() || !file.isDirectory()) return null;
		File[] filelist = file.listFiles();
		String[] list = new String[filelist.length];
		for(int i = 0; i < filelist.length; i++) {
			list[i] = filelist[i].getPath();
		}
		return list;
	}
}