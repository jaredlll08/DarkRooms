package com.blamejared.darkrooms.core.common;

import net.minecraft.block.BlockBush;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.FlowerEntry;
import org.spongepowered.asm.mixin.*;

import java.util.*;

@Mixin(Biome.class)
public class MixinPlant {
    
    @Shadow(remap = false)
    protected List<FlowerEntry> flowers = new ArrayList<>();
    
    /**
     * @author Jaredlll08
     */
    @Overwrite(remap = false)
    public void plantFlower(World world, Random rand, BlockPos pos) {
        if(flowers.isEmpty())
            return;
        FlowerEntry flower = WeightedRandom.getRandomItem(rand, flowers);
        if(flower == null || flower.state == null) {
            return;
        }
        if((flower.state.getBlock() instanceof BlockBush && !((BlockBush) flower.state.getBlock()).canBlockStay(world, pos, flower.state)) || world.getLight(pos) == 0) {
            InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(flower.state.getBlock()));
        } else {
            
            world.setBlockState(pos, flower.state, 3);
        }
    }
}
