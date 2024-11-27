package com.android.server.accessibility;

/* loaded from: classes.dex */
class BrailleDisplayConnection extends android.accessibilityservice.IBrailleDisplayConnection.Stub {
    static final int BUS_BLUETOOTH = 5;
    static final int BUS_UNKNOWN = -1;
    static final int BUS_USB = 3;
    private static final java.lang.String LOG_TAG = "BrailleDisplayConnection";
    private static final java.util.Set<java.io.File> sConnectedNodes = new android.util.ArraySet();
    private android.accessibilityservice.IBrailleDisplayController mController;
    private java.io.File mHidrawNode;
    private java.lang.Thread mInputThread;
    private final java.lang.Object mLock;
    private java.io.OutputStream mOutputStream;
    private android.os.HandlerThread mOutputThread;
    private com.android.server.accessibility.BrailleDisplayConnection.BrailleDisplayScanner mScanner;
    private final com.android.server.accessibility.AccessibilityServiceConnection mServiceConnection;

    interface BrailleDisplayScanner {
        int getDeviceBusType(@android.annotation.NonNull java.nio.file.Path path);

        byte[] getDeviceReportDescriptor(@android.annotation.NonNull java.nio.file.Path path);

        java.util.Collection<java.nio.file.Path> getHidrawNodePaths(@android.annotation.NonNull java.nio.file.Path path);

        java.lang.String getUniqueId(@android.annotation.NonNull java.nio.file.Path path);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface BusType {
    }

    @com.android.internal.annotations.VisibleForTesting
    interface NativeInterface {
        int getHidrawBusType(int i);

        byte[] getHidrawDesc(int i, int i2);

        int getHidrawDescSize(int i);

        java.lang.String getHidrawUniq(int i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native int nativeGetHidrawBusType(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native byte[] nativeGetHidrawDesc(int i, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native int nativeGetHidrawDescSize(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native java.lang.String nativeGetHidrawUniq(int i);

    BrailleDisplayConnection(@android.annotation.NonNull java.lang.Object obj, @android.annotation.NonNull com.android.server.accessibility.AccessibilityServiceConnection accessibilityServiceConnection) {
        java.util.Objects.requireNonNull(obj);
        this.mLock = obj;
        this.mScanner = getDefaultNativeScanner(new com.android.server.accessibility.BrailleDisplayConnection.DefaultNativeInterface());
        java.util.Objects.requireNonNull(accessibilityServiceConnection);
        this.mServiceConnection = accessibilityServiceConnection;
    }

    static com.android.server.accessibility.BrailleDisplayConnection.BrailleDisplayScanner createScannerForShell() {
        return getDefaultNativeScanner(new com.android.server.accessibility.BrailleDisplayConnection.DefaultNativeInterface());
    }

    void connectLocked(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.NonNull android.accessibilityservice.IBrailleDisplayController iBrailleDisplayController) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(iBrailleDisplayController);
        this.mController = iBrailleDisplayController;
        java.nio.file.Path of = java.nio.file.Path.of("/dev", new java.lang.String[0]);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Collection<java.nio.file.Path> hidrawNodePaths = this.mScanner.getHidrawNodePaths(of);
        if (hidrawNodePaths == null) {
            android.util.Slog.w(LOG_TAG, "Unable to access the HIDRAW node directory");
            sendConnectionErrorLocked(1);
            return;
        }
        boolean z = false;
        for (java.nio.file.Path path : hidrawNodePaths) {
            byte[] deviceReportDescriptor = this.mScanner.getDeviceReportDescriptor(path);
            if (deviceReportDescriptor == null) {
                z = true;
            } else {
                java.lang.String uniqueId = this.mScanner.getUniqueId(path);
                if (isBrailleDisplay(deviceReportDescriptor) && this.mScanner.getDeviceBusType(path) == i && str.equalsIgnoreCase(uniqueId)) {
                    arrayList.add(android.util.Pair.create(path.toFile(), deviceReportDescriptor));
                }
            }
        }
        int i2 = 2;
        if (arrayList.size() != 1) {
            if (z) {
                android.util.Slog.w(LOG_TAG, "Unable to access some HIDRAW node's descriptor");
                i2 = 3;
            } else {
                android.util.Slog.w(LOG_TAG, "Unable to find a unique Braille display matching the provided device");
            }
            sendConnectionErrorLocked(i2);
            return;
        }
        this.mHidrawNode = (java.io.File) ((android.util.Pair) arrayList.get(0)).first;
        byte[] bArr = (byte[]) ((android.util.Pair) arrayList.get(0)).second;
        if (sConnectedNodes.contains(this.mHidrawNode)) {
            android.util.Slog.w(LOG_TAG, "Unable to find an unused Braille display matching the provided device");
            sendConnectionErrorLocked(2);
            return;
        }
        sConnectedNodes.add(this.mHidrawNode);
        startReadingLocked();
        try {
            this.mServiceConnection.onBrailleDisplayConnectedLocked(this);
            this.mController.onConnected(this, bArr);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(LOG_TAG, "Error calling onConnected", e);
            disconnect();
        }
    }

    private void sendConnectionErrorLocked(int i) {
        try {
            this.mController.onConnectionFailed(i);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(LOG_TAG, "Error calling onConnectionFailed", e);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static boolean isBrailleDisplay(byte[] bArr) {
        int i = 0;
        boolean z = false;
        while (i < bArr.length) {
            byte b = bArr[i];
            if (!isHidItemShort(b)) {
                android.util.Slog.w(LOG_TAG, "Item " + ((int) b) + " declares unsupported long type");
                return false;
            }
            int hidItemDataSize = getHidItemDataSize(b);
            int i2 = i + hidItemDataSize;
            if (i2 >= bArr.length) {
                android.util.Slog.w(LOG_TAG, "Item " + ((int) b) + " specifies size past the remaining bytes");
                return false;
            }
            if (hidItemDataSize == 1 && isHidItemBrailleDisplayUsagePage(b, bArr[i + 1])) {
                z = true;
            }
            i = i2 + 1;
        }
        return z;
    }

    private static boolean isHidItemShort(byte b) {
        return (b & 240) != 240;
    }

    private static int getHidItemDataSize(byte b) {
        switch (b & 3) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            default:
                return 4;
        }
    }

    private static boolean isHidItemBrailleDisplayUsagePage(byte b, byte b2) {
        return ((byte) (b & 252)) == 4 && b2 == 65;
    }

    private void assertServiceIsConnectedLocked() {
        if (!this.mServiceConnection.isConnectedLocked()) {
            throw new java.lang.IllegalStateException("Accessibility service is not connected");
        }
    }

    @android.annotation.RequiresNoPermission
    public void disconnect() {
        synchronized (this.mLock) {
            closeInputLocked();
            closeOutputLocked();
            this.mServiceConnection.onBrailleDisplayDisconnectedLocked();
            try {
                this.mController.onDisconnected();
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(LOG_TAG, "Error calling onDisconnected");
            }
            sConnectedNodes.remove(this.mHidrawNode);
        }
    }

    @android.annotation.PermissionManuallyEnforced
    public void write(@android.annotation.NonNull final byte[] bArr) {
        java.util.Objects.requireNonNull(bArr);
        if (bArr.length > android.os.IBinder.getSuggestedMaxIpcSizeBytes()) {
            android.util.Slog.e(LOG_TAG, "Requested write of size " + bArr.length + " which is larger than maximum " + android.os.IBinder.getSuggestedMaxIpcSizeBytes());
            disconnect();
            return;
        }
        synchronized (this.mLock) {
            try {
                assertServiceIsConnectedLocked();
                if (this.mOutputThread == null) {
                    try {
                        this.mOutputStream = new java.io.FileOutputStream(this.mHidrawNode);
                        this.mOutputThread = new android.os.HandlerThread("BrailleDisplayConnection output thread", 10);
                        this.mOutputThread.setDaemon(true);
                        this.mOutputThread.start();
                    } catch (java.lang.Exception e) {
                        android.util.Slog.e(LOG_TAG, "Unable to create write stream", e);
                        disconnect();
                        return;
                    }
                }
                this.mOutputThread.getThreadHandler().post(new java.lang.Runnable() { // from class: com.android.server.accessibility.BrailleDisplayConnection$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.accessibility.BrailleDisplayConnection.this.lambda$write$0(bArr);
                    }
                });
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$write$0(byte[] bArr) {
        try {
            this.mOutputStream.write(bArr);
        } catch (java.io.IOException e) {
            android.util.Slog.d(LOG_TAG, "Error writing to connected Braille display", e);
            disconnect();
        }
    }

    private void startReadingLocked() {
        this.mInputThread = new java.lang.Thread(new java.lang.Runnable() { // from class: com.android.server.accessibility.BrailleDisplayConnection$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.accessibility.BrailleDisplayConnection.this.lambda$startReadingLocked$1();
            }
        }, "BrailleDisplayConnection input thread");
        this.mInputThread.setDaemon(true);
        this.mInputThread.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startReadingLocked$1() {
        android.os.Process.setThreadPriority(10);
        try {
            java.io.FileInputStream fileInputStream = new java.io.FileInputStream(this.mHidrawNode);
            try {
                byte[] bArr = new byte[android.os.IBinder.getSuggestedMaxIpcSizeBytes()];
                while (true) {
                    if (java.lang.Thread.interrupted()) {
                        break;
                    }
                    if (!this.mHidrawNode.exists()) {
                        disconnect();
                        break;
                    }
                    int read = fileInputStream.read(bArr);
                    if (read > 0) {
                        try {
                            this.mController.onInput(java.util.Arrays.copyOfRange(bArr, 0, read));
                        } catch (android.os.RemoteException e) {
                            android.util.Slog.e(LOG_TAG, "Error calling onInput", e);
                            disconnect();
                        }
                    }
                }
                fileInputStream.close();
            } finally {
            }
        } catch (java.io.IOException e2) {
            android.util.Slog.d(LOG_TAG, "Error reading from connected Braille display", e2);
            disconnect();
        }
    }

    private void closeInputLocked() {
        if (this.mInputThread != null) {
            this.mInputThread.interrupt();
        }
        this.mInputThread = null;
    }

    private void closeOutputLocked() {
        if (this.mOutputThread != null) {
            this.mOutputThread.quit();
        }
        this.mOutputThread = null;
        if (this.mOutputStream != null) {
            try {
                this.mOutputStream.close();
            } catch (java.io.IOException e) {
                android.util.Slog.e(LOG_TAG, "Unable to close output stream", e);
            }
        }
        this.mOutputStream = null;
    }

    /* renamed from: com.android.server.accessibility.BrailleDisplayConnection$1, reason: invalid class name */
    class AnonymousClass1 implements com.android.server.accessibility.BrailleDisplayConnection.BrailleDisplayScanner {
        private static final java.lang.String HIDRAW_DEVICE_GLOB = "hidraw*";
        final /* synthetic */ com.android.server.accessibility.BrailleDisplayConnection.NativeInterface val$nativeInterface;

        AnonymousClass1(com.android.server.accessibility.BrailleDisplayConnection.NativeInterface nativeInterface) {
            this.val$nativeInterface = nativeInterface;
        }

        @Override // com.android.server.accessibility.BrailleDisplayConnection.BrailleDisplayScanner
        public java.util.Collection<java.nio.file.Path> getHidrawNodePaths(@android.annotation.NonNull java.nio.file.Path path) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            try {
                java.nio.file.DirectoryStream<java.nio.file.Path> newDirectoryStream = java.nio.file.Files.newDirectoryStream(path, HIDRAW_DEVICE_GLOB);
                try {
                    java.util.Iterator<java.nio.file.Path> it = newDirectoryStream.iterator();
                    while (it.hasNext()) {
                        arrayList.add(it.next());
                    }
                    newDirectoryStream.close();
                    return arrayList;
                } finally {
                }
            } catch (java.io.IOException e) {
                return null;
            }
        }

        private <T> T readFromFileDescriptor(java.nio.file.Path path, java.util.function.Function<java.lang.Integer, T> function) {
            try {
                java.io.FileInputStream fileInputStream = new java.io.FileInputStream(path.toFile());
                try {
                    T apply = function.apply(java.lang.Integer.valueOf(fileInputStream.getFD().getInt$()));
                    fileInputStream.close();
                    return apply;
                } finally {
                }
            } catch (java.io.IOException e) {
                return null;
            }
        }

        @Override // com.android.server.accessibility.BrailleDisplayConnection.BrailleDisplayScanner
        public byte[] getDeviceReportDescriptor(@android.annotation.NonNull java.nio.file.Path path) {
            java.util.Objects.requireNonNull(path);
            final com.android.server.accessibility.BrailleDisplayConnection.NativeInterface nativeInterface = this.val$nativeInterface;
            return (byte[]) readFromFileDescriptor(path, new java.util.function.Function() { // from class: com.android.server.accessibility.BrailleDisplayConnection$1$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    byte[] lambda$getDeviceReportDescriptor$0;
                    lambda$getDeviceReportDescriptor$0 = com.android.server.accessibility.BrailleDisplayConnection.AnonymousClass1.lambda$getDeviceReportDescriptor$0(com.android.server.accessibility.BrailleDisplayConnection.NativeInterface.this, (java.lang.Integer) obj);
                    return lambda$getDeviceReportDescriptor$0;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ byte[] lambda$getDeviceReportDescriptor$0(com.android.server.accessibility.BrailleDisplayConnection.NativeInterface nativeInterface, java.lang.Integer num) {
            int hidrawDescSize = nativeInterface.getHidrawDescSize(num.intValue());
            if (hidrawDescSize > 0) {
                return nativeInterface.getHidrawDesc(num.intValue(), hidrawDescSize);
            }
            return null;
        }

        @Override // com.android.server.accessibility.BrailleDisplayConnection.BrailleDisplayScanner
        public java.lang.String getUniqueId(@android.annotation.NonNull java.nio.file.Path path) {
            java.util.Objects.requireNonNull(path);
            final com.android.server.accessibility.BrailleDisplayConnection.NativeInterface nativeInterface = this.val$nativeInterface;
            java.util.Objects.requireNonNull(nativeInterface);
            return (java.lang.String) readFromFileDescriptor(path, new java.util.function.Function() { // from class: com.android.server.accessibility.BrailleDisplayConnection$1$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    return com.android.server.accessibility.BrailleDisplayConnection.NativeInterface.this.getHidrawUniq(((java.lang.Integer) obj).intValue());
                }
            });
        }

        @Override // com.android.server.accessibility.BrailleDisplayConnection.BrailleDisplayScanner
        public int getDeviceBusType(@android.annotation.NonNull java.nio.file.Path path) {
            java.util.Objects.requireNonNull(path);
            final com.android.server.accessibility.BrailleDisplayConnection.NativeInterface nativeInterface = this.val$nativeInterface;
            java.util.Objects.requireNonNull(nativeInterface);
            java.lang.Integer num = (java.lang.Integer) readFromFileDescriptor(path, new java.util.function.Function() { // from class: com.android.server.accessibility.BrailleDisplayConnection$1$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    return java.lang.Integer.valueOf(com.android.server.accessibility.BrailleDisplayConnection.NativeInterface.this.getHidrawBusType(((java.lang.Integer) obj).intValue()));
                }
            });
            if (num != null) {
                return num.intValue();
            }
            return -1;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static com.android.server.accessibility.BrailleDisplayConnection.BrailleDisplayScanner getDefaultNativeScanner(@android.annotation.NonNull com.android.server.accessibility.BrailleDisplayConnection.NativeInterface nativeInterface) {
        java.util.Objects.requireNonNull(nativeInterface);
        return new com.android.server.accessibility.BrailleDisplayConnection.AnonymousClass1(nativeInterface);
    }

    com.android.server.accessibility.BrailleDisplayConnection.BrailleDisplayScanner setTestData(@android.annotation.NonNull java.util.List<android.os.Bundle> list) {
        com.android.server.accessibility.BrailleDisplayConnection.BrailleDisplayScanner brailleDisplayScanner;
        java.util.Objects.requireNonNull(list);
        final android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        for (android.os.Bundle bundle : list) {
            arrayMap.put(java.nio.file.Path.of(bundle.getString("HIDRAW_PATH"), new java.lang.String[0]), bundle);
        }
        synchronized (this.mLock) {
            this.mScanner = new com.android.server.accessibility.BrailleDisplayConnection.BrailleDisplayScanner() { // from class: com.android.server.accessibility.BrailleDisplayConnection.2
                @Override // com.android.server.accessibility.BrailleDisplayConnection.BrailleDisplayScanner
                public java.util.Collection<java.nio.file.Path> getHidrawNodePaths(@android.annotation.NonNull java.nio.file.Path path) {
                    if (arrayMap.isEmpty()) {
                        return null;
                    }
                    return arrayMap.keySet();
                }

                @Override // com.android.server.accessibility.BrailleDisplayConnection.BrailleDisplayScanner
                public byte[] getDeviceReportDescriptor(@android.annotation.NonNull java.nio.file.Path path) {
                    return ((android.os.Bundle) arrayMap.get(path)).getByteArray("DESCRIPTOR");
                }

                @Override // com.android.server.accessibility.BrailleDisplayConnection.BrailleDisplayScanner
                public java.lang.String getUniqueId(@android.annotation.NonNull java.nio.file.Path path) {
                    return ((android.os.Bundle) arrayMap.get(path)).getString("UNIQUE_ID");
                }

                @Override // com.android.server.accessibility.BrailleDisplayConnection.BrailleDisplayScanner
                public int getDeviceBusType(@android.annotation.NonNull java.nio.file.Path path) {
                    return ((android.os.Bundle) arrayMap.get(path)).getBoolean("BUS_BLUETOOTH") ? 5 : 3;
                }
            };
            brailleDisplayScanner = this.mScanner;
        }
        return brailleDisplayScanner;
    }

    private static class DefaultNativeInterface implements com.android.server.accessibility.BrailleDisplayConnection.NativeInterface {
        private DefaultNativeInterface() {
        }

        @Override // com.android.server.accessibility.BrailleDisplayConnection.NativeInterface
        public int getHidrawDescSize(int i) {
            return com.android.server.accessibility.BrailleDisplayConnection.nativeGetHidrawDescSize(i);
        }

        @Override // com.android.server.accessibility.BrailleDisplayConnection.NativeInterface
        public byte[] getHidrawDesc(int i, int i2) {
            return com.android.server.accessibility.BrailleDisplayConnection.nativeGetHidrawDesc(i, i2);
        }

        @Override // com.android.server.accessibility.BrailleDisplayConnection.NativeInterface
        public java.lang.String getHidrawUniq(int i) {
            return com.android.server.accessibility.BrailleDisplayConnection.nativeGetHidrawUniq(i);
        }

        @Override // com.android.server.accessibility.BrailleDisplayConnection.NativeInterface
        public int getHidrawBusType(int i) {
            return com.android.server.accessibility.BrailleDisplayConnection.nativeGetHidrawBusType(i);
        }
    }
}
