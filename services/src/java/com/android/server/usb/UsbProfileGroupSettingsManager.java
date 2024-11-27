package com.android.server.usb;

/* loaded from: classes2.dex */
public class UsbProfileGroupSettingsManager {
    private static final boolean DEBUG = false;
    private static final int DUMPSYS_LOG_BUFFER = 200;
    public static final java.lang.String PROPERTY_RESTRICT_USB_OVERLAY_ACTIVITIES = "android.app.PROPERTY_RESTRICT_USB_OVERLAY_ACTIVITIES";
    private static com.android.server.utils.EventLogger sEventLogger;
    private final android.app.ActivityManager mActivityManager;
    private final android.content.Context mContext;
    private final boolean mDisablePermissionDialogs;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mIsWriteSettingsScheduled;
    private final com.android.server.usb.MtpNotificationManager mMtpNotificationManager;
    private final android.content.pm.PackageManager mPackageManager;
    private final android.os.UserHandle mParentUser;
    private final android.util.AtomicFile mSettingsFile;

    @android.annotation.NonNull
    private final com.android.server.usb.UsbSettingsManager mSettingsManager;
    private final com.android.server.usb.UsbHandlerManager mUsbHandlerManager;
    private final android.os.UserManager mUserManager;
    private static final java.lang.String TAG = com.android.server.usb.UsbProfileGroupSettingsManager.class.getSimpleName();
    private static final java.io.File sSingleUserSettingsFile = new java.io.File("/data/system/usb_device_manager.xml");

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.HashMap<android.hardware.usb.DeviceFilter, com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage> mDevicePreferenceMap = new java.util.HashMap<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<android.hardware.usb.DeviceFilter, android.util.ArraySet<com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage>> mDevicePreferenceDeniedMap = new android.util.ArrayMap<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.HashMap<android.hardware.usb.AccessoryFilter, com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage> mAccessoryPreferenceMap = new java.util.HashMap<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<android.hardware.usb.AccessoryFilter, android.util.ArraySet<com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage>> mAccessoryPreferenceDeniedMap = new android.util.ArrayMap<>();
    private final java.lang.Object mLock = new java.lang.Object();
    com.android.server.usb.UsbProfileGroupSettingsManager.MyPackageMonitor mPackageMonitor = new com.android.server.usb.UsbProfileGroupSettingsManager.MyPackageMonitor();

    @com.android.internal.annotations.Immutable
    private static class UserPackage {

        @android.annotation.NonNull
        final java.lang.String packageName;

        @android.annotation.NonNull
        final android.os.UserHandle user;

        private UserPackage(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.UserHandle userHandle) {
            this.packageName = str;
            this.user = userHandle;
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage)) {
                return false;
            }
            com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage userPackage = (com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage) obj;
            return this.user.equals(userPackage.user) && this.packageName.equals(userPackage.packageName);
        }

        public int hashCode() {
            return (this.user.hashCode() * 31) + this.packageName.hashCode();
        }

        public java.lang.String toString() {
            return this.user.getIdentifier() + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + this.packageName;
        }

        public void dump(com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream, java.lang.String str, long j) {
            long start = dualDumpOutputStream.start(str, j);
            dualDumpOutputStream.write("user_id", 1120986464257L, this.user.getIdentifier());
            dualDumpOutputStream.write("package_name", 1138166333442L, this.packageName);
            dualDumpOutputStream.end(start);
        }
    }

    private class MyPackageMonitor extends com.android.internal.content.PackageMonitor {
        private MyPackageMonitor() {
        }

        public void onPackageAdded(java.lang.String str, int i) {
            if (!com.android.server.usb.UsbProfileGroupSettingsManager.this.mUserManager.isSameProfileGroup(com.android.server.usb.UsbProfileGroupSettingsManager.this.mParentUser.getIdentifier(), android.os.UserHandle.getUserId(i))) {
                return;
            }
            com.android.server.usb.UsbProfileGroupSettingsManager.this.handlePackageAdded(new com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage(str, android.os.UserHandle.getUserHandleForUid(i)));
        }

        public void onPackageRemoved(java.lang.String str, int i) {
            if (!com.android.server.usb.UsbProfileGroupSettingsManager.this.mUserManager.isSameProfileGroup(com.android.server.usb.UsbProfileGroupSettingsManager.this.mParentUser.getIdentifier(), android.os.UserHandle.getUserId(i))) {
                return;
            }
            com.android.server.usb.UsbProfileGroupSettingsManager.this.clearDefaults(str, android.os.UserHandle.getUserHandleForUid(i));
        }
    }

    public UsbProfileGroupSettingsManager(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.UserHandle userHandle, @android.annotation.NonNull com.android.server.usb.UsbSettingsManager usbSettingsManager, @android.annotation.NonNull com.android.server.usb.UsbHandlerManager usbHandlerManager) {
        try {
            android.content.Context createPackageContextAsUser = context.createPackageContextAsUser(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, 0, userHandle);
            this.mContext = context;
            this.mPackageManager = context.getPackageManager();
            this.mActivityManager = (android.app.ActivityManager) context.getSystemService(android.app.ActivityManager.class);
            this.mSettingsManager = usbSettingsManager;
            this.mUserManager = (android.os.UserManager) context.getSystemService("user");
            this.mParentUser = userHandle;
            this.mSettingsFile = new android.util.AtomicFile(new java.io.File(android.os.Environment.getUserSystemDirectory(userHandle.getIdentifier()), "usb_device_manager.xml"), "usb-state");
            this.mDisablePermissionDialogs = context.getResources().getBoolean(android.R.bool.config_disableTransitionAnimation);
            synchronized (this.mLock) {
                try {
                    if (android.os.UserHandle.SYSTEM.equals(userHandle)) {
                        upgradeSingleUserLocked();
                    }
                    readSettingsLocked();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            this.mPackageMonitor.register(context, (android.os.Looper) null, android.os.UserHandle.ALL, true);
            this.mMtpNotificationManager = new com.android.server.usb.MtpNotificationManager(createPackageContextAsUser, new com.android.server.usb.MtpNotificationManager.OnOpenInAppListener() { // from class: com.android.server.usb.UsbProfileGroupSettingsManager$$ExternalSyntheticLambda1
                @Override // com.android.server.usb.MtpNotificationManager.OnOpenInAppListener
                public final void onOpenInApp(android.hardware.usb.UsbDevice usbDevice) {
                    com.android.server.usb.UsbProfileGroupSettingsManager.this.lambda$new$0(usbDevice);
                }
            });
            this.mUsbHandlerManager = usbHandlerManager;
            sEventLogger = new com.android.server.utils.EventLogger(200, "UsbProfileGroupSettingsManager activity");
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            throw new java.lang.RuntimeException("Missing android package");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(android.hardware.usb.UsbDevice usbDevice) {
        resolveActivity(createDeviceAttachedIntent(usbDevice), usbDevice, false);
    }

    public void unregisterReceivers() {
        this.mPackageMonitor.unregister();
        this.mMtpNotificationManager.unregister();
    }

    void removeUser(@android.annotation.NonNull android.os.UserHandle userHandle) {
        synchronized (this.mLock) {
            try {
                java.util.Iterator<java.util.Map.Entry<android.hardware.usb.DeviceFilter, com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage>> it = this.mDevicePreferenceMap.entrySet().iterator();
                boolean z = false;
                while (it.hasNext()) {
                    if (it.next().getValue().user.equals(userHandle)) {
                        it.remove();
                        z = true;
                    }
                }
                java.util.Iterator<java.util.Map.Entry<android.hardware.usb.AccessoryFilter, com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage>> it2 = this.mAccessoryPreferenceMap.entrySet().iterator();
                while (it2.hasNext()) {
                    if (it2.next().getValue().user.equals(userHandle)) {
                        it2.remove();
                        z = true;
                    }
                }
                int size = this.mDevicePreferenceDeniedMap.size();
                for (int i = 0; i < size; i++) {
                    android.util.ArraySet<com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage> valueAt = this.mDevicePreferenceDeniedMap.valueAt(i);
                    for (int size2 = valueAt.size() - 1; size2 >= 0; size2--) {
                        if (valueAt.valueAt(size2).user.equals(userHandle)) {
                            valueAt.removeAt(size2);
                            z = true;
                        }
                    }
                }
                int size3 = this.mAccessoryPreferenceDeniedMap.size();
                for (int i2 = 0; i2 < size3; i2++) {
                    android.util.ArraySet<com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage> valueAt2 = this.mAccessoryPreferenceDeniedMap.valueAt(i2);
                    for (int size4 = valueAt2.size() - 1; size4 >= 0; size4--) {
                        if (valueAt2.valueAt(size4).user.equals(userHandle)) {
                            valueAt2.removeAt(size4);
                            z = true;
                        }
                    }
                }
                if (z) {
                    scheduleWriteSettingsLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void readPreference(org.xmlpull.v1.XmlPullParser xmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        android.os.UserHandle userHandle = this.mParentUser;
        int attributeCount = xmlPullParser.getAttributeCount();
        java.lang.String str = null;
        for (int i = 0; i < attributeCount; i++) {
            if (com.android.server.pm.Settings.ATTR_PACKAGE.equals(xmlPullParser.getAttributeName(i))) {
                str = xmlPullParser.getAttributeValue(i);
            }
            if ("user".equals(xmlPullParser.getAttributeName(i))) {
                userHandle = this.mUserManager.getUserForSerialNumber(java.lang.Integer.parseInt(xmlPullParser.getAttributeValue(i)));
            }
        }
        com.android.internal.util.XmlUtils.nextElement(xmlPullParser);
        if ("usb-device".equals(xmlPullParser.getName())) {
            android.hardware.usb.DeviceFilter read = android.hardware.usb.DeviceFilter.read(xmlPullParser);
            if (userHandle != null) {
                this.mDevicePreferenceMap.put(read, new com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage(str, userHandle));
            }
        } else if ("usb-accessory".equals(xmlPullParser.getName())) {
            android.hardware.usb.AccessoryFilter read2 = android.hardware.usb.AccessoryFilter.read(xmlPullParser);
            if (userHandle != null) {
                this.mAccessoryPreferenceMap.put(read2, new com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage(str, userHandle));
            }
        }
        com.android.internal.util.XmlUtils.nextElement(xmlPullParser);
    }

    private void readPreferenceDeniedList(@android.annotation.NonNull org.xmlpull.v1.XmlPullParser xmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        int depth = xmlPullParser.getDepth();
        if (!com.android.internal.util.XmlUtils.nextElementWithin(xmlPullParser, depth)) {
            return;
        }
        if ("usb-device".equals(xmlPullParser.getName())) {
            android.hardware.usb.DeviceFilter read = android.hardware.usb.DeviceFilter.read(xmlPullParser);
            while (com.android.internal.util.XmlUtils.nextElementWithin(xmlPullParser, depth)) {
                if ("user-package".equals(xmlPullParser.getName())) {
                    try {
                        int readIntAttribute = com.android.internal.util.XmlUtils.readIntAttribute(xmlPullParser, "user");
                        java.lang.String readStringAttribute = com.android.internal.util.XmlUtils.readStringAttribute(xmlPullParser, com.android.server.pm.Settings.ATTR_PACKAGE);
                        if (readStringAttribute == null) {
                            android.util.Slog.e(TAG, "Unable to parse package name");
                        }
                        android.util.ArraySet<com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage> arraySet = this.mDevicePreferenceDeniedMap.get(read);
                        if (arraySet == null) {
                            arraySet = new android.util.ArraySet<>();
                            this.mDevicePreferenceDeniedMap.put(read, arraySet);
                        }
                        arraySet.add(new com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage(readStringAttribute, android.os.UserHandle.of(readIntAttribute)));
                    } catch (java.net.ProtocolException e) {
                        android.util.Slog.e(TAG, "Unable to parse user id", e);
                    }
                }
            }
        } else if ("usb-accessory".equals(xmlPullParser.getName())) {
            android.hardware.usb.AccessoryFilter read2 = android.hardware.usb.AccessoryFilter.read(xmlPullParser);
            while (com.android.internal.util.XmlUtils.nextElementWithin(xmlPullParser, depth)) {
                if ("user-package".equals(xmlPullParser.getName())) {
                    try {
                        int readIntAttribute2 = com.android.internal.util.XmlUtils.readIntAttribute(xmlPullParser, "user");
                        java.lang.String readStringAttribute2 = com.android.internal.util.XmlUtils.readStringAttribute(xmlPullParser, com.android.server.pm.Settings.ATTR_PACKAGE);
                        if (readStringAttribute2 == null) {
                            android.util.Slog.e(TAG, "Unable to parse package name");
                        }
                        android.util.ArraySet<com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage> arraySet2 = this.mAccessoryPreferenceDeniedMap.get(read2);
                        if (arraySet2 == null) {
                            arraySet2 = new android.util.ArraySet<>();
                            this.mAccessoryPreferenceDeniedMap.put(read2, arraySet2);
                        }
                        arraySet2.add(new com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage(readStringAttribute2, android.os.UserHandle.of(readIntAttribute2)));
                    } catch (java.net.ProtocolException e2) {
                        android.util.Slog.e(TAG, "Unable to parse user id", e2);
                    }
                }
            }
        }
        while (xmlPullParser.getDepth() > depth) {
            xmlPullParser.nextTag();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void upgradeSingleUserLocked() {
        java.lang.Throwable th;
        java.io.FileInputStream fileInputStream;
        java.lang.Throwable e;
        if (sSingleUserSettingsFile.exists()) {
            this.mDevicePreferenceMap.clear();
            this.mAccessoryPreferenceMap.clear();
            try {
                try {
                    fileInputStream = new java.io.FileInputStream(sSingleUserSettingsFile);
                } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e2) {
                    fileInputStream = null;
                    e = e2;
                } catch (java.lang.Throwable th2) {
                    th = th2;
                    libcore.io.IoUtils.closeQuietly((java.lang.AutoCloseable) null);
                    throw th;
                }
                try {
                    com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(fileInputStream);
                    com.android.internal.util.XmlUtils.nextElement(resolvePullParser);
                    while (resolvePullParser.getEventType() != 1) {
                        if ("preference".equals(resolvePullParser.getName())) {
                            readPreference(resolvePullParser);
                        } else {
                            com.android.internal.util.XmlUtils.nextElement(resolvePullParser);
                        }
                    }
                } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e3) {
                    e = e3;
                    android.util.Log.wtf(TAG, "Failed to read single-user settings", e);
                    libcore.io.IoUtils.closeQuietly(fileInputStream);
                    scheduleWriteSettingsLocked();
                    sSingleUserSettingsFile.delete();
                }
                libcore.io.IoUtils.closeQuietly(fileInputStream);
                scheduleWriteSettingsLocked();
                sSingleUserSettingsFile.delete();
            } catch (java.lang.Throwable th3) {
                th = th3;
                libcore.io.IoUtils.closeQuietly((java.lang.AutoCloseable) null);
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void readSettingsLocked() {
        this.mDevicePreferenceMap.clear();
        this.mAccessoryPreferenceMap.clear();
        java.io.FileInputStream fileInputStream = null;
        try {
            try {
                fileInputStream = this.mSettingsFile.openRead();
                com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(fileInputStream);
                com.android.internal.util.XmlUtils.nextElement(resolvePullParser);
                while (resolvePullParser.getEventType() != 1) {
                    java.lang.String name = resolvePullParser.getName();
                    if ("preference".equals(name)) {
                        readPreference(resolvePullParser);
                    } else if ("preference-denied-list".equals(name)) {
                        readPreferenceDeniedList(resolvePullParser);
                    } else {
                        com.android.internal.util.XmlUtils.nextElement(resolvePullParser);
                    }
                }
            } catch (java.io.FileNotFoundException e) {
            } catch (java.lang.Exception e2) {
                android.util.Slog.e(TAG, "error reading settings file, deleting to start fresh", e2);
                this.mSettingsFile.delete();
            }
            libcore.io.IoUtils.closeQuietly(fileInputStream);
        } catch (java.lang.Throwable th) {
            libcore.io.IoUtils.closeQuietly(fileInputStream);
            throw th;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void scheduleWriteSettingsLocked() {
        if (this.mIsWriteSettingsScheduled) {
            return;
        }
        this.mIsWriteSettingsScheduled = true;
        android.os.AsyncTask.execute(new java.lang.Runnable() { // from class: com.android.server.usb.UsbProfileGroupSettingsManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.usb.UsbProfileGroupSettingsManager.this.lambda$scheduleWriteSettingsLocked$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleWriteSettingsLocked$1() {
        java.io.FileOutputStream fileOutputStream;
        java.io.IOException e;
        synchronized (this.mLock) {
            try {
                try {
                    fileOutputStream = this.mSettingsFile.startWrite();
                } catch (java.io.IOException e2) {
                    fileOutputStream = null;
                    e = e2;
                }
                try {
                    com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(fileOutputStream);
                    resolveSerializer.startDocument((java.lang.String) null, true);
                    resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                    resolveSerializer.startTag((java.lang.String) null, "settings");
                    for (android.hardware.usb.DeviceFilter deviceFilter : this.mDevicePreferenceMap.keySet()) {
                        resolveSerializer.startTag((java.lang.String) null, "preference");
                        resolveSerializer.attribute((java.lang.String) null, com.android.server.pm.Settings.ATTR_PACKAGE, this.mDevicePreferenceMap.get(deviceFilter).packageName);
                        resolveSerializer.attribute((java.lang.String) null, "user", java.lang.String.valueOf(getSerial(this.mDevicePreferenceMap.get(deviceFilter).user)));
                        deviceFilter.write(resolveSerializer);
                        resolveSerializer.endTag((java.lang.String) null, "preference");
                    }
                    for (android.hardware.usb.AccessoryFilter accessoryFilter : this.mAccessoryPreferenceMap.keySet()) {
                        resolveSerializer.startTag((java.lang.String) null, "preference");
                        resolveSerializer.attribute((java.lang.String) null, com.android.server.pm.Settings.ATTR_PACKAGE, this.mAccessoryPreferenceMap.get(accessoryFilter).packageName);
                        resolveSerializer.attribute((java.lang.String) null, "user", java.lang.String.valueOf(getSerial(this.mAccessoryPreferenceMap.get(accessoryFilter).user)));
                        accessoryFilter.write(resolveSerializer);
                        resolveSerializer.endTag((java.lang.String) null, "preference");
                    }
                    int size = this.mDevicePreferenceDeniedMap.size();
                    for (int i = 0; i < size; i++) {
                        android.hardware.usb.DeviceFilter keyAt = this.mDevicePreferenceDeniedMap.keyAt(i);
                        android.util.ArraySet<com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage> valueAt = this.mDevicePreferenceDeniedMap.valueAt(i);
                        resolveSerializer.startTag((java.lang.String) null, "preference-denied-list");
                        keyAt.write(resolveSerializer);
                        int size2 = valueAt.size();
                        for (int i2 = 0; i2 < size2; i2++) {
                            com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage valueAt2 = valueAt.valueAt(i2);
                            resolveSerializer.startTag((java.lang.String) null, "user-package");
                            resolveSerializer.attribute((java.lang.String) null, "user", java.lang.String.valueOf(getSerial(valueAt2.user)));
                            resolveSerializer.attribute((java.lang.String) null, com.android.server.pm.Settings.ATTR_PACKAGE, valueAt2.packageName);
                            resolveSerializer.endTag((java.lang.String) null, "user-package");
                        }
                        resolveSerializer.endTag((java.lang.String) null, "preference-denied-list");
                    }
                    int size3 = this.mAccessoryPreferenceDeniedMap.size();
                    for (int i3 = 0; i3 < size3; i3++) {
                        android.hardware.usb.AccessoryFilter keyAt2 = this.mAccessoryPreferenceDeniedMap.keyAt(i3);
                        android.util.ArraySet<com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage> valueAt3 = this.mAccessoryPreferenceDeniedMap.valueAt(i3);
                        resolveSerializer.startTag((java.lang.String) null, "preference-denied-list");
                        keyAt2.write(resolveSerializer);
                        int size4 = valueAt3.size();
                        for (int i4 = 0; i4 < size4; i4++) {
                            com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage valueAt4 = valueAt3.valueAt(i4);
                            resolveSerializer.startTag((java.lang.String) null, "user-package");
                            resolveSerializer.attribute((java.lang.String) null, "user", java.lang.String.valueOf(getSerial(valueAt4.user)));
                            resolveSerializer.attribute((java.lang.String) null, com.android.server.pm.Settings.ATTR_PACKAGE, valueAt4.packageName);
                            resolveSerializer.endTag((java.lang.String) null, "user-package");
                        }
                        resolveSerializer.endTag((java.lang.String) null, "preference-denied-list");
                    }
                    resolveSerializer.endTag((java.lang.String) null, "settings");
                    resolveSerializer.endDocument();
                    this.mSettingsFile.finishWrite(fileOutputStream);
                } catch (java.io.IOException e3) {
                    e = e3;
                    android.util.Slog.e(TAG, "Failed to write settings", e);
                    if (fileOutputStream != null) {
                        this.mSettingsFile.failWrite(fileOutputStream);
                    }
                    this.mIsWriteSettingsScheduled = false;
                }
                this.mIsWriteSettingsScheduled = false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v16 */
    /* JADX WARN: Type inference failed for: r1v2, types: [android.content.res.XmlResourceParser] */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v5, types: [java.util.ArrayList<android.hardware.usb.DeviceFilter>] */
    /* JADX WARN: Type inference failed for: r1v6 */
    @android.annotation.Nullable
    static java.util.ArrayList<android.hardware.usb.DeviceFilter> getDeviceFilters(@android.annotation.NonNull android.content.pm.PackageManager packageManager, @android.annotation.NonNull android.content.pm.ResolveInfo resolveInfo) {
        java.lang.Object obj;
        android.content.res.XmlResourceParser loadXmlMetaData;
        ?? r1 = 0;
        java.util.ArrayList arrayList = null;
        android.content.res.XmlResourceParser xmlResourceParser = null;
        try {
            try {
                loadXmlMetaData = resolveInfo.activityInfo.loadXmlMetaData(packageManager, "android.hardware.usb.action.USB_DEVICE_ATTACHED");
                try {
                } catch (java.lang.Exception e) {
                    e = e;
                    xmlResourceParser = loadXmlMetaData;
                    obj = null;
                    android.util.Slog.w(TAG, "Unable to load component info " + resolveInfo.toString(), e);
                    if (xmlResourceParser != null) {
                        xmlResourceParser.close();
                    }
                    r1 = obj;
                    return r1;
                } catch (java.lang.Throwable th) {
                    th = th;
                    r1 = loadXmlMetaData;
                    if (r1 != 0) {
                        r1.close();
                    }
                    throw th;
                }
            } catch (java.lang.Exception e2) {
                e = e2;
                obj = null;
            }
            if (loadXmlMetaData == null) {
                android.util.Slog.w(TAG, "no meta-data for " + resolveInfo);
                if (loadXmlMetaData != null) {
                    loadXmlMetaData.close();
                }
                return null;
            }
            com.android.internal.util.XmlUtils.nextElement(loadXmlMetaData);
            while (loadXmlMetaData.getEventType() != 1) {
                arrayList = arrayList;
                if ("usb-device".equals(loadXmlMetaData.getName())) {
                    if (arrayList == null) {
                        arrayList = new java.util.ArrayList(1);
                    }
                    arrayList.add(android.hardware.usb.DeviceFilter.read(loadXmlMetaData));
                }
                com.android.internal.util.XmlUtils.nextElement(loadXmlMetaData);
                arrayList = arrayList;
            }
            loadXmlMetaData.close();
            r1 = arrayList;
            return r1;
        } catch (java.lang.Throwable th2) {
            th = th2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v16 */
    /* JADX WARN: Type inference failed for: r1v2, types: [android.content.res.XmlResourceParser] */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v5, types: [java.util.ArrayList<android.hardware.usb.AccessoryFilter>] */
    /* JADX WARN: Type inference failed for: r1v6 */
    @android.annotation.Nullable
    static java.util.ArrayList<android.hardware.usb.AccessoryFilter> getAccessoryFilters(@android.annotation.NonNull android.content.pm.PackageManager packageManager, @android.annotation.NonNull android.content.pm.ResolveInfo resolveInfo) {
        java.lang.Object obj;
        android.content.res.XmlResourceParser loadXmlMetaData;
        ?? r1 = 0;
        java.util.ArrayList arrayList = null;
        android.content.res.XmlResourceParser xmlResourceParser = null;
        try {
            try {
                loadXmlMetaData = resolveInfo.activityInfo.loadXmlMetaData(packageManager, "android.hardware.usb.action.USB_ACCESSORY_ATTACHED");
                try {
                } catch (java.lang.Exception e) {
                    e = e;
                    xmlResourceParser = loadXmlMetaData;
                    obj = null;
                    android.util.Slog.w(TAG, "Unable to load component info " + resolveInfo.toString(), e);
                    if (xmlResourceParser != null) {
                        xmlResourceParser.close();
                    }
                    r1 = obj;
                    return r1;
                } catch (java.lang.Throwable th) {
                    th = th;
                    r1 = loadXmlMetaData;
                    if (r1 != 0) {
                        r1.close();
                    }
                    throw th;
                }
            } catch (java.lang.Exception e2) {
                e = e2;
                obj = null;
            }
            if (loadXmlMetaData == null) {
                android.util.Slog.w(TAG, "no meta-data for " + resolveInfo);
                if (loadXmlMetaData != null) {
                    loadXmlMetaData.close();
                }
                return null;
            }
            com.android.internal.util.XmlUtils.nextElement(loadXmlMetaData);
            while (loadXmlMetaData.getEventType() != 1) {
                arrayList = arrayList;
                if ("usb-accessory".equals(loadXmlMetaData.getName())) {
                    if (arrayList == null) {
                        arrayList = new java.util.ArrayList(1);
                    }
                    arrayList.add(android.hardware.usb.AccessoryFilter.read(loadXmlMetaData));
                }
                com.android.internal.util.XmlUtils.nextElement(loadXmlMetaData);
                arrayList = arrayList;
            }
            loadXmlMetaData.close();
            r1 = arrayList;
            return r1;
        } catch (java.lang.Throwable th2) {
            th = th2;
        }
    }

    private boolean packageMatchesLocked(android.content.pm.ResolveInfo resolveInfo, android.hardware.usb.UsbDevice usbDevice, android.hardware.usb.UsbAccessory usbAccessory) {
        java.util.ArrayList<android.hardware.usb.AccessoryFilter> accessoryFilters;
        java.util.ArrayList<android.hardware.usb.DeviceFilter> deviceFilters;
        if (isForwardMatch(resolveInfo)) {
            return true;
        }
        if (usbDevice != null && (deviceFilters = getDeviceFilters(this.mPackageManager, resolveInfo)) != null) {
            int size = deviceFilters.size();
            for (int i = 0; i < size; i++) {
                if (deviceFilters.get(i).matches(usbDevice)) {
                    return true;
                }
            }
        }
        if (usbAccessory != null && (accessoryFilters = getAccessoryFilters(this.mPackageManager, resolveInfo)) != null) {
            int size2 = accessoryFilters.size();
            for (int i2 = 0; i2 < size2; i2++) {
                if (accessoryFilters.get(i2).matches(usbAccessory)) {
                    return true;
                }
            }
        }
        return false;
    }

    @android.annotation.NonNull
    private java.util.ArrayList<android.content.pm.ResolveInfo> queryIntentActivitiesForAllProfiles(@android.annotation.NonNull android.content.Intent intent) {
        java.util.List enabledProfiles = this.mUserManager.getEnabledProfiles(this.mParentUser.getIdentifier());
        java.util.ArrayList<android.content.pm.ResolveInfo> arrayList = new java.util.ArrayList<>();
        int size = enabledProfiles.size();
        for (int i = 0; i < size; i++) {
            arrayList.addAll(this.mSettingsManager.getSettingsForUser(((android.content.pm.UserInfo) enabledProfiles.get(i)).id).queryIntentActivities(intent));
        }
        return arrayList;
    }

    private boolean isForwardMatch(@android.annotation.NonNull android.content.pm.ResolveInfo resolveInfo) {
        return resolveInfo.getComponentInfo().name.equals(com.android.internal.app.IntentForwarderActivity.FORWARD_INTENT_TO_MANAGED_PROFILE);
    }

    @android.annotation.NonNull
    private java.util.ArrayList<android.content.pm.ResolveInfo> preferHighPriority(@android.annotation.NonNull java.util.ArrayList<android.content.pm.ResolveInfo> arrayList) {
        android.util.SparseArray sparseArray = new android.util.SparseArray();
        android.util.SparseIntArray sparseIntArray = new android.util.SparseIntArray();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            android.content.pm.ResolveInfo resolveInfo = arrayList.get(i);
            if (isForwardMatch(resolveInfo)) {
                arrayList2.add(resolveInfo);
            } else {
                if (sparseIntArray.indexOfKey(resolveInfo.targetUserId) < 0) {
                    sparseIntArray.put(resolveInfo.targetUserId, Integer.MIN_VALUE);
                    sparseArray.put(resolveInfo.targetUserId, new java.util.ArrayList());
                }
                int i2 = sparseIntArray.get(resolveInfo.targetUserId);
                java.util.ArrayList arrayList3 = (java.util.ArrayList) sparseArray.get(resolveInfo.targetUserId);
                if (resolveInfo.priority == i2) {
                    arrayList3.add(resolveInfo);
                } else if (resolveInfo.priority > i2) {
                    sparseIntArray.put(resolveInfo.targetUserId, resolveInfo.priority);
                    arrayList3.clear();
                    arrayList3.add(resolveInfo);
                }
            }
        }
        java.util.ArrayList<android.content.pm.ResolveInfo> arrayList4 = new java.util.ArrayList<>(arrayList2);
        int size2 = sparseArray.size();
        for (int i3 = 0; i3 < size2; i3++) {
            arrayList4.addAll((java.util.Collection) sparseArray.valueAt(i3));
        }
        return arrayList4;
    }

    @android.annotation.NonNull
    private java.util.ArrayList<android.content.pm.ResolveInfo> removeForwardIntentIfNotNeeded(@android.annotation.NonNull java.util.ArrayList<android.content.pm.ResolveInfo> arrayList) {
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            android.content.pm.ResolveInfo resolveInfo = arrayList.get(i3);
            if (!isForwardMatch(resolveInfo)) {
                if (android.os.UserHandle.getUserHandleForUid(resolveInfo.activityInfo.applicationInfo.uid).equals(this.mParentUser)) {
                    i++;
                } else {
                    i2++;
                }
            }
        }
        if (i == 0 || i2 == 0) {
            java.util.ArrayList<android.content.pm.ResolveInfo> arrayList2 = new java.util.ArrayList<>(i + i2);
            for (int i4 = 0; i4 < size; i4++) {
                android.content.pm.ResolveInfo resolveInfo2 = arrayList.get(i4);
                if (!isForwardMatch(resolveInfo2)) {
                    arrayList2.add(resolveInfo2);
                }
            }
            return arrayList2;
        }
        return arrayList;
    }

    private java.util.ArrayList<android.content.pm.ResolveInfo> getDeviceMatchesLocked(android.hardware.usb.UsbDevice usbDevice, android.content.Intent intent) {
        java.util.ArrayList<android.content.pm.ResolveInfo> arrayList = new java.util.ArrayList<>();
        java.util.ArrayList<android.content.pm.ResolveInfo> queryIntentActivitiesForAllProfiles = queryIntentActivitiesForAllProfiles(intent);
        int size = queryIntentActivitiesForAllProfiles.size();
        for (int i = 0; i < size; i++) {
            android.content.pm.ResolveInfo resolveInfo = queryIntentActivitiesForAllProfiles.get(i);
            if (packageMatchesLocked(resolveInfo, usbDevice, null)) {
                arrayList.add(resolveInfo);
            }
        }
        return removeForwardIntentIfNotNeeded(preferHighPriority(arrayList));
    }

    private java.util.ArrayList<android.content.pm.ResolveInfo> getAccessoryMatchesLocked(android.hardware.usb.UsbAccessory usbAccessory, android.content.Intent intent) {
        java.util.ArrayList<android.content.pm.ResolveInfo> arrayList = new java.util.ArrayList<>();
        java.util.ArrayList<android.content.pm.ResolveInfo> queryIntentActivitiesForAllProfiles = queryIntentActivitiesForAllProfiles(intent);
        int size = queryIntentActivitiesForAllProfiles.size();
        for (int i = 0; i < size; i++) {
            android.content.pm.ResolveInfo resolveInfo = queryIntentActivitiesForAllProfiles.get(i);
            if (packageMatchesLocked(resolveInfo, null, usbAccessory)) {
                arrayList.add(resolveInfo);
            }
        }
        return removeForwardIntentIfNotNeeded(preferHighPriority(arrayList));
    }

    public void deviceAttached(android.hardware.usb.UsbDevice usbDevice) {
        android.content.Intent createDeviceAttachedIntent = createDeviceAttachedIntent(usbDevice);
        this.mContext.sendBroadcastAsUser(createDeviceAttachedIntent, android.os.UserHandle.ALL);
        if (!shouldRestrictOverlayActivities()) {
            resolveActivity(createDeviceAttachedIntent, usbDevice, true);
        }
    }

    private void resolveActivity(android.content.Intent intent, android.hardware.usb.UsbDevice usbDevice, boolean z) {
        java.util.ArrayList<android.content.pm.ResolveInfo> deviceMatchesLocked;
        android.content.pm.ActivityInfo defaultActivityLocked;
        synchronized (this.mLock) {
            deviceMatchesLocked = getDeviceMatchesLocked(usbDevice, intent);
            defaultActivityLocked = getDefaultActivityLocked(deviceMatchesLocked, this.mDevicePreferenceMap.get(new android.hardware.usb.DeviceFilter(usbDevice)));
        }
        if (z && com.android.server.usb.MtpNotificationManager.shouldShowNotification(this.mPackageManager, usbDevice) && defaultActivityLocked == null) {
            this.mMtpNotificationManager.showNotification(usbDevice);
        } else {
            resolveActivity(intent, deviceMatchesLocked, defaultActivityLocked, usbDevice, null);
        }
    }

    private boolean shouldRestrictOverlayActivities() {
        com.android.server.usb.flags.Flags.allowRestrictionOfOverlayActivities();
        return false;
    }

    private /* synthetic */ boolean lambda$shouldRestrictOverlayActivities$3(java.lang.String str) {
        try {
            return this.mPackageManager.getProperty(PROPERTY_RESTRICT_USB_OVERLAY_ACTIVITIES, str).getBoolean();
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public void deviceAttachedForFixedHandler(android.hardware.usb.UsbDevice usbDevice, android.content.ComponentName componentName) {
        android.content.Intent createDeviceAttachedIntent = createDeviceAttachedIntent(usbDevice);
        this.mContext.sendBroadcastAsUser(createDeviceAttachedIntent, android.os.UserHandle.of(android.app.ActivityManager.getCurrentUser()));
        try {
            android.content.pm.ApplicationInfo applicationInfoAsUser = this.mPackageManager.getApplicationInfoAsUser(componentName.getPackageName(), 0, this.mParentUser.getIdentifier());
            this.mSettingsManager.mUsbService.getPermissionsForUser(android.os.UserHandle.getUserId(applicationInfoAsUser.uid)).grantDevicePermission(usbDevice, applicationInfoAsUser.uid);
            android.content.Intent intent = new android.content.Intent(createDeviceAttachedIntent);
            intent.setComponent(componentName);
            try {
                this.mContext.startActivityAsUser(intent, this.mParentUser);
            } catch (android.content.ActivityNotFoundException e) {
                android.util.Slog.e(TAG, "unable to start activity " + intent);
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
            android.util.Slog.e(TAG, "Default USB handling package (" + componentName.getPackageName() + ") not found  for user " + this.mParentUser);
        }
    }

    void usbDeviceRemoved(@android.annotation.NonNull android.hardware.usb.UsbDevice usbDevice) {
        this.mMtpNotificationManager.hideNotification(usbDevice.getDeviceId());
    }

    public void accessoryAttached(android.hardware.usb.UsbAccessory usbAccessory) {
        java.util.ArrayList<android.content.pm.ResolveInfo> accessoryMatchesLocked;
        android.content.pm.ActivityInfo defaultActivityLocked;
        android.content.Intent intent = new android.content.Intent("android.hardware.usb.action.USB_ACCESSORY_ATTACHED");
        intent.putExtra("accessory", usbAccessory);
        intent.addFlags(android.hardware.audio.common.V2_0.AudioFormat.EVRCB);
        synchronized (this.mLock) {
            accessoryMatchesLocked = getAccessoryMatchesLocked(usbAccessory, intent);
            defaultActivityLocked = getDefaultActivityLocked(accessoryMatchesLocked, this.mAccessoryPreferenceMap.get(new android.hardware.usb.AccessoryFilter(usbAccessory)));
        }
        sEventLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("accessoryAttached: " + intent));
        resolveActivity(intent, accessoryMatchesLocked, defaultActivityLocked, null, usbAccessory);
    }

    private void resolveActivity(@android.annotation.NonNull android.content.Intent intent, @android.annotation.NonNull java.util.ArrayList<android.content.pm.ResolveInfo> arrayList, @android.annotation.Nullable android.content.pm.ActivityInfo activityInfo, @android.annotation.Nullable android.hardware.usb.UsbDevice usbDevice, @android.annotation.Nullable android.hardware.usb.UsbAccessory usbAccessory) {
        android.util.ArraySet<com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage> arraySet;
        if (usbDevice != null) {
            arraySet = this.mDevicePreferenceDeniedMap.get(new android.hardware.usb.DeviceFilter(usbDevice));
        } else if (usbAccessory == null) {
            arraySet = null;
        } else {
            arraySet = this.mAccessoryPreferenceDeniedMap.get(new android.hardware.usb.AccessoryFilter(usbAccessory));
        }
        if (arraySet != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                android.content.pm.ResolveInfo resolveInfo = arrayList.get(size);
                if (arraySet.contains(new com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage(resolveInfo.activityInfo.packageName, android.os.UserHandle.getUserHandleForUid(resolveInfo.activityInfo.applicationInfo.uid)))) {
                    arrayList.remove(size);
                }
            }
        }
        if (arrayList.size() == 0) {
            if (usbAccessory != null) {
                this.mUsbHandlerManager.showUsbAccessoryUriActivity(usbAccessory, this.mParentUser);
                return;
            }
            return;
        }
        if (activityInfo == null) {
            if (arrayList.size() == 1) {
                this.mUsbHandlerManager.confirmUsbHandler(arrayList.get(0), usbDevice, usbAccessory);
                return;
            } else {
                this.mUsbHandlerManager.selectUsbHandler(arrayList, this.mParentUser, intent);
                return;
            }
        }
        com.android.server.usb.UsbUserPermissionManager permissionsForUser = this.mSettingsManager.mUsbService.getPermissionsForUser(android.os.UserHandle.getUserId(activityInfo.applicationInfo.uid));
        if (usbDevice != null) {
            permissionsForUser.grantDevicePermission(usbDevice, activityInfo.applicationInfo.uid);
        } else if (usbAccessory != null) {
            permissionsForUser.grantAccessoryPermission(usbAccessory, activityInfo.applicationInfo.uid);
        }
        try {
            intent.setComponent(new android.content.ComponentName(activityInfo.packageName, activityInfo.name));
            this.mContext.startActivityAsUser(intent, android.os.UserHandle.getUserHandleForUid(activityInfo.applicationInfo.uid));
        } catch (android.content.ActivityNotFoundException e) {
            android.util.Slog.e(TAG, "startActivity failed", e);
        }
    }

    @android.annotation.Nullable
    private android.content.pm.ActivityInfo getDefaultActivityLocked(@android.annotation.NonNull java.util.ArrayList<android.content.pm.ResolveInfo> arrayList, @android.annotation.Nullable com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage userPackage) {
        android.content.pm.ActivityInfo activityInfo;
        if (userPackage != null) {
            java.util.Iterator<android.content.pm.ResolveInfo> it = arrayList.iterator();
            while (it.hasNext()) {
                android.content.pm.ResolveInfo next = it.next();
                if (next.activityInfo != null && userPackage.equals(new com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage(next.activityInfo.packageName, android.os.UserHandle.getUserHandleForUid(next.activityInfo.applicationInfo.uid)))) {
                    return next.activityInfo;
                }
            }
        }
        if (arrayList.size() == 1 && (activityInfo = arrayList.get(0).activityInfo) != null) {
            if (this.mDisablePermissionDialogs) {
                return activityInfo;
            }
            if (activityInfo.applicationInfo != null && (activityInfo.applicationInfo.flags & 1) != 0) {
                return activityInfo;
            }
        }
        return null;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean clearCompatibleMatchesLocked(@android.annotation.NonNull com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage userPackage, @android.annotation.NonNull android.hardware.usb.DeviceFilter deviceFilter) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.hardware.usb.DeviceFilter deviceFilter2 : this.mDevicePreferenceMap.keySet()) {
            if (deviceFilter.contains(deviceFilter2) && !this.mDevicePreferenceMap.get(deviceFilter2).equals(userPackage)) {
                arrayList.add(deviceFilter2);
            }
        }
        if (!arrayList.isEmpty()) {
            java.util.Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                this.mDevicePreferenceMap.remove((android.hardware.usb.DeviceFilter) it.next());
            }
        }
        return !arrayList.isEmpty();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean clearCompatibleMatchesLocked(@android.annotation.NonNull com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage userPackage, @android.annotation.NonNull android.hardware.usb.AccessoryFilter accessoryFilter) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.hardware.usb.AccessoryFilter accessoryFilter2 : this.mAccessoryPreferenceMap.keySet()) {
            if (accessoryFilter.contains(accessoryFilter2) && !this.mAccessoryPreferenceMap.get(accessoryFilter2).equals(userPackage)) {
                arrayList.add(accessoryFilter2);
            }
        }
        if (!arrayList.isEmpty()) {
            java.util.Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                this.mAccessoryPreferenceMap.remove((android.hardware.usb.AccessoryFilter) it.next());
            }
        }
        return !arrayList.isEmpty();
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x0071, code lost:
    
        if (0 == 0) goto L34;
     */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean handlePackageAddedLocked(com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage userPackage, android.content.pm.ActivityInfo activityInfo, java.lang.String str) {
        android.content.res.XmlResourceParser xmlResourceParser = null;
        boolean z = false;
        try {
            try {
                xmlResourceParser = activityInfo.loadXmlMetaData(this.mPackageManager, str);
            } catch (java.lang.Exception e) {
                android.util.Slog.w(TAG, "Unable to load component info " + activityInfo.toString(), e);
            }
            if (xmlResourceParser == null) {
                if (xmlResourceParser != null) {
                    xmlResourceParser.close();
                }
                return false;
            }
            com.android.internal.util.XmlUtils.nextElement(xmlResourceParser);
            while (xmlResourceParser.getEventType() != 1) {
                java.lang.String name = xmlResourceParser.getName();
                if ("usb-device".equals(name)) {
                    if (clearCompatibleMatchesLocked(userPackage, android.hardware.usb.DeviceFilter.read(xmlResourceParser))) {
                        z = true;
                    }
                } else if ("usb-accessory".equals(name) && clearCompatibleMatchesLocked(userPackage, android.hardware.usb.AccessoryFilter.read(xmlResourceParser))) {
                    z = true;
                }
                com.android.internal.util.XmlUtils.nextElement(xmlResourceParser);
            }
            xmlResourceParser.close();
            return z;
        } catch (java.lang.Throwable th) {
            if (0 != 0) {
                xmlResourceParser.close();
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePackageAdded(@android.annotation.NonNull com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage userPackage) {
        synchronized (this.mLock) {
            try {
                try {
                    android.content.pm.ActivityInfo[] activityInfoArr = this.mPackageManager.getPackageInfoAsUser(userPackage.packageName, 129, userPackage.user.getIdentifier()).activities;
                    if (activityInfoArr == null) {
                        return;
                    }
                    boolean z = false;
                    for (int i = 0; i < activityInfoArr.length; i++) {
                        if (handlePackageAddedLocked(userPackage, activityInfoArr[i], "android.hardware.usb.action.USB_DEVICE_ATTACHED")) {
                            z = true;
                        }
                        if (handlePackageAddedLocked(userPackage, activityInfoArr[i], "android.hardware.usb.action.USB_ACCESSORY_ATTACHED")) {
                            z = true;
                        }
                    }
                    if (z) {
                        scheduleWriteSettingsLocked();
                    }
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    android.util.Slog.e(TAG, "handlePackageUpdate could not find package " + userPackage, e);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private int getSerial(@android.annotation.NonNull android.os.UserHandle userHandle) {
        return this.mUserManager.getUserSerialNumber(userHandle.getIdentifier());
    }

    void setDevicePackage(@android.annotation.NonNull android.hardware.usb.UsbDevice usbDevice, @android.annotation.Nullable java.lang.String str, @android.annotation.NonNull android.os.UserHandle userHandle) {
        android.hardware.usb.DeviceFilter deviceFilter = new android.hardware.usb.DeviceFilter(usbDevice);
        synchronized (this.mLock) {
            boolean z = true;
            try {
                if (str == null) {
                    if (this.mDevicePreferenceMap.remove(deviceFilter) == null) {
                        z = false;
                    }
                } else {
                    com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage userPackage = new com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage(str, userHandle);
                    z = true ^ userPackage.equals(this.mDevicePreferenceMap.get(deviceFilter));
                    if (z) {
                        this.mDevicePreferenceMap.put(deviceFilter, userPackage);
                    }
                }
                if (z) {
                    scheduleWriteSettingsLocked();
                }
            } finally {
            }
        }
    }

    void addDevicePackagesToDenied(@android.annotation.NonNull android.hardware.usb.UsbDevice usbDevice, @android.annotation.NonNull java.lang.String[] strArr, @android.annotation.NonNull android.os.UserHandle userHandle) {
        android.util.ArraySet<com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage> arraySet;
        if (strArr.length == 0) {
            return;
        }
        android.hardware.usb.DeviceFilter deviceFilter = new android.hardware.usb.DeviceFilter(usbDevice);
        synchronized (this.mLock) {
            try {
                if (this.mDevicePreferenceDeniedMap.containsKey(deviceFilter)) {
                    arraySet = this.mDevicePreferenceDeniedMap.get(deviceFilter);
                } else {
                    android.util.ArraySet<com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage> arraySet2 = new android.util.ArraySet<>();
                    this.mDevicePreferenceDeniedMap.put(deviceFilter, arraySet2);
                    arraySet = arraySet2;
                }
                boolean z = false;
                for (java.lang.String str : strArr) {
                    com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage userPackage = new com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage(str, userHandle);
                    if (!arraySet.contains(userPackage)) {
                        arraySet.add(userPackage);
                        z = true;
                    }
                }
                if (z) {
                    scheduleWriteSettingsLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void addAccessoryPackagesToDenied(@android.annotation.NonNull android.hardware.usb.UsbAccessory usbAccessory, @android.annotation.NonNull java.lang.String[] strArr, @android.annotation.NonNull android.os.UserHandle userHandle) {
        android.util.ArraySet<com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage> arraySet;
        if (strArr.length == 0) {
            return;
        }
        android.hardware.usb.AccessoryFilter accessoryFilter = new android.hardware.usb.AccessoryFilter(usbAccessory);
        synchronized (this.mLock) {
            try {
                if (this.mAccessoryPreferenceDeniedMap.containsKey(accessoryFilter)) {
                    arraySet = this.mAccessoryPreferenceDeniedMap.get(accessoryFilter);
                } else {
                    android.util.ArraySet<com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage> arraySet2 = new android.util.ArraySet<>();
                    this.mAccessoryPreferenceDeniedMap.put(accessoryFilter, arraySet2);
                    arraySet = arraySet2;
                }
                boolean z = false;
                for (java.lang.String str : strArr) {
                    com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage userPackage = new com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage(str, userHandle);
                    if (!arraySet.contains(userPackage)) {
                        arraySet.add(userPackage);
                        z = true;
                    }
                }
                if (z) {
                    scheduleWriteSettingsLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void removeDevicePackagesFromDenied(@android.annotation.NonNull android.hardware.usb.UsbDevice usbDevice, @android.annotation.NonNull java.lang.String[] strArr, @android.annotation.NonNull android.os.UserHandle userHandle) {
        android.hardware.usb.DeviceFilter deviceFilter = new android.hardware.usb.DeviceFilter(usbDevice);
        synchronized (this.mLock) {
            try {
                android.util.ArraySet<com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage> arraySet = this.mDevicePreferenceDeniedMap.get(deviceFilter);
                if (arraySet != null) {
                    int length = strArr.length;
                    int i = 0;
                    boolean z = false;
                    while (true) {
                        if (i >= length) {
                            break;
                        }
                        com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage userPackage = new com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage(strArr[i], userHandle);
                        if (arraySet.contains(userPackage)) {
                            arraySet.remove(userPackage);
                            if (arraySet.size() != 0) {
                                z = true;
                            } else {
                                this.mDevicePreferenceDeniedMap.remove(deviceFilter);
                                z = true;
                                break;
                            }
                        }
                        i++;
                    }
                    if (z) {
                        scheduleWriteSettingsLocked();
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void removeAccessoryPackagesFromDenied(@android.annotation.NonNull android.hardware.usb.UsbAccessory usbAccessory, @android.annotation.NonNull java.lang.String[] strArr, @android.annotation.NonNull android.os.UserHandle userHandle) {
        android.hardware.usb.AccessoryFilter accessoryFilter = new android.hardware.usb.AccessoryFilter(usbAccessory);
        synchronized (this.mLock) {
            try {
                android.util.ArraySet<com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage> arraySet = this.mAccessoryPreferenceDeniedMap.get(accessoryFilter);
                if (arraySet != null) {
                    int length = strArr.length;
                    int i = 0;
                    boolean z = false;
                    while (true) {
                        if (i >= length) {
                            break;
                        }
                        com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage userPackage = new com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage(strArr[i], userHandle);
                        if (arraySet.contains(userPackage)) {
                            arraySet.remove(userPackage);
                            if (arraySet.size() != 0) {
                                z = true;
                            } else {
                                this.mAccessoryPreferenceDeniedMap.remove(accessoryFilter);
                                z = true;
                                break;
                            }
                        }
                        i++;
                    }
                    if (z) {
                        scheduleWriteSettingsLocked();
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void setAccessoryPackage(@android.annotation.NonNull android.hardware.usb.UsbAccessory usbAccessory, @android.annotation.Nullable java.lang.String str, @android.annotation.NonNull android.os.UserHandle userHandle) {
        android.hardware.usb.AccessoryFilter accessoryFilter = new android.hardware.usb.AccessoryFilter(usbAccessory);
        synchronized (this.mLock) {
            boolean z = true;
            try {
                if (str == null) {
                    if (this.mAccessoryPreferenceMap.remove(accessoryFilter) == null) {
                        z = false;
                    }
                } else {
                    com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage userPackage = new com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage(str, userHandle);
                    z = true ^ userPackage.equals(this.mAccessoryPreferenceMap.get(accessoryFilter));
                    if (z) {
                        this.mAccessoryPreferenceMap.put(accessoryFilter, userPackage);
                    }
                }
                if (z) {
                    scheduleWriteSettingsLocked();
                }
            } finally {
            }
        }
    }

    boolean hasDefaults(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.UserHandle userHandle) {
        com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage userPackage = new com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage(str, userHandle);
        synchronized (this.mLock) {
            try {
                if (this.mDevicePreferenceMap.values().contains(userPackage)) {
                    return true;
                }
                return this.mAccessoryPreferenceMap.values().contains(userPackage);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void clearDefaults(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.UserHandle userHandle) {
        com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage userPackage = new com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage(str, userHandle);
        synchronized (this.mLock) {
            try {
                if (clearPackageDefaultsLocked(userPackage)) {
                    scheduleWriteSettingsLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private boolean clearPackageDefaultsLocked(@android.annotation.NonNull com.android.server.usb.UsbProfileGroupSettingsManager.UserPackage userPackage) {
        boolean z;
        synchronized (this.mLock) {
            try {
                if (!this.mDevicePreferenceMap.containsValue(userPackage)) {
                    z = false;
                } else {
                    z = false;
                    for (android.hardware.usb.DeviceFilter deviceFilter : (android.hardware.usb.DeviceFilter[]) this.mDevicePreferenceMap.keySet().toArray(new android.hardware.usb.DeviceFilter[0])) {
                        if (userPackage.equals(this.mDevicePreferenceMap.get(deviceFilter))) {
                            this.mDevicePreferenceMap.remove(deviceFilter);
                            z = true;
                        }
                    }
                }
                if (this.mAccessoryPreferenceMap.containsValue(userPackage)) {
                    for (android.hardware.usb.AccessoryFilter accessoryFilter : (android.hardware.usb.AccessoryFilter[]) this.mAccessoryPreferenceMap.keySet().toArray(new android.hardware.usb.AccessoryFilter[0])) {
                        if (userPackage.equals(this.mAccessoryPreferenceMap.get(accessoryFilter))) {
                            this.mAccessoryPreferenceMap.remove(accessoryFilter);
                            z = true;
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return z;
    }

    public void dump(@android.annotation.NonNull com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream, @android.annotation.NonNull java.lang.String str, long j) {
        long start = dualDumpOutputStream.start(str, j);
        synchronized (this.mLock) {
            try {
                dualDumpOutputStream.write("parent_user_id", 1120986464257L, this.mParentUser.getIdentifier());
                for (android.hardware.usb.DeviceFilter deviceFilter : this.mDevicePreferenceMap.keySet()) {
                    long start2 = dualDumpOutputStream.start("device_preferences", 2246267895810L);
                    deviceFilter.dump(dualDumpOutputStream, com.android.server.pm.verify.domain.DomainVerificationPersistence.ATTR_FILTER, 1146756268033L);
                    this.mDevicePreferenceMap.get(deviceFilter).dump(dualDumpOutputStream, "user_package", 1146756268034L);
                    dualDumpOutputStream.end(start2);
                }
                for (android.hardware.usb.AccessoryFilter accessoryFilter : this.mAccessoryPreferenceMap.keySet()) {
                    long start3 = dualDumpOutputStream.start("accessory_preferences", 2246267895811L);
                    accessoryFilter.dump(dualDumpOutputStream, com.android.server.pm.verify.domain.DomainVerificationPersistence.ATTR_FILTER, 1146756268033L);
                    this.mAccessoryPreferenceMap.get(accessoryFilter).dump(dualDumpOutputStream, "user_package", 1146756268034L);
                    dualDumpOutputStream.end(start3);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        sEventLogger.dump(new com.android.server.usb.DualOutputStreamDumpSink(dualDumpOutputStream, 1138166333444L));
        dualDumpOutputStream.end(start);
    }

    private static android.content.Intent createDeviceAttachedIntent(android.hardware.usb.UsbDevice usbDevice) {
        android.content.Intent intent = new android.content.Intent("android.hardware.usb.action.USB_DEVICE_ATTACHED");
        intent.putExtra("device", usbDevice);
        intent.addFlags(android.hardware.audio.common.V2_0.AudioFormat.EVRCB);
        return intent;
    }
}
