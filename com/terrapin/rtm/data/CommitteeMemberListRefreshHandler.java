package com.terrapin.rtm.data;

import com.google.gwt.event.shared.EventHandler;

public interface CommitteeMemberListRefreshHandler extends EventHandler {
	void onRefresh(CommitteeMemberListRefreshEvent dre);
}
