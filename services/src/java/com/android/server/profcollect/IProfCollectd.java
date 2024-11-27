package com.android.server.profcollect;

/* loaded from: classes2.dex */
public interface IProfCollectd extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.server.profcollect.IProfCollectd";

    java.lang.String get_supported_provider() throws android.os.RemoteException;

    void process() throws android.os.RemoteException;

    void registerProviderStatusCallback(com.android.server.profcollect.IProviderStatusCallback iProviderStatusCallback) throws android.os.RemoteException;

    java.lang.String report(int i) throws android.os.RemoteException;

    void schedule() throws android.os.RemoteException;

    void terminate() throws android.os.RemoteException;

    void trace_once(java.lang.String str) throws android.os.RemoteException;

    public static class Default implements com.android.server.profcollect.IProfCollectd {
        @Override // com.android.server.profcollect.IProfCollectd
        public void schedule() throws android.os.RemoteException {
        }

        @Override // com.android.server.profcollect.IProfCollectd
        public void terminate() throws android.os.RemoteException {
        }

        @Override // com.android.server.profcollect.IProfCollectd
        public void trace_once(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.server.profcollect.IProfCollectd
        public void process() throws android.os.RemoteException {
        }

        @Override // com.android.server.profcollect.IProfCollectd
        public java.lang.String report(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.server.profcollect.IProfCollectd
        public java.lang.String get_supported_provider() throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.server.profcollect.IProfCollectd
        public void registerProviderStatusCallback(com.android.server.profcollect.IProviderStatusCallback iProviderStatusCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.server.profcollect.IProfCollectd {
        static final int TRANSACTION_get_supported_provider = 6;
        static final int TRANSACTION_process = 4;
        static final int TRANSACTION_registerProviderStatusCallback = 7;
        static final int TRANSACTION_report = 5;
        static final int TRANSACTION_schedule = 1;
        static final int TRANSACTION_terminate = 2;
        static final int TRANSACTION_trace_once = 3;

        public Stub() {
            attachInterface(this, com.android.server.profcollect.IProfCollectd.DESCRIPTOR);
        }

        public static com.android.server.profcollect.IProfCollectd asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.server.profcollect.IProfCollectd.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.server.profcollect.IProfCollectd)) {
                return (com.android.server.profcollect.IProfCollectd) queryLocalInterface;
            }
            return new com.android.server.profcollect.IProfCollectd.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(com.android.server.profcollect.IProfCollectd.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.server.profcollect.IProfCollectd.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    schedule();
                    parcel2.writeNoException();
                    return true;
                case 2:
                    terminate();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    trace_once(readString);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    process();
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String report = report(readInt);
                    parcel2.writeNoException();
                    parcel2.writeString(report);
                    return true;
                case 6:
                    java.lang.String str = get_supported_provider();
                    parcel2.writeNoException();
                    parcel2.writeString(str);
                    return true;
                case 7:
                    com.android.server.profcollect.IProviderStatusCallback asInterface = com.android.server.profcollect.IProviderStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerProviderStatusCallback(asInterface);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.server.profcollect.IProfCollectd {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.server.profcollect.IProfCollectd.DESCRIPTOR;
            }

            @Override // com.android.server.profcollect.IProfCollectd
            public void schedule() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.server.profcollect.IProfCollectd.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.server.profcollect.IProfCollectd
            public void terminate() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.server.profcollect.IProfCollectd.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.server.profcollect.IProfCollectd
            public void trace_once(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.server.profcollect.IProfCollectd.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.server.profcollect.IProfCollectd
            public void process() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.server.profcollect.IProfCollectd.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.server.profcollect.IProfCollectd
            public java.lang.String report(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.server.profcollect.IProfCollectd.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.server.profcollect.IProfCollectd
            public java.lang.String get_supported_provider() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.server.profcollect.IProfCollectd.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.server.profcollect.IProfCollectd
            public void registerProviderStatusCallback(com.android.server.profcollect.IProviderStatusCallback iProviderStatusCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.server.profcollect.IProfCollectd.DESCRIPTOR);
                    obtain.writeStrongInterface(iProviderStatusCallback);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
