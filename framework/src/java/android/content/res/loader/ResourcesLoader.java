package android.content.res.loader;

/* loaded from: classes.dex */
public class ResourcesLoader {
    private android.content.res.ApkAssets[] mApkAssets;
    private android.content.res.loader.ResourcesProvider[] mPreviousProviders;
    private android.content.res.loader.ResourcesProvider[] mProviders;
    private final java.lang.Object mLock = new java.lang.Object();
    private android.util.ArrayMap<java.lang.ref.WeakReference<java.lang.Object>, android.content.res.loader.ResourcesLoader.UpdateCallbacks> mChangeCallbacks = new android.util.ArrayMap<>();

    public interface UpdateCallbacks {
        void onLoaderUpdated(android.content.res.loader.ResourcesLoader resourcesLoader);
    }

    public java.util.List<android.content.res.loader.ResourcesProvider> getProviders() {
        java.util.List<android.content.res.loader.ResourcesProvider> emptyList;
        synchronized (this.mLock) {
            emptyList = this.mProviders == null ? java.util.Collections.emptyList() : java.util.Arrays.asList(this.mProviders);
        }
        return emptyList;
    }

    public void addProvider(android.content.res.loader.ResourcesProvider resourcesProvider) {
        synchronized (this.mLock) {
            this.mProviders = (android.content.res.loader.ResourcesProvider[]) com.android.internal.util.ArrayUtils.appendElement(android.content.res.loader.ResourcesProvider.class, this.mProviders, resourcesProvider);
            notifyProvidersChangedLocked();
        }
    }

    public void removeProvider(android.content.res.loader.ResourcesProvider resourcesProvider) {
        synchronized (this.mLock) {
            this.mProviders = (android.content.res.loader.ResourcesProvider[]) com.android.internal.util.ArrayUtils.removeElement(android.content.res.loader.ResourcesProvider.class, this.mProviders, resourcesProvider);
            notifyProvidersChangedLocked();
        }
    }

    public void setProviders(java.util.List<android.content.res.loader.ResourcesProvider> list) {
        synchronized (this.mLock) {
            this.mProviders = (android.content.res.loader.ResourcesProvider[]) list.toArray(new android.content.res.loader.ResourcesProvider[0]);
            notifyProvidersChangedLocked();
        }
    }

    public void clearProviders() {
        synchronized (this.mLock) {
            this.mProviders = null;
            notifyProvidersChangedLocked();
        }
    }

    public java.util.List<android.content.res.ApkAssets> getApkAssets() {
        synchronized (this.mLock) {
            if (this.mApkAssets == null) {
                return java.util.Collections.emptyList();
            }
            return java.util.Arrays.asList(this.mApkAssets);
        }
    }

    public void registerOnProvidersChangedCallback(java.lang.Object obj, android.content.res.loader.ResourcesLoader.UpdateCallbacks updateCallbacks) {
        synchronized (this.mLock) {
            this.mChangeCallbacks.put(new java.lang.ref.WeakReference<>(obj), updateCallbacks);
        }
    }

    public void unregisterOnProvidersChangedCallback(java.lang.Object obj) {
        synchronized (this.mLock) {
            int size = this.mChangeCallbacks.size();
            for (int i = 0; i < size; i++) {
                if (obj == this.mChangeCallbacks.keyAt(i).get()) {
                    this.mChangeCallbacks.removeAt(i);
                    return;
                }
            }
        }
    }

    private static boolean arrayEquals(android.content.res.loader.ResourcesProvider[] resourcesProviderArr, android.content.res.loader.ResourcesProvider[] resourcesProviderArr2) {
        if (resourcesProviderArr == resourcesProviderArr2) {
            return true;
        }
        if (resourcesProviderArr == null || resourcesProviderArr2 == null || resourcesProviderArr.length != resourcesProviderArr2.length) {
            return false;
        }
        int length = resourcesProviderArr.length;
        for (int i = 0; i < length; i++) {
            if (resourcesProviderArr[i] != resourcesProviderArr2[i]) {
                return false;
            }
        }
        return true;
    }

    private void notifyProvidersChangedLocked() {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        if (arrayEquals(this.mPreviousProviders, this.mProviders)) {
            return;
        }
        if (this.mProviders == null || this.mProviders.length == 0) {
            this.mApkAssets = null;
        } else {
            this.mApkAssets = new android.content.res.ApkAssets[this.mProviders.length];
            int length = this.mProviders.length;
            for (int i = 0; i < length; i++) {
                this.mProviders[i].incrementRefCount();
                this.mApkAssets[i] = this.mProviders[i].getApkAssets();
            }
        }
        if (this.mPreviousProviders != null) {
            for (android.content.res.loader.ResourcesProvider resourcesProvider : this.mPreviousProviders) {
                resourcesProvider.decrementRefCount();
            }
        }
        this.mPreviousProviders = this.mProviders;
        for (int size = this.mChangeCallbacks.size() - 1; size >= 0; size--) {
            if (this.mChangeCallbacks.keyAt(size).refersTo(null)) {
                this.mChangeCallbacks.removeAt(size);
            } else {
                arraySet.add(this.mChangeCallbacks.valueAt(size));
            }
        }
        int size2 = arraySet.size();
        for (int i2 = 0; i2 < size2; i2++) {
            ((android.content.res.loader.ResourcesLoader.UpdateCallbacks) arraySet.valueAt(i2)).onLoaderUpdated(this);
        }
    }
}
