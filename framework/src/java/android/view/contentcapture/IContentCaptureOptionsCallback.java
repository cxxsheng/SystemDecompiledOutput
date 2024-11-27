package android.view.contentcapture;

/* loaded from: classes4.dex */
public interface IContentCaptureOptionsCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.view.contentcapture.IContentCaptureOptionsCallback";

    void setContentCaptureOptions(android.content.ContentCaptureOptions contentCaptureOptions) throws android.os.RemoteException;

    public static class Default implements android.view.contentcapture.IContentCaptureOptionsCallback {
        @Override // android.view.contentcapture.IContentCaptureOptionsCallback
        public void setContentCaptureOptions(android.content.ContentCaptureOptions contentCaptureOptions) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.contentcapture.IContentCaptureOptionsCallback {
        static final int TRANSACTION_setContentCaptureOptions = 1;

        public Stub() {
            attachInterface(this, android.view.contentcapture.IContentCaptureOptionsCallback.DESCRIPTOR);
        }

        public static android.view.contentcapture.IContentCaptureOptionsCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.view.contentcapture.IContentCaptureOptionsCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.contentcapture.IContentCaptureOptionsCallback)) {
                return (android.view.contentcapture.IContentCaptureOptionsCallback) queryLocalInterface;
            }
            return new android.view.contentcapture.IContentCaptureOptionsCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setContentCaptureOptions";
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
                parcel.enforceInterface(android.view.contentcapture.IContentCaptureOptionsCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.view.contentcapture.IContentCaptureOptionsCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.content.ContentCaptureOptions contentCaptureOptions = (android.content.ContentCaptureOptions) parcel.readTypedObject(android.content.ContentCaptureOptions.CREATOR);
                    parcel.enforceNoDataAvail();
                    setContentCaptureOptions(contentCaptureOptions);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.contentcapture.IContentCaptureOptionsCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.contentcapture.IContentCaptureOptionsCallback.DESCRIPTOR;
            }

            @Override // android.view.contentcapture.IContentCaptureOptionsCallback
            public void setContentCaptureOptions(android.content.ContentCaptureOptions contentCaptureOptions) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.contentcapture.IContentCaptureOptionsCallback.DESCRIPTOR);
                    obtain.writeTypedObject(contentCaptureOptions, 0);
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
