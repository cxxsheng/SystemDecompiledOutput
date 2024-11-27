package com.android.server.tv.tunerresourcemanager;

/* loaded from: classes2.dex */
public class UseCasePriorityHints {
    private static final int INVALID_PRIORITY_VALUE = -1;
    private static final int INVALID_USE_CASE = -1;
    private static final java.lang.String PATH_TO_VENDOR_CONFIG_XML = "/vendor/etc/tunerResourceManagerUseCaseConfig.xml";
    private static final java.lang.String TAG = "UseCasePriorityHints";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);
    private static final java.lang.String NS = null;
    android.util.SparseArray<int[]> mPriorityHints = new android.util.SparseArray<>();
    java.util.Set<java.lang.Integer> mVendorDefinedUseCase = new java.util.HashSet();
    private int mDefaultForeground = 150;
    private int mDefaultBackground = 50;

    int getForegroundPriority(int i) {
        if (this.mPriorityHints.get(i) != null && this.mPriorityHints.get(i).length == 2) {
            return this.mPriorityHints.get(i)[0];
        }
        return this.mDefaultForeground;
    }

    int getBackgroundPriority(int i) {
        if (this.mPriorityHints.get(i) != null && this.mPriorityHints.get(i).length == 2) {
            return this.mPriorityHints.get(i)[1];
        }
        return this.mDefaultBackground;
    }

    boolean isDefinedUseCase(int i) {
        return this.mVendorDefinedUseCase.contains(java.lang.Integer.valueOf(i)) || isPredefinedUseCase(i);
    }

    public void parse() {
        java.io.File file = new java.io.File(PATH_TO_VENDOR_CONFIG_XML);
        if (file.exists()) {
            try {
                parseInternal(new java.io.FileInputStream(file));
                return;
            } catch (java.io.IOException e) {
                android.util.Slog.e(TAG, "Error reading vendor file: " + file, e);
                return;
            } catch (org.xmlpull.v1.XmlPullParserException e2) {
                android.util.Slog.e(TAG, "Unable to parse vendor file: " + file, e2);
                return;
            }
        }
        if (DEBUG) {
            android.util.Slog.i(TAG, "no vendor priority configuration available. Using default priority");
        }
        addNewUseCasePriority(100, 180, 100);
        addNewUseCasePriority(200, 450, 200);
        addNewUseCasePriority(300, com.android.server.SystemService.PHASE_LOCK_SETTINGS_READY, 300);
        addNewUseCasePriority(400, 490, 400);
        addNewUseCasePriority(500, 600, 500);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void parseInternal(java.io.InputStream inputStream) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        try {
            com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(inputStream);
            resolvePullParser.nextTag();
            readUseCase(resolvePullParser);
            inputStream.close();
            for (int i = 0; i < this.mPriorityHints.size(); i++) {
                int keyAt = this.mPriorityHints.keyAt(i);
                int[] iArr = this.mPriorityHints.get(keyAt);
                if (DEBUG) {
                    android.util.Slog.d(TAG, "{defaultFg=" + this.mDefaultForeground + ", defaultBg=" + this.mDefaultBackground + "}");
                    android.util.Slog.d(TAG, "{useCase=" + keyAt + ", fg=" + iArr[0] + ", bg=" + iArr[1] + "}");
                }
            }
        } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
            throw e;
        }
    }

    private void readUseCase(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        typedXmlPullParser.require(2, NS, "config");
        while (typedXmlPullParser.next() != 3) {
            if (typedXmlPullParser.getEventType() == 2) {
                java.lang.String name = typedXmlPullParser.getName();
                if (name.equals("useCaseDefault")) {
                    this.mDefaultForeground = readAttributeToInt("fgPriority", typedXmlPullParser);
                    this.mDefaultBackground = readAttributeToInt("bgPriority", typedXmlPullParser);
                    typedXmlPullParser.nextTag();
                    typedXmlPullParser.require(3, NS, name);
                } else if (name.equals("useCasePreDefined")) {
                    int formatTypeToNum = formatTypeToNum(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE, typedXmlPullParser);
                    if (formatTypeToNum == -1) {
                        android.util.Slog.e(TAG, "Wrong predefined use case name given in the vendor config.");
                    } else {
                        addNewUseCasePriority(formatTypeToNum, readAttributeToInt("fgPriority", typedXmlPullParser), readAttributeToInt("bgPriority", typedXmlPullParser));
                        typedXmlPullParser.nextTag();
                        typedXmlPullParser.require(3, NS, name);
                    }
                } else if (name.equals("useCaseVendor")) {
                    int readAttributeToInt = readAttributeToInt("id", typedXmlPullParser);
                    addNewUseCasePriority(readAttributeToInt, readAttributeToInt("fgPriority", typedXmlPullParser), readAttributeToInt("bgPriority", typedXmlPullParser));
                    this.mVendorDefinedUseCase.add(java.lang.Integer.valueOf(readAttributeToInt));
                    typedXmlPullParser.nextTag();
                    typedXmlPullParser.require(3, NS, name);
                } else {
                    skip(typedXmlPullParser);
                }
            }
        }
    }

    private void skip(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        if (typedXmlPullParser.getEventType() != 2) {
            throw new java.lang.IllegalStateException();
        }
        int i = 1;
        while (i != 0) {
            switch (typedXmlPullParser.next()) {
                case 2:
                    i++;
                    break;
                case 3:
                    i--;
                    break;
            }
        }
    }

    private int readAttributeToInt(java.lang.String str, com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException {
        return typedXmlPullParser.getAttributeInt((java.lang.String) null, str);
    }

    private void addNewUseCasePriority(int i, int i2, int i3) {
        this.mPriorityHints.append(i, new int[]{i2, i3});
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static int formatTypeToNum(java.lang.String str, com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) {
        char c;
        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, str);
        switch (attributeValue.hashCode()) {
            case -884787515:
                if (attributeValue.equals("USE_CASE_BACKGROUND")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 377959794:
                if (attributeValue.equals("USE_CASE_PLAYBACK")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1222007747:
                if (attributeValue.equals("USE_CASE_LIVE")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1222209876:
                if (attributeValue.equals("USE_CASE_SCAN")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1990900072:
                if (attributeValue.equals("USE_CASE_RECORD")) {
                    c = 4;
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
                return 100;
            case 1:
                return 200;
            case 2:
                return 300;
            case 3:
                return 400;
            case 4:
                return 500;
            default:
                return -1;
        }
    }

    private static boolean isPredefinedUseCase(int i) {
        switch (i) {
            case 100:
            case 200:
            case 300:
            case 400:
            case 500:
                return true;
            default:
                return false;
        }
    }
}
