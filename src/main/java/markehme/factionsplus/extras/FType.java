package markehme.factionsplus.extras;

import com.massivecraft.factions.FFlag;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.UConf;

/**
 * Anyone is free to use this FType enum as long as credit is 
 * given where credit is due. :-)
 * 
 * @author MarkehMe
 *
 */
public enum FType {
	
	FACTION (1, "Faction", 			true,  false, false, false),
	SAFEZONE (2, "SafeZone", 		false, true,  false, false),
	WARZONE (3, "WarZone", 			false, false, true,  false),
	WILDERNESS (4, "Wilderness",	false, false, false, true),
	;

	private final int _id;
	private final boolean iFaction;
	private final boolean iSafezone;
	private final boolean iWarZone;
	private final boolean iWilderness;
	private final String nName;
	
	public int getID() { return this._id; }
	public boolean isFaction() { return this.iFaction; }
	public boolean isSafeZone() { return this.iSafezone; }
	public boolean isWarZone() { return this.iWarZone; }
	public boolean isWilderness() { return this.iWilderness; }
	
	/**
	 * FType value setter, not public. Does not need to be used outside of this enum. 
	 * @param id
	 * @param isFaction
	 * @param isSafezone
	 * @param isWarZone
	 * @param isWilderness
	 */
	private FType(final int id, final String niceName, final boolean isFaction, final boolean isSafezone, final boolean isWarZone, final boolean isWilderness) {
		this._id = id;
		this.iFaction = isFaction;
		this.iSafezone = isSafezone;
		this.iWarZone = isWarZone;
		this.iWilderness = isWilderness;
		this.nName = niceName;

	}
	
	/**
	 * Turns a string (e.g. "wilderness" and returns an FType)
	 * @param str
	 * @return
	 */
	public static FType parse( String str ) {
		if(str == null) return null;

		String ty = str.trim().toLowerCase();
		
		if(ty == "wilderness") return WILDERNESS;
		if(ty == "warzone") return WARZONE;
		if(ty == "safezone") return SAFEZONE;
		if(ty == "faction") return FACTION;

		return null;
	}
	
	/**
	 * Pass a Faction to valueOf and it'll spit back the FType
	 * @param faction
	 * @return
	 */
	public static FType valueOf(Faction faction) {
		
		if(faction == null) return FType.WILDERNESS;
		
		// isNone always returns wilderness/none, easiest to detect 
		if(faction.isNone()) {
			return FType.WILDERNESS;
		}
		
		// If leader is not null (as in, there is a leader) it's a faction
		// NOTE: Turns out, Permanent factions CAN have leaders (UConf: permanentFactionsDisableLeaderPromotion)
		//		.. so, validate that it's not a permanent Faction
		if(faction.getLeader() != null && !faction.getFlag(FFlag.PERMANENT)) {
			return FType.FACTION;
			
		}
		
		// If there is no PVP AND it is a PERMANENT Faction then it must be a safezone, or is
		// the official safezone
		if((!faction.getFlag(FFlag.PVP) && faction.getFlag(FFlag.PERMANENT)) ||
			(faction.getId().equals(UConf.get(faction.getUniverse()).factionIdSafezone))) {
			
			return FType.SAFEZONE;
			
		}
		
		// If there is PvP, and it's not wilderness, and its permanent .. then it must be a warzone
		if(faction.getFlag( FFlag.PVP ) && faction.getFlag(FFlag.PERMANENT) ||
			(faction.getId().equals(UConf.get(faction.getUniverse()).factionIdWarzone))) {
			
			return FType.WARZONE;
			
		}
		
		// If FType is null, then Factions changed something and this library is out of date. #ohshit 
		return null;		
	}
	
	public String getNiceName() {
		return(this.nName);
	}
	
}