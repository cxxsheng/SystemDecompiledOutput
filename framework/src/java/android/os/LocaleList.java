package android.os;

/* loaded from: classes3.dex */
public final class LocaleList implements android.os.Parcelable {
    private static final int NUM_PSEUDO_LOCALES = 2;
    private static final java.lang.String STRING_AR_XB = "ar-XB";
    private static final java.lang.String STRING_EN_XA = "en-XA";
    private final java.util.Locale[] mList;
    private final java.lang.String mStringRepresentation;
    private static final java.util.Locale[] sEmptyList = new java.util.Locale[0];
    private static final android.os.LocaleList sEmptyLocaleList = new android.os.LocaleList(new java.util.Locale[0]);
    public static final android.os.Parcelable.Creator<android.os.LocaleList> CREATOR = new android.os.Parcelable.Creator<android.os.LocaleList>() { // from class: android.os.LocaleList.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.LocaleList createFromParcel(android.os.Parcel parcel) {
            return android.os.LocaleList.forLanguageTags(parcel.readString8());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.LocaleList[] newArray(int i) {
            return new android.os.LocaleList[i];
        }
    };
    private static final java.util.Locale LOCALE_EN_XA = new java.util.Locale("en", "XA");
    private static final java.util.Locale LOCALE_AR_XB = new java.util.Locale("ar", "XB");
    private static final java.util.Locale EN_LATN = java.util.Locale.forLanguageTag("en-Latn");
    private static final java.lang.Object sLock = new java.lang.Object();
    private static android.os.LocaleList sLastExplicitlySetLocaleList = null;
    private static android.os.LocaleList sDefaultLocaleList = null;
    private static android.os.LocaleList sDefaultAdjustedLocaleList = null;
    private static java.util.Locale sLastDefaultLocale = null;

    public java.util.Locale get(int i) {
        if (i < 0 || i >= this.mList.length) {
            return null;
        }
        return this.mList[i];
    }

    public boolean isEmpty() {
        return this.mList.length == 0;
    }

    public int size() {
        return this.mList.length;
    }

    public int indexOf(java.util.Locale locale) {
        for (int i = 0; i < this.mList.length; i++) {
            if (this.mList[i].equals(locale)) {
                return i;
            }
        }
        return -1;
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof android.os.LocaleList)) {
            return false;
        }
        java.util.Locale[] localeArr = ((android.os.LocaleList) obj).mList;
        if (this.mList.length != localeArr.length) {
            return false;
        }
        for (int i = 0; i < this.mList.length; i++) {
            if (!this.mList[i].equals(localeArr[i])) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.mList.length; i2++) {
            i = (i * 31) + this.mList[i2].hashCode();
        }
        return i;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START);
        for (int i = 0; i < this.mList.length; i++) {
            sb.append(this.mList[i]);
            if (i < this.mList.length - 1) {
                sb.append(',');
            }
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString8(this.mStringRepresentation);
    }

    public java.lang.String toLanguageTags() {
        return this.mStringRepresentation;
    }

    public java.util.Locale[] getIntersection(android.os.LocaleList localeList) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (java.util.Locale locale : this.mList) {
            java.util.Locale[] localeArr = localeList.mList;
            int length = localeArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                if (!matchesLanguageAndScript(localeArr[i], locale)) {
                    i++;
                } else {
                    arrayList.add(locale);
                    break;
                }
            }
        }
        return (java.util.Locale[]) arrayList.toArray(new java.util.Locale[0]);
    }

    public LocaleList(java.util.Locale... localeArr) {
        if (localeArr.length == 0) {
            this.mList = sEmptyList;
            this.mStringRepresentation = "";
            return;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.HashSet hashSet = new java.util.HashSet();
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (int i = 0; i < localeArr.length; i++) {
            java.util.Locale locale = localeArr[i];
            if (locale == null) {
                throw new java.lang.NullPointerException("list[" + i + "] is null");
            }
            if (!hashSet.contains(locale)) {
                java.util.Locale locale2 = (java.util.Locale) locale.clone();
                arrayList.add(locale2);
                sb.append(locale2.toLanguageTag());
                if (i < localeArr.length - 1) {
                    sb.append(',');
                }
                hashSet.add(locale2);
            }
        }
        this.mList = (java.util.Locale[]) arrayList.toArray(new java.util.Locale[arrayList.size()]);
        this.mStringRepresentation = sb.toString();
    }

    public LocaleList(java.util.Locale locale, android.os.LocaleList localeList) {
        int i;
        if (locale == null) {
            throw new java.lang.NullPointerException("topLocale is null");
        }
        int length = localeList == null ? 0 : localeList.mList.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                i2 = -1;
                break;
            } else if (locale.equals(localeList.mList[i2])) {
                break;
            } else {
                i2++;
            }
        }
        if (i2 != -1) {
            i = 0;
        } else {
            i = 1;
        }
        int i3 = i + length;
        java.util.Locale[] localeArr = new java.util.Locale[i3];
        localeArr[0] = (java.util.Locale) locale.clone();
        if (i2 == -1) {
            int i4 = 0;
            while (i4 < length) {
                int i5 = i4 + 1;
                localeArr[i5] = (java.util.Locale) localeList.mList[i4].clone();
                i4 = i5;
            }
        } else {
            int i6 = 0;
            while (i6 < i2) {
                int i7 = i6 + 1;
                localeArr[i7] = (java.util.Locale) localeList.mList[i6].clone();
                i6 = i7;
            }
            for (int i8 = i2 + 1; i8 < length; i8++) {
                localeArr[i8] = (java.util.Locale) localeList.mList[i8].clone();
            }
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (int i9 = 0; i9 < i3; i9++) {
            sb.append(localeArr[i9].toLanguageTag());
            if (i9 < i3 - 1) {
                sb.append(',');
            }
        }
        this.mList = localeArr;
        this.mStringRepresentation = sb.toString();
    }

    public static android.os.LocaleList getEmptyLocaleList() {
        return sEmptyLocaleList;
    }

    public static android.os.LocaleList forLanguageTags(java.lang.String str) {
        if (str == null || str.equals("")) {
            return getEmptyLocaleList();
        }
        java.lang.String[] split = str.split(",");
        int length = split.length;
        java.util.Locale[] localeArr = new java.util.Locale[length];
        for (int i = 0; i < length; i++) {
            localeArr[i] = java.util.Locale.forLanguageTag(split[i]);
        }
        return new android.os.LocaleList(localeArr);
    }

    private static java.lang.String getLikelyScript(java.util.Locale locale) {
        java.lang.String script = locale.getScript();
        if (!script.isEmpty()) {
            return script;
        }
        return android.icu.util.ULocale.addLikelySubtags(android.icu.util.ULocale.forLocale(locale)).getScript();
    }

    private static boolean isPseudoLocale(java.lang.String str) {
        return STRING_EN_XA.equals(str) || STRING_AR_XB.equals(str);
    }

    public static boolean isPseudoLocale(java.util.Locale locale) {
        return LOCALE_EN_XA.equals(locale) || LOCALE_AR_XB.equals(locale);
    }

    public static boolean isPseudoLocale(android.icu.util.ULocale uLocale) {
        return isPseudoLocale(uLocale != null ? uLocale.toLocale() : null);
    }

    public static boolean matchesLanguageAndScript(java.util.Locale locale, java.util.Locale locale2) {
        if (locale.equals(locale2)) {
            return true;
        }
        if (!locale.getLanguage().equals(locale2.getLanguage()) || isPseudoLocale(locale) || isPseudoLocale(locale2)) {
            return false;
        }
        java.lang.String likelyScript = getLikelyScript(locale);
        if (likelyScript.isEmpty()) {
            java.lang.String country = locale.getCountry();
            return country.isEmpty() || country.equals(locale2.getCountry());
        }
        return likelyScript.equals(getLikelyScript(locale2));
    }

    private int findFirstMatchIndex(java.util.Locale locale) {
        for (int i = 0; i < this.mList.length; i++) {
            if (matchesLanguageAndScript(locale, this.mList[i])) {
                return i;
            }
        }
        return Integer.MAX_VALUE;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x001e, code lost:
    
        if (r5 < Integer.MAX_VALUE) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int computeFirstMatchIndex(java.util.Collection<java.lang.String> collection, boolean z) {
        int i;
        if (this.mList.length == 1) {
            return 0;
        }
        if (this.mList.length == 0) {
            return -1;
        }
        if (z) {
            i = findFirstMatchIndex(EN_LATN);
            if (i == 0) {
                return 0;
            }
        }
        i = Integer.MAX_VALUE;
        java.util.Iterator<java.lang.String> it = collection.iterator();
        while (it.hasNext()) {
            int findFirstMatchIndex = findFirstMatchIndex(java.util.Locale.forLanguageTag(it.next()));
            if (findFirstMatchIndex == 0) {
                return 0;
            }
            if (findFirstMatchIndex < i) {
                i = findFirstMatchIndex;
            }
        }
        if (i == Integer.MAX_VALUE) {
            return 0;
        }
        return i;
    }

    private java.util.Locale computeFirstMatch(java.util.Collection<java.lang.String> collection, boolean z) {
        int computeFirstMatchIndex = computeFirstMatchIndex(collection, z);
        if (computeFirstMatchIndex == -1) {
            return null;
        }
        return this.mList[computeFirstMatchIndex];
    }

    public java.util.Locale getFirstMatch(java.lang.String[] strArr) {
        return computeFirstMatch(java.util.Arrays.asList(strArr), false);
    }

    public int getFirstMatchIndex(java.lang.String[] strArr) {
        return computeFirstMatchIndex(java.util.Arrays.asList(strArr), false);
    }

    public java.util.Locale getFirstMatchWithEnglishSupported(java.lang.String[] strArr) {
        return computeFirstMatch(java.util.Arrays.asList(strArr), true);
    }

    public int getFirstMatchIndexWithEnglishSupported(java.util.Collection<java.lang.String> collection) {
        return computeFirstMatchIndex(collection, true);
    }

    public int getFirstMatchIndexWithEnglishSupported(java.lang.String[] strArr) {
        return getFirstMatchIndexWithEnglishSupported(java.util.Arrays.asList(strArr));
    }

    public static boolean isPseudoLocalesOnly(java.lang.String[] strArr) {
        if (strArr == null) {
            return true;
        }
        if (strArr.length > 3) {
            return false;
        }
        for (java.lang.String str : strArr) {
            if (!str.isEmpty() && !isPseudoLocale(str)) {
                return false;
            }
        }
        return true;
    }

    public static android.os.LocaleList getDefault() {
        java.util.Locale locale = java.util.Locale.getDefault();
        synchronized (sLock) {
            if (!locale.equals(sLastDefaultLocale)) {
                sLastDefaultLocale = locale;
                if (sDefaultLocaleList != null && locale.equals(sDefaultLocaleList.get(0))) {
                    return sDefaultLocaleList;
                }
                sDefaultLocaleList = new android.os.LocaleList(locale, sLastExplicitlySetLocaleList);
                sDefaultAdjustedLocaleList = sDefaultLocaleList;
            }
            return sDefaultLocaleList;
        }
    }

    public static android.os.LocaleList getAdjustedDefault() {
        android.os.LocaleList localeList;
        getDefault();
        synchronized (sLock) {
            localeList = sDefaultAdjustedLocaleList;
        }
        return localeList;
    }

    public static void setDefault(android.os.LocaleList localeList) {
        setDefault(localeList, 0);
    }

    public static void setDefault(android.os.LocaleList localeList, int i) {
        if (localeList == null) {
            throw new java.lang.NullPointerException("locales is null");
        }
        if (localeList.isEmpty()) {
            throw new java.lang.IllegalArgumentException("locales is empty");
        }
        synchronized (sLock) {
            sLastDefaultLocale = localeList.get(i);
            java.util.Locale.setDefault(sLastDefaultLocale);
            sLastExplicitlySetLocaleList = localeList;
            sDefaultLocaleList = localeList;
            if (i == 0) {
                sDefaultAdjustedLocaleList = sDefaultLocaleList;
            } else {
                sDefaultAdjustedLocaleList = new android.os.LocaleList(sLastDefaultLocale, sDefaultLocaleList);
            }
        }
    }
}
