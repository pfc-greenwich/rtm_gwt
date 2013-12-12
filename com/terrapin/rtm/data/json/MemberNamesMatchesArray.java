package com.terrapin.rtm.data.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class MemberNamesMatchesArray extends JavaScriptObject {

	protected MemberNamesMatchesArray() {}
	
	public final native JsArray<MemberNamesMatchesEntry> getMemberNames() /*-{
		return this.matches;
	}-*/;
	
}
