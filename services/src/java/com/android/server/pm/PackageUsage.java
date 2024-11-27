package com.android.server.pm;

/* loaded from: classes2.dex */
class PackageUsage extends com.android.server.pm.AbstractStatsBase<java.util.Map<java.lang.String, com.android.server.pm.PackageSetting>> {
    private static final java.lang.String USAGE_FILE_MAGIC = "PACKAGE_USAGE__VERSION_";
    private static final java.lang.String USAGE_FILE_MAGIC_VERSION_1 = "PACKAGE_USAGE__VERSION_1";
    private boolean mIsHistoricalPackageUsageAvailable;

    PackageUsage() {
        super("package-usage.list", "PackageUsage_DiskWriter", true);
        this.mIsHistoricalPackageUsageAvailable = true;
    }

    boolean isHistoricalPackageUsageAvailable() {
        return this.mIsHistoricalPackageUsageAvailable;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.pm.AbstractStatsBase
    public void writeInternal(java.util.Map<java.lang.String, com.android.server.pm.PackageSetting> map) {
        java.io.FileOutputStream fileOutputStream;
        android.util.AtomicFile file = getFile();
        try {
            fileOutputStream = file.startWrite();
        } catch (java.io.IOException e) {
            e = e;
            fileOutputStream = null;
        }
        try {
            java.io.BufferedOutputStream bufferedOutputStream = new java.io.BufferedOutputStream(fileOutputStream);
            android.os.FileUtils.setPermissions(file.getBaseFile().getPath(), com.android.internal.util.FrameworkStatsLog.DISPLAY_HBM_STATE_CHANGED, 1000, 1032);
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(USAGE_FILE_MAGIC_VERSION_1);
            sb.append('\n');
            bufferedOutputStream.write(sb.toString().getBytes(java.nio.charset.StandardCharsets.US_ASCII));
            for (com.android.server.pm.PackageSetting packageSetting : map.values()) {
                if (packageSetting != null && packageSetting.getPkgState() != null && packageSetting.getPkgState().getLatestPackageUseTimeInMills() != 0) {
                    sb.setLength(0);
                    sb.append(packageSetting.getPackageName());
                    for (long j : packageSetting.getPkgState().getLastPackageUsageTimeInMills()) {
                        sb.append(' ');
                        sb.append(j);
                    }
                    sb.append('\n');
                    bufferedOutputStream.write(sb.toString().getBytes(java.nio.charset.StandardCharsets.US_ASCII));
                }
            }
            bufferedOutputStream.flush();
            file.finishWrite(fileOutputStream);
        } catch (java.io.IOException e2) {
            e = e2;
            if (fileOutputStream != null) {
                file.failWrite(fileOutputStream);
            }
            android.util.Log.e("PackageManager", "Failed to write package usage times", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.pm.AbstractStatsBase
    public void readInternal(java.util.Map<java.lang.String, com.android.server.pm.PackageSetting> map) {
        java.io.BufferedInputStream bufferedInputStream = null;
        try {
            try {
                java.io.BufferedInputStream bufferedInputStream2 = new java.io.BufferedInputStream(getFile().openRead());
                try {
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    java.lang.String readLine = readLine(bufferedInputStream2, sb);
                    if (readLine != null) {
                        if (USAGE_FILE_MAGIC_VERSION_1.equals(readLine)) {
                            readVersion1LP(map, bufferedInputStream2, sb);
                        } else {
                            readVersion0LP(map, bufferedInputStream2, sb, readLine);
                        }
                    }
                    libcore.io.IoUtils.closeQuietly(bufferedInputStream2);
                } catch (java.io.FileNotFoundException e) {
                    bufferedInputStream = bufferedInputStream2;
                    this.mIsHistoricalPackageUsageAvailable = false;
                    libcore.io.IoUtils.closeQuietly(bufferedInputStream);
                } catch (java.io.IOException e2) {
                    e = e2;
                    bufferedInputStream = bufferedInputStream2;
                    android.util.Log.w("PackageManager", "Failed to read package usage times", e);
                    libcore.io.IoUtils.closeQuietly(bufferedInputStream);
                } catch (java.lang.Throwable th) {
                    th = th;
                    bufferedInputStream = bufferedInputStream2;
                    libcore.io.IoUtils.closeQuietly(bufferedInputStream);
                    throw th;
                }
            } catch (java.io.FileNotFoundException e3) {
            } catch (java.io.IOException e4) {
                e = e4;
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
        }
    }

    private void readVersion0LP(java.util.Map<java.lang.String, com.android.server.pm.PackageSetting> map, java.io.InputStream inputStream, java.lang.StringBuilder sb, java.lang.String str) throws java.io.IOException {
        while (str != null) {
            java.lang.String[] split = str.split(" ");
            if (split.length != 2) {
                throw new java.io.IOException("Failed to parse " + str + " as package-timestamp pair.");
            }
            com.android.server.pm.PackageSetting packageSetting = map.get(split[0]);
            if (packageSetting != null) {
                long parseAsLong = parseAsLong(split[1]);
                for (int i = 0; i < 8; i++) {
                    packageSetting.getPkgState().setLastPackageUsageTimeInMills(i, parseAsLong);
                }
            }
            str = readLine(inputStream, sb);
        }
    }

    private void readVersion1LP(java.util.Map<java.lang.String, com.android.server.pm.PackageSetting> map, java.io.InputStream inputStream, java.lang.StringBuilder sb) throws java.io.IOException {
        while (true) {
            java.lang.String readLine = readLine(inputStream, sb);
            if (readLine != null) {
                java.lang.String[] split = readLine.split(" ");
                if (split.length != 9) {
                    throw new java.io.IOException("Failed to parse " + readLine + " as a timestamp array.");
                }
                int i = 0;
                com.android.server.pm.PackageSetting packageSetting = map.get(split[0]);
                if (packageSetting != null) {
                    while (i < 8) {
                        int i2 = i + 1;
                        packageSetting.getPkgState().setLastPackageUsageTimeInMills(i, parseAsLong(split[i2]));
                        i = i2;
                    }
                }
            } else {
                return;
            }
        }
    }

    private long parseAsLong(java.lang.String str) throws java.io.IOException {
        try {
            return java.lang.Long.parseLong(str);
        } catch (java.lang.NumberFormatException e) {
            throw new java.io.IOException("Failed to parse " + str + " as a long.", e);
        }
    }

    private java.lang.String readLine(java.io.InputStream inputStream, java.lang.StringBuilder sb) throws java.io.IOException {
        return readToken(inputStream, sb, '\n');
    }

    private java.lang.String readToken(java.io.InputStream inputStream, java.lang.StringBuilder sb, char c) throws java.io.IOException {
        sb.setLength(0);
        while (true) {
            int read = inputStream.read();
            if (read == -1) {
                if (sb.length() == 0) {
                    return null;
                }
                throw new java.io.IOException("Unexpected EOF");
            }
            if (read == c) {
                return sb.toString();
            }
            sb.append((char) read);
        }
    }
}
