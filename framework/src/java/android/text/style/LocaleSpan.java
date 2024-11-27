package android.text.style;

/* loaded from: classes3.dex */
public class LocaleSpan extends android.text.style.MetricAffectingSpan implements android.text.ParcelableSpan {
    private final android.os.LocaleList mLocales;

    public LocaleSpan(java.util.Locale locale) {
        this.mLocales = locale == null ? android.os.LocaleList.getEmptyLocaleList() : new android.os.LocaleList(locale);
    }

    public LocaleSpan(android.os.LocaleList localeList) {
        com.android.internal.util.Preconditions.checkNotNull(localeList, "locales cannot be null");
        this.mLocales = localeList;
    }

    public LocaleSpan(android.os.Parcel parcel) {
        this.mLocales = android.os.LocaleList.CREATOR.createFromParcel(parcel);
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeId() {
        return getSpanTypeIdInternal();
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeIdInternal() {
        return 23;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        writeToParcelInternal(parcel, i);
    }

    @Override // android.text.ParcelableSpan
    public void writeToParcelInternal(android.os.Parcel parcel, int i) {
        this.mLocales.writeToParcel(parcel, i);
    }

    public java.util.Locale getLocale() {
        return this.mLocales.get(0);
    }

    public android.os.LocaleList getLocales() {
        return this.mLocales;
    }

    @Override // android.text.style.CharacterStyle
    public void updateDrawState(android.text.TextPaint textPaint) {
        apply(textPaint, this.mLocales);
    }

    @Override // android.text.style.MetricAffectingSpan
    public void updateMeasureState(android.text.TextPaint textPaint) {
        apply(textPaint, this.mLocales);
    }

    private static void apply(android.graphics.Paint paint, android.os.LocaleList localeList) {
        paint.setTextLocales(localeList);
    }

    public java.lang.String toString() {
        return "LocaleSpan{locales=" + getLocales() + '}';
    }
}
