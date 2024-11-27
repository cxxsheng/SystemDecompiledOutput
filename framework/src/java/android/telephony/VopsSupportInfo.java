package android.telephony;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class VopsSupportInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.VopsSupportInfo> CREATOR = new android.os.Parcelable.Creator<android.telephony.VopsSupportInfo>() { // from class: android.telephony.VopsSupportInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.VopsSupportInfo createFromParcel(android.os.Parcel parcel) {
            switch (parcel.readInt()) {
                case 3:
                    return android.telephony.LteVopsSupportInfo.createFromParcelBody(parcel);
                case 6:
                    return android.telephony.NrVopsSupportInfo.createFromParcelBody(parcel);
                default:
                    throw new java.lang.RuntimeException("Bad VopsSupportInfo Parcel");
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.VopsSupportInfo[] newArray(int i) {
            return new android.telephony.VopsSupportInfo[i];
        }
    };

    public abstract boolean equals(java.lang.Object obj);

    public abstract int hashCode();

    public abstract boolean isEmergencyServiceFallbackSupported();

    public abstract boolean isEmergencyServiceSupported();

    public abstract boolean isVopsSupported();

    @Override // android.os.Parcelable
    public abstract void writeToParcel(android.os.Parcel parcel, int i);

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected void writeToParcel(android.os.Parcel parcel, int i, int i2) {
        parcel.writeInt(i2);
    }
}
