package android.annotation;

@java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE})
@java.lang.annotation.Repeatable(android.annotation.RestrictedFor.Container.class)
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
/* loaded from: classes.dex */
public @interface RestrictedFor {

    @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
    public @interface Container {
        android.annotation.RestrictedFor[] value();
    }

    android.annotation.RestrictedFor.Environment[] environments();

    int from();

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public static class Environment {
        public static final android.annotation.RestrictedFor.Environment SDK_SANDBOX = new android.annotation.RestrictedFor.Environment.AnonymousClass1("SDK_SANDBOX", 0);
        private static final /* synthetic */ android.annotation.RestrictedFor.Environment[] $VALUES = $values();

        private static /* synthetic */ android.annotation.RestrictedFor.Environment[] $values() {
            return new android.annotation.RestrictedFor.Environment[]{SDK_SANDBOX};
        }

        private Environment(java.lang.String str, int i) {
        }

        public static android.annotation.RestrictedFor.Environment valueOf(java.lang.String str) {
            return (android.annotation.RestrictedFor.Environment) java.lang.Enum.valueOf(android.annotation.RestrictedFor.Environment.class, str);
        }

        public static android.annotation.RestrictedFor.Environment[] values() {
            return (android.annotation.RestrictedFor.Environment[]) $VALUES.clone();
        }

        /* renamed from: android.annotation.RestrictedFor$Environment$1, reason: invalid class name */
        enum AnonymousClass1 extends android.annotation.RestrictedFor.Environment {
            private AnonymousClass1(java.lang.String str, int i) {
                super(str, i);
            }

            @Override // java.lang.Enum
            public java.lang.String toString() {
                return "SDK Runtime";
            }
        }
    }
}
