package com.android.server.timezonedetector.location;

/* loaded from: classes2.dex */
class DisabledLocationTimeZoneProvider extends com.android.server.timezonedetector.location.LocationTimeZoneProvider {
    DisabledLocationTimeZoneProvider(@android.annotation.NonNull com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderMetricsLogger providerMetricsLogger, @android.annotation.NonNull com.android.server.timezonedetector.location.ThreadingDomain threadingDomain, @android.annotation.NonNull java.lang.String str, boolean z) {
        super(providerMetricsLogger, threadingDomain, str, new com.android.server.timezonedetector.location.TimeZoneProviderEventPreProcessor() { // from class: com.android.server.timezonedetector.location.DisabledLocationTimeZoneProvider$$ExternalSyntheticLambda0
            @Override // com.android.server.timezonedetector.location.TimeZoneProviderEventPreProcessor
            public final android.service.timezone.TimeZoneProviderEvent preProcess(android.service.timezone.TimeZoneProviderEvent timeZoneProviderEvent) {
                android.service.timezone.TimeZoneProviderEvent lambda$new$0;
                lambda$new$0 = com.android.server.timezonedetector.location.DisabledLocationTimeZoneProvider.lambda$new$0(timeZoneProviderEvent);
                return lambda$new$0;
            }
        }, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.service.timezone.TimeZoneProviderEvent lambda$new$0(android.service.timezone.TimeZoneProviderEvent timeZoneProviderEvent) {
        return timeZoneProviderEvent;
    }

    @Override // com.android.server.timezonedetector.location.LocationTimeZoneProvider
    boolean onInitialize() {
        return false;
    }

    @Override // com.android.server.timezonedetector.location.LocationTimeZoneProvider
    void onDestroy() {
    }

    @Override // com.android.server.timezonedetector.location.LocationTimeZoneProvider
    void onStartUpdates(@android.annotation.NonNull java.time.Duration duration, @android.annotation.NonNull java.time.Duration duration2) {
        throw new java.lang.UnsupportedOperationException("Provider is disabled");
    }

    @Override // com.android.server.timezonedetector.location.LocationTimeZoneProvider
    void onStopUpdates() {
        throw new java.lang.UnsupportedOperationException("Provider is disabled");
    }

    @Override // com.android.server.timezonedetector.Dumpable
    public void dump(@android.annotation.NonNull android.util.IndentingPrintWriter indentingPrintWriter, @android.annotation.Nullable java.lang.String[] strArr) {
        synchronized (this.mSharedLock) {
            indentingPrintWriter.println("{DisabledLocationTimeZoneProvider}");
            indentingPrintWriter.println("mProviderName=" + this.mProviderName);
            indentingPrintWriter.println("mCurrentState=" + this.mCurrentState);
        }
    }

    public java.lang.String toString() {
        java.lang.String str;
        synchronized (this.mSharedLock) {
            str = "DisabledLocationTimeZoneProvider{mProviderName=" + this.mProviderName + ", mCurrentState=" + this.mCurrentState + '}';
        }
        return str;
    }
}
