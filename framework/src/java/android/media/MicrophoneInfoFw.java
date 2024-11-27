package android.media;

/* loaded from: classes2.dex */
public class MicrophoneInfoFw implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.MicrophoneInfoFw> CREATOR = new android.os.Parcelable.Creator<android.media.MicrophoneInfoFw>() { // from class: android.media.MicrophoneInfoFw.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.MicrophoneInfoFw createFromParcel(android.os.Parcel parcel) {
            android.media.MicrophoneInfoFw microphoneInfoFw = new android.media.MicrophoneInfoFw();
            microphoneInfoFw.readFromParcel(parcel);
            return microphoneInfoFw;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.MicrophoneInfoFw[] newArray(int i) {
            return new android.media.MicrophoneInfoFw[i];
        }
    };
    public android.media.audio.common.MicrophoneDynamicInfo dynamic;
    public android.media.audio.common.MicrophoneInfo info;
    public int portId = 0;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.info, i);
        parcel.writeTypedObject(this.dynamic, i);
        parcel.writeInt(this.portId);
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
            this.info = (android.media.audio.common.MicrophoneInfo) parcel.readTypedObject(android.media.audio.common.MicrophoneInfo.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.dynamic = (android.media.audio.common.MicrophoneDynamicInfo) parcel.readTypedObject(android.media.audio.common.MicrophoneDynamicInfo.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.portId = parcel.readInt();
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
        return describeContents(this.info) | 0 | describeContents(this.dynamic);
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
