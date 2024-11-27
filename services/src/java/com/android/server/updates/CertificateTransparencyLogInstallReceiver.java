package com.android.server.updates;

/* loaded from: classes2.dex */
public class CertificateTransparencyLogInstallReceiver extends com.android.server.updates.ConfigUpdateInstallReceiver {
    private static final java.lang.String LOGDIR_PREFIX = "logs-";
    private static final java.lang.String TAG = "CTLogInstallReceiver";

    public CertificateTransparencyLogInstallReceiver() {
        super("/data/misc/keychain/trusted_ct_logs/", "ct_logs", "metadata/", "version");
    }

    @Override // com.android.server.updates.ConfigUpdateInstallReceiver
    protected void install(java.io.InputStream inputStream, int i) throws java.io.IOException {
        this.updateDir.mkdir();
        if (!this.updateDir.isDirectory()) {
            throw new java.io.IOException("Unable to make directory " + this.updateDir.getCanonicalPath());
        }
        if (!this.updateDir.setReadable(true, false)) {
            throw new java.io.IOException("Unable to set permissions on " + this.updateDir.getCanonicalPath());
        }
        java.io.File file = new java.io.File(this.updateDir, "current");
        java.io.File file2 = new java.io.File(this.updateDir, LOGDIR_PREFIX + java.lang.String.valueOf(i));
        if (file2.exists()) {
            if (file2.getCanonicalPath().equals(file.getCanonicalPath())) {
                writeUpdate(this.updateDir, this.updateVersion, new java.io.ByteArrayInputStream(java.lang.Long.toString(i).getBytes()));
                deleteOldLogDirectories();
                return;
            }
            android.os.FileUtils.deleteContentsAndDir(file2);
        }
        try {
            file2.mkdir();
            if (!file2.isDirectory()) {
                throw new java.io.IOException("Unable to make directory " + file2.getCanonicalPath());
            }
            if (!file2.setReadable(true, false)) {
                throw new java.io.IOException("Failed to set " + file2.getCanonicalPath() + " readable");
            }
            try {
                org.json.JSONArray jSONArray = new org.json.JSONObject(new java.lang.String(libcore.io.Streams.readFullyNoClose(inputStream), java.nio.charset.StandardCharsets.UTF_8)).getJSONArray("logs");
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    installLog(file2, jSONArray.getJSONObject(i2));
                }
                java.io.File file3 = new java.io.File(this.updateDir, "new_symlink");
                try {
                    android.system.Os.symlink(file2.getCanonicalPath(), file3.getCanonicalPath());
                    file3.renameTo(file.getAbsoluteFile());
                    android.util.Slog.i(TAG, "CT log directory updated to " + file2.getAbsolutePath());
                    writeUpdate(this.updateDir, this.updateVersion, new java.io.ByteArrayInputStream(java.lang.Long.toString((long) i).getBytes()));
                    deleteOldLogDirectories();
                } catch (android.system.ErrnoException e) {
                    throw new java.io.IOException("Failed to create symlink", e);
                }
            } catch (org.json.JSONException e2) {
                throw new java.io.IOException("Failed to parse logs", e2);
            }
        } catch (java.io.IOException | java.lang.RuntimeException e3) {
            android.os.FileUtils.deleteContentsAndDir(file2);
            throw e3;
        }
    }

    private void installLog(java.io.File file, org.json.JSONObject jSONObject) throws java.io.IOException {
        try {
            java.io.File file2 = new java.io.File(file, getLogFileName(jSONObject.getString("key")));
            java.io.OutputStreamWriter outputStreamWriter = new java.io.OutputStreamWriter(new java.io.FileOutputStream(file2), java.nio.charset.StandardCharsets.UTF_8);
            try {
                writeLogEntry(outputStreamWriter, "key", jSONObject.getString("key"));
                writeLogEntry(outputStreamWriter, "url", jSONObject.getString("url"));
                writeLogEntry(outputStreamWriter, "description", jSONObject.getString("description"));
                outputStreamWriter.close();
                if (!file2.setReadable(true, false)) {
                    throw new java.io.IOException("Failed to set permissions on " + file2.getCanonicalPath());
                }
            } finally {
            }
        } catch (org.json.JSONException e) {
            throw new java.io.IOException("Failed to parse log", e);
        }
    }

    private java.lang.String getLogFileName(java.lang.String str) {
        try {
            return com.android.internal.util.HexDump.toHexString(java.security.MessageDigest.getInstance("SHA-256").digest(android.util.Base64.decode(str, 0)), false);
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    private void writeLogEntry(java.io.OutputStreamWriter outputStreamWriter, java.lang.String str, java.lang.String str2) throws java.io.IOException {
        outputStreamWriter.write(str + ":" + str2 + "\n");
    }

    private void deleteOldLogDirectories() throws java.io.IOException {
        if (!this.updateDir.exists()) {
            return;
        }
        final java.io.File canonicalFile = new java.io.File(this.updateDir, "current").getCanonicalFile();
        for (java.io.File file : this.updateDir.listFiles(new java.io.FileFilter() { // from class: com.android.server.updates.CertificateTransparencyLogInstallReceiver.1
            @Override // java.io.FileFilter
            public boolean accept(java.io.File file2) {
                return !canonicalFile.equals(file2) && file2.getName().startsWith(com.android.server.updates.CertificateTransparencyLogInstallReceiver.LOGDIR_PREFIX);
            }
        })) {
            android.os.FileUtils.deleteContentsAndDir(file);
        }
    }
}
