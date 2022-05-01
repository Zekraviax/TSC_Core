package com.soft_cafe.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.option.CloudRenderMode;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(WorldRenderer.class)
public interface WorldRendererAccessor {
    @Accessor("client")
    MinecraftClient getClient();

    @Accessor("world")
    ClientWorld getWorld();

    @Accessor("lightSkyBuffer")
    VertexBuffer getLightSkyBuffer();

    @Accessor("SUN")
    static Identifier getSun() {
        throw new AssertionError();
    }

    @Accessor("MOON_PHASES")
    static Identifier getMoonPhases() {
        throw new AssertionError();
    }

    @Accessor("starsBuffer")
    VertexBuffer getStarsBuffer();

    @Accessor("darkSkyBuffer")
    VertexBuffer getDarkSkyBuffer();

    /*
    @Accessor("ticks")
    int getTicks();

    @Accessor("lastCloudsBlockX")
    static int getLastCloudsBlockX() {
        throw new AssertionError();
    }

    @Accessor("lastCloudsBlockY")
    static int getLastCloudsBlockY() {
        throw new AssertionError();
    }

    @Accessor("lastCloudsBlockZ")
    static int getLastCloudsBlockZ() {
        throw new AssertionError();
    }

    @Accessor("lastCloudsColor")
    static Vec3d getLastCloudsColor() {
        throw new AssertionError();
    }

    @Accessor("lastCloudsRenderMode")
    static CloudRenderMode getLastCloudsRenderMode() {
        throw new AssertionError();
    }

    @Accessor("cloudsDirty")
    static boolean getCloudsDirty() {
        throw new AssertionError();
    }

    @Accessor("cloudsBuffer")
    static VertexBuffer getCloudsBuffer() {
        throw new AssertionError();
    }
    */


    @Invoker("renderEndSky")
    void invokeRenderEndSky(MatrixStack matrices);
}