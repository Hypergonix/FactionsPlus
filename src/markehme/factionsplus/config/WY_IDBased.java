package markehme.factionsplus.config;

import markehme.factionsplus.extras.*;



public abstract class WY_IDBased extends WYItem {
	
	private String	id;
	
	
	public WY_IDBased( int lineNumber, String identifier){//, WYSection _parent, WYItem _prev ) {
		super( lineNumber);//, _parent, _prev );
		id = identifier;
		assert Q.nn( id );
	}
	
	
	public String getId() {
		return id;
	}
	
	
	protected void setId( String string ) {
		id = string;
	}
	
	
	public String getInAbsoluteDottedForm() {
		return getInAbsoluteDottedForm( null );
	}
	
	
	/**
	 * @param upToAndExcluding
	 *            can be null
	 * @return ie. extras.lwc.disableSomething
	 */
	public String getInAbsoluteDottedForm( WYSection upToAndExcluding ) {
		String df = "";
		WYSection p = getParent();
		if ( ( null != p ) && ( !p.equals( upToAndExcluding ) ) ) {
			df = p.getInAbsoluteDottedForm( upToAndExcluding ) + Config.DOT;
		}
		return df + this.getId();
	}
	
	
	@Override
	public String toString() {
		return getId() + WannabeYaml.IDVALUE_SEPARATOR;
	}
}
