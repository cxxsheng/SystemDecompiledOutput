package android.service.autofill;

/* loaded from: classes3.dex */
final class RequiredValidators extends android.service.autofill.InternalValidator {
    public static final android.os.Parcelable.Creator<android.service.autofill.RequiredValidators> CREATOR = new android.os.Parcelable.Creator<android.service.autofill.RequiredValidators>() { // from class: android.service.autofill.RequiredValidators.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.RequiredValidators createFromParcel(android.os.Parcel parcel) {
            return new android.service.autofill.RequiredValidators((android.service.autofill.InternalValidator[]) parcel.readParcelableArray(null, android.service.autofill.InternalValidator.class));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.RequiredValidators[] newArray(int i) {
            return new android.service.autofill.RequiredValidators[i];
        }
    };
    private static final java.lang.String TAG = "RequiredValidators";
    private final android.service.autofill.InternalValidator[] mValidators;

    RequiredValidators(android.service.autofill.InternalValidator[] internalValidatorArr) {
        this.mValidators = (android.service.autofill.InternalValidator[]) com.android.internal.util.Preconditions.checkArrayElementsNotNull(internalValidatorArr, "validators");
    }

    @Override // android.service.autofill.InternalValidator
    public boolean isValid(android.service.autofill.ValueFinder valueFinder) {
        for (android.service.autofill.InternalValidator internalValidator : this.mValidators) {
            boolean isValid = internalValidator.isValid(valueFinder);
            if (android.view.autofill.Helper.sDebug) {
                android.util.Log.d(TAG, "isValid(" + internalValidator + "): " + isValid);
            }
            if (!isValid) {
                return false;
            }
        }
        return true;
    }

    public java.lang.String toString() {
        return !android.view.autofill.Helper.sDebug ? super.toString() : "RequiredValidators: [validators=" + java.util.Arrays.toString(this.mValidators) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelableArray(this.mValidators, i);
    }
}
