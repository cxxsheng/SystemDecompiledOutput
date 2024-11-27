package android.telephony;

/* loaded from: classes3.dex */
public class VoiceSpecificRegistrationInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.VoiceSpecificRegistrationInfo> CREATOR = new android.os.Parcelable.Creator<android.telephony.VoiceSpecificRegistrationInfo>() { // from class: android.telephony.VoiceSpecificRegistrationInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.VoiceSpecificRegistrationInfo createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.VoiceSpecificRegistrationInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.VoiceSpecificRegistrationInfo[] newArray(int i) {
            return new android.telephony.VoiceSpecificRegistrationInfo[i];
        }
    };
    public final boolean cssSupported;
    public final int defaultRoamingIndicator;
    public final int roamingIndicator;
    public final int systemIsInPrl;

    VoiceSpecificRegistrationInfo(boolean z, int i, int i2, int i3) {
        this.cssSupported = z;
        this.roamingIndicator = i;
        this.systemIsInPrl = i2;
        this.defaultRoamingIndicator = i3;
    }

    VoiceSpecificRegistrationInfo(android.telephony.VoiceSpecificRegistrationInfo voiceSpecificRegistrationInfo) {
        this.cssSupported = voiceSpecificRegistrationInfo.cssSupported;
        this.roamingIndicator = voiceSpecificRegistrationInfo.roamingIndicator;
        this.systemIsInPrl = voiceSpecificRegistrationInfo.systemIsInPrl;
        this.defaultRoamingIndicator = voiceSpecificRegistrationInfo.defaultRoamingIndicator;
    }

    private VoiceSpecificRegistrationInfo(android.os.Parcel parcel) {
        this.cssSupported = parcel.readBoolean();
        this.roamingIndicator = parcel.readInt();
        this.systemIsInPrl = parcel.readInt();
        this.defaultRoamingIndicator = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeBoolean(this.cssSupported);
        parcel.writeInt(this.roamingIndicator);
        parcel.writeInt(this.systemIsInPrl);
        parcel.writeInt(this.defaultRoamingIndicator);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "VoiceSpecificRegistrationInfo { mCssSupported=" + this.cssSupported + " mRoamingIndicator=" + this.roamingIndicator + " mSystemIsInPrl=" + this.systemIsInPrl + " mDefaultRoamingIndicator=" + this.defaultRoamingIndicator + "}";
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Boolean.valueOf(this.cssSupported), java.lang.Integer.valueOf(this.roamingIndicator), java.lang.Integer.valueOf(this.systemIsInPrl), java.lang.Integer.valueOf(this.defaultRoamingIndicator));
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.telephony.VoiceSpecificRegistrationInfo)) {
            return false;
        }
        android.telephony.VoiceSpecificRegistrationInfo voiceSpecificRegistrationInfo = (android.telephony.VoiceSpecificRegistrationInfo) obj;
        if (this.cssSupported == voiceSpecificRegistrationInfo.cssSupported && this.roamingIndicator == voiceSpecificRegistrationInfo.roamingIndicator && this.systemIsInPrl == voiceSpecificRegistrationInfo.systemIsInPrl && this.defaultRoamingIndicator == voiceSpecificRegistrationInfo.defaultRoamingIndicator) {
            return true;
        }
        return false;
    }
}
