package queengooborg.plustic.net;

import java.util.*;
import java.util.stream.*;

import io.netty.buffer.*;
import queengooborg.plustic.api.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;

public class PacketRequestToggleGui implements IMessage {
	public static IMessage onMessage(PacketRequestToggleGui message, MessageContext ctx) {
		IThreadListener mainThread = (WorldServer)ctx.getServerHandler().player.getEntityWorld();
		mainThread.addScheduledTask(() -> {
			if (Toggle.canToggle(ctx.getServerHandler().player.getHeldItemMainhand())) {
				PacketHandler.INSTANCE.sendTo(new PacketOpenToggleGui(), ctx.getServerHandler().player);
			}
		});
		return null;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {}
	
	@Override
	public void toBytes(ByteBuf buf) {}
	
}
