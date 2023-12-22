package com.soft_cafe;

import com.soft_cafe.gui.BirthdayCertificateScreen;
import com.soft_cafe.item.BirthCertificate;
import com.soft_cafe.server.PlayerState;
import com.soft_cafe.server.ServerState;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Material;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.BlockItem;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

import java.util.Collection;
import java.util.HashSet;

public class TSC_Core implements ModInitializer {
	public static final String MODID = "tsc_core";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	//public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	// Always accessible variables
	private static Calendar calendar;
	private static MinecraftClient client;

	// New Month event listeners
	private static Collection<Biome> listeners = new HashSet<>();
	private static boolean firstTimeSetupCheck = true;

	// Blocks
	public static final WindowBlock OAK_WINDOW = new WindowBlock(AbstractBlock.Settings.of(Material.GLASS).strength(0.3F).sounds(BlockSoundGroup.GLASS).nonOpaque());

	// Items
	public static final BirthCertificate BIRTHDAY_CERTIFICATE = new BirthCertificate(new FabricItemSettings().maxCount(1));

	// Packet Identifiers
	public static final Identifier DATE_OF_BIRTH = new Identifier("tsc_core", "dateofbirth");


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


	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		//LOGGER.info("Hello Fabric world!");

		//client = MinecraftClient.getInstance();
		calendar = new Calendar();

//		for (RegistryEntry<Biome> biome : MultiNoiseBiomeSource.Preset.OVERWORLD.getBiomeSource(BuiltinRegistries.).getBiomes()) {
//			listeners.add(biome.value());
//		}

		// Register TSC commands
		//CommandsRegister.registerTscClientCommands();

		// Register blocks
		Registry.register(Registries.BLOCK, new Identifier(MODID, "oak_window"), OAK_WINDOW);
		BlockRenderLayerMap.INSTANCE.putBlock(OAK_WINDOW, RenderLayer.getTranslucent());

		// Register items
		Registry.register(Registries.ITEM, new Identifier(MODID, "birthday_certificate"), BIRTHDAY_CERTIFICATE);
		Registry.register(Registries.ITEM, new Identifier(MODID,"oak_window"), new BlockItem(OAK_WINDOW, new FabricItemSettings()));

		// Pass the player their data
		ServerPlayConnectionEvents.JOIN.register(((handler, sender, server) -> {
			ServerState serverState = ServerState.getServerState(handler.player.world.getServer());
			PlayerState playerState = ServerState.getPlayerState(handler.player);

			PacketByteBuf data = PacketByteBufs.create();

			playerState.birthDayAsNumber = calendar.getDay();
			playerState.birthMonthAsNumber = calendar.getDisplayMonth();
			playerState.birthYear = calendar.getYear();

			data.writeInt(playerState.birthDayAsNumber);
			data.writeInt(playerState.birthMonthAsNumber);
			data.writeInt(playerState.birthYear);

			ServerPlayNetworking.send(handler.player, DATE_OF_BIRTH, data);
		}));

		ClientPlayNetworking.registerGlobalReceiver(DATE_OF_BIRTH, (client, handler, buf, responseSender) -> {
			BirthdayCertificateScreen.birthDay = buf.readInt();
			BirthdayCertificateScreen.birthMonth = buf.readInt();
			BirthdayCertificateScreen.birthYear = buf.readInt();
		});
	}


	public static String getHemisphere() {
		String hemisphereString = "North/South: ";

		// Check Z value for North/South hemisphere
		/*if (client.player.getBlockPos().getZ() > 0) {
			hemisphereString += "Northern";
		} else if (client.player.getBlockPos().getZ() < 0) {
			hemisphereString += "Southern";
		} else {
			hemisphereString += "Equator";
		}

		hemisphereString += "East/West: ";

		// Check X value for East/WestG hemisphere
		if (client.player.getBlockPos().getX() > 0) {
			hemisphereString += ", Eastern";
		} else if (client.player.getBlockPos().getX() < 0) {
			hemisphereString += ", Western";
		} else {
			hemisphereString += ", Equator";
		}*/

		return hemisphereString;
	}


	public static void newMonthEvent() {
//		if (firstTimeSetupCheck) {
//			for (RegistryEntry<Biome> biome : MultiNoiseBiomeSource.Preset.OVERWORLD.getBiomeSource(BuiltinRegistries.BIOME).getBiomes()) {
//				String biomeName = "Biome: " + biome.getKeyOrValue().map(biomeKey -> biomeKey.getValue().toString(), biome_ -> "[unregistered " + biome_ + "]");
//				biomeName = biomeName.replace("minecraft:", "");
//				((BiomeMixinAccess) (Object) biome.value()).setBiomeName(biomeName);
//			}
//
//			firstTimeSetupCheck = false;
//		}
//
//		for (Biome biome : listeners) {
//			((BiomeMixinAccess) (Object) biome).newMonth();
//		}
	}
}