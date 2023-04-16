package com.soft_cafe.mixin;

import com.soft_cafe.TSC_Core;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.DebugHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(DebugHud.class)
public abstract class DebugHudMixin {
    MinecraftClient client = MinecraftClient.getInstance();

    @Inject(method="getRightText", at=@At("RETURN"), cancellable = true)
    protected void getRightText(CallbackInfoReturnable<List<String>> rightText) {
        List<String> returnStrings = rightText.getReturnValue();

        // Current hemispheres
        if (client.player != null) {
            returnStrings.add("");
            returnStrings.add("Hemispheres: " + TSC_Core.getHemisphere());
        }

        String minuteAsString = Integer.toString(TSC_Core.getCalendar().getMinute());
        if (minuteAsString.length() < 2) {
            minuteAsString = "0" + minuteAsString;
        }

        // Date and Time
        returnStrings.add("");
        returnStrings.add("Time and Date:");

        returnStrings.add(TSC_Core.getCalendar().getHour() + ":" + minuteAsString + " " + TSC_Core.getCalendar().getMeridian());
        returnStrings.add(TSC_Core.getCalendar().getDayAsString());
        returnStrings.add(TSC_Core.getCalendar().getMonth() + " " + TSC_Core.getCalendar().getDisplayDay());
        returnStrings.add("Year " + Integer.toString(TSC_Core.getCalendar().getDisplayYear()));

        rightText.setReturnValue(returnStrings);

        // Get game time in ticks
        returnStrings.add("");
        returnStrings.add("Game times in ticks:");
        returnStrings.add("GetTime(): " + String.valueOf(client.world.getTime()));
        returnStrings.add("GetTimeOfDay(): " + String.valueOf(client.world.getTimeOfDay()));
    }
}