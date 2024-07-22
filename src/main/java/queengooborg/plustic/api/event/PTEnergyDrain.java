package queengooborg.plustic.api.event;

import net.minecraft.item.*;
import net.minecraftforge.event.entity.player.*;

public class PTEnergyDrain extends PlayerEvent {
	private final ItemStack tool;
	public int energyDrained;

	public PTEnergyDrain(ItemStack tool, EntityPlayer player, int energyDrained) {
		super(player);
		this.tool = tool;
		this.energyDrained = energyDrained;
	}

	public ItemStack getTool() {
		return tool;
	}
}
