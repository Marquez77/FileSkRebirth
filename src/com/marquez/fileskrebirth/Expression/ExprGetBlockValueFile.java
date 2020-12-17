package com.marquez.fileskrebirth.Expression;

import java.io.File;
import java.io.IOException;

import javax.annotation.Nullable;

import org.bukkit.block.Block;
import org.bukkit.event.Event;

import com.marquez.fileskrebirth.utils.FileUtil;
import com.marquez.fileskrebirth.utils.StringUtil;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprGetBlockValueFile extends SimpleExpression<Block>{
	private Expression<String> key;
	private Expression<String> file;

	public Class<? extends Block> getReturnType() {
        return Block.class;
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
		return "get file block";
	}

	@Nullable
	protected Block[] get(Event arg0) {
		final File file = new File(this.file.getSingle(arg0));
		final String key = (String)this.key.getSingle(arg0);
		try{
			return new Block[] { StringUtil.stringToBlock(FileUtil.getFile(file, key)) };
		}catch(IOException e) {
			Skript.error(e.getMessage());
		}
		return null;
	}
}