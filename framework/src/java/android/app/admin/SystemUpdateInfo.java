package android.app.admin;

/* loaded from: classes.dex */
public final class SystemUpdateInfo implements android.os.Parcelable {
    private static final java.lang.String ATTR_ORIGINAL_BUILD = "original-build";
    private static final java.lang.String ATTR_RECEIVED_TIME = "received-time";
    private static final java.lang.String ATTR_SECURITY_PATCH_STATE = "security-patch-state";
    public static final android.os.Parcelable.Creator<android.app.admin.SystemUpdateInfo> CREATOR = new android.os.Parcelable.Creator<android.app.admin.SystemUpdateInfo>() { // from class: android.app.admin.SystemUpdateInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.SystemUpdateInfo createFromParcel(android.os.Parcel parcel) {
            return new android.app.admin.SystemUpdateInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.SystemUpdateInfo[] newArray(int i) {
            return new android.app.admin.SystemUpdateInfo[i];
        }
    };
    public static final int SECURITY_PATCH_STATE_FALSE = 1;
    public static final int SECURITY_PATCH_STATE_TRUE = 2;
    public static final int SECURITY_PATCH_STATE_UNKNOWN = 0;
    private static final java.lang.String TAG = "SystemUpdateInfo";
    private final long mReceivedTime;
    private final int mSecurityPatchState;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SecurityPatchState {
    }

    private SystemUpdateInfo(long j, int i) {
        this.mReceivedTime = j;
        this.mSecurityPatchState = i;
    }

    private SystemUpdateInfo(android.os.Parcel parcel) {
        this.mReceivedTime = parcel.readLong();
        this.mSecurityPatchState = parcel.readInt();
    }

    public static android.app.admin.SystemUpdateInfo of(long j) {
        if (j == -1) {
            return null;
        }
        return new android.app.admin.SystemUpdateInfo(j, 0);
    }

    public static android.app.admin.SystemUpdateInfo of(long j, boolean z) {
        if (j == -1) {
            return null;
        }
        return new android.app.admin.SystemUpdateInfo(j, z ? 2 : 1);
    }

    public long getReceivedTime() {
        return this.mReceivedTime;
    }

    public int getSecurityPatchState() {
        return this.mSecurityPatchState;
    }

    public void writeToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.lang.String str) throws java.io.IOException {
        typedXmlSerializer.startTag(null, str);
        typedXmlSerializer.attributeLong(null, ATTR_RECEIVED_TIME, this.mReceivedTime);
        typedXmlSerializer.attributeInt(null, ATTR_SECURITY_PATCH_STATE, this.mSecurityPatchState);
        typedXmlSerializer.attribute(null, ATTR_ORIGINAL_BUILD, android.os.Build.VERSION.INCREMENTAL);
        typedXmlSerializer.endTag(null, str);
    }

    public static android.app.admin.SystemUpdateInfo readFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) {
        if (!android.os.Build.VERSION.INCREMENTAL.equals(typedXmlPullParser.getAttributeValue(null, ATTR_ORIGINAL_BUILD))) {
            return null;
        }
        try {
            return new android.app.admin.SystemUpdateInfo(typedXmlPullParser.getAttributeLong(null, ATTR_RECEIVED_TIME), typedXmlPullParser.getAttributeInt(null, ATTR_SECURITY_PATCH_STATE));
        } catch (org.xmlpull.v1.XmlPullParserException e) {
            android.util.Log.w(TAG, "Load xml failed", e);
            return null;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(getReceivedTime());
        parcel.writeInt(getSecurityPatchState());
    }

    public java.lang.String toString() {
        return java.lang.String.format("SystemUpdateInfo (receivedTime = %d, securityPatchState = %s)", java.lang.Long.valueOf(this.mReceivedTime), securityPatchStateToString(this.mSecurityPatchState));
    }

    private static java.lang.String securityPatchStateToString(int i) {
        switch (i) {
            case 0:
                return "unknown";
            case 1:
                return "false";
            case 2:
                return "true";
            default:
                throw new java.lang.IllegalArgumentException("Unrecognized security patch state: " + i);
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.app.admin.SystemUpdateInfo systemUpdateInfo = (android.app.admin.SystemUpdateInfo) obj;
        if (this.mReceivedTime == systemUpdateInfo.mReceivedTime && this.mSecurityPatchState == systemUpdateInfo.mSecurityPatchState) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Long.valueOf(this.mReceivedTime), java.lang.Integer.valueOf(this.mSecurityPatchState));
    }
}
