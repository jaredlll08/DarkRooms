package com.blamejared.darkrooms.core;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import javax.annotation.Nullable;
import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.SortingIndex(-5000)
@IFMLLoadingPlugin.TransformerExclusions("com.blamejared.darkrooms.core")
public class DRLoadingPlugin implements IFMLLoadingPlugin {
    
    public DRLoadingPlugin() {
        
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.darkrooms.json");
    }
    
    @Override
    public String[] getASMTransformerClass() {
        
        return new String[0];
    }
    
    @Override
    public String getModContainerClass() {
        
        return null;
    }
    
    @Nullable
    @Override
    public String getSetupClass() {
        
        return null;
    }
    
    @Override
    public void injectData(Map<String, Object> data) {
    
    }
    
    @Override
    public String getAccessTransformerClass() {
        
        return null;
    }
}