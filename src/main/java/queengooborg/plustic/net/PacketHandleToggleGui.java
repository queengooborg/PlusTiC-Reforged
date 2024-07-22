package queengooborg.plustic.net;

import io.netty.buffer.*;
import queengooborg.plustic.api.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import slimeknights.tconstruct.library.utils.*;

public class PacketHandleToggleGui implements IMessage {
	private String identifier;

	public PacketHandleToggleGui() {
	}

	public PacketHandleToggleGui(String identifier) {
		this.identifier = identifier;
	}

	public static IMessage onMessage(PacketHandleToggleGui message, MessageContext ctx) {
		IThreadListener mainThread = (WorldServer) ctx.getServerHandler().player.getEntityWorld();
		mainThread.addScheduledTask(() -> {
			EntityPlayerMP ep = ctx.getServerHandler().player;
			if (ep.getEntityWorld().isRemote) {
				return;
			}
			boolean newState = false;
			NBTTagCompound nbt = TagUtil.getTagSafe(ep.getHeldItemMainhand());
			newState = !Toggle.getToggleState(nbt, message.identifier);
			Toggle.setToggleState(nbt, message.identifier, newState);
			ep.getHeldItemMainhand().setTagCompound(nbt);
			ep.inventory.markDirty();
			PacketHandler.INSTANCE.sendTo(new PacketUpdateToggleGui(message.identifier, newState), ep);
		});
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		identifier = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, identifier);
	}
}
