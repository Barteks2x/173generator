package com.github.barteks2x.b173gen.test.fakeimpl;

import com.github.barteks2x.b173gen.test.util.BlockUtils;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.List;

public class BukkitBlockStub implements Block {
    private final BukkitWorldStub world;
    private final int x;
    private final int y;
    private final int z;
    private Material type;

    public BukkitBlockStub(BukkitWorldStub world, int x, int y, int z, Material type) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.type = type;
    }

    @Override
    public Material getType() {
        return type;
    }

    @Override
    public void setType(Material material) {
        this.type = material;
        this.world.setTypeDirectly(x, y, z, type);
    }

    @Override
    public boolean isLiquid() {
        return type == Material.WATER || type == Material.STATIONARY_WATER || type == Material.LAVA || type == Material.STATIONARY_LAVA;
    }

    //approximate value, heightmap only. Causes some differences
    @Override
    public byte getLightFromSky() {
        int light = 15;
        for(int y = 127; y >= this.y; y--) {
            int opacity = BlockUtils.getOpacity(world.getBlockAt(x, y, z).getType());
            light -= opacity;
            if(opacity < 0) {
                light = 0;
                break;
            }
        }

        return (byte) light;
    }

    @Override
    public boolean isEmpty() {
        return type == Material.AIR;
    }

    @Override
    public BlockState getState() {
        //TODO: Add more cases as needed
        switch(type) {
            case MOB_SPAWNER:
                return new BukkitCreatureSpawnerStub();
            case CHEST:
                return new BukkitChestStub();
            default:
                throw new UnsupportedOperationException();
        }
    }


    //----------------------------
    //    UNIMPLEMENTED
    //----------------------------

    @Override
    public byte getData() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Block getRelative(int i, int i1, int i2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Block getRelative(BlockFace blockFace) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Block getRelative(BlockFace blockFace, int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getTypeId() {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte getLightLevel() {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte getLightFromBlocks() {
        throw new UnsupportedOperationException();
    }

    @Override
    public World getWorld() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getX() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getY() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getZ() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Location getLocation() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Location getLocation(Location location) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Chunk getChunk() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setData(byte b) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setData(byte b, boolean b1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setType(Material material, boolean b) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean setTypeId(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean setTypeId(int i, boolean b) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean setTypeIdAndData(int i, byte b, boolean b1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public BlockFace getFace(Block block) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Biome getBiome() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setBiome(Biome biome) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isBlockPowered() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isBlockIndirectlyPowered() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isBlockFacePowered(BlockFace blockFace) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isBlockFaceIndirectlyPowered(BlockFace blockFace) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getBlockPower(BlockFace blockFace) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getBlockPower() {
        throw new UnsupportedOperationException();
    }

    @Override
    public double getTemperature() {
        throw new UnsupportedOperationException();
    }

    @Override
    public double getHumidity() {
        throw new UnsupportedOperationException();
    }

    @Override
    public PistonMoveReaction getPistonMoveReaction() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean breakNaturally() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean breakNaturally(ItemStack itemStack) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<ItemStack> getDrops() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<ItemStack> getDrops(ItemStack itemStack) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setMetadata(String s, MetadataValue metadataValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<MetadataValue> getMetadata(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasMetadata(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeMetadata(String s, Plugin plugin) {
        throw new UnsupportedOperationException();
    }

}
