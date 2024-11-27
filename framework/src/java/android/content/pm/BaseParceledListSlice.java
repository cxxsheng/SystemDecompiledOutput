package android.content.pm;

/* loaded from: classes.dex */
abstract class BaseParceledListSlice<T> implements android.os.Parcelable {
    private static final boolean DEBUG = false;
    private static final int MAX_IPC_SIZE = android.os.IBinder.getSuggestedMaxIpcSizeBytes();
    private static final java.lang.String TAG = "ParceledListSlice";
    private java.util.List<T> mList;
    private int mInlineCountLimit = Integer.MAX_VALUE;
    private boolean mHasBeenParceled = false;

    protected abstract android.os.Parcelable.Creator<?> readParcelableCreator(android.os.Parcel parcel, java.lang.ClassLoader classLoader);

    protected abstract void writeElement(T t, android.os.Parcel parcel, int i);

    protected abstract void writeParcelableCreator(T t, android.os.Parcel parcel);

    public BaseParceledListSlice(java.util.List<T> list) {
        this.mList = list;
    }

    BaseParceledListSlice(android.os.Parcel parcel, java.lang.ClassLoader classLoader) {
        int readInt = parcel.readInt();
        this.mList = new java.util.ArrayList(readInt);
        if (readInt <= 0) {
            return;
        }
        android.os.Parcelable.Creator<?> readParcelableCreator = readParcelableCreator(parcel, classLoader);
        java.lang.Class<?> cls = null;
        int i = 0;
        while (i < readInt && parcel.readInt() != 0) {
            cls = readVerifyAndAddElement(readParcelableCreator, parcel, classLoader, cls);
            i++;
        }
        if (i >= readInt) {
            return;
        }
        android.os.IBinder readStrongBinder = parcel.readStrongBinder();
        while (i < readInt) {
            android.os.Parcel obtain = android.os.Parcel.obtain();
            android.os.Parcel obtain2 = android.os.Parcel.obtain();
            obtain.writeInt(i);
            try {
                try {
                    readStrongBinder.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    while (i < readInt && obtain2.readInt() != 0) {
                        cls = readVerifyAndAddElement(readParcelableCreator, obtain2, classLoader, cls);
                        i++;
                    }
                } catch (android.os.RemoteException e) {
                    throw new android.os.BadParcelableException("Failure retrieving array; only received " + i + " of " + readInt, e);
                }
            } finally {
                obtain2.recycle();
                obtain.recycle();
            }
        }
    }

    private java.lang.Class<?> readVerifyAndAddElement(android.os.Parcelable.Creator<?> creator, android.os.Parcel parcel, java.lang.ClassLoader classLoader, java.lang.Class<?> cls) {
        T readCreator = readCreator(creator, parcel, classLoader);
        if (cls == null) {
            cls = readCreator.getClass();
        } else {
            verifySameType(cls, readCreator.getClass());
        }
        this.mList.add(readCreator);
        return cls;
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

    /* JADX WARN: Code restructure failed: missing block: B:14:0x004e, code lost:
    
        r8.writeInt(0);
        r8.writeStrongBinder(new android.content.pm.BaseParceledListSlice.AnonymousClass1(r7));
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0059, code lost:
    
        return;
     */
    @Override // android.os.Parcelable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void writeToParcel(android.os.Parcel parcel, final int i) {
        if (this.mHasBeenParceled) {
            throw new java.lang.IllegalStateException("Can't Parcel a ParceledListSlice more than once");
        }
        this.mHasBeenParceled = true;
        final int size = this.mList.size();
        parcel.writeInt(size);
        if (size > 0) {
            final java.lang.Class<?> cls = this.mList.get(0).getClass();
            writeParcelableCreator(this.mList.get(0), parcel);
            int i2 = 0;
            while (i2 < size && i2 < this.mInlineCountLimit && parcel.dataSize() < MAX_IPC_SIZE) {
                parcel.writeInt(1);
                T t = this.mList.get(i2);
                verifySameType(cls, t.getClass());
                writeElement(t, parcel, i);
                i2++;
            }
        }
    }
}
