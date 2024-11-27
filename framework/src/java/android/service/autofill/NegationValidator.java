package android.service.autofill;

/* loaded from: classes3.dex */
final class NegationValidator extends android.service.autofill.InternalValidator {
    public static final android.os.Parcelable.Creator<android.service.autofill.NegationValidator> CREATOR = new android.os.Parcelable.Creator<android.service.autofill.NegationValidator>() { // from class: android.service.autofill.NegationValidator.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.NegationValidator createFromParcel(android.os.Parcel parcel) {
            return new android.service.autofill.NegationValidator((android.service.autofill.InternalValidator) parcel.readParcelable(null, android.service.autofill.InternalValidator.class));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.NegationValidator[] newArray(int i) {
            return new android.service.autofill.NegationValidator[i];
        }
    };
    private final android.service.autofill.InternalValidator mValidator;

    NegationValidator(android.service.autofill.InternalValidator internalValidator) {
        this.mValidator = (android.service.autofill.InternalValidator) java.util.Objects.requireNonNull(internalValidator);
    }

    @Override // android.service.autofill.InternalValidator
    public boolean isValid(android.service.autofill.ValueFinder valueFinder) {
        return !this.mValidator.isValid(valueFinder);
    }

    public java.lang.String toString() {
        return !android.view.autofill.Helper.sDebug ? super.toString() : "NegationValidator: [validator=" + this.mValidator + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mValidator, i);
    }
}
