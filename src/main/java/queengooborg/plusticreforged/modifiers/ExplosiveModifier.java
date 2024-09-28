package queengooborg.plusticreforged.modifiers;

import net.minecraft.entity.LivingEntity;
import net.minecraft.world.Explosion;
import queengooborg.plusticreforged.api.Description;
import queengooborg.plusticreforged.api.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

public class ExplosiveModifier extends Modifier {
	public ExplosiveModifier() {
		super("explosive", "Explosive", new Description("As the name implies, an explosion is created when the tool is used."), 0xFF4F4F);
		this.usable = true;
	}

	@Override
	public int afterEntityHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt) {
		LivingEntity target = context.getLivingTarget();

		if (target != null) {
			target.level.explode(context.getLivingTarget(), context.getLivingTarget().getX(), context.getLivingTarget().getY(), context.getLivingTarget().getZ(), 0.5f, false, Explosion.Mode.NONE);
		}

		return 0;
	}
}
