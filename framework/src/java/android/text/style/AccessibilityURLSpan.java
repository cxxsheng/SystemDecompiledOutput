package android.text.style;

/* loaded from: classes3.dex */
public class AccessibilityURLSpan extends android.text.style.URLSpan implements android.os.Parcelable {
    final android.text.style.AccessibilityClickableSpan mAccessibilityClickableSpan;

    public AccessibilityURLSpan(android.text.style.URLSpan uRLSpan) {
        super(uRLSpan.getURL());
        this.mAccessibilityClickableSpan = new android.text.style.AccessibilityClickableSpan(uRLSpan.getId());
    }

    public AccessibilityURLSpan(android.os.Parcel parcel) {
        super(parcel);
        this.mAccessibilityClickableSpan = new android.text.style.AccessibilityClickableSpan(parcel);
    }

    @Override // android.text.style.URLSpan, android.text.ParcelableSpan
    public int getSpanTypeId() {
        return getSpanTypeIdInternal();
    }

    @Override // android.text.style.URLSpan, android.text.ParcelableSpan
    public int getSpanTypeIdInternal() {
        return 26;
    }

    @Override // android.text.style.URLSpan, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        writeToParcelInternal(parcel, i);
    }

    @Override // android.text.style.URLSpan, android.text.ParcelableSpan
    public void writeToParcelInternal(android.os.Parcel parcel, int i) {
        super.writeToParcelInternal(parcel, i);
        this.mAccessibilityClickableSpan.writeToParcel(parcel, i);
    }

    @Override // android.text.style.URLSpan, android.text.style.ClickableSpan
    public void onClick(android.view.View view) {
        this.mAccessibilityClickableSpan.onClick(view);
    }

    public void copyConnectionDataFrom(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        this.mAccessibilityClickableSpan.copyConnectionDataFrom(accessibilityNodeInfo);
    }
}
