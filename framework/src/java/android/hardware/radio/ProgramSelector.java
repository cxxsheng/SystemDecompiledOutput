package android.hardware.radio;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class ProgramSelector implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.ProgramSelector> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.ProgramSelector>() { // from class: android.hardware.radio.ProgramSelector.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.ProgramSelector createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.radio.ProgramSelector(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.ProgramSelector[] newArray(int i) {
            return new android.hardware.radio.ProgramSelector[i];
        }
    };
    public static final int IDENTIFIER_TYPE_AMFM_FREQUENCY = 1;
    public static final int IDENTIFIER_TYPE_DAB_DMB_SID_EXT = 14;
    public static final int IDENTIFIER_TYPE_DAB_ENSEMBLE = 6;
    public static final int IDENTIFIER_TYPE_DAB_FREQUENCY = 8;
    public static final int IDENTIFIER_TYPE_DAB_SCID = 7;

    @java.lang.Deprecated
    public static final int IDENTIFIER_TYPE_DAB_SIDECC = 5;

    @java.lang.Deprecated
    public static final int IDENTIFIER_TYPE_DAB_SID_EXT = 5;
    public static final int IDENTIFIER_TYPE_DRMO_FREQUENCY = 10;
    public static final int IDENTIFIER_TYPE_DRMO_MODULATION = 11;
    public static final int IDENTIFIER_TYPE_DRMO_SERVICE_ID = 9;
    public static final int IDENTIFIER_TYPE_HD_STATION_ID_EXT = 3;
    public static final int IDENTIFIER_TYPE_HD_STATION_LOCATION = 15;
    public static final int IDENTIFIER_TYPE_HD_STATION_NAME = 10004;

    @java.lang.Deprecated
    public static final int IDENTIFIER_TYPE_HD_SUBCHANNEL = 4;
    public static final int IDENTIFIER_TYPE_INVALID = 0;
    public static final int IDENTIFIER_TYPE_RDS_PI = 2;
    public static final int IDENTIFIER_TYPE_SXM_CHANNEL = 13;
    public static final int IDENTIFIER_TYPE_SXM_SERVICE_ID = 12;
    public static final int IDENTIFIER_TYPE_VENDOR_END = 1999;

    @java.lang.Deprecated
    public static final int IDENTIFIER_TYPE_VENDOR_PRIMARY_END = 1999;

    @java.lang.Deprecated
    public static final int IDENTIFIER_TYPE_VENDOR_PRIMARY_START = 1000;
    public static final int IDENTIFIER_TYPE_VENDOR_START = 1000;

    @java.lang.Deprecated
    public static final int PROGRAM_TYPE_AM = 1;

    @java.lang.Deprecated
    public static final int PROGRAM_TYPE_AM_HD = 3;

    @java.lang.Deprecated
    public static final int PROGRAM_TYPE_DAB = 5;

    @java.lang.Deprecated
    public static final int PROGRAM_TYPE_DRMO = 6;

    @java.lang.Deprecated
    public static final int PROGRAM_TYPE_FM = 2;

    @java.lang.Deprecated
    public static final int PROGRAM_TYPE_FM_HD = 4;

    @java.lang.Deprecated
    public static final int PROGRAM_TYPE_INVALID = 0;

    @java.lang.Deprecated
    public static final int PROGRAM_TYPE_SXM = 7;

    @java.lang.Deprecated
    public static final int PROGRAM_TYPE_VENDOR_END = 1999;

    @java.lang.Deprecated
    public static final int PROGRAM_TYPE_VENDOR_START = 1000;
    public static final int SUB_CHANNEL_HD_1 = 1;
    public static final int SUB_CHANNEL_HD_2 = 2;
    public static final int SUB_CHANNEL_HD_3 = 4;
    public static final int SUB_CHANNEL_HD_4 = 8;
    public static final int SUB_CHANNEL_HD_5 = 16;
    public static final int SUB_CHANNEL_HD_6 = 32;
    public static final int SUB_CHANNEL_HD_7 = 64;
    public static final int SUB_CHANNEL_HD_8 = 128;
    private final android.hardware.radio.ProgramSelector.Identifier mPrimaryId;
    private final int mProgramType;
    private final android.hardware.radio.ProgramSelector.Identifier[] mSecondaryIds;
    private final long[] mVendorIds;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface HdSubChannel {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface IdentifierType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @java.lang.Deprecated
    public @interface ProgramType {
    }

    public ProgramSelector(int i, android.hardware.radio.ProgramSelector.Identifier identifier, android.hardware.radio.ProgramSelector.Identifier[] identifierArr, long[] jArr) {
        identifierArr = identifierArr == null ? new android.hardware.radio.ProgramSelector.Identifier[0] : identifierArr;
        jArr = jArr == null ? new long[0] : jArr;
        if (java.util.stream.Stream.of((java.lang.Object[]) identifierArr).anyMatch(new java.util.function.Predicate() { // from class: android.hardware.radio.ProgramSelector$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return android.hardware.radio.ProgramSelector.lambda$new$0((android.hardware.radio.ProgramSelector.Identifier) obj);
            }
        })) {
            throw new java.lang.IllegalArgumentException("secondaryIds list must not contain nulls");
        }
        this.mProgramType = i;
        this.mPrimaryId = (android.hardware.radio.ProgramSelector.Identifier) java.util.Objects.requireNonNull(identifier);
        this.mSecondaryIds = identifierArr;
        this.mVendorIds = jArr;
    }

    static /* synthetic */ boolean lambda$new$0(android.hardware.radio.ProgramSelector.Identifier identifier) {
        return identifier == null;
    }

    @java.lang.Deprecated
    public int getProgramType() {
        return this.mProgramType;
    }

    public android.hardware.radio.ProgramSelector.Identifier getPrimaryId() {
        return this.mPrimaryId;
    }

    public android.hardware.radio.ProgramSelector.Identifier[] getSecondaryIds() {
        return this.mSecondaryIds;
    }

    public long getFirstId(int i) {
        if (this.mPrimaryId.getType() == i) {
            return this.mPrimaryId.getValue();
        }
        for (android.hardware.radio.ProgramSelector.Identifier identifier : this.mSecondaryIds) {
            if (identifier.getType() == i) {
                return identifier.getValue();
            }
        }
        throw new java.lang.IllegalArgumentException("Identifier " + i + " not found");
    }

    public android.hardware.radio.ProgramSelector.Identifier[] getAllIds(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (this.mPrimaryId.getType() == i) {
            arrayList.add(this.mPrimaryId);
        }
        for (android.hardware.radio.ProgramSelector.Identifier identifier : this.mSecondaryIds) {
            if (identifier.getType() == i) {
                arrayList.add(identifier);
            }
        }
        return (android.hardware.radio.ProgramSelector.Identifier[]) arrayList.toArray(new android.hardware.radio.ProgramSelector.Identifier[arrayList.size()]);
    }

    @java.lang.Deprecated
    public long[] getVendorIds() {
        return this.mVendorIds;
    }

    public android.hardware.radio.ProgramSelector withSecondaryPreferred(android.hardware.radio.ProgramSelector.Identifier identifier) {
        final int type = identifier.getType();
        return new android.hardware.radio.ProgramSelector(this.mProgramType, this.mPrimaryId, (android.hardware.radio.ProgramSelector.Identifier[]) java.util.stream.Stream.concat(java.util.Arrays.stream(this.mSecondaryIds).filter(new java.util.function.Predicate() { // from class: android.hardware.radio.ProgramSelector$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return android.hardware.radio.ProgramSelector.lambda$withSecondaryPreferred$1(type, (android.hardware.radio.ProgramSelector.Identifier) obj);
            }
        }), java.util.stream.Stream.of(identifier)).toArray(new java.util.function.IntFunction() { // from class: android.hardware.radio.ProgramSelector$$ExternalSyntheticLambda1
            @Override // java.util.function.IntFunction
            public final java.lang.Object apply(int i) {
                return android.hardware.radio.ProgramSelector.lambda$withSecondaryPreferred$2(i);
            }
        }), this.mVendorIds);
    }

    static /* synthetic */ boolean lambda$withSecondaryPreferred$1(int i, android.hardware.radio.ProgramSelector.Identifier identifier) {
        return identifier.getType() != i;
    }

    static /* synthetic */ android.hardware.radio.ProgramSelector.Identifier[] lambda$withSecondaryPreferred$2(int i) {
        return new android.hardware.radio.ProgramSelector.Identifier[i];
    }

    public static android.hardware.radio.ProgramSelector createAmFmSelector(int i, int i2) {
        return createAmFmSelector(i, i2, 0);
    }

    private static boolean isValidAmFmFrequency(boolean z, int i) {
        return z ? i > 150 && i <= 30000 : i > 60000 && i < 110000;
    }

    public static android.hardware.radio.ProgramSelector createAmFmSelector(int i, int i2, int i3) {
        android.hardware.radio.ProgramSelector.Identifier[] identifierArr;
        if (i == -1) {
            if (i2 < 50000) {
                i = i3 <= 0 ? 0 : 3;
            } else {
                i = i3 <= 0 ? 1 : 2;
            }
        }
        boolean z = i == 0 || i == 3;
        boolean z2 = i == 3 || i == 2;
        if (!z && !z2 && i != 1) {
            throw new java.lang.IllegalArgumentException("Unknown band: " + i);
        }
        if (i3 < 0 || i3 > 8) {
            throw new java.lang.IllegalArgumentException("Invalid subchannel: " + i3);
        }
        if (i3 > 0 && !z2) {
            throw new java.lang.IllegalArgumentException("Subchannels are not supported for non-HD radio");
        }
        if (!isValidAmFmFrequency(z, i2)) {
            throw new java.lang.IllegalArgumentException("Provided value is not a valid AM/FM frequency: " + i2);
        }
        int i4 = z ? 1 : 2;
        android.hardware.radio.ProgramSelector.Identifier identifier = new android.hardware.radio.ProgramSelector.Identifier(1, i2);
        if (i3 <= 0) {
            identifierArr = null;
        } else {
            identifierArr = new android.hardware.radio.ProgramSelector.Identifier[]{new android.hardware.radio.ProgramSelector.Identifier(4, i3 - 1)};
        }
        return new android.hardware.radio.ProgramSelector(i4, identifier, identifierArr, null);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder append = new java.lang.StringBuilder("ProgramSelector(type=").append(this.mProgramType).append(", primary=").append(this.mPrimaryId);
        if (this.mSecondaryIds.length > 0) {
            append.append(", secondary=").append(java.util.Arrays.toString(this.mSecondaryIds));
        }
        if (this.mVendorIds.length > 0) {
            append.append(", vendor=").append(java.util.Arrays.toString(this.mVendorIds));
        }
        append.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        return append.toString();
    }

    public int hashCode() {
        return this.mPrimaryId.hashCode();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof android.hardware.radio.ProgramSelector) {
            return this.mPrimaryId.equals(((android.hardware.radio.ProgramSelector) obj).getPrimaryId());
        }
        return false;
    }

    public boolean strictEquals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.hardware.radio.ProgramSelector)) {
            return false;
        }
        android.hardware.radio.ProgramSelector programSelector = (android.hardware.radio.ProgramSelector) obj;
        return this.mPrimaryId.equals(programSelector.getPrimaryId()) && this.mSecondaryIds.length == programSelector.mSecondaryIds.length && java.util.Arrays.asList(this.mSecondaryIds).containsAll(java.util.Arrays.asList(programSelector.mSecondaryIds));
    }

    private ProgramSelector(android.os.Parcel parcel) {
        this.mProgramType = parcel.readInt();
        this.mPrimaryId = (android.hardware.radio.ProgramSelector.Identifier) parcel.readTypedObject(android.hardware.radio.ProgramSelector.Identifier.CREATOR);
        this.mSecondaryIds = (android.hardware.radio.ProgramSelector.Identifier[]) parcel.createTypedArray(android.hardware.radio.ProgramSelector.Identifier.CREATOR);
        if (java.util.stream.Stream.of((java.lang.Object[]) this.mSecondaryIds).anyMatch(new java.util.function.Predicate() { // from class: android.hardware.radio.ProgramSelector$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return android.hardware.radio.ProgramSelector.lambda$new$3((android.hardware.radio.ProgramSelector.Identifier) obj);
            }
        })) {
            throw new java.lang.IllegalArgumentException("secondaryIds list must not contain nulls");
        }
        this.mVendorIds = parcel.createLongArray();
    }

    static /* synthetic */ boolean lambda$new$3(android.hardware.radio.ProgramSelector.Identifier identifier) {
        return identifier == null;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mProgramType);
        parcel.writeTypedObject(this.mPrimaryId, 0);
        parcel.writeTypedArray(this.mSecondaryIds, 0);
        parcel.writeLongArray(this.mVendorIds);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Identifier implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.hardware.radio.ProgramSelector.Identifier> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.ProgramSelector.Identifier>() { // from class: android.hardware.radio.ProgramSelector.Identifier.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.radio.ProgramSelector.Identifier createFromParcel(android.os.Parcel parcel) {
                return new android.hardware.radio.ProgramSelector.Identifier(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.radio.ProgramSelector.Identifier[] newArray(int i) {
                return new android.hardware.radio.ProgramSelector.Identifier[i];
            }
        };
        private final int mType;
        private final long mValue;

        public Identifier(int i, long j) {
            this.mType = i == 10004 ? 4 : i;
            this.mValue = j;
        }

        public int getType() {
            if (this.mType == 4 && this.mValue > 10) {
                return 10004;
            }
            return this.mType;
        }

        public boolean isCategoryType() {
            return (this.mType >= 1000 && this.mType <= 1999) || this.mType == 6;
        }

        public long getValue() {
            return this.mValue;
        }

        public java.lang.String toString() {
            return "Identifier(" + this.mType + ", " + this.mValue + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(this.mType), java.lang.Long.valueOf(this.mValue));
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof android.hardware.radio.ProgramSelector.Identifier)) {
                return false;
            }
            android.hardware.radio.ProgramSelector.Identifier identifier = (android.hardware.radio.ProgramSelector.Identifier) obj;
            return identifier.getType() == this.mType && identifier.getValue() == this.mValue;
        }

        private Identifier(android.os.Parcel parcel) {
            this.mType = parcel.readInt();
            this.mValue = parcel.readLong();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mType);
            parcel.writeLong(this.mValue);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }
    }
}
