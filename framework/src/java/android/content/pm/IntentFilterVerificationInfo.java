package android.content.pm;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class IntentFilterVerificationInfo implements android.os.Parcelable {
    private static final java.lang.String ATTR_DOMAIN_NAME = "name";
    private static final java.lang.String ATTR_PACKAGE_NAME = "packageName";
    private static final java.lang.String ATTR_STATUS = "status";
    private static final java.lang.String TAG_DOMAIN = "domain";
    private android.util.ArraySet<java.lang.String> mDomains;
    private java.lang.String mPackageName;
    private int mStatus;
    private static final java.lang.String TAG = android.content.pm.IntentFilterVerificationInfo.class.getName();
    public static final android.os.Parcelable.Creator<android.content.pm.IntentFilterVerificationInfo> CREATOR = new android.os.Parcelable.Creator<android.content.pm.IntentFilterVerificationInfo>() { // from class: android.content.pm.IntentFilterVerificationInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.IntentFilterVerificationInfo createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.IntentFilterVerificationInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.IntentFilterVerificationInfo[] newArray(int i) {
            return new android.content.pm.IntentFilterVerificationInfo[i];
        }
    };

    public IntentFilterVerificationInfo() {
        this.mDomains = new android.util.ArraySet<>();
        this.mPackageName = null;
        this.mStatus = 0;
    }

    public IntentFilterVerificationInfo(java.lang.String str, android.util.ArraySet<java.lang.String> arraySet) {
        this.mDomains = new android.util.ArraySet<>();
        this.mPackageName = str;
        this.mDomains = arraySet;
        this.mStatus = 0;
    }

    public IntentFilterVerificationInfo(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        this.mDomains = new android.util.ArraySet<>();
        readFromXml(typedXmlPullParser);
    }

    public IntentFilterVerificationInfo(android.os.Parcel parcel) {
        this.mDomains = new android.util.ArraySet<>();
        readFromParcel(parcel);
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public void setStatus(int i) {
        if (i >= 0 && i <= 3) {
            this.mStatus = i;
        } else {
            android.util.Log.w(TAG, "Trying to set a non supported status: " + i);
        }
    }

    public java.util.Set<java.lang.String> getDomains() {
        return this.mDomains;
    }

    public void setDomains(android.util.ArraySet<java.lang.String> arraySet) {
        this.mDomains = arraySet;
    }

    public java.lang.String getDomainsString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        java.util.Iterator<java.lang.String> it = this.mDomains.iterator();
        while (it.hasNext()) {
            java.lang.String next = it.next();
            if (sb.length() > 0) {
                sb.append(" ");
            }
            sb.append(next);
        }
        return sb.toString();
    }

    java.lang.String getStringFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str, java.lang.String str2) {
        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue(null, str);
        if (attributeValue == null) {
            android.util.Log.w(TAG, "Missing element under " + TAG + ": " + str + " at " + typedXmlPullParser.getPositionDescription());
            return str2;
        }
        return attributeValue;
    }

    int getIntFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str, int i) {
        return typedXmlPullParser.getAttributeInt(null, str, i);
    }

    public void readFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        this.mPackageName = getStringFromXml(typedXmlPullParser, "packageName", null);
        if (this.mPackageName == null) {
            android.util.Log.e(TAG, "Package name cannot be null!");
        }
        int intFromXml = getIntFromXml(typedXmlPullParser, "status", -1);
        if (intFromXml == -1) {
            android.util.Log.e(TAG, "Unknown status value: " + intFromXml);
        }
        this.mStatus = intFromXml;
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next != 1) {
                if (next != 3 || typedXmlPullParser.getDepth() > depth) {
                    if (next != 3 && next != 4) {
                        java.lang.String name = typedXmlPullParser.getName();
                        if (name.equals(TAG_DOMAIN)) {
                            java.lang.String stringFromXml = getStringFromXml(typedXmlPullParser, "name", null);
                            if (!android.text.TextUtils.isEmpty(stringFromXml)) {
                                this.mDomains.add(stringFromXml);
                            }
                        } else {
                            android.util.Log.w(TAG, "Unknown tag parsing IntentFilter: " + name);
                        }
                        com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    public void writeToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        typedXmlSerializer.attribute(null, "packageName", this.mPackageName);
        typedXmlSerializer.attributeInt(null, "status", this.mStatus);
        java.util.Iterator<java.lang.String> it = this.mDomains.iterator();
        while (it.hasNext()) {
            java.lang.String next = it.next();
            typedXmlSerializer.startTag(null, TAG_DOMAIN);
            typedXmlSerializer.attribute(null, "name", next);
            typedXmlSerializer.endTag(null, TAG_DOMAIN);
        }
    }

    public java.lang.String getStatusString() {
        return getStatusStringFromValue(this.mStatus << 32);
    }

    public static java.lang.String getStatusStringFromValue(long j) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        switch ((int) (j >> 32)) {
            case 1:
                sb.append("ask");
                break;
            case 2:
                sb.append("always : ");
                sb.append(java.lang.Long.toHexString(j & (-1)));
                break;
            case 3:
                sb.append("never");
                break;
            case 4:
                sb.append("always-ask");
                break;
            default:
                sb.append(android.hardware.input.KeyboardLayout.LAYOUT_TYPE_UNDEFINED);
                break;
        }
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private void readFromParcel(android.os.Parcel parcel) {
        this.mPackageName = parcel.readString();
        this.mStatus = parcel.readInt();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        parcel.readStringList(arrayList);
        this.mDomains.addAll(arrayList);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mPackageName);
        parcel.writeInt(this.mStatus);
        parcel.writeStringList(new java.util.ArrayList(this.mDomains));
    }
}
