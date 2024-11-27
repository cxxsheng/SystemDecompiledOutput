package android.telephony.gba;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class UaSecurityProtocolIdentifier implements android.os.Parcelable {
    public static final int ORG_3GPP = 1;
    public static final int ORG_3GPP2 = 2;
    public static final int ORG_GSMA = 4;
    public static final int ORG_LOCAL = 255;
    public static final int ORG_NONE = 0;
    public static final int ORG_OMA = 3;
    private static final int PROTOCOL_SIZE = 5;
    public static final int UA_SECURITY_PROTOCOL_3GPP_GENERATION_TMPI = 256;
    public static final int UA_SECURITY_PROTOCOL_3GPP_GENERIC_PUSH_LAYER = 5;
    public static final int UA_SECURITY_PROTOCOL_3GPP_HTTP_BASED_MBMS = 3;
    public static final int UA_SECURITY_PROTOCOL_3GPP_HTTP_DIGEST_AUTHENTICATION = 2;
    public static final int UA_SECURITY_PROTOCOL_3GPP_IMS_MEDIA_PLANE = 6;
    public static final int UA_SECURITY_PROTOCOL_3GPP_MBMS = 1;
    public static final int UA_SECURITY_PROTOCOL_3GPP_SIP_BASED_MBMS = 4;
    public static final int UA_SECURITY_PROTOCOL_3GPP_SUBSCRIBER_CERTIFICATE = 0;
    public static final int UA_SECURITY_PROTOCOL_3GPP_TLS_BROWSER = 131072;
    public static final int UA_SECURITY_PROTOCOL_3GPP_TLS_DEFAULT = 65536;
    private int mOrg;
    private int mProtocol;
    private int mTlsCipherSuite;
    private static final int[] sUaSp3gppIds = {0, 1, 2, 3, 4, 5, 6, 256, 65536, 131072};
    public static final android.os.Parcelable.Creator<android.telephony.gba.UaSecurityProtocolIdentifier> CREATOR = new android.os.Parcelable.Creator<android.telephony.gba.UaSecurityProtocolIdentifier>() { // from class: android.telephony.gba.UaSecurityProtocolIdentifier.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.gba.UaSecurityProtocolIdentifier createFromParcel(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            if (readInt < 0 || readInt2 < 0 || readInt3 < 0) {
                return null;
            }
            android.telephony.gba.UaSecurityProtocolIdentifier.Builder builder = new android.telephony.gba.UaSecurityProtocolIdentifier.Builder();
            if (readInt > 0) {
                try {
                    builder.setOrg(readInt);
                } catch (java.lang.IllegalArgumentException e) {
                    return null;
                }
            }
            if (readInt2 > 0) {
                builder.setProtocol(readInt2);
            }
            if (readInt3 > 0) {
                builder.setTlsCipherSuite(readInt3);
            }
            return builder.build();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.gba.UaSecurityProtocolIdentifier[] newArray(int i) {
            return new android.telephony.gba.UaSecurityProtocolIdentifier[i];
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface OrganizationCode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface UaSecurityProtocol3gpp {
    }

    private UaSecurityProtocolIdentifier() {
    }

    private UaSecurityProtocolIdentifier(android.telephony.gba.UaSecurityProtocolIdentifier uaSecurityProtocolIdentifier) {
        this.mOrg = uaSecurityProtocolIdentifier.mOrg;
        this.mProtocol = uaSecurityProtocolIdentifier.mProtocol;
        this.mTlsCipherSuite = uaSecurityProtocolIdentifier.mTlsCipherSuite;
    }

    public byte[] toByteArray() {
        byte[] bArr = new byte[5];
        java.nio.ByteBuffer wrap = java.nio.ByteBuffer.wrap(bArr);
        wrap.put((byte) this.mOrg);
        wrap.putInt(this.mProtocol | this.mTlsCipherSuite);
        return bArr;
    }

    public int getOrg() {
        return this.mOrg;
    }

    public int getProtocol() {
        return this.mProtocol;
    }

    public int getTlsCipherSuite() {
        return this.mTlsCipherSuite;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mOrg);
        parcel.writeInt(this.mProtocol);
        parcel.writeInt(this.mTlsCipherSuite);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "UaSecurityProtocolIdentifier[" + this.mOrg + " , " + (this.mProtocol | this.mTlsCipherSuite) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.telephony.gba.UaSecurityProtocolIdentifier)) {
            return false;
        }
        android.telephony.gba.UaSecurityProtocolIdentifier uaSecurityProtocolIdentifier = (android.telephony.gba.UaSecurityProtocolIdentifier) obj;
        return this.mOrg == uaSecurityProtocolIdentifier.mOrg && this.mProtocol == uaSecurityProtocolIdentifier.mProtocol && this.mTlsCipherSuite == uaSecurityProtocolIdentifier.mTlsCipherSuite;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mOrg), java.lang.Integer.valueOf(this.mProtocol), java.lang.Integer.valueOf(this.mTlsCipherSuite));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isTlsSupported() {
        if (this.mOrg == 1) {
            return this.mProtocol == 65536 || this.mProtocol == 131072;
        }
        return false;
    }

    public static final class Builder {
        private final android.telephony.gba.UaSecurityProtocolIdentifier mSp;

        public Builder() {
            this.mSp = new android.telephony.gba.UaSecurityProtocolIdentifier();
        }

        public Builder(android.telephony.gba.UaSecurityProtocolIdentifier uaSecurityProtocolIdentifier) {
            java.util.Objects.requireNonNull(uaSecurityProtocolIdentifier);
            this.mSp = new android.telephony.gba.UaSecurityProtocolIdentifier();
        }

        public android.telephony.gba.UaSecurityProtocolIdentifier.Builder setOrg(int i) {
            if (i < 0 || i > 255) {
                throw new java.lang.IllegalArgumentException("illegal organization code");
            }
            this.mSp.mOrg = i;
            this.mSp.mProtocol = 0;
            this.mSp.mTlsCipherSuite = 0;
            return this;
        }

        public android.telephony.gba.UaSecurityProtocolIdentifier.Builder setProtocol(int i) {
            if (i < 0 || ((i > 6 && i != 256 && i != 65536 && i != 131072) || this.mSp.mOrg != 1)) {
                throw new java.lang.IllegalArgumentException("illegal protocol code");
            }
            this.mSp.mProtocol = i;
            this.mSp.mTlsCipherSuite = 0;
            return this;
        }

        public android.telephony.gba.UaSecurityProtocolIdentifier.Builder setTlsCipherSuite(int i) {
            if (!this.mSp.isTlsSupported()) {
                throw new java.lang.IllegalArgumentException("The protocol does not support TLS");
            }
            if (!android.telephony.gba.TlsParams.isTlsCipherSuiteSupported(i)) {
                throw new java.lang.IllegalArgumentException("TLS cipher suite is not supported");
            }
            this.mSp.mTlsCipherSuite = i;
            return this;
        }

        public android.telephony.gba.UaSecurityProtocolIdentifier build() {
            return new android.telephony.gba.UaSecurityProtocolIdentifier();
        }
    }
}
