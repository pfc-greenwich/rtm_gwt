package com.terrapin.rtm.data;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ListBox;

public class CommitteeList extends ListBox {

	public void refresh() {
		RequestBuilder rq = new RequestBuilder(RequestBuilder.GET,
				"/export/gwt/rtm-cmte-list");
		Request rs = null;

		rq.setCallback(new RequestCallback() {
			@Override
			public void onError(Request request, Throwable exception) {
				Window.alert(exception.getMessage());
			}

			@Override
			public void onResponseReceived(Request request, Response response) {
				JSONArray committees;
				
				clear();
				if (Response.SC_OK == response.getStatusCode()) {
					// handle OK response from the server
					committees = JSONParser.parseStrict(response.getText())
							.isArray();

					for (int i = 0; i < committees.size(); i++) {
						addItem(committees.get(i).isString().stringValue());
					}
					AppUtils.EVENT_BUS.fireEvent(new CommitteeMemberListRefreshEvent());
					setEnabled(true);
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


}
