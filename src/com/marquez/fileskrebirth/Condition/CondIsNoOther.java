package com.marquez.fileskrebirth.Condition;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class CondIsNoOther extends Condition{

	private Expression<String> file;
	
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		this.file = (Expression<String>)arg0[0];
		return true;
	}

	public String toString(@Nullable Event arg0, boolean arg1) {
		return "file is not other";
	}

	public boolean check(Event arg0) {
		final File file = new File(this.file.getSingle(arg0));
		if(file == null || !file.exists()) {
			return false;
		}
		Path path = Paths.get(file.getPath());
		try {
			BasicFileAttributes attrb = Files.readAttributes(path, BasicFileAttributes.class);
			return attrb.isOther();
		} catch (IOException e) {
			Skript.error(e.getMessage());
		}
		return false;
	}
}
