package com.terrapin.rtm.data;

import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
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
import com.terrapin.rtm.data.MemberNamesMatchesSuggestOracle;
import com.terrapin.rtm.data.MemberNamesMatchesSuggestion;

public class DelegateList extends FlexTable {

	private int district = 0;
	private String memberUid = null;

	public DelegateList() {
		AppUtils.EVENT_BUS.addHandler(DelegateListRefreshEvent.TYPE,
				new DelegateListRefreshHandler() {
					public void onRefresh(DelegateListRefreshEvent dre) {
						refresh();
					}
				});
	}

//	private native void log(String msg)
//	/*-{
//		$wnd.console.log(msg)
//	}-*/;

	public void setDistrict(int d) {
		district = d;
	}

	public void refresh() {
		RequestBuilder rq = new RequestBuilder(RequestBuilder.GET,
				URL.encode("/export/gwt/standing-cmte/" + (district + 1)));
		Request rs = null;

		rq.setCallback(new RequestCallback() {

			@Override
			public void onError(Request request, Throwable exception) {
				Window.alert(exception.getMessage());
			}

			@Override
			public void onResponseReceived(Request request, Response response) {
				JSONArray delegates;

//				log("in onResponseReceived()");
				if (Response.SC_OK == response.getStatusCode()) {
					// handle OK response from the server
					delegates = JSONParser.parseStrict(response.getText())
							.isObject().get("nodes").isArray();
//					log("Array size = " + delegates.size());

					removeAllRows();

					for (int i = 0; i < delegates.size(); i++) {
						final DelegateUpdateButton btn = new DelegateUpdateButton();
						final SuggestBox name = new SuggestBox(
								new MemberNamesMatchesSuggestOracle());

						name.addSelectionHandler(new SelectionHandler<Suggestion>() {
							public void onSelection(
									SelectionEvent<Suggestion> event) {
								memberUid = ((MemberNamesMatchesSuggestion) event
										.getSelectedItem()).getUid();
								btn.setUid(memberUid);
								btn.setEnabled(true);
							}
						});
						
						name.addValueChangeHandler(new ValueChangeHandler<String>() {
							public void onValueChange(ValueChangeEvent<String> event) {
//								log("onValueChange() called.");
								btn.setUid("Anonymous");
								btn.setEnabled(true);
							}
						});

						btn.addClickHandler(new ClickHandler() {
							public void onClick(ClickEvent event) {
								updateCommitteeDelegate(btn);
							}
						});
						// log("object " + i + ": "
						// + delegates.get(i).isObject().get("node"));
						JSONObject d = (JSONObject) delegates.get(i).isObject()
								.get("node");
						btn.setNid(d.get("Nid").isString().stringValue());
						if (d.containsKey("field_cmte_member_uid")) {
							btn.setUid(d.get("field_cmte_member_uid")
									.isString().stringValue());
						}
						btn.setText("Update");
						btn.setEnabled(false);
						setWidget(i, 2, btn);
						setHTML(i, 0, d.get("field_position_value").isString()
								.stringValue()
								+ ", "
								+ d.get("field_cmte_value").isString()
										.stringValue());
						if (d.containsKey("field_cmte_member_uid")) {
							name.setValue(d.get("field_cmte_member_uid")
									.isString().stringValue());
						}
						setWidget(i, 1, name);
						
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
			// log("Request Sent");
		} catch (RequestException e) {
			Window.alert(e.getMessage());
		}

	}

	private void postJSONData(String uri, JSONObject json) {
		RequestBuilder rq = new RequestBuilder(RequestBuilder.POST,
				URL.encode(uri));
		Request rs = null;

		rq.setCallback(new RequestCallback() {
			@Override
			public void onError(Request request, Throwable exception) {
				Window.alert("postJSONData().onError(): " + exception.getMessage());
			}

			@Override
			public void onResponseReceived(Request request, Response response) {
				if (Response.SC_OK == response.getStatusCode()) {
					refresh();
				} else {
					Window.alert("onResponseReceived Error: [" + response.getStatusCode() +"] " + response.getStatusText());
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

	public void updateCommitteeDelegate(DelegateUpdateButton btn) {
		JSONObject obj = new JSONObject();
		obj.put("nid", new JSONString(btn.getNid()));
		obj.put("uid", new JSONString(btn.getUid()));
		postJSONData("/edit/committee/delegates/update", obj);

//		Window.alert("Updating nid="+btn.getNid()+"  uid="+btn.getUid());
//		refresh();
	}

}
