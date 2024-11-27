package android.view.accessibility;

/* loaded from: classes4.dex */
public interface IMagnificationConnection extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.view.accessibility.IMagnificationConnection";

    void disableWindowMagnification(int i, android.view.accessibility.IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) throws android.os.RemoteException;

    void enableWindowMagnification(int i, float f, float f2, float f3, float f4, float f5, android.view.accessibility.IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) throws android.os.RemoteException;

    void moveWindowMagnifier(int i, float f, float f2) throws android.os.RemoteException;

    void moveWindowMagnifierToPosition(int i, float f, float f2, android.view.accessibility.IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) throws android.os.RemoteException;

    void onFullscreenMagnificationActivationChanged(int i, boolean z) throws android.os.RemoteException;

    void onUserMagnificationScaleChanged(int i, int i2, float f) throws android.os.RemoteException;

    void removeMagnificationButton(int i) throws android.os.RemoteException;

    void removeMagnificationSettingsPanel(int i) throws android.os.RemoteException;

    void setConnectionCallback(android.view.accessibility.IMagnificationConnectionCallback iMagnificationConnectionCallback) throws android.os.RemoteException;

    void setScaleForWindowMagnification(int i, float f) throws android.os.RemoteException;

    void showMagnificationButton(int i, int i2) throws android.os.RemoteException;

    public static class Default implements android.view.accessibility.IMagnificationConnection {
        @Override // android.view.accessibility.IMagnificationConnection
        public void enableWindowMagnification(int i, float f, float f2, float f3, float f4, float f5, android.view.accessibility.IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IMagnificationConnection
        public void setScaleForWindowMagnification(int i, float f) throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IMagnificationConnection
        public void disableWindowMagnification(int i, android.view.accessibility.IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IMagnificationConnection
        public void moveWindowMagnifier(int i, float f, float f2) throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IMagnificationConnection
        public void moveWindowMagnifierToPosition(int i, float f, float f2, android.view.accessibility.IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IMagnificationConnection
        public void showMagnificationButton(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IMagnificationConnection
        public void removeMagnificationButton(int i) throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IMagnificationConnection
        public void removeMagnificationSettingsPanel(int i) throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IMagnificationConnection
        public void setConnectionCallback(android.view.accessibility.IMagnificationConnectionCallback iMagnificationConnectionCallback) throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IMagnificationConnection
        public void onUserMagnificationScaleChanged(int i, int i2, float f) throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IMagnificationConnection
        public void onFullscreenMagnificationActivationChanged(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.accessibility.IMagnificationConnection {
        static final int TRANSACTION_disableWindowMagnification = 3;
        static final int TRANSACTION_enableWindowMagnification = 1;
        static final int TRANSACTION_moveWindowMagnifier = 4;
        static final int TRANSACTION_moveWindowMagnifierToPosition = 5;
        static final int TRANSACTION_onFullscreenMagnificationActivationChanged = 11;
        static final int TRANSACTION_onUserMagnificationScaleChanged = 10;
        static final int TRANSACTION_removeMagnificationButton = 7;
        static final int TRANSACTION_removeMagnificationSettingsPanel = 8;
        static final int TRANSACTION_setConnectionCallback = 9;
        static final int TRANSACTION_setScaleForWindowMagnification = 2;
        static final int TRANSACTION_showMagnificationButton = 6;

        public Stub() {
            attachInterface(this, android.view.accessibility.IMagnificationConnection.DESCRIPTOR);
        }

        public static android.view.accessibility.IMagnificationConnection asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.view.accessibility.IMagnificationConnection.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.accessibility.IMagnificationConnection)) {
                return (android.view.accessibility.IMagnificationConnection) queryLocalInterface;
            }
            return new android.view.accessibility.IMagnificationConnection.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "enableWindowMagnification";
                case 2:
                    return "setScaleForWindowMagnification";
                case 3:
                    return "disableWindowMagnification";
                case 4:
                    return "moveWindowMagnifier";
                case 5:
                    return "moveWindowMagnifierToPosition";
                case 6:
                    return "showMagnificationButton";
                case 7:
                    return "removeMagnificationButton";
                case 8:
                    return "removeMagnificationSettingsPanel";
                case 9:
                    return "setConnectionCallback";
                case 10:
                    return "onUserMagnificationScaleChanged";
                case 11:
                    return "onFullscreenMagnificationActivationChanged";
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
                parcel.enforceInterface(android.view.accessibility.IMagnificationConnection.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.view.accessibility.IMagnificationConnection.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    float readFloat = parcel.readFloat();
                    float readFloat2 = parcel.readFloat();
                    float readFloat3 = parcel.readFloat();
                    float readFloat4 = parcel.readFloat();
                    float readFloat5 = parcel.readFloat();
                    android.view.accessibility.IRemoteMagnificationAnimationCallback asInterface = android.view.accessibility.IRemoteMagnificationAnimationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    enableWindowMagnification(readInt, readFloat, readFloat2, readFloat3, readFloat4, readFloat5, asInterface);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    float readFloat6 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setScaleForWindowMagnification(readInt2, readFloat6);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    android.view.accessibility.IRemoteMagnificationAnimationCallback asInterface2 = android.view.accessibility.IRemoteMagnificationAnimationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    disableWindowMagnification(readInt3, asInterface2);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    float readFloat7 = parcel.readFloat();
                    float readFloat8 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    moveWindowMagnifier(readInt4, readFloat7, readFloat8);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    float readFloat9 = parcel.readFloat();
                    float readFloat10 = parcel.readFloat();
                    android.view.accessibility.IRemoteMagnificationAnimationCallback asInterface3 = android.view.accessibility.IRemoteMagnificationAnimationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    moveWindowMagnifierToPosition(readInt5, readFloat9, readFloat10, asInterface3);
                    return true;
                case 6:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    showMagnificationButton(readInt6, readInt7);
                    return true;
                case 7:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeMagnificationButton(readInt8);
                    return true;
                case 8:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeMagnificationSettingsPanel(readInt9);
                    return true;
                case 9:
                    android.view.accessibility.IMagnificationConnectionCallback asInterface4 = android.view.accessibility.IMagnificationConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setConnectionCallback(asInterface4);
                    return true;
                case 10:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    float readFloat11 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    onUserMagnificationScaleChanged(readInt10, readInt11, readFloat11);
                    return true;
                case 11:
                    int readInt12 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onFullscreenMagnificationActivationChanged(readInt12, readBoolean);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.accessibility.IMagnificationConnection {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.accessibility.IMagnificationConnection.DESCRIPTOR;
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void enableWindowMagnification(int i, float f, float f2, float f3, float f4, float f5, android.view.accessibility.IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IMagnificationConnection.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeFloat(f);
                    obtain.writeFloat(f2);
                    obtain.writeFloat(f3);
                    obtain.writeFloat(f4);
                    obtain.writeFloat(f5);
                    obtain.writeStrongInterface(iRemoteMagnificationAnimationCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void setScaleForWindowMagnification(int i, float f) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IMagnificationConnection.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeFloat(f);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void disableWindowMagnification(int i, android.view.accessibility.IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IMagnificationConnection.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iRemoteMagnificationAnimationCallback);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void moveWindowMagnifier(int i, float f, float f2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IMagnificationConnection.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeFloat(f);
                    obtain.writeFloat(f2);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void moveWindowMagnifierToPosition(int i, float f, float f2, android.view.accessibility.IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IMagnificationConnection.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeFloat(f);
                    obtain.writeFloat(f2);
                    obtain.writeStrongInterface(iRemoteMagnificationAnimationCallback);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void showMagnificationButton(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IMagnificationConnection.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void removeMagnificationButton(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IMagnificationConnection.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void removeMagnificationSettingsPanel(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IMagnificationConnection.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void setConnectionCallback(android.view.accessibility.IMagnificationConnectionCallback iMagnificationConnectionCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IMagnificationConnection.DESCRIPTOR);
                    obtain.writeStrongInterface(iMagnificationConnectionCallback);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void onUserMagnificationScaleChanged(int i, int i2, float f) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IMagnificationConnection.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeFloat(f);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnection
            public void onFullscreenMagnificationActivationChanged(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IMagnificationConnection.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 10;
        }
    }
}
