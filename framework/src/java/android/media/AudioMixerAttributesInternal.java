package android.media;

/* loaded from: classes2.dex */
public class AudioMixerAttributesInternal implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.AudioMixerAttributesInternal> CREATOR = new android.os.Parcelable.Creator<android.media.AudioMixerAttributesInternal>() { // from class: android.media.AudioMixerAttributesInternal.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.AudioMixerAttributesInternal createFromParcel(android.os.Parcel parcel) {
            android.media.AudioMixerAttributesInternal audioMixerAttributesInternal = new android.media.AudioMixerAttributesInternal();
            audioMixerAttributesInternal.readFromParcel(parcel);
            return audioMixerAttributesInternal;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.AudioMixerAttributesInternal[] newArray(int i) {
            return new android.media.AudioMixerAttributesInternal[i];
        }
    };
    public android.media.audio.common.AudioConfigBase config;
    public int mixerBehavior;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.config, i);
        parcel.writeInt(this.mixerBehavior);
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
            this.config = (android.media.audio.common.AudioConfigBase) parcel.readTypedObject(android.media.audio.common.AudioConfigBase.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.mixerBehavior = parcel.readInt();
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
        return describeContents(this.config) | 0;
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}