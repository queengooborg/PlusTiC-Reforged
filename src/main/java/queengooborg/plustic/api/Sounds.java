package queengooborg.plustic.api;

import java.lang.ref.*;

import it.unimi.dsi.fastutil.ints.*;
import queengooborg.plustic.traits.*;
import net.minecraft.client.audio.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraftforge.event.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.relauncher.*;
import slimeknights.tconstruct.library.utils.*;

@Mod.EventBusSubscriber(modid = ModInfo.MODID)
public class Sounds {
	public static final SoundEvent LASER_BEAM = new SoundEvent(new ResourceLocation(ModInfo.MODID, "laser_beam"))
			.setRegistryName("laser_beam");
	
	@SubscribeEvent
	public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
		event.getRegistry().register(LASER_BEAM);
	}
	
	public static void playSoundToAll(Entity entity, SoundEvent sound, float volume, float pitch) {
		entity.getEntityWorld().playSound(null, entity.posX, entity.posY, entity.posZ,
				sound, entity.getSoundCategory(), volume, pitch);
	}
	
	@SideOnly(Side.CLIENT)
	public static class MOTSSound extends MovingSound {
		private WeakReference<EntityPlayer> player;

		public MOTSSound(EntityPlayer player, SoundEvent soundIn, SoundCategory categoryIn) {
			super(soundIn, categoryIn);
			this.player = new WeakReference<>(player);
		}

		@Override
		public void update() {
			if (player.get() == null
					|| !TinkerUtil.hasTrait(TagUtil.getTagSafe(player.get().getHeldItemMainhand()),
					MusicOfTheSpheres.musicofthespheres.identifier)) {
				this.donePlaying = true;
			}
			
			this.xPosF = (float)this.player.get().posX;
			this.yPosF = (float)this.player.get().posY;
			this.zPosF = (float)this.player.get().posZ;
		}
	}
}
