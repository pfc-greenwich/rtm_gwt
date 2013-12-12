package com.terrapin.rtm.committee.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.terrapin.rtm.data.CommitteeList;
import com.terrapin.rtm.data.CommitteeMemberList;
import com.terrapin.rtm.data.MemberNamesMatchesSuggestOracle;
import com.terrapin.rtm.data.MemberNamesMatchesSuggestion;

public class EditCommitteeMembers implements EntryPoint {
	private CommitteeMemberList memberList;
	private CommitteeList committeeList;
	private RootPanel rootPanel;
	private SuggestBox addMemberSuggestBox;
	private Button addMemberButton;
	private String addMemberUid = null;

//	private native void log(String msg)
//	/*-{
//	 $wnd.console.log(msg)
//	}-*/;

	@Override
	public void onModuleLoad() {
		rootPanel = RootPanel.get("gwt-content");
		if (rootPanel == null) {
			rootPanel = RootPanel.get();
		}
		rootPanel.getElement().getStyle().setPosition(Position.RELATIVE);

		addMemberSuggestBox = new SuggestBox(new MemberNamesMatchesSuggestOracle());

		addMemberSuggestBox
				.addSelectionHandler(new SelectionHandler<Suggestion>() {
					public void onSelection(SelectionEvent<Suggestion> event) {
						addMemberUid = ((MemberNamesMatchesSuggestion) event
								.getSelectedItem()).getUid();
					}
				});
		addMemberSuggestBox.setAutoSelectEnabled(false);
		addMemberSuggestBox.setStyleName("gwt-SuggestBoxPopup");
		rootPanel.add(addMemberSuggestBox, 10, 62);
		addMemberSuggestBox.setSize("220px", "14px");

		committeeList = new CommitteeList();
		memberList = new CommitteeMemberList(committeeList);
		memberList.setBorderWidth(0);
		rootPanel.add(memberList, 10, 88);

		committeeList.refresh();

		committeeList.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				memberList.refresh();
			}
		});
		rootPanel.add(committeeList, 10, 30);

		addMemberButton = new Button("Add Member Entry");
		addMemberButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				addMemberSuggestBox.setText("");
				memberList.addCommitteeMember(addMemberUid, committeeList
						.getValue(committeeList.getSelectedIndex()));
			}
		});
		rootPanel.add(addMemberButton, 243, 62);

		memberList.refresh();
	}

}
