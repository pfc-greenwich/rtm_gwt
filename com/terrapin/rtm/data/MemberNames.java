package com.terrapin.rtm.data;

import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.terrapin.rtm.data.json.MemberArray;
import com.terrapin.rtm.data.json.MemberEntry;

public class MemberNames extends FlexTable {

	private String getCity(String zip) {
		if (zip.equals("06830") || zip.equals("06831") || zip.equals("06836")) {
			return "Greenwich";
		} 
		
		if (zip.equals("06807")) {
			return "Cos Cob";
		} 
		
		if (zip.equals("06878")) {
			return "Riverside";
		} 
		
		if (zip.equals("06870")) {
			return "Old Greenwich";
		}
		return "";
	}
	private HTML formatMemberEntry(MemberEntry m) {
		String out = "<a href='http://rtm.greenwich.org/user/" + m.getUid() + "'>";
		out += "<span class='textlink'>";
		out += m.getFirstname() + " " + m.getLastname() + "</span></a><br/>";
		if (!m.getStreetAddress().equals("null")) {
			out += m.getStreetAddress() + "<br/>";
		}
		if (!m.getZipcode().equals("null")) {
			out += getCity(m.getZipcode()) + ", CT " + m.getZipcode() + "<br/>";
		}
		return new HTML(out);
	}
	
	public void getByUid(String Uid) {
		String url = "http://rtm.greenwich.org/export/gwt/rtm-member/uid/"
				+ Uid;

		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		jsonp.setTimeout(2 * 1000); // 2 seconds
		jsonp.requestObject(url, new AsyncCallback<MemberArray>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("MemberNames Failure:" + caught.getMessage());
			}

			@Override
			public void onSuccess(MemberArray result) {
				removeAllRows();
				for (int i = 0; i < result.getMembers().length(); i++) {
					MemberEntry mn = result.getMembers().get(i);
					setWidget(i, 0, formatMemberEntry(mn));
				}
			}

		});
	}
	
	public void getByDistrict(String District) {
		String url = "http://rtm.greenwich.org/export/gwt/rtm-member/district/"
				+ District;

		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		jsonp.setTimeout(2 * 1000); // 2 seconds
		jsonp.requestObject(url, new AsyncCallback<MemberArray>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("MemberNames Failure:" + caught.getMessage());
			}

			@Override
			public void onSuccess(MemberArray result) {
				removeAllRows();
				for (int i = 0; i < result.getMembers().length(); i++) {
					MemberEntry mn = result.getMembers().get(i);
					setWidget(i, 0, formatMemberEntry(mn));
				}
			}

		});
	}
}
