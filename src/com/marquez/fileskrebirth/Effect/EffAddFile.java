package com.marquez.fileskrebirth.Effect;

import java.io.File;
import java.io.IOException;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.marquez.fileskrebirth.utils.FileUtil;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

public class EffAddFile extends Effect{
	private Expression<String> text;
	private Expression<String> file;
    
    public boolean init(final Expression<?>[] arg0, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
        this.text = (Expression<String>)arg0[0];
        this.file = (Expression<String>)arg0[1];
        return true;
    }
    
    public String toString(@Nullable final Event arg0, final boolean arg1) {
        return "add file";
    }
    
    protected void execute(final Event arg0) {
    	final String text = (String)this.text.getSingle(arg0);
    	final File file = new File(this.file.getSingle(arg0));
    	if(text == null || file == null || !file.exists()) {
    		return;
    	}
        try {
			FileUtil.addFile(file, text);
		} catch (IOException e) {
			Skript.error(e.getMessage());
		}
    }
}