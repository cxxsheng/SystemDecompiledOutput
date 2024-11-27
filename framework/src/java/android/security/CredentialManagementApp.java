package android.security;

/* loaded from: classes3.dex */
public class CredentialManagementApp {
    private static final java.lang.String KEY_PACKAGE_NAME = "package_name";
    private static final java.lang.String TAG = "CredentialManagementApp";
    private android.security.AppUriAuthenticationPolicy mAuthenticationPolicy;
    private final java.lang.String mPackageName;

    public CredentialManagementApp(java.lang.String str, android.security.AppUriAuthenticationPolicy appUriAuthenticationPolicy) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(appUriAuthenticationPolicy);
        this.mPackageName = str;
        this.mAuthenticationPolicy = appUriAuthenticationPolicy;
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public android.security.AppUriAuthenticationPolicy getAuthenticationPolicy() {
        return this.mAuthenticationPolicy;
    }

    public void setAuthenticationPolicy(android.security.AppUriAuthenticationPolicy appUriAuthenticationPolicy) {
        java.util.Objects.requireNonNull(appUriAuthenticationPolicy);
        this.mAuthenticationPolicy = appUriAuthenticationPolicy;
    }

    public static android.security.CredentialManagementApp readFromXml(org.xmlpull.v1.XmlPullParser xmlPullParser) {
        try {
            return new android.security.CredentialManagementApp(xmlPullParser.getAttributeValue(null, "package_name"), android.security.AppUriAuthenticationPolicy.readFromXml(xmlPullParser));
        } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
            android.util.Log.w(TAG, "Reading from xml failed", e);
            return null;
        }
    }

    public void writeToXml(org.xmlpull.v1.XmlSerializer xmlSerializer) throws java.io.IOException {
        xmlSerializer.attribute(null, "package_name", this.mPackageName);
        if (this.mAuthenticationPolicy != null) {
            this.mAuthenticationPolicy.writeToXml(xmlSerializer);
        }
    }
}
