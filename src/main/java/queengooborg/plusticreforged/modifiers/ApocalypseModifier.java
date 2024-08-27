package queengooborg.plusticreforged.modifiers;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import queengooborg.plusticreforged.api.Description;
import queengooborg.plusticreforged.api.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

public class ApocalypseModifier extends Modifier {
	public ApocalypseModifier() {
		super("apocalypse", "Apocalypse", new Description("The end times...", "Your victim becomes moribund and is afflicted with increasing levels of Wither."), 0x3A2D7D);
		this.usable = true;
	}

	@Override
	public int afterEntityHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt) {
		LivingEntity target = context.getLivingTarget();
		if (target.isAlive()) {
			int amp = -1;
			EffectInstance potionEffect = target.getActiveEffectsMap().get(Effects.WITHER);
			if (potionEffect != null) amp = potionEffect.getAmplifier();
			amp = Math.min(3, amp+1);
			target.addEffect(new EffectInstance(Effects.WITHER, 130, amp));
		}

		return 0;
	}
}
