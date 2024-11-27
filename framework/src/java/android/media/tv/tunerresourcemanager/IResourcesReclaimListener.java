package android.media.tv.tunerresourcemanager;

/* loaded from: classes2.dex */
public interface IResourcesReclaimListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.tv.tunerresourcemanager.IResourcesReclaimListener";

    void onReclaimResources() throws android.os.RemoteException;

    public static class Default implements android.media.tv.tunerresourcemanager.IResourcesReclaimListener {
        @Override // android.media.tv.tunerresourcemanager.IResourcesReclaimListener
        public void onReclaimResources() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.tv.tunerresourcemanager.IResourcesReclaimListener {
        static final int TRANSACTION_onReclaimResources = 1;

        public Stub() {
            attachInterface(this, android.media.tv.tunerresourcemanager.IResourcesReclaimListener.DESCRIPTOR);
        }

        public static android.media.tv.tunerresourcemanager.IResourcesReclaimListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.tv.tunerresourcemanager.IResourcesReclaimListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.tv.tunerresourcemanager.IResourcesReclaimListener)) {
                return (android.media.tv.tunerresourcemanager.IResourcesReclaimListener) queryLocalInterface;
            }
            return new android.media.tv.tunerresourcemanager.IResourcesReclaimListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(android.media.tv.tunerresourcemanager.IResourcesReclaimListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.tv.tunerresourcemanager.IResourcesReclaimListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    onReclaimResources();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.tv.tunerresourcemanager.IResourcesReclaimListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.tv.tunerresourcemanager.IResourcesReclaimListener.DESCRIPTOR;
            }

            @Override // android.media.tv.tunerresourcemanager.IResourcesReclaimListener
            public void onReclaimResources() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.tunerresourcemanager.IResourcesReclaimListener.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
