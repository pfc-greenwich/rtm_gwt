/**
 * 
 */
package com.terrapin.rtm.data;

import com.google.gwt.event.shared.EventHandler;

/**
 * @author pcurtis
 *
 */
public interface OfficerListRefreshHandler extends EventHandler {
    void onRefresh(OfficerListRefreshEvent dre);
}
