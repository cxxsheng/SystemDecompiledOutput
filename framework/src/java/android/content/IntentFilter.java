package android.content;

/* loaded from: classes.dex */
public class IntentFilter implements android.os.Parcelable {
    private static final java.lang.String ACTION_STR = "action";
    private static final java.lang.String AGLOB_STR = "aglob";
    private static final java.lang.String AUTH_STR = "auth";
    private static final java.lang.String AUTO_VERIFY_STR = "autoVerify";
    private static final java.lang.String CAT_STR = "cat";
    private static final java.lang.String EXTRAS_STR = "extras";
    private static final java.lang.String GROUP_STR = "group";
    private static final java.lang.String HOST_STR = "host";
    private static final java.lang.String LITERAL_STR = "literal";
    public static final int MATCH_ADJUSTMENT_MASK = 65535;
    public static final int MATCH_ADJUSTMENT_NORMAL = 32768;
    public static final int MATCH_CATEGORY_EMPTY = 1048576;
    public static final int MATCH_CATEGORY_HOST = 3145728;
    public static final int MATCH_CATEGORY_MASK = 268369920;
    public static final int MATCH_CATEGORY_PATH = 5242880;
    public static final int MATCH_CATEGORY_PORT = 4194304;
    public static final int MATCH_CATEGORY_SCHEME = 2097152;
    public static final int MATCH_CATEGORY_SCHEME_SPECIFIC_PART = 5767168;
    public static final int MATCH_CATEGORY_TYPE = 6291456;
    private static final java.lang.String NAME_STR = "name";
    public static final int NO_MATCH_ACTION = -3;
    public static final int NO_MATCH_CATEGORY = -4;
    public static final int NO_MATCH_DATA = -2;
    public static final int NO_MATCH_EXTRAS = -5;
    public static final int NO_MATCH_TYPE = -1;
    private static final java.lang.String PATH_STR = "path";
    private static final java.lang.String PORT_STR = "port";
    private static final java.lang.String PREFIX_STR = "prefix";
    public static final java.lang.String SCHEME_HTTP = "http";
    public static final java.lang.String SCHEME_HTTPS = "https";
    public static final java.lang.String SCHEME_PACKAGE = "package";
    private static final java.lang.String SCHEME_STR = "scheme";
    private static final java.lang.String SGLOB_STR = "sglob";
    private static final java.lang.String SSP_STR = "ssp";
    private static final int STATE_NEED_VERIFY = 16;
    private static final int STATE_NEED_VERIFY_CHECKED = 256;
    private static final int STATE_VERIFIED = 4096;
    private static final int STATE_VERIFY_AUTO = 1;
    private static final java.lang.String STATIC_TYPE_STR = "staticType";
    private static final java.lang.String SUFFIX_STR = "suffix";
    public static final int SYSTEM_HIGH_PRIORITY = 1000;
    public static final int SYSTEM_LOW_PRIORITY = -1000;
    private static final java.lang.String TAG = "IntentFilter";
    private static final java.lang.String TYPE_STR = "type";
    private static final java.lang.String URI_RELATIVE_FILTER_GROUP_STR = "uriRelativeFilterGroup";
    public static final int VISIBILITY_EXPLICIT = 1;
    public static final int VISIBILITY_IMPLICIT = 2;
    public static final int VISIBILITY_NONE = 0;
    public static final java.lang.String WILDCARD = "*";
    public static final java.lang.String WILDCARD_PATH = "/*";
    private final android.util.ArraySet<java.lang.String> mActions;
    private java.util.ArrayList<java.lang.String> mCategories;
    private java.util.ArrayList<android.content.IntentFilter.AuthorityEntry> mDataAuthorities;
    private java.util.ArrayList<android.os.PatternMatcher> mDataPaths;
    private java.util.ArrayList<android.os.PatternMatcher> mDataSchemeSpecificParts;
    private java.util.ArrayList<java.lang.String> mDataSchemes;
    private java.util.ArrayList<java.lang.String> mDataTypes;
    private android.os.PersistableBundle mExtras;
    private boolean mHasDynamicPartialTypes;
    private boolean mHasStaticPartialTypes;
    private int mInstantAppVisibility;
    private java.util.ArrayList<java.lang.String> mMimeGroups;
    private int mOrder;
    private int mPriority;
    private java.util.ArrayList<java.lang.String> mStaticDataTypes;
    private java.util.ArrayList<android.content.UriRelativeFilterGroup> mUriRelativeFilterGroups;
    private int mVerifyState;
    private static final int[] EMPTY_INT_ARRAY = new int[0];
    private static final long[] EMPTY_LONG_ARRAY = new long[0];
    private static final double[] EMPTY_DOUBLE_ARRAY = new double[0];
    private static final java.lang.String[] EMPTY_STRING_ARRAY = new java.lang.String[0];
    private static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];
    public static final android.os.Parcelable.Creator<android.content.IntentFilter> CREATOR = new android.os.Parcelable.Creator<android.content.IntentFilter>() { // from class: android.content.IntentFilter.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.IntentFilter createFromParcel(android.os.Parcel parcel) {
            return new android.content.IntentFilter(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.IntentFilter[] newArray(int i) {
            return new android.content.IntentFilter[i];
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface InstantAppVisibility {
    }

    private static int findStringInSet(java.lang.String[] strArr, java.lang.String str, int[] iArr, int i) {
        if (strArr == null) {
            return -1;
        }
        int i2 = iArr[i];
        for (int i3 = 0; i3 < i2; i3++) {
            if (strArr[i3].equals(str)) {
                return i3;
            }
        }
        return -1;
    }

    private static java.lang.String[] addStringToSet(java.lang.String[] strArr, java.lang.String str, int[] iArr, int i) {
        if (findStringInSet(strArr, str, iArr, i) >= 0) {
            return strArr;
        }
        if (strArr == null) {
            java.lang.String[] strArr2 = new java.lang.String[2];
            strArr2[0] = str;
            iArr[i] = 1;
            return strArr2;
        }
        int i2 = iArr[i];
        if (i2 < strArr.length) {
            strArr[i2] = str;
            iArr[i] = i2 + 1;
            return strArr;
        }
        java.lang.String[] strArr3 = new java.lang.String[((i2 * 3) / 2) + 2];
        java.lang.System.arraycopy(strArr, 0, strArr3, 0, i2);
        strArr3[i2] = str;
        iArr[i] = i2 + 1;
        return strArr3;
    }

    private static java.lang.String[] removeStringFromSet(java.lang.String[] strArr, java.lang.String str, int[] iArr, int i) {
        int findStringInSet = findStringInSet(strArr, str, iArr, i);
        if (findStringInSet < 0) {
            return strArr;
        }
        int i2 = iArr[i];
        if (i2 > strArr.length / 4) {
            int i3 = findStringInSet + 1;
            int i4 = i2 - i3;
            if (i4 > 0) {
                java.lang.System.arraycopy(strArr, i3, strArr, findStringInSet, i4);
            }
            int i5 = i2 - 1;
            strArr[i5] = null;
            iArr[i] = i5;
            return strArr;
        }
        java.lang.String[] strArr2 = new java.lang.String[strArr.length / 3];
        if (findStringInSet > 0) {
            java.lang.System.arraycopy(strArr, 0, strArr2, 0, findStringInSet);
        }
        int i6 = findStringInSet + 1;
        if (i6 < i2) {
            java.lang.System.arraycopy(strArr, i6, strArr2, findStringInSet, i2 - i6);
        }
        return strArr2;
    }

    public static class MalformedMimeTypeException extends android.util.AndroidException {
        public MalformedMimeTypeException() {
        }

        public MalformedMimeTypeException(java.lang.String str) {
            super(str);
        }
    }

    public static android.content.IntentFilter create(java.lang.String str, java.lang.String str2) {
        try {
            return new android.content.IntentFilter(str, str2);
        } catch (android.content.IntentFilter.MalformedMimeTypeException e) {
            throw new java.lang.RuntimeException("Bad MIME type", e);
        }
    }

    public IntentFilter() {
        this.mCategories = null;
        this.mDataSchemes = null;
        this.mDataSchemeSpecificParts = null;
        this.mDataAuthorities = null;
        this.mDataPaths = null;
        this.mUriRelativeFilterGroups = null;
        this.mStaticDataTypes = null;
        this.mDataTypes = null;
        this.mMimeGroups = null;
        this.mHasStaticPartialTypes = false;
        this.mHasDynamicPartialTypes = false;
        this.mExtras = null;
        this.mPriority = 0;
        this.mActions = new android.util.ArraySet<>();
    }

    public IntentFilter(java.lang.String str) {
        this.mCategories = null;
        this.mDataSchemes = null;
        this.mDataSchemeSpecificParts = null;
        this.mDataAuthorities = null;
        this.mDataPaths = null;
        this.mUriRelativeFilterGroups = null;
        this.mStaticDataTypes = null;
        this.mDataTypes = null;
        this.mMimeGroups = null;
        this.mHasStaticPartialTypes = false;
        this.mHasDynamicPartialTypes = false;
        this.mExtras = null;
        this.mPriority = 0;
        this.mActions = new android.util.ArraySet<>();
        addAction(str);
    }

    public IntentFilter(java.lang.String str, java.lang.String str2) throws android.content.IntentFilter.MalformedMimeTypeException {
        this.mCategories = null;
        this.mDataSchemes = null;
        this.mDataSchemeSpecificParts = null;
        this.mDataAuthorities = null;
        this.mDataPaths = null;
        this.mUriRelativeFilterGroups = null;
        this.mStaticDataTypes = null;
        this.mDataTypes = null;
        this.mMimeGroups = null;
        this.mHasStaticPartialTypes = false;
        this.mHasDynamicPartialTypes = false;
        this.mExtras = null;
        this.mPriority = 0;
        this.mActions = new android.util.ArraySet<>();
        addAction(str);
        addDataType(str2);
    }

    public IntentFilter(android.content.IntentFilter intentFilter) {
        this.mCategories = null;
        this.mDataSchemes = null;
        this.mDataSchemeSpecificParts = null;
        this.mDataAuthorities = null;
        this.mDataPaths = null;
        this.mUriRelativeFilterGroups = null;
        this.mStaticDataTypes = null;
        this.mDataTypes = null;
        this.mMimeGroups = null;
        this.mHasStaticPartialTypes = false;
        this.mHasDynamicPartialTypes = false;
        this.mExtras = null;
        this.mPriority = intentFilter.mPriority;
        this.mOrder = intentFilter.mOrder;
        this.mActions = new android.util.ArraySet<>((android.util.ArraySet) intentFilter.mActions);
        if (intentFilter.mCategories != null) {
            this.mCategories = new java.util.ArrayList<>(intentFilter.mCategories);
        }
        if (intentFilter.mStaticDataTypes != null) {
            this.mStaticDataTypes = new java.util.ArrayList<>(intentFilter.mStaticDataTypes);
        }
        if (intentFilter.mDataTypes != null) {
            this.mDataTypes = new java.util.ArrayList<>(intentFilter.mDataTypes);
        }
        if (intentFilter.mDataSchemes != null) {
            this.mDataSchemes = new java.util.ArrayList<>(intentFilter.mDataSchemes);
        }
        if (intentFilter.mDataSchemeSpecificParts != null) {
            this.mDataSchemeSpecificParts = new java.util.ArrayList<>(intentFilter.mDataSchemeSpecificParts);
        }
        if (intentFilter.mDataAuthorities != null) {
            this.mDataAuthorities = new java.util.ArrayList<>(intentFilter.mDataAuthorities);
        }
        if (intentFilter.mDataPaths != null) {
            this.mDataPaths = new java.util.ArrayList<>(intentFilter.mDataPaths);
        }
        if (intentFilter.mUriRelativeFilterGroups != null) {
            this.mUriRelativeFilterGroups = new java.util.ArrayList<>(intentFilter.mUriRelativeFilterGroups);
        }
        if (intentFilter.mMimeGroups != null) {
            this.mMimeGroups = new java.util.ArrayList<>(intentFilter.mMimeGroups);
        }
        if (intentFilter.mExtras != null) {
            this.mExtras = new android.os.PersistableBundle(intentFilter.mExtras);
        }
        this.mHasStaticPartialTypes = intentFilter.mHasStaticPartialTypes;
        this.mHasDynamicPartialTypes = intentFilter.mHasDynamicPartialTypes;
        this.mVerifyState = intentFilter.mVerifyState;
        this.mInstantAppVisibility = intentFilter.mInstantAppVisibility;
    }

    public java.lang.String toLongString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("IntentFilter {");
        sb.append(" pri=");
        sb.append(this.mPriority);
        if (countActions() > 0) {
            sb.append(" act=");
            sb.append(this.mActions.toString());
        }
        if (countCategories() > 0) {
            sb.append(" cat=");
            sb.append(this.mCategories.toString());
        }
        if (countDataSchemes() > 0) {
            sb.append(" sch=");
            sb.append(this.mDataSchemes.toString());
        }
        if (android.content.pm.Flags.relativeReferenceIntentFilters() && countUriRelativeFilterGroups() > 0) {
            sb.append(" grp=");
            sb.append(this.mUriRelativeFilterGroups.toString());
        }
        sb.append(" }");
        return sb.toString();
    }

    public final void setPriority(int i) {
        this.mPriority = i;
    }

    public final int getPriority() {
        return this.mPriority;
    }

    @android.annotation.SystemApi
    public final void setOrder(int i) {
        this.mOrder = i;
    }

    @android.annotation.SystemApi
    public final int getOrder() {
        return this.mOrder;
    }

    public final void setAutoVerify(boolean z) {
        this.mVerifyState &= -2;
        if (z) {
            this.mVerifyState |= 1;
        }
    }

    public final boolean getAutoVerify() {
        return (this.mVerifyState & 1) == 1;
    }

    public final boolean handleAllWebDataURI() {
        return hasCategory(android.content.Intent.CATEGORY_APP_BROWSER) || (handlesWebUris(false) && countDataAuthorities() == 0);
    }

    public final boolean handlesWebUris(boolean z) {
        if (!hasAction("android.intent.action.VIEW") || !hasCategory(android.content.Intent.CATEGORY_BROWSABLE) || this.mDataSchemes == null || this.mDataSchemes.size() == 0) {
            return false;
        }
        int size = this.mDataSchemes.size();
        for (int i = 0; i < size; i++) {
            java.lang.String str = this.mDataSchemes.get(i);
            boolean z2 = SCHEME_HTTP.equals(str) || SCHEME_HTTPS.equals(str);
            if (z) {
                if (!z2) {
                    return false;
                }
            } else if (z2) {
                return true;
            }
        }
        return z;
    }

    public final boolean needsVerification() {
        return getAutoVerify() && handlesWebUris(true);
    }

    public final boolean isVerified() {
        return (this.mVerifyState & 256) == 256 && (this.mVerifyState & 16) == 16;
    }

    public void setVerified(boolean z) {
        this.mVerifyState |= 256;
        this.mVerifyState &= -4097;
        if (z) {
            this.mVerifyState |= 4096;
        }
    }

    public void setVisibilityToInstantApp(int i) {
        this.mInstantAppVisibility = i;
    }

    public int getVisibilityToInstantApp() {
        return this.mInstantAppVisibility;
    }

    public boolean isVisibleToInstantApp() {
        return this.mInstantAppVisibility != 0;
    }

    public boolean isExplicitlyVisibleToInstantApp() {
        return this.mInstantAppVisibility == 1;
    }

    public boolean isImplicitlyVisibleToInstantApp() {
        return this.mInstantAppVisibility == 2;
    }

    public final void addAction(java.lang.String str) {
        this.mActions.add(str.intern());
    }

    public final int countActions() {
        return this.mActions.size();
    }

    public final java.lang.String getAction(int i) {
        return this.mActions.valueAt(i);
    }

    public final boolean hasAction(java.lang.String str) {
        return str != null && this.mActions.contains(str);
    }

    public final boolean matchAction(java.lang.String str) {
        return matchAction(str, false, null);
    }

    private boolean matchAction(java.lang.String str, boolean z, java.util.Collection<java.lang.String> collection) {
        if (!z || !"*".equals(str)) {
            if (collection != null && collection.contains(str)) {
                return false;
            }
            return hasAction(str);
        }
        if (collection == null) {
            return true ^ this.mActions.isEmpty();
        }
        if (this.mActions.size() > collection.size()) {
            return true;
        }
        for (int size = this.mActions.size() - 1; size >= 0; size--) {
            if (!collection.contains(this.mActions.valueAt(size))) {
                return true;
            }
        }
        return false;
    }

    public final java.util.Iterator<java.lang.String> actionsIterator() {
        if (this.mActions != null) {
            return this.mActions.iterator();
        }
        return null;
    }

    public final void addDataType(java.lang.String str) throws android.content.IntentFilter.MalformedMimeTypeException {
        processMimeType(str, new java.util.function.BiConsumer() { // from class: android.content.IntentFilter$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                android.content.IntentFilter.this.lambda$addDataType$0((java.lang.String) obj, (java.lang.Boolean) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addDataType$0(java.lang.String str, java.lang.Boolean bool) {
        if (this.mDataTypes == null) {
            this.mDataTypes = new java.util.ArrayList<>();
        }
        if (this.mStaticDataTypes == null) {
            this.mStaticDataTypes = new java.util.ArrayList<>();
        }
        if (this.mDataTypes.contains(str)) {
            return;
        }
        this.mDataTypes.add(str.intern());
        this.mStaticDataTypes.add(str.intern());
        this.mHasStaticPartialTypes = this.mHasStaticPartialTypes || bool.booleanValue();
    }

    public final void addDynamicDataType(java.lang.String str) throws android.content.IntentFilter.MalformedMimeTypeException {
        processMimeType(str, new java.util.function.BiConsumer() { // from class: android.content.IntentFilter$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                android.content.IntentFilter.this.lambda$addDynamicDataType$1((java.lang.String) obj, (java.lang.Boolean) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addDynamicDataType$1(java.lang.String str, java.lang.Boolean bool) {
        if (this.mDataTypes == null) {
            this.mDataTypes = new java.util.ArrayList<>();
        }
        if (!this.mDataTypes.contains(str)) {
            this.mDataTypes.add(str.intern());
            this.mHasDynamicPartialTypes = this.mHasDynamicPartialTypes || bool.booleanValue();
        }
    }

    private void processMimeType(java.lang.String str, java.util.function.BiConsumer<java.lang.String, java.lang.Boolean> biConsumer) throws android.content.IntentFilter.MalformedMimeTypeException {
        int i;
        int indexOf = str.indexOf(47);
        int length = str.length();
        if (indexOf <= 0 || length < (i = indexOf + 2)) {
            throw new android.content.IntentFilter.MalformedMimeTypeException(str);
        }
        boolean z = false;
        if (length == i && str.charAt(indexOf + 1) == '*') {
            str = str.substring(0, indexOf);
            z = true;
        }
        biConsumer.accept(str, java.lang.Boolean.valueOf(z));
    }

    public final void clearDynamicDataTypes() {
        if (this.mDataTypes == null) {
            return;
        }
        if (this.mStaticDataTypes != null) {
            this.mDataTypes.clear();
            this.mDataTypes.addAll(this.mStaticDataTypes);
        } else {
            this.mDataTypes = null;
        }
        this.mHasDynamicPartialTypes = false;
    }

    public int countStaticDataTypes() {
        if (this.mStaticDataTypes != null) {
            return this.mStaticDataTypes.size();
        }
        return 0;
    }

    public final boolean hasDataType(java.lang.String str) {
        return this.mDataTypes != null && findMimeType(str);
    }

    public final boolean hasExactDataType(java.lang.String str) {
        return this.mDataTypes != null && this.mDataTypes.contains(str);
    }

    public final boolean hasExactDynamicDataType(java.lang.String str) {
        return hasExactDataType(str) && !hasExactStaticDataType(str);
    }

    public final boolean hasExactStaticDataType(java.lang.String str) {
        return this.mStaticDataTypes != null && this.mStaticDataTypes.contains(str);
    }

    public final int countDataTypes() {
        if (this.mDataTypes != null) {
            return this.mDataTypes.size();
        }
        return 0;
    }

    public final java.lang.String getDataType(int i) {
        return this.mDataTypes.get(i);
    }

    public final java.util.Iterator<java.lang.String> typesIterator() {
        if (this.mDataTypes != null) {
            return this.mDataTypes.iterator();
        }
        return null;
    }

    public final java.util.List<java.lang.String> dataTypes() {
        if (this.mDataTypes != null) {
            return new java.util.ArrayList(this.mDataTypes);
        }
        return null;
    }

    public final void addMimeGroup(java.lang.String str) {
        if (this.mMimeGroups == null) {
            this.mMimeGroups = new java.util.ArrayList<>();
        }
        if (!this.mMimeGroups.contains(str)) {
            this.mMimeGroups.add(str);
        }
    }

    public final boolean hasMimeGroup(java.lang.String str) {
        return this.mMimeGroups != null && this.mMimeGroups.contains(str);
    }

    public final java.lang.String getMimeGroup(int i) {
        return this.mMimeGroups.get(i);
    }

    public final int countMimeGroups() {
        if (this.mMimeGroups != null) {
            return this.mMimeGroups.size();
        }
        return 0;
    }

    public final java.util.Iterator<java.lang.String> mimeGroupsIterator() {
        if (this.mMimeGroups != null) {
            return this.mMimeGroups.iterator();
        }
        return null;
    }

    public final void addDataScheme(java.lang.String str) {
        if (this.mDataSchemes == null) {
            this.mDataSchemes = new java.util.ArrayList<>();
        }
        if (!this.mDataSchemes.contains(str)) {
            this.mDataSchemes.add(str.intern());
        }
    }

    public final int countDataSchemes() {
        if (this.mDataSchemes != null) {
            return this.mDataSchemes.size();
        }
        return 0;
    }

    public final java.lang.String getDataScheme(int i) {
        return this.mDataSchemes.get(i);
    }

    public final boolean hasDataScheme(java.lang.String str) {
        return this.mDataSchemes != null && this.mDataSchemes.contains(str);
    }

    public final java.util.Iterator<java.lang.String> schemesIterator() {
        if (this.mDataSchemes != null) {
            return this.mDataSchemes.iterator();
        }
        return null;
    }

    public static final class AuthorityEntry {
        private final java.lang.String mHost;
        private final java.lang.String mOrigHost;
        private final int mPort;
        private final boolean mWild;

        public AuthorityEntry(java.lang.String str, java.lang.String str2) {
            this.mOrigHost = str;
            boolean z = false;
            if (str.length() > 0 && str.charAt(0) == '*') {
                z = true;
            }
            this.mWild = z;
            this.mHost = this.mWild ? str.substring(1).intern() : str;
            this.mPort = str2 != null ? java.lang.Integer.parseInt(str2) : -1;
        }

        AuthorityEntry(android.os.Parcel parcel) {
            this.mOrigHost = parcel.readString();
            this.mHost = parcel.readString();
            this.mWild = parcel.readInt() != 0;
            this.mPort = parcel.readInt();
        }

        void writeToParcel(android.os.Parcel parcel) {
            parcel.writeString(this.mOrigHost);
            parcel.writeString(this.mHost);
            parcel.writeInt(this.mWild ? 1 : 0);
            parcel.writeInt(this.mPort);
        }

        void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1138166333441L, this.mHost);
            protoOutputStream.write(1133871366146L, this.mWild);
            protoOutputStream.write(1120986464259L, this.mPort);
            protoOutputStream.end(start);
        }

        public java.lang.String getHost() {
            return this.mOrigHost;
        }

        public int getPort() {
            return this.mPort;
        }

        public boolean match(android.content.IntentFilter.AuthorityEntry authorityEntry) {
            return this.mWild == authorityEntry.mWild && this.mHost.equals(authorityEntry.mHost) && this.mPort == authorityEntry.mPort;
        }

        public boolean equals(java.lang.Object obj) {
            if (obj instanceof android.content.IntentFilter.AuthorityEntry) {
                return match((android.content.IntentFilter.AuthorityEntry) obj);
            }
            return false;
        }

        public int match(android.net.Uri uri) {
            return match(uri, false);
        }

        public int match(android.net.Uri uri, boolean z) {
            java.lang.String host = uri.getHost();
            if (host == null) {
                if (z && this.mWild && this.mHost.isEmpty()) {
                    return android.content.IntentFilter.MATCH_CATEGORY_HOST;
                }
                return -2;
            }
            if (!z || !"*".equals(host)) {
                if (this.mWild) {
                    if (host.length() < this.mHost.length()) {
                        return -2;
                    }
                    host = host.substring(host.length() - this.mHost.length());
                }
                if (host.compareToIgnoreCase(this.mHost) != 0) {
                    return -2;
                }
            }
            if (z || this.mPort < 0) {
                return android.content.IntentFilter.MATCH_CATEGORY_HOST;
            }
            return this.mPort != uri.getPort() ? -2 : 4194304;
        }
    }

    public final void addDataSchemeSpecificPart(java.lang.String str, int i) {
        addDataSchemeSpecificPart(new android.os.PatternMatcher(str, i));
    }

    public final void addDataSchemeSpecificPart(android.os.PatternMatcher patternMatcher) {
        if (this.mDataSchemeSpecificParts == null) {
            this.mDataSchemeSpecificParts = new java.util.ArrayList<>();
        }
        this.mDataSchemeSpecificParts.add(patternMatcher);
    }

    public final int countDataSchemeSpecificParts() {
        if (this.mDataSchemeSpecificParts != null) {
            return this.mDataSchemeSpecificParts.size();
        }
        return 0;
    }

    public final android.os.PatternMatcher getDataSchemeSpecificPart(int i) {
        return this.mDataSchemeSpecificParts.get(i);
    }

    public final boolean hasDataSchemeSpecificPart(java.lang.String str) {
        return hasDataSchemeSpecificPart(str, false);
    }

    private boolean hasDataSchemeSpecificPart(java.lang.String str, boolean z) {
        if (this.mDataSchemeSpecificParts == null) {
            return false;
        }
        if (z && "*".equals(str) && this.mDataSchemeSpecificParts.size() > 0) {
            return true;
        }
        int size = this.mDataSchemeSpecificParts.size();
        for (int i = 0; i < size; i++) {
            if (this.mDataSchemeSpecificParts.get(i).match(str)) {
                return true;
            }
        }
        return false;
    }

    public final boolean hasDataSchemeSpecificPart(android.os.PatternMatcher patternMatcher) {
        if (this.mDataSchemeSpecificParts == null) {
            return false;
        }
        int size = this.mDataSchemeSpecificParts.size();
        for (int i = 0; i < size; i++) {
            android.os.PatternMatcher patternMatcher2 = this.mDataSchemeSpecificParts.get(i);
            if (patternMatcher2.getType() == patternMatcher.getType() && patternMatcher2.getPath().equals(patternMatcher.getPath())) {
                return true;
            }
        }
        return false;
    }

    public final java.util.Iterator<android.os.PatternMatcher> schemeSpecificPartsIterator() {
        if (this.mDataSchemeSpecificParts != null) {
            return this.mDataSchemeSpecificParts.iterator();
        }
        return null;
    }

    public final void addDataAuthority(java.lang.String str, java.lang.String str2) {
        if (str2 != null) {
            str2 = str2.intern();
        }
        addDataAuthority(new android.content.IntentFilter.AuthorityEntry(str.intern(), str2));
    }

    public final void addDataAuthority(android.content.IntentFilter.AuthorityEntry authorityEntry) {
        if (this.mDataAuthorities == null) {
            this.mDataAuthorities = new java.util.ArrayList<>();
        }
        this.mDataAuthorities.add(authorityEntry);
    }

    public final int countDataAuthorities() {
        if (this.mDataAuthorities != null) {
            return this.mDataAuthorities.size();
        }
        return 0;
    }

    public final android.content.IntentFilter.AuthorityEntry getDataAuthority(int i) {
        return this.mDataAuthorities.get(i);
    }

    public final boolean hasDataAuthority(android.net.Uri uri) {
        return matchDataAuthority(uri) >= 0;
    }

    public final boolean hasDataAuthority(android.content.IntentFilter.AuthorityEntry authorityEntry) {
        if (this.mDataAuthorities == null) {
            return false;
        }
        int size = this.mDataAuthorities.size();
        for (int i = 0; i < size; i++) {
            if (this.mDataAuthorities.get(i).match(authorityEntry)) {
                return true;
            }
        }
        return false;
    }

    public final java.util.Iterator<android.content.IntentFilter.AuthorityEntry> authoritiesIterator() {
        if (this.mDataAuthorities != null) {
            return this.mDataAuthorities.iterator();
        }
        return null;
    }

    public final void addDataPath(java.lang.String str, int i) {
        addDataPath(new android.os.PatternMatcher(str.intern(), i));
    }

    public final void addDataPath(android.os.PatternMatcher patternMatcher) {
        if (this.mDataPaths == null) {
            this.mDataPaths = new java.util.ArrayList<>();
        }
        this.mDataPaths.add(patternMatcher);
    }

    public final int countDataPaths() {
        if (this.mDataPaths != null) {
            return this.mDataPaths.size();
        }
        return 0;
    }

    public final android.os.PatternMatcher getDataPath(int i) {
        return this.mDataPaths.get(i);
    }

    public final boolean hasDataPath(java.lang.String str) {
        return hasDataPath(str, false);
    }

    private boolean hasDataPath(java.lang.String str, boolean z) {
        if (this.mDataPaths == null) {
            return false;
        }
        if (z && WILDCARD_PATH.equals(str)) {
            return true;
        }
        int size = this.mDataPaths.size();
        for (int i = 0; i < size; i++) {
            if (this.mDataPaths.get(i).match(str)) {
                return true;
            }
        }
        return false;
    }

    public final boolean hasDataPath(android.os.PatternMatcher patternMatcher) {
        if (this.mDataPaths == null) {
            return false;
        }
        int size = this.mDataPaths.size();
        for (int i = 0; i < size; i++) {
            android.os.PatternMatcher patternMatcher2 = this.mDataPaths.get(i);
            if (patternMatcher2.getType() == patternMatcher.getType() && patternMatcher2.getPath().equals(patternMatcher.getPath())) {
                return true;
            }
        }
        return false;
    }

    public final java.util.Iterator<android.os.PatternMatcher> pathsIterator() {
        if (this.mDataPaths != null) {
            return this.mDataPaths.iterator();
        }
        return null;
    }

    public final void addUriRelativeFilterGroup(android.content.UriRelativeFilterGroup uriRelativeFilterGroup) {
        java.util.Objects.requireNonNull(uriRelativeFilterGroup);
        if (this.mUriRelativeFilterGroups == null) {
            this.mUriRelativeFilterGroups = new java.util.ArrayList<>();
        }
        this.mUriRelativeFilterGroups.add(uriRelativeFilterGroup);
    }

    public final int countUriRelativeFilterGroups() {
        if (this.mUriRelativeFilterGroups == null) {
            return 0;
        }
        return this.mUriRelativeFilterGroups.size();
    }

    public final android.content.UriRelativeFilterGroup getUriRelativeFilterGroup(int i) {
        return this.mUriRelativeFilterGroups.get(i);
    }

    public final void clearUriRelativeFilterGroups() {
        this.mUriRelativeFilterGroups = null;
    }

    public final int matchDataAuthority(android.net.Uri uri) {
        return matchDataAuthority(uri, false);
    }

    public final int matchDataAuthority(android.net.Uri uri, boolean z) {
        if (uri == null || this.mDataAuthorities == null) {
            return -2;
        }
        int size = this.mDataAuthorities.size();
        for (int i = 0; i < size; i++) {
            int match = this.mDataAuthorities.get(i).match(uri, z);
            if (match >= 0) {
                return match;
            }
        }
        return -2;
    }

    public final int matchData(java.lang.String str, java.lang.String str2, android.net.Uri uri) {
        return matchData(str, str2, uri, false);
    }

    private int matchData(java.lang.String str, java.lang.String str2, android.net.Uri uri, boolean z) {
        int i;
        boolean z2 = z && countMimeGroups() != 0;
        java.util.ArrayList<java.lang.String> arrayList = this.mDataTypes;
        java.util.ArrayList<java.lang.String> arrayList2 = this.mDataSchemes;
        if (!z2 && arrayList == null && arrayList2 == null) {
            if (str != null || uri != null) {
                return -2;
            }
            return 1081344;
        }
        if (arrayList2 != null) {
            if (!arrayList2.contains(str2 != null ? str2 : "") && (!z || !"*".equals(str2))) {
                return -2;
            }
            if (this.mDataSchemeSpecificParts != null && uri != null) {
                if (!hasDataSchemeSpecificPart(uri.getSchemeSpecificPart(), z)) {
                    i = -2;
                } else {
                    i = 5767168;
                }
            } else {
                i = 2097152;
            }
            if (i != 5767168 && this.mDataAuthorities != null) {
                i = matchDataAuthority(uri, z);
                if (i < 0) {
                    return -2;
                }
                java.util.ArrayList<android.os.PatternMatcher> arrayList3 = this.mDataPaths;
                java.util.ArrayList<android.content.UriRelativeFilterGroup> arrayList4 = this.mUriRelativeFilterGroups;
                if (android.content.pm.Flags.relativeReferenceIntentFilters()) {
                    if (arrayList3 != null || arrayList4 != null) {
                        if (!hasDataPath(uri.getPath(), z) && !matchRelRefGroups(uri)) {
                            return -2;
                        }
                        i = 5242880;
                    }
                } else if (arrayList3 != null) {
                    if (!hasDataPath(uri.getPath(), z)) {
                        return -2;
                    }
                    i = 5242880;
                }
            }
            if (i == -2) {
                return -2;
            }
        } else {
            if (str2 != null && !"".equals(str2) && !"content".equals(str2) && !"file".equals(str2) && (!z || !"*".equals(str2))) {
                return -2;
            }
            i = 1048576;
        }
        if (z2) {
            return 6291456;
        }
        if (arrayList != null) {
            if (!findMimeType(str)) {
                return -1;
            }
            i = 6291456;
        } else if (str != null) {
            return -1;
        }
        return i + 32768;
    }

    private boolean matchRelRefGroups(android.net.Uri uri) {
        if (this.mUriRelativeFilterGroups == null) {
            return false;
        }
        return android.content.UriRelativeFilterGroup.matchGroupsToUri(this.mUriRelativeFilterGroups, uri);
    }

    public final void addCategory(java.lang.String str) {
        if (this.mCategories == null) {
            this.mCategories = new java.util.ArrayList<>();
        }
        if (!this.mCategories.contains(str)) {
            this.mCategories.add(str.intern());
        }
    }

    public final int countCategories() {
        if (this.mCategories != null) {
            return this.mCategories.size();
        }
        return 0;
    }

    public final java.lang.String getCategory(int i) {
        return this.mCategories.get(i);
    }

    public final boolean hasCategory(java.lang.String str) {
        return this.mCategories != null && this.mCategories.contains(str);
    }

    public final java.util.Iterator<java.lang.String> categoriesIterator() {
        if (this.mCategories != null) {
            return this.mCategories.iterator();
        }
        return null;
    }

    public final java.lang.String matchCategories(java.util.Set<java.lang.String> set) {
        if (set == null) {
            return null;
        }
        java.util.Iterator<java.lang.String> it = set.iterator();
        if (this.mCategories == null) {
            if (it.hasNext()) {
                return it.next();
            }
            return null;
        }
        while (it.hasNext()) {
            java.lang.String next = it.next();
            if (!this.mCategories.contains(next)) {
                return next;
            }
        }
        return null;
    }

    private java.lang.String matchExtras(android.os.Bundle bundle) {
        if (this.mExtras == null) {
            return null;
        }
        for (java.lang.String str : this.mExtras.keySet()) {
            if (bundle == null) {
                return str;
            }
            java.lang.Object obj = this.mExtras.get(str);
            java.lang.Object obj2 = bundle.get(str);
            if (obj2 == null || obj.getClass() != obj2.getClass() || !java.util.Objects.deepEquals(obj, obj2)) {
                return str;
            }
        }
        return null;
    }

    public final void addExtra(java.lang.String str, int i) {
        java.util.Objects.requireNonNull(str);
        if (this.mExtras == null) {
            this.mExtras = new android.os.PersistableBundle();
        }
        this.mExtras.putInt(str, i);
    }

    public final int getIntExtra(java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        if (this.mExtras == null) {
            return 0;
        }
        return this.mExtras.getInt(str);
    }

    public final void addExtra(java.lang.String str, int[] iArr) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(iArr);
        if (this.mExtras == null) {
            this.mExtras = new android.os.PersistableBundle();
        }
        this.mExtras.putIntArray(str, iArr);
    }

    public final int[] getIntArrayExtra(java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        return this.mExtras == null ? EMPTY_INT_ARRAY : this.mExtras.getIntArray(str);
    }

    public final void addExtra(java.lang.String str, long j) {
        java.util.Objects.requireNonNull(str);
        if (this.mExtras == null) {
            this.mExtras = new android.os.PersistableBundle();
        }
        this.mExtras.putLong(str, j);
    }

    public final long getLongExtra(java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        if (this.mExtras == null) {
            return 0L;
        }
        return this.mExtras.getLong(str);
    }

    public final void addExtra(java.lang.String str, long[] jArr) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(jArr);
        if (this.mExtras == null) {
            this.mExtras = new android.os.PersistableBundle();
        }
        this.mExtras.putLongArray(str, jArr);
    }

    public final long[] getLongArrayExtra(java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        return this.mExtras == null ? EMPTY_LONG_ARRAY : this.mExtras.getLongArray(str);
    }

    public final void addExtra(java.lang.String str, double d) {
        java.util.Objects.requireNonNull(str);
        if (this.mExtras == null) {
            this.mExtras = new android.os.PersistableBundle();
        }
        this.mExtras.putDouble(str, d);
    }

    public final double getDoubleExtra(java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        if (this.mExtras == null) {
            return 0.0d;
        }
        return this.mExtras.getDouble(str);
    }

    public final void addExtra(java.lang.String str, double[] dArr) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(dArr);
        if (this.mExtras == null) {
            this.mExtras = new android.os.PersistableBundle();
        }
        this.mExtras.putDoubleArray(str, dArr);
    }

    public final double[] getDoubleArrayExtra(java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        return this.mExtras == null ? EMPTY_DOUBLE_ARRAY : this.mExtras.getDoubleArray(str);
    }

    public final void addExtra(java.lang.String str, java.lang.String str2) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(str2);
        if (this.mExtras == null) {
            this.mExtras = new android.os.PersistableBundle();
        }
        this.mExtras.putString(str, str2);
    }

    public final java.lang.String getStringExtra(java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getString(str);
    }

    public final void addExtra(java.lang.String str, java.lang.String[] strArr) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(strArr);
        if (this.mExtras == null) {
            this.mExtras = new android.os.PersistableBundle();
        }
        this.mExtras.putStringArray(str, strArr);
    }

    public final java.lang.String[] getStringArrayExtra(java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        return this.mExtras == null ? EMPTY_STRING_ARRAY : this.mExtras.getStringArray(str);
    }

    public final void addExtra(java.lang.String str, boolean z) {
        java.util.Objects.requireNonNull(str);
        if (this.mExtras == null) {
            this.mExtras = new android.os.PersistableBundle();
        }
        this.mExtras.putBoolean(str, z);
    }

    public final boolean getBooleanExtra(java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        if (this.mExtras == null) {
            return false;
        }
        return this.mExtras.getBoolean(str);
    }

    public final void addExtra(java.lang.String str, boolean[] zArr) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(zArr);
        if (this.mExtras == null) {
            this.mExtras = new android.os.PersistableBundle();
        }
        this.mExtras.putBooleanArray(str, zArr);
    }

    public final boolean[] getBooleanArrayExtra(java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        return this.mExtras == null ? EMPTY_BOOLEAN_ARRAY : this.mExtras.getBooleanArray(str);
    }

    public final boolean hasExtra(java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        if (this.mExtras == null) {
            return false;
        }
        return this.mExtras.containsKey(str);
    }

    public final void setExtras(android.os.PersistableBundle persistableBundle) {
        this.mExtras = persistableBundle;
    }

    public final android.os.PersistableBundle getExtras() {
        return this.mExtras == null ? new android.os.PersistableBundle() : this.mExtras;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$asPredicate$2(android.content.Intent intent) {
        return match(null, intent, false, TAG) >= 0;
    }

    public java.util.function.Predicate<android.content.Intent> asPredicate() {
        return new java.util.function.Predicate() { // from class: android.content.IntentFilter$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$asPredicate$2;
                lambda$asPredicate$2 = android.content.IntentFilter.this.lambda$asPredicate$2((android.content.Intent) obj);
                return lambda$asPredicate$2;
            }
        };
    }

    public java.util.function.Predicate<android.content.Intent> asPredicateWithTypeResolution(final android.content.ContentResolver contentResolver) {
        java.util.Objects.requireNonNull(contentResolver);
        return new java.util.function.Predicate() { // from class: android.content.IntentFilter$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$asPredicateWithTypeResolution$3;
                lambda$asPredicateWithTypeResolution$3 = android.content.IntentFilter.this.lambda$asPredicateWithTypeResolution$3(contentResolver, (android.content.Intent) obj);
                return lambda$asPredicateWithTypeResolution$3;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$asPredicateWithTypeResolution$3(android.content.ContentResolver contentResolver, android.content.Intent intent) {
        return match(contentResolver, intent, true, TAG) >= 0;
    }

    public final int match(android.content.ContentResolver contentResolver, android.content.Intent intent, boolean z, java.lang.String str) {
        return match(intent.getAction(), z ? intent.resolveType(contentResolver) : intent.getType(), intent.getScheme(), intent.getData(), intent.getCategories(), str, false, null, intent.getExtras());
    }

    public final int match(java.lang.String str, java.lang.String str2, java.lang.String str3, android.net.Uri uri, java.util.Set<java.lang.String> set, java.lang.String str4) {
        return match(str, str2, str3, uri, set, str4, false, null);
    }

    public final int match(java.lang.String str, java.lang.String str2, java.lang.String str3, android.net.Uri uri, java.util.Set<java.lang.String> set, java.lang.String str4, boolean z, java.util.Collection<java.lang.String> collection) {
        return match(str, str2, str3, uri, set, str4, z, collection, null);
    }

    public final int match(java.lang.String str, java.lang.String str2, java.lang.String str3, android.net.Uri uri, java.util.Set<java.lang.String> set, java.lang.String str4, boolean z, java.util.Collection<java.lang.String> collection, android.os.Bundle bundle) {
        if (str != null && !matchAction(str, z, collection)) {
            return -3;
        }
        int matchData = matchData(str2, str3, uri, z);
        if (matchData < 0) {
            return matchData;
        }
        if (matchCategories(set) != null) {
            return -4;
        }
        if (matchExtras(bundle) != null) {
            return -5;
        }
        return matchData;
    }

    public void writeToXml(org.xmlpull.v1.XmlSerializer xmlSerializer) throws java.io.IOException {
        if (getAutoVerify()) {
            xmlSerializer.attribute(null, AUTO_VERIFY_STR, java.lang.Boolean.toString(true));
        }
        int countActions = countActions();
        for (int i = 0; i < countActions; i++) {
            xmlSerializer.startTag(null, "action");
            xmlSerializer.attribute(null, "name", this.mActions.valueAt(i));
            xmlSerializer.endTag(null, "action");
        }
        int countCategories = countCategories();
        for (int i2 = 0; i2 < countCategories; i2++) {
            xmlSerializer.startTag(null, CAT_STR);
            xmlSerializer.attribute(null, "name", this.mCategories.get(i2));
            xmlSerializer.endTag(null, CAT_STR);
        }
        writeDataTypesToXml(xmlSerializer);
        int countMimeGroups = countMimeGroups();
        for (int i3 = 0; i3 < countMimeGroups; i3++) {
            xmlSerializer.startTag(null, GROUP_STR);
            xmlSerializer.attribute(null, "name", this.mMimeGroups.get(i3));
            xmlSerializer.endTag(null, GROUP_STR);
        }
        int countDataSchemes = countDataSchemes();
        for (int i4 = 0; i4 < countDataSchemes; i4++) {
            xmlSerializer.startTag(null, SCHEME_STR);
            xmlSerializer.attribute(null, "name", this.mDataSchemes.get(i4));
            xmlSerializer.endTag(null, SCHEME_STR);
        }
        int countDataSchemeSpecificParts = countDataSchemeSpecificParts();
        for (int i5 = 0; i5 < countDataSchemeSpecificParts; i5++) {
            xmlSerializer.startTag(null, SSP_STR);
            android.os.PatternMatcher patternMatcher = this.mDataSchemeSpecificParts.get(i5);
            switch (patternMatcher.getType()) {
                case 0:
                    xmlSerializer.attribute(null, LITERAL_STR, patternMatcher.getPath());
                    break;
                case 1:
                    xmlSerializer.attribute(null, PREFIX_STR, patternMatcher.getPath());
                    break;
                case 2:
                    xmlSerializer.attribute(null, SGLOB_STR, patternMatcher.getPath());
                    break;
                case 3:
                    xmlSerializer.attribute(null, AGLOB_STR, patternMatcher.getPath());
                    break;
                case 4:
                    xmlSerializer.attribute(null, SUFFIX_STR, patternMatcher.getPath());
                    break;
            }
            xmlSerializer.endTag(null, SSP_STR);
        }
        int countDataAuthorities = countDataAuthorities();
        for (int i6 = 0; i6 < countDataAuthorities; i6++) {
            xmlSerializer.startTag(null, "auth");
            android.content.IntentFilter.AuthorityEntry authorityEntry = this.mDataAuthorities.get(i6);
            xmlSerializer.attribute(null, HOST_STR, authorityEntry.getHost());
            if (authorityEntry.getPort() >= 0) {
                xmlSerializer.attribute(null, "port", java.lang.Integer.toString(authorityEntry.getPort()));
            }
            xmlSerializer.endTag(null, "auth");
        }
        int countDataPaths = countDataPaths();
        for (int i7 = 0; i7 < countDataPaths; i7++) {
            xmlSerializer.startTag(null, PATH_STR);
            android.os.PatternMatcher patternMatcher2 = this.mDataPaths.get(i7);
            switch (patternMatcher2.getType()) {
                case 0:
                    xmlSerializer.attribute(null, LITERAL_STR, patternMatcher2.getPath());
                    break;
                case 1:
                    xmlSerializer.attribute(null, PREFIX_STR, patternMatcher2.getPath());
                    break;
                case 2:
                    xmlSerializer.attribute(null, SGLOB_STR, patternMatcher2.getPath());
                    break;
                case 3:
                    xmlSerializer.attribute(null, AGLOB_STR, patternMatcher2.getPath());
                    break;
                case 4:
                    xmlSerializer.attribute(null, SUFFIX_STR, patternMatcher2.getPath());
                    break;
            }
            xmlSerializer.endTag(null, PATH_STR);
        }
        if (this.mExtras != null) {
            xmlSerializer.startTag(null, "extras");
            try {
                this.mExtras.saveToXml(xmlSerializer);
                xmlSerializer.endTag(null, "extras");
            } catch (org.xmlpull.v1.XmlPullParserException e) {
                throw new java.lang.IllegalStateException("Failed to write extras: " + this.mExtras.toString(), e);
            }
        }
        if (android.content.pm.Flags.relativeReferenceIntentFilters()) {
            int countUriRelativeFilterGroups = countUriRelativeFilterGroups();
            for (int i8 = 0; i8 < countUriRelativeFilterGroups; i8++) {
                this.mUriRelativeFilterGroups.get(i8).writeToXml(xmlSerializer);
            }
        }
    }

    private void writeDataTypesToXml(org.xmlpull.v1.XmlSerializer xmlSerializer) throws java.io.IOException {
        if (this.mStaticDataTypes == null) {
            return;
        }
        java.util.Iterator<java.lang.String> it = this.mStaticDataTypes.iterator();
        int i = 0;
        while (it.hasNext()) {
            java.lang.String next = it.next();
            while (!this.mDataTypes.get(i).equals(next)) {
                writeDataTypeToXml(xmlSerializer, this.mDataTypes.get(i), "type");
                i++;
            }
            writeDataTypeToXml(xmlSerializer, next, STATIC_TYPE_STR);
            i++;
        }
        while (i < this.mDataTypes.size()) {
            writeDataTypeToXml(xmlSerializer, this.mDataTypes.get(i), "type");
            i++;
        }
    }

    private void writeDataTypeToXml(org.xmlpull.v1.XmlSerializer xmlSerializer, java.lang.String str, java.lang.String str2) throws java.io.IOException {
        xmlSerializer.startTag(null, str2);
        if (str.indexOf(47) < 0) {
            str = str + WILDCARD_PATH;
        }
        xmlSerializer.attribute(null, "name", str);
        xmlSerializer.endTag(null, str2);
    }

    public void readFromXml(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, AUTO_VERIFY_STR);
        setAutoVerify(android.text.TextUtils.isEmpty(attributeValue) ? false : java.lang.Boolean.getBoolean(attributeValue));
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            if (next != 1) {
                if (next != 3 || xmlPullParser.getDepth() > depth) {
                    if (next != 3 && next != 4) {
                        java.lang.String name = xmlPullParser.getName();
                        if (name.equals("action")) {
                            java.lang.String attributeValue2 = xmlPullParser.getAttributeValue(null, "name");
                            if (attributeValue2 != null) {
                                addAction(attributeValue2);
                            }
                        } else if (name.equals(CAT_STR)) {
                            java.lang.String attributeValue3 = xmlPullParser.getAttributeValue(null, "name");
                            if (attributeValue3 != null) {
                                addCategory(attributeValue3);
                            }
                        } else if (name.equals(STATIC_TYPE_STR)) {
                            java.lang.String attributeValue4 = xmlPullParser.getAttributeValue(null, "name");
                            if (attributeValue4 != null) {
                                try {
                                    addDataType(attributeValue4);
                                } catch (android.content.IntentFilter.MalformedMimeTypeException e) {
                                }
                            }
                        } else if (name.equals("type")) {
                            java.lang.String attributeValue5 = xmlPullParser.getAttributeValue(null, "name");
                            if (attributeValue5 != null) {
                                try {
                                    addDynamicDataType(attributeValue5);
                                } catch (android.content.IntentFilter.MalformedMimeTypeException e2) {
                                }
                            }
                        } else if (name.equals(GROUP_STR)) {
                            java.lang.String attributeValue6 = xmlPullParser.getAttributeValue(null, "name");
                            if (attributeValue6 != null) {
                                addMimeGroup(attributeValue6);
                            }
                        } else if (name.equals(SCHEME_STR)) {
                            java.lang.String attributeValue7 = xmlPullParser.getAttributeValue(null, "name");
                            if (attributeValue7 != null) {
                                addDataScheme(attributeValue7);
                            }
                        } else if (name.equals(SSP_STR)) {
                            java.lang.String attributeValue8 = xmlPullParser.getAttributeValue(null, LITERAL_STR);
                            if (attributeValue8 != null) {
                                addDataSchemeSpecificPart(attributeValue8, 0);
                            } else {
                                java.lang.String attributeValue9 = xmlPullParser.getAttributeValue(null, PREFIX_STR);
                                if (attributeValue9 != null) {
                                    addDataSchemeSpecificPart(attributeValue9, 1);
                                } else {
                                    java.lang.String attributeValue10 = xmlPullParser.getAttributeValue(null, SGLOB_STR);
                                    if (attributeValue10 != null) {
                                        addDataSchemeSpecificPart(attributeValue10, 2);
                                    } else {
                                        java.lang.String attributeValue11 = xmlPullParser.getAttributeValue(null, AGLOB_STR);
                                        if (attributeValue11 != null) {
                                            addDataSchemeSpecificPart(attributeValue11, 3);
                                        } else {
                                            java.lang.String attributeValue12 = xmlPullParser.getAttributeValue(null, SUFFIX_STR);
                                            if (attributeValue12 != null) {
                                                addDataSchemeSpecificPart(attributeValue12, 4);
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (name.equals("auth")) {
                            java.lang.String attributeValue13 = xmlPullParser.getAttributeValue(null, HOST_STR);
                            java.lang.String attributeValue14 = xmlPullParser.getAttributeValue(null, "port");
                            if (attributeValue13 != null) {
                                addDataAuthority(attributeValue13, attributeValue14);
                            }
                        } else if (name.equals(PATH_STR)) {
                            java.lang.String attributeValue15 = xmlPullParser.getAttributeValue(null, LITERAL_STR);
                            if (attributeValue15 != null) {
                                addDataPath(attributeValue15, 0);
                            } else {
                                java.lang.String attributeValue16 = xmlPullParser.getAttributeValue(null, PREFIX_STR);
                                if (attributeValue16 != null) {
                                    addDataPath(attributeValue16, 1);
                                } else {
                                    java.lang.String attributeValue17 = xmlPullParser.getAttributeValue(null, SGLOB_STR);
                                    if (attributeValue17 != null) {
                                        addDataPath(attributeValue17, 2);
                                    } else {
                                        java.lang.String attributeValue18 = xmlPullParser.getAttributeValue(null, AGLOB_STR);
                                        if (attributeValue18 != null) {
                                            addDataPath(attributeValue18, 3);
                                        } else {
                                            java.lang.String attributeValue19 = xmlPullParser.getAttributeValue(null, SUFFIX_STR);
                                            if (attributeValue19 != null) {
                                                addDataPath(attributeValue19, 4);
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (name.equals("extras")) {
                            this.mExtras = android.os.PersistableBundle.restoreFromXml(xmlPullParser);
                        } else if (android.content.pm.Flags.relativeReferenceIntentFilters() && URI_RELATIVE_FILTER_GROUP_STR.equals(name)) {
                            addUriRelativeFilterGroup(new android.content.UriRelativeFilterGroup(xmlPullParser));
                        } else {
                            android.util.Log.w(TAG, "Unknown tag parsing IntentFilter: " + name);
                        }
                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        if (this.mActions.size() > 0) {
            java.util.Iterator<java.lang.String> it = this.mActions.iterator();
            while (it.hasNext()) {
                protoOutputStream.write(2237677961217L, it.next());
            }
        }
        if (this.mCategories != null) {
            java.util.Iterator<java.lang.String> it2 = this.mCategories.iterator();
            while (it2.hasNext()) {
                protoOutputStream.write(2237677961218L, it2.next());
            }
        }
        if (this.mDataSchemes != null) {
            java.util.Iterator<java.lang.String> it3 = this.mDataSchemes.iterator();
            while (it3.hasNext()) {
                protoOutputStream.write(2237677961219L, it3.next());
            }
        }
        if (this.mDataSchemeSpecificParts != null) {
            java.util.Iterator<android.os.PatternMatcher> it4 = this.mDataSchemeSpecificParts.iterator();
            while (it4.hasNext()) {
                it4.next().dumpDebug(protoOutputStream, 2246267895812L);
            }
        }
        if (this.mDataAuthorities != null) {
            java.util.Iterator<android.content.IntentFilter.AuthorityEntry> it5 = this.mDataAuthorities.iterator();
            while (it5.hasNext()) {
                it5.next().dumpDebug(protoOutputStream, 2246267895813L);
            }
        }
        if (this.mDataPaths != null) {
            java.util.Iterator<android.os.PatternMatcher> it6 = this.mDataPaths.iterator();
            while (it6.hasNext()) {
                it6.next().dumpDebug(protoOutputStream, 2246267895814L);
            }
        }
        if (this.mDataTypes != null) {
            java.util.Iterator<java.lang.String> it7 = this.mDataTypes.iterator();
            while (it7.hasNext()) {
                protoOutputStream.write(2237677961223L, it7.next());
            }
        }
        if (this.mMimeGroups != null) {
            java.util.Iterator<java.lang.String> it8 = this.mMimeGroups.iterator();
            while (it8.hasNext()) {
                protoOutputStream.write(2237677961227L, it8.next());
            }
        }
        if (this.mPriority != 0 || hasPartialTypes()) {
            protoOutputStream.write(1120986464264L, this.mPriority);
            protoOutputStream.write(1133871366153L, hasPartialTypes());
        }
        protoOutputStream.write(1133871366154L, getAutoVerify());
        if (this.mExtras != null) {
            this.mExtras.dumpDebug(protoOutputStream, 1146756268044L);
        }
        if (android.content.pm.Flags.relativeReferenceIntentFilters() && this.mUriRelativeFilterGroups != null) {
            java.util.Iterator<android.content.UriRelativeFilterGroup> it9 = this.mUriRelativeFilterGroups.iterator();
            while (it9.hasNext()) {
                it9.next().dumpDebug(protoOutputStream, 2246267895821L);
            }
        }
        protoOutputStream.end(start);
    }

    public void dump(android.util.Printer printer, java.lang.String str) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(256);
        if (this.mActions.size() > 0) {
            java.util.Iterator<java.lang.String> it = this.mActions.iterator();
            while (it.hasNext()) {
                sb.setLength(0);
                sb.append(str);
                sb.append("Action: \"");
                sb.append(it.next());
                sb.append("\"");
                printer.println(sb.toString());
            }
        }
        if (this.mCategories != null) {
            java.util.Iterator<java.lang.String> it2 = this.mCategories.iterator();
            while (it2.hasNext()) {
                sb.setLength(0);
                sb.append(str);
                sb.append("Category: \"");
                sb.append(it2.next());
                sb.append("\"");
                printer.println(sb.toString());
            }
        }
        if (this.mDataSchemes != null) {
            java.util.Iterator<java.lang.String> it3 = this.mDataSchemes.iterator();
            while (it3.hasNext()) {
                sb.setLength(0);
                sb.append(str);
                sb.append("Scheme: \"");
                sb.append(it3.next());
                sb.append("\"");
                printer.println(sb.toString());
            }
        }
        if (this.mDataSchemeSpecificParts != null) {
            java.util.Iterator<android.os.PatternMatcher> it4 = this.mDataSchemeSpecificParts.iterator();
            while (it4.hasNext()) {
                android.os.PatternMatcher next = it4.next();
                sb.setLength(0);
                sb.append(str);
                sb.append("Ssp: \"");
                sb.append(next);
                sb.append("\"");
                printer.println(sb.toString());
            }
        }
        if (this.mDataAuthorities != null) {
            java.util.Iterator<android.content.IntentFilter.AuthorityEntry> it5 = this.mDataAuthorities.iterator();
            while (it5.hasNext()) {
                android.content.IntentFilter.AuthorityEntry next2 = it5.next();
                sb.setLength(0);
                sb.append(str);
                sb.append("Authority: \"");
                sb.append(next2.mHost);
                sb.append("\": ");
                sb.append(next2.mPort);
                if (next2.mWild) {
                    sb.append(" WILD");
                }
                printer.println(sb.toString());
            }
        }
        if (this.mDataPaths != null) {
            java.util.Iterator<android.os.PatternMatcher> it6 = this.mDataPaths.iterator();
            while (it6.hasNext()) {
                android.os.PatternMatcher next3 = it6.next();
                sb.setLength(0);
                sb.append(str);
                sb.append("Path: \"");
                sb.append(next3);
                sb.append("\"");
                printer.println(sb.toString());
            }
        }
        if (this.mUriRelativeFilterGroups != null) {
            java.util.Iterator<android.content.UriRelativeFilterGroup> it7 = this.mUriRelativeFilterGroups.iterator();
            while (it7.hasNext()) {
                sb.setLength(0);
                sb.append(str);
                sb.append("UriRelativeFilterGroup: \"");
                sb.append(it7.next());
                sb.append("\"");
                printer.println(sb.toString());
            }
        }
        if (this.mStaticDataTypes != null) {
            java.util.Iterator<java.lang.String> it8 = this.mStaticDataTypes.iterator();
            while (it8.hasNext()) {
                sb.setLength(0);
                sb.append(str);
                sb.append("StaticType: \"");
                sb.append(it8.next());
                sb.append("\"");
                printer.println(sb.toString());
            }
        }
        if (this.mDataTypes != null) {
            java.util.Iterator<java.lang.String> it9 = this.mDataTypes.iterator();
            while (it9.hasNext()) {
                java.lang.String next4 = it9.next();
                if (!hasExactStaticDataType(next4)) {
                    sb.setLength(0);
                    sb.append(str);
                    sb.append("Type: \"");
                    sb.append(next4);
                    sb.append("\"");
                    printer.println(sb.toString());
                }
            }
        }
        if (this.mMimeGroups != null) {
            java.util.Iterator<java.lang.String> it10 = this.mMimeGroups.iterator();
            while (it10.hasNext()) {
                sb.setLength(0);
                sb.append(str);
                sb.append("MimeGroup: \"");
                sb.append(it10.next());
                sb.append("\"");
                printer.println(sb.toString());
            }
        }
        if (this.mPriority != 0 || this.mOrder != 0 || hasPartialTypes()) {
            sb.setLength(0);
            sb.append(str);
            sb.append("mPriority=");
            sb.append(this.mPriority);
            sb.append(", mOrder=");
            sb.append(this.mOrder);
            sb.append(", mHasStaticPartialTypes=");
            sb.append(this.mHasStaticPartialTypes);
            sb.append(", mHasDynamicPartialTypes=");
            sb.append(this.mHasDynamicPartialTypes);
            printer.println(sb.toString());
        }
        if (getAutoVerify()) {
            sb.setLength(0);
            sb.append(str);
            sb.append("AutoVerify=");
            sb.append(getAutoVerify());
            printer.println(sb.toString());
        }
        if (this.mExtras != null) {
            sb.setLength(0);
            sb.append(str);
            sb.append("mExtras=");
            sb.append(this.mExtras.toString());
            printer.println(sb.toString());
        }
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeStringArray((java.lang.String[]) this.mActions.toArray(new java.lang.String[this.mActions.size()]));
        if (this.mCategories != null) {
            parcel.writeInt(1);
            parcel.writeStringList(this.mCategories);
        } else {
            parcel.writeInt(0);
        }
        if (this.mDataSchemes != null) {
            parcel.writeInt(1);
            parcel.writeStringList(this.mDataSchemes);
        } else {
            parcel.writeInt(0);
        }
        if (this.mStaticDataTypes != null) {
            parcel.writeInt(1);
            parcel.writeStringList(this.mStaticDataTypes);
        } else {
            parcel.writeInt(0);
        }
        if (this.mDataTypes != null) {
            parcel.writeInt(1);
            parcel.writeStringList(this.mDataTypes);
        } else {
            parcel.writeInt(0);
        }
        if (this.mMimeGroups != null) {
            parcel.writeInt(1);
            parcel.writeStringList(this.mMimeGroups);
        } else {
            parcel.writeInt(0);
        }
        if (this.mDataSchemeSpecificParts != null) {
            int size = this.mDataSchemeSpecificParts.size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                this.mDataSchemeSpecificParts.get(i2).writeToParcel(parcel, i);
            }
        } else {
            parcel.writeInt(0);
        }
        if (this.mDataAuthorities != null) {
            int size2 = this.mDataAuthorities.size();
            parcel.writeInt(size2);
            for (int i3 = 0; i3 < size2; i3++) {
                this.mDataAuthorities.get(i3).writeToParcel(parcel);
            }
        } else {
            parcel.writeInt(0);
        }
        if (this.mDataPaths != null) {
            int size3 = this.mDataPaths.size();
            parcel.writeInt(size3);
            for (int i4 = 0; i4 < size3; i4++) {
                this.mDataPaths.get(i4).writeToParcel(parcel, i);
            }
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.mPriority);
        parcel.writeInt(this.mHasStaticPartialTypes ? 1 : 0);
        parcel.writeInt(this.mHasDynamicPartialTypes ? 1 : 0);
        parcel.writeInt(getAutoVerify() ? 1 : 0);
        parcel.writeInt(this.mInstantAppVisibility);
        parcel.writeInt(this.mOrder);
        if (this.mExtras != null) {
            parcel.writeInt(1);
            this.mExtras.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        if (android.content.pm.Flags.relativeReferenceIntentFilters() && this.mUriRelativeFilterGroups != null) {
            int size4 = this.mUriRelativeFilterGroups.size();
            parcel.writeInt(size4);
            for (int i5 = 0; i5 < size4; i5++) {
                this.mUriRelativeFilterGroups.get(i5).writeToParcel(parcel, i);
            }
            return;
        }
        parcel.writeInt(0);
    }

    public boolean debugCheck() {
        return true;
    }

    public boolean checkDataPathAndSchemeSpecificParts() {
        int size = this.mDataPaths == null ? 0 : this.mDataPaths.size();
        int size2 = this.mDataSchemeSpecificParts == null ? 0 : this.mDataSchemeSpecificParts.size();
        for (int i = 0; i < size; i++) {
            if (!this.mDataPaths.get(i).check()) {
                return false;
            }
        }
        for (int i2 = 0; i2 < size2; i2++) {
            if (!this.mDataSchemeSpecificParts.get(i2).check()) {
                return false;
            }
        }
        return true;
    }

    public IntentFilter(android.os.Parcel parcel) {
        this.mCategories = null;
        this.mDataSchemes = null;
        this.mDataSchemeSpecificParts = null;
        this.mDataAuthorities = null;
        this.mDataPaths = null;
        this.mUriRelativeFilterGroups = null;
        this.mStaticDataTypes = null;
        this.mDataTypes = null;
        this.mMimeGroups = null;
        this.mHasStaticPartialTypes = false;
        this.mHasDynamicPartialTypes = false;
        this.mExtras = null;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        parcel.readStringList(arrayList);
        this.mActions = new android.util.ArraySet<>(arrayList);
        if (parcel.readInt() != 0) {
            this.mCategories = new java.util.ArrayList<>();
            parcel.readStringList(this.mCategories);
        }
        if (parcel.readInt() != 0) {
            this.mDataSchemes = new java.util.ArrayList<>();
            parcel.readStringList(this.mDataSchemes);
        }
        if (parcel.readInt() != 0) {
            this.mStaticDataTypes = new java.util.ArrayList<>();
            parcel.readStringList(this.mStaticDataTypes);
        }
        if (parcel.readInt() != 0) {
            this.mDataTypes = new java.util.ArrayList<>();
            parcel.readStringList(this.mDataTypes);
        }
        if (parcel.readInt() != 0) {
            this.mMimeGroups = new java.util.ArrayList<>();
            parcel.readStringList(this.mMimeGroups);
        }
        int readInt = parcel.readInt();
        if (readInt > 0) {
            this.mDataSchemeSpecificParts = new java.util.ArrayList<>(readInt);
            for (int i = 0; i < readInt; i++) {
                this.mDataSchemeSpecificParts.add(new android.os.PatternMatcher(parcel));
            }
        }
        int readInt2 = parcel.readInt();
        if (readInt2 > 0) {
            this.mDataAuthorities = new java.util.ArrayList<>(readInt2);
            for (int i2 = 0; i2 < readInt2; i2++) {
                this.mDataAuthorities.add(new android.content.IntentFilter.AuthorityEntry(parcel));
            }
        }
        int readInt3 = parcel.readInt();
        if (readInt3 > 0) {
            this.mDataPaths = new java.util.ArrayList<>(readInt3);
            for (int i3 = 0; i3 < readInt3; i3++) {
                this.mDataPaths.add(new android.os.PatternMatcher(parcel));
            }
        }
        this.mPriority = parcel.readInt();
        this.mHasStaticPartialTypes = parcel.readInt() > 0;
        this.mHasDynamicPartialTypes = parcel.readInt() > 0;
        setAutoVerify(parcel.readInt() > 0);
        setVisibilityToInstantApp(parcel.readInt());
        this.mOrder = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.mExtras = android.os.PersistableBundle.CREATOR.createFromParcel(parcel);
        }
        int readInt4 = parcel.readInt();
        if (android.content.pm.Flags.relativeReferenceIntentFilters() && readInt4 > 0) {
            this.mUriRelativeFilterGroups = new java.util.ArrayList<>(readInt4);
            for (int i4 = 0; i4 < readInt4; i4++) {
                this.mUriRelativeFilterGroups.add(new android.content.UriRelativeFilterGroup(parcel));
            }
        }
    }

    private boolean hasPartialTypes() {
        return this.mHasStaticPartialTypes || this.mHasDynamicPartialTypes;
    }

    private final boolean findMimeType(java.lang.String str) {
        java.util.ArrayList<java.lang.String> arrayList = this.mDataTypes;
        if (str == null) {
            return false;
        }
        if (arrayList.contains(str)) {
            return true;
        }
        int length = str.length();
        if (length == 3 && str.equals("*/*")) {
            return !arrayList.isEmpty();
        }
        if (hasPartialTypes() && arrayList.contains("*")) {
            return true;
        }
        int indexOf = str.indexOf(47);
        if (indexOf > 0) {
            if (hasPartialTypes() && arrayList.contains(str.substring(0, indexOf))) {
                return true;
            }
            if (length == indexOf + 2) {
                int i = indexOf + 1;
                if (str.charAt(i) == '*') {
                    int size = arrayList.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        if (str.regionMatches(0, arrayList.get(i2), 0, i)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public java.util.ArrayList<java.lang.String> getHostsList() {
        java.util.ArrayList<java.lang.String> arrayList = new java.util.ArrayList<>();
        java.util.Iterator<android.content.IntentFilter.AuthorityEntry> authoritiesIterator = authoritiesIterator();
        if (authoritiesIterator != null) {
            while (authoritiesIterator.hasNext()) {
                arrayList.add(authoritiesIterator.next().getHost());
            }
        }
        return arrayList;
    }

    public java.lang.String[] getHosts() {
        java.util.ArrayList<java.lang.String> hostsList = getHostsList();
        return (java.lang.String[]) hostsList.toArray(new java.lang.String[hostsList.size()]);
    }

    public static boolean filterEquals(android.content.IntentFilter intentFilter, android.content.IntentFilter intentFilter2) {
        int countActions = intentFilter.countActions();
        if (countActions != intentFilter2.countActions()) {
            return false;
        }
        for (int i = 0; i < countActions; i++) {
            if (!intentFilter2.hasAction(intentFilter.getAction(i))) {
                return false;
            }
        }
        int countCategories = intentFilter.countCategories();
        if (countCategories != intentFilter2.countCategories()) {
            return false;
        }
        for (int i2 = 0; i2 < countCategories; i2++) {
            if (!intentFilter2.hasCategory(intentFilter.getCategory(i2))) {
                return false;
            }
        }
        int countDataTypes = intentFilter.countDataTypes();
        if (countDataTypes != intentFilter2.countDataTypes()) {
            return false;
        }
        for (int i3 = 0; i3 < countDataTypes; i3++) {
            if (!intentFilter2.hasExactDataType(intentFilter.getDataType(i3))) {
                return false;
            }
        }
        int countDataSchemes = intentFilter.countDataSchemes();
        if (countDataSchemes != intentFilter2.countDataSchemes()) {
            return false;
        }
        for (int i4 = 0; i4 < countDataSchemes; i4++) {
            if (!intentFilter2.hasDataScheme(intentFilter.getDataScheme(i4))) {
                return false;
            }
        }
        int countDataAuthorities = intentFilter.countDataAuthorities();
        if (countDataAuthorities != intentFilter2.countDataAuthorities()) {
            return false;
        }
        for (int i5 = 0; i5 < countDataAuthorities; i5++) {
            if (!intentFilter2.hasDataAuthority(intentFilter.getDataAuthority(i5))) {
                return false;
            }
        }
        int countDataPaths = intentFilter.countDataPaths();
        if (countDataPaths != intentFilter2.countDataPaths()) {
            return false;
        }
        for (int i6 = 0; i6 < countDataPaths; i6++) {
            if (!intentFilter2.hasDataPath(intentFilter.getDataPath(i6))) {
                return false;
            }
        }
        int countDataSchemeSpecificParts = intentFilter.countDataSchemeSpecificParts();
        if (countDataSchemeSpecificParts != intentFilter2.countDataSchemeSpecificParts()) {
            return false;
        }
        for (int i7 = 0; i7 < countDataSchemeSpecificParts; i7++) {
            if (!intentFilter2.hasDataSchemeSpecificPart(intentFilter.getDataSchemeSpecificPart(i7))) {
                return false;
            }
        }
        return true;
    }
}
