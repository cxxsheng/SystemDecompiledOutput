package android.view.accessibility;

/* loaded from: classes4.dex */
public final class AccessibilityWindowAttributes implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.accessibility.AccessibilityWindowAttributes> CREATOR = new android.os.Parcelable.Creator<android.view.accessibility.AccessibilityWindowAttributes>() { // from class: android.view.accessibility.AccessibilityWindowAttributes.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.accessibility.AccessibilityWindowAttributes createFromParcel(android.os.Parcel parcel) {
            return new android.view.accessibility.AccessibilityWindowAttributes(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.accessibility.AccessibilityWindowAttributes[] newArray(int i) {
            return new android.view.accessibility.AccessibilityWindowAttributes[i];
        }
    };
    private final android.os.LocaleList mLocales;
    private final java.lang.CharSequence mWindowTitle;

    public AccessibilityWindowAttributes(android.view.WindowManager.LayoutParams layoutParams, android.os.LocaleList localeList) {
        this.mWindowTitle = populateWindowTitle(layoutParams);
        this.mLocales = localeList;
    }

    private AccessibilityWindowAttributes(android.os.Parcel parcel) {
        this.mWindowTitle = parcel.readCharSequence();
        android.os.LocaleList localeList = (android.os.LocaleList) parcel.readParcelable(null, android.os.LocaleList.class);
        if (localeList != null) {
            this.mLocales = localeList;
        } else {
            this.mLocales = android.os.LocaleList.getEmptyLocaleList();
        }
    }

    public java.lang.CharSequence getWindowTitle() {
        return this.mWindowTitle;
    }

    private java.lang.CharSequence populateWindowTitle(android.view.WindowManager.LayoutParams layoutParams) {
        java.lang.CharSequence charSequence = layoutParams.accessibilityTitle;
        boolean z = layoutParams.type >= 1000 && layoutParams.type <= 1999;
        boolean z2 = layoutParams.type == 2032;
        if (!android.text.TextUtils.isEmpty(charSequence)) {
            return charSequence;
        }
        if (z || z2) {
            if (android.text.TextUtils.isEmpty(layoutParams.getTitle())) {
                return null;
            }
            return layoutParams.getTitle();
        }
        return charSequence;
    }

    public android.os.LocaleList getLocales() {
        return this.mLocales;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.view.accessibility.AccessibilityWindowAttributes)) {
            return false;
        }
        android.view.accessibility.AccessibilityWindowAttributes accessibilityWindowAttributes = (android.view.accessibility.AccessibilityWindowAttributes) obj;
        return android.text.TextUtils.equals(this.mWindowTitle, accessibilityWindowAttributes.mWindowTitle) && java.util.Objects.equals(this.mLocales, accessibilityWindowAttributes.mLocales);
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mWindowTitle, this.mLocales);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeCharSequence(this.mWindowTitle);
        parcel.writeParcelable(this.mLocales, i);
    }

    public java.lang.String toString() {
        return "AccessibilityWindowAttributes{mAccessibilityWindowTitle=" + ((java.lang.Object) this.mWindowTitle) + "mLocales=" + this.mLocales + '}';
    }
}
