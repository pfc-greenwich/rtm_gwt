package com.terrapin.rtm.data;

import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.Timer;

public class MemberNamesMatchesSuggestOracle extends SuggestOracle {
	MemberNameMatches names = new MemberNameMatches();

	@Override
	public void requestSuggestions(final Request request,
			final Callback callback) {
		// TODO Auto-generated method stub
		Timer t = new Timer() {
			@Override
			public void run() {
				names.matchingNames(callback, request, request.getLimit());
			}
		};
		t.schedule(500);

	}

}
