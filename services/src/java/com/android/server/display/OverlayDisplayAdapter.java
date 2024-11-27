package com.android.server.display;

/* loaded from: classes.dex */
final class OverlayDisplayAdapter extends com.android.server.display.DisplayAdapter {
    static final boolean DEBUG = false;
    private static final java.lang.String DISPLAY_SPLITTER = ";";
    private static final java.lang.String FLAG_SPLITTER = ",";
    private static final int MAX_HEIGHT = 4096;
    private static final int MAX_WIDTH = 4096;
    private static final int MIN_HEIGHT = 100;
    private static final int MIN_WIDTH = 100;
    private static final java.lang.String MODE_SPLITTER = "\\|";
    private static final java.lang.String OVERLAY_DISPLAY_FLAG_OWN_CONTENT_ONLY = "own_content_only";
    private static final java.lang.String OVERLAY_DISPLAY_FLAG_SECURE = "secure";
    private static final java.lang.String OVERLAY_DISPLAY_FLAG_SHOULD_SHOW_SYSTEM_DECORATIONS = "should_show_system_decorations";
    static final java.lang.String TAG = "OverlayDisplayAdapter";
    private static final java.lang.String UNIQUE_ID_PREFIX = "overlay:";
    private java.lang.String mCurrentOverlaySetting;
    private final java.util.ArrayList<com.android.server.display.OverlayDisplayAdapter.OverlayDisplayHandle> mOverlays;
    private final android.os.Handler mUiHandler;
    private static final java.util.regex.Pattern DISPLAY_PATTERN = java.util.regex.Pattern.compile("([^,]+)(,[,_a-z]+)*");
    private static final java.util.regex.Pattern MODE_PATTERN = java.util.regex.Pattern.compile("(\\d+)x(\\d+)/(\\d+)");

    public OverlayDisplayAdapter(com.android.server.display.DisplayManagerService.SyncRoot syncRoot, android.content.Context context, android.os.Handler handler, com.android.server.display.DisplayAdapter.Listener listener, android.os.Handler handler2, com.android.server.display.feature.DisplayManagerFlags displayManagerFlags) {
        super(syncRoot, context, handler, listener, TAG, displayManagerFlags);
        this.mOverlays = new java.util.ArrayList<>();
        this.mCurrentOverlaySetting = "";
        this.mUiHandler = handler2;
    }

    @Override // com.android.server.display.DisplayAdapter
    public void dumpLocked(java.io.PrintWriter printWriter) {
        super.dumpLocked(printWriter);
        printWriter.println("mCurrentOverlaySetting=" + this.mCurrentOverlaySetting);
        printWriter.println("mOverlays: size=" + this.mOverlays.size());
        java.util.Iterator<com.android.server.display.OverlayDisplayAdapter.OverlayDisplayHandle> it = this.mOverlays.iterator();
        while (it.hasNext()) {
            it.next().dumpLocked(printWriter);
        }
    }

    @Override // com.android.server.display.DisplayAdapter
    public void registerLocked() {
        super.registerLocked();
        getHandler().post(new java.lang.Runnable() { // from class: com.android.server.display.OverlayDisplayAdapter.1
            @Override // java.lang.Runnable
            public void run() {
                com.android.server.display.OverlayDisplayAdapter.this.getContext().getContentResolver().registerContentObserver(android.provider.Settings.Global.getUriFor("overlay_display_devices"), true, new android.database.ContentObserver(com.android.server.display.OverlayDisplayAdapter.this.getHandler()) { // from class: com.android.server.display.OverlayDisplayAdapter.1.1
                    @Override // android.database.ContentObserver
                    public void onChange(boolean z) {
                        com.android.server.display.OverlayDisplayAdapter.this.updateOverlayDisplayDevices();
                    }
                });
                com.android.server.display.OverlayDisplayAdapter.this.updateOverlayDisplayDevices();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateOverlayDisplayDevices() {
        synchronized (getSyncRoot()) {
            updateOverlayDisplayDevicesLocked();
        }
    }

    private void updateOverlayDisplayDevicesLocked() {
        java.lang.String str;
        java.lang.String[] strArr;
        java.lang.String string = android.provider.Settings.Global.getString(getContext().getContentResolver(), "overlay_display_devices");
        if (string != null) {
            str = string;
        } else {
            str = "";
        }
        if (str.equals(this.mCurrentOverlaySetting)) {
            return;
        }
        this.mCurrentOverlaySetting = str;
        if (!this.mOverlays.isEmpty()) {
            android.util.Slog.i(TAG, "Dismissing all overlay display devices.");
            java.util.Iterator<com.android.server.display.OverlayDisplayAdapter.OverlayDisplayHandle> it = this.mOverlays.iterator();
            while (it.hasNext()) {
                it.next().dismissLocked();
            }
            this.mOverlays.clear();
        }
        int i = 0;
        for (java.lang.String str2 : str.split(DISPLAY_SPLITTER)) {
            java.util.regex.Matcher matcher = DISPLAY_PATTERN.matcher(str2);
            if (matcher.matches()) {
                if (i >= 4) {
                    android.util.Slog.w(TAG, "Too many overlay display devices specified: " + str);
                    return;
                }
                int i2 = 1;
                java.lang.String group = matcher.group(1);
                java.lang.String group2 = matcher.group(2);
                java.util.ArrayList arrayList = new java.util.ArrayList();
                java.lang.String[] split = group.split(MODE_SPLITTER);
                int length = split.length;
                int i3 = 0;
                while (i3 < length) {
                    java.lang.String str3 = split[i3];
                    java.util.regex.Matcher matcher2 = MODE_PATTERN.matcher(str3);
                    if (matcher2.matches()) {
                        try {
                            int parseInt = java.lang.Integer.parseInt(matcher2.group(i2), 10);
                            strArr = split;
                            try {
                                int parseInt2 = java.lang.Integer.parseInt(matcher2.group(2), 10);
                                int parseInt3 = java.lang.Integer.parseInt(matcher2.group(3), 10);
                                if (parseInt >= 100 && parseInt <= 4096 && parseInt2 >= 100 && parseInt2 <= 4096 && parseInt3 >= 120 && parseInt3 <= 640) {
                                    arrayList.add(new com.android.server.display.OverlayDisplayAdapter.OverlayMode(parseInt, parseInt2, parseInt3));
                                } else {
                                    android.util.Slog.w(TAG, "Ignoring out-of-range overlay display mode: " + str3);
                                }
                            } catch (java.lang.NumberFormatException e) {
                            }
                        } catch (java.lang.NumberFormatException e2) {
                            strArr = split;
                        }
                    } else {
                        strArr = split;
                        str3.isEmpty();
                    }
                    i3++;
                    split = strArr;
                    i2 = 1;
                }
                if (!arrayList.isEmpty()) {
                    int i4 = i + 1;
                    java.lang.String string2 = getContext().getResources().getString(android.R.string.deprecated_target_sdk_message, java.lang.Integer.valueOf(i4));
                    int chooseOverlayGravity = chooseOverlayGravity(i4);
                    com.android.server.display.OverlayDisplayAdapter.OverlayFlags parseFlags = com.android.server.display.OverlayDisplayAdapter.OverlayFlags.parseFlags(group2);
                    android.util.Slog.i(TAG, "Showing overlay display device #" + i4 + ": name=" + string2 + ", modes=" + java.util.Arrays.toString(arrayList.toArray()) + ", flags=" + parseFlags);
                    this.mOverlays.add(new com.android.server.display.OverlayDisplayAdapter.OverlayDisplayHandle(string2, arrayList, chooseOverlayGravity, parseFlags, i4));
                    i = i4;
                }
            }
            android.util.Slog.w(TAG, "Malformed overlay display devices setting: " + str);
        }
    }

    private static int chooseOverlayGravity(int i) {
        switch (i) {
            case 1:
                return 51;
            case 2:
                return 85;
            case 3:
                return 53;
            default:
                return 83;
        }
    }

    private abstract class OverlayDisplayDevice extends com.android.server.display.DisplayDevice {
        private int mActiveMode;
        private final int mDefaultMode;
        private final long mDisplayPresentationDeadlineNanos;
        private final com.android.server.display.OverlayDisplayAdapter.OverlayFlags mFlags;
        private com.android.server.display.DisplayDeviceInfo mInfo;
        private final android.view.Display.Mode[] mModes;
        private final java.lang.String mName;
        private final java.util.List<com.android.server.display.OverlayDisplayAdapter.OverlayMode> mRawModes;
        private final float mRefreshRate;
        private int mState;
        private android.view.Surface mSurface;
        private android.graphics.SurfaceTexture mSurfaceTexture;

        public abstract void onModeChangedLocked(int i);

        OverlayDisplayDevice(android.os.IBinder iBinder, java.lang.String str, java.util.List<com.android.server.display.OverlayDisplayAdapter.OverlayMode> list, int i, int i2, float f, long j, com.android.server.display.OverlayDisplayAdapter.OverlayFlags overlayFlags, int i3, android.graphics.SurfaceTexture surfaceTexture, int i4) {
            super(com.android.server.display.OverlayDisplayAdapter.this, iBinder, com.android.server.display.OverlayDisplayAdapter.UNIQUE_ID_PREFIX + i4, com.android.server.display.OverlayDisplayAdapter.this.getContext());
            this.mName = str;
            this.mRefreshRate = f;
            this.mDisplayPresentationDeadlineNanos = j;
            this.mFlags = overlayFlags;
            this.mState = i3;
            this.mSurfaceTexture = surfaceTexture;
            this.mRawModes = list;
            this.mModes = new android.view.Display.Mode[list.size()];
            for (int i5 = 0; i5 < list.size(); i5++) {
                com.android.server.display.OverlayDisplayAdapter.OverlayMode overlayMode = list.get(i5);
                this.mModes[i5] = com.android.server.display.DisplayAdapter.createMode(overlayMode.mWidth, overlayMode.mHeight, f);
            }
            this.mActiveMode = i;
            this.mDefaultMode = i2;
        }

        public void destroyLocked() {
            this.mSurfaceTexture = null;
            if (this.mSurface != null) {
                this.mSurface.release();
                this.mSurface = null;
            }
            com.android.server.display.DisplayControl.destroyDisplay(getDisplayTokenLocked());
        }

        @Override // com.android.server.display.DisplayDevice
        public boolean hasStableUniqueId() {
            return false;
        }

        @Override // com.android.server.display.DisplayDevice
        public void performTraversalLocked(android.view.SurfaceControl.Transaction transaction) {
            if (this.mSurfaceTexture != null) {
                if (this.mSurface == null) {
                    this.mSurface = new android.view.Surface(this.mSurfaceTexture);
                }
                setSurfaceLocked(transaction, this.mSurface);
            }
        }

        public void setStateLocked(int i) {
            this.mState = i;
            this.mInfo = null;
        }

        @Override // com.android.server.display.DisplayDevice
        public com.android.server.display.DisplayDeviceInfo getDisplayDeviceInfoLocked() {
            if (this.mInfo == null) {
                android.view.Display.Mode mode = this.mModes[this.mActiveMode];
                com.android.server.display.OverlayDisplayAdapter.OverlayMode overlayMode = this.mRawModes.get(this.mActiveMode);
                this.mInfo = new com.android.server.display.DisplayDeviceInfo();
                this.mInfo.name = this.mName;
                this.mInfo.uniqueId = getUniqueId();
                this.mInfo.width = mode.getPhysicalWidth();
                this.mInfo.height = mode.getPhysicalHeight();
                this.mInfo.modeId = mode.getModeId();
                this.mInfo.renderFrameRate = mode.getRefreshRate();
                this.mInfo.defaultModeId = this.mModes[0].getModeId();
                this.mInfo.supportedModes = this.mModes;
                this.mInfo.densityDpi = overlayMode.mDensityDpi;
                this.mInfo.xDpi = overlayMode.mDensityDpi;
                this.mInfo.yDpi = overlayMode.mDensityDpi;
                this.mInfo.presentationDeadlineNanos = this.mDisplayPresentationDeadlineNanos + (1000000000 / ((int) this.mRefreshRate));
                this.mInfo.flags = 64;
                if (this.mFlags.mSecure) {
                    this.mInfo.flags |= 4;
                }
                if (this.mFlags.mOwnContentOnly) {
                    this.mInfo.flags |= 128;
                }
                if (this.mFlags.mShouldShowSystemDecorations) {
                    this.mInfo.flags |= 4096;
                }
                this.mInfo.type = 4;
                this.mInfo.touch = 3;
                this.mInfo.state = this.mState;
                this.mInfo.flags |= 8192;
                this.mInfo.displayShape = android.view.DisplayShape.createDefaultDisplayShape(this.mInfo.width, this.mInfo.height, false);
            }
            return this.mInfo;
        }

        @Override // com.android.server.display.DisplayDevice
        public void setDesiredDisplayModeSpecsLocked(com.android.server.display.mode.DisplayModeDirector.DesiredDisplayModeSpecs desiredDisplayModeSpecs) {
            int i = desiredDisplayModeSpecs.baseModeId;
            int i2 = 0;
            if (i != 0) {
                while (true) {
                    if (i2 >= this.mModes.length) {
                        i2 = -1;
                        break;
                    } else if (this.mModes[i2].getModeId() == i) {
                        break;
                    } else {
                        i2++;
                    }
                }
            }
            if (i2 == -1) {
                android.util.Slog.w(com.android.server.display.OverlayDisplayAdapter.TAG, "Unable to locate mode " + i + ", reverting to default.");
                i2 = this.mDefaultMode;
            }
            if (this.mActiveMode == i2) {
                return;
            }
            this.mActiveMode = i2;
            this.mInfo = null;
            com.android.server.display.OverlayDisplayAdapter.this.sendDisplayDeviceEventLocked(this, 2);
            onModeChangedLocked(i2);
        }
    }

    private final class OverlayDisplayHandle implements com.android.server.display.OverlayDisplayWindow.Listener {
        private static final int DEFAULT_MODE_INDEX = 0;
        private com.android.server.display.OverlayDisplayAdapter.OverlayDisplayDevice mDevice;
        private final com.android.server.display.OverlayDisplayAdapter.OverlayFlags mFlags;
        private final int mGravity;
        private final java.util.List<com.android.server.display.OverlayDisplayAdapter.OverlayMode> mModes;
        private final java.lang.String mName;
        private final int mNumber;
        private com.android.server.display.OverlayDisplayWindow mWindow;
        private final java.lang.Runnable mShowRunnable = new java.lang.Runnable() { // from class: com.android.server.display.OverlayDisplayAdapter.OverlayDisplayHandle.2
            @Override // java.lang.Runnable
            public void run() {
                com.android.server.display.OverlayDisplayAdapter.OverlayMode overlayMode = (com.android.server.display.OverlayDisplayAdapter.OverlayMode) com.android.server.display.OverlayDisplayAdapter.OverlayDisplayHandle.this.mModes.get(com.android.server.display.OverlayDisplayAdapter.OverlayDisplayHandle.this.mActiveMode);
                com.android.server.display.OverlayDisplayWindow overlayDisplayWindow = new com.android.server.display.OverlayDisplayWindow(com.android.server.display.OverlayDisplayAdapter.this.getContext(), com.android.server.display.OverlayDisplayAdapter.OverlayDisplayHandle.this.mName, overlayMode.mWidth, overlayMode.mHeight, overlayMode.mDensityDpi, com.android.server.display.OverlayDisplayAdapter.OverlayDisplayHandle.this.mGravity, com.android.server.display.OverlayDisplayAdapter.OverlayDisplayHandle.this.mFlags.mSecure, com.android.server.display.OverlayDisplayAdapter.OverlayDisplayHandle.this);
                overlayDisplayWindow.show();
                synchronized (com.android.server.display.OverlayDisplayAdapter.this.getSyncRoot()) {
                    com.android.server.display.OverlayDisplayAdapter.OverlayDisplayHandle.this.mWindow = overlayDisplayWindow;
                }
            }
        };
        private final java.lang.Runnable mDismissRunnable = new java.lang.Runnable() { // from class: com.android.server.display.OverlayDisplayAdapter.OverlayDisplayHandle.3
            @Override // java.lang.Runnable
            public void run() {
                com.android.server.display.OverlayDisplayWindow overlayDisplayWindow;
                synchronized (com.android.server.display.OverlayDisplayAdapter.this.getSyncRoot()) {
                    overlayDisplayWindow = com.android.server.display.OverlayDisplayAdapter.OverlayDisplayHandle.this.mWindow;
                    com.android.server.display.OverlayDisplayAdapter.OverlayDisplayHandle.this.mWindow = null;
                }
                if (overlayDisplayWindow != null) {
                    overlayDisplayWindow.dismiss();
                }
            }
        };
        private final java.lang.Runnable mResizeRunnable = new java.lang.Runnable() { // from class: com.android.server.display.OverlayDisplayAdapter.OverlayDisplayHandle.4
            @Override // java.lang.Runnable
            public void run() {
                synchronized (com.android.server.display.OverlayDisplayAdapter.this.getSyncRoot()) {
                    try {
                        if (com.android.server.display.OverlayDisplayAdapter.OverlayDisplayHandle.this.mWindow == null) {
                            return;
                        }
                        com.android.server.display.OverlayDisplayAdapter.OverlayMode overlayMode = (com.android.server.display.OverlayDisplayAdapter.OverlayMode) com.android.server.display.OverlayDisplayAdapter.OverlayDisplayHandle.this.mModes.get(com.android.server.display.OverlayDisplayAdapter.OverlayDisplayHandle.this.mActiveMode);
                        com.android.server.display.OverlayDisplayAdapter.OverlayDisplayHandle.this.mWindow.resize(overlayMode.mWidth, overlayMode.mHeight, overlayMode.mDensityDpi);
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        };
        private int mActiveMode = 0;

        OverlayDisplayHandle(java.lang.String str, java.util.List<com.android.server.display.OverlayDisplayAdapter.OverlayMode> list, int i, com.android.server.display.OverlayDisplayAdapter.OverlayFlags overlayFlags, int i2) {
            this.mName = str;
            this.mModes = list;
            this.mGravity = i;
            this.mFlags = overlayFlags;
            this.mNumber = i2;
            showLocked();
        }

        private void showLocked() {
            com.android.server.display.OverlayDisplayAdapter.this.mUiHandler.post(this.mShowRunnable);
        }

        public void dismissLocked() {
            com.android.server.display.OverlayDisplayAdapter.this.mUiHandler.removeCallbacks(this.mShowRunnable);
            com.android.server.display.OverlayDisplayAdapter.this.mUiHandler.post(this.mDismissRunnable);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onActiveModeChangedLocked(int i) {
            com.android.server.display.OverlayDisplayAdapter.this.mUiHandler.removeCallbacks(this.mResizeRunnable);
            this.mActiveMode = i;
            if (this.mWindow != null) {
                com.android.server.display.OverlayDisplayAdapter.this.mUiHandler.post(this.mResizeRunnable);
            }
        }

        @Override // com.android.server.display.OverlayDisplayWindow.Listener
        public void onWindowCreated(android.graphics.SurfaceTexture surfaceTexture, float f, long j, int i) {
            synchronized (com.android.server.display.OverlayDisplayAdapter.this.getSyncRoot()) {
                this.mDevice = new com.android.server.display.OverlayDisplayAdapter.OverlayDisplayDevice(com.android.server.display.DisplayControl.createDisplay(this.mName, this.mFlags.mSecure), this.mName, this.mModes, this.mActiveMode, 0, f, j, this.mFlags, i, surfaceTexture, this.mNumber) { // from class: com.android.server.display.OverlayDisplayAdapter.OverlayDisplayHandle.1
                    {
                        com.android.server.display.OverlayDisplayAdapter overlayDisplayAdapter = com.android.server.display.OverlayDisplayAdapter.this;
                    }

                    @Override // com.android.server.display.OverlayDisplayAdapter.OverlayDisplayDevice
                    public void onModeChangedLocked(int i2) {
                        com.android.server.display.OverlayDisplayAdapter.OverlayDisplayHandle.this.onActiveModeChangedLocked(i2);
                    }
                };
                com.android.server.display.OverlayDisplayAdapter.this.sendDisplayDeviceEventLocked(this.mDevice, 1);
            }
        }

        @Override // com.android.server.display.OverlayDisplayWindow.Listener
        public void onWindowDestroyed() {
            synchronized (com.android.server.display.OverlayDisplayAdapter.this.getSyncRoot()) {
                try {
                    if (this.mDevice != null) {
                        this.mDevice.destroyLocked();
                        com.android.server.display.OverlayDisplayAdapter.this.sendDisplayDeviceEventLocked(this.mDevice, 3);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.display.OverlayDisplayWindow.Listener
        public void onStateChanged(int i) {
            synchronized (com.android.server.display.OverlayDisplayAdapter.this.getSyncRoot()) {
                try {
                    if (this.mDevice != null) {
                        this.mDevice.setStateLocked(i);
                        com.android.server.display.OverlayDisplayAdapter.this.sendDisplayDeviceEventLocked(this.mDevice, 2);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void dumpLocked(java.io.PrintWriter printWriter) {
            printWriter.println("  " + this.mName + ":");
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("    mModes=");
            sb.append(java.util.Arrays.toString(this.mModes.toArray()));
            printWriter.println(sb.toString());
            printWriter.println("    mActiveMode=" + this.mActiveMode);
            printWriter.println("    mGravity=" + this.mGravity);
            printWriter.println("    mFlags=" + this.mFlags);
            printWriter.println("    mNumber=" + this.mNumber);
            if (this.mWindow != null) {
                com.android.internal.util.IndentingPrintWriter indentingPrintWriter = new com.android.internal.util.IndentingPrintWriter(printWriter, "    ");
                indentingPrintWriter.increaseIndent();
                com.android.internal.util.DumpUtils.dumpAsync(com.android.server.display.OverlayDisplayAdapter.this.mUiHandler, this.mWindow, indentingPrintWriter, "", 200L);
            }
        }
    }

    private static final class OverlayMode {
        final int mDensityDpi;
        final int mHeight;
        final int mWidth;

        OverlayMode(int i, int i2, int i3) {
            this.mWidth = i;
            this.mHeight = i2;
            this.mDensityDpi = i3;
        }

        public java.lang.String toString() {
            return "{width=" + this.mWidth + ", height=" + this.mHeight + ", densityDpi=" + this.mDensityDpi + "}";
        }
    }

    private static final class OverlayFlags {
        final boolean mOwnContentOnly;
        final boolean mSecure;
        final boolean mShouldShowSystemDecorations;

        OverlayFlags(boolean z, boolean z2, boolean z3) {
            this.mSecure = z;
            this.mOwnContentOnly = z2;
            this.mShouldShowSystemDecorations = z3;
        }

        static com.android.server.display.OverlayDisplayAdapter.OverlayFlags parseFlags(@android.annotation.Nullable java.lang.String str) {
            if (android.text.TextUtils.isEmpty(str)) {
                return new com.android.server.display.OverlayDisplayAdapter.OverlayFlags(false, false, false);
            }
            boolean z = false;
            boolean z2 = false;
            boolean z3 = false;
            for (java.lang.String str2 : str.split(com.android.server.display.OverlayDisplayAdapter.FLAG_SPLITTER)) {
                if (com.android.server.display.OverlayDisplayAdapter.OVERLAY_DISPLAY_FLAG_SECURE.equals(str2)) {
                    z = true;
                }
                if (com.android.server.display.OverlayDisplayAdapter.OVERLAY_DISPLAY_FLAG_OWN_CONTENT_ONLY.equals(str2)) {
                    z2 = true;
                }
                if (com.android.server.display.OverlayDisplayAdapter.OVERLAY_DISPLAY_FLAG_SHOULD_SHOW_SYSTEM_DECORATIONS.equals(str2)) {
                    z3 = true;
                }
            }
            return new com.android.server.display.OverlayDisplayAdapter.OverlayFlags(z, z2, z3);
        }

        public java.lang.String toString() {
            return "{secure=" + this.mSecure + ", ownContentOnly=" + this.mOwnContentOnly + ", shouldShowSystemDecorations=" + this.mShouldShowSystemDecorations + "}";
        }
    }
}
