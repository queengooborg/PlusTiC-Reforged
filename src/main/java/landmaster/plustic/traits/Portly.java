package landmaster.plustic.traits;

import landmaster.plustic.net.*;
import landmaster.plustic.proxy.*;
import net.minecraft.client.resources.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.text.*;
import net.minecraft.world.*;
import net.minecraftforge.common.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.relauncher.*;
import slimeknights.tconstruct.library.traits.*;
import slimeknights.tconstruct.library.utils.*;

public class Portly extends AbstractTrait {
	public static final Portly portly = new Portly();
	
	public Portly() {
		super("portly", 0x00443B);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (isSelected && FMLCommonHandler.instance().getSide().isClient()) {
			if (CommonProxy.keyBindings.get(0).isPressed()) {
				PacketHandler.INSTANCE.sendToServer(new PacketReleaseEntity());
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void captureEntity(PlayerInteractEvent.EntityInteractSpecific event) {
		NBTTagCompound nbt = TagUtil.getTagSafe(event.getItemStack());
		if (event.getWorld().isRemote
				|| event.isCanceled()
				|| !event.getEntityPlayer().isSneaking()
				|| event.getItemStack() == null
				|| !TinkerUtil.hasTrait(nbt, getIdentifier())
				|| ToolHelper.getCurrentDurability(event.getItemStack()) < durabilityCost(event.getTarget())
				|| nbt.hasKey("portlyGentleman", 10)
				|| event.getTarget() instanceof EntityPlayer)
			return;
		synchronized (event.getTarget()) {
			nbt.setTag("portlyGentleman", event.getTarget().serializeNBT());
			event.getItemStack().setTagCompound(nbt);
			event.getWorld().removeEntity(event.getTarget());
		}
		ToolHelper.damageTool(event.getItemStack(), durabilityCost(event.getTarget()), event.getEntityLiving());
		event.getEntityPlayer().swingArm(event.getHand());
		event.getEntityPlayer().addChatMessage(new TextComponentTranslation(
				"msg.plustic.portlymodifier.set", nbt.getCompoundTag("portlyGentleman").getString("id")));
	}
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void tooltip(ItemTooltipEvent event) {
		NBTTagCompound nbt = TagUtil.getTagSafe(event.getItemStack());
		if (event.isCanceled()
				|| event.getItemStack() == null
				|| !TinkerUtil.hasTrait(nbt, getIdentifier())) return;
		if (nbt.hasKey("portlyGentleman", 10)) {
			event.getToolTip().add(I18n.format("tooltip.plustic.portlymodifier.info",
					nbt.getCompoundTag("portlyGentleman").getString("id")));
		}
	}
	private int durabilityCost(Entity entity) {
		return Math.max(15, entity instanceof EntityLivingBase ?
				(int)((EntityLivingBase)entity).getHealth() : 15);
	}
}
