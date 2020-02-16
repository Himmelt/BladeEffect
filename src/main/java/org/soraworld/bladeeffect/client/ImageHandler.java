package org.soraworld.bladeeffect.client;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Himmelt
 */
@SideOnly(Side.CLIENT)
public class ImageHandler {

    private final CopyOnWriteArrayList<TickImage> images = new CopyOnWriteArrayList<>();

    private static ImageHandler INSTANCE;

    private ImageHandler() {
    }

    public static ImageHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ImageHandler();
        }
        return INSTANCE;
    }

    public void addImage(TickImage image) {
        images.add(image);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (event.type == RenderGameOverlayEvent.ElementType.HOTBAR) {
            for (TickImage image : images) {
                image.display();
            }
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        images.removeIf(title -> !title.tick());
    }

    public static boolean bindTexture(TextureManager manager, ResourceLocation resource) {
        HashMap map = ObfuscationReflectionHelper.getPrivateValue(TextureManager.class, manager, "mapTextureObjects", "field_110585_a", "b");
        Object object = map.get(resource);
        boolean success = true;
        if (object == null) {
            object = new SimpleTexture(resource);
            success = manager.loadTexture(resource, (ITextureObject) object);
        }
        success = success && object != TextureUtil.missingTexture;
        if (success) {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, ((ITextureObject) object).getGlTextureId());
        }
        return success;
    }

    public static void drawScaledCustomSizeModalRect(double screenX, double screenY, double textureU, double textureV,
                                                     float uWidth, float uHeight,
                                                     float width, float height, float tileWidth, float tileHeight, float alpha) {
        float f4 = 1.0F / tileWidth;
        float f5 = 1.0F / tileHeight;

        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glAlphaFunc(GL11.GL_ALWAYS, 0.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);

        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(screenX, (screenY + height), 0.0D, (textureU * f4), ((textureV + uHeight) * f5));
        tessellator.addVertexWithUV((screenX + width), (screenY + height), 0.0D, ((textureU + uWidth) * f4), ((textureV + uHeight) * f5));
        tessellator.addVertexWithUV((screenX + width), screenY, 0.0D, ((textureU + uWidth) * f4), (textureV * f5));
        tessellator.addVertexWithUV(screenX, screenY, 0.0D, (textureU * f4), (textureV * f5));
        tessellator.draw();

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
        GL11.glPopMatrix();
    }
}
