package android.view.inputmethod;

/* loaded from: classes4.dex */
final class IInputMethodManagerGlobalInvoker {
    private static volatile com.android.internal.view.IInputMethodManager sServiceCache = null;
    private static volatile com.android.internal.inputmethod.IImeTracker sTrackerServiceCache = null;
    private static int sCurStartInputSeq = 0;

    IInputMethodManagerGlobalInvoker() {
    }

    static boolean isAvailable() {
        return getService() != null;
    }

    static com.android.internal.view.IInputMethodManager getService() {
        com.android.internal.view.IInputMethodManager iInputMethodManager = sServiceCache;
        if (iInputMethodManager == null) {
            if (android.view.inputmethod.InputMethodManager.isInEditModeInternal() || (iInputMethodManager = com.android.internal.view.IInputMethodManager.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.INPUT_METHOD_SERVICE))) == null) {
                return null;
            }
            sServiceCache = iInputMethodManager;
        }
        return iInputMethodManager;
    }

    private static void handleRemoteExceptionOrRethrow(android.os.RemoteException remoteException, java.util.function.Consumer<android.os.RemoteException> consumer) {
        if (consumer != null) {
            consumer.accept(remoteException);
            return;
        }
        throw remoteException.rethrowFromSystemServer();
    }

    static void startProtoDump(byte[] bArr, int i, java.lang.String str, java.util.function.Consumer<android.os.RemoteException> consumer) {
        com.android.internal.view.IInputMethodManager service = getService();
        if (service == null) {
            return;
        }
        try {
            service.startProtoDump(bArr, i, str);
        } catch (android.os.RemoteException e) {
            handleRemoteExceptionOrRethrow(e, consumer);
        }
    }

    static void startImeTrace(java.util.function.Consumer<android.os.RemoteException> consumer) {
        com.android.internal.view.IInputMethodManager service = getService();
        if (service == null) {
            return;
        }
        try {
            service.startImeTrace();
        } catch (android.os.RemoteException e) {
            handleRemoteExceptionOrRethrow(e, consumer);
        }
    }

    static void stopImeTrace(java.util.function.Consumer<android.os.RemoteException> consumer) {
        com.android.internal.view.IInputMethodManager service = getService();
        if (service == null) {
            return;
        }
        try {
            service.stopImeTrace();
        } catch (android.os.RemoteException e) {
            handleRemoteExceptionOrRethrow(e, consumer);
        }
    }

    static boolean isImeTraceEnabled() {
        com.android.internal.view.IInputMethodManager service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.isImeTraceEnabled();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static void removeImeSurface(java.util.function.Consumer<android.os.RemoteException> consumer) {
        com.android.internal.view.IInputMethodManager service = getService();
        if (service == null) {
            return;
        }
        try {
            service.removeImeSurface();
        } catch (android.os.RemoteException e) {
            handleRemoteExceptionOrRethrow(e, consumer);
        }
    }

    static void addClient(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, com.android.internal.inputmethod.IRemoteInputConnection iRemoteInputConnection, int i) {
        com.android.internal.view.IInputMethodManager service = getService();
        if (service == null) {
            return;
        }
        try {
            service.addClient(iInputMethodClient, iRemoteInputConnection, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static android.view.inputmethod.InputMethodInfo getCurrentInputMethodInfoAsUser(int i) {
        com.android.internal.view.IInputMethodManager service = getService();
        if (service == null) {
            return null;
        }
        try {
            return service.getCurrentInputMethodInfoAsUser(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static java.util.List<android.view.inputmethod.InputMethodInfo> getInputMethodList(int i, int i2) {
        com.android.internal.view.IInputMethodManager service = getService();
        if (service == null) {
            return new java.util.ArrayList();
        }
        try {
            return service.getInputMethodList(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static java.util.List<android.view.inputmethod.InputMethodInfo> getEnabledInputMethodList(int i) {
        com.android.internal.view.IInputMethodManager service = getService();
        if (service == null) {
            return new java.util.ArrayList();
        }
        try {
            return service.getEnabledInputMethodList(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static java.util.List<android.view.inputmethod.InputMethodSubtype> getEnabledInputMethodSubtypeList(java.lang.String str, boolean z, int i) {
        com.android.internal.view.IInputMethodManager service = getService();
        if (service == null) {
            return new java.util.ArrayList();
        }
        try {
            return service.getEnabledInputMethodSubtypeList(str, z, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static android.view.inputmethod.InputMethodSubtype getLastInputMethodSubtype(int i) {
        com.android.internal.view.IInputMethodManager service = getService();
        if (service == null) {
            return null;
        }
        try {
            return service.getLastInputMethodSubtype(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static boolean showSoftInput(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, android.os.IBinder iBinder, android.view.inputmethod.ImeTracker.Token token, int i, int i2, android.os.ResultReceiver resultReceiver, int i3) {
        com.android.internal.view.IInputMethodManager service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.showSoftInput(iInputMethodClient, iBinder, token, i, i2, resultReceiver, i3);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static boolean hideSoftInput(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, android.os.IBinder iBinder, android.view.inputmethod.ImeTracker.Token token, int i, android.os.ResultReceiver resultReceiver, int i2) {
        com.android.internal.view.IInputMethodManager service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.hideSoftInput(iInputMethodClient, iBinder, token, i, resultReceiver, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static void hideSoftInputFromServerForTest() {
        com.android.internal.view.IInputMethodManager service = getService();
        if (service == null) {
            return;
        }
        try {
            service.hideSoftInputFromServerForTest();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static com.android.internal.inputmethod.InputBindResult startInputOrWindowGainedFocus(int i, com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, android.os.IBinder iBinder, int i2, int i3, int i4, android.view.inputmethod.EditorInfo editorInfo, com.android.internal.inputmethod.IRemoteInputConnection iRemoteInputConnection, com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, int i5, int i6, android.window.ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher) {
        com.android.internal.view.IInputMethodManager service = getService();
        if (service == null) {
            return com.android.internal.inputmethod.InputBindResult.NULL;
        }
        try {
            return service.startInputOrWindowGainedFocus(i, iInputMethodClient, iBinder, i2, i3, i4, editorInfo, iRemoteInputConnection, iRemoteAccessibilityInputConnection, i5, i6, imeOnBackInvokedDispatcher);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static int startInputOrWindowGainedFocusAsync(int i, com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, android.os.IBinder iBinder, int i2, int i3, int i4, android.view.inputmethod.EditorInfo editorInfo, com.android.internal.inputmethod.IRemoteInputConnection iRemoteInputConnection, com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, int i5, int i6, android.window.ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher) {
        com.android.internal.view.IInputMethodManager service = getService();
        if (service == null) {
            return -1;
        }
        try {
            service.startInputOrWindowGainedFocusAsync(i, iInputMethodClient, iBinder, i2, i3, i4, editorInfo, iRemoteInputConnection, iRemoteAccessibilityInputConnection, i5, i6, imeOnBackInvokedDispatcher, advanceAngGetStartInputSequenceNumber());
            return sCurStartInputSeq;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static int advanceAngGetStartInputSequenceNumber() {
        int i = sCurStartInputSeq + 1;
        sCurStartInputSeq = i;
        return i;
    }

    static void showInputMethodPickerFromClient(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, int i) {
        com.android.internal.view.IInputMethodManager service = getService();
        if (service == null) {
            return;
        }
        try {
            service.showInputMethodPickerFromClient(iInputMethodClient, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static void showInputMethodPickerFromSystem(int i, int i2) {
        com.android.internal.view.IInputMethodManager service = getService();
        if (service == null) {
            return;
        }
        try {
            service.showInputMethodPickerFromSystem(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static boolean isInputMethodPickerShownForTest() {
        com.android.internal.view.IInputMethodManager service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.isInputMethodPickerShownForTest();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static android.view.inputmethod.InputMethodSubtype getCurrentInputMethodSubtype(int i) {
        com.android.internal.view.IInputMethodManager service = getService();
        if (service == null) {
            return null;
        }
        try {
            return service.getCurrentInputMethodSubtype(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static void setAdditionalInputMethodSubtypes(java.lang.String str, android.view.inputmethod.InputMethodSubtype[] inputMethodSubtypeArr, int i) {
        com.android.internal.view.IInputMethodManager service = getService();
        if (service == null) {
            return;
        }
        try {
            service.setAdditionalInputMethodSubtypes(str, inputMethodSubtypeArr, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static void setExplicitlyEnabledInputMethodSubtypes(java.lang.String str, int[] iArr, int i) {
        com.android.internal.view.IInputMethodManager service = getService();
        if (service == null) {
            return;
        }
        try {
            service.setExplicitlyEnabledInputMethodSubtypes(str, iArr, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static int getInputMethodWindowVisibleHeight(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient) {
        com.android.internal.view.IInputMethodManager service = getService();
        if (service == null) {
            return 0;
        }
        try {
            return service.getInputMethodWindowVisibleHeight(iInputMethodClient);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static void reportPerceptibleAsync(android.os.IBinder iBinder, boolean z) {
        com.android.internal.view.IInputMethodManager service = getService();
        if (service == null) {
            return;
        }
        try {
            service.reportPerceptibleAsync(iBinder, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static void removeImeSurfaceFromWindowAsync(android.os.IBinder iBinder) {
        com.android.internal.view.IInputMethodManager service = getService();
        if (service == null) {
            return;
        }
        try {
            service.removeImeSurfaceFromWindowAsync(iBinder);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static void startStylusHandwriting(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient) {
        com.android.internal.view.IInputMethodManager service = getService();
        if (service == null) {
            return;
        }
        try {
            service.startStylusHandwriting(iInputMethodClient);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static boolean startConnectionlessStylusHandwriting(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, int i, android.view.inputmethod.CursorAnchorInfo cursorAnchorInfo, java.lang.String str, java.lang.String str2, com.android.internal.inputmethod.IConnectionlessHandwritingCallback iConnectionlessHandwritingCallback) {
        com.android.internal.view.IInputMethodManager service = getService();
        if (service == null) {
            return false;
        }
        try {
            service.startConnectionlessStylusHandwriting(iInputMethodClient, i, cursorAnchorInfo, str, str2, iConnectionlessHandwritingCallback);
            return true;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static void prepareStylusHandwritingDelegation(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, int i, java.lang.String str, java.lang.String str2) {
        com.android.internal.view.IInputMethodManager service = getService();
        if (service == null) {
            return;
        }
        try {
            service.prepareStylusHandwritingDelegation(iInputMethodClient, i, str, str2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static boolean acceptStylusHandwritingDelegation(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, int i, java.lang.String str, java.lang.String str2, int i2) {
        com.android.internal.view.IInputMethodManager service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.acceptStylusHandwritingDelegation(iInputMethodClient, i, str, str2, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static boolean acceptStylusHandwritingDelegationAsync(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, int i, java.lang.String str, java.lang.String str2, int i2, com.android.internal.inputmethod.IBooleanListener iBooleanListener) {
        com.android.internal.view.IInputMethodManager service = getService();
        if (service == null) {
            return false;
        }
        try {
            service.acceptStylusHandwritingDelegationAsync(iInputMethodClient, i, str, str2, i2, iBooleanListener);
            return true;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static boolean isStylusHandwritingAvailableAsUser(int i, boolean z) {
        com.android.internal.view.IInputMethodManager service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.isStylusHandwritingAvailableAsUser(i, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static void addVirtualStylusIdForTestSession(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient) {
        com.android.internal.view.IInputMethodManager service = getService();
        if (service == null) {
            return;
        }
        try {
            service.addVirtualStylusIdForTestSession(iInputMethodClient);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static void setStylusWindowIdleTimeoutForTest(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, long j) {
        com.android.internal.view.IInputMethodManager service = getService();
        if (service == null) {
            return;
        }
        try {
            service.setStylusWindowIdleTimeoutForTest(iInputMethodClient, j);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static android.view.inputmethod.ImeTracker.Token onStart(java.lang.String str, int i, int i2, int i3, int i4, boolean z) {
        com.android.internal.inputmethod.IImeTracker imeTrackerService = getImeTrackerService();
        if (imeTrackerService == null) {
            return android.view.inputmethod.ImeTracker.Token.empty(str);
        }
        try {
            return imeTrackerService.onStart(str, i, i2, i3, i4, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static void onProgress(android.os.IBinder iBinder, int i) {
        com.android.internal.inputmethod.IImeTracker imeTrackerService = getImeTrackerService();
        if (imeTrackerService == null) {
            return;
        }
        try {
            imeTrackerService.onProgress(iBinder, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static void onFailed(android.view.inputmethod.ImeTracker.Token token, int i) {
        com.android.internal.inputmethod.IImeTracker imeTrackerService = getImeTrackerService();
        if (imeTrackerService == null) {
            return;
        }
        try {
            imeTrackerService.onFailed(token, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static void onCancelled(android.view.inputmethod.ImeTracker.Token token, int i) {
        com.android.internal.inputmethod.IImeTracker imeTrackerService = getImeTrackerService();
        if (imeTrackerService == null) {
            return;
        }
        try {
            imeTrackerService.onCancelled(token, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static void onShown(android.view.inputmethod.ImeTracker.Token token) {
        com.android.internal.inputmethod.IImeTracker imeTrackerService = getImeTrackerService();
        if (imeTrackerService == null) {
            return;
        }
        try {
            imeTrackerService.onShown(token);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static void onHidden(android.view.inputmethod.ImeTracker.Token token) {
        com.android.internal.inputmethod.IImeTracker imeTrackerService = getImeTrackerService();
        if (imeTrackerService == null) {
            return;
        }
        try {
            imeTrackerService.onHidden(token);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static boolean hasPendingImeVisibilityRequests() {
        com.android.internal.inputmethod.IImeTracker imeTrackerService = getImeTrackerService();
        if (imeTrackerService == null) {
            return true;
        }
        try {
            return imeTrackerService.hasPendingImeVisibilityRequests();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static com.android.internal.inputmethod.IImeTracker getImeTrackerService() {
        com.android.internal.inputmethod.IImeTracker iImeTracker = sTrackerServiceCache;
        if (iImeTracker == null) {
            com.android.internal.view.IInputMethodManager service = getService();
            if (service == null) {
                return null;
            }
            try {
                iImeTracker = service.getImeTrackerService();
                if (iImeTracker == null) {
                    return null;
                }
                sTrackerServiceCache = iImeTracker;
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return iImeTracker;
    }
}
