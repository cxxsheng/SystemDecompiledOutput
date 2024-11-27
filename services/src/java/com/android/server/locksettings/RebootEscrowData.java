package com.android.server.locksettings;

/* loaded from: classes2.dex */
class RebootEscrowData {
    private static final int CURRENT_VERSION = 2;
    private static final int LEGACY_SINGLE_ENCRYPTED_VERSION = 1;
    private final byte[] mBlob;
    private final com.android.server.locksettings.RebootEscrowKey mKey;
    private final byte mSpVersion;
    private final byte[] mSyntheticPassword;

    private RebootEscrowData(byte b, byte[] bArr, byte[] bArr2, com.android.server.locksettings.RebootEscrowKey rebootEscrowKey) {
        this.mSpVersion = b;
        this.mSyntheticPassword = bArr;
        this.mBlob = bArr2;
        this.mKey = rebootEscrowKey;
    }

    public byte getSpVersion() {
        return this.mSpVersion;
    }

    public byte[] getSyntheticPassword() {
        return this.mSyntheticPassword;
    }

    public byte[] getBlob() {
        return this.mBlob;
    }

    public com.android.server.locksettings.RebootEscrowKey getKey() {
        return this.mKey;
    }

    private static byte[] decryptBlobCurrentVersion(javax.crypto.SecretKey secretKey, com.android.server.locksettings.RebootEscrowKey rebootEscrowKey, java.io.DataInputStream dataInputStream) throws java.io.IOException {
        if (secretKey == null) {
            throw new java.io.IOException("Failed to find wrapper key in keystore, cannot decrypt the escrow data");
        }
        return com.android.server.locksettings.AesEncryptionUtil.decrypt(rebootEscrowKey.getKey(), com.android.server.locksettings.AesEncryptionUtil.decrypt(secretKey, dataInputStream));
    }

    static com.android.server.locksettings.RebootEscrowData fromEncryptedData(com.android.server.locksettings.RebootEscrowKey rebootEscrowKey, byte[] bArr, javax.crypto.SecretKey secretKey) throws java.io.IOException {
        java.util.Objects.requireNonNull(rebootEscrowKey);
        java.util.Objects.requireNonNull(bArr);
        java.io.DataInputStream dataInputStream = new java.io.DataInputStream(new java.io.ByteArrayInputStream(bArr));
        int readInt = dataInputStream.readInt();
        byte readByte = dataInputStream.readByte();
        switch (readInt) {
            case 1:
                return new com.android.server.locksettings.RebootEscrowData(readByte, com.android.server.locksettings.AesEncryptionUtil.decrypt(rebootEscrowKey.getKey(), dataInputStream), bArr, rebootEscrowKey);
            case 2:
                return new com.android.server.locksettings.RebootEscrowData(readByte, decryptBlobCurrentVersion(secretKey, rebootEscrowKey, dataInputStream), bArr, rebootEscrowKey);
            default:
                throw new java.io.IOException("Unsupported version " + readInt);
        }
    }

    static com.android.server.locksettings.RebootEscrowData fromSyntheticPassword(com.android.server.locksettings.RebootEscrowKey rebootEscrowKey, byte b, byte[] bArr, javax.crypto.SecretKey secretKey) throws java.io.IOException {
        java.util.Objects.requireNonNull(bArr);
        byte[] encrypt = com.android.server.locksettings.AesEncryptionUtil.encrypt(secretKey, com.android.server.locksettings.AesEncryptionUtil.encrypt(rebootEscrowKey.getKey(), bArr));
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        java.io.DataOutputStream dataOutputStream = new java.io.DataOutputStream(byteArrayOutputStream);
        dataOutputStream.writeInt(2);
        dataOutputStream.writeByte(b);
        dataOutputStream.write(encrypt);
        return new com.android.server.locksettings.RebootEscrowData(b, bArr, byteArrayOutputStream.toByteArray(), rebootEscrowKey);
    }
}
