package com.android.server.timezonedetector;

/* loaded from: classes2.dex */
final class TimeZoneCanonicalizer implements java.util.function.Function<java.lang.String, java.lang.String> {
    TimeZoneCanonicalizer() {
    }

    @Override // java.util.function.Function
    public java.lang.String apply(java.lang.String str) {
        java.lang.String findCanonicalTimeZoneId = com.android.i18n.timezone.TimeZoneFinder.getInstance().getCountryZonesFinder().findCanonicalTimeZoneId(str);
        return findCanonicalTimeZoneId == null ? str : findCanonicalTimeZoneId;
    }
}
