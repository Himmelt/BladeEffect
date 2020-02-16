package org.soraworld.bladeeffect.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import static org.soraworld.bladeeffect.BladeEffect.MOD_ID;
import static org.soraworld.bladeeffect.client.ImageHandler.bindTexture;
import static org.soraworld.bladeeffect.client.ImageHandler.drawScaledCustomSizeModalRect;

/**
 * @author Himmelt
 */
public class TickImage {

    private int fadeIn;
    private int fadeOut;
    private float opacity;
    private float scale;
    private String name;

    private int timer;
    private int totalTime;
    private float alpha;
    private boolean repeat = false;
    private int frame, frameSpeed, frameAmount;

    private final Minecraft mc = Minecraft.getMinecraft();

    public static final byte HIDE = 0, SHOW = 1;

    public TickImage() {
    }

    public TickImage(String name, int fadeIn, int time, int fadeOut, float opacity, float scale, int frameAmount, int frameSpeed, boolean repeat) {
        this.name = name;
        this.fadeIn = fadeIn;
        this.fadeOut = fadeOut;
        this.opacity = opacity;
        this.scale = scale;
        this.frameSpeed = Math.max(1, Math.abs(frameSpeed));
        this.frameAmount = Math.max(0, Math.abs(frameAmount));
        this.repeat = repeat;
        totalTime = fadeIn + time + fadeOut;
        timer = totalTime;
        if (this.scale < 0) {
            this.scale = 1;
        }
        if (this.opacity < 0 || this.opacity > 1) {
            this.opacity = 1;
        }
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof TickImage && name.equals(((TickImage) obj).name);
    }

    public boolean tick() {
        if (--timer < 0) {
            return false;
        }
        int past = totalTime - timer;
        if (repeat && frame >= frameAmount) {
            frame = 0;
        }
        if (frame < frameAmount && past % frameSpeed == 0) {
            frame++;
        }
        alpha = 1F;
        if (past <= fadeIn) {
            alpha = opacity * past / fadeIn;
        } else if (timer <= fadeOut) {
            alpha = opacity * timer / fadeOut;
        } else {
            alpha = opacity;
        }
        return true;
    }

    public void display() {
        ResourceLocation resource = new ResourceLocation(MOD_ID, "images/" + name + ".png");
        ScaledResolution scaled = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        int width = scaled.getScaledWidth();
        int height = scaled.getScaledHeight();
        int size = MathHelper.ceiling_float_int(Math.min(width, height) * scale);

        if (bindTexture(mc.getTextureManager(), resource)) {
            drawScaledCustomSizeModalRect((width - size) / 2D, (height - size) / 2D,
                    frame, 0,
                    1, 1,
                    size, size,
                    frameAmount, 1, alpha);
        }
    }
}
