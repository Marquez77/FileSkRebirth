package com.marquez.fileskrebirth.Effect;

import ch.njol.util.*;
import ch.njol.skript.Skript;
import ch.njol.skript.command.ScriptCommandEvent;
import ch.njol.skript.lang.*;

import java.io.File;

import javax.annotation.*;

import org.bukkit.event.*;

import com.marquez.fileskrebirth.utils.FileUtil;

public class EffDeleteFile extends Effect{
	private Expression<String> file;
    
    public boolean init(final Expression<?>[] arg0, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
        this.file = (Expression<String>)arg0[0];
        return true;
    }
    
    public String toString(@Nullable final Event arg0, final boolean arg1) {
        return "delete file";
    }
    
    protected void execute(final Event arg0) {
    	final File file = new File(this.file.getSingle(arg0));
    	if(file == null || !file.exists()) {
    		return;
    	}
    	if(file.getAbsolutePath().contains("Windows")) {
    		if(arg0 instanceof ScriptCommandEvent) {
				ScriptCommandEvent e = (ScriptCommandEvent)arg0;
				Skript.warning("/" + e.getCommand() + " 명령어에서 윈도우 폴더에 파일 삭제를 시도하였습니다. [거부됨]");
				return;
			}
			Skript.warning(arg0.getEventName() + " 이벤트에서 윈도우 폴더의  파일 삭제를 시도하였습니다. [거부됨]");
			Skript.warning("파일 위치: " + file.getAbsolutePath());
			return;
		}
    	if(!FileUtil.canDeleteFile()) {
    		if(arg0 instanceof ScriptCommandEvent) {
				ScriptCommandEvent e = (ScriptCommandEvent)arg0;
				Skript.warning("/" + e.getCommand() + " 명령어에서 파일 제거 한도를 초과한 상태로 파일 제거를 시도하였습니다. [거부됨]");
				return;
			}
    		Skript.warning(arg0.getEventName() + " 이벤트에서 파일 제거 한도를 초과한 상태로 파일 제거를 시도하였습니다. [거부됨]");
			Skript.warning("파일 위치: " + file.getAbsolutePath());
			return;
    	}
    	if(file.isDirectory()) {
    		for(File f : file.listFiles()) {
    			if(!FileUtil.canDeleteFile()) {
    				if(arg0 instanceof ScriptCommandEvent) {
    					ScriptCommandEvent e = (ScriptCommandEvent)arg0;
    					Skript.warning("/" + e.getCommand() + " 파일 제거 한도를 초과한 상태로 파일 제거를 시도하였습니다. [거부됨]");
    					return;
    				}
    				Skript.warning(arg0.getEventName() + " 이벤트에서 파일 제거 한도를 초과한 상태로 파일 제거를 시도하였습니다. [거부됨]");
    				Skript.warning("파일 위치: " + f.getAbsolutePath());
    				return;
    			}
    			f.delete();
    		}
    	}
        file.delete();
    }
}