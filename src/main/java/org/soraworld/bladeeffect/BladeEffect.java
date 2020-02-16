package org.soraworld.bladeeffect;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import org.soraworld.bladeeffect.proxy.AbstractProxy;

/**
 * @author Himmelt
 */
@Mod(
        modid = BladeEffect.MOD_ID,
        name = BladeEffect.MOD_NAME,
        version = BladeEffect.MOD_VERSION
)
public class BladeEffect {

    public static final String MOD_ID = "bladeeffect";
    public static final String MOD_NAME = "BladeEffect";
    public static final String MOD_VERSION = "1.0.1";

    @SidedProxy(
            clientSide = "org.soraworld.bladeeffect.client.ClientProxy",
            serverSide = "org.soraworld.bladeeffect.server.ServerProxy"
    )
    private static AbstractProxy proxy;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        proxy.onPreInit(event);
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        proxy.onInit(event);
    }
}
