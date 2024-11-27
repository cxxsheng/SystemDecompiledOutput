package android.window;

/* loaded from: classes4.dex */
public interface IGlobalDragListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.window.IGlobalDragListener";

    void onCrossWindowDrop(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) throws android.os.RemoteException;

    void onUnhandledDrop(android.view.DragEvent dragEvent, android.window.IUnhandledDragCallback iUnhandledDragCallback) throws android.os.RemoteException;

    public static class Default implements android.window.IGlobalDragListener {
        @Override // android.window.IGlobalDragListener
        public void onCrossWindowDrop(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) throws android.os.RemoteException {
        }

        @Override // android.window.IGlobalDragListener
        public void onUnhandledDrop(android.view.DragEvent dragEvent, android.window.IUnhandledDragCallback iUnhandledDragCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.window.IGlobalDragListener {
        static final int TRANSACTION_onCrossWindowDrop = 1;
        static final int TRANSACTION_onUnhandledDrop = 2;

        public Stub() {
            attachInterface(this, android.window.IGlobalDragListener.DESCRIPTOR);
        }

        public static android.window.IGlobalDragListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.window.IGlobalDragListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.window.IGlobalDragListener)) {
                return (android.window.IGlobalDragListener) queryLocalInterface;
            }
            return new android.window.IGlobalDragListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onCrossWindowDrop";
                case 2:
                    return "onUnhandledDrop";
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
                parcel.enforceInterface(android.window.IGlobalDragListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.window.IGlobalDragListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.app.ActivityManager.RunningTaskInfo runningTaskInfo = (android.app.ActivityManager.RunningTaskInfo) parcel.readTypedObject(android.app.ActivityManager.RunningTaskInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCrossWindowDrop(runningTaskInfo);
                    return true;
                case 2:
                    android.view.DragEvent dragEvent = (android.view.DragEvent) parcel.readTypedObject(android.view.DragEvent.CREATOR);
                    android.window.IUnhandledDragCallback asInterface = android.window.IUnhandledDragCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onUnhandledDrop(dragEvent, asInterface);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.window.IGlobalDragListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.window.IGlobalDragListener.DESCRIPTOR;
            }

            @Override // android.window.IGlobalDragListener
            public void onCrossWindowDrop(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.window.IGlobalDragListener.DESCRIPTOR);
                    obtain.writeTypedObject(runningTaskInfo, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.window.IGlobalDragListener
            public void onUnhandledDrop(android.view.DragEvent dragEvent, android.window.IUnhandledDragCallback iUnhandledDragCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.window.IGlobalDragListener.DESCRIPTOR);
                    obtain.writeTypedObject(dragEvent, 0);
                    obtain.writeStrongInterface(iUnhandledDragCallback);
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
