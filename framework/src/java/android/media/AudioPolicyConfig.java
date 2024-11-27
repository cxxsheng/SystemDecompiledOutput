package android.media;

/* loaded from: classes2.dex */
public class AudioPolicyConfig implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.AudioPolicyConfig> CREATOR = new android.os.Parcelable.Creator<android.media.AudioPolicyConfig>() { // from class: android.media.AudioPolicyConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.AudioPolicyConfig createFromParcel(android.os.Parcel parcel) {
            android.media.AudioPolicyConfig audioPolicyConfig = new android.media.AudioPolicyConfig();
            audioPolicyConfig.readFromParcel(parcel);
            return audioPolicyConfig;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.AudioPolicyConfig[] newArray(int i) {
            return new android.media.AudioPolicyConfig[i];
        }
    };
    public android.media.audio.common.AudioHalEngineConfig engineConfig;
    public android.media.AudioHwModule[] modules;
    public int[] supportedModes;
    public android.media.SurroundSoundConfig surroundSoundConfig;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedArray(this.modules, i);
        parcel.writeIntArray(this.supportedModes);
        parcel.writeTypedObject(this.surroundSoundConfig, i);
        parcel.writeTypedObject(this.engineConfig, i);
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
            this.modules = (android.media.AudioHwModule[]) parcel.createTypedArray(android.media.AudioHwModule.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.supportedModes = parcel.createIntArray();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.surroundSoundConfig = (android.media.SurroundSoundConfig) parcel.readTypedObject(android.media.SurroundSoundConfig.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.engineConfig = (android.media.audio.common.AudioHalEngineConfig) parcel.readTypedObject(android.media.audio.common.AudioHalEngineConfig.CREATOR);
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
        return describeContents(this.modules) | 0 | describeContents(this.surroundSoundConfig) | describeContents(this.engineConfig);
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
