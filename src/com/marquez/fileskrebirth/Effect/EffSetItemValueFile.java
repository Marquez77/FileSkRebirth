package com.marquez.fileskrebirth.Effect;

import java.io.File;
import java.io.IOException;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import com.marquez.fileskrebirth.utils.FileUtil;
import com.marquez.fileskrebirth.utils.StringUtil;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

public class EffSetItemValueFile extends Effect{
	private Expression<String> key;
	private Expression<ItemStack> value;
	private Expression<String> file;

	public boolean init(final Expression<?>[] arg0, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
		this.key = (Expression<String>)arg0[0];
		this.value = (Expression<ItemStack>)arg0[1];
		this.file = (Expression<String>)arg0[2];
		return true;
	}

	public String toString(@Nullable final Event arg0, final boolean arg1) {
		return "set file item key value";
	}

	protected void execute(final Event arg0) {
		final File file = new File(this.file.getSingle(arg0));
		final String key = (String)this.key.getSingle(arg0);
		final String value = StringUtil.itemToString((ItemStack)this.value.getSingle(arg0));
		try {
			FileUtil.setFile(file, key, value);
		} catch (IOException e) {
			Skript.error(e.getMessage());
		}
	}
}