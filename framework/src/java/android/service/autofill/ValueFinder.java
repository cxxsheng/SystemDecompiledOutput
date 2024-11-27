package android.service.autofill;

/* loaded from: classes3.dex */
public interface ValueFinder {
    android.view.autofill.AutofillValue findRawValueByAutofillId(android.view.autofill.AutofillId autofillId);

    default java.lang.String findByAutofillId(android.view.autofill.AutofillId autofillId) {
        android.view.autofill.AutofillValue findRawValueByAutofillId = findRawValueByAutofillId(autofillId);
        if (findRawValueByAutofillId == null || !findRawValueByAutofillId.isText()) {
            return null;
        }
        return findRawValueByAutofillId.getTextValue().toString();
    }
}
