package org.soraworld.bladeeffect.client;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import org.soraworld.bladeeffect.proxy.AbstractProxy;

/**
 * @author Himmelt
 */
@SideOnly(Side.CLIENT)
public class ClientProxy extends AbstractProxy {

    private boolean repeat = false;
    private int time = 20;
    private int fadeOut = 20;
    private int frameSpeed = 1;
    private int frameAmount = 14;

    @Override
    public void onPreInit(FMLPreInitializationEvent event) {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        repeat = config.getBoolean("repeat", "general", false, "是否循环");
        time = config.getInt("time", "general", 20, 0, Integer.MAX_VALUE, "显示时长");
        fadeOut = config.getInt("fadeOut", "general", 20, 0, Integer.MAX_VALUE, "d淡出时长");
        frameSpeed = config.getInt("frameSpeed", "general", 1, 1, Integer.MAX_VALUE, "帧间隔");
        frameAmount = config.getInt("frameAmount", "general", 14, 1, Integer.MAX_VALUE, "总帧数");
        config.save();
    }

    @Override
    public void onInit(FMLInitializationEvent event) {
        FMLCommonHandler.instance().bus().register(ImageHandler.getInstance());
        MinecraftForge.EVENT_BUS.register(ImageHandler.getInstance());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onServerStarting(FMLServerStartingEvent event) {
    }

    @SubscribeEvent
    public void onPlayerAttackEntity(AttackEntityEvent event) {
        EntityPlayer player = event.entityPlayer;
        ItemStack stack = player.getHeldItem();
        if (stack != null) {
            Item weapon = stack.getItem();
            if (weapon instanceof ItemSword || weapon instanceof ItemTool || weapon.getUnlocalizedName().toLowerCase().contains("sword")) {
                ImageHandler.getInstance().addImage(new TickImage("blade_effect", 0, time, fadeOut, 1.0F, 1.0F,
                        frameAmount, frameSpeed, repeat));
            }
        }
    }
}
