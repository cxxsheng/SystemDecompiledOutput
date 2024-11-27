package android.hardware.radio.data;

/* loaded from: classes2.dex */
public class SlicingConfig implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.data.SlicingConfig> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.data.SlicingConfig>() { // from class: android.hardware.radio.data.SlicingConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.data.SlicingConfig createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.data.SlicingConfig slicingConfig = new android.hardware.radio.data.SlicingConfig();
            slicingConfig.readFromParcel(parcel);
            return slicingConfig;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.data.SlicingConfig[] newArray(int i) {
            return new android.hardware.radio.data.SlicingConfig[i];
        }
    };
    public android.hardware.radio.data.SliceInfo[] sliceInfo;
    public android.hardware.radio.data.UrspRule[] urspRules;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedArray(this.urspRules, i);
        parcel.writeTypedArray(this.sliceInfo, i);
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
            this.urspRules = (android.hardware.radio.data.UrspRule[]) parcel.createTypedArray(android.hardware.radio.data.UrspRule.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.sliceInfo = (android.hardware.radio.data.SliceInfo[]) parcel.createTypedArray(android.hardware.radio.data.SliceInfo.CREATOR);
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
        stringJoiner.add("urspRules: " + java.util.Arrays.toString(this.urspRules));
        stringJoiner.add("sliceInfo: " + java.util.Arrays.toString(this.sliceInfo));
        return "SlicingConfig" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.urspRules) | 0 | describeContents(this.sliceInfo);
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
