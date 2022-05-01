package com.soft_cafe;

import com.mojang.brigadier.CommandDispatcher;
import com.soft_cafe.biome.BiomeMixinAccess;
import com.soft_cafe.command.CommandsRegister;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.MultiNoiseBiomeSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashSet;

public class Atmosphere implements ModInitializer {
	public static final String MODID = "Atmosphere";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	private static Calendar calendar;
	private static MinecraftClient client;

	// New Month event listeners
	private static Collection<Biome> listeners = new HashSet<>();

	private static boolean firstTimeSetupCheck = true;


	// Getters and setters
	public static Calendar getCalendar() {
		return calendar;
	}

	public static void setCalendar(Calendar calendar) {
		Atmosphere.calendar = calendar;
	}

	public static MinecraftClient getClient() {
		return client;
	}

	public static void setClient(MinecraftClient client) {
		Atmosphere.client = client;
	}


	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		LOGGER.info("Hello Fabric world!");

		client = MinecraftClient.getInstance();

		// Register TSC commands
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			CommandsRegister.registerTSCCommands(ClientCommandManager.DISPATCHER);
			/*
			// First part
			LiteralCommandNode<ServerCommandSource> tscNode = CommandManager
					.literal("tsc")
					.build();

			// Second Parts
			LiteralCommandNode<ServerCommandSource> dateNode = CommandManager
					.literal("date")
					.executes(CommandsRegister::printDate)
					.build();

			LiteralCommandNode<ServerCommandSource> hemisphereNode = CommandManager
					.literal("hemisphere")
					.executes(CommandsRegister::printHemisphere)
					.build();

			LiteralCommandNode<ServerCommandSource> temperatureNode = CommandManager
					.literal("temperature")
					.executes(CommandsRegister::printTemperature)
					.build();

			// Alter Calendar Date
			LiteralCommandNode<ServerCommandSource> calendarNode = CommandManager
					.literal("calendar")
					.build();
			LiteralCommandNode<ServerCommandSource> yearAddNode = CommandManager
					.literal("year_set")
					.then(CommandManager.argument("year", IntegerArgumentType.integer()))
					.executes(CommandsRegister.setCalendarYear(context, 2))
					.build();

			dispatcher.getRoot().addChild(tscNode);
			tscNode.addChild(dateNode);
			tscNode.addChild(hemisphereNode);
			tscNode.addChild(temperatureNode);
			// Alter Calendar Date
			tscNode.addChild(calendarNode);
			//calendarNode.addChild(yearAddNode);

			*/
		});

		// Hook into the world tick
		calendar = new Calendar();
		ClientTickEvents.END_CLIENT_TICK.register((client) -> {
			/*
			if (firstTimeSetupCheck) {
				if (client.world != null) {
					FirstTimeSetupAfterWorldOpened();
				}
			}
			*/
		});


		for (RegistryEntry<Biome> biome : MultiNoiseBiomeSource.Preset.OVERWORLD.getBiomeSource(BuiltinRegistries.BIOME).getBiomes()) {
			listeners.add(biome.value());
		}
	}


	public static String getHemisphere() {
		String hemisphereString = "North/South: ";

		// Check Z value for N/S hemisphere
		if (client.player.getBlockPos().getZ() > 0) {
			hemisphereString += "Northern";
		} else if (client.player.getBlockPos().getZ() < 0) {
			hemisphereString += "Southern";
		} else {
			hemisphereString += "Equator";
		}

		hemisphereString += "\nEast/West: ";

		// Check X value for E/W hemisphere
		if (client.player.getBlockPos().getX() > 0) {
			hemisphereString += ", Eastern";
		} else if (client.player.getBlockPos().getX() < 0) {
			hemisphereString += ", Western";
		} else {
			hemisphereString += ", Equator";
		}

		return hemisphereString;
	}


	public static void newMonthEvent() {
		if (firstTimeSetupCheck) {
			for (RegistryEntry<Biome> biome : MultiNoiseBiomeSource.Preset.OVERWORLD.getBiomeSource(BuiltinRegistries.BIOME).getBiomes()) {
				String biomeName = "Biome: " + biome.getKeyOrValue().map(biomeKey -> biomeKey.getValue().toString(), biome_ -> "[unregistered " + biome_ + "]");
				biomeName = biomeName.replace("minecraft:", "");
				((BiomeMixinAccess) (Object) biome.value()).setBiomeName(biomeName);
			}

			firstTimeSetupCheck = false;
		}

		for (Biome biome : listeners) {
			((BiomeMixinAccess) (Object) biome).newMonth();
		}
	}
}