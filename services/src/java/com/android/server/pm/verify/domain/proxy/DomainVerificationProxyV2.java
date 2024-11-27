package com.android.server.pm.verify.domain.proxy;

/* loaded from: classes2.dex */
public class DomainVerificationProxyV2 implements com.android.server.pm.verify.domain.proxy.DomainVerificationProxy {
    private static final boolean DEBUG_BROADCASTS = false;
    private static final java.lang.String TAG = "DomainVerificationProxyV2";

    @android.annotation.NonNull
    private final com.android.server.pm.verify.domain.proxy.DomainVerificationProxyV2.Connection mConnection;

    @android.annotation.NonNull
    private final android.content.Context mContext;

    @android.annotation.NonNull
    private final android.content.ComponentName mVerifierComponent;

    public interface Connection extends com.android.server.pm.verify.domain.proxy.DomainVerificationProxy.BaseConnection {
    }

    public DomainVerificationProxyV2(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.pm.verify.domain.proxy.DomainVerificationProxyV2.Connection connection, @android.annotation.NonNull android.content.ComponentName componentName) {
        this.mContext = context;
        this.mConnection = connection;
        this.mVerifierComponent = componentName;
    }

    @Override // com.android.server.pm.verify.domain.proxy.DomainVerificationProxy
    public void sendBroadcastForPackages(@android.annotation.NonNull java.util.Set<java.lang.String> set) {
        this.mConnection.schedule(1, set);
    }

    @Override // com.android.server.pm.verify.domain.proxy.DomainVerificationProxy
    public boolean runMessage(int i, java.lang.Object obj) {
        switch (i) {
            case 1:
                android.os.Parcelable domainVerificationRequest = new android.content.pm.verify.domain.DomainVerificationRequest((java.util.Set) obj);
                long powerSaveTempWhitelistAppDuration = this.mConnection.getPowerSaveTempWhitelistAppDuration();
                android.app.BroadcastOptions makeBasic = android.app.BroadcastOptions.makeBasic();
                makeBasic.setTemporaryAppAllowlist(powerSaveTempWhitelistAppDuration, 0, 308, "");
                this.mConnection.getDeviceIdleInternal().addPowerSaveTempWhitelistApp(android.os.Process.myUid(), this.mVerifierComponent.getPackageName(), powerSaveTempWhitelistAppDuration, 0, true, 308, "domain verification agent");
                this.mContext.sendBroadcastAsUser(new android.content.Intent("android.intent.action.DOMAINS_NEED_VERIFICATION").setComponent(this.mVerifierComponent).putExtra("android.content.pm.verify.domain.extra.VERIFICATION_REQUEST", domainVerificationRequest).addFlags(268435456), android.os.UserHandle.SYSTEM, null, makeBasic.toBundle());
                return true;
            default:
                return false;
        }
    }

    @Override // com.android.server.pm.verify.domain.proxy.DomainVerificationProxy
    public boolean isCallerVerifier(int i) {
        return this.mConnection.isCallerPackage(i, this.mVerifierComponent.getPackageName());
    }

    @Override // com.android.server.pm.verify.domain.proxy.DomainVerificationProxy
    @android.annotation.Nullable
    public android.content.ComponentName getComponentName() {
        return this.mVerifierComponent;
    }
}
