package com.blamejared.darkrooms.core.common;

import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.*;

import java.util.Random;

@Mixin(BlockGrass.class)
public class MixinGrass {
    
    /**
     * @author Jaredlll08
     */
    @Overwrite()
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        BlockPos blockpos = pos.up();
        
        for(int i = 0; i < 128; ++i) {
            BlockPos blockpos1 = blockpos;
            int j = 0;
            
            while(true) {
                if(j >= i / 16) {
                    if(worldIn.isAirBlock(blockpos1)) {
                        if(rand.nextInt(8) == 0) {
                            worldIn.getBiome(blockpos1).plantFlower(worldIn, rand, blockpos1);
                        } else {
                            IBlockState iblockstate1 = Blocks.TALLGRASS.getDefaultState().withProperty(BlockTallGrass.TYPE, BlockTallGrass.EnumType.GRASS);
                            
                            if(Blocks.TALLGRASS.canBlockStay(worldIn, blockpos1, iblockstate1) && worldIn.getLight(blockpos1) > 0) {
                                worldIn.setBlockState(blockpos1, iblockstate1, 3);
                            } else {
                                NonNullList<ItemStack> drops = NonNullList.create();
                                iblockstate1.getBlock().getDrops(drops, worldIn, blockpos, iblockstate1, 0);
                                for(ItemStack drop : drops) {
                                    InventoryHelper.spawnItemStack(worldIn, blockpos1.getX(), blockpos1.getY(), blockpos1.getZ(), drop);
                                }
                            }
                        }
                    }
                    
                    break;
                }
                
                blockpos1 = blockpos1.add(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);
                
                if(worldIn.getBlockState(blockpos1.down()).getBlock() != Blocks.GRASS || worldIn.getBlockState(blockpos1).isNormalCube()) {
                    break;
                }
                
                ++j;
            }
        }
    }
}
