package queengooborg.plusticreforged.modifiers;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import queengooborg.plusticreforged.api.Description;
import queengooborg.plusticreforged.api.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;
import slimeknights.tconstruct.shared.TinkerCommons;
import slimeknights.tconstruct.shared.block.SlimeType;

import javax.annotation.Nullable;
import java.util.Random;

public class NaturesBlessingModifier extends Modifier {
	private Random random;

	public NaturesBlessingModifier() {
		super("naturesblessing", "Nature's Blessing", new Description("A chance to drop bread per mob killed or block broken. Sometimes heals the player by a bit after killing a mob."), 0xBEFA5C);
		this.usable = true;
	}

	@Override
	public void afterBlockBreak(IModifierToolStack tool, int level, ToolHarvestContext context) {
		dropBread(context.getWorld(), context.getPos(), 0.005f);
	}

	@Override
	public int afterEntityHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt) {
		if (!context.getLivingTarget().isAlive()) {
			dropBread(context.getLivingTarget().level, context.getLivingTarget().blockPosition(), 0.05f);
			float rnd = random.nextFloat();
			if (rnd < 0.3f) {
				context.getPlayerAttacker().heal(3.2f);
			}
		}

		return 0;
	}

	protected void dropBread(World world, BlockPos pos, float chance) {
		if (random.nextFloat() < chance) {
			ItemEntity entity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Items.BREAD, 1));
			world.addFreshEntity(entity);
		}
	}
}
