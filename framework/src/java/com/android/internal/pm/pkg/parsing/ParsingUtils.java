package com.android.internal.pm.pkg.parsing;

/* loaded from: classes5.dex */
public class ParsingUtils {
    public static final java.lang.String ANDROID_RES_NAMESPACE = "http://schemas.android.com/apk/res/android";
    public static final int DEFAULT_MAX_SDK_VERSION = Integer.MAX_VALUE;
    public static final int DEFAULT_MIN_SDK_VERSION = 1;
    public static final int DEFAULT_TARGET_SDK_VERSION = 0;
    public static final int NOT_SET = -1;
    public static final java.lang.String TAG = "PackageParsing";

    public static java.lang.String buildClassName(java.lang.String str, java.lang.CharSequence charSequence) {
        if (charSequence == null || charSequence.length() <= 0) {
            return null;
        }
        java.lang.String charSequence2 = charSequence.toString();
        if (charSequence2.charAt(0) == '.') {
            return str + charSequence2;
        }
        if (charSequence2.indexOf(46) < 0) {
            return str + '.' + charSequence2;
        }
        return charSequence2;
    }

    public static android.content.pm.parsing.result.ParseResult unknownTag(java.lang.String str, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.XmlResourceParser xmlResourceParser, android.content.pm.parsing.result.ParseInput parseInput) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        android.util.Slog.w(TAG, "Unknown element under " + str + ": " + xmlResourceParser.getName() + " at " + parsingPackage.getBaseApkPath() + " " + xmlResourceParser.getPositionDescription());
        com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
        return parseInput.success(null);
    }

    public static <Interface, Impl extends Interface> java.util.List<Interface> createTypedInterfaceList(android.os.Parcel parcel, android.os.Parcelable.Creator<Impl> creator) {
        int readInt = parcel.readInt();
        if (readInt < 0) {
            return new java.util.ArrayList();
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(readInt);
        while (readInt > 0) {
            arrayList.add(parcel.readTypedObject(creator));
            readInt--;
        }
        return arrayList;
    }

    public static void writeParcelableList(android.os.Parcel parcel, java.util.List<?> list) {
        if (list == null) {
            parcel.writeInt(-1);
            return;
        }
        int size = list.size();
        parcel.writeInt(size);
        for (int i = 0; i < size; i++) {
            parcel.writeTypedObject((android.os.Parcelable) list.get(i), 0);
        }
    }

    public static class StringPairListParceler implements com.android.internal.util.Parcelling<java.util.List<android.util.Pair<java.lang.String, com.android.internal.pm.pkg.component.ParsedIntentInfo>>> {
        @Override // com.android.internal.util.Parcelling
        public void parcel(java.util.List<android.util.Pair<java.lang.String, com.android.internal.pm.pkg.component.ParsedIntentInfo>> list, android.os.Parcel parcel, int i) {
            if (list == null) {
                parcel.writeInt(-1);
                return;
            }
            int size = list.size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                android.util.Pair<java.lang.String, com.android.internal.pm.pkg.component.ParsedIntentInfo> pair = list.get(i2);
                parcel.writeString(pair.first);
                parcel.writeParcelable((android.os.Parcelable) pair.second, i);
            }
        }

        @Override // com.android.internal.util.Parcelling
        public java.util.List<android.util.Pair<java.lang.String, com.android.internal.pm.pkg.component.ParsedIntentInfo>> unparcel(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            if (readInt == -1) {
                return null;
            }
            if (readInt == 0) {
                return new java.util.ArrayList(0);
            }
            java.util.ArrayList arrayList = new java.util.ArrayList(readInt);
            for (int i = 0; i < readInt; i++) {
                arrayList.add(android.util.Pair.create(parcel.readString(), (com.android.internal.pm.pkg.component.ParsedIntentInfo) parcel.readParcelable(com.android.internal.pm.pkg.component.ParsedIntentInfoImpl.class.getClassLoader(), com.android.internal.pm.pkg.component.ParsedIntentInfo.class)));
            }
            return arrayList;
        }
    }

    public static android.content.pm.parsing.result.ParseResult<java.util.Set<java.lang.String>> parseKnownActivityEmbeddingCerts(android.content.res.TypedArray typedArray, android.content.res.Resources resources, int i, android.content.pm.parsing.result.ParseInput parseInput) {
        java.util.Set set = null;
        if (!typedArray.hasValue(i)) {
            return parseInput.success(null);
        }
        int resourceId = typedArray.getResourceId(i, 0);
        if (resourceId != 0) {
            if (resources.getResourceTypeName(resourceId).equals("array")) {
                java.lang.String[] stringArray = resources.getStringArray(resourceId);
                if (stringArray != null) {
                    set = java.util.Set.of((java.lang.Object[]) stringArray);
                }
            } else {
                java.lang.String string = resources.getString(resourceId);
                if (string != null) {
                    set = java.util.Set.of(string);
                }
            }
            if (set == null || set.isEmpty()) {
                return parseInput.error("Defined a knownActivityEmbeddingCerts attribute but the provided resource is null");
            }
            return parseInput.success(set);
        }
        java.lang.String string2 = typedArray.getString(i);
        if (string2 == null || string2.isEmpty()) {
            return parseInput.error("Defined a knownActivityEmbeddingCerts attribute but the provided string is empty");
        }
        return parseInput.success(java.util.Set.of(string2));
    }
}
