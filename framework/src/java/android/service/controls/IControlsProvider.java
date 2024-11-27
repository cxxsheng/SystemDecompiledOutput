package android.service.controls;

/* loaded from: classes3.dex */
public interface IControlsProvider extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.controls.IControlsProvider";

    void action(java.lang.String str, android.service.controls.actions.ControlActionWrapper controlActionWrapper, android.service.controls.IControlsActionCallback iControlsActionCallback) throws android.os.RemoteException;

    void load(android.service.controls.IControlsSubscriber iControlsSubscriber) throws android.os.RemoteException;

    void loadSuggested(android.service.controls.IControlsSubscriber iControlsSubscriber) throws android.os.RemoteException;

    void subscribe(java.util.List<java.lang.String> list, android.service.controls.IControlsSubscriber iControlsSubscriber) throws android.os.RemoteException;

    public static class Default implements android.service.controls.IControlsProvider {
        @Override // android.service.controls.IControlsProvider
        public void load(android.service.controls.IControlsSubscriber iControlsSubscriber) throws android.os.RemoteException {
        }

        @Override // android.service.controls.IControlsProvider
        public void loadSuggested(android.service.controls.IControlsSubscriber iControlsSubscriber) throws android.os.RemoteException {
        }

        @Override // android.service.controls.IControlsProvider
        public void subscribe(java.util.List<java.lang.String> list, android.service.controls.IControlsSubscriber iControlsSubscriber) throws android.os.RemoteException {
        }

        @Override // android.service.controls.IControlsProvider
        public void action(java.lang.String str, android.service.controls.actions.ControlActionWrapper controlActionWrapper, android.service.controls.IControlsActionCallback iControlsActionCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.controls.IControlsProvider {
        static final int TRANSACTION_action = 4;
        static final int TRANSACTION_load = 1;
        static final int TRANSACTION_loadSuggested = 2;
        static final int TRANSACTION_subscribe = 3;

        public Stub() {
            attachInterface(this, android.service.controls.IControlsProvider.DESCRIPTOR);
        }

        public static android.service.controls.IControlsProvider asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.controls.IControlsProvider.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.controls.IControlsProvider)) {
                return (android.service.controls.IControlsProvider) queryLocalInterface;
            }
            return new android.service.controls.IControlsProvider.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "load";
                case 2:
                    return "loadSuggested";
                case 3:
                    return "subscribe";
                case 4:
                    return "action";
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
                parcel.enforceInterface(android.service.controls.IControlsProvider.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.controls.IControlsProvider.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.service.controls.IControlsSubscriber asInterface = android.service.controls.IControlsSubscriber.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    load(asInterface);
                    return true;
                case 2:
                    android.service.controls.IControlsSubscriber asInterface2 = android.service.controls.IControlsSubscriber.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    loadSuggested(asInterface2);
                    return true;
                case 3:
                    java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
                    android.service.controls.IControlsSubscriber asInterface3 = android.service.controls.IControlsSubscriber.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    subscribe(createStringArrayList, asInterface3);
                    return true;
                case 4:
                    java.lang.String readString = parcel.readString();
                    android.service.controls.actions.ControlActionWrapper controlActionWrapper = (android.service.controls.actions.ControlActionWrapper) parcel.readTypedObject(android.service.controls.actions.ControlActionWrapper.CREATOR);
                    android.service.controls.IControlsActionCallback asInterface4 = android.service.controls.IControlsActionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    action(readString, controlActionWrapper, asInterface4);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.controls.IControlsProvider {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.controls.IControlsProvider.DESCRIPTOR;
            }

            @Override // android.service.controls.IControlsProvider
            public void load(android.service.controls.IControlsSubscriber iControlsSubscriber) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.controls.IControlsProvider.DESCRIPTOR);
                    obtain.writeStrongInterface(iControlsSubscriber);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.controls.IControlsProvider
            public void loadSuggested(android.service.controls.IControlsSubscriber iControlsSubscriber) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.controls.IControlsProvider.DESCRIPTOR);
                    obtain.writeStrongInterface(iControlsSubscriber);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.controls.IControlsProvider
            public void subscribe(java.util.List<java.lang.String> list, android.service.controls.IControlsSubscriber iControlsSubscriber) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.controls.IControlsProvider.DESCRIPTOR);
                    obtain.writeStringList(list);
                    obtain.writeStrongInterface(iControlsSubscriber);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.controls.IControlsProvider
            public void action(java.lang.String str, android.service.controls.actions.ControlActionWrapper controlActionWrapper, android.service.controls.IControlsActionCallback iControlsActionCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.controls.IControlsProvider.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(controlActionWrapper, 0);
                    obtain.writeStrongInterface(iControlsActionCallback);
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
