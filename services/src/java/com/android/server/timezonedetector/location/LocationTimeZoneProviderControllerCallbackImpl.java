package com.android.server.timezonedetector.location;

/* loaded from: classes2.dex */
class LocationTimeZoneProviderControllerCallbackImpl extends com.android.server.timezonedetector.location.LocationTimeZoneProviderController.Callback {
    LocationTimeZoneProviderControllerCallbackImpl(@android.annotation.NonNull com.android.server.timezonedetector.location.ThreadingDomain threadingDomain) {
        super(threadingDomain);
    }

    @Override // com.android.server.timezonedetector.location.LocationTimeZoneProviderController.Callback
    void sendEvent(@android.annotation.NonNull com.android.server.timezonedetector.LocationAlgorithmEvent locationAlgorithmEvent) {
        this.mThreadingDomain.assertCurrentThread();
        ((com.android.server.timezonedetector.TimeZoneDetectorInternal) com.android.server.LocalServices.getService(com.android.server.timezonedetector.TimeZoneDetectorInternal.class)).handleLocationAlgorithmEvent(locationAlgorithmEvent);
    }
}
