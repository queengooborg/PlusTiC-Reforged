package queengooborg.plusticreforged.modifiers;

import net.minecraft.entity.LivingEntity;
import queengooborg.plusticreforged.api.Description;
import queengooborg.plusticreforged.api.Modifier;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

import javax.annotation.Nullable;

public class NaturesBlessingModifier extends Modifier {
	public NaturesBlessingModifier() {
		super("naturesblessing", "Nature's Blessing", new Description("A chance to drop bread per mob killed or block broken. Sometimes heals the player by a bit after killing a mob."), 0xBEFA5C);
	}

	//	@Override
	//	public void afterBlockBreak(ItemStack tool, World world, IBlockState state, BlockPos pos, EntityLivingBase player, boolean wasEffective) {
	//		dropBread(player.getEntityWorld(), pos.getX(), pos.getY(), pos.getZ(), 0.005f);
	//	}
	//
	//	@Override
	//	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
	//		if (wasHit && !target.isEntityAlive()) {
	//			dropBread(target.getEntityWorld(),target.posX,target.posY,target.posZ,0.05f);
	//			float rnd = random.nextFloat();
	//			if (rnd < 0.3f) player.heal(3.2f);
	//		}
	//	}
	//
	//	protected void dropBread(World world, double x, double y, double z, float chance) {
	//		if (!world.isRemote && random.nextFloat() < chance) {
	//			EntityItem entity = new EntityItem(world, x, y, z, new ItemStack(Items.BREAD));
	//			world.spawnEntity(entity);
	//		}
	//	}
}
