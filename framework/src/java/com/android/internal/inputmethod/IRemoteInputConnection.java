package com.android.internal.inputmethod;

/* loaded from: classes4.dex */
public interface IRemoteInputConnection extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.inputmethod.IRemoteInputConnection";

    void beginBatchEdit(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader) throws android.os.RemoteException;

    void cancelCancellationSignal(android.os.IBinder iBinder) throws android.os.RemoteException;

    void clearMetaKeyStates(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i) throws android.os.RemoteException;

    void commitCompletion(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, android.view.inputmethod.CompletionInfo completionInfo) throws android.os.RemoteException;

    void commitContent(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, android.view.inputmethod.InputContentInfo inputContentInfo, int i, android.os.Bundle bundle, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException;

    void commitCorrection(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, android.view.inputmethod.CorrectionInfo correctionInfo) throws android.os.RemoteException;

    void commitText(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, java.lang.CharSequence charSequence, int i) throws android.os.RemoteException;

    void commitTextWithTextAttribute(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, java.lang.CharSequence charSequence, int i, android.view.inputmethod.TextAttribute textAttribute) throws android.os.RemoteException;

    void deleteSurroundingText(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) throws android.os.RemoteException;

    void deleteSurroundingTextInCodePoints(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) throws android.os.RemoteException;

    void endBatchEdit(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader) throws android.os.RemoteException;

    void finishComposingText(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader) throws android.os.RemoteException;

    void forgetCancellationSignal(android.os.IBinder iBinder) throws android.os.RemoteException;

    void getCursorCapsMode(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException;

    void getExtractedText(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, android.view.inputmethod.ExtractedTextRequest extractedTextRequest, int i, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException;

    void getSelectedText(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException;

    void getSurroundingText(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, int i3, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException;

    void getTextAfterCursor(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException;

    void getTextBeforeCursor(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException;

    void performContextMenuAction(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i) throws android.os.RemoteException;

    void performEditorAction(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i) throws android.os.RemoteException;

    void performHandwritingGesture(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, android.view.inputmethod.ParcelableHandwritingGesture parcelableHandwritingGesture, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException;

    void performPrivateCommand(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException;

    void performSpellCheck(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader) throws android.os.RemoteException;

    void previewHandwritingGesture(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, android.view.inputmethod.ParcelableHandwritingGesture parcelableHandwritingGesture, android.os.IBinder iBinder) throws android.os.RemoteException;

    void replaceText(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, java.lang.CharSequence charSequence, int i3, android.view.inputmethod.TextAttribute textAttribute) throws android.os.RemoteException;

    void requestCursorUpdates(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException;

    void requestCursorUpdatesWithFilter(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, int i3, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException;

    void requestTextBoundsInfo(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, android.graphics.RectF rectF, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException;

    void sendKeyEvent(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, android.view.KeyEvent keyEvent) throws android.os.RemoteException;

    void setComposingRegion(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) throws android.os.RemoteException;

    void setComposingRegionWithTextAttribute(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, android.view.inputmethod.TextAttribute textAttribute) throws android.os.RemoteException;

    void setComposingText(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, java.lang.CharSequence charSequence, int i) throws android.os.RemoteException;

    void setComposingTextWithTextAttribute(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, java.lang.CharSequence charSequence, int i, android.view.inputmethod.TextAttribute textAttribute) throws android.os.RemoteException;

    void setImeConsumesInput(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, boolean z) throws android.os.RemoteException;

    void setSelection(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) throws android.os.RemoteException;

    public static class Default implements com.android.internal.inputmethod.IRemoteInputConnection {
        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void getTextBeforeCursor(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void getTextAfterCursor(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void getCursorCapsMode(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void getExtractedText(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, android.view.inputmethod.ExtractedTextRequest extractedTextRequest, int i, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void deleteSurroundingText(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void deleteSurroundingTextInCodePoints(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void setComposingText(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, java.lang.CharSequence charSequence, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void setComposingTextWithTextAttribute(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, java.lang.CharSequence charSequence, int i, android.view.inputmethod.TextAttribute textAttribute) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void finishComposingText(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void commitText(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, java.lang.CharSequence charSequence, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void commitTextWithTextAttribute(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, java.lang.CharSequence charSequence, int i, android.view.inputmethod.TextAttribute textAttribute) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void commitCompletion(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, android.view.inputmethod.CompletionInfo completionInfo) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void commitCorrection(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, android.view.inputmethod.CorrectionInfo correctionInfo) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void setSelection(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void performEditorAction(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void performContextMenuAction(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void beginBatchEdit(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void endBatchEdit(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void sendKeyEvent(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, android.view.KeyEvent keyEvent) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void clearMetaKeyStates(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void performSpellCheck(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void performPrivateCommand(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void performHandwritingGesture(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, android.view.inputmethod.ParcelableHandwritingGesture parcelableHandwritingGesture, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void previewHandwritingGesture(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, android.view.inputmethod.ParcelableHandwritingGesture parcelableHandwritingGesture, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void setComposingRegion(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void setComposingRegionWithTextAttribute(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, android.view.inputmethod.TextAttribute textAttribute) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void getSelectedText(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void requestCursorUpdates(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void requestCursorUpdatesWithFilter(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, int i3, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void requestTextBoundsInfo(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, android.graphics.RectF rectF, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void commitContent(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, android.view.inputmethod.InputContentInfo inputContentInfo, int i, android.os.Bundle bundle, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void getSurroundingText(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, int i3, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void setImeConsumesInput(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void replaceText(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, java.lang.CharSequence charSequence, int i3, android.view.inputmethod.TextAttribute textAttribute) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void cancelCancellationSignal(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void forgetCancellationSignal(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.inputmethod.IRemoteInputConnection {
        static final int TRANSACTION_beginBatchEdit = 17;
        static final int TRANSACTION_cancelCancellationSignal = 35;
        static final int TRANSACTION_clearMetaKeyStates = 20;
        static final int TRANSACTION_commitCompletion = 12;
        static final int TRANSACTION_commitContent = 31;
        static final int TRANSACTION_commitCorrection = 13;
        static final int TRANSACTION_commitText = 10;
        static final int TRANSACTION_commitTextWithTextAttribute = 11;
        static final int TRANSACTION_deleteSurroundingText = 5;
        static final int TRANSACTION_deleteSurroundingTextInCodePoints = 6;
        static final int TRANSACTION_endBatchEdit = 18;
        static final int TRANSACTION_finishComposingText = 9;
        static final int TRANSACTION_forgetCancellationSignal = 36;
        static final int TRANSACTION_getCursorCapsMode = 3;
        static final int TRANSACTION_getExtractedText = 4;
        static final int TRANSACTION_getSelectedText = 27;
        static final int TRANSACTION_getSurroundingText = 32;
        static final int TRANSACTION_getTextAfterCursor = 2;
        static final int TRANSACTION_getTextBeforeCursor = 1;
        static final int TRANSACTION_performContextMenuAction = 16;
        static final int TRANSACTION_performEditorAction = 15;
        static final int TRANSACTION_performHandwritingGesture = 23;
        static final int TRANSACTION_performPrivateCommand = 22;
        static final int TRANSACTION_performSpellCheck = 21;
        static final int TRANSACTION_previewHandwritingGesture = 24;
        static final int TRANSACTION_replaceText = 34;
        static final int TRANSACTION_requestCursorUpdates = 28;
        static final int TRANSACTION_requestCursorUpdatesWithFilter = 29;
        static final int TRANSACTION_requestTextBoundsInfo = 30;
        static final int TRANSACTION_sendKeyEvent = 19;
        static final int TRANSACTION_setComposingRegion = 25;
        static final int TRANSACTION_setComposingRegionWithTextAttribute = 26;
        static final int TRANSACTION_setComposingText = 7;
        static final int TRANSACTION_setComposingTextWithTextAttribute = 8;
        static final int TRANSACTION_setImeConsumesInput = 33;
        static final int TRANSACTION_setSelection = 14;

        public Stub() {
            attachInterface(this, com.android.internal.inputmethod.IRemoteInputConnection.DESCRIPTOR);
        }

        public static com.android.internal.inputmethod.IRemoteInputConnection asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.inputmethod.IRemoteInputConnection.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.inputmethod.IRemoteInputConnection)) {
                return (com.android.internal.inputmethod.IRemoteInputConnection) queryLocalInterface;
            }
            return new com.android.internal.inputmethod.IRemoteInputConnection.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getTextBeforeCursor";
                case 2:
                    return "getTextAfterCursor";
                case 3:
                    return "getCursorCapsMode";
                case 4:
                    return "getExtractedText";
                case 5:
                    return "deleteSurroundingText";
                case 6:
                    return "deleteSurroundingTextInCodePoints";
                case 7:
                    return "setComposingText";
                case 8:
                    return "setComposingTextWithTextAttribute";
                case 9:
                    return "finishComposingText";
                case 10:
                    return "commitText";
                case 11:
                    return "commitTextWithTextAttribute";
                case 12:
                    return "commitCompletion";
                case 13:
                    return "commitCorrection";
                case 14:
                    return "setSelection";
                case 15:
                    return "performEditorAction";
                case 16:
                    return "performContextMenuAction";
                case 17:
                    return "beginBatchEdit";
                case 18:
                    return "endBatchEdit";
                case 19:
                    return "sendKeyEvent";
                case 20:
                    return "clearMetaKeyStates";
                case 21:
                    return "performSpellCheck";
                case 22:
                    return "performPrivateCommand";
                case 23:
                    return "performHandwritingGesture";
                case 24:
                    return "previewHandwritingGesture";
                case 25:
                    return "setComposingRegion";
                case 26:
                    return "setComposingRegionWithTextAttribute";
                case 27:
                    return "getSelectedText";
                case 28:
                    return "requestCursorUpdates";
                case 29:
                    return "requestCursorUpdatesWithFilter";
                case 30:
                    return "requestTextBoundsInfo";
                case 31:
                    return "commitContent";
                case 32:
                    return "getSurroundingText";
                case 33:
                    return "setImeConsumesInput";
                case 34:
                    return "replaceText";
                case 35:
                    return "cancelCancellationSignal";
                case 36:
                    return "forgetCancellationSignal";
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
                parcel.enforceInterface(com.android.internal.inputmethod.IRemoteInputConnection.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.inputmethod.IRemoteInputConnection.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    com.android.internal.infra.AndroidFuture androidFuture = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getTextBeforeCursor(inputConnectionCommandHeader, readInt, readInt2, androidFuture);
                    return true;
                case 2:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader2 = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    com.android.internal.infra.AndroidFuture androidFuture2 = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getTextAfterCursor(inputConnectionCommandHeader2, readInt3, readInt4, androidFuture2);
                    return true;
                case 3:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader3 = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    int readInt5 = parcel.readInt();
                    com.android.internal.infra.AndroidFuture androidFuture3 = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getCursorCapsMode(inputConnectionCommandHeader3, readInt5, androidFuture3);
                    return true;
                case 4:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader4 = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    android.view.inputmethod.ExtractedTextRequest extractedTextRequest = (android.view.inputmethod.ExtractedTextRequest) parcel.readTypedObject(android.view.inputmethod.ExtractedTextRequest.CREATOR);
                    int readInt6 = parcel.readInt();
                    com.android.internal.infra.AndroidFuture androidFuture4 = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getExtractedText(inputConnectionCommandHeader4, extractedTextRequest, readInt6, androidFuture4);
                    return true;
                case 5:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader5 = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deleteSurroundingText(inputConnectionCommandHeader5, readInt7, readInt8);
                    return true;
                case 6:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader6 = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deleteSurroundingTextInCodePoints(inputConnectionCommandHeader6, readInt9, readInt10);
                    return true;
                case 7:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader7 = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    java.lang.CharSequence charSequence = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setComposingText(inputConnectionCommandHeader7, charSequence, readInt11);
                    return true;
                case 8:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader8 = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    java.lang.CharSequence charSequence2 = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    int readInt12 = parcel.readInt();
                    android.view.inputmethod.TextAttribute textAttribute = (android.view.inputmethod.TextAttribute) parcel.readTypedObject(android.view.inputmethod.TextAttribute.CREATOR);
                    parcel.enforceNoDataAvail();
                    setComposingTextWithTextAttribute(inputConnectionCommandHeader8, charSequence2, readInt12, textAttribute);
                    return true;
                case 9:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader9 = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    parcel.enforceNoDataAvail();
                    finishComposingText(inputConnectionCommandHeader9);
                    return true;
                case 10:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader10 = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    java.lang.CharSequence charSequence3 = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    commitText(inputConnectionCommandHeader10, charSequence3, readInt13);
                    return true;
                case 11:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader11 = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    java.lang.CharSequence charSequence4 = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    int readInt14 = parcel.readInt();
                    android.view.inputmethod.TextAttribute textAttribute2 = (android.view.inputmethod.TextAttribute) parcel.readTypedObject(android.view.inputmethod.TextAttribute.CREATOR);
                    parcel.enforceNoDataAvail();
                    commitTextWithTextAttribute(inputConnectionCommandHeader11, charSequence4, readInt14, textAttribute2);
                    return true;
                case 12:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader12 = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    android.view.inputmethod.CompletionInfo completionInfo = (android.view.inputmethod.CompletionInfo) parcel.readTypedObject(android.view.inputmethod.CompletionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    commitCompletion(inputConnectionCommandHeader12, completionInfo);
                    return true;
                case 13:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader13 = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    android.view.inputmethod.CorrectionInfo correctionInfo = (android.view.inputmethod.CorrectionInfo) parcel.readTypedObject(android.view.inputmethod.CorrectionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    commitCorrection(inputConnectionCommandHeader13, correctionInfo);
                    return true;
                case 14:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader14 = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSelection(inputConnectionCommandHeader14, readInt15, readInt16);
                    return true;
                case 15:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader15 = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    performEditorAction(inputConnectionCommandHeader15, readInt17);
                    return true;
                case 16:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader16 = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    performContextMenuAction(inputConnectionCommandHeader16, readInt18);
                    return true;
                case 17:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader17 = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    parcel.enforceNoDataAvail();
                    beginBatchEdit(inputConnectionCommandHeader17);
                    return true;
                case 18:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader18 = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    parcel.enforceNoDataAvail();
                    endBatchEdit(inputConnectionCommandHeader18);
                    return true;
                case 19:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader19 = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    android.view.KeyEvent keyEvent = (android.view.KeyEvent) parcel.readTypedObject(android.view.KeyEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendKeyEvent(inputConnectionCommandHeader19, keyEvent);
                    return true;
                case 20:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader20 = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearMetaKeyStates(inputConnectionCommandHeader20, readInt19);
                    return true;
                case 21:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader21 = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    parcel.enforceNoDataAvail();
                    performSpellCheck(inputConnectionCommandHeader21);
                    return true;
                case 22:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader22 = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    java.lang.String readString = parcel.readString();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    performPrivateCommand(inputConnectionCommandHeader22, readString, bundle);
                    return true;
                case 23:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader23 = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    android.view.inputmethod.ParcelableHandwritingGesture parcelableHandwritingGesture = (android.view.inputmethod.ParcelableHandwritingGesture) parcel.readTypedObject(android.view.inputmethod.ParcelableHandwritingGesture.CREATOR);
                    android.os.ResultReceiver resultReceiver = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    performHandwritingGesture(inputConnectionCommandHeader23, parcelableHandwritingGesture, resultReceiver);
                    return true;
                case 24:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader24 = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    android.view.inputmethod.ParcelableHandwritingGesture parcelableHandwritingGesture2 = (android.view.inputmethod.ParcelableHandwritingGesture) parcel.readTypedObject(android.view.inputmethod.ParcelableHandwritingGesture.CREATOR);
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    previewHandwritingGesture(inputConnectionCommandHeader24, parcelableHandwritingGesture2, readStrongBinder);
                    return true;
                case 25:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader25 = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    int readInt20 = parcel.readInt();
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setComposingRegion(inputConnectionCommandHeader25, readInt20, readInt21);
                    return true;
                case 26:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader26 = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    android.view.inputmethod.TextAttribute textAttribute3 = (android.view.inputmethod.TextAttribute) parcel.readTypedObject(android.view.inputmethod.TextAttribute.CREATOR);
                    parcel.enforceNoDataAvail();
                    setComposingRegionWithTextAttribute(inputConnectionCommandHeader26, readInt22, readInt23, textAttribute3);
                    return true;
                case 27:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader27 = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    int readInt24 = parcel.readInt();
                    com.android.internal.infra.AndroidFuture androidFuture5 = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getSelectedText(inputConnectionCommandHeader27, readInt24, androidFuture5);
                    return true;
                case 28:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader28 = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    int readInt25 = parcel.readInt();
                    int readInt26 = parcel.readInt();
                    com.android.internal.infra.AndroidFuture androidFuture6 = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestCursorUpdates(inputConnectionCommandHeader28, readInt25, readInt26, androidFuture6);
                    return true;
                case 29:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader29 = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    int readInt27 = parcel.readInt();
                    int readInt28 = parcel.readInt();
                    int readInt29 = parcel.readInt();
                    com.android.internal.infra.AndroidFuture androidFuture7 = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestCursorUpdatesWithFilter(inputConnectionCommandHeader29, readInt27, readInt28, readInt29, androidFuture7);
                    return true;
                case 30:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader30 = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    android.graphics.RectF rectF = (android.graphics.RectF) parcel.readTypedObject(android.graphics.RectF.CREATOR);
                    android.os.ResultReceiver resultReceiver2 = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestTextBoundsInfo(inputConnectionCommandHeader30, rectF, resultReceiver2);
                    return true;
                case 31:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader31 = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    android.view.inputmethod.InputContentInfo inputContentInfo = (android.view.inputmethod.InputContentInfo) parcel.readTypedObject(android.view.inputmethod.InputContentInfo.CREATOR);
                    int readInt30 = parcel.readInt();
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    com.android.internal.infra.AndroidFuture androidFuture8 = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    commitContent(inputConnectionCommandHeader31, inputContentInfo, readInt30, bundle2, androidFuture8);
                    return true;
                case 32:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader32 = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    int readInt31 = parcel.readInt();
                    int readInt32 = parcel.readInt();
                    int readInt33 = parcel.readInt();
                    com.android.internal.infra.AndroidFuture androidFuture9 = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getSurroundingText(inputConnectionCommandHeader32, readInt31, readInt32, readInt33, androidFuture9);
                    return true;
                case 33:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader33 = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setImeConsumesInput(inputConnectionCommandHeader33, readBoolean);
                    return true;
                case 34:
                    com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader34 = (com.android.internal.inputmethod.InputConnectionCommandHeader) parcel.readTypedObject(com.android.internal.inputmethod.InputConnectionCommandHeader.CREATOR);
                    int readInt34 = parcel.readInt();
                    int readInt35 = parcel.readInt();
                    java.lang.CharSequence charSequence5 = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    int readInt36 = parcel.readInt();
                    android.view.inputmethod.TextAttribute textAttribute4 = (android.view.inputmethod.TextAttribute) parcel.readTypedObject(android.view.inputmethod.TextAttribute.CREATOR);
                    parcel.enforceNoDataAvail();
                    replaceText(inputConnectionCommandHeader34, readInt34, readInt35, charSequence5, readInt36, textAttribute4);
                    return true;
                case 35:
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    cancelCancellationSignal(readStrongBinder2);
                    return true;
                case 36:
                    android.os.IBinder readStrongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    forgetCancellationSignal(readStrongBinder3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.inputmethod.IRemoteInputConnection {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.inputmethod.IRemoteInputConnection.DESCRIPTOR;
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void getTextBeforeCursor(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void getTextAfterCursor(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void getCursorCapsMode(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void getExtractedText(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, android.view.inputmethod.ExtractedTextRequest extractedTextRequest, int i, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeTypedObject(extractedTextRequest, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void deleteSurroundingText(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void deleteSurroundingTextInCodePoints(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void setComposingText(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, java.lang.CharSequence charSequence, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void setComposingTextWithTextAttribute(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, java.lang.CharSequence charSequence, int i, android.view.inputmethod.TextAttribute textAttribute) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i);
                    obtain.writeTypedObject(textAttribute, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void finishComposingText(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void commitText(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, java.lang.CharSequence charSequence, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void commitTextWithTextAttribute(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, java.lang.CharSequence charSequence, int i, android.view.inputmethod.TextAttribute textAttribute) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i);
                    obtain.writeTypedObject(textAttribute, 0);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void commitCompletion(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, android.view.inputmethod.CompletionInfo completionInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeTypedObject(completionInfo, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void commitCorrection(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, android.view.inputmethod.CorrectionInfo correctionInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeTypedObject(correctionInfo, 0);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void setSelection(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void performEditorAction(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void performContextMenuAction(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void beginBatchEdit(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void endBatchEdit(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void sendKeyEvent(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, android.view.KeyEvent keyEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeTypedObject(keyEvent, 0);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void clearMetaKeyStates(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void performSpellCheck(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void performPrivateCommand(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void performHandwritingGesture(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, android.view.inputmethod.ParcelableHandwritingGesture parcelableHandwritingGesture, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeTypedObject(parcelableHandwritingGesture, 0);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void previewHandwritingGesture(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, android.view.inputmethod.ParcelableHandwritingGesture parcelableHandwritingGesture, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeTypedObject(parcelableHandwritingGesture, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(24, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void setComposingRegion(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(25, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void setComposingRegionWithTextAttribute(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, android.view.inputmethod.TextAttribute textAttribute) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(textAttribute, 0);
                    this.mRemote.transact(26, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void getSelectedText(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(27, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void requestCursorUpdates(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(28, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void requestCursorUpdatesWithFilter(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, int i3, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(29, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void requestTextBoundsInfo(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, android.graphics.RectF rectF, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeTypedObject(rectF, 0);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(30, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void commitContent(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, android.view.inputmethod.InputContentInfo inputContentInfo, int i, android.os.Bundle bundle, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeTypedObject(inputContentInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(31, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void getSurroundingText(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, int i3, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(32, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void setImeConsumesInput(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(33, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void replaceText(com.android.internal.inputmethod.InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, java.lang.CharSequence charSequence, int i3, android.view.inputmethod.TextAttribute textAttribute) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(textAttribute, 0);
                    this.mRemote.transact(34, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void cancelCancellationSignal(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(35, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void forgetCancellationSignal(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(36, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 35;
        }
    }
}
