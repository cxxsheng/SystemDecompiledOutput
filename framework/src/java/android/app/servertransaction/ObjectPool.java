package android.app.servertransaction;

/* loaded from: classes.dex */
class ObjectPool {
    private static final int MAX_POOL_SIZE = 50;
    private static final java.lang.Object sPoolSync = new java.lang.Object();
    private static final java.util.Map<java.lang.Class, java.util.ArrayList<? extends android.app.servertransaction.ObjectPoolItem>> sPoolMap = new java.util.HashMap();

    ObjectPool() {
    }

    public static <T extends android.app.servertransaction.ObjectPoolItem> T obtain(java.lang.Class<T> cls) {
        synchronized (sPoolSync) {
            java.util.ArrayList<? extends android.app.servertransaction.ObjectPoolItem> arrayList = sPoolMap.get(cls);
            if (arrayList == null || arrayList.isEmpty()) {
                return null;
            }
            return (T) arrayList.remove(arrayList.size() - 1);
        }
    }

    public static <T extends android.app.servertransaction.ObjectPoolItem> void recycle(T t) {
        synchronized (sPoolSync) {
            java.util.ArrayList<? extends android.app.servertransaction.ObjectPoolItem> arrayList = sPoolMap.get(t.getClass());
            if (arrayList == null) {
                arrayList = new java.util.ArrayList<>();
                sPoolMap.put(t.getClass(), arrayList);
            }
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                if (arrayList.get(i) == t) {
                    throw new java.lang.IllegalStateException("Trying to recycle already recycled item");
                }
            }
            if (size < 50) {
                arrayList.add(t);
            }
        }
    }
}
