package com.github.barteks2x.b173gen.test.fakeimpl;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

public class BukkitInventoryStub implements Inventory {
    private final int size;
    private final ItemStack[] data;

    private BukkitInventoryStub(Builder builder) {
        this.size = builder.size;
        this.data = new ItemStack[size];
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public void setItem(int index, ItemStack item) {
        this.data[index] = item;
    }

    //UNIMPLEMENTED:

    @Override
    public int getMaxStackSize() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setMaxStackSize(int size) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getName() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ItemStack getItem(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public HashMap<Integer, ItemStack> addItem(ItemStack... items) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override
    public HashMap<Integer, ItemStack> removeItem(ItemStack... items) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override
    public ItemStack[] getContents() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setContents(ItemStack[] items) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override
    public ItemStack[] getStorageContents() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setStorageContents(ItemStack[] items) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(int materialId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Material material) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(ItemStack item) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(int materialId, int amount) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Material material, int amount) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(ItemStack item, int amount) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAtLeast(ItemStack item, int amount) {
        throw new UnsupportedOperationException();
    }

    @Override
    public HashMap<Integer, ? extends ItemStack> all(int materialId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public HashMap<Integer, ? extends ItemStack> all(Material material) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override
    public HashMap<Integer, ? extends ItemStack> all(ItemStack item) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int first(int materialId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int first(Material material) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int first(ItemStack item) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int firstEmpty() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(int materialId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(Material material) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(ItemStack item) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<HumanEntity> getViewers() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getTitle() {
        throw new UnsupportedOperationException();
    }

    @Override
    public InventoryType getType() {
        throw new UnsupportedOperationException();
    }

    @Override
    public InventoryHolder getHolder() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<ItemStack> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<ItemStack> iterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Location getLocation() {
        throw new UnsupportedOperationException();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int size;


        public Builder setSize(int size) {
            this.size = size;
            return this;
        }

        public BukkitInventoryStub build() {
            return new BukkitInventoryStub(this);
        }
    }
}
