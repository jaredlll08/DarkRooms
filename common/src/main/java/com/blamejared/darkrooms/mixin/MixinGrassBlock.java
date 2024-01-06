package com.blamejared.darkrooms.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Mixin(GrassBlock.class)
public abstract class MixinGrassBlock extends SpreadingSnowyDirtBlock {
    
    protected MixinGrassBlock(Properties $$0) {
        
        super($$0);
    }
    
    @Unique
    private final Set<BlockPos> darkrooms$toDestroy = new HashSet<>();
    
    @ModifyArg(method = "performBonemeal", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/placement/PlacedFeature;place(Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/chunk/ChunkGenerator;Lnet/minecraft/util/RandomSource;Lnet/minecraft/core/BlockPos;)Z"))
    public BlockPos darkrooms$capturePos(BlockPos pos) {
        
        darkrooms$toDestroy.add(pos);
        return pos;
    }
    
    @Inject(method = "performBonemeal", at = @At(value = "TAIL"))
    public void darkrooms$destroyBlocks(ServerLevel level, RandomSource $$1, BlockPos $$2, BlockState $$3, CallbackInfo ci) {
        
        Iterator<BlockPos> iterator = darkrooms$toDestroy.iterator();
        while(iterator.hasNext()) {
            BlockPos blockPos = iterator.next();
            int brightness = Math.max(level.getBrightness(LightLayer.BLOCK, blockPos), level.getBrightness(LightLayer.SKY, blockPos));
            if(brightness == 0) {
                BlockState state = level.getBlockState(blockPos);
                Block.dropResources(state, level, blockPos);
                level.removeBlock(blockPos, false);
            }
            iterator.remove();
        }
    }
    
}
