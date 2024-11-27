package android.net;

/* loaded from: classes.dex */
public class NativeNetworkConfig implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.NativeNetworkConfig> CREATOR = new android.os.Parcelable.Creator<android.net.NativeNetworkConfig>() { // from class: android.net.NativeNetworkConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.NativeNetworkConfig createFromParcel(android.os.Parcel parcel) {
            return android.net.NativeNetworkConfig.internalCreateFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.NativeNetworkConfig[] newArray(int i) {
            return new android.net.NativeNetworkConfig[i];
        }
    };
    public final boolean excludeLocalRoutes;
    public final int netId;
    public final int networkType;
    public final int permission;
    public final boolean secure;
    public final int vpnType;

    public static final class Builder {
        private int netId = 0;
        private int networkType = 0;
        private int permission = 0;
        private boolean secure = false;
        private int vpnType = 2;
        private boolean excludeLocalRoutes = false;

        public android.net.NativeNetworkConfig.Builder setNetId(int i) {
            this.netId = i;
            return this;
        }

        public android.net.NativeNetworkConfig.Builder setNetworkType(int i) {
            this.networkType = i;
            return this;
        }

        public android.net.NativeNetworkConfig.Builder setPermission(int i) {
            this.permission = i;
            return this;
        }

        public android.net.NativeNetworkConfig.Builder setSecure(boolean z) {
            this.secure = z;
            return this;
        }

        public android.net.NativeNetworkConfig.Builder setVpnType(int i) {
            this.vpnType = i;
            return this;
        }

        public android.net.NativeNetworkConfig.Builder setExcludeLocalRoutes(boolean z) {
            this.excludeLocalRoutes = z;
            return this;
        }

        public android.net.NativeNetworkConfig build() {
            return new android.net.NativeNetworkConfig(this.netId, this.networkType, this.permission, this.secure, this.vpnType, this.excludeLocalRoutes);
        }
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.netId);
        parcel.writeInt(this.networkType);
        parcel.writeInt(this.permission);
        parcel.writeBoolean(this.secure);
        parcel.writeInt(this.vpnType);
        parcel.writeBoolean(this.excludeLocalRoutes);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public NativeNetworkConfig(int i, int i2, int i3, boolean z, int i4, boolean z2) {
        this.netId = i;
        this.networkType = i2;
        this.permission = i3;
        this.secure = z;
        this.vpnType = i4;
        this.excludeLocalRoutes = z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.net.NativeNetworkConfig internalCreateFromParcel(android.os.Parcel parcel) {
        int i;
        android.net.NativeNetworkConfig.Builder builder = new android.net.NativeNetworkConfig.Builder();
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
                builder.setNetworkType(parcel.readInt());
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    builder.build();
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                } else {
                    builder.setPermission(parcel.readInt());
                    if (parcel.dataPosition() - dataPosition >= readInt) {
                        builder.build();
                        if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else {
                        builder.setSecure(parcel.readBoolean());
                        if (parcel.dataPosition() - dataPosition >= readInt) {
                            builder.build();
                            if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else {
                            builder.setVpnType(parcel.readInt());
                            if (parcel.dataPosition() - dataPosition >= readInt) {
                                builder.build();
                                if (dataPosition > Integer.MAX_VALUE - readInt) {
                                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                                }
                            } else {
                                builder.setExcludeLocalRoutes(parcel.readBoolean());
                                if (dataPosition > Integer.MAX_VALUE - readInt) {
                                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                                }
                            }
                        }
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
        stringJoiner.add("networkType: " + this.networkType);
        stringJoiner.add("permission: " + this.permission);
        stringJoiner.add("secure: " + this.secure);
        stringJoiner.add("vpnType: " + this.vpnType);
        stringJoiner.add("excludeLocalRoutes: " + this.excludeLocalRoutes);
        return "NativeNetworkConfig" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.net.NativeNetworkConfig)) {
            return false;
        }
        android.net.NativeNetworkConfig nativeNetworkConfig = (android.net.NativeNetworkConfig) obj;
        if (java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.netId), java.lang.Integer.valueOf(nativeNetworkConfig.netId)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.networkType), java.lang.Integer.valueOf(nativeNetworkConfig.networkType)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.permission), java.lang.Integer.valueOf(nativeNetworkConfig.permission)) && java.util.Objects.deepEquals(java.lang.Boolean.valueOf(this.secure), java.lang.Boolean.valueOf(nativeNetworkConfig.secure)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.vpnType), java.lang.Integer.valueOf(nativeNetworkConfig.vpnType)) && java.util.Objects.deepEquals(java.lang.Boolean.valueOf(this.excludeLocalRoutes), java.lang.Boolean.valueOf(nativeNetworkConfig.excludeLocalRoutes))) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(java.lang.Integer.valueOf(this.netId), java.lang.Integer.valueOf(this.networkType), java.lang.Integer.valueOf(this.permission), java.lang.Boolean.valueOf(this.secure), java.lang.Integer.valueOf(this.vpnType), java.lang.Boolean.valueOf(this.excludeLocalRoutes)).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
