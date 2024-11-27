package android.hardware.input;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class VirtualKeyboardConfig extends android.hardware.input.VirtualInputDeviceConfig implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.input.VirtualKeyboardConfig> CREATOR = new android.os.Parcelable.Creator<android.hardware.input.VirtualKeyboardConfig>() { // from class: android.hardware.input.VirtualKeyboardConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.input.VirtualKeyboardConfig createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.input.VirtualKeyboardConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.input.VirtualKeyboardConfig[] newArray(int i) {
            return new android.hardware.input.VirtualKeyboardConfig[i];
        }
    };
    public static final java.lang.String DEFAULT_LANGUAGE_TAG = "en-Latn-US";
    public static final java.lang.String DEFAULT_LAYOUT_TYPE = "qwerty";
    private final java.lang.String mLanguageTag;
    private final java.lang.String mLayoutType;

    private VirtualKeyboardConfig(android.hardware.input.VirtualKeyboardConfig.Builder builder) {
        super(builder);
        this.mLanguageTag = builder.mLanguageTag;
        this.mLayoutType = builder.mLayoutType;
    }

    private VirtualKeyboardConfig(android.os.Parcel parcel) {
        super(parcel);
        this.mLanguageTag = parcel.readString8();
        this.mLayoutType = parcel.readString8();
    }

    public java.lang.String getLanguageTag() {
        return this.mLanguageTag;
    }

    public java.lang.String getLayoutType() {
        return this.mLayoutType;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.hardware.input.VirtualInputDeviceConfig, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString8(this.mLanguageTag);
        parcel.writeString8(this.mLayoutType);
    }

    @Override // android.hardware.input.VirtualInputDeviceConfig
    java.lang.String additionalFieldsToString() {
        return " languageTag=" + this.mLanguageTag + " layoutType=" + this.mLayoutType;
    }

    public static final class Builder extends android.hardware.input.VirtualInputDeviceConfig.Builder<android.hardware.input.VirtualKeyboardConfig.Builder> {
        private java.lang.String mLanguageTag = android.hardware.input.VirtualKeyboardConfig.DEFAULT_LANGUAGE_TAG;
        private java.lang.String mLayoutType = "qwerty";

        public android.hardware.input.VirtualKeyboardConfig.Builder setLanguageTag(java.lang.String str) {
            java.util.Objects.requireNonNull(str, "languageTag cannot be null");
            android.icu.util.ULocale forLanguageTag = android.icu.util.ULocale.forLanguageTag(str);
            if (forLanguageTag.getLanguage().isEmpty()) {
                throw new java.lang.IllegalArgumentException("The language tag is not valid.");
            }
            this.mLanguageTag = android.icu.util.ULocale.createCanonical(forLanguageTag).toLanguageTag();
            return this;
        }

        public android.hardware.input.VirtualKeyboardConfig.Builder setLayoutType(java.lang.String str) {
            java.util.Objects.requireNonNull(str, "layoutType cannot be null");
            this.mLayoutType = str;
            return this;
        }

        public android.hardware.input.VirtualKeyboardConfig build() {
            return new android.hardware.input.VirtualKeyboardConfig(this);
        }
    }
}
