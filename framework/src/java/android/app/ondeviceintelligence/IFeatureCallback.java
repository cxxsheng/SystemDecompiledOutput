package android.app.ondeviceintelligence;

/* loaded from: classes.dex */
public interface IFeatureCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.ondeviceintelligence.IFeatureCallback";

    void onFailure(int i, java.lang.String str, android.os.PersistableBundle persistableBundle) throws android.os.RemoteException;

    void onSuccess(android.app.ondeviceintelligence.Feature feature) throws android.os.RemoteException;

    public static class Default implements android.app.ondeviceintelligence.IFeatureCallback {
        @Override // android.app.ondeviceintelligence.IFeatureCallback
        public void onSuccess(android.app.ondeviceintelligence.Feature feature) throws android.os.RemoteException {
        }

        @Override // android.app.ondeviceintelligence.IFeatureCallback
        public void onFailure(int i, java.lang.String str, android.os.PersistableBundle persistableBundle) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.ondeviceintelligence.IFeatureCallback {
        static final int TRANSACTION_onFailure = 3;
        static final int TRANSACTION_onSuccess = 2;

        public Stub() {
            attachInterface(this, android.app.ondeviceintelligence.IFeatureCallback.DESCRIPTOR);
        }

        public static android.app.ondeviceintelligence.IFeatureCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.ondeviceintelligence.IFeatureCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.ondeviceintelligence.IFeatureCallback)) {
                return (android.app.ondeviceintelligence.IFeatureCallback) queryLocalInterface;
            }
            return new android.app.ondeviceintelligence.IFeatureCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 2:
                    return "onSuccess";
                case 3:
                    return "onFailure";
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
                parcel.enforceInterface(android.app.ondeviceintelligence.IFeatureCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.ondeviceintelligence.IFeatureCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 2:
                    android.app.ondeviceintelligence.Feature feature = (android.app.ondeviceintelligence.Feature) parcel.readTypedObject(android.app.ondeviceintelligence.Feature.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSuccess(feature);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    android.os.PersistableBundle persistableBundle = (android.os.PersistableBundle) parcel.readTypedObject(android.os.PersistableBundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onFailure(readInt, readString, persistableBundle);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.ondeviceintelligence.IFeatureCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.ondeviceintelligence.IFeatureCallback.DESCRIPTOR;
            }

            @Override // android.app.ondeviceintelligence.IFeatureCallback
            public void onSuccess(android.app.ondeviceintelligence.Feature feature) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.ondeviceintelligence.IFeatureCallback.DESCRIPTOR);
                    obtain.writeTypedObject(feature, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.ondeviceintelligence.IFeatureCallback
            public void onFailure(int i, java.lang.String str, android.os.PersistableBundle persistableBundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.ondeviceintelligence.IFeatureCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(persistableBundle, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}