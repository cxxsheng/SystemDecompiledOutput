package com.android.internal.net;

/* loaded from: classes4.dex */
public interface INetworkWatchlistManager extends android.os.IInterface {
    byte[] getWatchlistConfigHash() throws android.os.RemoteException;

    void reloadWatchlist() throws android.os.RemoteException;

    void reportWatchlistIfNecessary() throws android.os.RemoteException;

    boolean startWatchlistLogging() throws android.os.RemoteException;

    boolean stopWatchlistLogging() throws android.os.RemoteException;

    public static class Default implements com.android.internal.net.INetworkWatchlistManager {
        @Override // com.android.internal.net.INetworkWatchlistManager
        public boolean startWatchlistLogging() throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.net.INetworkWatchlistManager
        public boolean stopWatchlistLogging() throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.net.INetworkWatchlistManager
        public void reloadWatchlist() throws android.os.RemoteException {
        }

        @Override // com.android.internal.net.INetworkWatchlistManager
        public void reportWatchlistIfNecessary() throws android.os.RemoteException {
        }

        @Override // com.android.internal.net.INetworkWatchlistManager
        public byte[] getWatchlistConfigHash() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.net.INetworkWatchlistManager {
        public static final java.lang.String DESCRIPTOR = "com.android.internal.net.INetworkWatchlistManager";
        static final int TRANSACTION_getWatchlistConfigHash = 5;
        static final int TRANSACTION_reloadWatchlist = 3;
        static final int TRANSACTION_reportWatchlistIfNecessary = 4;
        static final int TRANSACTION_startWatchlistLogging = 1;
        static final int TRANSACTION_stopWatchlistLogging = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.internal.net.INetworkWatchlistManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.net.INetworkWatchlistManager)) {
                return (com.android.internal.net.INetworkWatchlistManager) queryLocalInterface;
            }
            return new com.android.internal.net.INetworkWatchlistManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "startWatchlistLogging";
                case 2:
                    return "stopWatchlistLogging";
                case 3:
                    return "reloadWatchlist";
                case 4:
                    return "reportWatchlistIfNecessary";
                case 5:
                    return "getWatchlistConfigHash";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean startWatchlistLogging = startWatchlistLogging();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startWatchlistLogging);
                    return true;
                case 2:
                    boolean stopWatchlistLogging = stopWatchlistLogging();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(stopWatchlistLogging);
                    return true;
                case 3:
                    reloadWatchlist();
                    parcel2.writeNoException();
                    return true;
                case 4:
                    reportWatchlistIfNecessary();
                    parcel2.writeNoException();
                    return true;
                case 5:
                    byte[] watchlistConfigHash = getWatchlistConfigHash();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(watchlistConfigHash);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.net.INetworkWatchlistManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.net.INetworkWatchlistManager.Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.net.INetworkWatchlistManager
            public boolean startWatchlistLogging() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.net.INetworkWatchlistManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.INetworkWatchlistManager
            public boolean stopWatchlistLogging() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.net.INetworkWatchlistManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.INetworkWatchlistManager
            public void reloadWatchlist() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.net.INetworkWatchlistManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.INetworkWatchlistManager
            public void reportWatchlistIfNecessary() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.net.INetworkWatchlistManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.INetworkWatchlistManager
            public byte[] getWatchlistConfigHash() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.net.INetworkWatchlistManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}
