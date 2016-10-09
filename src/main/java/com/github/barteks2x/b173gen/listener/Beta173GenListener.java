package com.github.barteks2x.b173gen.listener;

import com.github.barteks2x.b173gen.Generator;
import com.github.barteks2x.b173gen.ISimpleWorld;
import com.github.barteks2x.b173gen.config.WorldConfig;
import com.github.barteks2x.b173gen.oldgen.WorldGenBigTreeOld;
import com.github.barteks2x.b173gen.oldgen.WorldGenBirchTreeOld;
import com.github.barteks2x.b173gen.oldgen.WorldGenTaiga2Old;
import com.github.barteks2x.b173gen.oldgen.WorldGenTreeOld;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.material.MaterialData;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Beta173GenListener implements Listener {

    private final Generator plugin;
    private final Random r = new Random();

    public Beta173GenListener(Generator plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onWorldInit(WorldInitEvent event) {
        this.plugin.initWorld(event.getWorld());
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onStructureGrow(StructureGrowEvent event) {
        Location loc = event.getLocation();
        World world = loc.getWorld();
        WorldConfig cfg;
        if((cfg = plugin.getOrCreateWorldConfig(world.getName())) != null) {
            if(cfg.oldTreeGrowing) {
                //hack from b 1.7.3: temporarily set sapling block to air to allow tree to generate
                BlockState prevState = loc.getBlock().getState();
                loc.getBlock().setType(Material.AIR);
                BlockCollectingWorld fakeWorld = new BlockCollectingWorld(loc.getWorld());
                GrowthResult result = growTree(event.getSpecies(), loc, fakeWorld);
                switch(result) {
                    case SUCCESS:
                        event.getBlocks().clear();
                        event.getBlocks().addAll(fakeWorld.newStates.values());
                        break;
                    case FAIL:
                        event.setCancelled(true);
                        //fall-through
                    case IGNORE:
                        if(!prevState.update(true)) {
                            Generator.logger().severe("Couldn't reset sapling block after failed tree generation at " + loc);
                        }
                        break;
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(event == null || event.getItem() == null) {
            return;
        }
        if(event.getItem().getType() == Material.EYE_OF_ENDER) {
            Player player = event.getPlayer();
            World world = player.getLocation().getWorld();
            WorldConfig cfg;
            if((cfg = plugin.getOrCreateWorldConfig(world.getName())) != null) {
                event.setCancelled(true);
                player.sendMessage(cfg.eyeOfEnderMsg);
            }
        }
    }

    private GrowthResult growTree(TreeType type, Location loc, ISimpleWorld fakeWorld) {
        boolean result;
        switch(type) {
            case TREE:
                result = new WorldGenTreeOld().generate(fakeWorld, new Random(), loc.
                        getBlockX(), loc.getBlockY(), loc.getBlockZ());
                return result ? GrowthResult.SUCCESS : GrowthResult.FAIL;
            case BIG_TREE:
                result = new WorldGenBigTreeOld().generate(fakeWorld, new Random(), loc.
                        getBlockX(), loc.getBlockY(), loc.getBlockZ());
                return result ? GrowthResult.SUCCESS : GrowthResult.FAIL;
            case BIRCH:
                result = new WorldGenBirchTreeOld().generate(fakeWorld, new Random(), loc.
                        getBlockX(), loc.getBlockY(), loc.getBlockZ());
                return result ? GrowthResult.SUCCESS : GrowthResult.FAIL;
            case REDWOOD:
                result = new WorldGenTaiga2Old().generate(fakeWorld, new Random(), loc.
                        getBlockX(), loc.getBlockY(), loc.getBlockZ());
                return result ? GrowthResult.SUCCESS : GrowthResult.FAIL;
            default:
                return GrowthResult.IGNORE;
        }
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onCommand(ServerCommandEvent event){
        if(event.getCommand().equals("reload")){
            String color = ChatColor.RED.toString();
            event.getSender().sendMessage(color +"[173generator] detected using /reload command.");
            event.getSender().sendMessage(color + "/reload command is NOT supported by 173generator. It WILL cause issues. Restart your server.");
            event.getSender().sendMessage(color + "If you continue default world generator may be used");
        }
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onCommand(PlayerCommandPreprocessEvent event){
        if(event.getMessage().startsWith("/reload")){
            String color = ChatColor.RED.toString();
            event.getPlayer().sendMessage(color +"[173generator] detected using /reload command.");
            event.getPlayer().sendMessage(color + "/reload command is NOT supported by 173generator. It WILL cause issues. Restart your server.");
            event.getPlayer().sendMessage(color + "If you continue default world generator may be used");
        }
    }

    private enum GrowthResult {
        SUCCESS, FAIL, IGNORE
    }

    private static class BlockCollectingWorld implements ISimpleWorld {

        private World world;
        private Map<BlockPos, BlockState> newStates = new HashMap<>();

        BlockCollectingWorld(World bukkitWorld) {
            this.world = bukkitWorld;
        }

        @Override public Material getType(int x, int y, int z) {
            BlockPos pos = new BlockPos(x, y, z);
            BlockState state = newStates.get(pos);
            if(state != null) {
                return state.getType();
            }
            return world.getBlockAt(x, y, z).getType();
        }

        @Override public void setType(int x, int y, int z, Material material) {
            BlockState state = world.getBlockAt(x, y, z).getState();
            state.setType(material);
            newStates.put(new BlockPos(x, y, z), state);
        }

        @Override public boolean isEmpty(int x, int y, int z) {
            return getType(x, y, z) == Material.AIR;
        }

        @Override public int getBlockLight(int x, int y, int z) {
            return 0;//not needed
        }

        @Override public int getSkyLight(int x, int y, int z) {
            return 0;//not needed
        }

        @Override public BlockState getBlockState(int x, int y, int z) {
            BlockPos pos = new BlockPos(x, y, z);
            BlockState state = newStates.get(pos);
            if(state != null) {
                return state;
            }
            return world.getBlockAt(x, y, z).getState();
        }

        @Override public void setType(int x, int y, int z, Material type, MaterialData data) {
            BlockState state = world.getBlockAt(x, y, z).getState();
            state.setType(type);
            state.setData(data);
            newStates.put(new BlockPos(x, y, z), state);
        }

        @Override public int getHighestBlockYAt(int x, int z) {
            throw new UnsupportedOperationException("Not implemented");
        }

        private static class BlockPos {
            private int x;
            private int y;
            private int z;

            private BlockPos(int x, int y, int z) {
                this.x = x;
                this.y = y;
                this.z = z;
            }

            @Override public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                BlockPos blockPos = (BlockPos) o;

                if (x != blockPos.x) return false;
                if (y != blockPos.y) return false;
                return z == blockPos.z;

            }

            @Override public int hashCode() {
                int result = x;
                result = 31*result + y;
                result = 31*result + z;
                return result;
            }
        }
    }
}
