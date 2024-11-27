package com.android.server.accounts;

/* loaded from: classes.dex */
class AccountAuthenticatorCache extends android.content.pm.RegisteredServicesCache<android.accounts.AuthenticatorDescription> implements com.android.server.accounts.IAccountAuthenticatorCache {
    private static final java.lang.String TAG = "Account";
    private static final com.android.server.accounts.AccountAuthenticatorCache.MySerializer sSerializer = new com.android.server.accounts.AccountAuthenticatorCache.MySerializer();

    @Override // com.android.server.accounts.IAccountAuthenticatorCache
    public /* bridge */ /* synthetic */ android.content.pm.RegisteredServicesCache.ServiceInfo getServiceInfo(android.accounts.AuthenticatorDescription authenticatorDescription, int i) {
        return super.getServiceInfo(authenticatorDescription, i);
    }

    public AccountAuthenticatorCache(android.content.Context context) {
        super(context, "android.accounts.AccountAuthenticator", "android.accounts.AccountAuthenticator", "account-authenticator", sSerializer);
    }

    /* renamed from: parseServiceAttributes, reason: merged with bridge method [inline-methods] */
    public android.accounts.AuthenticatorDescription m869parseServiceAttributes(android.content.res.Resources resources, java.lang.String str, android.util.AttributeSet attributeSet) {
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(attributeSet, com.android.internal.R.styleable.AccountAuthenticator);
        try {
            java.lang.String string = obtainAttributes.getString(2);
            int resourceId = obtainAttributes.getResourceId(0, 0);
            int resourceId2 = obtainAttributes.getResourceId(1, 0);
            int resourceId3 = obtainAttributes.getResourceId(3, 0);
            int resourceId4 = obtainAttributes.getResourceId(4, 0);
            boolean z = obtainAttributes.getBoolean(5, false);
            if (!android.text.TextUtils.isEmpty(string)) {
                return new android.accounts.AuthenticatorDescription(string, str, resourceId, resourceId2, resourceId3, resourceId4, z);
            }
            obtainAttributes.recycle();
            return null;
        } finally {
            obtainAttributes.recycle();
        }
    }

    private static class MySerializer implements android.content.pm.XmlSerializerAndParser<android.accounts.AuthenticatorDescription> {
        private MySerializer() {
        }

        public void writeAsXml(android.accounts.AuthenticatorDescription authenticatorDescription, com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
            typedXmlSerializer.attribute((java.lang.String) null, com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE, authenticatorDescription.type);
        }

        /* renamed from: createFromXml, reason: merged with bridge method [inline-methods] */
        public android.accounts.AuthenticatorDescription m870createFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            return android.accounts.AuthenticatorDescription.newKey(typedXmlPullParser.getAttributeValue((java.lang.String) null, com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE));
        }
    }
}
