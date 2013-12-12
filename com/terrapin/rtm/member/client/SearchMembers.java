package com.terrapin.rtm.member.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.terrapin.rtm.data.MemberNames;
import com.terrapin.rtm.data.MemberNamesMatchesSuggestOracle;
import com.terrapin.rtm.data.MemberNamesMatchesSuggestion;
//import com.terrapin.rtm.data.Track;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.user.client.ui.ScrollPanel;

public class SearchMembers implements EntryPoint {

	private RootPanel rootPanel;
	private String addMemberUid;

	@Override
	public void onModuleLoad() {
	
		final RTMGraphics img = GWT.create(RTMGraphics.class);

		rootPanel = RootPanel.get("gwt-content");
		rootPanel.setSize("382px", "");
		if (rootPanel == null) {
			rootPanel = RootPanel.get();
		}
		rootPanel.getElement().getStyle().setPosition(Position.RELATIVE);
		
//		Track.setTrackingAccount("UA-20560683-1");

		final MemberNames memberList = new MemberNames();
		memberList.setStyleName("text");

		final ListBox listBox = new ListBox();

		final ScrollPanel memberData = new ScrollPanel(memberList);
		memberData.setStyleName("text");
		memberData.setSize("322px", "250px");
		rootPanel.add(memberData, 20, 250);
		
		
		final Image SmallMapImage = new Image();

		Image Header = new Image();
		Header.setResource(img.top_header_RTM_Search());
		Header.setSize("362", "22");
		rootPanel.add(Header, 10, 20);

		InlineHTML lblMainText = new InlineHTML(
				"<b>Search by Name or District</b><br/>Either type in name or select a district number.");
		lblMainText.setStyleName("text");
		rootPanel.add(lblMainText, 10, 48);
		lblMainText.setSize("360px", "45px");

		SmallMapImage.setSize("75", "125");
		SmallMapImage.setResource(img.rtmMap0_sm());
		rootPanel.add(SmallMapImage, 270, 110);
		

		final SuggestBox NameSuggest = new SuggestBox(new MemberNamesMatchesSuggestOracle());
		NameSuggest.addSelectionHandler(new SelectionHandler<Suggestion>() {

			public void onSelection(SelectionEvent<Suggestion> event) {
				addMemberUid = ((MemberNamesMatchesSuggestion) event.getSelectedItem())
						.getUid();
				memberList.getByUid(addMemberUid);
				SmallMapImage.setResource(img.rtmMap0_sm());
				NameSuggest.setValue("");
				listBox.setSelectedIndex(0);
//				Track.trackEvent("RTM Member Search", "Single Member Search");
			}
		});
		NameSuggest.setAutoSelectEnabled(false);
		NameSuggest.setStyleName("gwt-SuggestBoxPopup");
		rootPanel.add(NameSuggest, 20, 128);
		NameSuggest.setSize("200px", "25px");

		Label lblNameSuggest = new Label("Name:");
		lblNameSuggest
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		lblNameSuggest.setStyleName("text");
		rootPanel.add(lblNameSuggest, 10, 109);
		lblNameSuggest.setSize("190px", "23px");

		listBox.setDirectionEstimator(false);
		listBox.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				NameSuggest.setValue("");
				int idx = listBox.getSelectedIndex();
//				Track.trackEvent("RTM Member Search", "District Listing");
				switch (idx) {
				case 0:
					SmallMapImage.setResource(img.rtmMap0_sm());
					memberList.removeAllRows();
					break;
				case 1:
					SmallMapImage.setResource(img.rtmMap1_sm());
					memberList.getByDistrict("1");
					break;
				case 2:
					SmallMapImage.setResource(img.rtmMap2_sm());
					memberList.getByDistrict("2");
					break;
				case 3:
					SmallMapImage.setResource(img.rtmMap3_sm());
					memberList.getByDistrict("3");
					break;
				case 4:
					SmallMapImage.setResource(img.rtmMap4_sm());
					memberList.getByDistrict("4");
					break;
				case 5:
					SmallMapImage.setResource(img.rtmMap5_sm());
					memberList.getByDistrict("5");
					break;
				case 6:
					SmallMapImage.setResource(img.rtmMap6_sm());
					memberList.getByDistrict("6");
					break;
				case 7:
					SmallMapImage.setResource(img.rtmMap7_sm());
					memberList.getByDistrict("7");
					break;
				case 8:
					SmallMapImage.setResource(img.rtmMap8_sm());
					memberList.getByDistrict("8");
					break;
				case 9:
					SmallMapImage.setResource(img.rtmMap9_sm());
					memberList.getByDistrict("9");
					break;
				case 10:
					SmallMapImage.setResource(img.rtmMap10_sm());
					memberList.getByDistrict("10");
					break;
				case 11:
					SmallMapImage.setResource(img.rtmMap11_sm());
					memberList.getByDistrict("11");
					break;
				case 12:
					SmallMapImage.setResource(img.rtmMap12_sm());
					memberList.getByDistrict("12");
					break;
				}
			}
		});
		listBox.addItem("All Districts");
		listBox.addItem("1-South Center");
		listBox.addItem("2-Harbor");
		listBox.addItem("3-Chickahominy");
		listBox.addItem("4-Byram");
		listBox.addItem("5-Riverside");
		listBox.addItem("6-Old Greenwich");
		listBox.addItem("7-North Central");
		listBox.addItem("8-Cos Cob");
		listBox.addItem("9-Pemberwick-Glenville");
		listBox.addItem("10-Northwest");
		listBox.addItem("11-Northeast");
		listBox.addItem("12-Havemeyer");
		listBox.setSelectedIndex(0);
		rootPanel.add(listBox, 20, 195);
		listBox.setSize("200px", "25px");
		listBox.setVisibleItemCount(1);

		Label lblDistrict = new Label("District:");
		lblDistrict.setSize("190px", "23px");
		lblDistrict.setStyleName("text");
		rootPanel.add(lblDistrict, 20, 177);

	}
}
