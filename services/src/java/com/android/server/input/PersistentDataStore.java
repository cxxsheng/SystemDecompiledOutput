package com.android.server.input;

/* loaded from: classes2.dex */
final class PersistentDataStore {
    private static final int INVALID_VALUE = -1;
    static final java.lang.String TAG = "InputManager";
    private boolean mDirty;
    private com.android.server.input.PersistentDataStore.Injector mInjector;
    private final java.util.HashMap<java.lang.String, com.android.server.input.PersistentDataStore.InputDeviceState> mInputDevices;
    private java.util.Map<java.lang.Integer, java.lang.Integer> mKeyRemapping;
    private boolean mLoaded;

    public PersistentDataStore() {
        this(new com.android.server.input.PersistentDataStore.Injector());
    }

    @com.android.internal.annotations.VisibleForTesting
    PersistentDataStore(com.android.server.input.PersistentDataStore.Injector injector) {
        this.mInputDevices = new java.util.HashMap<>();
        this.mKeyRemapping = new java.util.HashMap();
        this.mInjector = injector;
    }

    public void saveIfNeeded() {
        if (this.mDirty) {
            save();
            this.mDirty = false;
        }
    }

    public boolean hasInputDeviceEntry(java.lang.String str) {
        return getInputDeviceState(str) != null;
    }

    public android.hardware.input.TouchCalibration getTouchCalibration(java.lang.String str, int i) {
        com.android.server.input.PersistentDataStore.InputDeviceState inputDeviceState = getInputDeviceState(str);
        if (inputDeviceState == null) {
            return android.hardware.input.TouchCalibration.IDENTITY;
        }
        android.hardware.input.TouchCalibration touchCalibration = inputDeviceState.getTouchCalibration(i);
        if (touchCalibration == null) {
            return android.hardware.input.TouchCalibration.IDENTITY;
        }
        return touchCalibration;
    }

    public boolean setTouchCalibration(java.lang.String str, int i, android.hardware.input.TouchCalibration touchCalibration) {
        if (getOrCreateInputDeviceState(str).setTouchCalibration(i, touchCalibration)) {
            setDirty();
            return true;
        }
        return false;
    }

    @android.annotation.Nullable
    public java.lang.String getCurrentKeyboardLayout(java.lang.String str) {
        com.android.server.input.PersistentDataStore.InputDeviceState inputDeviceState = getInputDeviceState(str);
        if (inputDeviceState != null) {
            return inputDeviceState.getCurrentKeyboardLayout();
        }
        return null;
    }

    public boolean setCurrentKeyboardLayout(java.lang.String str, java.lang.String str2) {
        if (getOrCreateInputDeviceState(str).setCurrentKeyboardLayout(str2)) {
            setDirty();
            return true;
        }
        return false;
    }

    @android.annotation.Nullable
    public java.lang.String getKeyboardLayout(java.lang.String str, java.lang.String str2) {
        com.android.server.input.PersistentDataStore.InputDeviceState inputDeviceState = getInputDeviceState(str);
        if (inputDeviceState != null) {
            return inputDeviceState.getKeyboardLayout(str2);
        }
        return null;
    }

    public boolean setKeyboardLayout(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        if (getOrCreateInputDeviceState(str).setKeyboardLayout(str2, str3)) {
            setDirty();
            return true;
        }
        return false;
    }

    public boolean setSelectedKeyboardLayouts(java.lang.String str, @android.annotation.NonNull java.util.Set<java.lang.String> set) {
        if (getOrCreateInputDeviceState(str).setSelectedKeyboardLayouts(set)) {
            setDirty();
            return true;
        }
        return false;
    }

    public java.lang.String[] getKeyboardLayouts(java.lang.String str) {
        com.android.server.input.PersistentDataStore.InputDeviceState inputDeviceState = getInputDeviceState(str);
        if (inputDeviceState == null) {
            return (java.lang.String[]) com.android.internal.util.ArrayUtils.emptyArray(java.lang.String.class);
        }
        return inputDeviceState.getKeyboardLayouts();
    }

    public boolean addKeyboardLayout(java.lang.String str, java.lang.String str2) {
        if (getOrCreateInputDeviceState(str).addKeyboardLayout(str2)) {
            setDirty();
            return true;
        }
        return false;
    }

    public boolean removeKeyboardLayout(java.lang.String str, java.lang.String str2) {
        if (getOrCreateInputDeviceState(str).removeKeyboardLayout(str2)) {
            setDirty();
            return true;
        }
        return false;
    }

    public boolean switchKeyboardLayout(java.lang.String str, int i) {
        com.android.server.input.PersistentDataStore.InputDeviceState inputDeviceState = getInputDeviceState(str);
        if (inputDeviceState != null && inputDeviceState.switchKeyboardLayout(i)) {
            setDirty();
            return true;
        }
        return false;
    }

    public boolean setKeyboardBacklightBrightness(java.lang.String str, int i, int i2) {
        if (getOrCreateInputDeviceState(str).setKeyboardBacklightBrightness(i, i2)) {
            setDirty();
            return true;
        }
        return false;
    }

    public java.util.OptionalInt getKeyboardBacklightBrightness(java.lang.String str, int i) {
        com.android.server.input.PersistentDataStore.InputDeviceState inputDeviceState = getInputDeviceState(str);
        if (inputDeviceState == null) {
            return java.util.OptionalInt.empty();
        }
        return inputDeviceState.getKeyboardBacklightBrightness(i);
    }

    public boolean remapKey(int i, int i2) {
        loadIfNeeded();
        if (this.mKeyRemapping.getOrDefault(java.lang.Integer.valueOf(i), -1).intValue() == i2) {
            return false;
        }
        this.mKeyRemapping.put(java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
        setDirty();
        return true;
    }

    public boolean clearMappedKey(int i) {
        loadIfNeeded();
        if (this.mKeyRemapping.containsKey(java.lang.Integer.valueOf(i))) {
            this.mKeyRemapping.remove(java.lang.Integer.valueOf(i));
            setDirty();
            return true;
        }
        return true;
    }

    public java.util.Map<java.lang.Integer, java.lang.Integer> getKeyRemapping() {
        loadIfNeeded();
        return new java.util.HashMap(this.mKeyRemapping);
    }

    public boolean removeUninstalledKeyboardLayouts(java.util.Set<java.lang.String> set) {
        java.util.Iterator<com.android.server.input.PersistentDataStore.InputDeviceState> it = this.mInputDevices.values().iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (it.next().removeUninstalledKeyboardLayouts(set)) {
                z = true;
            }
        }
        if (!z) {
            return false;
        }
        setDirty();
        return true;
    }

    private com.android.server.input.PersistentDataStore.InputDeviceState getInputDeviceState(java.lang.String str) {
        loadIfNeeded();
        return this.mInputDevices.get(str);
    }

    private com.android.server.input.PersistentDataStore.InputDeviceState getOrCreateInputDeviceState(java.lang.String str) {
        loadIfNeeded();
        com.android.server.input.PersistentDataStore.InputDeviceState inputDeviceState = this.mInputDevices.get(str);
        if (inputDeviceState == null) {
            com.android.server.input.PersistentDataStore.InputDeviceState inputDeviceState2 = new com.android.server.input.PersistentDataStore.InputDeviceState();
            this.mInputDevices.put(str, inputDeviceState2);
            setDirty();
            return inputDeviceState2;
        }
        return inputDeviceState;
    }

    private void loadIfNeeded() {
        if (!this.mLoaded) {
            load();
            this.mLoaded = true;
        }
    }

    private void setDirty() {
        this.mDirty = true;
    }

    private void clearState() {
        this.mKeyRemapping.clear();
        this.mInputDevices.clear();
    }

    private void load() {
        java.io.InputStream openRead;
        clearState();
        try {
            try {
                openRead = this.mInjector.openRead();
                try {
                    loadFromXml(android.util.Xml.resolvePullParser(openRead));
                } catch (java.io.IOException e) {
                    android.util.Slog.w(TAG, "Failed to load input manager persistent store data.", e);
                    clearState();
                } catch (org.xmlpull.v1.XmlPullParserException e2) {
                    android.util.Slog.w(TAG, "Failed to load input manager persistent store data.", e2);
                    clearState();
                }
            } finally {
                libcore.io.IoUtils.closeQuietly(openRead);
            }
        } catch (java.io.FileNotFoundException e3) {
        }
    }

    private void save() {
        try {
            java.io.FileOutputStream startWrite = this.mInjector.startWrite();
            try {
                com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(startWrite);
                saveToXml(resolveSerializer);
                resolveSerializer.flush();
                this.mInjector.finishWrite(startWrite, true);
            } catch (java.lang.Throwable th) {
                this.mInjector.finishWrite(startWrite, false);
                throw th;
            }
        } catch (java.io.IOException e) {
            android.util.Slog.w(TAG, "Failed to save input manager persistent store data.", e);
        }
    }

    private void loadFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        com.android.internal.util.XmlUtils.beginDocument(typedXmlPullParser, "input-manager-state");
        int depth = typedXmlPullParser.getDepth();
        while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
            if (typedXmlPullParser.getName().equals("key-remapping")) {
                loadKeyRemappingFromXml(typedXmlPullParser);
            } else if (typedXmlPullParser.getName().equals("input-devices")) {
                loadInputDevicesFromXml(typedXmlPullParser);
            }
        }
    }

    private void loadInputDevicesFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        int depth = typedXmlPullParser.getDepth();
        while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
            if (typedXmlPullParser.getName().equals("input-device")) {
                java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, "descriptor");
                if (attributeValue == null) {
                    throw new org.xmlpull.v1.XmlPullParserException("Missing descriptor attribute on input-device.");
                }
                if (this.mInputDevices.containsKey(attributeValue)) {
                    throw new org.xmlpull.v1.XmlPullParserException("Found duplicate input device.");
                }
                com.android.server.input.PersistentDataStore.InputDeviceState inputDeviceState = new com.android.server.input.PersistentDataStore.InputDeviceState();
                inputDeviceState.loadFromXml(typedXmlPullParser);
                this.mInputDevices.put(attributeValue, inputDeviceState);
            }
        }
    }

    private void loadKeyRemappingFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        int depth = typedXmlPullParser.getDepth();
        while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
            if (typedXmlPullParser.getName().equals("remap")) {
                this.mKeyRemapping.put(java.lang.Integer.valueOf(typedXmlPullParser.getAttributeInt((java.lang.String) null, "from-key")), java.lang.Integer.valueOf(typedXmlPullParser.getAttributeInt((java.lang.String) null, "to-key")));
            }
        }
    }

    private void saveToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        typedXmlSerializer.startDocument((java.lang.String) null, true);
        typedXmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        typedXmlSerializer.startTag((java.lang.String) null, "input-manager-state");
        typedXmlSerializer.startTag((java.lang.String) null, "key-remapping");
        java.util.Iterator<java.lang.Integer> it = this.mKeyRemapping.keySet().iterator();
        while (it.hasNext()) {
            int intValue = it.next().intValue();
            int intValue2 = this.mKeyRemapping.get(java.lang.Integer.valueOf(intValue)).intValue();
            typedXmlSerializer.startTag((java.lang.String) null, "remap");
            typedXmlSerializer.attributeInt((java.lang.String) null, "from-key", intValue);
            typedXmlSerializer.attributeInt((java.lang.String) null, "to-key", intValue2);
            typedXmlSerializer.endTag((java.lang.String) null, "remap");
        }
        typedXmlSerializer.endTag((java.lang.String) null, "key-remapping");
        typedXmlSerializer.startTag((java.lang.String) null, "input-devices");
        for (java.util.Map.Entry<java.lang.String, com.android.server.input.PersistentDataStore.InputDeviceState> entry : this.mInputDevices.entrySet()) {
            java.lang.String key = entry.getKey();
            com.android.server.input.PersistentDataStore.InputDeviceState value = entry.getValue();
            typedXmlSerializer.startTag((java.lang.String) null, "input-device");
            typedXmlSerializer.attribute((java.lang.String) null, "descriptor", key);
            value.saveToXml(typedXmlSerializer);
            typedXmlSerializer.endTag((java.lang.String) null, "input-device");
        }
        typedXmlSerializer.endTag((java.lang.String) null, "input-devices");
        typedXmlSerializer.endTag((java.lang.String) null, "input-manager-state");
        typedXmlSerializer.endDocument();
    }

    private static final class InputDeviceState {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final java.lang.String[] CALIBRATION_NAME = {"x_scale", "x_ymix", "x_offset", "y_xmix", "y_scale", "y_offset"};

        @android.annotation.Nullable
        private java.lang.String mCurrentKeyboardLayout;
        private final android.util.SparseIntArray mKeyboardBacklightBrightnessMap;
        private final java.util.Map<java.lang.String, java.lang.String> mKeyboardLayoutMap;
        private final java.util.ArrayList<java.lang.String> mKeyboardLayouts;
        private java.util.Set<java.lang.String> mSelectedKeyboardLayouts;
        private final android.hardware.input.TouchCalibration[] mTouchCalibration;

        private InputDeviceState() {
            this.mTouchCalibration = new android.hardware.input.TouchCalibration[4];
            this.mKeyboardLayouts = new java.util.ArrayList<>();
            this.mKeyboardBacklightBrightnessMap = new android.util.SparseIntArray();
            this.mKeyboardLayoutMap = new android.util.ArrayMap();
        }

        public android.hardware.input.TouchCalibration getTouchCalibration(int i) {
            try {
                return this.mTouchCalibration[i];
            } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                android.util.Slog.w(com.android.server.input.PersistentDataStore.TAG, "Cannot get touch calibration.", e);
                return null;
            }
        }

        public boolean setTouchCalibration(int i, android.hardware.input.TouchCalibration touchCalibration) {
            try {
                if (touchCalibration.equals(this.mTouchCalibration[i])) {
                    return false;
                }
                this.mTouchCalibration[i] = touchCalibration;
                return true;
            } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                android.util.Slog.w(com.android.server.input.PersistentDataStore.TAG, "Cannot set touch calibration.", e);
                return false;
            }
        }

        @android.annotation.Nullable
        public java.lang.String getKeyboardLayout(java.lang.String str) {
            return this.mKeyboardLayoutMap.get(str);
        }

        public boolean setKeyboardLayout(java.lang.String str, java.lang.String str2) {
            return !java.util.Objects.equals(this.mKeyboardLayoutMap.put(str, str2), str2);
        }

        public boolean setSelectedKeyboardLayouts(@android.annotation.NonNull java.util.Set<java.lang.String> set) {
            if (java.util.Objects.equals(this.mSelectedKeyboardLayouts, set)) {
                return false;
            }
            this.mSelectedKeyboardLayouts = new java.util.HashSet(set);
            return true;
        }

        @android.annotation.Nullable
        public java.lang.String getCurrentKeyboardLayout() {
            return this.mCurrentKeyboardLayout;
        }

        public boolean setCurrentKeyboardLayout(java.lang.String str) {
            if (java.util.Objects.equals(this.mCurrentKeyboardLayout, str)) {
                return false;
            }
            addKeyboardLayout(str);
            this.mCurrentKeyboardLayout = str;
            return true;
        }

        public java.lang.String[] getKeyboardLayouts() {
            if (this.mKeyboardLayouts.isEmpty()) {
                return (java.lang.String[]) com.android.internal.util.ArrayUtils.emptyArray(java.lang.String.class);
            }
            return (java.lang.String[]) this.mKeyboardLayouts.toArray(new java.lang.String[this.mKeyboardLayouts.size()]);
        }

        public boolean addKeyboardLayout(java.lang.String str) {
            int binarySearch = java.util.Collections.binarySearch(this.mKeyboardLayouts, str);
            if (binarySearch >= 0) {
                return false;
            }
            this.mKeyboardLayouts.add((-binarySearch) - 1, str);
            if (this.mCurrentKeyboardLayout == null) {
                this.mCurrentKeyboardLayout = str;
            }
            return true;
        }

        public boolean removeKeyboardLayout(java.lang.String str) {
            int binarySearch = java.util.Collections.binarySearch(this.mKeyboardLayouts, str);
            if (binarySearch < 0) {
                return false;
            }
            this.mKeyboardLayouts.remove(binarySearch);
            updateCurrentKeyboardLayoutIfRemoved(str, binarySearch);
            return true;
        }

        public boolean setKeyboardBacklightBrightness(int i, int i2) {
            if (this.mKeyboardBacklightBrightnessMap.get(i, -1) == i2) {
                return false;
            }
            this.mKeyboardBacklightBrightnessMap.put(i, i2);
            return true;
        }

        public java.util.OptionalInt getKeyboardBacklightBrightness(int i) {
            int i2 = this.mKeyboardBacklightBrightnessMap.get(i, -1);
            return i2 == -1 ? java.util.OptionalInt.empty() : java.util.OptionalInt.of(i2);
        }

        private void updateCurrentKeyboardLayoutIfRemoved(java.lang.String str, int i) {
            if (java.util.Objects.equals(this.mCurrentKeyboardLayout, str)) {
                if (!this.mKeyboardLayouts.isEmpty()) {
                    if (i == this.mKeyboardLayouts.size()) {
                        i = 0;
                    }
                    this.mCurrentKeyboardLayout = this.mKeyboardLayouts.get(i);
                    return;
                }
                this.mCurrentKeyboardLayout = null;
            }
        }

        public boolean switchKeyboardLayout(int i) {
            int i2;
            int size = this.mKeyboardLayouts.size();
            if (size < 2) {
                return false;
            }
            int binarySearch = java.util.Collections.binarySearch(this.mKeyboardLayouts, this.mCurrentKeyboardLayout);
            if (i <= 0) {
                i2 = ((binarySearch + size) - 1) % size;
            } else {
                i2 = (binarySearch + 1) % size;
            }
            this.mCurrentKeyboardLayout = this.mKeyboardLayouts.get(i2);
            return true;
        }

        public boolean removeUninstalledKeyboardLayouts(java.util.Set<java.lang.String> set) {
            int size = this.mKeyboardLayouts.size();
            boolean z = false;
            while (true) {
                int i = size - 1;
                if (size <= 0) {
                    break;
                }
                java.lang.String str = this.mKeyboardLayouts.get(i);
                if (!set.contains(str)) {
                    android.util.Slog.i(com.android.server.input.PersistentDataStore.TAG, "Removing uninstalled keyboard layout " + str);
                    this.mKeyboardLayouts.remove(i);
                    updateCurrentKeyboardLayoutIfRemoved(str, i);
                    z = true;
                }
                size = i;
            }
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (java.lang.String str2 : this.mKeyboardLayoutMap.keySet()) {
                if (!set.contains(this.mKeyboardLayoutMap.get(str2))) {
                    arrayList.add(str2);
                }
            }
            if (!arrayList.isEmpty()) {
                java.util.Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    this.mKeyboardLayoutMap.remove((java.lang.String) it.next());
                }
                return true;
            }
            return z;
        }

        public void loadFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            int stringToSurfaceRotation;
            int depth = typedXmlPullParser.getDepth();
            while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
                if (typedXmlPullParser.getName().equals("keyboard-layout")) {
                    java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, "descriptor");
                    if (attributeValue == null) {
                        throw new org.xmlpull.v1.XmlPullParserException("Missing descriptor attribute on keyboard-layout.");
                    }
                    java.lang.String attributeValue2 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "current");
                    if (this.mKeyboardLayouts.contains(attributeValue)) {
                        throw new org.xmlpull.v1.XmlPullParserException("Found duplicate keyboard layout.");
                    }
                    this.mKeyboardLayouts.add(attributeValue);
                    if (attributeValue2 != null && attributeValue2.equals("true")) {
                        if (this.mCurrentKeyboardLayout != null) {
                            throw new org.xmlpull.v1.XmlPullParserException("Found multiple current keyboard layouts.");
                        }
                        this.mCurrentKeyboardLayout = attributeValue;
                    }
                } else if (typedXmlPullParser.getName().equals("keyed-keyboard-layout")) {
                    java.lang.String attributeValue3 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "key");
                    if (attributeValue3 == null) {
                        throw new org.xmlpull.v1.XmlPullParserException("Missing key attribute on keyed-keyboard-layout.");
                    }
                    java.lang.String attributeValue4 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "layout");
                    if (attributeValue4 == null) {
                        throw new org.xmlpull.v1.XmlPullParserException("Missing layout attribute on keyed-keyboard-layout.");
                    }
                    this.mKeyboardLayoutMap.put(attributeValue3, attributeValue4);
                } else if (typedXmlPullParser.getName().equals("selected-keyboard-layout")) {
                    java.lang.String attributeValue5 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "layout");
                    if (attributeValue5 == null) {
                        throw new org.xmlpull.v1.XmlPullParserException("Missing layout attribute on selected-keyboard-layout.");
                    }
                    if (this.mSelectedKeyboardLayouts == null) {
                        this.mSelectedKeyboardLayouts = new java.util.HashSet();
                    }
                    this.mSelectedKeyboardLayouts.add(attributeValue5);
                } else if (typedXmlPullParser.getName().equals("light-info")) {
                    this.mKeyboardBacklightBrightnessMap.put(typedXmlPullParser.getAttributeInt((java.lang.String) null, "light-id"), typedXmlPullParser.getAttributeInt((java.lang.String) null, "light-brightness"));
                } else if (typedXmlPullParser.getName().equals("calibration")) {
                    java.lang.String attributeValue6 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "format");
                    java.lang.String attributeValue7 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "rotation");
                    if (attributeValue6 == null) {
                        throw new org.xmlpull.v1.XmlPullParserException("Missing format attribute on calibration.");
                    }
                    if (!attributeValue6.equals("affine")) {
                        throw new org.xmlpull.v1.XmlPullParserException("Unsupported format for calibration.");
                    }
                    if (attributeValue7 == null) {
                        stringToSurfaceRotation = -1;
                    } else {
                        try {
                            stringToSurfaceRotation = stringToSurfaceRotation(attributeValue7);
                        } catch (java.lang.IllegalArgumentException e) {
                            throw new org.xmlpull.v1.XmlPullParserException("Unsupported rotation for calibration.");
                        }
                    }
                    float[] affineTransform = android.hardware.input.TouchCalibration.IDENTITY.getAffineTransform();
                    int depth2 = typedXmlPullParser.getDepth();
                    while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth2)) {
                        java.lang.String lowerCase = typedXmlPullParser.getName().toLowerCase();
                        java.lang.String nextText = typedXmlPullParser.nextText();
                        int i = 0;
                        while (true) {
                            if (i < affineTransform.length && i < CALIBRATION_NAME.length) {
                                if (!lowerCase.equals(CALIBRATION_NAME[i])) {
                                    i++;
                                } else {
                                    affineTransform[i] = java.lang.Float.parseFloat(nextText);
                                    break;
                                }
                            }
                        }
                    }
                    if (stringToSurfaceRotation != -1) {
                        this.mTouchCalibration[stringToSurfaceRotation] = new android.hardware.input.TouchCalibration(affineTransform[0], affineTransform[1], affineTransform[2], affineTransform[3], affineTransform[4], affineTransform[5]);
                    } else {
                        for (int i2 = 0; i2 < this.mTouchCalibration.length; i2++) {
                            this.mTouchCalibration[i2] = new android.hardware.input.TouchCalibration(affineTransform[0], affineTransform[1], affineTransform[2], affineTransform[3], affineTransform[4], affineTransform[5]);
                        }
                    }
                } else {
                    continue;
                }
            }
            java.util.Collections.sort(this.mKeyboardLayouts);
            if (this.mCurrentKeyboardLayout == null && !this.mKeyboardLayouts.isEmpty()) {
                this.mCurrentKeyboardLayout = this.mKeyboardLayouts.get(0);
            }
        }

        public void saveToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
            java.util.Iterator<java.lang.String> it = this.mKeyboardLayouts.iterator();
            while (it.hasNext()) {
                java.lang.String next = it.next();
                typedXmlSerializer.startTag((java.lang.String) null, "keyboard-layout");
                typedXmlSerializer.attribute((java.lang.String) null, "descriptor", next);
                if (next.equals(this.mCurrentKeyboardLayout)) {
                    typedXmlSerializer.attributeBoolean((java.lang.String) null, "current", true);
                }
                typedXmlSerializer.endTag((java.lang.String) null, "keyboard-layout");
            }
            for (java.lang.String str : this.mKeyboardLayoutMap.keySet()) {
                typedXmlSerializer.startTag((java.lang.String) null, "keyed-keyboard-layout");
                typedXmlSerializer.attribute((java.lang.String) null, "key", str);
                typedXmlSerializer.attribute((java.lang.String) null, "layout", this.mKeyboardLayoutMap.get(str));
                typedXmlSerializer.endTag((java.lang.String) null, "keyed-keyboard-layout");
            }
            if (this.mSelectedKeyboardLayouts != null) {
                for (java.lang.String str2 : this.mSelectedKeyboardLayouts) {
                    typedXmlSerializer.startTag((java.lang.String) null, "selected-keyboard-layout");
                    typedXmlSerializer.attribute((java.lang.String) null, "layout", str2);
                    typedXmlSerializer.endTag((java.lang.String) null, "selected-keyboard-layout");
                }
            }
            for (int i = 0; i < this.mKeyboardBacklightBrightnessMap.size(); i++) {
                typedXmlSerializer.startTag((java.lang.String) null, "light-info");
                typedXmlSerializer.attributeInt((java.lang.String) null, "light-id", this.mKeyboardBacklightBrightnessMap.keyAt(i));
                typedXmlSerializer.attributeInt((java.lang.String) null, "light-brightness", this.mKeyboardBacklightBrightnessMap.valueAt(i));
                typedXmlSerializer.endTag((java.lang.String) null, "light-info");
            }
            for (int i2 = 0; i2 < this.mTouchCalibration.length; i2++) {
                if (this.mTouchCalibration[i2] != null) {
                    java.lang.String surfaceRotationToString = surfaceRotationToString(i2);
                    float[] affineTransform = this.mTouchCalibration[i2].getAffineTransform();
                    typedXmlSerializer.startTag((java.lang.String) null, "calibration");
                    typedXmlSerializer.attribute((java.lang.String) null, "format", "affine");
                    typedXmlSerializer.attribute((java.lang.String) null, "rotation", surfaceRotationToString);
                    for (int i3 = 0; i3 < affineTransform.length && i3 < CALIBRATION_NAME.length; i3++) {
                        typedXmlSerializer.startTag((java.lang.String) null, CALIBRATION_NAME[i3]);
                        typedXmlSerializer.text(java.lang.Float.toString(affineTransform[i3]));
                        typedXmlSerializer.endTag((java.lang.String) null, CALIBRATION_NAME[i3]);
                    }
                    typedXmlSerializer.endTag((java.lang.String) null, "calibration");
                }
            }
        }

        private static java.lang.String surfaceRotationToString(int i) {
            switch (i) {
                case 0:
                    return "0";
                case 1:
                    return "90";
                case 2:
                    return "180";
                case 3:
                    return "270";
                default:
                    throw new java.lang.IllegalArgumentException("Unsupported surface rotation value" + i);
            }
        }

        private static int stringToSurfaceRotation(java.lang.String str) {
            if ("0".equals(str)) {
                return 0;
            }
            if ("90".equals(str)) {
                return 1;
            }
            if ("180".equals(str)) {
                return 2;
            }
            if ("270".equals(str)) {
                return 3;
            }
            throw new java.lang.IllegalArgumentException("Unsupported surface rotation string '" + str + "'");
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static class Injector {
        private final android.util.AtomicFile mAtomicFile = new android.util.AtomicFile(new java.io.File("/data/system/input-manager-state.xml"), "input-state");

        Injector() {
        }

        java.io.InputStream openRead() throws java.io.FileNotFoundException {
            return this.mAtomicFile.openRead();
        }

        java.io.FileOutputStream startWrite() throws java.io.IOException {
            return this.mAtomicFile.startWrite();
        }

        void finishWrite(java.io.FileOutputStream fileOutputStream, boolean z) {
            if (z) {
                this.mAtomicFile.finishWrite(fileOutputStream);
            } else {
                this.mAtomicFile.failWrite(fileOutputStream);
            }
        }
    }
}
