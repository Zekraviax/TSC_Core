package com.soft_cafe.mixin;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.soft_cafe.TSC_Core;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {
    private float sunSize = 30.f;
    private float moonSize = 5.f;
    private float starSize = 100.f;

    // Replace the Moon_Phases texture with our own various Moon textures
    private Identifier MOON_PHASE = new Identifier("atmosphere:textures/environment/moonphase_7.png");
    private Identifier SUN_TEXTURE = new Identifier("atmosphere:textures/environment/sun_smog.png");
    private Identifier STARS_BOTTOM_TEXTURE = new Identifier("atmosphere:textures/environment/zodiac_down.png");
    private Identifier STARS_TOP_TEXTURE = new Identifier("atmosphere:textures/environment/zodiac_up.png");
    private Identifier STARS_FRONT_TEXTURE = new Identifier("atmosphere:textures/environment/north.png");
    private Identifier STARS_BACK_TEXTURE = new Identifier("atmosphere:textures/environment/south.png");
    private Identifier STARS_LEFT_TEXTURE = new Identifier("atmosphere:textures/environment/zodiac_west.png");
    private Identifier STARS_RIGHT_TEXTURE = new Identifier("atmosphere:textures/environment/zodiac_east.png");
    private Identifier TESTER_TEXTURE = new Identifier("atmosphere:textures/tester.png");

    // Access variables in WorldRenderer for our customRenderSky method
    private MinecraftClient client = ((WorldRendererAccessor) (Object) this).getClient();
    private ClientWorld world = ((WorldRendererAccessor) (Object) this).getWorld();
    private VertexBuffer lightSkyBuffer = ((WorldRendererAccessor) (Object) this).getLightSkyBuffer();
    private VertexBuffer starsBuffer = ((WorldRendererAccessor) (Object) this).getStarsBuffer();
    private VertexBuffer darkSkyBuffer = ((WorldRendererAccessor) (Object) this).getDarkSkyBuffer();

    // Custom moon phases
    void returnAtmosphereMoonPhase(long time) {
        int phase = (TSC_Core.getCalendar().getDay() % 30);

        if (phase >= 0 && phase <= 2) {
            MOON_PHASE = new Identifier("atmosphere:textures/environment/moonphase_0.png");
        } else if (phase == 3) {
            MOON_PHASE = new Identifier("atmosphere:textures/environment/moonphase_1.png");
        } else if (phase == 4) {
            MOON_PHASE = new Identifier("atmosphere:textures/environment/moonphase_2.png");
        } else if (phase == 5) {
            MOON_PHASE = new Identifier("atmosphere:textures/environment/moonphase_3.png");
        } else if (phase == 6) {
            MOON_PHASE = new Identifier("atmosphere:textures/environment/moonphase_4.png");
        } else if (phase == 7) {
            MOON_PHASE = new Identifier("atmosphere:textures/environment/moonphase_5.png");
        } else if (phase == 8 || phase == 9) {
            MOON_PHASE = new Identifier("atmosphere:textures/environment/moonphase_6.png");
        } else if (phase == 10) {
            MOON_PHASE = new Identifier("atmosphere:textures/environment/moonphase_7.png");
        } else if (phase == 11) {
            MOON_PHASE = new Identifier("atmosphere:textures/environment/moonphase_8.png");
        } else if (phase == 12) {
            MOON_PHASE = new Identifier("atmosphere:textures/environment/moonphase_9.png");
        } else if (phase == 13) {
            MOON_PHASE = new Identifier("atmosphere:textures/environment/moonphase_10.png");
        } else if (phase == 14) {
            MOON_PHASE = new Identifier("atmosphere:textures/environment/moonphase_11.png");
        } else if (phase >= 15 && phase <= 17) {    // Full moon
            if (TSC_Core.getCalendar().getYear() % 37 == 0) {
                MOON_PHASE = new Identifier("atmosphere:textures/environment/moonphase_blood.png");
            } else {
                MOON_PHASE = new Identifier("atmosphere:textures/environment/moonphase_12.png");
            }
        } else if (phase == 18) {
            MOON_PHASE = new Identifier("atmosphere:textures/environment/moonphase_13.png");
        } else if (phase == 19) {
            MOON_PHASE = new Identifier("atmosphere:textures/environment/moonphase_14.png");
        } else if (phase == 20) {
            MOON_PHASE = new Identifier("atmosphere:textures/environment/moonphase_15.png");
        } else if (phase == 21) {
            MOON_PHASE = new Identifier("atmosphere:textures/environment/moonphase_16.png");
        } else if (phase == 22) {
            MOON_PHASE = new Identifier("atmosphere:textures/environment/moonphase_17.png");
        } else if (phase == 23 || phase == 24) {
            MOON_PHASE = new Identifier("atmosphere:textures/environment/moonphase_18.png");
        } else if (phase == 25) {
            MOON_PHASE = new Identifier("atmosphere:textures/environment/moonphase_19.png");
        } else if (phase == 26) {
            MOON_PHASE = new Identifier("atmosphere:textures/environment/moonphase_20.png");
        } else if (phase == 27) {
            MOON_PHASE = new Identifier("atmosphere:textures/environment/moonphase_21.png");
        } else if (phase == 28) {
            MOON_PHASE = new Identifier("atmosphere:textures/environment/moonphase_22.png");
        } else if (phase >= 29) {
            MOON_PHASE = new Identifier("atmosphere:textures/environment/moonphase_23.png");
        }
    }

    @Redirect(method = "render",
            at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/render/WorldRenderer;renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lorg/joml/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V"))
    public void renderSky(WorldRenderer instance, MatrixStack matrices, Matrix4f projectionMatrix, float tickDelta, Camera camera, boolean bl, Runnable runnable) {
        float q;
        float p;
        float o;
        int m;
        float k;
        float i;
        LivingEntity livingEntity;
        Entity entity;
        runnable.run();
        if (!bl) {
            CameraSubmersionType cameraSubmersionType = camera.getSubmersionType();
            if (cameraSubmersionType != CameraSubmersionType.POWDER_SNOW && cameraSubmersionType != CameraSubmersionType.LAVA && (!((entity = camera.getFocusedEntity()) instanceof LivingEntity) || !(livingEntity = (LivingEntity) entity).hasStatusEffect(StatusEffects.BLINDNESS))) {
                if (this.client.world.getDimensionEffects().getSkyType() == DimensionEffects.SkyType.END) {
                    ((WorldRendererAccessor) (Object) this).invokeRenderEndSky(matrices);
                } else if (this.client.world.getDimensionEffects().getSkyType() == DimensionEffects.SkyType.NORMAL) {
                    //customRenderStars(matrices);

                    Vec3d vec3d = this.world.getSkyColor(this.client.gameRenderer.getCamera().getPos(), tickDelta);
                    float f = (float) vec3d.x;
                    float g = (float) vec3d.y;
                    float h = (float) vec3d.z;
                    BackgroundRenderer.setFogBlack();
                    BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
                    RenderSystem.depthMask(false);
                    RenderSystem.setShaderColor(f, g, h, 1.0f);
                    ShaderProgram shaderProgram = RenderSystem.getShader();
                    this.lightSkyBuffer.bind();
                    this.lightSkyBuffer.draw(matrices.peek().getPositionMatrix(), projectionMatrix, shaderProgram);
                    RenderSystem.enableBlend();
                    RenderSystem.defaultBlendFunc();
                    float[] fs = this.world.getDimensionEffects().getFogColorOverride(this.world.getSkyAngle(tickDelta), tickDelta);
                    if (fs != null) {
                        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
                        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
                        matrices.push();
                        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0F));
                        i = MathHelper.sin(this.world.getSkyAngleRadians(tickDelta)) < 0.0F ? 180.0F : 0.0F;
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(i));
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90.0F));
                        float j = fs[0];
                        k = fs[1];
                        float l = fs[2];
                        Matrix4f matrix4f = matrices.peek().getPositionMatrix();
                        bufferBuilder.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
                        bufferBuilder.vertex(matrix4f, 0.0f, 100.0f, 0.0f).color(j, k, l, fs[3]).next();
                        m = 16;
                        for (int n = 0; n <= 16; ++n) {
                            o = (float) n * ((float) Math.PI * 2) / 16.0f;
                            p = MathHelper.sin(o);
                            q = MathHelper.cos(o);
                            bufferBuilder.vertex(matrix4f, p * 120.0f, q * 120.0f, -q * 40.0f * fs[3]).color(fs[0], fs[1], fs[2], 0.0f).next();
                        }
                        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
                        matrices.pop();
                    }
                    RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
                    matrices.push();
                    i = 1.0f - this.world.getRainGradient(tickDelta);
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, i);
                    matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-90.0F));
                    matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(this.world.getSkyAngle(tickDelta) * 360.0F));
                    Matrix4f matrix4f2 = matrices.peek().getPositionMatrix();

                    // Sun
                    k = 30.0f;
                    RenderSystem.setShader(GameRenderer::getPositionTexProgram);
                    RenderSystem.setShaderTexture(0, SUN_TEXTURE);
                    bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
                    bufferBuilder.vertex(matrix4f2, -k, 100.0F, -k).texture(0.0F, 0.0F).next();
                    bufferBuilder.vertex(matrix4f2, k, 100.0F, -k).texture(1.0F, 0.0F).next();
                    bufferBuilder.vertex(matrix4f2, k, 100.0F, k).texture(1.0F, 1.0F).next();
                    bufferBuilder.vertex(matrix4f2, -k, 100.0F, k).texture(0.0F, 1.0F).next();
                    BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());

                    // Moon
                    //moonSize = 10.f + ((float)Math.random() * 10.f) * ((float)Math.random() * 2.f);
                    returnAtmosphereMoonPhase(world.getTime());
                    RenderSystem.setShaderTexture(0, MOON_PHASE);
                    bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
                    bufferBuilder.vertex(matrix4f2, -moonSize, -100.0f, moonSize).texture(0.f, 0.f).next();
                    bufferBuilder.vertex(matrix4f2, moonSize, -100.0f, moonSize).texture(1.f, 0.f).next();
                    bufferBuilder.vertex(matrix4f2, moonSize, -100.0f, -moonSize).texture(1.f, 1.f).next();
                    bufferBuilder.vertex(matrix4f2, -moonSize, -100.0f, -moonSize).texture(0.f, 1.f).next();
                    BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());

                    // Stars
//                    k = 100.0f;
//                    Tessellator tessellator = Tessellator.getInstance();
//
//                    for (int loopCount = 1; loopCount <= 3; loopCount++) {
//                        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
//                        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
//
//                        //matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180.0F));
//
//                        if (loopCount == 1) {
//                            RenderSystem.setShaderTexture(0, STARS_FRONT_TEXTURE);
//                        } else if (loopCount == 2) {
//                            RenderSystem.setShaderTexture(0, STARS_BACK_TEXTURE);
//                        } else if (loopCount == 3) {
//                            RenderSystem.setShaderTexture(0, STARS_TOP_TEXTURE);
//                        }
//                        bufferBuilder.vertex(matrix4f2, -k, 100.0F, -k).texture(1.0F, 0.0F).next();
//                        bufferBuilder.vertex(matrix4f2, k, 100.0F, -k).texture(0.0F, 0.0F).next();
//                        bufferBuilder.vertex(matrix4f2, k, 100.0F, k).texture(0.0F, 1.0F).next();
//                        bufferBuilder.vertex(matrix4f2, -k, 100.0F, k).texture(1.0F, 1.0F).next();
//                        tessellator.draw();
//                    }
//
//                    RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
//                    RenderSystem.disableBlend();

                    matrices.pop();
                    //-- End Stars


                    RenderSystem.setShaderColor(0.0f, 0.0f, 0.0f, 1.0f);
                    double d = this.client.player.getCameraPosVec((float) tickDelta).y - this.world.getLevelProperties().getSkyDarknessHeight(this.world);
                    if (d < 0.0) {
                        matrices.push();
                        matrices.translate(0.0, 12.0, 0.0);
                        this.darkSkyBuffer.draw(matrices.peek().getPositionMatrix(), projectionMatrix, shaderProgram);
                        matrices.pop();
                    }
                    if (this.world.getDimensionEffects().isAlternateSkyColor()) {
                        RenderSystem.setShaderColor(f * 0.2f + 0.04f, g * 0.2f + 0.04f, h * 0.6f + 0.1f, 1.0f);
                    } else {
                        RenderSystem.setShaderColor(f, g, h, 1.0f);
                    }
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                    RenderSystem.depthMask(true);

                    customRenderStars(matrices, tickDelta);
                }
            }
        }

    }

    private void customRenderStars(MatrixStack matrices, float tickDelta) {
        RenderSystem.enableBlend();
        RenderSystem.depthMask(false);
        RenderSystem.setShader(GameRenderer::getPositionTexColorProgram);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        matrices.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(this.world.getSkyAngle(tickDelta) * 360.0f));
        matrices.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(TSC_Core.getCalendar().getConstellationsAngle()));

        for(int i = 0; i < 6; ++i) {
            matrices.push();

            if (i == 0) {
                RenderSystem.setShaderTexture(0, STARS_BOTTOM_TEXTURE);
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-90.0f));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(270.0f));
            }

            if (i == 1) {
                // West
                RenderSystem.setShaderTexture(0, STARS_LEFT_TEXTURE);
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0F));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0f));
            }

            if (i == 2) {
                RenderSystem.setShaderTexture(0, STARS_RIGHT_TEXTURE);
                // East
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90.0F));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0f));
            }

            if (i == 3) {
                RenderSystem.setShaderTexture(0, STARS_TOP_TEXTURE);
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180.0F));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0f));
            }

            if (i == 4) {
                RenderSystem.setShaderTexture(0, STARS_FRONT_TEXTURE);
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90.0F));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-90.0f));
            }

            if (i == 5) {
                RenderSystem.setShaderTexture(0, STARS_BACK_TEXTURE);
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-90.0F));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0f));
            }

            Matrix4f matrix4f = matrices.peek().getPositionMatrix();
            bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
            bufferBuilder.vertex(matrix4f, -100.0F, -100.0F, -100.0F).texture(0.0F, 0.0F).color(255, 255, 255, 255).next();
            bufferBuilder.vertex(matrix4f, -100.0F, -100.0F, 100.0F).texture(0.0F, 1.0F).color(255, 255, 255, 255).next();
            bufferBuilder.vertex(matrix4f, 100.0F, -100.0F, 100.0F).texture(1.0F, 1.0F).color(255, 255, 255, 255).next();
            bufferBuilder.vertex(matrix4f, 100.0F, -100.0F, -100.0F).texture(1.0F, 0.0F).color(255, 255, 255, 255).next();
            tessellator.draw();
            matrices.pop();
        }

        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
    }


    @Redirect(method = "renderStars()V",
            at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/render/WorldRenderer;renderStars(Lnet/minecraft/client/render/BufferBuilder;)Lnet/minecraft/client/render/BufferBuilder$BuiltBuffer;"))
    BufferBuilder.BuiltBuffer renderStars(WorldRenderer instance, BufferBuilder buffer) {
        // This function stops the default stars from rendering
        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);

        return buffer.end();
    }
}