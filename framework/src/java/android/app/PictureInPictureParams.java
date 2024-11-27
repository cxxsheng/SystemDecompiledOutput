package android.app;

/* loaded from: classes.dex */
public final class PictureInPictureParams implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.PictureInPictureParams> CREATOR = new android.os.Parcelable.Creator<android.app.PictureInPictureParams>() { // from class: android.app.PictureInPictureParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.PictureInPictureParams createFromParcel(android.os.Parcel parcel) {
            return new android.app.PictureInPictureParams(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.PictureInPictureParams[] newArray(int i) {
            return new android.app.PictureInPictureParams[i];
        }
    };
    private android.util.Rational mAspectRatio;
    private java.lang.Boolean mAutoEnterEnabled;
    private android.app.RemoteAction mCloseAction;
    private android.util.Rational mExpandedAspectRatio;
    private java.lang.Boolean mIsLaunchIntoPip;
    private java.lang.Boolean mSeamlessResizeEnabled;
    private android.graphics.Rect mSourceRectHint;
    private java.lang.CharSequence mSubtitle;
    private java.lang.CharSequence mTitle;
    private java.util.List<android.app.RemoteAction> mUserActions;

    public static class Builder {
        private android.util.Rational mAspectRatio;
        private java.lang.Boolean mAutoEnterEnabled;
        private android.app.RemoteAction mCloseAction;
        private android.util.Rational mExpandedAspectRatio;
        private java.lang.Boolean mIsLaunchIntoPip;
        private java.lang.Boolean mSeamlessResizeEnabled;
        private android.graphics.Rect mSourceRectHint;
        private java.lang.CharSequence mSubtitle;
        private java.lang.CharSequence mTitle;
        private java.util.List<android.app.RemoteAction> mUserActions;

        public Builder() {
        }

        public Builder(android.app.PictureInPictureParams pictureInPictureParams) {
            this.mAspectRatio = pictureInPictureParams.mAspectRatio;
            this.mUserActions = pictureInPictureParams.mUserActions;
            this.mCloseAction = pictureInPictureParams.mCloseAction;
            this.mSourceRectHint = pictureInPictureParams.mSourceRectHint;
            this.mAutoEnterEnabled = pictureInPictureParams.mAutoEnterEnabled;
            this.mSeamlessResizeEnabled = pictureInPictureParams.mSeamlessResizeEnabled;
            this.mTitle = pictureInPictureParams.mTitle;
            this.mSubtitle = pictureInPictureParams.mSubtitle;
            this.mIsLaunchIntoPip = pictureInPictureParams.mIsLaunchIntoPip;
        }

        public android.app.PictureInPictureParams.Builder setAspectRatio(android.util.Rational rational) {
            this.mAspectRatio = rational;
            return this;
        }

        public android.app.PictureInPictureParams.Builder setExpandedAspectRatio(android.util.Rational rational) {
            this.mExpandedAspectRatio = rational;
            return this;
        }

        public android.app.PictureInPictureParams.Builder setActions(java.util.List<android.app.RemoteAction> list) {
            if (this.mUserActions != null) {
                this.mUserActions = null;
            }
            if (list != null) {
                this.mUserActions = new java.util.ArrayList(list);
            }
            return this;
        }

        public android.app.PictureInPictureParams.Builder setCloseAction(android.app.RemoteAction remoteAction) {
            this.mCloseAction = remoteAction;
            return this;
        }

        public android.app.PictureInPictureParams.Builder setSourceRectHint(android.graphics.Rect rect) {
            if (rect == null) {
                this.mSourceRectHint = null;
            } else {
                this.mSourceRectHint = new android.graphics.Rect(rect);
            }
            return this;
        }

        public android.app.PictureInPictureParams.Builder setAutoEnterEnabled(boolean z) {
            this.mAutoEnterEnabled = java.lang.Boolean.valueOf(z);
            return this;
        }

        public android.app.PictureInPictureParams.Builder setSeamlessResizeEnabled(boolean z) {
            this.mSeamlessResizeEnabled = java.lang.Boolean.valueOf(z);
            return this;
        }

        public android.app.PictureInPictureParams.Builder setTitle(java.lang.CharSequence charSequence) {
            this.mTitle = charSequence;
            return this;
        }

        public android.app.PictureInPictureParams.Builder setSubtitle(java.lang.CharSequence charSequence) {
            this.mSubtitle = charSequence;
            return this;
        }

        android.app.PictureInPictureParams.Builder setIsLaunchIntoPip(boolean z) {
            this.mIsLaunchIntoPip = java.lang.Boolean.valueOf(z);
            return this;
        }

        public android.app.PictureInPictureParams build() {
            return new android.app.PictureInPictureParams(this.mAspectRatio, this.mExpandedAspectRatio, this.mUserActions, this.mCloseAction, this.mSourceRectHint, this.mAutoEnterEnabled, this.mSeamlessResizeEnabled, this.mTitle, this.mSubtitle, this.mIsLaunchIntoPip);
        }
    }

    PictureInPictureParams() {
    }

    PictureInPictureParams(android.os.Parcel parcel) {
        this.mAspectRatio = readRationalFromParcel(parcel);
        this.mExpandedAspectRatio = readRationalFromParcel(parcel);
        if (parcel.readInt() != 0) {
            this.mUserActions = new java.util.ArrayList();
            parcel.readTypedList(this.mUserActions, android.app.RemoteAction.CREATOR);
        }
        this.mCloseAction = (android.app.RemoteAction) parcel.readTypedObject(android.app.RemoteAction.CREATOR);
        if (parcel.readInt() != 0) {
            this.mSourceRectHint = android.graphics.Rect.CREATOR.createFromParcel(parcel);
        }
        if (parcel.readInt() != 0) {
            this.mAutoEnterEnabled = java.lang.Boolean.valueOf(parcel.readBoolean());
        }
        if (parcel.readInt() != 0) {
            this.mSeamlessResizeEnabled = java.lang.Boolean.valueOf(parcel.readBoolean());
        }
        if (parcel.readInt() != 0) {
            this.mTitle = parcel.readCharSequence();
        }
        if (parcel.readInt() != 0) {
            this.mSubtitle = parcel.readCharSequence();
        }
        if (parcel.readInt() != 0) {
            this.mIsLaunchIntoPip = java.lang.Boolean.valueOf(parcel.readBoolean());
        }
    }

    PictureInPictureParams(android.util.Rational rational, android.util.Rational rational2, java.util.List<android.app.RemoteAction> list, android.app.RemoteAction remoteAction, android.graphics.Rect rect, java.lang.Boolean bool, java.lang.Boolean bool2, java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, java.lang.Boolean bool3) {
        this.mAspectRatio = rational;
        this.mExpandedAspectRatio = rational2;
        this.mUserActions = list;
        this.mCloseAction = remoteAction;
        this.mSourceRectHint = rect;
        this.mAutoEnterEnabled = bool;
        this.mSeamlessResizeEnabled = bool2;
        this.mTitle = charSequence;
        this.mSubtitle = charSequence2;
        this.mIsLaunchIntoPip = bool3;
    }

    public PictureInPictureParams(android.app.PictureInPictureParams pictureInPictureParams) {
        this(pictureInPictureParams.mAspectRatio, pictureInPictureParams.mExpandedAspectRatio, pictureInPictureParams.mUserActions, pictureInPictureParams.mCloseAction, pictureInPictureParams.hasSourceBoundsHint() ? new android.graphics.Rect(pictureInPictureParams.getSourceRectHint()) : null, pictureInPictureParams.mAutoEnterEnabled, pictureInPictureParams.mSeamlessResizeEnabled, pictureInPictureParams.mTitle, pictureInPictureParams.mSubtitle, pictureInPictureParams.mIsLaunchIntoPip);
    }

    public void copyOnlySet(android.app.PictureInPictureParams pictureInPictureParams) {
        if (pictureInPictureParams.hasSetAspectRatio()) {
            this.mAspectRatio = pictureInPictureParams.mAspectRatio;
        }
        this.mExpandedAspectRatio = pictureInPictureParams.mExpandedAspectRatio;
        if (pictureInPictureParams.hasSetActions()) {
            this.mUserActions = pictureInPictureParams.mUserActions;
        }
        if (pictureInPictureParams.hasSetCloseAction()) {
            this.mCloseAction = pictureInPictureParams.mCloseAction;
        }
        if (pictureInPictureParams.hasSourceBoundsHint()) {
            this.mSourceRectHint = new android.graphics.Rect(pictureInPictureParams.getSourceRectHint());
        }
        if (pictureInPictureParams.mAutoEnterEnabled != null) {
            this.mAutoEnterEnabled = pictureInPictureParams.mAutoEnterEnabled;
        }
        if (pictureInPictureParams.mSeamlessResizeEnabled != null) {
            this.mSeamlessResizeEnabled = pictureInPictureParams.mSeamlessResizeEnabled;
        }
        if (pictureInPictureParams.hasSetTitle()) {
            this.mTitle = pictureInPictureParams.mTitle;
        }
        if (pictureInPictureParams.hasSetSubtitle()) {
            this.mSubtitle = pictureInPictureParams.mSubtitle;
        }
        if (pictureInPictureParams.mIsLaunchIntoPip != null) {
            this.mIsLaunchIntoPip = pictureInPictureParams.mIsLaunchIntoPip;
        }
    }

    public float getAspectRatioFloat() {
        if (this.mAspectRatio != null) {
            return this.mAspectRatio.floatValue();
        }
        return 0.0f;
    }

    public android.util.Rational getAspectRatio() {
        return this.mAspectRatio;
    }

    public boolean hasSetAspectRatio() {
        return this.mAspectRatio != null;
    }

    public float getExpandedAspectRatioFloat() {
        if (this.mExpandedAspectRatio != null) {
            return this.mExpandedAspectRatio.floatValue();
        }
        return 0.0f;
    }

    public android.util.Rational getExpandedAspectRatio() {
        return this.mExpandedAspectRatio;
    }

    public boolean hasSetExpandedAspectRatio() {
        return this.mExpandedAspectRatio != null;
    }

    public java.util.List<android.app.RemoteAction> getActions() {
        if (this.mUserActions == null) {
            return new java.util.ArrayList();
        }
        return this.mUserActions;
    }

    public boolean hasSetActions() {
        return this.mUserActions != null;
    }

    public android.app.RemoteAction getCloseAction() {
        return this.mCloseAction;
    }

    public boolean hasSetCloseAction() {
        return this.mCloseAction != null;
    }

    public void truncateActions(int i) {
        if (hasSetActions()) {
            this.mUserActions = this.mUserActions.subList(0, java.lang.Math.min(this.mUserActions.size(), i));
        }
    }

    public android.graphics.Rect getSourceRectHint() {
        return this.mSourceRectHint;
    }

    public boolean hasSourceBoundsHint() {
        return (this.mSourceRectHint == null || this.mSourceRectHint.isEmpty()) ? false : true;
    }

    public boolean isAutoEnterEnabled() {
        if (this.mAutoEnterEnabled == null) {
            return false;
        }
        return this.mAutoEnterEnabled.booleanValue();
    }

    public boolean isSeamlessResizeEnabled() {
        if (this.mSeamlessResizeEnabled == null) {
            return true;
        }
        return this.mSeamlessResizeEnabled.booleanValue();
    }

    public boolean hasSetTitle() {
        return this.mTitle != null;
    }

    public java.lang.CharSequence getTitle() {
        return this.mTitle;
    }

    public boolean hasSetSubtitle() {
        return this.mSubtitle != null;
    }

    public java.lang.CharSequence getSubtitle() {
        return this.mSubtitle;
    }

    public boolean isLaunchIntoPip() {
        if (this.mIsLaunchIntoPip == null) {
            return false;
        }
        return this.mIsLaunchIntoPip.booleanValue();
    }

    public boolean empty() {
        return (hasSourceBoundsHint() || hasSetActions() || hasSetCloseAction() || hasSetAspectRatio() || hasSetExpandedAspectRatio() || this.mAutoEnterEnabled != null || this.mSeamlessResizeEnabled != null || hasSetTitle() || hasSetSubtitle() || this.mIsLaunchIntoPip != null) ? false : true;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.app.PictureInPictureParams)) {
            return false;
        }
        android.app.PictureInPictureParams pictureInPictureParams = (android.app.PictureInPictureParams) obj;
        return java.util.Objects.equals(this.mAutoEnterEnabled, pictureInPictureParams.mAutoEnterEnabled) && java.util.Objects.equals(this.mSeamlessResizeEnabled, pictureInPictureParams.mSeamlessResizeEnabled) && java.util.Objects.equals(this.mAspectRatio, pictureInPictureParams.mAspectRatio) && java.util.Objects.equals(this.mExpandedAspectRatio, pictureInPictureParams.mExpandedAspectRatio) && java.util.Objects.equals(this.mUserActions, pictureInPictureParams.mUserActions) && java.util.Objects.equals(this.mCloseAction, pictureInPictureParams.mCloseAction) && java.util.Objects.equals(this.mSourceRectHint, pictureInPictureParams.mSourceRectHint) && java.util.Objects.equals(this.mTitle, pictureInPictureParams.mTitle) && java.util.Objects.equals(this.mSubtitle, pictureInPictureParams.mSubtitle) && java.util.Objects.equals(this.mIsLaunchIntoPip, pictureInPictureParams.mIsLaunchIntoPip);
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mAspectRatio, this.mExpandedAspectRatio, this.mUserActions, this.mCloseAction, this.mSourceRectHint, this.mAutoEnterEnabled, this.mSeamlessResizeEnabled, this.mTitle, this.mSubtitle, this.mIsLaunchIntoPip);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        writeRationalToParcel(this.mAspectRatio, parcel);
        writeRationalToParcel(this.mExpandedAspectRatio, parcel);
        if (this.mUserActions != null) {
            parcel.writeInt(1);
            parcel.writeTypedList(this.mUserActions, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeTypedObject(this.mCloseAction, 0);
        if (this.mSourceRectHint != null) {
            parcel.writeInt(1);
            this.mSourceRectHint.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        if (this.mAutoEnterEnabled != null) {
            parcel.writeInt(1);
            parcel.writeBoolean(this.mAutoEnterEnabled.booleanValue());
        } else {
            parcel.writeInt(0);
        }
        if (this.mSeamlessResizeEnabled != null) {
            parcel.writeInt(1);
            parcel.writeBoolean(this.mSeamlessResizeEnabled.booleanValue());
        } else {
            parcel.writeInt(0);
        }
        if (this.mTitle != null) {
            parcel.writeInt(1);
            parcel.writeCharSequence(this.mTitle);
        } else {
            parcel.writeInt(0);
        }
        if (this.mSubtitle != null) {
            parcel.writeInt(1);
            parcel.writeCharSequence(this.mSubtitle);
        } else {
            parcel.writeInt(0);
        }
        if (this.mIsLaunchIntoPip != null) {
            parcel.writeInt(1);
            parcel.writeBoolean(this.mIsLaunchIntoPip.booleanValue());
        } else {
            parcel.writeInt(0);
        }
    }

    private void writeRationalToParcel(android.util.Rational rational, android.os.Parcel parcel) {
        if (rational != null) {
            parcel.writeInt(1);
            parcel.writeInt(rational.getNumerator());
            parcel.writeInt(rational.getDenominator());
            return;
        }
        parcel.writeInt(0);
    }

    private android.util.Rational readRationalFromParcel(android.os.Parcel parcel) {
        if (parcel.readInt() != 0) {
            return new android.util.Rational(parcel.readInt(), parcel.readInt());
        }
        return null;
    }

    public java.lang.String toString() {
        return "PictureInPictureParams( aspectRatio=" + getAspectRatio() + " expandedAspectRatio=" + this.mExpandedAspectRatio + " sourceRectHint=" + getSourceRectHint() + " hasSetActions=" + hasSetActions() + " hasSetCloseAction=" + hasSetCloseAction() + " isAutoPipEnabled=" + isAutoEnterEnabled() + " isSeamlessResizeEnabled=" + isSeamlessResizeEnabled() + " title=" + ((java.lang.Object) getTitle()) + " subtitle=" + ((java.lang.Object) getSubtitle()) + " isLaunchIntoPip=" + isLaunchIntoPip() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
    }
}
