package com.android.server.utils.quota;

/* loaded from: classes2.dex */
class UptcMap<T> {
    private final android.util.SparseArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, T>> mData = new android.util.SparseArrayMap<>();

    interface UptcDataConsumer<D> {
        void accept(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable D d);
    }

    UptcMap() {
    }

    public void add(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable T t) {
        android.util.ArrayMap arrayMap = (android.util.ArrayMap) this.mData.get(i, str);
        if (arrayMap == null) {
            arrayMap = new android.util.ArrayMap();
            this.mData.add(i, str, arrayMap);
        }
        arrayMap.put(str2, t);
    }

    public void clear() {
        this.mData.clear();
    }

    public boolean contains(int i, @android.annotation.NonNull java.lang.String str) {
        return this.mData.contains(i, str);
    }

    public boolean contains(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        android.util.ArrayMap arrayMap = (android.util.ArrayMap) this.mData.get(i, str);
        return arrayMap != null && arrayMap.containsKey(str2);
    }

    public void delete(int i) {
        this.mData.delete(i);
    }

    public void delete(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        android.util.ArrayMap arrayMap = (android.util.ArrayMap) this.mData.get(i, str);
        if (arrayMap != null) {
            arrayMap.remove(str2);
            if (arrayMap.size() == 0) {
                this.mData.delete(i, str);
            }
        }
    }

    public android.util.ArrayMap<java.lang.String, T> delete(int i, @android.annotation.NonNull java.lang.String str) {
        return (android.util.ArrayMap) this.mData.delete(i, str);
    }

    @android.annotation.Nullable
    public android.util.ArrayMap<java.lang.String, T> get(int i, @android.annotation.NonNull java.lang.String str) {
        return (android.util.ArrayMap) this.mData.get(i, str);
    }

    @android.annotation.Nullable
    public T get(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        android.util.ArrayMap arrayMap = (android.util.ArrayMap) this.mData.get(i, str);
        if (arrayMap != null) {
            return (T) arrayMap.get(str2);
        }
        return null;
    }

    @android.annotation.Nullable
    public T getOrCreate(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2, java.util.function.Function<java.lang.Void, T> function) {
        android.util.ArrayMap arrayMap = (android.util.ArrayMap) this.mData.get(i, str);
        if (arrayMap == null || !arrayMap.containsKey(str2)) {
            T apply = function.apply(null);
            add(i, str, str2, apply);
            return apply;
        }
        return (T) arrayMap.get(str2);
    }

    private int getUserIdAtIndex(int i) {
        return this.mData.keyAt(i);
    }

    @android.annotation.NonNull
    private java.lang.String getPackageNameAtIndex(int i, int i2) {
        return (java.lang.String) this.mData.keyAt(i, i2);
    }

    @android.annotation.NonNull
    private java.lang.String getTagAtIndex(int i, int i2, int i3) {
        return (java.lang.String) ((android.util.ArrayMap) this.mData.valueAt(i, i2)).keyAt(i3);
    }

    public int userCount() {
        return this.mData.numMaps();
    }

    public int packageCountForUser(int i) {
        return this.mData.numElementsForKey(i);
    }

    public int tagCountForUserAndPackage(int i, @android.annotation.NonNull java.lang.String str) {
        android.util.ArrayMap arrayMap = (android.util.ArrayMap) this.mData.get(i, str);
        if (arrayMap != null) {
            return arrayMap.size();
        }
        return 0;
    }

    @android.annotation.Nullable
    public T valueAt(int i, int i2, int i3) {
        android.util.ArrayMap arrayMap = (android.util.ArrayMap) this.mData.valueAt(i, i2);
        if (arrayMap != null) {
            return (T) arrayMap.valueAt(i3);
        }
        return null;
    }

    public void forEach(final java.util.function.Consumer<T> consumer) {
        this.mData.forEach(new java.util.function.Consumer() { // from class: com.android.server.utils.quota.UptcMap$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.utils.quota.UptcMap.lambda$forEach$0(consumer, (android.util.ArrayMap) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void lambda$forEach$0(java.util.function.Consumer consumer, android.util.ArrayMap arrayMap) {
        for (int size = arrayMap.size() - 1; size >= 0; size--) {
            consumer.accept(arrayMap.valueAt(size));
        }
    }

    public void forEach(com.android.server.utils.quota.UptcMap.UptcDataConsumer<T> uptcDataConsumer) {
        int userCount = userCount();
        for (int i = 0; i < userCount; i++) {
            int userIdAtIndex = getUserIdAtIndex(i);
            int packageCountForUser = packageCountForUser(userIdAtIndex);
            for (int i2 = 0; i2 < packageCountForUser; i2++) {
                java.lang.String packageNameAtIndex = getPackageNameAtIndex(i, i2);
                int tagCountForUserAndPackage = tagCountForUserAndPackage(userIdAtIndex, packageNameAtIndex);
                for (int i3 = 0; i3 < tagCountForUserAndPackage; i3++) {
                    java.lang.String tagAtIndex = getTagAtIndex(i, i2, i3);
                    uptcDataConsumer.accept(userIdAtIndex, packageNameAtIndex, tagAtIndex, get(userIdAtIndex, packageNameAtIndex, tagAtIndex));
                }
            }
        }
    }
}
