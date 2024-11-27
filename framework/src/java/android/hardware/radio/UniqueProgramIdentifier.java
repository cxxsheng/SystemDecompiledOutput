package android.hardware.radio;

/* loaded from: classes2.dex */
public final class UniqueProgramIdentifier implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.UniqueProgramIdentifier> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.UniqueProgramIdentifier>() { // from class: android.hardware.radio.UniqueProgramIdentifier.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.UniqueProgramIdentifier createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.radio.UniqueProgramIdentifier(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.UniqueProgramIdentifier[] newArray(int i) {
            return new android.hardware.radio.UniqueProgramIdentifier[i];
        }
    };
    private final android.hardware.radio.ProgramSelector.Identifier[] mCriticalSecondaryIds;
    private final android.hardware.radio.ProgramSelector.Identifier mPrimaryId;

    public static boolean requireCriticalSecondaryIds(int i) {
        return i == 14 || i == 5;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0062  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public UniqueProgramIdentifier(android.hardware.radio.ProgramSelector programSelector) {
        java.util.Objects.requireNonNull(programSelector, "Program selector can not be null");
        this.mPrimaryId = programSelector.getPrimaryId();
        switch (this.mPrimaryId.getType()) {
            case 5:
            case 14:
                android.hardware.radio.ProgramSelector.Identifier[] secondaryIds = programSelector.getSecondaryIds();
                android.hardware.radio.ProgramSelector.Identifier identifier = null;
                android.hardware.radio.ProgramSelector.Identifier identifier2 = null;
                for (int i = 0; i < secondaryIds.length; i++) {
                    if (identifier == null && secondaryIds[i].getType() == 6) {
                        identifier = programSelector.getSecondaryIds()[i];
                    } else if (identifier2 == null && secondaryIds[i].getType() == 8) {
                        identifier2 = secondaryIds[i];
                    }
                    if (identifier != null && identifier2 != null) {
                        if (identifier != null) {
                            if (identifier2 == null) {
                                this.mCriticalSecondaryIds = new android.hardware.radio.ProgramSelector.Identifier[0];
                                break;
                            } else {
                                this.mCriticalSecondaryIds = new android.hardware.radio.ProgramSelector.Identifier[]{identifier2};
                                break;
                            }
                        } else if (identifier2 == null) {
                            this.mCriticalSecondaryIds = new android.hardware.radio.ProgramSelector.Identifier[]{identifier};
                            break;
                        } else {
                            this.mCriticalSecondaryIds = new android.hardware.radio.ProgramSelector.Identifier[]{identifier, identifier2};
                            break;
                        }
                    }
                }
                if (identifier != null) {
                }
                break;
            default:
                this.mCriticalSecondaryIds = new android.hardware.radio.ProgramSelector.Identifier[0];
                break;
        }
    }

    public UniqueProgramIdentifier(android.hardware.radio.ProgramSelector.Identifier identifier) {
        this.mPrimaryId = identifier;
        this.mCriticalSecondaryIds = new android.hardware.radio.ProgramSelector.Identifier[0];
    }

    public android.hardware.radio.ProgramSelector.Identifier getPrimaryId() {
        return this.mPrimaryId;
    }

    public java.util.List<android.hardware.radio.ProgramSelector.Identifier> getCriticalSecondaryIds() {
        return java.util.List.of((java.lang.Object[]) this.mCriticalSecondaryIds);
    }

    public java.lang.String toString() {
        return "UniqueProgramIdentifier(primary=" + this.mPrimaryId + ", criticalSecondary=" + java.util.Arrays.toString(this.mCriticalSecondaryIds) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mPrimaryId, java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mCriticalSecondaryIds)));
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.hardware.radio.UniqueProgramIdentifier)) {
            return false;
        }
        android.hardware.radio.UniqueProgramIdentifier uniqueProgramIdentifier = (android.hardware.radio.UniqueProgramIdentifier) obj;
        return uniqueProgramIdentifier.mPrimaryId.equals(this.mPrimaryId) && java.util.Arrays.equals(uniqueProgramIdentifier.mCriticalSecondaryIds, this.mCriticalSecondaryIds);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private UniqueProgramIdentifier(android.os.Parcel parcel) {
        this.mPrimaryId = (android.hardware.radio.ProgramSelector.Identifier) parcel.readTypedObject(android.hardware.radio.ProgramSelector.Identifier.CREATOR);
        this.mCriticalSecondaryIds = (android.hardware.radio.ProgramSelector.Identifier[]) parcel.createTypedArray(android.hardware.radio.ProgramSelector.Identifier.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedObject(this.mPrimaryId, 0);
        parcel.writeTypedArray(this.mCriticalSecondaryIds, 0);
        if (java.util.stream.Stream.of((java.lang.Object[]) this.mCriticalSecondaryIds).anyMatch(new java.util.function.Predicate() { // from class: android.hardware.radio.UniqueProgramIdentifier$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return java.util.Objects.isNull((android.hardware.radio.ProgramSelector.Identifier) obj);
            }
        })) {
            throw new java.lang.IllegalArgumentException("criticalSecondaryIds list must not contain nulls");
        }
    }
}
