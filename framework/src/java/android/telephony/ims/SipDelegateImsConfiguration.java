package android.telephony.ims;

@android.annotation.SystemApi
@java.lang.Deprecated
/* loaded from: classes3.dex */
public final class SipDelegateImsConfiguration implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.ims.SipDelegateImsConfiguration> CREATOR = new android.os.Parcelable.Creator<android.telephony.ims.SipDelegateImsConfiguration>() { // from class: android.telephony.ims.SipDelegateImsConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.SipDelegateImsConfiguration createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.ims.SipDelegateImsConfiguration(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.SipDelegateImsConfiguration[] newArray(int i) {
            return new android.telephony.ims.SipDelegateImsConfiguration[i];
        }
    };
    public static final java.lang.String IPTYPE_IPV4 = "IPV4";
    public static final java.lang.String IPTYPE_IPV6 = "IPV6";
    public static final java.lang.String KEY_SIP_CONFIG_AUTHENTICATION_HEADER_STRING = "sip_config_auhentication_header_string";
    public static final java.lang.String KEY_SIP_CONFIG_AUTHENTICATION_NONCE_STRING = "sip_config_authentication_nonce_string";
    public static final java.lang.String KEY_SIP_CONFIG_CELLULAR_NETWORK_INFO_HEADER_STRING = "sip_config_cellular_network_info_header_string";
    public static final java.lang.String KEY_SIP_CONFIG_HOME_DOMAIN_STRING = "sip_config_home_domain_string";
    public static final java.lang.String KEY_SIP_CONFIG_IMEI_STRING = "sip_config_imei_string";
    public static final java.lang.String KEY_SIP_CONFIG_IPTYPE_STRING = "sip_config_iptype_string";
    public static final java.lang.String KEY_SIP_CONFIG_IS_COMPACT_FORM_ENABLED_BOOL = "sip_config_is_compact_form_enabled_bool";
    public static final java.lang.String KEY_SIP_CONFIG_IS_GRUU_ENABLED_BOOL = "sip_config_is_gruu_enabled_bool";
    public static final java.lang.String KEY_SIP_CONFIG_IS_IPSEC_ENABLED_BOOL = "sip_config_is_ipsec_enabled_bool";
    public static final java.lang.String KEY_SIP_CONFIG_IS_KEEPALIVE_ENABLED_BOOL = "sip_config_is_keepalive_enabled_bool";
    public static final java.lang.String KEY_SIP_CONFIG_IS_NAT_ENABLED_BOOL = "sip_config_is_nat_enabled_bool";
    public static final java.lang.String KEY_SIP_CONFIG_MAX_PAYLOAD_SIZE_ON_UDP_INT = "sip_config_udp_max_payload_size_int";
    public static final java.lang.String KEY_SIP_CONFIG_PATH_HEADER_STRING = "sip_config_path_header_string";
    public static final java.lang.String KEY_SIP_CONFIG_P_ACCESS_NETWORK_INFO_HEADER_STRING = "sip_config_p_access_network_info_header_string";
    public static final java.lang.String KEY_SIP_CONFIG_P_ASSOCIATED_URI_HEADER_STRING = "sip_config_p_associated_uri_header_string";
    public static final java.lang.String KEY_SIP_CONFIG_P_LAST_ACCESS_NETWORK_INFO_HEADER_STRING = "sip_config_p_last_access_network_info_header_string";
    public static final java.lang.String KEY_SIP_CONFIG_SECURITY_VERIFY_HEADER_STRING = "sip_config_security_verify_header_string";
    public static final java.lang.String KEY_SIP_CONFIG_SERVER_DEFAULT_IPADDRESS_STRING = "sip_config_server_default_ipaddress_string";
    public static final java.lang.String KEY_SIP_CONFIG_SERVER_DEFAULT_PORT_INT = "sip_config_server_default_port_int";
    public static final java.lang.String KEY_SIP_CONFIG_SERVER_IPSEC_CLIENT_PORT_INT = "sip_config_server_ipsec_client_port_int";
    public static final java.lang.String KEY_SIP_CONFIG_SERVER_IPSEC_OLD_CLIENT_PORT_INT = "sip_config_server_ipsec_old_client_port_int";
    public static final java.lang.String KEY_SIP_CONFIG_SERVER_IPSEC_SERVER_PORT_INT = "sip_config_server_ipsec_server_port_int";
    public static final java.lang.String KEY_SIP_CONFIG_SERVICE_ROUTE_HEADER_STRING = "sip_config_service_route_header_string";
    public static final java.lang.String KEY_SIP_CONFIG_TRANSPORT_TYPE_STRING = "sip_config_protocol_type_string";
    public static final java.lang.String KEY_SIP_CONFIG_UE_DEFAULT_IPADDRESS_STRING = "sip_config_ue_default_ipaddress_string";
    public static final java.lang.String KEY_SIP_CONFIG_UE_DEFAULT_PORT_INT = "sip_config_ue_default_port_int";
    public static final java.lang.String KEY_SIP_CONFIG_UE_IPSEC_CLIENT_PORT_INT = "sip_config_ue_ipsec_client_port_int";
    public static final java.lang.String KEY_SIP_CONFIG_UE_IPSEC_OLD_CLIENT_PORT_INT = "sip_config_ue_ipsec_old_client_port_int";
    public static final java.lang.String KEY_SIP_CONFIG_UE_IPSEC_SERVER_PORT_INT = "sip_config_ue_ipsec_server_port_int";
    public static final java.lang.String KEY_SIP_CONFIG_UE_PRIVATE_USER_ID_STRING = "sip_config_ue_private_user_id_string";
    public static final java.lang.String KEY_SIP_CONFIG_UE_PUBLIC_GRUU_STRING = "sip_config_ue_public_gruu_string";
    public static final java.lang.String KEY_SIP_CONFIG_UE_PUBLIC_IPADDRESS_WITH_NAT_STRING = "sip_config_ue_public_ipaddress_with_nat_string";
    public static final java.lang.String KEY_SIP_CONFIG_UE_PUBLIC_PORT_WITH_NAT_INT = "sip_config_ue_public_port_with_nat_int";
    public static final java.lang.String KEY_SIP_CONFIG_UE_PUBLIC_USER_ID_STRING = "sip_config_ue_public_user_id_string";
    public static final java.lang.String KEY_SIP_CONFIG_URI_USER_PART_STRING = "sip_config_uri_user_part_string";
    public static final java.lang.String KEY_SIP_CONFIG_USER_AGENT_HEADER_STRING = "sip_config_sip_user_agent_header_string";
    public static final java.lang.String SIP_TRANSPORT_TCP = "TCP";
    public static final java.lang.String SIP_TRANSPORT_UDP = "UDP";
    private final android.os.PersistableBundle mBundle;
    private final long mVersion;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface BooleanConfigKey {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface IntConfigKey {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface StringConfigKey {
    }

    public static final class Builder {
        private final android.os.PersistableBundle mBundle;
        private final long mVersion;

        public Builder(int i) {
            this.mVersion = i;
            this.mBundle = new android.os.PersistableBundle();
        }

        public Builder(android.telephony.ims.SipDelegateImsConfiguration sipDelegateImsConfiguration) {
            this.mVersion = sipDelegateImsConfiguration.getVersion() + 1;
            this.mBundle = sipDelegateImsConfiguration.copyBundle();
        }

        public android.telephony.ims.SipDelegateImsConfiguration.Builder addString(java.lang.String str, java.lang.String str2) {
            this.mBundle.putString(str, str2);
            return this;
        }

        public android.telephony.ims.SipDelegateImsConfiguration.Builder addInt(java.lang.String str, int i) {
            this.mBundle.putInt(str, i);
            return this;
        }

        public android.telephony.ims.SipDelegateImsConfiguration.Builder addBoolean(java.lang.String str, boolean z) {
            this.mBundle.putBoolean(str, z);
            return this;
        }

        public android.telephony.ims.SipDelegateImsConfiguration build() {
            return new android.telephony.ims.SipDelegateImsConfiguration(this.mVersion, this.mBundle);
        }
    }

    private SipDelegateImsConfiguration(long j, android.os.PersistableBundle persistableBundle) {
        this.mVersion = j;
        this.mBundle = persistableBundle;
    }

    private SipDelegateImsConfiguration(android.os.Parcel parcel) {
        this.mVersion = parcel.readLong();
        this.mBundle = parcel.readPersistableBundle();
    }

    public boolean containsKey(java.lang.String str) {
        return this.mBundle.containsKey(str);
    }

    public java.lang.String getString(java.lang.String str) {
        return this.mBundle.getString(str);
    }

    public int getInt(java.lang.String str, int i) {
        if (!this.mBundle.containsKey(str)) {
            return i;
        }
        return this.mBundle.getInt(str);
    }

    public boolean getBoolean(java.lang.String str, boolean z) {
        if (!this.mBundle.containsKey(str)) {
            return z;
        }
        return this.mBundle.getBoolean(str);
    }

    public android.os.PersistableBundle copyBundle() {
        return new android.os.PersistableBundle(this.mBundle);
    }

    public long getVersion() {
        return this.mVersion;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.mVersion);
        parcel.writePersistableBundle(this.mBundle);
    }

    public android.telephony.ims.SipDelegateConfiguration toNewConfig() {
        int i;
        android.net.Uri uri;
        java.lang.String string = getString(KEY_SIP_CONFIG_TRANSPORT_TYPE_STRING);
        if (string != null && string.equals(SIP_TRANSPORT_UDP)) {
            i = 0;
        } else {
            i = 1;
        }
        android.telephony.ims.SipDelegateConfiguration.Builder builder = new android.telephony.ims.SipDelegateConfiguration.Builder(this.mVersion, i, getSocketAddr(getString(KEY_SIP_CONFIG_UE_DEFAULT_IPADDRESS_STRING), getInt(KEY_SIP_CONFIG_UE_DEFAULT_PORT_INT, -1)), getSocketAddr(getString(KEY_SIP_CONFIG_SERVER_DEFAULT_IPADDRESS_STRING), getInt(KEY_SIP_CONFIG_SERVER_DEFAULT_PORT_INT, -1)));
        builder.setSipCompactFormEnabled(getBoolean(KEY_SIP_CONFIG_IS_COMPACT_FORM_ENABLED_BOOL, false));
        builder.setSipKeepaliveEnabled(getBoolean(KEY_SIP_CONFIG_IS_KEEPALIVE_ENABLED_BOOL, false));
        builder.setMaxUdpPayloadSizeBytes(getInt(KEY_SIP_CONFIG_MAX_PAYLOAD_SIZE_ON_UDP_INT, -1));
        builder.setPublicUserIdentifier(getString(KEY_SIP_CONFIG_UE_PUBLIC_USER_ID_STRING));
        builder.setPrivateUserIdentifier(getString(KEY_SIP_CONFIG_UE_PRIVATE_USER_ID_STRING));
        builder.setHomeDomain(getString(KEY_SIP_CONFIG_HOME_DOMAIN_STRING));
        builder.setImei(getString(KEY_SIP_CONFIG_IMEI_STRING));
        builder.setSipAuthenticationHeader(getString(KEY_SIP_CONFIG_AUTHENTICATION_HEADER_STRING));
        builder.setSipAuthenticationNonce(getString(KEY_SIP_CONFIG_AUTHENTICATION_NONCE_STRING));
        builder.setSipServiceRouteHeader(getString(KEY_SIP_CONFIG_SERVICE_ROUTE_HEADER_STRING));
        builder.setSipPathHeader(getString(KEY_SIP_CONFIG_PATH_HEADER_STRING));
        builder.setSipUserAgentHeader(getString(KEY_SIP_CONFIG_USER_AGENT_HEADER_STRING));
        builder.setSipContactUserParameter(getString(KEY_SIP_CONFIG_URI_USER_PART_STRING));
        builder.setSipPaniHeader(getString(KEY_SIP_CONFIG_P_ACCESS_NETWORK_INFO_HEADER_STRING));
        builder.setSipPlaniHeader(getString(KEY_SIP_CONFIG_P_LAST_ACCESS_NETWORK_INFO_HEADER_STRING));
        builder.setSipCniHeader(getString(KEY_SIP_CONFIG_CELLULAR_NETWORK_INFO_HEADER_STRING));
        builder.setSipAssociatedUriHeader(getString(KEY_SIP_CONFIG_P_ASSOCIATED_URI_HEADER_STRING));
        if (getBoolean(KEY_SIP_CONFIG_IS_GRUU_ENABLED_BOOL, false)) {
            java.lang.String string2 = getString(KEY_SIP_CONFIG_UE_PUBLIC_GRUU_STRING);
            if (android.text.TextUtils.isEmpty(string2)) {
                uri = null;
            } else {
                uri = android.net.Uri.parse(string2);
            }
            builder.setPublicGruuUri(uri);
        }
        if (getBoolean(KEY_SIP_CONFIG_IS_IPSEC_ENABLED_BOOL, false)) {
            builder.setIpSecConfiguration(new android.telephony.ims.SipDelegateConfiguration.IpSecConfiguration(getInt(KEY_SIP_CONFIG_UE_IPSEC_CLIENT_PORT_INT, -1), getInt(KEY_SIP_CONFIG_UE_IPSEC_SERVER_PORT_INT, -1), getInt(KEY_SIP_CONFIG_UE_IPSEC_OLD_CLIENT_PORT_INT, -1), getInt(KEY_SIP_CONFIG_SERVER_IPSEC_CLIENT_PORT_INT, -1), getInt(KEY_SIP_CONFIG_SERVER_IPSEC_SERVER_PORT_INT, -1), getInt(KEY_SIP_CONFIG_SERVER_IPSEC_OLD_CLIENT_PORT_INT, -1), getString(KEY_SIP_CONFIG_SECURITY_VERIFY_HEADER_STRING)));
        }
        if (getBoolean(KEY_SIP_CONFIG_IS_NAT_ENABLED_BOOL, false)) {
            builder.setNatSocketAddress(getSocketAddr(getString(KEY_SIP_CONFIG_UE_PUBLIC_IPADDRESS_WITH_NAT_STRING), getInt(KEY_SIP_CONFIG_UE_PUBLIC_PORT_WITH_NAT_INT, -1)));
        }
        return builder.build();
    }

    private java.net.InetSocketAddress getSocketAddr(java.lang.String str, int i) {
        return new java.net.InetSocketAddress(android.net.InetAddresses.parseNumericAddress(str), i);
    }
}
