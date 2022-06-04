package com.soft_cafe.mixin;

import com.soft_cafe.PlayerEntityAccessor;
import com.soft_cafe.TSC_Core;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerManager.class)
abstract public class PlayerManagerMixin {
    NbtCompound localNbtCompound;

    @Inject(method = "loadPlayerData", at = @At("RETURN"), cancellable = true)
    void getNbtCompound(ServerPlayerEntity player, CallbackInfoReturnable<NbtCompound> cir) {
        localNbtCompound = cir.getReturnValue();
    }

    @Inject(method = "onPlayerConnect", at = @At("TAIL"))
    void onPlayerConnect(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        TSC_Core.LOGGER.info("Player has spawned.");
        PlayerEntity playerAsEntity = (PlayerEntity) player;

        // Get birthday data.
        localNbtCompound.putInt("BirthDayOfMonth", TSC_Core.getCalendar().getDay());

        if (localNbtCompound != null) {
            player.writeCustomDataToNbt(localNbtCompound);
        } else {
            TSC_Core.LOGGER.info("Error retrieving NbtCompound");
        }
    }
}