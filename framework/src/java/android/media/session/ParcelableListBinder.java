package android.media.session;

/* loaded from: classes2.dex */
public class ParcelableListBinder<T extends android.os.Parcelable> extends android.os.Binder {
    private static final int END_OF_PARCEL = 0;
    private static final int ITEM_CONTINUED = 1;
    private static final int SUGGESTED_MAX_IPC_SIZE = android.os.IBinder.getSuggestedMaxIpcSizeBytes();
    private boolean mConsumed;
    private final java.util.function.Consumer<java.util.List<T>> mConsumer;
    private int mCount;
    private final java.lang.Class<T> mListElementsClass;
    private final java.lang.Object mLock = new java.lang.Object();
    private final java.util.List<T> mList = new java.util.ArrayList();

    public ParcelableListBinder(java.lang.Class<T> cls, java.util.function.Consumer<java.util.List<T>> consumer) {
        this.mListElementsClass = cls;
        this.mConsumer = consumer;
    }

    @Override // android.os.Binder
    protected boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
        java.util.List<T> list;
        if (i != 1) {
            return super.onTransact(i, parcel, parcel2, i2);
        }
        synchronized (this.mLock) {
            if (this.mConsumed) {
                return false;
            }
            int size = this.mList.size();
            if (size == 0) {
                this.mCount = parcel.readInt();
            }
            while (true) {
                list = null;
                if (size >= this.mCount || parcel.readInt() == 0) {
                    break;
                }
                android.os.Parcelable readParcelable = parcel.readParcelable(null);
                if (this.mListElementsClass.isAssignableFrom(readParcelable.getClass())) {
                    android.os.Parcelable parcelable = readParcelable;
                    this.mList.add(readParcelable);
                }
                size++;
            }
            if (size >= this.mCount) {
                list = this.mList;
                this.mConsumed = true;
            }
            if (list != null) {
                this.mConsumer.accept(list);
            }
            return true;
        }
    }

    public static <T extends android.os.Parcelable> void send(android.os.IBinder iBinder, java.util.List<T> list) throws android.os.RemoteException {
        int size = list.size();
        int i = 0;
        do {
            android.os.Parcel obtain = android.os.Parcel.obtain();
            android.os.Parcel obtain2 = android.os.Parcel.obtain();
            if (i == 0) {
                obtain.writeInt(size);
            }
            while (i < size && obtain.dataSize() < SUGGESTED_MAX_IPC_SIZE) {
                obtain.writeInt(1);
                obtain.writeParcelable(list.get(i), 0);
                i++;
            }
            if (i < size) {
                obtain.writeInt(0);
            }
            iBinder.transact(1, obtain, obtain2, 0);
            obtain2.recycle();
            obtain.recycle();
        } while (i < size);
    }
}
