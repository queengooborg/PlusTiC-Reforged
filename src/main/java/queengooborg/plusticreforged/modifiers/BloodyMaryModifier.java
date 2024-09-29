package queengooborg.plusticreforged.modifiers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import queengooborg.plusticreforged.api.Description;
import queengooborg.plusticreforged.api.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;
import slimeknights.tconstruct.shared.TinkerCommons;
import slimeknights.tconstruct.shared.block.SlimeType;

import java.awt.*;
import java.util.Random;

public class BloodyMaryModifier extends Modifier {
	private Random random;

	public BloodyMaryModifier() {
		super("bloodymary", "Bloody Mary", new Description("Brought to you by the legendary Queen of England...", "Deals bonus damage depending on the amount of health already lost by the target. Also, a chance to drop a Coagulated Blood for each hit."), new Color(255, 0, 0));
		this.usable = true;
	}

	@Override
	public float getEntityDamage(IModifierToolStack tool, int level, ToolAttackContext context, float baseDamage, float damage) {
		return (float) (damage + Math.pow(context.getLivingTarget().getMaxHealth() - context.getLivingTarget().getHealth(), 0.6));
	}

	@Override
	public int afterEntityHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt) {
		Entity target = context.getTarget();
		spillBlood(target.level, target.getX(), target.getY(), target.getZ(), 0.23f);
		return 0;
	}

	protected void spillBlood(World world, double x, double y, double z, float chance) {
		if (random.nextFloat() >= chance) {
			ItemEntity entity = new ItemEntity(world, x, y, z, new ItemStack(TinkerCommons.slimeball.get(SlimeType.BLOOD)));
			world.addFreshEntity(entity);
		}
	}
}
