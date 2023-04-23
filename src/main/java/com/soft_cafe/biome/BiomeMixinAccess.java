package com.soft_cafe.biome;

public interface BiomeMixinAccess {
    // Getters and Setters
    Float getMinTemperature();
    void setMinTemperature(float minTemp);

    Float getMaxTemperature();
    void setMaxTemperature(float maxTemp);

    String getBiomeName();
    void setBiomeName(String biomeName);

    void newMonth();
}
