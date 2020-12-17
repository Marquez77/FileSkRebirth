package com.marquez.fileskrebirth.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.logging.Level;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class YamlConfig extends YamlConfiguration {
	
	@Override
	public String saveToString() {
		String data = new String();
		boolean first = true;
		for(String s : super.saveToString().split("\\\\u"))
		{
			if(s.length() >= 4 && !first)
			{
				data += (char)Integer.parseInt(s.substring(0, 4), 16);
				if(s.length() >= 5)
				{
					data += s.substring(4);
				}
			}
			else
			{
				data += s;
				first = false;
			}
		}
		return data;
	}
	
	@Override
	public void save(File file) throws IOException {
		Validate.notNull(file, "File cannot be null");
		Files.createParentDirs(file);
		String data = saveToString();
		try(Writer writer = new OutputStreamWriter(new FileOutputStream(file), Charsets.UTF_8))
		{
			writer.write(data);
		}
	}
	
	public static YamlConfig loadConfiguration(File file) {
		Validate.notNull(file, "File cannot be null");
		YamlConfig config = new YamlConfig();
		try
		{
			config.load(file);
		}
		catch(FileNotFoundException e)
		{
		}
		catch(IOException | InvalidConfigurationException e)
		{
			Bukkit.getLogger().log(Level.SEVERE, "Cannot load " + file, e);
		}
		return config;
	}
}
