package queengooborg.plustic.traits;

import java.util.*;
import java.util.function.*;

import queengooborg.plustic.api.*;
import queengooborg.plustic.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.util.text.*;
import net.minecraft.world.*;
import net.minecraftforge.common.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.items.*;
import slimeknights.tconstruct.library.traits.*;
import slimeknights.tconstruct.library.utils.*;

/**
 * Abstract class for PlusTiC traits that rescue the holder from death given certain items.
 * @author Landmaster
 *
 */
public abstract class DeathSaveTrait extends AbstractTrait {
	private final int cost;
	private final Predicate<ItemStack> stackMatcher;
	private final String unlocSaveMessage;
	
	public DeathSaveTrait(String identifier, int color, int cost, Predicate<ItemStack> stackMatcher, String unlocSaveMessage) {
		super(identifier, color);
		this.cost = cost;
		this.stackMatcher = stackMatcher;
		this.unlocSaveMessage = unlocSaveMessage;
		MinecraftForge.EVENT_BUS.register(this);
		Toggle.addToggleable(identifier);
		Portal.addPortalable(identifier);
	}
	
	@Override
	public String getLocalizedDesc() {
		// add the item cost to the description
		return String.format(super.getLocalizedDesc(), cost);
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void timing(LivingDeathEvent event) {
		if (event.getEntity().getEntityWorld().isRemote
				|| !(event.getEntity() instanceof EntityPlayerMP)) {
			return;
		}

		Arrays.stream(EnumHand.values())
		.map(event.getEntityLiving()::getHeldItem)
		.map(TagUtil::getTagSafe)
		.filter(nbt -> TinkerUtil.hasTrait(nbt, identifier)
				&& Toggle.getToggleState(nbt, identifier)
				&& nbt.hasKey(Portal.PORTAL_NBT, 10))
		.map(nbt -> nbt.getCompoundTag(Portal.PORTAL_NBT))
		.map(Coord4D::fromNBT)
		.filter(coord -> Utils.canTeleportTo((EntityPlayer)event.getEntity(), coord))
		.findFirst().ifPresent(coord -> {
			checkItems(event, coord);
		});
	}
	
	private void checkItems(LivingDeathEvent event, Coord4D coord) {
		IItemHandler ih = event.getEntity().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		for (int i=0; i<ih.getSlots(); ++i) {
			ItemStack is = ih.extractItem(i, cost, true);
			if (stackMatcher.test(is) && is.getCount() >= cost) {
				ih.extractItem(i, cost, false);
				event.setCanceled(true);
				event.getEntityLiving().clearActivePotions();
				MinecraftForge.EVENT_BUS.register(new Object() {
					@SubscribeEvent
					public void onServerTick(TickEvent.ServerTickEvent event0) {
						if (!event.getEntityLiving().isBurning()) {
							MinecraftForge.EVENT_BUS.unregister(this);
						}
						event.getEntityLiving().extinguish();
					}
				});
				event.getEntityLiving().setHealth(1);
				event.getEntityLiving().addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 160, 1));
				event.getEntityLiving().addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 160));
				event.getEntity().sendMessage(new TextComponentTranslation(
						unlocSaveMessage));
				event.getEntity().fallDistance = 0;
				Utils.teleportPlayerTo((EntityPlayerMP)event.getEntity(), coord);
				return;
			}
		}
	}
}
