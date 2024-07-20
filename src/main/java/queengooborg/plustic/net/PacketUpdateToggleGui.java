package queengooborg.plustic.net;

import java.util.*;

import io.netty.buffer.*;
import queengooborg.plustic.api.*;
import net.minecraft.client.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;

public class PacketUpdateToggleGui implements IMessage {

	private String identifier;
	private boolean value;
	
	public PacketUpdateToggleGui() {}
	public PacketUpdateToggleGui(String identifier, boolean value) {
		this.identifier = identifier;
		this.value = value;
	}
	
	public static IMessage onMessage(PacketUpdateToggleGui message, MessageContext ctx) {
		Minecraft.getMinecraft().addScheduledTask(() -> {
			if (Minecraft.getMinecraft().currentScreen instanceof Toggle.Gui) {
				((Toggle.Gui)Minecraft.getMinecraft().currentScreen).update(message.identifier, message.value);
			}
		});
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		identifier = ByteBufUtils.readUTF8String(buf);
		value = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, identifier);
		buf.writeBoolean(value);
	}
}
