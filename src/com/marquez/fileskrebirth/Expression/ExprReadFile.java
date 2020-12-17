package com.marquez.fileskrebirth.Expression;

import ch.njol.util.*;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.*;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;

import java.io.File;
import java.io.IOException;

import javax.annotation.*;
import org.bukkit.event.*;

import com.marquez.fileskrebirth.utils.FileUtil;

public class ExprReadFile extends SimpleExpression<String>{
	private Expression<String> file;
	private Expression<Integer> line;
	
	public Class<? extends String> getReturnType() {
        return String.class;
    }

	public boolean isSingle() {
		return true;
	}

	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		this.file = (Expression<String>)arg0[0];
		this.line = (Expression<Integer>)arg0[1];
		return true;
	}

	public String toString(@Nullable Event arg0, boolean arg1) {
		return "read file";
	}

	@Nullable
	protected String[] get(Event arg0) {
		final File file = new File(this.file.getSingle(arg0));
		final Integer line = this.line.getSingle(arg0);
		if(file == null || !file.exists()) {
			return null;
		}
		try {
			String[] list = FileUtil.readFiles(file);
			if(line > 1) return new String[] { list[line-1] };
			return list;
		}catch(IOException e) {
			Skript.error(e.getMessage());
		}
		return null;
	}
}