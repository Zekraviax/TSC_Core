package com.soft_cafe;

import com.soft_cafe.biome.BiomeMixinAccess;
import com.soft_cafe.command.CommandsRegister;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.MultiNoiseBiomeSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashSet;

public class TSC_Core implements ModInitializer {
	public static final String MODID = "TSC_Core";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	private static Calendar calendar;
	private static MinecraftClient client;

	private NbtCompound savedCompound;

	// New Month event listeners
	private static Collection<Biome> listeners = new HashSet<>();

	private static boolean firstTimeSetupCheck = true;


	// Getters and setters
	public static Calendar getCalendar() {
		return calendar;
	}

	public static void setCalendar(Calendar calendar) {
		TSC_Core.calendar = calendar;
	}

	public static MinecraftClient getClient() {
		return client;
	}

	public static void setClient(MinecraftClient client) {
		TSC_Core.client = client;
	}

	public NbtCompound getSavedCompound() {
		return savedCompound;
	}

	public void setSavedCompound(NbtCompound savedCompound) {
		this.savedCompound = savedCompound;
	}


	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		LOGGER.info("Hello Fabric world!");

		client = MinecraftClient.getInstance();
		calendar = new Calendar();

		// Register TSC commands
		CommandsRegister.registerTscClientCommands();

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

		hemisphereString += "East/West: ";

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