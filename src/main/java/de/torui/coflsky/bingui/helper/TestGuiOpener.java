package de.torui.coflsky.bingui.helper;

import de.torui.coflsky.bingui.gui.BinGuiNew;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TestGuiOpener {
    @SubscribeEvent
    public void openChestEvent(net.minecraftforge.event.entity.player.PlayerInteractEvent event) {
        if (event.action.equals(PlayerInteractEvent.Action.RIGHT_CLICK_AIR)) {
            IChatComponent message = new ChatComponentText("FLIP: §6Bat Person Helmet §2500,000 -> 750,000 (+242,500 §448%§2) §7 Med: §b750,000§7 Vol: §b20.7§r§7 sellers ah");
            String[] lore = {
                    "This is a Test lore",
                    "This is a Test lore",
                    "This is a Test lore",
                    "This is a Test lore",
                    "This is a Test loreThis is a Test lore",
                    "This is a Test lore",
                    "This is a Test lore",
                    "This is a Test lore",
                    "This is a Test lore",
                    "This is a Test lore"

            };
            Minecraft.getMinecraft().displayGuiScreen(new BinGuiNew(message, lore,"0"));
        }
    }
}