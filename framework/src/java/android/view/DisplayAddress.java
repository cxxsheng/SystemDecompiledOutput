package android.view;

/* loaded from: classes4.dex */
public abstract class DisplayAddress implements android.os.Parcelable {
    public static android.view.DisplayAddress.Physical fromPhysicalDisplayId(long j) {
        return new android.view.DisplayAddress.Physical(j);
    }

    public static android.view.DisplayAddress.Physical fromPortAndModel(int i, java.lang.Long l) {
        return new android.view.DisplayAddress.Physical(i, l);
    }

    public static android.view.DisplayAddress.Network fromMacAddress(java.lang.String str) {
        return new android.view.DisplayAddress.Network(str);
    }

    public static final class Physical extends android.view.DisplayAddress {
        public static final android.os.Parcelable.Creator<android.view.DisplayAddress.Physical> CREATOR = new android.os.Parcelable.Creator<android.view.DisplayAddress.Physical>() { // from class: android.view.DisplayAddress.Physical.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.DisplayAddress.Physical createFromParcel(android.os.Parcel parcel) {
                return new android.view.DisplayAddress.Physical(parcel.readLong());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.DisplayAddress.Physical[] newArray(int i) {
                return new android.view.DisplayAddress.Physical[i];
            }
        };
        private static final int MODEL_SHIFT = 8;
        private static final long UNKNOWN_MODEL = 0;
        private final long mPhysicalDisplayId;

        public long getPhysicalDisplayId() {
            return this.mPhysicalDisplayId;
        }

        public int getPort() {
            return (int) (this.mPhysicalDisplayId & 255);
        }

        public java.lang.Long getModel() {
            long j = this.mPhysicalDisplayId >>> 8;
            if (j == 0) {
                return null;
            }
            return java.lang.Long.valueOf(j);
        }

        public boolean equals(java.lang.Object obj) {
            return (obj instanceof android.view.DisplayAddress.Physical) && this.mPhysicalDisplayId == ((android.view.DisplayAddress.Physical) obj).mPhysicalDisplayId;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder append = new java.lang.StringBuilder("{").append("port=").append(getPort());
            java.lang.Long model = getModel();
            if (model != null) {
                append.append(", model=0x").append(java.lang.Long.toHexString(model.longValue()));
            }
            return append.append("}").toString();
        }

        public int hashCode() {
            return java.lang.Long.hashCode(this.mPhysicalDisplayId);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeLong(this.mPhysicalDisplayId);
        }

        public static boolean isPortMatch(android.view.DisplayAddress displayAddress, android.view.DisplayAddress displayAddress2) {
            if (!(displayAddress instanceof android.view.DisplayAddress.Physical) || !(displayAddress2 instanceof android.view.DisplayAddress.Physical)) {
                return false;
            }
            android.view.DisplayAddress.Physical physical = (android.view.DisplayAddress.Physical) displayAddress;
            android.view.DisplayAddress.Physical physical2 = (android.view.DisplayAddress.Physical) displayAddress2;
            if (physical.getModel() == null || physical2.getModel() == null) {
                return physical.getPort() == physical2.getPort();
            }
            return physical.equals(physical2);
        }

        private Physical(long j) {
            this.mPhysicalDisplayId = j;
        }

        private Physical(int i, java.lang.Long l) {
            if (i < 0 || i > 255) {
                throw new java.lang.IllegalArgumentException("The port should be in the interval [0, 255]");
            }
            this.mPhysicalDisplayId = (l == null ? 0L : l.longValue() << 8) | java.lang.Integer.toUnsignedLong(i);
        }
    }

    public static final class Network extends android.view.DisplayAddress {
        public static final android.os.Parcelable.Creator<android.view.DisplayAddress.Network> CREATOR = new android.os.Parcelable.Creator<android.view.DisplayAddress.Network>() { // from class: android.view.DisplayAddress.Network.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.DisplayAddress.Network createFromParcel(android.os.Parcel parcel) {
                return new android.view.DisplayAddress.Network(parcel.readString());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.DisplayAddress.Network[] newArray(int i) {
                return new android.view.DisplayAddress.Network[i];
            }
        };
        private final java.lang.String mMacAddress;

        public boolean equals(java.lang.Object obj) {
            return (obj instanceof android.view.DisplayAddress.Network) && this.mMacAddress.equals(((android.view.DisplayAddress.Network) obj).mMacAddress);
        }

        public java.lang.String toString() {
            return this.mMacAddress;
        }

        public int hashCode() {
            return this.mMacAddress.hashCode();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeString(this.mMacAddress);
        }

        private Network(java.lang.String str) {
            this.mMacAddress = str;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
