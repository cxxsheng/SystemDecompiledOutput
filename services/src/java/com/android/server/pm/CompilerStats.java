package com.android.server.pm;

/* loaded from: classes2.dex */
class CompilerStats extends com.android.server.pm.AbstractStatsBase<java.lang.Void> {
    private static final int COMPILER_STATS_VERSION = 1;
    private static final java.lang.String COMPILER_STATS_VERSION_HEADER = "PACKAGE_MANAGER__COMPILER_STATS__";
    private final java.util.Map<java.lang.String, com.android.server.pm.CompilerStats.PackageStats> packageStats;

    static class PackageStats {
        private final java.util.Map<java.lang.String, java.lang.Long> compileTimePerCodePath = new android.util.ArrayMap(2);
        private final java.lang.String packageName;

        public PackageStats(java.lang.String str) {
            this.packageName = str;
        }

        public java.lang.String getPackageName() {
            return this.packageName;
        }

        public long getCompileTime(java.lang.String str) {
            java.lang.String storedPathFromCodePath = getStoredPathFromCodePath(str);
            synchronized (this.compileTimePerCodePath) {
                try {
                    java.lang.Long l = this.compileTimePerCodePath.get(storedPathFromCodePath);
                    if (l == null) {
                        return 0L;
                    }
                    return l.longValue();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void setCompileTime(java.lang.String str, long j) {
            java.lang.String storedPathFromCodePath = getStoredPathFromCodePath(str);
            synchronized (this.compileTimePerCodePath) {
                try {
                    if (j <= 0) {
                        this.compileTimePerCodePath.remove(storedPathFromCodePath);
                    } else {
                        this.compileTimePerCodePath.put(storedPathFromCodePath, java.lang.Long.valueOf(j));
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private static java.lang.String getStoredPathFromCodePath(java.lang.String str) {
            return str.substring(str.lastIndexOf(java.io.File.separatorChar) + 1);
        }

        public void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
            synchronized (this.compileTimePerCodePath) {
                try {
                    if (this.compileTimePerCodePath.size() == 0) {
                        indentingPrintWriter.println("(No recorded stats)");
                    } else {
                        for (java.util.Map.Entry<java.lang.String, java.lang.Long> entry : this.compileTimePerCodePath.entrySet()) {
                            indentingPrintWriter.println(" " + entry.getKey() + " - " + entry.getValue());
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    public CompilerStats() {
        super("package-cstats.list", "CompilerStats_DiskWriter", false);
        this.packageStats = new java.util.HashMap();
    }

    public com.android.server.pm.CompilerStats.PackageStats getPackageStats(java.lang.String str) {
        com.android.server.pm.CompilerStats.PackageStats packageStats;
        synchronized (this.packageStats) {
            packageStats = this.packageStats.get(str);
        }
        return packageStats;
    }

    public void setPackageStats(java.lang.String str, com.android.server.pm.CompilerStats.PackageStats packageStats) {
        synchronized (this.packageStats) {
            this.packageStats.put(str, packageStats);
        }
    }

    public com.android.server.pm.CompilerStats.PackageStats createPackageStats(java.lang.String str) {
        com.android.server.pm.CompilerStats.PackageStats packageStats;
        synchronized (this.packageStats) {
            packageStats = new com.android.server.pm.CompilerStats.PackageStats(str);
            this.packageStats.put(str, packageStats);
        }
        return packageStats;
    }

    public com.android.server.pm.CompilerStats.PackageStats getOrCreatePackageStats(java.lang.String str) {
        synchronized (this.packageStats) {
            try {
                com.android.server.pm.CompilerStats.PackageStats packageStats = this.packageStats.get(str);
                if (packageStats != null) {
                    return packageStats;
                }
                return createPackageStats(str);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void deletePackageStats(java.lang.String str) {
        synchronized (this.packageStats) {
            this.packageStats.remove(str);
        }
    }

    public void write(java.io.Writer writer) {
        com.android.internal.util.FastPrintWriter fastPrintWriter = new com.android.internal.util.FastPrintWriter(writer);
        fastPrintWriter.print(COMPILER_STATS_VERSION_HEADER);
        fastPrintWriter.println(1);
        synchronized (this.packageStats) {
            try {
                for (com.android.server.pm.CompilerStats.PackageStats packageStats : this.packageStats.values()) {
                    synchronized (packageStats.compileTimePerCodePath) {
                        try {
                            if (!packageStats.compileTimePerCodePath.isEmpty()) {
                                fastPrintWriter.println(packageStats.getPackageName());
                                for (java.util.Map.Entry entry : packageStats.compileTimePerCodePath.entrySet()) {
                                    fastPrintWriter.println("-" + ((java.lang.String) entry.getKey()) + ":" + entry.getValue());
                                }
                            }
                        } finally {
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        fastPrintWriter.flush();
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0079, code lost:
    
        throw new java.lang.IllegalArgumentException("Could not parse data " + r3);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean read(java.io.Reader reader) {
        synchronized (this.packageStats) {
            try {
                this.packageStats.clear();
                try {
                    java.io.BufferedReader bufferedReader = new java.io.BufferedReader(reader);
                    java.lang.String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        throw new java.lang.IllegalArgumentException("No version line found.");
                    }
                    if (!readLine.startsWith(COMPILER_STATS_VERSION_HEADER)) {
                        throw new java.lang.IllegalArgumentException("Invalid version line: " + readLine);
                    }
                    int parseInt = java.lang.Integer.parseInt(readLine.substring(COMPILER_STATS_VERSION_HEADER.length()));
                    if (parseInt != 1) {
                        throw new java.lang.IllegalArgumentException("Unexpected version: " + parseInt);
                    }
                    com.android.server.pm.CompilerStats.PackageStats packageStats = new com.android.server.pm.CompilerStats.PackageStats("fake package");
                    while (true) {
                        java.lang.String readLine2 = bufferedReader.readLine();
                        if (readLine2 != null) {
                            if (readLine2.startsWith("-")) {
                                int indexOf = readLine2.indexOf(58);
                                if (indexOf == -1 || indexOf == 1) {
                                    break;
                                }
                                packageStats.setCompileTime(readLine2.substring(1, indexOf), java.lang.Long.parseLong(readLine2.substring(indexOf + 1)));
                            } else {
                                packageStats = getOrCreatePackageStats(readLine2);
                            }
                        }
                    }
                } catch (java.lang.Exception e) {
                    android.util.Log.e("PackageManager", "Error parsing compiler stats", e);
                    return false;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return true;
    }

    void writeNow() {
        writeNow(null);
    }

    boolean maybeWriteAsync() {
        return maybeWriteAsync(null);
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
                android.util.Log.e("PackageManager", "Failed to write compiler stats", e);
            }
        } catch (java.io.IOException e2) {
            e = e2;
            fileOutputStream = null;
        }
    }

    void read() {
        read((com.android.server.pm.CompilerStats) null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.pm.AbstractStatsBase
    public void readInternal(java.lang.Void r4) {
        java.io.BufferedReader bufferedReader = null;
        try {
            java.io.BufferedReader bufferedReader2 = new java.io.BufferedReader(new java.io.InputStreamReader(getFile().openRead()));
            try {
                read((java.io.Reader) bufferedReader2);
                libcore.io.IoUtils.closeQuietly(bufferedReader2);
            } catch (java.io.FileNotFoundException e) {
                bufferedReader = bufferedReader2;
                libcore.io.IoUtils.closeQuietly(bufferedReader);
            } catch (java.lang.Throwable th) {
                th = th;
                bufferedReader = bufferedReader2;
                libcore.io.IoUtils.closeQuietly(bufferedReader);
                throw th;
            }
        } catch (java.io.FileNotFoundException e2) {
        } catch (java.lang.Throwable th2) {
            th = th2;
        }
    }
}
