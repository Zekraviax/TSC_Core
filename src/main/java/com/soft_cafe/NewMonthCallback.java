package com.soft_cafe;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.world.biome.Biome;

public interface NewMonthCallback {
    /*
    Event<NewMonthCallback> EVENT = EventFactory.createArrayBacked(NewMonthCallback.class, (listeners) -> (biome) -> {
        for (NewMonthCallback listener : listeners) {
            ActionResult result = listener.newMonth(biome);
        }

        return ActionResult.PASS;
    });

    ActionResult newMonth(Biome biome);
    */

    void onEvent();
}