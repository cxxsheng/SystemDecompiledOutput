package com.android.server.timezonedetector.location;

/* loaded from: classes2.dex */
public class ZoneInfoDbTimeZoneProviderEventPreProcessor implements com.android.server.timezonedetector.location.TimeZoneProviderEventPreProcessor {
    @Override // com.android.server.timezonedetector.location.TimeZoneProviderEventPreProcessor
    public android.service.timezone.TimeZoneProviderEvent preProcess(@android.annotation.NonNull android.service.timezone.TimeZoneProviderEvent timeZoneProviderEvent) {
        if (timeZoneProviderEvent.getSuggestion() == null || timeZoneProviderEvent.getSuggestion().getTimeZoneIds().isEmpty()) {
            return timeZoneProviderEvent;
        }
        if (hasInvalidZones(timeZoneProviderEvent)) {
            return android.service.timezone.TimeZoneProviderEvent.createUncertainEvent(timeZoneProviderEvent.getCreationElapsedMillis(), new android.service.timezone.TimeZoneProviderStatus.Builder(timeZoneProviderEvent.getTimeZoneProviderStatus()).setTimeZoneResolutionOperationStatus(3).build());
        }
        return timeZoneProviderEvent;
    }

    private static boolean hasInvalidZones(android.service.timezone.TimeZoneProviderEvent timeZoneProviderEvent) {
        for (java.lang.String str : timeZoneProviderEvent.getSuggestion().getTimeZoneIds()) {
            if (!com.android.i18n.timezone.ZoneInfoDb.getInstance().hasTimeZone(str)) {
                com.android.server.timezonedetector.location.LocationTimeZoneManagerService.infoLog("event=" + timeZoneProviderEvent + " has unsupported zone(" + str + ")");
                return true;
            }
        }
        return false;
    }
}
