/**
 * 
 */
package com.terrapin.rtm.data;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;

/**
 * @author pcurtis
 *
 */
public class OfficerListRefreshEvent extends GwtEvent<OfficerListRefreshHandler> {

    public static Type<OfficerListRefreshHandler> TYPE = new Type<OfficerListRefreshHandler>();

    public Type<OfficerListRefreshHandler> getAssociatedType() {
        return TYPE;
    }

    protected void dispatch(OfficerListRefreshHandler handler) {
        handler.onRefresh(this);
    }

}
