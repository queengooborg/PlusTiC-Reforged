package landmaster.plustic.net;

import io.netty.buffer.*;
import landmaster.plustic.tile.TECentrifuge;
import landmaster.plustic.util.Coord4D;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketUpdateTECentrifugeLiquid implements IMessage {
	private Coord4D pos;
	private FluidStack input;

	public PacketUpdateTECentrifugeLiquid() {
	}

	public PacketUpdateTECentrifugeLiquid(Coord4D pos, FluidStack input) {
		this.pos = pos;
		this.input = input;
	}

	public static IMessage onMessage(PacketUpdateTECentrifugeLiquid message, MessageContext ctx) {
		Minecraft.getMinecraft().addScheduledTask(() -> {
			if (Minecraft.getMinecraft().world.provider.getDimension() != message.pos.dimensionId) return;
			TileEntity te = Minecraft.getMinecraft().world.getTileEntity(message.pos.pos());
			if (te instanceof TECentrifuge) {
				//System.out.println(message.input == null ? 0 : message.input.amount);
				((TECentrifuge) te).getTank().setFluid(message.input);
			}
		});
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		pos = Coord4D.fromByteBuf(buf);
		input = FluidStack.loadFluidStackFromNBT(ByteBufUtils.readTag(buf));
	}

	@Override
	public void toBytes(ByteBuf buf) {
		pos.toByteBuf(buf);
		ByteBufUtils.writeTag(buf, input != null ? input.writeToNBT(new NBTTagCompound()) : null);
	}

}
