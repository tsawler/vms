package com.verilion.beans;

public class StudentBeans {

	protected String first_name = "";
	protected String last_name = "";
	protected int speciality = 0;
	protected int subspeciality = 0;
	protected String email_address = "";
	protected int ct_province_state_id = 0;
	protected int cme = 0;
	protected int cmetype = 0;
	protected int customer_id = 0;
	protected int ct_access_level_id = 0;
	protected String password = "";
	protected int student_type = 0;
	protected String how_found = "";
	protected int participation = 0;
	protected int ct_country_id = 0;
	protected String other_subspeciality = "";
	protected String notify_grandrounds_yn = "";
	protected String notify_webbased_yn= "";
	protected int current_course_id = 0;
	protected String current_course_completed_yn = "n";

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public int getSpeciality() {
		return speciality;
	}

	public void setSpeciality(int speciality) {
		this.speciality = speciality;
	}

	public int getCt_province_state_id() {
		return ct_province_state_id;
	}

	public void setCt_province_state_id(int ct_province_state_id) {
		this.ct_province_state_id = ct_province_state_id;
	}

	public int getCme() {
		return cme;
	}

	public void setCme(int cme) {
		this.cme = cme;
	}

	public int getCmetype() {
		return cmetype;
	}

	public void setCmetype(int cmetype) {
		this.cmetype = cmetype;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public int getCt_access_level_id() {
		return ct_access_level_id;
	}

	public void setCt_access_level_id(int ct_access_level_id) {
		this.ct_access_level_id = ct_access_level_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getStudent_type() {
		return student_type;
	}

	public void setStudent_type(int student_type) {
		this.student_type = student_type;
	}

	public String getEmail_address() {
		return email_address;
	}

	public void setEmail_address(String email_address) {
		this.email_address = email_address;
	}

	public int getParticipation() {
		return participation;
	}

	public void setParticipation(int participation) {
		this.participation = participation;
	}

	public int getCt_country_id() {
		return ct_country_id;
	}

	public void setCt_country_id(int ct_country_id) {
		this.ct_country_id = ct_country_id;
	}

	public String getHow_found() {
		return how_found;
	}

	public void setHow_found(String how_found) {
		this.how_found = how_found;
	}

	public int getSubspeciality() {
		return subspeciality;
	}

	public void setSubspeciality(int subspeciality) {
		this.subspeciality = subspeciality;
	}

	public String getOther_subspeciality() {
		return other_subspeciality;
	}

	public void setOther_subspeciality(String other_subspeciality) {
		this.other_subspeciality = other_subspeciality;
	}

	public String getNotify_grandrounds_yn() {
		return notify_grandrounds_yn;
	}

	public void setNotify_grandrounds_yn(String notify_grandrounds_yn) {
		this.notify_grandrounds_yn = notify_grandrounds_yn;
	}

	public String getNotify_webbased_yn() {
		return notify_webbased_yn;
	}

	public void setNotify_webbased_yn(String notify_webbased_yn) {
		this.notify_webbased_yn = notify_webbased_yn;
	}

	public int getCurrent_course_id() {
		return current_course_id;
	}

	public void setCurrent_course_id(int current_course_id) {
		this.current_course_id = current_course_id;
	}

	public String getCurrent_course_completed_yn() {
		return current_course_completed_yn;
	}

	public void setCurrent_course_completed_yn(String current_course_completed_yn) {
		this.current_course_completed_yn = current_course_completed_yn;
	}
}