package android.net.netd.aidl;

/* loaded from: classes.dex */
public class NativeUidRangeConfig implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.netd.aidl.NativeUidRangeConfig> CREATOR = new android.os.Parcelable.Creator<android.net.netd.aidl.NativeUidRangeConfig>() { // from class: android.net.netd.aidl.NativeUidRangeConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.netd.aidl.NativeUidRangeConfig createFromParcel(android.os.Parcel parcel) {
            return android.net.netd.aidl.NativeUidRangeConfig.internalCreateFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.netd.aidl.NativeUidRangeConfig[] newArray(int i) {
            return new android.net.netd.aidl.NativeUidRangeConfig[i];
        }
    };
    public final int netId;
    public final int subPriority;
    public final android.net.UidRangeParcel[] uidRanges;

    public static final class Builder {
        private int netId = 0;
        private int subPriority = 0;
        private android.net.UidRangeParcel[] uidRanges;

        public android.net.netd.aidl.NativeUidRangeConfig.Builder setNetId(int i) {
            this.netId = i;
            return this;
        }

        public android.net.netd.aidl.NativeUidRangeConfig.Builder setUidRanges(android.net.UidRangeParcel[] uidRangeParcelArr) {
            this.uidRanges = uidRangeParcelArr;
            return this;
        }

        public android.net.netd.aidl.NativeUidRangeConfig.Builder setSubPriority(int i) {
            this.subPriority = i;
            return this;
        }

        public android.net.netd.aidl.NativeUidRangeConfig build() {
            return new android.net.netd.aidl.NativeUidRangeConfig(this.netId, this.uidRanges, this.subPriority);
        }
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.netId);
        parcel.writeTypedArray(this.uidRanges, i);
        parcel.writeInt(this.subPriority);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public NativeUidRangeConfig(int i, android.net.UidRangeParcel[] uidRangeParcelArr, int i2) {
        this.netId = i;
        this.uidRanges = uidRangeParcelArr;
        this.subPriority = i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.net.netd.aidl.NativeUidRangeConfig internalCreateFromParcel(android.os.Parcel parcel) {
        int i;
        android.net.netd.aidl.NativeUidRangeConfig.Builder builder = new android.net.netd.aidl.NativeUidRangeConfig.Builder();
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
        } finally {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                android.os.BadParcelableException badParcelableException = new android.os.BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            return builder.build();
        }
        if (readInt < 4) {
            throw new android.os.BadParcelableException("Parcelable too small");
        }
        builder.build();
        if (parcel.dataPosition() - dataPosition >= readInt) {
            builder.build();
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new android.os.BadParcelableException("Overflow in the size of parcelable");
            }
        } else {
            builder.setNetId(parcel.readInt());
            if (parcel.dataPosition() - dataPosition >= readInt) {
                builder.build();
                if (dataPosition > i) {
                    throw new android.os.BadParcelableException(r4);
                }
            } else {
                builder.setUidRanges((android.net.UidRangeParcel[]) parcel.createTypedArray(android.net.UidRangeParcel.CREATOR));
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    builder.build();
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                } else {
                    builder.setSubPriority(parcel.readInt());
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                }
            }
        }
        parcel.setDataPosition(dataPosition + readInt);
        return builder.build();
    }

    public java.lang.String toString() {
        java.util.StringJoiner stringJoiner = new java.util.StringJoiner(", ", "{", "}");
        stringJoiner.add("netId: " + this.netId);
        stringJoiner.add("uidRanges: " + java.util.Arrays.toString(this.uidRanges));
        stringJoiner.add("subPriority: " + this.subPriority);
        return "NativeUidRangeConfig" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.net.netd.aidl.NativeUidRangeConfig)) {
            return false;
        }
        android.net.netd.aidl.NativeUidRangeConfig nativeUidRangeConfig = (android.net.netd.aidl.NativeUidRangeConfig) obj;
        if (java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.netId), java.lang.Integer.valueOf(nativeUidRangeConfig.netId)) && java.util.Objects.deepEquals(this.uidRanges, nativeUidRangeConfig.uidRanges) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.subPriority), java.lang.Integer.valueOf(nativeUidRangeConfig.subPriority))) {
            return true;
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(java.lang.Integer.valueOf(this.netId), this.uidRanges, java.lang.Integer.valueOf(this.subPriority)).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.uidRanges) | 0;
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
