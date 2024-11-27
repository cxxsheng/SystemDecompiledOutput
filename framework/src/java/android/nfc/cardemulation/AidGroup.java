package android.nfc.cardemulation;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class AidGroup implements android.os.Parcelable {
    private static final int MAX_NUM_AIDS = 256;
    private static final java.lang.String TAG = "AidGroup";
    private final java.util.List<java.lang.String> mAids;
    private final java.lang.String mCategory;
    private final java.lang.String mDescription;
    public static final android.os.Parcelable.Creator<android.nfc.cardemulation.AidGroup> CREATOR = new android.os.Parcelable.Creator<android.nfc.cardemulation.AidGroup>() { // from class: android.nfc.cardemulation.AidGroup.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.nfc.cardemulation.AidGroup createFromParcel(android.os.Parcel parcel) {
            java.lang.String readString8 = parcel.readString8();
            int readInt = parcel.readInt();
            java.util.ArrayList arrayList = new java.util.ArrayList();
            if (readInt > 0) {
                parcel.readStringList(arrayList);
            }
            return new android.nfc.cardemulation.AidGroup(arrayList, readString8);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.nfc.cardemulation.AidGroup[] newArray(int i) {
            return new android.nfc.cardemulation.AidGroup[i];
        }
    };
    private static final java.util.regex.Pattern AID_PATTERN = java.util.regex.Pattern.compile("[0-9A-Fa-f]{10,32}\\*?\\#?");

    public AidGroup(java.util.List<java.lang.String> list, java.lang.String str) {
        if (list == null || list.size() == 0) {
            throw new java.lang.IllegalArgumentException("No AIDS in AID group.");
        }
        if (list.size() > 256) {
            throw new java.lang.IllegalArgumentException("Too many AIDs in AID group.");
        }
        for (java.lang.String str2 : list) {
            if (!isValidAid(str2)) {
                throw new java.lang.IllegalArgumentException("AID " + str2 + " is not a valid AID.");
            }
        }
        if (isValidCategory(str)) {
            this.mCategory = str;
        } else {
            this.mCategory = "other";
        }
        this.mAids = new java.util.ArrayList(list.size());
        java.util.Iterator<java.lang.String> it = list.iterator();
        while (it.hasNext()) {
            this.mAids.add(it.next().toUpperCase(java.util.Locale.US));
        }
        this.mDescription = null;
    }

    AidGroup(java.lang.String str, java.lang.String str2) {
        this.mAids = new java.util.ArrayList();
        this.mCategory = str;
        this.mDescription = str2;
    }

    public java.lang.String getCategory() {
        return this.mCategory;
    }

    public java.util.List<java.lang.String> getAids() {
        return this.mAids;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("Category: " + this.mCategory + ", AIDs:");
        java.util.Iterator<java.lang.String> it = this.mAids.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            sb.append(", ");
        }
        return sb.toString();
    }

    public void dump(android.util.proto.ProtoOutputStream protoOutputStream) {
        protoOutputStream.write(1138166333441L, this.mCategory);
        java.util.Iterator<java.lang.String> it = this.mAids.iterator();
        while (it.hasNext()) {
            protoOutputStream.write(2237677961218L, it.next());
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString8(this.mCategory);
        parcel.writeInt(this.mAids.size());
        if (this.mAids.size() > 0) {
            parcel.writeStringList(this.mAids);
        }
    }

    public static android.nfc.cardemulation.AidGroup createFromXml(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int eventType = xmlPullParser.getEventType();
        int depth = xmlPullParser.getDepth();
        boolean z = false;
        java.lang.String str = null;
        while (eventType != 1 && xmlPullParser.getDepth() >= depth) {
            java.lang.String name = xmlPullParser.getName();
            if (eventType == 2) {
                if (name.equals("aid")) {
                    if (z) {
                        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "value");
                        if (attributeValue != null) {
                            arrayList.add(attributeValue.toUpperCase());
                        }
                    } else {
                        android.util.Log.d(TAG, "Ignoring <aid> tag while not in group");
                    }
                } else if (name.equals("aid-group")) {
                    str = xmlPullParser.getAttributeValue(null, "category");
                    if (str == null) {
                        android.util.Log.e(TAG, "<aid-group> tag without valid category");
                        return null;
                    }
                    z = true;
                } else {
                    android.util.Log.d(TAG, "Ignoring unexpected tag: " + name);
                }
            } else if (eventType == 3 && name.equals("aid-group") && z && arrayList.size() > 0) {
                return new android.nfc.cardemulation.AidGroup(arrayList, str);
            }
            eventType = xmlPullParser.next();
        }
        return null;
    }

    public void writeAsXml(org.xmlpull.v1.XmlSerializer xmlSerializer) throws java.io.IOException {
        xmlSerializer.startTag(null, "aid-group");
        xmlSerializer.attribute(null, "category", this.mCategory);
        for (java.lang.String str : this.mAids) {
            xmlSerializer.startTag(null, "aid");
            xmlSerializer.attribute(null, "value", str);
            xmlSerializer.endTag(null, "aid");
        }
        xmlSerializer.endTag(null, "aid-group");
    }

    private static boolean isValidCategory(java.lang.String str) {
        return "payment".equals(str) || "other".equals(str);
    }

    private static boolean isValidAid(java.lang.String str) {
        if (str == null) {
            return false;
        }
        if ((str.endsWith("*") || str.endsWith("#")) && str.length() % 2 == 0) {
            android.util.Log.e(TAG, "AID " + str + " is not a valid AID.");
            return false;
        }
        if (!str.endsWith("*") && !str.endsWith("#") && str.length() % 2 != 0) {
            android.util.Log.e(TAG, "AID " + str + " is not a valid AID.");
            return false;
        }
        if (!AID_PATTERN.matcher(str).matches()) {
            android.util.Log.e(TAG, "AID " + str + " is not a valid AID.");
            return false;
        }
        return true;
    }
}
