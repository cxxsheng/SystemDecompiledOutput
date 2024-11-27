package com.android.server.os;

/* loaded from: classes2.dex */
class BugreportManagerServiceImpl extends android.os.IDumpstate.Stub {
    private static final java.lang.String ATTR_BUGREPORT_FILE = "bugreport-file";
    private static final java.lang.String ATTR_CALLING_PACKAGE = "calling-package";
    private static final java.lang.String ATTR_CALLING_UID = "calling-uid";
    private static final java.lang.String BUGREPORT_SERVICE = "bugreportd";
    private static final boolean DEBUG = false;
    private static final long DEFAULT_BUGREPORT_SERVICE_TIMEOUT_MILLIS = 30000;
    private static final int LOCAL_LOG_SIZE = 20;
    private static final java.lang.String ROLE_SYSTEM_AUTOMOTIVE_PROJECTION = "android.app.role.SYSTEM_AUTOMOTIVE_PROJECTION";
    private static final java.lang.String TAG = "BugreportManagerService";
    private static final java.lang.String TAG_BUGREPORT_DATA = "bugreport-data";
    private static final java.lang.String TAG_BUGREPORT_MAP = "bugreport-map";
    private static final java.lang.String TAG_PERSISTENT_BUGREPORT = "persistent-bugreport";
    private final android.app.AppOpsManager mAppOps;
    private final android.util.ArraySet<java.lang.String> mBugreportAllowlistedPackages;
    private final com.android.server.os.BugreportManagerServiceImpl.BugreportFileManager mBugreportFileManager;
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private com.android.server.os.BugreportManagerServiceImpl.DumpstateListener mCurrentDumpstateListener;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.LocalLog mFinishedBugreports;
    private final com.android.server.os.BugreportManagerServiceImpl.Injector mInjector;
    private final java.lang.Object mLock;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mNumberFinishedBugreports;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.OptionalInt mPreDumpedDataUid;
    private final android.telephony.TelephonyManager mTelephonyManager;

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    static class BugreportFileManager {
        private final android.util.AtomicFile mMappingFile;
        private final java.lang.Object mLock = new java.lang.Object();
        private boolean mReadBugreportMapping = false;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private android.util.ArrayMap<android.util.Pair<java.lang.Integer, java.lang.String>, android.util.ArraySet<java.lang.String>> mBugreportFiles = new android.util.ArrayMap<>();

        @com.android.internal.annotations.GuardedBy({"mLock"})
        @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
        final java.util.Set<java.lang.String> mBugreportFilesToPersist = new java.util.HashSet();

        BugreportFileManager(android.util.AtomicFile atomicFile) {
            this.mMappingFile = atomicFile;
        }

        @android.annotation.RequiresPermission(conditional = true, value = "android.permission.INTERACT_ACROSS_USERS")
        void ensureCallerPreviouslyGeneratedFile(android.content.Context context, final android.content.pm.PackageManager packageManager, final android.util.Pair<java.lang.Integer, java.lang.String> pair, final int i, final java.lang.String str, boolean z) {
            synchronized (this.mLock) {
                try {
                    if (android.app.admin.flags.Flags.onboardingBugreportV2Enabled()) {
                        int intValue = ((java.lang.Integer) android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.os.BugreportManagerServiceImpl$BugreportFileManager$$ExternalSyntheticLambda0
                            public final java.lang.Object getOrThrow() {
                                java.lang.Integer lambda$ensureCallerPreviouslyGeneratedFile$0;
                                lambda$ensureCallerPreviouslyGeneratedFile$0 = com.android.server.os.BugreportManagerServiceImpl.BugreportFileManager.lambda$ensureCallerPreviouslyGeneratedFile$0(packageManager, pair, i, str);
                                return lambda$ensureCallerPreviouslyGeneratedFile$0;
                            }
                        })).intValue();
                        if (intValue != ((java.lang.Integer) pair.first).intValue() && context.checkCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS") != 0) {
                            throw new java.lang.SecurityException(((java.lang.String) pair.second) + " does not hold the INTERACT_ACROSS_USERS permission to access cross-user bugreports.");
                        }
                        if (!this.mReadBugreportMapping || z) {
                            readBugreportMappingLocked();
                        }
                        android.util.ArraySet<java.lang.String> arraySet = this.mBugreportFiles.get(new android.util.Pair(java.lang.Integer.valueOf(intValue), (java.lang.String) pair.second));
                        if (arraySet == null || !arraySet.contains(str)) {
                            throwInvalidBugreportFileForCallerException(str, (java.lang.String) pair.second);
                        }
                    } else {
                        android.util.ArraySet<java.lang.String> arraySet2 = this.mBugreportFiles.get(pair);
                        if (arraySet2 != null && arraySet2.contains(str)) {
                            arraySet2.remove(str);
                            if (arraySet2.isEmpty()) {
                                this.mBugreportFiles.remove(pair);
                            }
                        } else {
                            throwInvalidBugreportFileForCallerException(str, (java.lang.String) pair.second);
                        }
                    }
                } finally {
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ java.lang.Integer lambda$ensureCallerPreviouslyGeneratedFile$0(android.content.pm.PackageManager packageManager, android.util.Pair pair, int i, java.lang.String str) throws java.lang.Exception {
            try {
                return java.lang.Integer.valueOf(packageManager.getPackageUidAsUser((java.lang.String) pair.second, i));
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                throwInvalidBugreportFileForCallerException(str, (java.lang.String) pair.second);
                return -1;
            }
        }

        private static void throwInvalidBugreportFileForCallerException(java.lang.String str, java.lang.String str2) {
            throw new java.lang.IllegalArgumentException("File " + str + " was not generated on behalf of calling package " + str2);
        }

        void addBugreportFileForCaller(android.util.Pair<java.lang.Integer, java.lang.String> pair, java.lang.String str, boolean z) {
            addBugreportMapping(pair, str);
            synchronized (this.mLock) {
                try {
                    if (android.app.admin.flags.Flags.onboardingBugreportV2Enabled()) {
                        if (z) {
                            this.mBugreportFilesToPersist.add(str);
                        }
                        writeBugreportDataLocked();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private void addBugreportMapping(android.util.Pair<java.lang.Integer, java.lang.String> pair, java.lang.String str) {
            synchronized (this.mLock) {
                try {
                    if (!this.mBugreportFiles.containsKey(pair)) {
                        this.mBugreportFiles.put(pair, new android.util.ArraySet<>());
                    }
                    this.mBugreportFiles.get(pair).add(str);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @com.android.internal.annotations.GuardedBy({"mLock"})
        private void readBugreportMappingLocked() {
            this.mBugreportFiles = new android.util.ArrayMap<>();
            try {
                try {
                    java.io.FileInputStream openRead = this.mMappingFile.openRead();
                    try {
                        com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(openRead);
                        com.android.internal.util.XmlUtils.beginDocument(resolvePullParser, com.android.server.os.BugreportManagerServiceImpl.TAG_BUGREPORT_DATA);
                        int depth = resolvePullParser.getDepth();
                        while (true) {
                            char c = 1;
                            if (com.android.internal.util.XmlUtils.nextElementWithin(resolvePullParser, depth)) {
                                java.lang.String name = resolvePullParser.getName();
                                switch (name.hashCode()) {
                                    case -1731556110:
                                        if (name.equals(com.android.server.os.BugreportManagerServiceImpl.TAG_PERSISTENT_BUGREPORT)) {
                                            break;
                                        }
                                        c = 65535;
                                        break;
                                    case 761901751:
                                        if (name.equals(com.android.server.os.BugreportManagerServiceImpl.TAG_BUGREPORT_MAP)) {
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
                                        readBugreportMapEntry(resolvePullParser);
                                        break;
                                    case 1:
                                        readPersistentBugreportEntry(resolvePullParser);
                                        break;
                                    default:
                                        android.util.Slog.e(com.android.server.os.BugreportManagerServiceImpl.TAG, "Unknown tag while reading bugreport mapping file: " + name);
                                        break;
                                }
                            } else {
                                this.mReadBugreportMapping = true;
                                if (openRead != null) {
                                    openRead.close();
                                    return;
                                }
                                return;
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        if (openRead != null) {
                            try {
                                openRead.close();
                            } catch (java.lang.Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
                    this.mMappingFile.delete();
                }
            } catch (java.io.FileNotFoundException e2) {
                android.util.Slog.i(com.android.server.os.BugreportManagerServiceImpl.TAG, "Bugreport mapping file does not exist");
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private void writeBugreportDataLocked() {
            if (this.mBugreportFiles.isEmpty() && this.mBugreportFilesToPersist.isEmpty()) {
                return;
            }
            try {
                java.io.FileOutputStream startWrite = this.mMappingFile.startWrite();
                try {
                    com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(startWrite);
                    resolveSerializer.startDocument((java.lang.String) null, true);
                    resolveSerializer.startTag((java.lang.String) null, com.android.server.os.BugreportManagerServiceImpl.TAG_BUGREPORT_DATA);
                    for (java.util.Map.Entry<android.util.Pair<java.lang.Integer, java.lang.String>, android.util.ArraySet<java.lang.String>> entry : this.mBugreportFiles.entrySet()) {
                        android.util.Pair<java.lang.Integer, java.lang.String> key = entry.getKey();
                        java.util.Iterator<java.lang.String> it = entry.getValue().iterator();
                        while (it.hasNext()) {
                            writeBugreportMapEntry(key, it.next(), resolveSerializer);
                        }
                    }
                    java.util.Iterator<java.lang.String> it2 = this.mBugreportFilesToPersist.iterator();
                    while (it2.hasNext()) {
                        writePersistentBugreportEntry(it2.next(), resolveSerializer);
                    }
                    resolveSerializer.endTag((java.lang.String) null, com.android.server.os.BugreportManagerServiceImpl.TAG_BUGREPORT_DATA);
                    resolveSerializer.endDocument();
                    this.mMappingFile.finishWrite(startWrite);
                    if (startWrite != null) {
                        startWrite.close();
                    }
                } finally {
                }
            } catch (java.io.IOException e) {
                android.util.Slog.e(com.android.server.os.BugreportManagerServiceImpl.TAG, "Failed to write bugreport mapping file", e);
            }
        }

        private void readBugreportMapEntry(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException {
            int attributeInt = typedXmlPullParser.getAttributeInt((java.lang.String) null, com.android.server.os.BugreportManagerServiceImpl.ATTR_CALLING_UID);
            java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, com.android.server.os.BugreportManagerServiceImpl.ATTR_CALLING_PACKAGE);
            addBugreportMapping(new android.util.Pair<>(java.lang.Integer.valueOf(attributeInt), attributeValue), typedXmlPullParser.getAttributeValue((java.lang.String) null, com.android.server.os.BugreportManagerServiceImpl.ATTR_BUGREPORT_FILE));
        }

        private void readPersistentBugreportEntry(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException {
            java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, com.android.server.os.BugreportManagerServiceImpl.ATTR_BUGREPORT_FILE);
            synchronized (this.mLock) {
                this.mBugreportFilesToPersist.add(attributeValue);
            }
        }

        private void writeBugreportMapEntry(android.util.Pair<java.lang.Integer, java.lang.String> pair, java.lang.String str, com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
            typedXmlSerializer.startTag((java.lang.String) null, com.android.server.os.BugreportManagerServiceImpl.TAG_BUGREPORT_MAP);
            typedXmlSerializer.attributeInt((java.lang.String) null, com.android.server.os.BugreportManagerServiceImpl.ATTR_CALLING_UID, ((java.lang.Integer) pair.first).intValue());
            typedXmlSerializer.attribute((java.lang.String) null, com.android.server.os.BugreportManagerServiceImpl.ATTR_CALLING_PACKAGE, (java.lang.String) pair.second);
            typedXmlSerializer.attribute((java.lang.String) null, com.android.server.os.BugreportManagerServiceImpl.ATTR_BUGREPORT_FILE, str);
            typedXmlSerializer.endTag((java.lang.String) null, com.android.server.os.BugreportManagerServiceImpl.TAG_BUGREPORT_MAP);
        }

        private void writePersistentBugreportEntry(java.lang.String str, com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
            typedXmlSerializer.startTag((java.lang.String) null, com.android.server.os.BugreportManagerServiceImpl.TAG_PERSISTENT_BUGREPORT);
            typedXmlSerializer.attribute((java.lang.String) null, com.android.server.os.BugreportManagerServiceImpl.ATTR_BUGREPORT_FILE, str);
            typedXmlSerializer.endTag((java.lang.String) null, com.android.server.os.BugreportManagerServiceImpl.TAG_PERSISTENT_BUGREPORT);
        }
    }

    static class Injector {
        android.util.ArraySet<java.lang.String> mAllowlistedPackages;
        android.content.Context mContext;
        android.util.AtomicFile mMappingFile;

        Injector(android.content.Context context, android.util.ArraySet<java.lang.String> arraySet, android.util.AtomicFile atomicFile) {
            this.mContext = context;
            this.mAllowlistedPackages = arraySet;
            this.mMappingFile = atomicFile;
        }

        android.content.Context getContext() {
            return this.mContext;
        }

        android.util.ArraySet<java.lang.String> getAllowlistedPackages() {
            return this.mAllowlistedPackages;
        }

        android.util.AtomicFile getMappingFile() {
            return this.mMappingFile;
        }

        android.os.UserManager getUserManager() {
            return (android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class);
        }

        android.app.admin.DevicePolicyManager getDevicePolicyManager() {
            return (android.app.admin.DevicePolicyManager) this.mContext.getSystemService(android.app.admin.DevicePolicyManager.class);
        }
    }

    BugreportManagerServiceImpl(android.content.Context context) {
        this(new com.android.server.os.BugreportManagerServiceImpl.Injector(context, com.android.server.SystemConfig.getInstance().getBugreportWhitelistedPackages(), new android.util.AtomicFile(new java.io.File(new java.io.File(android.os.Environment.getDataDirectory(), "system"), "bugreport-mapping.xml"))));
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    BugreportManagerServiceImpl(com.android.server.os.BugreportManagerServiceImpl.Injector injector) {
        this.mLock = new java.lang.Object();
        this.mPreDumpedDataUid = java.util.OptionalInt.empty();
        this.mFinishedBugreports = new android.util.LocalLog(20);
        this.mInjector = injector;
        this.mContext = injector.getContext();
        this.mAppOps = (android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class);
        this.mTelephonyManager = (android.telephony.TelephonyManager) this.mContext.getSystemService(android.telephony.TelephonyManager.class);
        this.mBugreportFileManager = new com.android.server.os.BugreportManagerServiceImpl.BugreportFileManager(injector.getMappingFile());
        this.mBugreportAllowlistedPackages = injector.getAllowlistedPackages();
    }

    @Override // android.os.IDumpstate
    @android.annotation.RequiresPermission("android.permission.DUMP")
    public void preDumpUiData(java.lang.String str) {
        enforcePermission(str, android.os.Binder.getCallingUid(), true);
        synchronized (this.mLock) {
            preDumpUiDataLocked(str);
        }
    }

    @Override // android.os.IDumpstate
    @android.annotation.RequiresPermission("android.permission.DUMP")
    public void startBugreport(int i, java.lang.String str, java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, int i2, int i3, android.os.IDumpstateListener iDumpstateListener, boolean z) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(fileDescriptor);
        java.util.Objects.requireNonNull(iDumpstateListener);
        validateBugreportMode(i2);
        validateBugreportFlags(i3);
        int callingUid = android.os.Binder.getCallingUid();
        enforcePermission(str, callingUid, i2 == 4);
        ensureUserCanTakeBugReport(i2);
        com.android.server.utils.Slogf.i(TAG, "Starting bugreport for %s / %d", str, java.lang.Integer.valueOf(callingUid));
        synchronized (this.mLock) {
            startBugreportLocked(callingUid, str, fileDescriptor, fileDescriptor2, i2, i3, iDumpstateListener, z);
        }
    }

    @Override // android.os.IDumpstate
    @android.annotation.RequiresPermission("android.permission.DUMP")
    public void cancelBugreport(int i, java.lang.String str) {
        int callingUid = android.os.Binder.getCallingUid();
        enforcePermission(str, callingUid, true);
        com.android.server.utils.Slogf.i(TAG, "Cancelling bugreport for %s / %d", str, java.lang.Integer.valueOf(callingUid));
        synchronized (this.mLock) {
            android.os.IDumpstate dumpstateBinderServiceLocked = getDumpstateBinderServiceLocked();
            if (dumpstateBinderServiceLocked == null) {
                android.util.Slog.w(TAG, "cancelBugreport: Could not find native dumpstate service");
                return;
            }
            try {
                dumpstateBinderServiceLocked.cancelBugreport(callingUid, str);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "RemoteException in cancelBugreport", e);
            }
            stopDumpstateBinderServiceLocked();
        }
    }

    @Override // android.os.IDumpstate
    @android.annotation.RequiresPermission(conditional = true, value = "android.permission.DUMP")
    public void retrieveBugreport(int i, java.lang.String str, int i2, java.io.FileDescriptor fileDescriptor, java.lang.String str2, boolean z, android.os.IDumpstateListener iDumpstateListener) {
        int callingUid = android.os.Binder.getCallingUid();
        boolean z2 = false;
        enforcePermission(str, callingUid, false);
        com.android.server.utils.Slogf.i(TAG, "Retrieving bugreport for %s / %d", str, java.lang.Integer.valueOf(callingUid));
        try {
            this.mBugreportFileManager.ensureCallerPreviouslyGeneratedFile(this.mContext, this.mContext.getPackageManager(), new android.util.Pair<>(java.lang.Integer.valueOf(callingUid), str), i2, str2, false);
            synchronized (this.mLock) {
                try {
                    if (isDumpstateBinderServiceRunningLocked()) {
                        android.util.Slog.w(TAG, "'dumpstate' is already running. Cannot retrieve a bugreport while another one is currently in progress.");
                        reportError(iDumpstateListener, 5);
                        return;
                    }
                    android.os.IDumpstate startAndGetDumpstateBinderServiceLocked = startAndGetDumpstateBinderServiceLocked();
                    if (startAndGetDumpstateBinderServiceLocked == null) {
                        android.util.Slog.w(TAG, "Unable to get bugreport service");
                        reportError(iDumpstateListener, 2);
                        return;
                    }
                    com.android.server.os.BugreportManagerServiceImpl.DumpstateListener dumpstateListener = new com.android.server.os.BugreportManagerServiceImpl.DumpstateListener(this, iDumpstateListener, startAndGetDumpstateBinderServiceLocked, new android.util.Pair(java.lang.Integer.valueOf(callingUid), str), true);
                    if (android.app.admin.flags.Flags.onboardingBugreportV2Enabled()) {
                        z2 = this.mBugreportFileManager.mBugreportFilesToPersist.contains(str2);
                    }
                    setCurrentDumpstateListenerLocked(dumpstateListener);
                    try {
                        startAndGetDumpstateBinderServiceLocked.retrieveBugreport(callingUid, str, i2, fileDescriptor, str2, z2, dumpstateListener);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(TAG, "RemoteException in retrieveBugreport", e);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        } catch (java.lang.IllegalArgumentException e2) {
            android.util.Slog.e(TAG, e2.getMessage());
            reportError(iDumpstateListener, 6);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void setCurrentDumpstateListenerLocked(com.android.server.os.BugreportManagerServiceImpl.DumpstateListener dumpstateListener) {
        if (this.mCurrentDumpstateListener != null) {
            com.android.server.utils.Slogf.w(TAG, "setCurrentDumpstateListenerLocked(%s): called when mCurrentDumpstateListener is already set (%s)", dumpstateListener, this.mCurrentDumpstateListener);
        }
        this.mCurrentDumpstateListener = dumpstateListener;
    }

    private void validateBugreportMode(int i) {
        if (i != 0 && i != 1 && i != 2 && i != 3 && i != 4 && i != 5 && i != 7) {
            android.util.Slog.w(TAG, "Unknown bugreport mode: " + i);
            throw new java.lang.IllegalArgumentException("Unknown bugreport mode: " + i);
        }
    }

    private void validateBugreportFlags(int i) {
        int clearBugreportFlag = clearBugreportFlag(i, 7);
        if (clearBugreportFlag != 0) {
            android.util.Slog.w(TAG, "Unknown bugreport flags: " + clearBugreportFlag);
            throw new java.lang.IllegalArgumentException("Unknown bugreport flags: " + clearBugreportFlag);
        }
    }

    private void enforcePermission(java.lang.String str, int i, boolean z) {
        long clearCallingIdentity;
        this.mAppOps.checkPackage(i, str);
        boolean contains = this.mBugreportAllowlistedPackages.contains(str);
        if (!contains) {
            clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                contains = ((android.app.role.RoleManager) this.mContext.getSystemService(android.app.role.RoleManager.class)).getRoleHolders(ROLE_SYSTEM_AUTOMOTIVE_PROJECTION).contains(str);
            } finally {
            }
        }
        if (contains && this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") == 0) {
            return;
        }
        clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        if (z) {
            try {
                if (this.mTelephonyManager.checkCarrierPrivilegesForPackageAnyPhone(str) == 1) {
                    return;
                }
            } finally {
            }
        }
        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(str);
        sb.append(" does not hold the DUMP permission or is not bugreport-whitelisted or does not have an allowed role ");
        sb.append(z ? "and does not have carrier privileges " : "");
        sb.append("to request a bugreport");
        java.lang.String sb2 = sb.toString();
        android.util.Slog.w(TAG, sb2);
        throw new java.lang.SecurityException(sb2);
    }

    private void ensureUserCanTakeBugReport(int i) {
        boolean isAdmin;
        int userId = android.os.UserHandle.getUserId(android.os.Binder.getCallingUid());
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.content.pm.UserInfo profileParent = this.mInjector.getUserManager().getProfileParent(userId);
            if (profileParent == null) {
                isAdmin = this.mInjector.getUserManager().isUserAdmin(userId);
            } else {
                userId = profileParent.id;
                isAdmin = profileParent.isAdmin();
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            if (!isAdmin) {
                if (i == 2 && isUserAffiliated(userId)) {
                    return;
                }
                logAndThrow(android.text.TextUtils.formatSimple("Calling user %s is not an admin user. Only admin users and their profiles are allowed to take bugreport.", new java.lang.Object[]{java.lang.Integer.valueOf(userId)}));
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    private boolean isUserAffiliated(int i) {
        android.app.admin.DevicePolicyManager devicePolicyManager = this.mInjector.getDevicePolicyManager();
        int deviceOwnerUserId = devicePolicyManager.getDeviceOwnerUserId();
        if (deviceOwnerUserId == -10000) {
            return false;
        }
        if (i != deviceOwnerUserId && !devicePolicyManager.isAffiliatedUser(i)) {
            logAndThrow("User " + i + " is not affiliated to the device owner.");
            return true;
        }
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void preDumpUiDataLocked(java.lang.String str) {
        this.mPreDumpedDataUid = java.util.OptionalInt.empty();
        if (isDumpstateBinderServiceRunningLocked()) {
            android.util.Slog.e(TAG, "'dumpstate' is already running. Cannot pre-dump data while another operation is currently in progress.");
            return;
        }
        android.os.IDumpstate startAndGetDumpstateBinderServiceLocked = startAndGetDumpstateBinderServiceLocked();
        if (startAndGetDumpstateBinderServiceLocked == null) {
            android.util.Slog.e(TAG, "Unable to get bugreport service");
            return;
        }
        try {
            startAndGetDumpstateBinderServiceLocked.preDumpUiData(str);
            stopDumpstateBinderServiceLocked();
            this.mPreDumpedDataUid = java.util.OptionalInt.of(android.os.Binder.getCallingUid());
        } catch (android.os.RemoteException e) {
            stopDumpstateBinderServiceLocked();
        } catch (java.lang.Throwable th) {
            stopDumpstateBinderServiceLocked();
            throw th;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void startBugreportLocked(int i, java.lang.String str, java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, int i2, int i3, android.os.IDumpstateListener iDumpstateListener, boolean z) {
        int i4 = i3;
        if (isDumpstateBinderServiceRunningLocked()) {
            android.util.Slog.w(TAG, "'dumpstate' is already running. Cannot start a new bugreport while another operation is currently in progress.");
            reportError(iDumpstateListener, 5);
            return;
        }
        if ((i4 & 1) != 0) {
            if (this.mPreDumpedDataUid.isEmpty()) {
                i4 = clearBugreportFlag(i4, 1);
                android.util.Slog.w(TAG, "Ignoring BUGREPORT_FLAG_USE_PREDUMPED_UI_DATA. No pre-dumped data is available.");
            } else if (this.mPreDumpedDataUid.getAsInt() != i) {
                i4 = clearBugreportFlag(i4, 1);
                android.util.Slog.w(TAG, "Ignoring BUGREPORT_FLAG_USE_PREDUMPED_UI_DATA. Data was pre-dumped by a different UID.");
            }
        }
        boolean z2 = (i4 & 2) != 0;
        boolean z3 = (i4 & 4) != 0;
        android.os.IDumpstate startAndGetDumpstateBinderServiceLocked = startAndGetDumpstateBinderServiceLocked();
        if (startAndGetDumpstateBinderServiceLocked == null) {
            android.util.Slog.w(TAG, "Unable to get bugreport service");
            reportError(iDumpstateListener, 2);
            return;
        }
        com.android.server.os.BugreportManagerServiceImpl.DumpstateListener dumpstateListener = new com.android.server.os.BugreportManagerServiceImpl.DumpstateListener(iDumpstateListener, startAndGetDumpstateBinderServiceLocked, new android.util.Pair(java.lang.Integer.valueOf(i), str), z2, z3);
        setCurrentDumpstateListenerLocked(dumpstateListener);
        try {
            startAndGetDumpstateBinderServiceLocked.startBugreport(i, str, fileDescriptor, fileDescriptor2, i2, i4, dumpstateListener, z);
        } catch (android.os.RemoteException e) {
            cancelBugreport(i, str);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isDumpstateBinderServiceRunningLocked() {
        return getDumpstateBinderServiceLocked() != null;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.os.IDumpstate getDumpstateBinderServiceLocked() {
        return android.os.IDumpstate.Stub.asInterface(android.os.ServiceManager.getService("dumpstate"));
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.os.IDumpstate startAndGetDumpstateBinderServiceLocked() {
        android.os.SystemProperties.set("ctl.start", BUGREPORT_SERVICE);
        android.os.IDumpstate iDumpstate = null;
        int i = 500;
        boolean z = false;
        int i2 = 0;
        while (true) {
            if (z) {
                break;
            }
            iDumpstate = getDumpstateBinderServiceLocked();
            if (iDumpstate != null) {
                android.util.Slog.i(TAG, "Got bugreport service handle.");
                break;
            }
            android.os.SystemClock.sleep(i);
            android.util.Slog.i(TAG, "Waiting to get dumpstate service handle (" + i2 + "ms)");
            i2 += i;
            i *= 2;
            z = ((long) i2) > 30000;
        }
        if (z) {
            android.util.Slog.w(TAG, "Timed out waiting to get dumpstate service handle (" + i2 + "ms)");
        }
        return iDumpstate;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void stopDumpstateBinderServiceLocked() {
        android.os.SystemProperties.set("ctl.stop", BUGREPORT_SERVICE);
    }

    @Override // android.os.Binder
    @android.annotation.RequiresPermission("android.permission.DUMP")
    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, TAG, printWriter)) {
            printWriter.printf("Allow-listed packages: %s\n", this.mBugreportAllowlistedPackages);
            synchronized (this.mLock) {
                try {
                    printWriter.print("Pre-dumped data UID: ");
                    if (this.mPreDumpedDataUid.isEmpty()) {
                        printWriter.println("none");
                    } else {
                        printWriter.println(this.mPreDumpedDataUid.getAsInt());
                    }
                    if (this.mCurrentDumpstateListener == null) {
                        printWriter.println("Not taking a bug report");
                    } else {
                        this.mCurrentDumpstateListener.dump(printWriter);
                    }
                    if (this.mNumberFinishedBugreports == 0) {
                        printWriter.println("No finished bugreports");
                    } else {
                        printWriter.printf("%d finished bugreport%s. Last %d:\n", java.lang.Integer.valueOf(this.mNumberFinishedBugreports), this.mNumberFinishedBugreports > 1 ? "s" : "", java.lang.Integer.valueOf(java.lang.Math.min(this.mNumberFinishedBugreports, 20)));
                        this.mFinishedBugreports.dump("  ", printWriter);
                    }
                } finally {
                }
            }
            synchronized (this.mBugreportFileManager.mLock) {
                try {
                    if (!this.mBugreportFileManager.mReadBugreportMapping) {
                        printWriter.println("Has not read bugreport mapping");
                    }
                    int size = this.mBugreportFileManager.mBugreportFiles.size();
                    printWriter.printf("%d pending file%s", java.lang.Integer.valueOf(size), size > 1 ? "s" : "");
                    if (size > 0) {
                        for (int i = 0; i < size; i++) {
                            printWriter.printf("  %s: %s\n", callerToString((android.util.Pair) this.mBugreportFileManager.mBugreportFiles.keyAt(i)), (android.util.ArraySet) this.mBugreportFileManager.mBugreportFiles.valueAt(i));
                        }
                    } else {
                        printWriter.println();
                    }
                } finally {
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String callerToString(@android.annotation.Nullable android.util.Pair<java.lang.Integer, java.lang.String> pair) {
        if (pair == null) {
            return "N/A";
        }
        return ((java.lang.String) pair.second) + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + pair.first;
    }

    private int clearBugreportFlag(int i, int i2) {
        return i & (~i2);
    }

    private void reportError(android.os.IDumpstateListener iDumpstateListener, int i) {
        try {
            iDumpstateListener.onError(i);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "onError() transaction threw RemoteException: " + e.getMessage());
        }
    }

    private void logAndThrow(java.lang.String str) {
        android.util.Slog.w(TAG, str);
        throw new java.lang.IllegalArgumentException(str);
    }

    private final class DumpstateListener extends android.os.IDumpstateListener.Stub implements android.os.IBinder.DeathRecipient {
        private static int sNextId;
        private final android.util.Pair<java.lang.Integer, java.lang.String> mCaller;
        private boolean mDone;
        private final android.os.IDumpstate mDs;
        private final int mId;
        private boolean mKeepBugreportOnRetrieval;
        private final android.os.IDumpstateListener mListener;
        private int mProgress;
        private final boolean mReportFinishedFile;

        DumpstateListener(com.android.server.os.BugreportManagerServiceImpl bugreportManagerServiceImpl, android.os.IDumpstateListener iDumpstateListener, android.os.IDumpstate iDumpstate, android.util.Pair<java.lang.Integer, java.lang.String> pair, boolean z) {
            this(iDumpstateListener, iDumpstate, pair, z, false);
        }

        DumpstateListener(android.os.IDumpstateListener iDumpstateListener, android.os.IDumpstate iDumpstate, android.util.Pair<java.lang.Integer, java.lang.String> pair, boolean z, boolean z2) {
            int i = sNextId + 1;
            sNextId = i;
            this.mId = i;
            this.mListener = iDumpstateListener;
            this.mDs = iDumpstate;
            this.mCaller = pair;
            this.mReportFinishedFile = z;
            this.mKeepBugreportOnRetrieval = z2;
            try {
                this.mDs.asBinder().linkToDeath(this, 0);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.os.BugreportManagerServiceImpl.TAG, "Unable to register Death Recipient for IDumpstate", e);
            }
        }

        @Override // android.os.IDumpstateListener
        public void onProgress(int i) throws android.os.RemoteException {
            this.mProgress = i;
            this.mListener.onProgress(i);
        }

        @Override // android.os.IDumpstateListener
        public void onError(int i) throws android.os.RemoteException {
            com.android.server.utils.Slogf.e(com.android.server.os.BugreportManagerServiceImpl.TAG, "onError(): %d", java.lang.Integer.valueOf(i));
            synchronized (com.android.server.os.BugreportManagerServiceImpl.this.mLock) {
                releaseItselfLocked();
                reportFinishedLocked("ErroCode: " + i);
            }
            this.mListener.onError(i);
        }

        @Override // android.os.IDumpstateListener
        public void onFinished(java.lang.String str) throws android.os.RemoteException {
            com.android.server.utils.Slogf.i(com.android.server.os.BugreportManagerServiceImpl.TAG, "onFinished(): %s", str);
            synchronized (com.android.server.os.BugreportManagerServiceImpl.this.mLock) {
                releaseItselfLocked();
                reportFinishedLocked("File: " + str);
            }
            if (this.mReportFinishedFile) {
                com.android.server.os.BugreportManagerServiceImpl.this.mBugreportFileManager.addBugreportFileForCaller(this.mCaller, str, this.mKeepBugreportOnRetrieval);
            }
            this.mListener.onFinished(str);
        }

        @Override // android.os.IDumpstateListener
        public void onScreenshotTaken(boolean z) throws android.os.RemoteException {
            this.mListener.onScreenshotTaken(z);
        }

        @Override // android.os.IDumpstateListener
        public void onUiIntensiveBugreportDumpsFinished() throws android.os.RemoteException {
            this.mListener.onUiIntensiveBugreportDumpsFinished();
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            try {
                java.lang.Thread.sleep(1000L);
            } catch (java.lang.InterruptedException e) {
            }
            synchronized (com.android.server.os.BugreportManagerServiceImpl.this.mLock) {
                if (!this.mDone) {
                    android.util.Slog.e(com.android.server.os.BugreportManagerServiceImpl.TAG, "IDumpstate likely crashed. Notifying listener");
                    try {
                        this.mListener.onError(2);
                    } catch (android.os.RemoteException e2) {
                    }
                }
            }
            this.mDs.asBinder().unlinkToDeath(this, 0);
        }

        public java.lang.String toString() {
            return "DumpstateListener[id=" + this.mId + ", progress=" + this.mProgress + "]";
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private void reportFinishedLocked(java.lang.String str) {
            com.android.server.os.BugreportManagerServiceImpl.this.mNumberFinishedBugreports++;
            com.android.server.os.BugreportManagerServiceImpl.this.mFinishedBugreports.log("Caller: " + com.android.server.os.BugreportManagerServiceImpl.callerToString(this.mCaller) + " " + str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dump(java.io.PrintWriter printWriter) {
            printWriter.println("DumpstateListener:");
            printWriter.printf("  id: %d\n", java.lang.Integer.valueOf(this.mId));
            printWriter.printf("  caller: %s\n", com.android.server.os.BugreportManagerServiceImpl.callerToString(this.mCaller));
            printWriter.printf("  reports finished file: %b\n", java.lang.Boolean.valueOf(this.mReportFinishedFile));
            printWriter.printf("  progress: %d\n", java.lang.Integer.valueOf(this.mProgress));
            printWriter.printf("  done: %b\n", java.lang.Boolean.valueOf(this.mDone));
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private void releaseItselfLocked() {
            this.mDone = true;
            if (com.android.server.os.BugreportManagerServiceImpl.this.mCurrentDumpstateListener == this) {
                com.android.server.os.BugreportManagerServiceImpl.this.mCurrentDumpstateListener = null;
                return;
            }
            com.android.server.utils.Slogf.w(com.android.server.os.BugreportManagerServiceImpl.TAG, "releaseItselfLocked(): " + this + " is finished, but current listener is " + com.android.server.os.BugreportManagerServiceImpl.this.mCurrentDumpstateListener);
        }
    }
}
