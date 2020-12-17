package com.marquez.fileskrebirth.Effect;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.marquez.fileskrebirth.utils.FileUtil;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

public class EffFileEncoding extends Effect{
	private Expression<String> encoding;
    
    public boolean init(final Expression<?>[] arg0, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
        this.encoding = (Expression<String>)arg0[0];
        return true;
    }
    
    public String toString(@Nullable final Event arg0, final boolean arg1) {
        return "set file encoding";
    }
    
    protected void execute(final Event arg0) {
    	final String encoding = this.encoding.getSingle(arg0);
    	FileUtil.encoding = encoding;
    }
}