package com.terrapin.rtm.data;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;

public class DelegateListRefreshEvent extends
		GwtEvent<DelegateListRefreshHandler> {

	public static Type<DelegateListRefreshHandler> TYPE = new Type<DelegateListRefreshHandler>();

	public Type<DelegateListRefreshHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(DelegateListRefreshHandler handler) {
		handler.onRefresh(this);
	}
}
