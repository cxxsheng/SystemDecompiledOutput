package com.android.framework.protobuf;

/* loaded from: classes4.dex */
public class ExtensionRegistryLite {
    static final java.lang.String EXTENSION_CLASS_NAME = "com.android.framework.protobuf.Extension";
    private static volatile com.android.framework.protobuf.ExtensionRegistryLite emptyRegistry;
    private final java.util.Map<com.android.framework.protobuf.ExtensionRegistryLite.ObjectIntPair, com.android.framework.protobuf.GeneratedMessageLite.GeneratedExtension<?, ?>> extensionsByNumber;
    private static volatile boolean eagerlyParseMessageSets = false;
    private static boolean doFullRuntimeInheritanceCheck = true;
    static final com.android.framework.protobuf.ExtensionRegistryLite EMPTY_REGISTRY_LITE = new com.android.framework.protobuf.ExtensionRegistryLite(true);

    private static class ExtensionClassHolder {
        static final java.lang.Class<?> INSTANCE = resolveExtensionClass();

        private ExtensionClassHolder() {
        }

        static java.lang.Class<?> resolveExtensionClass() {
            try {
                return java.lang.Class.forName(com.android.framework.protobuf.ExtensionRegistryLite.EXTENSION_CLASS_NAME);
            } catch (java.lang.ClassNotFoundException e) {
                return null;
            }
        }
    }

    public static boolean isEagerlyParseMessageSets() {
        return eagerlyParseMessageSets;
    }

    public static void setEagerlyParseMessageSets(boolean z) {
        eagerlyParseMessageSets = z;
    }

    public static com.android.framework.protobuf.ExtensionRegistryLite newInstance() {
        if (doFullRuntimeInheritanceCheck) {
            return com.android.framework.protobuf.ExtensionRegistryFactory.create();
        }
        return new com.android.framework.protobuf.ExtensionRegistryLite();
    }

    public static com.android.framework.protobuf.ExtensionRegistryLite getEmptyRegistry() {
        com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite = emptyRegistry;
        if (extensionRegistryLite == null) {
            synchronized (com.android.framework.protobuf.ExtensionRegistryLite.class) {
                extensionRegistryLite = emptyRegistry;
                if (extensionRegistryLite == null) {
                    if (doFullRuntimeInheritanceCheck) {
                        extensionRegistryLite = com.android.framework.protobuf.ExtensionRegistryFactory.createEmpty();
                    } else {
                        extensionRegistryLite = EMPTY_REGISTRY_LITE;
                    }
                    emptyRegistry = extensionRegistryLite;
                }
            }
        }
        return extensionRegistryLite;
    }

    public com.android.framework.protobuf.ExtensionRegistryLite getUnmodifiable() {
        return new com.android.framework.protobuf.ExtensionRegistryLite(this);
    }

    public <ContainingType extends com.android.framework.protobuf.MessageLite> com.android.framework.protobuf.GeneratedMessageLite.GeneratedExtension<ContainingType, ?> findLiteExtensionByNumber(ContainingType containingtype, int i) {
        return (com.android.framework.protobuf.GeneratedMessageLite.GeneratedExtension) this.extensionsByNumber.get(new com.android.framework.protobuf.ExtensionRegistryLite.ObjectIntPair(containingtype, i));
    }

    public final void add(com.android.framework.protobuf.GeneratedMessageLite.GeneratedExtension<?, ?> generatedExtension) {
        this.extensionsByNumber.put(new com.android.framework.protobuf.ExtensionRegistryLite.ObjectIntPair(generatedExtension.getContainingTypeDefaultInstance(), generatedExtension.getNumber()), generatedExtension);
    }

    public final void add(com.android.framework.protobuf.ExtensionLite<?, ?> extensionLite) {
        if (com.android.framework.protobuf.GeneratedMessageLite.GeneratedExtension.class.isAssignableFrom(extensionLite.getClass())) {
            add((com.android.framework.protobuf.GeneratedMessageLite.GeneratedExtension<?, ?>) extensionLite);
        }
        if (!doFullRuntimeInheritanceCheck || !com.android.framework.protobuf.ExtensionRegistryFactory.isFullRegistry(this)) {
            return;
        }
        try {
            getClass().getMethod("add", com.android.framework.protobuf.ExtensionRegistryLite.ExtensionClassHolder.INSTANCE).invoke(this, extensionLite);
        } catch (java.lang.Exception e) {
            throw new java.lang.IllegalArgumentException(java.lang.String.format("Could not invoke ExtensionRegistry#add for %s", extensionLite), e);
        }
    }

    ExtensionRegistryLite() {
        this.extensionsByNumber = new java.util.HashMap();
    }

    ExtensionRegistryLite(com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) {
        if (extensionRegistryLite == EMPTY_REGISTRY_LITE) {
            this.extensionsByNumber = java.util.Collections.emptyMap();
        } else {
            this.extensionsByNumber = java.util.Collections.unmodifiableMap(extensionRegistryLite.extensionsByNumber);
        }
    }

    ExtensionRegistryLite(boolean z) {
        this.extensionsByNumber = java.util.Collections.emptyMap();
    }

    private static final class ObjectIntPair {
        private final int number;
        private final java.lang.Object object;

        ObjectIntPair(java.lang.Object obj, int i) {
            this.object = obj;
            this.number = i;
        }

        public int hashCode() {
            return (java.lang.System.identityHashCode(this.object) * 65535) + this.number;
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof com.android.framework.protobuf.ExtensionRegistryLite.ObjectIntPair)) {
                return false;
            }
            com.android.framework.protobuf.ExtensionRegistryLite.ObjectIntPair objectIntPair = (com.android.framework.protobuf.ExtensionRegistryLite.ObjectIntPair) obj;
            return this.object == objectIntPair.object && this.number == objectIntPair.number;
        }
    }
}
