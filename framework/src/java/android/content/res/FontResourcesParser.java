package android.content.res;

/* loaded from: classes.dex */
public class FontResourcesParser {
    private static final java.lang.String TAG = "FontResourcesParser";

    public interface FamilyResourceEntry {
    }

    public static final class ProviderResourceEntry implements android.content.res.FontResourcesParser.FamilyResourceEntry {
        private final java.util.List<java.util.List<java.lang.String>> mCerts;
        private final java.lang.String mProviderAuthority;
        private final java.lang.String mProviderPackage;
        private final java.lang.String mQuery;
        private final java.lang.String mSystemFontFamilyName;

        public ProviderResourceEntry(java.lang.String str, java.lang.String str2, java.lang.String str3, java.util.List<java.util.List<java.lang.String>> list, java.lang.String str4) {
            this.mProviderAuthority = str;
            this.mProviderPackage = str2;
            this.mQuery = str3;
            this.mCerts = list;
            this.mSystemFontFamilyName = str4;
        }

        public java.lang.String getAuthority() {
            return this.mProviderAuthority;
        }

        public java.lang.String getPackage() {
            return this.mProviderPackage;
        }

        public java.lang.String getQuery() {
            return this.mQuery;
        }

        public java.lang.String getSystemFontFamilyName() {
            return this.mSystemFontFamilyName;
        }

        public java.util.List<java.util.List<java.lang.String>> getCerts() {
            return this.mCerts;
        }
    }

    public static final class FontFileResourceEntry {
        public static final int ITALIC = 1;
        public static final int RESOLVE_BY_FONT_TABLE = -1;
        public static final int UPRIGHT = 0;
        private final java.lang.String mFileName;
        private int mItalic;
        private int mResourceId;
        private int mTtcIndex;
        private java.lang.String mVariationSettings;
        private int mWeight;

        public FontFileResourceEntry(java.lang.String str, int i, int i2, java.lang.String str2, int i3) {
            this.mFileName = str;
            this.mWeight = i;
            this.mItalic = i2;
            this.mVariationSettings = str2;
            this.mTtcIndex = i3;
        }

        public java.lang.String getFileName() {
            return this.mFileName;
        }

        public int getWeight() {
            return this.mWeight;
        }

        public int getItalic() {
            return this.mItalic;
        }

        public java.lang.String getVariationSettings() {
            return this.mVariationSettings;
        }

        public int getTtcIndex() {
            return this.mTtcIndex;
        }
    }

    public static final class FontFamilyFilesResourceEntry implements android.content.res.FontResourcesParser.FamilyResourceEntry {
        private final android.content.res.FontResourcesParser.FontFileResourceEntry[] mEntries;

        public FontFamilyFilesResourceEntry(android.content.res.FontResourcesParser.FontFileResourceEntry[] fontFileResourceEntryArr) {
            this.mEntries = fontFileResourceEntryArr;
        }

        public android.content.res.FontResourcesParser.FontFileResourceEntry[] getEntries() {
            return this.mEntries;
        }
    }

    public static android.content.res.FontResourcesParser.FamilyResourceEntry parse(org.xmlpull.v1.XmlPullParser xmlPullParser, android.content.res.Resources resources) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int next;
        do {
            next = xmlPullParser.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next != 2) {
            throw new org.xmlpull.v1.XmlPullParserException("No start tag found");
        }
        return readFamilies(xmlPullParser, resources);
    }

    private static android.content.res.FontResourcesParser.FamilyResourceEntry readFamilies(org.xmlpull.v1.XmlPullParser xmlPullParser, android.content.res.Resources resources) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        xmlPullParser.require(2, null, "font-family");
        if (xmlPullParser.getName().equals("font-family")) {
            return readFamily(xmlPullParser, resources);
        }
        skip(xmlPullParser);
        android.util.Log.e(TAG, "Failed to find font-family tag");
        return null;
    }

    private static android.content.res.FontResourcesParser.FamilyResourceEntry readFamily(org.xmlpull.v1.XmlPullParser xmlPullParser, android.content.res.Resources resources) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(android.util.Xml.asAttributeSet(xmlPullParser), com.android.internal.R.styleable.FontFamily);
        java.lang.String string = obtainAttributes.getString(0);
        java.lang.String string2 = obtainAttributes.getString(2);
        boolean z = true;
        java.lang.String string3 = obtainAttributes.getString(1);
        int resourceId = obtainAttributes.getResourceId(3, 0);
        java.lang.String string4 = obtainAttributes.getString(4);
        obtainAttributes.recycle();
        java.util.ArrayList arrayList = null;
        if (string != null && string2 != null && string3 != null) {
            while (xmlPullParser.next() != 3) {
                skip(xmlPullParser);
            }
            if (resourceId != 0) {
                android.content.res.TypedArray obtainTypedArray = resources.obtainTypedArray(resourceId);
                if (obtainTypedArray.length() > 0) {
                    arrayList = new java.util.ArrayList();
                    if (obtainTypedArray.getResourceId(0, 0) == 0) {
                        z = false;
                    }
                    if (z) {
                        for (int i = 0; i < obtainTypedArray.length(); i++) {
                            arrayList.add(java.util.Arrays.asList(resources.getStringArray(obtainTypedArray.getResourceId(i, 0))));
                        }
                    } else {
                        arrayList.add(java.util.Arrays.asList(resources.getStringArray(resourceId)));
                    }
                }
                obtainTypedArray.recycle();
            }
            return new android.content.res.FontResourcesParser.ProviderResourceEntry(string, string2, string3, arrayList, string4);
        }
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        while (xmlPullParser.next() != 3) {
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals(android.content.Context.FONT_SERVICE)) {
                    android.content.res.FontResourcesParser.FontFileResourceEntry readFont = readFont(xmlPullParser, resources);
                    if (readFont != null) {
                        arrayList2.add(readFont);
                    }
                } else {
                    skip(xmlPullParser);
                }
            }
        }
        if (arrayList2.isEmpty()) {
            return null;
        }
        return new android.content.res.FontResourcesParser.FontFamilyFilesResourceEntry((android.content.res.FontResourcesParser.FontFileResourceEntry[]) arrayList2.toArray(new android.content.res.FontResourcesParser.FontFileResourceEntry[arrayList2.size()]));
    }

    private static android.content.res.FontResourcesParser.FontFileResourceEntry readFont(org.xmlpull.v1.XmlPullParser xmlPullParser, android.content.res.Resources resources) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(android.util.Xml.asAttributeSet(xmlPullParser), com.android.internal.R.styleable.FontFamilyFont);
        int i = obtainAttributes.getInt(1, -1);
        int i2 = obtainAttributes.getInt(2, -1);
        java.lang.String string = obtainAttributes.getString(4);
        int i3 = obtainAttributes.getInt(3, 0);
        java.lang.String string2 = obtainAttributes.getString(0);
        obtainAttributes.recycle();
        while (xmlPullParser.next() != 3) {
            skip(xmlPullParser);
        }
        if (string2 == null) {
            return null;
        }
        return new android.content.res.FontResourcesParser.FontFileResourceEntry(string2, i, i2, string, i3);
    }

    private static void skip(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int i = 1;
        while (i > 0) {
            switch (xmlPullParser.next()) {
                case 2:
                    i++;
                    break;
                case 3:
                    i--;
                    break;
            }
        }
    }
}
