package queengooborg.plusticreforged.modifiers;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import queengooborg.plusticreforged.api.Description;
import queengooborg.plusticreforged.api.Modifier;

public class NaturesWrathModifier extends Modifier {
	public NaturesWrathModifier() {
		super("natureswrath", "Nature's Wrath", new Description("Nature's wrath burning...", "After attacking a mob, there is a chance to burn the target, as well as to heal the player."), 0x007523);
	}

	// XXX Convert me!

//	@Override
//	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
//		if (wasHit) {
//			float rnd = random.nextFloat();
//			if (rnd < 0.2f && target.isEntityAlive()) {
//				target.setFire(5);
//			}
//			if (rnd < 0.5f) player.heal(1.4f);
//		}
//	}
}
