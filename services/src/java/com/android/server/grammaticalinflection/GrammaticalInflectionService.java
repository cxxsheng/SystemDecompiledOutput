package com.android.server.grammaticalinflection;

/* loaded from: classes.dex */
public class GrammaticalInflectionService extends com.android.server.SystemService {
    private static final java.lang.String ATTR_NAME = "grammatical_gender";
    private static final java.lang.String GRAMMATICAL_GENDER_PROPERTY = "persist.sys.grammatical_gender";
    private static final java.lang.String GRAMMATICAL_INFLECTION_ENABLED = "i18n.grammatical_Inflection.enabled";
    private static final java.lang.String TAG = "GrammaticalInflection";
    private static final java.lang.String TAG_GRAMMATICAL_INFLECTION = "grammatical_inflection";
    private static final java.lang.String USER_SETTINGS_FILE_NAME = "user_settings.xml";
    private final com.android.server.wm.ActivityTaskManagerInternal mActivityTaskManagerInternal;
    private final com.android.server.grammaticalinflection.GrammaticalInflectionBackupHelper mBackupHelper;
    private com.android.server.grammaticalinflection.GrammaticalInflectionService.GrammaticalInflectionBinderService mBinderService;
    private android.content.Context mContext;
    private final android.util.SparseIntArray mGrammaticalGenderCache;
    private final java.lang.Object mLock;
    private android.content.pm.PackageManagerInternal mPackageManagerInternal;
    private android.permission.PermissionManager mPermissionManager;

    public GrammaticalInflectionService(android.content.Context context) {
        super(context);
        this.mLock = new java.lang.Object();
        this.mGrammaticalGenderCache = new android.util.SparseIntArray();
        this.mContext = context;
        this.mActivityTaskManagerInternal = (com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class);
        this.mPackageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        this.mBackupHelper = new com.android.server.grammaticalinflection.GrammaticalInflectionBackupHelper(this, context.getPackageManager());
        this.mBinderService = new com.android.server.grammaticalinflection.GrammaticalInflectionService.GrammaticalInflectionBinderService();
        this.mPermissionManager = (android.permission.PermissionManager) context.getSystemService(android.permission.PermissionManager.class);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService(TAG_GRAMMATICAL_INFLECTION, this.mBinderService);
        com.android.server.LocalServices.addService(com.android.server.grammaticalinflection.GrammaticalInflectionManagerInternal.class, new com.android.server.grammaticalinflection.GrammaticalInflectionService.GrammaticalInflectionManagerInternalImpl());
    }

    private final class GrammaticalInflectionBinderService extends android.app.IGrammaticalInflectionManager.Stub {
        private GrammaticalInflectionBinderService() {
        }

        public void setRequestedApplicationGrammaticalGender(java.lang.String str, int i, int i2) {
            com.android.server.grammaticalinflection.GrammaticalInflectionService.this.setRequestedApplicationGrammaticalGender(str, i, i2);
        }

        public void setSystemWideGrammaticalGender(int i, int i2) {
            com.android.server.grammaticalinflection.GrammaticalInflectionService.this.checkCallerIsSystem();
            com.android.server.grammaticalinflection.GrammaticalInflectionService.this.setSystemWideGrammaticalGender(i, i2);
        }

        public int getSystemGrammaticalGender(android.content.AttributionSource attributionSource, int i) {
            if (com.android.server.grammaticalinflection.GrammaticalInflectionService.this.canGetSystemGrammaticalGender(attributionSource)) {
                return com.android.server.grammaticalinflection.GrammaticalInflectionService.this.getSystemGrammaticalGender(attributionSource, i);
            }
            return 0;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
            new com.android.server.grammaticalinflection.GrammaticalInflectionShellCommand(com.android.server.grammaticalinflection.GrammaticalInflectionService.this.mBinderService, com.android.server.grammaticalinflection.GrammaticalInflectionService.this.mContext.getAttributionSource()).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }
    }

    private final class GrammaticalInflectionManagerInternalImpl extends com.android.server.grammaticalinflection.GrammaticalInflectionManagerInternal {
        private GrammaticalInflectionManagerInternalImpl() {
        }

        @Override // com.android.server.grammaticalinflection.GrammaticalInflectionManagerInternal
        @android.annotation.Nullable
        public byte[] getBackupPayload(int i) {
            com.android.server.grammaticalinflection.GrammaticalInflectionService.this.checkCallerIsSystem();
            return com.android.server.grammaticalinflection.GrammaticalInflectionService.this.mBackupHelper.getBackupPayload(i);
        }

        @Override // com.android.server.grammaticalinflection.GrammaticalInflectionManagerInternal
        public void stageAndApplyRestoredPayload(byte[] bArr, int i) {
            com.android.server.grammaticalinflection.GrammaticalInflectionService.this.mBackupHelper.stageAndApplyRestoredPayload(bArr, i);
        }

        @Override // com.android.server.grammaticalinflection.GrammaticalInflectionManagerInternal
        public int getSystemGrammaticalGender(int i) {
            if (com.android.server.grammaticalinflection.GrammaticalInflectionService.this.checkSystemTermsOfAddressIsEnabled()) {
                return com.android.server.grammaticalinflection.GrammaticalInflectionService.this.getSystemGrammaticalGender(com.android.server.grammaticalinflection.GrammaticalInflectionService.this.mContext.getAttributionSource(), i);
            }
            return 0;
        }

        @Override // com.android.server.grammaticalinflection.GrammaticalInflectionManagerInternal
        public int retrieveSystemGrammaticalGender(android.content.res.Configuration configuration) {
            int systemGrammaticalGender = getSystemGrammaticalGender(com.android.server.grammaticalinflection.GrammaticalInflectionService.this.mContext.getUserId());
            if (configuration.getGrammaticalGenderRaw() == -1 || systemGrammaticalGender <= 0) {
                return android.os.SystemProperties.getInt(com.android.server.grammaticalinflection.GrammaticalInflectionService.GRAMMATICAL_GENDER_PROPERTY, -1);
            }
            return systemGrammaticalGender;
        }

        @Override // com.android.server.grammaticalinflection.GrammaticalInflectionManagerInternal
        public boolean canGetSystemGrammaticalGender(int i, java.lang.String str) {
            return com.android.server.grammaticalinflection.GrammaticalInflectionService.this.canGetSystemGrammaticalGender(new android.content.AttributionSource.Builder(i).setPackageName(str).build());
        }
    }

    protected int getApplicationGrammaticalGender(java.lang.String str, int i) {
        com.android.server.wm.ActivityTaskManagerInternal.PackageConfig applicationConfig = this.mActivityTaskManagerInternal.getApplicationConfig(str, i);
        if (applicationConfig == null || applicationConfig.mGrammaticalGender == null) {
            return 0;
        }
        return applicationConfig.mGrammaticalGender.intValue();
    }

    protected void setRequestedApplicationGrammaticalGender(java.lang.String str, int i, int i2) {
        int applicationGrammaticalGender = getApplicationGrammaticalGender(str, i);
        com.android.server.wm.ActivityTaskManagerInternal.PackageConfigurationUpdater createPackageConfigurationUpdater = this.mActivityTaskManagerInternal.createPackageConfigurationUpdater(str, i);
        if (!android.os.SystemProperties.getBoolean(GRAMMATICAL_INFLECTION_ENABLED, true)) {
            if (applicationGrammaticalGender != 0) {
                android.util.Log.d(TAG, "Clearing the user's grammatical gender setting");
                createPackageConfigurationUpdater.setGrammaticalGender(0).commit();
                return;
            }
            return;
        }
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.APPLICATION_GRAMMATICAL_INFLECTION_CHANGED, 2, this.mPackageManagerInternal.getPackageUid(str, 0L, i), i2 != 0, applicationGrammaticalGender != 0);
        createPackageConfigurationUpdater.setGrammaticalGender(i2).commit();
    }

    protected void setSystemWideGrammaticalGender(int i, int i2) {
        java.io.FileOutputStream fileOutputStream;
        android.os.Trace.beginSection("GrammaticalInflectionService.setSystemWideGrammaticalGender");
        if (!android.app.GrammaticalInflectionManager.VALID_GRAMMATICAL_GENDER_VALUES.contains(java.lang.Integer.valueOf(i))) {
            throw new java.lang.IllegalArgumentException("Unknown grammatical gender");
        }
        if (!checkSystemTermsOfAddressIsEnabled()) {
            if (i == 0) {
                return;
            }
            android.util.Log.d(TAG, "Clearing the system grammatical gender setting");
            i = 0;
        }
        synchronized (this.mLock) {
            android.util.AtomicFile atomicFile = new android.util.AtomicFile(getGrammaticalGenderFile(i2));
            try {
                fileOutputStream = atomicFile.startWrite();
                try {
                    fileOutputStream.write(toXmlByteArray(i, fileOutputStream));
                    atomicFile.finishWrite(fileOutputStream);
                    this.mGrammaticalGenderCache.put(i2, i);
                } catch (java.io.IOException e) {
                    e = e;
                    android.util.Log.e(TAG, "Failed to write file " + atomicFile, e);
                    if (fileOutputStream != null) {
                        atomicFile.failWrite(fileOutputStream);
                    }
                    throw new java.lang.RuntimeException(e);
                }
            } catch (java.io.IOException e2) {
                e = e2;
                fileOutputStream = null;
            }
        }
        try {
            android.content.res.Configuration configuration = new android.content.res.Configuration();
            int grammaticalGender = configuration.getGrammaticalGender();
            configuration.setGrammaticalGender(i);
            android.app.ActivityTaskManager.getService().updateConfiguration(configuration);
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.SYSTEM_GRAMMATICAL_INFLECTION_CHANGED, 1, i2, i != 0, grammaticalGender != 0);
        } catch (android.os.RemoteException e3) {
            android.util.Log.w(TAG, "Can not update configuration", e3);
        }
        android.os.Trace.endSection();
    }

    public int getSystemGrammaticalGender(android.content.AttributionSource attributionSource, int i) {
        int i2 = 0;
        if (attributionSource.getPackageName() == null) {
            android.util.Log.d(TAG, "Package name is null.");
            return 0;
        }
        synchronized (this.mLock) {
            int i3 = this.mGrammaticalGenderCache.get(i);
            if (i3 >= 0) {
                i2 = i3;
            }
        }
        return i2;
    }

    private java.io.File getGrammaticalGenderFile(int i) {
        return new java.io.File(new java.io.File(android.os.Environment.getDataSystemCeDirectory(i), TAG_GRAMMATICAL_INFLECTION), USER_SETTINGS_FILE_NAME);
    }

    private byte[] toXmlByteArray(int i, java.io.FileOutputStream fileOutputStream) {
        try {
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
            com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(fileOutputStream);
            resolveSerializer.setOutput(byteArrayOutputStream, java.nio.charset.StandardCharsets.UTF_8.name());
            resolveSerializer.startDocument((java.lang.String) null, true);
            resolveSerializer.startTag((java.lang.String) null, TAG_GRAMMATICAL_INFLECTION);
            resolveSerializer.attributeInt((java.lang.String) null, ATTR_NAME, i);
            resolveSerializer.endTag((java.lang.String) null, TAG_GRAMMATICAL_INFLECTION);
            resolveSerializer.endDocument();
            return byteArrayOutputStream.toByteArray();
        } catch (java.io.IOException e) {
            return null;
        }
    }

    private int getGrammaticalGenderFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        com.android.internal.util.XmlUtils.nextElement(typedXmlPullParser);
        while (typedXmlPullParser.getEventType() != 1) {
            if (TAG_GRAMMATICAL_INFLECTION.equals(typedXmlPullParser.getName())) {
                return typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_NAME);
            }
            com.android.internal.util.XmlUtils.nextElement(typedXmlPullParser);
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkCallerIsSystem() {
        int callingUid = android.os.Binder.getCallingUid();
        if (callingUid != 1000 && callingUid != 2000 && callingUid != 0) {
            throw new java.lang.SecurityException("Caller is not system, shell and root.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkSystemTermsOfAddressIsEnabled() {
        if (!android.app.Flags.systemTermsOfAddressEnabled()) {
            android.util.Log.d(TAG, "The flag must be enabled to allow calling the API.");
            return false;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean canGetSystemGrammaticalGender(android.content.AttributionSource attributionSource) {
        return checkSystemTermsOfAddressIsEnabled() && com.android.server.grammaticalinflection.GrammaticalInflectionUtils.checkSystemGrammaticalGenderPermission(this.mPermissionManager, attributionSource);
    }

    @Override // com.android.server.SystemService
    public void onUserUnlocked(final com.android.server.SystemService.TargetUser targetUser) {
        com.android.server.IoThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.grammaticalinflection.GrammaticalInflectionService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.grammaticalinflection.GrammaticalInflectionService.this.lambda$onUserUnlocked$0(targetUser);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onUserUnlocked$0(com.android.server.SystemService.TargetUser targetUser) {
        int userIdentifier = targetUser.getUserIdentifier();
        java.io.File grammaticalGenderFile = getGrammaticalGenderFile(userIdentifier);
        synchronized (this.mLock) {
            try {
                if (!grammaticalGenderFile.exists()) {
                    android.util.Log.d(TAG, "User " + userIdentifier + " doesn't have the grammatical gender file.");
                    return;
                }
                if (this.mGrammaticalGenderCache.indexOfKey(userIdentifier) < 0) {
                    try {
                        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(grammaticalGenderFile);
                        try {
                            this.mGrammaticalGenderCache.put(userIdentifier, getGrammaticalGenderFromXml(android.util.Xml.resolvePullParser(fileInputStream)));
                            fileInputStream.close();
                        } catch (java.lang.Throwable th) {
                            try {
                                fileInputStream.close();
                            } catch (java.lang.Throwable th2) {
                                th.addSuppressed(th2);
                            }
                            throw th;
                        }
                    } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
                        android.util.Log.e(TAG, "Failed to parse XML configuration from " + grammaticalGenderFile, e);
                    }
                }
            } catch (java.lang.Throwable th3) {
                throw th3;
            }
        }
    }
}
