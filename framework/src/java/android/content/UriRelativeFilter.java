package android.content;

/* loaded from: classes.dex */
public final class UriRelativeFilter {
    private static final java.lang.String FILTER_STR = "filter";
    public static final int FRAGMENT = 2;
    private static final java.lang.String PART_STR = "part";
    public static final int PATH = 0;
    private static final java.lang.String PATTERN_STR = "pattern";
    public static final int QUERY = 1;
    static final java.lang.String URI_RELATIVE_FILTER_STR = "uriRelativeFilter";
    private final java.lang.String mFilter;
    private final int mPatternType;
    private final int mUriPart;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface UriPart {
    }

    public UriRelativeFilter(int i, int i2, java.lang.String str) {
        this.mUriPart = i;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.content.UriRelativeFilter.UriPart.class, (java.lang.annotation.Annotation) null, this.mUriPart);
        this.mPatternType = i2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.os.PatternMatcher.PatternType.class, (java.lang.annotation.Annotation) null, this.mPatternType);
        this.mFilter = str;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mFilter);
    }

    public int getUriPart() {
        return this.mUriPart;
    }

    public int getPatternType() {
        return this.mPatternType;
    }

    public java.lang.String getFilter() {
        return this.mFilter;
    }

    public boolean matchData(android.net.Uri uri) {
        android.os.PatternMatcher patternMatcher = new android.os.PatternMatcher(this.mFilter, this.mPatternType);
        switch (getUriPart()) {
            case 0:
                return patternMatcher.match(uri.getPath());
            case 1:
                return matchQuery(patternMatcher, uri.getQuery());
            case 2:
                return patternMatcher.match(uri.getFragment());
            default:
                return false;
        }
    }

    private boolean matchQuery(android.os.PatternMatcher patternMatcher, java.lang.String str) {
        if (str != null) {
            java.lang.String[] split = str.split("&");
            if (split.length == 1) {
                split = str.split(android.inputmethodservice.navigationbar.NavigationBarInflaterView.GRAVITY_SEPARATOR);
            }
            for (java.lang.String str2 : split) {
                if (patternMatcher.match(str2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, this.mUriPart);
        protoOutputStream.write(1120986464258L, this.mPatternType);
        protoOutputStream.write(1138166333443L, this.mFilter);
        protoOutputStream.end(start);
    }

    public void writeToXml(org.xmlpull.v1.XmlSerializer xmlSerializer) throws java.io.IOException {
        xmlSerializer.startTag(null, URI_RELATIVE_FILTER_STR);
        xmlSerializer.attribute(null, PATTERN_STR, java.lang.Integer.toString(this.mPatternType));
        xmlSerializer.attribute(null, PART_STR, java.lang.Integer.toString(this.mUriPart));
        xmlSerializer.attribute(null, FILTER_STR, this.mFilter);
        xmlSerializer.endTag(null, URI_RELATIVE_FILTER_STR);
    }

    private java.lang.String uriPartToString() {
        switch (this.mUriPart) {
            case 0:
                return "PATH";
            case 1:
                return "QUERY";
            case 2:
                return "FRAGMENT";
            default:
                return "UNKNOWN";
        }
    }

    private java.lang.String patternTypeToString() {
        switch (this.mPatternType) {
            case 0:
                return "LITERAL";
            case 1:
                return "PREFIX";
            case 2:
                return "GLOB";
            case 3:
                return "ADVANCED_GLOB";
            case 4:
                return "SUFFIX";
            default:
                return "UNKNOWN";
        }
    }

    public java.lang.String toString() {
        return "UriRelativeFilter { uriPart = " + uriPartToString() + ", patternType = " + patternTypeToString() + ", filter = " + this.mFilter + " }";
    }

    public android.content.UriRelativeFilterParcel toParcel() {
        android.content.UriRelativeFilterParcel uriRelativeFilterParcel = new android.content.UriRelativeFilterParcel();
        uriRelativeFilterParcel.uriPart = this.mUriPart;
        uriRelativeFilterParcel.patternType = this.mPatternType;
        uriRelativeFilterParcel.filter = this.mFilter;
        return uriRelativeFilterParcel;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.content.UriRelativeFilter uriRelativeFilter = (android.content.UriRelativeFilter) obj;
        if (this.mUriPart == uriRelativeFilter.mUriPart && this.mPatternType == uriRelativeFilter.mPatternType && java.util.Objects.equals(this.mFilter, uriRelativeFilter.mFilter)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((this.mUriPart + 31) * 31) + this.mPatternType) * 31) + java.util.Objects.hashCode(this.mFilter);
    }

    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mUriPart);
        parcel.writeInt(this.mPatternType);
        parcel.writeString(this.mFilter);
    }

    UriRelativeFilter(android.os.Parcel parcel) {
        this.mUriPart = parcel.readInt();
        this.mPatternType = parcel.readInt();
        this.mFilter = parcel.readString();
    }

    public UriRelativeFilter(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        this.mUriPart = java.lang.Integer.parseInt(xmlPullParser.getAttributeValue(null, PART_STR));
        this.mPatternType = java.lang.Integer.parseInt(xmlPullParser.getAttributeValue(null, PATTERN_STR));
        this.mFilter = xmlPullParser.getAttributeValue(null, FILTER_STR);
    }

    public UriRelativeFilter(android.content.UriRelativeFilterParcel uriRelativeFilterParcel) {
        this.mUriPart = uriRelativeFilterParcel.uriPart;
        this.mPatternType = uriRelativeFilterParcel.patternType;
        this.mFilter = uriRelativeFilterParcel.filter;
    }
}
