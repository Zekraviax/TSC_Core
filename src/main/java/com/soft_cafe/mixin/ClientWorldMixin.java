package com.soft_cafe.mixin;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;

import java.util.function.Supplier;

@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin extends World {
    protected ClientWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, DynamicRegistryManager registryManager, RegistryEntry<DimensionType> dimensionEntry, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long biomeAccess, int maxChainedNeighborUpdates) {
        super(properties, registryRef, registryManager, dimensionEntry, profiler, isClient, debugWorld, biomeAccess, maxChainedNeighborUpdates);
    }
//    long adjustedTime = 0;
//
//    protected ClientWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, DynamicRegistryManager registryManager, RegistryEntry<DimensionType> dimensionEntry, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long biomeAccess, int maxChainedNeighborUpdates) {
//        super(properties, registryRef, registryManager, dimensionEntry, profiler, isClient, debugWorld, biomeAccess, maxChainedNeighborUpdates);
//    }
//
//    @Inject(method = "tickTime", at = @At("TAIL"))
//    protected void timeTimeWithCalendar(CallbackInfo ci) {
//        if (properties != null) {
//            adjustedTime = (getTimeOfDay() + 6000) % 24000;
//
//            if (TSC_Core.getCalendar().getDay() != (int) Math.floor((getTimeOfDay() / 24000)) + 1) {
//                TSC_Core.getCalendar().newDay();
//            }
//
//            if (TSC_Core.getCalendar().getDisplayDay() >= 28) {
//                TSC_Core.getCalendar().newMonth();
//            }
//
//            TSC_Core.getCalendar().setMinute((int) Math.floor((adjustedTime % 1000) / 16.667));
//            TSC_Core.getCalendar().setHour((int) Math.floor((adjustedTime / 1000)));
//            TSC_Core.getCalendar().setDay((int) Math.floor((getTimeOfDay() / 24000)) + 1);
//        }
//    }
}