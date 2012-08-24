package org.Barteks2x.b173gen.plugin;

import org.Barteks2x.b173gen.generator.ChunkProviderGenerate;

public class WorldConfig
{
	public Generator plugin;
	public ChunkProviderGenerate chunkProvider;
	public boolean isInit = false;

	public WorldConfig(Generator plug)
	{
	    this.plugin = plug;
	}

}