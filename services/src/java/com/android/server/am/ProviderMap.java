package com.android.server.am;

/* loaded from: classes.dex */
public final class ProviderMap {
    private static final boolean DBG = false;
    private static final java.lang.String TAG = "ProviderMap";
    private final com.android.server.am.ActivityManagerService mAm;
    private final java.util.HashMap<java.lang.String, com.android.server.am.ContentProviderRecord> mSingletonByName = new java.util.HashMap<>();
    private final java.util.HashMap<android.content.ComponentName, com.android.server.am.ContentProviderRecord> mSingletonByClass = new java.util.HashMap<>();
    private final android.util.SparseArray<java.util.HashMap<java.lang.String, com.android.server.am.ContentProviderRecord>> mProvidersByNamePerUser = new android.util.SparseArray<>();
    private final android.util.SparseArray<java.util.HashMap<android.content.ComponentName, com.android.server.am.ContentProviderRecord>> mProvidersByClassPerUser = new android.util.SparseArray<>();

    ProviderMap(com.android.server.am.ActivityManagerService activityManagerService) {
        this.mAm = activityManagerService;
    }

    com.android.server.am.ContentProviderRecord getProviderByName(java.lang.String str) {
        return getProviderByName(str, -1);
    }

    com.android.server.am.ContentProviderRecord getProviderByName(java.lang.String str, int i) {
        com.android.server.am.ContentProviderRecord contentProviderRecord = this.mSingletonByName.get(str);
        if (contentProviderRecord != null) {
            return contentProviderRecord;
        }
        return getProvidersByName(i).get(str);
    }

    com.android.server.am.ContentProviderRecord getProviderByClass(android.content.ComponentName componentName) {
        return getProviderByClass(componentName, -1);
    }

    com.android.server.am.ContentProviderRecord getProviderByClass(android.content.ComponentName componentName, int i) {
        com.android.server.am.ContentProviderRecord contentProviderRecord = this.mSingletonByClass.get(componentName);
        if (contentProviderRecord != null) {
            return contentProviderRecord;
        }
        return getProvidersByClass(i).get(componentName);
    }

    void putProviderByName(java.lang.String str, com.android.server.am.ContentProviderRecord contentProviderRecord) {
        if (contentProviderRecord.singleton) {
            this.mSingletonByName.put(str, contentProviderRecord);
        } else {
            getProvidersByName(android.os.UserHandle.getUserId(contentProviderRecord.appInfo.uid)).put(str, contentProviderRecord);
        }
    }

    void putProviderByClass(android.content.ComponentName componentName, com.android.server.am.ContentProviderRecord contentProviderRecord) {
        if (contentProviderRecord.singleton) {
            this.mSingletonByClass.put(componentName, contentProviderRecord);
        } else {
            getProvidersByClass(android.os.UserHandle.getUserId(contentProviderRecord.appInfo.uid)).put(componentName, contentProviderRecord);
        }
    }

    void removeProviderByName(java.lang.String str, int i) {
        if (this.mSingletonByName.containsKey(str)) {
            this.mSingletonByName.remove(str);
            return;
        }
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("Bad user " + i);
        }
        java.util.HashMap<java.lang.String, com.android.server.am.ContentProviderRecord> providersByName = getProvidersByName(i);
        providersByName.remove(str);
        if (providersByName.size() == 0) {
            this.mProvidersByNamePerUser.remove(i);
        }
    }

    void removeProviderByClass(android.content.ComponentName componentName, int i) {
        if (this.mSingletonByClass.containsKey(componentName)) {
            this.mSingletonByClass.remove(componentName);
            return;
        }
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("Bad user " + i);
        }
        java.util.HashMap<android.content.ComponentName, com.android.server.am.ContentProviderRecord> providersByClass = getProvidersByClass(i);
        providersByClass.remove(componentName);
        if (providersByClass.size() == 0) {
            this.mProvidersByClassPerUser.remove(i);
        }
    }

    private java.util.HashMap<java.lang.String, com.android.server.am.ContentProviderRecord> getProvidersByName(int i) {
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("Bad user " + i);
        }
        java.util.HashMap<java.lang.String, com.android.server.am.ContentProviderRecord> hashMap = this.mProvidersByNamePerUser.get(i);
        if (hashMap == null) {
            java.util.HashMap<java.lang.String, com.android.server.am.ContentProviderRecord> hashMap2 = new java.util.HashMap<>();
            this.mProvidersByNamePerUser.put(i, hashMap2);
            return hashMap2;
        }
        return hashMap;
    }

    java.util.HashMap<android.content.ComponentName, com.android.server.am.ContentProviderRecord> getProvidersByClass(int i) {
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("Bad user " + i);
        }
        java.util.HashMap<android.content.ComponentName, com.android.server.am.ContentProviderRecord> hashMap = this.mProvidersByClassPerUser.get(i);
        if (hashMap == null) {
            java.util.HashMap<android.content.ComponentName, com.android.server.am.ContentProviderRecord> hashMap2 = new java.util.HashMap<>();
            this.mProvidersByClassPerUser.put(i, hashMap2);
            return hashMap2;
        }
        return hashMap;
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x004d, code lost:
    
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean collectPackageProvidersLocked(java.lang.String str, java.util.Set<java.lang.String> set, boolean z, boolean z2, java.util.HashMap<android.content.ComponentName, com.android.server.am.ContentProviderRecord> hashMap, java.util.ArrayList<com.android.server.am.ContentProviderRecord> arrayList) {
        boolean z3 = false;
        for (com.android.server.am.ContentProviderRecord contentProviderRecord : hashMap.values()) {
            if ((str == null || (contentProviderRecord.info.packageName.equals(str) && (set == null || set.contains(contentProviderRecord.name.getClassName())))) && (contentProviderRecord.proc == null || z2 || !contentProviderRecord.proc.isPersistent())) {
                arrayList.add(contentProviderRecord);
                z3 = true;
            }
        }
        return z3;
    }

    boolean collectPackageProvidersLocked(java.lang.String str, java.util.Set<java.lang.String> set, boolean z, boolean z2, int i, java.util.ArrayList<com.android.server.am.ContentProviderRecord> arrayList) {
        boolean collectPackageProvidersLocked;
        if (i != -1 && i != 0) {
            collectPackageProvidersLocked = false;
        } else {
            collectPackageProvidersLocked = collectPackageProvidersLocked(str, set, z, z2, this.mSingletonByClass, arrayList);
        }
        if (!z && collectPackageProvidersLocked) {
            return true;
        }
        if (i == -1) {
            for (int i2 = 0; i2 < this.mProvidersByClassPerUser.size(); i2++) {
                if (collectPackageProvidersLocked(str, set, z, z2, this.mProvidersByClassPerUser.valueAt(i2), arrayList)) {
                    if (!z) {
                        return true;
                    }
                    collectPackageProvidersLocked = true;
                }
            }
            return collectPackageProvidersLocked;
        }
        java.util.HashMap<android.content.ComponentName, com.android.server.am.ContentProviderRecord> providersByClass = getProvidersByClass(i);
        if (providersByClass != null) {
            return collectPackageProvidersLocked | collectPackageProvidersLocked(str, set, z, z2, providersByClass, arrayList);
        }
        return collectPackageProvidersLocked;
    }

    private boolean dumpProvidersByClassLocked(java.io.PrintWriter printWriter, boolean z, java.lang.String str, java.lang.String str2, boolean z2, java.util.HashMap<android.content.ComponentName, com.android.server.am.ContentProviderRecord> hashMap) {
        java.util.Iterator<java.util.Map.Entry<android.content.ComponentName, com.android.server.am.ContentProviderRecord>> it = hashMap.entrySet().iterator();
        boolean z3 = false;
        while (it.hasNext()) {
            com.android.server.am.ContentProviderRecord value = it.next().getValue();
            if (str == null || str.equals(value.appInfo.packageName)) {
                if (z2) {
                    printWriter.println("");
                    z2 = false;
                }
                if (str2 != null) {
                    printWriter.println(str2);
                    str2 = null;
                }
                printWriter.print("  * ");
                printWriter.println(value);
                value.dump(printWriter, "    ", z);
                z3 = true;
            }
        }
        return z3;
    }

    private boolean dumpProvidersByNameLocked(java.io.PrintWriter printWriter, java.lang.String str, java.lang.String str2, boolean z, java.util.HashMap<java.lang.String, com.android.server.am.ContentProviderRecord> hashMap) {
        boolean z2 = false;
        for (java.util.Map.Entry<java.lang.String, com.android.server.am.ContentProviderRecord> entry : hashMap.entrySet()) {
            com.android.server.am.ContentProviderRecord value = entry.getValue();
            if (str == null || str.equals(value.appInfo.packageName)) {
                if (z) {
                    printWriter.println("");
                    z = false;
                }
                if (str2 != null) {
                    printWriter.println(str2);
                    str2 = null;
                }
                printWriter.print("  ");
                printWriter.print(entry.getKey());
                printWriter.print(": ");
                printWriter.println(value.toShortString());
                z2 = true;
            }
        }
        return z2;
    }

    boolean dumpProvidersLocked(java.io.PrintWriter printWriter, boolean z, java.lang.String str) {
        boolean dumpProvidersByClassLocked = this.mSingletonByClass.size() > 0 ? false | dumpProvidersByClassLocked(printWriter, z, str, "  Published single-user content providers (by class):", false, this.mSingletonByClass) : false;
        for (int i = 0; i < this.mProvidersByClassPerUser.size(); i++) {
            dumpProvidersByClassLocked |= dumpProvidersByClassLocked(printWriter, z, str, "  Published user " + this.mProvidersByClassPerUser.keyAt(i) + " content providers (by class):", dumpProvidersByClassLocked, this.mProvidersByClassPerUser.valueAt(i));
        }
        if (z) {
            dumpProvidersByClassLocked = dumpProvidersByNameLocked(printWriter, str, "  Single-user authority to provider mappings:", dumpProvidersByClassLocked, this.mSingletonByName) | dumpProvidersByClassLocked;
            for (int i2 = 0; i2 < this.mProvidersByNamePerUser.size(); i2++) {
                dumpProvidersByClassLocked |= dumpProvidersByNameLocked(printWriter, str, "  User " + this.mProvidersByNamePerUser.keyAt(i2) + " authority to provider mappings:", dumpProvidersByClassLocked, this.mProvidersByNamePerUser.valueAt(i2));
            }
        }
        return dumpProvidersByClassLocked;
    }

    private java.util.ArrayList<com.android.server.am.ContentProviderRecord> getProvidersForName(java.lang.String str) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList<com.android.server.am.ContentProviderRecord> arrayList2 = new java.util.ArrayList<>();
        java.util.function.Predicate filterRecord = com.android.internal.util.DumpUtils.filterRecord(str);
        com.android.server.am.ActivityManagerService activityManagerService = this.mAm;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                arrayList.addAll(this.mSingletonByClass.values());
                for (int i = 0; i < this.mProvidersByClassPerUser.size(); i++) {
                    arrayList.addAll(this.mProvidersByClassPerUser.valueAt(i).values());
                }
                com.android.internal.util.CollectionUtils.addIf(arrayList, arrayList2, filterRecord);
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
        arrayList2.sort(java.util.Comparator.comparing(new java.util.function.Function() { // from class: com.android.server.am.ProviderMap$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return ((com.android.server.am.ContentProviderRecord) obj).getComponentName();
            }
        }));
        return arrayList2;
    }

    protected boolean dumpProvider(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String str, java.lang.String[] strArr, int i, boolean z) {
        try {
            int i2 = 0;
            this.mAm.mOomAdjuster.mCachedAppOptimizer.enableFreezer(false);
            java.util.ArrayList<com.android.server.am.ContentProviderRecord> providersForName = getProvidersForName(str);
            if (providersForName.size() <= 0) {
                this.mAm.mOomAdjuster.mCachedAppOptimizer.enableFreezer(true);
                return false;
            }
            boolean z2 = false;
            while (i2 < providersForName.size()) {
                if (z2) {
                    printWriter.println();
                }
                dumpProvider("", fileDescriptor, printWriter, providersForName.get(i2), strArr, z);
                i2++;
                z2 = true;
            }
            this.mAm.mOomAdjuster.mCachedAppOptimizer.enableFreezer(true);
            return true;
        } catch (java.lang.Throwable th) {
            this.mAm.mOomAdjuster.mCachedAppOptimizer.enableFreezer(true);
            throw th;
        }
    }

    private void dumpProvider(java.lang.String str, java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, com.android.server.am.ContentProviderRecord contentProviderRecord, java.lang.String[] strArr, boolean z) {
        android.app.IApplicationThread thread = contentProviderRecord.proc != null ? contentProviderRecord.proc.getThread() : null;
        for (java.lang.String str2 : strArr) {
            if (!z && str2.contains("--proto")) {
                if (thread != null) {
                    dumpToTransferPipe(null, fileDescriptor, printWriter, contentProviderRecord, thread, strArr);
                    return;
                }
                return;
            }
        }
        java.lang.String str3 = str + "  ";
        com.android.server.am.ActivityManagerService activityManagerService = this.mAm;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                printWriter.print(str);
                printWriter.print("PROVIDER ");
                printWriter.print(contentProviderRecord);
                printWriter.print(" pid=");
                if (contentProviderRecord.proc != null) {
                    printWriter.println(contentProviderRecord.proc.getPid());
                } else {
                    printWriter.println("(not running)");
                }
                if (z) {
                    contentProviderRecord.dump(printWriter, str3, true);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
        if (thread != null) {
            printWriter.println("    Client:");
            printWriter.flush();
            dumpToTransferPipe("      ", fileDescriptor, printWriter, contentProviderRecord, thread, strArr);
        }
    }

    protected boolean dumpProviderProto(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String str, java.lang.String[] strArr) {
        android.app.IApplicationThread thread;
        java.lang.String[] strArr2 = (java.lang.String[]) java.util.Arrays.copyOf(strArr, strArr.length + 1);
        strArr2[strArr.length] = "--proto";
        java.util.ArrayList<com.android.server.am.ContentProviderRecord> providersForName = getProvidersForName(str);
        if (providersForName.size() <= 0) {
            return false;
        }
        for (int i = 0; i < providersForName.size(); i++) {
            com.android.server.am.ContentProviderRecord contentProviderRecord = providersForName.get(i);
            if (contentProviderRecord.proc != null && (thread = contentProviderRecord.proc.getThread()) != null) {
                dumpToTransferPipe(null, fileDescriptor, printWriter, contentProviderRecord, thread, strArr2);
                return true;
            }
        }
        return false;
    }

    private void dumpToTransferPipe(java.lang.String str, java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, com.android.server.am.ContentProviderRecord contentProviderRecord, android.app.IApplicationThread iApplicationThread, java.lang.String[] strArr) {
        try {
            com.android.internal.os.TransferPipe transferPipe = new com.android.internal.os.TransferPipe();
            try {
                iApplicationThread.dumpProvider(transferPipe.getWriteFd(), contentProviderRecord.provider.asBinder(), strArr);
                transferPipe.setBufferPrefix(str);
                transferPipe.go(fileDescriptor, 2000L);
            } finally {
                transferPipe.kill();
            }
        } catch (android.os.RemoteException e) {
            printWriter.println("      Got a RemoteException while dumping the service");
        } catch (java.io.IOException e2) {
            printWriter.println("      Failure while dumping the provider: " + e2);
        }
    }
}
