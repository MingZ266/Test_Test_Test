package com.tai.TestTestTest;

class CityBasicInfo {
    private String CityName;
    private String Country;
    private String AdminArea;
    private String ParentCity;
    private String Timezone;

    CityBasicInfo(String CityName, String Country, String AdminArea, String ParentCity, String Timezone) {
        this.CityName   = CityName;
        this.Country    = Country;
        this.AdminArea  = AdminArea;
        this.ParentCity = ParentCity;
        this.Timezone   = Timezone;
    }

    String getDisplayStr() {
        return "地区名：" + CityName +
                "  归属地：" + Country + " - " + AdminArea + " - " + ParentCity +
                "  所在时区：GMT" + Timezone + "\n";
    }
}
