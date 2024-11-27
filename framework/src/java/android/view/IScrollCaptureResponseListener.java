package android.view;

/* loaded from: classes4.dex */
public interface IScrollCaptureResponseListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.view.IScrollCaptureResponseListener";

    void onScrollCaptureResponse(android.view.ScrollCaptureResponse scrollCaptureResponse) throws android.os.RemoteException;

    public static class Default implements android.view.IScrollCaptureResponseListener {
        @Override // android.view.IScrollCaptureResponseListener
        public void onScrollCaptureResponse(android.view.ScrollCaptureResponse scrollCaptureResponse) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.IScrollCaptureResponseListener {
        static final int TRANSACTION_onScrollCaptureResponse = 1;

        public Stub() {
            attachInterface(this, android.view.IScrollCaptureResponseListener.DESCRIPTOR);
        }

        public static android.view.IScrollCaptureResponseListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.view.IScrollCaptureResponseListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.IScrollCaptureResponseListener)) {
                return (android.view.IScrollCaptureResponseListener) queryLocalInterface;
            }
            return new android.view.IScrollCaptureResponseListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onScrollCaptureResponse";
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
                parcel.enforceInterface(android.view.IScrollCaptureResponseListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.view.IScrollCaptureResponseListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.view.ScrollCaptureResponse scrollCaptureResponse = (android.view.ScrollCaptureResponse) parcel.readTypedObject(android.view.ScrollCaptureResponse.CREATOR);
                    parcel.enforceNoDataAvail();
                    onScrollCaptureResponse(scrollCaptureResponse);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.IScrollCaptureResponseListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.IScrollCaptureResponseListener.DESCRIPTOR;
            }

            @Override // android.view.IScrollCaptureResponseListener
            public void onScrollCaptureResponse(android.view.ScrollCaptureResponse scrollCaptureResponse) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IScrollCaptureResponseListener.DESCRIPTOR);
                    obtain.writeTypedObject(scrollCaptureResponse, 0);
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
