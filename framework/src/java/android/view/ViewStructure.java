package android.view;

/* loaded from: classes4.dex */
public abstract class ViewStructure {
    public static final java.lang.String EXTRA_ACTIVE_CHILDREN_IDS = "android.view.ViewStructure.extra.ACTIVE_CHILDREN_IDS";
    public static final java.lang.String EXTRA_FIRST_ACTIVE_POSITION = "android.view.ViewStructure.extra.FIRST_ACTIVE_POSITION";

    public static abstract class HtmlInfo {

        public static abstract class Builder {
            public abstract android.view.ViewStructure.HtmlInfo.Builder addAttribute(java.lang.String str, java.lang.String str2);

            public abstract android.view.ViewStructure.HtmlInfo build();
        }

        public abstract java.util.List<android.util.Pair<java.lang.String, java.lang.String>> getAttributes();

        public abstract java.lang.String getTag();
    }

    public abstract int addChildCount(int i);

    public abstract void asyncCommit();

    public abstract android.view.ViewStructure asyncNewChild(int i);

    public abstract android.view.autofill.AutofillId getAutofillId();

    public abstract int getChildCount();

    public abstract android.os.Bundle getExtras();

    public abstract java.lang.CharSequence getHint();

    public abstract android.graphics.Rect getTempRect();

    public abstract java.lang.CharSequence getText();

    public abstract int getTextSelectionEnd();

    public abstract int getTextSelectionStart();

    public abstract boolean hasExtras();

    public abstract android.view.ViewStructure newChild(int i);

    public abstract android.view.ViewStructure.HtmlInfo.Builder newHtmlInfoBuilder(java.lang.String str);

    public abstract void setAccessibilityFocused(boolean z);

    public abstract void setActivated(boolean z);

    public abstract void setAlpha(float f);

    public abstract void setAssistBlocked(boolean z);

    public abstract void setAutofillHints(java.lang.String[] strArr);

    public abstract void setAutofillId(android.view.autofill.AutofillId autofillId);

    public abstract void setAutofillId(android.view.autofill.AutofillId autofillId, int i);

    public abstract void setAutofillOptions(java.lang.CharSequence[] charSequenceArr);

    public abstract void setAutofillType(int i);

    public abstract void setAutofillValue(android.view.autofill.AutofillValue autofillValue);

    public abstract void setCheckable(boolean z);

    public abstract void setChecked(boolean z);

    public abstract void setChildCount(int i);

    public abstract void setClassName(java.lang.String str);

    public abstract void setClickable(boolean z);

    public abstract void setContentDescription(java.lang.CharSequence charSequence);

    public abstract void setContextClickable(boolean z);

    public abstract void setDataIsSensitive(boolean z);

    public abstract void setDimens(int i, int i2, int i3, int i4, int i5, int i6);

    public abstract void setElevation(float f);

    public abstract void setEnabled(boolean z);

    public abstract void setFocusable(boolean z);

    public abstract void setFocused(boolean z);

    public abstract void setHint(java.lang.CharSequence charSequence);

    public abstract void setHtmlInfo(android.view.ViewStructure.HtmlInfo htmlInfo);

    public abstract void setId(int i, java.lang.String str, java.lang.String str2, java.lang.String str3);

    public abstract void setInputType(int i);

    public abstract void setLocaleList(android.os.LocaleList localeList);

    public abstract void setLongClickable(boolean z);

    public abstract void setOpaque(boolean z);

    public abstract void setSelected(boolean z);

    public abstract void setText(java.lang.CharSequence charSequence);

    public abstract void setText(java.lang.CharSequence charSequence, int i, int i2);

    public abstract void setTextLines(int[] iArr, int[] iArr2);

    public abstract void setTextStyle(float f, int i, int i2, int i3);

    public abstract void setTransformation(android.graphics.Matrix matrix);

    public abstract void setVisibility(int i);

    public abstract void setWebDomain(java.lang.String str);

    public void setTextIdEntry(java.lang.String str) {
        com.android.internal.util.Preconditions.checkNotNull(str);
    }

    public void setHintIdEntry(java.lang.String str) {
        com.android.internal.util.Preconditions.checkNotNull(str);
    }

    public android.credentials.GetCredentialRequest getCredentialManagerRequest() {
        return null;
    }

    public android.os.OutcomeReceiver<android.credentials.GetCredentialResponse, android.credentials.GetCredentialException> getCredentialManagerCallback() {
        return null;
    }

    public void setImportantForAutofill(int i) {
    }

    public void setIsCredential(boolean z) {
    }

    public void setReceiveContentMimeTypes(java.lang.String[] strArr) {
    }

    public void setMinTextEms(int i) {
    }

    public void setMaxTextEms(int i) {
    }

    public void setMaxTextLength(int i) {
    }

    public void setCredentialManagerRequest(android.credentials.GetCredentialRequest getCredentialRequest, android.os.OutcomeReceiver<android.credentials.GetCredentialResponse, android.credentials.GetCredentialException> outcomeReceiver) {
    }

    public void clearCredentialManagerRequest() {
    }
}
