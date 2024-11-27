package com.android.modules.utils;

/* loaded from: classes5.dex */
abstract class BaseParceledListSlice<T> implements android.os.Parcelable {
    private int mInlineCountLimit = Integer.MAX_VALUE;
    private final java.util.List<T> mList;
    private static java.lang.String TAG = "ParceledListSlice";
    private static boolean DEBUG = false;
    private static final int MAX_IPC_SIZE = android.os.IBinder.getSuggestedMaxIpcSizeBytes();

    protected abstract android.os.Parcelable.Creator<?> readParcelableCreator(android.os.Parcel parcel, java.lang.ClassLoader classLoader);

    protected abstract void writeElement(T t, android.os.Parcel parcel, int i);

    protected abstract void writeParcelableCreator(T t, android.os.Parcel parcel);

    public BaseParceledListSlice(java.util.List<T> list) {
        this.mList = list;
    }

    BaseParceledListSlice(android.os.Parcel parcel, java.lang.ClassLoader classLoader) {
        int readInt = parcel.readInt();
        this.mList = new java.util.ArrayList(readInt);
        if (DEBUG) {
            android.util.Log.d(TAG, "Retrieving " + readInt + " items");
        }
        if (readInt <= 0) {
            return;
        }
        android.os.Parcelable.Creator<?> readParcelableCreator = readParcelableCreator(parcel, classLoader);
        java.lang.Class<?> cls = null;
        int i = 0;
        while (i < readInt && parcel.readInt() != 0) {
            T readCreator = readCreator(readParcelableCreator, parcel, classLoader);
            if (cls == null) {
                cls = readCreator.getClass();
            } else {
                verifySameType(cls, readCreator.getClass());
            }
            this.mList.add(readCreator);
            if (DEBUG) {
                android.util.Log.d(TAG, "Read inline #" + i + ": " + this.mList.get(this.mList.size() - 1));
            }
            i++;
        }
        if (i >= readInt) {
            return;
        }
        android.os.IBinder readStrongBinder = parcel.readStrongBinder();
        while (i < readInt) {
            if (DEBUG) {
                android.util.Log.d(TAG, "Reading more @" + i + " of " + readInt + ": retriever=" + readStrongBinder);
            }
            android.os.Parcel obtain = android.os.Parcel.obtain();
            android.os.Parcel obtain2 = android.os.Parcel.obtain();
            obtain.writeInt(i);
            try {
                readStrongBinder.transact(1, obtain, obtain2, 0);
                while (i < readInt && obtain2.readInt() != 0) {
                    T readCreator2 = readCreator(readParcelableCreator, obtain2, classLoader);
                    verifySameType(cls, readCreator2.getClass());
                    this.mList.add(readCreator2);
                    if (DEBUG) {
                        android.util.Log.d(TAG, "Read extra #" + i + ": " + this.mList.get(this.mList.size() - 1));
                    }
                    i++;
                }
                obtain2.recycle();
                obtain.recycle();
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "Failure retrieving array; only received " + i + " of " + readInt, e);
                return;
            }
        }
    }

    private T readCreator(android.os.Parcelable.Creator<?> creator, android.os.Parcel parcel, java.lang.ClassLoader classLoader) {
        if (creator instanceof android.os.Parcelable.ClassLoaderCreator) {
            return (T) ((android.os.Parcelable.ClassLoaderCreator) creator).createFromParcel(parcel, classLoader);
        }
        return (T) creator.createFromParcel(parcel);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void verifySameType(java.lang.Class<?> cls, java.lang.Class<?> cls2) {
        if (!cls2.equals(cls)) {
            throw new java.lang.IllegalArgumentException("Can't unparcel type " + (cls2 == null ? null : cls2.getName()) + " in list of type " + (cls != null ? cls.getName() : null));
        }
    }

    public java.util.List<T> getList() {
        return this.mList;
    }

    public void setInlineCountLimit(int i) {
        this.mInlineCountLimit = i;
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0096, code lost:
    
        r8.writeInt(0);
        r2 = new com.android.modules.utils.BaseParceledListSlice.AnonymousClass1(r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x00a0, code lost:
    
        if (com.android.modules.utils.BaseParceledListSlice.DEBUG == false) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x00a2, code lost:
    
        android.util.Log.d(com.android.modules.utils.BaseParceledListSlice.TAG, "Breaking @" + r3 + " of " + r0 + ": retriever=" + r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x00ce, code lost:
    
        r8.writeStrongBinder(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x00d1, code lost:
    
        return;
     */
    @Override // android.os.Parcelable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void writeToParcel(android.os.Parcel parcel, final int i) {
        final int size = this.mList.size();
        parcel.writeInt(size);
        if (DEBUG) {
            android.util.Log.d(TAG, "Writing " + size + " items");
        }
        if (size > 0) {
            final java.lang.Class<?> cls = this.mList.get(0).getClass();
            writeParcelableCreator(this.mList.get(0), parcel);
            int i2 = 0;
            while (i2 < size && i2 < this.mInlineCountLimit && parcel.dataSize() < MAX_IPC_SIZE) {
                parcel.writeInt(1);
                T t = this.mList.get(i2);
                verifySameType(cls, t.getClass());
                writeElement(t, parcel, i);
                if (DEBUG) {
                    android.util.Log.d(TAG, "Wrote inline #" + i2 + ": " + this.mList.get(i2));
                }
                i2++;
            }
        }
    }
}
