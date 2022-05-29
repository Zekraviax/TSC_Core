package com.soft_cafe.mixin;

import com.soft_cafe.TSC_Core;
import net.minecraft.server.PlayerManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(PlayerManager.class)
abstract public class PlayerManagerMixin {
    @Inject(method="onPlayerConnect", at = @At("HEAD"))
    void onPlayerConnect() {
        TSC_Core.LOGGER.info("Player has spawned.");
    }
}
