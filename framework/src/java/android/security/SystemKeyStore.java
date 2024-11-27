package android.security;

/* loaded from: classes3.dex */
public class SystemKeyStore {
    private static final java.lang.String KEY_FILE_EXTENSION = ".sks";
    private static final java.lang.String SYSTEM_KEYSTORE_DIRECTORY = "misc/systemkeys";
    private static android.security.SystemKeyStore mInstance = new android.security.SystemKeyStore();

    private SystemKeyStore() {
    }

    public static android.security.SystemKeyStore getInstance() {
        return mInstance;
    }

    public static java.lang.String toHexString(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        int length = bArr.length;
        java.lang.StringBuilder sb = new java.lang.StringBuilder(bArr.length * 2);
        for (byte b : bArr) {
            java.lang.String num = java.lang.Integer.toString(b & 255, 16);
            if (num.length() == 1) {
                num = android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS + num;
            }
            sb.append(num);
        }
        return sb.toString();
    }

    public java.lang.String generateNewKeyHexString(int i, java.lang.String str, java.lang.String str2) throws java.security.NoSuchAlgorithmException {
        return toHexString(generateNewKey(i, str, str2));
    }

    public byte[] generateNewKey(int i, java.lang.String str, java.lang.String str2) throws java.security.NoSuchAlgorithmException {
        android.os.StrictMode.noteDiskWrite();
        java.io.File keyFile = getKeyFile(str2);
        if (keyFile.exists()) {
            throw new java.lang.IllegalArgumentException();
        }
        javax.crypto.KeyGenerator keyGenerator = javax.crypto.KeyGenerator.getInstance(str);
        keyGenerator.init(i, java.security.SecureRandom.getInstance("SHA1PRNG"));
        byte[] encoded = keyGenerator.generateKey().getEncoded();
        try {
            if (!keyFile.createNewFile()) {
                throw new java.lang.IllegalArgumentException();
            }
            java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(keyFile);
            fileOutputStream.write(encoded);
            fileOutputStream.flush();
            android.os.FileUtils.sync(fileOutputStream);
            fileOutputStream.close();
            android.os.FileUtils.setPermissions(keyFile.getName(), 384, -1, -1);
            return encoded;
        } catch (java.io.IOException e) {
            return null;
        }
    }

    private java.io.File getKeyFile(java.lang.String str) {
        android.os.StrictMode.noteDiskWrite();
        return new java.io.File(new java.io.File(android.os.Environment.getDataDirectory(), SYSTEM_KEYSTORE_DIRECTORY), str + KEY_FILE_EXTENSION);
    }

    public java.lang.String retrieveKeyHexString(java.lang.String str) throws java.io.IOException {
        return toHexString(retrieveKey(str));
    }

    public byte[] retrieveKey(java.lang.String str) throws java.io.IOException {
        android.os.StrictMode.noteDiskRead();
        java.io.File keyFile = getKeyFile(str);
        if (!keyFile.exists()) {
            return null;
        }
        return libcore.io.IoUtils.readFileAsByteArray(keyFile.toString());
    }

    public void deleteKey(java.lang.String str) {
        android.os.StrictMode.noteDiskWrite();
        java.io.File keyFile = getKeyFile(str);
        if (!keyFile.exists()) {
            throw new java.lang.IllegalArgumentException();
        }
        keyFile.delete();
    }
}
