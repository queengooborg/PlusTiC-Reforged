package queengooborg.plustic.traits;

import net.minecraft.item.*;
import slimeknights.tconstruct.library.traits.*;

public class Thundering extends AbstractTrait {
	public static final Thundering thundering = new Thundering();

	public Thundering() {
		super("thundering", 0xFFFF00);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		target.getEntityWorld().addWeatherEffect(
				new EntityLightningBolt(target.getEntityWorld(), target.posX, target.posY, target.posZ, false)
		);
	}
}
