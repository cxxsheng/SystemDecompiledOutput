package android.view.textservice;

/* loaded from: classes4.dex */
public final class SpellCheckerSubtype implements android.os.Parcelable {
    private static final java.lang.String EXTRA_VALUE_KEY_VALUE_SEPARATOR = "=";
    private static final java.lang.String EXTRA_VALUE_PAIR_SEPARATOR = ",";
    public static final int SUBTYPE_ID_NONE = 0;
    private static final java.lang.String SUBTYPE_LANGUAGE_TAG_NONE = "";
    private java.util.HashMap<java.lang.String, java.lang.String> mExtraValueHashMapCache;
    private final java.lang.String mSubtypeExtraValue;
    private final int mSubtypeHashCode;
    private final int mSubtypeId;
    private final java.lang.String mSubtypeLanguageTag;
    private final java.lang.String mSubtypeLocale;
    private final int mSubtypeNameResId;
    private static final java.lang.String TAG = android.view.textservice.SpellCheckerSubtype.class.getSimpleName();
    public static final android.os.Parcelable.Creator<android.view.textservice.SpellCheckerSubtype> CREATOR = new android.os.Parcelable.Creator<android.view.textservice.SpellCheckerSubtype>() { // from class: android.view.textservice.SpellCheckerSubtype.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.textservice.SpellCheckerSubtype createFromParcel(android.os.Parcel parcel) {
            return new android.view.textservice.SpellCheckerSubtype(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.textservice.SpellCheckerSubtype[] newArray(int i) {
            return new android.view.textservice.SpellCheckerSubtype[i];
        }
    };

    public SpellCheckerSubtype(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, int i2) {
        this.mSubtypeNameResId = i;
        this.mSubtypeLocale = str == null ? "" : str;
        this.mSubtypeLanguageTag = str2 == null ? "" : str2;
        this.mSubtypeExtraValue = str3 == null ? "" : str3;
        this.mSubtypeId = i2;
        this.mSubtypeHashCode = this.mSubtypeId != 0 ? this.mSubtypeId : hashCodeInternal(this.mSubtypeLocale, this.mSubtypeExtraValue);
    }

    @java.lang.Deprecated
    public SpellCheckerSubtype(int i, java.lang.String str, java.lang.String str2) {
        this(i, str, "", str2, 0);
    }

    SpellCheckerSubtype(android.os.Parcel parcel) {
        this.mSubtypeNameResId = parcel.readInt();
        java.lang.String readString = parcel.readString();
        this.mSubtypeLocale = readString == null ? "" : readString;
        java.lang.String readString2 = parcel.readString();
        this.mSubtypeLanguageTag = readString2 == null ? "" : readString2;
        java.lang.String readString3 = parcel.readString();
        this.mSubtypeExtraValue = readString3 != null ? readString3 : "";
        this.mSubtypeId = parcel.readInt();
        this.mSubtypeHashCode = this.mSubtypeId != 0 ? this.mSubtypeId : hashCodeInternal(this.mSubtypeLocale, this.mSubtypeExtraValue);
    }

    public int getNameResId() {
        return this.mSubtypeNameResId;
    }

    @java.lang.Deprecated
    public java.lang.String getLocale() {
        return this.mSubtypeLocale;
    }

    public java.lang.String getLanguageTag() {
        return this.mSubtypeLanguageTag;
    }

    public java.lang.String getExtraValue() {
        return this.mSubtypeExtraValue;
    }

    private java.util.HashMap<java.lang.String, java.lang.String> getExtraValueHashMap() {
        if (this.mExtraValueHashMapCache == null) {
            this.mExtraValueHashMapCache = new java.util.HashMap<>();
            for (java.lang.String str : this.mSubtypeExtraValue.split(",")) {
                java.lang.String[] split = str.split(EXTRA_VALUE_KEY_VALUE_SEPARATOR);
                if (split.length == 1) {
                    this.mExtraValueHashMapCache.put(split[0], null);
                } else if (split.length > 1) {
                    if (split.length > 2) {
                        android.util.Slog.w(TAG, "ExtraValue has two or more '='s");
                    }
                    this.mExtraValueHashMapCache.put(split[0], split[1]);
                }
            }
        }
        return this.mExtraValueHashMapCache;
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

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.view.textservice.SpellCheckerSubtype)) {
            return false;
        }
        android.view.textservice.SpellCheckerSubtype spellCheckerSubtype = (android.view.textservice.SpellCheckerSubtype) obj;
        return (spellCheckerSubtype.mSubtypeId == 0 && this.mSubtypeId == 0) ? spellCheckerSubtype.hashCode() == hashCode() && spellCheckerSubtype.getNameResId() == getNameResId() && spellCheckerSubtype.getLocale().equals(getLocale()) && spellCheckerSubtype.getLanguageTag().equals(getLanguageTag()) && spellCheckerSubtype.getExtraValue().equals(getExtraValue()) : spellCheckerSubtype.hashCode() == hashCode();
    }

    public java.util.Locale getLocaleObject() {
        if (!android.text.TextUtils.isEmpty(this.mSubtypeLanguageTag)) {
            return java.util.Locale.forLanguageTag(this.mSubtypeLanguageTag);
        }
        return com.android.internal.inputmethod.SubtypeLocaleUtils.constructLocaleFromString(this.mSubtypeLocale);
    }

    public java.lang.CharSequence getDisplayName(android.content.Context context, java.lang.String str, android.content.pm.ApplicationInfo applicationInfo) {
        java.util.Locale localeObject = getLocaleObject();
        java.lang.String displayName = localeObject != null ? localeObject.getDisplayName() : this.mSubtypeLocale;
        if (this.mSubtypeNameResId == 0) {
            return displayName;
        }
        java.lang.CharSequence text = context.getPackageManager().getText(str, this.mSubtypeNameResId, applicationInfo);
        if (!android.text.TextUtils.isEmpty(text)) {
            return java.lang.String.format(text.toString(), displayName);
        }
        return displayName;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mSubtypeNameResId);
        parcel.writeString(this.mSubtypeLocale);
        parcel.writeString(this.mSubtypeLanguageTag);
        parcel.writeString(this.mSubtypeExtraValue);
        parcel.writeInt(this.mSubtypeId);
    }

    private static int hashCodeInternal(java.lang.String str, java.lang.String str2) {
        return java.util.Arrays.hashCode(new java.lang.Object[]{str, str2});
    }

    public static java.util.List<android.view.textservice.SpellCheckerSubtype> sort(android.content.Context context, int i, android.view.textservice.SpellCheckerInfo spellCheckerInfo, java.util.List<android.view.textservice.SpellCheckerSubtype> list) {
        if (spellCheckerInfo == null) {
            return list;
        }
        java.util.HashSet hashSet = new java.util.HashSet(list);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int subtypeCount = spellCheckerInfo.getSubtypeCount();
        for (int i2 = 0; i2 < subtypeCount; i2++) {
            android.view.textservice.SpellCheckerSubtype subtypeAt = spellCheckerInfo.getSubtypeAt(i2);
            if (hashSet.contains(subtypeAt)) {
                arrayList.add(subtypeAt);
                hashSet.remove(subtypeAt);
            }
        }
        java.util.Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            arrayList.add((android.view.textservice.SpellCheckerSubtype) it.next());
        }
        return arrayList;
    }
}
