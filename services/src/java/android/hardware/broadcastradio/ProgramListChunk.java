package android.hardware.broadcastradio;

/* loaded from: classes.dex */
public class ProgramListChunk implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.broadcastradio.ProgramListChunk> CREATOR = new android.os.Parcelable.Creator<android.hardware.broadcastradio.ProgramListChunk>() { // from class: android.hardware.broadcastradio.ProgramListChunk.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.broadcastradio.ProgramListChunk createFromParcel(android.os.Parcel parcel) {
            android.hardware.broadcastradio.ProgramListChunk programListChunk = new android.hardware.broadcastradio.ProgramListChunk();
            programListChunk.readFromParcel(parcel);
            return programListChunk;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.broadcastradio.ProgramListChunk[] newArray(int i) {
            return new android.hardware.broadcastradio.ProgramListChunk[i];
        }
    };
    public android.hardware.broadcastradio.ProgramInfo[] modified;
    public android.hardware.broadcastradio.ProgramIdentifier[] removed;
    public boolean purge = false;
    public boolean complete = false;

    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeBoolean(this.purge);
        parcel.writeBoolean(this.complete);
        parcel.writeTypedArray(this.modified, i);
        parcel.writeTypedArray(this.removed, i);
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
            this.purge = parcel.readBoolean();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.complete = parcel.readBoolean();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.modified = (android.hardware.broadcastradio.ProgramInfo[]) parcel.createTypedArray(android.hardware.broadcastradio.ProgramInfo.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.removed = (android.hardware.broadcastradio.ProgramIdentifier[]) parcel.createTypedArray(android.hardware.broadcastradio.ProgramIdentifier.CREATOR);
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
        stringJoiner.add("purge: " + this.purge);
        stringJoiner.add("complete: " + this.complete);
        stringJoiner.add("modified: " + java.util.Arrays.toString(this.modified));
        stringJoiner.add("removed: " + java.util.Arrays.toString(this.removed));
        return "ProgramListChunk" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.hardware.broadcastradio.ProgramListChunk)) {
            return false;
        }
        android.hardware.broadcastradio.ProgramListChunk programListChunk = (android.hardware.broadcastradio.ProgramListChunk) obj;
        if (java.util.Objects.deepEquals(java.lang.Boolean.valueOf(this.purge), java.lang.Boolean.valueOf(programListChunk.purge)) && java.util.Objects.deepEquals(java.lang.Boolean.valueOf(this.complete), java.lang.Boolean.valueOf(programListChunk.complete)) && java.util.Objects.deepEquals(this.modified, programListChunk.modified) && java.util.Objects.deepEquals(this.removed, programListChunk.removed)) {
            return true;
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(java.lang.Boolean.valueOf(this.purge), java.lang.Boolean.valueOf(this.complete), this.modified, this.removed).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.modified) | 0 | describeContents(this.removed);
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
