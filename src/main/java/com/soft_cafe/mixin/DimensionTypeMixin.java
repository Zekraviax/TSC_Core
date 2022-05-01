package com.soft_cafe.mixin;

import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DimensionType.class)
public class DimensionTypeMixin {
    /*
    @Inject(method = "getMoonPhase", at = @At("RETURN"), cancellable = true)
    void returnAtmosphereMoonPhase(long time, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue((int)(time / 24000L % 30L + 30L) % 30);
    }
    */
}