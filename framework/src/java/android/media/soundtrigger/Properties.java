package android.media.soundtrigger;

/* loaded from: classes2.dex */
public class Properties implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.soundtrigger.Properties> CREATOR = new android.os.Parcelable.Creator<android.media.soundtrigger.Properties>() { // from class: android.media.soundtrigger.Properties.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.soundtrigger.Properties createFromParcel(android.os.Parcel parcel) {
            android.media.soundtrigger.Properties properties = new android.media.soundtrigger.Properties();
            properties.readFromParcel(parcel);
            return properties;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.soundtrigger.Properties[] newArray(int i) {
            return new android.media.soundtrigger.Properties[i];
        }
    };
    public java.lang.String description;
    public java.lang.String implementor;
    public java.lang.String supportedModelArch;
    public java.lang.String uuid;
    public int version = 0;
    public int maxSoundModels = 0;
    public int maxKeyPhrases = 0;
    public int maxUsers = 0;
    public int recognitionModes = 0;
    public boolean captureTransition = false;
    public int maxBufferMs = 0;
    public boolean concurrentCapture = false;
    public boolean triggerInEvent = false;
    public int powerConsumptionMw = 0;
    public int audioCapabilities = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.implementor);
        parcel.writeString(this.description);
        parcel.writeInt(this.version);
        parcel.writeString(this.uuid);
        parcel.writeString(this.supportedModelArch);
        parcel.writeInt(this.maxSoundModels);
        parcel.writeInt(this.maxKeyPhrases);
        parcel.writeInt(this.maxUsers);
        parcel.writeInt(this.recognitionModes);
        parcel.writeBoolean(this.captureTransition);
        parcel.writeInt(this.maxBufferMs);
        parcel.writeBoolean(this.concurrentCapture);
        parcel.writeBoolean(this.triggerInEvent);
        parcel.writeInt(this.powerConsumptionMw);
        parcel.writeInt(this.audioCapabilities);
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
            this.implementor = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.description = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.version = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.uuid = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.supportedModelArch = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.maxSoundModels = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.maxKeyPhrases = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.maxUsers = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.recognitionModes = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.captureTransition = parcel.readBoolean();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.maxBufferMs = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.concurrentCapture = parcel.readBoolean();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.triggerInEvent = parcel.readBoolean();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.powerConsumptionMw = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.audioCapabilities = parcel.readInt();
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
        stringJoiner.add("implementor: " + java.util.Objects.toString(this.implementor));
        stringJoiner.add("description: " + java.util.Objects.toString(this.description));
        stringJoiner.add("version: " + this.version);
        stringJoiner.add("uuid: " + java.util.Objects.toString(this.uuid));
        stringJoiner.add("supportedModelArch: " + java.util.Objects.toString(this.supportedModelArch));
        stringJoiner.add("maxSoundModels: " + this.maxSoundModels);
        stringJoiner.add("maxKeyPhrases: " + this.maxKeyPhrases);
        stringJoiner.add("maxUsers: " + this.maxUsers);
        stringJoiner.add("recognitionModes: " + this.recognitionModes);
        stringJoiner.add("captureTransition: " + this.captureTransition);
        stringJoiner.add("maxBufferMs: " + this.maxBufferMs);
        stringJoiner.add("concurrentCapture: " + this.concurrentCapture);
        stringJoiner.add("triggerInEvent: " + this.triggerInEvent);
        stringJoiner.add("powerConsumptionMw: " + this.powerConsumptionMw);
        stringJoiner.add("audioCapabilities: " + this.audioCapabilities);
        return "Properties" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.soundtrigger.Properties)) {
            return false;
        }
        android.media.soundtrigger.Properties properties = (android.media.soundtrigger.Properties) obj;
        if (java.util.Objects.deepEquals(this.implementor, properties.implementor) && java.util.Objects.deepEquals(this.description, properties.description) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.version), java.lang.Integer.valueOf(properties.version)) && java.util.Objects.deepEquals(this.uuid, properties.uuid) && java.util.Objects.deepEquals(this.supportedModelArch, properties.supportedModelArch) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.maxSoundModels), java.lang.Integer.valueOf(properties.maxSoundModels)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.maxKeyPhrases), java.lang.Integer.valueOf(properties.maxKeyPhrases)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.maxUsers), java.lang.Integer.valueOf(properties.maxUsers)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.recognitionModes), java.lang.Integer.valueOf(properties.recognitionModes)) && java.util.Objects.deepEquals(java.lang.Boolean.valueOf(this.captureTransition), java.lang.Boolean.valueOf(properties.captureTransition)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.maxBufferMs), java.lang.Integer.valueOf(properties.maxBufferMs)) && java.util.Objects.deepEquals(java.lang.Boolean.valueOf(this.concurrentCapture), java.lang.Boolean.valueOf(properties.concurrentCapture)) && java.util.Objects.deepEquals(java.lang.Boolean.valueOf(this.triggerInEvent), java.lang.Boolean.valueOf(properties.triggerInEvent)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.powerConsumptionMw), java.lang.Integer.valueOf(properties.powerConsumptionMw)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.audioCapabilities), java.lang.Integer.valueOf(properties.audioCapabilities))) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(this.implementor, this.description, java.lang.Integer.valueOf(this.version), this.uuid, this.supportedModelArch, java.lang.Integer.valueOf(this.maxSoundModels), java.lang.Integer.valueOf(this.maxKeyPhrases), java.lang.Integer.valueOf(this.maxUsers), java.lang.Integer.valueOf(this.recognitionModes), java.lang.Boolean.valueOf(this.captureTransition), java.lang.Integer.valueOf(this.maxBufferMs), java.lang.Boolean.valueOf(this.concurrentCapture), java.lang.Boolean.valueOf(this.triggerInEvent), java.lang.Integer.valueOf(this.powerConsumptionMw), java.lang.Integer.valueOf(this.audioCapabilities)).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
