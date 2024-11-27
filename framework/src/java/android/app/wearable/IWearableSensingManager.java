package android.app.wearable;

/* loaded from: classes.dex */
public interface IWearableSensingManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.wearable.IWearableSensingManager";

    void provideConnection(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    void provideData(android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    void provideDataStream(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    void registerDataRequestObserver(int i, android.app.PendingIntent pendingIntent, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    void startHotwordRecognition(android.content.ComponentName componentName, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    void stopHotwordRecognition(android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    void unregisterDataRequestObserver(int i, android.app.PendingIntent pendingIntent, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    public static class Default implements android.app.wearable.IWearableSensingManager {
        @Override // android.app.wearable.IWearableSensingManager
        public void provideConnection(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.app.wearable.IWearableSensingManager
        public void provideDataStream(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.app.wearable.IWearableSensingManager
        public void provideData(android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.app.wearable.IWearableSensingManager
        public void registerDataRequestObserver(int i, android.app.PendingIntent pendingIntent, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.app.wearable.IWearableSensingManager
        public void unregisterDataRequestObserver(int i, android.app.PendingIntent pendingIntent, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.app.wearable.IWearableSensingManager
        public void startHotwordRecognition(android.content.ComponentName componentName, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.app.wearable.IWearableSensingManager
        public void stopHotwordRecognition(android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.wearable.IWearableSensingManager {
        static final int TRANSACTION_provideConnection = 1;
        static final int TRANSACTION_provideData = 3;
        static final int TRANSACTION_provideDataStream = 2;
        static final int TRANSACTION_registerDataRequestObserver = 4;
        static final int TRANSACTION_startHotwordRecognition = 6;
        static final int TRANSACTION_stopHotwordRecognition = 7;
        static final int TRANSACTION_unregisterDataRequestObserver = 5;

        public Stub() {
            attachInterface(this, android.app.wearable.IWearableSensingManager.DESCRIPTOR);
        }

        public static android.app.wearable.IWearableSensingManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.wearable.IWearableSensingManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.wearable.IWearableSensingManager)) {
                return (android.app.wearable.IWearableSensingManager) queryLocalInterface;
            }
            return new android.app.wearable.IWearableSensingManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "provideConnection";
                case 2:
                    return "provideDataStream";
                case 3:
                    return "provideData";
                case 4:
                    return "registerDataRequestObserver";
                case 5:
                    return "unregisterDataRequestObserver";
                case 6:
                    return "startHotwordRecognition";
                case 7:
                    return "stopHotwordRecognition";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(android.app.wearable.IWearableSensingManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.wearable.IWearableSensingManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    android.os.RemoteCallback remoteCallback = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    provideConnection(parcelFileDescriptor, remoteCallback);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.os.ParcelFileDescriptor parcelFileDescriptor2 = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    android.os.RemoteCallback remoteCallback2 = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    provideDataStream(parcelFileDescriptor2, remoteCallback2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    android.os.PersistableBundle persistableBundle = (android.os.PersistableBundle) parcel.readTypedObject(android.os.PersistableBundle.CREATOR);
                    android.os.SharedMemory sharedMemory = (android.os.SharedMemory) parcel.readTypedObject(android.os.SharedMemory.CREATOR);
                    android.os.RemoteCallback remoteCallback3 = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    provideData(persistableBundle, sharedMemory, remoteCallback3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt = parcel.readInt();
                    android.app.PendingIntent pendingIntent = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    android.os.RemoteCallback remoteCallback4 = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerDataRequestObserver(readInt, pendingIntent, remoteCallback4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt2 = parcel.readInt();
                    android.app.PendingIntent pendingIntent2 = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    android.os.RemoteCallback remoteCallback5 = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    unregisterDataRequestObserver(readInt2, pendingIntent2, remoteCallback5);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    android.os.RemoteCallback remoteCallback6 = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    startHotwordRecognition(componentName, remoteCallback6);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    android.os.RemoteCallback remoteCallback7 = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    stopHotwordRecognition(remoteCallback7);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.wearable.IWearableSensingManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.wearable.IWearableSensingManager.DESCRIPTOR;
            }

            @Override // android.app.wearable.IWearableSensingManager
            public void provideConnection(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.wearable.IWearableSensingManager.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.wearable.IWearableSensingManager
            public void provideDataStream(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.wearable.IWearableSensingManager.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.wearable.IWearableSensingManager
            public void provideData(android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.wearable.IWearableSensingManager.DESCRIPTOR);
                    obtain.writeTypedObject(persistableBundle, 0);
                    obtain.writeTypedObject(sharedMemory, 0);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.wearable.IWearableSensingManager
            public void registerDataRequestObserver(int i, android.app.PendingIntent pendingIntent, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.wearable.IWearableSensingManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.wearable.IWearableSensingManager
            public void unregisterDataRequestObserver(int i, android.app.PendingIntent pendingIntent, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.wearable.IWearableSensingManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.wearable.IWearableSensingManager
            public void startHotwordRecognition(android.content.ComponentName componentName, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.wearable.IWearableSensingManager.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.wearable.IWearableSensingManager
            public void stopHotwordRecognition(android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.wearable.IWearableSensingManager.DESCRIPTOR);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }
    }
}
