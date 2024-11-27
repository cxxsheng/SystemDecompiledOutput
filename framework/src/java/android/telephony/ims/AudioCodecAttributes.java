package android.telephony.ims;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class AudioCodecAttributes implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.ims.AudioCodecAttributes> CREATOR = new android.os.Parcelable.Creator<android.telephony.ims.AudioCodecAttributes>() { // from class: android.telephony.ims.AudioCodecAttributes.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.AudioCodecAttributes createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.ims.AudioCodecAttributes(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.AudioCodecAttributes[] newArray(int i) {
            return new android.telephony.ims.AudioCodecAttributes[i];
        }
    };
    private float mBandwidthKhz;
    private android.util.Range<java.lang.Float> mBandwidthRangeKhz;
    private float mBitrateKbps;
    private android.util.Range<java.lang.Float> mBitrateRangeKbps;

    public AudioCodecAttributes(float f, android.util.Range<java.lang.Float> range, float f2, android.util.Range<java.lang.Float> range2) {
        this.mBitrateKbps = f;
        this.mBitrateRangeKbps = range;
        this.mBandwidthKhz = f2;
        this.mBandwidthRangeKhz = range2;
    }

    private AudioCodecAttributes(android.os.Parcel parcel) {
        this.mBitrateKbps = parcel.readFloat();
        this.mBitrateRangeKbps = new android.util.Range<>(java.lang.Float.valueOf(parcel.readFloat()), java.lang.Float.valueOf(parcel.readFloat()));
        this.mBandwidthKhz = parcel.readFloat();
        this.mBandwidthRangeKhz = new android.util.Range<>(java.lang.Float.valueOf(parcel.readFloat()), java.lang.Float.valueOf(parcel.readFloat()));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeFloat(this.mBitrateKbps);
        parcel.writeFloat(this.mBitrateRangeKbps.getLower().floatValue());
        parcel.writeFloat(this.mBitrateRangeKbps.getUpper().floatValue());
        parcel.writeFloat(this.mBandwidthKhz);
        parcel.writeFloat(this.mBandwidthRangeKhz.getLower().floatValue());
        parcel.writeFloat(this.mBandwidthRangeKhz.getUpper().floatValue());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public float getBitrateKbps() {
        return this.mBitrateKbps;
    }

    public android.util.Range<java.lang.Float> getBitrateRangeKbps() {
        return this.mBitrateRangeKbps;
    }

    public float getBandwidthKhz() {
        return this.mBandwidthKhz;
    }

    public android.util.Range<java.lang.Float> getBandwidthRangeKhz() {
        return this.mBandwidthRangeKhz;
    }

    public java.lang.String toString() {
        return "{ bitrateKbps=" + this.mBitrateKbps + ", bitrateRangeKbps=" + this.mBitrateRangeKbps + ", bandwidthKhz=" + this.mBandwidthKhz + ", bandwidthRangeKhz=" + this.mBandwidthRangeKhz + " }";
    }
}
