package com.android.server.servicewatcher;

/* loaded from: classes2.dex */
public final class CurrentUserServiceSupplier extends android.content.BroadcastReceiver implements com.android.server.servicewatcher.ServiceWatcher.ServiceSupplier<com.android.server.servicewatcher.CurrentUserServiceSupplier.BoundServiceInfo> {
    private static final java.lang.String EXTRA_SERVICE_IS_MULTIUSER = "serviceIsMultiuser";
    private static final java.lang.String EXTRA_SERVICE_VERSION = "serviceVersion";
    private static final java.lang.String NO_MATCH_PACKAGE = "";
    private static final java.lang.String TAG = "CurrentUserServiceSupplier";
    private static final java.util.Comparator<com.android.server.servicewatcher.CurrentUserServiceSupplier.BoundServiceInfo> sBoundServiceInfoComparator = new java.util.Comparator() { // from class: com.android.server.servicewatcher.CurrentUserServiceSupplier$$ExternalSyntheticLambda0
        @Override // java.util.Comparator
        public final int compare(java.lang.Object obj, java.lang.Object obj2) {
            int lambda$static$0;
            lambda$static$0 = com.android.server.servicewatcher.CurrentUserServiceSupplier.lambda$static$0((com.android.server.servicewatcher.CurrentUserServiceSupplier.BoundServiceInfo) obj, (com.android.server.servicewatcher.CurrentUserServiceSupplier.BoundServiceInfo) obj2);
            return lambda$static$0;
        }
    };
    private final android.app.ActivityManagerInternal mActivityManager;

    @android.annotation.Nullable
    private final java.lang.String mCallerPermission;
    private final android.content.Context mContext;
    private final android.content.Intent mIntent;
    private volatile com.android.server.servicewatcher.ServiceWatcher.ServiceChangedListener mListener;
    private final boolean mMatchSystemAppsOnly;

    @android.annotation.Nullable
    private final java.lang.String mServicePermission;

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$static$0(com.android.server.servicewatcher.CurrentUserServiceSupplier.BoundServiceInfo boundServiceInfo, com.android.server.servicewatcher.CurrentUserServiceSupplier.BoundServiceInfo boundServiceInfo2) {
        if (boundServiceInfo == boundServiceInfo2) {
            return 0;
        }
        if (boundServiceInfo == null) {
            return -1;
        }
        if (boundServiceInfo2 == null) {
            return 1;
        }
        int compare = java.lang.Integer.compare(boundServiceInfo.getVersion(), boundServiceInfo2.getVersion());
        if (compare == 0) {
            if (boundServiceInfo.getUserId() != 0 && boundServiceInfo2.getUserId() == 0) {
                return -1;
            }
            if (boundServiceInfo.getUserId() == 0 && boundServiceInfo2.getUserId() != 0) {
                return 1;
            }
        }
        return compare;
    }

    public static class BoundServiceInfo extends com.android.server.servicewatcher.ServiceWatcher.BoundServiceInfo {

        @android.annotation.Nullable
        private final android.os.Bundle mMetadata;
        private final int mVersion;

        private static int parseUid(android.content.pm.ResolveInfo resolveInfo) {
            int i = resolveInfo.serviceInfo.applicationInfo.uid;
            android.os.Bundle bundle = resolveInfo.serviceInfo.metaData;
            if (bundle != null && bundle.getBoolean(com.android.server.servicewatcher.CurrentUserServiceSupplier.EXTRA_SERVICE_IS_MULTIUSER, false)) {
                return android.os.UserHandle.getUid(0, android.os.UserHandle.getAppId(i));
            }
            return i;
        }

        private static int parseVersion(android.content.pm.ResolveInfo resolveInfo) {
            if (resolveInfo.serviceInfo.metaData != null) {
                return resolveInfo.serviceInfo.metaData.getInt(com.android.server.servicewatcher.CurrentUserServiceSupplier.EXTRA_SERVICE_VERSION, Integer.MIN_VALUE);
            }
            return Integer.MIN_VALUE;
        }

        protected BoundServiceInfo(java.lang.String str, android.content.pm.ResolveInfo resolveInfo) {
            this(str, parseUid(resolveInfo), resolveInfo.serviceInfo.getComponentName(), parseVersion(resolveInfo), resolveInfo.serviceInfo.metaData);
        }

        protected BoundServiceInfo(java.lang.String str, int i, android.content.ComponentName componentName, int i2, @android.annotation.Nullable android.os.Bundle bundle) {
            super(str, i, componentName);
            this.mVersion = i2;
            this.mMetadata = bundle;
        }

        public int getVersion() {
            return this.mVersion;
        }

        @android.annotation.Nullable
        public android.os.Bundle getMetadata() {
            return this.mMetadata;
        }

        @Override // com.android.server.servicewatcher.ServiceWatcher.BoundServiceInfo
        public java.lang.String toString() {
            return super.toString() + "@" + this.mVersion;
        }
    }

    public static com.android.server.servicewatcher.CurrentUserServiceSupplier createFromConfig(android.content.Context context, java.lang.String str, int i, int i2) {
        return create(context, str, retrieveExplicitPackage(context, i, i2), null, null);
    }

    public static com.android.server.servicewatcher.CurrentUserServiceSupplier create(android.content.Context context, java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable java.lang.String str3, @android.annotation.Nullable java.lang.String str4) {
        return new com.android.server.servicewatcher.CurrentUserServiceSupplier(context, str, str2, str3, str4, true);
    }

    public static com.android.server.servicewatcher.CurrentUserServiceSupplier createUnsafeForTestsOnly(android.content.Context context, java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable java.lang.String str3, @android.annotation.Nullable java.lang.String str4) {
        return new com.android.server.servicewatcher.CurrentUserServiceSupplier(context, str, str2, str3, str4, false);
    }

    @android.annotation.Nullable
    private static java.lang.String retrieveExplicitPackage(android.content.Context context, int i, int i2) {
        android.content.res.Resources resources = context.getResources();
        if (!resources.getBoolean(i)) {
            if (android.location.flags.Flags.fixServiceWatcher()) {
                android.util.TypedValue typedValue = new android.util.TypedValue();
                resources.getValue(i2, typedValue, true);
                java.lang.CharSequence coerceToString = typedValue.coerceToString();
                if (coerceToString == null) {
                    return "";
                }
                return coerceToString.toString();
            }
            return resources.getString(i2);
        }
        return null;
    }

    private CurrentUserServiceSupplier(android.content.Context context, java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable java.lang.String str3, @android.annotation.Nullable java.lang.String str4, boolean z) {
        this.mContext = context;
        android.app.ActivityManagerInternal activityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
        java.util.Objects.requireNonNull(activityManagerInternal);
        this.mActivityManager = activityManagerInternal;
        this.mIntent = new android.content.Intent(str);
        if (str2 != null) {
            this.mIntent.setPackage(str2);
        }
        this.mCallerPermission = str3;
        this.mServicePermission = str4;
        this.mMatchSystemAppsOnly = z;
    }

    @Override // com.android.server.servicewatcher.ServiceWatcher.ServiceSupplier
    public boolean hasMatchingService() {
        int i;
        if (android.location.flags.Flags.fixServiceWatcher() && "".equals(this.mIntent.getPackage())) {
            return false;
        }
        if (!this.mMatchSystemAppsOnly) {
            i = 786432;
        } else {
            i = 1835008;
        }
        return !this.mContext.getPackageManager().queryIntentServicesAsUser(this.mIntent, i, 0).isEmpty();
    }

    @Override // com.android.server.servicewatcher.ServiceWatcher.ServiceSupplier
    public void register(com.android.server.servicewatcher.ServiceWatcher.ServiceChangedListener serviceChangedListener) {
        com.android.internal.util.Preconditions.checkState(this.mListener == null);
        this.mListener = serviceChangedListener;
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        intentFilter.addAction("android.intent.action.USER_UNLOCKED");
        intentFilter.setPriority(1000);
        this.mContext.registerReceiverAsUser(this, android.os.UserHandle.ALL, intentFilter, null, com.android.server.FgThread.getHandler());
    }

    @Override // com.android.server.servicewatcher.ServiceWatcher.ServiceSupplier
    public void unregister() {
        com.android.internal.util.Preconditions.checkArgument(this.mListener != null);
        this.mListener = null;
        this.mContext.unregisterReceiver(this);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.server.servicewatcher.ServiceWatcher.ServiceSupplier
    public com.android.server.servicewatcher.CurrentUserServiceSupplier.BoundServiceInfo getServiceInfo() {
        int i;
        com.android.server.servicewatcher.CurrentUserServiceSupplier.BoundServiceInfo boundServiceInfo = null;
        if (android.location.flags.Flags.fixServiceWatcher() && "".equals(this.mIntent.getPackage())) {
            return null;
        }
        if (!this.mMatchSystemAppsOnly) {
            i = 268435584;
        } else {
            i = 269484160;
        }
        for (android.content.pm.ResolveInfo resolveInfo : this.mContext.getPackageManager().queryIntentServicesAsUser(this.mIntent, i, this.mActivityManager.getCurrentUserId())) {
            android.content.pm.ServiceInfo serviceInfo = resolveInfo.serviceInfo;
            java.util.Objects.requireNonNull(serviceInfo);
            if (this.mCallerPermission == null || this.mCallerPermission.equals(serviceInfo.permission)) {
                com.android.server.servicewatcher.CurrentUserServiceSupplier.BoundServiceInfo boundServiceInfo2 = new com.android.server.servicewatcher.CurrentUserServiceSupplier.BoundServiceInfo(this.mIntent.getAction(), resolveInfo);
                if (this.mServicePermission != null && this.mContext.checkPermission(this.mServicePermission, -1, boundServiceInfo2.mUid) != 0) {
                    android.util.Log.d(TAG, boundServiceInfo2.getComponentName().flattenToShortString() + " disqualified due to not holding " + this.mCallerPermission);
                } else if (sBoundServiceInfoComparator.compare(boundServiceInfo2, boundServiceInfo) > 0) {
                    boundServiceInfo = boundServiceInfo2;
                }
            } else {
                android.util.Log.d(TAG, serviceInfo.getComponentName().flattenToShortString() + " disqualified due to not requiring " + this.mCallerPermission);
            }
        }
        return boundServiceInfo;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.content.BroadcastReceiver
    public void onReceive(android.content.Context context, android.content.Intent intent) {
        int intExtra;
        com.android.server.servicewatcher.ServiceWatcher.ServiceChangedListener serviceChangedListener;
        char c;
        java.lang.String action = intent.getAction();
        if (action == null || (intExtra = intent.getIntExtra("android.intent.extra.user_handle", com.android.server.am.ProcessList.INVALID_ADJ)) == -10000 || (serviceChangedListener = this.mListener) == null) {
            return;
        }
        switch (action.hashCode()) {
            case 833559602:
                if (action.equals("android.intent.action.USER_UNLOCKED")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 959232034:
                if (action.equals("android.intent.action.USER_SWITCHED")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                serviceChangedListener.onServiceChanged();
                break;
            case 1:
                if (intExtra == this.mActivityManager.getCurrentUserId()) {
                    serviceChangedListener.onServiceChanged();
                    break;
                }
                break;
        }
    }
}
