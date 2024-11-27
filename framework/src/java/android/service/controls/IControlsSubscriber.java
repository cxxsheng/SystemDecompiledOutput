package android.service.controls;

/* loaded from: classes3.dex */
public interface IControlsSubscriber extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.controls.IControlsSubscriber";

    void onComplete(android.os.IBinder iBinder) throws android.os.RemoteException;

    void onError(android.os.IBinder iBinder, java.lang.String str) throws android.os.RemoteException;

    void onNext(android.os.IBinder iBinder, android.service.controls.Control control) throws android.os.RemoteException;

    void onSubscribe(android.os.IBinder iBinder, android.service.controls.IControlsSubscription iControlsSubscription) throws android.os.RemoteException;

    public static class Default implements android.service.controls.IControlsSubscriber {
        @Override // android.service.controls.IControlsSubscriber
        public void onSubscribe(android.os.IBinder iBinder, android.service.controls.IControlsSubscription iControlsSubscription) throws android.os.RemoteException {
        }

        @Override // android.service.controls.IControlsSubscriber
        public void onNext(android.os.IBinder iBinder, android.service.controls.Control control) throws android.os.RemoteException {
        }

        @Override // android.service.controls.IControlsSubscriber
        public void onError(android.os.IBinder iBinder, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.service.controls.IControlsSubscriber
        public void onComplete(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.controls.IControlsSubscriber {
        static final int TRANSACTION_onComplete = 4;
        static final int TRANSACTION_onError = 3;
        static final int TRANSACTION_onNext = 2;
        static final int TRANSACTION_onSubscribe = 1;

        public Stub() {
            attachInterface(this, android.service.controls.IControlsSubscriber.DESCRIPTOR);
        }

        public static android.service.controls.IControlsSubscriber asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.controls.IControlsSubscriber.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.controls.IControlsSubscriber)) {
                return (android.service.controls.IControlsSubscriber) queryLocalInterface;
            }
            return new android.service.controls.IControlsSubscriber.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onSubscribe";
                case 2:
                    return "onNext";
                case 3:
                    return "onError";
                case 4:
                    return "onComplete";
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
                parcel.enforceInterface(android.service.controls.IControlsSubscriber.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.controls.IControlsSubscriber.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    android.service.controls.IControlsSubscription asInterface = android.service.controls.IControlsSubscription.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onSubscribe(readStrongBinder, asInterface);
                    return true;
                case 2:
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    android.service.controls.Control control = (android.service.controls.Control) parcel.readTypedObject(android.service.controls.Control.CREATOR);
                    parcel.enforceNoDataAvail();
                    onNext(readStrongBinder2, control);
                    return true;
                case 3:
                    android.os.IBinder readStrongBinder3 = parcel.readStrongBinder();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onError(readStrongBinder3, readString);
                    return true;
                case 4:
                    android.os.IBinder readStrongBinder4 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    onComplete(readStrongBinder4);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.controls.IControlsSubscriber {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.controls.IControlsSubscriber.DESCRIPTOR;
            }

            @Override // android.service.controls.IControlsSubscriber
            public void onSubscribe(android.os.IBinder iBinder, android.service.controls.IControlsSubscription iControlsSubscription) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.controls.IControlsSubscriber.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iControlsSubscription);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.controls.IControlsSubscriber
            public void onNext(android.os.IBinder iBinder, android.service.controls.Control control) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.controls.IControlsSubscriber.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(control, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.controls.IControlsSubscriber
            public void onError(android.os.IBinder iBinder, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.controls.IControlsSubscriber.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.controls.IControlsSubscriber
            public void onComplete(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.controls.IControlsSubscriber.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
