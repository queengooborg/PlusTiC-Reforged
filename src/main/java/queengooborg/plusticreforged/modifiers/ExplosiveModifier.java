package queengooborg.plusticreforged.modifiers;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import queengooborg.plusticreforged.api.Description;
import queengooborg.plusticreforged.api.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

import javax.annotation.Nullable;

public class ExplosiveModifier extends Modifier {
	public ExplosiveModifier() {
		super("explosive", "Explosive", new Description("As the name implies, an explosion is created when the tool is used."), 0xFF4F4F);
	}

	// XXX Convert me!

	// @Override
	//	public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical) {
	//		if (!player.getEntityWorld().isRemote)
	//			target.getEntityWorld().createExplosion(player, target.posX, target.posY, target.posZ, 2.4f, false);
	//	}
}
