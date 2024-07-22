package queengooborg.plustic.net;

import io.netty.buffer.*;
import queengooborg.plustic.api.*;
import net.minecraft.client.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;

public class PacketOpenToggleGui implements IMessage {
	public PacketOpenToggleGui() {
	}

	public static IMessage onMessage(PacketOpenToggleGui message, MessageContext ctx) {
		proxy.handle(message, ctx);
		return null;
	}

	@SidedProxy(clientSide = "queengooborg.plustic.net.PacketOpenToggleGui$ProxyClient", serverSide = "queengooborg.plustic.net.PacketOpenToggleGui$Proxy")
	public static Proxy proxy;

	public static class Proxy {
		public void handle(PacketOpenToggleGui message, MessageContext ctx) {
		}
	}

	public static class ProxyClient extends Proxy {
		@Override
		public void handle(PacketOpenToggleGui message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				Minecraft.getMinecraft().displayGuiScreen(new Toggle.Gui(Minecraft.getMinecraft().player));
			});
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
	}

	@Override
	public void toBytes(ByteBuf buf) {
	}

}
