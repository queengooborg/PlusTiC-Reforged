package queengooborg.plustic.api;

import java.util.*;

import queengooborg.plustic.*;
import queengooborg.plustic.net.*;
import queengooborg.plustic.util.*;
import net.minecraft.client.resources.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraftforge.common.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.event.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.relauncher.*;
import slimeknights.tconstruct.library.utils.*;

public class Portal {
	public static final String PORTAL_NBT = "nickoftime";
	
	private static final Set<String> portalable = new HashSet<>();
	public static void addPortalable(String identifier) {
		portalable.add(identifier);
	}
	
	public static boolean canUse(NBTTagCompound nbt) {
		for (String identifier: portalable) {
			if (TinkerUtil.hasTrait(nbt, identifier)) {
				return true;
			}
		}
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void testSetPortal(InputEvent.KeyInputEvent event) {
		if (PlusTiC.proxy.isControlPressed("set_portal")) {
			PacketHandler.INSTANCE.sendToServer(new PacketSetPortal());
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void tooltip(ItemTooltipEvent event) {
		NBTTagCompound nbt0 = TagUtil.getTagSafe(event.getItemStack());
		if (event.isCanceled()
				|| event.getItemStack() == null
				|| !canUse(nbt0)) return;
		if (nbt0.hasKey(Portal.PORTAL_NBT, 10)) {
			NBTTagCompound nbt = nbt0.getCompoundTag(Portal.PORTAL_NBT);
			event.getToolTip().add(I18n.format("tooltip.plustic.portal.info",
					nbt.getInteger("x"),
					nbt.getInteger("y"),
					nbt.getInteger("z"),
					nbt.getInteger("dim")));
		}
	}
}
