package com.soft_cafe.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Collections;
import java.util.List;

public class BirthdayCertificateScreen extends Screen {
    public static final Identifier BOOK_TEXTURE = new Identifier("textures/gui/book.png");
    private List<OrderedText> cachedPage = Collections.emptyList();
    private Text pageIndexText;
    private int pageIndex = 0;
    private int cachedPageIndex = -1;

    // Player birth date
    public static int birthDay = 0;
    public static int birthMonth = 0;
    public static int birthYear = 0;

    public BirthdayCertificateScreen(String title) {
        super(Text.of(title));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, BOOK_TEXTURE);

        int XPositionOnScreen = (this.width - 192) / 2;
        int YPositionOnScreen = MinecraftClient.getInstance().getWindow().getHeight() / 2 - this.height;
        this.drawTexture(matrices, XPositionOnScreen, YPositionOnScreen, 0, 0, 192, 192);

        if (this.cachedPageIndex != this.pageIndex) {
//            StringVisitable stringVisitable = this.contents.getPage(this.pageIndex);
//            this.cachedPage = this.textRenderer.wrapLines(stringVisitable, 114);
//            this.pageIndexText = new TranslatableText("book.pageIndicator", this.pageIndex + 1, Math.max(this.getPageCount(), 1));
        }

        this.cachedPageIndex = this.pageIndex;

        // Draw the pageIndexText
        pageIndexText = Text.of("Hewwo!");
        int TextRendererWidth = this.textRenderer.getWidth(this.pageIndexText);
        this.textRenderer.draw(matrices, this.pageIndexText, (float)(XPositionOnScreen - TextRendererWidth + 192 - 44), 18.0f, 0);


        // Draw the text in the cachedPage list.
        cachedPage = client.textRenderer.wrapLines(Text.of("Birth Day: " + String.valueOf(birthDay) +
                "\nBirth Month: " + String.valueOf(birthMonth) +
                "\nBirth Year: " + String.valueOf(birthYear)), 150);

        int l = Math.min(128 / this.textRenderer.fontHeight, this.cachedPage.size());
        for (int m = 0; m < l; ++m) {
            OrderedText orderedText = this.cachedPage.get(m);
            this.textRenderer.draw(matrices, orderedText, (float)(XPositionOnScreen + 36), (float)(32 + m * this.textRenderer.fontHeight), 0);
        }

        super.render(matrices, mouseX, mouseY, delta);
    }
}