package android.text;

/* loaded from: classes3.dex */
public interface TextWatcher extends android.text.NoCopySpan {
    void afterTextChanged(android.text.Editable editable);

    void beforeTextChanged(java.lang.CharSequence charSequence, int i, int i2, int i3);

    void onTextChanged(java.lang.CharSequence charSequence, int i, int i2, int i3);
}
