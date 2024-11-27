package android.text;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class FontConfig implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.text.FontConfig> CREATOR = new android.os.Parcelable.Creator<android.text.FontConfig>() { // from class: android.text.FontConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.text.FontConfig createFromParcel(android.os.Parcel parcel) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            parcel.readTypedList(arrayList, android.text.FontConfig.FontFamily.CREATOR);
            java.util.ArrayList arrayList2 = new java.util.ArrayList();
            parcel.readTypedList(arrayList2, android.text.FontConfig.Alias.CREATOR);
            java.util.ArrayList arrayList3 = new java.util.ArrayList();
            parcel.readTypedList(arrayList3, android.text.FontConfig.NamedFamilyList.CREATOR);
            return new android.text.FontConfig(arrayList, arrayList2, arrayList3, java.util.Collections.emptyList(), parcel.readLong(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.text.FontConfig[] newArray(int i) {
            return new android.text.FontConfig[i];
        }
    };
    private final java.util.List<android.text.FontConfig.Alias> mAliases;
    private final int mConfigVersion;
    private final java.util.List<android.text.FontConfig.FontFamily> mFamilies;
    private final long mLastModifiedTimeMillis;
    private final java.util.List<android.text.FontConfig.Customization.LocaleFallback> mLocaleFallbackCustomizations;
    private final java.util.List<android.text.FontConfig.NamedFamilyList> mNamedFamilyLists;

    public FontConfig(java.util.List<android.text.FontConfig.FontFamily> list, java.util.List<android.text.FontConfig.Alias> list2, java.util.List<android.text.FontConfig.NamedFamilyList> list3, java.util.List<android.text.FontConfig.Customization.LocaleFallback> list4, long j, int i) {
        this.mFamilies = list;
        this.mAliases = list2;
        this.mNamedFamilyLists = list3;
        this.mLocaleFallbackCustomizations = list4;
        this.mLastModifiedTimeMillis = j;
        this.mConfigVersion = i;
    }

    public FontConfig(java.util.List<android.text.FontConfig.FontFamily> list, java.util.List<android.text.FontConfig.Alias> list2, long j, int i) {
        this(list, list2, java.util.Collections.emptyList(), java.util.Collections.emptyList(), j, i);
    }

    public java.util.List<android.text.FontConfig.FontFamily> getFontFamilies() {
        return this.mFamilies;
    }

    public java.util.List<android.text.FontConfig.Alias> getAliases() {
        return this.mAliases;
    }

    public java.util.List<android.text.FontConfig.NamedFamilyList> getNamedFamilyLists() {
        return this.mNamedFamilyLists;
    }

    public java.util.List<android.text.FontConfig.Customization.LocaleFallback> getLocaleFallbackCustomizations() {
        return this.mLocaleFallbackCustomizations;
    }

    public long getLastModifiedTimeMillis() {
        return this.mLastModifiedTimeMillis;
    }

    public int getConfigVersion() {
        return this.mConfigVersion;
    }

    @java.lang.Deprecated
    public android.text.FontConfig.FontFamily[] getFamilies() {
        return (android.text.FontConfig.FontFamily[]) this.mFamilies.toArray(new android.text.FontConfig.FontFamily[0]);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedList(this.mFamilies, i);
        parcel.writeTypedList(this.mAliases, i);
        parcel.writeTypedList(this.mNamedFamilyLists, i);
        parcel.writeLong(this.mLastModifiedTimeMillis);
        parcel.writeInt(this.mConfigVersion);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.text.FontConfig fontConfig = (android.text.FontConfig) obj;
        if (this.mLastModifiedTimeMillis == fontConfig.mLastModifiedTimeMillis && this.mConfigVersion == fontConfig.mConfigVersion && java.util.Objects.equals(this.mFamilies, fontConfig.mFamilies) && java.util.Objects.equals(this.mAliases, fontConfig.mAliases)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mFamilies, this.mAliases, java.lang.Long.valueOf(this.mLastModifiedTimeMillis), java.lang.Integer.valueOf(this.mConfigVersion));
    }

    public java.lang.String toString() {
        return "FontConfig{mFamilies=" + this.mFamilies + ", mAliases=" + this.mAliases + ", mLastModifiedTimeMillis=" + this.mLastModifiedTimeMillis + ", mConfigVersion=" + this.mConfigVersion + '}';
    }

    public static final class Font implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.text.FontConfig.Font> CREATOR = new android.os.Parcelable.Creator<android.text.FontConfig.Font>() { // from class: android.text.FontConfig.Font.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.text.FontConfig.Font createFromParcel(android.os.Parcel parcel) {
                java.io.File file = new java.io.File(parcel.readString8());
                java.lang.String readString8 = parcel.readString8();
                return new android.text.FontConfig.Font(file, readString8 == null ? null : new java.io.File(readString8), parcel.readString8(), new android.graphics.fonts.FontStyle(parcel.readInt(), parcel.readInt()), parcel.readInt(), parcel.readString8(), parcel.readString8(), parcel.readInt());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.text.FontConfig.Font[] newArray(int i) {
                return new android.text.FontConfig.Font[i];
            }
        };
        public static final int VAR_TYPE_AXES_ITAL = 2;
        public static final int VAR_TYPE_AXES_NONE = 0;
        public static final int VAR_TYPE_AXES_WGHT = 1;
        private final java.io.File mFile;
        private final java.lang.String mFontFamilyName;
        private final java.lang.String mFontVariationSettings;
        private final int mIndex;
        private final java.io.File mOriginalFile;
        private final java.lang.String mPostScriptName;
        private final android.graphics.fonts.FontStyle mStyle;
        private final int mVarTypeAxes;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface VarTypeAxes {
        }

        public Font(java.io.File file, java.io.File file2, java.lang.String str, android.graphics.fonts.FontStyle fontStyle, int i, java.lang.String str2, java.lang.String str3, int i2) {
            this.mFile = file;
            this.mOriginalFile = file2;
            this.mPostScriptName = str;
            this.mStyle = fontStyle;
            this.mIndex = i;
            this.mFontVariationSettings = str2;
            this.mFontFamilyName = str3;
            this.mVarTypeAxes = i2;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeString8(this.mFile.getAbsolutePath());
            parcel.writeString8(this.mOriginalFile == null ? null : this.mOriginalFile.getAbsolutePath());
            parcel.writeString8(this.mPostScriptName);
            parcel.writeInt(this.mStyle.getWeight());
            parcel.writeInt(this.mStyle.getSlant());
            parcel.writeInt(this.mIndex);
            parcel.writeString8(this.mFontVariationSettings);
            parcel.writeString8(this.mFontFamilyName);
            parcel.writeInt(this.mVarTypeAxes);
        }

        public java.io.File getFile() {
            return this.mFile;
        }

        public java.io.File getOriginalFile() {
            return this.mOriginalFile;
        }

        public android.graphics.fonts.FontStyle getStyle() {
            return this.mStyle;
        }

        public java.lang.String getFontVariationSettings() {
            return this.mFontVariationSettings;
        }

        public java.lang.String getFontFamilyName() {
            return this.mFontFamilyName;
        }

        public int getTtcIndex() {
            return this.mIndex;
        }

        public java.lang.String getPostScriptName() {
            return this.mPostScriptName;
        }

        public int getVarTypeAxes() {
            return this.mVarTypeAxes;
        }

        @java.lang.Deprecated
        public android.graphics.fonts.FontVariationAxis[] getAxes() {
            return android.graphics.fonts.FontVariationAxis.fromFontVariationSettings(this.mFontVariationSettings);
        }

        @java.lang.Deprecated
        public int getWeight() {
            return getStyle().getWeight();
        }

        @java.lang.Deprecated
        public boolean isItalic() {
            return getStyle().getSlant() == 1;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.text.FontConfig.Font font = (android.text.FontConfig.Font) obj;
            if (this.mIndex == font.mIndex && java.util.Objects.equals(this.mFile, font.mFile) && java.util.Objects.equals(this.mOriginalFile, font.mOriginalFile) && java.util.Objects.equals(this.mStyle, font.mStyle) && java.util.Objects.equals(this.mFontVariationSettings, font.mFontVariationSettings) && java.util.Objects.equals(this.mFontFamilyName, font.mFontFamilyName) && this.mVarTypeAxes == font.mVarTypeAxes) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(this.mFile, this.mOriginalFile, this.mStyle, java.lang.Integer.valueOf(this.mIndex), this.mFontVariationSettings, this.mFontFamilyName, java.lang.Integer.valueOf(this.mVarTypeAxes));
        }

        public java.lang.String toString() {
            return "Font{mFile=" + this.mFile + ", mOriginalFile=" + this.mOriginalFile + ", mStyle=" + this.mStyle + ", mIndex=" + this.mIndex + ", mFontVariationSettings='" + this.mFontVariationSettings + android.text.format.DateFormat.QUOTE + ", mFontFamilyName='" + this.mFontFamilyName + android.text.format.DateFormat.QUOTE + ", mVarTypeAxes='" + this.mVarTypeAxes + android.text.format.DateFormat.QUOTE + '}';
        }
    }

    public static final class Alias implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.text.FontConfig.Alias> CREATOR = new android.os.Parcelable.Creator<android.text.FontConfig.Alias>() { // from class: android.text.FontConfig.Alias.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.text.FontConfig.Alias createFromParcel(android.os.Parcel parcel) {
                return new android.text.FontConfig.Alias(parcel.readString8(), parcel.readString8(), parcel.readInt());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.text.FontConfig.Alias[] newArray(int i) {
                return new android.text.FontConfig.Alias[i];
            }
        };
        private final java.lang.String mName;
        private final java.lang.String mOriginal;
        private final int mWeight;

        public Alias(java.lang.String str, java.lang.String str2, int i) {
            this.mName = str;
            this.mOriginal = str2;
            this.mWeight = i;
        }

        public java.lang.String getName() {
            return this.mName;
        }

        public java.lang.String getOriginal() {
            return this.mOriginal;
        }

        public int getWeight() {
            return this.mWeight;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeString8(this.mName);
            parcel.writeString8(this.mOriginal);
            parcel.writeInt(this.mWeight);
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.text.FontConfig.Alias alias = (android.text.FontConfig.Alias) obj;
            if (this.mWeight == alias.mWeight && java.util.Objects.equals(this.mName, alias.mName) && java.util.Objects.equals(this.mOriginal, alias.mOriginal)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(this.mName, this.mOriginal, java.lang.Integer.valueOf(this.mWeight));
        }

        public java.lang.String toString() {
            return "Alias{mName='" + this.mName + android.text.format.DateFormat.QUOTE + ", mOriginal='" + this.mOriginal + android.text.format.DateFormat.QUOTE + ", mWeight=" + this.mWeight + '}';
        }
    }

    public static final class FontFamily implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.text.FontConfig.FontFamily> CREATOR = new android.os.Parcelable.Creator<android.text.FontConfig.FontFamily>() { // from class: android.text.FontConfig.FontFamily.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.text.FontConfig.FontFamily createFromParcel(android.os.Parcel parcel) {
                java.util.ArrayList arrayList = new java.util.ArrayList();
                parcel.readTypedList(arrayList, android.text.FontConfig.Font.CREATOR);
                java.lang.String readString8 = parcel.readString8();
                return new android.text.FontConfig.FontFamily(arrayList, android.os.LocaleList.forLanguageTags(readString8), parcel.readInt());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.text.FontConfig.FontFamily[] newArray(int i) {
                return new android.text.FontConfig.FontFamily[i];
            }
        };
        public static final int VARIANT_COMPACT = 1;
        public static final int VARIANT_DEFAULT = 0;
        public static final int VARIANT_ELEGANT = 2;
        private final java.util.List<android.text.FontConfig.Font> mFonts;
        private final android.os.LocaleList mLocaleList;
        private final int mVariant;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface Variant {
        }

        public FontFamily(java.util.List<android.text.FontConfig.Font> list, android.os.LocaleList localeList, int i) {
            this.mFonts = list;
            this.mLocaleList = localeList;
            this.mVariant = i;
        }

        public java.util.List<android.text.FontConfig.Font> getFontList() {
            return this.mFonts;
        }

        @java.lang.Deprecated
        public java.lang.String getName() {
            return null;
        }

        public android.os.LocaleList getLocaleList() {
            return this.mLocaleList;
        }

        public int getVariant() {
            return this.mVariant;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeTypedList(this.mFonts, i);
            parcel.writeString8(this.mLocaleList.toLanguageTags());
            parcel.writeInt(this.mVariant);
        }

        @java.lang.Deprecated
        public android.text.FontConfig.Font[] getFonts() {
            return (android.text.FontConfig.Font[]) this.mFonts.toArray(new android.text.FontConfig.Font[0]);
        }

        @java.lang.Deprecated
        public java.lang.String getLanguages() {
            return this.mLocaleList.toLanguageTags();
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.text.FontConfig.FontFamily fontFamily = (android.text.FontConfig.FontFamily) obj;
            if (this.mVariant == fontFamily.mVariant && java.util.Objects.equals(this.mFonts, fontFamily.mFonts) && java.util.Objects.equals(this.mLocaleList, fontFamily.mLocaleList)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(this.mFonts, this.mLocaleList, java.lang.Integer.valueOf(this.mVariant));
        }

        public java.lang.String toString() {
            return "FontFamily{mFonts=" + this.mFonts + ", mLocaleList=" + this.mLocaleList + ", mVariant=" + this.mVariant + '}';
        }
    }

    public static final class NamedFamilyList implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.text.FontConfig.NamedFamilyList> CREATOR = new android.os.Parcelable.Creator<android.text.FontConfig.NamedFamilyList>() { // from class: android.text.FontConfig.NamedFamilyList.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.text.FontConfig.NamedFamilyList createFromParcel(android.os.Parcel parcel) {
                java.util.ArrayList arrayList = new java.util.ArrayList();
                parcel.readTypedList(arrayList, android.text.FontConfig.FontFamily.CREATOR);
                return new android.text.FontConfig.NamedFamilyList(arrayList, parcel.readString8());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.text.FontConfig.NamedFamilyList[] newArray(int i) {
                return new android.text.FontConfig.NamedFamilyList[i];
            }
        };
        private final java.util.List<android.text.FontConfig.FontFamily> mFamilies;
        private final java.lang.String mName;

        public NamedFamilyList(java.util.List<android.text.FontConfig.FontFamily> list, java.lang.String str) {
            this.mFamilies = list;
            this.mName = str;
        }

        public NamedFamilyList(android.text.FontConfig.FontFamily fontFamily) {
            this.mFamilies = new java.util.ArrayList();
            this.mFamilies.add(fontFamily);
            this.mName = fontFamily.getName();
        }

        public java.util.List<android.text.FontConfig.FontFamily> getFamilies() {
            return this.mFamilies;
        }

        public java.lang.String getName() {
            return this.mName;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeTypedList(this.mFamilies, i);
            parcel.writeString8(this.mName);
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.text.FontConfig.NamedFamilyList namedFamilyList = (android.text.FontConfig.NamedFamilyList) obj;
            if (java.util.Objects.equals(this.mFamilies, namedFamilyList.mFamilies) && java.util.Objects.equals(this.mName, namedFamilyList.mName)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(this.mFamilies, this.mName);
        }

        public java.lang.String toString() {
            return "NamedFamilyList{mFamilies=" + this.mFamilies + ", mName='" + this.mName + android.text.format.DateFormat.QUOTE + '}';
        }
    }

    public static class Customization {
        private Customization() {
        }

        public static class LocaleFallback {
            public static final int OPERATION_APPEND = 1;
            public static final int OPERATION_PREPEND = 0;
            public static final int OPERATION_REPLACE = 2;
            private final android.text.FontConfig.FontFamily mFamily;
            private final java.util.Locale mLocale;
            private final int mOperation;
            private final java.lang.String mScript;

            @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
            public @interface Operation {
            }

            public LocaleFallback(java.util.Locale locale, int i, android.text.FontConfig.FontFamily fontFamily) {
                this.mLocale = locale;
                this.mOperation = i;
                this.mFamily = fontFamily;
                this.mScript = android.text.FontConfig.resolveScript(locale);
            }

            public java.util.Locale getLocale() {
                return this.mLocale;
            }

            public int getOperation() {
                return this.mOperation;
            }

            public android.text.FontConfig.FontFamily getFamily() {
                return this.mFamily;
            }

            public java.lang.String getScript() {
                return this.mScript;
            }

            public java.lang.String toString() {
                return "LocaleFallback{mLocale=" + this.mLocale + ", mOperation=" + this.mOperation + ", mFamily=" + this.mFamily + '}';
            }
        }
    }

    public static java.lang.String resolveScript(java.util.Locale locale) {
        java.lang.String script = locale.getScript();
        if (script != null && !script.isEmpty()) {
            return script;
        }
        return android.icu.util.ULocale.addLikelySubtags(android.icu.util.ULocale.forLocale(locale)).getScript();
    }
}
