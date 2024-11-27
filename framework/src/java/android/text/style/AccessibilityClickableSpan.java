package android.text.style;

/* loaded from: classes3.dex */
public class AccessibilityClickableSpan extends android.text.style.ClickableSpan implements android.text.ParcelableSpan {
    public static final android.os.Parcelable.Creator<android.text.style.AccessibilityClickableSpan> CREATOR = new android.os.Parcelable.Creator<android.text.style.AccessibilityClickableSpan>() { // from class: android.text.style.AccessibilityClickableSpan.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.text.style.AccessibilityClickableSpan createFromParcel(android.os.Parcel parcel) {
            return new android.text.style.AccessibilityClickableSpan(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.text.style.AccessibilityClickableSpan[] newArray(int i) {
            return new android.text.style.AccessibilityClickableSpan[i];
        }
    };
    private final int mOriginalClickableSpanId;
    private int mWindowId = -1;
    private long mSourceNodeId = android.view.accessibility.AccessibilityNodeInfo.UNDEFINED_NODE_ID;
    private int mConnectionId = -1;

    public AccessibilityClickableSpan(int i) {
        this.mOriginalClickableSpanId = i;
    }

    public AccessibilityClickableSpan(android.os.Parcel parcel) {
        this.mOriginalClickableSpanId = parcel.readInt();
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeId() {
        return getSpanTypeIdInternal();
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeIdInternal() {
        return 25;
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
        parcel.writeInt(this.mOriginalClickableSpanId);
    }

    public android.text.style.ClickableSpan findClickableSpan(java.lang.CharSequence charSequence) {
        if (!(charSequence instanceof android.text.Spanned)) {
            return null;
        }
        android.text.style.ClickableSpan[] clickableSpanArr = (android.text.style.ClickableSpan[]) ((android.text.Spanned) charSequence).getSpans(0, charSequence.length(), android.text.style.ClickableSpan.class);
        for (int i = 0; i < clickableSpanArr.length; i++) {
            if (clickableSpanArr[i].getId() == this.mOriginalClickableSpanId) {
                return clickableSpanArr[i];
            }
        }
        return null;
    }

    public void copyConnectionDataFrom(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        this.mConnectionId = accessibilityNodeInfo.getConnectionId();
        this.mWindowId = accessibilityNodeInfo.getWindowId();
        this.mSourceNodeId = accessibilityNodeInfo.getSourceNodeId();
    }

    @Override // android.text.style.ClickableSpan
    public void onClick(android.view.View view) {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putParcelable(android.view.accessibility.AccessibilityNodeInfo.ACTION_ARGUMENT_ACCESSIBLE_CLICKABLE_SPAN, this);
        if (this.mWindowId == -1 || this.mSourceNodeId == android.view.accessibility.AccessibilityNodeInfo.UNDEFINED_NODE_ID || this.mConnectionId == -1) {
            throw new java.lang.RuntimeException("ClickableSpan for accessibility service not properly initialized");
        }
        android.view.accessibility.AccessibilityInteractionClient.getInstance().performAccessibilityAction(this.mConnectionId, this.mWindowId, this.mSourceNodeId, com.android.internal.R.id.accessibilityActionClickOnClickableSpan, bundle);
    }
}
