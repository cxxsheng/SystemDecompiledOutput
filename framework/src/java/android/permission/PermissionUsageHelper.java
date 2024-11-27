package android.permission;

/* loaded from: classes3.dex */
public class PermissionUsageHelper implements android.app.AppOpsManager.OnOpActiveChangedListener, android.app.AppOpsManager.OnOpStartedListener {
    private static final long DEFAULT_RECENT_TIME_MS = 15000;
    private static final long DEFAULT_RUNNING_TIME_MS = 5000;
    private static final java.lang.String PROPERTY_CAMERA_MIC_ICONS_ENABLED = "camera_mic_icons_enabled";
    private static final java.lang.String PROPERTY_LOCATION_INDICATORS_ENABLED = "location_indicators_enabled";
    private static final java.lang.String RECENT_ACCESS_TIME_MS = "recent_access_time_ms";
    private static final java.lang.String RUNNING_ACCESS_TIME_MS = "running_access_time_ms";
    private static final java.lang.String SYSTEM_PKG = "android";
    private android.app.AppOpsManager mAppOpsManager;
    private android.content.Context mContext;
    private android.content.pm.PackageManager mPkgManager;
    private android.companion.virtual.VirtualDeviceManager mVirtualDeviceManager;
    private static final java.lang.String LOG_TAG = android.permission.PermissionUsageHelper.class.getName();
    private static final java.util.List<java.lang.String> LOCATION_OPS = java.util.List.of(android.app.AppOpsManager.OPSTR_COARSE_LOCATION, android.app.AppOpsManager.OPSTR_FINE_LOCATION);
    private static final java.util.List<java.lang.String> MIC_OPS = java.util.List.of(android.app.AppOpsManager.OPSTR_PHONE_CALL_MICROPHONE, android.app.AppOpsManager.OPSTR_RECEIVE_AMBIENT_TRIGGER_AUDIO, android.app.AppOpsManager.OPSTR_RECORD_AUDIO);
    private static final java.util.List<java.lang.String> CAMERA_OPS = java.util.List.of(android.app.AppOpsManager.OPSTR_PHONE_CALL_CAMERA, android.app.AppOpsManager.OPSTR_CAMERA);
    private final android.util.ArrayMap<java.lang.Integer, java.util.ArrayList<android.permission.PermissionUsageHelper.AccessChainLink>> mAttributionChains = new android.util.ArrayMap<>();
    private android.util.ArrayMap<android.os.UserHandle, android.content.Context> mUserContexts = new android.util.ArrayMap<>();

    private static boolean shouldShowIndicators() {
        return android.provider.DeviceConfig.getBoolean("privacy", "camera_mic_icons_enabled", true);
    }

    private static boolean shouldShowLocationIndicator() {
        return android.provider.DeviceConfig.getBoolean("privacy", "location_indicators_enabled", false);
    }

    private static long getRecentThreshold(java.lang.Long l) {
        return l.longValue() - android.provider.DeviceConfig.getLong("privacy", RECENT_ACCESS_TIME_MS, DEFAULT_RECENT_TIME_MS);
    }

    private static long getRunningThreshold(java.lang.Long l) {
        return l.longValue() - android.provider.DeviceConfig.getLong("privacy", RUNNING_ACCESS_TIME_MS, 5000L);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static java.lang.String getGroupForOp(java.lang.String str) {
        char c;
        switch (str.hashCode()) {
            case -1671423430:
                if (str.equals(android.app.AppOpsManager.OPSTR_COARSE_LOCATION)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -210165041:
                if (str.equals(android.app.AppOpsManager.OPSTR_FINE_LOCATION)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 148526607:
                if (str.equals(android.app.AppOpsManager.OPSTR_PHONE_CALL_MICROPHONE)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 646711553:
                if (str.equals(android.app.AppOpsManager.OPSTR_RECEIVE_AMBIENT_TRIGGER_AUDIO)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1191287187:
                if (str.equals(android.app.AppOpsManager.OPSTR_RECORD_AUDIO)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1390525066:
                if (str.equals(android.app.AppOpsManager.OPSTR_PHONE_CALL_CAMERA)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 1528082064:
                if (str.equals(android.app.AppOpsManager.OPSTR_CAMERA)) {
                    c = 2;
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
            case 1:
                return android.Manifest.permission_group.MICROPHONE;
            case 2:
                return android.Manifest.permission_group.CAMERA;
            case 3:
            case 4:
                return str;
            case 5:
            case 6:
                return android.Manifest.permission_group.LOCATION;
            default:
                throw new java.lang.IllegalArgumentException("Unknown app op: " + str);
        }
    }

    public PermissionUsageHelper(android.content.Context context) {
        this.mContext = context;
        this.mPkgManager = context.getPackageManager();
        this.mAppOpsManager = (android.app.AppOpsManager) context.getSystemService(android.app.AppOpsManager.class);
        this.mVirtualDeviceManager = (android.companion.virtual.VirtualDeviceManager) context.getSystemService(android.companion.virtual.VirtualDeviceManager.class);
        this.mUserContexts.put(android.os.Process.myUserHandle(), this.mContext);
        this.mAppOpsManager.startWatchingActive(new java.lang.String[]{android.app.AppOpsManager.OPSTR_CAMERA, android.app.AppOpsManager.OPSTR_RECORD_AUDIO}, context.getMainExecutor(), this);
        this.mAppOpsManager.startWatchingStarted(new int[]{26, 27}, this);
    }

    private android.content.Context getUserContext(android.os.UserHandle userHandle) {
        if (!this.mUserContexts.containsKey(userHandle)) {
            this.mUserContexts.put(userHandle, this.mContext.createContextAsUser(userHandle, 0));
        }
        return this.mUserContexts.get(userHandle);
    }

    public void tearDown() {
        this.mAppOpsManager.stopWatchingActive(this);
        this.mAppOpsManager.stopWatchingStarted(this);
    }

    @Override // android.app.AppOpsManager.OnOpActiveChangedListener
    public void onOpActiveChanged(java.lang.String str, int i, java.lang.String str2, boolean z) {
    }

    @Override // android.app.AppOpsManager.OnOpActiveChangedListener
    public void onOpActiveChanged(java.lang.String str, int i, java.lang.String str2, java.lang.String str3, boolean z, int i2, int i3) {
        if (z) {
            return;
        }
        synchronized (this.mAttributionChains) {
            this.mAttributionChains.remove(java.lang.Integer.valueOf(i3));
            int size = this.mAttributionChains.size();
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (int i4 = 0; i4 < size; i4++) {
                int intValue = this.mAttributionChains.keyAt(i4).intValue();
                java.util.ArrayList<android.permission.PermissionUsageHelper.AccessChainLink> valueAt = this.mAttributionChains.valueAt(i4);
                int size2 = valueAt.size();
                int i5 = 0;
                while (true) {
                    if (i5 >= size2) {
                        break;
                    }
                    if (!valueAt.get(i5).packageAndOpEquals(str, str2, str3, i)) {
                        i5++;
                    } else {
                        arrayList.add(java.lang.Integer.valueOf(intValue));
                        break;
                    }
                }
            }
            this.mAttributionChains.removeAll(arrayList);
        }
    }

    @Override // android.app.AppOpsManager.OnOpStartedListener
    public void onOpStarted(int i, int i2, java.lang.String str, java.lang.String str2, int i3, int i4) {
    }

    @Override // android.app.AppOpsManager.OnOpStartedListener
    public void onOpStarted(int i, int i2, java.lang.String str, java.lang.String str2, int i3, int i4, int i5, int i6, int i7) {
        if (i5 == 0 || i7 == -1 || i6 == 0 || (i6 & 8) == 0) {
            return;
        }
        synchronized (this.mAttributionChains) {
            addLinkToChainIfNotPresentLocked(android.app.AppOpsManager.opToPublicName(i), str, i2, str2, i6, i7);
        }
    }

    private void addLinkToChainIfNotPresentLocked(java.lang.String str, java.lang.String str2, int i, java.lang.String str3, int i2, int i3) {
        java.util.ArrayList<android.permission.PermissionUsageHelper.AccessChainLink> computeIfAbsent = this.mAttributionChains.computeIfAbsent(java.lang.Integer.valueOf(i3), new java.util.function.Function() { // from class: android.permission.PermissionUsageHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return android.permission.PermissionUsageHelper.lambda$addLinkToChainIfNotPresentLocked$0((java.lang.Integer) obj);
            }
        });
        android.permission.PermissionUsageHelper.AccessChainLink accessChainLink = new android.permission.PermissionUsageHelper.AccessChainLink(str, str2, str3, i, i2);
        if (computeIfAbsent.contains(accessChainLink)) {
            return;
        }
        int size = computeIfAbsent.size();
        if (size != 0 && !accessChainLink.isEnd()) {
            int i4 = size - 1;
            if (computeIfAbsent.get(i4).isEnd()) {
                if (accessChainLink.isStart()) {
                    computeIfAbsent.add(0, accessChainLink);
                    return;
                } else {
                    if (computeIfAbsent.get(computeIfAbsent.size() - 1).isEnd()) {
                        computeIfAbsent.add(i4, accessChainLink);
                        return;
                    }
                    return;
                }
            }
        }
        computeIfAbsent.add(accessChainLink);
    }

    static /* synthetic */ java.util.ArrayList lambda$addLinkToChainIfNotPresentLocked$0(java.lang.Integer num) {
        return new java.util.ArrayList();
    }

    public java.util.List<android.permission.PermissionGroupUsage> getOpUsageDataByDevice(boolean z, java.lang.String str) {
        java.lang.String str2;
        boolean z2;
        android.permission.PermissionUsageHelper permissionUsageHelper = this;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (!shouldShowIndicators()) {
            return arrayList;
        }
        java.util.ArrayList arrayList2 = new java.util.ArrayList(CAMERA_OPS);
        if (shouldShowLocationIndicator()) {
            arrayList2.addAll(LOCATION_OPS);
        }
        if (z) {
            arrayList2.addAll(MIC_OPS);
        }
        java.util.Map<java.lang.String, java.util.List<android.permission.PermissionUsageHelper.OpUsage>> opUsagesByDevice = permissionUsageHelper.getOpUsagesByDevice(arrayList2, str);
        java.util.ArrayList arrayList3 = new java.util.ArrayList(opUsagesByDevice.keySet());
        android.media.AudioManager audioManager = (android.media.AudioManager) permissionUsageHelper.mContext.getSystemService(android.media.AudioManager.class);
        java.lang.String str3 = android.app.AppOpsManager.OPSTR_PHONE_CALL_CAMERA;
        boolean contains = arrayList3.contains(android.app.AppOpsManager.OPSTR_PHONE_CALL_CAMERA);
        java.lang.String str4 = android.app.AppOpsManager.OPSTR_PHONE_CALL_MICROPHONE;
        boolean z3 = true;
        boolean z4 = contains || arrayList3.contains(android.app.AppOpsManager.OPSTR_PHONE_CALL_MICROPHONE);
        java.lang.String str5 = android.Manifest.permission_group.MICROPHONE;
        if (z4 && arrayList3.contains(android.Manifest.permission_group.MICROPHONE) && audioManager.getMode() == 3) {
            android.telephony.TelephonyManager telephonyManager = (android.telephony.TelephonyManager) permissionUsageHelper.mContext.getSystemService(android.telephony.TelephonyManager.class);
            java.util.List<android.permission.PermissionUsageHelper.OpUsage> list = opUsagesByDevice.get(android.Manifest.permission_group.MICROPHONE);
            for (int i = 0; i < list.size(); i++) {
                if (telephonyManager.checkCarrierPrivilegesForPackage(list.get(i).packageName) == 1) {
                    arrayList3.remove(android.app.AppOpsManager.OPSTR_PHONE_CALL_CAMERA);
                    arrayList3.remove(android.app.AppOpsManager.OPSTR_PHONE_CALL_MICROPHONE);
                }
            }
        }
        android.util.ArrayMap<java.lang.String, java.util.Map<java.lang.String, java.lang.String>> arrayMap = new android.util.ArrayMap<>();
        int i2 = 0;
        while (i2 < arrayList3.size()) {
            java.lang.String str6 = (java.lang.String) arrayList3.get(i2);
            android.util.ArrayMap<android.permission.PermissionUsageHelper.OpUsage, java.lang.CharSequence> uniqueUsagesWithLabels = permissionUsageHelper.getUniqueUsagesWithLabels(str6, opUsagesByDevice.get(str6));
            permissionUsageHelper.updateSubattributionLabelsMap(opUsagesByDevice.get(str6), arrayMap);
            if (str6.equals(str4)) {
                str2 = str5;
                z2 = z3;
            } else if (!str6.equals(str3)) {
                str2 = str6;
                z2 = false;
            } else {
                str2 = android.Manifest.permission_group.CAMERA;
                z2 = z3;
            }
            int i3 = 0;
            while (i3 < uniqueUsagesWithLabels.size()) {
                android.permission.PermissionUsageHelper.OpUsage keyAt = uniqueUsagesWithLabels.keyAt(i3);
                arrayList.add(new android.permission.PermissionGroupUsage(keyAt.packageName, keyAt.uid, keyAt.lastAccessTime, str2, keyAt.isRunning, z2, keyAt.attributionTag, arrayMap.getOrDefault(keyAt.packageName, new android.util.ArrayMap()).getOrDefault(keyAt.attributionTag, null), uniqueUsagesWithLabels.valueAt(i3), str));
                i3++;
                i2 = i2;
                arrayMap = arrayMap;
                str3 = str3;
                opUsagesByDevice = opUsagesByDevice;
                arrayList3 = arrayList3;
                str4 = str4;
                uniqueUsagesWithLabels = uniqueUsagesWithLabels;
                str5 = str5;
                z3 = true;
            }
            i2++;
            permissionUsageHelper = this;
            opUsagesByDevice = opUsagesByDevice;
        }
        return arrayList;
    }

    public java.util.List<android.permission.PermissionGroupUsage> getOpUsageDataForAllDevices(boolean z) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.List<android.companion.virtual.VirtualDevice> virtualDevices = this.mVirtualDeviceManager.getVirtualDevices();
        android.util.ArraySet arraySet = new android.util.ArraySet();
        for (int i = 0; i < virtualDevices.size(); i++) {
            arraySet.add(virtualDevices.get(i).getPersistentDeviceId());
        }
        arraySet.add(android.companion.virtual.VirtualDeviceManager.PERSISTENT_DEVICE_ID_DEFAULT);
        for (int i2 = 0; i2 < arraySet.size(); i2++) {
            arrayList.addAll(getOpUsageDataByDevice(z, (java.lang.String) arraySet.valueAt(i2)));
        }
        return arrayList;
    }

    private void updateSubattributionLabelsMap(java.util.List<android.permission.PermissionUsageHelper.OpUsage> list, android.util.ArrayMap<java.lang.String, java.util.Map<java.lang.String, java.lang.String>> arrayMap) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (android.permission.PermissionUsageHelper.OpUsage opUsage : list) {
            if (opUsage.attributionTag != null && !arrayMap.containsKey(opUsage.packageName)) {
                arrayMap.put(opUsage.packageName, getSubattributionLabelsForPackage(opUsage.packageName, opUsage.uid));
            }
        }
    }

    private android.util.ArrayMap<java.lang.String, java.lang.String> getSubattributionLabelsForPackage(java.lang.String str, int i) {
        android.util.ArrayMap<java.lang.String, java.lang.String> arrayMap = new android.util.ArrayMap<>();
        android.os.UserHandle userHandleForUid = android.os.UserHandle.getUserHandleForUid(i);
        if (!isSubattributionSupported(str, i)) {
            return arrayMap;
        }
        android.content.Context userContext = getUserContext(userHandleForUid);
        android.content.pm.PackageInfo packageInfo = userContext.getPackageManager().getPackageInfo(str, android.content.pm.PackageManager.PackageInfoFlags.of(2147487744L));
        android.content.Context createPackageContext = userContext.createPackageContext(packageInfo.packageName, 0);
        for (android.content.pm.Attribution attribution : packageInfo.attributions) {
            try {
                arrayMap.put(attribution.getTag(), createPackageContext.getString(attribution.getLabel()));
            } catch (android.content.res.Resources.NotFoundException e) {
            }
        }
        return arrayMap;
    }

    private boolean isSubattributionSupported(java.lang.String str, int i) {
        android.content.pm.ApplicationInfo applicationInfoAsUser;
        try {
            if (!isLocationProvider(str) || (applicationInfoAsUser = getUserContext(android.os.UserHandle.getUserHandleForUid(i)).getPackageManager().getApplicationInfoAsUser(str, android.content.pm.PackageManager.ApplicationInfoFlags.of(0L), android.os.UserHandle.getUserId(i))) == null) {
                return false;
            }
            return applicationInfoAsUser.areAttributionsUserVisible();
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private boolean isLocationProvider(java.lang.String str) {
        return ((android.location.LocationManager) java.util.Objects.requireNonNull((android.location.LocationManager) this.mContext.getSystemService(android.location.LocationManager.class))).isProviderPackage(str);
    }

    private java.util.Map<java.lang.String, java.util.List<android.permission.PermissionUsageHelper.OpUsage>> getOpUsagesByDevice(java.util.List<java.lang.String> list, java.lang.String str) {
        java.util.List<android.app.AppOpsManager.PackageOps> packagesForOps;
        long j;
        android.permission.PermissionUsageHelper.OpUsage opUsage;
        int i;
        java.util.ArrayList arrayList;
        int i2;
        int i3;
        long j2;
        try {
            if (android.permission.flags.Flags.deviceAwarePermissionApisEnabled()) {
                packagesForOps = this.mAppOpsManager.getPackagesForOps((java.lang.String[]) list.toArray(new java.lang.String[list.size()]), str);
            } else {
                if (!java.util.Objects.equals(str, android.companion.virtual.VirtualDeviceManager.PERSISTENT_DEVICE_ID_DEFAULT)) {
                    android.util.Slog.w(LOG_TAG, "device_aware_permission_apis_enabled flag not enabled when deviceId is not default");
                    return java.util.Collections.emptyMap();
                }
                packagesForOps = this.mAppOpsManager.getPackagesForOps((java.lang.String[]) list.toArray(new java.lang.String[list.size()]));
            }
            long currentTimeMillis = java.lang.System.currentTimeMillis();
            long recentThreshold = getRecentThreshold(java.lang.Long.valueOf(currentTimeMillis));
            long runningThreshold = getRunningThreshold(java.lang.Long.valueOf(currentTimeMillis));
            android.util.ArrayMap arrayMap = new android.util.ArrayMap();
            int size = packagesForOps.size();
            for (int i4 = 0; i4 < size; i4++) {
                android.app.AppOpsManager.PackageOps packageOps = packagesForOps.get(i4);
                int uid = packageOps.getUid();
                android.os.UserHandle.getUserHandleForUid(uid);
                java.lang.String packageName = packageOps.getPackageName();
                int size2 = packageOps.getOps().size();
                int i5 = 0;
                while (i5 < size2) {
                    android.app.AppOpsManager.OpEntry opEntry = packageOps.getOps().get(i5);
                    java.lang.String opStr = opEntry.getOpStr();
                    java.util.ArrayList arrayList2 = new java.util.ArrayList(opEntry.getAttributedOpEntries().keySet());
                    int size3 = opEntry.getAttributedOpEntries().size();
                    int i6 = 0;
                    while (i6 < size3) {
                        java.util.List<android.app.AppOpsManager.PackageOps> list2 = packagesForOps;
                        java.lang.String str2 = (java.lang.String) arrayList2.get(i6);
                        long j3 = currentTimeMillis;
                        android.app.AppOpsManager.AttributedOpEntry attributedOpEntry = opEntry.getAttributedOpEntries().get(str2);
                        long lastAccessTime = attributedOpEntry.getLastAccessTime(13);
                        if (!attributedOpEntry.isRunning()) {
                            j = lastAccessTime;
                        } else {
                            j = j3;
                        }
                        if (j < recentThreshold && !attributedOpEntry.isRunning()) {
                            j2 = recentThreshold;
                            i = size3;
                            arrayList = arrayList2;
                            i2 = i5;
                            i3 = size2;
                        } else {
                            boolean z = attributedOpEntry.isRunning() || j >= runningThreshold;
                            android.app.AppOpsManager.OpEventProxyInfo lastProxyInfo = attributedOpEntry.getLastProxyInfo(13);
                            if (lastProxyInfo != null && lastProxyInfo.getPackageName() != null) {
                                opUsage = new android.permission.PermissionUsageHelper.OpUsage(lastProxyInfo.getPackageName(), lastProxyInfo.getAttributionTag(), opStr, lastProxyInfo.getUid(), j, z, null);
                            } else {
                                opUsage = null;
                            }
                            java.lang.String groupForOp = getGroupForOp(opStr);
                            i = size3;
                            arrayList = arrayList2;
                            i2 = i5;
                            i3 = size2;
                            android.permission.PermissionUsageHelper.OpUsage opUsage2 = new android.permission.PermissionUsageHelper.OpUsage(packageName, str2, opStr, uid, j, z, opUsage);
                            java.lang.Integer valueOf = java.lang.Integer.valueOf(opUsage2.getPackageIdHash());
                            if (!arrayMap.containsKey(groupForOp)) {
                                android.util.ArrayMap arrayMap2 = new android.util.ArrayMap();
                                arrayMap2.put(valueOf, opUsage2);
                                arrayMap.put(groupForOp, arrayMap2);
                                j2 = recentThreshold;
                            } else {
                                java.util.Map map = (java.util.Map) arrayMap.get(groupForOp);
                                if (map.containsKey(valueOf)) {
                                    j2 = recentThreshold;
                                    if (opUsage2.lastAccessTime > ((android.permission.PermissionUsageHelper.OpUsage) map.get(valueOf)).lastAccessTime) {
                                        map.put(valueOf, opUsage2);
                                    }
                                } else {
                                    map.put(valueOf, opUsage2);
                                    j2 = recentThreshold;
                                }
                            }
                        }
                        i6++;
                        size2 = i3;
                        recentThreshold = j2;
                        size3 = i;
                        arrayList2 = arrayList;
                        i5 = i2;
                        currentTimeMillis = j3;
                        packagesForOps = list2;
                    }
                    i5++;
                    packagesForOps = packagesForOps;
                }
            }
            android.util.ArrayMap arrayMap3 = new android.util.ArrayMap();
            java.util.ArrayList arrayList3 = new java.util.ArrayList(arrayMap.keySet());
            for (int i7 = 0; i7 < arrayList3.size(); i7++) {
                java.lang.String str3 = (java.lang.String) arrayList3.get(i7);
                arrayMap3.put(str3, new java.util.ArrayList(((java.util.Map) arrayMap.get(str3)).values()));
            }
            return arrayMap3;
        } catch (java.lang.NullPointerException e) {
            return java.util.Collections.emptyMap();
        }
    }

    private java.lang.CharSequence formatLabelList(java.util.List<java.lang.CharSequence> list) {
        return android.icu.text.ListFormatter.getInstance().format(list);
    }

    private android.util.ArrayMap<android.permission.PermissionUsageHelper.OpUsage, java.lang.CharSequence> getUniqueUsagesWithLabels(java.lang.String str, java.util.List<android.permission.PermissionUsageHelper.OpUsage> list) {
        java.lang.String charSequence;
        int i;
        android.permission.PermissionUsageHelper.OpUsage opUsage;
        int i2;
        android.util.ArrayMap<android.permission.PermissionUsageHelper.OpUsage, java.lang.CharSequence> arrayMap = new android.util.ArrayMap<>();
        if (list == null || list.isEmpty()) {
            return arrayMap;
        }
        android.util.ArrayMap arrayMap2 = new android.util.ArrayMap();
        android.util.ArrayMap arrayMap3 = new android.util.ArrayMap();
        android.util.ArraySet arraySet = new android.util.ArraySet();
        android.util.ArrayMap arrayMap4 = new android.util.ArrayMap();
        android.util.ArrayMap arrayMap5 = new android.util.ArrayMap();
        for (int i3 = 0; i3 < list.size(); i3++) {
            android.permission.PermissionUsageHelper.OpUsage opUsage2 = list.get(i3);
            arrayMap2.put(java.lang.Integer.valueOf(opUsage2.getPackageIdHash()), opUsage2);
            if (opUsage2.proxy != null) {
                arrayMap5.put(java.lang.Integer.valueOf(opUsage2.proxy.getPackageIdHash()), opUsage2);
            }
        }
        int i4 = 0;
        while (i4 < list.size()) {
            android.permission.PermissionUsageHelper.OpUsage opUsage3 = list.get(i4);
            if (opUsage3 == null) {
                i2 = i4;
            } else {
                if (!arrayMap5.containsKey(java.lang.Integer.valueOf(opUsage3.getPackageIdHash())) && opUsage3.proxy != null && !android.Manifest.permission_group.MICROPHONE.equals(str)) {
                    arrayMap4.put(opUsage3, new java.util.ArrayList());
                    arraySet.add(java.lang.Integer.valueOf(opUsage3.getPackageIdHash()));
                }
                int packageIdHash = opUsage3.getPackageIdHash();
                android.permission.PermissionUsageHelper.OpUsage opUsage4 = (android.permission.PermissionUsageHelper.OpUsage) arrayMap3.get(java.lang.Integer.valueOf(packageIdHash));
                if (!shouldShowPackage(opUsage3.packageName)) {
                    i2 = i4;
                } else {
                    if (opUsage4 != null) {
                        i2 = i4;
                        if (opUsage3.lastAccessTime <= opUsage4.lastAccessTime) {
                        }
                    } else {
                        i2 = i4;
                    }
                    arrayMap3.put(java.lang.Integer.valueOf(packageIdHash), opUsage3);
                }
            }
            i4 = i2 + 1;
        }
        for (int i5 = 0; i5 < arrayMap4.size(); i5++) {
            android.permission.PermissionUsageHelper.OpUsage opUsage5 = (android.permission.PermissionUsageHelper.OpUsage) arrayMap4.keyAt(i5);
            arrayMap3.remove(java.lang.Integer.valueOf(opUsage5.getPackageIdHash()));
            android.permission.PermissionUsageHelper.OpUsage opUsage6 = (android.permission.PermissionUsageHelper.OpUsage) arrayMap4.keyAt(i5);
            java.util.ArrayList arrayList = (java.util.ArrayList) arrayMap4.get(opUsage6);
            if (opUsage6 != null && arrayList != null) {
                int size = arrayMap2.size();
                int i6 = 0;
                while (opUsage6.proxy != null) {
                    if (arrayMap2.containsKey(java.lang.Integer.valueOf(opUsage6.proxy.getPackageIdHash()))) {
                        i = size;
                        opUsage = (android.permission.PermissionUsageHelper.OpUsage) arrayMap2.get(java.lang.Integer.valueOf(opUsage6.proxy.getPackageIdHash()));
                    } else {
                        android.permission.PermissionUsageHelper.OpUsage opUsage7 = opUsage6.proxy;
                        if (!shouldShowPackage(opUsage7.packageName)) {
                            break;
                        }
                        i = size + 1;
                        opUsage = opUsage7;
                    }
                    if (opUsage == null || i6 == i || opUsage.getPackageIdHash() == opUsage5.getPackageIdHash()) {
                        break;
                    }
                    arraySet.add(java.lang.Integer.valueOf(opUsage.getPackageIdHash()));
                    if (!opUsage.packageName.equals(opUsage5.packageName) && shouldShowPackage(opUsage.packageName)) {
                        try {
                            android.content.pm.PackageManager packageManager = getUserContext(opUsage.getUser()).getPackageManager();
                            java.lang.CharSequence loadLabel = packageManager.getApplicationInfo(opUsage.packageName, 0).loadLabel(packageManager);
                            if (!arrayList.contains(loadLabel)) {
                                arrayList.add(loadLabel);
                            }
                        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                        }
                    }
                    i6++;
                    opUsage6 = opUsage;
                    size = i;
                }
                if (!android.Manifest.permission_group.MICROPHONE.equals(str)) {
                    arrayMap.put(opUsage5, arrayList.isEmpty() ? null : formatLabelList(arrayList));
                }
            }
        }
        synchronized (this.mAttributionChains) {
            for (int i7 = 0; i7 < this.mAttributionChains.size(); i7++) {
                java.util.ArrayList<android.permission.PermissionUsageHelper.AccessChainLink> valueAt = this.mAttributionChains.valueAt(i7);
                int size2 = valueAt.size() - 1;
                if (!valueAt.isEmpty() && valueAt.get(size2).isEnd()) {
                    if (valueAt.get(0).isStart() && str.equals(getGroupForOp(valueAt.get(0).usage.op)) && android.Manifest.permission_group.MICROPHONE.equals(str)) {
                        java.util.Iterator<android.permission.PermissionUsageHelper.AccessChainLink> it = valueAt.iterator();
                        while (it.hasNext()) {
                            arraySet.add(java.lang.Integer.valueOf(it.next().usage.getPackageIdHash()));
                        }
                        android.permission.PermissionUsageHelper.AccessChainLink accessChainLink = valueAt.get(0);
                        android.permission.PermissionUsageHelper.AccessChainLink accessChainLink2 = valueAt.get(size2);
                        while (size2 > 0 && !shouldShowPackage(accessChainLink2.usage.packageName)) {
                            size2--;
                            accessChainLink2 = valueAt.get(size2);
                        }
                        if (!accessChainLink2.usage.packageName.equals(accessChainLink.usage.packageName)) {
                            try {
                                android.content.pm.PackageManager packageManager2 = getUserContext(accessChainLink2.usage.getUser()).getPackageManager();
                                try {
                                    charSequence = packageManager2.getApplicationInfo(accessChainLink2.usage.packageName, 0).loadLabel(packageManager2).toString();
                                } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
                                }
                            } catch (android.content.pm.PackageManager.NameNotFoundException e3) {
                            }
                            arrayMap.put(accessChainLink.usage, charSequence);
                        }
                        charSequence = null;
                        arrayMap.put(accessChainLink.usage, charSequence);
                    }
                }
            }
        }
        java.util.Iterator it2 = arrayMap3.keySet().iterator();
        while (it2.hasNext()) {
            int intValue = ((java.lang.Integer) it2.next()).intValue();
            if (!arraySet.contains(java.lang.Integer.valueOf(intValue))) {
                arrayMap.put((android.permission.PermissionUsageHelper.OpUsage) arrayMap3.get(java.lang.Integer.valueOf(intValue)), null);
            }
        }
        return arrayMap;
    }

    private boolean shouldShowPackage(java.lang.String str) {
        return android.permission.PermissionManager.shouldShowPackageForIndicatorCached(this.mContext, str);
    }

    private static class OpUsage {
        public final java.lang.String attributionTag;
        public final boolean isRunning;
        public final long lastAccessTime;
        public final java.lang.String op;
        public final java.lang.String packageName;
        public final android.permission.PermissionUsageHelper.OpUsage proxy;
        public final int uid;

        OpUsage(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, long j, boolean z, android.permission.PermissionUsageHelper.OpUsage opUsage) {
            this.packageName = str;
            this.attributionTag = str2;
            this.op = str3;
            this.uid = i;
            this.lastAccessTime = j;
            this.isRunning = z;
            this.proxy = opUsage;
        }

        public android.os.UserHandle getUser() {
            return android.os.UserHandle.getUserHandleForUid(this.uid);
        }

        public int getPackageIdHash() {
            return java.util.Objects.hash(this.packageName, java.lang.Integer.valueOf(this.uid));
        }

        public int hashCode() {
            return java.util.Objects.hash(this.packageName, this.attributionTag, this.op, java.lang.Integer.valueOf(this.uid), java.lang.Long.valueOf(this.lastAccessTime), java.lang.Boolean.valueOf(this.isRunning));
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof android.permission.PermissionUsageHelper.OpUsage)) {
                return false;
            }
            android.permission.PermissionUsageHelper.OpUsage opUsage = (android.permission.PermissionUsageHelper.OpUsage) obj;
            return java.util.Objects.equals(this.packageName, opUsage.packageName) && java.util.Objects.equals(this.attributionTag, opUsage.attributionTag) && java.util.Objects.equals(this.op, opUsage.op) && this.uid == opUsage.uid && this.lastAccessTime == opUsage.lastAccessTime && this.isRunning == opUsage.isRunning;
        }
    }

    private static class AccessChainLink {
        public final int flags;
        public final android.permission.PermissionUsageHelper.OpUsage usage;

        AccessChainLink(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, int i2) {
            this.usage = new android.permission.PermissionUsageHelper.OpUsage(str2, str3, str, i, java.lang.System.currentTimeMillis(), true, null);
            this.flags = i2;
        }

        public boolean isEnd() {
            return (this.flags & 1) != 0;
        }

        public boolean isStart() {
            return (this.flags & 4) != 0;
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof android.permission.PermissionUsageHelper.AccessChainLink)) {
                return false;
            }
            android.permission.PermissionUsageHelper.AccessChainLink accessChainLink = (android.permission.PermissionUsageHelper.AccessChainLink) obj;
            return accessChainLink.flags == this.flags && packageAndOpEquals(accessChainLink.usage.op, accessChainLink.usage.packageName, accessChainLink.usage.attributionTag, accessChainLink.usage.uid);
        }

        public boolean packageAndOpEquals(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
            return java.util.Objects.equals(str, this.usage.op) && java.util.Objects.equals(str2, this.usage.packageName) && java.util.Objects.equals(str3, this.usage.attributionTag) && i == this.usage.uid;
        }
    }
}
