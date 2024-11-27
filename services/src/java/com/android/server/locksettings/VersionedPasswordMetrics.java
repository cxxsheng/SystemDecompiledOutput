package com.android.server.locksettings;

/* loaded from: classes2.dex */
class VersionedPasswordMetrics {
    private static final int VERSION_1 = 1;
    private final android.app.admin.PasswordMetrics mMetrics;
    private final int mVersion;

    private VersionedPasswordMetrics(int i, android.app.admin.PasswordMetrics passwordMetrics) {
        this.mMetrics = passwordMetrics;
        this.mVersion = i;
    }

    public VersionedPasswordMetrics(com.android.internal.widget.LockscreenCredential lockscreenCredential) {
        this(1, android.app.admin.PasswordMetrics.computeForCredential(lockscreenCredential));
    }

    public int getVersion() {
        return this.mVersion;
    }

    public android.app.admin.PasswordMetrics getMetrics() {
        return this.mMetrics;
    }

    public byte[] serialize() {
        java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate(44);
        allocate.putInt(this.mVersion);
        allocate.putInt(this.mMetrics.credType);
        allocate.putInt(this.mMetrics.length);
        allocate.putInt(this.mMetrics.letters);
        allocate.putInt(this.mMetrics.upperCase);
        allocate.putInt(this.mMetrics.lowerCase);
        allocate.putInt(this.mMetrics.numeric);
        allocate.putInt(this.mMetrics.symbols);
        allocate.putInt(this.mMetrics.nonLetter);
        allocate.putInt(this.mMetrics.nonNumeric);
        allocate.putInt(this.mMetrics.seqLength);
        return allocate.array();
    }

    public static com.android.server.locksettings.VersionedPasswordMetrics deserialize(byte[] bArr) {
        java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate(bArr.length);
        allocate.put(bArr, 0, bArr.length);
        allocate.flip();
        return new com.android.server.locksettings.VersionedPasswordMetrics(allocate.getInt(), new android.app.admin.PasswordMetrics(allocate.getInt(), allocate.getInt(), allocate.getInt(), allocate.getInt(), allocate.getInt(), allocate.getInt(), allocate.getInt(), allocate.getInt(), allocate.getInt(), allocate.getInt()));
    }
}
