package com.android.framework.protobuf;

/* loaded from: classes4.dex */
final class ExtensionRegistryFactory {
    static final java.lang.Class<?> EXTENSION_REGISTRY_CLASS = reflectExtensionRegistry();
    static final java.lang.String FULL_REGISTRY_CLASS_NAME = "com.android.framework.protobuf.ExtensionRegistry";

    ExtensionRegistryFactory() {
    }

    static java.lang.Class<?> reflectExtensionRegistry() {
        try {
            return java.lang.Class.forName(FULL_REGISTRY_CLASS_NAME);
        } catch (java.lang.ClassNotFoundException e) {
            return null;
        }
    }

    public static com.android.framework.protobuf.ExtensionRegistryLite create() {
        com.android.framework.protobuf.ExtensionRegistryLite invokeSubclassFactory = invokeSubclassFactory("newInstance");
        return invokeSubclassFactory != null ? invokeSubclassFactory : new com.android.framework.protobuf.ExtensionRegistryLite();
    }

    public static com.android.framework.protobuf.ExtensionRegistryLite createEmpty() {
        com.android.framework.protobuf.ExtensionRegistryLite invokeSubclassFactory = invokeSubclassFactory("getEmptyRegistry");
        return invokeSubclassFactory != null ? invokeSubclassFactory : com.android.framework.protobuf.ExtensionRegistryLite.EMPTY_REGISTRY_LITE;
    }

    static boolean isFullRegistry(com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) {
        return EXTENSION_REGISTRY_CLASS != null && EXTENSION_REGISTRY_CLASS.isAssignableFrom(extensionRegistryLite.getClass());
    }

    private static final com.android.framework.protobuf.ExtensionRegistryLite invokeSubclassFactory(java.lang.String str) {
        if (EXTENSION_REGISTRY_CLASS == null) {
            return null;
        }
        try {
            return (com.android.framework.protobuf.ExtensionRegistryLite) EXTENSION_REGISTRY_CLASS.getDeclaredMethod(str, new java.lang.Class[0]).invoke(null, new java.lang.Object[0]);
        } catch (java.lang.Exception e) {
            return null;
        }
    }
}
