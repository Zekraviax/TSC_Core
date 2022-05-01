package com.soft_cafe.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.soft_cafe.Atmosphere;
import com.soft_cafe.biome.BiomeMixinAccess;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.Biome;

public class CommandsRegister {
    public static void registerTSCCommands(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(LiteralArgumentBuilder.<FabricClientCommandSource>literal("tsc")
            .then(LiteralArgumentBuilder.literal("year"))
                .then(LiteralArgumentBuilder.literal("set"))
                    .then(RequiredArgumentBuilder.<FabricClientCommandSource, Integer>argument("year", IntegerArgumentType.integer())
                        .executes(ctx -> {
                            return ServerCommands.setCalendarYear(ctx.getArgument("year", Integer.class));
                        }))
                .then(LiteralArgumentBuilder.literal("add"))
                    .then(RequiredArgumentBuilder.<FabricClientCommandSource, Integer>argument("year", IntegerArgumentType.integer())
                        .executes(ctx -> {
                            return ServerCommands.addCalendarYear(ctx.getArgument("year", Integer.class));
                        })));
    }
}