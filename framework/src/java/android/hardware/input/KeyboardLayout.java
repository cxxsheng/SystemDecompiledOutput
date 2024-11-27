package android.hardware.input;

/* loaded from: classes2.dex */
public final class KeyboardLayout implements android.os.Parcelable, java.lang.Comparable<android.hardware.input.KeyboardLayout> {
    public static final android.os.Parcelable.Creator<android.hardware.input.KeyboardLayout> CREATOR = new android.os.Parcelable.Creator<android.hardware.input.KeyboardLayout>() { // from class: android.hardware.input.KeyboardLayout.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.input.KeyboardLayout createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.input.KeyboardLayout(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.input.KeyboardLayout[] newArray(int i) {
            return new android.hardware.input.KeyboardLayout[i];
        }
    };
    public static final java.lang.String LAYOUT_TYPE_AZERTY = "azerty";
    public static final java.lang.String LAYOUT_TYPE_COLEMAK = "colemak";
    public static final java.lang.String LAYOUT_TYPE_DVORAK = "dvorak";
    public static final java.lang.String LAYOUT_TYPE_EXTENDED = "extended";
    public static final java.lang.String LAYOUT_TYPE_QWERTY = "qwerty";
    public static final java.lang.String LAYOUT_TYPE_QWERTZ = "qwertz";
    public static final java.lang.String LAYOUT_TYPE_TURKISH_F = "turkish_f";
    public static final java.lang.String LAYOUT_TYPE_TURKISH_Q = "turkish_q";
    public static final java.lang.String LAYOUT_TYPE_UNDEFINED = "undefined";
    public static final java.lang.String LAYOUT_TYPE_WORKMAN = "workman";
    private final java.lang.String mCollection;
    private final java.lang.String mDescriptor;
    private final java.lang.String mLabel;
    private final android.hardware.input.KeyboardLayout.LayoutType mLayoutType;
    private final android.os.LocaleList mLocales;
    private final int mPriority;
    private final int mProductId;
    private final int mVendorId;

    public enum LayoutType {
        UNDEFINED(0, android.hardware.input.KeyboardLayout.LAYOUT_TYPE_UNDEFINED),
        QWERTY(1, "qwerty"),
        QWERTZ(2, android.hardware.input.KeyboardLayout.LAYOUT_TYPE_QWERTZ),
        AZERTY(3, android.hardware.input.KeyboardLayout.LAYOUT_TYPE_AZERTY),
        DVORAK(4, android.hardware.input.KeyboardLayout.LAYOUT_TYPE_DVORAK),
        COLEMAK(5, android.hardware.input.KeyboardLayout.LAYOUT_TYPE_COLEMAK),
        WORKMAN(6, android.hardware.input.KeyboardLayout.LAYOUT_TYPE_WORKMAN),
        TURKISH_Q(7, android.hardware.input.KeyboardLayout.LAYOUT_TYPE_TURKISH_Q),
        TURKISH_F(8, android.hardware.input.KeyboardLayout.LAYOUT_TYPE_TURKISH_F),
        EXTENDED(9, android.hardware.input.KeyboardLayout.LAYOUT_TYPE_EXTENDED);

        private final java.lang.String mName;
        private final int mValue;
        private static final java.util.Map<java.lang.Integer, android.hardware.input.KeyboardLayout.LayoutType> VALUE_TO_ENUM_MAP = new java.util.HashMap();
        private static final java.util.Map<java.lang.String, android.hardware.input.KeyboardLayout.LayoutType> NAME_TO_ENUM_MAP = new java.util.HashMap();

        static {
            for (android.hardware.input.KeyboardLayout.LayoutType layoutType : values()) {
                VALUE_TO_ENUM_MAP.put(java.lang.Integer.valueOf(layoutType.mValue), layoutType);
                NAME_TO_ENUM_MAP.put(layoutType.mName, layoutType);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static android.hardware.input.KeyboardLayout.LayoutType of(int i) {
            return VALUE_TO_ENUM_MAP.getOrDefault(java.lang.Integer.valueOf(i), UNDEFINED);
        }

        LayoutType(int i, java.lang.String str) {
            this.mValue = i;
            this.mName = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getValue() {
            return this.mValue;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public java.lang.String getName() {
            return this.mName;
        }

        public static int getLayoutTypeEnumValue(java.lang.String str) {
            return NAME_TO_ENUM_MAP.getOrDefault(str, UNDEFINED).getValue();
        }

        public static java.lang.String getLayoutNameFromValue(int i) {
            return VALUE_TO_ENUM_MAP.getOrDefault(java.lang.Integer.valueOf(i), UNDEFINED).getName();
        }
    }

    public KeyboardLayout(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, android.os.LocaleList localeList, int i2, int i3, int i4) {
        this.mDescriptor = str;
        this.mLabel = str2;
        this.mCollection = str3;
        this.mPriority = i;
        this.mLocales = localeList;
        this.mLayoutType = android.hardware.input.KeyboardLayout.LayoutType.of(i2);
        this.mVendorId = i3;
        this.mProductId = i4;
    }

    private KeyboardLayout(android.os.Parcel parcel) {
        this.mDescriptor = parcel.readString();
        this.mLabel = parcel.readString();
        this.mCollection = parcel.readString();
        this.mPriority = parcel.readInt();
        this.mLocales = android.os.LocaleList.CREATOR.createFromParcel(parcel);
        this.mLayoutType = android.hardware.input.KeyboardLayout.LayoutType.of(parcel.readInt());
        this.mVendorId = parcel.readInt();
        this.mProductId = parcel.readInt();
    }

    public java.lang.String getDescriptor() {
        return this.mDescriptor;
    }

    public java.lang.String getLabel() {
        return this.mLabel;
    }

    public java.lang.String getCollection() {
        return this.mCollection;
    }

    public android.os.LocaleList getLocales() {
        return this.mLocales;
    }

    public java.lang.String getLayoutType() {
        return this.mLayoutType.getName();
    }

    public int getVendorId() {
        return this.mVendorId;
    }

    public int getProductId() {
        return this.mProductId;
    }

    public boolean isAnsiLayout() {
        for (int i = 0; i < this.mLocales.size(); i++) {
            java.util.Locale locale = this.mLocales.get(i);
            if (locale != null && locale.getCountry().equalsIgnoreCase(android.app.blob.XmlTags.ATTR_USER_ID) && this.mLayoutType != android.hardware.input.KeyboardLayout.LayoutType.EXTENDED) {
                return true;
            }
        }
        return false;
    }

    public boolean isJisLayout() {
        for (int i = 0; i < this.mLocales.size(); i++) {
            java.util.Locale locale = this.mLocales.get(i);
            if (locale != null && locale.getCountry().equalsIgnoreCase("jp")) {
                return true;
            }
        }
        return false;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mDescriptor);
        parcel.writeString(this.mLabel);
        parcel.writeString(this.mCollection);
        parcel.writeInt(this.mPriority);
        this.mLocales.writeToParcel(parcel, 0);
        parcel.writeInt(this.mLayoutType.getValue());
        parcel.writeInt(this.mVendorId);
        parcel.writeInt(this.mProductId);
    }

    @Override // java.lang.Comparable
    public int compareTo(android.hardware.input.KeyboardLayout keyboardLayout) {
        int compare = java.lang.Integer.compare(keyboardLayout.mPriority, this.mPriority);
        if (compare == 0) {
            compare = java.lang.Integer.compare(this.mLayoutType.mValue, keyboardLayout.mLayoutType.mValue);
        }
        if (compare == 0) {
            compare = this.mLabel.compareToIgnoreCase(keyboardLayout.mLabel);
        }
        if (compare == 0) {
            return this.mCollection.compareToIgnoreCase(keyboardLayout.mCollection);
        }
        return compare;
    }

    public java.lang.String toString() {
        return "KeyboardLayout " + this.mLabel + (this.mCollection.isEmpty() ? "" : " - " + this.mCollection) + ", descriptor: " + this.mDescriptor + ", priority: " + this.mPriority + ", locales: " + this.mLocales.toString() + ", layout type: " + this.mLayoutType.getName() + ", vendorId: " + this.mVendorId + ", productId: " + this.mProductId;
    }

    public static boolean isLayoutTypeValid(java.lang.String str) {
        java.util.Objects.requireNonNull(str, "Provided layout name should not be null");
        for (android.hardware.input.KeyboardLayout.LayoutType layoutType : android.hardware.input.KeyboardLayout.LayoutType.values()) {
            if (str.equals(layoutType.getName())) {
                return true;
            }
        }
        return false;
    }
}
