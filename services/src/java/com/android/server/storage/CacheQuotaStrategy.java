package com.android.server.storage;

/* loaded from: classes2.dex */
public class CacheQuotaStrategy implements android.os.RemoteCallback.OnResultListener {
    private static final java.lang.String ATTR_PREVIOUS_BYTES = "previousBytes";
    private static final java.lang.String ATTR_QUOTA_IN_BYTES = "bytes";
    private static final java.lang.String ATTR_UID = "uid";
    private static final java.lang.String ATTR_UUID = "uuid";
    private static final java.lang.String CACHE_INFO_TAG = "cache-info";
    private static final java.lang.String TAG = "CacheQuotaStrategy";
    private static final java.lang.String TAG_QUOTA = "quota";
    private final android.content.Context mContext;
    private final com.android.server.pm.Installer mInstaller;
    private final java.lang.Object mLock = new java.lang.Object();
    private android.util.AtomicFile mPreviousValuesFile;
    private final android.util.ArrayMap<java.lang.String, android.util.SparseLongArray> mQuotaMap;
    private android.app.usage.ICacheQuotaService mRemoteService;
    private android.content.ServiceConnection mServiceConnection;
    private final android.app.usage.UsageStatsManagerInternal mUsageStats;

    public CacheQuotaStrategy(android.content.Context context, android.app.usage.UsageStatsManagerInternal usageStatsManagerInternal, com.android.server.pm.Installer installer, android.util.ArrayMap<java.lang.String, android.util.SparseLongArray> arrayMap) {
        java.util.Objects.requireNonNull(context);
        this.mContext = context;
        java.util.Objects.requireNonNull(usageStatsManagerInternal);
        this.mUsageStats = usageStatsManagerInternal;
        java.util.Objects.requireNonNull(installer);
        this.mInstaller = installer;
        java.util.Objects.requireNonNull(arrayMap);
        this.mQuotaMap = arrayMap;
        this.mPreviousValuesFile = new android.util.AtomicFile(new java.io.File(new java.io.File(android.os.Environment.getDataDirectory(), "system"), "cachequota.xml"));
    }

    public void recalculateQuotas() {
        createServiceConnection();
        android.content.ComponentName serviceComponentName = getServiceComponentName();
        if (serviceComponentName != null) {
            android.content.Intent intent = new android.content.Intent();
            intent.setComponent(serviceComponentName);
            this.mContext.bindServiceAsUser(intent, this.mServiceConnection, 1, android.os.UserHandle.CURRENT);
        }
    }

    private void createServiceConnection() {
        if (this.mServiceConnection != null) {
            return;
        }
        this.mServiceConnection = new android.content.ServiceConnection() { // from class: com.android.server.storage.CacheQuotaStrategy.1
            @Override // android.content.ServiceConnection
            public void onServiceConnected(android.content.ComponentName componentName, final android.os.IBinder iBinder) {
                android.os.AsyncTask.execute(new java.lang.Runnable() { // from class: com.android.server.storage.CacheQuotaStrategy.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        synchronized (com.android.server.storage.CacheQuotaStrategy.this.mLock) {
                            com.android.server.storage.CacheQuotaStrategy.this.mRemoteService = android.app.usage.ICacheQuotaService.Stub.asInterface(iBinder);
                            java.util.List unfulfilledRequests = com.android.server.storage.CacheQuotaStrategy.this.getUnfulfilledRequests();
                            try {
                                com.android.server.storage.CacheQuotaStrategy.this.mRemoteService.computeCacheQuotaHints(new android.os.RemoteCallback(com.android.server.storage.CacheQuotaStrategy.this), unfulfilledRequests);
                            } catch (android.os.RemoteException e) {
                                android.util.Slog.w(com.android.server.storage.CacheQuotaStrategy.TAG, "Remote exception occurred while trying to get cache quota", e);
                            }
                        }
                    }
                });
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(android.content.ComponentName componentName) {
                synchronized (com.android.server.storage.CacheQuotaStrategy.this.mLock) {
                    com.android.server.storage.CacheQuotaStrategy.this.mRemoteService = null;
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.util.List<android.app.usage.CacheQuotaHint> getUnfulfilledRequests() {
        com.android.server.storage.CacheQuotaStrategy cacheQuotaStrategy = this;
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        long j = currentTimeMillis - 31449600000L;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.List<android.content.pm.UserInfo> users = ((android.os.UserManager) cacheQuotaStrategy.mContext.getSystemService(android.os.UserManager.class)).getUsers();
        android.content.pm.PackageManager packageManager = cacheQuotaStrategy.mContext.getPackageManager();
        for (android.content.pm.UserInfo userInfo : users) {
            java.util.List<android.app.usage.UsageStats> queryUsageStatsForUser = cacheQuotaStrategy.mUsageStats.queryUsageStatsForUser(userInfo.id, 4, j, currentTimeMillis, false);
            if (queryUsageStatsForUser == null) {
                cacheQuotaStrategy = this;
            } else {
                for (int i = 0; i < queryUsageStatsForUser.size(); i++) {
                    android.app.usage.UsageStats usageStats = queryUsageStatsForUser.get(i);
                    try {
                        android.content.pm.ApplicationInfo applicationInfoAsUser = packageManager.getApplicationInfoAsUser(usageStats.getPackageName(), 0, userInfo.id);
                        arrayList.add(new android.app.usage.CacheQuotaHint.Builder().setVolumeUuid(applicationInfoAsUser.volumeUuid).setUid(applicationInfoAsUser.uid).setUsageStats(usageStats).setQuota(-1L).build());
                    } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    }
                }
                cacheQuotaStrategy = this;
            }
        }
        return arrayList;
    }

    public void onResult(android.os.Bundle bundle) {
        java.util.ArrayList parcelableArrayList = bundle.getParcelableArrayList("requests", android.app.usage.CacheQuotaHint.class);
        pushProcessedQuotas(parcelableArrayList);
        writeXmlToFile(parcelableArrayList);
    }

    private void pushProcessedQuotas(java.util.List<android.app.usage.CacheQuotaHint> list) {
        for (android.app.usage.CacheQuotaHint cacheQuotaHint : list) {
            long quota = cacheQuotaHint.getQuota();
            if (quota != -1) {
                try {
                    int uid = cacheQuotaHint.getUid();
                    this.mInstaller.setAppQuota(cacheQuotaHint.getVolumeUuid(), android.os.UserHandle.getUserId(uid), android.os.UserHandle.getAppId(uid), quota);
                    insertIntoQuotaMap(cacheQuotaHint.getVolumeUuid(), android.os.UserHandle.getUserId(uid), android.os.UserHandle.getAppId(uid), quota);
                } catch (com.android.server.pm.Installer.InstallerException e) {
                    android.util.Slog.w(TAG, "Failed to set cache quota for " + cacheQuotaHint.getUid(), e);
                }
            }
        }
        disconnectService();
    }

    private void insertIntoQuotaMap(java.lang.String str, int i, int i2, long j) {
        android.util.SparseLongArray sparseLongArray = this.mQuotaMap.get(str);
        if (sparseLongArray == null) {
            sparseLongArray = new android.util.SparseLongArray();
            this.mQuotaMap.put(str, sparseLongArray);
        }
        sparseLongArray.put(android.os.UserHandle.getUid(i, i2), j);
    }

    private void disconnectService() {
        if (this.mServiceConnection != null) {
            this.mContext.unbindService(this.mServiceConnection);
            this.mServiceConnection = null;
        }
    }

    private android.content.ComponentName getServiceComponentName() {
        java.lang.String servicesSystemSharedLibraryPackageName = this.mContext.getPackageManager().getServicesSystemSharedLibraryPackageName();
        if (servicesSystemSharedLibraryPackageName == null) {
            android.util.Slog.w(TAG, "could not access the cache quota service: no package!");
            return null;
        }
        android.content.Intent intent = new android.content.Intent("android.app.usage.CacheQuotaService");
        intent.setPackage(servicesSystemSharedLibraryPackageName);
        android.content.pm.ResolveInfo resolveService = this.mContext.getPackageManager().resolveService(intent, 132);
        if (resolveService == null || resolveService.serviceInfo == null) {
            android.util.Slog.w(TAG, "No valid components found.");
            return null;
        }
        android.content.pm.ServiceInfo serviceInfo = resolveService.serviceInfo;
        return new android.content.ComponentName(serviceInfo.packageName, serviceInfo.name);
    }

    private void writeXmlToFile(java.util.List<android.app.usage.CacheQuotaHint> list) {
        java.io.FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = this.mPreviousValuesFile.startWrite();
            saveToXml(android.util.Xml.resolveSerializer(fileOutputStream), list, new android.os.StatFs(android.os.Environment.getDataDirectory().getAbsolutePath()).getAvailableBytes());
            this.mPreviousValuesFile.finishWrite(fileOutputStream);
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "An error occurred while writing the cache quota file.", e);
            this.mPreviousValuesFile.failWrite(fileOutputStream);
        }
    }

    public long setupQuotasFromFile() throws java.io.IOException {
        try {
            java.io.FileInputStream openRead = this.mPreviousValuesFile.openRead();
            try {
                try {
                    android.util.Pair<java.lang.Long, java.util.List<android.app.usage.CacheQuotaHint>> readFromXml = readFromXml(openRead);
                    if (openRead != null) {
                        openRead.close();
                    }
                    if (readFromXml == null) {
                        android.util.Slog.e(TAG, "An error occurred while parsing the cache quota file.");
                        return -1L;
                    }
                    pushProcessedQuotas((java.util.List) readFromXml.second);
                    return ((java.lang.Long) readFromXml.first).longValue();
                } catch (org.xmlpull.v1.XmlPullParserException e) {
                    throw new java.lang.IllegalStateException(e.getMessage());
                }
            } finally {
            }
        } catch (java.io.FileNotFoundException e2) {
            return -1L;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static void saveToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.util.List<android.app.usage.CacheQuotaHint> list, long j) throws java.io.IOException {
        typedXmlSerializer.startDocument((java.lang.String) null, true);
        typedXmlSerializer.startTag((java.lang.String) null, CACHE_INFO_TAG);
        typedXmlSerializer.attributeLong((java.lang.String) null, ATTR_PREVIOUS_BYTES, j);
        for (android.app.usage.CacheQuotaHint cacheQuotaHint : list) {
            typedXmlSerializer.startTag((java.lang.String) null, TAG_QUOTA);
            if (cacheQuotaHint.getVolumeUuid() != null) {
                typedXmlSerializer.attribute((java.lang.String) null, ATTR_UUID, cacheQuotaHint.getVolumeUuid());
            }
            typedXmlSerializer.attributeInt((java.lang.String) null, "uid", cacheQuotaHint.getUid());
            typedXmlSerializer.attributeLong((java.lang.String) null, ATTR_QUOTA_IN_BYTES, cacheQuotaHint.getQuota());
            typedXmlSerializer.endTag((java.lang.String) null, TAG_QUOTA);
        }
        typedXmlSerializer.endTag((java.lang.String) null, CACHE_INFO_TAG);
        typedXmlSerializer.endDocument();
    }

    protected static android.util.Pair<java.lang.Long, java.util.List<android.app.usage.CacheQuotaHint>> readFromXml(java.io.InputStream inputStream) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(inputStream);
        int eventType = resolvePullParser.getEventType();
        while (eventType != 2 && eventType != 1) {
            eventType = resolvePullParser.next();
        }
        if (eventType == 1) {
            android.util.Slog.d(TAG, "No quotas found in quota file.");
            return null;
        }
        if (!CACHE_INFO_TAG.equals(resolvePullParser.getName())) {
            throw new java.lang.IllegalStateException("Invalid starting tag.");
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        try {
            long attributeLong = resolvePullParser.getAttributeLong((java.lang.String) null, ATTR_PREVIOUS_BYTES);
            int next = resolvePullParser.next();
            do {
                if (next == 2 && TAG_QUOTA.equals(resolvePullParser.getName())) {
                    android.app.usage.CacheQuotaHint requestFromXml = getRequestFromXml(resolvePullParser);
                    if (requestFromXml != null) {
                        arrayList.add(requestFromXml);
                    }
                }
                next = resolvePullParser.next();
            } while (next != 1);
            return new android.util.Pair<>(java.lang.Long.valueOf(attributeLong), arrayList);
        } catch (java.lang.NumberFormatException e) {
            throw new java.lang.IllegalStateException("Previous bytes formatted incorrectly; aborting quota read.");
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static android.app.usage.CacheQuotaHint getRequestFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) {
        try {
            java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_UUID);
            int attributeInt = typedXmlPullParser.getAttributeInt((java.lang.String) null, "uid");
            return new android.app.usage.CacheQuotaHint.Builder().setVolumeUuid(attributeValue).setUid(attributeInt).setQuota(typedXmlPullParser.getAttributeLong((java.lang.String) null, ATTR_QUOTA_IN_BYTES)).build();
        } catch (org.xmlpull.v1.XmlPullParserException e) {
            android.util.Slog.e(TAG, "Invalid cache quota request, skipping.");
            return null;
        }
    }
}
