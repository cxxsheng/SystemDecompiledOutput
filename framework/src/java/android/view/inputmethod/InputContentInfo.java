package android.view.inputmethod;

/* loaded from: classes4.dex */
public final class InputContentInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.inputmethod.InputContentInfo> CREATOR = new android.os.Parcelable.Creator<android.view.inputmethod.InputContentInfo>() { // from class: android.view.inputmethod.InputContentInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.InputContentInfo createFromParcel(android.os.Parcel parcel) {
            return new android.view.inputmethod.InputContentInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.InputContentInfo[] newArray(int i) {
            return new android.view.inputmethod.InputContentInfo[i];
        }
    };
    private final android.net.Uri mContentUri;
    private final int mContentUriOwnerUserId;
    private final android.content.ClipDescription mDescription;
    private final android.net.Uri mLinkUri;
    private com.android.internal.inputmethod.IInputContentUriToken mUriToken;

    public InputContentInfo(android.net.Uri uri, android.content.ClipDescription clipDescription) {
        this(uri, clipDescription, null);
    }

    public InputContentInfo(android.net.Uri uri, android.content.ClipDescription clipDescription, android.net.Uri uri2) {
        validateInternal(uri, clipDescription, uri2, true);
        this.mContentUri = uri;
        this.mContentUriOwnerUserId = android.content.ContentProvider.getUserIdFromUri(this.mContentUri, android.os.UserHandle.myUserId());
        this.mDescription = clipDescription;
        this.mLinkUri = uri2;
    }

    public boolean validate() {
        return validateInternal(this.mContentUri, this.mDescription, this.mLinkUri, false);
    }

    private static boolean validateInternal(android.net.Uri uri, android.content.ClipDescription clipDescription, android.net.Uri uri2, boolean z) {
        if (uri == null) {
            if (!z) {
                return false;
            }
            throw new java.lang.NullPointerException("contentUri");
        }
        if (clipDescription == null) {
            if (!z) {
                return false;
            }
            throw new java.lang.NullPointerException("description");
        }
        if (!"content".equals(uri.getScheme())) {
            if (!z) {
                return false;
            }
            throw new java.security.InvalidParameterException("contentUri must have content scheme");
        }
        if (uri2 != null) {
            java.lang.String scheme = uri2.getScheme();
            if (scheme == null || (!scheme.equalsIgnoreCase(android.content.IntentFilter.SCHEME_HTTP) && !scheme.equalsIgnoreCase(android.content.IntentFilter.SCHEME_HTTPS))) {
                if (!z) {
                    return false;
                }
                throw new java.security.InvalidParameterException("linkUri must have either http or https scheme");
            }
            return true;
        }
        return true;
    }

    public android.net.Uri getContentUri() {
        if (this.mContentUriOwnerUserId != android.os.UserHandle.myUserId()) {
            return android.content.ContentProvider.maybeAddUserId(this.mContentUri, this.mContentUriOwnerUserId);
        }
        return this.mContentUri;
    }

    public android.content.ClipDescription getDescription() {
        return this.mDescription;
    }

    public android.net.Uri getLinkUri() {
        return this.mLinkUri;
    }

    public void setUriToken(com.android.internal.inputmethod.IInputContentUriToken iInputContentUriToken) {
        if (this.mUriToken != null) {
            throw new java.lang.IllegalStateException("URI token is already set");
        }
        this.mUriToken = iInputContentUriToken;
    }

    public void requestPermission() {
        if (this.mUriToken == null) {
            return;
        }
        try {
            this.mUriToken.take();
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void releasePermission() {
        if (this.mUriToken == null) {
            return;
        }
        try {
            this.mUriToken.release();
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        android.net.Uri.writeToParcel(parcel, this.mContentUri);
        parcel.writeInt(this.mContentUriOwnerUserId);
        this.mDescription.writeToParcel(parcel, i);
        android.net.Uri.writeToParcel(parcel, this.mLinkUri);
        if (this.mUriToken != null) {
            parcel.writeInt(1);
            parcel.writeStrongBinder(this.mUriToken.asBinder());
        } else {
            parcel.writeInt(0);
        }
    }

    private InputContentInfo(android.os.Parcel parcel) {
        this.mContentUri = android.net.Uri.CREATOR.createFromParcel(parcel);
        this.mContentUriOwnerUserId = parcel.readInt();
        this.mDescription = android.content.ClipDescription.CREATOR.createFromParcel(parcel);
        this.mLinkUri = android.net.Uri.CREATOR.createFromParcel(parcel);
        if (parcel.readInt() == 1) {
            this.mUriToken = com.android.internal.inputmethod.IInputContentUriToken.Stub.asInterface(parcel.readStrongBinder());
        } else {
            this.mUriToken = null;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
