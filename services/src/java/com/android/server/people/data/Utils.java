package com.android.server.people.data;

/* loaded from: classes2.dex */
class Utils {
    static java.lang.String getCurrentCountryIso(android.content.Context context) {
        java.lang.String str;
        android.location.Country detectCountry;
        android.location.CountryDetector countryDetector = (android.location.CountryDetector) context.getSystemService("country_detector");
        if (countryDetector != null && (detectCountry = countryDetector.detectCountry()) != null) {
            str = detectCountry.getCountryIso();
        } else {
            str = null;
        }
        if (str == null) {
            return java.util.Locale.getDefault().getCountry();
        }
        return str;
    }

    private Utils() {
    }
}
