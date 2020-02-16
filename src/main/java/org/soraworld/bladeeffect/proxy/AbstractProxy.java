package org.soraworld.bladeeffect.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.soraworld.bladeeffect.BladeEffect;

/**
 * @author Himmelt
 */
public abstract class AbstractProxy {

    protected static final SimpleNetworkWrapper CHANNEL = NetworkRegistry.INSTANCE.newSimpleChannel(BladeEffect.MOD_ID);

    /**
     * On pre init.
     *
     * @param event the event
     */
    public abstract void onPreInit(FMLPreInitializationEvent event);

    /**
     * On init.
     *
     * @param event the event
     */
    public abstract void onInit(FMLInitializationEvent event);

    /**
     * On server starting.
     *
     * @param event the event
     */
    public abstract void onServerStarting(FMLServerStartingEvent event);
}
