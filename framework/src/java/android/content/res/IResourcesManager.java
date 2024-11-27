package android.content.res;

/* loaded from: classes.dex */
public interface IResourcesManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.content.res.IResourcesManager";

    boolean dumpResources(java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    public static class Default implements android.content.res.IResourcesManager {
        @Override // android.content.res.IResourcesManager
        public boolean dumpResources(java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.content.res.IResourcesManager {
        static final int TRANSACTION_dumpResources = 1;

        public Stub() {
            attachInterface(this, android.content.res.IResourcesManager.DESCRIPTOR);
        }

        public static android.content.res.IResourcesManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.content.res.IResourcesManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.content.res.IResourcesManager)) {
                return (android.content.res.IResourcesManager) queryLocalInterface;
            }
            return new android.content.res.IResourcesManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "dumpResources";
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
                parcel.enforceInterface(android.content.res.IResourcesManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.content.res.IResourcesManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    android.os.RemoteCallback remoteCallback = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean dumpResources = dumpResources(readString, parcelFileDescriptor, remoteCallback);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dumpResources);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.content.res.IResourcesManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.content.res.IResourcesManager.DESCRIPTOR;
            }

            @Override // android.content.res.IResourcesManager
            public boolean dumpResources(java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.res.IResourcesManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
