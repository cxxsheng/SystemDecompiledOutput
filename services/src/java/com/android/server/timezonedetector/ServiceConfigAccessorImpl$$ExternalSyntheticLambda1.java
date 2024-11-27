package com.android.server.timezonedetector;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes2.dex */
public final /* synthetic */ class ServiceConfigAccessorImpl$$ExternalSyntheticLambda1 implements java.lang.Runnable {
    public final /* synthetic */ com.android.server.timezonedetector.ServiceConfigAccessorImpl f$0;

    public /* synthetic */ ServiceConfigAccessorImpl$$ExternalSyntheticLambda1(com.android.server.timezonedetector.ServiceConfigAccessorImpl serviceConfigAccessorImpl) {
        this.f$0 = serviceConfigAccessorImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f$0.handleConfigurationInternalChangeOnMainThread();
    }
}
