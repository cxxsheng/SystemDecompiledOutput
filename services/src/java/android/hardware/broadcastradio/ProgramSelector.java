package android.hardware.broadcastradio;

/* loaded from: classes.dex */
public class ProgramSelector implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.broadcastradio.ProgramSelector> CREATOR = new android.os.Parcelable.Creator<android.hardware.broadcastradio.ProgramSelector>() { // from class: android.hardware.broadcastradio.ProgramSelector.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.broadcastradio.ProgramSelector createFromParcel(android.os.Parcel parcel) {
            android.hardware.broadcastradio.ProgramSelector programSelector = new android.hardware.broadcastradio.ProgramSelector();
            programSelector.readFromParcel(parcel);
            return programSelector;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.broadcastradio.ProgramSelector[] newArray(int i) {
            return new android.hardware.broadcastradio.ProgramSelector[i];
        }
    };
    public android.hardware.broadcastradio.ProgramIdentifier primaryId;
    public android.hardware.broadcastradio.ProgramIdentifier[] secondaryIds;

    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.primaryId, i);
        parcel.writeTypedArray(this.secondaryIds, i);
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
            this.primaryId = (android.hardware.broadcastradio.ProgramIdentifier) parcel.readTypedObject(android.hardware.broadcastradio.ProgramIdentifier.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.secondaryIds = (android.hardware.broadcastradio.ProgramIdentifier[]) parcel.createTypedArray(android.hardware.broadcastradio.ProgramIdentifier.CREATOR);
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
        stringJoiner.add("primaryId: " + java.util.Objects.toString(this.primaryId));
        stringJoiner.add("secondaryIds: " + java.util.Arrays.toString(this.secondaryIds));
        return "ProgramSelector" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.hardware.broadcastradio.ProgramSelector)) {
            return false;
        }
        android.hardware.broadcastradio.ProgramSelector programSelector = (android.hardware.broadcastradio.ProgramSelector) obj;
        if (java.util.Objects.deepEquals(this.primaryId, programSelector.primaryId) && java.util.Objects.deepEquals(this.secondaryIds, programSelector.secondaryIds)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(this.primaryId, this.secondaryIds).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.primaryId) | 0 | describeContents(this.secondaryIds);
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
