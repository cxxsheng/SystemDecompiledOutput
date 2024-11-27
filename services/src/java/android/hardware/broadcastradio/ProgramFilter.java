package android.hardware.broadcastradio;

/* loaded from: classes.dex */
public class ProgramFilter implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.broadcastradio.ProgramFilter> CREATOR = new android.os.Parcelable.Creator<android.hardware.broadcastradio.ProgramFilter>() { // from class: android.hardware.broadcastradio.ProgramFilter.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.broadcastradio.ProgramFilter createFromParcel(android.os.Parcel parcel) {
            android.hardware.broadcastradio.ProgramFilter programFilter = new android.hardware.broadcastradio.ProgramFilter();
            programFilter.readFromParcel(parcel);
            return programFilter;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.broadcastradio.ProgramFilter[] newArray(int i) {
            return new android.hardware.broadcastradio.ProgramFilter[i];
        }
    };
    public int[] identifierTypes;
    public android.hardware.broadcastradio.ProgramIdentifier[] identifiers;
    public boolean includeCategories = false;
    public boolean excludeModifications = false;

    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeIntArray(this.identifierTypes);
        parcel.writeTypedArray(this.identifiers, i);
        parcel.writeBoolean(this.includeCategories);
        parcel.writeBoolean(this.excludeModifications);
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
            this.identifierTypes = parcel.createIntArray();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.identifiers = (android.hardware.broadcastradio.ProgramIdentifier[]) parcel.createTypedArray(android.hardware.broadcastradio.ProgramIdentifier.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.includeCategories = parcel.readBoolean();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.excludeModifications = parcel.readBoolean();
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
        stringJoiner.add("identifierTypes: " + android.hardware.broadcastradio.IdentifierType$$.arrayToString(this.identifierTypes));
        stringJoiner.add("identifiers: " + java.util.Arrays.toString(this.identifiers));
        stringJoiner.add("includeCategories: " + this.includeCategories);
        stringJoiner.add("excludeModifications: " + this.excludeModifications);
        return "ProgramFilter" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.hardware.broadcastradio.ProgramFilter)) {
            return false;
        }
        android.hardware.broadcastradio.ProgramFilter programFilter = (android.hardware.broadcastradio.ProgramFilter) obj;
        if (java.util.Objects.deepEquals(this.identifierTypes, programFilter.identifierTypes) && java.util.Objects.deepEquals(this.identifiers, programFilter.identifiers) && java.util.Objects.deepEquals(java.lang.Boolean.valueOf(this.includeCategories), java.lang.Boolean.valueOf(programFilter.includeCategories)) && java.util.Objects.deepEquals(java.lang.Boolean.valueOf(this.excludeModifications), java.lang.Boolean.valueOf(programFilter.excludeModifications))) {
            return true;
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(this.identifierTypes, this.identifiers, java.lang.Boolean.valueOf(this.includeCategories), java.lang.Boolean.valueOf(this.excludeModifications)).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.identifiers) | 0;
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
