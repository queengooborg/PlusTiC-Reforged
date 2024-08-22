package queengooborg.plusticreforged.modifiers;

import queengooborg.plusticreforged.api.Description;
import queengooborg.plusticreforged.api.Modifier;

public class BloodyMaryModifier extends Modifier {
	public BloodyMaryModifier() {
		super("bloodymary", "Bloody Mary", new Description("Brought to you by the legendary Queen of England...", "Deals bonus damage depending on the amount of health already lost by the target. Also, a chance to drop a Coagulated Blood for each hit."), 0xFF0000);
	}

//	@Override
//	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
//		newDamage += Math.pow(target.getMaxHealth()-target.getHealth(),0.6);
//		return super.damage(tool, player, target, damage, newDamage, isCritical);
//	}
//
//	@Override
//	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
//		if (wasHit) {
//			spillBlood(target.getEntityWorld(),target.posX,target.posY,target.posZ,0.23f);
//		}
//	}
//
//	protected void spillBlood(World world, double x, double y, double z, float chance) {
//		if (!world.isRemote && random.nextFloat() < chance) {
//			EntityItem entity = new EntityItem(world, x, y, z, TinkerCommons.matSlimeBallBlood.copy());
//			world.spawnEntity(entity);
//		}
//	}
}
