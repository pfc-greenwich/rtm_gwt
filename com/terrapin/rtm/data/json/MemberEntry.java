package com.terrapin.rtm.data.json;

import com.google.gwt.core.client.JavaScriptObject;

public class MemberEntry extends JavaScriptObject {

	protected MemberEntry() {
	}

	public final native String getUid() /*-{
		return this.uid;
	}-*/;

	public final native String getDistrict() /*-{
		return this.district;
	}-*/;

	public final native String getLastname() /*-{
		return this.lastname;
	}-*/;

	public final native String getFirstname() /*-{
		return this.firstname;
	}-*/;

	public final native String getStreetAddress() /*-{
		return this.street_address;
	}-*/;

	public final native String getZipcode() /*-{
		return this.zipcode;
	}-*/;

	public final native String getEMail() /*-{
		return this.email;
	}-*/;

	public final native String getHomeTelephone() /*-{
		return this.home_telephone;
	}-*/;

	public final native String getOfficeTelephone() /*-{
		return this.office_telephone;
	}-*/;

	public final native String getMobileTelephone() /*-{
		return this.mobile_telephone;
	}-*/;

	public final native String getFax() /*-{
		return this.fax;
	}-*/;

	public final native String getOccupation() /*-{
		return this.occupation;
	}-*/;

}
