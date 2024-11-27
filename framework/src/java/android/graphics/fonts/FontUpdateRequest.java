package android.graphics.fonts;

/* loaded from: classes.dex */
public final class FontUpdateRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.graphics.fonts.FontUpdateRequest> CREATOR = new android.os.Parcelable.Creator<android.graphics.fonts.FontUpdateRequest>() { // from class: android.graphics.fonts.FontUpdateRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.graphics.fonts.FontUpdateRequest createFromParcel(android.os.Parcel parcel) {
            return new android.graphics.fonts.FontUpdateRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.graphics.fonts.FontUpdateRequest[] newArray(int i) {
            return new android.graphics.fonts.FontUpdateRequest[i];
        }
    };
    public static final int TYPE_UPDATE_FONT_FAMILY = 1;
    public static final int TYPE_UPDATE_FONT_FILE = 0;
    private final android.os.ParcelFileDescriptor mFd;
    private final android.graphics.fonts.FontUpdateRequest.Family mFontFamily;
    private final byte[] mSignature;
    private final int mType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Type {
    }

    public static final class Font implements android.os.Parcelable {
        private static final java.lang.String ATTR_AXIS = "axis";
        private static final java.lang.String ATTR_INDEX = "index";
        private static final java.lang.String ATTR_POSTSCRIPT_NAME = "name";
        private static final java.lang.String ATTR_SLANT = "slant";
        private static final java.lang.String ATTR_WEIGHT = "weight";
        public static final android.os.Parcelable.Creator<android.graphics.fonts.FontUpdateRequest.Font> CREATOR = new android.os.Parcelable.Creator<android.graphics.fonts.FontUpdateRequest.Font>() { // from class: android.graphics.fonts.FontUpdateRequest.Font.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.graphics.fonts.FontUpdateRequest.Font createFromParcel(android.os.Parcel parcel) {
                java.lang.String readString8 = parcel.readString8();
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                return new android.graphics.fonts.FontUpdateRequest.Font(readString8, new android.graphics.fonts.FontStyle(readInt, readInt2), parcel.readInt(), parcel.readString8());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.graphics.fonts.FontUpdateRequest.Font[] newArray(int i) {
                return new android.graphics.fonts.FontUpdateRequest.Font[i];
            }
        };
        private final android.graphics.fonts.FontStyle mFontStyle;
        private final java.lang.String mFontVariationSettings;
        private final int mIndex;
        private final java.lang.String mPostScriptName;

        public Font(java.lang.String str, android.graphics.fonts.FontStyle fontStyle, int i, java.lang.String str2) {
            this.mPostScriptName = str;
            this.mFontStyle = fontStyle;
            this.mIndex = i;
            this.mFontVariationSettings = str2;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeString8(this.mPostScriptName);
            parcel.writeInt(this.mFontStyle.getWeight());
            parcel.writeInt(this.mFontStyle.getSlant());
            parcel.writeInt(this.mIndex);
            parcel.writeString8(this.mFontVariationSettings);
        }

        public static void writeToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, android.graphics.fonts.FontUpdateRequest.Font font) throws java.io.IOException {
            typedXmlSerializer.attribute(null, "name", font.getPostScriptName());
            typedXmlSerializer.attributeInt(null, "index", font.getIndex());
            typedXmlSerializer.attributeInt(null, "weight", font.getFontStyle().getWeight());
            typedXmlSerializer.attributeInt(null, ATTR_SLANT, font.getFontStyle().getSlant());
            typedXmlSerializer.attribute(null, "axis", font.getFontVariationSettings());
        }

        public static android.graphics.fonts.FontUpdateRequest.Font readFromXml(org.xmlpull.v1.XmlPullParser xmlPullParser) throws java.io.IOException {
            java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "name");
            if (attributeValue == null) {
                throw new java.io.IOException("name attribute is missing in font tag.");
            }
            int attributeValueInt = android.graphics.fonts.FontUpdateRequest.getAttributeValueInt(xmlPullParser, "index", 0);
            int attributeValueInt2 = android.graphics.fonts.FontUpdateRequest.getAttributeValueInt(xmlPullParser, "weight", 400);
            int attributeValueInt3 = android.graphics.fonts.FontUpdateRequest.getAttributeValueInt(xmlPullParser, ATTR_SLANT, 0);
            java.lang.String attributeValue2 = xmlPullParser.getAttributeValue(null, "axis");
            if (attributeValue2 == null) {
                attributeValue2 = "";
            }
            return new android.graphics.fonts.FontUpdateRequest.Font(attributeValue, new android.graphics.fonts.FontStyle(attributeValueInt2, attributeValueInt3), attributeValueInt, attributeValue2);
        }

        public java.lang.String getPostScriptName() {
            return this.mPostScriptName;
        }

        public android.graphics.fonts.FontStyle getFontStyle() {
            return this.mFontStyle;
        }

        public int getIndex() {
            return this.mIndex;
        }

        public java.lang.String getFontVariationSettings() {
            return this.mFontVariationSettings;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.graphics.fonts.FontUpdateRequest.Font font = (android.graphics.fonts.FontUpdateRequest.Font) obj;
            if (this.mIndex == font.mIndex && this.mPostScriptName.equals(font.mPostScriptName) && this.mFontStyle.equals(font.mFontStyle) && this.mFontVariationSettings.equals(font.mFontVariationSettings)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(this.mPostScriptName, this.mFontStyle, java.lang.Integer.valueOf(this.mIndex), this.mFontVariationSettings);
        }

        public java.lang.String toString() {
            return "Font{mPostScriptName='" + this.mPostScriptName + android.text.format.DateFormat.QUOTE + ", mFontStyle=" + this.mFontStyle + ", mIndex=" + this.mIndex + ", mFontVariationSettings='" + this.mFontVariationSettings + android.text.format.DateFormat.QUOTE + '}';
        }
    }

    public static final class Family implements android.os.Parcelable {
        private static final java.lang.String ATTR_NAME = "name";
        public static final android.os.Parcelable.Creator<android.graphics.fonts.FontUpdateRequest.Family> CREATOR = new android.os.Parcelable.Creator<android.graphics.fonts.FontUpdateRequest.Family>() { // from class: android.graphics.fonts.FontUpdateRequest.Family.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.graphics.fonts.FontUpdateRequest.Family createFromParcel(android.os.Parcel parcel) {
                return new android.graphics.fonts.FontUpdateRequest.Family(parcel.readString8(), parcel.readParcelableList(new java.util.ArrayList(), android.graphics.fonts.FontUpdateRequest.Font.class.getClassLoader(), android.graphics.fonts.FontUpdateRequest.Font.class));
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.graphics.fonts.FontUpdateRequest.Family[] newArray(int i) {
                return new android.graphics.fonts.FontUpdateRequest.Family[i];
            }
        };
        private static final java.lang.String TAG_FAMILY = "family";
        private static final java.lang.String TAG_FONT = "font";
        private final java.util.List<android.graphics.fonts.FontUpdateRequest.Font> mFonts;
        private final java.lang.String mName;

        public Family(java.lang.String str, java.util.List<android.graphics.fonts.FontUpdateRequest.Font> list) {
            this.mName = str;
            this.mFonts = list;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeString8(this.mName);
            parcel.writeParcelableList(this.mFonts, i);
        }

        public static void writeFamilyToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, android.graphics.fonts.FontUpdateRequest.Family family) throws java.io.IOException {
            typedXmlSerializer.attribute(null, "name", family.getName());
            java.util.List<android.graphics.fonts.FontUpdateRequest.Font> fonts = family.getFonts();
            for (int i = 0; i < fonts.size(); i++) {
                android.graphics.fonts.FontUpdateRequest.Font font = fonts.get(i);
                typedXmlSerializer.startTag(null, "font");
                android.graphics.fonts.FontUpdateRequest.Font.writeToXml(typedXmlSerializer, font);
                typedXmlSerializer.endTag(null, "font");
            }
        }

        public static android.graphics.fonts.FontUpdateRequest.Family readFromXml(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            if (xmlPullParser.getEventType() != 2 || !xmlPullParser.getName().equals(TAG_FAMILY)) {
                throw new java.io.IOException("Unexpected parser state: must be START_TAG with family");
            }
            java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "name");
            if (attributeValue == null) {
                throw new java.io.IOException("name attribute is missing in family tag.");
            }
            while (true) {
                int next = xmlPullParser.next();
                if (next == 1) {
                    break;
                }
                if (next == 2 && xmlPullParser.getName().equals("font")) {
                    arrayList.add(android.graphics.fonts.FontUpdateRequest.Font.readFromXml(xmlPullParser));
                } else if (next == 3 && xmlPullParser.getName().equals(TAG_FAMILY)) {
                    break;
                }
            }
            return new android.graphics.fonts.FontUpdateRequest.Family(attributeValue, arrayList);
        }

        public java.lang.String getName() {
            return this.mName;
        }

        public java.util.List<android.graphics.fonts.FontUpdateRequest.Font> getFonts() {
            return this.mFonts;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.graphics.fonts.FontUpdateRequest.Family family = (android.graphics.fonts.FontUpdateRequest.Family) obj;
            if (this.mName.equals(family.mName) && this.mFonts.equals(family.mFonts)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(this.mName, this.mFonts);
        }

        public java.lang.String toString() {
            return "Family{mName='" + this.mName + android.text.format.DateFormat.QUOTE + ", mFonts=" + this.mFonts + '}';
        }
    }

    public FontUpdateRequest(android.os.ParcelFileDescriptor parcelFileDescriptor, byte[] bArr) {
        this.mType = 0;
        this.mFd = parcelFileDescriptor;
        this.mSignature = bArr;
        this.mFontFamily = null;
    }

    public FontUpdateRequest(android.graphics.fonts.FontUpdateRequest.Family family) {
        this.mType = 1;
        this.mFd = null;
        this.mSignature = null;
        this.mFontFamily = family;
    }

    public FontUpdateRequest(java.lang.String str, java.util.List<android.graphics.fonts.FontFamilyUpdateRequest.Font> list) {
        this(createFontFamily(str, list));
    }

    private static android.graphics.fonts.FontUpdateRequest.Family createFontFamily(java.lang.String str, java.util.List<android.graphics.fonts.FontFamilyUpdateRequest.Font> list) {
        java.util.ArrayList arrayList = new java.util.ArrayList(list.size());
        for (android.graphics.fonts.FontFamilyUpdateRequest.Font font : list) {
            arrayList.add(new android.graphics.fonts.FontUpdateRequest.Font(font.getPostScriptName(), font.getStyle(), font.getIndex(), android.graphics.fonts.FontVariationAxis.toFontVariationSettings(font.getAxes())));
        }
        return new android.graphics.fonts.FontUpdateRequest.Family(str, arrayList);
    }

    protected FontUpdateRequest(android.os.Parcel parcel) {
        this.mType = parcel.readInt();
        this.mFd = (android.os.ParcelFileDescriptor) parcel.readParcelable(android.os.ParcelFileDescriptor.class.getClassLoader(), android.os.ParcelFileDescriptor.class);
        this.mSignature = parcel.readBlob();
        this.mFontFamily = (android.graphics.fonts.FontUpdateRequest.Family) parcel.readParcelable(android.text.FontConfig.FontFamily.class.getClassLoader(), android.graphics.fonts.FontUpdateRequest.Family.class);
    }

    public int getType() {
        return this.mType;
    }

    public android.os.ParcelFileDescriptor getFd() {
        return this.mFd;
    }

    public byte[] getSignature() {
        return this.mSignature;
    }

    public android.graphics.fonts.FontUpdateRequest.Family getFontFamily() {
        return this.mFontFamily;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        if (this.mFd != null) {
            return this.mFd.describeContents();
        }
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeParcelable(this.mFd, i);
        parcel.writeBlob(this.mSignature);
        parcel.writeParcelable(this.mFontFamily, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getAttributeValueInt(org.xmlpull.v1.XmlPullParser xmlPullParser, java.lang.String str, int i) {
        try {
            java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, str);
            if (attributeValue == null) {
                return i;
            }
            return java.lang.Integer.parseInt(attributeValue);
        } catch (java.lang.NumberFormatException e) {
            return i;
        }
    }
}
