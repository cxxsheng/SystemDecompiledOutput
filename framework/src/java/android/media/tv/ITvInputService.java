package android.media.tv;

/* loaded from: classes2.dex */
public interface ITvInputService extends android.os.IInterface {
    void createRecordingSession(android.media.tv.ITvInputSessionCallback iTvInputSessionCallback, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void createSession(android.view.InputChannel inputChannel, android.media.tv.ITvInputSessionCallback iTvInputSessionCallback, java.lang.String str, java.lang.String str2, android.content.AttributionSource attributionSource) throws android.os.RemoteException;

    java.util.List<java.lang.String> getAvailableExtensionInterfaceNames() throws android.os.RemoteException;

    android.os.IBinder getExtensionInterface(java.lang.String str) throws android.os.RemoteException;

    java.lang.String getExtensionInterfacePermission(java.lang.String str) throws android.os.RemoteException;

    void notifyHardwareAdded(android.media.tv.TvInputHardwareInfo tvInputHardwareInfo) throws android.os.RemoteException;

    void notifyHardwareRemoved(android.media.tv.TvInputHardwareInfo tvInputHardwareInfo) throws android.os.RemoteException;

    void notifyHdmiDeviceAdded(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo) throws android.os.RemoteException;

    void notifyHdmiDeviceRemoved(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo) throws android.os.RemoteException;

    void notifyHdmiDeviceUpdated(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo) throws android.os.RemoteException;

    void registerCallback(android.media.tv.ITvInputServiceCallback iTvInputServiceCallback) throws android.os.RemoteException;

    void unregisterCallback(android.media.tv.ITvInputServiceCallback iTvInputServiceCallback) throws android.os.RemoteException;

    public static class Default implements android.media.tv.ITvInputService {
        @Override // android.media.tv.ITvInputService
        public void registerCallback(android.media.tv.ITvInputServiceCallback iTvInputServiceCallback) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputService
        public void unregisterCallback(android.media.tv.ITvInputServiceCallback iTvInputServiceCallback) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputService
        public void createSession(android.view.InputChannel inputChannel, android.media.tv.ITvInputSessionCallback iTvInputSessionCallback, java.lang.String str, java.lang.String str2, android.content.AttributionSource attributionSource) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputService
        public void createRecordingSession(android.media.tv.ITvInputSessionCallback iTvInputSessionCallback, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputService
        public java.util.List<java.lang.String> getAvailableExtensionInterfaceNames() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.tv.ITvInputService
        public android.os.IBinder getExtensionInterface(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.tv.ITvInputService
        public java.lang.String getExtensionInterfacePermission(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.tv.ITvInputService
        public void notifyHardwareAdded(android.media.tv.TvInputHardwareInfo tvInputHardwareInfo) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputService
        public void notifyHardwareRemoved(android.media.tv.TvInputHardwareInfo tvInputHardwareInfo) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputService
        public void notifyHdmiDeviceAdded(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputService
        public void notifyHdmiDeviceRemoved(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputService
        public void notifyHdmiDeviceUpdated(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.tv.ITvInputService {
        public static final java.lang.String DESCRIPTOR = "android.media.tv.ITvInputService";
        static final int TRANSACTION_createRecordingSession = 4;
        static final int TRANSACTION_createSession = 3;
        static final int TRANSACTION_getAvailableExtensionInterfaceNames = 5;
        static final int TRANSACTION_getExtensionInterface = 6;
        static final int TRANSACTION_getExtensionInterfacePermission = 7;
        static final int TRANSACTION_notifyHardwareAdded = 8;
        static final int TRANSACTION_notifyHardwareRemoved = 9;
        static final int TRANSACTION_notifyHdmiDeviceAdded = 10;
        static final int TRANSACTION_notifyHdmiDeviceRemoved = 11;
        static final int TRANSACTION_notifyHdmiDeviceUpdated = 12;
        static final int TRANSACTION_registerCallback = 1;
        static final int TRANSACTION_unregisterCallback = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.media.tv.ITvInputService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.tv.ITvInputService)) {
                return (android.media.tv.ITvInputService) queryLocalInterface;
            }
            return new android.media.tv.ITvInputService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "registerCallback";
                case 2:
                    return "unregisterCallback";
                case 3:
                    return "createSession";
                case 4:
                    return "createRecordingSession";
                case 5:
                    return "getAvailableExtensionInterfaceNames";
                case 6:
                    return "getExtensionInterface";
                case 7:
                    return "getExtensionInterfacePermission";
                case 8:
                    return "notifyHardwareAdded";
                case 9:
                    return "notifyHardwareRemoved";
                case 10:
                    return "notifyHdmiDeviceAdded";
                case 11:
                    return "notifyHdmiDeviceRemoved";
                case 12:
                    return "notifyHdmiDeviceUpdated";
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
                    android.media.tv.ITvInputServiceCallback asInterface = android.media.tv.ITvInputServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerCallback(asInterface);
                    return true;
                case 2:
                    android.media.tv.ITvInputServiceCallback asInterface2 = android.media.tv.ITvInputServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterCallback(asInterface2);
                    return true;
                case 3:
                    android.view.InputChannel inputChannel = (android.view.InputChannel) parcel.readTypedObject(android.view.InputChannel.CREATOR);
                    android.media.tv.ITvInputSessionCallback asInterface3 = android.media.tv.ITvInputSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString = parcel.readString();
                    java.lang.String readString2 = parcel.readString();
                    android.content.AttributionSource attributionSource = (android.content.AttributionSource) parcel.readTypedObject(android.content.AttributionSource.CREATOR);
                    parcel.enforceNoDataAvail();
                    createSession(inputChannel, asInterface3, readString, readString2, attributionSource);
                    return true;
                case 4:
                    android.media.tv.ITvInputSessionCallback asInterface4 = android.media.tv.ITvInputSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString3 = parcel.readString();
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    createRecordingSession(asInterface4, readString3, readString4);
                    return true;
                case 5:
                    java.util.List<java.lang.String> availableExtensionInterfaceNames = getAvailableExtensionInterfaceNames();
                    parcel2.writeNoException();
                    parcel2.writeStringList(availableExtensionInterfaceNames);
                    return true;
                case 6:
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.os.IBinder extensionInterface = getExtensionInterface(readString5);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(extensionInterface);
                    return true;
                case 7:
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String extensionInterfacePermission = getExtensionInterfacePermission(readString6);
                    parcel2.writeNoException();
                    parcel2.writeString(extensionInterfacePermission);
                    return true;
                case 8:
                    android.media.tv.TvInputHardwareInfo tvInputHardwareInfo = (android.media.tv.TvInputHardwareInfo) parcel.readTypedObject(android.media.tv.TvInputHardwareInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyHardwareAdded(tvInputHardwareInfo);
                    return true;
                case 9:
                    android.media.tv.TvInputHardwareInfo tvInputHardwareInfo2 = (android.media.tv.TvInputHardwareInfo) parcel.readTypedObject(android.media.tv.TvInputHardwareInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyHardwareRemoved(tvInputHardwareInfo2);
                    return true;
                case 10:
                    android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo = (android.hardware.hdmi.HdmiDeviceInfo) parcel.readTypedObject(android.hardware.hdmi.HdmiDeviceInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyHdmiDeviceAdded(hdmiDeviceInfo);
                    return true;
                case 11:
                    android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo2 = (android.hardware.hdmi.HdmiDeviceInfo) parcel.readTypedObject(android.hardware.hdmi.HdmiDeviceInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyHdmiDeviceRemoved(hdmiDeviceInfo2);
                    return true;
                case 12:
                    android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo3 = (android.hardware.hdmi.HdmiDeviceInfo) parcel.readTypedObject(android.hardware.hdmi.HdmiDeviceInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyHdmiDeviceUpdated(hdmiDeviceInfo3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.tv.ITvInputService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.tv.ITvInputService.Stub.DESCRIPTOR;
            }

            @Override // android.media.tv.ITvInputService
            public void registerCallback(android.media.tv.ITvInputServiceCallback iTvInputServiceCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iTvInputServiceCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputService
            public void unregisterCallback(android.media.tv.ITvInputServiceCallback iTvInputServiceCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iTvInputServiceCallback);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputService
            public void createSession(android.view.InputChannel inputChannel, android.media.tv.ITvInputSessionCallback iTvInputSessionCallback, java.lang.String str, java.lang.String str2, android.content.AttributionSource attributionSource) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(inputChannel, 0);
                    obtain.writeStrongInterface(iTvInputSessionCallback);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(attributionSource, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputService
            public void createRecordingSession(android.media.tv.ITvInputSessionCallback iTvInputSessionCallback, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iTvInputSessionCallback);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputService
            public java.util.List<java.lang.String> getAvailableExtensionInterfaceNames() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputService.Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputService
            public android.os.IBinder getExtensionInterface(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputService
            public java.lang.String getExtensionInterfacePermission(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputService
            public void notifyHardwareAdded(android.media.tv.TvInputHardwareInfo tvInputHardwareInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(tvInputHardwareInfo, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputService
            public void notifyHardwareRemoved(android.media.tv.TvInputHardwareInfo tvInputHardwareInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(tvInputHardwareInfo, 0);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputService
            public void notifyHdmiDeviceAdded(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(hdmiDeviceInfo, 0);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputService
            public void notifyHdmiDeviceRemoved(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(hdmiDeviceInfo, 0);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputService
            public void notifyHdmiDeviceUpdated(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(hdmiDeviceInfo, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 11;
        }
    }
}
