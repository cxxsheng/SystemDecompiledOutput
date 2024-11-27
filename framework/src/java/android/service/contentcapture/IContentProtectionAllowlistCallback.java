package android.service.contentcapture;

/* loaded from: classes3.dex */
public interface IContentProtectionAllowlistCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.contentcapture.IContentProtectionAllowlistCallback";

    void setAllowlist(java.util.List<java.lang.String> list) throws android.os.RemoteException;

    public static class Default implements android.service.contentcapture.IContentProtectionAllowlistCallback {
        @Override // android.service.contentcapture.IContentProtectionAllowlistCallback
        public void setAllowlist(java.util.List<java.lang.String> list) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.contentcapture.IContentProtectionAllowlistCallback {
        static final int TRANSACTION_setAllowlist = 1;

        public Stub() {
            attachInterface(this, android.service.contentcapture.IContentProtectionAllowlistCallback.DESCRIPTOR);
        }

        public static android.service.contentcapture.IContentProtectionAllowlistCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.contentcapture.IContentProtectionAllowlistCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.contentcapture.IContentProtectionAllowlistCallback)) {
                return (android.service.contentcapture.IContentProtectionAllowlistCallback) queryLocalInterface;
            }
            return new android.service.contentcapture.IContentProtectionAllowlistCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setAllowlist";
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
                parcel.enforceInterface(android.service.contentcapture.IContentProtectionAllowlistCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.contentcapture.IContentProtectionAllowlistCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    setAllowlist(createStringArrayList);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.contentcapture.IContentProtectionAllowlistCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.contentcapture.IContentProtectionAllowlistCallback.DESCRIPTOR;
            }

            @Override // android.service.contentcapture.IContentProtectionAllowlistCallback
            public void setAllowlist(java.util.List<java.lang.String> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.contentcapture.IContentProtectionAllowlistCallback.DESCRIPTOR);
                    obtain.writeStringList(list);
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
