package android.view;

/* loaded from: classes4.dex */
public interface IWindow extends android.os.IInterface {
    void closeSystemDialogs(java.lang.String str) throws android.os.RemoteException;

    void dispatchAppVisibility(boolean z) throws android.os.RemoteException;

    void dispatchDragEvent(android.view.DragEvent dragEvent) throws android.os.RemoteException;

    void dispatchGetNewSurface() throws android.os.RemoteException;

    void dispatchWallpaperCommand(java.lang.String str, int i, int i2, int i3, android.os.Bundle bundle, boolean z) throws android.os.RemoteException;

    void dispatchWallpaperOffsets(float f, float f2, float f3, float f4, float f5, boolean z) throws android.os.RemoteException;

    void dispatchWindowShown() throws android.os.RemoteException;

    void executeCommand(java.lang.String str, java.lang.String str2, android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException;

    void hideInsets(int i, boolean z, android.view.inputmethod.ImeTracker.Token token) throws android.os.RemoteException;

    void insetsControlChanged(android.view.InsetsState insetsState, android.view.InsetsSourceControl[] insetsSourceControlArr) throws android.os.RemoteException;

    void moved(int i, int i2) throws android.os.RemoteException;

    void requestAppKeyboardShortcuts(com.android.internal.os.IResultReceiver iResultReceiver, int i) throws android.os.RemoteException;

    void requestScrollCapture(android.view.IScrollCaptureResponseListener iScrollCaptureResponseListener) throws android.os.RemoteException;

    void resized(android.window.ClientWindowFrames clientWindowFrames, boolean z, android.util.MergedConfiguration mergedConfiguration, android.view.InsetsState insetsState, boolean z2, boolean z3, int i, int i2, boolean z4) throws android.os.RemoteException;

    void showInsets(int i, boolean z, android.view.inputmethod.ImeTracker.Token token) throws android.os.RemoteException;

    void updatePointerIcon(float f, float f2) throws android.os.RemoteException;

    public static class Default implements android.view.IWindow {
        @Override // android.view.IWindow
        public void executeCommand(java.lang.String str, java.lang.String str2, android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException {
        }

        @Override // android.view.IWindow
        public void resized(android.window.ClientWindowFrames clientWindowFrames, boolean z, android.util.MergedConfiguration mergedConfiguration, android.view.InsetsState insetsState, boolean z2, boolean z3, int i, int i2, boolean z4) throws android.os.RemoteException {
        }

        @Override // android.view.IWindow
        public void insetsControlChanged(android.view.InsetsState insetsState, android.view.InsetsSourceControl[] insetsSourceControlArr) throws android.os.RemoteException {
        }

        @Override // android.view.IWindow
        public void showInsets(int i, boolean z, android.view.inputmethod.ImeTracker.Token token) throws android.os.RemoteException {
        }

        @Override // android.view.IWindow
        public void hideInsets(int i, boolean z, android.view.inputmethod.ImeTracker.Token token) throws android.os.RemoteException {
        }

        @Override // android.view.IWindow
        public void moved(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.view.IWindow
        public void dispatchAppVisibility(boolean z) throws android.os.RemoteException {
        }

        @Override // android.view.IWindow
        public void dispatchGetNewSurface() throws android.os.RemoteException {
        }

        @Override // android.view.IWindow
        public void closeSystemDialogs(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.view.IWindow
        public void dispatchWallpaperOffsets(float f, float f2, float f3, float f4, float f5, boolean z) throws android.os.RemoteException {
        }

        @Override // android.view.IWindow
        public void dispatchWallpaperCommand(java.lang.String str, int i, int i2, int i3, android.os.Bundle bundle, boolean z) throws android.os.RemoteException {
        }

        @Override // android.view.IWindow
        public void dispatchDragEvent(android.view.DragEvent dragEvent) throws android.os.RemoteException {
        }

        @Override // android.view.IWindow
        public void updatePointerIcon(float f, float f2) throws android.os.RemoteException {
        }

        @Override // android.view.IWindow
        public void dispatchWindowShown() throws android.os.RemoteException {
        }

        @Override // android.view.IWindow
        public void requestAppKeyboardShortcuts(com.android.internal.os.IResultReceiver iResultReceiver, int i) throws android.os.RemoteException {
        }

        @Override // android.view.IWindow
        public void requestScrollCapture(android.view.IScrollCaptureResponseListener iScrollCaptureResponseListener) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.IWindow {
        public static final java.lang.String DESCRIPTOR = "android.view.IWindow";
        static final int TRANSACTION_closeSystemDialogs = 9;
        static final int TRANSACTION_dispatchAppVisibility = 7;
        static final int TRANSACTION_dispatchDragEvent = 12;
        static final int TRANSACTION_dispatchGetNewSurface = 8;
        static final int TRANSACTION_dispatchWallpaperCommand = 11;
        static final int TRANSACTION_dispatchWallpaperOffsets = 10;
        static final int TRANSACTION_dispatchWindowShown = 14;
        static final int TRANSACTION_executeCommand = 1;
        static final int TRANSACTION_hideInsets = 5;
        static final int TRANSACTION_insetsControlChanged = 3;
        static final int TRANSACTION_moved = 6;
        static final int TRANSACTION_requestAppKeyboardShortcuts = 15;
        static final int TRANSACTION_requestScrollCapture = 16;
        static final int TRANSACTION_resized = 2;
        static final int TRANSACTION_showInsets = 4;
        static final int TRANSACTION_updatePointerIcon = 13;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.view.IWindow asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.IWindow)) {
                return (android.view.IWindow) queryLocalInterface;
            }
            return new android.view.IWindow.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "executeCommand";
                case 2:
                    return "resized";
                case 3:
                    return "insetsControlChanged";
                case 4:
                    return "showInsets";
                case 5:
                    return "hideInsets";
                case 6:
                    return "moved";
                case 7:
                    return "dispatchAppVisibility";
                case 8:
                    return "dispatchGetNewSurface";
                case 9:
                    return "closeSystemDialogs";
                case 10:
                    return "dispatchWallpaperOffsets";
                case 11:
                    return "dispatchWallpaperCommand";
                case 12:
                    return "dispatchDragEvent";
                case 13:
                    return "updatePointerIcon";
                case 14:
                    return "dispatchWindowShown";
                case 15:
                    return "requestAppKeyboardShortcuts";
                case 16:
                    return "requestScrollCapture";
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
                    java.lang.String readString = parcel.readString();
                    java.lang.String readString2 = parcel.readString();
                    android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    executeCommand(readString, readString2, parcelFileDescriptor);
                    return true;
                case 2:
                    android.window.ClientWindowFrames clientWindowFrames = (android.window.ClientWindowFrames) parcel.readTypedObject(android.window.ClientWindowFrames.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    android.util.MergedConfiguration mergedConfiguration = (android.util.MergedConfiguration) parcel.readTypedObject(android.util.MergedConfiguration.CREATOR);
                    android.view.InsetsState insetsState = (android.view.InsetsState) parcel.readTypedObject(android.view.InsetsState.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    boolean readBoolean3 = parcel.readBoolean();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    resized(clientWindowFrames, readBoolean, mergedConfiguration, insetsState, readBoolean2, readBoolean3, readInt, readInt2, readBoolean4);
                    return true;
                case 3:
                    android.view.InsetsState insetsState2 = (android.view.InsetsState) parcel.readTypedObject(android.view.InsetsState.CREATOR);
                    android.view.InsetsSourceControl[] insetsSourceControlArr = (android.view.InsetsSourceControl[]) parcel.createTypedArray(android.view.InsetsSourceControl.CREATOR);
                    parcel.enforceNoDataAvail();
                    insetsControlChanged(insetsState2, insetsSourceControlArr);
                    return true;
                case 4:
                    int readInt3 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    android.view.inputmethod.ImeTracker.Token token = (android.view.inputmethod.ImeTracker.Token) parcel.readTypedObject(android.view.inputmethod.ImeTracker.Token.CREATOR);
                    parcel.enforceNoDataAvail();
                    showInsets(readInt3, readBoolean5, token);
                    return true;
                case 5:
                    int readInt4 = parcel.readInt();
                    boolean readBoolean6 = parcel.readBoolean();
                    android.view.inputmethod.ImeTracker.Token token2 = (android.view.inputmethod.ImeTracker.Token) parcel.readTypedObject(android.view.inputmethod.ImeTracker.Token.CREATOR);
                    parcel.enforceNoDataAvail();
                    hideInsets(readInt4, readBoolean6, token2);
                    return true;
                case 6:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    moved(readInt5, readInt6);
                    return true;
                case 7:
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    dispatchAppVisibility(readBoolean7);
                    return true;
                case 8:
                    dispatchGetNewSurface();
                    return true;
                case 9:
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    closeSystemDialogs(readString3);
                    return true;
                case 10:
                    float readFloat = parcel.readFloat();
                    float readFloat2 = parcel.readFloat();
                    float readFloat3 = parcel.readFloat();
                    float readFloat4 = parcel.readFloat();
                    float readFloat5 = parcel.readFloat();
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    dispatchWallpaperOffsets(readFloat, readFloat2, readFloat3, readFloat4, readFloat5, readBoolean8);
                    return true;
                case 11:
                    java.lang.String readString4 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    dispatchWallpaperCommand(readString4, readInt7, readInt8, readInt9, bundle, readBoolean9);
                    return true;
                case 12:
                    android.view.DragEvent dragEvent = (android.view.DragEvent) parcel.readTypedObject(android.view.DragEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    dispatchDragEvent(dragEvent);
                    return true;
                case 13:
                    float readFloat6 = parcel.readFloat();
                    float readFloat7 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    updatePointerIcon(readFloat6, readFloat7);
                    return true;
                case 14:
                    dispatchWindowShown();
                    return true;
                case 15:
                    com.android.internal.os.IResultReceiver asInterface = com.android.internal.os.IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestAppKeyboardShortcuts(asInterface, readInt10);
                    return true;
                case 16:
                    android.view.IScrollCaptureResponseListener asInterface2 = android.view.IScrollCaptureResponseListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestScrollCapture(asInterface2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.IWindow {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.IWindow.Stub.DESCRIPTOR;
            }

            @Override // android.view.IWindow
            public void executeCommand(java.lang.String str, java.lang.String str2, android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindow.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindow
            public void resized(android.window.ClientWindowFrames clientWindowFrames, boolean z, android.util.MergedConfiguration mergedConfiguration, android.view.InsetsState insetsState, boolean z2, boolean z3, int i, int i2, boolean z4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindow.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(clientWindowFrames, 0);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(mergedConfiguration, 0);
                    obtain.writeTypedObject(insetsState, 0);
                    obtain.writeBoolean(z2);
                    obtain.writeBoolean(z3);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z4);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindow
            public void insetsControlChanged(android.view.InsetsState insetsState, android.view.InsetsSourceControl[] insetsSourceControlArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindow.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(insetsState, 0);
                    obtain.writeTypedArray(insetsSourceControlArr, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindow
            public void showInsets(int i, boolean z, android.view.inputmethod.ImeTracker.Token token) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindow.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(token, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindow
            public void hideInsets(int i, boolean z, android.view.inputmethod.ImeTracker.Token token) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindow.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(token, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindow
            public void moved(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindow.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindow
            public void dispatchAppVisibility(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindow.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindow
            public void dispatchGetNewSurface() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindow.Stub.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindow
            public void closeSystemDialogs(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindow.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindow
            public void dispatchWallpaperOffsets(float f, float f2, float f3, float f4, float f5, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindow.Stub.DESCRIPTOR);
                    obtain.writeFloat(f);
                    obtain.writeFloat(f2);
                    obtain.writeFloat(f3);
                    obtain.writeFloat(f4);
                    obtain.writeFloat(f5);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindow
            public void dispatchWallpaperCommand(java.lang.String str, int i, int i2, int i3, android.os.Bundle bundle, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindow.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindow
            public void dispatchDragEvent(android.view.DragEvent dragEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindow.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(dragEvent, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindow
            public void updatePointerIcon(float f, float f2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindow.Stub.DESCRIPTOR);
                    obtain.writeFloat(f);
                    obtain.writeFloat(f2);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindow
            public void dispatchWindowShown() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindow.Stub.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindow
            public void requestAppKeyboardShortcuts(com.android.internal.os.IResultReceiver iResultReceiver, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindow.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultReceiver);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindow
            public void requestScrollCapture(android.view.IScrollCaptureResponseListener iScrollCaptureResponseListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindow.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iScrollCaptureResponseListener);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 15;
        }
    }
}
