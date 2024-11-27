package android.net;

/* loaded from: classes.dex */
public class IpSecMigrateInfoParcel implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.IpSecMigrateInfoParcel> CREATOR = new android.os.Parcelable.Creator<android.net.IpSecMigrateInfoParcel>() { // from class: android.net.IpSecMigrateInfoParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.IpSecMigrateInfoParcel createFromParcel(android.os.Parcel parcel) {
            return android.net.IpSecMigrateInfoParcel.internalCreateFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.IpSecMigrateInfoParcel[] newArray(int i) {
            return new android.net.IpSecMigrateInfoParcel[i];
        }
    };
    public final int direction;
    public final int interfaceId;
    public final java.lang.String newDestinationAddress;
    public final java.lang.String newSourceAddress;
    public final java.lang.String oldDestinationAddress;
    public final java.lang.String oldSourceAddress;
    public final int requestId;
    public final int selAddrFamily;

    public static final class Builder {
        private java.lang.String newDestinationAddress;
        private java.lang.String newSourceAddress;
        private java.lang.String oldDestinationAddress;
        private java.lang.String oldSourceAddress;
        private int requestId = 0;
        private int selAddrFamily = 0;
        private int direction = 0;
        private int interfaceId = 0;

        public android.net.IpSecMigrateInfoParcel.Builder setRequestId(int i) {
            this.requestId = i;
            return this;
        }

        public android.net.IpSecMigrateInfoParcel.Builder setSelAddrFamily(int i) {
            this.selAddrFamily = i;
            return this;
        }

        public android.net.IpSecMigrateInfoParcel.Builder setDirection(int i) {
            this.direction = i;
            return this;
        }

        public android.net.IpSecMigrateInfoParcel.Builder setOldSourceAddress(java.lang.String str) {
            this.oldSourceAddress = str;
            return this;
        }

        public android.net.IpSecMigrateInfoParcel.Builder setOldDestinationAddress(java.lang.String str) {
            this.oldDestinationAddress = str;
            return this;
        }

        public android.net.IpSecMigrateInfoParcel.Builder setNewSourceAddress(java.lang.String str) {
            this.newSourceAddress = str;
            return this;
        }

        public android.net.IpSecMigrateInfoParcel.Builder setNewDestinationAddress(java.lang.String str) {
            this.newDestinationAddress = str;
            return this;
        }

        public android.net.IpSecMigrateInfoParcel.Builder setInterfaceId(int i) {
            this.interfaceId = i;
            return this;
        }

        public android.net.IpSecMigrateInfoParcel build() {
            return new android.net.IpSecMigrateInfoParcel(this.requestId, this.selAddrFamily, this.direction, this.oldSourceAddress, this.oldDestinationAddress, this.newSourceAddress, this.newDestinationAddress, this.interfaceId);
        }
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.requestId);
        parcel.writeInt(this.selAddrFamily);
        parcel.writeInt(this.direction);
        parcel.writeString(this.oldSourceAddress);
        parcel.writeString(this.oldDestinationAddress);
        parcel.writeString(this.newSourceAddress);
        parcel.writeString(this.newDestinationAddress);
        parcel.writeInt(this.interfaceId);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public IpSecMigrateInfoParcel(int i, int i2, int i3, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, int i4) {
        this.requestId = i;
        this.selAddrFamily = i2;
        this.direction = i3;
        this.oldSourceAddress = str;
        this.oldDestinationAddress = str2;
        this.newSourceAddress = str3;
        this.newDestinationAddress = str4;
        this.interfaceId = i4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.net.IpSecMigrateInfoParcel internalCreateFromParcel(android.os.Parcel parcel) {
        int i;
        android.net.IpSecMigrateInfoParcel.Builder builder = new android.net.IpSecMigrateInfoParcel.Builder();
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
            builder.setRequestId(parcel.readInt());
            if (parcel.dataPosition() - dataPosition >= readInt) {
                builder.build();
                if (dataPosition > i) {
                    throw new android.os.BadParcelableException(r4);
                }
            } else {
                builder.setSelAddrFamily(parcel.readInt());
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    builder.build();
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                } else {
                    builder.setDirection(parcel.readInt());
                    if (parcel.dataPosition() - dataPosition >= readInt) {
                        builder.build();
                        if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else {
                        builder.setOldSourceAddress(parcel.readString());
                        if (parcel.dataPosition() - dataPosition >= readInt) {
                            builder.build();
                            if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else {
                            builder.setOldDestinationAddress(parcel.readString());
                            if (parcel.dataPosition() - dataPosition >= readInt) {
                                builder.build();
                                if (dataPosition > Integer.MAX_VALUE - readInt) {
                                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                                }
                            } else {
                                builder.setNewSourceAddress(parcel.readString());
                                if (parcel.dataPosition() - dataPosition >= readInt) {
                                    builder.build();
                                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                                    }
                                } else {
                                    builder.setNewDestinationAddress(parcel.readString());
                                    if (parcel.dataPosition() - dataPosition >= readInt) {
                                        builder.build();
                                        if (dataPosition > Integer.MAX_VALUE - readInt) {
                                            throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                                        }
                                    } else {
                                        builder.setInterfaceId(parcel.readInt());
                                        if (dataPosition > Integer.MAX_VALUE - readInt) {
                                            throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                                        }
                                    }
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
