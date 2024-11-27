package com.android.server.backup;

/* loaded from: classes.dex */
public final class BackupPasswordManager {
    private static final int BACKUP_PW_FILE_VERSION = 2;
    private static final boolean DEBUG = false;
    private static final int DEFAULT_PW_FILE_VERSION = 1;
    private static final java.lang.String PASSWORD_HASH_FILE_NAME = "pwhash";
    private static final java.lang.String PASSWORD_VERSION_FILE_NAME = "pwversion";
    public static final java.lang.String PBKDF_CURRENT = "PBKDF2WithHmacSHA1";
    public static final java.lang.String PBKDF_FALLBACK = "PBKDF2WithHmacSHA1And8bit";
    private static final java.lang.String TAG = "BackupPasswordManager";
    private final java.io.File mBaseStateDir;
    private final android.content.Context mContext;
    private java.lang.String mPasswordHash;
    private byte[] mPasswordSalt;
    private int mPasswordVersion;
    private final java.security.SecureRandom mRng;

    BackupPasswordManager(android.content.Context context, java.io.File file, java.security.SecureRandom secureRandom) {
        this.mContext = context;
        this.mRng = secureRandom;
        this.mBaseStateDir = file;
        loadStateFromFilesystem();
    }

    boolean hasBackupPassword() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "hasBackupPassword");
        return this.mPasswordHash != null && this.mPasswordHash.length() > 0;
    }

    boolean backupPasswordMatches(java.lang.String str) {
        if (hasBackupPassword() && !passwordMatchesSaved(str)) {
            return false;
        }
        return true;
    }

    boolean setBackupPassword(java.lang.String str, java.lang.String str2) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "setBackupPassword");
        if (!passwordMatchesSaved(str)) {
            return false;
        }
        try {
            getPasswordVersionFileCodec().serialize(2);
            this.mPasswordVersion = 2;
            if (str2 == null || str2.isEmpty()) {
                return clearPassword();
            }
            try {
                byte[] randomSalt = randomSalt();
                java.lang.String buildPasswordHash = com.android.server.backup.utils.PasswordUtils.buildPasswordHash(PBKDF_CURRENT, str2, randomSalt, 10000);
                getPasswordHashFileCodec().serialize(new com.android.server.backup.BackupPasswordManager.BackupPasswordHash(buildPasswordHash, randomSalt));
                this.mPasswordHash = buildPasswordHash;
                this.mPasswordSalt = randomSalt;
                return true;
            } catch (java.io.IOException e) {
                android.util.Slog.e(TAG, "Unable to set backup password");
                return false;
            }
        } catch (java.io.IOException e2) {
            android.util.Slog.e(TAG, "Unable to write backup pw version; password not changed");
            return false;
        }
    }

    private boolean usePbkdf2Fallback() {
        return this.mPasswordVersion < 2;
    }

    private boolean clearPassword() {
        java.io.File passwordHashFile = getPasswordHashFile();
        if (passwordHashFile.exists() && !passwordHashFile.delete()) {
            android.util.Slog.e(TAG, "Unable to clear backup password");
            return false;
        }
        this.mPasswordHash = null;
        this.mPasswordSalt = null;
        return true;
    }

    private void loadStateFromFilesystem() {
        try {
            this.mPasswordVersion = getPasswordVersionFileCodec().deserialize().intValue();
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Unable to read backup pw version");
            this.mPasswordVersion = 1;
        }
        try {
            com.android.server.backup.BackupPasswordManager.BackupPasswordHash deserialize = getPasswordHashFileCodec().deserialize();
            this.mPasswordHash = deserialize.hash;
            this.mPasswordSalt = deserialize.salt;
        } catch (java.io.IOException e2) {
            android.util.Slog.e(TAG, "Unable to read saved backup pw hash");
        }
    }

    private boolean passwordMatchesSaved(java.lang.String str) {
        return passwordMatchesSaved(PBKDF_CURRENT, str) || (usePbkdf2Fallback() && passwordMatchesSaved(PBKDF_FALLBACK, str));
    }

    private boolean passwordMatchesSaved(java.lang.String str, java.lang.String str2) {
        if (this.mPasswordHash == null) {
            return str2 == null || str2.equals("");
        }
        if (str2 == null || str2.length() == 0) {
            return false;
        }
        return this.mPasswordHash.equalsIgnoreCase(com.android.server.backup.utils.PasswordUtils.buildPasswordHash(str, str2, this.mPasswordSalt, 10000));
    }

    private byte[] randomSalt() {
        byte[] bArr = new byte[64];
        this.mRng.nextBytes(bArr);
        return bArr;
    }

    private com.android.server.backup.utils.DataStreamFileCodec<java.lang.Integer> getPasswordVersionFileCodec() {
        return new com.android.server.backup.utils.DataStreamFileCodec<>(new java.io.File(this.mBaseStateDir, PASSWORD_VERSION_FILE_NAME), new com.android.server.backup.BackupPasswordManager.PasswordVersionFileCodec());
    }

    private com.android.server.backup.utils.DataStreamFileCodec<com.android.server.backup.BackupPasswordManager.BackupPasswordHash> getPasswordHashFileCodec() {
        return new com.android.server.backup.utils.DataStreamFileCodec<>(getPasswordHashFile(), new com.android.server.backup.BackupPasswordManager.PasswordHashFileCodec());
    }

    private java.io.File getPasswordHashFile() {
        return new java.io.File(this.mBaseStateDir, PASSWORD_HASH_FILE_NAME);
    }

    private static final class BackupPasswordHash {
        public java.lang.String hash;
        public byte[] salt;

        BackupPasswordHash(java.lang.String str, byte[] bArr) {
            this.hash = str;
            this.salt = bArr;
        }
    }

    private static final class PasswordVersionFileCodec implements com.android.server.backup.utils.DataStreamCodec<java.lang.Integer> {
        private PasswordVersionFileCodec() {
        }

        @Override // com.android.server.backup.utils.DataStreamCodec
        public void serialize(java.lang.Integer num, java.io.DataOutputStream dataOutputStream) throws java.io.IOException {
            dataOutputStream.write(num.intValue());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.android.server.backup.utils.DataStreamCodec
        public java.lang.Integer deserialize(java.io.DataInputStream dataInputStream) throws java.io.IOException {
            return java.lang.Integer.valueOf(dataInputStream.readInt());
        }
    }

    private static final class PasswordHashFileCodec implements com.android.server.backup.utils.DataStreamCodec<com.android.server.backup.BackupPasswordManager.BackupPasswordHash> {
        private PasswordHashFileCodec() {
        }

        @Override // com.android.server.backup.utils.DataStreamCodec
        public void serialize(com.android.server.backup.BackupPasswordManager.BackupPasswordHash backupPasswordHash, java.io.DataOutputStream dataOutputStream) throws java.io.IOException {
            dataOutputStream.writeInt(backupPasswordHash.salt.length);
            dataOutputStream.write(backupPasswordHash.salt);
            dataOutputStream.writeUTF(backupPasswordHash.hash);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.android.server.backup.utils.DataStreamCodec
        public com.android.server.backup.BackupPasswordManager.BackupPasswordHash deserialize(java.io.DataInputStream dataInputStream) throws java.io.IOException {
            byte[] bArr = new byte[dataInputStream.readInt()];
            dataInputStream.readFully(bArr);
            return new com.android.server.backup.BackupPasswordManager.BackupPasswordHash(dataInputStream.readUTF(), bArr);
        }
    }
}
