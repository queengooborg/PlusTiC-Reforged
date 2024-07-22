package queengooborg.plustic.tools;

import java.util.*;

import queengooborg.plustic.*;
import queengooborg.plustic.config.ModInfo;
import queengooborg.plustic.net.*;
import net.minecraft.client.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.gameevent.*;

@Mod.EventBusSubscriber(modid = ModInfo.MODID, value = Side.CLIENT)
public interface IToggleTool<T extends Enum<T>> {
	Class<T> clazz();

	String getTag();

	static Enum<?> rotateMode(ItemStack is) {
		if (!(is.getItem() instanceof IToggleTool)) {
			return null;
		}
		final Enum<?>[] vals = ((IToggleTool<?>) is.getItem()).clazz().getEnumConstants();
		final String tag = ((IToggleTool<?>) is.getItem()).getTag();
		int newIdx = (is.getTagCompound().getInteger(tag) + 1) % vals.length;
		is.getTagCompound().setInteger(tag, newIdx);
		return vals[newIdx];
	}

	@SuppressWarnings("unchecked")
	static <T extends Enum<T>> T rotateMode(ItemStack is, Class<T> clazz) {
		if (!(is.getItem() instanceof IToggleTool && ((IToggleTool<?>) is.getItem()).clazz() == clazz)) {
			return null;
		}
		return (T) rotateMode(is);
	}

	static <T extends Enum<T>> T getMode(ItemStack is, Class<T> clazz) {
		if (!(is.getItem() instanceof IToggleTool && ((IToggleTool<?>) is.getItem()).clazz() == clazz)) {
			return null;
		}
		return clazz.getEnumConstants()[is.getTagCompound().getInteger(((IToggleTool<?>) is.getItem()).getTag())];
	}

	static Enum<?> rotateHeldItemMode(EntityLivingBase elb) {
		for (EnumHand hand : EnumHand.values()) {
			final Enum<?> enm = rotateMode(elb.getHeldItem(hand));
			if (enm != null) return enm;
		}
		return null;
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	static void onInput(InputEvent.KeyInputEvent event) {
		if (PlusTiC.proxy.isControlPressed("toggle_tool") && Arrays.stream(EnumHand.values())
				.map(Minecraft.getMinecraft().player::getHeldItem)
				.anyMatch(is -> is.getItem() instanceof IToggleTool)) {
			PacketHandler.INSTANCE.sendToServer(new PacketUpdateToolModeServer());
		}
	}
}
