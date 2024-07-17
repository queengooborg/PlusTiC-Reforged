package queengooborg.plustic.traits;

import queengooborg.plustic.entity.*;

public class DPRK extends EntityCameoTrait {
	public static final DPRK dprk = new DPRK();
	
	public DPRK() {
		super("dprk", 0xE30000, EntitySupremeLeader::new);
	}
}
