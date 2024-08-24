package queengooborg.plusticreforged.modifiers;

import queengooborg.plusticreforged.api.Description;
import queengooborg.plusticreforged.api.Modifier;

public class ThunderingModifier extends Modifier {
	public ThunderingModifier() {
		super("thundering", "Thundering", new Description("Summon a thunderbolt on impact."), 0xFFD000);
	}

	// XXX Convert me!

//	@Override
//	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
//		target.getEntityWorld().addWeatherEffect(
//				new EntityLightningBolt(target.getEntityWorld(), target.posX, target.posY, target.posZ, false)
//				);
//	}
}
