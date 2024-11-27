package android.view;

/* loaded from: classes4.dex */
public interface ISystemGestureExclusionListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.view.ISystemGestureExclusionListener";

    void onSystemGestureExclusionChanged(int i, android.graphics.Region region, android.graphics.Region region2) throws android.os.RemoteException;

    public static class Default implements android.view.ISystemGestureExclusionListener {
        @Override // android.view.ISystemGestureExclusionListener
        public void onSystemGestureExclusionChanged(int i, android.graphics.Region region, android.graphics.Region region2) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.ISystemGestureExclusionListener {
        static final int TRANSACTION_onSystemGestureExclusionChanged = 1;

        public Stub() {
            attachInterface(this, android.view.ISystemGestureExclusionListener.DESCRIPTOR);
        }

        public static android.view.ISystemGestureExclusionListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.view.ISystemGestureExclusionListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.ISystemGestureExclusionListener)) {
                return (android.view.ISystemGestureExclusionListener) queryLocalInterface;
            }
            return new android.view.ISystemGestureExclusionListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onSystemGestureExclusionChanged";
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
                parcel.enforceInterface(android.view.ISystemGestureExclusionListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.view.ISystemGestureExclusionListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    android.graphics.Region region = (android.graphics.Region) parcel.readTypedObject(android.graphics.Region.CREATOR);
                    android.graphics.Region region2 = (android.graphics.Region) parcel.readTypedObject(android.graphics.Region.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSystemGestureExclusionChanged(readInt, region, region2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.ISystemGestureExclusionListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.ISystemGestureExclusionListener.DESCRIPTOR;
            }

            @Override // android.view.ISystemGestureExclusionListener
            public void onSystemGestureExclusionChanged(int i, android.graphics.Region region, android.graphics.Region region2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.ISystemGestureExclusionListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(region, 0);
                    obtain.writeTypedObject(region2, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
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
