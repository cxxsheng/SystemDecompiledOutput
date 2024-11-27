package android.telephony;

/* loaded from: classes3.dex */
public class CellConfigLte implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.CellConfigLte> CREATOR = new android.os.Parcelable.Creator<android.telephony.CellConfigLte>() { // from class: android.telephony.CellConfigLte.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellConfigLte createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.CellConfigLte(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellConfigLte[] newArray(int i) {
            return new android.telephony.CellConfigLte[0];
        }
    };
    private final boolean mIsEndcAvailable;

    public CellConfigLte() {
        this.mIsEndcAvailable = false;
    }

    public CellConfigLte(boolean z) {
        this.mIsEndcAvailable = z;
    }

    public CellConfigLte(android.telephony.CellConfigLte cellConfigLte) {
        this.mIsEndcAvailable = cellConfigLte.mIsEndcAvailable;
    }

    boolean isEndcAvailable() {
        return this.mIsEndcAvailable;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Boolean.valueOf(this.mIsEndcAvailable));
    }

    public boolean equals(java.lang.Object obj) {
        return (obj instanceof android.telephony.CellConfigLte) && this.mIsEndcAvailable == ((android.telephony.CellConfigLte) obj).mIsEndcAvailable;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeBoolean(this.mIsEndcAvailable);
    }

    public java.lang.String toString() {
        return getClass().getName() + " :{" + (" isEndcAvailable = " + this.mIsEndcAvailable) + " }";
    }

    private CellConfigLte(android.os.Parcel parcel) {
        this.mIsEndcAvailable = parcel.readBoolean();
    }
}
