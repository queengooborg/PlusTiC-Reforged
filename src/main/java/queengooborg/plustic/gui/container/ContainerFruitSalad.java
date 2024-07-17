package queengooborg.plustic.gui.container;

import queengooborg.plustic.traits.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.items.*;
import slimeknights.tconstruct.library.utils.*;

public class ContainerFruitSalad extends Container {
	@CapabilityInject(FruitSalad.IFSItemHandler.class)
	private static Capability<FruitSalad.IFSItemHandler> FS_ITEM_CAP = null;
	
	public ContainerFruitSalad(EntityPlayer player) {	
		//System.out.println("CAP: "+FS_ITEM_CAP);
		if (player.getHeldItemMainhand().hasCapability(FS_ITEM_CAP, null)) {
			//System.out.println("TEEHEE!");
			FruitSalad.IFSItemHandler fshandler = player.getHeldItemMainhand().getCapability(FS_ITEM_CAP, null);
			final int sz = fshandler.getSlots();
			for (int i=0; i<sz; ++i) {
				addSlotToContainer(new SlotItemHandler(fshandler, i, 80 + (i - 2) * 18, 35));
			}
		}
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
	
		for (int k = 0; k < 9; k++) {
			addSlotToContainer(new Slot(player.inventory, k, 8 + k * 18, 142));
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return TinkerUtil.hasTrait(TagUtil.getTagSafe(playerIn.getHeldItemMainhand()),
				FruitSalad.fruitsalad.identifier);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(index);
		
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			
			int containerSlots = inventorySlots.size() - player.inventory.mainInventory.size();
			
			if (index < containerSlots) {
				if (!this.mergeItemStack(itemstack1, containerSlots, inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, containerSlots, false)) {
				return ItemStack.EMPTY;
			}
			
			if (itemstack1.getCount() == 0) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
			
			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}
			
			slot.onTake(player, itemstack1);
		}
		
		return itemstack;
	}
}