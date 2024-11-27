package com.android.server.timezonedetector.location;

/* loaded from: classes2.dex */
class BinderLocationTimeZoneProvider extends com.android.server.timezonedetector.location.LocationTimeZoneProvider {

    @android.annotation.NonNull
    private final com.android.server.timezonedetector.location.LocationTimeZoneProviderProxy mProxy;

    BinderLocationTimeZoneProvider(@android.annotation.NonNull com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderMetricsLogger providerMetricsLogger, @android.annotation.NonNull com.android.server.timezonedetector.location.ThreadingDomain threadingDomain, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.timezonedetector.location.LocationTimeZoneProviderProxy locationTimeZoneProviderProxy, boolean z) {
        super(providerMetricsLogger, threadingDomain, str, new com.android.server.timezonedetector.location.ZoneInfoDbTimeZoneProviderEventPreProcessor(), z);
        java.util.Objects.requireNonNull(locationTimeZoneProviderProxy);
        this.mProxy = locationTimeZoneProviderProxy;
    }

    @Override // com.android.server.timezonedetector.location.LocationTimeZoneProvider
    boolean onInitialize() {
        this.mProxy.initialize(new com.android.server.timezonedetector.location.LocationTimeZoneProviderProxy.Listener() { // from class: com.android.server.timezonedetector.location.BinderLocationTimeZoneProvider.1
            @Override // com.android.server.timezonedetector.location.LocationTimeZoneProviderProxy.Listener
            public void onReportTimeZoneProviderEvent(@android.annotation.NonNull android.service.timezone.TimeZoneProviderEvent timeZoneProviderEvent) {
                com.android.server.timezonedetector.location.BinderLocationTimeZoneProvider.this.handleTimeZoneProviderEvent(timeZoneProviderEvent);
            }

            @Override // com.android.server.timezonedetector.location.LocationTimeZoneProviderProxy.Listener
            public void onProviderBound() {
                com.android.server.timezonedetector.location.BinderLocationTimeZoneProvider.this.handleOnProviderBound();
            }

            @Override // com.android.server.timezonedetector.location.LocationTimeZoneProviderProxy.Listener
            public void onProviderUnbound() {
                com.android.server.timezonedetector.location.BinderLocationTimeZoneProvider.this.handleTemporaryFailure("onProviderUnbound()");
            }
        });
        return true;
    }

    @Override // com.android.server.timezonedetector.location.LocationTimeZoneProvider
    void onDestroy() {
        this.mProxy.destroy();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnProviderBound() {
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            try {
                com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState providerState = this.mCurrentState.get();
                switch (providerState.stateEnum) {
                    case 1:
                    case 2:
                    case 3:
                        com.android.server.timezonedetector.location.LocationTimeZoneManagerService.debugLog("handleOnProviderBound mProviderName=" + this.mProviderName + ", currentState=" + providerState + ": Provider is started.");
                        break;
                    case 4:
                        com.android.server.timezonedetector.location.LocationTimeZoneManagerService.debugLog("handleOnProviderBound mProviderName=" + this.mProviderName + ", currentState=" + providerState + ": Provider is stopped.");
                        break;
                    case 5:
                    case 6:
                        com.android.server.timezonedetector.location.LocationTimeZoneManagerService.debugLog("handleOnProviderBound, mProviderName=" + this.mProviderName + ", currentState=" + providerState + ": No state change required, provider is terminated.");
                        break;
                    default:
                        throw new java.lang.IllegalStateException("Unknown currentState=" + providerState);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.timezonedetector.location.LocationTimeZoneProvider
    void onStartUpdates(@android.annotation.NonNull java.time.Duration duration, @android.annotation.NonNull java.time.Duration duration2) {
        this.mProxy.setRequest(com.android.server.timezonedetector.location.TimeZoneProviderRequest.createStartUpdatesRequest(duration, duration2));
    }

    @Override // com.android.server.timezonedetector.location.LocationTimeZoneProvider
    void onStopUpdates() {
        this.mProxy.setRequest(com.android.server.timezonedetector.location.TimeZoneProviderRequest.createStopUpdatesRequest());
    }

    @Override // com.android.server.timezonedetector.Dumpable
    public void dump(@android.annotation.NonNull android.util.IndentingPrintWriter indentingPrintWriter, @android.annotation.Nullable java.lang.String[] strArr) {
        synchronized (this.mSharedLock) {
            indentingPrintWriter.println("{BinderLocationTimeZoneProvider}");
            indentingPrintWriter.println("mProviderName=" + this.mProviderName);
            indentingPrintWriter.println("mCurrentState=" + this.mCurrentState);
            indentingPrintWriter.println("mProxy=" + this.mProxy);
            indentingPrintWriter.println("State history:");
            indentingPrintWriter.increaseIndent();
            this.mCurrentState.dump(indentingPrintWriter);
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("Proxy details:");
            indentingPrintWriter.increaseIndent();
            this.mProxy.dump(indentingPrintWriter, strArr);
            indentingPrintWriter.decreaseIndent();
        }
    }

    public java.lang.String toString() {
        java.lang.String str;
        synchronized (this.mSharedLock) {
            str = "BinderLocationTimeZoneProvider{mProviderName=" + this.mProviderName + ", mCurrentState=" + this.mCurrentState + ", mProxy=" + this.mProxy + '}';
        }
        return str;
    }
}
