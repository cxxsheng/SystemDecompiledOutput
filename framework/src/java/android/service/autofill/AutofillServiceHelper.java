package android.service.autofill;

/* loaded from: classes3.dex */
final class AutofillServiceHelper {
    static android.view.autofill.AutofillId[] assertValid(android.view.autofill.AutofillId[] autofillIdArr) {
        com.android.internal.util.Preconditions.checkArgument(autofillIdArr != null && autofillIdArr.length > 0, "must have at least one id");
        for (int i = 0; i < autofillIdArr.length; i++) {
            if (autofillIdArr[i] == null) {
                throw new java.lang.IllegalArgumentException("ids[" + i + "] must not be null");
            }
        }
        return autofillIdArr;
    }

    private AutofillServiceHelper() {
        throw new java.lang.UnsupportedOperationException("contains static members only");
    }
}
