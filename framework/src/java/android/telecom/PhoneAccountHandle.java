package android.telecom;

/* loaded from: classes3.dex */
public final class PhoneAccountHandle implements android.os.Parcelable {
    private final android.content.ComponentName mComponentName;
    private final java.lang.String mId;
    private final android.os.UserHandle mUserHandle;
    private static final android.content.ComponentName TELEPHONY_COMPONENT_NAME = new android.content.ComponentName("com.android.phone", "com.android.services.telephony.TelephonyConnectionService");
    public static final android.os.Parcelable.Creator<android.telecom.PhoneAccountHandle> CREATOR = new android.os.Parcelable.Creator<android.telecom.PhoneAccountHandle>() { // from class: android.telecom.PhoneAccountHandle.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.PhoneAccountHandle createFromParcel(android.os.Parcel parcel) {
            return new android.telecom.PhoneAccountHandle(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.PhoneAccountHandle[] newArray(int i) {
            return new android.telecom.PhoneAccountHandle[i];
        }
    };

    public PhoneAccountHandle(android.content.ComponentName componentName, java.lang.String str) {
        this(componentName, str, android.os.Process.myUserHandle());
    }

    public PhoneAccountHandle(android.content.ComponentName componentName, java.lang.String str, android.os.UserHandle userHandle) {
        checkParameters(componentName, userHandle);
        this.mComponentName = componentName;
        this.mId = str;
        this.mUserHandle = userHandle;
    }

    public android.content.ComponentName getComponentName() {
        return this.mComponentName;
    }

    public java.lang.String getId() {
        return this.mId;
    }

    public android.os.UserHandle getUserHandle() {
        return this.mUserHandle;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mComponentName, this.mId, this.mUserHandle);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder append = new java.lang.StringBuilder().append(this.mComponentName).append(", ");
        if (TELEPHONY_COMPONENT_NAME.equals(this.mComponentName)) {
            append.append(this.mId);
        } else {
            append.append(android.telecom.Log.pii(this.mId));
        }
        append.append(", ");
        append.append(this.mUserHandle);
        return append.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (obj != null && (obj instanceof android.telecom.PhoneAccountHandle)) {
            android.telecom.PhoneAccountHandle phoneAccountHandle = (android.telecom.PhoneAccountHandle) obj;
            if (java.util.Objects.equals(phoneAccountHandle.getComponentName(), getComponentName()) && java.util.Objects.equals(phoneAccountHandle.getId(), getId()) && java.util.Objects.equals(phoneAccountHandle.getUserHandle(), getUserHandle())) {
                return true;
            }
        }
        return false;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        this.mComponentName.writeToParcel(parcel, i);
        parcel.writeString(this.mId);
        this.mUserHandle.writeToParcel(parcel, i);
    }

    private void checkParameters(android.content.ComponentName componentName, android.os.UserHandle userHandle) {
        if (componentName == null) {
            android.util.Log.w("PhoneAccountHandle", new java.lang.Exception("PhoneAccountHandle has been created with null ComponentName!"));
        }
        if (userHandle == null) {
            android.util.Log.w("PhoneAccountHandle", new java.lang.Exception("PhoneAccountHandle has been created with null UserHandle!"));
        }
    }

    private PhoneAccountHandle(android.os.Parcel parcel) {
        this(android.content.ComponentName.CREATOR.createFromParcel(parcel), parcel.readString(), android.os.UserHandle.CREATOR.createFromParcel(parcel));
    }

    public static boolean areFromSamePackage(android.telecom.PhoneAccountHandle phoneAccountHandle, android.telecom.PhoneAccountHandle phoneAccountHandle2) {
        return java.util.Objects.equals(phoneAccountHandle != null ? phoneAccountHandle.getComponentName().getPackageName() : null, phoneAccountHandle2 != null ? phoneAccountHandle2.getComponentName().getPackageName() : null);
    }
}
