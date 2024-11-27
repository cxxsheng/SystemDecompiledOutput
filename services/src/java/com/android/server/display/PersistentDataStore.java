package com.android.server.display;

/* loaded from: classes.dex */
final class PersistentDataStore {
    private static final java.lang.String ATTR_DEVICE_ADDRESS = "deviceAddress";
    private static final java.lang.String ATTR_DEVICE_ALIAS = "deviceAlias";
    private static final java.lang.String ATTR_DEVICE_NAME = "deviceName";
    private static final java.lang.String ATTR_PACKAGE_NAME = "package-name";
    private static final java.lang.String ATTR_TIME_STAMP = "timestamp";
    private static final java.lang.String ATTR_UNIQUE_ID = "unique-id";
    private static final java.lang.String ATTR_USER_SERIAL = "user-serial";
    public static final int DEFAULT_USER_ID = -1;
    static final java.lang.String TAG = "DisplayManager.PersistentDataStore";
    private static final java.lang.String TAG_BRIGHTNESS_CONFIGURATION = "brightness-configuration";
    private static final java.lang.String TAG_BRIGHTNESS_CONFIGURATIONS = "brightness-configurations";
    private static final java.lang.String TAG_BRIGHTNESS_NITS_FOR_DEFAULT_DISPLAY = "brightness-nits-for-default-display";
    private static final java.lang.String TAG_BRIGHTNESS_VALUE = "brightness-value";
    private static final java.lang.String TAG_COLOR_MODE = "color-mode";
    private static final java.lang.String TAG_DISPLAY = "display";
    private static final java.lang.String TAG_DISPLAY_MANAGER_STATE = "display-manager-state";
    private static final java.lang.String TAG_DISPLAY_STATES = "display-states";
    private static final java.lang.String TAG_REFRESH_RATE = "refresh-rate";
    private static final java.lang.String TAG_REMEMBERED_WIFI_DISPLAYS = "remembered-wifi-displays";
    private static final java.lang.String TAG_RESOLUTION_HEIGHT = "resolution-height";
    private static final java.lang.String TAG_RESOLUTION_WIDTH = "resolution-width";
    private static final java.lang.String TAG_STABLE_DEVICE_VALUES = "stable-device-values";
    private static final java.lang.String TAG_STABLE_DISPLAY_HEIGHT = "stable-display-height";
    private static final java.lang.String TAG_STABLE_DISPLAY_WIDTH = "stable-display-width";
    private static final java.lang.String TAG_WIFI_DISPLAY = "wifi-display";
    private float mBrightnessNitsForDefaultDisplay;
    private boolean mDirty;
    private final java.util.HashMap<java.lang.String, com.android.server.display.PersistentDataStore.DisplayState> mDisplayStates;
    private final java.lang.Object mFileAccessLock;
    private com.android.server.display.PersistentDataStore.BrightnessConfigurations mGlobalBrightnessConfigurations;
    private final android.os.Handler mHandler;
    private com.android.server.display.PersistentDataStore.Injector mInjector;
    private boolean mLoaded;
    private java.util.ArrayList<android.hardware.display.WifiDisplay> mRememberedWifiDisplays;
    private final com.android.server.display.PersistentDataStore.StableDeviceValues mStableDeviceValues;

    public PersistentDataStore() {
        this(new com.android.server.display.PersistentDataStore.Injector());
    }

    @com.android.internal.annotations.VisibleForTesting
    PersistentDataStore(com.android.server.display.PersistentDataStore.Injector injector) {
        this(injector, new android.os.Handler(com.android.internal.os.BackgroundThread.getHandler().getLooper()));
    }

    @com.android.internal.annotations.VisibleForTesting
    PersistentDataStore(com.android.server.display.PersistentDataStore.Injector injector, android.os.Handler handler) {
        this.mRememberedWifiDisplays = new java.util.ArrayList<>();
        this.mDisplayStates = new java.util.HashMap<>();
        this.mBrightnessNitsForDefaultDisplay = -1.0f;
        this.mStableDeviceValues = new com.android.server.display.PersistentDataStore.StableDeviceValues();
        this.mGlobalBrightnessConfigurations = new com.android.server.display.PersistentDataStore.BrightnessConfigurations();
        this.mFileAccessLock = new java.lang.Object();
        this.mInjector = injector;
        this.mHandler = handler;
    }

    public void saveIfNeeded() {
        if (this.mDirty) {
            save();
            this.mDirty = false;
        }
    }

    public android.hardware.display.WifiDisplay getRememberedWifiDisplay(java.lang.String str) {
        loadIfNeeded();
        int findRememberedWifiDisplay = findRememberedWifiDisplay(str);
        if (findRememberedWifiDisplay >= 0) {
            return this.mRememberedWifiDisplays.get(findRememberedWifiDisplay);
        }
        return null;
    }

    public android.hardware.display.WifiDisplay[] getRememberedWifiDisplays() {
        loadIfNeeded();
        return (android.hardware.display.WifiDisplay[]) this.mRememberedWifiDisplays.toArray(new android.hardware.display.WifiDisplay[this.mRememberedWifiDisplays.size()]);
    }

    public android.hardware.display.WifiDisplay applyWifiDisplayAlias(android.hardware.display.WifiDisplay wifiDisplay) {
        java.lang.String str;
        if (wifiDisplay != null) {
            loadIfNeeded();
            int findRememberedWifiDisplay = findRememberedWifiDisplay(wifiDisplay.getDeviceAddress());
            if (findRememberedWifiDisplay < 0) {
                str = null;
            } else {
                str = this.mRememberedWifiDisplays.get(findRememberedWifiDisplay).getDeviceAlias();
            }
            if (!java.util.Objects.equals(wifiDisplay.getDeviceAlias(), str)) {
                return new android.hardware.display.WifiDisplay(wifiDisplay.getDeviceAddress(), wifiDisplay.getDeviceName(), str, wifiDisplay.isAvailable(), wifiDisplay.canConnect(), wifiDisplay.isRemembered());
            }
        }
        return wifiDisplay;
    }

    public android.hardware.display.WifiDisplay[] applyWifiDisplayAliases(android.hardware.display.WifiDisplay[] wifiDisplayArr) {
        if (wifiDisplayArr != null) {
            int length = wifiDisplayArr.length;
            android.hardware.display.WifiDisplay[] wifiDisplayArr2 = wifiDisplayArr;
            for (int i = 0; i < length; i++) {
                android.hardware.display.WifiDisplay applyWifiDisplayAlias = applyWifiDisplayAlias(wifiDisplayArr[i]);
                if (applyWifiDisplayAlias != wifiDisplayArr[i]) {
                    if (wifiDisplayArr2 == wifiDisplayArr) {
                        wifiDisplayArr2 = new android.hardware.display.WifiDisplay[length];
                        java.lang.System.arraycopy(wifiDisplayArr, 0, wifiDisplayArr2, 0, length);
                    }
                    wifiDisplayArr2[i] = applyWifiDisplayAlias;
                }
            }
            return wifiDisplayArr2;
        }
        return wifiDisplayArr;
    }

    public boolean rememberWifiDisplay(android.hardware.display.WifiDisplay wifiDisplay) {
        loadIfNeeded();
        int findRememberedWifiDisplay = findRememberedWifiDisplay(wifiDisplay.getDeviceAddress());
        if (findRememberedWifiDisplay >= 0) {
            if (this.mRememberedWifiDisplays.get(findRememberedWifiDisplay).equals(wifiDisplay)) {
                return false;
            }
            this.mRememberedWifiDisplays.set(findRememberedWifiDisplay, wifiDisplay);
        } else {
            this.mRememberedWifiDisplays.add(wifiDisplay);
        }
        setDirty();
        return true;
    }

    public boolean forgetWifiDisplay(java.lang.String str) {
        loadIfNeeded();
        int findRememberedWifiDisplay = findRememberedWifiDisplay(str);
        if (findRememberedWifiDisplay >= 0) {
            this.mRememberedWifiDisplays.remove(findRememberedWifiDisplay);
            setDirty();
            return true;
        }
        return false;
    }

    private int findRememberedWifiDisplay(java.lang.String str) {
        int size = this.mRememberedWifiDisplays.size();
        for (int i = 0; i < size; i++) {
            if (this.mRememberedWifiDisplays.get(i).getDeviceAddress().equals(str)) {
                return i;
            }
        }
        return -1;
    }

    public int getColorMode(com.android.server.display.DisplayDevice displayDevice) {
        com.android.server.display.PersistentDataStore.DisplayState displayState;
        if (displayDevice.hasStableUniqueId() && (displayState = getDisplayState(displayDevice.getUniqueId(), false)) != null) {
            return displayState.getColorMode();
        }
        return -1;
    }

    public boolean setColorMode(com.android.server.display.DisplayDevice displayDevice, int i) {
        if (!displayDevice.hasStableUniqueId() || !getDisplayState(displayDevice.getUniqueId(), true).setColorMode(i)) {
            return false;
        }
        setDirty();
        return true;
    }

    public float getBrightness(com.android.server.display.DisplayDevice displayDevice, int i) {
        com.android.server.display.PersistentDataStore.DisplayState displayState;
        if (displayDevice == null || !displayDevice.hasStableUniqueId() || (displayState = getDisplayState(displayDevice.getUniqueId(), false)) == null) {
            return Float.NaN;
        }
        return displayState.getBrightness(i);
    }

    public boolean setBrightness(com.android.server.display.DisplayDevice displayDevice, float f, int i) {
        java.lang.String uniqueId;
        if (displayDevice == null || !displayDevice.hasStableUniqueId() || (uniqueId = displayDevice.getUniqueId()) == null || !getDisplayState(uniqueId, true).setBrightness(f, i)) {
            return false;
        }
        setDirty();
        return true;
    }

    public float getBrightnessNitsForDefaultDisplay() {
        return this.mBrightnessNitsForDefaultDisplay;
    }

    public boolean setBrightnessNitsForDefaultDisplay(float f) {
        if (f != this.mBrightnessNitsForDefaultDisplay) {
            this.mBrightnessNitsForDefaultDisplay = f;
            setDirty();
            return true;
        }
        return false;
    }

    public boolean setUserPreferredRefreshRate(com.android.server.display.DisplayDevice displayDevice, float f) {
        java.lang.String uniqueId = displayDevice.getUniqueId();
        if (!displayDevice.hasStableUniqueId() || uniqueId == null || !getDisplayState(displayDevice.getUniqueId(), true).setRefreshRate(f)) {
            return false;
        }
        setDirty();
        return true;
    }

    public float getUserPreferredRefreshRate(com.android.server.display.DisplayDevice displayDevice) {
        com.android.server.display.PersistentDataStore.DisplayState displayState;
        if (displayDevice == null || !displayDevice.hasStableUniqueId() || (displayState = getDisplayState(displayDevice.getUniqueId(), false)) == null) {
            return Float.NaN;
        }
        return displayState.getRefreshRate();
    }

    public boolean setUserPreferredResolution(com.android.server.display.DisplayDevice displayDevice, int i, int i2) {
        java.lang.String uniqueId = displayDevice.getUniqueId();
        if (!displayDevice.hasStableUniqueId() || uniqueId == null || !getDisplayState(displayDevice.getUniqueId(), true).setResolution(i, i2)) {
            return false;
        }
        setDirty();
        return true;
    }

    public android.graphics.Point getUserPreferredResolution(com.android.server.display.DisplayDevice displayDevice) {
        com.android.server.display.PersistentDataStore.DisplayState displayState;
        if (displayDevice == null || !displayDevice.hasStableUniqueId() || (displayState = getDisplayState(displayDevice.getUniqueId(), false)) == null) {
            return null;
        }
        return displayState.getResolution();
    }

    public android.graphics.Point getStableDisplaySize() {
        loadIfNeeded();
        return this.mStableDeviceValues.getDisplaySize();
    }

    public void setStableDisplaySize(android.graphics.Point point) {
        loadIfNeeded();
        if (this.mStableDeviceValues.setDisplaySize(point)) {
            setDirty();
        }
    }

    public void setBrightnessConfigurationForUser(android.hardware.display.BrightnessConfiguration brightnessConfiguration, int i, @android.annotation.Nullable java.lang.String str) {
        loadIfNeeded();
        if (this.mGlobalBrightnessConfigurations.setBrightnessConfigurationForUser(brightnessConfiguration, i, str)) {
            setDirty();
        }
    }

    public boolean setBrightnessConfigurationForDisplayLocked(android.hardware.display.BrightnessConfiguration brightnessConfiguration, com.android.server.display.DisplayDevice displayDevice, int i, java.lang.String str) {
        if (displayDevice == null || !displayDevice.hasStableUniqueId() || !getDisplayState(displayDevice.getUniqueId(), true).setBrightnessConfiguration(brightnessConfiguration, i, str)) {
            return false;
        }
        setDirty();
        return true;
    }

    public android.hardware.display.BrightnessConfiguration getBrightnessConfigurationForDisplayLocked(java.lang.String str, int i) {
        loadIfNeeded();
        com.android.server.display.PersistentDataStore.DisplayState displayState = this.mDisplayStates.get(str);
        if (displayState != null) {
            return displayState.getBrightnessConfiguration(i);
        }
        return null;
    }

    public android.hardware.display.BrightnessConfiguration getBrightnessConfiguration(int i) {
        loadIfNeeded();
        return this.mGlobalBrightnessConfigurations.getBrightnessConfiguration(i);
    }

    private com.android.server.display.PersistentDataStore.DisplayState getDisplayState(java.lang.String str, boolean z) {
        loadIfNeeded();
        com.android.server.display.PersistentDataStore.DisplayState displayState = this.mDisplayStates.get(str);
        if (displayState == null && z) {
            com.android.server.display.PersistentDataStore.DisplayState displayState2 = new com.android.server.display.PersistentDataStore.DisplayState();
            this.mDisplayStates.put(str, displayState2);
            setDirty();
            return displayState2;
        }
        return displayState;
    }

    public void loadIfNeeded() {
        if (!this.mLoaded) {
            load();
            this.mLoaded = true;
        }
    }

    private void setDirty() {
        this.mDirty = true;
    }

    private void clearState() {
        this.mRememberedWifiDisplays.clear();
    }

    private void load() {
        java.io.InputStream openRead;
        synchronized (this.mFileAccessLock) {
            clearState();
            try {
                try {
                    openRead = this.mInjector.openRead();
                    try {
                        loadFromXml(android.util.Xml.resolvePullParser(openRead));
                    } catch (java.io.IOException e) {
                        android.util.Slog.w(TAG, "Failed to load display manager persistent store data.", e);
                        clearState();
                    } catch (org.xmlpull.v1.XmlPullParserException e2) {
                        android.util.Slog.w(TAG, "Failed to load display manager persistent store data.", e2);
                        clearState();
                    }
                } finally {
                    libcore.io.IoUtils.closeQuietly(openRead);
                }
            } catch (java.io.FileNotFoundException e3) {
            }
        }
    }

    private void save() {
        try {
            final java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
            com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(byteArrayOutputStream);
            saveToXml(resolveSerializer);
            resolveSerializer.flush();
            this.mHandler.removeCallbacksAndMessages(null);
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.display.PersistentDataStore$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.display.PersistentDataStore.this.lambda$save$0(byteArrayOutputStream);
                }
            });
        } catch (java.io.IOException e) {
            android.util.Slog.w(TAG, "Failed to process the XML serializer.", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$save$0(java.io.ByteArrayOutputStream byteArrayOutputStream) {
        com.android.server.display.PersistentDataStore.Injector injector;
        synchronized (this.mFileAccessLock) {
            java.io.OutputStream outputStream = null;
            try {
                try {
                    outputStream = this.mInjector.startWrite();
                    byteArrayOutputStream.writeTo(outputStream);
                    outputStream.flush();
                    injector = this.mInjector;
                } catch (java.io.IOException e) {
                    android.util.Slog.w(TAG, "Failed to save display manager persistent store data.", e);
                    if (outputStream != null) {
                        injector = this.mInjector;
                    }
                }
                injector.finishWrite(outputStream, true);
            } catch (java.lang.Throwable th) {
                if (outputStream != null) {
                    this.mInjector.finishWrite(outputStream, true);
                }
                throw th;
            }
        }
    }

    private void loadFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        com.android.internal.util.XmlUtils.beginDocument(typedXmlPullParser, TAG_DISPLAY_MANAGER_STATE);
        int depth = typedXmlPullParser.getDepth();
        while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
            if (typedXmlPullParser.getName().equals(TAG_REMEMBERED_WIFI_DISPLAYS)) {
                loadRememberedWifiDisplaysFromXml(typedXmlPullParser);
            }
            if (typedXmlPullParser.getName().equals(TAG_DISPLAY_STATES)) {
                loadDisplaysFromXml(typedXmlPullParser);
            }
            if (typedXmlPullParser.getName().equals(TAG_STABLE_DEVICE_VALUES)) {
                this.mStableDeviceValues.loadFromXml(typedXmlPullParser);
            }
            if (typedXmlPullParser.getName().equals(TAG_BRIGHTNESS_CONFIGURATIONS)) {
                this.mGlobalBrightnessConfigurations.loadFromXml(typedXmlPullParser);
            }
            if (typedXmlPullParser.getName().equals(TAG_BRIGHTNESS_NITS_FOR_DEFAULT_DISPLAY)) {
                this.mBrightnessNitsForDefaultDisplay = java.lang.Float.parseFloat(typedXmlPullParser.nextText());
            }
        }
    }

    private void loadRememberedWifiDisplaysFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        int depth = typedXmlPullParser.getDepth();
        while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
            if (typedXmlPullParser.getName().equals(TAG_WIFI_DISPLAY)) {
                java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_DEVICE_ADDRESS);
                java.lang.String attributeValue2 = typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_DEVICE_NAME);
                java.lang.String attributeValue3 = typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_DEVICE_ALIAS);
                if (attributeValue == null || attributeValue2 == null) {
                    throw new org.xmlpull.v1.XmlPullParserException("Missing deviceAddress or deviceName attribute on wifi-display.");
                }
                if (findRememberedWifiDisplay(attributeValue) >= 0) {
                    throw new org.xmlpull.v1.XmlPullParserException("Found duplicate wifi display device address.");
                }
                this.mRememberedWifiDisplays.add(new android.hardware.display.WifiDisplay(attributeValue, attributeValue2, attributeValue3, false, false, false));
            }
        }
    }

    private void loadDisplaysFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        int depth = typedXmlPullParser.getDepth();
        while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
            if (typedXmlPullParser.getName().equals(TAG_DISPLAY)) {
                java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_UNIQUE_ID);
                if (attributeValue == null) {
                    throw new org.xmlpull.v1.XmlPullParserException("Missing unique-id attribute on display.");
                }
                if (this.mDisplayStates.containsKey(attributeValue)) {
                    throw new org.xmlpull.v1.XmlPullParserException("Found duplicate display.");
                }
                com.android.server.display.PersistentDataStore.DisplayState displayState = new com.android.server.display.PersistentDataStore.DisplayState();
                displayState.loadFromXml(typedXmlPullParser);
                this.mDisplayStates.put(attributeValue, displayState);
            }
        }
    }

    private void saveToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        typedXmlSerializer.startDocument((java.lang.String) null, true);
        typedXmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        typedXmlSerializer.startTag((java.lang.String) null, TAG_DISPLAY_MANAGER_STATE);
        typedXmlSerializer.startTag((java.lang.String) null, TAG_REMEMBERED_WIFI_DISPLAYS);
        java.util.Iterator<android.hardware.display.WifiDisplay> it = this.mRememberedWifiDisplays.iterator();
        while (it.hasNext()) {
            android.hardware.display.WifiDisplay next = it.next();
            typedXmlSerializer.startTag((java.lang.String) null, TAG_WIFI_DISPLAY);
            typedXmlSerializer.attribute((java.lang.String) null, ATTR_DEVICE_ADDRESS, next.getDeviceAddress());
            typedXmlSerializer.attribute((java.lang.String) null, ATTR_DEVICE_NAME, next.getDeviceName());
            if (next.getDeviceAlias() != null) {
                typedXmlSerializer.attribute((java.lang.String) null, ATTR_DEVICE_ALIAS, next.getDeviceAlias());
            }
            typedXmlSerializer.endTag((java.lang.String) null, TAG_WIFI_DISPLAY);
        }
        typedXmlSerializer.endTag((java.lang.String) null, TAG_REMEMBERED_WIFI_DISPLAYS);
        typedXmlSerializer.startTag((java.lang.String) null, TAG_DISPLAY_STATES);
        for (java.util.Map.Entry<java.lang.String, com.android.server.display.PersistentDataStore.DisplayState> entry : this.mDisplayStates.entrySet()) {
            java.lang.String key = entry.getKey();
            com.android.server.display.PersistentDataStore.DisplayState value = entry.getValue();
            typedXmlSerializer.startTag((java.lang.String) null, TAG_DISPLAY);
            typedXmlSerializer.attribute((java.lang.String) null, ATTR_UNIQUE_ID, key);
            value.saveToXml(typedXmlSerializer);
            typedXmlSerializer.endTag((java.lang.String) null, TAG_DISPLAY);
        }
        typedXmlSerializer.endTag((java.lang.String) null, TAG_DISPLAY_STATES);
        typedXmlSerializer.startTag((java.lang.String) null, TAG_STABLE_DEVICE_VALUES);
        this.mStableDeviceValues.saveToXml(typedXmlSerializer);
        typedXmlSerializer.endTag((java.lang.String) null, TAG_STABLE_DEVICE_VALUES);
        typedXmlSerializer.startTag((java.lang.String) null, TAG_BRIGHTNESS_CONFIGURATIONS);
        this.mGlobalBrightnessConfigurations.saveToXml(typedXmlSerializer);
        typedXmlSerializer.endTag((java.lang.String) null, TAG_BRIGHTNESS_CONFIGURATIONS);
        typedXmlSerializer.startTag((java.lang.String) null, TAG_BRIGHTNESS_NITS_FOR_DEFAULT_DISPLAY);
        typedXmlSerializer.text(java.lang.Float.toString(this.mBrightnessNitsForDefaultDisplay));
        typedXmlSerializer.endTag((java.lang.String) null, TAG_BRIGHTNESS_NITS_FOR_DEFAULT_DISPLAY);
        typedXmlSerializer.endTag((java.lang.String) null, TAG_DISPLAY_MANAGER_STATE);
        typedXmlSerializer.endDocument();
    }

    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println("PersistentDataStore");
        printWriter.println("  mLoaded=" + this.mLoaded);
        printWriter.println("  mDirty=" + this.mDirty);
        printWriter.println("  RememberedWifiDisplays:");
        java.util.Iterator<android.hardware.display.WifiDisplay> it = this.mRememberedWifiDisplays.iterator();
        int i = 0;
        int i2 = 0;
        while (it.hasNext()) {
            printWriter.println("    " + i2 + ": " + it.next());
            i2++;
        }
        printWriter.println("  DisplayStates:");
        for (java.util.Map.Entry<java.lang.String, com.android.server.display.PersistentDataStore.DisplayState> entry : this.mDisplayStates.entrySet()) {
            printWriter.println("    " + i + ": " + entry.getKey());
            entry.getValue().dump(printWriter, "      ");
            i++;
        }
        printWriter.println("  StableDeviceValues:");
        this.mStableDeviceValues.dump(printWriter, "      ");
        printWriter.println("  GlobalBrightnessConfigurations:");
        this.mGlobalBrightnessConfigurations.dump(printWriter, "      ");
        printWriter.println("  mBrightnessNitsForDefaultDisplay=" + this.mBrightnessNitsForDefaultDisplay);
    }

    private static final class DisplayState {
        private int mColorMode;
        private com.android.server.display.PersistentDataStore.BrightnessConfigurations mDisplayBrightnessConfigurations;
        private int mHeight;
        private android.util.SparseArray<java.lang.Float> mPerUserBrightness;
        private float mRefreshRate;
        private int mWidth;

        private DisplayState() {
            this.mPerUserBrightness = new android.util.SparseArray<>();
            this.mDisplayBrightnessConfigurations = new com.android.server.display.PersistentDataStore.BrightnessConfigurations();
        }

        public boolean setColorMode(int i) {
            if (i == this.mColorMode) {
                return false;
            }
            this.mColorMode = i;
            return true;
        }

        public int getColorMode() {
            return this.mColorMode;
        }

        public boolean setBrightness(float f, int i) {
            this.mPerUserBrightness.remove(-1);
            if (getBrightness(i) == f) {
                return false;
            }
            this.mPerUserBrightness.set(i, java.lang.Float.valueOf(f));
            return true;
        }

        public float getBrightness(int i) {
            android.util.SparseArray<java.lang.Float> sparseArray = this.mPerUserBrightness;
            java.lang.Float valueOf = java.lang.Float.valueOf(Float.NaN);
            float floatValue = sparseArray.get(i, valueOf).floatValue();
            if (java.lang.Float.isNaN(floatValue)) {
                return this.mPerUserBrightness.get(-1, valueOf).floatValue();
            }
            return floatValue;
        }

        public boolean setBrightnessConfiguration(android.hardware.display.BrightnessConfiguration brightnessConfiguration, int i, java.lang.String str) {
            this.mDisplayBrightnessConfigurations.setBrightnessConfigurationForUser(brightnessConfiguration, i, str);
            return true;
        }

        public android.hardware.display.BrightnessConfiguration getBrightnessConfiguration(int i) {
            return (android.hardware.display.BrightnessConfiguration) this.mDisplayBrightnessConfigurations.mConfigurations.get(i);
        }

        public boolean setResolution(int i, int i2) {
            if (i == this.mWidth && i2 == this.mHeight) {
                return false;
            }
            this.mWidth = i;
            this.mHeight = i2;
            return true;
        }

        public android.graphics.Point getResolution() {
            return new android.graphics.Point(this.mWidth, this.mHeight);
        }

        public boolean setRefreshRate(float f) {
            if (f == this.mRefreshRate) {
                return false;
            }
            this.mRefreshRate = f;
            return true;
        }

        public float getRefreshRate() {
            return this.mRefreshRate;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public void loadFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            char c;
            int depth = typedXmlPullParser.getDepth();
            while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
                java.lang.String name = typedXmlPullParser.getName();
                switch (name.hashCode()) {
                    case -1377859227:
                        if (name.equals(com.android.server.display.PersistentDataStore.TAG_RESOLUTION_WIDTH)) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1321967815:
                        if (name.equals(com.android.server.display.PersistentDataStore.TAG_BRIGHTNESS_CONFIGURATIONS)) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case -945778443:
                        if (name.equals(com.android.server.display.PersistentDataStore.TAG_BRIGHTNESS_VALUE)) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case -196957848:
                        if (name.equals(com.android.server.display.PersistentDataStore.TAG_RESOLUTION_HEIGHT)) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case -92443502:
                        if (name.equals(com.android.server.display.PersistentDataStore.TAG_REFRESH_RATE)) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1243304397:
                        if (name.equals(com.android.server.display.PersistentDataStore.TAG_COLOR_MODE)) {
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
                        this.mColorMode = java.lang.Integer.parseInt(typedXmlPullParser.nextText());
                        break;
                    case 1:
                        loadBrightnessFromXml(typedXmlPullParser);
                        break;
                    case 2:
                        this.mDisplayBrightnessConfigurations.loadFromXml(typedXmlPullParser);
                        break;
                    case 3:
                        this.mWidth = java.lang.Integer.parseInt(typedXmlPullParser.nextText());
                        break;
                    case 4:
                        this.mHeight = java.lang.Integer.parseInt(typedXmlPullParser.nextText());
                        break;
                    case 5:
                        this.mRefreshRate = java.lang.Float.parseFloat(typedXmlPullParser.nextText());
                        break;
                }
            }
        }

        public void saveToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
            typedXmlSerializer.startTag((java.lang.String) null, com.android.server.display.PersistentDataStore.TAG_COLOR_MODE);
            typedXmlSerializer.text(java.lang.Integer.toString(this.mColorMode));
            typedXmlSerializer.endTag((java.lang.String) null, com.android.server.display.PersistentDataStore.TAG_COLOR_MODE);
            for (int i = 0; i < this.mPerUserBrightness.size(); i++) {
                typedXmlSerializer.startTag((java.lang.String) null, com.android.server.display.PersistentDataStore.TAG_BRIGHTNESS_VALUE);
                typedXmlSerializer.attributeInt((java.lang.String) null, com.android.server.display.PersistentDataStore.ATTR_USER_SERIAL, this.mPerUserBrightness.keyAt(i));
                typedXmlSerializer.text(java.lang.Float.toString(this.mPerUserBrightness.valueAt(i).floatValue()));
                typedXmlSerializer.endTag((java.lang.String) null, com.android.server.display.PersistentDataStore.TAG_BRIGHTNESS_VALUE);
            }
            typedXmlSerializer.startTag((java.lang.String) null, com.android.server.display.PersistentDataStore.TAG_BRIGHTNESS_CONFIGURATIONS);
            this.mDisplayBrightnessConfigurations.saveToXml(typedXmlSerializer);
            typedXmlSerializer.endTag((java.lang.String) null, com.android.server.display.PersistentDataStore.TAG_BRIGHTNESS_CONFIGURATIONS);
            typedXmlSerializer.startTag((java.lang.String) null, com.android.server.display.PersistentDataStore.TAG_RESOLUTION_WIDTH);
            typedXmlSerializer.text(java.lang.Integer.toString(this.mWidth));
            typedXmlSerializer.endTag((java.lang.String) null, com.android.server.display.PersistentDataStore.TAG_RESOLUTION_WIDTH);
            typedXmlSerializer.startTag((java.lang.String) null, com.android.server.display.PersistentDataStore.TAG_RESOLUTION_HEIGHT);
            typedXmlSerializer.text(java.lang.Integer.toString(this.mHeight));
            typedXmlSerializer.endTag((java.lang.String) null, com.android.server.display.PersistentDataStore.TAG_RESOLUTION_HEIGHT);
            typedXmlSerializer.startTag((java.lang.String) null, com.android.server.display.PersistentDataStore.TAG_REFRESH_RATE);
            typedXmlSerializer.text(java.lang.Float.toString(this.mRefreshRate));
            typedXmlSerializer.endTag((java.lang.String) null, com.android.server.display.PersistentDataStore.TAG_REFRESH_RATE);
        }

        public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
            printWriter.println(str + "ColorMode=" + this.mColorMode);
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(str);
            sb.append("BrightnessValues: ");
            printWriter.println(sb.toString());
            for (int i = 0; i < this.mPerUserBrightness.size(); i++) {
                printWriter.println("User: " + this.mPerUserBrightness.keyAt(i) + " Value: " + this.mPerUserBrightness.valueAt(i));
            }
            printWriter.println(str + "DisplayBrightnessConfigurations: ");
            this.mDisplayBrightnessConfigurations.dump(printWriter, str);
            printWriter.println(str + "Resolution=" + this.mWidth + " " + this.mHeight);
            java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
            sb2.append(str);
            sb2.append("RefreshRate=");
            sb2.append(this.mRefreshRate);
            printWriter.println(sb2.toString());
        }

        private void loadBrightnessFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            int i;
            try {
                i = typedXmlPullParser.getAttributeInt((java.lang.String) null, com.android.server.display.PersistentDataStore.ATTR_USER_SERIAL);
            } catch (java.lang.NumberFormatException | org.xmlpull.v1.XmlPullParserException e) {
                android.util.Slog.e(com.android.server.display.PersistentDataStore.TAG, "Failed to read user serial", e);
                i = -1;
            }
            try {
                this.mPerUserBrightness.set(i, java.lang.Float.valueOf(java.lang.Float.parseFloat(typedXmlPullParser.nextText())));
            } catch (java.lang.NumberFormatException e2) {
                android.util.Slog.e(com.android.server.display.PersistentDataStore.TAG, "Failed to read brightness", e2);
            }
        }
    }

    private static final class StableDeviceValues {
        private int mHeight;
        private int mWidth;

        private StableDeviceValues() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.graphics.Point getDisplaySize() {
            return new android.graphics.Point(this.mWidth, this.mHeight);
        }

        public boolean setDisplaySize(android.graphics.Point point) {
            if (this.mWidth != point.x || this.mHeight != point.y) {
                this.mWidth = point.x;
                this.mHeight = point.y;
                return true;
            }
            return false;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public void loadFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            char c;
            int depth = typedXmlPullParser.getDepth();
            while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
                java.lang.String name = typedXmlPullParser.getName();
                switch (name.hashCode()) {
                    case -1635792540:
                        if (name.equals(com.android.server.display.PersistentDataStore.TAG_STABLE_DISPLAY_HEIGHT)) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1069578729:
                        if (name.equals(com.android.server.display.PersistentDataStore.TAG_STABLE_DISPLAY_WIDTH)) {
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
                        this.mWidth = loadIntValue(typedXmlPullParser);
                        break;
                    case 1:
                        this.mHeight = loadIntValue(typedXmlPullParser);
                        break;
                }
            }
        }

        private static int loadIntValue(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            try {
                return java.lang.Integer.parseInt(typedXmlPullParser.nextText());
            } catch (java.lang.NumberFormatException e) {
                return 0;
            }
        }

        public void saveToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
            if (this.mWidth > 0 && this.mHeight > 0) {
                typedXmlSerializer.startTag((java.lang.String) null, com.android.server.display.PersistentDataStore.TAG_STABLE_DISPLAY_WIDTH);
                typedXmlSerializer.text(java.lang.Integer.toString(this.mWidth));
                typedXmlSerializer.endTag((java.lang.String) null, com.android.server.display.PersistentDataStore.TAG_STABLE_DISPLAY_WIDTH);
                typedXmlSerializer.startTag((java.lang.String) null, com.android.server.display.PersistentDataStore.TAG_STABLE_DISPLAY_HEIGHT);
                typedXmlSerializer.text(java.lang.Integer.toString(this.mHeight));
                typedXmlSerializer.endTag((java.lang.String) null, com.android.server.display.PersistentDataStore.TAG_STABLE_DISPLAY_HEIGHT);
            }
        }

        public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
            printWriter.println(str + "StableDisplayWidth=" + this.mWidth);
            printWriter.println(str + "StableDisplayHeight=" + this.mHeight);
        }
    }

    private static final class BrightnessConfigurations {
        private final android.util.SparseArray<android.hardware.display.BrightnessConfiguration> mConfigurations = new android.util.SparseArray<>();
        private final android.util.SparseLongArray mTimeStamps = new android.util.SparseLongArray();
        private final android.util.SparseArray<java.lang.String> mPackageNames = new android.util.SparseArray<>();

        /* JADX INFO: Access modifiers changed from: private */
        public boolean setBrightnessConfigurationForUser(android.hardware.display.BrightnessConfiguration brightnessConfiguration, int i, java.lang.String str) {
            android.hardware.display.BrightnessConfiguration brightnessConfiguration2 = this.mConfigurations.get(i);
            if (brightnessConfiguration2 == brightnessConfiguration) {
                return false;
            }
            if (brightnessConfiguration2 == null || !brightnessConfiguration2.equals(brightnessConfiguration)) {
                if (brightnessConfiguration != null) {
                    if (str == null) {
                        this.mPackageNames.remove(i);
                    } else {
                        this.mPackageNames.put(i, str);
                    }
                    this.mTimeStamps.put(i, java.lang.System.currentTimeMillis());
                    this.mConfigurations.put(i, brightnessConfiguration);
                    return true;
                }
                this.mPackageNames.remove(i);
                this.mTimeStamps.delete(i);
                this.mConfigurations.remove(i);
                return true;
            }
            return false;
        }

        public android.hardware.display.BrightnessConfiguration getBrightnessConfiguration(int i) {
            return this.mConfigurations.get(i);
        }

        public void loadFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            int i;
            int depth = typedXmlPullParser.getDepth();
            while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
                if (com.android.server.display.PersistentDataStore.TAG_BRIGHTNESS_CONFIGURATION.equals(typedXmlPullParser.getName())) {
                    try {
                        i = typedXmlPullParser.getAttributeInt((java.lang.String) null, com.android.server.display.PersistentDataStore.ATTR_USER_SERIAL);
                    } catch (java.lang.NumberFormatException e) {
                        android.util.Slog.e(com.android.server.display.PersistentDataStore.TAG, "Failed to read in brightness configuration", e);
                        i = -1;
                    }
                    java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, com.android.server.display.PersistentDataStore.ATTR_PACKAGE_NAME);
                    long attributeLong = typedXmlPullParser.getAttributeLong((java.lang.String) null, "timestamp", -1L);
                    try {
                        android.hardware.display.BrightnessConfiguration loadFromXml = android.hardware.display.BrightnessConfiguration.loadFromXml(typedXmlPullParser);
                        if (i >= 0 && loadFromXml != null) {
                            this.mConfigurations.put(i, loadFromXml);
                            if (attributeLong != -1) {
                                this.mTimeStamps.put(i, attributeLong);
                            }
                            if (attributeValue != null) {
                                this.mPackageNames.put(i, attributeValue);
                            }
                        }
                    } catch (java.lang.IllegalArgumentException e2) {
                        android.util.Slog.e(com.android.server.display.PersistentDataStore.TAG, "Failed to load brightness configuration!", e2);
                    }
                }
            }
        }

        public void saveToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
            for (int i = 0; i < this.mConfigurations.size(); i++) {
                int keyAt = this.mConfigurations.keyAt(i);
                android.hardware.display.BrightnessConfiguration valueAt = this.mConfigurations.valueAt(i);
                typedXmlSerializer.startTag((java.lang.String) null, com.android.server.display.PersistentDataStore.TAG_BRIGHTNESS_CONFIGURATION);
                typedXmlSerializer.attributeInt((java.lang.String) null, com.android.server.display.PersistentDataStore.ATTR_USER_SERIAL, keyAt);
                java.lang.String str = this.mPackageNames.get(keyAt);
                if (str != null) {
                    typedXmlSerializer.attribute((java.lang.String) null, com.android.server.display.PersistentDataStore.ATTR_PACKAGE_NAME, str);
                }
                long j = this.mTimeStamps.get(keyAt, -1L);
                if (j != -1) {
                    typedXmlSerializer.attributeLong((java.lang.String) null, "timestamp", j);
                }
                valueAt.saveToXml(typedXmlSerializer);
                typedXmlSerializer.endTag((java.lang.String) null, com.android.server.display.PersistentDataStore.TAG_BRIGHTNESS_CONFIGURATION);
            }
        }

        public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
            for (int i = 0; i < this.mConfigurations.size(); i++) {
                int keyAt = this.mConfigurations.keyAt(i);
                long j = this.mTimeStamps.get(keyAt, -1L);
                java.lang.String str2 = this.mPackageNames.get(keyAt);
                printWriter.println(str + "User " + keyAt + ":");
                if (j != -1) {
                    printWriter.println(str + "  set at: " + android.util.TimeUtils.formatForLogging(j));
                }
                if (str2 != null) {
                    printWriter.println(str + "  set by: " + str2);
                }
                printWriter.println(str + "  " + this.mConfigurations.valueAt(i));
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static class Injector {
        private final android.util.AtomicFile mAtomicFile = new android.util.AtomicFile(new java.io.File("/data/system/display-manager-state.xml"), "display-state");

        public java.io.InputStream openRead() throws java.io.FileNotFoundException {
            return this.mAtomicFile.openRead();
        }

        public java.io.OutputStream startWrite() throws java.io.IOException {
            return this.mAtomicFile.startWrite();
        }

        public void finishWrite(java.io.OutputStream outputStream, boolean z) {
            if (!(outputStream instanceof java.io.FileOutputStream)) {
                throw new java.lang.IllegalArgumentException("Unexpected OutputStream as argument: " + outputStream);
            }
            java.io.FileOutputStream fileOutputStream = (java.io.FileOutputStream) outputStream;
            if (z) {
                this.mAtomicFile.finishWrite(fileOutputStream);
            } else {
                this.mAtomicFile.failWrite(fileOutputStream);
            }
        }
    }
}
