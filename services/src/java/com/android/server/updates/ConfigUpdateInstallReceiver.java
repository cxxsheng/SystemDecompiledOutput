package com.android.server.updates;

/* loaded from: classes2.dex */
public class ConfigUpdateInstallReceiver extends android.content.BroadcastReceiver {
    private static final java.lang.String EXTRA_REQUIRED_HASH = "REQUIRED_HASH";
    private static final java.lang.String EXTRA_VERSION_NUMBER = "VERSION";
    private static final java.lang.String TAG = "ConfigUpdateInstallReceiver";
    protected final java.io.File updateContent;
    protected final java.io.File updateDir;
    protected final java.io.File updateVersion;

    public ConfigUpdateInstallReceiver(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) {
        this.updateDir = new java.io.File(str);
        this.updateContent = new java.io.File(str, str2);
        this.updateVersion = new java.io.File(new java.io.File(str, str3), str4);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(final android.content.Context context, final android.content.Intent intent) {
        new java.lang.Thread() { // from class: com.android.server.updates.ConfigUpdateInstallReceiver.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                try {
                    int versionFromIntent = com.android.server.updates.ConfigUpdateInstallReceiver.this.getVersionFromIntent(intent);
                    java.lang.String requiredHashFromIntent = com.android.server.updates.ConfigUpdateInstallReceiver.this.getRequiredHashFromIntent(intent);
                    int currentVersion = com.android.server.updates.ConfigUpdateInstallReceiver.this.getCurrentVersion();
                    java.lang.String currentHash = com.android.server.updates.ConfigUpdateInstallReceiver.getCurrentHash(com.android.server.updates.ConfigUpdateInstallReceiver.this.getCurrentContent());
                    if (!com.android.server.updates.ConfigUpdateInstallReceiver.this.verifyVersion(currentVersion, versionFromIntent)) {
                        android.util.Slog.i(com.android.server.updates.ConfigUpdateInstallReceiver.TAG, "Not installing, new version is <= current version");
                        return;
                    }
                    if (com.android.server.updates.ConfigUpdateInstallReceiver.this.verifyPreviousHash(currentHash, requiredHashFromIntent)) {
                        android.util.Slog.i(com.android.server.updates.ConfigUpdateInstallReceiver.TAG, "Found new update, installing...");
                        java.io.BufferedInputStream altContent = com.android.server.updates.ConfigUpdateInstallReceiver.this.getAltContent(context, intent);
                        try {
                            com.android.server.updates.ConfigUpdateInstallReceiver.this.install(altContent, versionFromIntent);
                            if (altContent != null) {
                                altContent.close();
                            }
                            android.util.Slog.i(com.android.server.updates.ConfigUpdateInstallReceiver.TAG, "Installation successful");
                            com.android.server.updates.ConfigUpdateInstallReceiver.this.postInstall(context, intent);
                            return;
                        } finally {
                        }
                    }
                    android.util.EventLog.writeEvent(com.android.server.EventLogTags.CONFIG_INSTALL_FAILED, "Current hash did not match required value");
                } catch (java.lang.Exception e) {
                    android.util.Slog.e(com.android.server.updates.ConfigUpdateInstallReceiver.TAG, "Could not update content!", e);
                    java.lang.String obj = e.toString();
                    if (obj.length() > 100) {
                        obj = obj.substring(0, 99);
                    }
                    android.util.EventLog.writeEvent(com.android.server.EventLogTags.CONFIG_INSTALL_FAILED, obj);
                }
            }
        }.start();
    }

    private android.net.Uri getContentFromIntent(android.content.Intent intent) {
        android.net.Uri data = intent.getData();
        if (data == null) {
            throw new java.lang.IllegalStateException("Missing required content path, ignoring.");
        }
        return data;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getVersionFromIntent(android.content.Intent intent) throws java.lang.NumberFormatException {
        java.lang.String stringExtra = intent.getStringExtra(EXTRA_VERSION_NUMBER);
        if (stringExtra == null) {
            throw new java.lang.IllegalStateException("Missing required version number, ignoring.");
        }
        return java.lang.Integer.parseInt(stringExtra.trim());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.String getRequiredHashFromIntent(android.content.Intent intent) {
        java.lang.String stringExtra = intent.getStringExtra(EXTRA_REQUIRED_HASH);
        if (stringExtra == null) {
            throw new java.lang.IllegalStateException("Missing required previous hash, ignoring.");
        }
        return stringExtra.trim();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getCurrentVersion() throws java.lang.NumberFormatException {
        try {
            return java.lang.Integer.parseInt(libcore.io.IoUtils.readFileAsString(this.updateVersion.getCanonicalPath()).trim());
        } catch (java.io.IOException e) {
            android.util.Slog.i(TAG, "Couldn't find current metadata, assuming first update");
            return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.io.BufferedInputStream getAltContent(android.content.Context context, android.content.Intent intent) throws java.io.IOException {
        android.net.Uri contentFromIntent = getContentFromIntent(intent);
        android.os.Binder.allowBlockingForCurrentThread();
        try {
            return new java.io.BufferedInputStream(context.getContentResolver().openInputStream(contentFromIntent));
        } finally {
            android.os.Binder.defaultBlockingForCurrentThread();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public byte[] getCurrentContent() {
        try {
            return libcore.io.IoUtils.readFileAsByteArray(this.updateContent.getCanonicalPath());
        } catch (java.io.IOException e) {
            android.util.Slog.i(TAG, "Failed to read current content, assuming first update!");
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String getCurrentHash(byte[] bArr) {
        if (bArr == null) {
            return "0";
        }
        try {
            return com.android.internal.util.HexDump.toHexString(java.security.MessageDigest.getInstance("SHA512").digest(bArr), false);
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new java.lang.AssertionError(e);
        }
    }

    protected boolean verifyVersion(int i, int i2) {
        return i < i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean verifyPreviousHash(java.lang.String str, java.lang.String str2) {
        if (str2.equals("NONE")) {
            return true;
        }
        return str.equals(str2);
    }

    protected void writeUpdate(java.io.File file, java.io.File file2, java.io.InputStream inputStream) throws java.io.IOException {
        java.io.FileOutputStream fileOutputStream;
        java.io.File file3 = null;
        try {
            java.io.File parentFile = file2.getParentFile();
            parentFile.mkdirs();
            if (!parentFile.exists()) {
                throw new java.io.IOException("Failed to create directory " + parentFile.getCanonicalPath());
            }
            while (!parentFile.equals(this.updateDir)) {
                parentFile.setExecutable(true, false);
                parentFile = parentFile.getParentFile();
            }
            java.io.File createTempFile = java.io.File.createTempFile("journal", "", file);
            try {
                createTempFile.setReadable(true, false);
                fileOutputStream = new java.io.FileOutputStream(createTempFile);
            } catch (java.lang.Throwable th) {
                th = th;
                fileOutputStream = null;
            }
            try {
                libcore.io.Streams.copy(inputStream, fileOutputStream);
                fileOutputStream.getFD().sync();
                if (createTempFile.renameTo(file2)) {
                    createTempFile.delete();
                    libcore.io.IoUtils.closeQuietly(fileOutputStream);
                } else {
                    throw new java.io.IOException("Failed to atomically rename " + file2.getCanonicalPath());
                }
            } catch (java.lang.Throwable th2) {
                th = th2;
                file3 = createTempFile;
                th = th;
                if (file3 != null) {
                    file3.delete();
                }
                libcore.io.IoUtils.closeQuietly(fileOutputStream);
                throw th;
            }
        } catch (java.lang.Throwable th3) {
            th = th3;
            fileOutputStream = null;
        }
    }

    protected void install(java.io.InputStream inputStream, int i) throws java.io.IOException {
        writeUpdate(this.updateDir, this.updateContent, inputStream);
        writeUpdate(this.updateDir, this.updateVersion, new java.io.ByteArrayInputStream(java.lang.Long.toString(i).getBytes()));
    }

    protected void postInstall(android.content.Context context, android.content.Intent intent) {
    }
}
