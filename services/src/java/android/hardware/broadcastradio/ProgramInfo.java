package android.hardware.broadcastradio;

/* loaded from: classes.dex */
public class ProgramInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.broadcastradio.ProgramInfo> CREATOR = new android.os.Parcelable.Creator<android.hardware.broadcastradio.ProgramInfo>() { // from class: android.hardware.broadcastradio.ProgramInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.broadcastradio.ProgramInfo createFromParcel(android.os.Parcel parcel) {
            android.hardware.broadcastradio.ProgramInfo programInfo = new android.hardware.broadcastradio.ProgramInfo();
            programInfo.readFromParcel(parcel);
            return programInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.broadcastradio.ProgramInfo[] newArray(int i) {
            return new android.hardware.broadcastradio.ProgramInfo[i];
        }
    };
    public static final int FLAG_HD_AUDIO_ACQUISITION = 256;
    public static final int FLAG_HD_SIS_ACQUISITION = 128;
    public static final int FLAG_LIVE = 1;
    public static final int FLAG_MUTED = 2;
    public static final int FLAG_SIGNAL_ACQUISITION = 64;
    public static final int FLAG_STEREO = 32;
    public static final int FLAG_TRAFFIC_ANNOUNCEMENT = 8;
    public static final int FLAG_TRAFFIC_PROGRAM = 4;
    public static final int FLAG_TUNABLE = 16;
    public android.hardware.broadcastradio.ProgramIdentifier logicallyTunedTo;
    public android.hardware.broadcastradio.Metadata[] metadata;
    public android.hardware.broadcastradio.ProgramIdentifier physicallyTunedTo;
    public android.hardware.broadcastradio.ProgramIdentifier[] relatedContent;
    public android.hardware.broadcastradio.ProgramSelector selector;
    public android.hardware.broadcastradio.VendorKeyValue[] vendorInfo;
    public int infoFlags = 0;
    public int signalQuality = 0;

    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.selector, i);
        parcel.writeTypedObject(this.logicallyTunedTo, i);
        parcel.writeTypedObject(this.physicallyTunedTo, i);
        parcel.writeTypedArray(this.relatedContent, i);
        parcel.writeInt(this.infoFlags);
        parcel.writeInt(this.signalQuality);
        parcel.writeTypedArray(this.metadata, i);
        parcel.writeTypedArray(this.vendorInfo, i);
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
            this.selector = (android.hardware.broadcastradio.ProgramSelector) parcel.readTypedObject(android.hardware.broadcastradio.ProgramSelector.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.logicallyTunedTo = (android.hardware.broadcastradio.ProgramIdentifier) parcel.readTypedObject(android.hardware.broadcastradio.ProgramIdentifier.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.physicallyTunedTo = (android.hardware.broadcastradio.ProgramIdentifier) parcel.readTypedObject(android.hardware.broadcastradio.ProgramIdentifier.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.relatedContent = (android.hardware.broadcastradio.ProgramIdentifier[]) parcel.createTypedArray(android.hardware.broadcastradio.ProgramIdentifier.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.infoFlags = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.signalQuality = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.metadata = (android.hardware.broadcastradio.Metadata[]) parcel.createTypedArray(android.hardware.broadcastradio.Metadata.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.vendorInfo = (android.hardware.broadcastradio.VendorKeyValue[]) parcel.createTypedArray(android.hardware.broadcastradio.VendorKeyValue.CREATOR);
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
        stringJoiner.add("selector: " + java.util.Objects.toString(this.selector));
        stringJoiner.add("logicallyTunedTo: " + java.util.Objects.toString(this.logicallyTunedTo));
        stringJoiner.add("physicallyTunedTo: " + java.util.Objects.toString(this.physicallyTunedTo));
        stringJoiner.add("relatedContent: " + java.util.Arrays.toString(this.relatedContent));
        stringJoiner.add("infoFlags: " + this.infoFlags);
        stringJoiner.add("signalQuality: " + this.signalQuality);
        stringJoiner.add("metadata: " + java.util.Arrays.toString(this.metadata));
        stringJoiner.add("vendorInfo: " + java.util.Arrays.toString(this.vendorInfo));
        return "ProgramInfo" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.hardware.broadcastradio.ProgramInfo)) {
            return false;
        }
        android.hardware.broadcastradio.ProgramInfo programInfo = (android.hardware.broadcastradio.ProgramInfo) obj;
        if (java.util.Objects.deepEquals(this.selector, programInfo.selector) && java.util.Objects.deepEquals(this.logicallyTunedTo, programInfo.logicallyTunedTo) && java.util.Objects.deepEquals(this.physicallyTunedTo, programInfo.physicallyTunedTo) && java.util.Objects.deepEquals(this.relatedContent, programInfo.relatedContent) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.infoFlags), java.lang.Integer.valueOf(programInfo.infoFlags)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.signalQuality), java.lang.Integer.valueOf(programInfo.signalQuality)) && java.util.Objects.deepEquals(this.metadata, programInfo.metadata) && java.util.Objects.deepEquals(this.vendorInfo, programInfo.vendorInfo)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(this.selector, this.logicallyTunedTo, this.physicallyTunedTo, this.relatedContent, java.lang.Integer.valueOf(this.infoFlags), java.lang.Integer.valueOf(this.signalQuality), this.metadata, this.vendorInfo).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.selector) | 0 | describeContents(this.logicallyTunedTo) | describeContents(this.physicallyTunedTo) | describeContents(this.relatedContent) | describeContents(this.metadata) | describeContents(this.vendorInfo);
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
