package com.android.server.am;

/* loaded from: classes.dex */
final class PackageList {
    private final android.util.ArrayMap<java.lang.String, com.android.internal.app.procstats.ProcessStats.ProcessStateHolder> mPkgList = new android.util.ArrayMap<>();
    private final com.android.server.am.ProcessRecord mProcess;

    PackageList(com.android.server.am.ProcessRecord processRecord) {
        this.mProcess = processRecord;
    }

    com.android.internal.app.procstats.ProcessStats.ProcessStateHolder put(java.lang.String str, com.android.internal.app.procstats.ProcessStats.ProcessStateHolder processStateHolder) {
        com.android.internal.app.procstats.ProcessStats.ProcessStateHolder put;
        synchronized (this) {
            this.mProcess.getWindowProcessController().addPackage(str);
            put = this.mPkgList.put(str, processStateHolder);
        }
        return put;
    }

    void clear() {
        synchronized (this) {
            this.mPkgList.clear();
            this.mProcess.getWindowProcessController().clearPackageList();
        }
    }

    int size() {
        int size;
        synchronized (this) {
            size = this.mPkgList.size();
        }
        return size;
    }

    boolean containsKey(java.lang.Object obj) {
        boolean containsKey;
        synchronized (this) {
            containsKey = this.mPkgList.containsKey(obj);
        }
        return containsKey;
    }

    com.android.internal.app.procstats.ProcessStats.ProcessStateHolder get(java.lang.String str) {
        com.android.internal.app.procstats.ProcessStats.ProcessStateHolder processStateHolder;
        synchronized (this) {
            processStateHolder = this.mPkgList.get(str);
        }
        return processStateHolder;
    }

    void forEachPackage(@android.annotation.NonNull java.util.function.Consumer<java.lang.String> consumer) {
        synchronized (this) {
            try {
                int size = this.mPkgList.size();
                for (int i = 0; i < size; i++) {
                    consumer.accept(this.mPkgList.keyAt(i));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void forEachPackage(@android.annotation.NonNull java.util.function.BiConsumer<java.lang.String, com.android.internal.app.procstats.ProcessStats.ProcessStateHolder> biConsumer) {
        synchronized (this) {
            try {
                int size = this.mPkgList.size();
                for (int i = 0; i < size; i++) {
                    biConsumer.accept(this.mPkgList.keyAt(i), this.mPkgList.valueAt(i));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    <R> R searchEachPackage(@android.annotation.NonNull java.util.function.Function<java.lang.String, R> function) {
        synchronized (this) {
            try {
                int size = this.mPkgList.size();
                for (int i = 0; i < size; i++) {
                    R apply = function.apply(this.mPkgList.keyAt(i));
                    if (apply != null) {
                        return apply;
                    }
                }
                return null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void forEachPackageProcessStats(@android.annotation.NonNull java.util.function.Consumer<com.android.internal.app.procstats.ProcessStats.ProcessStateHolder> consumer) {
        synchronized (this) {
            try {
                int size = this.mPkgList.size();
                for (int i = 0; i < size; i++) {
                    consumer.accept(this.mPkgList.valueAt(i));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    android.util.ArrayMap<java.lang.String, com.android.internal.app.procstats.ProcessStats.ProcessStateHolder> getPackageListLocked() {
        return this.mPkgList;
    }

    java.lang.String[] getPackageList() {
        synchronized (this) {
            try {
                int size = this.mPkgList.size();
                if (size == 0) {
                    return null;
                }
                java.lang.String[] strArr = new java.lang.String[size];
                for (int i = 0; i < size; i++) {
                    strArr[i] = this.mPkgList.keyAt(i);
                }
                return strArr;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    java.util.List<android.content.pm.VersionedPackage> getPackageListWithVersionCode() {
        synchronized (this) {
            try {
                int size = this.mPkgList.size();
                if (size == 0) {
                    return null;
                }
                java.util.ArrayList arrayList = new java.util.ArrayList();
                for (int i = 0; i < size; i++) {
                    arrayList.add(new android.content.pm.VersionedPackage(this.mPkgList.keyAt(i), this.mPkgList.valueAt(i).appVersion));
                }
                return arrayList;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        synchronized (this) {
            try {
                printWriter.print(str);
                printWriter.print("packageList={");
                int size = this.mPkgList.size();
                for (int i = 0; i < size; i++) {
                    if (i > 0) {
                        printWriter.print(", ");
                    }
                    printWriter.print(this.mPkgList.keyAt(i));
                }
                printWriter.println("}");
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
