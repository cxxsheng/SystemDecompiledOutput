package com.android.server.wallpaper;

/* loaded from: classes.dex */
public class LocalColorRepository {
    android.util.ArrayMap<android.os.IBinder, android.util.SparseArray<android.util.ArraySet<android.graphics.RectF>>> mLocalColorAreas = new android.util.ArrayMap<>();
    android.os.RemoteCallbackList<android.app.ILocalWallpaperColorConsumer> mCallbacks = new android.os.RemoteCallbackList<>();

    public void addAreas(final android.app.ILocalWallpaperColorConsumer iLocalWallpaperColorConsumer, java.util.List<android.graphics.RectF> list, int i) {
        android.util.ArraySet<android.graphics.RectF> arraySet;
        android.os.IBinder asBinder = iLocalWallpaperColorConsumer.asBinder();
        android.util.SparseArray<android.util.ArraySet<android.graphics.RectF>> sparseArray = this.mLocalColorAreas.get(asBinder);
        if (sparseArray == null) {
            try {
                iLocalWallpaperColorConsumer.asBinder().linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: com.android.server.wallpaper.LocalColorRepository$$ExternalSyntheticLambda1
                    @Override // android.os.IBinder.DeathRecipient
                    public final void binderDied() {
                        com.android.server.wallpaper.LocalColorRepository.this.lambda$addAreas$0(iLocalWallpaperColorConsumer);
                    }
                }, 0);
            } catch (android.os.RemoteException e) {
                e.printStackTrace();
            }
            sparseArray = new android.util.SparseArray<>();
            this.mLocalColorAreas.put(asBinder, sparseArray);
            arraySet = null;
        } else {
            arraySet = sparseArray.get(i);
        }
        if (arraySet == null) {
            arraySet = new android.util.ArraySet<>(list);
            sparseArray.put(i, arraySet);
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            arraySet.add(list.get(i2));
        }
        this.mCallbacks.register(iLocalWallpaperColorConsumer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addAreas$0(android.app.ILocalWallpaperColorConsumer iLocalWallpaperColorConsumer) {
        this.mLocalColorAreas.remove(iLocalWallpaperColorConsumer.asBinder());
    }

    public java.util.List<android.graphics.RectF> removeAreas(android.app.ILocalWallpaperColorConsumer iLocalWallpaperColorConsumer, java.util.List<android.graphics.RectF> list, int i) {
        android.os.IBinder asBinder = iLocalWallpaperColorConsumer.asBinder();
        android.util.SparseArray<android.util.ArraySet<android.graphics.RectF>> sparseArray = this.mLocalColorAreas.get(asBinder);
        if (sparseArray != null) {
            android.util.ArraySet<android.graphics.RectF> arraySet = sparseArray.get(i);
            if (arraySet == null) {
                this.mCallbacks.unregister(iLocalWallpaperColorConsumer);
            } else {
                for (int i2 = 0; i2 < list.size(); i2++) {
                    arraySet.remove(list.get(i2));
                }
                if (arraySet.size() == 0) {
                    sparseArray.remove(i);
                }
            }
            if (sparseArray.size() == 0) {
                this.mLocalColorAreas.remove(asBinder);
                this.mCallbacks.unregister(iLocalWallpaperColorConsumer);
            }
        } else {
            this.mCallbacks.unregister(iLocalWallpaperColorConsumer);
        }
        android.util.ArraySet arraySet2 = new android.util.ArraySet(list);
        for (int i3 = 0; i3 < this.mLocalColorAreas.size(); i3++) {
            for (int i4 = 0; i4 < this.mLocalColorAreas.valueAt(i3).size(); i4++) {
                for (int i5 = 0; i5 < this.mLocalColorAreas.valueAt(i3).valueAt(i4).size(); i5++) {
                    arraySet2.remove(this.mLocalColorAreas.valueAt(i3).valueAt(i4).valueAt(i5));
                }
            }
        }
        return new java.util.ArrayList(arraySet2);
    }

    public java.util.List<android.graphics.RectF> getAreasByDisplayId(int i) {
        android.util.ArraySet<android.graphics.RectF> arraySet;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i2 = 0; i2 < this.mLocalColorAreas.size(); i2++) {
            android.util.SparseArray<android.util.ArraySet<android.graphics.RectF>> valueAt = this.mLocalColorAreas.valueAt(i2);
            if (valueAt != null && (arraySet = valueAt.get(i)) != null) {
                for (int i3 = 0; i3 < arraySet.size(); i3++) {
                    arrayList.add(arraySet.valueAt(i3));
                }
            }
        }
        return arrayList;
    }

    public void forEachCallback(final java.util.function.Consumer<android.app.ILocalWallpaperColorConsumer> consumer, final android.graphics.RectF rectF, final int i) {
        this.mCallbacks.broadcast(new java.util.function.Consumer() { // from class: com.android.server.wallpaper.LocalColorRepository$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wallpaper.LocalColorRepository.this.lambda$forEachCallback$1(i, rectF, consumer, (android.app.ILocalWallpaperColorConsumer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$forEachCallback$1(int i, android.graphics.RectF rectF, java.util.function.Consumer consumer, android.app.ILocalWallpaperColorConsumer iLocalWallpaperColorConsumer) {
        android.util.ArraySet<android.graphics.RectF> arraySet;
        android.util.SparseArray<android.util.ArraySet<android.graphics.RectF>> sparseArray = this.mLocalColorAreas.get(iLocalWallpaperColorConsumer.asBinder());
        if (sparseArray != null && (arraySet = sparseArray.get(i)) != null && arraySet.contains(rectF)) {
            consumer.accept(iLocalWallpaperColorConsumer);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected boolean isCallbackAvailable(android.app.ILocalWallpaperColorConsumer iLocalWallpaperColorConsumer) {
        return this.mLocalColorAreas.get(iLocalWallpaperColorConsumer.asBinder()) != null;
    }
}
