package android.hardware.broadcastradio;

/* loaded from: classes.dex */
public class AmFmRegionConfig implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.broadcastradio.AmFmRegionConfig> CREATOR = new android.os.Parcelable.Creator<android.hardware.broadcastradio.AmFmRegionConfig>() { // from class: android.hardware.broadcastradio.AmFmRegionConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.broadcastradio.AmFmRegionConfig createFromParcel(android.os.Parcel parcel) {
            android.hardware.broadcastradio.AmFmRegionConfig amFmRegionConfig = new android.hardware.broadcastradio.AmFmRegionConfig();
            amFmRegionConfig.readFromParcel(parcel);
            return amFmRegionConfig;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.broadcastradio.AmFmRegionConfig[] newArray(int i) {
            return new android.hardware.broadcastradio.AmFmRegionConfig[i];
        }
    };
    public static final int DEEMPHASIS_D50 = 1;
    public static final int DEEMPHASIS_D75 = 2;
    public static final int RBDS = 2;
    public static final int RDS = 1;
    public int fmDeemphasis = 0;
    public int fmRds = 0;
    public android.hardware.broadcastradio.AmFmBandRange[] ranges;

    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedArray(this.ranges, i);
        parcel.writeInt(this.fmDeemphasis);
        parcel.writeInt(this.fmRds);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public final void readFromParcel(android.os.Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new android.os.BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.ranges = (android.hardware.broadcastradio.AmFmBandRange[]) parcel.createTypedArray(android.hardware.broadcastradio.AmFmBandRange.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.fmDeemphasis = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.fmRds = parcel.readInt();
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            }
        } catch (java.lang.Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new android.os.BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            throw th;
        }
    }

    public java.lang.String toString() {
        java.util.StringJoiner stringJoiner = new java.util.StringJoiner(", ", "{", "}");
        stringJoiner.add("ranges: " + java.util.Arrays.toString(this.ranges));
        stringJoiner.add("fmDeemphasis: " + this.fmDeemphasis);
        stringJoiner.add("fmRds: " + this.fmRds);
        return "AmFmRegionConfig" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.hardware.broadcastradio.AmFmRegionConfig)) {
            return false;
        }
        android.hardware.broadcastradio.AmFmRegionConfig amFmRegionConfig = (android.hardware.broadcastradio.AmFmRegionConfig) obj;
        if (java.util.Objects.deepEquals(this.ranges, amFmRegionConfig.ranges) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.fmDeemphasis), java.lang.Integer.valueOf(amFmRegionConfig.fmDeemphasis)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.fmRds), java.lang.Integer.valueOf(amFmRegionConfig.fmRds))) {
            return true;
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(this.ranges, java.lang.Integer.valueOf(this.fmDeemphasis), java.lang.Integer.valueOf(this.fmRds)).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.ranges) | 0;
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof java.lang.Object[]) {
            int i = 0;
            for (java.lang.Object obj2 : (java.lang.Object[]) obj) {
                i |= describeContents(obj2);
            }
            return i;
        }
        if (!(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
