package com.android.server.pm.verify.domain.proxy;

/* loaded from: classes2.dex */
public interface DomainVerificationProxy {
    public static final boolean DEBUG_PROXIES = false;
    public static final java.lang.String TAG = "DomainVerificationProxy";

    public interface BaseConnection {
        com.android.server.DeviceIdleInternal getDeviceIdleInternal();

        long getPowerSaveTempWhitelistAppDuration();

        boolean isCallerPackage(int i, @android.annotation.NonNull java.lang.String str);

        void schedule(int i, @android.annotation.Nullable java.lang.Object obj);
    }

    @android.annotation.Nullable
    android.content.ComponentName getComponentName();

    boolean isCallerVerifier(int i);

    boolean runMessage(int i, java.lang.Object obj);

    void sendBroadcastForPackages(@android.annotation.NonNull java.util.Set<java.lang.String> set);

    static <ConnectionType extends com.android.server.pm.verify.domain.proxy.DomainVerificationProxyV1.Connection & com.android.server.pm.verify.domain.proxy.DomainVerificationProxyV2.Connection> com.android.server.pm.verify.domain.proxy.DomainVerificationProxy makeProxy(@android.annotation.Nullable android.content.ComponentName componentName, @android.annotation.Nullable android.content.ComponentName componentName2, @android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.pm.verify.domain.DomainVerificationManagerInternal domainVerificationManagerInternal, @android.annotation.NonNull com.android.server.pm.verify.domain.DomainVerificationCollector domainVerificationCollector, @android.annotation.NonNull ConnectionType connectiontype) {
        android.content.ComponentName componentName3;
        com.android.server.pm.verify.domain.proxy.DomainVerificationProxyV1 domainVerificationProxyV1;
        com.android.server.pm.verify.domain.proxy.DomainVerificationProxyV2 domainVerificationProxyV2 = null;
        if (componentName2 != null && componentName != null && !java.util.Objects.equals(componentName2.getPackageName(), componentName.getPackageName())) {
            componentName3 = null;
        } else {
            componentName3 = componentName;
        }
        if (componentName3 == null) {
            domainVerificationProxyV1 = null;
        } else {
            domainVerificationProxyV1 = new com.android.server.pm.verify.domain.proxy.DomainVerificationProxyV1(context, domainVerificationManagerInternal, domainVerificationCollector, connectiontype, componentName3);
        }
        if (componentName2 != null) {
            domainVerificationProxyV2 = new com.android.server.pm.verify.domain.proxy.DomainVerificationProxyV2(context, connectiontype, componentName2);
        }
        if (domainVerificationProxyV1 != null && domainVerificationProxyV2 != null) {
            return new com.android.server.pm.verify.domain.proxy.DomainVerificationProxyCombined(domainVerificationProxyV1, domainVerificationProxyV2);
        }
        if (domainVerificationProxyV1 != null) {
            return domainVerificationProxyV1;
        }
        if (domainVerificationProxyV2 != null) {
            return domainVerificationProxyV2;
        }
        return new com.android.server.pm.verify.domain.proxy.DomainVerificationProxyUnavailable();
    }
}
