package android.app.assist;

/* loaded from: classes.dex */
public class AssistContent implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.assist.AssistContent> CREATOR = new android.os.Parcelable.Creator<android.app.assist.AssistContent>() { // from class: android.app.assist.AssistContent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.assist.AssistContent createFromParcel(android.os.Parcel parcel) {
            return new android.app.assist.AssistContent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.assist.AssistContent[] newArray(int i) {
            return new android.app.assist.AssistContent[i];
        }
    };
    private android.content.ClipData mClipData;
    private final android.os.Bundle mExtras;
    private android.content.Intent mIntent;
    private boolean mIsAppProvidedIntent;
    private boolean mIsAppProvidedWebUri;
    private java.lang.String mStructuredData;
    private android.net.Uri mUri;

    public AssistContent() {
        this.mIsAppProvidedIntent = false;
        this.mIsAppProvidedWebUri = false;
        this.mExtras = new android.os.Bundle();
    }

    public void setDefaultIntent(android.content.Intent intent) {
        android.net.Uri data;
        this.mIntent = intent;
        this.mIsAppProvidedIntent = false;
        this.mIsAppProvidedWebUri = false;
        this.mUri = null;
        if (intent != null && "android.intent.action.VIEW".equals(intent.getAction()) && (data = intent.getData()) != null) {
            if (android.content.IntentFilter.SCHEME_HTTP.equals(data.getScheme()) || android.content.IntentFilter.SCHEME_HTTPS.equals(data.getScheme())) {
                this.mUri = data;
            }
        }
    }

    public void setIntent(android.content.Intent intent) {
        this.mIsAppProvidedIntent = true;
        this.mIntent = intent;
    }

    public android.content.Intent getIntent() {
        return this.mIntent;
    }

    public boolean isAppProvidedIntent() {
        return this.mIsAppProvidedIntent;
    }

    public void setClipData(android.content.ClipData clipData) {
        this.mClipData = clipData;
    }

    public android.content.ClipData getClipData() {
        return this.mClipData;
    }

    public void setStructuredData(java.lang.String str) {
        this.mStructuredData = str;
    }

    public java.lang.String getStructuredData() {
        return this.mStructuredData;
    }

    public void setWebUri(android.net.Uri uri) {
        this.mIsAppProvidedWebUri = true;
        this.mUri = uri;
    }

    public android.net.Uri getWebUri() {
        return this.mUri;
    }

    public boolean isAppProvidedWebUri() {
        return this.mIsAppProvidedWebUri;
    }

    public android.os.Bundle getExtras() {
        return this.mExtras;
    }

    AssistContent(android.os.Parcel parcel) {
        this.mIsAppProvidedIntent = false;
        this.mIsAppProvidedWebUri = false;
        if (parcel.readInt() != 0) {
            this.mIntent = android.content.Intent.CREATOR.createFromParcel(parcel);
        }
        if (parcel.readInt() != 0) {
            this.mClipData = android.content.ClipData.CREATOR.createFromParcel(parcel);
        }
        if (parcel.readInt() != 0) {
            this.mUri = android.net.Uri.CREATOR.createFromParcel(parcel);
        }
        if (parcel.readInt() != 0) {
            this.mStructuredData = parcel.readString();
        }
        this.mIsAppProvidedIntent = parcel.readInt() == 1;
        this.mExtras = parcel.readBundle();
        this.mIsAppProvidedWebUri = parcel.readInt() == 1;
    }

    void writeToParcelInternal(android.os.Parcel parcel, int i) {
        if (this.mIntent != null) {
            parcel.writeInt(1);
            this.mIntent.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        if (this.mClipData != null) {
            parcel.writeInt(1);
            this.mClipData.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        if (this.mUri != null) {
            parcel.writeInt(1);
            this.mUri.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        if (this.mStructuredData != null) {
            parcel.writeInt(1);
            parcel.writeString(this.mStructuredData);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.mIsAppProvidedIntent ? 1 : 0);
        parcel.writeBundle(this.mExtras);
        parcel.writeInt(this.mIsAppProvidedWebUri ? 1 : 0);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        writeToParcelInternal(parcel, i);
    }
}
