package android.hardware.power;

/* loaded from: classes2.dex */
public class ChannelConfig implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.power.ChannelConfig> CREATOR = new android.os.Parcelable.Creator<android.hardware.power.ChannelConfig>() { // from class: android.hardware.power.ChannelConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.power.ChannelConfig createFromParcel(android.os.Parcel parcel) {
            android.hardware.power.ChannelConfig channelConfig = new android.hardware.power.ChannelConfig();
            channelConfig.readFromParcel(parcel);
            return channelConfig;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.power.ChannelConfig[] newArray(int i) {
            return new android.hardware.power.ChannelConfig[i];
        }
    };
    public android.hardware.common.fmq.MQDescriptor<android.hardware.power.ChannelMessage, java.lang.Byte> channelDescriptor;
    public android.hardware.common.fmq.MQDescriptor<java.lang.Byte, java.lang.Byte> eventFlagDescriptor;
    public int readFlagBitmask = 0;
    public int writeFlagBitmask = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.channelDescriptor, i);
        parcel.writeTypedObject(this.eventFlagDescriptor, i);
        parcel.writeInt(this.readFlagBitmask);
        parcel.writeInt(this.writeFlagBitmask);
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
            this.channelDescriptor = (android.hardware.common.fmq.MQDescriptor) parcel.readTypedObject(android.hardware.common.fmq.MQDescriptor.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.eventFlagDescriptor = (android.hardware.common.fmq.MQDescriptor) parcel.readTypedObject(android.hardware.common.fmq.MQDescriptor.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.readFlagBitmask = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.writeFlagBitmask = parcel.readInt();
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.channelDescriptor) | 0 | describeContents(this.eventFlagDescriptor);
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
