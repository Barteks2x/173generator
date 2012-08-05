package org.orangecloud00.ptmbukkit;

import org.Barteks2x.b173gen.generator.ChunkProviderGenerate;

public class WorldConfig
{
	public PTMPlugin plugin;
	public ChunkProviderGenerate chunkProvider;
	public boolean isInit = false;

	public WorldConfig(PTMPlugin plug)
	{
	    this.plugin = plug;
	}

}