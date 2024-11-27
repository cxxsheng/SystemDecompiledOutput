package com.android.server.pm.dex;

/* loaded from: classes2.dex */
public class PackageDexUsage extends com.android.server.pm.AbstractStatsBase<java.lang.Void> {
    private static final java.lang.String CODE_PATH_LINE_CHAR = "+";
    private static final java.lang.String DEX_LINE_CHAR = "#";
    private static final java.lang.String LOADING_PACKAGE_CHAR = "@";

    @com.android.internal.annotations.VisibleForTesting
    static final int MAX_SECONDARY_FILES_PER_OWNER = 100;
    private static final int PACKAGE_DEX_USAGE_VERSION = 2;
    private static final java.lang.String PACKAGE_DEX_USAGE_VERSION_HEADER = "PACKAGE_MANAGER__PACKAGE_DEX_USAGE__";
    private static final java.lang.String SPLIT_CHAR = ",";
    private static final java.lang.String TAG = "PackageDexUsage";
    static final java.lang.String UNSUPPORTED_CLASS_LOADER_CONTEXT = "=UnsupportedClassLoaderContext=";
    static final java.lang.String VARIABLE_CLASS_LOADER_CONTEXT = "=VariableClassLoaderContext=";

    @com.android.internal.annotations.GuardedBy({"mPackageUseInfoMap"})
    private final java.util.Map<java.lang.String, com.android.server.pm.dex.PackageDexUsage.PackageUseInfo> mPackageUseInfoMap;

    PackageDexUsage() {
        super("package-dex-usage.list", "PackageDexUsage_DiskWriter", false);
        this.mPackageUseInfoMap = new java.util.HashMap();
    }

    boolean record(java.lang.String str, java.lang.String str2, int i, java.lang.String str3, boolean z, java.lang.String str4, java.lang.String str5, boolean z2) {
        if (!com.android.server.pm.PackageManagerServiceUtils.checkISA(str3)) {
            throw new java.lang.IllegalArgumentException("loaderIsa " + str3 + " is unsupported");
        }
        if (str5 == null) {
            throw new java.lang.IllegalArgumentException("Null classLoaderContext");
        }
        if (str5.equals(UNSUPPORTED_CLASS_LOADER_CONTEXT)) {
            android.util.Slog.e(TAG, "Unsupported context?");
            return false;
        }
        boolean z3 = !str.equals(str4);
        synchronized (this.mPackageUseInfoMap) {
            try {
                com.android.server.pm.dex.PackageDexUsage.PackageUseInfo packageUseInfo = this.mPackageUseInfoMap.get(str);
                if (packageUseInfo == null) {
                    com.android.server.pm.dex.PackageDexUsage.PackageUseInfo packageUseInfo2 = new com.android.server.pm.dex.PackageDexUsage.PackageUseInfo(str);
                    if (z) {
                        packageUseInfo2.mergePrimaryCodePaths(str2, str4);
                    } else {
                        com.android.server.pm.dex.PackageDexUsage.DexUseInfo dexUseInfo = new com.android.server.pm.dex.PackageDexUsage.DexUseInfo(z3, i, str5, str3);
                        packageUseInfo2.mDexUseInfoMap.put(str2, dexUseInfo);
                        maybeAddLoadingPackage(str, str4, dexUseInfo.mLoadingPackages);
                    }
                    this.mPackageUseInfoMap.put(str, packageUseInfo2);
                    return true;
                }
                if (z) {
                    return packageUseInfo.mergePrimaryCodePaths(str2, str4);
                }
                com.android.server.pm.dex.PackageDexUsage.DexUseInfo dexUseInfo2 = new com.android.server.pm.dex.PackageDexUsage.DexUseInfo(z3, i, str5, str3);
                boolean maybeAddLoadingPackage = maybeAddLoadingPackage(str, str4, dexUseInfo2.mLoadingPackages);
                com.android.server.pm.dex.PackageDexUsage.DexUseInfo dexUseInfo3 = (com.android.server.pm.dex.PackageDexUsage.DexUseInfo) packageUseInfo.mDexUseInfoMap.get(str2);
                if (dexUseInfo3 == null) {
                    if (packageUseInfo.mDexUseInfoMap.size() >= 100) {
                        return maybeAddLoadingPackage;
                    }
                    packageUseInfo.mDexUseInfoMap.put(str2, dexUseInfo2);
                    return true;
                }
                if (i != dexUseInfo3.mOwnerUserId) {
                    throw new java.lang.IllegalArgumentException("Trying to change ownerUserId for  dex path " + str2 + " from " + dexUseInfo3.mOwnerUserId + " to " + i);
                }
                return dexUseInfo3.merge(dexUseInfo2, z2) || maybeAddLoadingPackage;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void read() {
        read((com.android.server.pm.dex.PackageDexUsage) null);
    }

    void maybeWriteAsync() {
        maybeWriteAsync(null);
    }

    void writeNow() {
        writeInternal((java.lang.Void) null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.pm.AbstractStatsBase
    public void writeInternal(java.lang.Void r3) {
        java.io.FileOutputStream fileOutputStream;
        android.util.AtomicFile file = getFile();
        try {
            fileOutputStream = file.startWrite();
            try {
                java.io.OutputStreamWriter outputStreamWriter = new java.io.OutputStreamWriter(fileOutputStream);
                write(outputStreamWriter);
                outputStreamWriter.flush();
                file.finishWrite(fileOutputStream);
            } catch (java.io.IOException e) {
                e = e;
                if (fileOutputStream != null) {
                    file.failWrite(fileOutputStream);
                }
                android.util.Slog.e(TAG, "Failed to write usage for dex files", e);
            }
        } catch (java.io.IOException e2) {
            e = e2;
            fileOutputStream = null;
        }
    }

    void write(java.io.Writer writer) {
        java.util.Map<java.lang.String, com.android.server.pm.dex.PackageDexUsage.PackageUseInfo> clonePackageUseInfoMap = clonePackageUseInfoMap();
        com.android.internal.util.FastPrintWriter fastPrintWriter = new com.android.internal.util.FastPrintWriter(writer);
        fastPrintWriter.print(PACKAGE_DEX_USAGE_VERSION_HEADER);
        fastPrintWriter.println(2);
        for (java.util.Map.Entry<java.lang.String, com.android.server.pm.dex.PackageDexUsage.PackageUseInfo> entry : clonePackageUseInfoMap.entrySet()) {
            java.lang.String key = entry.getKey();
            com.android.server.pm.dex.PackageDexUsage.PackageUseInfo value = entry.getValue();
            fastPrintWriter.println(key);
            for (java.util.Map.Entry entry2 : value.mPrimaryCodePaths.entrySet()) {
                java.lang.String str = (java.lang.String) entry2.getKey();
                java.util.Set set = (java.util.Set) entry2.getValue();
                fastPrintWriter.println(CODE_PATH_LINE_CHAR + str);
                fastPrintWriter.println(LOADING_PACKAGE_CHAR + java.lang.String.join(SPLIT_CHAR, set));
            }
            for (java.util.Map.Entry entry3 : value.mDexUseInfoMap.entrySet()) {
                java.lang.String str2 = (java.lang.String) entry3.getKey();
                com.android.server.pm.dex.PackageDexUsage.DexUseInfo dexUseInfo = (com.android.server.pm.dex.PackageDexUsage.DexUseInfo) entry3.getValue();
                fastPrintWriter.println(DEX_LINE_CHAR + str2);
                fastPrintWriter.print(java.lang.String.join(SPLIT_CHAR, java.lang.Integer.toString(dexUseInfo.mOwnerUserId), writeBoolean(dexUseInfo.mIsUsedByOtherApps)));
                java.util.Iterator it = dexUseInfo.mLoaderIsas.iterator();
                while (it.hasNext()) {
                    fastPrintWriter.print(SPLIT_CHAR + ((java.lang.String) it.next()));
                }
                fastPrintWriter.println();
                fastPrintWriter.println(LOADING_PACKAGE_CHAR + java.lang.String.join(SPLIT_CHAR, dexUseInfo.mLoadingPackages));
                fastPrintWriter.println(dexUseInfo.getClassLoaderContext());
            }
        }
        fastPrintWriter.flush();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.pm.AbstractStatsBase
    public void readInternal(java.lang.Void r4) {
        java.io.BufferedReader bufferedReader = null;
        try {
            try {
                java.io.BufferedReader bufferedReader2 = new java.io.BufferedReader(new java.io.InputStreamReader(getFile().openRead()));
                try {
                    read((java.io.Reader) bufferedReader2);
                    libcore.io.IoUtils.closeQuietly(bufferedReader2);
                } catch (java.io.FileNotFoundException e) {
                    bufferedReader = bufferedReader2;
                    libcore.io.IoUtils.closeQuietly(bufferedReader);
                } catch (java.io.IOException e2) {
                    e = e2;
                    bufferedReader = bufferedReader2;
                    android.util.Slog.w(TAG, "Failed to parse package dex usage.", e);
                    libcore.io.IoUtils.closeQuietly(bufferedReader);
                } catch (java.lang.Throwable th) {
                    th = th;
                    bufferedReader = bufferedReader2;
                    libcore.io.IoUtils.closeQuietly(bufferedReader);
                    throw th;
                }
            } catch (java.io.FileNotFoundException e3) {
                libcore.io.IoUtils.closeQuietly(bufferedReader);
            } catch (java.io.IOException e4) {
                e = e4;
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
        }
    }

    void read(java.io.Reader reader) throws java.io.IOException {
        java.util.HashMap hashMap = new java.util.HashMap();
        java.io.BufferedReader bufferedReader = new java.io.BufferedReader(reader);
        java.lang.String readLine = bufferedReader.readLine();
        if (readLine == null) {
            throw new java.lang.IllegalStateException("No version line found.");
        }
        if (!readLine.startsWith(PACKAGE_DEX_USAGE_VERSION_HEADER)) {
            throw new java.lang.IllegalStateException("Invalid version line: " + readLine);
        }
        int parseInt = java.lang.Integer.parseInt(readLine.substring(PACKAGE_DEX_USAGE_VERSION_HEADER.length()));
        if (!isSupportedVersion(parseInt)) {
            android.util.Slog.w(TAG, "Unexpected package-dex-use version: " + parseInt + ". Not reading from it");
            return;
        }
        java.util.HashSet hashSet = new java.util.HashSet();
        for (java.lang.String str : android.os.Build.SUPPORTED_ABIS) {
            hashSet.add(dalvik.system.VMRuntime.getInstructionSet(str));
        }
        java.lang.String str2 = null;
        java.lang.String str3 = null;
        com.android.server.pm.dex.PackageDexUsage.PackageUseInfo packageUseInfo = null;
        while (true) {
            java.lang.String readLine2 = bufferedReader.readLine();
            if (readLine2 != null) {
                if (readLine2.startsWith(DEX_LINE_CHAR)) {
                    if (str3 == null) {
                        throw new java.lang.IllegalStateException("Malformed PackageDexUsage file. Expected package line before dex line.");
                    }
                    java.lang.String substring = readLine2.substring(DEX_LINE_CHAR.length());
                    java.lang.String readLine3 = bufferedReader.readLine();
                    if (readLine3 == null) {
                        throw new java.lang.IllegalStateException("Could not find dexUseInfo line");
                    }
                    java.lang.String[] split = readLine3.split(SPLIT_CHAR);
                    if (split.length < 3) {
                        throw new java.lang.IllegalStateException("Invalid PackageDexUsage line: " + readLine3);
                    }
                    java.util.Set<java.lang.String> readLoadingPackages = readLoadingPackages(bufferedReader, parseInt);
                    java.lang.String readClassLoaderContext = readClassLoaderContext(bufferedReader, parseInt);
                    if (!UNSUPPORTED_CLASS_LOADER_CONTEXT.equals(readClassLoaderContext)) {
                        com.android.server.pm.dex.PackageDexUsage.DexUseInfo dexUseInfo = new com.android.server.pm.dex.PackageDexUsage.DexUseInfo(readBoolean(split[1]), java.lang.Integer.parseInt(split[0]), readClassLoaderContext, str2);
                        dexUseInfo.mLoadingPackages.addAll(readLoadingPackages);
                        for (int i = 2; i < split.length; i++) {
                            java.lang.String str4 = split[i];
                            if (hashSet.contains(str4)) {
                                dexUseInfo.mLoaderIsas.add(split[i]);
                            } else {
                                android.util.Slog.wtf(TAG, "Unsupported ISA when parsing PackageDexUsage: " + str4);
                            }
                        }
                        if (hashSet.isEmpty()) {
                            android.util.Slog.wtf(TAG, "Ignore dexPath when parsing PackageDexUsage because of unsupported isas. dexPath=" + substring);
                        } else {
                            packageUseInfo.mDexUseInfoMap.put(substring, dexUseInfo);
                        }
                    }
                } else if (readLine2.startsWith(CODE_PATH_LINE_CHAR)) {
                    packageUseInfo.mPrimaryCodePaths.put(readLine2.substring(CODE_PATH_LINE_CHAR.length()), readLoadingPackages(bufferedReader, parseInt));
                } else {
                    packageUseInfo = new com.android.server.pm.dex.PackageDexUsage.PackageUseInfo(readLine2);
                    hashMap.put(readLine2, packageUseInfo);
                    str3 = readLine2;
                    str2 = null;
                }
                str2 = null;
            } else {
                synchronized (this.mPackageUseInfoMap) {
                    this.mPackageUseInfoMap.clear();
                    this.mPackageUseInfoMap.putAll(hashMap);
                }
                return;
            }
        }
    }

    private java.lang.String readClassLoaderContext(java.io.BufferedReader bufferedReader, int i) throws java.io.IOException {
        java.lang.String readLine = bufferedReader.readLine();
        if (readLine == null) {
            throw new java.lang.IllegalStateException("Could not find the classLoaderContext line.");
        }
        return readLine;
    }

    private java.util.Set<java.lang.String> readLoadingPackages(java.io.BufferedReader bufferedReader, int i) throws java.io.IOException {
        java.lang.String readLine = bufferedReader.readLine();
        if (readLine == null) {
            throw new java.lang.IllegalStateException("Could not find the loadingPackages line.");
        }
        java.util.HashSet hashSet = new java.util.HashSet();
        if (readLine.length() != LOADING_PACKAGE_CHAR.length()) {
            java.util.Collections.addAll(hashSet, readLine.substring(LOADING_PACKAGE_CHAR.length()).split(SPLIT_CHAR));
        }
        return hashSet;
    }

    private boolean maybeAddLoadingPackage(java.lang.String str, java.lang.String str2, java.util.Set<java.lang.String> set) {
        return !str.equals(str2) && set.add(str2);
    }

    private boolean isSupportedVersion(int i) {
        return i == 2;
    }

    void syncData(java.util.Map<java.lang.String, java.util.Set<java.lang.Integer>> map, java.util.Map<java.lang.String, java.util.Set<java.lang.String>> map2, java.util.List<java.lang.String> list) {
        synchronized (this.mPackageUseInfoMap) {
            try {
                java.util.Iterator<java.util.Map.Entry<java.lang.String, com.android.server.pm.dex.PackageDexUsage.PackageUseInfo>> it = this.mPackageUseInfoMap.entrySet().iterator();
                while (it.hasNext()) {
                    java.util.Map.Entry<java.lang.String, com.android.server.pm.dex.PackageDexUsage.PackageUseInfo> next = it.next();
                    java.lang.String key = next.getKey();
                    if (!list.contains(key)) {
                        com.android.server.pm.dex.PackageDexUsage.PackageUseInfo value = next.getValue();
                        java.util.Set<java.lang.Integer> set = map.get(key);
                        if (set == null) {
                            it.remove();
                        } else {
                            java.util.Iterator it2 = value.mDexUseInfoMap.entrySet().iterator();
                            while (it2.hasNext()) {
                                if (!set.contains(java.lang.Integer.valueOf(((com.android.server.pm.dex.PackageDexUsage.DexUseInfo) ((java.util.Map.Entry) it2.next()).getValue()).mOwnerUserId))) {
                                    it2.remove();
                                }
                            }
                            java.util.Set<java.lang.String> set2 = map2.get(key);
                            java.util.Iterator it3 = value.mPrimaryCodePaths.entrySet().iterator();
                            while (it3.hasNext()) {
                                java.util.Map.Entry entry = (java.util.Map.Entry) it3.next();
                                if (!set2.contains((java.lang.String) entry.getKey())) {
                                    it3.remove();
                                } else {
                                    java.util.Iterator it4 = ((java.util.Set) entry.getValue()).iterator();
                                    while (it4.hasNext()) {
                                        java.lang.String str = (java.lang.String) it4.next();
                                        if (!list.contains(str) && !map.containsKey(str)) {
                                            it4.remove();
                                        }
                                    }
                                }
                            }
                            if (!value.isAnyCodePathUsedByOtherApps() && value.mDexUseInfoMap.isEmpty()) {
                                it.remove();
                            }
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean clearUsedByOtherApps(java.lang.String str) {
        synchronized (this.mPackageUseInfoMap) {
            try {
                com.android.server.pm.dex.PackageDexUsage.PackageUseInfo packageUseInfo = this.mPackageUseInfoMap.get(str);
                if (packageUseInfo == null) {
                    return false;
                }
                return packageUseInfo.clearCodePathUsedByOtherApps();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean removePackage(java.lang.String str) {
        boolean z;
        synchronized (this.mPackageUseInfoMap) {
            z = this.mPackageUseInfoMap.remove(str) != null;
        }
        return z;
    }

    boolean removeUserPackage(java.lang.String str, int i) {
        synchronized (this.mPackageUseInfoMap) {
            try {
                com.android.server.pm.dex.PackageDexUsage.PackageUseInfo packageUseInfo = this.mPackageUseInfoMap.get(str);
                boolean z = false;
                if (packageUseInfo == null) {
                    return false;
                }
                java.util.Iterator it = packageUseInfo.mDexUseInfoMap.entrySet().iterator();
                while (it.hasNext()) {
                    if (((com.android.server.pm.dex.PackageDexUsage.DexUseInfo) ((java.util.Map.Entry) it.next()).getValue()).mOwnerUserId == i) {
                        it.remove();
                        z = true;
                    }
                }
                if (packageUseInfo.mDexUseInfoMap.isEmpty() && !packageUseInfo.isAnyCodePathUsedByOtherApps()) {
                    this.mPackageUseInfoMap.remove(str);
                    z = true;
                }
                return z;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean removeDexFile(java.lang.String str, java.lang.String str2, int i) {
        synchronized (this.mPackageUseInfoMap) {
            try {
                com.android.server.pm.dex.PackageDexUsage.PackageUseInfo packageUseInfo = this.mPackageUseInfoMap.get(str);
                if (packageUseInfo == null) {
                    return false;
                }
                return removeDexFile(packageUseInfo, str2, i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private boolean removeDexFile(com.android.server.pm.dex.PackageDexUsage.PackageUseInfo packageUseInfo, java.lang.String str, int i) {
        com.android.server.pm.dex.PackageDexUsage.DexUseInfo dexUseInfo = (com.android.server.pm.dex.PackageDexUsage.DexUseInfo) packageUseInfo.mDexUseInfoMap.get(str);
        if (dexUseInfo == null || dexUseInfo.mOwnerUserId != i) {
            return false;
        }
        packageUseInfo.mDexUseInfoMap.remove(str);
        return true;
    }

    com.android.server.pm.dex.PackageDexUsage.PackageUseInfo getPackageUseInfo(java.lang.String str) {
        com.android.server.pm.dex.PackageDexUsage.PackageUseInfo packageUseInfo;
        synchronized (this.mPackageUseInfoMap) {
            com.android.server.pm.dex.PackageDexUsage.PackageUseInfo packageUseInfo2 = this.mPackageUseInfoMap.get(str);
            packageUseInfo = null;
            byte b = 0;
            if (packageUseInfo2 != null) {
                packageUseInfo = new com.android.server.pm.dex.PackageDexUsage.PackageUseInfo(packageUseInfo2);
            }
        }
        return packageUseInfo;
    }

    java.util.Set<java.lang.String> getAllPackagesWithSecondaryDexFiles() {
        java.util.HashSet hashSet = new java.util.HashSet();
        synchronized (this.mPackageUseInfoMap) {
            try {
                for (java.util.Map.Entry<java.lang.String, com.android.server.pm.dex.PackageDexUsage.PackageUseInfo> entry : this.mPackageUseInfoMap.entrySet()) {
                    if (!entry.getValue().mDexUseInfoMap.isEmpty()) {
                        hashSet.add(entry.getKey());
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return hashSet;
    }

    void clear() {
        synchronized (this.mPackageUseInfoMap) {
            this.mPackageUseInfoMap.clear();
        }
    }

    private java.util.Map<java.lang.String, com.android.server.pm.dex.PackageDexUsage.PackageUseInfo> clonePackageUseInfoMap() {
        java.util.HashMap hashMap = new java.util.HashMap();
        synchronized (this.mPackageUseInfoMap) {
            try {
                for (java.util.Map.Entry<java.lang.String, com.android.server.pm.dex.PackageDexUsage.PackageUseInfo> entry : this.mPackageUseInfoMap.entrySet()) {
                    hashMap.put(entry.getKey(), new com.android.server.pm.dex.PackageDexUsage.PackageUseInfo(entry.getValue()));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return hashMap;
    }

    private java.lang.String writeBoolean(boolean z) {
        return z ? "1" : "0";
    }

    private boolean readBoolean(java.lang.String str) {
        if ("0".equals(str)) {
            return false;
        }
        if ("1".equals(str)) {
            return true;
        }
        throw new java.lang.IllegalArgumentException("Unknown bool encoding: " + str);
    }

    java.lang.String dump() {
        java.io.StringWriter stringWriter = new java.io.StringWriter();
        write(stringWriter);
        return stringWriter.toString();
    }

    public static class PackageUseInfo {
        private final java.util.Map<java.lang.String, com.android.server.pm.dex.PackageDexUsage.DexUseInfo> mDexUseInfoMap;
        private final java.lang.String mPackageName;
        private final java.util.Map<java.lang.String, java.util.Set<java.lang.String>> mPrimaryCodePaths;

        PackageUseInfo(java.lang.String str) {
            this.mPrimaryCodePaths = new java.util.HashMap();
            this.mDexUseInfoMap = new java.util.HashMap();
            this.mPackageName = str;
        }

        private PackageUseInfo(com.android.server.pm.dex.PackageDexUsage.PackageUseInfo packageUseInfo) {
            this.mPackageName = packageUseInfo.mPackageName;
            this.mPrimaryCodePaths = new java.util.HashMap();
            for (java.util.Map.Entry<java.lang.String, java.util.Set<java.lang.String>> entry : packageUseInfo.mPrimaryCodePaths.entrySet()) {
                this.mPrimaryCodePaths.put(entry.getKey(), new java.util.HashSet(entry.getValue()));
            }
            this.mDexUseInfoMap = new java.util.HashMap();
            for (java.util.Map.Entry<java.lang.String, com.android.server.pm.dex.PackageDexUsage.DexUseInfo> entry2 : packageUseInfo.mDexUseInfoMap.entrySet()) {
                this.mDexUseInfoMap.put(entry2.getKey(), new com.android.server.pm.dex.PackageDexUsage.DexUseInfo(entry2.getValue()));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean mergePrimaryCodePaths(java.lang.String str, java.lang.String str2) {
            java.util.Set<java.lang.String> set = this.mPrimaryCodePaths.get(str);
            if (set == null) {
                set = new java.util.HashSet<>();
                this.mPrimaryCodePaths.put(str, set);
            }
            return set.add(str2);
        }

        public boolean isUsedByOtherApps(java.lang.String str) {
            if (!this.mPrimaryCodePaths.containsKey(str)) {
                return false;
            }
            java.util.Set<java.lang.String> set = this.mPrimaryCodePaths.get(str);
            if (set.contains(this.mPackageName)) {
                return set.size() > 1;
            }
            return !set.isEmpty();
        }

        public java.util.Map<java.lang.String, com.android.server.pm.dex.PackageDexUsage.DexUseInfo> getDexUseInfoMap() {
            return this.mDexUseInfoMap;
        }

        public java.util.Set<java.lang.String> getLoadingPackages(java.lang.String str) {
            return this.mPrimaryCodePaths.getOrDefault(str, null);
        }

        public boolean isAnyCodePathUsedByOtherApps() {
            return !this.mPrimaryCodePaths.isEmpty();
        }

        boolean clearCodePathUsedByOtherApps() {
            java.util.ArrayList arrayList = new java.util.ArrayList(1);
            arrayList.add(this.mPackageName);
            java.util.Iterator<java.util.Map.Entry<java.lang.String, java.util.Set<java.lang.String>>> it = this.mPrimaryCodePaths.entrySet().iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (it.next().getValue().retainAll(arrayList)) {
                    z = true;
                }
            }
            return z;
        }
    }

    public static class DexUseInfo {
        private java.lang.String mClassLoaderContext;
        private boolean mIsUsedByOtherApps;
        private final java.util.Set<java.lang.String> mLoaderIsas;
        private final java.util.Set<java.lang.String> mLoadingPackages;
        private final int mOwnerUserId;

        @com.android.internal.annotations.VisibleForTesting
        DexUseInfo(boolean z, int i, java.lang.String str, java.lang.String str2) {
            this.mIsUsedByOtherApps = z;
            this.mOwnerUserId = i;
            this.mClassLoaderContext = str;
            this.mLoaderIsas = new java.util.HashSet();
            if (str2 != null) {
                this.mLoaderIsas.add(str2);
            }
            this.mLoadingPackages = new java.util.HashSet();
        }

        private DexUseInfo(com.android.server.pm.dex.PackageDexUsage.DexUseInfo dexUseInfo) {
            this.mIsUsedByOtherApps = dexUseInfo.mIsUsedByOtherApps;
            this.mOwnerUserId = dexUseInfo.mOwnerUserId;
            this.mClassLoaderContext = dexUseInfo.mClassLoaderContext;
            this.mLoaderIsas = new java.util.HashSet(dexUseInfo.mLoaderIsas);
            this.mLoadingPackages = new java.util.HashSet(dexUseInfo.mLoadingPackages);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean merge(com.android.server.pm.dex.PackageDexUsage.DexUseInfo dexUseInfo, boolean z) {
            boolean z2 = this.mIsUsedByOtherApps;
            this.mIsUsedByOtherApps = this.mIsUsedByOtherApps || dexUseInfo.mIsUsedByOtherApps;
            boolean addAll = this.mLoaderIsas.addAll(dexUseInfo.mLoaderIsas);
            boolean addAll2 = this.mLoadingPackages.addAll(dexUseInfo.mLoadingPackages);
            java.lang.String str = this.mClassLoaderContext;
            if (z) {
                this.mClassLoaderContext = dexUseInfo.mClassLoaderContext;
            } else if (isUnsupportedContext(this.mClassLoaderContext)) {
                this.mClassLoaderContext = dexUseInfo.mClassLoaderContext;
            } else if (!java.util.Objects.equals(this.mClassLoaderContext, dexUseInfo.mClassLoaderContext)) {
                this.mClassLoaderContext = com.android.server.pm.dex.PackageDexUsage.VARIABLE_CLASS_LOADER_CONTEXT;
            }
            return addAll || z2 != this.mIsUsedByOtherApps || addAll2 || !java.util.Objects.equals(str, this.mClassLoaderContext);
        }

        private static boolean isUnsupportedContext(java.lang.String str) {
            return com.android.server.pm.dex.PackageDexUsage.UNSUPPORTED_CLASS_LOADER_CONTEXT.equals(str);
        }

        public boolean isUsedByOtherApps() {
            return this.mIsUsedByOtherApps;
        }

        int getOwnerUserId() {
            return this.mOwnerUserId;
        }

        public java.util.Set<java.lang.String> getLoaderIsas() {
            return this.mLoaderIsas;
        }

        public java.util.Set<java.lang.String> getLoadingPackages() {
            return this.mLoadingPackages;
        }

        public java.lang.String getClassLoaderContext() {
            return this.mClassLoaderContext;
        }

        public boolean isUnsupportedClassLoaderContext() {
            return isUnsupportedContext(this.mClassLoaderContext);
        }

        public boolean isVariableClassLoaderContext() {
            return com.android.server.pm.dex.PackageDexUsage.VARIABLE_CLASS_LOADER_CONTEXT.equals(this.mClassLoaderContext);
        }
    }
}
