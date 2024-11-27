package android.media.soundtrigger_middleware;

/* loaded from: classes2.dex */
public class SoundTriggerModuleDescriptor implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor> CREATOR = new android.os.Parcelable.Creator<android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor>() { // from class: android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor createFromParcel(android.os.Parcel parcel) {
            android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor soundTriggerModuleDescriptor = new android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor();
            soundTriggerModuleDescriptor.readFromParcel(parcel);
            return soundTriggerModuleDescriptor;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor[] newArray(int i) {
            return new android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor[i];
        }
    };
    public int handle = 0;
    public android.media.soundtrigger.Properties properties;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.handle);
        parcel.writeTypedObject(this.properties, i);
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
            this.handle = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.properties = (android.media.soundtrigger.Properties) parcel.readTypedObject(android.media.soundtrigger.Properties.CREATOR);
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

    public java.lang.String toString() {
        java.util.StringJoiner stringJoiner = new java.util.StringJoiner(", ", "{", "}");
        stringJoiner.add("handle: " + this.handle);
        stringJoiner.add("properties: " + java.util.Objects.toString(this.properties));
        return "SoundTriggerModuleDescriptor" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor)) {
            return false;
        }
        android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor soundTriggerModuleDescriptor = (android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor) obj;
        if (java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.handle), java.lang.Integer.valueOf(soundTriggerModuleDescriptor.handle)) && java.util.Objects.deepEquals(this.properties, soundTriggerModuleDescriptor.properties)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(java.lang.Integer.valueOf(this.handle), this.properties).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.properties) | 0;
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
