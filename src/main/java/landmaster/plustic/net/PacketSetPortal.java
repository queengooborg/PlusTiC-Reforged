package landmaster.plustic.net;

import io.netty.buffer.*;
import landmaster.plustic.api.Portal;
import landmaster.plustic.util.Coord4D;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import slimeknights.tconstruct.library.utils.*;

public class PacketSetPortal implements IMessage {
	@CapabilityInject(Portal.IPortalArmor.class)
	private static final Capability<Portal.IPortalArmor> PORTAL_ARMOR = null;

	public static IMessage onMessage(PacketSetPortal message, MessageContext ctx) {
		IThreadListener mainThread = (WorldServer) ctx.getServerHandler().player.getEntityWorld();
		mainThread.addScheduledTask(() -> {
			EntityPlayerMP ep = ctx.getServerHandler().player;
			if (ep.getEntityWorld().isRemote) {
				return;
			}
			Vec3d eye = ep.getPositionVector().add(0, ep.getEyeHeight(), 0);
			Vec3d look = ep.getLookVec().scale(5);
			RayTraceResult rtr = ep.getEntityWorld().rayTraceBlocks(eye, eye.add(look));
			if (rtr == null || rtr.getBlockPos() == null) return;
			if (!ep.isSneaking()) {
				NBTTagCompound nbt = TagUtil.getTagSafe(ep.getHeldItemMainhand());
				if (Portal.canUse(nbt)) {
					NBTTagCompound nick = new NBTTagCompound();
					nick.setInteger("x", rtr.getBlockPos().getX());
					nick.setInteger("y", rtr.getBlockPos().getY());
					nick.setInteger("z", rtr.getBlockPos().getZ());
					nick.setInteger("dim", ep.getEntityWorld().provider.getDimension());
					nbt.setTag(Portal.PORTAL_NBT, nick);
					ep.getHeldItemMainhand().setTagCompound(nbt);
					ep.sendMessage(new TextComponentTranslation(
							"msg.plustic.portal.set", nick.getInteger("x"),
							nick.getInteger("y"), nick.getInteger("z"), nick.getInteger("dim")));
				}
			} else if (ep.hasCapability(PORTAL_ARMOR, null)) {
				Coord4D newCoord = new Coord4D(rtr.getBlockPos(), ep.getEntityWorld());
				ep.getCapability(PORTAL_ARMOR, null).location(newCoord);
				ep.sendMessage(new TextComponentTranslation(
						"msg.plustic.portal.armor.set", newCoord.xCoord, newCoord.yCoord, newCoord.zCoord, newCoord.dimensionId));
			}
		});
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
	}

	@Override
	public void toBytes(ByteBuf buf) {
	}
}
