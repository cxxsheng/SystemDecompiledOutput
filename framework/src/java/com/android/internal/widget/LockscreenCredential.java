package com.android.internal.widget;

/* loaded from: classes5.dex */
public class LockscreenCredential implements android.os.Parcelable, java.lang.AutoCloseable {
    public static final android.os.Parcelable.Creator<com.android.internal.widget.LockscreenCredential> CREATOR = new android.os.Parcelable.Creator<com.android.internal.widget.LockscreenCredential>() { // from class: com.android.internal.widget.LockscreenCredential.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.widget.LockscreenCredential createFromParcel(android.os.Parcel parcel) {
            return new com.android.internal.widget.LockscreenCredential(parcel.readInt(), parcel.createByteArray(), parcel.readBoolean());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.widget.LockscreenCredential[] newArray(int i) {
            return new com.android.internal.widget.LockscreenCredential[i];
        }
    };
    private byte[] mCredential;
    private final boolean mHasInvalidChars;
    private final int mType;

    private LockscreenCredential(int i, byte[] bArr, boolean z) {
        java.util.Objects.requireNonNull(bArr);
        if (i == -1) {
            com.android.internal.util.Preconditions.checkArgument(bArr.length == 0);
        } else {
            com.android.internal.util.Preconditions.checkArgument(i == 3 || i == 4 || i == 1);
        }
        this.mType = i;
        this.mCredential = bArr;
        this.mHasInvalidChars = z;
    }

    private LockscreenCredential(int i, java.lang.CharSequence charSequence) {
        this(i, charsToBytesTruncating(charSequence), hasInvalidChars(charSequence));
    }

    public static com.android.internal.widget.LockscreenCredential createNone() {
        return new com.android.internal.widget.LockscreenCredential(-1, new byte[0], false);
    }

    public static com.android.internal.widget.LockscreenCredential createPattern(java.util.List<com.android.internal.widget.LockPatternView.Cell> list, byte b) {
        return new com.android.internal.widget.LockscreenCredential(1, com.android.internal.widget.LockPatternUtils.patternToByteArray(list, b), false);
    }

    public static com.android.internal.widget.LockscreenCredential createPassword(java.lang.CharSequence charSequence) {
        return new com.android.internal.widget.LockscreenCredential(4, charSequence);
    }

    public static com.android.internal.widget.LockscreenCredential createUnifiedProfilePassword(byte[] bArr) {
        return new com.android.internal.widget.LockscreenCredential(4, java.util.Arrays.copyOf(bArr, bArr.length), false);
    }

    public static com.android.internal.widget.LockscreenCredential createPin(java.lang.CharSequence charSequence) {
        return new com.android.internal.widget.LockscreenCredential(3, charSequence);
    }

    public static com.android.internal.widget.LockscreenCredential createPasswordOrNone(java.lang.CharSequence charSequence) {
        if (android.text.TextUtils.isEmpty(charSequence)) {
            return createNone();
        }
        return createPassword(charSequence);
    }

    public static com.android.internal.widget.LockscreenCredential createPinOrNone(java.lang.CharSequence charSequence) {
        if (android.text.TextUtils.isEmpty(charSequence)) {
            return createNone();
        }
        return createPin(charSequence);
    }

    private void ensureNotZeroized() {
        com.android.internal.util.Preconditions.checkState(this.mCredential != null, "Credential is already zeroized");
    }

    public int getType() {
        ensureNotZeroized();
        return this.mType;
    }

    public byte[] getCredential() {
        ensureNotZeroized();
        return this.mCredential;
    }

    public boolean isNone() {
        ensureNotZeroized();
        return this.mType == -1;
    }

    public boolean isPattern() {
        ensureNotZeroized();
        return this.mType == 1;
    }

    public boolean isPin() {
        ensureNotZeroized();
        return this.mType == 3;
    }

    public boolean isPassword() {
        ensureNotZeroized();
        return this.mType == 4;
    }

    public int size() {
        ensureNotZeroized();
        return this.mCredential.length;
    }

    public boolean hasInvalidChars() {
        ensureNotZeroized();
        return this.mHasInvalidChars;
    }

    public com.android.internal.widget.LockscreenCredential duplicate() {
        return new com.android.internal.widget.LockscreenCredential(this.mType, this.mCredential != null ? java.util.Arrays.copyOf(this.mCredential, this.mCredential.length) : null, this.mHasInvalidChars);
    }

    public void zeroize() {
        if (this.mCredential != null) {
            java.util.Arrays.fill(this.mCredential, (byte) 0);
            this.mCredential = null;
        }
    }

    public void validateBasicRequirements() {
        if (this.mHasInvalidChars) {
            throw new java.lang.IllegalArgumentException("credential contains invalid characters");
        }
        switch (getType()) {
            case 1:
                if (size() < 4) {
                    throw new java.lang.IllegalArgumentException("pattern must be at least 4 dots long.");
                }
                return;
            case 2:
            default:
                return;
            case 3:
                if (size() < 4) {
                    throw new java.lang.IllegalArgumentException("PIN must be at least 4 digits long.");
                }
                return;
            case 4:
                if (size() < 4) {
                    throw new java.lang.IllegalArgumentException("password must be at least 4 characters long.");
                }
                return;
        }
    }

    public boolean checkAgainstStoredType(int i) {
        return i == 2 ? getType() == 4 || getType() == 3 : getType() == i;
    }

    public java.lang.String passwordToHistoryHash(byte[] bArr, byte[] bArr2) {
        return passwordToHistoryHash(this.mCredential, bArr, bArr2);
    }

    public static java.lang.String passwordToHistoryHash(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        if (bArr == null || bArr.length == 0 || bArr3 == null || bArr2 == null) {
            return null;
        }
        try {
            java.security.MessageDigest messageDigest = java.security.MessageDigest.getInstance("SHA-256");
            messageDigest.update(bArr3);
            messageDigest.update(bArr);
            messageDigest.update(bArr2);
            return libcore.util.HexEncoding.encodeToString(messageDigest.digest());
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new java.lang.AssertionError("Missing digest algorithm: ", e);
        }
    }

    @java.lang.Deprecated
    public static java.lang.String legacyPasswordToHash(byte[] bArr, byte[] bArr2) {
        if (bArr == null || bArr.length == 0 || bArr2 == null) {
            return null;
        }
        try {
            byte[] concat = com.android.internal.util.ArrayUtils.concat(bArr, bArr2);
            byte[] digest = java.security.MessageDigest.getInstance("SHA-1").digest(concat);
            byte[] digest2 = java.security.MessageDigest.getInstance(android.security.keystore.KeyProperties.DIGEST_MD5).digest(concat);
            java.util.Arrays.fill(concat, (byte) 0);
            return libcore.util.HexEncoding.encodeToString(com.android.internal.util.ArrayUtils.concat(digest, digest2));
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new java.lang.AssertionError("Missing digest algorithm: ", e);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeByteArray(this.mCredential);
        parcel.writeBoolean(this.mHasInvalidChars);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        zeroize();
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mType), java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mCredential)), java.lang.Boolean.valueOf(this.mHasInvalidChars));
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof com.android.internal.widget.LockscreenCredential)) {
            return false;
        }
        com.android.internal.widget.LockscreenCredential lockscreenCredential = (com.android.internal.widget.LockscreenCredential) obj;
        return this.mType == lockscreenCredential.mType && java.util.Arrays.equals(this.mCredential, lockscreenCredential.mCredential) && this.mHasInvalidChars == lockscreenCredential.mHasInvalidChars;
    }

    private static boolean hasInvalidChars(java.lang.CharSequence charSequence) {
        for (int i = 0; i < charSequence.length(); i++) {
            char charAt = charSequence.charAt(i);
            if (charAt < ' ' || charAt > 127) {
                return true;
            }
        }
        return false;
    }

    private static byte[] charsToBytesTruncating(java.lang.CharSequence charSequence) {
        byte[] bArr = new byte[charSequence.length()];
        for (int i = 0; i < charSequence.length(); i++) {
            bArr[i] = (byte) charSequence.charAt(i);
        }
        return bArr;
    }
}
