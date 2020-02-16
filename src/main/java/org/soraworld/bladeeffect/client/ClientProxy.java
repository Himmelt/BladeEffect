package org.soraworld.bladeeffect.client;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.common.MinecraftForge;
import org.soraworld.bladeeffect.proxy.AbstractProxy;

/**
 * @author Himmelt
 */
@SideOnly(Side.CLIENT)
public class ClientProxy extends AbstractProxy {

    @Override
    public void onPreInit(FMLPreInitializationEvent event) {
    }

    @Override
    public void onInit(FMLInitializationEvent event) {
        FMLCommonHandler.instance().bus().register(ImageHandler.getInstance());
        MinecraftForge.EVENT_BUS.register(ImageHandler.getInstance());
        MinecraftForge.EVENT_BUS.register(new AttackHandler());
    }

    @Override
    public void onServerStarting(FMLServerStartingEvent event) {
    }
}
