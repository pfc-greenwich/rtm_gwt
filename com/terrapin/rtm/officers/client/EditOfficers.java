/**
 * 
 */
package com.terrapin.rtm.officers.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.terrapin.rtm.data.AppUtils;
import com.terrapin.rtm.data.CommitteeMemberListRefreshEvent;
import com.terrapin.rtm.data.DelegateList;
import com.terrapin.rtm.data.OfficerList;
import com.terrapin.rtm.data.OfficerListRefreshEvent;
import com.google.gwt.user.client.ui.Image;

/**
 * @author pcurtis
 *
 */
public class EditOfficers implements EntryPoint {
    private RootPanel rootPanel;
    private String selection;
    private OfficerList officerList;
    private RadioButton Committee;
    private RadioButton District;
    private ListBox listBox;
    private Image waitIcon;
    /* (non-Javadoc)
     * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
     */
    /**
     * @wbp.parser.entryPoint
     */
    @Override
    public void onModuleLoad() {
        rootPanel = RootPanel.get("gwt-content");
        if (rootPanel == null) {
            rootPanel = RootPanel.get();
        }
        rootPanel.getElement().getStyle().setPosition(Position.RELATIVE);
        rootPanel.setSize("350", "400");

        waitIcon = new Image("/files/gwt/wait24trans.gif");
        rootPanel.add(waitIcon, 406, 60);
        waitIcon.setSize("25px", "25px");
        waitIcon.setVisible(true);
        
        ScrollPanel scrollPanel = new ScrollPanel();
        rootPanel.add(scrollPanel, 10, 104);
        scrollPanel.setSize("430px", "196px");
        
        Committee = new RadioButton("selectOfficers", "Committee");
        rootPanel.add(Committee, 40, 10);
        Committee.addClickHandler(new ClickHandler(){
            @Override
            public void onClick(ClickEvent event) {
                loadListBox();
            }
        });

        
        District = new RadioButton("selectOfficers", "District");
        rootPanel.add(District, 252, 10);
        District.addClickHandler(new ClickHandler(){
            @Override
            public void onClick(ClickEvent event) {
                loadListBox();
            }
        });
        
        listBox = new ListBox();
        rootPanel.add(listBox, 10, 55);
        listBox.setSize("390px", "32px");
        listBox.setVisibleItemCount(1);

        listBox.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                waitIcon.setVisible(true);
                officerList.setDistrict(listBox.getSelectedIndex());
                officerList.setCmte(listBox.getValue(listBox.getSelectedIndex()));
                officerList.refresh();
                waitIcon.setVisible(false);
            }
        });
        listBox.setVisibleItemCount(1);

        officerList = new OfficerList();
        scrollPanel.setWidget(officerList);
        officerList.setSize("100%", "100%");

        // Set the selection, then do the refresh()
        District.setValue(true);
        
        loadListBox();
        officerList.refresh();
        waitIcon.setVisible(false);
        
    }
    
    private void loadListBox() {
        waitIcon.setVisible(true);

        if (Committee.getValue()) {
            // Load committee names           
            RequestBuilder rq = new RequestBuilder(RequestBuilder.GET,
                    "/export/gwt/rtm-standing-cmte-list");
            Request rs = null;

            rq.setCallback(new RequestCallback() {
                @Override
                public void onError(Request request, Throwable exception) {
                    Window.alert(exception.getMessage());
                }

                @Override
                public void onResponseReceived(Request request, Response response) {
                    JSONArray committees;
                    
                    listBox.clear();
                    if (Response.SC_OK == response.getStatusCode()) {
                        // handle OK response from the server
                        committees = JSONParser.parseStrict(response.getText())
                                .isArray();

                        for (int i = 0; i < committees.size(); i++) {
                            listBox.addItem(committees.get(i).isString().stringValue());
                        }
                        AppUtils.EVENT_BUS.fireEvent(new OfficerListRefreshEvent());
                        officerList.setSearch("c");
                        officerList.setCmte(listBox.getValue(0));
                        officerList.refresh();
                        waitIcon.setVisible(false);


                    } else {
                        Window.alert("Error: refreshCommitteeList() HTTP status = "
                                + response.getStatusCode());
                        // handle non-OK response from the server
                    }
                }

            });

            try {
                rs = rq.send();
            } catch (RequestException e) {
                // TODO Auto-generated catch block
                Window.alert(e.getMessage());
            }

        }
        
        if (District.getValue()) {
            // Load districts
            listBox.clear();
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
            officerList.setDistrict(0);
            officerList.setSearch("d");
            officerList.refresh();
            waitIcon.setVisible(false);

        }
    }
    
    public void iconOn() {
        waitIcon.setVisible(true);
    }

    public void iconOff() {
        waitIcon.setVisible(false);
    }
}
