package com.soft_cafe.mixin;

import com.soft_cafe.PlayerEntityAccessor;
import com.soft_cafe.TSC_Core;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements PlayerEntityAccessor {
    @Unique
    int BirthDayOfMonth = 3;

    @Override
    public int getBirthDayOfMonth() {
        return BirthDayOfMonth;
    }

    @Override
    public void setBirthDayOfMonth(int newDay) {
        BirthDayOfMonth = newDay;
    }

    /*
    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    void writeBirthdayCertificateDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putInt("BirthDayOfMonth", BirthDayOfMonth);
        TSC_Core.LOGGER.info("Write Player birthday certificate date to NBT");
    }
    */
}