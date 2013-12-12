package com.terrapin.rtm.data.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class MemberArray extends JavaScriptObject {

	protected MemberArray() {}
	
	public final native JsArray<MemberEntry> getMembers() /*-{
		return this.matches;
	}-*/;
}
