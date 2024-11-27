package android.view;

/* loaded from: classes4.dex */
public class ScrollCaptureResponse implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.ScrollCaptureResponse> CREATOR = new android.os.Parcelable.Creator<android.view.ScrollCaptureResponse>() { // from class: android.view.ScrollCaptureResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.ScrollCaptureResponse[] newArray(int i) {
            return new android.view.ScrollCaptureResponse[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.ScrollCaptureResponse createFromParcel(android.os.Parcel parcel) {
            return new android.view.ScrollCaptureResponse(parcel);
        }
    };
    private android.graphics.Rect mBoundsInWindow;
    private android.view.IScrollCaptureConnection mConnection;
    private java.lang.String mDescription;
    private java.util.ArrayList<java.lang.String> mMessages;
    private java.lang.String mPackageName;
    private android.graphics.Rect mWindowBounds;
    private java.lang.String mWindowTitle;

    public boolean isConnected() {
        return this.mConnection != null && this.mConnection.asBinder().isBinderAlive();
    }

    public void close() {
        if (this.mConnection != null) {
            try {
                this.mConnection.close();
            } catch (android.os.RemoteException e) {
            }
            this.mConnection = null;
        }
    }

    ScrollCaptureResponse(java.lang.String str, android.view.IScrollCaptureConnection iScrollCaptureConnection, android.graphics.Rect rect, android.graphics.Rect rect2, java.lang.String str2, java.lang.String str3, java.util.ArrayList<java.lang.String> arrayList) {
        this.mDescription = "";
        this.mConnection = null;
        this.mWindowBounds = null;
        this.mBoundsInWindow = null;
        this.mWindowTitle = null;
        this.mPackageName = null;
        this.mMessages = new java.util.ArrayList<>();
        this.mDescription = str;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mDescription);
        this.mConnection = iScrollCaptureConnection;
        this.mWindowBounds = rect;
        this.mBoundsInWindow = rect2;
        this.mWindowTitle = str2;
        this.mPackageName = str3;
        this.mMessages = arrayList;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mMessages);
    }

    public java.lang.String getDescription() {
        return this.mDescription;
    }

    public android.view.IScrollCaptureConnection getConnection() {
        return this.mConnection;
    }

    public android.graphics.Rect getWindowBounds() {
        return this.mWindowBounds;
    }

    public android.graphics.Rect getBoundsInWindow() {
        return this.mBoundsInWindow;
    }

    public java.lang.String getWindowTitle() {
        return this.mWindowTitle;
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public java.util.ArrayList<java.lang.String> getMessages() {
        return this.mMessages;
    }

    public java.lang.String toString() {
        return "ScrollCaptureResponse { description = " + this.mDescription + ", connection = " + this.mConnection + ", windowBounds = " + this.mWindowBounds + ", boundsInWindow = " + this.mBoundsInWindow + ", windowTitle = " + this.mWindowTitle + ", packageName = " + this.mPackageName + ", messages = " + this.mMessages + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        byte b = this.mConnection != null ? (byte) 2 : (byte) 0;
        if (this.mWindowBounds != null) {
            b = (byte) (b | 4);
        }
        if (this.mBoundsInWindow != null) {
            b = (byte) (b | 8);
        }
        if (this.mWindowTitle != null) {
            b = (byte) (b | 16);
        }
        if (this.mPackageName != null) {
            b = (byte) (b | com.android.net.module.util.NetworkStackConstants.TCPHDR_URG);
        }
        parcel.writeByte(b);
        parcel.writeString(this.mDescription);
        if (this.mConnection != null) {
            parcel.writeStrongInterface(this.mConnection);
        }
        if (this.mWindowBounds != null) {
            parcel.writeTypedObject(this.mWindowBounds, i);
        }
        if (this.mBoundsInWindow != null) {
            parcel.writeTypedObject(this.mBoundsInWindow, i);
        }
        if (this.mWindowTitle != null) {
            parcel.writeString(this.mWindowTitle);
        }
        if (this.mPackageName != null) {
            parcel.writeString(this.mPackageName);
        }
        parcel.writeStringList(this.mMessages);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected ScrollCaptureResponse(android.os.Parcel parcel) {
        this.mDescription = "";
        this.mConnection = null;
        this.mWindowBounds = null;
        this.mBoundsInWindow = null;
        this.mWindowTitle = null;
        this.mPackageName = null;
        this.mMessages = new java.util.ArrayList<>();
        byte readByte = parcel.readByte();
        java.lang.String readString = parcel.readString();
        android.view.IScrollCaptureConnection asInterface = (readByte & 2) == 0 ? null : android.view.IScrollCaptureConnection.Stub.asInterface(parcel.readStrongBinder());
        android.graphics.Rect rect = (readByte & 4) == 0 ? null : (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
        android.graphics.Rect rect2 = (readByte & 8) == 0 ? null : (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
        java.lang.String readString2 = (readByte & 16) == 0 ? null : parcel.readString();
        java.lang.String readString3 = (readByte & com.android.net.module.util.NetworkStackConstants.TCPHDR_URG) == 0 ? null : parcel.readString();
        java.util.ArrayList<java.lang.String> arrayList = new java.util.ArrayList<>();
        parcel.readStringList(arrayList);
        this.mDescription = readString;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mDescription);
        this.mConnection = asInterface;
        this.mWindowBounds = rect;
        this.mBoundsInWindow = rect2;
        this.mWindowTitle = readString2;
        this.mPackageName = readString3;
        this.mMessages = arrayList;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mMessages);
    }

    public static class Builder {
        private android.graphics.Rect mBoundsInWindow;
        private long mBuilderFieldsSet = 0;
        private android.view.IScrollCaptureConnection mConnection;
        private java.lang.String mDescription;
        private java.util.ArrayList<java.lang.String> mMessages;
        private java.lang.String mPackageName;
        private android.graphics.Rect mWindowBounds;
        private java.lang.String mWindowTitle;

        public android.view.ScrollCaptureResponse.Builder setDescription(java.lang.String str) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mDescription = str;
            return this;
        }

        public android.view.ScrollCaptureResponse.Builder setConnection(android.view.IScrollCaptureConnection iScrollCaptureConnection) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mConnection = iScrollCaptureConnection;
            return this;
        }

        public android.view.ScrollCaptureResponse.Builder setWindowBounds(android.graphics.Rect rect) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mWindowBounds = rect;
            return this;
        }

        public android.view.ScrollCaptureResponse.Builder setBoundsInWindow(android.graphics.Rect rect) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mBoundsInWindow = rect;
            return this;
        }

        public android.view.ScrollCaptureResponse.Builder setWindowTitle(java.lang.String str) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            this.mWindowTitle = str;
            return this;
        }

        public android.view.ScrollCaptureResponse.Builder setPackageName(java.lang.String str) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 32;
            this.mPackageName = str;
            return this;
        }

        public android.view.ScrollCaptureResponse.Builder setMessages(java.util.ArrayList<java.lang.String> arrayList) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 64;
            this.mMessages = arrayList;
            return this;
        }

        public android.view.ScrollCaptureResponse.Builder addMessage(java.lang.String str) {
            if (this.mMessages == null) {
                setMessages(new java.util.ArrayList<>());
            }
            this.mMessages.add(str);
            return this;
        }

        public android.view.ScrollCaptureResponse build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 128;
            if ((this.mBuilderFieldsSet & 1) == 0) {
                this.mDescription = "";
            }
            if ((this.mBuilderFieldsSet & 2) == 0) {
                this.mConnection = null;
            }
            if ((this.mBuilderFieldsSet & 4) == 0) {
                this.mWindowBounds = null;
            }
            if ((this.mBuilderFieldsSet & 8) == 0) {
                this.mBoundsInWindow = null;
            }
            if ((this.mBuilderFieldsSet & 16) == 0) {
                this.mWindowTitle = null;
            }
            if ((this.mBuilderFieldsSet & 32) == 0) {
                this.mPackageName = null;
            }
            if ((this.mBuilderFieldsSet & 64) == 0) {
                this.mMessages = new java.util.ArrayList<>();
            }
            return new android.view.ScrollCaptureResponse(this.mDescription, this.mConnection, this.mWindowBounds, this.mBoundsInWindow, this.mWindowTitle, this.mPackageName, this.mMessages);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 128) != 0) {
                throw new java.lang.IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
