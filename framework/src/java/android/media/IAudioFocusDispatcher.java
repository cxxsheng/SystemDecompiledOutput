package android.media;

/* loaded from: classes2.dex */
public interface IAudioFocusDispatcher extends android.os.IInterface {
    void dispatchAudioFocusChange(int i, java.lang.String str) throws android.os.RemoteException;

    void dispatchFocusResultFromExtPolicy(int i, java.lang.String str) throws android.os.RemoteException;

    public static class Default implements android.media.IAudioFocusDispatcher {
        @Override // android.media.IAudioFocusDispatcher
        public void dispatchAudioFocusChange(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioFocusDispatcher
        public void dispatchFocusResultFromExtPolicy(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.IAudioFocusDispatcher {
        public static final java.lang.String DESCRIPTOR = "android.media.IAudioFocusDispatcher";
        static final int TRANSACTION_dispatchAudioFocusChange = 1;
        static final int TRANSACTION_dispatchFocusResultFromExtPolicy = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.media.IAudioFocusDispatcher asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.IAudioFocusDispatcher)) {
                return (android.media.IAudioFocusDispatcher) queryLocalInterface;
            }
            return new android.media.IAudioFocusDispatcher.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "dispatchAudioFocusChange";
                case 2:
                    return "dispatchFocusResultFromExtPolicy";
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
                    int readInt = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    dispatchAudioFocusChange(readInt, readString);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    dispatchFocusResultFromExtPolicy(readInt2, readString2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.IAudioFocusDispatcher {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.IAudioFocusDispatcher.Stub.DESCRIPTOR;
            }

            @Override // android.media.IAudioFocusDispatcher
            public void dispatchAudioFocusChange(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioFocusDispatcher.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioFocusDispatcher
            public void dispatchFocusResultFromExtPolicy(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioFocusDispatcher.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
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
