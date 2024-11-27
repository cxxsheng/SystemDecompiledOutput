package com.android.server.pm.dex;

/* loaded from: classes2.dex */
class PackageDynamicCodeLoading extends com.android.server.pm.AbstractStatsBase<java.lang.Void> {
    private static final char FIELD_SEPARATOR = ':';
    static final int FILE_TYPE_DEX = 68;
    static final int FILE_TYPE_NATIVE = 78;
    private static final java.lang.String FILE_VERSION_HEADER = "DCL1";

    @com.android.internal.annotations.VisibleForTesting
    static final int MAX_FILES_PER_OWNER = 100;
    private static final java.util.regex.Pattern PACKAGE_LINE_PATTERN = java.util.regex.Pattern.compile("([A-Z]):([0-9]+):([^:]*):(.*)");
    private static final java.lang.String PACKAGE_PREFIX = "P:";
    private static final java.lang.String PACKAGE_SEPARATOR = ",";
    private static final java.lang.String TAG = "PackageDynamicCodeLoading";
    private final java.lang.Object mLock;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.Map<java.lang.String, com.android.server.pm.dex.PackageDynamicCodeLoading.PackageDynamicCode> mPackageMap;

    PackageDynamicCodeLoading() {
        super("package-dcl.list", "PackageDynamicCodeLoading_DiskWriter", false);
        this.mLock = new java.lang.Object();
        this.mPackageMap = new java.util.HashMap();
    }

    boolean record(java.lang.String str, java.lang.String str2, int i, int i2, java.lang.String str3) {
        boolean add;
        if (!isValidFileType(i)) {
            throw new java.lang.IllegalArgumentException("Bad file type: " + i);
        }
        synchronized (this.mLock) {
            try {
                com.android.server.pm.dex.PackageDynamicCodeLoading.PackageDynamicCode packageDynamicCode = this.mPackageMap.get(str);
                if (packageDynamicCode == null) {
                    packageDynamicCode = new com.android.server.pm.dex.PackageDynamicCodeLoading.PackageDynamicCode();
                    this.mPackageMap.put(str, packageDynamicCode);
                }
                add = packageDynamicCode.add(str2, (char) i, i2, str3);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return add;
    }

    private static boolean isValidFileType(int i) {
        return i == 68 || i == 78;
    }

    java.util.Set<java.lang.String> getAllPackagesWithDynamicCodeLoading() {
        java.util.HashSet hashSet;
        synchronized (this.mLock) {
            hashSet = new java.util.HashSet(this.mPackageMap.keySet());
        }
        return hashSet;
    }

    com.android.server.pm.dex.PackageDynamicCodeLoading.PackageDynamicCode getPackageDynamicCodeInfo(java.lang.String str) {
        com.android.server.pm.dex.PackageDynamicCodeLoading.PackageDynamicCode packageDynamicCode;
        synchronized (this.mLock) {
            com.android.server.pm.dex.PackageDynamicCodeLoading.PackageDynamicCode packageDynamicCode2 = this.mPackageMap.get(str);
            packageDynamicCode = null;
            byte b = 0;
            if (packageDynamicCode2 != null) {
                packageDynamicCode = new com.android.server.pm.dex.PackageDynamicCodeLoading.PackageDynamicCode(packageDynamicCode2);
            }
        }
        return packageDynamicCode;
    }

    void clear() {
        synchronized (this.mLock) {
            this.mPackageMap.clear();
        }
    }

    boolean removePackage(java.lang.String str) {
        boolean z;
        synchronized (this.mLock) {
            z = this.mPackageMap.remove(str) != null;
        }
        return z;
    }

    boolean removeUserPackage(java.lang.String str, int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.pm.dex.PackageDynamicCodeLoading.PackageDynamicCode packageDynamicCode = this.mPackageMap.get(str);
                if (packageDynamicCode == null) {
                    return false;
                }
                if (!packageDynamicCode.removeUser(i)) {
                    return false;
                }
                if (packageDynamicCode.mFileUsageMap.isEmpty()) {
                    this.mPackageMap.remove(str);
                }
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean removeFile(java.lang.String str, java.lang.String str2, int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.pm.dex.PackageDynamicCodeLoading.PackageDynamicCode packageDynamicCode = this.mPackageMap.get(str);
                if (packageDynamicCode == null) {
                    return false;
                }
                if (!packageDynamicCode.removeFile(str2, i)) {
                    return false;
                }
                if (packageDynamicCode.mFileUsageMap.isEmpty()) {
                    this.mPackageMap.remove(str);
                }
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void syncData(java.util.Map<java.lang.String, java.util.Set<java.lang.Integer>> map) {
        synchronized (this.mLock) {
            try {
                java.util.Iterator<java.util.Map.Entry<java.lang.String, com.android.server.pm.dex.PackageDynamicCodeLoading.PackageDynamicCode>> it = this.mPackageMap.entrySet().iterator();
                while (it.hasNext()) {
                    java.util.Map.Entry<java.lang.String, com.android.server.pm.dex.PackageDynamicCodeLoading.PackageDynamicCode> next = it.next();
                    java.util.Set<java.lang.Integer> set = map.get(next.getKey());
                    if (set == null) {
                        it.remove();
                    } else {
                        com.android.server.pm.dex.PackageDynamicCodeLoading.PackageDynamicCode value = next.getValue();
                        value.syncData(map, set);
                        if (value.mFileUsageMap.isEmpty()) {
                            it.remove();
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void maybeWriteAsync() {
        super.maybeWriteAsync(null);
    }

    void writeNow() {
        super.writeNow(null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.pm.AbstractStatsBase
    public final void writeInternal(java.lang.Void r3) {
        java.io.FileOutputStream fileOutputStream;
        android.util.AtomicFile file = getFile();
        try {
            fileOutputStream = file.startWrite();
            try {
                write(fileOutputStream);
                file.finishWrite(fileOutputStream);
            } catch (java.io.IOException e) {
                e = e;
                file.failWrite(fileOutputStream);
                android.util.Slog.e(TAG, "Failed to write dynamic usage for secondary code files.", e);
            }
        } catch (java.io.IOException e2) {
            e = e2;
            fileOutputStream = null;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void write(java.io.OutputStream outputStream) throws java.io.IOException {
        java.util.HashMap hashMap;
        synchronized (this.mLock) {
            try {
                hashMap = new java.util.HashMap(this.mPackageMap.size());
                for (java.util.Map.Entry<java.lang.String, com.android.server.pm.dex.PackageDynamicCodeLoading.PackageDynamicCode> entry : this.mPackageMap.entrySet()) {
                    hashMap.put(entry.getKey(), new com.android.server.pm.dex.PackageDynamicCodeLoading.PackageDynamicCode(entry.getValue()));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        write(outputStream, hashMap);
    }

    private static void write(java.io.OutputStream outputStream, java.util.Map<java.lang.String, com.android.server.pm.dex.PackageDynamicCodeLoading.PackageDynamicCode> map) throws java.io.IOException {
        com.android.internal.util.FastPrintWriter fastPrintWriter = new com.android.internal.util.FastPrintWriter(outputStream);
        fastPrintWriter.println(FILE_VERSION_HEADER);
        for (java.util.Map.Entry<java.lang.String, com.android.server.pm.dex.PackageDynamicCodeLoading.PackageDynamicCode> entry : map.entrySet()) {
            fastPrintWriter.print(PACKAGE_PREFIX);
            fastPrintWriter.println(entry.getKey());
            for (java.util.Map.Entry<java.lang.String, com.android.server.pm.dex.PackageDynamicCodeLoading.DynamicCodeFile> entry2 : entry.getValue().mFileUsageMap.entrySet()) {
                java.lang.String key = entry2.getKey();
                com.android.server.pm.dex.PackageDynamicCodeLoading.DynamicCodeFile value = entry2.getValue();
                fastPrintWriter.print(value.mFileType);
                fastPrintWriter.print(FIELD_SEPARATOR);
                fastPrintWriter.print(value.mUserId);
                fastPrintWriter.print(FIELD_SEPARATOR);
                java.lang.String str = "";
                for (java.lang.String str2 : value.mLoadingPackages) {
                    fastPrintWriter.print(str);
                    fastPrintWriter.print(str2);
                    str = PACKAGE_SEPARATOR;
                }
                fastPrintWriter.print(FIELD_SEPARATOR);
                fastPrintWriter.println(escape(key));
            }
        }
        fastPrintWriter.flush();
        if (fastPrintWriter.checkError()) {
            throw new java.io.IOException("Writer failed");
        }
    }

    void read() {
        super.read((com.android.server.pm.dex.PackageDynamicCodeLoading) null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.pm.AbstractStatsBase
    public final void readInternal(java.lang.Void r4) {
        java.io.FileInputStream fileInputStream = null;
        try {
            try {
                fileInputStream = getFile().openRead();
                read((java.io.InputStream) fileInputStream);
            } catch (java.io.FileNotFoundException e) {
            } catch (java.io.IOException e2) {
                android.util.Slog.w(TAG, "Failed to parse dynamic usage for secondary code files.", e2);
            }
        } finally {
            libcore.io.IoUtils.closeQuietly(fileInputStream);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void read(java.io.InputStream inputStream) throws java.io.IOException {
        java.util.HashMap hashMap = new java.util.HashMap();
        read(inputStream, hashMap);
        synchronized (this.mLock) {
            this.mPackageMap = hashMap;
        }
    }

    private static void read(java.io.InputStream inputStream, java.util.Map<java.lang.String, com.android.server.pm.dex.PackageDynamicCodeLoading.PackageDynamicCode> map) throws java.io.IOException {
        java.lang.String readLine;
        java.io.BufferedReader bufferedReader = new java.io.BufferedReader(new java.io.InputStreamReader(inputStream));
        java.lang.String readLine2 = bufferedReader.readLine();
        if (!FILE_VERSION_HEADER.equals(readLine2)) {
            throw new java.io.IOException("Incorrect version line: " + readLine2);
        }
        java.lang.String readLine3 = bufferedReader.readLine();
        if (readLine3 != null && !readLine3.startsWith(PACKAGE_PREFIX)) {
            throw new java.io.IOException("Malformed line: " + readLine3);
        }
        while (readLine3 != null) {
            java.lang.String substring = readLine3.substring(PACKAGE_PREFIX.length());
            com.android.server.pm.dex.PackageDynamicCodeLoading.PackageDynamicCode packageDynamicCode = new com.android.server.pm.dex.PackageDynamicCodeLoading.PackageDynamicCode();
            while (true) {
                readLine = bufferedReader.readLine();
                if (readLine == null || readLine.startsWith(PACKAGE_PREFIX)) {
                    break;
                } else {
                    readFileInfo(readLine, packageDynamicCode);
                }
            }
            if (!packageDynamicCode.mFileUsageMap.isEmpty()) {
                map.put(substring, packageDynamicCode);
            }
            readLine3 = readLine;
        }
    }

    private static void readFileInfo(java.lang.String str, com.android.server.pm.dex.PackageDynamicCodeLoading.PackageDynamicCode packageDynamicCode) throws java.io.IOException {
        try {
            java.util.regex.Matcher matcher = PACKAGE_LINE_PATTERN.matcher(str);
            if (!matcher.matches()) {
                throw new java.io.IOException("Malformed line: " + str);
            }
            char charAt = matcher.group(1).charAt(0);
            int parseInt = java.lang.Integer.parseInt(matcher.group(2));
            java.lang.String[] split = matcher.group(3).split(PACKAGE_SEPARATOR);
            java.lang.String unescape = unescape(matcher.group(4));
            if (split.length == 0) {
                throw new java.io.IOException("Malformed line: " + str);
            }
            if (!isValidFileType(charAt)) {
                throw new java.io.IOException("Unknown file type: " + str);
            }
            packageDynamicCode.mFileUsageMap.put(unescape, new com.android.server.pm.dex.PackageDynamicCodeLoading.DynamicCodeFile(charAt, parseInt, split));
        } catch (java.lang.RuntimeException e) {
            throw new java.io.IOException("Unable to parse line: " + str, e);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static java.lang.String escape(java.lang.String str) {
        if (str.indexOf(92) == -1 && str.indexOf(10) == -1 && str.indexOf(13) == -1) {
            return str;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder(str.length() + 10);
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            switch (charAt) {
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                default:
                    sb.append(charAt);
                    break;
            }
        }
        return sb.toString();
    }

    @com.android.internal.annotations.VisibleForTesting
    static java.lang.String unescape(java.lang.String str) throws java.io.IOException {
        int indexOf = str.indexOf(92);
        if (indexOf == -1) {
            return str;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder(str.length());
        int i = 0;
        while (indexOf < str.length() - 1) {
            sb.append((java.lang.CharSequence) str, i, indexOf);
            switch (str.charAt(indexOf + 1)) {
                case '\\':
                    sb.append('\\');
                    break;
                case 'n':
                    sb.append('\n');
                    break;
                case 'r':
                    sb.append('\r');
                    break;
                default:
                    throw new java.io.IOException("Bad escape in: " + str);
            }
            i = indexOf + 2;
            indexOf = str.indexOf(92, i);
            if (indexOf == -1) {
                sb.append((java.lang.CharSequence) str, i, str.length());
                return sb.toString();
            }
        }
        throw new java.io.IOException("Unexpected \\ in: " + str);
    }

    static class PackageDynamicCode {
        final java.util.Map<java.lang.String, com.android.server.pm.dex.PackageDynamicCodeLoading.DynamicCodeFile> mFileUsageMap;

        private PackageDynamicCode() {
            this.mFileUsageMap = new java.util.HashMap();
        }

        private PackageDynamicCode(com.android.server.pm.dex.PackageDynamicCodeLoading.PackageDynamicCode packageDynamicCode) {
            this.mFileUsageMap = new java.util.HashMap(packageDynamicCode.mFileUsageMap.size());
            for (java.util.Map.Entry<java.lang.String, com.android.server.pm.dex.PackageDynamicCodeLoading.DynamicCodeFile> entry : packageDynamicCode.mFileUsageMap.entrySet()) {
                this.mFileUsageMap.put(entry.getKey(), new com.android.server.pm.dex.PackageDynamicCodeLoading.DynamicCodeFile(entry.getValue()));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean add(java.lang.String str, char c, int i, java.lang.String str2) {
            com.android.server.pm.dex.PackageDynamicCodeLoading.DynamicCodeFile dynamicCodeFile = this.mFileUsageMap.get(str);
            if (dynamicCodeFile == null) {
                if (this.mFileUsageMap.size() >= 100) {
                    return false;
                }
                this.mFileUsageMap.put(str, new com.android.server.pm.dex.PackageDynamicCodeLoading.DynamicCodeFile(c, i, new java.lang.String[]{str2}));
                return true;
            }
            if (dynamicCodeFile.mUserId != i) {
                return false;
            }
            return dynamicCodeFile.mLoadingPackages.add(str2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean removeUser(int i) {
            java.util.Iterator<com.android.server.pm.dex.PackageDynamicCodeLoading.DynamicCodeFile> it = this.mFileUsageMap.values().iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (it.next().mUserId == i) {
                    it.remove();
                    z = true;
                }
            }
            return z;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean removeFile(java.lang.String str, int i) {
            com.android.server.pm.dex.PackageDynamicCodeLoading.DynamicCodeFile dynamicCodeFile = this.mFileUsageMap.get(str);
            if (dynamicCodeFile == null || dynamicCodeFile.mUserId != i) {
                return false;
            }
            this.mFileUsageMap.remove(str);
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void syncData(java.util.Map<java.lang.String, java.util.Set<java.lang.Integer>> map, java.util.Set<java.lang.Integer> set) {
            java.util.Iterator<com.android.server.pm.dex.PackageDynamicCodeLoading.DynamicCodeFile> it = this.mFileUsageMap.values().iterator();
            while (it.hasNext()) {
                com.android.server.pm.dex.PackageDynamicCodeLoading.DynamicCodeFile next = it.next();
                int i = next.mUserId;
                if (!set.contains(java.lang.Integer.valueOf(i))) {
                    it.remove();
                } else {
                    java.util.Iterator<java.lang.String> it2 = next.mLoadingPackages.iterator();
                    while (it2.hasNext()) {
                        java.util.Set<java.lang.Integer> set2 = map.get(it2.next());
                        if (set2 == null || !set2.contains(java.lang.Integer.valueOf(i))) {
                            it2.remove();
                        }
                    }
                    if (next.mLoadingPackages.isEmpty()) {
                        it.remove();
                    }
                }
            }
        }
    }

    static class DynamicCodeFile {
        final char mFileType;
        final java.util.Set<java.lang.String> mLoadingPackages;
        final int mUserId;

        private DynamicCodeFile(char c, int i, java.lang.String... strArr) {
            this.mFileType = c;
            this.mUserId = i;
            this.mLoadingPackages = new java.util.HashSet(java.util.Arrays.asList(strArr));
        }

        private DynamicCodeFile(com.android.server.pm.dex.PackageDynamicCodeLoading.DynamicCodeFile dynamicCodeFile) {
            this.mFileType = dynamicCodeFile.mFileType;
            this.mUserId = dynamicCodeFile.mUserId;
            this.mLoadingPackages = new java.util.HashSet(dynamicCodeFile.mLoadingPackages);
        }
    }
}
