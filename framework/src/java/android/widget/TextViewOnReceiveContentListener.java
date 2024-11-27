package android.widget;

/* loaded from: classes4.dex */
public final class TextViewOnReceiveContentListener implements android.view.OnReceiveContentListener {
    private static final long AUTOFILL_NON_TEXT_REQUIRES_ON_RECEIVE_CONTENT_LISTENER = 163400105;
    private static final java.lang.String LOG_TAG = "ReceiveContent";
    private android.widget.TextViewOnReceiveContentListener.InputConnectionInfo mInputConnectionInfo;

    @Override // android.view.OnReceiveContentListener
    public android.view.ContentInfo onReceiveContent(android.view.View view, android.view.ContentInfo contentInfo) {
        java.lang.CharSequence coerceToStyledText;
        if (android.util.Log.isLoggable(LOG_TAG, 3)) {
            android.util.Log.d(LOG_TAG, "onReceive: " + contentInfo);
        }
        int source = contentInfo.getSource();
        if (source == 2) {
            return contentInfo;
        }
        if (source == 4) {
            onReceiveForAutofill((android.widget.TextView) view, contentInfo);
            return null;
        }
        android.content.ClipData clip = contentInfo.getClip();
        int flags = contentInfo.getFlags();
        android.text.Editable editable = (android.text.Editable) ((android.widget.TextView) view).getText();
        android.content.Context context = view.getContext();
        boolean z = false;
        for (int i = 0; i < clip.getItemCount(); i++) {
            if ((flags & 1) != 0) {
                coerceToStyledText = clip.getItemAt(i).coerceToText(context);
                if (coerceToStyledText instanceof android.text.Spanned) {
                    coerceToStyledText = coerceToStyledText.toString();
                }
            } else {
                coerceToStyledText = clip.getItemAt(i).coerceToStyledText(context);
            }
            if (coerceToStyledText != null) {
                if (!z) {
                    replaceSelection(editable, coerceToStyledText);
                    z = true;
                } else {
                    editable.insert(android.text.Selection.getSelectionEnd(editable), "\n");
                    editable.insert(android.text.Selection.getSelectionEnd(editable), coerceToStyledText);
                }
            }
        }
        return null;
    }

    private static void replaceSelection(android.text.Editable editable, java.lang.CharSequence charSequence) {
        int selectionStart = android.text.Selection.getSelectionStart(editable);
        int selectionEnd = android.text.Selection.getSelectionEnd(editable);
        int max = java.lang.Math.max(0, java.lang.Math.min(selectionStart, selectionEnd));
        int max2 = java.lang.Math.max(0, java.lang.Math.max(selectionStart, selectionEnd));
        android.text.Selection.setSelection(editable, max2);
        editable.replace(max, max2, charSequence);
    }

    private void onReceiveForAutofill(android.widget.TextView textView, android.view.ContentInfo contentInfo) {
        android.content.ClipData clip = contentInfo.getClip();
        if (isUsageOfImeCommitContentEnabled(textView) && (clip = handleNonTextViaImeCommitContent(clip)) == null) {
            if (android.util.Log.isLoggable(LOG_TAG, 2)) {
                android.util.Log.v(LOG_TAG, "onReceive: Handled via IME");
            }
        } else {
            textView.lambda$setTextAsync$0(coerceToText(clip, textView.getContext(), contentInfo.getFlags()));
            android.text.Editable editable = (android.text.Editable) textView.getText();
            android.text.Selection.setSelection(editable, editable.length());
        }
    }

    private static java.lang.CharSequence coerceToText(android.content.ClipData clipData, android.content.Context context, int i) {
        java.lang.CharSequence coerceToStyledText;
        android.text.SpannableStringBuilder spannableStringBuilder = new android.text.SpannableStringBuilder();
        for (int i2 = 0; i2 < clipData.getItemCount(); i2++) {
            if ((i & 1) != 0) {
                coerceToStyledText = clipData.getItemAt(i2).coerceToText(context);
                if (coerceToStyledText instanceof android.text.Spanned) {
                    coerceToStyledText = coerceToStyledText.toString();
                }
            } else {
                coerceToStyledText = clipData.getItemAt(i2).coerceToStyledText(context);
            }
            if (coerceToStyledText != null) {
                spannableStringBuilder.append(coerceToStyledText);
            }
        }
        return spannableStringBuilder;
    }

    private static boolean isUsageOfImeCommitContentEnabled(android.view.View view) {
        return view.getReceiveContentMimeTypes() == null && !android.compat.Compatibility.isChangeEnabled(AUTOFILL_NON_TEXT_REQUIRES_ON_RECEIVE_CONTENT_LISTENER);
    }

    private static final class InputConnectionInfo {
        private final java.lang.String[] mEditorInfoContentMimeTypes;
        private final java.lang.ref.WeakReference<android.view.inputmethod.InputConnection> mInputConnection;

        private InputConnectionInfo(android.view.inputmethod.InputConnection inputConnection, java.lang.String[] strArr) {
            this.mInputConnection = new java.lang.ref.WeakReference<>(inputConnection);
            this.mEditorInfoContentMimeTypes = strArr;
        }

        public java.lang.String toString() {
            return "InputConnectionInfo{mimeTypes=" + java.util.Arrays.toString(this.mEditorInfoContentMimeTypes) + ", ic=" + this.mInputConnection + '}';
        }
    }

    void setInputConnectionInfo(android.widget.TextView textView, android.view.inputmethod.InputConnection inputConnection, android.view.inputmethod.EditorInfo editorInfo) {
        if (!isUsageOfImeCommitContentEnabled(textView)) {
            this.mInputConnectionInfo = null;
            return;
        }
        java.lang.String[] strArr = editorInfo.contentMimeTypes;
        if (strArr == null || strArr.length == 0) {
            this.mInputConnectionInfo = null;
        } else {
            this.mInputConnectionInfo = new android.widget.TextViewOnReceiveContentListener.InputConnectionInfo(inputConnection, strArr);
        }
    }

    void clearInputConnectionInfo() {
        this.mInputConnectionInfo = null;
    }

    public java.lang.String[] getFallbackMimeTypesForAutofill(android.widget.TextView textView) {
        android.widget.TextViewOnReceiveContentListener.InputConnectionInfo inputConnectionInfo;
        if (isUsageOfImeCommitContentEnabled(textView) && (inputConnectionInfo = this.mInputConnectionInfo) != null) {
            return inputConnectionInfo.mEditorInfoContentMimeTypes;
        }
        return null;
    }

    private android.content.ClipData handleNonTextViaImeCommitContent(android.content.ClipData clipData) {
        android.content.ClipDescription description = clipData.getDescription();
        if (!containsUri(clipData) || containsOnlyText(clipData)) {
            if (android.util.Log.isLoggable(LOG_TAG, 2)) {
                android.util.Log.v(LOG_TAG, "onReceive: Clip doesn't contain any non-text URIs: " + description);
            }
            return clipData;
        }
        android.widget.TextViewOnReceiveContentListener.InputConnectionInfo inputConnectionInfo = this.mInputConnectionInfo;
        android.view.inputmethod.InputConnection inputConnection = inputConnectionInfo != null ? (android.view.inputmethod.InputConnection) inputConnectionInfo.mInputConnection.get() : null;
        if (inputConnection == null) {
            if (android.util.Log.isLoggable(LOG_TAG, 3)) {
                android.util.Log.d(LOG_TAG, "onReceive: No usable EditorInfo/InputConnection");
            }
            return clipData;
        }
        if (!isClipMimeTypeSupported(inputConnectionInfo.mEditorInfoContentMimeTypes, clipData.getDescription())) {
            if (android.util.Log.isLoggable(LOG_TAG, 3)) {
                android.util.Log.d(LOG_TAG, "onReceive: MIME type is not supported by the app's commitContent impl");
            }
            return clipData;
        }
        if (android.util.Log.isLoggable(LOG_TAG, 2)) {
            android.util.Log.v(LOG_TAG, "onReceive: Trying to insert via IME: " + description);
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(0);
        for (int i = 0; i < clipData.getItemCount(); i++) {
            android.content.ClipData.Item itemAt = clipData.getItemAt(i);
            android.net.Uri uri = itemAt.getUri();
            if (uri == null || !"content".equals(uri.getScheme())) {
                if (android.util.Log.isLoggable(LOG_TAG, 2)) {
                    android.util.Log.v(LOG_TAG, "onReceive: No content URI in item: uri=" + uri);
                }
                arrayList.add(itemAt);
            } else {
                if (android.util.Log.isLoggable(LOG_TAG, 2)) {
                    android.util.Log.v(LOG_TAG, "onReceive: Calling commitContent: uri=" + uri);
                }
                if (!inputConnection.commitContent(new android.view.inputmethod.InputContentInfo(uri, description), 0, null)) {
                    if (android.util.Log.isLoggable(LOG_TAG, 2)) {
                        android.util.Log.v(LOG_TAG, "onReceive: Call to commitContent returned false: uri=" + uri);
                    }
                    arrayList.add(itemAt);
                }
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return new android.content.ClipData(description, (java.util.ArrayList<android.content.ClipData.Item>) arrayList);
    }

    private static boolean isClipMimeTypeSupported(java.lang.String[] strArr, android.content.ClipDescription clipDescription) {
        for (java.lang.String str : strArr) {
            if (clipDescription.hasMimeType(str)) {
                return true;
            }
        }
        return false;
    }

    private static boolean containsUri(android.content.ClipData clipData) {
        for (int i = 0; i < clipData.getItemCount(); i++) {
            if (clipData.getItemAt(i).getUri() != null) {
                return true;
            }
        }
        return false;
    }

    private static boolean containsOnlyText(android.content.ClipData clipData) {
        android.content.ClipDescription description = clipData.getDescription();
        for (int i = 0; i < description.getMimeTypeCount(); i++) {
            if (!description.getMimeType(i).startsWith("text/")) {
                return false;
            }
        }
        return true;
    }
}
