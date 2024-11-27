package android.media.soundtrigger_middleware;

/* loaded from: classes2.dex */
public interface IInjectGlobalEvent extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.soundtrigger_middleware.IInjectGlobalEvent";

    void setResourceContention(boolean z, android.media.soundtrigger_middleware.IAcknowledgeEvent iAcknowledgeEvent) throws android.os.RemoteException;

    void triggerOnResourcesAvailable() throws android.os.RemoteException;

    void triggerRestart() throws android.os.RemoteException;

    public static class Default implements android.media.soundtrigger_middleware.IInjectGlobalEvent {
        @Override // android.media.soundtrigger_middleware.IInjectGlobalEvent
        public void triggerRestart() throws android.os.RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.IInjectGlobalEvent
        public void setResourceContention(boolean z, android.media.soundtrigger_middleware.IAcknowledgeEvent iAcknowledgeEvent) throws android.os.RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.IInjectGlobalEvent
        public void triggerOnResourcesAvailable() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.soundtrigger_middleware.IInjectGlobalEvent {
        static final int TRANSACTION_setResourceContention = 2;
        static final int TRANSACTION_triggerOnResourcesAvailable = 3;
        static final int TRANSACTION_triggerRestart = 1;

        public Stub() {
            attachInterface(this, android.media.soundtrigger_middleware.IInjectGlobalEvent.DESCRIPTOR);
        }

        public static android.media.soundtrigger_middleware.IInjectGlobalEvent asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.soundtrigger_middleware.IInjectGlobalEvent.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.soundtrigger_middleware.IInjectGlobalEvent)) {
                return (android.media.soundtrigger_middleware.IInjectGlobalEvent) queryLocalInterface;
            }
            return new android.media.soundtrigger_middleware.IInjectGlobalEvent.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(android.media.soundtrigger_middleware.IInjectGlobalEvent.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.soundtrigger_middleware.IInjectGlobalEvent.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    triggerRestart();
                    return true;
                case 2:
                    boolean readBoolean = parcel.readBoolean();
                    android.media.soundtrigger_middleware.IAcknowledgeEvent asInterface = android.media.soundtrigger_middleware.IAcknowledgeEvent.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setResourceContention(readBoolean, asInterface);
                    return true;
                case 3:
                    triggerOnResourcesAvailable();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.soundtrigger_middleware.IInjectGlobalEvent {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.soundtrigger_middleware.IInjectGlobalEvent.DESCRIPTOR;
            }

            @Override // android.media.soundtrigger_middleware.IInjectGlobalEvent
            public void triggerRestart() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.soundtrigger_middleware.IInjectGlobalEvent.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.IInjectGlobalEvent
            public void setResourceContention(boolean z, android.media.soundtrigger_middleware.IAcknowledgeEvent iAcknowledgeEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.soundtrigger_middleware.IInjectGlobalEvent.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iAcknowledgeEvent);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.IInjectGlobalEvent
            public void triggerOnResourcesAvailable() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.soundtrigger_middleware.IInjectGlobalEvent.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
