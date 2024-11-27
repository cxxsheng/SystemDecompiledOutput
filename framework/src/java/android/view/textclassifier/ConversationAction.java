package android.view.textclassifier;

/* loaded from: classes4.dex */
public final class ConversationAction implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.textclassifier.ConversationAction> CREATOR = new android.os.Parcelable.Creator<android.view.textclassifier.ConversationAction>() { // from class: android.view.textclassifier.ConversationAction.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.textclassifier.ConversationAction createFromParcel(android.os.Parcel parcel) {
            return new android.view.textclassifier.ConversationAction(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.textclassifier.ConversationAction[] newArray(int i) {
            return new android.view.textclassifier.ConversationAction[i];
        }
    };
    public static final java.lang.String TYPE_ADD_CONTACT = "add_contact";
    public static final java.lang.String TYPE_CALL_PHONE = "call_phone";
    public static final java.lang.String TYPE_COPY = "copy";
    public static final java.lang.String TYPE_CREATE_REMINDER = "create_reminder";
    public static final java.lang.String TYPE_OPEN_URL = "open_url";
    public static final java.lang.String TYPE_SEND_EMAIL = "send_email";
    public static final java.lang.String TYPE_SEND_SMS = "send_sms";
    public static final java.lang.String TYPE_SHARE_LOCATION = "share_location";
    public static final java.lang.String TYPE_TEXT_REPLY = "text_reply";
    public static final java.lang.String TYPE_TRACK_FLIGHT = "track_flight";
    public static final java.lang.String TYPE_VIEW_CALENDAR = "view_calendar";
    public static final java.lang.String TYPE_VIEW_MAP = "view_map";
    private final android.app.RemoteAction mAction;
    private final android.os.Bundle mExtras;
    private final float mScore;
    private final java.lang.CharSequence mTextReply;
    private final java.lang.String mType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ActionType {
    }

    private ConversationAction(java.lang.String str, android.app.RemoteAction remoteAction, java.lang.CharSequence charSequence, float f, android.os.Bundle bundle) {
        this.mType = (java.lang.String) java.util.Objects.requireNonNull(str);
        this.mAction = remoteAction;
        this.mTextReply = charSequence;
        this.mScore = f;
        this.mExtras = (android.os.Bundle) java.util.Objects.requireNonNull(bundle);
    }

    private ConversationAction(android.os.Parcel parcel) {
        this.mType = parcel.readString();
        this.mAction = (android.app.RemoteAction) parcel.readParcelable(null, android.app.RemoteAction.class);
        this.mTextReply = parcel.readCharSequence();
        this.mScore = parcel.readFloat();
        this.mExtras = parcel.readBundle();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mType);
        parcel.writeParcelable(this.mAction, i);
        parcel.writeCharSequence(this.mTextReply);
        parcel.writeFloat(this.mScore);
        parcel.writeBundle(this.mExtras);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String getType() {
        return this.mType;
    }

    public android.app.RemoteAction getAction() {
        return this.mAction;
    }

    public float getConfidenceScore() {
        return this.mScore;
    }

    public java.lang.CharSequence getTextReply() {
        return this.mTextReply;
    }

    public android.os.Bundle getExtras() {
        return this.mExtras;
    }

    public android.view.textclassifier.ConversationAction.Builder toBuilder() {
        return new android.view.textclassifier.ConversationAction.Builder(this.mType).setTextReply(this.mTextReply).setAction(this.mAction).setConfidenceScore(this.mScore).setExtras(this.mExtras);
    }

    public static final class Builder {
        private android.app.RemoteAction mAction;
        private android.os.Bundle mExtras;
        private float mScore;
        private java.lang.CharSequence mTextReply;
        private java.lang.String mType;

        public Builder(java.lang.String str) {
            this.mType = (java.lang.String) java.util.Objects.requireNonNull(str);
        }

        public android.view.textclassifier.ConversationAction.Builder setAction(android.app.RemoteAction remoteAction) {
            this.mAction = remoteAction;
            return this;
        }

        public android.view.textclassifier.ConversationAction.Builder setTextReply(java.lang.CharSequence charSequence) {
            this.mTextReply = charSequence;
            return this;
        }

        public android.view.textclassifier.ConversationAction.Builder setConfidenceScore(float f) {
            this.mScore = f;
            return this;
        }

        public android.view.textclassifier.ConversationAction.Builder setExtras(android.os.Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public android.view.textclassifier.ConversationAction build() {
            return new android.view.textclassifier.ConversationAction(this.mType, this.mAction, this.mTextReply, this.mScore, this.mExtras == null ? android.os.Bundle.EMPTY : this.mExtras);
        }
    }
}
