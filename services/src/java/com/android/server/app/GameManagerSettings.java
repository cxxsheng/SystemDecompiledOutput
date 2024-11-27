package com.android.server.app;

/* loaded from: classes.dex */
public class GameManagerSettings {
    private static final java.lang.String ATTR_FPS = "fps";
    private static final java.lang.String ATTR_GAME_MODE = "gameMode";
    private static final java.lang.String ATTR_LOADING_BOOST_DURATION = "loadingBoost";
    private static final java.lang.String ATTR_NAME = "name";
    private static final java.lang.String ATTR_SCALING = "scaling";
    private static final java.lang.String ATTR_USE_ANGLE = "useAngle";
    private static final java.lang.String GAME_SERVICE_FILE_NAME = "game-manager-service.xml";
    public static final java.lang.String TAG = "GameManagerService_GameManagerSettings";
    private static final java.lang.String TAG_GAME_MODE_CONFIG = "gameModeConfig";
    private static final java.lang.String TAG_PACKAGE = "package";
    private static final java.lang.String TAG_PACKAGES = "packages";

    @com.android.internal.annotations.VisibleForTesting
    final android.util.AtomicFile mSettingsFile;
    private final java.io.File mSystemDir;
    private final android.util.ArrayMap<java.lang.String, java.lang.Integer> mGameModes = new android.util.ArrayMap<>();
    private final android.util.ArrayMap<java.lang.String, com.android.server.app.GameManagerService.GamePackageConfiguration> mConfigOverrides = new android.util.ArrayMap<>();

    GameManagerSettings(java.io.File file) {
        this.mSystemDir = new java.io.File(file, "system");
        this.mSystemDir.mkdirs();
        android.os.FileUtils.setPermissions(this.mSystemDir.toString(), 509, -1, -1);
        this.mSettingsFile = new android.util.AtomicFile(new java.io.File(this.mSystemDir, GAME_SERVICE_FILE_NAME));
    }

    int getGameModeLocked(java.lang.String str) {
        int intValue;
        if (!this.mGameModes.containsKey(str) || (intValue = this.mGameModes.get(str).intValue()) == 0) {
            return 1;
        }
        return intValue;
    }

    void setGameModeLocked(java.lang.String str, int i) {
        this.mGameModes.put(str, java.lang.Integer.valueOf(i));
    }

    void removeGame(java.lang.String str) {
        this.mGameModes.remove(str);
        this.mConfigOverrides.remove(str);
    }

    com.android.server.app.GameManagerService.GamePackageConfiguration getConfigOverride(java.lang.String str) {
        return this.mConfigOverrides.get(str);
    }

    void setConfigOverride(java.lang.String str, com.android.server.app.GameManagerService.GamePackageConfiguration gamePackageConfiguration) {
        this.mConfigOverrides.put(str, gamePackageConfiguration);
    }

    void removeConfigOverride(java.lang.String str) {
        this.mConfigOverrides.remove(str);
    }

    void writePersistentDataLocked() {
        java.io.FileOutputStream startWrite;
        java.io.FileOutputStream fileOutputStream = null;
        try {
            startWrite = this.mSettingsFile.startWrite();
        } catch (java.io.IOException e) {
            e = e;
        }
        try {
            com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(startWrite);
            resolveSerializer.startDocument((java.lang.String) null, true);
            resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            resolveSerializer.startTag((java.lang.String) null, TAG_PACKAGES);
            android.util.ArraySet arraySet = new android.util.ArraySet(this.mGameModes.keySet());
            arraySet.addAll(this.mConfigOverrides.keySet());
            java.util.Iterator it = arraySet.iterator();
            while (it.hasNext()) {
                java.lang.String str = (java.lang.String) it.next();
                resolveSerializer.startTag((java.lang.String) null, "package");
                resolveSerializer.attribute((java.lang.String) null, "name", str);
                if (this.mGameModes.containsKey(str)) {
                    resolveSerializer.attributeInt((java.lang.String) null, ATTR_GAME_MODE, this.mGameModes.get(str).intValue());
                }
                writeGameModeConfigTags(resolveSerializer, this.mConfigOverrides.get(str));
                resolveSerializer.endTag((java.lang.String) null, "package");
            }
            resolveSerializer.endTag((java.lang.String) null, TAG_PACKAGES);
            resolveSerializer.endDocument();
            this.mSettingsFile.finishWrite(startWrite);
            android.os.FileUtils.setPermissions(this.mSettingsFile.toString(), com.android.internal.util.FrameworkStatsLog.HOTWORD_DETECTION_SERVICE_RESTARTED, -1, -1);
        } catch (java.io.IOException e2) {
            e = e2;
            fileOutputStream = startWrite;
            this.mSettingsFile.failWrite(fileOutputStream);
            android.util.Slog.wtf(TAG, "Unable to write game manager service settings, current changes will be lost at reboot", e);
        }
    }

    private void writeGameModeConfigTags(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, com.android.server.app.GameManagerService.GamePackageConfiguration gamePackageConfiguration) throws java.io.IOException {
        if (gamePackageConfiguration == null) {
            return;
        }
        for (int i : gamePackageConfiguration.getAvailableGameModes()) {
            com.android.server.app.GameManagerService.GamePackageConfiguration.GameModeConfiguration gameModeConfiguration = gamePackageConfiguration.getGameModeConfiguration(i);
            if (gameModeConfiguration != null) {
                typedXmlSerializer.startTag((java.lang.String) null, TAG_GAME_MODE_CONFIG);
                typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_GAME_MODE, i);
                typedXmlSerializer.attributeBoolean((java.lang.String) null, "useAngle", gameModeConfiguration.getUseAngle());
                typedXmlSerializer.attribute((java.lang.String) null, "fps", gameModeConfiguration.getFpsStr());
                typedXmlSerializer.attributeFloat((java.lang.String) null, ATTR_SCALING, gameModeConfiguration.getScaling());
                typedXmlSerializer.attributeInt((java.lang.String) null, "loadingBoost", gameModeConfiguration.getLoadingBoostDuration());
                typedXmlSerializer.endTag((java.lang.String) null, TAG_GAME_MODE_CONFIG);
            }
        }
    }

    boolean readPersistentDataLocked() {
        int next;
        this.mGameModes.clear();
        if (!this.mSettingsFile.exists()) {
            android.util.Slog.v(TAG, "Settings file doesn't exist, skip reading");
            return false;
        }
        try {
            java.io.FileInputStream openRead = this.mSettingsFile.openRead();
            com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(openRead);
            do {
                next = resolvePullParser.next();
                if (next == 2) {
                    break;
                }
            } while (next != 1);
            if (next != 2) {
                android.util.Slog.wtf(TAG, "No start tag found in game manager settings");
                return false;
            }
            int depth = resolvePullParser.getDepth();
            while (true) {
                int next2 = resolvePullParser.next();
                if (next2 == 1 || (next2 == 3 && resolvePullParser.getDepth() <= depth)) {
                    break;
                }
                if (next2 != 3 && next2 != 4) {
                    java.lang.String name = resolvePullParser.getName();
                    if (next2 == 2 && "package".equals(name)) {
                        readPackage(resolvePullParser);
                    } else {
                        com.android.internal.util.XmlUtils.skipCurrentTag(resolvePullParser);
                        android.util.Slog.w(TAG, "Unknown element under packages tag: " + name + " with type: " + next2);
                    }
                }
            }
            openRead.close();
            return true;
        } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
            android.util.Slog.wtf(TAG, "Error reading game manager settings", e);
            return false;
        }
    }

    private void readPackage(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, "name");
        if (attributeValue == null) {
            android.util.Slog.wtf(TAG, "No package name found in package tag");
            com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
            return;
        }
        try {
            this.mGameModes.put(attributeValue, java.lang.Integer.valueOf(typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_GAME_MODE)));
        } catch (org.xmlpull.v1.XmlPullParserException e) {
            android.util.Slog.v(TAG, "No game mode selected by user for package" + attributeValue);
        }
        int depth = typedXmlPullParser.getDepth();
        com.android.server.app.GameManagerService.GamePackageConfiguration gamePackageConfiguration = new com.android.server.app.GameManagerService.GamePackageConfiguration(attributeValue);
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4) {
                java.lang.String name = typedXmlPullParser.getName();
                if (next == 2 && TAG_GAME_MODE_CONFIG.equals(name)) {
                    readGameModeConfig(typedXmlPullParser, gamePackageConfiguration);
                } else {
                    com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                    android.util.Slog.w(TAG, "Unknown element under package tag: " + name + " with type: " + next);
                }
            }
        }
        if (gamePackageConfiguration.hasActiveGameModeConfig()) {
            this.mConfigOverrides.put(attributeValue, gamePackageConfiguration);
        }
    }

    private void readGameModeConfig(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, com.android.server.app.GameManagerService.GamePackageConfiguration gamePackageConfiguration) {
        try {
            com.android.server.app.GameManagerService.GamePackageConfiguration.GameModeConfiguration orAddDefaultGameModeConfiguration = gamePackageConfiguration.getOrAddDefaultGameModeConfiguration(typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_GAME_MODE));
            try {
                orAddDefaultGameModeConfiguration.setScaling(typedXmlPullParser.getAttributeFloat((java.lang.String) null, ATTR_SCALING));
            } catch (org.xmlpull.v1.XmlPullParserException e) {
                java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_SCALING);
                if (attributeValue != null) {
                    android.util.Slog.wtf(TAG, "Invalid scaling value in config tag: " + attributeValue, e);
                }
            }
            java.lang.String attributeValue2 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "fps");
            if (attributeValue2 == null) {
                attributeValue2 = "";
            }
            orAddDefaultGameModeConfiguration.setFpsStr(attributeValue2);
            try {
                orAddDefaultGameModeConfiguration.setUseAngle(typedXmlPullParser.getAttributeBoolean((java.lang.String) null, "useAngle"));
            } catch (org.xmlpull.v1.XmlPullParserException e2) {
                java.lang.String attributeValue3 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "useAngle");
                if (attributeValue3 != null) {
                    android.util.Slog.wtf(TAG, "Invalid useAngle value in config tag: " + attributeValue3, e2);
                }
            }
            try {
                orAddDefaultGameModeConfiguration.setLoadingBoostDuration(typedXmlPullParser.getAttributeInt((java.lang.String) null, "loadingBoost"));
            } catch (org.xmlpull.v1.XmlPullParserException e3) {
                java.lang.String attributeValue4 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "loadingBoost");
                if (attributeValue4 != null) {
                    android.util.Slog.wtf(TAG, "Invalid loading boost in config tag: " + attributeValue4, e3);
                }
            }
        } catch (org.xmlpull.v1.XmlPullParserException e4) {
            android.util.Slog.wtf(TAG, "Invalid game mode value in config tag: " + typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_GAME_MODE), e4);
        }
    }
}
