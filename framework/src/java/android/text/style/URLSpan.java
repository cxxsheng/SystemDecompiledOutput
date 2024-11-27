package android.text.style;

/* loaded from: classes3.dex */
public class URLSpan extends android.text.style.ClickableSpan implements android.text.ParcelableSpan {
    private final java.lang.String mURL;

    public URLSpan(java.lang.String str) {
        this.mURL = str;
    }

    public URLSpan(android.os.Parcel parcel) {
        this.mURL = parcel.readString();
    }

    public int getSpanTypeId() {
        return getSpanTypeIdInternal();
    }

    public int getSpanTypeIdInternal() {
        return 11;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(android.os.Parcel parcel, int i) {
        writeToParcelInternal(parcel, i);
    }

    public void writeToParcelInternal(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mURL);
    }

    public java.lang.String getURL() {
        return this.mURL;
    }

    @Override // android.text.style.ClickableSpan
    public void onClick(android.view.View view) {
        android.net.Uri parse = android.net.Uri.parse(getURL());
        android.content.Context context = view.getContext();
        android.content.Intent intent = new android.content.Intent("android.intent.action.VIEW", parse);
        intent.putExtra(android.provider.Browser.EXTRA_APPLICATION_ID, context.getPackageName());
        try {
            context.startActivity(intent);
        } catch (android.content.ActivityNotFoundException e) {
            android.util.Log.w("URLSpan", "Activity was not found for intent, " + intent.toString());
        }
    }

    @Override // android.text.style.ClickableSpan
    public java.lang.String toString() {
        return "URLSpan{URL='" + getURL() + android.text.format.DateFormat.QUOTE + '}';
    }
}
