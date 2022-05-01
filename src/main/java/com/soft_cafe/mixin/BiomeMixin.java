package com.soft_cafe.mixin;

import com.soft_cafe.NewMonthCallback;
import com.soft_cafe.biome.BiomeMixinAccess;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Biome.class)
public abstract class BiomeMixin implements BiomeMixinAccess, NewMonthCallback {
    @Unique
    private String biomeName = "null";

    @Unique
    private Float minTemperature = -5.f;

    @Unique
    private Float maxTemperature = 2.f;

    /*
    @Unique
    private Map<Float, Float> summerOneTemperatureRanges = new HashMap<>();

    @Unique
    private Map<Float, Float> summerTwoTemperatureRanges =  new HashMap<>();

    @Unique
    private Map<Float, Float> summerThreeTemperatureRanges = new HashMap<>();
    */


    // Interface methods
    @Override
    public Float getMinTemperature() {
        return minTemperature;
    }

    @Override
    public void setMinTemperature(float minTemperature) { this.minTemperature = minTemperature; }

    @Override
    public Float getMaxTemperature() {
        return maxTemperature;
    }

    @Override
    public void setMaxTemperature(float maxTemperature) { this.maxTemperature = maxTemperature; }

    @Override
    public String getBiomeName() { return biomeName; }

    @Override
    public void setBiomeName(String biomeName) {
        this.biomeName = biomeName;
    }


    @Override
    public void newMonth() {
        System.out.println("NEW MONTH: " + biomeName);
        // BiomeTemperatureRangesLookup.LookUp(biomeName, this);
    }
}