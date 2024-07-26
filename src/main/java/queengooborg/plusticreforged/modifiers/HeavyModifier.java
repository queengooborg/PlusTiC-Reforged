package queengooborg.plusticreforged.modifiers;

import queengooborg.plusticreforged.api.Description;
import queengooborg.plusticreforged.api.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

public class HeavyModifier extends Modifier {
	public HeavyModifier() {
		super("heavy_metal", "Heavy", null, 0x555555);
	}

	@Override
	public float beforeEntityHit(IModifierToolStack tool, int level, ToolAttackContext context, float damage, float baseKnockback, float knockback) {
		return knockback * 1.3f;
	}

	@Override
	public int afterEntityHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt) {
		if ((damageDealt > 0) && context.getTarget().isAlive()) {
			// XXX Figure me out!
//			context.getTarget().addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 120, 1));
		}

		return 0;
	}
}
