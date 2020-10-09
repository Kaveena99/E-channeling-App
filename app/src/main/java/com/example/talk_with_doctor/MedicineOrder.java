package com.example.talk_with_doctor;

public class MedicineOrder {
    private String PharmacyName;
    private String CustomerMobile;
    private String mImageUrl;

    public MedicineOrder() {

    }

    public MedicineOrder(String pharmacyName, String customerMobile, String mImageUrl) {
        PharmacyName = pharmacyName;
        CustomerMobile = customerMobile;
        this.mImageUrl = mImageUrl;
    }

    public String getPharmacyName() {
        return PharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        PharmacyName = pharmacyName;
    }

    public String getCustomerMobile() {
        return CustomerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        CustomerMobile = customerMobile;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }


}
