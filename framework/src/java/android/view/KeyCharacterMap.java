package android.view;

/* loaded from: classes4.dex */
public class KeyCharacterMap implements android.os.Parcelable {
    private static final int ACCENT_ACUTE = 180;
    private static final int ACCENT_APOSTROPHE = 39;
    private static final int ACCENT_BREVE = 728;
    private static final int ACCENT_CARON = 711;
    private static final int ACCENT_CEDILLA = 184;
    private static final int ACCENT_CIRCUMFLEX = 710;
    private static final int ACCENT_CIRCUMFLEX_LEGACY = 94;
    private static final int ACCENT_COMMA_ABOVE = 8125;
    private static final int ACCENT_COMMA_ABOVE_RIGHT = 700;
    private static final int ACCENT_DOT_ABOVE = 729;
    private static final int ACCENT_DOT_BELOW = 46;
    private static final int ACCENT_DOUBLE_ACUTE = 733;
    private static final int ACCENT_GRAVE = 715;
    private static final int ACCENT_GRAVE_LEGACY = 96;
    private static final int ACCENT_HOOK_ABOVE = 704;
    private static final int ACCENT_HORN = 39;
    private static final int ACCENT_MACRON = 175;
    private static final int ACCENT_MACRON_BELOW = 717;
    private static final int ACCENT_OGONEK = 731;
    private static final int ACCENT_QUOTATION_MARK = 34;
    private static final int ACCENT_REVERSED_COMMA_ABOVE = 701;
    private static final int ACCENT_RING_ABOVE = 730;
    private static final int ACCENT_STROKE = 45;
    private static final int ACCENT_TILDE = 732;
    private static final int ACCENT_TILDE_LEGACY = 126;
    private static final int ACCENT_TURNED_COMMA_ABOVE = 699;
    private static final int ACCENT_UMLAUT = 168;
    private static final int ACCENT_VERTICAL_LINE_ABOVE = 712;
    private static final int ACCENT_VERTICAL_LINE_BELOW = 716;
    public static final int ALPHA = 3;

    @java.lang.Deprecated
    public static final int BUILT_IN_KEYBOARD = 0;
    private static final int CHAR_SPACE = 32;
    public static final int COMBINING_ACCENT = Integer.MIN_VALUE;
    public static final int COMBINING_ACCENT_MASK = Integer.MAX_VALUE;
    public static final android.os.Parcelable.Creator<android.view.KeyCharacterMap> CREATOR;
    public static final int FULL = 4;
    public static final char HEX_INPUT = 61184;
    public static final int MODIFIER_BEHAVIOR_CHORDED = 0;
    public static final int MODIFIER_BEHAVIOR_CHORDED_OR_TOGGLED = 1;
    public static final int NUMERIC = 1;
    public static final char PICKER_DIALOG_INPUT = 61185;
    public static final int PREDICTIVE = 2;
    public static final int SPECIAL_FUNCTION = 5;
    public static final int VIRTUAL_KEYBOARD = -1;
    private static final java.lang.StringBuilder sDeadKeyBuilder;
    private static final android.util.SparseIntArray sDeadKeyCache;
    private long mPtr;
    private static final android.util.SparseIntArray sCombiningToAccent = new android.util.SparseIntArray();
    private static final android.util.SparseIntArray sAccentToCombining = new android.util.SparseIntArray();

    @java.lang.Deprecated
    public static class KeyData {
        public static final int META_LENGTH = 4;
        public char displayLabel;
        public char[] meta = new char[4];
        public char number;
    }

    private static native void nativeApplyOverlay(long j, java.lang.String str, java.lang.String str2);

    private static native void nativeDispose(long j);

    private static native boolean nativeEquals(long j, long j2);

    private static native char nativeGetCharacter(long j, int i, int i2);

    private static native char nativeGetDisplayLabel(long j, int i);

    private static native android.view.KeyEvent[] nativeGetEvents(long j, char[] cArr);

    private static native boolean nativeGetFallbackAction(long j, int i, int i2, android.view.KeyCharacterMap.FallbackAction fallbackAction);

    private static native int nativeGetKeyboardType(long j);

    private static native int nativeGetMappedKey(long j, int i);

    private static native char nativeGetMatch(long j, int i, char[] cArr, int i2);

    private static native char nativeGetNumber(long j, int i);

    private static native android.view.KeyCharacterMap nativeObtainEmptyKeyCharacterMap(int i);

    private static native long nativeReadFromParcel(android.os.Parcel parcel);

    private static native void nativeWriteToParcel(long j, android.os.Parcel parcel);

    static {
        addCombining(768, 715);
        addCombining(769, 180);
        addCombining(770, 710);
        addCombining(771, 732);
        addCombining(772, 175);
        addCombining(774, 728);
        addCombining(775, 729);
        addCombining(776, 168);
        addCombining(777, 704);
        addCombining(778, 730);
        addCombining(779, 733);
        addCombining(780, 711);
        addCombining(786, 699);
        addCombining(787, ACCENT_COMMA_ABOVE);
        addCombining(788, 701);
        addCombining(789, 700);
        addCombining(com.android.internal.logging.nano.MetricsProto.MetricsEvent.NOTIFICATION_SINCE_UPDATE_MILLIS, 39);
        addCombining(803, 46);
        addCombining(807, 184);
        addCombining(808, 731);
        addCombining(com.android.internal.logging.nano.MetricsProto.MetricsEvent.PROVISIONING_TERMS_ACTIVITY_TIME_MS, 716);
        addCombining(817, 717);
        addCombining(821, 45);
        sCombiningToAccent.append(com.android.internal.logging.nano.MetricsProto.MetricsEvent.NOTIFICATION_SNOOZED_CRITERIA, 715);
        sCombiningToAccent.append(com.android.internal.logging.nano.MetricsProto.MetricsEvent.FIELD_CONTEXT, 180);
        sCombiningToAccent.append(com.android.internal.logging.nano.MetricsProto.MetricsEvent.WIFI_NETWORK_RECOMMENDATION_SAVED_NETWORK_EVALUATOR, ACCENT_COMMA_ABOVE);
        sCombiningToAccent.append(781, 39);
        sCombiningToAccent.append(782, 34);
        sAccentToCombining.append(96, 768);
        sAccentToCombining.append(94, 770);
        sAccentToCombining.append(126, 771);
        sAccentToCombining.append(39, 769);
        sAccentToCombining.append(34, 776);
        sDeadKeyCache = new android.util.SparseIntArray();
        sDeadKeyBuilder = new java.lang.StringBuilder();
        addDeadKey(45, 68, 272);
        addDeadKey(45, 71, 484);
        addDeadKey(45, 72, 294);
        addDeadKey(45, 73, 407);
        addDeadKey(45, 76, 321);
        addDeadKey(45, 79, 216);
        addDeadKey(45, 84, 358);
        addDeadKey(45, 100, 273);
        addDeadKey(45, 103, 485);
        addDeadKey(45, 104, 295);
        addDeadKey(45, 105, 616);
        addDeadKey(45, 108, 322);
        addDeadKey(45, 111, 248);
        addDeadKey(45, 116, 359);
        CREATOR = new android.os.Parcelable.Creator<android.view.KeyCharacterMap>() { // from class: android.view.KeyCharacterMap.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.KeyCharacterMap createFromParcel(android.os.Parcel parcel) {
                return new android.view.KeyCharacterMap(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.KeyCharacterMap[] newArray(int i) {
                return new android.view.KeyCharacterMap[i];
            }
        };
    }

    private static void addCombining(int i, int i2) {
        sCombiningToAccent.append(i, i2);
        sAccentToCombining.append(i2, i);
    }

    private static void addDeadKey(int i, int i2, int i3) {
        int i4 = sAccentToCombining.get(i);
        if (i4 == 0) {
            throw new java.lang.IllegalStateException("Invalid dead key declaration.");
        }
        sDeadKeyCache.put((i4 << 16) | i2, i3);
    }

    private KeyCharacterMap(android.os.Parcel parcel) {
        if (parcel == null) {
            throw new java.lang.IllegalArgumentException("parcel must not be null");
        }
        this.mPtr = nativeReadFromParcel(parcel);
        if (this.mPtr == 0) {
            throw new java.lang.RuntimeException("Could not read KeyCharacterMap from parcel.");
        }
    }

    private KeyCharacterMap(long j) {
        this.mPtr = j;
    }

    protected void finalize() throws java.lang.Throwable {
        if (this.mPtr != 0) {
            nativeDispose(this.mPtr);
            this.mPtr = 0L;
        }
    }

    public static android.view.KeyCharacterMap obtainEmptyMap(int i) {
        return nativeObtainEmptyKeyCharacterMap(i);
    }

    public static android.view.KeyCharacterMap load(int i) {
        android.hardware.input.InputManagerGlobal inputManagerGlobal = android.hardware.input.InputManagerGlobal.getInstance();
        android.view.InputDevice inputDevice = inputManagerGlobal.getInputDevice(i);
        if (inputDevice == null && (inputDevice = inputManagerGlobal.getInputDevice(-1)) == null) {
            throw new android.view.KeyCharacterMap.UnavailableException("Could not load key character map for device " + i);
        }
        return inputDevice.getKeyCharacterMap();
    }

    public static android.view.KeyCharacterMap load(java.lang.String str, java.lang.String str2) {
        android.view.KeyCharacterMap load = load(-1);
        load.applyOverlay(str, str2);
        return load;
    }

    private void applyOverlay(java.lang.String str, java.lang.String str2) {
        nativeApplyOverlay(this.mPtr, str, str2);
    }

    public int getMappedKeyOrDefault(int i, int i2) {
        int nativeGetMappedKey = nativeGetMappedKey(this.mPtr, i);
        return nativeGetMappedKey == 0 ? i2 : nativeGetMappedKey;
    }

    public int get(int i, int i2) {
        char nativeGetCharacter = nativeGetCharacter(this.mPtr, i, android.view.KeyEvent.normalizeMetaState(i2));
        int i3 = sCombiningToAccent.get(nativeGetCharacter);
        if (i3 != 0) {
            return Integer.MIN_VALUE | i3;
        }
        return nativeGetCharacter;
    }

    public android.view.KeyCharacterMap.FallbackAction getFallbackAction(int i, int i2) {
        android.view.KeyCharacterMap.FallbackAction obtain = android.view.KeyCharacterMap.FallbackAction.obtain();
        if (nativeGetFallbackAction(this.mPtr, i, android.view.KeyEvent.normalizeMetaState(i2), obtain)) {
            obtain.metaState = android.view.KeyEvent.normalizeMetaState(obtain.metaState);
            return obtain;
        }
        obtain.recycle();
        return null;
    }

    public char getNumber(int i) {
        return nativeGetNumber(this.mPtr, i);
    }

    public char getMatch(int i, char[] cArr) {
        return getMatch(i, cArr, 0);
    }

    public char getMatch(int i, char[] cArr, int i2) {
        if (cArr == null) {
            throw new java.lang.IllegalArgumentException("chars must not be null.");
        }
        return nativeGetMatch(this.mPtr, i, cArr, android.view.KeyEvent.normalizeMetaState(i2));
    }

    public char getDisplayLabel(int i) {
        return nativeGetDisplayLabel(this.mPtr, i);
    }

    public static int getDeadChar(int i, int i2) {
        int i3;
        if (i2 == i || 32 == i2) {
            return i;
        }
        int i4 = sAccentToCombining.get(i);
        int i5 = 0;
        if (i4 == 0) {
            return 0;
        }
        int i6 = (i4 << 16) | i2;
        synchronized (sDeadKeyCache) {
            i3 = sDeadKeyCache.get(i6, -1);
            if (i3 == -1) {
                sDeadKeyBuilder.setLength(0);
                sDeadKeyBuilder.append((char) i2);
                sDeadKeyBuilder.append((char) i4);
                java.lang.String normalize = java.text.Normalizer.normalize(sDeadKeyBuilder, java.text.Normalizer.Form.NFC);
                if (normalize.codePointCount(0, normalize.length()) == 1) {
                    i5 = normalize.codePointAt(0);
                }
                sDeadKeyCache.put(i6, i5);
                i3 = i5;
            }
        }
        return i3;
    }

    public static int getCombiningChar(int i) {
        return sAccentToCombining.get(i);
    }

    @java.lang.Deprecated
    public boolean getKeyData(int i, android.view.KeyCharacterMap.KeyData keyData) {
        if (keyData.meta.length < 4) {
            throw new java.lang.IndexOutOfBoundsException("results.meta.length must be >= 4");
        }
        char nativeGetDisplayLabel = nativeGetDisplayLabel(this.mPtr, i);
        if (nativeGetDisplayLabel == 0) {
            return false;
        }
        keyData.displayLabel = nativeGetDisplayLabel;
        keyData.number = nativeGetNumber(this.mPtr, i);
        keyData.meta[0] = nativeGetCharacter(this.mPtr, i, 0);
        keyData.meta[1] = nativeGetCharacter(this.mPtr, i, 1);
        keyData.meta[2] = nativeGetCharacter(this.mPtr, i, 2);
        keyData.meta[3] = nativeGetCharacter(this.mPtr, i, 3);
        return true;
    }

    public android.view.KeyEvent[] getEvents(char[] cArr) {
        if (cArr == null) {
            throw new java.lang.IllegalArgumentException("chars must not be null.");
        }
        return nativeGetEvents(this.mPtr, cArr);
    }

    public boolean isPrintingKey(int i) {
        switch (java.lang.Character.getType(nativeGetDisplayLabel(this.mPtr, i))) {
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
                return false;
            default:
                return true;
        }
    }

    public int getKeyboardType() {
        return nativeGetKeyboardType(this.mPtr);
    }

    public int getModifierBehavior() {
        switch (getKeyboardType()) {
            case 4:
            case 5:
                return 0;
            default:
                return 1;
        }
    }

    public static boolean deviceHasKey(int i) {
        return android.hardware.input.InputManagerGlobal.getInstance().deviceHasKeys(new int[]{i})[0];
    }

    public static boolean[] deviceHasKeys(int[] iArr) {
        return android.hardware.input.InputManagerGlobal.getInstance().deviceHasKeys(iArr);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        if (parcel == null) {
            throw new java.lang.IllegalArgumentException("parcel must not be null");
        }
        nativeWriteToParcel(this.mPtr, parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.view.KeyCharacterMap)) {
            return false;
        }
        android.view.KeyCharacterMap keyCharacterMap = (android.view.KeyCharacterMap) obj;
        if (this.mPtr == 0 || keyCharacterMap.mPtr == 0) {
            return this.mPtr == keyCharacterMap.mPtr;
        }
        return nativeEquals(this.mPtr, keyCharacterMap.mPtr);
    }

    public static class UnavailableException extends android.util.AndroidRuntimeException {
        public UnavailableException(java.lang.String str) {
            super(str);
        }
    }

    public static final class FallbackAction {
        private static final int MAX_RECYCLED = 10;
        private static android.view.KeyCharacterMap.FallbackAction sRecycleBin;
        private static final java.lang.Object sRecycleLock = new java.lang.Object();
        private static int sRecycledCount;
        public int keyCode;
        public int metaState;
        private android.view.KeyCharacterMap.FallbackAction next;

        private FallbackAction() {
        }

        public static android.view.KeyCharacterMap.FallbackAction obtain() {
            android.view.KeyCharacterMap.FallbackAction fallbackAction;
            synchronized (sRecycleLock) {
                if (sRecycleBin == null) {
                    fallbackAction = new android.view.KeyCharacterMap.FallbackAction();
                } else {
                    fallbackAction = sRecycleBin;
                    sRecycleBin = fallbackAction.next;
                    sRecycledCount--;
                    fallbackAction.next = null;
                }
            }
            return fallbackAction;
        }

        public void recycle() {
            synchronized (sRecycleLock) {
                if (sRecycledCount < 10) {
                    this.next = sRecycleBin;
                    sRecycleBin = this;
                    sRecycledCount++;
                } else {
                    this.next = null;
                }
            }
        }
    }
}
