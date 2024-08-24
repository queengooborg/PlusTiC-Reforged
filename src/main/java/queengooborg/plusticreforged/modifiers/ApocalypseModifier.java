package queengooborg.plusticreforged.modifiers;

import queengooborg.plusticreforged.api.Description;
import queengooborg.plusticreforged.api.Modifier;

public class ApocalypseModifier extends Modifier {
	public ApocalypseModifier() {
		super("apocalypse", "Apocalypse", new Description("The end times...", "Your victim becomes moribund and is afflicted with increasing levels of Wither."), 0x3A2D7D);
	}

	// XXX Convert me!

//	@Override
//	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
//		if (wasHit && target.isEntityAlive()) {
//			int amp = -1;
//			PotionEffect potionEffect = target.getActivePotionEffect(MobEffects.WITHER);
//			if (potionEffect != null) amp = potionEffect.getAmplifier();
//			amp = Math.min(3, amp+1);
//			target.addPotionEffect(new PotionEffect(MobEffects.WITHER, 130, amp));
//		}
//	}
}
