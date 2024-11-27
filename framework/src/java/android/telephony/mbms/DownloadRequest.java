package android.telephony.mbms;

/* loaded from: classes3.dex */
public final class DownloadRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.mbms.DownloadRequest> CREATOR = new android.os.Parcelable.Creator<android.telephony.mbms.DownloadRequest>() { // from class: android.telephony.mbms.DownloadRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.mbms.DownloadRequest createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.mbms.DownloadRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.mbms.DownloadRequest[] newArray(int i) {
            return new android.telephony.mbms.DownloadRequest[i];
        }
    };
    private static final int CURRENT_VERSION = 1;
    private static final java.lang.String LOG_TAG = "MbmsDownloadRequest";
    public static final int MAX_APP_INTENT_SIZE = 50000;
    public static final int MAX_DESTINATION_URI_SIZE = 50000;
    private final android.net.Uri destinationUri;
    private final java.lang.String fileServiceId;
    private final java.lang.String serializedResultIntentForApp;
    private final android.net.Uri sourceUri;
    private final int subscriptionId;
    private final int version;

    private static class SerializationDataContainer implements java.io.Externalizable {
        private java.lang.String appIntent;
        private android.net.Uri destination;
        private java.lang.String fileServiceId;
        private android.net.Uri source;
        private int subscriptionId;
        private int version;

        public SerializationDataContainer() {
        }

        SerializationDataContainer(android.telephony.mbms.DownloadRequest downloadRequest) {
            this.fileServiceId = downloadRequest.fileServiceId;
            this.source = downloadRequest.sourceUri;
            this.destination = downloadRequest.destinationUri;
            this.subscriptionId = downloadRequest.subscriptionId;
            this.appIntent = downloadRequest.serializedResultIntentForApp;
            this.version = downloadRequest.version;
        }

        @Override // java.io.Externalizable
        public void writeExternal(java.io.ObjectOutput objectOutput) throws java.io.IOException {
            objectOutput.write(this.version);
            objectOutput.writeUTF(this.fileServiceId);
            objectOutput.writeUTF(this.source.toString());
            objectOutput.writeUTF(this.destination.toString());
            objectOutput.write(this.subscriptionId);
            objectOutput.writeUTF(this.appIntent);
        }

        @Override // java.io.Externalizable
        public void readExternal(java.io.ObjectInput objectInput) throws java.io.IOException {
            this.version = objectInput.read();
            this.fileServiceId = objectInput.readUTF();
            this.source = android.net.Uri.parse(objectInput.readUTF());
            this.destination = android.net.Uri.parse(objectInput.readUTF());
            this.subscriptionId = objectInput.read();
            this.appIntent = objectInput.readUTF();
        }
    }

    public static class Builder {
        private java.lang.String appIntent;
        private android.net.Uri destination;
        private java.lang.String fileServiceId;
        private android.net.Uri source;
        private int subscriptionId;
        private int version = 1;

        public static android.telephony.mbms.DownloadRequest.Builder fromDownloadRequest(android.telephony.mbms.DownloadRequest downloadRequest) {
            android.telephony.mbms.DownloadRequest.Builder subscriptionId = new android.telephony.mbms.DownloadRequest.Builder(downloadRequest.sourceUri, downloadRequest.destinationUri).setServiceId(downloadRequest.fileServiceId).setSubscriptionId(downloadRequest.subscriptionId);
            subscriptionId.appIntent = downloadRequest.serializedResultIntentForApp;
            return subscriptionId;
        }

        public static android.telephony.mbms.DownloadRequest.Builder fromSerializedRequest(byte[] bArr) {
            try {
                android.telephony.mbms.DownloadRequest.SerializationDataContainer serializationDataContainer = (android.telephony.mbms.DownloadRequest.SerializationDataContainer) new java.io.ObjectInputStream(new java.io.ByteArrayInputStream(bArr)).readObject();
                android.telephony.mbms.DownloadRequest.Builder builder = new android.telephony.mbms.DownloadRequest.Builder(serializationDataContainer.source, serializationDataContainer.destination);
                builder.version = serializationDataContainer.version;
                builder.appIntent = serializationDataContainer.appIntent;
                builder.fileServiceId = serializationDataContainer.fileServiceId;
                builder.subscriptionId = serializationDataContainer.subscriptionId;
                return builder;
            } catch (java.io.IOException e) {
                android.util.Log.e(android.telephony.mbms.DownloadRequest.LOG_TAG, "Got IOException trying to parse opaque data");
                throw new java.lang.IllegalArgumentException(e);
            } catch (java.lang.ClassNotFoundException e2) {
                android.util.Log.e(android.telephony.mbms.DownloadRequest.LOG_TAG, "Got ClassNotFoundException trying to parse opaque data");
                throw new java.lang.IllegalArgumentException(e2);
            }
        }

        public Builder(android.net.Uri uri, android.net.Uri uri2) {
            if (uri == null || uri2 == null) {
                throw new java.lang.IllegalArgumentException("Source and destination URIs must be non-null.");
            }
            this.source = uri;
            this.destination = uri2;
        }

        public android.telephony.mbms.DownloadRequest.Builder setServiceInfo(android.telephony.mbms.FileServiceInfo fileServiceInfo) {
            this.fileServiceId = fileServiceInfo.getServiceId();
            return this;
        }

        @android.annotation.SystemApi
        public android.telephony.mbms.DownloadRequest.Builder setServiceId(java.lang.String str) {
            this.fileServiceId = str;
            return this;
        }

        public android.telephony.mbms.DownloadRequest.Builder setSubscriptionId(int i) {
            this.subscriptionId = i;
            return this;
        }

        public android.telephony.mbms.DownloadRequest.Builder setAppIntent(android.content.Intent intent) {
            this.appIntent = intent.toUri(0);
            if (this.appIntent.length() > 50000) {
                throw new java.lang.IllegalArgumentException("App intent must not exceed length 50000");
            }
            return this;
        }

        public android.telephony.mbms.DownloadRequest build() {
            return new android.telephony.mbms.DownloadRequest(this.fileServiceId, this.source, this.destination, this.subscriptionId, this.appIntent, this.version);
        }
    }

    private DownloadRequest(java.lang.String str, android.net.Uri uri, android.net.Uri uri2, int i, java.lang.String str2, int i2) {
        this.fileServiceId = str;
        this.sourceUri = uri;
        this.subscriptionId = i;
        this.destinationUri = uri2;
        this.serializedResultIntentForApp = str2;
        this.version = i2;
    }

    private DownloadRequest(android.os.Parcel parcel) {
        this.fileServiceId = parcel.readString();
        this.sourceUri = (android.net.Uri) parcel.readParcelable(getClass().getClassLoader(), android.net.Uri.class);
        this.destinationUri = (android.net.Uri) parcel.readParcelable(getClass().getClassLoader(), android.net.Uri.class);
        this.subscriptionId = parcel.readInt();
        this.serializedResultIntentForApp = parcel.readString();
        this.version = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.fileServiceId);
        parcel.writeParcelable(this.sourceUri, i);
        parcel.writeParcelable(this.destinationUri, i);
        parcel.writeInt(this.subscriptionId);
        parcel.writeString(this.serializedResultIntentForApp);
        parcel.writeInt(this.version);
    }

    public java.lang.String getFileServiceId() {
        return this.fileServiceId;
    }

    public android.net.Uri getSourceUri() {
        return this.sourceUri;
    }

    public android.net.Uri getDestinationUri() {
        return this.destinationUri;
    }

    public int getSubscriptionId() {
        return this.subscriptionId;
    }

    public android.content.Intent getIntentForApp() {
        try {
            return android.content.Intent.parseUri(this.serializedResultIntentForApp, 0);
        } catch (java.net.URISyntaxException e) {
            return null;
        }
    }

    public byte[] toByteArray() {
        try {
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
            java.io.ObjectOutputStream objectOutputStream = new java.io.ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(new android.telephony.mbms.DownloadRequest.SerializationDataContainer(this));
            objectOutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (java.io.IOException e) {
            android.util.Log.e(LOG_TAG, "Got IOException trying to serialize opaque data");
            return null;
        }
    }

    public int getVersion() {
        return this.version;
    }

    public static int getMaxAppIntentSize() {
        return 50000;
    }

    public static int getMaxDestinationUriSize() {
        return 50000;
    }

    public java.lang.String getHash() {
        try {
            java.security.MessageDigest messageDigest = java.security.MessageDigest.getInstance("SHA-256");
            if (this.version >= 1) {
                messageDigest.update(this.sourceUri.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8));
                messageDigest.update(this.destinationUri.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8));
                if (this.serializedResultIntentForApp != null) {
                    messageDigest.update(this.serializedResultIntentForApp.getBytes(java.nio.charset.StandardCharsets.UTF_8));
                }
            }
            return android.util.Base64.encodeToString(messageDigest.digest(), 10);
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new java.lang.RuntimeException("Could not get sha256 hash object");
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.telephony.mbms.DownloadRequest)) {
            return false;
        }
        android.telephony.mbms.DownloadRequest downloadRequest = (android.telephony.mbms.DownloadRequest) obj;
        if (this.subscriptionId == downloadRequest.subscriptionId && this.version == downloadRequest.version && java.util.Objects.equals(this.fileServiceId, downloadRequest.fileServiceId) && java.util.Objects.equals(this.sourceUri, downloadRequest.sourceUri) && java.util.Objects.equals(this.destinationUri, downloadRequest.destinationUri) && java.util.Objects.equals(this.serializedResultIntentForApp, downloadRequest.serializedResultIntentForApp)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.fileServiceId, this.sourceUri, this.destinationUri, java.lang.Integer.valueOf(this.subscriptionId), this.serializedResultIntentForApp, java.lang.Integer.valueOf(this.version));
    }
}
