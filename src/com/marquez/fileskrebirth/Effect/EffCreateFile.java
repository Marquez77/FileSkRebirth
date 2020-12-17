package com.marquez.fileskrebirth.Effect;

import ch.njol.util.*;
import ch.njol.skript.Skript;
import ch.njol.skript.command.ScriptCommandEvent;
import ch.njol.skript.lang.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.annotation.*;

import org.bukkit.event.*;

import com.marquez.fileskrebirth.utils.FileUtil;

public class EffCreateFile extends Effect{
	private Expression<String> file;

	public boolean init(final Expression<?>[] arg0, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
		this.file = (Expression<String>)arg0[0];
		return true;
	}

	public String toString(@Nullable final Event arg0, final boolean arg1) {
		return "create file";
	}

	protected void execute(final Event arg0) {
		final File file = new File(this.file.getSingle(arg0));
		if(file == null || file.exists()) {
			return;
		}
		if(file.getPath().contains("Windows")) {
			if(arg0 instanceof ScriptCommandEvent) {
				ScriptCommandEvent e = (ScriptCommandEvent)arg0;
				Skript.warning("/" + e.getCommand() + " ��ɾ�� ������ ������ ���� ������ �õ��Ͽ����ϴ�. [�źε�]");
				return;
			}
			Skript.warning(arg0.getEventName() + " �̺�Ʈ���� ������ ������ ���� ������ �õ��Ͽ����ϴ�. [�źε�]");
			return;
		}
		if(!FileUtil.canCreateFile()) {
			if(arg0 instanceof ScriptCommandEvent) {
				ScriptCommandEvent e = (ScriptCommandEvent)arg0;
				Skript.warning("/" + e.getCommand() + " ��ɾ�� ���� ���� �ѵ��� �ʰ��� ���·� ���� ������ �õ��Ͽ����ϴ�. [�źε�]");
				return;
			}
    		Skript.warning(arg0.getEventName() + " �̺�Ʈ���� ���� ���� �ѵ��� �ʰ��� ���·� ���� ������ �õ��Ͽ����ϴ�. [�źε�]");
			Skript.warning("���� ��ġ: " + file.getAbsolutePath());
			return;
    	}
		try {
			if(file.getParentFile() != null && !file.getParentFile().exists()) file.getParentFile().mkdirs();
			file.createNewFile();
		} catch (IOException e) {
			Skript.error(e.getMessage());
		}
	}
}