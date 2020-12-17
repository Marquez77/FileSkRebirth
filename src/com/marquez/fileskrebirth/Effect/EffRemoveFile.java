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

public class EffRemoveFile extends Effect{
	private Expression<Number> line;
	private Expression<String> file;
    
    public boolean init(final Expression<?>[] arg0, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
        this.line = (Expression<Number>)arg0[0];
        this.file = (Expression<String>)arg0[1];
        return true;
    }
    
    public String toString(@Nullable final Event arg0, final boolean arg1) {
        return "remove file line";
    }
    
    protected void execute(final Event arg0) {
    	final Integer line = this.line.getSingle(arg0).intValue();
    	final File file = new File(this.file.getSingle(arg0));
    	if(line == null || file == null || !file.exists()) {
    		return;
    		
    	}
    	try{
	        String[] list = FileUtil.readFiles(file);
	        if(line < 1 || line >= list.length) {
	        	return;
	        }
	        list[line-1] = null;
	        FileUtil.writeFile(file, list);
    	}catch(IOException e) {
    		Skript.error(e.getMessage());
    	}
    }
}