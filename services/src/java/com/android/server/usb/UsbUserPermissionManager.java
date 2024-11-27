package com.android.server.usb;

/* loaded from: classes2.dex */
class UsbUserPermissionManager {
    private static final boolean DEBUG = false;
    private static final int SNET_EVENT_LOG_ID = 1397638484;
    private static final java.lang.String TAG = com.android.server.usb.UsbUserPermissionManager.class.getSimpleName();
    private final android.content.Context mContext;
    private final boolean mDisablePermissionDialogs;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mIsCopyPermissionsScheduled;

    @android.annotation.NonNull
    private final android.util.AtomicFile mPermissionsFile;
    private final com.android.server.usb.UsbUserSettingsManager mUsbUserSettingsManager;
    private final android.os.UserHandle mUser;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<java.lang.String, android.util.SparseBooleanArray> mDevicePermissionMap = new android.util.ArrayMap<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<android.hardware.usb.UsbAccessory, android.util.SparseBooleanArray> mAccessoryPermissionMap = new android.util.ArrayMap<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<android.hardware.usb.DeviceFilter, android.util.SparseBooleanArray> mDevicePersistentPermissionMap = new android.util.ArrayMap<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<android.hardware.usb.AccessoryFilter, android.util.SparseBooleanArray> mAccessoryPersistentPermissionMap = new android.util.ArrayMap<>();
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.hardware.SensorPrivacyManagerInternal mSensorPrivacyMgrInternal = (android.hardware.SensorPrivacyManagerInternal) com.android.server.LocalServices.getService(android.hardware.SensorPrivacyManagerInternal.class);

    UsbUserPermissionManager(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.usb.UsbUserSettingsManager usbUserSettingsManager) {
        this.mContext = context;
        this.mUser = context.getUser();
        this.mUsbUserSettingsManager = usbUserSettingsManager;
        this.mDisablePermissionDialogs = context.getResources().getBoolean(android.R.bool.config_disableTransitionAnimation);
        this.mPermissionsFile = new android.util.AtomicFile(new java.io.File(android.os.Environment.getUserSystemDirectory(this.mUser.getIdentifier()), "usb_permissions.xml"), "usb-permissions");
        synchronized (this.mLock) {
            readPermissionsLocked();
        }
    }

    void removeAccessoryPermissions(@android.annotation.NonNull android.hardware.usb.UsbAccessory usbAccessory) {
        synchronized (this.mLock) {
            this.mAccessoryPermissionMap.remove(usbAccessory);
        }
    }

    void removeDevicePermissions(@android.annotation.NonNull android.hardware.usb.UsbDevice usbDevice) {
        synchronized (this.mLock) {
            this.mDevicePermissionMap.remove(usbDevice.getDeviceName());
        }
    }

    void grantDevicePermission(@android.annotation.NonNull android.hardware.usb.UsbDevice usbDevice, int i) {
        synchronized (this.mLock) {
            try {
                java.lang.String deviceName = usbDevice.getDeviceName();
                android.util.SparseBooleanArray sparseBooleanArray = this.mDevicePermissionMap.get(deviceName);
                if (sparseBooleanArray == null) {
                    sparseBooleanArray = new android.util.SparseBooleanArray(1);
                    this.mDevicePermissionMap.put(deviceName, sparseBooleanArray);
                }
                sparseBooleanArray.put(i, true);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void grantAccessoryPermission(@android.annotation.NonNull android.hardware.usb.UsbAccessory usbAccessory, int i) {
        synchronized (this.mLock) {
            try {
                android.util.SparseBooleanArray sparseBooleanArray = this.mAccessoryPermissionMap.get(usbAccessory);
                if (sparseBooleanArray == null) {
                    sparseBooleanArray = new android.util.SparseBooleanArray(1);
                    this.mAccessoryPermissionMap.put(usbAccessory, sparseBooleanArray);
                }
                sparseBooleanArray.put(i, true);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean hasPermission(@android.annotation.NonNull android.hardware.usb.UsbDevice usbDevice, @android.annotation.NonNull java.lang.String str, int i, int i2) {
        int indexOfKey;
        if (usbDevice.getHasVideoCapture() && (this.mSensorPrivacyMgrInternal.isSensorPrivacyEnabled(android.os.UserHandle.getUserId(i2), 2) || !isCameraPermissionGranted(str, i, i2))) {
            return false;
        }
        if (usbDevice.getHasAudioCapture() && this.mSensorPrivacyMgrInternal.isSensorPrivacyEnabled(android.os.UserHandle.getUserId(i2), 1)) {
            return false;
        }
        synchronized (this.mLock) {
            if (i2 != 1000) {
                try {
                    if (!this.mDisablePermissionDialogs) {
                        android.util.SparseBooleanArray sparseBooleanArray = this.mDevicePersistentPermissionMap.get(new android.hardware.usb.DeviceFilter(usbDevice));
                        if (sparseBooleanArray != null && (indexOfKey = sparseBooleanArray.indexOfKey(i2)) >= 0) {
                            return sparseBooleanArray.valueAt(indexOfKey);
                        }
                        android.util.SparseBooleanArray sparseBooleanArray2 = this.mDevicePermissionMap.get(usbDevice.getDeviceName());
                        if (sparseBooleanArray2 == null) {
                            return false;
                        }
                        return sparseBooleanArray2.get(i2);
                    }
                } finally {
                }
            }
            return true;
        }
    }

    boolean hasPermission(@android.annotation.NonNull android.hardware.usb.UsbAccessory usbAccessory, int i, int i2) {
        int indexOfKey;
        synchronized (this.mLock) {
            if (i2 != 1000) {
                try {
                    if (!this.mDisablePermissionDialogs && this.mContext.checkPermission("android.permission.MANAGE_USB", i, i2) != 0) {
                        android.util.SparseBooleanArray sparseBooleanArray = this.mAccessoryPersistentPermissionMap.get(new android.hardware.usb.AccessoryFilter(usbAccessory));
                        if (sparseBooleanArray != null && (indexOfKey = sparseBooleanArray.indexOfKey(i2)) >= 0) {
                            return sparseBooleanArray.valueAt(indexOfKey);
                        }
                        android.util.SparseBooleanArray sparseBooleanArray2 = this.mAccessoryPermissionMap.get(usbAccessory);
                        if (sparseBooleanArray2 == null) {
                            return false;
                        }
                        return sparseBooleanArray2.get(i2);
                    }
                } finally {
                }
            }
            return true;
        }
    }

    void setDevicePersistentPermission(@android.annotation.NonNull android.hardware.usb.UsbDevice usbDevice, int i, boolean z) {
        android.hardware.usb.DeviceFilter deviceFilter = new android.hardware.usb.DeviceFilter(usbDevice);
        synchronized (this.mLock) {
            try {
                android.util.SparseBooleanArray sparseBooleanArray = this.mDevicePersistentPermissionMap.get(deviceFilter);
                if (sparseBooleanArray == null) {
                    sparseBooleanArray = new android.util.SparseBooleanArray();
                    this.mDevicePersistentPermissionMap.put(deviceFilter, sparseBooleanArray);
                }
                int indexOfKey = sparseBooleanArray.indexOfKey(i);
                boolean z2 = true;
                if (indexOfKey >= 0) {
                    if (sparseBooleanArray.valueAt(indexOfKey) == z) {
                        z2 = false;
                    }
                    sparseBooleanArray.setValueAt(indexOfKey, z);
                } else {
                    sparseBooleanArray.put(i, z);
                }
                if (z2) {
                    scheduleWritePermissionsLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void setAccessoryPersistentPermission(@android.annotation.NonNull android.hardware.usb.UsbAccessory usbAccessory, int i, boolean z) {
        android.hardware.usb.AccessoryFilter accessoryFilter = new android.hardware.usb.AccessoryFilter(usbAccessory);
        synchronized (this.mLock) {
            try {
                android.util.SparseBooleanArray sparseBooleanArray = this.mAccessoryPersistentPermissionMap.get(accessoryFilter);
                if (sparseBooleanArray == null) {
                    sparseBooleanArray = new android.util.SparseBooleanArray();
                    this.mAccessoryPersistentPermissionMap.put(accessoryFilter, sparseBooleanArray);
                }
                int indexOfKey = sparseBooleanArray.indexOfKey(i);
                boolean z2 = true;
                if (indexOfKey >= 0) {
                    if (sparseBooleanArray.valueAt(indexOfKey) == z) {
                        z2 = false;
                    }
                    sparseBooleanArray.setValueAt(indexOfKey, z);
                } else {
                    sparseBooleanArray.put(i, z);
                }
                if (z2) {
                    scheduleWritePermissionsLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void readPermission(@android.annotation.NonNull org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        try {
            int readIntAttribute = com.android.internal.util.XmlUtils.readIntAttribute(xmlPullParser, com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.UID);
            java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "granted");
            if (attributeValue == null || (!attributeValue.equals(java.lang.Boolean.TRUE.toString()) && !attributeValue.equals(java.lang.Boolean.FALSE.toString()))) {
                android.util.Slog.e(TAG, "error reading usb permission granted state");
                com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                return;
            }
            boolean equals = attributeValue.equals(java.lang.Boolean.TRUE.toString());
            com.android.internal.util.XmlUtils.nextElement(xmlPullParser);
            if ("usb-device".equals(xmlPullParser.getName())) {
                android.hardware.usb.DeviceFilter read = android.hardware.usb.DeviceFilter.read(xmlPullParser);
                int indexOfKey = this.mDevicePersistentPermissionMap.indexOfKey(read);
                if (indexOfKey >= 0) {
                    this.mDevicePersistentPermissionMap.valueAt(indexOfKey).put(readIntAttribute, equals);
                    return;
                }
                android.util.SparseBooleanArray sparseBooleanArray = new android.util.SparseBooleanArray();
                this.mDevicePersistentPermissionMap.put(read, sparseBooleanArray);
                sparseBooleanArray.put(readIntAttribute, equals);
                return;
            }
            if ("usb-accessory".equals(xmlPullParser.getName())) {
                android.hardware.usb.AccessoryFilter read2 = android.hardware.usb.AccessoryFilter.read(xmlPullParser);
                int indexOfKey2 = this.mAccessoryPersistentPermissionMap.indexOfKey(read2);
                if (indexOfKey2 >= 0) {
                    this.mAccessoryPersistentPermissionMap.valueAt(indexOfKey2).put(readIntAttribute, equals);
                    return;
                }
                android.util.SparseBooleanArray sparseBooleanArray2 = new android.util.SparseBooleanArray();
                this.mAccessoryPersistentPermissionMap.put(read2, sparseBooleanArray2);
                sparseBooleanArray2.put(readIntAttribute, equals);
            }
        } catch (java.lang.NumberFormatException e) {
            android.util.Slog.e(TAG, "error reading usb permission uid", e);
            com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void readPermissionsLocked() {
        this.mDevicePersistentPermissionMap.clear();
        this.mAccessoryPersistentPermissionMap.clear();
        try {
            java.io.FileInputStream openRead = this.mPermissionsFile.openRead();
            try {
                com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(openRead);
                com.android.internal.util.XmlUtils.nextElement(resolvePullParser);
                while (resolvePullParser.getEventType() != 1) {
                    if ("permission".equals(resolvePullParser.getName())) {
                        readPermission(resolvePullParser);
                    } else {
                        com.android.internal.util.XmlUtils.nextElement(resolvePullParser);
                    }
                }
                if (openRead != null) {
                    openRead.close();
                }
            } finally {
            }
        } catch (java.io.FileNotFoundException e) {
        } catch (java.lang.Exception e2) {
            android.util.Slog.e(TAG, "error reading usb permissions file, deleting to start fresh", e2);
            this.mPermissionsFile.delete();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void scheduleWritePermissionsLocked() {
        if (this.mIsCopyPermissionsScheduled) {
            return;
        }
        this.mIsCopyPermissionsScheduled = true;
        android.os.AsyncTask.execute(new java.lang.Runnable() { // from class: com.android.server.usb.UsbUserPermissionManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.usb.UsbUserPermissionManager.this.lambda$scheduleWritePermissionsLocked$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleWritePermissionsLocked$0() {
        int size;
        android.hardware.usb.DeviceFilter[] deviceFilterArr;
        int[][] iArr;
        boolean[][] zArr;
        int i;
        int size2;
        android.hardware.usb.AccessoryFilter[] accessoryFilterArr;
        int[][] iArr2;
        java.io.FileOutputStream startWrite;
        synchronized (this.mLock) {
            try {
                size = this.mDevicePersistentPermissionMap.size();
                deviceFilterArr = new android.hardware.usb.DeviceFilter[size];
                iArr = new int[size][];
                zArr = new boolean[size][];
                i = 0;
                for (int i2 = 0; i2 < size; i2++) {
                    deviceFilterArr[i2] = new android.hardware.usb.DeviceFilter(this.mDevicePersistentPermissionMap.keyAt(i2));
                    android.util.SparseBooleanArray valueAt = this.mDevicePersistentPermissionMap.valueAt(i2);
                    int size3 = valueAt.size();
                    iArr[i2] = new int[size3];
                    zArr[i2] = new boolean[size3];
                    for (int i3 = 0; i3 < size3; i3++) {
                        iArr[i2][i3] = valueAt.keyAt(i3);
                        zArr[i2][i3] = valueAt.valueAt(i3);
                    }
                }
                size2 = this.mAccessoryPersistentPermissionMap.size();
                accessoryFilterArr = new android.hardware.usb.AccessoryFilter[size2];
                iArr2 = new int[size2][];
                boolean[][] zArr2 = new boolean[size2][];
                for (int i4 = 0; i4 < size2; i4++) {
                    accessoryFilterArr[i4] = new android.hardware.usb.AccessoryFilter(this.mAccessoryPersistentPermissionMap.keyAt(i4));
                    android.util.SparseBooleanArray valueAt2 = this.mAccessoryPersistentPermissionMap.valueAt(i4);
                    int size4 = valueAt2.size();
                    iArr2[i4] = new int[size4];
                    zArr2[i4] = new boolean[size4];
                    for (int i5 = 0; i5 < size4; i5++) {
                        iArr2[i4][i5] = valueAt2.keyAt(i5);
                        zArr2[i4][i5] = valueAt2.valueAt(i5);
                    }
                }
                this.mIsCopyPermissionsScheduled = false;
            } finally {
            }
        }
        synchronized (this.mPermissionsFile) {
            java.io.FileOutputStream fileOutputStream = null;
            java.lang.String str = null;
            try {
                try {
                    startWrite = this.mPermissionsFile.startWrite();
                } catch (java.io.IOException e) {
                    e = e;
                }
                try {
                    com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(startWrite);
                    resolveSerializer.startDocument((java.lang.String) null, true);
                    resolveSerializer.startTag((java.lang.String) null, "permissions");
                    int i6 = 0;
                    while (i6 < size) {
                        int length = iArr[i6].length;
                        int i7 = i;
                        while (i7 < length) {
                            resolveSerializer.startTag(str, "permission");
                            resolveSerializer.attribute((java.lang.String) null, com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.UID, java.lang.Integer.toString(iArr[i6][i7]));
                            resolveSerializer.attribute((java.lang.String) null, "granted", java.lang.Boolean.toString(zArr[i6][i7]));
                            deviceFilterArr[i6].write(resolveSerializer);
                            resolveSerializer.endTag((java.lang.String) null, "permission");
                            i7++;
                            iArr = iArr;
                            str = null;
                        }
                        i6++;
                        str = null;
                        i = 0;
                    }
                    for (int i8 = 0; i8 < size2; i8++) {
                        int length2 = iArr2[i8].length;
                        for (int i9 = 0; i9 < length2; i9++) {
                            resolveSerializer.startTag((java.lang.String) null, "permission");
                            resolveSerializer.attribute((java.lang.String) null, com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.UID, java.lang.Integer.toString(iArr2[i8][i9]));
                            resolveSerializer.attribute((java.lang.String) null, "granted", java.lang.Boolean.toString(zArr[i8][i9]));
                            accessoryFilterArr[i8].write(resolveSerializer);
                            resolveSerializer.endTag((java.lang.String) null, "permission");
                        }
                    }
                    resolveSerializer.endTag((java.lang.String) null, "permissions");
                    resolveSerializer.endDocument();
                    this.mPermissionsFile.finishWrite(startWrite);
                } catch (java.io.IOException e2) {
                    e = e2;
                    fileOutputStream = startWrite;
                    android.util.Slog.e(TAG, "Failed to write permissions", e);
                    if (fileOutputStream != null) {
                        this.mPermissionsFile.failWrite(fileOutputStream);
                    }
                }
            } finally {
            }
        }
    }

    void requestPermissionDialog(@android.annotation.Nullable android.hardware.usb.UsbDevice usbDevice, @android.annotation.Nullable android.hardware.usb.UsbAccessory usbAccessory, boolean z, @android.annotation.NonNull java.lang.String str, int i, @android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.app.PendingIntent pendingIntent) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                android.content.Intent intent = new android.content.Intent();
                if (usbDevice != null) {
                    intent.putExtra("device", usbDevice);
                } else {
                    intent.putExtra("accessory", usbAccessory);
                }
                intent.putExtra("android.intent.extra.INTENT", pendingIntent);
                intent.putExtra("android.intent.extra.UID", i);
                intent.putExtra("android.hardware.usb.extra.CAN_BE_DEFAULT", z);
                intent.putExtra("android.hardware.usb.extra.PACKAGE", str);
                intent.setComponent(android.content.ComponentName.unflattenFromString(context.getResources().getString(android.R.string.config_slicePermissionComponent)));
                intent.addFlags(268435456);
                context.startActivityAsUser(intent, this.mUser);
            } catch (android.content.ActivityNotFoundException e) {
                android.util.Slog.e(TAG, "unable to start UsbPermissionActivity");
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    void dump(@android.annotation.NonNull com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream, java.lang.String str, long j) {
        long j2;
        long j3;
        long j4;
        long start = dualDumpOutputStream.start(str, j);
        synchronized (this.mLock) {
            try {
                dualDumpOutputStream.write("user_id", 1120986464257L, this.mUser.getIdentifier());
                int size = this.mDevicePermissionMap.size();
                int i = 0;
                while (true) {
                    j2 = 1138166333441L;
                    if (i >= size) {
                        break;
                    }
                    java.lang.String keyAt = this.mDevicePermissionMap.keyAt(i);
                    long start2 = dualDumpOutputStream.start("device_permissions", 2246267895810L);
                    dualDumpOutputStream.write("device_name", 1138166333441L, keyAt);
                    android.util.SparseBooleanArray valueAt = this.mDevicePermissionMap.valueAt(i);
                    int size2 = valueAt.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        dualDumpOutputStream.write("uids", 2220498092034L, valueAt.keyAt(i2));
                    }
                    dualDumpOutputStream.end(start2);
                    i++;
                }
                int size3 = this.mAccessoryPermissionMap.size();
                int i3 = 0;
                while (i3 < size3) {
                    android.hardware.usb.UsbAccessory keyAt2 = this.mAccessoryPermissionMap.keyAt(i3);
                    long start3 = dualDumpOutputStream.start("accessory_permissions", 2246267895811L);
                    dualDumpOutputStream.write("accessory_description", j2, keyAt2.getDescription());
                    android.util.SparseBooleanArray valueAt2 = this.mAccessoryPermissionMap.valueAt(i3);
                    int size4 = valueAt2.size();
                    for (int i4 = 0; i4 < size4; i4++) {
                        dualDumpOutputStream.write("uids", 2220498092034L, valueAt2.keyAt(i4));
                    }
                    dualDumpOutputStream.end(start3);
                    i3++;
                    j2 = 1138166333441L;
                }
                int size5 = this.mDevicePersistentPermissionMap.size();
                int i5 = 0;
                while (true) {
                    j3 = 1146756268033L;
                    if (i5 >= size5) {
                        break;
                    }
                    android.hardware.usb.DeviceFilter keyAt3 = this.mDevicePersistentPermissionMap.keyAt(i5);
                    long start4 = dualDumpOutputStream.start("device_persistent_permissions", 2246267895812L);
                    keyAt3.dump(dualDumpOutputStream, "device", 1146756268033L);
                    android.util.SparseBooleanArray valueAt3 = this.mDevicePersistentPermissionMap.valueAt(i5);
                    int size6 = valueAt3.size();
                    int i6 = 0;
                    while (i6 < size6) {
                        long start5 = dualDumpOutputStream.start("uid_permission", 2246267895810L);
                        dualDumpOutputStream.write(com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.UID, 1120986464257L, valueAt3.keyAt(i6));
                        dualDumpOutputStream.write("is_granted", 1133871366146L, valueAt3.valueAt(i6));
                        dualDumpOutputStream.end(start5);
                        i6++;
                        start = start;
                    }
                    dualDumpOutputStream.end(start4);
                    i5++;
                    start = start;
                }
                j4 = start;
                int size7 = this.mAccessoryPersistentPermissionMap.size();
                int i7 = 0;
                while (i7 < size7) {
                    android.hardware.usb.AccessoryFilter keyAt4 = this.mAccessoryPersistentPermissionMap.keyAt(i7);
                    long start6 = dualDumpOutputStream.start("accessory_persistent_permissions", 2246267895813L);
                    keyAt4.dump(dualDumpOutputStream, "accessory", j3);
                    android.util.SparseBooleanArray valueAt4 = this.mAccessoryPersistentPermissionMap.valueAt(i7);
                    int size8 = valueAt4.size();
                    for (int i8 = 0; i8 < size8; i8++) {
                        long start7 = dualDumpOutputStream.start("uid_permission", 2246267895810L);
                        dualDumpOutputStream.write(com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.UID, 1120986464257L, valueAt4.keyAt(i8));
                        dualDumpOutputStream.write("is_granted", 1133871366146L, valueAt4.valueAt(i8));
                        dualDumpOutputStream.end(start7);
                    }
                    dualDumpOutputStream.end(start6);
                    i7++;
                    j3 = 1146756268033L;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        dualDumpOutputStream.end(j4);
    }

    private boolean isCameraPermissionGranted(java.lang.String str, int i, int i2) {
        try {
            android.content.pm.ApplicationInfo applicationInfo = this.mContext.getPackageManager().getApplicationInfo(str, 0);
            if (applicationInfo.uid != i2) {
                android.util.Slog.i(TAG, "Package " + str + " does not match caller's uid " + i2);
                return false;
            }
            if (applicationInfo.targetSdkVersion >= 28 && -1 == this.mContext.checkPermission("android.permission.CAMERA", i, i2)) {
                android.util.Slog.i(TAG, "Camera permission required for USB video class devices");
                return false;
            }
            return true;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.i(TAG, "Package not found, likely due to invalid package name!");
            return false;
        }
    }

    public void checkPermission(android.hardware.usb.UsbDevice usbDevice, java.lang.String str, int i, int i2) {
        if (!hasPermission(usbDevice, str, i, i2)) {
            throw new java.lang.SecurityException("User has not given " + i2 + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + str + " permission to access device " + usbDevice.getDeviceName());
        }
    }

    public void checkPermission(android.hardware.usb.UsbAccessory usbAccessory, int i, int i2) {
        if (!hasPermission(usbAccessory, i, i2)) {
            throw new java.lang.SecurityException("User has not given " + i2 + " permission to accessory " + usbAccessory);
        }
    }

    private void requestPermissionDialog(@android.annotation.Nullable android.hardware.usb.UsbDevice usbDevice, @android.annotation.Nullable android.hardware.usb.UsbAccessory usbAccessory, boolean z, java.lang.String str, android.app.PendingIntent pendingIntent, int i) {
        try {
            boolean z2 = false;
            if (this.mContext.getPackageManager().getApplicationInfo(str, 0).uid != i) {
                android.util.Slog.w(TAG, "package " + str + " does not match caller's uid " + i);
                android.util.EventLog.writeEvent(SNET_EVENT_LOG_ID, "180104273", -1, "");
                z2 = true;
            }
            if (z2) {
                throw new java.lang.IllegalArgumentException("package " + str + " not found");
            }
            requestPermissionDialog(usbDevice, usbAccessory, z, str, i, this.mContext, pendingIntent);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            throw new java.lang.IllegalArgumentException("package " + str + " not found");
        }
    }

    public void requestPermission(android.hardware.usb.UsbDevice usbDevice, java.lang.String str, android.app.PendingIntent pendingIntent, int i, int i2) {
        android.content.Intent intent = new android.content.Intent();
        if (hasPermission(usbDevice, str, i, i2)) {
            intent.putExtra("device", usbDevice);
            intent.putExtra("permission", true);
            try {
                pendingIntent.send(this.mContext, 0, intent);
                return;
            } catch (android.app.PendingIntent.CanceledException e) {
                return;
            }
        }
        if (usbDevice.getHasVideoCapture() && !isCameraPermissionGranted(str, i, i2)) {
            intent.putExtra("device", usbDevice);
            intent.putExtra("permission", false);
            try {
                pendingIntent.send(this.mContext, 0, intent);
                return;
            } catch (android.app.PendingIntent.CanceledException e2) {
                return;
            }
        }
        requestPermissionDialog(usbDevice, null, this.mUsbUserSettingsManager.canBeDefault(usbDevice, str), str, pendingIntent, i2);
    }

    public void requestPermission(android.hardware.usb.UsbAccessory usbAccessory, java.lang.String str, android.app.PendingIntent pendingIntent, int i, int i2) {
        if (hasPermission(usbAccessory, i, i2)) {
            android.content.Intent intent = new android.content.Intent();
            intent.putExtra("accessory", usbAccessory);
            intent.putExtra("permission", true);
            try {
                pendingIntent.send(this.mContext, 0, intent);
                return;
            } catch (android.app.PendingIntent.CanceledException e) {
                return;
            }
        }
        requestPermissionDialog(null, usbAccessory, this.mUsbUserSettingsManager.canBeDefault(usbAccessory, str), str, pendingIntent, i2);
    }
}
