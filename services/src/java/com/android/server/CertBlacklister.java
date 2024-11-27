package com.android.server;

/* loaded from: classes.dex */
public class CertBlacklister extends android.os.Binder {
    public static final java.lang.String PUBKEY_BLACKLIST_KEY = "pubkey_blacklist";
    public static final java.lang.String SERIAL_BLACKLIST_KEY = "serial_blacklist";
    private static final java.lang.String TAG = "CertBlacklister";
    private static final java.lang.String DENYLIST_ROOT = java.lang.System.getenv("ANDROID_DATA") + "/misc/keychain/";
    public static final java.lang.String PUBKEY_PATH = DENYLIST_ROOT + "pubkey_blacklist.txt";
    public static final java.lang.String SERIAL_PATH = DENYLIST_ROOT + "serial_blacklist.txt";

    private static class BlacklistObserver extends android.database.ContentObserver {
        private final android.content.ContentResolver mContentResolver;
        private final java.lang.String mKey;
        private final java.lang.String mName;
        private final java.lang.String mPath;
        private final java.io.File mTmpDir;

        public BlacklistObserver(java.lang.String str, java.lang.String str2, java.lang.String str3, android.content.ContentResolver contentResolver) {
            super(null);
            this.mKey = str;
            this.mName = str2;
            this.mPath = str3;
            this.mTmpDir = new java.io.File(this.mPath).getParentFile();
            this.mContentResolver = contentResolver;
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            super.onChange(z);
            writeDenylist();
        }

        public java.lang.String getValue() {
            return android.provider.Settings.Secure.getString(this.mContentResolver, this.mKey);
        }

        private void writeDenylist() {
            new java.lang.Thread("BlacklistUpdater") { // from class: com.android.server.CertBlacklister.BlacklistObserver.1
                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Type inference failed for: r2v10, types: [java.lang.String] */
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    synchronized (com.android.server.CertBlacklister.BlacklistObserver.this.mTmpDir) {
                        java.lang.String value = com.android.server.CertBlacklister.BlacklistObserver.this.getValue();
                        if (value != null) {
                            android.util.Slog.i(com.android.server.CertBlacklister.TAG, "Certificate blacklist changed, updating...");
                            java.io.FileOutputStream fileOutputStream = null;
                            java.io.FileOutputStream fileOutputStream2 = null;
                            try {
                                try {
                                    java.io.File createTempFile = java.io.File.createTempFile("journal", "", com.android.server.CertBlacklister.BlacklistObserver.this.mTmpDir);
                                    createTempFile.setReadable(true, false);
                                    java.io.FileOutputStream fileOutputStream3 = new java.io.FileOutputStream(createTempFile);
                                    try {
                                        fileOutputStream3.write(value.getBytes());
                                        android.os.FileUtils.sync(fileOutputStream3);
                                        createTempFile.renameTo(new java.io.File(com.android.server.CertBlacklister.BlacklistObserver.this.mPath));
                                        ?? r2 = "Certificate blacklist updated";
                                        android.util.Slog.i(com.android.server.CertBlacklister.TAG, "Certificate blacklist updated");
                                        libcore.io.IoUtils.closeQuietly(fileOutputStream3);
                                        fileOutputStream = r2;
                                    } catch (java.io.IOException e) {
                                        e = e;
                                        fileOutputStream2 = fileOutputStream3;
                                        android.util.Slog.e(com.android.server.CertBlacklister.TAG, "Failed to write blacklist", e);
                                        libcore.io.IoUtils.closeQuietly(fileOutputStream2);
                                        fileOutputStream = fileOutputStream2;
                                    } catch (java.lang.Throwable th) {
                                        th = th;
                                        fileOutputStream = fileOutputStream3;
                                        libcore.io.IoUtils.closeQuietly(fileOutputStream);
                                        throw th;
                                    }
                                } catch (java.io.IOException e2) {
                                    e = e2;
                                }
                            } catch (java.lang.Throwable th2) {
                                th = th2;
                            }
                        }
                    }
                }
            }.start();
        }
    }

    public CertBlacklister(android.content.Context context) {
        registerObservers(context.getContentResolver());
    }

    private com.android.server.CertBlacklister.BlacklistObserver buildPubkeyObserver(android.content.ContentResolver contentResolver) {
        return new com.android.server.CertBlacklister.BlacklistObserver(PUBKEY_BLACKLIST_KEY, "pubkey", PUBKEY_PATH, contentResolver);
    }

    private com.android.server.CertBlacklister.BlacklistObserver buildSerialObserver(android.content.ContentResolver contentResolver) {
        return new com.android.server.CertBlacklister.BlacklistObserver(SERIAL_BLACKLIST_KEY, "serial", SERIAL_PATH, contentResolver);
    }

    private void registerObservers(android.content.ContentResolver contentResolver) {
        contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor(PUBKEY_BLACKLIST_KEY), true, buildPubkeyObserver(contentResolver));
        contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor(SERIAL_BLACKLIST_KEY), true, buildSerialObserver(contentResolver));
    }
}
