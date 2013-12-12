package com.terrapin.rtm.data;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;

public class CommitteeMemberList extends FlexTable {

	private CommitteeList committeeList;

	public CommitteeMemberList(CommitteeList cl) {
		committeeList = cl;
		AppUtils.EVENT_BUS.addHandler(CommitteeMemberListRefreshEvent.TYPE,
				new CommitteeMemberListRefreshHandler() {
					public void onRefresh(CommitteeMemberListRefreshEvent dre) {
						refresh();
					}
				});

	}

	public void refresh() {
		RequestBuilder rq = new RequestBuilder(RequestBuilder.GET,
				URL.encode("/export/gwt/rtm-cmte-members/"
						+ committeeList.getValue(committeeList.getSelectedIndex())));
		Request rs = null;

		rq.setCallback(new RequestCallback() {
			@Override
			public void onError(Request request, Throwable exception) {
				Window.alert(exception.getMessage());
			}

			@Override
			public void onResponseReceived(Request request, Response response) {
				JSONArray members;

				if (Response.SC_OK == response.getStatusCode()) {
					// handle OK response from the server
					members = JSONParser.parseStrict(response.getText())
							.isArray();
					removeAllRows();

					for (int i = 0; i < members.size(); i++) {
						final CommitteeMemberDeleteButton btn = new CommitteeMemberDeleteButton();
						btn.addClickHandler(new ClickHandler() {
							public void onClick(ClickEvent event) {
								deleteCommitteeMember(btn);
							}
						});

						btn.setNid(members.get(i).isArray().get(0).isString()
								.stringValue());
						btn.setUid(members.get(i).isArray().get(2).isString()
								.stringValue());
						btn.setText("Delete");
						setWidget(i, 0, btn);
						setText(i, 1, members.get(i).isArray().get(3)
								.isString().stringValue());
					}
				} else {
					Window.alert("Error: refresh() HTTP status = "
							+ response.getStatusText());
					// handle non-OK response from the server
				}
			}

		});
		try {
			rs = rq.send();
		} catch (RequestException e) {
			Window.alert(e.getMessage());
		}
	}

	public void addCommitteeMember(String uid, String committee) {
		JSONObject obj = new JSONObject();
		obj.put("uid", new JSONString(uid));
		obj.put("committee", new JSONString(committee));
		postJSONData("/edit/committee/members/add", obj);
		// Window.alert("Adding " + uid + " to " + committee);
	}

	public void deleteCommitteeMember(CommitteeMemberDeleteButton btn) {
		JSONObject obj = new JSONObject();
		obj.put("nid", new JSONString(btn.getNid()));
		obj.put("uid", new JSONString(btn.getUid()));
		postJSONData("/edit/committee/members/delete", obj);

		// Window.alert("Removing nid="+btn.getNid()+"  uid="+btn.getUid());

	}

	private void postJSONData(String uri, JSONObject json) {
		RequestBuilder rq = new RequestBuilder(RequestBuilder.POST,
				URL.encode(uri));
		Request rs = null;

		rq.setCallback(new RequestCallback() {
			@Override
			public void onError(Request request, Throwable exception) {
				Window.alert(exception.getMessage());
			}

			@Override
			public void onResponseReceived(Request request, Response response) {
				if (Response.SC_OK == response.getStatusCode()) {
					committeeList.refresh();
				} else {
					Window.alert("Error: " + response.getStatusText());
				}

			}

		});

		rq.setRequestData("json=" + URL.encode(json.toString()));
		rq.setHeader("Content-Type", "application/x-www-form-urlencoded");
		try {
			rs = rq.send();
		} catch (RequestException e) {
			Window.alert("Sending Request postJSONData(): " + e.getMessage());
		}
	}

}
