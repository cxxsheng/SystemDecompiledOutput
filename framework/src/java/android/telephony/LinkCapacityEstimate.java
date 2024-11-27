package android.telephony;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class LinkCapacityEstimate implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.LinkCapacityEstimate> CREATOR = new android.os.Parcelable.Creator() { // from class: android.telephony.LinkCapacityEstimate.1
        @Override // android.os.Parcelable.Creator
        public android.telephony.LinkCapacityEstimate createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.LinkCapacityEstimate(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public android.telephony.LinkCapacityEstimate[] newArray(int i) {
            return new android.telephony.LinkCapacityEstimate[i];
        }
    };
    public static final int INVALID = -1;
    public static final int LCE_TYPE_COMBINED = 2;
    public static final int LCE_TYPE_PRIMARY = 0;
    public static final int LCE_TYPE_SECONDARY = 1;
    private final int mDownlinkCapacityKbps;
    private final int mType;
    private final int mUplinkCapacityKbps;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface LceType {
    }

    public LinkCapacityEstimate(int i, int i2, int i3) {
        this.mDownlinkCapacityKbps = i2;
        this.mUplinkCapacityKbps = i3;
        this.mType = i;
    }

    public LinkCapacityEstimate(android.os.Parcel parcel) {
        this.mDownlinkCapacityKbps = parcel.readInt();
        this.mUplinkCapacityKbps = parcel.readInt();
        this.mType = parcel.readInt();
    }

    public int getType() {
        return this.mType;
    }

    public int getDownlinkCapacityKbps() {
        return this.mDownlinkCapacityKbps;
    }

    public int getUplinkCapacityKbps() {
        return this.mUplinkCapacityKbps;
    }

    public java.lang.String toString() {
        return "{mType=" + this.mType + ", mDownlinkCapacityKbps=" + this.mDownlinkCapacityKbps + ", mUplinkCapacityKbps=" + this.mUplinkCapacityKbps + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mDownlinkCapacityKbps);
        parcel.writeInt(this.mUplinkCapacityKbps);
        parcel.writeInt(this.mType);
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.telephony.LinkCapacityEstimate) || hashCode() != obj.hashCode()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        android.telephony.LinkCapacityEstimate linkCapacityEstimate = (android.telephony.LinkCapacityEstimate) obj;
        if (this.mDownlinkCapacityKbps != linkCapacityEstimate.mDownlinkCapacityKbps || this.mUplinkCapacityKbps != linkCapacityEstimate.mUplinkCapacityKbps || this.mType != linkCapacityEstimate.mType) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mDownlinkCapacityKbps), java.lang.Integer.valueOf(this.mUplinkCapacityKbps), java.lang.Integer.valueOf(this.mType));
    }
}
