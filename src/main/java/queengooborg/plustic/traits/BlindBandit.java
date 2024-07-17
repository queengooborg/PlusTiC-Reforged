package queengooborg.plustic.traits;

import queengooborg.plustic.entity.*;

public class BlindBandit extends EntityCameoTrait {
	public static final BlindBandit blindbandit = new BlindBandit();
	
	public BlindBandit() {
		super("blindbandit", 0xFF00FF, EntityBlindBandit::new);
	}
}
