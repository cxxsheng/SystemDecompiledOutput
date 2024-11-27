package android.net.networkstack.aidl.ip;

/* loaded from: classes.dex */
public class ReachabilityLossInfoParcelable implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.networkstack.aidl.ip.ReachabilityLossInfoParcelable> CREATOR = new android.os.Parcelable.Creator<android.net.networkstack.aidl.ip.ReachabilityLossInfoParcelable>() { // from class: android.net.networkstack.aidl.ip.ReachabilityLossInfoParcelable.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.networkstack.aidl.ip.ReachabilityLossInfoParcelable createFromParcel(android.os.Parcel parcel) {
            return android.net.networkstack.aidl.ip.ReachabilityLossInfoParcelable.internalCreateFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.networkstack.aidl.ip.ReachabilityLossInfoParcelable[] newArray(int i) {
            return new android.net.networkstack.aidl.ip.ReachabilityLossInfoParcelable[i];
        }
    };
    public final java.lang.String message;
    public final int reason;

    public static final class Builder {
        private java.lang.String message;
        private int reason;

        public android.net.networkstack.aidl.ip.ReachabilityLossInfoParcelable.Builder setMessage(java.lang.String str) {
            this.message = str;
            return this;
        }

        public android.net.networkstack.aidl.ip.ReachabilityLossInfoParcelable.Builder setReason(int i) {
            this.reason = i;
            return this;
        }

        public android.net.networkstack.aidl.ip.ReachabilityLossInfoParcelable build() {
            return new android.net.networkstack.aidl.ip.ReachabilityLossInfoParcelable(this.message, this.reason);
        }
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.message);
        parcel.writeInt(this.reason);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public ReachabilityLossInfoParcelable(java.lang.String str, int i) {
        this.message = str;
        this.reason = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.net.networkstack.aidl.ip.ReachabilityLossInfoParcelable internalCreateFromParcel(android.os.Parcel parcel) {
        int i;
        android.net.networkstack.aidl.ip.ReachabilityLossInfoParcelable.Builder builder = new android.net.networkstack.aidl.ip.ReachabilityLossInfoParcelable.Builder();
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
            builder.setMessage(parcel.readString());
            if (parcel.dataPosition() - dataPosition >= readInt) {
                builder.build();
                if (dataPosition > i) {
                    throw new android.os.BadParcelableException(r4);
                }
            } else {
                builder.setReason(parcel.readInt());
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
            }
        }
        parcel.setDataPosition(dataPosition + readInt);
        return builder.build();
    }

    public java.lang.String toString() {
        java.util.StringJoiner stringJoiner = new java.util.StringJoiner(", ", "{", "}");
        stringJoiner.add("message: " + java.util.Objects.toString(this.message));
        stringJoiner.add("reason: " + this.reason);
        return "ReachabilityLossInfoParcelable" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.net.networkstack.aidl.ip.ReachabilityLossInfoParcelable)) {
            return false;
        }
        android.net.networkstack.aidl.ip.ReachabilityLossInfoParcelable reachabilityLossInfoParcelable = (android.net.networkstack.aidl.ip.ReachabilityLossInfoParcelable) obj;
        if (java.util.Objects.deepEquals(this.message, reachabilityLossInfoParcelable.message) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.reason), java.lang.Integer.valueOf(reachabilityLossInfoParcelable.reason))) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(this.message, java.lang.Integer.valueOf(this.reason)).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
