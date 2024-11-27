package com.android.server.timezonedetector.location;

/* loaded from: classes2.dex */
abstract class LocationTimeZoneProviderProxy implements com.android.server.timezonedetector.Dumpable {

    @android.annotation.NonNull
    protected final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mSharedLock"})
    @android.annotation.Nullable
    protected com.android.server.timezonedetector.location.LocationTimeZoneProviderProxy.Listener mListener;

    @android.annotation.NonNull
    protected final java.lang.Object mSharedLock;

    @android.annotation.NonNull
    protected final com.android.server.timezonedetector.location.ThreadingDomain mThreadingDomain;

    interface Listener {
        void onProviderBound();

        void onProviderUnbound();

        void onReportTimeZoneProviderEvent(@android.annotation.NonNull android.service.timezone.TimeZoneProviderEvent timeZoneProviderEvent);
    }

    @com.android.internal.annotations.GuardedBy({"mSharedLock"})
    abstract void onDestroy();

    @com.android.internal.annotations.GuardedBy({"mSharedLock"})
    abstract void onInitialize();

    abstract void setRequest(@android.annotation.NonNull com.android.server.timezonedetector.location.TimeZoneProviderRequest timeZoneProviderRequest);

    LocationTimeZoneProviderProxy(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.timezonedetector.location.ThreadingDomain threadingDomain) {
        java.util.Objects.requireNonNull(context);
        this.mContext = context;
        java.util.Objects.requireNonNull(threadingDomain);
        this.mThreadingDomain = threadingDomain;
        this.mSharedLock = threadingDomain.getLockObject();
    }

    void initialize(@android.annotation.NonNull com.android.server.timezonedetector.location.LocationTimeZoneProviderProxy.Listener listener) {
        java.util.Objects.requireNonNull(listener);
        synchronized (this.mSharedLock) {
            try {
                if (this.mListener != null) {
                    throw new java.lang.IllegalStateException("listener already set");
                }
                this.mListener = listener;
                onInitialize();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void destroy() {
        synchronized (this.mSharedLock) {
            onDestroy();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleTimeZoneProviderEvent$0(android.service.timezone.TimeZoneProviderEvent timeZoneProviderEvent) {
        this.mListener.onReportTimeZoneProviderEvent(timeZoneProviderEvent);
    }

    final void handleTimeZoneProviderEvent(@android.annotation.NonNull final android.service.timezone.TimeZoneProviderEvent timeZoneProviderEvent) {
        this.mThreadingDomain.post(new java.lang.Runnable() { // from class: com.android.server.timezonedetector.location.LocationTimeZoneProviderProxy$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.timezonedetector.location.LocationTimeZoneProviderProxy.this.lambda$handleTimeZoneProviderEvent$0(timeZoneProviderEvent);
            }
        });
    }
}
