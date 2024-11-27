package com.android.server.pm;

@com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
/* loaded from: classes2.dex */
public final class PendingPackageBroadcasts {
    private final java.lang.Object mLock = new com.android.server.pm.PackageManagerTracedLock();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    final android.util.SparseArray<android.util.ArrayMap<java.lang.String, java.util.ArrayList<java.lang.String>>> mUidMap = new android.util.SparseArray<>(2);

    public boolean hasPackage(int i, @android.annotation.NonNull java.lang.String str) {
        boolean z;
        synchronized (this.mLock) {
            try {
                android.util.ArrayMap<java.lang.String, java.util.ArrayList<java.lang.String>> arrayMap = this.mUidMap.get(i);
                z = arrayMap != null && arrayMap.containsKey(str);
            } finally {
            }
        }
        return z;
    }

    public void put(int i, java.lang.String str, java.util.ArrayList<java.lang.String> arrayList) {
        synchronized (this.mLock) {
            getOrAllocate(i).put(str, arrayList);
        }
    }

    public void addComponent(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        synchronized (this.mLock) {
            try {
                java.util.ArrayList<java.lang.String> orAllocate = getOrAllocate(i, str);
                if (!orAllocate.contains(str2)) {
                    orAllocate.add(str2);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void addComponents(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.util.List<java.lang.String> list) {
        synchronized (this.mLock) {
            try {
                java.util.ArrayList<java.lang.String> orAllocate = getOrAllocate(i, str);
                for (int i2 = 0; i2 < list.size(); i2++) {
                    java.lang.String str2 = list.get(i2);
                    if (!orAllocate.contains(str2)) {
                        orAllocate.add(str2);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void remove(int i, java.lang.String str) {
        synchronized (this.mLock) {
            try {
                android.util.ArrayMap<java.lang.String, java.util.ArrayList<java.lang.String>> arrayMap = this.mUidMap.get(i);
                if (arrayMap != null) {
                    arrayMap.remove(str);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void remove(int i) {
        synchronized (this.mLock) {
            this.mUidMap.remove(i);
        }
    }

    @android.annotation.Nullable
    public android.util.SparseArray<android.util.ArrayMap<java.lang.String, java.util.ArrayList<java.lang.String>>> copiedMap() {
        android.util.SparseArray<android.util.ArrayMap<java.lang.String, java.util.ArrayList<java.lang.String>>> sparseArray;
        synchronized (this.mLock) {
            try {
                sparseArray = new android.util.SparseArray<>();
                for (int i = 0; i < this.mUidMap.size(); i++) {
                    android.util.ArrayMap<java.lang.String, java.util.ArrayList<java.lang.String>> valueAt = this.mUidMap.valueAt(i);
                    android.util.ArrayMap<java.lang.String, java.util.ArrayList<java.lang.String>> arrayMap = new android.util.ArrayMap<>();
                    for (int i2 = 0; i2 < valueAt.size(); i2++) {
                        arrayMap.put(valueAt.keyAt(i2), new java.util.ArrayList<>(valueAt.valueAt(i2)));
                    }
                    sparseArray.put(this.mUidMap.keyAt(i), arrayMap);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return sparseArray;
    }

    public void clear() {
        synchronized (this.mLock) {
            this.mUidMap.clear();
        }
    }

    private android.util.ArrayMap<java.lang.String, java.util.ArrayList<java.lang.String>> getOrAllocate(int i) {
        android.util.ArrayMap<java.lang.String, java.util.ArrayList<java.lang.String>> arrayMap;
        synchronized (this.mLock) {
            try {
                arrayMap = this.mUidMap.get(i);
                if (arrayMap == null) {
                    arrayMap = new android.util.ArrayMap<>();
                    this.mUidMap.put(i, arrayMap);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return arrayMap;
    }

    private java.util.ArrayList<java.lang.String> getOrAllocate(int i, @android.annotation.NonNull java.lang.String str) {
        java.util.ArrayList<java.lang.String> computeIfAbsent;
        synchronized (this.mLock) {
            try {
                android.util.ArrayMap<java.lang.String, java.util.ArrayList<java.lang.String>> arrayMap = this.mUidMap.get(i);
                if (arrayMap == null) {
                    arrayMap = new android.util.ArrayMap<>();
                    this.mUidMap.put(i, arrayMap);
                }
                computeIfAbsent = arrayMap.computeIfAbsent(str, new java.util.function.Function() { // from class: com.android.server.pm.PendingPackageBroadcasts$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final java.lang.Object apply(java.lang.Object obj) {
                        java.util.ArrayList lambda$getOrAllocate$0;
                        lambda$getOrAllocate$0 = com.android.server.pm.PendingPackageBroadcasts.lambda$getOrAllocate$0((java.lang.String) obj);
                        return lambda$getOrAllocate$0;
                    }
                });
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return computeIfAbsent;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.util.ArrayList lambda$getOrAllocate$0(java.lang.String str) {
        return new java.util.ArrayList();
    }
}
