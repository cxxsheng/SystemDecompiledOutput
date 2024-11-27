package com.android.server.pm.verify.domain.proxy;

/* loaded from: classes2.dex */
class DomainVerificationProxyCombined implements com.android.server.pm.verify.domain.proxy.DomainVerificationProxy {

    @android.annotation.NonNull
    private final com.android.server.pm.verify.domain.proxy.DomainVerificationProxy mProxyV1;

    @android.annotation.NonNull
    private final com.android.server.pm.verify.domain.proxy.DomainVerificationProxy mProxyV2;

    DomainVerificationProxyCombined(@android.annotation.NonNull com.android.server.pm.verify.domain.proxy.DomainVerificationProxy domainVerificationProxy, @android.annotation.NonNull com.android.server.pm.verify.domain.proxy.DomainVerificationProxy domainVerificationProxy2) {
        this.mProxyV1 = domainVerificationProxy;
        this.mProxyV2 = domainVerificationProxy2;
    }

    @Override // com.android.server.pm.verify.domain.proxy.DomainVerificationProxy
    public void sendBroadcastForPackages(@android.annotation.NonNull java.util.Set<java.lang.String> set) {
        this.mProxyV2.sendBroadcastForPackages(set);
        this.mProxyV1.sendBroadcastForPackages(set);
    }

    @Override // com.android.server.pm.verify.domain.proxy.DomainVerificationProxy
    public boolean runMessage(int i, java.lang.Object obj) {
        return this.mProxyV2.runMessage(i, obj) || this.mProxyV1.runMessage(i, obj);
    }

    @Override // com.android.server.pm.verify.domain.proxy.DomainVerificationProxy
    public boolean isCallerVerifier(int i) {
        return this.mProxyV2.isCallerVerifier(i) || this.mProxyV1.isCallerVerifier(i);
    }

    @Override // com.android.server.pm.verify.domain.proxy.DomainVerificationProxy
    @android.annotation.NonNull
    public android.content.ComponentName getComponentName() {
        return this.mProxyV2.getComponentName();
    }
}
