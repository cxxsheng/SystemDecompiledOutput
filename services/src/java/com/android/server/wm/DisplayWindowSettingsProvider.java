package com.android.server.wm;

/* loaded from: classes3.dex */
class DisplayWindowSettingsProvider implements com.android.server.wm.DisplayWindowSettings.SettingsProvider {
    private static final java.lang.String DATA_DISPLAY_SETTINGS_FILE_PATH = "system/display_settings.xml";
    private static final int IDENTIFIER_PORT = 1;
    private static final int IDENTIFIER_UNIQUE_ID = 0;
    private static final java.lang.String TAG = "WindowManager";
    private static final java.lang.String VENDOR_DISPLAY_SETTINGS_FILE_PATH = "etc/display_settings.xml";
    private static final java.lang.String WM_DISPLAY_COMMIT_TAG = "wm-displays";

    @android.annotation.NonNull
    private com.android.server.wm.DisplayWindowSettingsProvider.ReadableSettings mBaseSettings;

    @android.annotation.NonNull
    private final com.android.server.wm.DisplayWindowSettingsProvider.WritableSettings mOverrideSettings;

    @interface DisplayIdentifierType {
    }

    interface ReadableSettingsStorage {
        java.io.InputStream openRead() throws java.io.IOException;
    }

    interface WritableSettingsStorage extends com.android.server.wm.DisplayWindowSettingsProvider.ReadableSettingsStorage {
        void finishWrite(java.io.OutputStream outputStream, boolean z);

        java.io.OutputStream startWrite() throws java.io.IOException;
    }

    DisplayWindowSettingsProvider() {
        this(new com.android.server.wm.DisplayWindowSettingsProvider.AtomicFileStorage(getVendorSettingsFile()), new com.android.server.wm.DisplayWindowSettingsProvider.AtomicFileStorage(getOverrideSettingsFile()));
    }

    @com.android.internal.annotations.VisibleForTesting
    DisplayWindowSettingsProvider(@android.annotation.NonNull com.android.server.wm.DisplayWindowSettingsProvider.ReadableSettingsStorage readableSettingsStorage, @android.annotation.NonNull com.android.server.wm.DisplayWindowSettingsProvider.WritableSettingsStorage writableSettingsStorage) {
        this.mBaseSettings = new com.android.server.wm.DisplayWindowSettingsProvider.ReadableSettings(readableSettingsStorage);
        this.mOverrideSettings = new com.android.server.wm.DisplayWindowSettingsProvider.WritableSettings(writableSettingsStorage);
    }

    void setBaseSettingsFilePath(@android.annotation.Nullable java.lang.String str) {
        android.util.AtomicFile vendorSettingsFile;
        java.io.File file = str != null ? new java.io.File(str) : null;
        if (file != null && file.exists()) {
            vendorSettingsFile = new android.util.AtomicFile(file, WM_DISPLAY_COMMIT_TAG);
        } else {
            android.util.Slog.w(TAG, "display settings " + str + " does not exist, using vendor defaults");
            vendorSettingsFile = getVendorSettingsFile();
        }
        setBaseSettingsStorage(new com.android.server.wm.DisplayWindowSettingsProvider.AtomicFileStorage(vendorSettingsFile));
    }

    @com.android.internal.annotations.VisibleForTesting
    void setBaseSettingsStorage(@android.annotation.NonNull com.android.server.wm.DisplayWindowSettingsProvider.ReadableSettingsStorage readableSettingsStorage) {
        this.mBaseSettings = new com.android.server.wm.DisplayWindowSettingsProvider.ReadableSettings(readableSettingsStorage);
    }

    @Override // com.android.server.wm.DisplayWindowSettings.SettingsProvider
    @android.annotation.NonNull
    public com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry getSettings(@android.annotation.NonNull android.view.DisplayInfo displayInfo) {
        com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry settingsEntry = this.mBaseSettings.getSettingsEntry(displayInfo);
        com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry orCreateSettingsEntry = this.mOverrideSettings.getOrCreateSettingsEntry(displayInfo);
        if (settingsEntry == null) {
            return new com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry(orCreateSettingsEntry);
        }
        com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry settingsEntry2 = new com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry(settingsEntry);
        settingsEntry2.updateFrom(orCreateSettingsEntry);
        return settingsEntry2;
    }

    @Override // com.android.server.wm.DisplayWindowSettings.SettingsProvider
    @android.annotation.NonNull
    public com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry getOverrideSettings(@android.annotation.NonNull android.view.DisplayInfo displayInfo) {
        return new com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry(this.mOverrideSettings.getOrCreateSettingsEntry(displayInfo));
    }

    @Override // com.android.server.wm.DisplayWindowSettings.SettingsProvider
    public void updateOverrideSettings(@android.annotation.NonNull android.view.DisplayInfo displayInfo, @android.annotation.NonNull com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry settingsEntry) {
        this.mOverrideSettings.updateSettingsEntry(displayInfo, settingsEntry);
    }

    @Override // com.android.server.wm.DisplayWindowSettings.SettingsProvider
    public void onDisplayRemoved(@android.annotation.NonNull android.view.DisplayInfo displayInfo) {
        this.mOverrideSettings.onDisplayRemoved(displayInfo);
    }

    @Override // com.android.server.wm.DisplayWindowSettings.SettingsProvider
    public void clearDisplaySettings(@android.annotation.NonNull android.view.DisplayInfo displayInfo) {
        this.mOverrideSettings.clearDisplaySettings(displayInfo);
    }

    @com.android.internal.annotations.VisibleForTesting
    int getOverrideSettingsSize() {
        return this.mOverrideSettings.mSettings.size();
    }

    private static class ReadableSettings {

        @com.android.server.wm.DisplayWindowSettingsProvider.DisplayIdentifierType
        protected int mIdentifierType;

        @android.annotation.NonNull
        protected final android.util.ArrayMap<java.lang.String, com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry> mSettings = new android.util.ArrayMap<>();

        ReadableSettings(@android.annotation.NonNull com.android.server.wm.DisplayWindowSettingsProvider.ReadableSettingsStorage readableSettingsStorage) {
            loadSettings(readableSettingsStorage);
        }

        @android.annotation.Nullable
        final com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry getSettingsEntry(@android.annotation.NonNull android.view.DisplayInfo displayInfo) {
            java.lang.String identifier = getIdentifier(displayInfo);
            com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry settingsEntry = this.mSettings.get(identifier);
            if (settingsEntry != null) {
                return settingsEntry;
            }
            com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry settingsEntry2 = this.mSettings.get(displayInfo.name);
            if (settingsEntry2 != null) {
                this.mSettings.remove(displayInfo.name);
                this.mSettings.put(identifier, settingsEntry2);
                return settingsEntry2;
            }
            return null;
        }

        @android.annotation.NonNull
        protected final java.lang.String getIdentifier(@android.annotation.NonNull android.view.DisplayInfo displayInfo) {
            if (this.mIdentifierType == 1 && displayInfo.address != null && (displayInfo.address instanceof android.view.DisplayAddress.Physical)) {
                return "port:" + displayInfo.address.getPort();
            }
            return displayInfo.uniqueId;
        }

        private void loadSettings(@android.annotation.NonNull com.android.server.wm.DisplayWindowSettingsProvider.ReadableSettingsStorage readableSettingsStorage) {
            com.android.server.wm.DisplayWindowSettingsProvider.FileData readSettings = com.android.server.wm.DisplayWindowSettingsProvider.readSettings(readableSettingsStorage);
            if (readSettings != null) {
                this.mIdentifierType = readSettings.mIdentifierType;
                this.mSettings.putAll(readSettings.mSettings);
            }
        }
    }

    private static final class WritableSettings extends com.android.server.wm.DisplayWindowSettingsProvider.ReadableSettings {

        @android.annotation.NonNull
        private final com.android.server.wm.DisplayWindowSettingsProvider.WritableSettingsStorage mSettingsStorage;

        @android.annotation.NonNull
        private final android.util.ArraySet<java.lang.String> mVirtualDisplayIdentifiers;

        WritableSettings(@android.annotation.NonNull com.android.server.wm.DisplayWindowSettingsProvider.WritableSettingsStorage writableSettingsStorage) {
            super(writableSettingsStorage);
            this.mVirtualDisplayIdentifiers = new android.util.ArraySet<>();
            this.mSettingsStorage = writableSettingsStorage;
        }

        @android.annotation.NonNull
        com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry getOrCreateSettingsEntry(@android.annotation.NonNull android.view.DisplayInfo displayInfo) {
            java.lang.String identifier = getIdentifier(displayInfo);
            com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry settingsEntry = this.mSettings.get(identifier);
            if (settingsEntry != null) {
                return settingsEntry;
            }
            com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry settingsEntry2 = this.mSettings.get(displayInfo.name);
            if (settingsEntry2 != null) {
                this.mSettings.remove(displayInfo.name);
                this.mSettings.put(identifier, settingsEntry2);
                writeSettings();
                return settingsEntry2;
            }
            com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry settingsEntry3 = new com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry();
            this.mSettings.put(identifier, settingsEntry3);
            if (displayInfo.type == 5) {
                this.mVirtualDisplayIdentifiers.add(identifier);
            }
            return settingsEntry3;
        }

        void updateSettingsEntry(@android.annotation.NonNull android.view.DisplayInfo displayInfo, @android.annotation.NonNull com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry settingsEntry) {
            if (getOrCreateSettingsEntry(displayInfo).setTo(settingsEntry) && displayInfo.type != 5) {
                writeSettings();
            }
        }

        void onDisplayRemoved(@android.annotation.NonNull android.view.DisplayInfo displayInfo) {
            java.lang.String identifier = getIdentifier(displayInfo);
            if (!this.mSettings.containsKey(identifier)) {
                return;
            }
            if (this.mVirtualDisplayIdentifiers.remove(identifier) || this.mSettings.get(identifier).isEmpty()) {
                this.mSettings.remove(identifier);
            }
        }

        void clearDisplaySettings(@android.annotation.NonNull android.view.DisplayInfo displayInfo) {
            java.lang.String identifier = getIdentifier(displayInfo);
            this.mSettings.remove(identifier);
            this.mVirtualDisplayIdentifiers.remove(identifier);
        }

        private void writeSettings() {
            com.android.server.wm.DisplayWindowSettingsProvider.FileData fileData = new com.android.server.wm.DisplayWindowSettingsProvider.FileData();
            fileData.mIdentifierType = this.mIdentifierType;
            int size = this.mSettings.size();
            for (int i = 0; i < size; i++) {
                java.lang.String keyAt = this.mSettings.keyAt(i);
                if (!this.mVirtualDisplayIdentifiers.contains(keyAt)) {
                    fileData.mSettings.put(keyAt, this.mSettings.get(keyAt));
                }
            }
            com.android.server.wm.DisplayWindowSettingsProvider.writeSettings(this.mSettingsStorage, fileData);
        }
    }

    @android.annotation.NonNull
    private static android.util.AtomicFile getVendorSettingsFile() {
        java.io.File file = new java.io.File(android.os.Environment.getProductDirectory(), VENDOR_DISPLAY_SETTINGS_FILE_PATH);
        if (!file.exists()) {
            file = new java.io.File(android.os.Environment.getVendorDirectory(), VENDOR_DISPLAY_SETTINGS_FILE_PATH);
        }
        return new android.util.AtomicFile(file, WM_DISPLAY_COMMIT_TAG);
    }

    @android.annotation.NonNull
    private static android.util.AtomicFile getOverrideSettingsFile() {
        return new android.util.AtomicFile(new java.io.File(android.os.Environment.getDataDirectory(), DATA_DISPLAY_SETTINGS_FILE_PATH), WM_DISPLAY_COMMIT_TAG);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x004f, code lost:
    
        if (r5 != 4) goto L101;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0052, code lost:
    
        r5 = r2.getName();
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x005c, code lost:
    
        if (r5.equals("display") == false) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0068, code lost:
    
        if (r5.equals("config") == false) goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x006e, code lost:
    
        android.util.Slog.w(com.android.server.wm.DisplayWindowSettingsProvider.TAG, "Unknown element under <display-settings>: " + r2.getName());
        com.android.internal.util.XmlUtils.skipCurrentTag(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x006a, code lost:
    
        readConfig(r2, r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x005e, code lost:
    
        readDisplay(r2, r3);
     */
    /* JADX WARN: Removed duplicated region for block: B:49:0x012c  */
    @android.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static com.android.server.wm.DisplayWindowSettingsProvider.FileData readSettings(@android.annotation.NonNull com.android.server.wm.DisplayWindowSettingsProvider.ReadableSettingsStorage readableSettingsStorage) {
        boolean z;
        com.android.modules.utils.TypedXmlPullParser resolvePullParser;
        int next;
        try {
            java.io.InputStream openRead = readableSettingsStorage.openRead();
            com.android.server.wm.DisplayWindowSettingsProvider.FileData fileData = new com.android.server.wm.DisplayWindowSettingsProvider.FileData();
            try {
                try {
                    try {
                        try {
                            try {
                                try {
                                    resolvePullParser = android.util.Xml.resolvePullParser(openRead);
                                    do {
                                        next = resolvePullParser.next();
                                        z = true;
                                        if (next == 2) {
                                            break;
                                        }
                                    } while (next != 1);
                                } catch (java.lang.IllegalStateException e) {
                                    android.util.Slog.w(TAG, "Failed parsing " + e);
                                    openRead.close();
                                    z = false;
                                    if (!z) {
                                    }
                                    return fileData;
                                } catch (org.xmlpull.v1.XmlPullParserException e2) {
                                    android.util.Slog.w(TAG, "Failed parsing " + e2);
                                    openRead.close();
                                    z = false;
                                    if (!z) {
                                    }
                                    return fileData;
                                }
                            } catch (java.lang.IndexOutOfBoundsException e3) {
                                android.util.Slog.w(TAG, "Failed parsing " + e3);
                                openRead.close();
                                z = false;
                                if (!z) {
                                }
                                return fileData;
                            } catch (java.lang.NumberFormatException e4) {
                                android.util.Slog.w(TAG, "Failed parsing " + e4);
                                openRead.close();
                                z = false;
                                if (!z) {
                                }
                                return fileData;
                            }
                        } catch (java.io.IOException e5) {
                            android.util.Slog.w(TAG, "Failed parsing " + e5);
                            openRead.close();
                            z = false;
                            if (!z) {
                            }
                            return fileData;
                        }
                    } catch (java.lang.NullPointerException e6) {
                        android.util.Slog.w(TAG, "Failed parsing " + e6);
                        openRead.close();
                        z = false;
                        if (!z) {
                        }
                        return fileData;
                    }
                } catch (java.io.IOException e7) {
                    z = false;
                    if (!z) {
                    }
                    return fileData;
                }
                if (next != 2) {
                    throw new java.lang.IllegalStateException("no start tag found");
                }
                int depth = resolvePullParser.getDepth();
                while (true) {
                    int next2 = resolvePullParser.next();
                    if (next2 == 1 || (next2 == 3 && resolvePullParser.getDepth() <= depth)) {
                        try {
                            openRead.close();
                            break;
                        } catch (java.io.IOException e8) {
                        }
                    }
                }
                if (!z) {
                    fileData.mSettings.clear();
                }
                return fileData;
            } catch (java.lang.Throwable th) {
                try {
                    openRead.close();
                } catch (java.io.IOException e9) {
                }
                throw th;
            }
        } catch (java.io.IOException e10) {
            android.util.Slog.i(TAG, "No existing display settings, starting empty");
            return null;
        }
    }

    private static int getIntAttribute(@android.annotation.NonNull com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, @android.annotation.NonNull java.lang.String str, int i) {
        return typedXmlPullParser.getAttributeInt((java.lang.String) null, str, i);
    }

    @android.annotation.Nullable
    private static java.lang.Integer getIntegerAttribute(@android.annotation.NonNull com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.Integer num) {
        try {
            return java.lang.Integer.valueOf(typedXmlPullParser.getAttributeInt((java.lang.String) null, str));
        } catch (java.lang.Exception e) {
            return num;
        }
    }

    @android.annotation.Nullable
    private static java.lang.Boolean getBooleanAttribute(@android.annotation.NonNull com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.Boolean bool) {
        try {
            return java.lang.Boolean.valueOf(typedXmlPullParser.getAttributeBoolean((java.lang.String) null, str));
        } catch (java.lang.Exception e) {
            return bool;
        }
    }

    private static void readDisplay(@android.annotation.NonNull com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, @android.annotation.NonNull com.android.server.wm.DisplayWindowSettingsProvider.FileData fileData) throws java.lang.NumberFormatException, org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, "name");
        if (attributeValue != null) {
            com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry settingsEntry = new com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry();
            settingsEntry.mWindowingMode = getIntAttribute(typedXmlPullParser, "windowingMode", 0);
            settingsEntry.mUserRotationMode = getIntegerAttribute(typedXmlPullParser, "userRotationMode", null);
            settingsEntry.mUserRotation = getIntegerAttribute(typedXmlPullParser, "userRotation", null);
            settingsEntry.mForcedWidth = getIntAttribute(typedXmlPullParser, "forcedWidth", 0);
            settingsEntry.mForcedHeight = getIntAttribute(typedXmlPullParser, "forcedHeight", 0);
            settingsEntry.mForcedDensity = getIntAttribute(typedXmlPullParser, "forcedDensity", 0);
            settingsEntry.mForcedScalingMode = getIntegerAttribute(typedXmlPullParser, "forcedScalingMode", null);
            settingsEntry.mRemoveContentMode = getIntAttribute(typedXmlPullParser, "removeContentMode", 0);
            settingsEntry.mShouldShowWithInsecureKeyguard = getBooleanAttribute(typedXmlPullParser, "shouldShowWithInsecureKeyguard", null);
            settingsEntry.mShouldShowSystemDecors = getBooleanAttribute(typedXmlPullParser, "shouldShowSystemDecors", null);
            java.lang.Boolean booleanAttribute = getBooleanAttribute(typedXmlPullParser, "shouldShowIme", null);
            if (booleanAttribute != null) {
                settingsEntry.mImePolicy = java.lang.Integer.valueOf(booleanAttribute.booleanValue() ? 0 : 1);
            } else {
                settingsEntry.mImePolicy = getIntegerAttribute(typedXmlPullParser, "imePolicy", null);
            }
            settingsEntry.mFixedToUserRotation = getIntegerAttribute(typedXmlPullParser, "fixedToUserRotation", null);
            settingsEntry.mIgnoreOrientationRequest = getBooleanAttribute(typedXmlPullParser, "ignoreOrientationRequest", null);
            settingsEntry.mIgnoreDisplayCutout = getBooleanAttribute(typedXmlPullParser, "ignoreDisplayCutout", null);
            settingsEntry.mDontMoveToTop = getBooleanAttribute(typedXmlPullParser, "dontMoveToTop", null);
            fileData.mSettings.put(attributeValue, settingsEntry);
        }
        com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
    }

    private static void readConfig(@android.annotation.NonNull com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, @android.annotation.NonNull com.android.server.wm.DisplayWindowSettingsProvider.FileData fileData) throws java.lang.NumberFormatException, org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        fileData.mIdentifierType = getIntAttribute(typedXmlPullParser, "identifier", 0);
        com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void writeSettings(@android.annotation.NonNull com.android.server.wm.DisplayWindowSettingsProvider.WritableSettingsStorage writableSettingsStorage, @android.annotation.NonNull com.android.server.wm.DisplayWindowSettingsProvider.FileData fileData) {
        try {
            java.io.OutputStream startWrite = writableSettingsStorage.startWrite();
            try {
                try {
                    com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(startWrite);
                    resolveSerializer.startDocument((java.lang.String) null, true);
                    resolveSerializer.startTag((java.lang.String) null, "display-settings");
                    resolveSerializer.startTag((java.lang.String) null, "config");
                    resolveSerializer.attributeInt((java.lang.String) null, "identifier", fileData.mIdentifierType);
                    resolveSerializer.endTag((java.lang.String) null, "config");
                    for (java.util.Map.Entry<java.lang.String, com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry> entry : fileData.mSettings.entrySet()) {
                        java.lang.String key = entry.getKey();
                        com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry value = entry.getValue();
                        if (!value.isEmpty()) {
                            resolveSerializer.startTag((java.lang.String) null, "display");
                            resolveSerializer.attribute((java.lang.String) null, "name", key);
                            if (value.mWindowingMode != 0) {
                                resolveSerializer.attributeInt((java.lang.String) null, "windowingMode", value.mWindowingMode);
                            }
                            if (value.mUserRotationMode != null) {
                                resolveSerializer.attributeInt((java.lang.String) null, "userRotationMode", value.mUserRotationMode.intValue());
                            }
                            if (value.mUserRotation != null) {
                                resolveSerializer.attributeInt((java.lang.String) null, "userRotation", value.mUserRotation.intValue());
                            }
                            if (value.mForcedWidth != 0 && value.mForcedHeight != 0) {
                                resolveSerializer.attributeInt((java.lang.String) null, "forcedWidth", value.mForcedWidth);
                                resolveSerializer.attributeInt((java.lang.String) null, "forcedHeight", value.mForcedHeight);
                            }
                            if (value.mForcedDensity != 0) {
                                resolveSerializer.attributeInt((java.lang.String) null, "forcedDensity", value.mForcedDensity);
                            }
                            if (value.mForcedScalingMode != null) {
                                resolveSerializer.attributeInt((java.lang.String) null, "forcedScalingMode", value.mForcedScalingMode.intValue());
                            }
                            if (value.mRemoveContentMode != 0) {
                                resolveSerializer.attributeInt((java.lang.String) null, "removeContentMode", value.mRemoveContentMode);
                            }
                            if (value.mShouldShowWithInsecureKeyguard != null) {
                                resolveSerializer.attributeBoolean((java.lang.String) null, "shouldShowWithInsecureKeyguard", value.mShouldShowWithInsecureKeyguard.booleanValue());
                            }
                            if (value.mShouldShowSystemDecors != null) {
                                resolveSerializer.attributeBoolean((java.lang.String) null, "shouldShowSystemDecors", value.mShouldShowSystemDecors.booleanValue());
                            }
                            if (value.mImePolicy != null) {
                                resolveSerializer.attributeInt((java.lang.String) null, "imePolicy", value.mImePolicy.intValue());
                            }
                            if (value.mFixedToUserRotation != null) {
                                resolveSerializer.attributeInt((java.lang.String) null, "fixedToUserRotation", value.mFixedToUserRotation.intValue());
                            }
                            if (value.mIgnoreOrientationRequest != null) {
                                resolveSerializer.attributeBoolean((java.lang.String) null, "ignoreOrientationRequest", value.mIgnoreOrientationRequest.booleanValue());
                            }
                            if (value.mIgnoreDisplayCutout != null) {
                                resolveSerializer.attributeBoolean((java.lang.String) null, "ignoreDisplayCutout", value.mIgnoreDisplayCutout.booleanValue());
                            }
                            if (value.mDontMoveToTop != null) {
                                resolveSerializer.attributeBoolean((java.lang.String) null, "dontMoveToTop", value.mDontMoveToTop.booleanValue());
                            }
                            resolveSerializer.endTag((java.lang.String) null, "display");
                        }
                    }
                    resolveSerializer.endTag((java.lang.String) null, "display-settings");
                    resolveSerializer.endDocument();
                    writableSettingsStorage.finishWrite(startWrite, true);
                } catch (java.io.IOException e) {
                    android.util.Slog.w(TAG, "Failed to write display window settings.", e);
                    writableSettingsStorage.finishWrite(startWrite, false);
                }
            } catch (java.lang.Throwable th) {
                writableSettingsStorage.finishWrite(startWrite, false);
                throw th;
            }
        } catch (java.io.IOException e2) {
            android.util.Slog.w(TAG, "Failed to write display settings: " + e2);
        }
    }

    private static final class FileData {
        int mIdentifierType;

        @android.annotation.NonNull
        final java.util.Map<java.lang.String, com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry> mSettings;

        private FileData() {
            this.mSettings = new android.util.ArrayMap();
        }

        public java.lang.String toString() {
            return "FileData{mIdentifierType=" + this.mIdentifierType + ", mSettings=" + this.mSettings + '}';
        }
    }

    private static final class AtomicFileStorage implements com.android.server.wm.DisplayWindowSettingsProvider.WritableSettingsStorage {

        @android.annotation.NonNull
        private final android.util.AtomicFile mAtomicFile;

        AtomicFileStorage(@android.annotation.NonNull android.util.AtomicFile atomicFile) {
            this.mAtomicFile = atomicFile;
        }

        @Override // com.android.server.wm.DisplayWindowSettingsProvider.ReadableSettingsStorage
        public java.io.InputStream openRead() throws java.io.FileNotFoundException {
            return this.mAtomicFile.openRead();
        }

        @Override // com.android.server.wm.DisplayWindowSettingsProvider.WritableSettingsStorage
        public java.io.OutputStream startWrite() throws java.io.IOException {
            return this.mAtomicFile.startWrite();
        }

        @Override // com.android.server.wm.DisplayWindowSettingsProvider.WritableSettingsStorage
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
