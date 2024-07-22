package queengooborg.plustic.net;

import io.netty.buffer.*;
import queengooborg.plustic.*;
import queengooborg.plustic.gui.handler.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;

public class PacketOpenFSGui implements IMessage {
	public static IMessage onMessage(PacketOpenFSGui packet, MessageContext ctx) {
		IThreadListener mainThread = (WorldServer) ctx.getServerHandler().player.getEntityWorld();
		mainThread.addScheduledTask(() -> {
			EntityPlayer ep = ctx.getServerHandler().player;
			BlockPos bp = ep.getPosition();
			ep.openGui(PlusTiC.INSTANCE, PTGuiHandler.FRUITSALAD, ep.world,
					bp.getX(), bp.getY(), bp.getZ());
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
