package android.content;

/* loaded from: classes.dex */
public class ContentProviderResult implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.ContentProviderResult> CREATOR = new android.os.Parcelable.Creator<android.content.ContentProviderResult>() { // from class: android.content.ContentProviderResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.ContentProviderResult createFromParcel(android.os.Parcel parcel) {
            return new android.content.ContentProviderResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.ContentProviderResult[] newArray(int i) {
            return new android.content.ContentProviderResult[i];
        }
    };
    public final java.lang.Integer count;
    public final java.lang.Throwable exception;
    public final android.os.Bundle extras;
    public final android.net.Uri uri;

    public ContentProviderResult(android.net.Uri uri) {
        this((android.net.Uri) java.util.Objects.requireNonNull(uri), null, null, null);
    }

    public ContentProviderResult(int i) {
        this(null, java.lang.Integer.valueOf(i), null, null);
    }

    public ContentProviderResult(android.os.Bundle bundle) {
        this(null, null, (android.os.Bundle) java.util.Objects.requireNonNull(bundle), null);
    }

    public ContentProviderResult(java.lang.Throwable th) {
        this(null, null, null, th);
    }

    public ContentProviderResult(android.net.Uri uri, java.lang.Integer num, android.os.Bundle bundle, java.lang.Throwable th) {
        this.uri = uri;
        this.count = num;
        this.extras = bundle;
        this.exception = th;
    }

    public ContentProviderResult(android.os.Parcel parcel) {
        if (parcel.readInt() != 0) {
            this.uri = android.net.Uri.CREATOR.createFromParcel(parcel);
        } else {
            this.uri = null;
        }
        if (parcel.readInt() != 0) {
            this.count = java.lang.Integer.valueOf(parcel.readInt());
        } else {
            this.count = null;
        }
        if (parcel.readInt() != 0) {
            this.extras = parcel.readBundle();
        } else {
            this.extras = null;
        }
        if (parcel.readInt() != 0) {
            this.exception = android.os.ParcelableException.readFromParcel(parcel);
        } else {
            this.exception = null;
        }
    }

    public ContentProviderResult(android.content.ContentProviderResult contentProviderResult, int i) {
        this.uri = android.content.ContentProvider.maybeAddUserId(contentProviderResult.uri, i);
        this.count = contentProviderResult.count;
        this.extras = contentProviderResult.extras;
        this.exception = contentProviderResult.exception;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        if (this.uri != null) {
            parcel.writeInt(1);
            this.uri.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        if (this.count != null) {
            parcel.writeInt(1);
            parcel.writeInt(this.count.intValue());
        } else {
            parcel.writeInt(0);
        }
        if (this.extras != null) {
            parcel.writeInt(1);
            parcel.writeBundle(this.extras);
        } else {
            parcel.writeInt(0);
        }
        if (this.exception != null) {
            parcel.writeInt(1);
            android.os.ParcelableException.writeToParcel(parcel, this.exception);
        } else {
            parcel.writeInt(0);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("ContentProviderResult(");
        if (this.uri != null) {
            sb.append("uri=").append(this.uri).append(' ');
        }
        if (this.count != null) {
            sb.append("count=").append(this.count).append(' ');
        }
        if (this.extras != null) {
            sb.append("extras=").append(this.extras).append(' ');
        }
        if (this.exception != null) {
            sb.append("exception=").append(this.exception).append(' ');
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        return sb.toString();
    }
}
