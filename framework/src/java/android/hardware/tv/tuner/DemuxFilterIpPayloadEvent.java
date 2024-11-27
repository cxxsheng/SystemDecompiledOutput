package android.hardware.tv.tuner;

/* loaded from: classes2.dex */
public class DemuxFilterIpPayloadEvent implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.tv.tuner.DemuxFilterIpPayloadEvent> CREATOR = new android.os.Parcelable.Creator<android.hardware.tv.tuner.DemuxFilterIpPayloadEvent>() { // from class: android.hardware.tv.tuner.DemuxFilterIpPayloadEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.DemuxFilterIpPayloadEvent createFromParcel(android.os.Parcel parcel) {
            android.hardware.tv.tuner.DemuxFilterIpPayloadEvent demuxFilterIpPayloadEvent = new android.hardware.tv.tuner.DemuxFilterIpPayloadEvent();
            demuxFilterIpPayloadEvent.readFromParcel(parcel);
            return demuxFilterIpPayloadEvent;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.DemuxFilterIpPayloadEvent[] newArray(int i) {
            return new android.hardware.tv.tuner.DemuxFilterIpPayloadEvent[i];
        }
    };
    public int dataLength = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.dataLength);
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
            } else {
                this.dataLength = parcel.readInt();
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
        return 0;
    }
}
