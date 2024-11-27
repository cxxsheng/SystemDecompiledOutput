package android.hardware.radio;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class RadioMetadata implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.RadioMetadata> CREATOR;
    private static final android.util.ArrayMap<java.lang.String, java.lang.Integer> METADATA_KEYS_TYPE = new android.util.ArrayMap<>();
    public static final java.lang.String METADATA_KEY_ALBUM = "android.hardware.radio.metadata.ALBUM";
    public static final java.lang.String METADATA_KEY_ART = "android.hardware.radio.metadata.ART";
    public static final java.lang.String METADATA_KEY_ARTIST = "android.hardware.radio.metadata.ARTIST";
    public static final java.lang.String METADATA_KEY_CLOCK = "android.hardware.radio.metadata.CLOCK";
    public static final java.lang.String METADATA_KEY_COMMENT_ACTUAL_TEXT = "android.hardware.radio.metadata.COMMENT_ACTUAL_TEXT";
    public static final java.lang.String METADATA_KEY_COMMENT_SHORT_DESCRIPTION = "android.hardware.radio.metadata.COMMENT_SHORT_DESCRIPTION";
    public static final java.lang.String METADATA_KEY_COMMERCIAL = "android.hardware.radio.metadata.COMMERCIAL";
    public static final java.lang.String METADATA_KEY_DAB_COMPONENT_NAME = "android.hardware.radio.metadata.DAB_COMPONENT_NAME";
    public static final java.lang.String METADATA_KEY_DAB_COMPONENT_NAME_SHORT = "android.hardware.radio.metadata.DAB_COMPONENT_NAME_SHORT";
    public static final java.lang.String METADATA_KEY_DAB_ENSEMBLE_NAME = "android.hardware.radio.metadata.DAB_ENSEMBLE_NAME";
    public static final java.lang.String METADATA_KEY_DAB_ENSEMBLE_NAME_SHORT = "android.hardware.radio.metadata.DAB_ENSEMBLE_NAME_SHORT";
    public static final java.lang.String METADATA_KEY_DAB_SERVICE_NAME = "android.hardware.radio.metadata.DAB_SERVICE_NAME";
    public static final java.lang.String METADATA_KEY_DAB_SERVICE_NAME_SHORT = "android.hardware.radio.metadata.DAB_SERVICE_NAME_SHORT";
    public static final java.lang.String METADATA_KEY_GENRE = "android.hardware.radio.metadata.GENRE";
    public static final java.lang.String METADATA_KEY_HD_STATION_NAME_LONG = "android.hardware.radio.metadata.HD_STATION_NAME_LONG";
    public static final java.lang.String METADATA_KEY_HD_STATION_NAME_SHORT = "android.hardware.radio.metadata.HD_STATION_NAME_SHORT";
    public static final java.lang.String METADATA_KEY_HD_SUBCHANNELS_AVAILABLE = "android.hardware.radio.metadata.HD_SUBCHANNELS_AVAILABLE";
    public static final java.lang.String METADATA_KEY_ICON = "android.hardware.radio.metadata.ICON";
    public static final java.lang.String METADATA_KEY_PROGRAM_NAME = "android.hardware.radio.metadata.PROGRAM_NAME";
    public static final java.lang.String METADATA_KEY_RBDS_PTY = "android.hardware.radio.metadata.RBDS_PTY";
    public static final java.lang.String METADATA_KEY_RDS_PI = "android.hardware.radio.metadata.RDS_PI";
    public static final java.lang.String METADATA_KEY_RDS_PS = "android.hardware.radio.metadata.RDS_PS";
    public static final java.lang.String METADATA_KEY_RDS_PTY = "android.hardware.radio.metadata.RDS_PTY";
    public static final java.lang.String METADATA_KEY_RDS_RT = "android.hardware.radio.metadata.RDS_RT";
    public static final java.lang.String METADATA_KEY_TITLE = "android.hardware.radio.metadata.TITLE";
    public static final java.lang.String METADATA_KEY_UFIDS = "android.hardware.radio.metadata.UFIDS";
    private static final int METADATA_TYPE_BITMAP = 2;
    private static final int METADATA_TYPE_CLOCK = 3;
    private static final int METADATA_TYPE_INT = 0;
    private static final int METADATA_TYPE_INVALID = -1;
    private static final int METADATA_TYPE_TEXT = 1;
    private static final int METADATA_TYPE_TEXT_ARRAY = 4;
    private static final int NATIVE_KEY_ALBUM = 7;
    private static final int NATIVE_KEY_ART = 10;
    private static final int NATIVE_KEY_ARTIST = 6;
    private static final int NATIVE_KEY_CLOCK = 11;
    private static final int NATIVE_KEY_GENRE = 8;
    private static final int NATIVE_KEY_ICON = 9;
    private static final int NATIVE_KEY_INVALID = -1;
    private static final android.util.SparseArray<java.lang.String> NATIVE_KEY_MAPPING;
    private static final int NATIVE_KEY_RBDS_PTY = 3;
    private static final int NATIVE_KEY_RDS_PI = 0;
    private static final int NATIVE_KEY_RDS_PS = 1;
    private static final int NATIVE_KEY_RDS_PTY = 2;
    private static final int NATIVE_KEY_RDS_RT = 4;
    private static final int NATIVE_KEY_TITLE = 5;
    private static final java.lang.String TAG = "BroadcastRadio.metadata";
    private final android.os.Bundle mBundle;
    private java.lang.Integer mHashCode;

    static {
        METADATA_KEYS_TYPE.put(METADATA_KEY_RDS_PI, 0);
        METADATA_KEYS_TYPE.put(METADATA_KEY_RDS_PS, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_RDS_PTY, 0);
        METADATA_KEYS_TYPE.put(METADATA_KEY_RBDS_PTY, 0);
        METADATA_KEYS_TYPE.put(METADATA_KEY_RDS_RT, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_TITLE, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_ARTIST, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_ALBUM, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_GENRE, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_ICON, 2);
        METADATA_KEYS_TYPE.put(METADATA_KEY_ART, 2);
        METADATA_KEYS_TYPE.put(METADATA_KEY_CLOCK, 3);
        METADATA_KEYS_TYPE.put(METADATA_KEY_PROGRAM_NAME, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DAB_ENSEMBLE_NAME, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DAB_ENSEMBLE_NAME_SHORT, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DAB_SERVICE_NAME, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DAB_SERVICE_NAME_SHORT, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DAB_COMPONENT_NAME, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DAB_COMPONENT_NAME_SHORT, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_COMMENT_SHORT_DESCRIPTION, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_COMMENT_ACTUAL_TEXT, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_COMMERCIAL, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_UFIDS, 4);
        METADATA_KEYS_TYPE.put(METADATA_KEY_HD_STATION_NAME_SHORT, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_HD_STATION_NAME_LONG, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_HD_SUBCHANNELS_AVAILABLE, 0);
        NATIVE_KEY_MAPPING = new android.util.SparseArray<>();
        NATIVE_KEY_MAPPING.put(0, METADATA_KEY_RDS_PI);
        NATIVE_KEY_MAPPING.put(1, METADATA_KEY_RDS_PS);
        NATIVE_KEY_MAPPING.put(2, METADATA_KEY_RDS_PTY);
        NATIVE_KEY_MAPPING.put(3, METADATA_KEY_RBDS_PTY);
        NATIVE_KEY_MAPPING.put(4, METADATA_KEY_RDS_RT);
        NATIVE_KEY_MAPPING.put(5, METADATA_KEY_TITLE);
        NATIVE_KEY_MAPPING.put(6, METADATA_KEY_ARTIST);
        NATIVE_KEY_MAPPING.put(7, METADATA_KEY_ALBUM);
        NATIVE_KEY_MAPPING.put(8, METADATA_KEY_GENRE);
        NATIVE_KEY_MAPPING.put(9, METADATA_KEY_ICON);
        NATIVE_KEY_MAPPING.put(10, METADATA_KEY_ART);
        NATIVE_KEY_MAPPING.put(11, METADATA_KEY_CLOCK);
        CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.RadioMetadata>() { // from class: android.hardware.radio.RadioMetadata.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.radio.RadioMetadata createFromParcel(android.os.Parcel parcel) {
                return new android.hardware.radio.RadioMetadata(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.radio.RadioMetadata[] newArray(int i) {
                return new android.hardware.radio.RadioMetadata[i];
            }
        };
    }

    @android.annotation.SystemApi
    public static final class Clock implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.hardware.radio.RadioMetadata.Clock> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.RadioMetadata.Clock>() { // from class: android.hardware.radio.RadioMetadata.Clock.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.radio.RadioMetadata.Clock createFromParcel(android.os.Parcel parcel) {
                return new android.hardware.radio.RadioMetadata.Clock(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.radio.RadioMetadata.Clock[] newArray(int i) {
                return new android.hardware.radio.RadioMetadata.Clock[i];
            }
        };
        private final int mTimezoneOffsetMinutes;
        private final long mUtcEpochSeconds;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeLong(this.mUtcEpochSeconds);
            parcel.writeInt(this.mTimezoneOffsetMinutes);
        }

        public Clock(long j, int i) {
            this.mUtcEpochSeconds = j;
            this.mTimezoneOffsetMinutes = i;
        }

        private Clock(android.os.Parcel parcel) {
            this.mUtcEpochSeconds = parcel.readLong();
            this.mTimezoneOffsetMinutes = parcel.readInt();
        }

        public long getUtcEpochSeconds() {
            return this.mUtcEpochSeconds;
        }

        public int getTimezoneOffsetMinutes() {
            return this.mTimezoneOffsetMinutes;
        }
    }

    public int hashCode() {
        if (this.mHashCode == null) {
            java.util.ArrayList arrayList = new java.util.ArrayList(this.mBundle.keySet());
            arrayList.sort(null);
            java.lang.Object[] objArr = new java.lang.Object[arrayList.size() * 2];
            for (int i = 0; i < arrayList.size(); i++) {
                int i2 = i * 2;
                objArr[i2] = arrayList.get(i);
                objArr[i2 + 1] = this.mBundle.get((java.lang.String) arrayList.get(i));
            }
            this.mHashCode = java.lang.Integer.valueOf(java.util.Arrays.hashCode(objArr));
        }
        return this.mHashCode.intValue();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.hardware.radio.RadioMetadata)) {
            return false;
        }
        android.os.Bundle bundle = ((android.hardware.radio.RadioMetadata) obj).mBundle;
        if (!this.mBundle.keySet().equals(bundle.keySet())) {
            return false;
        }
        for (java.lang.String str : this.mBundle.keySet()) {
            if (android.hardware.radio.Flags.hdRadioImproved() && java.util.Objects.equals(METADATA_KEYS_TYPE.get(str), 4)) {
                if (!java.util.Arrays.equals(this.mBundle.getStringArray(str), bundle.getStringArray(str))) {
                    return false;
                }
            } else if (!java.util.Objects.equals(this.mBundle.get(str), bundle.get(str))) {
                return false;
            }
        }
        return true;
    }

    RadioMetadata() {
        this.mBundle = new android.os.Bundle();
    }

    private RadioMetadata(android.os.Bundle bundle) {
        this.mBundle = new android.os.Bundle(bundle);
    }

    private RadioMetadata(android.os.Parcel parcel) {
        this.mBundle = parcel.readBundle();
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("RadioMetadata[");
        boolean z = true;
        for (java.lang.String str : this.mBundle.keySet()) {
            if (z) {
                z = false;
            } else {
                sb.append(", ");
            }
            sb.append(str.startsWith("android.hardware.radio.metadata") ? str.substring("android.hardware.radio.metadata".length()) : str);
            sb.append('=');
            if (android.hardware.radio.Flags.hdRadioImproved() && java.util.Objects.equals(METADATA_KEYS_TYPE.get(str), 4)) {
                java.lang.String[] stringArray = this.mBundle.getStringArray(str);
                sb.append('[');
                for (int i = 0; i < stringArray.length; i++) {
                    if (i != 0) {
                        sb.append(',');
                    }
                    sb.append(stringArray[i]);
                }
                sb.append(']');
            } else {
                sb.append(this.mBundle.get(str));
            }
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }

    public boolean containsKey(java.lang.String str) {
        return this.mBundle.containsKey(str);
    }

    public java.lang.String getString(java.lang.String str) {
        return this.mBundle.getString(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void putInt(android.os.Bundle bundle, java.lang.String str, int i) {
        int intValue = METADATA_KEYS_TYPE.getOrDefault(str, -1).intValue();
        if (intValue != 0 && intValue != 2) {
            throw new java.lang.IllegalArgumentException("The " + str + " key cannot be used to put an int");
        }
        bundle.putInt(str, i);
    }

    public int getInt(java.lang.String str) {
        return this.mBundle.getInt(str, 0);
    }

    @java.lang.Deprecated
    public android.graphics.Bitmap getBitmap(java.lang.String str) {
        try {
            return (android.graphics.Bitmap) this.mBundle.getParcelable(str, android.graphics.Bitmap.class);
        } catch (java.lang.Exception e) {
            android.util.Log.w(TAG, "Failed to retrieve a key as Bitmap.", e);
            return null;
        }
    }

    public int getBitmapId(java.lang.String str) {
        java.util.Objects.requireNonNull(str, "Metadata key can not be null");
        if (!METADATA_KEY_ICON.equals(str) && !METADATA_KEY_ART.equals(str)) {
            throw new java.lang.IllegalArgumentException("Failed to retrieve key " + str + " as bitmap key");
        }
        return getInt(str);
    }

    public android.hardware.radio.RadioMetadata.Clock getClock(java.lang.String str) {
        try {
            return (android.hardware.radio.RadioMetadata.Clock) this.mBundle.getParcelable(str, android.hardware.radio.RadioMetadata.Clock.class);
        } catch (java.lang.Exception e) {
            android.util.Log.w(TAG, "Failed to retrieve a key as Clock.", e);
            return null;
        }
    }

    public java.lang.String[] getStringArray(java.lang.String str) {
        java.util.Objects.requireNonNull(str, "Metadata key can not be null");
        if (!java.util.Objects.equals(METADATA_KEYS_TYPE.get(str), 4)) {
            throw new java.lang.IllegalArgumentException("Failed to retrieve key " + str + " as string array");
        }
        java.lang.String[] stringArray = this.mBundle.getStringArray(str);
        if (stringArray == null) {
            throw new java.lang.IllegalArgumentException("Key " + str + " is not found in metadata");
        }
        return stringArray;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeBundle(this.mBundle);
    }

    public int size() {
        return this.mBundle.size();
    }

    public java.util.Set<java.lang.String> keySet() {
        return this.mBundle.keySet();
    }

    public static java.lang.String getKeyFromNativeKey(int i) {
        return NATIVE_KEY_MAPPING.get(i, null);
    }

    public static final class Builder {
        private final android.os.Bundle mBundle;

        public Builder() {
            this.mBundle = new android.os.Bundle();
        }

        public Builder(android.hardware.radio.RadioMetadata radioMetadata) {
            this.mBundle = new android.os.Bundle(radioMetadata.mBundle);
        }

        public Builder(android.hardware.radio.RadioMetadata radioMetadata, int i) {
            this(radioMetadata);
            for (java.lang.String str : this.mBundle.keySet()) {
                java.lang.Object obj = this.mBundle.get(str);
                if (obj != null && (obj instanceof android.graphics.Bitmap)) {
                    android.graphics.Bitmap bitmap = (android.graphics.Bitmap) obj;
                    if (bitmap.getHeight() > i || bitmap.getWidth() > i) {
                        putBitmap(str, scaleBitmap(bitmap, i));
                    }
                }
            }
        }

        public android.hardware.radio.RadioMetadata.Builder putString(java.lang.String str, java.lang.String str2) {
            if (!android.hardware.radio.RadioMetadata.METADATA_KEYS_TYPE.containsKey(str) || ((java.lang.Integer) android.hardware.radio.RadioMetadata.METADATA_KEYS_TYPE.get(str)).intValue() != 1) {
                throw new java.lang.IllegalArgumentException("The " + str + " key cannot be used to put a String");
            }
            this.mBundle.putString(str, str2);
            return this;
        }

        public android.hardware.radio.RadioMetadata.Builder putInt(java.lang.String str, int i) {
            android.hardware.radio.RadioMetadata.putInt(this.mBundle, str, i);
            return this;
        }

        public android.hardware.radio.RadioMetadata.Builder putBitmap(java.lang.String str, android.graphics.Bitmap bitmap) {
            if (!android.hardware.radio.RadioMetadata.METADATA_KEYS_TYPE.containsKey(str) || ((java.lang.Integer) android.hardware.radio.RadioMetadata.METADATA_KEYS_TYPE.get(str)).intValue() != 2) {
                throw new java.lang.IllegalArgumentException("The " + str + " key cannot be used to put a Bitmap");
            }
            this.mBundle.putParcelable(str, bitmap);
            return this;
        }

        public android.hardware.radio.RadioMetadata.Builder putClock(java.lang.String str, long j, int i) {
            if (!android.hardware.radio.RadioMetadata.METADATA_KEYS_TYPE.containsKey(str) || ((java.lang.Integer) android.hardware.radio.RadioMetadata.METADATA_KEYS_TYPE.get(str)).intValue() != 3) {
                throw new java.lang.IllegalArgumentException("The " + str + " key cannot be used to put a RadioMetadata.Clock.");
            }
            this.mBundle.putParcelable(str, new android.hardware.radio.RadioMetadata.Clock(j, i));
            return this;
        }

        public android.hardware.radio.RadioMetadata.Builder putStringArray(java.lang.String str, java.lang.String[] strArr) {
            java.util.Objects.requireNonNull(str, "Key can not be null");
            java.util.Objects.requireNonNull(strArr, "Value can not be null");
            if (!android.hardware.radio.RadioMetadata.METADATA_KEYS_TYPE.containsKey(str) || !java.util.Objects.equals(android.hardware.radio.RadioMetadata.METADATA_KEYS_TYPE.get(str), 4)) {
                throw new java.lang.IllegalArgumentException("The " + str + " key cannot be used to put a RadioMetadata String Array.");
            }
            this.mBundle.putStringArray(str, strArr);
            return this;
        }

        public android.hardware.radio.RadioMetadata build() {
            return new android.hardware.radio.RadioMetadata(this.mBundle);
        }

        private android.graphics.Bitmap scaleBitmap(android.graphics.Bitmap bitmap, int i) {
            float f = i;
            float min = java.lang.Math.min(f / bitmap.getWidth(), f / bitmap.getHeight());
            return android.graphics.Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * min), (int) (bitmap.getHeight() * min), true);
        }
    }

    int putIntFromNative(int i, int i2) {
        try {
            putInt(this.mBundle, getKeyFromNativeKey(i), i2);
            this.mHashCode = null;
            return 0;
        } catch (java.lang.IllegalArgumentException e) {
            return -1;
        }
    }

    int putStringFromNative(int i, java.lang.String str) {
        java.lang.String keyFromNativeKey = getKeyFromNativeKey(i);
        if (!METADATA_KEYS_TYPE.containsKey(keyFromNativeKey) || METADATA_KEYS_TYPE.get(keyFromNativeKey).intValue() != 1) {
            return -1;
        }
        this.mBundle.putString(keyFromNativeKey, str);
        this.mHashCode = null;
        return 0;
    }

    int putBitmapFromNative(int i, byte[] bArr) {
        java.lang.String keyFromNativeKey = getKeyFromNativeKey(i);
        if (!METADATA_KEYS_TYPE.containsKey(keyFromNativeKey) || METADATA_KEYS_TYPE.get(keyFromNativeKey).intValue() != 2) {
            return -1;
        }
        try {
            android.graphics.Bitmap decodeByteArray = android.graphics.BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
            if (decodeByteArray != null) {
                this.mBundle.putParcelable(keyFromNativeKey, decodeByteArray);
                this.mHashCode = null;
                return 0;
            }
        } catch (java.lang.Exception e) {
        }
        return -1;
    }

    int putClockFromNative(int i, long j, int i2) {
        java.lang.String keyFromNativeKey = getKeyFromNativeKey(i);
        if (!METADATA_KEYS_TYPE.containsKey(keyFromNativeKey) || METADATA_KEYS_TYPE.get(keyFromNativeKey).intValue() != 3) {
            return -1;
        }
        this.mBundle.putParcelable(keyFromNativeKey, new android.hardware.radio.RadioMetadata.Clock(j, i2));
        this.mHashCode = null;
        return 0;
    }
}
