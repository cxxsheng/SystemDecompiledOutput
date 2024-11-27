package android.content.integrity;

/* loaded from: classes.dex */
public abstract class AtomicFormula extends android.content.integrity.IntegrityFormula {
    public static final int APP_CERTIFICATE = 1;
    public static final int APP_CERTIFICATE_LINEAGE = 8;
    public static final int EQ = 0;
    public static final int GT = 1;
    public static final int GTE = 2;
    public static final int INSTALLER_CERTIFICATE = 3;
    public static final int INSTALLER_NAME = 2;
    public static final int PACKAGE_NAME = 0;
    public static final int PRE_INSTALLED = 5;
    public static final int STAMP_CERTIFICATE_HASH = 7;
    public static final int STAMP_TRUSTED = 6;
    public static final int VERSION_CODE = 4;
    private final int mKey;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Key {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Operator {
    }

    public AtomicFormula(int i) {
        com.android.internal.util.Preconditions.checkArgument(isValidKey(i), "Unknown key: %d", java.lang.Integer.valueOf(i));
        this.mKey = i;
    }

    public static final class LongAtomicFormula extends android.content.integrity.AtomicFormula implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.content.integrity.AtomicFormula.LongAtomicFormula> CREATOR = new android.os.Parcelable.Creator<android.content.integrity.AtomicFormula.LongAtomicFormula>() { // from class: android.content.integrity.AtomicFormula.LongAtomicFormula.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.integrity.AtomicFormula.LongAtomicFormula createFromParcel(android.os.Parcel parcel) {
                return new android.content.integrity.AtomicFormula.LongAtomicFormula(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.integrity.AtomicFormula.LongAtomicFormula[] newArray(int i) {
                return new android.content.integrity.AtomicFormula.LongAtomicFormula[i];
            }
        };
        private final java.lang.Integer mOperator;
        private final java.lang.Long mValue;

        public LongAtomicFormula(int i) {
            super(i);
            com.android.internal.util.Preconditions.checkArgument(i == 4, "Key %s cannot be used with LongAtomicFormula", keyToString(i));
            this.mValue = null;
            this.mOperator = null;
        }

        public LongAtomicFormula(int i, int i2, long j) {
            super(i);
            com.android.internal.util.Preconditions.checkArgument(i == 4, "Key %s cannot be used with LongAtomicFormula", keyToString(i));
            com.android.internal.util.Preconditions.checkArgument(isValidOperator(i2), "Unknown operator: %d", java.lang.Integer.valueOf(i2));
            this.mOperator = java.lang.Integer.valueOf(i2);
            this.mValue = java.lang.Long.valueOf(j);
        }

        LongAtomicFormula(android.os.Parcel parcel) {
            super(parcel.readInt());
            this.mValue = java.lang.Long.valueOf(parcel.readLong());
            this.mOperator = java.lang.Integer.valueOf(parcel.readInt());
        }

        @Override // android.content.integrity.IntegrityFormula
        public int getTag() {
            return 2;
        }

        @Override // android.content.integrity.IntegrityFormula
        public boolean matches(android.content.integrity.AppInstallMetadata appInstallMetadata) {
            if (this.mValue == null || this.mOperator == null) {
                return false;
            }
            long longMetadataValue = getLongMetadataValue(appInstallMetadata, getKey());
            switch (this.mOperator.intValue()) {
                case 0:
                    return longMetadataValue == this.mValue.longValue();
                case 1:
                    return longMetadataValue > this.mValue.longValue();
                case 2:
                    return longMetadataValue >= this.mValue.longValue();
                default:
                    throw new java.lang.IllegalArgumentException(java.lang.String.format("Unexpected operator %d", this.mOperator));
            }
        }

        @Override // android.content.integrity.IntegrityFormula
        public boolean isAppCertificateFormula() {
            return false;
        }

        @Override // android.content.integrity.IntegrityFormula
        public boolean isAppCertificateLineageFormula() {
            return false;
        }

        @Override // android.content.integrity.IntegrityFormula
        public boolean isInstallerFormula() {
            return false;
        }

        public java.lang.String toString() {
            if (this.mValue == null || this.mOperator == null) {
                return java.lang.String.format("(%s)", keyToString(getKey()));
            }
            return java.lang.String.format("(%s %s %s)", keyToString(getKey()), operatorToString(this.mOperator.intValue()), this.mValue);
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.content.integrity.AtomicFormula.LongAtomicFormula longAtomicFormula = (android.content.integrity.AtomicFormula.LongAtomicFormula) obj;
            if (getKey() == longAtomicFormula.getKey() && java.util.Objects.equals(this.mValue, longAtomicFormula.mValue) && java.util.Objects.equals(this.mOperator, longAtomicFormula.mOperator)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(getKey()), this.mOperator, this.mValue);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            if (this.mValue == null || this.mOperator == null) {
                throw new java.lang.IllegalStateException("Cannot write an empty LongAtomicFormula.");
            }
            parcel.writeInt(getKey());
            parcel.writeLong(this.mValue.longValue());
            parcel.writeInt(this.mOperator.intValue());
        }

        public java.lang.Long getValue() {
            return this.mValue;
        }

        public java.lang.Integer getOperator() {
            return this.mOperator;
        }

        private static boolean isValidOperator(int i) {
            return i == 0 || i == 1 || i == 2;
        }

        private static long getLongMetadataValue(android.content.integrity.AppInstallMetadata appInstallMetadata, int i) {
            switch (i) {
                case 4:
                    return appInstallMetadata.getVersionCode();
                default:
                    throw new java.lang.IllegalStateException("Unexpected key in IntAtomicFormula" + i);
            }
        }
    }

    public static final class StringAtomicFormula extends android.content.integrity.AtomicFormula implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.content.integrity.AtomicFormula.StringAtomicFormula> CREATOR = new android.os.Parcelable.Creator<android.content.integrity.AtomicFormula.StringAtomicFormula>() { // from class: android.content.integrity.AtomicFormula.StringAtomicFormula.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.integrity.AtomicFormula.StringAtomicFormula createFromParcel(android.os.Parcel parcel) {
                return new android.content.integrity.AtomicFormula.StringAtomicFormula(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.integrity.AtomicFormula.StringAtomicFormula[] newArray(int i) {
                return new android.content.integrity.AtomicFormula.StringAtomicFormula[i];
            }
        };
        private final java.lang.Boolean mIsHashedValue;
        private final java.lang.String mValue;

        public StringAtomicFormula(int i) {
            super(i);
            boolean z = true;
            if (i != 0 && i != 1 && i != 3 && i != 2 && i != 7 && i != 8) {
                z = false;
            }
            com.android.internal.util.Preconditions.checkArgument(z, "Key %s cannot be used with StringAtomicFormula", keyToString(i));
            this.mValue = null;
            this.mIsHashedValue = null;
        }

        public StringAtomicFormula(int i, java.lang.String str, boolean z) {
            super(i);
            boolean z2 = true;
            if (i != 0 && i != 1 && i != 3 && i != 2 && i != 7 && i != 8) {
                z2 = false;
            }
            com.android.internal.util.Preconditions.checkArgument(z2, "Key %s cannot be used with StringAtomicFormula", keyToString(i));
            this.mValue = str;
            this.mIsHashedValue = java.lang.Boolean.valueOf(z);
        }

        public StringAtomicFormula(int i, java.lang.String str) {
            super(i);
            com.android.internal.util.Preconditions.checkArgument(i == 0 || i == 1 || i == 3 || i == 2 || i == 7 || i == 8, "Key %s cannot be used with StringAtomicFormula", keyToString(i));
            this.mValue = hashValue(i, str);
            this.mIsHashedValue = java.lang.Boolean.valueOf(i == 1 || i == 3 || i == 7 || i == 8 || !this.mValue.equals(str));
        }

        StringAtomicFormula(android.os.Parcel parcel) {
            super(parcel.readInt());
            this.mValue = parcel.readStringNoHelper();
            this.mIsHashedValue = java.lang.Boolean.valueOf(parcel.readByte() != 0);
        }

        @Override // android.content.integrity.IntegrityFormula
        public int getTag() {
            return 1;
        }

        @Override // android.content.integrity.IntegrityFormula
        public boolean matches(android.content.integrity.AppInstallMetadata appInstallMetadata) {
            if (this.mValue == null || this.mIsHashedValue == null) {
                return false;
            }
            return getMetadataValue(appInstallMetadata, getKey()).contains(this.mValue);
        }

        @Override // android.content.integrity.IntegrityFormula
        public boolean isAppCertificateFormula() {
            return getKey() == 1;
        }

        @Override // android.content.integrity.IntegrityFormula
        public boolean isAppCertificateLineageFormula() {
            return getKey() == 8;
        }

        @Override // android.content.integrity.IntegrityFormula
        public boolean isInstallerFormula() {
            return getKey() == 2 || getKey() == 3;
        }

        public java.lang.String toString() {
            if (this.mValue == null || this.mIsHashedValue == null) {
                return java.lang.String.format("(%s)", keyToString(getKey()));
            }
            return java.lang.String.format("(%s %s %s)", keyToString(getKey()), operatorToString(0), this.mValue);
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.content.integrity.AtomicFormula.StringAtomicFormula stringAtomicFormula = (android.content.integrity.AtomicFormula.StringAtomicFormula) obj;
            if (getKey() == stringAtomicFormula.getKey() && java.util.Objects.equals(this.mValue, stringAtomicFormula.mValue)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(getKey()), this.mValue);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            if (this.mValue == null || this.mIsHashedValue == null) {
                throw new java.lang.IllegalStateException("Cannot write an empty StringAtomicFormula.");
            }
            parcel.writeInt(getKey());
            parcel.writeStringNoHelper(this.mValue);
            parcel.writeByte(this.mIsHashedValue.booleanValue() ? (byte) 1 : (byte) 0);
        }

        public java.lang.String getValue() {
            return this.mValue;
        }

        public java.lang.Boolean getIsHashedValue() {
            return this.mIsHashedValue;
        }

        private static java.util.List<java.lang.String> getMetadataValue(android.content.integrity.AppInstallMetadata appInstallMetadata, int i) {
            switch (i) {
                case 0:
                    return java.util.Collections.singletonList(appInstallMetadata.getPackageName());
                case 1:
                    return appInstallMetadata.getAppCertificates();
                case 2:
                    return java.util.Collections.singletonList(appInstallMetadata.getInstallerName());
                case 3:
                    return appInstallMetadata.getInstallerCertificates();
                case 4:
                case 5:
                case 6:
                default:
                    throw new java.lang.IllegalStateException("Unexpected key in StringAtomicFormula: " + i);
                case 7:
                    return java.util.Collections.singletonList(appInstallMetadata.getStampCertificateHash());
                case 8:
                    return appInstallMetadata.getAppCertificateLineage();
            }
        }

        private static java.lang.String hashValue(int i, java.lang.String str) {
            if (str.length() > 32 && (i == 0 || i == 2)) {
                return hash(str);
            }
            return str;
        }

        private static java.lang.String hash(java.lang.String str) {
            try {
                return android.content.integrity.IntegrityUtils.getHexDigest(java.security.MessageDigest.getInstance("SHA-256").digest(str.getBytes(java.nio.charset.StandardCharsets.UTF_8)));
            } catch (java.security.NoSuchAlgorithmException e) {
                throw new java.lang.RuntimeException("SHA-256 algorithm not found", e);
            }
        }
    }

    public static final class BooleanAtomicFormula extends android.content.integrity.AtomicFormula implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.content.integrity.AtomicFormula.BooleanAtomicFormula> CREATOR = new android.os.Parcelable.Creator<android.content.integrity.AtomicFormula.BooleanAtomicFormula>() { // from class: android.content.integrity.AtomicFormula.BooleanAtomicFormula.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.integrity.AtomicFormula.BooleanAtomicFormula createFromParcel(android.os.Parcel parcel) {
                return new android.content.integrity.AtomicFormula.BooleanAtomicFormula(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.integrity.AtomicFormula.BooleanAtomicFormula[] newArray(int i) {
                return new android.content.integrity.AtomicFormula.BooleanAtomicFormula[i];
            }
        };
        private final java.lang.Boolean mValue;

        public BooleanAtomicFormula(int i) {
            super(i);
            com.android.internal.util.Preconditions.checkArgument(i == 5 || i == 6, java.lang.String.format("Key %s cannot be used with BooleanAtomicFormula", keyToString(i)));
            this.mValue = null;
        }

        public BooleanAtomicFormula(int i, boolean z) {
            super(i);
            com.android.internal.util.Preconditions.checkArgument(i == 5 || i == 6, java.lang.String.format("Key %s cannot be used with BooleanAtomicFormula", keyToString(i)));
            this.mValue = java.lang.Boolean.valueOf(z);
        }

        BooleanAtomicFormula(android.os.Parcel parcel) {
            super(parcel.readInt());
            this.mValue = java.lang.Boolean.valueOf(parcel.readByte() != 0);
        }

        @Override // android.content.integrity.IntegrityFormula
        public int getTag() {
            return 3;
        }

        @Override // android.content.integrity.IntegrityFormula
        public boolean matches(android.content.integrity.AppInstallMetadata appInstallMetadata) {
            return this.mValue != null && getBooleanMetadataValue(appInstallMetadata, getKey()) == this.mValue.booleanValue();
        }

        @Override // android.content.integrity.IntegrityFormula
        public boolean isAppCertificateFormula() {
            return false;
        }

        @Override // android.content.integrity.IntegrityFormula
        public boolean isAppCertificateLineageFormula() {
            return false;
        }

        @Override // android.content.integrity.IntegrityFormula
        public boolean isInstallerFormula() {
            return false;
        }

        public java.lang.String toString() {
            if (this.mValue == null) {
                return java.lang.String.format("(%s)", keyToString(getKey()));
            }
            return java.lang.String.format("(%s %s %s)", keyToString(getKey()), operatorToString(0), this.mValue);
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.content.integrity.AtomicFormula.BooleanAtomicFormula booleanAtomicFormula = (android.content.integrity.AtomicFormula.BooleanAtomicFormula) obj;
            if (getKey() == booleanAtomicFormula.getKey() && java.util.Objects.equals(this.mValue, booleanAtomicFormula.mValue)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(getKey()), this.mValue);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            if (this.mValue == null) {
                throw new java.lang.IllegalStateException("Cannot write an empty BooleanAtomicFormula.");
            }
            parcel.writeInt(getKey());
            parcel.writeByte(this.mValue.booleanValue() ? (byte) 1 : (byte) 0);
        }

        public java.lang.Boolean getValue() {
            return this.mValue;
        }

        private static boolean getBooleanMetadataValue(android.content.integrity.AppInstallMetadata appInstallMetadata, int i) {
            switch (i) {
                case 5:
                    return appInstallMetadata.isPreInstalled();
                case 6:
                    return appInstallMetadata.isStampTrusted();
                default:
                    throw new java.lang.IllegalStateException("Unexpected key in BooleanAtomicFormula: " + i);
            }
        }
    }

    public int getKey() {
        return this.mKey;
    }

    static java.lang.String keyToString(int i) {
        switch (i) {
            case 0:
                return "PACKAGE_NAME";
            case 1:
                return "APP_CERTIFICATE";
            case 2:
                return "INSTALLER_NAME";
            case 3:
                return "INSTALLER_CERTIFICATE";
            case 4:
                return "VERSION_CODE";
            case 5:
                return "PRE_INSTALLED";
            case 6:
                return "STAMP_TRUSTED";
            case 7:
                return "STAMP_CERTIFICATE_HASH";
            case 8:
                return "APP_CERTIFICATE_LINEAGE";
            default:
                throw new java.lang.IllegalArgumentException("Unknown key " + i);
        }
    }

    static java.lang.String operatorToString(int i) {
        switch (i) {
            case 0:
                return "EQ";
            case 1:
                return "GT";
            case 2:
                return "GTE";
            default:
                throw new java.lang.IllegalArgumentException("Unknown operator " + i);
        }
    }

    private static boolean isValidKey(int i) {
        return i == 0 || i == 1 || i == 4 || i == 2 || i == 3 || i == 5 || i == 6 || i == 7 || i == 8;
    }
}
