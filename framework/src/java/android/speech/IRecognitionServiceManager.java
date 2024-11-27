package android.speech;

/* loaded from: classes3.dex */
public interface IRecognitionServiceManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.speech.IRecognitionServiceManager";

    void createSession(android.content.ComponentName componentName, android.os.IBinder iBinder, boolean z, android.speech.IRecognitionServiceManagerCallback iRecognitionServiceManagerCallback) throws android.os.RemoteException;

    void setTemporaryComponent(android.content.ComponentName componentName) throws android.os.RemoteException;

    public static class Default implements android.speech.IRecognitionServiceManager {
        @Override // android.speech.IRecognitionServiceManager
        public void createSession(android.content.ComponentName componentName, android.os.IBinder iBinder, boolean z, android.speech.IRecognitionServiceManagerCallback iRecognitionServiceManagerCallback) throws android.os.RemoteException {
        }

        @Override // android.speech.IRecognitionServiceManager
        public void setTemporaryComponent(android.content.ComponentName componentName) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.speech.IRecognitionServiceManager {
        static final int TRANSACTION_createSession = 1;
        static final int TRANSACTION_setTemporaryComponent = 2;

        public Stub() {
            attachInterface(this, android.speech.IRecognitionServiceManager.DESCRIPTOR);
        }

        public static android.speech.IRecognitionServiceManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.speech.IRecognitionServiceManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.speech.IRecognitionServiceManager)) {
                return (android.speech.IRecognitionServiceManager) queryLocalInterface;
            }
            return new android.speech.IRecognitionServiceManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "createSession";
                case 2:
                    return "setTemporaryComponent";
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
                parcel.enforceInterface(android.speech.IRecognitionServiceManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.speech.IRecognitionServiceManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    boolean readBoolean = parcel.readBoolean();
                    android.speech.IRecognitionServiceManagerCallback asInterface = android.speech.IRecognitionServiceManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    createSession(componentName, readStrongBinder, readBoolean, asInterface);
                    return true;
                case 2:
                    android.content.ComponentName componentName2 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    setTemporaryComponent(componentName2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.speech.IRecognitionServiceManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.speech.IRecognitionServiceManager.DESCRIPTOR;
            }

            @Override // android.speech.IRecognitionServiceManager
            public void createSession(android.content.ComponentName componentName, android.os.IBinder iBinder, boolean z, android.speech.IRecognitionServiceManagerCallback iRecognitionServiceManagerCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.speech.IRecognitionServiceManager.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iRecognitionServiceManagerCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.IRecognitionServiceManager
            public void setTemporaryComponent(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.speech.IRecognitionServiceManager.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
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
