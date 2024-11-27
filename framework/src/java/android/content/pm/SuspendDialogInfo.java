package android.content.pm;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class SuspendDialogInfo implements android.os.Parcelable {
    public static final int BUTTON_ACTION_MORE_DETAILS = 0;
    public static final int BUTTON_ACTION_UNSUSPEND = 1;
    private static final java.lang.String XML_ATTR_BUTTON_ACTION = "buttonAction";
    private static final java.lang.String XML_ATTR_BUTTON_TEXT = "buttonText";
    private static final java.lang.String XML_ATTR_BUTTON_TEXT_RES_ID = "buttonTextResId";
    private static final java.lang.String XML_ATTR_DIALOG_MESSAGE = "dialogMessage";
    private static final java.lang.String XML_ATTR_DIALOG_MESSAGE_RES_ID = "dialogMessageResId";
    private static final java.lang.String XML_ATTR_ICON_RES_ID = "iconResId";
    private static final java.lang.String XML_ATTR_TITLE = "title";
    private static final java.lang.String XML_ATTR_TITLE_RES_ID = "titleResId";
    private final java.lang.String mDialogMessage;
    private final int mDialogMessageResId;
    private final int mIconResId;
    private final int mNeutralButtonAction;
    private final java.lang.String mNeutralButtonText;
    private final int mNeutralButtonTextResId;
    private final java.lang.String mTitle;
    private final int mTitleResId;
    private static final java.lang.String TAG = android.content.pm.SuspendDialogInfo.class.getSimpleName();
    public static final android.os.Parcelable.Creator<android.content.pm.SuspendDialogInfo> CREATOR = new android.os.Parcelable.Creator<android.content.pm.SuspendDialogInfo>() { // from class: android.content.pm.SuspendDialogInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.SuspendDialogInfo createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.SuspendDialogInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.SuspendDialogInfo[] newArray(int i) {
            return new android.content.pm.SuspendDialogInfo[i];
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ButtonAction {
    }

    public int getIconResId() {
        return this.mIconResId;
    }

    public int getTitleResId() {
        return this.mTitleResId;
    }

    public java.lang.String getTitle() {
        return this.mTitle;
    }

    public int getDialogMessageResId() {
        return this.mDialogMessageResId;
    }

    public java.lang.String getDialogMessage() {
        return this.mDialogMessage;
    }

    public int getNeutralButtonTextResId() {
        return this.mNeutralButtonTextResId;
    }

    public java.lang.String getNeutralButtonText() {
        return this.mNeutralButtonText;
    }

    public int getNeutralButtonAction() {
        return this.mNeutralButtonAction;
    }

    public void saveToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        if (this.mIconResId != 0) {
            typedXmlSerializer.attributeInt(null, "iconResId", this.mIconResId);
        }
        if (this.mTitleResId != 0) {
            typedXmlSerializer.attributeInt(null, XML_ATTR_TITLE_RES_ID, this.mTitleResId);
        } else {
            com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, "title", this.mTitle);
        }
        if (this.mDialogMessageResId != 0) {
            typedXmlSerializer.attributeInt(null, XML_ATTR_DIALOG_MESSAGE_RES_ID, this.mDialogMessageResId);
        } else {
            com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, XML_ATTR_DIALOG_MESSAGE, this.mDialogMessage);
        }
        if (this.mNeutralButtonTextResId != 0) {
            typedXmlSerializer.attributeInt(null, XML_ATTR_BUTTON_TEXT_RES_ID, this.mNeutralButtonTextResId);
        } else {
            com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, XML_ATTR_BUTTON_TEXT, this.mNeutralButtonText);
        }
        typedXmlSerializer.attributeInt(null, XML_ATTR_BUTTON_ACTION, this.mNeutralButtonAction);
    }

    public static android.content.pm.SuspendDialogInfo restoreFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) {
        android.content.pm.SuspendDialogInfo.Builder builder = new android.content.pm.SuspendDialogInfo.Builder();
        try {
            int attributeInt = typedXmlPullParser.getAttributeInt(null, "iconResId", 0);
            int attributeInt2 = typedXmlPullParser.getAttributeInt(null, XML_ATTR_TITLE_RES_ID, 0);
            java.lang.String readStringAttribute = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, "title");
            int attributeInt3 = typedXmlPullParser.getAttributeInt(null, XML_ATTR_BUTTON_TEXT_RES_ID, 0);
            java.lang.String readStringAttribute2 = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, XML_ATTR_BUTTON_TEXT);
            int attributeInt4 = typedXmlPullParser.getAttributeInt(null, XML_ATTR_BUTTON_ACTION, 0);
            int attributeInt5 = typedXmlPullParser.getAttributeInt(null, XML_ATTR_DIALOG_MESSAGE_RES_ID, 0);
            java.lang.String readStringAttribute3 = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, XML_ATTR_DIALOG_MESSAGE);
            if (attributeInt != 0) {
                builder.setIcon(attributeInt);
            }
            if (attributeInt2 != 0) {
                builder.setTitle(attributeInt2);
            } else if (readStringAttribute != null) {
                builder.setTitle(readStringAttribute);
            }
            if (attributeInt3 != 0) {
                builder.setNeutralButtonText(attributeInt3);
            } else if (readStringAttribute2 != null) {
                builder.setNeutralButtonText(readStringAttribute2);
            }
            if (attributeInt5 != 0) {
                builder.setMessage(attributeInt5);
            } else if (readStringAttribute3 != null) {
                builder.setMessage(readStringAttribute3);
            }
            builder.setNeutralButtonAction(attributeInt4);
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Exception while parsing from xml. Some fields may default", e);
        }
        return builder.build();
    }

    public int hashCode() {
        return (((((((((((((this.mIconResId * 31) + this.mTitleResId) * 31) + java.util.Objects.hashCode(this.mTitle)) * 31) + this.mNeutralButtonTextResId) * 31) + java.util.Objects.hashCode(this.mNeutralButtonText)) * 31) + this.mDialogMessageResId) * 31) + java.util.Objects.hashCode(this.mDialogMessage)) * 31) + this.mNeutralButtonAction;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.content.pm.SuspendDialogInfo)) {
            return false;
        }
        android.content.pm.SuspendDialogInfo suspendDialogInfo = (android.content.pm.SuspendDialogInfo) obj;
        return this.mIconResId == suspendDialogInfo.mIconResId && this.mTitleResId == suspendDialogInfo.mTitleResId && java.util.Objects.equals(this.mTitle, suspendDialogInfo.mTitle) && this.mDialogMessageResId == suspendDialogInfo.mDialogMessageResId && java.util.Objects.equals(this.mDialogMessage, suspendDialogInfo.mDialogMessage) && this.mNeutralButtonTextResId == suspendDialogInfo.mNeutralButtonTextResId && java.util.Objects.equals(this.mNeutralButtonText, suspendDialogInfo.mNeutralButtonText) && this.mNeutralButtonAction == suspendDialogInfo.mNeutralButtonAction;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("SuspendDialogInfo: {");
        if (this.mIconResId != 0) {
            sb.append("mIconId = 0x");
            sb.append(java.lang.Integer.toHexString(this.mIconResId));
            sb.append(" ");
        }
        if (this.mTitleResId != 0) {
            sb.append("mTitleResId = 0x");
            sb.append(java.lang.Integer.toHexString(this.mTitleResId));
            sb.append(" ");
        } else if (this.mTitle != null) {
            sb.append("mTitle = \"");
            sb.append(this.mTitle);
            sb.append("\"");
        }
        if (this.mNeutralButtonTextResId != 0) {
            sb.append("mNeutralButtonTextResId = 0x");
            sb.append(java.lang.Integer.toHexString(this.mNeutralButtonTextResId));
            sb.append(" ");
        } else if (this.mNeutralButtonText != null) {
            sb.append("mNeutralButtonText = \"");
            sb.append(this.mNeutralButtonText);
            sb.append("\"");
        }
        if (this.mDialogMessageResId != 0) {
            sb.append("mDialogMessageResId = 0x");
            sb.append(java.lang.Integer.toHexString(this.mDialogMessageResId));
            sb.append(" ");
        } else if (this.mDialogMessage != null) {
            sb.append("mDialogMessage = \"");
            sb.append(this.mDialogMessage);
            sb.append("\" ");
        }
        sb.append("mNeutralButtonAction = ");
        sb.append(this.mNeutralButtonAction);
        sb.append("}");
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mIconResId);
        parcel.writeInt(this.mTitleResId);
        parcel.writeString(this.mTitle);
        parcel.writeInt(this.mDialogMessageResId);
        parcel.writeString(this.mDialogMessage);
        parcel.writeInt(this.mNeutralButtonTextResId);
        parcel.writeString(this.mNeutralButtonText);
        parcel.writeInt(this.mNeutralButtonAction);
    }

    private SuspendDialogInfo(android.os.Parcel parcel) {
        this.mIconResId = parcel.readInt();
        this.mTitleResId = parcel.readInt();
        this.mTitle = parcel.readString();
        this.mDialogMessageResId = parcel.readInt();
        this.mDialogMessage = parcel.readString();
        this.mNeutralButtonTextResId = parcel.readInt();
        this.mNeutralButtonText = parcel.readString();
        this.mNeutralButtonAction = parcel.readInt();
    }

    SuspendDialogInfo(android.content.pm.SuspendDialogInfo.Builder builder) {
        this.mIconResId = builder.mIconResId;
        this.mTitleResId = builder.mTitleResId;
        this.mTitle = this.mTitleResId == 0 ? builder.mTitle : null;
        this.mDialogMessageResId = builder.mDialogMessageResId;
        this.mDialogMessage = this.mDialogMessageResId == 0 ? builder.mDialogMessage : null;
        this.mNeutralButtonTextResId = builder.mNeutralButtonTextResId;
        this.mNeutralButtonText = this.mNeutralButtonTextResId == 0 ? builder.mNeutralButtonText : null;
        this.mNeutralButtonAction = builder.mNeutralButtonAction;
    }

    public static final class Builder {
        private java.lang.String mDialogMessage;
        private java.lang.String mNeutralButtonText;
        private java.lang.String mTitle;
        private int mDialogMessageResId = 0;
        private int mTitleResId = 0;
        private int mIconResId = 0;
        private int mNeutralButtonTextResId = 0;
        private int mNeutralButtonAction = 0;

        public android.content.pm.SuspendDialogInfo.Builder setIcon(int i) {
            com.android.internal.util.Preconditions.checkArgument(android.content.res.ResourceId.isValid(i), "Invalid resource id provided");
            this.mIconResId = i;
            return this;
        }

        public android.content.pm.SuspendDialogInfo.Builder setTitle(int i) {
            com.android.internal.util.Preconditions.checkArgument(android.content.res.ResourceId.isValid(i), "Invalid resource id provided");
            this.mTitleResId = i;
            return this;
        }

        public android.content.pm.SuspendDialogInfo.Builder setTitle(java.lang.String str) {
            com.android.internal.util.Preconditions.checkStringNotEmpty(str, "Title cannot be null or empty");
            this.mTitle = str;
            return this;
        }

        public android.content.pm.SuspendDialogInfo.Builder setMessage(java.lang.String str) {
            com.android.internal.util.Preconditions.checkStringNotEmpty(str, "Message cannot be null or empty");
            this.mDialogMessage = str;
            return this;
        }

        public android.content.pm.SuspendDialogInfo.Builder setMessage(int i) {
            com.android.internal.util.Preconditions.checkArgument(android.content.res.ResourceId.isValid(i), "Invalid resource id provided");
            this.mDialogMessageResId = i;
            return this;
        }

        public android.content.pm.SuspendDialogInfo.Builder setNeutralButtonText(int i) {
            com.android.internal.util.Preconditions.checkArgument(android.content.res.ResourceId.isValid(i), "Invalid resource id provided");
            this.mNeutralButtonTextResId = i;
            return this;
        }

        public android.content.pm.SuspendDialogInfo.Builder setNeutralButtonText(java.lang.String str) {
            com.android.internal.util.Preconditions.checkStringNotEmpty(str, "Button text cannot be null or empty");
            this.mNeutralButtonText = str;
            return this;
        }

        public android.content.pm.SuspendDialogInfo.Builder setNeutralButtonAction(int i) {
            boolean z = true;
            if (i != 0 && i != 1) {
                z = false;
            }
            com.android.internal.util.Preconditions.checkArgument(z, "Invalid button action");
            this.mNeutralButtonAction = i;
            return this;
        }

        public android.content.pm.SuspendDialogInfo build() {
            return new android.content.pm.SuspendDialogInfo(this);
        }
    }
}
