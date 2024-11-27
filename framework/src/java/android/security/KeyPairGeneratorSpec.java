package android.security;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public final class KeyPairGeneratorSpec implements java.security.spec.AlgorithmParameterSpec {
    private final android.content.Context mContext;
    private final java.util.Date mEndDate;
    private final int mKeySize;
    private final java.lang.String mKeyType;
    private final java.lang.String mKeystoreAlias;
    private final java.math.BigInteger mSerialNumber;
    private final java.security.spec.AlgorithmParameterSpec mSpec;
    private final java.util.Date mStartDate;
    private final javax.security.auth.x500.X500Principal mSubjectDN;

    public KeyPairGeneratorSpec(android.content.Context context, java.lang.String str, java.lang.String str2, int i, java.security.spec.AlgorithmParameterSpec algorithmParameterSpec, javax.security.auth.x500.X500Principal x500Principal, java.math.BigInteger bigInteger, java.util.Date date, java.util.Date date2, int i2) {
        if (context == null) {
            throw new java.lang.IllegalArgumentException("context == null");
        }
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("keyStoreAlias must not be empty");
        }
        if (x500Principal == null) {
            throw new java.lang.IllegalArgumentException("subjectDN == null");
        }
        if (bigInteger == null) {
            throw new java.lang.IllegalArgumentException("serialNumber == null");
        }
        if (date == null) {
            throw new java.lang.IllegalArgumentException("startDate == null");
        }
        if (date2 == null) {
            throw new java.lang.IllegalArgumentException("endDate == null");
        }
        if (date2.before(date)) {
            throw new java.lang.IllegalArgumentException("endDate < startDate");
        }
        if (date2.before(date)) {
            throw new java.lang.IllegalArgumentException("endDate < startDate");
        }
        this.mContext = context;
        this.mKeystoreAlias = str;
        this.mKeyType = str2;
        this.mKeySize = i;
        this.mSpec = algorithmParameterSpec;
        this.mSubjectDN = x500Principal;
        this.mSerialNumber = bigInteger;
        this.mStartDate = date;
        this.mEndDate = date2;
    }

    public android.content.Context getContext() {
        return this.mContext;
    }

    public java.lang.String getKeystoreAlias() {
        return this.mKeystoreAlias;
    }

    public java.lang.String getKeyType() {
        return this.mKeyType;
    }

    public int getKeySize() {
        return this.mKeySize;
    }

    public java.security.spec.AlgorithmParameterSpec getAlgorithmParameterSpec() {
        return this.mSpec;
    }

    public javax.security.auth.x500.X500Principal getSubjectDN() {
        return this.mSubjectDN;
    }

    public java.math.BigInteger getSerialNumber() {
        return this.mSerialNumber;
    }

    public java.util.Date getStartDate() {
        return this.mStartDate;
    }

    public java.util.Date getEndDate() {
        return this.mEndDate;
    }

    public int getFlags() {
        return 0;
    }

    @java.lang.Deprecated
    public boolean isEncryptionRequired() {
        return false;
    }

    @java.lang.Deprecated
    public static final class Builder {
        private final android.content.Context mContext;
        private java.util.Date mEndDate;
        private int mKeySize = -1;
        private java.lang.String mKeyType;
        private java.lang.String mKeystoreAlias;
        private java.math.BigInteger mSerialNumber;
        private java.security.spec.AlgorithmParameterSpec mSpec;
        private java.util.Date mStartDate;
        private javax.security.auth.x500.X500Principal mSubjectDN;

        public Builder(android.content.Context context) {
            if (context == null) {
                throw new java.lang.NullPointerException("context == null");
            }
            this.mContext = context;
        }

        public android.security.KeyPairGeneratorSpec.Builder setAlias(java.lang.String str) {
            if (str == null) {
                throw new java.lang.NullPointerException("alias == null");
            }
            this.mKeystoreAlias = str;
            return this;
        }

        public android.security.KeyPairGeneratorSpec.Builder setKeyType(java.lang.String str) throws java.security.NoSuchAlgorithmException {
            if (str == null) {
                throw new java.lang.NullPointerException("keyType == null");
            }
            try {
                android.security.keystore.KeyProperties.KeyAlgorithm.toKeymasterAsymmetricKeyAlgorithm(str);
                this.mKeyType = str;
                return this;
            } catch (java.lang.IllegalArgumentException e) {
                throw new java.security.NoSuchAlgorithmException("Unsupported key type: " + str);
            }
        }

        public android.security.KeyPairGeneratorSpec.Builder setKeySize(int i) {
            if (i < 0) {
                throw new java.lang.IllegalArgumentException("keySize < 0");
            }
            this.mKeySize = i;
            return this;
        }

        public android.security.KeyPairGeneratorSpec.Builder setAlgorithmParameterSpec(java.security.spec.AlgorithmParameterSpec algorithmParameterSpec) {
            if (algorithmParameterSpec == null) {
                throw new java.lang.NullPointerException("spec == null");
            }
            this.mSpec = algorithmParameterSpec;
            return this;
        }

        public android.security.KeyPairGeneratorSpec.Builder setSubject(javax.security.auth.x500.X500Principal x500Principal) {
            if (x500Principal == null) {
                throw new java.lang.NullPointerException("subject == null");
            }
            this.mSubjectDN = x500Principal;
            return this;
        }

        public android.security.KeyPairGeneratorSpec.Builder setSerialNumber(java.math.BigInteger bigInteger) {
            if (bigInteger == null) {
                throw new java.lang.NullPointerException("serialNumber == null");
            }
            this.mSerialNumber = bigInteger;
            return this;
        }

        public android.security.KeyPairGeneratorSpec.Builder setStartDate(java.util.Date date) {
            if (date == null) {
                throw new java.lang.NullPointerException("startDate == null");
            }
            this.mStartDate = date;
            return this;
        }

        public android.security.KeyPairGeneratorSpec.Builder setEndDate(java.util.Date date) {
            if (date == null) {
                throw new java.lang.NullPointerException("endDate == null");
            }
            this.mEndDate = date;
            return this;
        }

        @java.lang.Deprecated
        public android.security.KeyPairGeneratorSpec.Builder setEncryptionRequired() {
            return this;
        }

        public android.security.KeyPairGeneratorSpec build() {
            return new android.security.KeyPairGeneratorSpec(this.mContext, this.mKeystoreAlias, this.mKeyType, this.mKeySize, this.mSpec, this.mSubjectDN, this.mSerialNumber, this.mStartDate, this.mEndDate, 0);
        }
    }
}
