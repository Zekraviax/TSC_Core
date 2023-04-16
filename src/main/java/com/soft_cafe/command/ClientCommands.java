package com.soft_cafe.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.soft_cafe.TSC_Core;
import com.soft_cafe.biome.BiomeMixinAccess;
import com.soft_cafe.gui.BirthdayCertificateScreen;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

public class ClientCommands {
    public static int printTemperature(CommandContext<ServerCommandSource> serverCommandSourceCommandContext) throws CommandSyntaxException {
        Biome biome = null;

        ServerCommandSource s = serverCommandSourceCommandContext.getSource();
        ServerWorld world = s.getWorld();

        ServerPlayerEntity player = s.getPlayerOrThrow();
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

    public static int getBirthday(FabricClientCommandSource source) throws CommandSyntaxException {
        NbtCompound nbtCompound = MinecraftClient.getInstance().getServer().getSaveProperties().getPlayerData();

        source.getPlayer().sendMessage(Text.of("Player Birthday: " + nbtCompound.getInt("BirthDayOfMonth")));

        BirthdayCertificateScreen screen = new BirthdayCertificateScreen("test");
        MinecraftClient.getInstance().setScreen(screen);

        return 1;
    }
}