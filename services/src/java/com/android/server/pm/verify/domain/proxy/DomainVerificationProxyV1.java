package com.android.server.pm.verify.domain.proxy;

/* loaded from: classes2.dex */
public class DomainVerificationProxyV1 implements com.android.server.pm.verify.domain.proxy.DomainVerificationProxy {
    private static final boolean DEBUG_BROADCASTS = false;
    private static final java.lang.String TAG = "DomainVerificationProxyV1";

    @android.annotation.NonNull
    private final com.android.server.pm.verify.domain.DomainVerificationCollector mCollector;

    @android.annotation.NonNull
    private final com.android.server.pm.verify.domain.proxy.DomainVerificationProxyV1.Connection mConnection;

    @android.annotation.NonNull
    private final android.content.Context mContext;

    @android.annotation.NonNull
    private final com.android.server.pm.verify.domain.DomainVerificationManagerInternal mManager;

    @android.annotation.NonNull
    private final android.content.ComponentName mVerifierComponent;

    @android.annotation.NonNull
    private final java.lang.Object mLock = new java.lang.Object();

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<java.lang.Integer, android.util.Pair<java.util.UUID, java.lang.String>> mRequests = new android.util.ArrayMap<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mVerificationToken = 0;

    public interface Connection extends com.android.server.pm.verify.domain.proxy.DomainVerificationProxy.BaseConnection {
        @android.annotation.Nullable
        com.android.server.pm.pkg.AndroidPackage getPackage(@android.annotation.NonNull java.lang.String str);
    }

    public DomainVerificationProxyV1(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.pm.verify.domain.DomainVerificationManagerInternal domainVerificationManagerInternal, @android.annotation.NonNull com.android.server.pm.verify.domain.DomainVerificationCollector domainVerificationCollector, @android.annotation.NonNull com.android.server.pm.verify.domain.proxy.DomainVerificationProxyV1.Connection connection, @android.annotation.NonNull android.content.ComponentName componentName) {
        this.mContext = context;
        this.mConnection = connection;
        this.mVerifierComponent = componentName;
        this.mManager = domainVerificationManagerInternal;
        this.mCollector = domainVerificationCollector;
    }

    public static void queueLegacyVerifyResult(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.pm.verify.domain.proxy.DomainVerificationProxyV1.Connection connection, int i, int i2, @android.annotation.Nullable java.util.List<java.lang.String> list, int i3) {
        context.enforceCallingOrSelfPermission("android.permission.INTENT_FILTER_VERIFICATION_AGENT", "Only the intent filter verification agent can verify applications");
        connection.schedule(3, new com.android.server.pm.verify.domain.proxy.DomainVerificationProxyV1.Response(i3, i, i2, list));
    }

    @Override // com.android.server.pm.verify.domain.proxy.DomainVerificationProxy
    public void sendBroadcastForPackages(@android.annotation.NonNull java.util.Set<java.lang.String> set) {
        synchronized (this.mLock) {
            try {
                for (int size = this.mRequests.size() - 1; size >= 0; size--) {
                    if (set.contains(this.mRequests.valueAt(size).second)) {
                        this.mRequests.removeAt(size);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        this.mConnection.schedule(2, set);
    }

    @Override // com.android.server.pm.verify.domain.proxy.DomainVerificationProxy
    public boolean runMessage(int i, java.lang.Object obj) {
        switch (i) {
            case 2:
                java.util.Set<java.lang.String> set = (java.util.Set) obj;
                android.util.ArrayMap<java.lang.Integer, android.util.Pair<java.util.UUID, java.lang.String>> arrayMap = new android.util.ArrayMap<>(set.size());
                synchronized (this.mLock) {
                    try {
                        for (java.lang.String str : set) {
                            java.util.UUID domainVerificationInfoId = this.mManager.getDomainVerificationInfoId(str);
                            if (domainVerificationInfoId != null) {
                                int i2 = this.mVerificationToken;
                                this.mVerificationToken = i2 + 1;
                                arrayMap.put(java.lang.Integer.valueOf(i2), android.util.Pair.create(domainVerificationInfoId, str));
                            }
                        }
                        this.mRequests.putAll((android.util.ArrayMap<? extends java.lang.Integer, ? extends android.util.Pair<java.util.UUID, java.lang.String>>) arrayMap);
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                sendBroadcasts(arrayMap);
                return true;
            case 3:
                com.android.server.pm.verify.domain.proxy.DomainVerificationProxyV1.Response response = (com.android.server.pm.verify.domain.proxy.DomainVerificationProxyV1.Response) obj;
                android.util.Pair<java.util.UUID, java.lang.String> pair = this.mRequests.get(java.lang.Integer.valueOf(response.verificationId));
                if (pair == null) {
                    return true;
                }
                java.util.UUID uuid = (java.util.UUID) pair.first;
                java.lang.String str2 = (java.lang.String) pair.second;
                try {
                    android.content.pm.verify.domain.DomainVerificationInfo domainVerificationInfo = this.mManager.getDomainVerificationInfo(str2);
                    if (domainVerificationInfo == null || !java.util.Objects.equals(uuid, domainVerificationInfo.getIdentifier()) || this.mConnection.getPackage(str2) == null) {
                        return true;
                    }
                    android.util.ArraySet arraySet = new android.util.ArraySet(response.failedDomains);
                    java.util.Set keySet = domainVerificationInfo.getHostToStateMap().keySet();
                    android.util.ArraySet arraySet2 = new android.util.ArraySet(keySet);
                    arraySet2.removeAll(arraySet);
                    for (int size = arraySet2.size() - 1; size >= 0; size--) {
                        java.lang.String str3 = (java.lang.String) arraySet2.valueAt(size);
                        if (str3.startsWith("*.")) {
                            java.lang.String substring = str3.substring(2);
                            if (arraySet.contains(substring)) {
                                arraySet.add(str3);
                                arraySet2.removeAt(size);
                                if (!keySet.contains(substring)) {
                                    arraySet.remove(substring);
                                }
                            }
                        }
                    }
                    int i3 = response.callingUid;
                    if (!arraySet2.isEmpty()) {
                        try {
                            if (this.mManager.setDomainVerificationStatusInternal(i3, uuid, arraySet2, 1) != 0) {
                                android.util.Slog.e(TAG, "Failure reporting successful domains for " + str2);
                            }
                        } catch (java.lang.Exception e) {
                            android.util.Slog.e(TAG, "Failure reporting successful domains for " + str2, e);
                        }
                    }
                    if (!arraySet.isEmpty()) {
                        try {
                            if (this.mManager.setDomainVerificationStatusInternal(i3, uuid, arraySet, 6) != 0) {
                                android.util.Slog.e(TAG, "Failure reporting failed domains for " + str2);
                            }
                        } catch (java.lang.Exception e2) {
                            android.util.Slog.e(TAG, "Failure reporting failed domains for " + str2, e2);
                        }
                    }
                    return true;
                } catch (android.content.pm.PackageManager.NameNotFoundException e3) {
                    return true;
                }
            default:
                return false;
        }
    }

    @Override // com.android.server.pm.verify.domain.proxy.DomainVerificationProxy
    public boolean isCallerVerifier(int i) {
        return this.mConnection.isCallerPackage(i, this.mVerifierComponent.getPackageName());
    }

    private void sendBroadcasts(@android.annotation.NonNull android.util.ArrayMap<java.lang.Integer, android.util.Pair<java.util.UUID, java.lang.String>> arrayMap) {
        long powerSaveTempWhitelistAppDuration = this.mConnection.getPowerSaveTempWhitelistAppDuration();
        this.mConnection.getDeviceIdleInternal().addPowerSaveTempWhitelistApp(android.os.Process.myUid(), this.mVerifierComponent.getPackageName(), powerSaveTempWhitelistAppDuration, 0, true, com.android.internal.util.FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_DOMAIN_VERIFICATION_V1, "domain verification agent");
        int size = arrayMap.size();
        for (int i = 0; i < size; i++) {
            int intValue = arrayMap.keyAt(i).intValue();
            java.lang.String str = (java.lang.String) arrayMap.valueAt(i).second;
            com.android.server.pm.pkg.AndroidPackage androidPackage = this.mConnection.getPackage(str);
            if (androidPackage != null) {
                android.content.Intent addFlags = new android.content.Intent("android.intent.action.INTENT_FILTER_NEEDS_VERIFICATION").setComponent(this.mVerifierComponent).putExtra("android.content.pm.extra.INTENT_FILTER_VERIFICATION_ID", intValue).putExtra("android.content.pm.extra.INTENT_FILTER_VERIFICATION_URI_SCHEME", "https").putExtra("android.content.pm.extra.INTENT_FILTER_VERIFICATION_HOSTS", buildHostsString(androidPackage)).putExtra("android.content.pm.extra.INTENT_FILTER_VERIFICATION_PACKAGE_NAME", str).addFlags(268435456);
                android.app.BroadcastOptions makeBasic = android.app.BroadcastOptions.makeBasic();
                makeBasic.setTemporaryAppAllowlist(powerSaveTempWhitelistAppDuration, 0, com.android.internal.util.FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_DOMAIN_VERIFICATION_V1, "");
                this.mContext.sendBroadcastAsUser(addFlags, android.os.UserHandle.SYSTEM, null, makeBasic.toBundle());
            }
        }
    }

    @android.annotation.NonNull
    private java.lang.String buildHostsString(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage) {
        android.util.ArraySet<java.lang.String> collectValidAutoVerifyDomains = this.mCollector.collectValidAutoVerifyDomains(androidPackage);
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        int size = collectValidAutoVerifyDomains.size();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                sb.append(" ");
            }
            java.lang.String valueAt = collectValidAutoVerifyDomains.valueAt(i);
            if (valueAt.startsWith("*.")) {
                valueAt = valueAt.substring(2);
            }
            sb.append(valueAt);
        }
        return sb.toString();
    }

    @Override // com.android.server.pm.verify.domain.proxy.DomainVerificationProxy
    @android.annotation.NonNull
    public android.content.ComponentName getComponentName() {
        return this.mVerifierComponent;
    }

    private static class Response {
        public final int callingUid;

        @android.annotation.NonNull
        public final java.util.List<java.lang.String> failedDomains;
        public final int verificationCode;
        public final int verificationId;

        private Response(int i, int i2, int i3, @android.annotation.Nullable java.util.List<java.lang.String> list) {
            this.callingUid = i;
            this.verificationId = i2;
            this.verificationCode = i3;
            this.failedDomains = list == null ? java.util.Collections.emptyList() : list;
        }
    }
}
