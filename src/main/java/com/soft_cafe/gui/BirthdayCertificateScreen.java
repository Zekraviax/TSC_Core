package com.soft_cafe.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.soft_cafe.TSC_Core;
import net.minecraft.client.gui.screen.CreditsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;

public class BirthdayCertificateScreen extends Screen {
    float speed = 1;
    float time = 1;

    public BirthdayCertificateScreen(String title) {
        super(Text.of(title));
    }

    @Override
    public void tick() {
        TSC_Core.LOGGER.info("render!");
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        int l;
        time += delta * speed;
        //this.renderBackground();
        int i = this.width / 2 - 137;
        int j = this.height + 50;
        float f = -time;
        matrices.push();
        matrices.translate(0.0, f, 0.0);
        //RenderSystem.setShaderTexture(0, MINECRAFT_TITLE_TEXTURE);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.enableBlend();
        this.drawWithOutline(i, j, (x, y) -> {
            this.drawTexture(matrices, x + 0, (int)y, 0, 0, 155, 44);
            this.drawTexture(matrices, x + 155, (int)y, 0, 45, 155, 44);
        });
        RenderSystem.disableBlend();
        //RenderSystem.setShaderTexture(0, EDITION_TITLE_TEXTURE);
        CreditsScreen.drawTexture(matrices, i + 88, j + 37, 0.0f, 0.0f, 98, 14, 128, 16);
        int k = j + 100;

        /*
        for (l = 0; l < this.credits.size(); ++l) {
            float g;
            if (l == this.credits.size() - 1 && (g = (float)k + f - (float)(this.height / 2 - 6)) < 0.0f) {
                matrices.translate(0.0, -g, 0.0);
            }
            if ((float)k + f + 12.0f + 8.0f > 0.0f && (float)k + f < (float)this.height) {
                OrderedText orderedText = this.credits.get(l);
                if (this.centeredLines.contains(l)) {
                    this.textRenderer.drawWithShadow(matrices, orderedText, (float)(i + (274 - this.textRenderer.getWidth(orderedText)) / 2), (float)k, 0xFFFFFF);
                } else {
                    this.textRenderer.drawWithShadow(matrices, orderedText, (float)i, (float)k, 0xFFFFFF);
                }
            }
            k += 12;
        }
        */

        matrices.pop();
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        //RenderSystem.setShaderTexture(0, VIGNETTE_TEXTURE);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SrcFactor.ZERO, GlStateManager.DstFactor.ONE_MINUS_SRC_COLOR);
        l = this.width;
        int m = this.height;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
        bufferBuilder.vertex(0.0, m, this.getZOffset()).texture(0.0f, 1.0f).color(1.0f, 1.0f, 1.0f, 1.0f).next();
        bufferBuilder.vertex(l, m, this.getZOffset()).texture(1.0f, 1.0f).color(1.0f, 1.0f, 1.0f, 1.0f).next();
        bufferBuilder.vertex(l, 0.0, this.getZOffset()).texture(1.0f, 0.0f).color(1.0f, 1.0f, 1.0f, 1.0f).next();
        bufferBuilder.vertex(0.0, 0.0, this.getZOffset()).texture(0.0f, 0.0f).color(1.0f, 1.0f, 1.0f, 1.0f).next();
        tessellator.draw();
        RenderSystem.disableBlend();

        super.render(matrices, mouseX, mouseY, delta);
    }
}