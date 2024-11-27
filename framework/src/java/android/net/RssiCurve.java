package android.net;

@android.annotation.SystemApi
@java.lang.Deprecated
/* loaded from: classes2.dex */
public class RssiCurve implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.RssiCurve> CREATOR = new android.os.Parcelable.Creator<android.net.RssiCurve>() { // from class: android.net.RssiCurve.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.RssiCurve createFromParcel(android.os.Parcel parcel) {
            return new android.net.RssiCurve(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.RssiCurve[] newArray(int i) {
            return new android.net.RssiCurve[i];
        }
    };
    private static final int DEFAULT_ACTIVE_NETWORK_RSSI_BOOST = 25;
    public final int activeNetworkRssiBoost;
    public final int bucketWidth;
    public final byte[] rssiBuckets;
    public final int start;

    public RssiCurve(int i, int i2, byte[] bArr) {
        this(i, i2, bArr, 25);
    }

    public RssiCurve(int i, int i2, byte[] bArr, int i3) {
        this.start = i;
        this.bucketWidth = i2;
        if (bArr == null || bArr.length == 0) {
            throw new java.lang.IllegalArgumentException("rssiBuckets must be at least one element large.");
        }
        this.rssiBuckets = bArr;
        this.activeNetworkRssiBoost = i3;
    }

    private RssiCurve(android.os.Parcel parcel) {
        this.start = parcel.readInt();
        this.bucketWidth = parcel.readInt();
        this.rssiBuckets = new byte[parcel.readInt()];
        parcel.readByteArray(this.rssiBuckets);
        this.activeNetworkRssiBoost = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.start);
        parcel.writeInt(this.bucketWidth);
        parcel.writeInt(this.rssiBuckets.length);
        parcel.writeByteArray(this.rssiBuckets);
        parcel.writeInt(this.activeNetworkRssiBoost);
    }

    public byte lookupScore(int i) {
        return lookupScore(i, false);
    }

    public byte lookupScore(int i, boolean z) {
        if (z) {
            i += this.activeNetworkRssiBoost;
        }
        int i2 = (i - this.start) / this.bucketWidth;
        if (i2 < 0) {
            i2 = 0;
        } else if (i2 > this.rssiBuckets.length - 1) {
            i2 = this.rssiBuckets.length - 1;
        }
        return this.rssiBuckets[i2];
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.net.RssiCurve rssiCurve = (android.net.RssiCurve) obj;
        if (this.start == rssiCurve.start && this.bucketWidth == rssiCurve.bucketWidth && java.util.Arrays.equals(this.rssiBuckets, rssiCurve.rssiBuckets) && this.activeNetworkRssiBoost == rssiCurve.activeNetworkRssiBoost) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.start), java.lang.Integer.valueOf(this.bucketWidth), java.lang.Integer.valueOf(this.activeNetworkRssiBoost)) ^ java.util.Arrays.hashCode(this.rssiBuckets);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("RssiCurve[start=").append(this.start).append(",bucketWidth=").append(this.bucketWidth).append(",activeNetworkRssiBoost=").append(this.activeNetworkRssiBoost);
        sb.append(",buckets=");
        for (int i = 0; i < this.rssiBuckets.length; i++) {
            sb.append((int) this.rssiBuckets[i]);
            if (i < this.rssiBuckets.length - 1) {
                sb.append(",");
            }
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }
}
