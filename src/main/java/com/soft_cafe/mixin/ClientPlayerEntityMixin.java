package com.soft_cafe.mixin;

import com.soft_cafe.TSC_Core;
import com.soft_cafe.gui.BirthdayCertificateScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.BookEditScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
abstract public class ClientPlayerEntityMixin {
    private final MinecraftClient client = ((ClientPlayerEntityAccessor) (Object) this).getClient();

    @Inject(method = "useBook", at = @At("TAIL"))
    protected void useBookOrBirthdayCertificate(ItemStack book, Hand hand, CallbackInfo ci) {
        if (book.isOf(Items.WRITABLE_BOOK)) {
            this.client.setScreen(new BookEditScreen(((ClientPlayerEntity) (Object) this), book, hand));
        } else if (book.isOf(TSC_Core.BIRTHDAY_CERTIFICATE)) {
            this.client.setScreen(new BirthdayCertificateScreen("Code Title"));
        }
    }
}