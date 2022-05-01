package com.soft_cafe.mixin;

import com.soft_cafe.Atmosphere;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin extends World {
    long adjustedTime = 0;


    protected ClientWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, RegistryEntry<DimensionType> registryEntry, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long seed) {
        super(properties, registryRef, registryEntry, profiler, isClient, debugWorld, seed);
    }


    @Inject(method = "tickTime", at = @At("TAIL"))
    protected void timeTimeWithCalendar(CallbackInfo ci) {
        if (properties != null) {
            adjustedTime = (getTimeOfDay() + 6000) % 24000;

            if (Atmosphere.getCalendar().getDay() != (int) Math.floor((getTimeOfDay() / 24000)) + 1) {
                Atmosphere.getCalendar().newDay();
            }

            if (Atmosphere.getCalendar().getDisplayDay() >= 28) {
                Atmosphere.getCalendar().newMonth();
            }

            Atmosphere.getCalendar().setMinute((int) Math.floor((adjustedTime % 1000) / 16.667));
            Atmosphere.getCalendar().setHour((int) Math.floor((adjustedTime / 1000)));
            Atmosphere.getCalendar().setDay((int) Math.floor((getTimeOfDay() / 24000)) + 1);
        }
    }
}
