package android.service.remotelockscreenvalidation;

/* loaded from: classes3.dex */
public interface IRemoteLockscreenValidationCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.remotelockscreenvalidation.IRemoteLockscreenValidationCallback";

    void onFailure(java.lang.String str) throws android.os.RemoteException;

    void onSuccess(android.app.RemoteLockscreenValidationResult remoteLockscreenValidationResult) throws android.os.RemoteException;

    public static class Default implements android.service.remotelockscreenvalidation.IRemoteLockscreenValidationCallback {
        @Override // android.service.remotelockscreenvalidation.IRemoteLockscreenValidationCallback
        public void onSuccess(android.app.RemoteLockscreenValidationResult remoteLockscreenValidationResult) throws android.os.RemoteException {
        }

        @Override // android.service.remotelockscreenvalidation.IRemoteLockscreenValidationCallback
        public void onFailure(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.remotelockscreenvalidation.IRemoteLockscreenValidationCallback {
        static final int TRANSACTION_onFailure = 2;
        static final int TRANSACTION_onSuccess = 1;

        public Stub() {
            attachInterface(this, android.service.remotelockscreenvalidation.IRemoteLockscreenValidationCallback.DESCRIPTOR);
        }

        public static android.service.remotelockscreenvalidation.IRemoteLockscreenValidationCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.remotelockscreenvalidation.IRemoteLockscreenValidationCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.remotelockscreenvalidation.IRemoteLockscreenValidationCallback)) {
                return (android.service.remotelockscreenvalidation.IRemoteLockscreenValidationCallback) queryLocalInterface;
            }
            return new android.service.remotelockscreenvalidation.IRemoteLockscreenValidationCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onSuccess";
                case 2:
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
                parcel.enforceInterface(android.service.remotelockscreenvalidation.IRemoteLockscreenValidationCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.remotelockscreenvalidation.IRemoteLockscreenValidationCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.app.RemoteLockscreenValidationResult remoteLockscreenValidationResult = (android.app.RemoteLockscreenValidationResult) parcel.readTypedObject(android.app.RemoteLockscreenValidationResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSuccess(remoteLockscreenValidationResult);
                    return true;
                case 2:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onFailure(readString);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.remotelockscreenvalidation.IRemoteLockscreenValidationCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.remotelockscreenvalidation.IRemoteLockscreenValidationCallback.DESCRIPTOR;
            }

            @Override // android.service.remotelockscreenvalidation.IRemoteLockscreenValidationCallback
            public void onSuccess(android.app.RemoteLockscreenValidationResult remoteLockscreenValidationResult) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.remotelockscreenvalidation.IRemoteLockscreenValidationCallback.DESCRIPTOR);
                    obtain.writeTypedObject(remoteLockscreenValidationResult, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.remotelockscreenvalidation.IRemoteLockscreenValidationCallback
            public void onFailure(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.remotelockscreenvalidation.IRemoteLockscreenValidationCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
