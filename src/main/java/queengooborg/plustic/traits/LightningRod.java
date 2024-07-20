package queengooborg.plustic.traits;

import javax.annotation.Nonnull;

import queengooborg.plustic.api.*;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import slimeknights.tconstruct.library.potion.*;
import slimeknights.tconstruct.library.traits.*;

public class LightningRod extends AbstractTrait {
	public static final LightningRod lightningrod = new LightningRod();
	
	public static final LightningPotion lightningPotion = new LightningPotion();
	
	public LightningRod() {
		super("lightningrod", 0x6df5ff);
	}
	
	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (isSelected && (entity.ticksExisted % 20 == 0)) {
			world.addWeatherEffect(new EntityLightningBolt(world, entity.posX + 6*random.nextDouble()-3, entity.posY + 6*random.nextDouble()-3, entity.posZ + 6*random.nextDouble()-3, true));
		}
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		lightningPotion.apply(target, 10*20);
	}
	
	private static class LightningPotion extends TinkerPotion {
		public LightningPotion() {
			super(new ResourceLocation(ModInfo.MODID, "lightning_potion"), true, false, 0x6df5ff);
		}
		
		@Override
		public boolean isReady(int duration, int strength) {
			return duration % 20 == 0;
		}
		
		@Override
	    public void performEffect(@Nonnull EntityLivingBase entity, int id) {
			entity.world.addWeatherEffect(new EntityLightningBolt(entity.world, entity.posX, entity.posY, entity.posZ, false));
		}
	}
}
