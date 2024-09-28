package landmaster.plustic.util;

import landmaster.plustic.api.ModInfo;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.events.*;
import slimeknights.tconstruct.library.modifiers.*;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = ModInfo.MODID)
public class ModifierRegisterPromise extends CompletableFuture<IModifier> {
	private static final Multimap<String, ModifierRegisterPromise> promises = MultimapBuilder.hashKeys().arrayListValues().build();

	private final String identifier;

	public ModifierRegisterPromise(String identifier) {
		this.identifier = identifier;

		this.whenComplete((modifier, ex) -> promises.remove(this.identifier, this));

		final IModifier modifier = TinkerRegistry.getModifier(this.identifier);
		if (modifier != null) {
			this.complete(modifier);
		} else {
			promises.put(this.identifier, this);
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onModifierRegister(TinkerRegisterEvent.ModifierRegisterEvent event) {
		new ArrayList<>(promises.get(event.getRecipe().getIdentifier()))
				.forEach(promise -> promise.complete(event.getRecipe()));
	}
}
