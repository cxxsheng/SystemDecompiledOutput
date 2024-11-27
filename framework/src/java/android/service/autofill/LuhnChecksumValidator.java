package android.service.autofill;

/* loaded from: classes3.dex */
public final class LuhnChecksumValidator extends android.service.autofill.InternalValidator implements android.service.autofill.Validator, android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.autofill.LuhnChecksumValidator> CREATOR = new android.os.Parcelable.Creator<android.service.autofill.LuhnChecksumValidator>() { // from class: android.service.autofill.LuhnChecksumValidator.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.LuhnChecksumValidator createFromParcel(android.os.Parcel parcel) {
            return new android.service.autofill.LuhnChecksumValidator((android.view.autofill.AutofillId[]) parcel.readParcelableArray(null, android.view.autofill.AutofillId.class));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.LuhnChecksumValidator[] newArray(int i) {
            return new android.service.autofill.LuhnChecksumValidator[i];
        }
    };
    private static final java.lang.String TAG = "LuhnChecksumValidator";
    private final android.view.autofill.AutofillId[] mIds;

    public LuhnChecksumValidator(android.view.autofill.AutofillId... autofillIdArr) {
        this.mIds = (android.view.autofill.AutofillId[]) com.android.internal.util.Preconditions.checkArrayElementsNotNull(autofillIdArr, android.provider.Downloads.EXTRA_IDS);
    }

    private static boolean isLuhnChecksumValid(java.lang.String str) {
        int i = 0;
        boolean z = false;
        for (int length = str.length() - 1; length >= 0; length--) {
            int charAt = str.charAt(length) - '0';
            if (charAt >= 0 && charAt <= 9) {
                if (z && (charAt = charAt * 2) > 9) {
                    charAt -= 9;
                }
                i += charAt;
                z = !z;
            }
        }
        return i % 10 == 0;
    }

    @Override // android.service.autofill.InternalValidator
    public boolean isValid(android.service.autofill.ValueFinder valueFinder) {
        if (this.mIds == null || this.mIds.length == 0) {
            return false;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (android.view.autofill.AutofillId autofillId : this.mIds) {
            java.lang.String findByAutofillId = valueFinder.findByAutofillId(autofillId);
            if (findByAutofillId == null) {
                if (android.view.autofill.Helper.sDebug) {
                    android.util.Log.d(TAG, "No partial number for id " + autofillId);
                }
                return false;
            }
            sb.append(findByAutofillId);
        }
        java.lang.String sb2 = sb.toString();
        boolean isLuhnChecksumValid = isLuhnChecksumValid(sb2);
        if (android.view.autofill.Helper.sDebug) {
            android.util.Log.d(TAG, "isValid(" + sb2.length() + " chars): " + isLuhnChecksumValid);
        }
        return isLuhnChecksumValid;
    }

    public java.lang.String toString() {
        return !android.view.autofill.Helper.sDebug ? super.toString() : "LuhnChecksumValidator: [ids=" + java.util.Arrays.toString(this.mIds) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelableArray(this.mIds, i);
    }
}
