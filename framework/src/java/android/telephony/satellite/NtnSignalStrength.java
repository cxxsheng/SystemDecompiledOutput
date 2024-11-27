package android.telephony.satellite;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class NtnSignalStrength implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.satellite.NtnSignalStrength> CREATOR = new android.os.Parcelable.Creator<android.telephony.satellite.NtnSignalStrength>() { // from class: android.telephony.satellite.NtnSignalStrength.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.satellite.NtnSignalStrength createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.satellite.NtnSignalStrength(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.satellite.NtnSignalStrength[] newArray(int i) {
            return new android.telephony.satellite.NtnSignalStrength[i];
        }
    };
    public static final int NTN_SIGNAL_STRENGTH_GOOD = 3;
    public static final int NTN_SIGNAL_STRENGTH_GREAT = 4;
    public static final int NTN_SIGNAL_STRENGTH_MODERATE = 2;
    public static final int NTN_SIGNAL_STRENGTH_NONE = 0;
    public static final int NTN_SIGNAL_STRENGTH_POOR = 1;
    private int mLevel;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface NtnSignalStrengthLevel {
    }

    public NtnSignalStrength(int i) {
        this.mLevel = i;
    }

    public NtnSignalStrength(android.telephony.satellite.NtnSignalStrength ntnSignalStrength) {
        this.mLevel = ntnSignalStrength == null ? 0 : ntnSignalStrength.getLevel();
    }

    private NtnSignalStrength(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getLevel() {
        return this.mLevel;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mLevel);
    }

    private void readFromParcel(android.os.Parcel parcel) {
        this.mLevel = parcel.readInt();
    }

    public int hashCode() {
        return this.mLevel;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass() && this.mLevel == ((android.telephony.satellite.NtnSignalStrength) obj).mLevel) {
            return true;
        }
        return false;
    }

    public java.lang.String toString() {
        return "NtnSignalStrength{mLevel=" + this.mLevel + '}';
    }
}
