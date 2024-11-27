package com.android.server.backup.utils;

/* loaded from: classes.dex */
public class TarBackupReader {
    private static final int TAR_HEADER_LENGTH_FILESIZE = 12;
    private static final int TAR_HEADER_LENGTH_MODE = 8;
    private static final int TAR_HEADER_LENGTH_MODTIME = 12;
    private static final int TAR_HEADER_LENGTH_PATH = 100;
    private static final int TAR_HEADER_LENGTH_PATH_PREFIX = 155;
    private static final int TAR_HEADER_LONG_RADIX = 8;
    private static final int TAR_HEADER_OFFSET_FILESIZE = 124;
    private static final int TAR_HEADER_OFFSET_MODE = 100;
    private static final int TAR_HEADER_OFFSET_MODTIME = 136;
    private static final int TAR_HEADER_OFFSET_PATH = 0;
    private static final int TAR_HEADER_OFFSET_PATH_PREFIX = 345;
    private static final int TAR_HEADER_OFFSET_TYPE_CHAR = 156;
    private com.android.server.backup.utils.BackupManagerMonitorEventSender mBackupManagerMonitorEventSender;
    private final com.android.server.backup.utils.BytesReadListener mBytesReadListener;
    private final java.io.InputStream mInputStream;
    private byte[] mWidgetData = null;

    public TarBackupReader(java.io.InputStream inputStream, com.android.server.backup.utils.BytesReadListener bytesReadListener, android.app.backup.IBackupManagerMonitor iBackupManagerMonitor) {
        this.mInputStream = inputStream;
        this.mBytesReadListener = bytesReadListener;
        this.mBackupManagerMonitorEventSender = new com.android.server.backup.utils.BackupManagerMonitorEventSender(iBackupManagerMonitor);
    }

    public com.android.server.backup.FileMetadata readTarHeaders() throws java.io.IOException {
        byte[] bArr = new byte[512];
        if (!readTarHeader(bArr)) {
            return null;
        }
        try {
            com.android.server.backup.FileMetadata fileMetadata = new com.android.server.backup.FileMetadata();
            fileMetadata.size = extractRadix(bArr, 124, 12, 8);
            fileMetadata.mtime = extractRadix(bArr, 136, 12, 8);
            fileMetadata.mode = extractRadix(bArr, 100, 8, 8);
            fileMetadata.path = extractString(bArr, 345, 155);
            java.lang.String extractString = extractString(bArr, 0, 100);
            if (extractString.length() > 0) {
                if (fileMetadata.path.length() > 0) {
                    fileMetadata.path += '/';
                }
                fileMetadata.path += extractString;
            }
            byte b = bArr[156];
            if (b == 120) {
                boolean readPaxExtendedHeader = readPaxExtendedHeader(fileMetadata);
                if (readPaxExtendedHeader) {
                    readPaxExtendedHeader = readTarHeader(bArr);
                }
                if (!readPaxExtendedHeader) {
                    throw new java.io.IOException("Bad or missing pax header");
                }
                b = bArr[156];
            }
            switch (b) {
                case 0:
                    return null;
                case 48:
                    fileMetadata.type = 1;
                    break;
                case 53:
                    fileMetadata.type = 2;
                    if (fileMetadata.size != 0) {
                        android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Directory entry with nonzero size in header");
                        fileMetadata.size = 0L;
                        break;
                    }
                    break;
                default:
                    android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Unknown tar entity type: " + ((int) b));
                    throw new java.io.IOException("Unknown entity type " + ((int) b));
            }
            if ("shared/".regionMatches(0, fileMetadata.path, 0, "shared/".length())) {
                fileMetadata.path = fileMetadata.path.substring("shared/".length());
                fileMetadata.packageName = com.android.server.backup.UserBackupManagerService.SHARED_BACKUP_AGENT_PACKAGE;
                fileMetadata.domain = "shared";
                android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, "File in shared storage: " + fileMetadata.path);
            } else if ("apps/".regionMatches(0, fileMetadata.path, 0, "apps/".length())) {
                fileMetadata.path = fileMetadata.path.substring("apps/".length());
                int indexOf = fileMetadata.path.indexOf(47);
                if (indexOf >= 0) {
                    fileMetadata.packageName = fileMetadata.path.substring(0, indexOf);
                    fileMetadata.path = fileMetadata.path.substring(indexOf + 1);
                    if (!fileMetadata.path.equals(com.android.server.backup.UserBackupManagerService.BACKUP_MANIFEST_FILENAME) && !fileMetadata.path.equals(com.android.server.backup.UserBackupManagerService.BACKUP_METADATA_FILENAME)) {
                        int indexOf2 = fileMetadata.path.indexOf(47);
                        if (indexOf2 >= 0) {
                            fileMetadata.domain = fileMetadata.path.substring(0, indexOf2);
                            fileMetadata.path = fileMetadata.path.substring(indexOf2 + 1);
                        } else {
                            throw new java.io.IOException("Illegal semantic path in non-manifest " + fileMetadata.path);
                        }
                    }
                } else {
                    throw new java.io.IOException("Illegal semantic path in " + fileMetadata.path);
                }
            }
            return fileMetadata;
        } catch (java.io.IOException e) {
            android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Parse error in header: " + e.getMessage());
            throw e;
        }
    }

    private static int readExactly(java.io.InputStream inputStream, byte[] bArr, int i, int i2) throws java.io.IOException {
        if (i2 <= 0) {
            throw new java.lang.IllegalArgumentException("size must be > 0");
        }
        int i3 = 0;
        while (i3 < i2) {
            int read = inputStream.read(bArr, i + i3, i2 - i3);
            if (read <= 0) {
                break;
            }
            i3 += read;
        }
        return i3;
    }

    public android.content.pm.Signature[] readAppManifestAndReturnSignatures(com.android.server.backup.FileMetadata fileMetadata) throws java.io.IOException {
        if (fileMetadata.size > 65536) {
            throw new java.io.IOException("Restore manifest too big; corrupt? size=" + fileMetadata.size);
        }
        byte[] bArr = new byte[(int) fileMetadata.size];
        if (readExactly(this.mInputStream, bArr, 0, (int) fileMetadata.size) == fileMetadata.size) {
            this.mBytesReadListener.onBytesRead(fileMetadata.size);
            java.lang.String[] strArr = new java.lang.String[1];
            try {
                int extractLine = extractLine(bArr, 0, strArr);
                int parseInt = java.lang.Integer.parseInt(strArr[0]);
                if (parseInt != 1) {
                    android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, "Unknown restore manifest version " + parseInt + " for package " + fileMetadata.packageName);
                    this.mBackupManagerMonitorEventSender.monitorEvent(44, null, 3, com.android.server.backup.utils.BackupManagerMonitorEventSender.putMonitoringExtra(com.android.server.backup.utils.BackupManagerMonitorEventSender.putMonitoringExtra((android.os.Bundle) null, "android.app.backup.extra.LOG_EVENT_PACKAGE_NAME", fileMetadata.packageName), "android.app.backup.extra.LOG_EVENT_PACKAGE_VERSION", parseInt));
                } else {
                    int extractLine2 = extractLine(bArr, extractLine, strArr);
                    java.lang.String str = strArr[0];
                    if (!str.equals(fileMetadata.packageName)) {
                        android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, "Expected package " + fileMetadata.packageName + " but restore manifest claims " + str);
                        this.mBackupManagerMonitorEventSender.monitorEvent(43, null, 3, com.android.server.backup.utils.BackupManagerMonitorEventSender.putMonitoringExtra(com.android.server.backup.utils.BackupManagerMonitorEventSender.putMonitoringExtra((android.os.Bundle) null, "android.app.backup.extra.LOG_EVENT_PACKAGE_NAME", fileMetadata.packageName), "android.app.backup.extra.LOG_MANIFEST_PACKAGE_NAME", str));
                    } else {
                        int extractLine3 = extractLine(bArr, extractLine2, strArr);
                        fileMetadata.version = java.lang.Integer.parseInt(strArr[0]);
                        int extractLine4 = extractLine(bArr, extractLine3, strArr);
                        java.lang.Integer.parseInt(strArr[0]);
                        int extractLine5 = extractLine(bArr, extractLine4, strArr);
                        fileMetadata.installerPackageName = strArr[0].length() > 0 ? strArr[0] : null;
                        int extractLine6 = extractLine(bArr, extractLine5, strArr);
                        fileMetadata.hasApk = strArr[0].equals("1");
                        int extractLine7 = extractLine(bArr, extractLine6, strArr);
                        int parseInt2 = java.lang.Integer.parseInt(strArr[0]);
                        if (parseInt2 > 0) {
                            android.content.pm.Signature[] signatureArr = new android.content.pm.Signature[parseInt2];
                            for (int i = 0; i < parseInt2; i++) {
                                extractLine7 = extractLine(bArr, extractLine7, strArr);
                                signatureArr[i] = new android.content.pm.Signature(strArr[0]);
                            }
                            return signatureArr;
                        }
                        android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, "Missing signature on backed-up package " + fileMetadata.packageName);
                        this.mBackupManagerMonitorEventSender.monitorEvent(42, null, 3, com.android.server.backup.utils.BackupManagerMonitorEventSender.putMonitoringExtra((android.os.Bundle) null, "android.app.backup.extra.LOG_EVENT_PACKAGE_NAME", fileMetadata.packageName));
                    }
                }
            } catch (java.lang.NumberFormatException e) {
                android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Corrupt restore manifest for package " + fileMetadata.packageName);
                this.mBackupManagerMonitorEventSender.monitorEvent(46, null, 3, com.android.server.backup.utils.BackupManagerMonitorEventSender.putMonitoringExtra((android.os.Bundle) null, "android.app.backup.extra.LOG_EVENT_PACKAGE_NAME", fileMetadata.packageName));
            } catch (java.lang.IllegalArgumentException e2) {
                android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, e2.getMessage());
            }
            return null;
        }
        throw new java.io.IOException("Unexpected EOF in manifest");
    }

    public com.android.server.backup.restore.RestorePolicy chooseRestorePolicy(android.content.pm.PackageManager packageManager, boolean z, com.android.server.backup.FileMetadata fileMetadata, android.content.pm.Signature[] signatureArr, android.content.pm.PackageManagerInternal packageManagerInternal, int i, android.content.Context context) {
        return chooseRestorePolicy(packageManager, z, fileMetadata, signatureArr, packageManagerInternal, i, com.android.server.backup.utils.BackupEligibilityRules.forBackup(packageManager, packageManagerInternal, i, context));
    }

    public com.android.server.backup.restore.RestorePolicy chooseRestorePolicy(android.content.pm.PackageManager packageManager, boolean z, com.android.server.backup.FileMetadata fileMetadata, android.content.pm.Signature[] signatureArr, android.content.pm.PackageManagerInternal packageManagerInternal, int i, com.android.server.backup.utils.BackupEligibilityRules backupEligibilityRules) {
        if (signatureArr == null) {
            return com.android.server.backup.restore.RestorePolicy.IGNORE;
        }
        com.android.server.backup.restore.RestorePolicy restorePolicy = com.android.server.backup.restore.RestorePolicy.IGNORE;
        try {
            android.content.pm.PackageInfo packageInfoAsUser = packageManager.getPackageInfoAsUser(fileMetadata.packageName, 134217728, i);
            int i2 = packageInfoAsUser.applicationInfo.flags;
            if (backupEligibilityRules.isAppBackupAllowed(packageInfoAsUser.applicationInfo)) {
                if (!android.os.UserHandle.isCore(packageInfoAsUser.applicationInfo.uid) || packageInfoAsUser.applicationInfo.backupAgentName != null) {
                    if (backupEligibilityRules.signaturesMatch(signatureArr, packageInfoAsUser)) {
                        if ((packageInfoAsUser.applicationInfo.flags & 131072) != 0) {
                            android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, "Package has restoreAnyVersion; taking data");
                            this.mBackupManagerMonitorEventSender.monitorEvent(34, packageInfoAsUser, 3, null);
                            restorePolicy = com.android.server.backup.restore.RestorePolicy.ACCEPT;
                        } else if (packageInfoAsUser.getLongVersionCode() >= fileMetadata.version) {
                            android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, "Sig + version match; taking data");
                            restorePolicy = com.android.server.backup.restore.RestorePolicy.ACCEPT;
                            this.mBackupManagerMonitorEventSender.monitorEvent(35, packageInfoAsUser, 3, null);
                        } else if (z) {
                            android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, "Data version " + fileMetadata.version + " is newer than installed version " + packageInfoAsUser.getLongVersionCode() + " - requiring apk");
                            restorePolicy = com.android.server.backup.restore.RestorePolicy.ACCEPT_IF_APK;
                        } else {
                            android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, "Data requires newer version " + fileMetadata.version + "; ignoring");
                            this.mBackupManagerMonitorEventSender.monitorEvent(36, packageInfoAsUser, 3, com.android.server.backup.utils.BackupManagerMonitorEventSender.putMonitoringExtra((android.os.Bundle) null, "android.app.backup.extra.LOG_OLD_VERSION", fileMetadata.version));
                            restorePolicy = com.android.server.backup.restore.RestorePolicy.IGNORE;
                        }
                    } else {
                        android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Restore manifest signatures do not match installed application for " + fileMetadata.packageName);
                        this.mBackupManagerMonitorEventSender.monitorEvent(37, packageInfoAsUser, 3, null);
                    }
                } else {
                    android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Package " + fileMetadata.packageName + " is system level with no agent");
                    this.mBackupManagerMonitorEventSender.monitorEvent(38, packageInfoAsUser, 2, null);
                }
            } else {
                android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, "Restore manifest from " + fileMetadata.packageName + " but allowBackup=false");
                this.mBackupManagerMonitorEventSender.monitorEvent(39, packageInfoAsUser, 3, null);
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            if (z) {
                android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, "Package " + fileMetadata.packageName + " not installed; requiring apk in dataset");
                restorePolicy = com.android.server.backup.restore.RestorePolicy.ACCEPT_IF_APK;
            } else {
                restorePolicy = com.android.server.backup.restore.RestorePolicy.IGNORE;
            }
            this.mBackupManagerMonitorEventSender.monitorEvent(40, null, 3, com.android.server.backup.utils.BackupManagerMonitorEventSender.putMonitoringExtra(com.android.server.backup.utils.BackupManagerMonitorEventSender.putMonitoringExtra((android.os.Bundle) null, "android.app.backup.extra.LOG_EVENT_PACKAGE_NAME", fileMetadata.packageName), "android.app.backup.extra.LOG_POLICY_ALLOW_APKS", z));
        }
        if (restorePolicy == com.android.server.backup.restore.RestorePolicy.ACCEPT_IF_APK && !fileMetadata.hasApk) {
            android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, "Cannot restore package " + fileMetadata.packageName + " without the matching .apk");
            this.mBackupManagerMonitorEventSender.monitorEvent(41, null, 3, com.android.server.backup.utils.BackupManagerMonitorEventSender.putMonitoringExtra((android.os.Bundle) null, "android.app.backup.extra.LOG_EVENT_PACKAGE_NAME", fileMetadata.packageName));
        }
        return restorePolicy;
    }

    public void skipTarPadding(long j) throws java.io.IOException {
        long j2 = (j + 512) % 512;
        if (j2 > 0) {
            int i = 512 - ((int) j2);
            if (readExactly(this.mInputStream, new byte[i], 0, i) == i) {
                this.mBytesReadListener.onBytesRead(i);
                return;
            }
            throw new java.io.IOException("Unexpected EOF in padding");
        }
    }

    public void readMetadata(com.android.server.backup.FileMetadata fileMetadata) throws java.io.IOException {
        if (fileMetadata.size > 65536) {
            throw new java.io.IOException("Metadata too big; corrupt? size=" + fileMetadata.size);
        }
        int i = (int) fileMetadata.size;
        byte[] bArr = new byte[i];
        if (readExactly(this.mInputStream, bArr, 0, (int) fileMetadata.size) == fileMetadata.size) {
            this.mBytesReadListener.onBytesRead(fileMetadata.size);
            java.lang.String[] strArr = new java.lang.String[1];
            int extractLine = extractLine(bArr, 0, strArr);
            int parseInt = java.lang.Integer.parseInt(strArr[0]);
            if (parseInt == 1) {
                int extractLine2 = extractLine(bArr, extractLine, strArr);
                java.lang.String str = strArr[0];
                if (fileMetadata.packageName.equals(str)) {
                    java.io.ByteArrayInputStream byteArrayInputStream = new java.io.ByteArrayInputStream(bArr, extractLine2, i - extractLine2);
                    java.io.DataInputStream dataInputStream = new java.io.DataInputStream(byteArrayInputStream);
                    while (byteArrayInputStream.available() > 0) {
                        int readInt = dataInputStream.readInt();
                        int readInt2 = dataInputStream.readInt();
                        if (readInt2 > 65536) {
                            throw new java.io.IOException("Datum " + java.lang.Integer.toHexString(readInt) + " too big; corrupt? size=" + fileMetadata.size);
                        }
                        switch (readInt) {
                            case com.android.server.backup.UserBackupManagerService.BACKUP_WIDGET_METADATA_TOKEN /* 33549569 */:
                                this.mWidgetData = new byte[readInt2];
                                dataInputStream.read(this.mWidgetData);
                                break;
                            default:
                                android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, "Ignoring metadata blob " + java.lang.Integer.toHexString(readInt) + " for " + fileMetadata.packageName);
                                dataInputStream.skipBytes(readInt2);
                                break;
                        }
                    }
                    return;
                }
                android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Metadata mismatch: package " + fileMetadata.packageName + " but widget data for " + str);
                this.mBackupManagerMonitorEventSender.monitorEvent(47, null, 3, com.android.server.backup.utils.BackupManagerMonitorEventSender.putMonitoringExtra(com.android.server.backup.utils.BackupManagerMonitorEventSender.putMonitoringExtra((android.os.Bundle) null, "android.app.backup.extra.LOG_EVENT_PACKAGE_NAME", fileMetadata.packageName), "android.app.backup.extra.LOG_WIDGET_PACKAGE_NAME", str));
                return;
            }
            android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Unsupported metadata version " + parseInt);
            this.mBackupManagerMonitorEventSender.monitorEvent(48, null, 3, com.android.server.backup.utils.BackupManagerMonitorEventSender.putMonitoringExtra(com.android.server.backup.utils.BackupManagerMonitorEventSender.putMonitoringExtra((android.os.Bundle) null, "android.app.backup.extra.LOG_EVENT_PACKAGE_NAME", fileMetadata.packageName), "android.app.backup.extra.LOG_EVENT_PACKAGE_VERSION", parseInt));
            return;
        }
        throw new java.io.IOException("Unexpected EOF in widget data");
    }

    private static int extractLine(byte[] bArr, int i, java.lang.String[] strArr) throws java.io.IOException {
        int length = bArr.length;
        if (i >= length) {
            throw new java.io.IOException("Incomplete data");
        }
        int i2 = i;
        while (i2 < length && bArr[i2] != 10) {
            i2++;
        }
        strArr[0] = new java.lang.String(bArr, i, i2 - i);
        return i2 + 1;
    }

    private boolean readTarHeader(byte[] bArr) throws java.io.IOException {
        int readExactly = readExactly(this.mInputStream, bArr, 0, 512);
        if (readExactly == 0) {
            return false;
        }
        if (readExactly < 512) {
            throw new java.io.IOException("Unable to read full block header");
        }
        this.mBytesReadListener.onBytesRead(512L);
        return true;
    }

    private boolean readPaxExtendedHeader(com.android.server.backup.FileMetadata fileMetadata) throws java.io.IOException {
        if (fileMetadata.size > 32768) {
            android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Suspiciously large pax header size " + fileMetadata.size + " - aborting");
            throw new java.io.IOException("Sanity failure: pax header size " + fileMetadata.size);
        }
        int i = ((int) ((fileMetadata.size + 511) >> 9)) * 512;
        byte[] bArr = new byte[i];
        int i2 = 0;
        if (readExactly(this.mInputStream, bArr, 0, i) < i) {
            throw new java.io.IOException("Unable to read full pax header");
        }
        this.mBytesReadListener.onBytesRead(i);
        int i3 = (int) fileMetadata.size;
        do {
            int i4 = i2 + 1;
            while (i4 < i3 && bArr[i4] != 32) {
                i4++;
            }
            if (i4 >= i3) {
                throw new java.io.IOException("Invalid pax data");
            }
            int extractRadix = (int) extractRadix(bArr, i2, i4 - i2, 10);
            int i5 = i4 + 1;
            i2 += extractRadix;
            int i6 = i2 - 1;
            int i7 = i5 + 1;
            while (bArr[i7] != 61 && i7 <= i6) {
                i7++;
            }
            if (i7 > i6) {
                throw new java.io.IOException("Invalid pax declaration");
            }
            java.lang.String str = new java.lang.String(bArr, i5, i7 - i5, "UTF-8");
            java.lang.String str2 = new java.lang.String(bArr, i7 + 1, (i6 - i7) - 1, "UTF-8");
            if ("path".equals(str)) {
                fileMetadata.path = str2;
            } else if ("size".equals(str)) {
                fileMetadata.size = java.lang.Long.parseLong(str2);
            } else {
                android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, "Unhandled pax key: " + i5);
            }
        } while (i2 < i3);
        return true;
    }

    private static long extractRadix(byte[] bArr, int i, int i2, int i3) throws java.io.IOException {
        int i4 = i2 + i;
        long j = 0;
        while (i < i4) {
            int i5 = bArr[i];
            if (i5 == 0 || i5 == 32) {
                break;
            }
            if (i5 < 48 || i5 > (i3 + 48) - 1) {
                throw new java.io.IOException("Invalid number in header: '" + ((char) i5) + "' for radix " + i3);
            }
            j = (i5 - 48) + (i3 * j);
            i++;
        }
        return j;
    }

    private static java.lang.String extractString(byte[] bArr, int i, int i2) throws java.io.IOException {
        int i3 = i2 + i;
        int i4 = i;
        while (i4 < i3 && bArr[i4] != 0) {
            i4++;
        }
        return new java.lang.String(bArr, i, i4 - i, "US-ASCII");
    }

    private static void hexLog(byte[] bArr) {
        int length = bArr.length;
        java.lang.StringBuilder sb = new java.lang.StringBuilder(64);
        int i = 0;
        while (length > 0) {
            sb.append(java.lang.String.format("%04x   ", java.lang.Integer.valueOf(i)));
            int i2 = length <= 16 ? length : 16;
            for (int i3 = 0; i3 < i2; i3++) {
                sb.append(java.lang.String.format("%02x ", java.lang.Byte.valueOf(bArr[i + i3])));
            }
            android.util.Slog.i("hexdump", sb.toString());
            sb.setLength(0);
            length -= i2;
            i += i2;
        }
    }

    public android.app.backup.IBackupManagerMonitor getMonitor() {
        return this.mBackupManagerMonitorEventSender.getMonitor();
    }

    public byte[] getWidgetData() {
        return this.mWidgetData;
    }
}
