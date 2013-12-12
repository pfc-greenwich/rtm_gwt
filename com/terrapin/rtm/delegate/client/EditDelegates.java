/*
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.terrapin.rtm.delegate.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.i18n.client.Dictionary;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.terrapin.rtm.data.DelegateList;
import com.terrapin.rtm.data.MemberNamesMatchesSuggestOracle;
import com.terrapin.rtm.data.MemberNamesMatchesSuggestion;
import com.google.gwt.user.client.ui.Label;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class EditDelegates implements EntryPoint {
    private RootPanel rootPanel;
    private DelegateList delegateList;
    private ListBox listBox;
    private static String adminURI = "/edit/committee/delegates";
    private static String chairmanURI = "/edit/standing-cmte";

    public void onModuleLoad() {
        rootPanel = RootPanel.get("gwt-content");
        if (rootPanel == null) {
            rootPanel = RootPanel.get();
        }
        rootPanel.getElement().getStyle().setPosition(Position.RELATIVE);
        rootPanel.setSize("350", "400");

        Label DistrictLabel = new Label("New label");
        DistrictLabel.setWordWrap(false);
        DistrictLabel.setText("Loading ....");
        rootPanel.add(DistrictLabel, 130, 10);

        ScrollPanel scrollPanel = new ScrollPanel();
        rootPanel.add(scrollPanel, 10, 37);
        scrollPanel.setSize("520px", "423px");

        delegateList = new DelegateList();
        scrollPanel.setWidget(delegateList);
        delegateList.setSize("100%", "100%");
        
        if (Window.Location.getPath().equals(adminURI)) {
            listBox = new ListBox();
            listBox.setSize("120px", "21px");
            listBox.setEnabled(true);
            listBox.addItem("District 1");
            listBox.addItem("District 2");
            listBox.addItem("District 3");
            listBox.addItem("District 4");
            listBox.addItem("District 5");
            listBox.addItem("District 6");
            listBox.addItem("District 7");
            listBox.addItem("District 8");
            listBox.addItem("District 9");
            listBox.addItem("District 10");
            listBox.addItem("District 11");
            listBox.addItem("District 12");

            listBox.addChangeHandler(new ChangeHandler() {
                @Override
                public void onChange(ChangeEvent event) {
                    delegateList.setDistrict(listBox.getSelectedIndex());
                    delegateList.refresh();
                }
            });
            listBox.setVisibleItemCount(1);
            rootPanel.add(listBox, 10, 10);
            DistrictLabel.setText("");
        }
        
        if (Window.Location.getPath().equals(chairmanURI)) {
            Dictionary userInfo = Dictionary.getDictionary("currentUser");
            DistrictLabel.setText("RTM Committee Delegates for District " + userInfo.get("district"));
            delegateList.setDistrict(Integer.parseInt(userInfo.get("district")) - 1);
        }


        delegateList.refresh();
    }
}
