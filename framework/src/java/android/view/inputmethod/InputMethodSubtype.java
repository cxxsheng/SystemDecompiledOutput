package android.view.inputmethod;

/* loaded from: classes4.dex */
public final class InputMethodSubtype implements android.os.Parcelable {
    private static final java.lang.String EXTRA_KEY_UNTRANSLATABLE_STRING_IN_SUBTYPE_NAME = "UntranslatableReplacementStringInSubtypeName";
    private static final java.lang.String EXTRA_VALUE_KEY_VALUE_SEPARATOR = "=";
    private static final java.lang.String EXTRA_VALUE_PAIR_SEPARATOR = ",";
    private static final java.lang.String LANGUAGE_TAG_NONE = "";
    public static final int SUBTYPE_ID_NONE = 0;
    private static final java.lang.String SUBTYPE_MODE_KEYBOARD = "keyboard";
    private static final java.lang.String UNDEFINED_LANGUAGE_TAG = "und";
    private volatile java.lang.String mCachedCanonicalizedLanguageTag;
    private volatile java.util.Locale mCachedLocaleObj;
    private volatile java.util.HashMap<java.lang.String, java.lang.String> mExtraValueHashMapCache;
    private final boolean mIsAsciiCapable;
    private final boolean mIsAuxiliary;
    private final java.lang.Object mLock;
    private final boolean mOverridesImplicitlyEnabledSubtype;
    private final java.lang.String mPkLanguageTag;
    private final java.lang.String mPkLayoutType;
    private final java.lang.String mSubtypeExtraValue;
    private final int mSubtypeHashCode;
    private final int mSubtypeIconResId;
    private final int mSubtypeId;
    private final java.lang.String mSubtypeLanguageTag;
    private final java.lang.String mSubtypeLocale;
    private final java.lang.String mSubtypeMode;
    private final java.lang.CharSequence mSubtypeNameOverride;
    private final int mSubtypeNameResId;
    private static final java.lang.String TAG = android.view.inputmethod.InputMethodSubtype.class.getSimpleName();
    public static final android.os.Parcelable.Creator<android.view.inputmethod.InputMethodSubtype> CREATOR = new android.os.Parcelable.Creator<android.view.inputmethod.InputMethodSubtype>() { // from class: android.view.inputmethod.InputMethodSubtype.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.InputMethodSubtype createFromParcel(android.os.Parcel parcel) {
            return new android.view.inputmethod.InputMethodSubtype(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.InputMethodSubtype[] newArray(int i) {
            return new android.view.inputmethod.InputMethodSubtype[i];
        }
    };

    public static class InputMethodSubtypeBuilder {
        private boolean mIsAuxiliary = false;
        private boolean mOverridesImplicitlyEnabledSubtype = false;
        private boolean mIsAsciiCapable = false;
        private int mSubtypeIconResId = 0;
        private int mSubtypeNameResId = 0;
        private java.lang.CharSequence mSubtypeNameOverride = "";
        private java.lang.String mPkLanguageTag = "";
        private java.lang.String mPkLayoutType = "";
        private int mSubtypeId = 0;
        private java.lang.String mSubtypeLocale = "";
        private java.lang.String mSubtypeLanguageTag = "";
        private java.lang.String mSubtypeMode = "";
        private java.lang.String mSubtypeExtraValue = "";

        public android.view.inputmethod.InputMethodSubtype.InputMethodSubtypeBuilder setIsAuxiliary(boolean z) {
            this.mIsAuxiliary = z;
            return this;
        }

        public android.view.inputmethod.InputMethodSubtype.InputMethodSubtypeBuilder setOverridesImplicitlyEnabledSubtype(boolean z) {
            this.mOverridesImplicitlyEnabledSubtype = z;
            return this;
        }

        public android.view.inputmethod.InputMethodSubtype.InputMethodSubtypeBuilder setIsAsciiCapable(boolean z) {
            this.mIsAsciiCapable = z;
            return this;
        }

        public android.view.inputmethod.InputMethodSubtype.InputMethodSubtypeBuilder setSubtypeIconResId(int i) {
            this.mSubtypeIconResId = i;
            return this;
        }

        public android.view.inputmethod.InputMethodSubtype.InputMethodSubtypeBuilder setSubtypeNameResId(int i) {
            this.mSubtypeNameResId = i;
            return this;
        }

        public android.view.inputmethod.InputMethodSubtype.InputMethodSubtypeBuilder setSubtypeNameOverride(java.lang.CharSequence charSequence) {
            this.mSubtypeNameOverride = charSequence;
            return this;
        }

        public android.view.inputmethod.InputMethodSubtype.InputMethodSubtypeBuilder setPhysicalKeyboardHint(android.icu.util.ULocale uLocale, java.lang.String str) {
            java.util.Objects.requireNonNull(str, "layoutType cannot be null");
            this.mPkLanguageTag = uLocale == null ? "" : uLocale.toLanguageTag();
            this.mPkLayoutType = str;
            return this;
        }

        public android.view.inputmethod.InputMethodSubtype.InputMethodSubtypeBuilder setSubtypeId(int i) {
            this.mSubtypeId = i;
            return this;
        }

        public android.view.inputmethod.InputMethodSubtype.InputMethodSubtypeBuilder setSubtypeLocale(java.lang.String str) {
            if (str == null) {
                str = "";
            }
            this.mSubtypeLocale = str;
            return this;
        }

        public android.view.inputmethod.InputMethodSubtype.InputMethodSubtypeBuilder setLanguageTag(java.lang.String str) {
            if (str == null) {
                str = "";
            }
            this.mSubtypeLanguageTag = str;
            return this;
        }

        public android.view.inputmethod.InputMethodSubtype.InputMethodSubtypeBuilder setSubtypeMode(java.lang.String str) {
            if (str == null) {
                str = "";
            }
            this.mSubtypeMode = str;
            return this;
        }

        public android.view.inputmethod.InputMethodSubtype.InputMethodSubtypeBuilder setSubtypeExtraValue(java.lang.String str) {
            if (str == null) {
                str = "";
            }
            this.mSubtypeExtraValue = str;
            return this;
        }

        public android.view.inputmethod.InputMethodSubtype build() {
            return new android.view.inputmethod.InputMethodSubtype(this);
        }
    }

    private static android.view.inputmethod.InputMethodSubtype.InputMethodSubtypeBuilder getBuilder(int i, int i2, java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z, boolean z2, int i3, boolean z3) {
        android.view.inputmethod.InputMethodSubtype.InputMethodSubtypeBuilder inputMethodSubtypeBuilder = new android.view.inputmethod.InputMethodSubtype.InputMethodSubtypeBuilder();
        inputMethodSubtypeBuilder.mSubtypeNameResId = i;
        inputMethodSubtypeBuilder.mSubtypeIconResId = i2;
        inputMethodSubtypeBuilder.mSubtypeLocale = str;
        inputMethodSubtypeBuilder.mSubtypeMode = str2;
        inputMethodSubtypeBuilder.mSubtypeExtraValue = str3;
        inputMethodSubtypeBuilder.mIsAuxiliary = z;
        inputMethodSubtypeBuilder.mOverridesImplicitlyEnabledSubtype = z2;
        inputMethodSubtypeBuilder.mSubtypeId = i3;
        inputMethodSubtypeBuilder.mIsAsciiCapable = z3;
        return inputMethodSubtypeBuilder;
    }

    @java.lang.Deprecated
    public InputMethodSubtype(int i, int i2, java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z, boolean z2) {
        this(i, i2, str, str2, str3, z, z2, 0);
    }

    @java.lang.Deprecated
    public InputMethodSubtype(int i, int i2, java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z, boolean z2, int i3) {
        this(getBuilder(i, i2, str, str2, str3, z, z2, i3, false));
    }

    private InputMethodSubtype(android.view.inputmethod.InputMethodSubtype.InputMethodSubtypeBuilder inputMethodSubtypeBuilder) {
        this.mLock = new java.lang.Object();
        this.mSubtypeNameResId = inputMethodSubtypeBuilder.mSubtypeNameResId;
        this.mSubtypeNameOverride = inputMethodSubtypeBuilder.mSubtypeNameOverride;
        this.mPkLanguageTag = inputMethodSubtypeBuilder.mPkLanguageTag;
        this.mPkLayoutType = inputMethodSubtypeBuilder.mPkLayoutType;
        this.mSubtypeIconResId = inputMethodSubtypeBuilder.mSubtypeIconResId;
        this.mSubtypeLocale = inputMethodSubtypeBuilder.mSubtypeLocale;
        this.mSubtypeLanguageTag = inputMethodSubtypeBuilder.mSubtypeLanguageTag;
        this.mSubtypeMode = inputMethodSubtypeBuilder.mSubtypeMode;
        this.mSubtypeExtraValue = inputMethodSubtypeBuilder.mSubtypeExtraValue;
        this.mIsAuxiliary = inputMethodSubtypeBuilder.mIsAuxiliary;
        this.mOverridesImplicitlyEnabledSubtype = inputMethodSubtypeBuilder.mOverridesImplicitlyEnabledSubtype;
        this.mSubtypeId = inputMethodSubtypeBuilder.mSubtypeId;
        this.mIsAsciiCapable = inputMethodSubtypeBuilder.mIsAsciiCapable;
        if (this.mSubtypeId != 0) {
            this.mSubtypeHashCode = this.mSubtypeId;
        } else {
            this.mSubtypeHashCode = hashCodeInternal(this.mSubtypeLocale, this.mSubtypeMode, this.mSubtypeExtraValue, this.mIsAuxiliary, this.mOverridesImplicitlyEnabledSubtype, this.mIsAsciiCapable);
        }
    }

    InputMethodSubtype(android.os.Parcel parcel) {
        this.mLock = new java.lang.Object();
        this.mSubtypeNameResId = parcel.readInt();
        java.lang.String createFromParcel = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mSubtypeNameOverride = createFromParcel == null ? "" : createFromParcel;
        java.lang.String readString8 = parcel.readString8();
        this.mPkLanguageTag = readString8 == null ? "" : readString8;
        java.lang.String readString82 = parcel.readString8();
        this.mPkLayoutType = readString82 == null ? "" : readString82;
        this.mSubtypeIconResId = parcel.readInt();
        java.lang.String readString = parcel.readString();
        this.mSubtypeLocale = readString == null ? "" : readString;
        java.lang.String readString2 = parcel.readString();
        this.mSubtypeLanguageTag = readString2 == null ? "" : readString2;
        java.lang.String readString3 = parcel.readString();
        this.mSubtypeMode = readString3 == null ? "" : readString3;
        java.lang.String readString4 = parcel.readString();
        this.mSubtypeExtraValue = readString4 != null ? readString4 : "";
        this.mIsAuxiliary = parcel.readInt() == 1;
        this.mOverridesImplicitlyEnabledSubtype = parcel.readInt() == 1;
        this.mSubtypeHashCode = parcel.readInt();
        this.mSubtypeId = parcel.readInt();
        this.mIsAsciiCapable = parcel.readInt() == 1;
    }

    public int getNameResId() {
        return this.mSubtypeNameResId;
    }

    public java.lang.CharSequence getNameOverride() {
        return this.mSubtypeNameOverride;
    }

    public android.icu.util.ULocale getPhysicalKeyboardHintLanguageTag() {
        if (android.text.TextUtils.isEmpty(this.mPkLanguageTag)) {
            return null;
        }
        return android.icu.util.ULocale.forLanguageTag(this.mPkLanguageTag);
    }

    public java.lang.String getPhysicalKeyboardHintLayoutType() {
        return this.mPkLayoutType;
    }

    public int getIconResId() {
        return this.mSubtypeIconResId;
    }

    @java.lang.Deprecated
    public java.lang.String getLocale() {
        return this.mSubtypeLocale;
    }

    public java.lang.String getLanguageTag() {
        return this.mSubtypeLanguageTag;
    }

    public java.util.Locale getLocaleObject() {
        if (this.mCachedLocaleObj != null) {
            return this.mCachedLocaleObj;
        }
        synchronized (this.mLock) {
            if (this.mCachedLocaleObj != null) {
                return this.mCachedLocaleObj;
            }
            if (!android.text.TextUtils.isEmpty(this.mSubtypeLanguageTag)) {
                this.mCachedLocaleObj = java.util.Locale.forLanguageTag(this.mSubtypeLanguageTag);
            } else {
                this.mCachedLocaleObj = com.android.internal.inputmethod.SubtypeLocaleUtils.constructLocaleFromString(this.mSubtypeLocale);
            }
            return this.mCachedLocaleObj;
        }
    }

    public java.lang.String getCanonicalizedLanguageTag() {
        java.lang.String str;
        java.lang.String str2 = this.mCachedCanonicalizedLanguageTag;
        if (str2 != null) {
            return str2;
        }
        java.util.Locale localeObject = getLocaleObject();
        if (localeObject != null) {
            java.lang.String languageTag = localeObject.toLanguageTag();
            if (!android.text.TextUtils.isEmpty(languageTag)) {
                str = android.icu.util.ULocale.createCanonical(android.icu.util.ULocale.forLanguageTag(languageTag)).toLanguageTag();
                java.lang.String emptyIfNull = android.text.TextUtils.emptyIfNull(str);
                this.mCachedCanonicalizedLanguageTag = emptyIfNull;
                return emptyIfNull;
            }
        }
        str = null;
        java.lang.String emptyIfNull2 = android.text.TextUtils.emptyIfNull(str);
        this.mCachedCanonicalizedLanguageTag = emptyIfNull2;
        return emptyIfNull2;
    }

    public boolean isSuitableForPhysicalKeyboardLayoutMapping() {
        if (hashCode() != 0 && android.text.TextUtils.equals(getMode(), SUBTYPE_MODE_KEYBOARD)) {
            return !isAuxiliary();
        }
        return false;
    }

    public java.lang.String getMode() {
        return this.mSubtypeMode;
    }

    public java.lang.String getExtraValue() {
        return this.mSubtypeExtraValue;
    }

    public boolean isAuxiliary() {
        return this.mIsAuxiliary;
    }

    public boolean overridesImplicitlyEnabledSubtype() {
        return this.mOverridesImplicitlyEnabledSubtype;
    }

    public boolean isAsciiCapable() {
        return this.mIsAsciiCapable;
    }

    public java.lang.CharSequence getDisplayName(android.content.Context context, java.lang.String str, android.content.pm.ApplicationInfo applicationInfo) {
        android.icu.text.DisplayContext displayContext;
        java.lang.String localeDisplayName;
        if (this.mSubtypeNameResId == 0) {
            if (android.text.TextUtils.isEmpty(this.mSubtypeNameOverride)) {
                return getLocaleDisplayName(getLocaleFromContext(context), getLocaleObject(), android.icu.text.DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU);
            }
            return this.mSubtypeNameOverride;
        }
        java.lang.CharSequence text = context.getPackageManager().getText(str, this.mSubtypeNameResId, applicationInfo);
        if (android.text.TextUtils.isEmpty(text)) {
            return "";
        }
        java.lang.String charSequence = text.toString();
        if (containsExtraValueKey(EXTRA_KEY_UNTRANSLATABLE_STRING_IN_SUBTYPE_NAME)) {
            localeDisplayName = getExtraValueOf(EXTRA_KEY_UNTRANSLATABLE_STRING_IN_SUBTYPE_NAME);
        } else {
            if (android.text.TextUtils.equals(charSequence, "%s")) {
                displayContext = android.icu.text.DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU;
            } else if (charSequence.startsWith("%s")) {
                displayContext = android.icu.text.DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE;
            } else {
                displayContext = android.icu.text.DisplayContext.CAPITALIZATION_FOR_MIDDLE_OF_SENTENCE;
            }
            localeDisplayName = getLocaleDisplayName(getLocaleFromContext(context), getLocaleObject(), displayContext);
        }
        if (localeDisplayName == null) {
            localeDisplayName = "";
        }
        try {
            return java.lang.String.format(charSequence, localeDisplayName);
        } catch (java.util.IllegalFormatException e) {
            android.util.Slog.w(TAG, "Found illegal format in subtype name(" + ((java.lang.Object) text) + "): " + e);
            return "";
        }
    }

    private static java.util.Locale getLocaleFromContext(android.content.Context context) {
        android.content.res.Configuration configuration;
        if (context == null || context.getResources() == null || (configuration = context.getResources().getConfiguration()) == null) {
            return null;
        }
        return configuration.getLocales().get(0);
    }

    private static java.lang.String getLocaleDisplayName(java.util.Locale locale, java.util.Locale locale2, android.icu.text.DisplayContext displayContext) {
        if (locale2 == null) {
            return "";
        }
        if (locale == null) {
            locale = java.util.Locale.getDefault();
        }
        return android.icu.text.LocaleDisplayNames.getInstance(locale, displayContext).localeDisplayName(locale2);
    }

    private java.util.HashMap<java.lang.String, java.lang.String> getExtraValueHashMap() {
        synchronized (this) {
            java.util.HashMap<java.lang.String, java.lang.String> hashMap = this.mExtraValueHashMapCache;
            if (hashMap != null) {
                return hashMap;
            }
            java.util.HashMap<java.lang.String, java.lang.String> hashMap2 = new java.util.HashMap<>();
            for (java.lang.String str : this.mSubtypeExtraValue.split(",")) {
                java.lang.String[] split = str.split(EXTRA_VALUE_KEY_VALUE_SEPARATOR);
                if (split.length == 1) {
                    hashMap2.put(split[0], null);
                } else if (split.length > 1) {
                    if (split.length > 2) {
                        android.util.Slog.w(TAG, "ExtraValue has two or more '='s");
                    }
                    hashMap2.put(split[0], split[1]);
                }
            }
            this.mExtraValueHashMapCache = hashMap2;
            return hashMap2;
        }
    }

    public boolean containsExtraValueKey(java.lang.String str) {
        return getExtraValueHashMap().containsKey(str);
    }

    public java.lang.String getExtraValueOf(java.lang.String str) {
        return getExtraValueHashMap().get(str);
    }

    public int hashCode() {
        return this.mSubtypeHashCode;
    }

    public final boolean hasSubtypeId() {
        return this.mSubtypeId != 0;
    }

    public final int getSubtypeId() {
        return this.mSubtypeId;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.view.inputmethod.InputMethodSubtype)) {
            return false;
        }
        android.view.inputmethod.InputMethodSubtype inputMethodSubtype = (android.view.inputmethod.InputMethodSubtype) obj;
        return (inputMethodSubtype.mSubtypeId == 0 && this.mSubtypeId == 0) ? inputMethodSubtype.hashCode() == hashCode() && inputMethodSubtype.getLocale().equals(getLocale()) && inputMethodSubtype.getLanguageTag().equals(getLanguageTag()) && inputMethodSubtype.getMode().equals(getMode()) && inputMethodSubtype.getExtraValue().equals(getExtraValue()) && inputMethodSubtype.isAuxiliary() == isAuxiliary() && inputMethodSubtype.overridesImplicitlyEnabledSubtype() == overridesImplicitlyEnabledSubtype() && inputMethodSubtype.isAsciiCapable() == isAsciiCapable() : inputMethodSubtype.hashCode() == hashCode();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mSubtypeNameResId);
        android.text.TextUtils.writeToParcel(this.mSubtypeNameOverride, parcel, i);
        parcel.writeString8(this.mPkLanguageTag);
        parcel.writeString8(this.mPkLayoutType);
        parcel.writeInt(this.mSubtypeIconResId);
        parcel.writeString(this.mSubtypeLocale);
        parcel.writeString(this.mSubtypeLanguageTag);
        parcel.writeString(this.mSubtypeMode);
        parcel.writeString(this.mSubtypeExtraValue);
        parcel.writeInt(this.mIsAuxiliary ? 1 : 0);
        parcel.writeInt(this.mOverridesImplicitlyEnabledSubtype ? 1 : 0);
        parcel.writeInt(this.mSubtypeHashCode);
        parcel.writeInt(this.mSubtypeId);
        parcel.writeInt(this.mIsAsciiCapable ? 1 : 0);
    }

    private static int hashCodeInternal(java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z, boolean z2, boolean z3) {
        if (!z3) {
            return java.util.Arrays.hashCode(new java.lang.Object[]{str, str2, str3, java.lang.Boolean.valueOf(z), java.lang.Boolean.valueOf(z2)});
        }
        return java.util.Arrays.hashCode(new java.lang.Object[]{str, str2, str3, java.lang.Boolean.valueOf(z), java.lang.Boolean.valueOf(z2), java.lang.Boolean.valueOf(z3)});
    }

    public static java.util.List<android.view.inputmethod.InputMethodSubtype> sort(android.view.inputmethod.InputMethodInfo inputMethodInfo, java.util.List<android.view.inputmethod.InputMethodSubtype> list) {
        if (inputMethodInfo == null) {
            return list;
        }
        java.util.HashSet hashSet = new java.util.HashSet(list);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int subtypeCount = inputMethodInfo.getSubtypeCount();
        for (int i = 0; i < subtypeCount; i++) {
            android.view.inputmethod.InputMethodSubtype subtypeAt = inputMethodInfo.getSubtypeAt(i);
            if (hashSet.contains(subtypeAt)) {
                arrayList.add(subtypeAt);
                hashSet.remove(subtypeAt);
            }
        }
        java.util.Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            arrayList.add((android.view.inputmethod.InputMethodSubtype) it.next());
        }
        return arrayList;
    }
}
