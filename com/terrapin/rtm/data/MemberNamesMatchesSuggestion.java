package com.terrapin.rtm.data;

import java.io.Serializable;

import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

public class MemberNamesMatchesSuggestion implements Suggestion, Serializable {

    private String name;
    private String uid;

    public MemberNamesMatchesSuggestion(String name) {
            this.name = name;
    }

    public MemberNamesMatchesSuggestion(String name, String uid) {
            this.name = name;
            this.uid = uid;
    }

    @Override
    public String getDisplayString() {
            // return "<div class='reference-autocomplete'>" + name + "</div>";
            return name;
    }

    @Override
    public String getReplacementString() {
            return name;
    }

    public String getUid() {
            return uid;
    }

    public void setUid(String uid) {
            this.uid = uid;
    }


}
