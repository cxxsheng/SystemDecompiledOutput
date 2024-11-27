package com.android.server.infra;

/* loaded from: classes2.dex */
public final class SecureSettingsServiceNameResolver extends com.android.server.infra.ServiceNameBaseResolver {
    private static final char COMPONENT_NAME_SEPARATOR = ':';

    @android.annotation.NonNull
    private final java.lang.String mProperty;
    private final android.text.TextUtils.SimpleStringSplitter mStringColonSplitter;

    public SecureSettingsServiceNameResolver(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.lang.String str) {
        this(context, str, false);
    }

    public SecureSettingsServiceNameResolver(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.lang.String str, boolean z) {
        super(context, z);
        this.mStringColonSplitter = new android.text.TextUtils.SimpleStringSplitter(COMPONENT_NAME_SEPARATOR);
        this.mProperty = str;
    }

    @Override // com.android.server.infra.ServiceNameResolver
    public void dumpShort(@android.annotation.NonNull java.io.PrintWriter printWriter) {
        printWriter.print("SecureSettingsServiceNamer: prop=");
        printWriter.print(this.mProperty);
    }

    @Override // com.android.server.infra.ServiceNameBaseResolver, com.android.server.infra.ServiceNameResolver
    public void dumpShort(@android.annotation.NonNull java.io.PrintWriter printWriter, int i) {
        printWriter.print("defaultService=");
        printWriter.print(getDefaultServiceName(i));
    }

    @Override // com.android.server.infra.ServiceNameBaseResolver
    public java.lang.String toString() {
        return "SecureSettingsServiceNameResolver[" + this.mProperty + "]";
    }

    @Override // com.android.server.infra.ServiceNameBaseResolver
    public java.lang.String[] readServiceNameList(int i) {
        return parseColonDelimitedServiceNames(android.provider.Settings.Secure.getStringForUser(this.mContext.getContentResolver(), this.mProperty, i));
    }

    @Override // com.android.server.infra.ServiceNameBaseResolver
    public java.lang.String readServiceName(int i) {
        return android.provider.Settings.Secure.getStringForUser(this.mContext.getContentResolver(), this.mProperty, i);
    }

    @Override // com.android.server.infra.ServiceNameResolver
    public void setServiceNameList(java.util.List<java.lang.String> list, int i) {
        if (list == null || list.isEmpty()) {
            android.provider.Settings.Secure.putStringForUser(this.mContext.getContentResolver(), this.mProperty, null, i);
            return;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder(list.get(0));
        for (int i2 = 1; i2 < list.size(); i2++) {
            sb.append(COMPONENT_NAME_SEPARATOR);
            sb.append(list.get(i2));
        }
        android.provider.Settings.Secure.putStringForUser(this.mContext.getContentResolver(), this.mProperty, sb.toString(), i);
    }

    private java.lang.String[] parseColonDelimitedServiceNames(java.lang.String str) {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        if (!android.text.TextUtils.isEmpty(str)) {
            android.text.TextUtils.SimpleStringSplitter simpleStringSplitter = this.mStringColonSplitter;
            simpleStringSplitter.setString(str);
            while (simpleStringSplitter.hasNext()) {
                java.lang.String next = simpleStringSplitter.next();
                if (!android.text.TextUtils.isEmpty(next)) {
                    arraySet.add(next);
                }
            }
        }
        return (java.lang.String[]) arraySet.toArray(new java.lang.String[arraySet.size()]);
    }
}
