package com.terrapin.rtm.data.json;

import com.google.gwt.core.client.JavaScriptObject;

public class MemberNamesMatchesEntry extends JavaScriptObject {

	protected MemberNamesMatchesEntry() {}
	
	public final native String getRealName() /*-{
		return this.realname;
	}-*/;
	
	public final native String getUid() /*-{
		return this.uid;
	}-*/;

}
