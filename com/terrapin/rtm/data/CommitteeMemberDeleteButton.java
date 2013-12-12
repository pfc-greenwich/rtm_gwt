package com.terrapin.rtm.data;

import com.google.gwt.user.client.ui.Button;

public class CommitteeMemberDeleteButton extends Button {
    private String nid;
    private String uid;

    public String getNid() {
            return this.nid;
    }
    
    public String getUid() {
            return this.uid;
    }
    
    public void setNid(String nid) {
            this.nid = nid;
    }
    
    public void setUid(String uid) {
            this.uid = uid;
    }

}
