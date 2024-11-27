package com.android.server.pm.verify.domain.proxy;

/* loaded from: classes2.dex */
public class DomainVerificationProxyUnavailable implements com.android.server.pm.verify.domain.proxy.DomainVerificationProxy {
    @Override // com.android.server.pm.verify.domain.proxy.DomainVerificationProxy
    public void sendBroadcastForPackages(@android.annotation.NonNull java.util.Set<java.lang.String> set) {
    }

    @Override // com.android.server.pm.verify.domain.proxy.DomainVerificationProxy
    public boolean runMessage(int i, java.lang.Object obj) {
        return false;
    }

    @Override // com.android.server.pm.verify.domain.proxy.DomainVerificationProxy
    public boolean isCallerVerifier(int i) {
        return false;
    }

    @Override // com.android.server.pm.verify.domain.proxy.DomainVerificationProxy
    @android.annotation.Nullable
    public android.content.ComponentName getComponentName() {
        return null;
    }
}
