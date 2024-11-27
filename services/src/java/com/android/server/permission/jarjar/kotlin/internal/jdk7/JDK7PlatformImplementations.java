package com.android.server.permission.jarjar.kotlin.internal.jdk7;

/* compiled from: JDK7PlatformImplementations.kt */
/* loaded from: classes2.dex */
public class JDK7PlatformImplementations extends com.android.server.permission.jarjar.kotlin.internal.PlatformImplementations {

    /* compiled from: JDK7PlatformImplementations.kt */
    private static final class ReflectSdkVersion {

        @org.jetbrains.annotations.NotNull
        public static final com.android.server.permission.jarjar.kotlin.internal.jdk7.JDK7PlatformImplementations.ReflectSdkVersion INSTANCE = new com.android.server.permission.jarjar.kotlin.internal.jdk7.JDK7PlatformImplementations.ReflectSdkVersion();

        @org.jetbrains.annotations.Nullable
        public static final java.lang.Integer sdkVersion;

        private ReflectSdkVersion() {
        }

        static {
            java.lang.Integer num;
            java.lang.Integer num2 = null;
            try {
                java.lang.Object obj = java.lang.Class.forName("android.os.Build$VERSION").getField("SDK_INT").get(null);
                num = obj instanceof java.lang.Integer ? (java.lang.Integer) obj : null;
            } catch (java.lang.Throwable th) {
                num = null;
            }
            if (num != null) {
                int it = num.intValue();
                if (it > 0) {
                    num2 = num;
                }
            }
            sdkVersion = num2;
        }
    }

    private final boolean sdkIsNullOrAtLeast(int version) {
        return com.android.server.permission.jarjar.kotlin.internal.jdk7.JDK7PlatformImplementations.ReflectSdkVersion.sdkVersion == null || com.android.server.permission.jarjar.kotlin.internal.jdk7.JDK7PlatformImplementations.ReflectSdkVersion.sdkVersion.intValue() >= version;
    }

    @Override // com.android.server.permission.jarjar.kotlin.internal.PlatformImplementations
    public void addSuppressed(@org.jetbrains.annotations.NotNull java.lang.Throwable cause, @org.jetbrains.annotations.NotNull java.lang.Throwable exception) {
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(cause, "cause");
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(exception, "exception");
        if (sdkIsNullOrAtLeast(19)) {
            cause.addSuppressed(exception);
        } else {
            super.addSuppressed(cause, exception);
        }
    }
}
