package android.telephony;

/* loaded from: classes3.dex */
public class ModemInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.ModemInfo> CREATOR = new android.os.Parcelable.Creator() { // from class: android.telephony.ModemInfo.1
        @Override // android.os.Parcelable.Creator
        public android.telephony.ModemInfo createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.ModemInfo(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public android.telephony.ModemInfo[] newArray(int i) {
            return new android.telephony.ModemInfo[i];
        }
    };
    public final boolean isDataSupported;
    public final boolean isVoiceSupported;
    public final int modemId;
    public final int rat;

    public ModemInfo(int i) {
        this(i, 0, true, true);
    }

    public ModemInfo(int i, int i2, boolean z, boolean z2) {
        this.modemId = i;
        this.rat = i2;
        this.isVoiceSupported = z;
        this.isDataSupported = z2;
    }

    public ModemInfo(android.os.Parcel parcel) {
        this.modemId = parcel.readInt();
        this.rat = parcel.readInt();
        this.isVoiceSupported = parcel.readBoolean();
        this.isDataSupported = parcel.readBoolean();
    }

    public java.lang.String toString() {
        return "modemId=" + this.modemId + " rat=" + this.rat + " isVoiceSupported:" + this.isVoiceSupported + " isDataSupported:" + this.isDataSupported;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.modemId), java.lang.Integer.valueOf(this.rat), java.lang.Boolean.valueOf(this.isVoiceSupported), java.lang.Boolean.valueOf(this.isDataSupported));
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.telephony.ModemInfo) || hashCode() != obj.hashCode()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        android.telephony.ModemInfo modemInfo = (android.telephony.ModemInfo) obj;
        if (this.modemId != modemInfo.modemId || this.rat != modemInfo.rat || this.isVoiceSupported != modemInfo.isVoiceSupported || this.isDataSupported != modemInfo.isDataSupported) {
            return false;
        }
        return true;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.modemId);
        parcel.writeInt(this.rat);
        parcel.writeBoolean(this.isVoiceSupported);
        parcel.writeBoolean(this.isDataSupported);
    }
}
