package com.terrapin.rtm.data;

public class Track {
	/**
	 * constructor - nothing to do
	 */
	public Track() {
	}

	public static native void setTrackingAccount(String acct) /*-{

		try {
			_gaq.push([ '_setAccount', acct ]);
			_gaq.push([ '_trackPageview' ]);
		} catch (err) {
			alert('FAILURE: to set account in google analytics: ' + err);
		}

	}-*/;

	public static native void trackEvent(String category, String action) /*-{

		try {
			$wnd._gaq.push([ "_trackEvent", category, action ]);
		} catch (err) {
			alert('FAILURE: to send in event to google analytics: ' + err);
		}

	}-*/;

}

/*
 * // setup tracking object with account var pageTracker =
 * $wnd._gat._getTracker("UA-20560683-1"); // rtm@greenwich.org account
 * 
 * pageTracker._setRemoteServerMode();
 * 
 * // send event to google server pageTracker._trackEvent(category, action);
 */
