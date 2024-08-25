package queengooborg.plusticreforged.modifiers;

import queengooborg.plusticreforged.api.Description;
import queengooborg.plusticreforged.api.Modifier;

public class DarkTravelerModifier extends Modifier {
	public DarkTravelerModifier() {
		super("dark_traveler", "Dark Traveler", new Description("Surrounding mobs get randomly afflicted with damage."), 0x270133);
	}

	// XXX Convert me!

//	@Override
//	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
//		if (world.isRemote || !isSelected) return;
//		if (random.nextFloat() < 0.035f && ToolHelper.getCurrentDurability(tool) >= 1) {
//			List<EntityLiving> lst = world.getEntitiesWithinAABB(EntityLiving.class,
//					Utils.AABBfromVecs(entity.getPositionVector().subtract(8,8,8),
//							entity.getPositionVector().add(8,8,8)),
//					ent -> ent instanceof IMob && ent != entity);
//			if (lst.size() > 0) {
//				EntityLiving randomEntity = lst.get(random.nextInt(lst.size()));
//				randomEntity.attackEntityFrom(new EntityDamageSource("darktraveler", entity).setDamageBypassesArmor(),
//						2f+random.nextFloat()*2.5f);
//				ToolHelper.damageTool(tool, 1, entity instanceof EntityLivingBase
//						? (EntityLivingBase)entity : null);
//			}
//		}
//	}
}
