package org.soraworld.bladeeffect.client;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

/**
 * @author Himmelt
 */
@SideOnly(Side.CLIENT)
public class AttackHandler {

    @SubscribeEvent
    public void onPlayerAttackEntity(AttackEntityEvent event) {
        EntityPlayer player = event.entityPlayer;
        ItemStack stack = player.getHeldItem();
        if (stack != null) {
            Item weapon = stack.getItem();
            if (weapon instanceof ItemSword || weapon instanceof ItemTool || weapon.getUnlocalizedName().toLowerCase().contains("sword")) {
                ImageHandler.getInstance().addImage(new TickImage("blade_light", 0, 20, 20, 1.0F, 1.0F,
                        32, 1, false));
            }
        }
    }
}
