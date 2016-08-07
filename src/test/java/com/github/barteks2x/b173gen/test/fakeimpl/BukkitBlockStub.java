package com.github.barteks2x.b173gen.test.fakeimpl;

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

    //----------------------------
    //    UNIMPLEMENTED
    //----------------------------

    @Override
    public byte getData() {
        return 0;
    }

    @Override
    public Block getRelative(int i, int i1, int i2) {
        return null;
    }

    @Override
    public Block getRelative(BlockFace blockFace) {
        return null;
    }

    @Override
    public Block getRelative(BlockFace blockFace, int i) {
        return null;
    }

    @Override
    public int getTypeId() {
        return 0;
    }

    @Override
    public byte getLightLevel() {
        return 0;
    }

    @Override
    public byte getLightFromSky() {
        return 0;
    }

    @Override
    public byte getLightFromBlocks() {
        return 0;
    }

    @Override
    public World getWorld() {
        return null;
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public int getZ() {
        return 0;
    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public Location getLocation(Location location) {
        return null;
    }

    @Override
    public Chunk getChunk() {
        return null;
    }

    @Override
    public void setData(byte b) {

    }

    @Override
    public void setData(byte b, boolean b1) {

    }

    @Override
    public void setType(Material material, boolean b) {

    }

    @Override
    public boolean setTypeId(int i) {
        return false;
    }

    @Override
    public boolean setTypeId(int i, boolean b) {
        return false;
    }

    @Override
    public boolean setTypeIdAndData(int i, byte b, boolean b1) {
        return false;
    }

    @Override
    public BlockFace getFace(Block block) {
        return null;
    }

    @Override
    public BlockState getState() {
        return null;
    }

    @Override
    public Biome getBiome() {
        return null;
    }

    @Override
    public void setBiome(Biome biome) {

    }

    @Override
    public boolean isBlockPowered() {
        return false;
    }

    @Override
    public boolean isBlockIndirectlyPowered() {
        return false;
    }

    @Override
    public boolean isBlockFacePowered(BlockFace blockFace) {
        return false;
    }

    @Override
    public boolean isBlockFaceIndirectlyPowered(BlockFace blockFace) {
        return false;
    }

    @Override
    public int getBlockPower(BlockFace blockFace) {
        return 0;
    }

    @Override
    public int getBlockPower() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isLiquid() {
        return false;
    }

    @Override
    public double getTemperature() {
        return 0;
    }

    @Override
    public double getHumidity() {
        return 0;
    }

    @Override
    public PistonMoveReaction getPistonMoveReaction() {
        return null;
    }

    @Override
    public boolean breakNaturally() {
        return false;
    }

    @Override
    public boolean breakNaturally(ItemStack itemStack) {
        return false;
    }

    @Override
    public Collection<ItemStack> getDrops() {
        return null;
    }

    @Override
    public Collection<ItemStack> getDrops(ItemStack itemStack) {
        return null;
    }

    @Override
    public void setMetadata(String s, MetadataValue metadataValue) {

    }

    @Override
    public List<MetadataValue> getMetadata(String s) {
        return null;
    }

    @Override
    public boolean hasMetadata(String s) {
        return false;
    }

    @Override
    public void removeMetadata(String s, Plugin plugin) {

    }

}
