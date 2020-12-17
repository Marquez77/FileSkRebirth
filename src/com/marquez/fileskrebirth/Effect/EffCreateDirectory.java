package com.marquez.fileskrebirth.Effect;

import ch.njol.util.*;
import ch.njol.skript.lang.*;

import java.io.File;

import javax.annotation.*;

import org.bukkit.event.*;

public class EffCreateDirectory extends Effect{
	private Expression<String> file;
    
    public boolean init(final Expression<?>[] arg0, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
        this.file = (Expression<String>)arg0[0];
        return true;
    }
    
    public String toString(@Nullable final Event arg0, final boolean arg1) {
        return "create directory";
    }
    
    protected void execute(final Event arg0) {
    	final File file = new File(this.file.getSingle(arg0));
    	if(file == null || file.exists()) {
    		return;
    	}
        file.mkdirs();
    }
}