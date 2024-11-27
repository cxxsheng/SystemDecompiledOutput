package com.android.server.pm;

/* loaded from: classes2.dex */
public abstract class InstantAppResolver {
    private static final boolean DEBUG_INSTANT = android.os.Build.IS_DEBUGGABLE;
    private static final int RESOLUTION_BIND_TIMEOUT = 2;
    private static final int RESOLUTION_CALL_TIMEOUT = 3;
    private static final int RESOLUTION_FAILURE = 1;
    private static final int RESOLUTION_SUCCESS = 0;
    private static final java.lang.String TAG = "PackageManager";
    private static com.android.internal.logging.MetricsLogger sMetricsLogger;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ResolutionStatus {
    }

    private static com.android.internal.logging.MetricsLogger getLogger() {
        if (sMetricsLogger == null) {
            sMetricsLogger = new com.android.internal.logging.MetricsLogger();
        }
        return sMetricsLogger;
    }

    public static android.content.Intent sanitizeIntent(android.content.Intent intent) {
        android.net.Uri fromParts;
        android.content.Intent intent2 = new android.content.Intent(intent.getAction());
        java.util.Set<java.lang.String> categories = intent.getCategories();
        if (categories != null) {
            java.util.Iterator<java.lang.String> it = categories.iterator();
            while (it.hasNext()) {
                intent2.addCategory(it.next());
            }
        }
        if (intent.getData() == null) {
            fromParts = null;
        } else {
            fromParts = android.net.Uri.fromParts(intent.getScheme(), "", "");
        }
        intent2.setDataAndType(fromParts, intent.getType());
        intent2.addFlags(intent.getFlags());
        intent2.setPackage(intent.getPackage());
        return intent2;
    }

    @android.annotation.NonNull
    public static android.content.pm.InstantAppResolveInfo.InstantAppDigest parseDigest(@android.annotation.NonNull android.content.Intent intent) {
        if (intent.getData() != null && !android.text.TextUtils.isEmpty(intent.getData().getHost())) {
            return new android.content.pm.InstantAppResolveInfo.InstantAppDigest(intent.getData().getHost(), 5);
        }
        return android.content.pm.InstantAppResolveInfo.InstantAppDigest.UNDEFINED;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00ad  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static android.content.pm.AuxiliaryResolveInfo doInstantAppResolutionPhaseOne(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull com.android.server.pm.UserManagerService userManagerService, com.android.server.pm.InstantAppResolverConnection instantAppResolverConnection, android.content.pm.InstantAppRequest instantAppRequest) {
        android.content.Intent intent;
        java.lang.String str;
        int i;
        int i2;
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        java.lang.String str2 = instantAppRequest.token;
        if (DEBUG_INSTANT) {
            android.util.Log.d(TAG, "[" + str2 + "] Phase1; resolving");
        }
        android.content.Intent intent2 = instantAppRequest.origIntent;
        android.content.pm.AuxiliaryResolveInfo auxiliaryResolveInfo = null;
        try {
            java.util.List<android.content.pm.InstantAppResolveInfo> instantAppResolveInfoList = instantAppResolverConnection.getInstantAppResolveInfoList(buildRequestInfo(instantAppRequest));
            if (instantAppResolveInfoList == null || instantAppResolveInfoList.size() <= 0) {
                intent = intent2;
                str = TAG;
                i = 2;
            } else {
                java.lang.String str3 = instantAppRequest.resolvedType;
                int i3 = instantAppRequest.userId;
                java.lang.String str4 = intent2.getPackage();
                int[] iArr = instantAppRequest.hostDigestPrefixSecure;
                str = TAG;
                i = 2;
                intent = intent2;
                try {
                    auxiliaryResolveInfo = filterInstantAppIntent(computer, userManagerService, instantAppResolveInfoList, intent2, str3, i3, str4, str2, iArr);
                } catch (com.android.server.pm.InstantAppResolverConnection.ConnectionException e) {
                    e = e;
                    i2 = 1;
                    if (e.failure == 1) {
                        i2 = i;
                    } else if (e.failure == i) {
                        i2 = 3;
                    }
                    if (instantAppRequest.resolveForStart) {
                        logMetrics(899, currentTimeMillis, str2, i2);
                    }
                    if (DEBUG_INSTANT) {
                        if (i2 != i) {
                        }
                    }
                    if (auxiliaryResolveInfo != null) {
                    }
                    return auxiliaryResolveInfo;
                }
            }
            i2 = 0;
        } catch (com.android.server.pm.InstantAppResolverConnection.ConnectionException e2) {
            e = e2;
            intent = intent2;
            str = TAG;
            i = 2;
        }
        if (instantAppRequest.resolveForStart && i2 == 0) {
            logMetrics(899, currentTimeMillis, str2, i2);
        }
        if (DEBUG_INSTANT && auxiliaryResolveInfo == null) {
            if (i2 != i) {
                android.util.Log.d(str, "[" + str2 + "] Phase1; bind timed out");
            } else {
                java.lang.String str5 = str;
                if (i2 == 3) {
                    android.util.Log.d(str5, "[" + str2 + "] Phase1; call timed out");
                } else if (i2 != 0) {
                    android.util.Log.d(str5, "[" + str2 + "] Phase1; service connection error");
                } else {
                    android.util.Log.d(str5, "[" + str2 + "] Phase1; No results matched");
                }
            }
        }
        if (auxiliaryResolveInfo != null && (intent.getFlags() & 2048) != 0) {
            return new android.content.pm.AuxiliaryResolveInfo(str2, false, createFailureIntent(intent, str2), (java.util.List) null, instantAppRequest.hostDigestPrefixSecure);
        }
        return auxiliaryResolveInfo;
    }

    public static void doInstantAppResolutionPhaseTwo(final android.content.Context context, @android.annotation.NonNull final com.android.server.pm.Computer computer, @android.annotation.NonNull final com.android.server.pm.UserManagerService userManagerService, com.android.server.pm.InstantAppResolverConnection instantAppResolverConnection, final android.content.pm.InstantAppRequest instantAppRequest, final android.content.pm.ActivityInfo activityInfo, android.os.Handler handler) {
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        final java.lang.String str = instantAppRequest.token;
        if (DEBUG_INSTANT) {
            android.util.Log.d(TAG, "[" + str + "] Phase2; resolving");
        }
        final android.content.Intent intent = instantAppRequest.origIntent;
        final android.content.Intent sanitizeIntent = sanitizeIntent(intent);
        try {
            instantAppResolverConnection.getInstantAppIntentFilterList(buildRequestInfo(instantAppRequest), new com.android.server.pm.InstantAppResolverConnection.PhaseTwoCallback() { // from class: com.android.server.pm.InstantAppResolver.1
                /* JADX INFO: Access modifiers changed from: package-private */
                @Override // com.android.server.pm.InstantAppResolverConnection.PhaseTwoCallback
                public void onPhaseTwoResolved(java.util.List<android.content.pm.InstantAppResolveInfo> list, long j) {
                    android.content.Intent intent2;
                    if (list != null && list.size() > 0) {
                        android.content.pm.AuxiliaryResolveInfo filterInstantAppIntent = com.android.server.pm.InstantAppResolver.filterInstantAppIntent(com.android.server.pm.Computer.this, userManagerService, list, intent, null, 0, intent.getPackage(), str, instantAppRequest.hostDigestPrefixSecure);
                        intent2 = filterInstantAppIntent != null ? filterInstantAppIntent.failureIntent : null;
                    } else {
                        intent2 = null;
                    }
                    android.content.Intent buildEphemeralInstallerIntent = com.android.server.pm.InstantAppResolver.buildEphemeralInstallerIntent(instantAppRequest.origIntent, sanitizeIntent, intent2, instantAppRequest.callingPackage, instantAppRequest.callingFeatureId, instantAppRequest.verificationBundle, instantAppRequest.resolvedType, instantAppRequest.userId, instantAppRequest.responseObj.installFailureActivity, str, false, instantAppRequest.responseObj.filters);
                    buildEphemeralInstallerIntent.setComponent(new android.content.ComponentName(activityInfo.packageName, activityInfo.name));
                    com.android.server.pm.InstantAppResolver.logMetrics(com.android.server.am.ProcessList.CACHED_APP_MIN_ADJ, j, str, instantAppRequest.responseObj.filters != null ? 0 : 1);
                    context.startActivity(buildEphemeralInstallerIntent);
                }
            }, handler, currentTimeMillis);
        } catch (com.android.server.pm.InstantAppResolverConnection.ConnectionException e) {
            int i = e.failure == 1 ? 2 : 1;
            logMetrics(com.android.server.am.ProcessList.CACHED_APP_MIN_ADJ, currentTimeMillis, str, i);
            if (DEBUG_INSTANT) {
                if (i == 2) {
                    android.util.Log.d(TAG, "[" + str + "] Phase2; bind timed out");
                    return;
                }
                android.util.Log.d(TAG, "[" + str + "] Phase2; service connection error");
            }
        }
    }

    public static android.content.Intent buildEphemeralInstallerIntent(@android.annotation.NonNull android.content.Intent intent, @android.annotation.NonNull android.content.Intent intent2, @android.annotation.Nullable android.content.Intent intent3, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable android.os.Bundle bundle, @android.annotation.NonNull java.lang.String str3, int i, @android.annotation.Nullable android.content.ComponentName componentName, @android.annotation.Nullable java.lang.String str4, boolean z, java.util.List<android.content.pm.AuxiliaryResolveInfo.AuxiliaryFilter> list) {
        android.content.Intent intent4;
        int flags = intent.getFlags();
        android.content.Intent intent5 = new android.content.Intent();
        intent5.setFlags(flags | 1073741824 | 8388608);
        if (str4 != null) {
            intent5.putExtra("android.intent.extra.INSTANT_APP_TOKEN", str4);
        }
        if (intent.getData() != null) {
            intent5.putExtra("android.intent.extra.INSTANT_APP_HOSTNAME", intent.getData().getHost());
        }
        intent5.putExtra("android.intent.extra.INSTANT_APP_ACTION", intent.getAction());
        intent5.putExtra("android.intent.extra.INTENT", intent2);
        if (!z) {
            if (intent3 != null || componentName != null) {
                if (componentName != null) {
                    try {
                        intent4 = new android.content.Intent();
                        intent4.setComponent(componentName);
                        if (list != null && list.size() == 1) {
                            intent4.putExtra("android.intent.extra.SPLIT_NAME", list.get(0).splitName);
                        }
                        intent4.putExtra("android.intent.extra.INTENT", intent);
                    } catch (android.os.RemoteException e) {
                    }
                } else {
                    intent4 = intent3;
                }
                intent5.putExtra("android.intent.extra.INSTANT_APP_FAILURE", new android.content.IntentSender(android.app.ActivityManager.getService().getIntentSenderWithFeature(2, str, str2, (android.os.IBinder) null, (java.lang.String) null, 1, new android.content.Intent[]{intent4}, new java.lang.String[]{str3}, 1409286144, (android.os.Bundle) null, i)));
            }
            android.content.Intent intent6 = new android.content.Intent(intent);
            intent6.setLaunchToken(str4);
            try {
                intent5.putExtra("android.intent.extra.INSTANT_APP_SUCCESS", new android.content.IntentSender(android.app.ActivityManager.getService().getIntentSenderWithFeature(2, str, str2, (android.os.IBinder) null, (java.lang.String) null, 0, new android.content.Intent[]{intent6}, new java.lang.String[]{str3}, 1409286144, (android.os.Bundle) null, i)));
            } catch (android.os.RemoteException e2) {
            }
            if (bundle != null) {
                intent5.putExtra("android.intent.extra.VERIFICATION_BUNDLE", bundle);
            }
            intent5.putExtra("android.intent.extra.CALLING_PACKAGE", str);
            if (list != null) {
                android.os.Bundle[] bundleArr = new android.os.Bundle[list.size()];
                int size = list.size();
                for (int i2 = 0; i2 < size; i2++) {
                    android.os.Bundle bundle2 = new android.os.Bundle();
                    android.content.pm.AuxiliaryResolveInfo.AuxiliaryFilter auxiliaryFilter = list.get(i2);
                    bundle2.putBoolean("android.intent.extra.UNKNOWN_INSTANT_APP", auxiliaryFilter.resolveInfo != null && auxiliaryFilter.resolveInfo.shouldLetInstallerDecide());
                    bundle2.putString("android.intent.extra.PACKAGE_NAME", auxiliaryFilter.packageName);
                    bundle2.putString("android.intent.extra.SPLIT_NAME", auxiliaryFilter.splitName);
                    bundle2.putLong("android.intent.extra.LONG_VERSION_CODE", auxiliaryFilter.versionCode);
                    bundle2.putBundle("android.intent.extra.INSTANT_APP_EXTRAS", auxiliaryFilter.extras);
                    bundleArr[i2] = bundle2;
                    if (i2 == 0) {
                        intent5.putExtras(bundle2);
                        intent5.putExtra("android.intent.extra.VERSION_CODE", (int) auxiliaryFilter.versionCode);
                    }
                }
                intent5.putExtra("android.intent.extra.INSTANT_APP_BUNDLES", bundleArr);
            }
            intent5.setAction("android.intent.action.INSTALL_INSTANT_APP_PACKAGE");
        } else {
            intent5.setAction("android.intent.action.RESOLVE_INSTANT_APP_PACKAGE");
        }
        return intent5;
    }

    private static android.content.pm.InstantAppRequestInfo buildRequestInfo(android.content.pm.InstantAppRequest instantAppRequest) {
        return new android.content.pm.InstantAppRequestInfo(sanitizeIntent(instantAppRequest.origIntent), instantAppRequest.hostDigestPrefixSecure, android.os.UserHandle.of(instantAppRequest.userId), instantAppRequest.isRequesterInstantApp, instantAppRequest.token);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.content.pm.AuxiliaryResolveInfo filterInstantAppIntent(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull com.android.server.pm.UserManagerService userManagerService, java.util.List<android.content.pm.InstantAppResolveInfo> list, android.content.Intent intent, java.lang.String str, int i, java.lang.String str2, java.lang.String str3, int[] iArr) {
        boolean z;
        android.content.pm.InstantAppResolveInfo.InstantAppDigest parseDigest = parseDigest(intent);
        int[] digestPrefix = parseDigest.getDigestPrefix();
        byte[][] digestBytes = parseDigest.getDigestBytes();
        boolean z2 = intent.isWebIntent() || (digestPrefix.length > 0 && (intent.getFlags() & 2048) == 0);
        boolean z3 = false;
        java.util.ArrayList arrayList = null;
        for (android.content.pm.InstantAppResolveInfo instantAppResolveInfo : list) {
            if (z2 && instantAppResolveInfo.shouldLetInstallerDecide()) {
                android.util.Slog.d(TAG, "InstantAppResolveInfo with mShouldLetInstallerDecide=true when digest required; ignoring");
            } else {
                byte[] digestBytes2 = instantAppResolveInfo.getDigestBytes();
                if (digestPrefix.length > 0 && (z2 || digestBytes2.length > 0)) {
                    int length = digestPrefix.length - 1;
                    while (true) {
                        if (length < 0) {
                            z = false;
                            break;
                        }
                        if (java.util.Arrays.equals(digestBytes[length], digestBytes2)) {
                            z = true;
                            break;
                        }
                        length--;
                    }
                    if (!z) {
                    }
                }
                java.util.List<android.content.pm.AuxiliaryResolveInfo.AuxiliaryFilter> computeResolveFilters = computeResolveFilters(computer, userManagerService, intent, str, i, str2, str3, instantAppResolveInfo);
                if (computeResolveFilters != null) {
                    if (computeResolveFilters.isEmpty()) {
                        z3 = true;
                    }
                    if (arrayList == null) {
                        arrayList = new java.util.ArrayList(computeResolveFilters);
                    } else {
                        arrayList.addAll(computeResolveFilters);
                    }
                }
            }
        }
        if (arrayList == null || arrayList.isEmpty()) {
            return null;
        }
        return new android.content.pm.AuxiliaryResolveInfo(str3, z3, createFailureIntent(intent, str3), arrayList, iArr);
    }

    private static android.content.Intent createFailureIntent(android.content.Intent intent, java.lang.String str) {
        android.content.Intent intent2 = new android.content.Intent(intent);
        intent2.setFlags(intent2.getFlags() | Integer.MIN_VALUE);
        intent2.setFlags(intent2.getFlags() & (-2049));
        intent2.setLaunchToken(str);
        return intent2;
    }

    private static java.util.List<android.content.pm.AuxiliaryResolveInfo.AuxiliaryFilter> computeResolveFilters(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull com.android.server.pm.UserManagerService userManagerService, android.content.Intent intent, java.lang.String str, int i, java.lang.String str2, java.lang.String str3, android.content.pm.InstantAppResolveInfo instantAppResolveInfo) {
        if (instantAppResolveInfo.shouldLetInstallerDecide()) {
            return java.util.Collections.singletonList(new android.content.pm.AuxiliaryResolveInfo.AuxiliaryFilter(instantAppResolveInfo, (java.lang.String) null, instantAppResolveInfo.getExtras()));
        }
        if (str2 != null && !str2.equals(instantAppResolveInfo.getPackageName())) {
            return null;
        }
        java.util.List intentFilters = instantAppResolveInfo.getIntentFilters();
        if (intentFilters == null || intentFilters.isEmpty()) {
            if (intent.isWebIntent()) {
                return null;
            }
            if (DEBUG_INSTANT) {
                android.util.Log.d(TAG, "No app filters; go to phase 2");
            }
            return java.util.Collections.emptyList();
        }
        com.android.server.pm.resolution.ComponentResolver.InstantAppIntentResolver instantAppIntentResolver = new com.android.server.pm.resolution.ComponentResolver.InstantAppIntentResolver(userManagerService);
        for (int size = intentFilters.size() - 1; size >= 0; size--) {
            android.content.pm.InstantAppIntentFilter instantAppIntentFilter = (android.content.pm.InstantAppIntentFilter) intentFilters.get(size);
            java.util.List filters = instantAppIntentFilter.getFilters();
            if (filters != null && !filters.isEmpty()) {
                for (int size2 = filters.size() - 1; size2 >= 0; size2--) {
                    android.content.IntentFilter intentFilter = (android.content.IntentFilter) filters.get(size2);
                    java.util.Iterator<android.content.IntentFilter.AuthorityEntry> authoritiesIterator = intentFilter.authoritiesIterator();
                    if ((authoritiesIterator != null && authoritiesIterator.hasNext()) || ((!intentFilter.hasDataScheme("http") && !intentFilter.hasDataScheme("https")) || !intentFilter.hasAction("android.intent.action.VIEW") || !intentFilter.hasCategory("android.intent.category.BROWSABLE"))) {
                        instantAppIntentResolver.addFilter(computer, new android.content.pm.AuxiliaryResolveInfo.AuxiliaryFilter(intentFilter, instantAppResolveInfo, instantAppIntentFilter.getSplitName(), instantAppResolveInfo.getExtras()));
                    }
                }
            }
        }
        java.util.List<android.content.pm.AuxiliaryResolveInfo.AuxiliaryFilter> queryIntent = instantAppIntentResolver.queryIntent(computer, intent, str, false, i);
        if (!queryIntent.isEmpty()) {
            if (DEBUG_INSTANT) {
                android.util.Log.d(TAG, "[" + str3 + "] Found match(es); " + queryIntent);
            }
            return queryIntent;
        }
        if (DEBUG_INSTANT) {
            android.util.Log.d(TAG, "[" + str3 + "] No matches found package: " + instantAppResolveInfo.getPackageName() + ", versionCode: " + instantAppResolveInfo.getVersionCode());
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void logMetrics(int i, long j, java.lang.String str, int i2) {
        getLogger().write(new android.metrics.LogMaker(i).setType(4).addTaggedData(901, new java.lang.Long(java.lang.System.currentTimeMillis() - j)).addTaggedData(903, str).addTaggedData(902, new java.lang.Integer(i2)));
    }
}
