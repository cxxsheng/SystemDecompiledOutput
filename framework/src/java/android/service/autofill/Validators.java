package android.service.autofill;

/* loaded from: classes3.dex */
public final class Validators {
    private Validators() {
        throw new java.lang.UnsupportedOperationException("contains static methods only");
    }

    public static android.service.autofill.Validator and(android.service.autofill.Validator... validatorArr) {
        return new android.service.autofill.RequiredValidators(getInternalValidators(validatorArr));
    }

    public static android.service.autofill.Validator or(android.service.autofill.Validator... validatorArr) {
        return new android.service.autofill.OptionalValidators(getInternalValidators(validatorArr));
    }

    public static android.service.autofill.Validator not(android.service.autofill.Validator validator) {
        com.android.internal.util.Preconditions.checkArgument(validator instanceof android.service.autofill.InternalValidator, "validator not provided by Android System: %s", validator);
        return new android.service.autofill.NegationValidator((android.service.autofill.InternalValidator) validator);
    }

    private static android.service.autofill.InternalValidator[] getInternalValidators(android.service.autofill.Validator[] validatorArr) {
        com.android.internal.util.Preconditions.checkArrayElementsNotNull(validatorArr, "validators");
        android.service.autofill.InternalValidator[] internalValidatorArr = new android.service.autofill.InternalValidator[validatorArr.length];
        for (int i = 0; i < validatorArr.length; i++) {
            com.android.internal.util.Preconditions.checkArgument(validatorArr[i] instanceof android.service.autofill.InternalValidator, "element %d not provided by Android System: %s", java.lang.Integer.valueOf(i), validatorArr[i]);
            internalValidatorArr[i] = (android.service.autofill.InternalValidator) validatorArr[i];
        }
        return internalValidatorArr;
    }
}
