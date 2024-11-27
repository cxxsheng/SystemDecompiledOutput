package android.view;

/* loaded from: classes4.dex */
public interface IWindowSession extends android.os.IInterface {
    int addToDisplay(android.view.IWindow iWindow, android.view.WindowManager.LayoutParams layoutParams, int i, int i2, int i3, android.view.InputChannel inputChannel, android.view.InsetsState insetsState, android.view.InsetsSourceControl.Array array, android.graphics.Rect rect, float[] fArr) throws android.os.RemoteException;

    int addToDisplayAsUser(android.view.IWindow iWindow, android.view.WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, android.view.InputChannel inputChannel, android.view.InsetsState insetsState, android.view.InsetsSourceControl.Array array, android.graphics.Rect rect, float[] fArr) throws android.os.RemoteException;

    int addToDisplayWithoutInputChannel(android.view.IWindow iWindow, android.view.WindowManager.LayoutParams layoutParams, int i, int i2, android.view.InsetsState insetsState, android.graphics.Rect rect, float[] fArr) throws android.os.RemoteException;

    void cancelDragAndDrop(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException;

    boolean cancelDraw(android.view.IWindow iWindow) throws android.os.RemoteException;

    void clearTouchableRegion(android.view.IWindow iWindow) throws android.os.RemoteException;

    void dragRecipientEntered(android.view.IWindow iWindow) throws android.os.RemoteException;

    void dragRecipientExited(android.view.IWindow iWindow) throws android.os.RemoteException;

    boolean dropForAccessibility(android.view.IWindow iWindow, int i, int i2) throws android.os.RemoteException;

    void finishDrawing(android.view.IWindow iWindow, android.view.SurfaceControl.Transaction transaction, int i) throws android.os.RemoteException;

    void finishMovingTask(android.view.IWindow iWindow) throws android.os.RemoteException;

    void generateDisplayHash(android.view.IWindow iWindow, android.graphics.Rect rect, java.lang.String str, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    android.view.IWindowId getWindowId(android.os.IBinder iBinder) throws android.os.RemoteException;

    void grantEmbeddedWindowFocus(android.view.IWindow iWindow, android.window.InputTransferToken inputTransferToken, boolean z) throws android.os.RemoteException;

    void grantInputChannel(int i, android.view.SurfaceControl surfaceControl, android.os.IBinder iBinder, android.window.InputTransferToken inputTransferToken, int i2, int i3, int i4, int i5, android.os.IBinder iBinder2, android.window.InputTransferToken inputTransferToken2, java.lang.String str, android.view.InputChannel inputChannel) throws android.os.RemoteException;

    boolean moveFocusToAdjacentWindow(android.view.IWindow iWindow, int i) throws android.os.RemoteException;

    void onRectangleOnScreenRequested(android.os.IBinder iBinder, android.graphics.Rect rect) throws android.os.RemoteException;

    boolean outOfMemory(android.view.IWindow iWindow) throws android.os.RemoteException;

    android.os.IBinder performDrag(android.view.IWindow iWindow, int i, android.view.SurfaceControl surfaceControl, int i2, int i3, int i4, float f, float f2, float f3, float f4, android.content.ClipData clipData) throws android.os.RemoteException;

    boolean performHapticFeedback(int i, boolean z, boolean z2) throws android.os.RemoteException;

    void performHapticFeedbackAsync(int i, boolean z, boolean z2) throws android.os.RemoteException;

    void pokeDrawLock(android.os.IBinder iBinder) throws android.os.RemoteException;

    int relayout(android.view.IWindow iWindow, android.view.WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, int i5, int i6, android.window.ClientWindowFrames clientWindowFrames, android.util.MergedConfiguration mergedConfiguration, android.view.SurfaceControl surfaceControl, android.view.InsetsState insetsState, android.view.InsetsSourceControl.Array array, android.os.Bundle bundle) throws android.os.RemoteException;

    void relayoutAsync(android.view.IWindow iWindow, android.view.WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, int i5, int i6) throws android.os.RemoteException;

    void remove(android.os.IBinder iBinder) throws android.os.RemoteException;

    void reportDecorViewGestureInterceptionChanged(android.view.IWindow iWindow, boolean z) throws android.os.RemoteException;

    void reportDropResult(android.view.IWindow iWindow, boolean z) throws android.os.RemoteException;

    void reportKeepClearAreasChanged(android.view.IWindow iWindow, java.util.List<android.graphics.Rect> list, java.util.List<android.graphics.Rect> list2) throws android.os.RemoteException;

    void reportSystemGestureExclusionChanged(android.view.IWindow iWindow, java.util.List<android.graphics.Rect> list) throws android.os.RemoteException;

    void sendWallpaperCommand(android.os.IBinder iBinder, java.lang.String str, int i, int i2, int i3, android.os.Bundle bundle, boolean z) throws android.os.RemoteException;

    void setInsets(android.view.IWindow iWindow, int i, android.graphics.Rect rect, android.graphics.Rect rect2, android.graphics.Region region) throws android.os.RemoteException;

    void setOnBackInvokedCallbackInfo(android.view.IWindow iWindow, android.window.OnBackInvokedCallbackInfo onBackInvokedCallbackInfo) throws android.os.RemoteException;

    void setShouldZoomOutWallpaper(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException;

    void setWallpaperDisplayOffset(android.os.IBinder iBinder, int i, int i2) throws android.os.RemoteException;

    void setWallpaperPosition(android.os.IBinder iBinder, float f, float f2, float f3, float f4) throws android.os.RemoteException;

    void setWallpaperZoomOut(android.os.IBinder iBinder, float f) throws android.os.RemoteException;

    boolean startMovingTask(android.view.IWindow iWindow, float f, float f2) throws android.os.RemoteException;

    void updateInputChannel(android.os.IBinder iBinder, int i, android.view.SurfaceControl surfaceControl, int i2, int i3, int i4, android.graphics.Region region) throws android.os.RemoteException;

    void updatePointerIcon(android.view.IWindow iWindow) throws android.os.RemoteException;

    void updateRequestedVisibleTypes(android.view.IWindow iWindow, int i) throws android.os.RemoteException;

    void updateTapExcludeRegion(android.view.IWindow iWindow, android.graphics.Region region) throws android.os.RemoteException;

    void wallpaperCommandComplete(android.os.IBinder iBinder, android.os.Bundle bundle) throws android.os.RemoteException;

    void wallpaperOffsetsComplete(android.os.IBinder iBinder) throws android.os.RemoteException;

    public static class Default implements android.view.IWindowSession {
        @Override // android.view.IWindowSession
        public int addToDisplay(android.view.IWindow iWindow, android.view.WindowManager.LayoutParams layoutParams, int i, int i2, int i3, android.view.InputChannel inputChannel, android.view.InsetsState insetsState, android.view.InsetsSourceControl.Array array, android.graphics.Rect rect, float[] fArr) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.view.IWindowSession
        public int addToDisplayAsUser(android.view.IWindow iWindow, android.view.WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, android.view.InputChannel inputChannel, android.view.InsetsState insetsState, android.view.InsetsSourceControl.Array array, android.graphics.Rect rect, float[] fArr) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.view.IWindowSession
        public int addToDisplayWithoutInputChannel(android.view.IWindow iWindow, android.view.WindowManager.LayoutParams layoutParams, int i, int i2, android.view.InsetsState insetsState, android.graphics.Rect rect, float[] fArr) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.view.IWindowSession
        public void remove(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowSession
        public int relayout(android.view.IWindow iWindow, android.view.WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, int i5, int i6, android.window.ClientWindowFrames clientWindowFrames, android.util.MergedConfiguration mergedConfiguration, android.view.SurfaceControl surfaceControl, android.view.InsetsState insetsState, android.view.InsetsSourceControl.Array array, android.os.Bundle bundle) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.view.IWindowSession
        public void relayoutAsync(android.view.IWindow iWindow, android.view.WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, int i5, int i6) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowSession
        public boolean outOfMemory(android.view.IWindow iWindow) throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.IWindowSession
        public void setInsets(android.view.IWindow iWindow, int i, android.graphics.Rect rect, android.graphics.Rect rect2, android.graphics.Region region) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowSession
        public void finishDrawing(android.view.IWindow iWindow, android.view.SurfaceControl.Transaction transaction, int i) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowSession
        public boolean performHapticFeedback(int i, boolean z, boolean z2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.IWindowSession
        public void performHapticFeedbackAsync(int i, boolean z, boolean z2) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowSession
        public android.os.IBinder performDrag(android.view.IWindow iWindow, int i, android.view.SurfaceControl surfaceControl, int i2, int i3, int i4, float f, float f2, float f3, float f4, android.content.ClipData clipData) throws android.os.RemoteException {
            return null;
        }

        @Override // android.view.IWindowSession
        public boolean dropForAccessibility(android.view.IWindow iWindow, int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.IWindowSession
        public void reportDropResult(android.view.IWindow iWindow, boolean z) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowSession
        public void cancelDragAndDrop(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowSession
        public void dragRecipientEntered(android.view.IWindow iWindow) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowSession
        public void dragRecipientExited(android.view.IWindow iWindow) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowSession
        public void setWallpaperPosition(android.os.IBinder iBinder, float f, float f2, float f3, float f4) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowSession
        public void setWallpaperZoomOut(android.os.IBinder iBinder, float f) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowSession
        public void setShouldZoomOutWallpaper(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowSession
        public void wallpaperOffsetsComplete(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowSession
        public void setWallpaperDisplayOffset(android.os.IBinder iBinder, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowSession
        public void sendWallpaperCommand(android.os.IBinder iBinder, java.lang.String str, int i, int i2, int i3, android.os.Bundle bundle, boolean z) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowSession
        public void wallpaperCommandComplete(android.os.IBinder iBinder, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowSession
        public void onRectangleOnScreenRequested(android.os.IBinder iBinder, android.graphics.Rect rect) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowSession
        public android.view.IWindowId getWindowId(android.os.IBinder iBinder) throws android.os.RemoteException {
            return null;
        }

        @Override // android.view.IWindowSession
        public void pokeDrawLock(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowSession
        public boolean startMovingTask(android.view.IWindow iWindow, float f, float f2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.IWindowSession
        public void finishMovingTask(android.view.IWindow iWindow) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowSession
        public void updatePointerIcon(android.view.IWindow iWindow) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowSession
        public void updateTapExcludeRegion(android.view.IWindow iWindow, android.graphics.Region region) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowSession
        public void updateRequestedVisibleTypes(android.view.IWindow iWindow, int i) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowSession
        public void reportSystemGestureExclusionChanged(android.view.IWindow iWindow, java.util.List<android.graphics.Rect> list) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowSession
        public void reportDecorViewGestureInterceptionChanged(android.view.IWindow iWindow, boolean z) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowSession
        public void reportKeepClearAreasChanged(android.view.IWindow iWindow, java.util.List<android.graphics.Rect> list, java.util.List<android.graphics.Rect> list2) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowSession
        public void grantInputChannel(int i, android.view.SurfaceControl surfaceControl, android.os.IBinder iBinder, android.window.InputTransferToken inputTransferToken, int i2, int i3, int i4, int i5, android.os.IBinder iBinder2, android.window.InputTransferToken inputTransferToken2, java.lang.String str, android.view.InputChannel inputChannel) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowSession
        public void updateInputChannel(android.os.IBinder iBinder, int i, android.view.SurfaceControl surfaceControl, int i2, int i3, int i4, android.graphics.Region region) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowSession
        public void grantEmbeddedWindowFocus(android.view.IWindow iWindow, android.window.InputTransferToken inputTransferToken, boolean z) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowSession
        public void generateDisplayHash(android.view.IWindow iWindow, android.graphics.Rect rect, java.lang.String str, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowSession
        public void setOnBackInvokedCallbackInfo(android.view.IWindow iWindow, android.window.OnBackInvokedCallbackInfo onBackInvokedCallbackInfo) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowSession
        public void clearTouchableRegion(android.view.IWindow iWindow) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowSession
        public boolean cancelDraw(android.view.IWindow iWindow) throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.IWindowSession
        public boolean moveFocusToAdjacentWindow(android.view.IWindow iWindow, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.IWindowSession {
        public static final java.lang.String DESCRIPTOR = "android.view.IWindowSession";
        static final int TRANSACTION_addToDisplay = 1;
        static final int TRANSACTION_addToDisplayAsUser = 2;
        static final int TRANSACTION_addToDisplayWithoutInputChannel = 3;
        static final int TRANSACTION_cancelDragAndDrop = 15;
        static final int TRANSACTION_cancelDraw = 42;
        static final int TRANSACTION_clearTouchableRegion = 41;
        static final int TRANSACTION_dragRecipientEntered = 16;
        static final int TRANSACTION_dragRecipientExited = 17;
        static final int TRANSACTION_dropForAccessibility = 13;
        static final int TRANSACTION_finishDrawing = 9;
        static final int TRANSACTION_finishMovingTask = 29;
        static final int TRANSACTION_generateDisplayHash = 39;
        static final int TRANSACTION_getWindowId = 26;
        static final int TRANSACTION_grantEmbeddedWindowFocus = 38;
        static final int TRANSACTION_grantInputChannel = 36;
        static final int TRANSACTION_moveFocusToAdjacentWindow = 43;
        static final int TRANSACTION_onRectangleOnScreenRequested = 25;
        static final int TRANSACTION_outOfMemory = 7;
        static final int TRANSACTION_performDrag = 12;
        static final int TRANSACTION_performHapticFeedback = 10;
        static final int TRANSACTION_performHapticFeedbackAsync = 11;
        static final int TRANSACTION_pokeDrawLock = 27;
        static final int TRANSACTION_relayout = 5;
        static final int TRANSACTION_relayoutAsync = 6;
        static final int TRANSACTION_remove = 4;
        static final int TRANSACTION_reportDecorViewGestureInterceptionChanged = 34;
        static final int TRANSACTION_reportDropResult = 14;
        static final int TRANSACTION_reportKeepClearAreasChanged = 35;
        static final int TRANSACTION_reportSystemGestureExclusionChanged = 33;
        static final int TRANSACTION_sendWallpaperCommand = 23;
        static final int TRANSACTION_setInsets = 8;
        static final int TRANSACTION_setOnBackInvokedCallbackInfo = 40;
        static final int TRANSACTION_setShouldZoomOutWallpaper = 20;
        static final int TRANSACTION_setWallpaperDisplayOffset = 22;
        static final int TRANSACTION_setWallpaperPosition = 18;
        static final int TRANSACTION_setWallpaperZoomOut = 19;
        static final int TRANSACTION_startMovingTask = 28;
        static final int TRANSACTION_updateInputChannel = 37;
        static final int TRANSACTION_updatePointerIcon = 30;
        static final int TRANSACTION_updateRequestedVisibleTypes = 32;
        static final int TRANSACTION_updateTapExcludeRegion = 31;
        static final int TRANSACTION_wallpaperCommandComplete = 24;
        static final int TRANSACTION_wallpaperOffsetsComplete = 21;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.view.IWindowSession asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.IWindowSession)) {
                return (android.view.IWindowSession) queryLocalInterface;
            }
            return new android.view.IWindowSession.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "addToDisplay";
                case 2:
                    return "addToDisplayAsUser";
                case 3:
                    return "addToDisplayWithoutInputChannel";
                case 4:
                    return "remove";
                case 5:
                    return "relayout";
                case 6:
                    return "relayoutAsync";
                case 7:
                    return "outOfMemory";
                case 8:
                    return "setInsets";
                case 9:
                    return "finishDrawing";
                case 10:
                    return "performHapticFeedback";
                case 11:
                    return "performHapticFeedbackAsync";
                case 12:
                    return "performDrag";
                case 13:
                    return "dropForAccessibility";
                case 14:
                    return "reportDropResult";
                case 15:
                    return "cancelDragAndDrop";
                case 16:
                    return "dragRecipientEntered";
                case 17:
                    return "dragRecipientExited";
                case 18:
                    return "setWallpaperPosition";
                case 19:
                    return "setWallpaperZoomOut";
                case 20:
                    return "setShouldZoomOutWallpaper";
                case 21:
                    return "wallpaperOffsetsComplete";
                case 22:
                    return "setWallpaperDisplayOffset";
                case 23:
                    return "sendWallpaperCommand";
                case 24:
                    return "wallpaperCommandComplete";
                case 25:
                    return "onRectangleOnScreenRequested";
                case 26:
                    return "getWindowId";
                case 27:
                    return "pokeDrawLock";
                case 28:
                    return "startMovingTask";
                case 29:
                    return "finishMovingTask";
                case 30:
                    return "updatePointerIcon";
                case 31:
                    return "updateTapExcludeRegion";
                case 32:
                    return "updateRequestedVisibleTypes";
                case 33:
                    return "reportSystemGestureExclusionChanged";
                case 34:
                    return "reportDecorViewGestureInterceptionChanged";
                case 35:
                    return "reportKeepClearAreasChanged";
                case 36:
                    return "grantInputChannel";
                case 37:
                    return "updateInputChannel";
                case 38:
                    return "grantEmbeddedWindowFocus";
                case 39:
                    return "generateDisplayHash";
                case 40:
                    return "setOnBackInvokedCallbackInfo";
                case 41:
                    return "clearTouchableRegion";
                case 42:
                    return "cancelDraw";
                case 43:
                    return "moveFocusToAdjacentWindow";
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
            float[] fArr;
            float[] fArr2;
            float[] fArr3;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.view.IWindow asInterface = android.view.IWindow.Stub.asInterface(parcel.readStrongBinder());
                    android.view.WindowManager.LayoutParams layoutParams = (android.view.WindowManager.LayoutParams) parcel.readTypedObject(android.view.WindowManager.LayoutParams.CREATOR);
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    android.view.InputChannel inputChannel = new android.view.InputChannel();
                    android.view.InsetsState insetsState = new android.view.InsetsState();
                    android.view.InsetsSourceControl.Array array = new android.view.InsetsSourceControl.Array();
                    android.graphics.Rect rect = new android.graphics.Rect();
                    int readInt4 = parcel.readInt();
                    if (readInt4 < 0) {
                        fArr = null;
                    } else {
                        fArr = new float[readInt4];
                    }
                    parcel.enforceNoDataAvail();
                    float[] fArr4 = fArr;
                    int addToDisplay = addToDisplay(asInterface, layoutParams, readInt, readInt2, readInt3, inputChannel, insetsState, array, rect, fArr4);
                    parcel2.writeNoException();
                    parcel2.writeInt(addToDisplay);
                    parcel2.writeTypedObject(inputChannel, 1);
                    parcel2.writeTypedObject(insetsState, 1);
                    parcel2.writeTypedObject(array, 1);
                    parcel2.writeTypedObject(rect, 1);
                    parcel2.writeFloatArray(fArr4);
                    return true;
                case 2:
                    android.view.IWindow asInterface2 = android.view.IWindow.Stub.asInterface(parcel.readStrongBinder());
                    android.view.WindowManager.LayoutParams layoutParams2 = (android.view.WindowManager.LayoutParams) parcel.readTypedObject(android.view.WindowManager.LayoutParams.CREATOR);
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    android.view.InputChannel inputChannel2 = new android.view.InputChannel();
                    android.view.InsetsState insetsState2 = new android.view.InsetsState();
                    android.view.InsetsSourceControl.Array array2 = new android.view.InsetsSourceControl.Array();
                    android.graphics.Rect rect2 = new android.graphics.Rect();
                    int readInt9 = parcel.readInt();
                    if (readInt9 < 0) {
                        fArr2 = null;
                    } else {
                        fArr2 = new float[readInt9];
                    }
                    parcel.enforceNoDataAvail();
                    float[] fArr5 = fArr2;
                    int addToDisplayAsUser = addToDisplayAsUser(asInterface2, layoutParams2, readInt5, readInt6, readInt7, readInt8, inputChannel2, insetsState2, array2, rect2, fArr5);
                    parcel2.writeNoException();
                    parcel2.writeInt(addToDisplayAsUser);
                    parcel2.writeTypedObject(inputChannel2, 1);
                    parcel2.writeTypedObject(insetsState2, 1);
                    parcel2.writeTypedObject(array2, 1);
                    parcel2.writeTypedObject(rect2, 1);
                    parcel2.writeFloatArray(fArr5);
                    return true;
                case 3:
                    android.view.IWindow asInterface3 = android.view.IWindow.Stub.asInterface(parcel.readStrongBinder());
                    android.view.WindowManager.LayoutParams layoutParams3 = (android.view.WindowManager.LayoutParams) parcel.readTypedObject(android.view.WindowManager.LayoutParams.CREATOR);
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    android.view.InsetsState insetsState3 = new android.view.InsetsState();
                    android.graphics.Rect rect3 = new android.graphics.Rect();
                    int readInt12 = parcel.readInt();
                    if (readInt12 < 0) {
                        fArr3 = null;
                    } else {
                        fArr3 = new float[readInt12];
                    }
                    parcel.enforceNoDataAvail();
                    int addToDisplayWithoutInputChannel = addToDisplayWithoutInputChannel(asInterface3, layoutParams3, readInt10, readInt11, insetsState3, rect3, fArr3);
                    parcel2.writeNoException();
                    parcel2.writeInt(addToDisplayWithoutInputChannel);
                    parcel2.writeTypedObject(insetsState3, 1);
                    parcel2.writeTypedObject(rect3, 1);
                    parcel2.writeFloatArray(fArr3);
                    return true;
                case 4:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    remove(readStrongBinder);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    android.view.IWindow asInterface4 = android.view.IWindow.Stub.asInterface(parcel.readStrongBinder());
                    android.view.WindowManager.LayoutParams layoutParams4 = (android.view.WindowManager.LayoutParams) parcel.readTypedObject(android.view.WindowManager.LayoutParams.CREATOR);
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    android.window.ClientWindowFrames clientWindowFrames = new android.window.ClientWindowFrames();
                    android.util.MergedConfiguration mergedConfiguration = new android.util.MergedConfiguration();
                    android.view.SurfaceControl surfaceControl = new android.view.SurfaceControl();
                    android.view.InsetsState insetsState4 = new android.view.InsetsState();
                    android.view.InsetsSourceControl.Array array3 = new android.view.InsetsSourceControl.Array();
                    android.os.Bundle bundle = new android.os.Bundle();
                    parcel.enforceNoDataAvail();
                    int relayout = relayout(asInterface4, layoutParams4, readInt13, readInt14, readInt15, readInt16, readInt17, readInt18, clientWindowFrames, mergedConfiguration, surfaceControl, insetsState4, array3, bundle);
                    parcel2.writeNoException();
                    parcel2.writeInt(relayout);
                    parcel2.writeTypedObject(clientWindowFrames, 1);
                    parcel2.writeTypedObject(mergedConfiguration, 1);
                    parcel2.writeTypedObject(surfaceControl, 1);
                    parcel2.writeTypedObject(insetsState4, 1);
                    parcel2.writeTypedObject(array3, 1);
                    parcel2.writeTypedObject(bundle, 1);
                    return true;
                case 6:
                    android.view.IWindow asInterface5 = android.view.IWindow.Stub.asInterface(parcel.readStrongBinder());
                    android.view.WindowManager.LayoutParams layoutParams5 = (android.view.WindowManager.LayoutParams) parcel.readTypedObject(android.view.WindowManager.LayoutParams.CREATOR);
                    int readInt19 = parcel.readInt();
                    int readInt20 = parcel.readInt();
                    int readInt21 = parcel.readInt();
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    relayoutAsync(asInterface5, layoutParams5, readInt19, readInt20, readInt21, readInt22, readInt23, readInt24);
                    return true;
                case 7:
                    android.view.IWindow asInterface6 = android.view.IWindow.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean outOfMemory = outOfMemory(asInterface6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(outOfMemory);
                    return true;
                case 8:
                    android.view.IWindow asInterface7 = android.view.IWindow.Stub.asInterface(parcel.readStrongBinder());
                    int readInt25 = parcel.readInt();
                    android.graphics.Rect rect4 = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    android.graphics.Rect rect5 = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    android.graphics.Region region = (android.graphics.Region) parcel.readTypedObject(android.graphics.Region.CREATOR);
                    parcel.enforceNoDataAvail();
                    setInsets(asInterface7, readInt25, rect4, rect5, region);
                    return true;
                case 9:
                    android.view.IWindow asInterface8 = android.view.IWindow.Stub.asInterface(parcel.readStrongBinder());
                    android.view.SurfaceControl.Transaction transaction = (android.view.SurfaceControl.Transaction) parcel.readTypedObject(android.view.SurfaceControl.Transaction.CREATOR);
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    finishDrawing(asInterface8, transaction, readInt26);
                    return true;
                case 10:
                    int readInt27 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean performHapticFeedback = performHapticFeedback(readInt27, readBoolean, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(performHapticFeedback);
                    return true;
                case 11:
                    int readInt28 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    performHapticFeedbackAsync(readInt28, readBoolean3, readBoolean4);
                    return true;
                case 12:
                    android.view.IWindow asInterface9 = android.view.IWindow.Stub.asInterface(parcel.readStrongBinder());
                    int readInt29 = parcel.readInt();
                    android.view.SurfaceControl surfaceControl2 = (android.view.SurfaceControl) parcel.readTypedObject(android.view.SurfaceControl.CREATOR);
                    int readInt30 = parcel.readInt();
                    int readInt31 = parcel.readInt();
                    int readInt32 = parcel.readInt();
                    float readFloat = parcel.readFloat();
                    float readFloat2 = parcel.readFloat();
                    float readFloat3 = parcel.readFloat();
                    float readFloat4 = parcel.readFloat();
                    android.content.ClipData clipData = (android.content.ClipData) parcel.readTypedObject(android.content.ClipData.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.os.IBinder performDrag = performDrag(asInterface9, readInt29, surfaceControl2, readInt30, readInt31, readInt32, readFloat, readFloat2, readFloat3, readFloat4, clipData);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(performDrag);
                    return true;
                case 13:
                    android.view.IWindow asInterface10 = android.view.IWindow.Stub.asInterface(parcel.readStrongBinder());
                    int readInt33 = parcel.readInt();
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean dropForAccessibility = dropForAccessibility(asInterface10, readInt33, readInt34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dropForAccessibility);
                    return true;
                case 14:
                    android.view.IWindow asInterface11 = android.view.IWindow.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    reportDropResult(asInterface11, readBoolean5);
                    return true;
                case 15:
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    cancelDragAndDrop(readStrongBinder2, readBoolean6);
                    return true;
                case 16:
                    android.view.IWindow asInterface12 = android.view.IWindow.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    dragRecipientEntered(asInterface12);
                    return true;
                case 17:
                    android.view.IWindow asInterface13 = android.view.IWindow.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    dragRecipientExited(asInterface13);
                    return true;
                case 18:
                    android.os.IBinder readStrongBinder3 = parcel.readStrongBinder();
                    float readFloat5 = parcel.readFloat();
                    float readFloat6 = parcel.readFloat();
                    float readFloat7 = parcel.readFloat();
                    float readFloat8 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setWallpaperPosition(readStrongBinder3, readFloat5, readFloat6, readFloat7, readFloat8);
                    return true;
                case 19:
                    android.os.IBinder readStrongBinder4 = parcel.readStrongBinder();
                    float readFloat9 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setWallpaperZoomOut(readStrongBinder4, readFloat9);
                    return true;
                case 20:
                    android.os.IBinder readStrongBinder5 = parcel.readStrongBinder();
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setShouldZoomOutWallpaper(readStrongBinder5, readBoolean7);
                    return true;
                case 21:
                    android.os.IBinder readStrongBinder6 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    wallpaperOffsetsComplete(readStrongBinder6);
                    return true;
                case 22:
                    android.os.IBinder readStrongBinder7 = parcel.readStrongBinder();
                    int readInt35 = parcel.readInt();
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setWallpaperDisplayOffset(readStrongBinder7, readInt35, readInt36);
                    return true;
                case 23:
                    android.os.IBinder readStrongBinder8 = parcel.readStrongBinder();
                    java.lang.String readString = parcel.readString();
                    int readInt37 = parcel.readInt();
                    int readInt38 = parcel.readInt();
                    int readInt39 = parcel.readInt();
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    sendWallpaperCommand(readStrongBinder8, readString, readInt37, readInt38, readInt39, bundle2, readBoolean8);
                    return true;
                case 24:
                    android.os.IBinder readStrongBinder9 = parcel.readStrongBinder();
                    android.os.Bundle bundle3 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    wallpaperCommandComplete(readStrongBinder9, bundle3);
                    return true;
                case 25:
                    android.os.IBinder readStrongBinder10 = parcel.readStrongBinder();
                    android.graphics.Rect rect6 = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRectangleOnScreenRequested(readStrongBinder10, rect6);
                    return true;
                case 26:
                    android.os.IBinder readStrongBinder11 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    android.view.IWindowId windowId = getWindowId(readStrongBinder11);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(windowId);
                    return true;
                case 27:
                    android.os.IBinder readStrongBinder12 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    pokeDrawLock(readStrongBinder12);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    android.view.IWindow asInterface14 = android.view.IWindow.Stub.asInterface(parcel.readStrongBinder());
                    float readFloat10 = parcel.readFloat();
                    float readFloat11 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    boolean startMovingTask = startMovingTask(asInterface14, readFloat10, readFloat11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startMovingTask);
                    return true;
                case 29:
                    android.view.IWindow asInterface15 = android.view.IWindow.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    finishMovingTask(asInterface15);
                    return true;
                case 30:
                    android.view.IWindow asInterface16 = android.view.IWindow.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    updatePointerIcon(asInterface16);
                    return true;
                case 31:
                    android.view.IWindow asInterface17 = android.view.IWindow.Stub.asInterface(parcel.readStrongBinder());
                    android.graphics.Region region2 = (android.graphics.Region) parcel.readTypedObject(android.graphics.Region.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateTapExcludeRegion(asInterface17, region2);
                    return true;
                case 32:
                    android.view.IWindow asInterface18 = android.view.IWindow.Stub.asInterface(parcel.readStrongBinder());
                    int readInt40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateRequestedVisibleTypes(asInterface18, readInt40);
                    return true;
                case 33:
                    android.view.IWindow asInterface19 = android.view.IWindow.Stub.asInterface(parcel.readStrongBinder());
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.graphics.Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    reportSystemGestureExclusionChanged(asInterface19, createTypedArrayList);
                    return true;
                case 34:
                    android.view.IWindow asInterface20 = android.view.IWindow.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    reportDecorViewGestureInterceptionChanged(asInterface20, readBoolean9);
                    return true;
                case 35:
                    android.view.IWindow asInterface21 = android.view.IWindow.Stub.asInterface(parcel.readStrongBinder());
                    java.util.ArrayList createTypedArrayList2 = parcel.createTypedArrayList(android.graphics.Rect.CREATOR);
                    java.util.ArrayList createTypedArrayList3 = parcel.createTypedArrayList(android.graphics.Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    reportKeepClearAreasChanged(asInterface21, createTypedArrayList2, createTypedArrayList3);
                    return true;
                case 36:
                    int readInt41 = parcel.readInt();
                    android.view.SurfaceControl surfaceControl3 = (android.view.SurfaceControl) parcel.readTypedObject(android.view.SurfaceControl.CREATOR);
                    android.os.IBinder readStrongBinder13 = parcel.readStrongBinder();
                    android.window.InputTransferToken inputTransferToken = (android.window.InputTransferToken) parcel.readTypedObject(android.window.InputTransferToken.CREATOR);
                    int readInt42 = parcel.readInt();
                    int readInt43 = parcel.readInt();
                    int readInt44 = parcel.readInt();
                    int readInt45 = parcel.readInt();
                    android.os.IBinder readStrongBinder14 = parcel.readStrongBinder();
                    android.window.InputTransferToken inputTransferToken2 = (android.window.InputTransferToken) parcel.readTypedObject(android.window.InputTransferToken.CREATOR);
                    java.lang.String readString2 = parcel.readString();
                    android.view.InputChannel inputChannel3 = new android.view.InputChannel();
                    parcel.enforceNoDataAvail();
                    grantInputChannel(readInt41, surfaceControl3, readStrongBinder13, inputTransferToken, readInt42, readInt43, readInt44, readInt45, readStrongBinder14, inputTransferToken2, readString2, inputChannel3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(inputChannel3, 1);
                    return true;
                case 37:
                    android.os.IBinder readStrongBinder15 = parcel.readStrongBinder();
                    int readInt46 = parcel.readInt();
                    android.view.SurfaceControl surfaceControl4 = (android.view.SurfaceControl) parcel.readTypedObject(android.view.SurfaceControl.CREATOR);
                    int readInt47 = parcel.readInt();
                    int readInt48 = parcel.readInt();
                    int readInt49 = parcel.readInt();
                    android.graphics.Region region3 = (android.graphics.Region) parcel.readTypedObject(android.graphics.Region.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateInputChannel(readStrongBinder15, readInt46, surfaceControl4, readInt47, readInt48, readInt49, region3);
                    return true;
                case 38:
                    android.view.IWindow asInterface22 = android.view.IWindow.Stub.asInterface(parcel.readStrongBinder());
                    android.window.InputTransferToken inputTransferToken3 = (android.window.InputTransferToken) parcel.readTypedObject(android.window.InputTransferToken.CREATOR);
                    boolean readBoolean10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    grantEmbeddedWindowFocus(asInterface22, inputTransferToken3, readBoolean10);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    android.view.IWindow asInterface23 = android.view.IWindow.Stub.asInterface(parcel.readStrongBinder());
                    android.graphics.Rect rect7 = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    java.lang.String readString3 = parcel.readString();
                    android.os.RemoteCallback remoteCallback = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    generateDisplayHash(asInterface23, rect7, readString3, remoteCallback);
                    return true;
                case 40:
                    android.view.IWindow asInterface24 = android.view.IWindow.Stub.asInterface(parcel.readStrongBinder());
                    android.window.OnBackInvokedCallbackInfo onBackInvokedCallbackInfo = (android.window.OnBackInvokedCallbackInfo) parcel.readTypedObject(android.window.OnBackInvokedCallbackInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setOnBackInvokedCallbackInfo(asInterface24, onBackInvokedCallbackInfo);
                    return true;
                case 41:
                    android.view.IWindow asInterface25 = android.view.IWindow.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    clearTouchableRegion(asInterface25);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    android.view.IWindow asInterface26 = android.view.IWindow.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean cancelDraw = cancelDraw(asInterface26);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(cancelDraw);
                    return true;
                case 43:
                    android.view.IWindow asInterface27 = android.view.IWindow.Stub.asInterface(parcel.readStrongBinder());
                    int readInt50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean moveFocusToAdjacentWindow = moveFocusToAdjacentWindow(asInterface27, readInt50);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(moveFocusToAdjacentWindow);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.IWindowSession {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.IWindowSession.Stub.DESCRIPTOR;
            }

            @Override // android.view.IWindowSession
            public int addToDisplay(android.view.IWindow iWindow, android.view.WindowManager.LayoutParams layoutParams, int i, int i2, int i3, android.view.InputChannel inputChannel, android.view.InsetsState insetsState, android.view.InsetsSourceControl.Array array, android.graphics.Rect rect, float[] fArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeTypedObject(layoutParams, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(fArr.length);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    if (obtain2.readInt() != 0) {
                        inputChannel.readFromParcel(obtain2);
                    }
                    if (obtain2.readInt() != 0) {
                        insetsState.readFromParcel(obtain2);
                    }
                    if (obtain2.readInt() != 0) {
                        array.readFromParcel(obtain2);
                    }
                    if (obtain2.readInt() != 0) {
                        rect.readFromParcel(obtain2);
                    }
                    obtain2.readFloatArray(fArr);
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public int addToDisplayAsUser(android.view.IWindow iWindow, android.view.WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, android.view.InputChannel inputChannel, android.view.InsetsState insetsState, android.view.InsetsSourceControl.Array array, android.graphics.Rect rect, float[] fArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeTypedObject(layoutParams, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(fArr.length);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    if (obtain2.readInt() != 0) {
                        inputChannel.readFromParcel(obtain2);
                    }
                    if (obtain2.readInt() != 0) {
                        insetsState.readFromParcel(obtain2);
                    }
                    if (obtain2.readInt() != 0) {
                        array.readFromParcel(obtain2);
                    }
                    if (obtain2.readInt() != 0) {
                        rect.readFromParcel(obtain2);
                    }
                    obtain2.readFloatArray(fArr);
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public int addToDisplayWithoutInputChannel(android.view.IWindow iWindow, android.view.WindowManager.LayoutParams layoutParams, int i, int i2, android.view.InsetsState insetsState, android.graphics.Rect rect, float[] fArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeTypedObject(layoutParams, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(fArr.length);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    if (obtain2.readInt() != 0) {
                        insetsState.readFromParcel(obtain2);
                    }
                    if (obtain2.readInt() != 0) {
                        rect.readFromParcel(obtain2);
                    }
                    obtain2.readFloatArray(fArr);
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void remove(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public int relayout(android.view.IWindow iWindow, android.view.WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, int i5, int i6, android.window.ClientWindowFrames clientWindowFrames, android.util.MergedConfiguration mergedConfiguration, android.view.SurfaceControl surfaceControl, android.view.InsetsState insetsState, android.view.InsetsSourceControl.Array array, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeTypedObject(layoutParams, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeInt(i6);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    if (obtain2.readInt() != 0) {
                        clientWindowFrames.readFromParcel(obtain2);
                    }
                    if (obtain2.readInt() != 0) {
                        mergedConfiguration.readFromParcel(obtain2);
                    }
                    if (obtain2.readInt() != 0) {
                        surfaceControl.readFromParcel(obtain2);
                    }
                    if (obtain2.readInt() != 0) {
                        insetsState.readFromParcel(obtain2);
                    }
                    if (obtain2.readInt() != 0) {
                        array.readFromParcel(obtain2);
                    }
                    if (obtain2.readInt() != 0) {
                        bundle.readFromParcel(obtain2);
                    }
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void relayoutAsync(android.view.IWindow iWindow, android.view.WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, int i5, int i6) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeTypedObject(layoutParams, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeInt(i6);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public boolean outOfMemory(android.view.IWindow iWindow) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void setInsets(android.view.IWindow iWindow, int i, android.graphics.Rect rect, android.graphics.Rect rect2, android.graphics.Region region) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeTypedObject(rect2, 0);
                    obtain.writeTypedObject(region, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void finishDrawing(android.view.IWindow iWindow, android.view.SurfaceControl.Transaction transaction, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeTypedObject(transaction, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public boolean performHapticFeedback(int i, boolean z, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void performHapticFeedbackAsync(int i, boolean z, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public android.os.IBinder performDrag(android.view.IWindow iWindow, int i, android.view.SurfaceControl surfaceControl, int i2, int i3, int i4, float f, float f2, float f3, float f4, android.content.ClipData clipData) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(surfaceControl, 0);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeFloat(f);
                    obtain.writeFloat(f2);
                    obtain.writeFloat(f3);
                    obtain.writeFloat(f4);
                    obtain.writeTypedObject(clipData, 0);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public boolean dropForAccessibility(android.view.IWindow iWindow, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void reportDropResult(android.view.IWindow iWindow, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void cancelDragAndDrop(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void dragRecipientEntered(android.view.IWindow iWindow) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void dragRecipientExited(android.view.IWindow iWindow) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void setWallpaperPosition(android.os.IBinder iBinder, float f, float f2, float f3, float f4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeFloat(f);
                    obtain.writeFloat(f2);
                    obtain.writeFloat(f3);
                    obtain.writeFloat(f4);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void setWallpaperZoomOut(android.os.IBinder iBinder, float f) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeFloat(f);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void setShouldZoomOutWallpaper(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void wallpaperOffsetsComplete(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void setWallpaperDisplayOffset(android.os.IBinder iBinder, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void sendWallpaperCommand(android.os.IBinder iBinder, java.lang.String str, int i, int i2, int i3, android.os.Bundle bundle, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void wallpaperCommandComplete(android.os.IBinder iBinder, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(24, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void onRectangleOnScreenRequested(android.os.IBinder iBinder, android.graphics.Rect rect) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(25, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public android.view.IWindowId getWindowId(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.view.IWindowId.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void pokeDrawLock(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public boolean startMovingTask(android.view.IWindow iWindow, float f, float f2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeFloat(f);
                    obtain.writeFloat(f2);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void finishMovingTask(android.view.IWindow iWindow) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    this.mRemote.transact(29, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void updatePointerIcon(android.view.IWindow iWindow) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    this.mRemote.transact(30, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void updateTapExcludeRegion(android.view.IWindow iWindow, android.graphics.Region region) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeTypedObject(region, 0);
                    this.mRemote.transact(31, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void updateRequestedVisibleTypes(android.view.IWindow iWindow, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeInt(i);
                    this.mRemote.transact(32, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void reportSystemGestureExclusionChanged(android.view.IWindow iWindow, java.util.List<android.graphics.Rect> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(33, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void reportDecorViewGestureInterceptionChanged(android.view.IWindow iWindow, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(34, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void reportKeepClearAreasChanged(android.view.IWindow iWindow, java.util.List<android.graphics.Rect> list, java.util.List<android.graphics.Rect> list2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeTypedList(list, 0);
                    obtain.writeTypedList(list2, 0);
                    this.mRemote.transact(35, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void grantInputChannel(int i, android.view.SurfaceControl surfaceControl, android.os.IBinder iBinder, android.window.InputTransferToken inputTransferToken, int i2, int i3, int i4, int i5, android.os.IBinder iBinder2, android.window.InputTransferToken inputTransferToken2, java.lang.String str, android.view.InputChannel inputChannel) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(surfaceControl, 0);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(inputTransferToken, 0);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeStrongBinder(iBinder2);
                    obtain.writeTypedObject(inputTransferToken2, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        inputChannel.readFromParcel(obtain2);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void updateInputChannel(android.os.IBinder iBinder, int i, android.view.SurfaceControl surfaceControl, int i2, int i3, int i4, android.graphics.Region region) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(surfaceControl, 0);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeTypedObject(region, 0);
                    this.mRemote.transact(37, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void grantEmbeddedWindowFocus(android.view.IWindow iWindow, android.window.InputTransferToken inputTransferToken, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeTypedObject(inputTransferToken, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void generateDisplayHash(android.view.IWindow iWindow, android.graphics.Rect rect, java.lang.String str, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(39, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void setOnBackInvokedCallbackInfo(android.view.IWindow iWindow, android.window.OnBackInvokedCallbackInfo onBackInvokedCallbackInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeTypedObject(onBackInvokedCallbackInfo, 0);
                    this.mRemote.transact(40, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public void clearTouchableRegion(android.view.IWindow iWindow) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public boolean cancelDraw(android.view.IWindow iWindow) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowSession
            public boolean moveFocusToAdjacentWindow(android.view.IWindow iWindow, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowSession.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeInt(i);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 42;
        }
    }
}
