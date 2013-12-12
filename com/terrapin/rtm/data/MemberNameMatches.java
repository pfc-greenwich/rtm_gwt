package com.terrapin.rtm.data;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Callback;

import com.terrapin.rtm.data.json.*;

public class MemberNameMatches {
	private List<MemberNamesMatchesSuggestion> names = new ArrayList<MemberNamesMatchesSuggestion>();
	public Boolean requestInProcess = false;
	private Callback callback;

//	private native void log(String msg)
//	/*-{
//		$wnd.console.log(msg)
//	}-*/;

	public void matchingNames(final Callback rqCallback,
			final SuggestOracle.Request rqRequest, int limit) {

		// DataUtil.log("matchingNames(): '" + rqRequest.getQuery() +
		// "' requestInProcess = " + requestInProcess);

		if (requestInProcess) {
			return;
		}

//		log("In MemberNameMatches matching names request");
		requestInProcess = true;
		names = new ArrayList<MemberNamesMatchesSuggestion>();
		String url = "http://rtm.greenwich.org/export/gwt/autocomplete/rtm-member/"
				+ rqRequest.getQuery();

		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		jsonp.setTimeout(5 * 1000); // 5 seconds
		jsonp.requestObject(url, new AsyncCallback<MemberNamesMatchesArray>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("MemberNameMatches Failure:" + caught.getMessage());
				requestInProcess = false;
			}

			@Override
			public void onSuccess(MemberNamesMatchesArray result) {
//				log("in OnSuccess: " + result.toString());
				for (int i = 0; i < result.getMemberNames().length(); i++) {
					MemberNamesMatchesEntry mn = result.getMemberNames().get(i);
					names.add(new MemberNamesMatchesSuggestion(mn.getRealName(), mn
							.getUid()));
				}
				requestInProcess = false;
				rqCallback.onSuggestionsReady(rqRequest,
						new SuggestOracle.Response(names));
			}
		});

		/*
		 * RequestBuilder rq = new RequestBuilder(RequestBuilder.GET, url);
		 * Request rs = null;
		 * 
		 * rq.setCallback(new RequestCallback() {
		 * 
		 * @Override public void onError(Request request, Throwable exception) {
		 * Window.alert("Error: MemberNameMatches.onResponseReceived.onError(): " +
		 * exception.getMessage());
		 * 
		 * }
		 * 
		 * @Override public void onResponseReceived(Request request, Response
		 * response) { JSONArray members;
		 * 
		 * // log("response.getStatusCode(): " + // response.getStatusCode());
		 * 
		 * if (Response.SC_OK == response.getStatusCode()) { // handle OK
		 * response from the server // log("Handling OK response"); members =
		 * JSONParser.parseStrict(response.getText()) .isArray();
		 * 
		 * for (int i = 0; i < members.size(); i++) { names.add(new
		 * MemberNamesMatchesSuggestion(members.get(i)
		 * .isObject().get("realname").isString() .stringValue(),
		 * members.get(i).isObject() .get("uid").isString().stringValue())); }
		 * // log(members.size() + " members retrieved."); requestInProcess =
		 * false; rqCallback.onSuggestionsReady(rqRequest, new
		 * SuggestOracle.Response(names)); } else {
		 * Window.alert("Error: MemberNameMatches.onResponseReceived():" +
		 * response.getStatusText()); // handle non-OK response from the server
		 * } requestInProcess = false; }
		 * 
		 * }); try { rs = rq.send(); } catch (RequestException e) {
		 * Window.alert("Error: MemberNameMatches.rq.send(): " + e.getMessage()); }
		 */
	}
}
