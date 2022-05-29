package com.soft_cafe.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.soft_cafe.TSC_Core;
import com.soft_cafe.biome.BiomeMixinAccess;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.Biome;

public class ServerCommands {
    public static int printTemperature(CommandContext<ServerCommandSource> serverCommandSourceCommandContext) throws CommandSyntaxException {
        Biome biome = null;

        ServerCommandSource s = serverCommandSourceCommandContext.getSource();
        ServerWorld world = s.getWorld();

        ServerPlayerEntity player = s.getPlayer();
        BlockPos pos = player.getBlockPos();

        RegistryEntry<Biome> entry = world.getBiome(pos);
        biome = entry.value();

        float minTemp = ((BiomeMixinAccess) (Object) biome).getMinTemperature();
        float maxTemp = ((BiomeMixinAccess) (Object) biome).getMaxTemperature();

        System.out.println(Float.toString(minTemp) + " - " + Float.toString(maxTemp));

        return 1;
    }

    public static int setCalendarYear(int yearArg) throws CommandSyntaxException {
        TSC_Core.getCalendar().setYear(yearArg);
        TSC_Core.getCalendar().setDisplayYear(yearArg);

        return 1;
    }

    public static int addCalendarYear(int yearArg) throws CommandSyntaxException {
        TSC_Core.getCalendar().setYear(TSC_Core.getCalendar().getYear() + yearArg);
        TSC_Core.getCalendar().setDisplayYear(TSC_Core.getCalendar().getDisplayYear() + yearArg);

        return 1;
    }
}
