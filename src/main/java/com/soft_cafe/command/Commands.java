package com.soft_cafe.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.soft_cafe.Atmosphere;
import com.soft_cafe.biome.BiomeMixinAccess;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.Biome;

public class Commands {
    public static int printDate(CommandContext<ServerCommandSource> serverCommandSourceCommandContext) throws CommandSyntaxException {
        String timeString = Atmosphere.getCalendar().getTimeAsFormattedString();
        String dateString = Atmosphere.getCalendar().getDateAsFormattedString();
        String printString = timeString + "\n" + dateString;

        serverCommandSourceCommandContext.getSource().getPlayer().sendMessage(new TranslatableText(printString), false);
        return 1;
    }


    public static int printHemisphere(CommandContext<ServerCommandSource> serverCommandSourceCommandContext) throws CommandSyntaxException {
        serverCommandSourceCommandContext.getSource().getPlayer().sendMessage(new TranslatableText(Atmosphere.getHemisphere()), false);
        return 1;
    }


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
}