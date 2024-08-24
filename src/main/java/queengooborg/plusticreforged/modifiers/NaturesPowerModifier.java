package queengooborg.plusticreforged.modifiers;

import queengooborg.plusticreforged.api.Description;
import queengooborg.plusticreforged.api.Modifier;

public class NaturesPowerModifier extends Modifier {
	public NaturesPowerModifier() {
		super("naturespower", "Nature's Power", new Description("After attacking a mob: 20% chance to burn target. 20% chance to give player Speed. 20% chance to give player Strength."), 0xFFFF00);
	}

	// XXX Convert me!

	//	@Override
	//	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
	//		if (wasHit) {
	//			float rnd = random.nextFloat();
	//			if (rnd < 0.2 && target.isEntityAlive())
	//				target.setFire(3);
	//			else if (rnd < 0.4 && player.isEntityAlive())
	//				player.addPotionEffect(new PotionEffect(MobEffects.SPEED,101));
	//			else if (rnd < 0.6 && player.isEntityAlive())
	//				player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH,101));
	//		}
	//	}
}
