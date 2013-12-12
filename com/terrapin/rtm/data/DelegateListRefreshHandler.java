package com.terrapin.rtm.data;

import com.google.gwt.event.shared.EventHandler;

public interface DelegateListRefreshHandler extends EventHandler {
	void onRefresh(DelegateListRefreshEvent dre);
}
