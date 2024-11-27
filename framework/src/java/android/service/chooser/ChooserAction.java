package android.service.chooser;

/* loaded from: classes3.dex */
public final class ChooserAction implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.chooser.ChooserAction> CREATOR = new android.os.Parcelable.Creator<android.service.chooser.ChooserAction>() { // from class: android.service.chooser.ChooserAction.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.chooser.ChooserAction createFromParcel(android.os.Parcel parcel) {
            return new android.service.chooser.ChooserAction(android.graphics.drawable.Icon.CREATOR.createFromParcel(parcel), android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel), android.app.PendingIntent.CREATOR.createFromParcel(parcel));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.chooser.ChooserAction[] newArray(int i) {
            return new android.service.chooser.ChooserAction[i];
        }
    };
    private final android.app.PendingIntent mAction;
    private final android.graphics.drawable.Icon mIcon;
    private final java.lang.CharSequence mLabel;

    private ChooserAction(android.graphics.drawable.Icon icon, java.lang.CharSequence charSequence, android.app.PendingIntent pendingIntent) {
        this.mIcon = icon;
        this.mLabel = charSequence;
        this.mAction = pendingIntent;
    }

    public java.lang.CharSequence getLabel() {
        return this.mLabel;
    }

    public android.graphics.drawable.Icon getIcon() {
        return this.mIcon;
    }

    public android.app.PendingIntent getAction() {
        return this.mAction;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        this.mIcon.writeToParcel(parcel, i);
        android.text.TextUtils.writeToParcel(this.mLabel, parcel, i);
        this.mAction.writeToParcel(parcel, i);
    }

    public java.lang.String toString() {
        return "ChooserAction {label=" + ((java.lang.Object) this.mLabel) + ", intent=" + this.mAction + "}";
    }

    public static final class Builder {
        private final android.app.PendingIntent mAction;
        private final android.graphics.drawable.Icon mIcon;
        private final java.lang.CharSequence mLabel;

        public Builder(android.graphics.drawable.Icon icon, java.lang.CharSequence charSequence, android.app.PendingIntent pendingIntent) {
            java.util.Objects.requireNonNull(icon, "icon can not be null");
            java.util.Objects.requireNonNull(charSequence, "label can not be null");
            java.util.Objects.requireNonNull(pendingIntent, "pending intent can not be null");
            this.mIcon = icon;
            this.mLabel = charSequence;
            this.mAction = pendingIntent;
        }

        public android.service.chooser.ChooserAction build() {
            return new android.service.chooser.ChooserAction(this.mIcon, this.mLabel, this.mAction);
        }
    }
}
