package com.android.server.usb;

/* loaded from: classes2.dex */
public class UsbHostManager {
    private static final boolean DEBUG = false;
    private static final int LINUX_FOUNDATION_VID = 7531;
    private static final int MAX_CONNECT_RECORDS = 32;
    private static final int MAX_UNIQUE_CODE_GENERATION_ATTEMPTS = 10;
    private static final java.lang.String TAG = com.android.server.usb.UsbHostManager.class.getSimpleName();
    static final java.text.SimpleDateFormat sFormat = new java.text.SimpleDateFormat("MM-dd HH:mm:ss:SSS");
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mSettingsLock"})
    private com.android.server.usb.UsbProfileGroupSettingsManager mCurrentSettings;
    private final boolean mHasMidiFeature;
    private final java.lang.String[] mHostDenyList;
    private com.android.server.usb.UsbHostManager.ConnectionRecord mLastConnect;
    private int mNumConnects;
    private final com.android.server.usb.UsbPermissionManager mPermissionManager;
    private final com.android.server.usb.UsbAlsaManager mUsbAlsaManager;

    @com.android.internal.annotations.GuardedBy({"mHandlerLock"})
    private android.content.ComponentName mUsbDeviceConnectionHandler;
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.HashMap<java.lang.String, android.hardware.usb.UsbDevice> mDevices = new java.util.HashMap<>();
    private java.lang.Object mSettingsLock = new java.lang.Object();
    private java.lang.Object mHandlerLock = new java.lang.Object();
    private final java.util.LinkedList<com.android.server.usb.UsbHostManager.ConnectionRecord> mConnections = new java.util.LinkedList<>();
    private final android.util.ArrayMap<java.lang.String, com.android.server.usb.UsbHostManager.ConnectionRecord> mConnected = new android.util.ArrayMap<>();
    private final java.util.HashMap<java.lang.String, java.util.ArrayList<com.android.server.usb.UsbDirectMidiDevice>> mMidiDevices = new java.util.HashMap<>();
    private final java.util.HashSet<java.lang.String> mMidiUniqueCodes = new java.util.HashSet<>();
    private final java.util.Random mRandom = new java.util.Random();

    /* JADX INFO: Access modifiers changed from: private */
    public native void monitorUsbHostBus();

    private native android.os.ParcelFileDescriptor nativeOpenDevice(java.lang.String str);

    class ConnectionRecord {
        static final int CONNECT = 0;
        static final int CONNECT_BADDEVICE = 2;
        static final int CONNECT_BADPARSE = 1;
        static final int DISCONNECT = -1;
        private static final int kDumpBytesPerLine = 16;
        final byte[] mDescriptors;
        java.lang.String mDeviceAddress;
        final int mMode;
        long mTimestamp = java.lang.System.currentTimeMillis();

        ConnectionRecord(java.lang.String str, int i, byte[] bArr) {
            this.mDeviceAddress = str;
            this.mMode = i;
            this.mDescriptors = bArr;
        }

        private java.lang.String formatTime() {
            return new java.lang.StringBuilder(com.android.server.usb.UsbHostManager.sFormat.format(new java.util.Date(this.mTimestamp))).toString();
        }

        void dump(@android.annotation.NonNull com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream, java.lang.String str, long j) {
            long start = dualDumpOutputStream.start(str, j);
            dualDumpOutputStream.write("device_address", 1138166333441L, this.mDeviceAddress);
            dualDumpOutputStream.write(com.android.server.app.GameManagerService.GamePackageConfiguration.GameModeConfiguration.MODE_KEY, 1159641169922L, this.mMode);
            dualDumpOutputStream.write(com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.TIMESTAMP, 1112396529667L, this.mTimestamp);
            if (this.mMode != -1) {
                com.android.server.usb.descriptors.UsbDescriptorParser usbDescriptorParser = new com.android.server.usb.descriptors.UsbDescriptorParser(this.mDeviceAddress, this.mDescriptors);
                com.android.server.usb.descriptors.UsbDeviceDescriptor deviceDescriptor = usbDescriptorParser.getDeviceDescriptor();
                dualDumpOutputStream.write("manufacturer", 1120986464260L, deviceDescriptor.getVendorID());
                dualDumpOutputStream.write("product", 1120986464261L, deviceDescriptor.getProductID());
                long start2 = dualDumpOutputStream.start("is_headset", 1146756268038L);
                dualDumpOutputStream.write("in", 1133871366145L, usbDescriptorParser.isInputHeadset());
                dualDumpOutputStream.write("out", 1133871366146L, usbDescriptorParser.isOutputHeadset());
                dualDumpOutputStream.end(start2);
            }
            dualDumpOutputStream.end(start);
        }

        void dumpShort(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
            if (this.mMode != -1) {
                indentingPrintWriter.println(formatTime() + " Connect " + this.mDeviceAddress + " mode:" + this.mMode);
                com.android.server.usb.descriptors.UsbDescriptorParser usbDescriptorParser = new com.android.server.usb.descriptors.UsbDescriptorParser(this.mDeviceAddress, this.mDescriptors);
                com.android.server.usb.descriptors.UsbDeviceDescriptor deviceDescriptor = usbDescriptorParser.getDeviceDescriptor();
                indentingPrintWriter.println("manfacturer:0x" + java.lang.Integer.toHexString(deviceDescriptor.getVendorID()) + " product:" + java.lang.Integer.toHexString(deviceDescriptor.getProductID()));
                indentingPrintWriter.println("isHeadset[in: " + usbDescriptorParser.isInputHeadset() + " , out: " + usbDescriptorParser.isOutputHeadset() + "], isDock: " + usbDescriptorParser.isDock());
                return;
            }
            indentingPrintWriter.println(formatTime() + " Disconnect " + this.mDeviceAddress);
        }

        void dumpTree(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
            if (this.mMode != -1) {
                indentingPrintWriter.println(formatTime() + " Connect " + this.mDeviceAddress + " mode:" + this.mMode);
                com.android.server.usb.descriptors.UsbDescriptorParser usbDescriptorParser = new com.android.server.usb.descriptors.UsbDescriptorParser(this.mDeviceAddress, this.mDescriptors);
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                com.android.server.usb.descriptors.tree.UsbDescriptorsTree usbDescriptorsTree = new com.android.server.usb.descriptors.tree.UsbDescriptorsTree();
                usbDescriptorsTree.parse(usbDescriptorParser);
                usbDescriptorsTree.report(new com.android.server.usb.descriptors.report.TextReportCanvas(usbDescriptorParser, sb));
                sb.append("isHeadset[in: " + usbDescriptorParser.isInputHeadset() + " , out: " + usbDescriptorParser.isOutputHeadset() + "], isDock: " + usbDescriptorParser.isDock());
                indentingPrintWriter.println(sb.toString());
                return;
            }
            indentingPrintWriter.println(formatTime() + " Disconnect " + this.mDeviceAddress);
        }

        void dumpList(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
            if (this.mMode != -1) {
                indentingPrintWriter.println(formatTime() + " Connect " + this.mDeviceAddress + " mode:" + this.mMode);
                com.android.server.usb.descriptors.UsbDescriptorParser usbDescriptorParser = new com.android.server.usb.descriptors.UsbDescriptorParser(this.mDeviceAddress, this.mDescriptors);
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                com.android.server.usb.descriptors.report.TextReportCanvas textReportCanvas = new com.android.server.usb.descriptors.report.TextReportCanvas(usbDescriptorParser, sb);
                java.util.Iterator<com.android.server.usb.descriptors.UsbDescriptor> it = usbDescriptorParser.getDescriptors().iterator();
                while (it.hasNext()) {
                    it.next().report(textReportCanvas);
                }
                indentingPrintWriter.println(sb.toString());
                indentingPrintWriter.println("isHeadset[in: " + usbDescriptorParser.isInputHeadset() + " , out: " + usbDescriptorParser.isOutputHeadset() + "], isDock: " + usbDescriptorParser.isDock());
                return;
            }
            indentingPrintWriter.println(formatTime() + " Disconnect " + this.mDeviceAddress);
        }

        void dumpRaw(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
            if (this.mMode != -1) {
                indentingPrintWriter.println(formatTime() + " Connect " + this.mDeviceAddress + " mode:" + this.mMode);
                int length = this.mDescriptors.length;
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("Raw Descriptors ");
                sb.append(length);
                sb.append(" bytes");
                indentingPrintWriter.println(sb.toString());
                int i = 0;
                for (int i2 = 0; i2 < length / 16; i2++) {
                    java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
                    int i3 = 0;
                    while (i3 < 16) {
                        sb2.append(java.lang.String.format("0x%02X", java.lang.Byte.valueOf(this.mDescriptors[i])));
                        sb2.append(" ");
                        i3++;
                        i++;
                    }
                    indentingPrintWriter.println(sb2.toString());
                }
                java.lang.StringBuilder sb3 = new java.lang.StringBuilder();
                while (i < length) {
                    sb3.append(java.lang.String.format("0x%02X", java.lang.Byte.valueOf(this.mDescriptors[i])));
                    sb3.append(" ");
                    i++;
                }
                indentingPrintWriter.println(sb3.toString());
                return;
            }
            indentingPrintWriter.println(formatTime() + " Disconnect " + this.mDeviceAddress);
        }
    }

    public UsbHostManager(android.content.Context context, com.android.server.usb.UsbAlsaManager usbAlsaManager, com.android.server.usb.UsbPermissionManager usbPermissionManager) {
        this.mContext = context;
        this.mHostDenyList = context.getResources().getStringArray(android.R.array.config_udfps_enroll_stage_thresholds);
        this.mUsbAlsaManager = usbAlsaManager;
        this.mPermissionManager = usbPermissionManager;
        java.lang.String string = context.getResources().getString(android.R.string.concurrent_display_notification_active_title);
        if (!android.text.TextUtils.isEmpty(string)) {
            setUsbDeviceConnectionHandler(android.content.ComponentName.unflattenFromString(string));
        }
        this.mHasMidiFeature = context.getPackageManager().hasSystemFeature("android.software.midi");
    }

    public void setCurrentUserSettings(com.android.server.usb.UsbProfileGroupSettingsManager usbProfileGroupSettingsManager) {
        synchronized (this.mSettingsLock) {
            this.mCurrentSettings = usbProfileGroupSettingsManager;
        }
    }

    private com.android.server.usb.UsbProfileGroupSettingsManager getCurrentUserSettings() {
        com.android.server.usb.UsbProfileGroupSettingsManager usbProfileGroupSettingsManager;
        synchronized (this.mSettingsLock) {
            usbProfileGroupSettingsManager = this.mCurrentSettings;
        }
        return usbProfileGroupSettingsManager;
    }

    public void setUsbDeviceConnectionHandler(@android.annotation.Nullable android.content.ComponentName componentName) {
        synchronized (this.mHandlerLock) {
            this.mUsbDeviceConnectionHandler = componentName;
        }
    }

    @android.annotation.Nullable
    private android.content.ComponentName getUsbDeviceConnectionHandler() {
        android.content.ComponentName componentName;
        synchronized (this.mHandlerLock) {
            componentName = this.mUsbDeviceConnectionHandler;
        }
        return componentName;
    }

    private boolean isDenyListed(java.lang.String str) {
        int length = this.mHostDenyList.length;
        for (int i = 0; i < length; i++) {
            if (str.startsWith(this.mHostDenyList[i])) {
                return true;
            }
        }
        return false;
    }

    private boolean isDenyListed(int i, int i2) {
        if (i == 9) {
            return true;
        }
        return i == 3 && i2 == 1;
    }

    private void addConnectionRecord(java.lang.String str, int i, byte[] bArr) {
        this.mNumConnects++;
        while (this.mConnections.size() >= 32) {
            this.mConnections.removeFirst();
        }
        com.android.server.usb.UsbHostManager.ConnectionRecord connectionRecord = new com.android.server.usb.UsbHostManager.ConnectionRecord(str, i, bArr);
        this.mConnections.add(connectionRecord);
        if (i != -1) {
            this.mLastConnect = connectionRecord;
        }
        if (i == 0) {
            this.mConnected.put(str, connectionRecord);
        } else if (i == -1) {
            this.mConnected.remove(str);
        }
    }

    private void logUsbDevice(com.android.server.usb.descriptors.UsbDescriptorParser usbDescriptorParser) {
        int i;
        java.lang.String str;
        int i2;
        java.lang.String str2;
        java.lang.String str3;
        java.lang.String str4;
        com.android.server.usb.descriptors.UsbDeviceDescriptor deviceDescriptor = usbDescriptorParser.getDeviceDescriptor();
        if (deviceDescriptor == null) {
            i = 0;
            str = "<unknown>";
            i2 = 0;
            str2 = "<unknown>";
            str3 = str2;
            str4 = str3;
        } else {
            i = deviceDescriptor.getVendorID();
            i2 = deviceDescriptor.getProductID();
            str = deviceDescriptor.getMfgString(usbDescriptorParser);
            str3 = deviceDescriptor.getProductString(usbDescriptorParser);
            str4 = deviceDescriptor.getDeviceReleaseString();
            str2 = deviceDescriptor.getSerialString(usbDescriptorParser);
        }
        if (i == LINUX_FOUNDATION_VID) {
            return;
        }
        boolean hasAudioInterface = usbDescriptorParser.hasAudioInterface();
        boolean hasHIDInterface = usbDescriptorParser.hasHIDInterface();
        boolean hasStorageInterface = usbDescriptorParser.hasStorageInterface();
        android.util.Slog.d(TAG, (("USB device attached: " + java.lang.String.format("vidpid %04x:%04x", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2))) + java.lang.String.format(" mfg/product/ver/serial %s/%s/%s/%s", str, str3, str4, str2)) + java.lang.String.format(" hasAudio/HID/Storage: %b/%b/%b", java.lang.Boolean.valueOf(hasAudioInterface), java.lang.Boolean.valueOf(hasHIDInterface), java.lang.Boolean.valueOf(hasStorageInterface)));
    }

    private boolean usbDeviceAdded(java.lang.String str, int i, int i2, byte[] bArr) {
        if (isDenyListed(str) || isDenyListed(i, i2)) {
            return false;
        }
        com.android.server.usb.descriptors.UsbDescriptorParser usbDescriptorParser = new com.android.server.usb.descriptors.UsbDescriptorParser(str, bArr);
        if (i == 0 && !checkUsbInterfacesDenyListed(usbDescriptorParser)) {
            return false;
        }
        logUsbDevice(usbDescriptorParser);
        synchronized (this.mLock) {
            try {
                if (this.mDevices.get(str) != null) {
                    android.util.Slog.w(TAG, "device already on mDevices list: " + str);
                    return false;
                }
                android.hardware.usb.UsbDevice.Builder androidUsbDeviceBuilder = usbDescriptorParser.toAndroidUsbDeviceBuilder();
                if (androidUsbDeviceBuilder == null) {
                    android.util.Slog.e(TAG, "Couldn't create UsbDevice object.");
                    addConnectionRecord(str, 2, usbDescriptorParser.getRawDescriptors());
                } else {
                    com.android.server.usb.UsbSerialReader usbSerialReader = new com.android.server.usb.UsbSerialReader(this.mContext, this.mPermissionManager, androidUsbDeviceBuilder.serialNumber);
                    android.hardware.usb.UsbDevice build = androidUsbDeviceBuilder.build(usbSerialReader);
                    usbSerialReader.setDevice(build);
                    this.mDevices.put(str, build);
                    android.util.Slog.d(TAG, "Added device " + build);
                    android.content.ComponentName usbDeviceConnectionHandler = getUsbDeviceConnectionHandler();
                    if (usbDeviceConnectionHandler == null) {
                        getCurrentUserSettings().deviceAttached(build);
                    } else {
                        getCurrentUserSettings().deviceAttachedForFixedHandler(build, usbDeviceConnectionHandler);
                    }
                    this.mUsbAlsaManager.usbDeviceAdded(str, build, usbDescriptorParser);
                    if (this.mHasMidiFeature) {
                        java.lang.String generateNewUsbDeviceIdentifier = generateNewUsbDeviceIdentifier();
                        java.util.ArrayList<com.android.server.usb.UsbDirectMidiDevice> arrayList = new java.util.ArrayList<>();
                        if (usbDescriptorParser.containsUniversalMidiDeviceEndpoint()) {
                            com.android.server.usb.UsbDirectMidiDevice create = com.android.server.usb.UsbDirectMidiDevice.create(this.mContext, build, usbDescriptorParser, true, generateNewUsbDeviceIdentifier);
                            if (create != null) {
                                arrayList.add(create);
                            } else {
                                android.util.Slog.e(TAG, "Universal Midi Device is null.");
                            }
                            if (usbDescriptorParser.containsLegacyMidiDeviceEndpoint()) {
                                com.android.server.usb.UsbDirectMidiDevice create2 = com.android.server.usb.UsbDirectMidiDevice.create(this.mContext, build, usbDescriptorParser, false, generateNewUsbDeviceIdentifier);
                                if (create2 != null) {
                                    arrayList.add(create2);
                                } else {
                                    android.util.Slog.e(TAG, "Legacy Midi Device is null.");
                                }
                            }
                        }
                        if (!arrayList.isEmpty()) {
                            this.mMidiDevices.put(str, arrayList);
                        }
                    }
                    addConnectionRecord(str, 0, usbDescriptorParser.getRawDescriptors());
                    com.android.internal.util.FrameworkStatsLog.write(77, build.getVendorId(), build.getProductId(), usbDescriptorParser.hasAudioInterface(), usbDescriptorParser.hasHIDInterface(), usbDescriptorParser.hasStorageInterface(), 1, 0L);
                }
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void usbDeviceRemoved(java.lang.String str) {
        synchronized (this.mLock) {
            try {
                android.hardware.usb.UsbDevice remove = this.mDevices.remove(str);
                if (remove != null) {
                    android.util.Slog.d(TAG, "Removed device at " + str + ": " + remove.getProductName());
                    this.mUsbAlsaManager.usbDeviceRemoved(str);
                    this.mPermissionManager.usbDeviceRemoved(remove);
                    java.util.ArrayList<com.android.server.usb.UsbDirectMidiDevice> remove2 = this.mMidiDevices.remove(str);
                    if (remove2 != null) {
                        java.util.Iterator<com.android.server.usb.UsbDirectMidiDevice> it = remove2.iterator();
                        while (it.hasNext()) {
                            com.android.server.usb.UsbDirectMidiDevice next = it.next();
                            if (next != null) {
                                libcore.io.IoUtils.closeQuietly(next);
                            }
                        }
                        android.util.Slog.i(TAG, "USB MIDI Devices Removed: " + str);
                    }
                    getCurrentUserSettings().usbDeviceRemoved(remove);
                    com.android.server.usb.UsbHostManager.ConnectionRecord connectionRecord = this.mConnected.get(str);
                    addConnectionRecord(str, -1, null);
                    if (connectionRecord != null) {
                        com.android.server.usb.descriptors.UsbDescriptorParser usbDescriptorParser = new com.android.server.usb.descriptors.UsbDescriptorParser(str, connectionRecord.mDescriptors);
                        com.android.internal.util.FrameworkStatsLog.write(77, remove.getVendorId(), remove.getProductId(), usbDescriptorParser.hasAudioInterface(), usbDescriptorParser.hasHIDInterface(), usbDescriptorParser.hasStorageInterface(), 0, java.lang.System.currentTimeMillis() - connectionRecord.mTimestamp);
                    }
                } else {
                    android.util.Slog.d(TAG, "Removed device at " + str + " was already gone");
                }
            } finally {
            }
        }
    }

    public void systemReady() {
        synchronized (this.mLock) {
            new java.lang.Thread(null, new java.lang.Runnable() { // from class: com.android.server.usb.UsbHostManager$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.usb.UsbHostManager.this.monitorUsbHostBus();
                }
            }, "UsbService host thread").start();
        }
    }

    public void getDeviceList(android.os.Bundle bundle) {
        synchronized (this.mLock) {
            try {
                for (java.lang.String str : this.mDevices.keySet()) {
                    bundle.putParcelable(str, this.mDevices.get(str));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public android.os.ParcelFileDescriptor openDevice(java.lang.String str, com.android.server.usb.UsbUserPermissionManager usbUserPermissionManager, java.lang.String str2, int i, int i2) {
        android.os.ParcelFileDescriptor nativeOpenDevice;
        synchronized (this.mLock) {
            try {
                if (isDenyListed(str)) {
                    throw new java.lang.SecurityException("USB device is on a restricted bus");
                }
                android.hardware.usb.UsbDevice usbDevice = this.mDevices.get(str);
                if (usbDevice == null) {
                    throw new java.lang.IllegalArgumentException("device " + str + " does not exist or is restricted");
                }
                usbUserPermissionManager.checkPermission(usbDevice, str2, i, i2);
                nativeOpenDevice = nativeOpenDevice(str);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return nativeOpenDevice;
    }

    public void dump(com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream, java.lang.String str, long j) {
        long start = dualDumpOutputStream.start(str, j);
        synchronized (this.mHandlerLock) {
            try {
                if (this.mUsbDeviceConnectionHandler != null) {
                    com.android.internal.util.dump.DumpUtils.writeComponentName(dualDumpOutputStream, "default_usb_host_connection_handler", 1146756268033L, this.mUsbDeviceConnectionHandler);
                }
            } finally {
            }
        }
        synchronized (this.mLock) {
            try {
                java.util.Iterator<java.lang.String> it = this.mDevices.keySet().iterator();
                while (it.hasNext()) {
                    com.android.internal.usb.DumpUtils.writeDevice(dualDumpOutputStream, "devices", 2246267895810L, this.mDevices.get(it.next()));
                }
                dualDumpOutputStream.write("num_connects", 1120986464259L, this.mNumConnects);
                java.util.Iterator<com.android.server.usb.UsbHostManager.ConnectionRecord> it2 = this.mConnections.iterator();
                while (it2.hasNext()) {
                    it2.next().dump(dualDumpOutputStream, "connections", 2246267895812L);
                }
                java.util.Iterator<java.util.ArrayList<com.android.server.usb.UsbDirectMidiDevice>> it3 = this.mMidiDevices.values().iterator();
                while (it3.hasNext()) {
                    java.util.Iterator<com.android.server.usb.UsbDirectMidiDevice> it4 = it3.next().iterator();
                    while (it4.hasNext()) {
                        it4.next().dump(dualDumpOutputStream, "midi_devices", 2246267895813L);
                    }
                }
            } finally {
            }
        }
        dualDumpOutputStream.end(start);
    }

    public void dumpDescriptors(com.android.internal.util.IndentingPrintWriter indentingPrintWriter, java.lang.String[] strArr) {
        if (this.mLastConnect != null) {
            indentingPrintWriter.println("Last Connected USB Device:");
            if (strArr.length <= 1 || strArr[1].equals("-dump-short")) {
                this.mLastConnect.dumpShort(indentingPrintWriter);
                return;
            }
            if (strArr[1].equals("-dump-tree")) {
                this.mLastConnect.dumpTree(indentingPrintWriter);
                return;
            } else if (strArr[1].equals("-dump-list")) {
                this.mLastConnect.dumpList(indentingPrintWriter);
                return;
            } else {
                if (strArr[1].equals("-dump-raw")) {
                    this.mLastConnect.dumpRaw(indentingPrintWriter);
                    return;
                }
                return;
            }
        }
        indentingPrintWriter.println("No USB Devices have been connected.");
    }

    private boolean checkUsbInterfacesDenyListed(com.android.server.usb.descriptors.UsbDescriptorParser usbDescriptorParser) {
        java.util.Iterator<com.android.server.usb.descriptors.UsbDescriptor> it = usbDescriptorParser.getDescriptors().iterator();
        boolean z = false;
        while (it.hasNext()) {
            com.android.server.usb.descriptors.UsbDescriptor next = it.next();
            if (next instanceof com.android.server.usb.descriptors.UsbInterfaceDescriptor) {
                com.android.server.usb.descriptors.UsbInterfaceDescriptor usbInterfaceDescriptor = (com.android.server.usb.descriptors.UsbInterfaceDescriptor) next;
                z = isDenyListed(usbInterfaceDescriptor.getUsbClass(), usbInterfaceDescriptor.getUsbSubclass());
                if (!z) {
                    break;
                }
            }
        }
        return !z;
    }

    private java.lang.String generateNewUsbDeviceIdentifier() {
        java.lang.String str;
        int i = 0;
        do {
            if (i > 10) {
                android.util.Slog.w(TAG, "MIDI unique code array resetting");
                this.mMidiUniqueCodes.clear();
                i = 0;
            }
            str = "";
            for (int i2 = 0; i2 < 3; i2++) {
                str = str + this.mRandom.nextInt(10);
            }
            i++;
        } while (this.mMidiUniqueCodes.contains(str));
        this.mMidiUniqueCodes.add(str);
        return str;
    }
}
