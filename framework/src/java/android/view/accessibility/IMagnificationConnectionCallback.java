package android.view.accessibility;

/* loaded from: classes4.dex */
public interface IMagnificationConnectionCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.view.accessibility.IMagnificationConnectionCallback";

    void onAccessibilityActionPerformed(int i) throws android.os.RemoteException;

    void onChangeMagnificationMode(int i, int i2) throws android.os.RemoteException;

    void onMove(int i) throws android.os.RemoteException;

    void onPerformScaleAction(int i, float f, boolean z) throws android.os.RemoteException;

    void onSourceBoundsChanged(int i, android.graphics.Rect rect) throws android.os.RemoteException;

    void onWindowMagnifierBoundsChanged(int i, android.graphics.Rect rect) throws android.os.RemoteException;

    public static class Default implements android.view.accessibility.IMagnificationConnectionCallback {
        @Override // android.view.accessibility.IMagnificationConnectionCallback
        public void onWindowMagnifierBoundsChanged(int i, android.graphics.Rect rect) throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IMagnificationConnectionCallback
        public void onChangeMagnificationMode(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IMagnificationConnectionCallback
        public void onSourceBoundsChanged(int i, android.graphics.Rect rect) throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IMagnificationConnectionCallback
        public void onPerformScaleAction(int i, float f, boolean z) throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IMagnificationConnectionCallback
        public void onAccessibilityActionPerformed(int i) throws android.os.RemoteException {
        }

        @Override // android.view.accessibility.IMagnificationConnectionCallback
        public void onMove(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.accessibility.IMagnificationConnectionCallback {
        static final int TRANSACTION_onAccessibilityActionPerformed = 5;
        static final int TRANSACTION_onChangeMagnificationMode = 2;
        static final int TRANSACTION_onMove = 6;
        static final int TRANSACTION_onPerformScaleAction = 4;
        static final int TRANSACTION_onSourceBoundsChanged = 3;
        static final int TRANSACTION_onWindowMagnifierBoundsChanged = 1;

        public Stub() {
            attachInterface(this, android.view.accessibility.IMagnificationConnectionCallback.DESCRIPTOR);
        }

        public static android.view.accessibility.IMagnificationConnectionCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.view.accessibility.IMagnificationConnectionCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.accessibility.IMagnificationConnectionCallback)) {
                return (android.view.accessibility.IMagnificationConnectionCallback) queryLocalInterface;
            }
            return new android.view.accessibility.IMagnificationConnectionCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onWindowMagnifierBoundsChanged";
                case 2:
                    return "onChangeMagnificationMode";
                case 3:
                    return "onSourceBoundsChanged";
                case 4:
                    return "onPerformScaleAction";
                case 5:
                    return "onAccessibilityActionPerformed";
                case 6:
                    return "onMove";
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
                parcel.enforceInterface(android.view.accessibility.IMagnificationConnectionCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.view.accessibility.IMagnificationConnectionCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    android.graphics.Rect rect = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    onWindowMagnifierBoundsChanged(readInt, rect);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onChangeMagnificationMode(readInt2, readInt3);
                    return true;
                case 3:
                    int readInt4 = parcel.readInt();
                    android.graphics.Rect rect2 = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSourceBoundsChanged(readInt4, rect2);
                    return true;
                case 4:
                    int readInt5 = parcel.readInt();
                    float readFloat = parcel.readFloat();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onPerformScaleAction(readInt5, readFloat, readBoolean);
                    return true;
                case 5:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAccessibilityActionPerformed(readInt6);
                    return true;
                case 6:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onMove(readInt7);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.accessibility.IMagnificationConnectionCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.accessibility.IMagnificationConnectionCallback.DESCRIPTOR;
            }

            @Override // android.view.accessibility.IMagnificationConnectionCallback
            public void onWindowMagnifierBoundsChanged(int i, android.graphics.Rect rect) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IMagnificationConnectionCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnectionCallback
            public void onChangeMagnificationMode(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IMagnificationConnectionCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnectionCallback
            public void onSourceBoundsChanged(int i, android.graphics.Rect rect) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IMagnificationConnectionCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnectionCallback
            public void onPerformScaleAction(int i, float f, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IMagnificationConnectionCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeFloat(f);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnectionCallback
            public void onAccessibilityActionPerformed(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IMagnificationConnectionCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnectionCallback
            public void onMove(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IMagnificationConnectionCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }
    }
}
