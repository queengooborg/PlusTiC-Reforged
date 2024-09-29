package queengooborg.plusticreforged.modifiers;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import queengooborg.plusticreforged.api.Description;
import queengooborg.plusticreforged.api.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

import java.awt.*;
import java.util.Random;

public class NaturesPowerModifier extends Modifier {
	private Random random;

	public NaturesPowerModifier() {
		super("naturespower", "Nature's Power", new Description("After attacking a mob: 20% chance to burn target. 20% chance to give player Speed. 20% chance to give player Strength."), new Color(255, 255, 0));
		this.usable = true;
	}

	public int afterEntityHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt) {
		LivingEntity target = context.getLivingTarget();
		LivingEntity player = context.getPlayerAttacker();

		float rnd = random.nextFloat();
		if (rnd < 0.2 && target.isAlive()) {
			target.setSecondsOnFire(3);
		} else if (rnd < 0.4 && player.isAlive()) {
			player.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 101));
		} else if (rnd < 0.6 && player.isAlive()) {
			player.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 101));
		}

		return 0;
	}
}
