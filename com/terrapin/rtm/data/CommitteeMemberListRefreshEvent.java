package com.terrapin.rtm.data;

import com.google.gwt.event.shared.GwtEvent;

public class CommitteeMemberListRefreshEvent extends GwtEvent<CommitteeMemberListRefreshHandler> {

	public static Type<CommitteeMemberListRefreshHandler> TYPE = new Type<CommitteeMemberListRefreshHandler>();

	public Type<CommitteeMemberListRefreshHandler> getAssociatedType() {
	    return TYPE;
	}

	@Override
	protected void dispatch(CommitteeMemberListRefreshHandler handler) {
	    handler.onRefresh(this);
	}
}