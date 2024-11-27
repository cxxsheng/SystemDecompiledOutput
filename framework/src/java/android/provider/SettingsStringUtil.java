package android.provider;

/* loaded from: classes3.dex */
public class SettingsStringUtil {
    public static final java.lang.String DELIMITER = ":";

    private SettingsStringUtil() {
    }

    public static abstract class ColonDelimitedSet<T> extends java.util.HashSet<T> {
        protected abstract T itemFromString(java.lang.String str);

        public ColonDelimitedSet(java.lang.String str) {
            for (java.lang.String str2 : android.text.TextUtils.split(android.text.TextUtils.emptyIfNull(str), ":")) {
                add(itemFromString(str2));
            }
        }

        protected java.lang.String itemToString(T t) {
            return java.lang.String.valueOf(t);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.AbstractCollection
        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            java.util.Iterator it = iterator();
            if (it.hasNext()) {
                sb.append(itemToString(it.next()));
                while (it.hasNext()) {
                    sb.append(":");
                    sb.append(itemToString(it.next()));
                }
            }
            return sb.toString();
        }

        public static class OfStrings extends android.provider.SettingsStringUtil.ColonDelimitedSet<java.lang.String> {
            public OfStrings(java.lang.String str) {
                super(str);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.provider.SettingsStringUtil.ColonDelimitedSet
            public java.lang.String itemFromString(java.lang.String str) {
                return str;
            }

            public static java.lang.String addAll(java.lang.String str, java.util.Collection<java.lang.String> collection) {
                android.provider.SettingsStringUtil.ColonDelimitedSet.OfStrings ofStrings = new android.provider.SettingsStringUtil.ColonDelimitedSet.OfStrings(str);
                return ofStrings.addAll(collection) ? ofStrings.toString() : str;
            }

            public static java.lang.String add(java.lang.String str, java.lang.String str2) {
                android.provider.SettingsStringUtil.ColonDelimitedSet.OfStrings ofStrings = new android.provider.SettingsStringUtil.ColonDelimitedSet.OfStrings(str);
                if (ofStrings.contains(str2)) {
                    return str;
                }
                ofStrings.add(str2);
                return ofStrings.toString();
            }

            public static java.lang.String remove(java.lang.String str, java.lang.String str2) {
                android.provider.SettingsStringUtil.ColonDelimitedSet.OfStrings ofStrings = new android.provider.SettingsStringUtil.ColonDelimitedSet.OfStrings(str);
                if (!ofStrings.contains(str2)) {
                    return str;
                }
                ofStrings.remove(str2);
                return ofStrings.toString();
            }

            public static boolean contains(java.lang.String str, java.lang.String str2) {
                return com.android.internal.util.ArrayUtils.indexOf(android.text.TextUtils.split(str, ":"), str2) != -1;
            }
        }
    }

    public static class ComponentNameSet extends android.provider.SettingsStringUtil.ColonDelimitedSet<android.content.ComponentName> {
        public ComponentNameSet(java.lang.String str) {
            super(str);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.provider.SettingsStringUtil.ColonDelimitedSet
        public android.content.ComponentName itemFromString(java.lang.String str) {
            return android.content.ComponentName.unflattenFromString(str);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.provider.SettingsStringUtil.ColonDelimitedSet
        public java.lang.String itemToString(android.content.ComponentName componentName) {
            return componentName != null ? componentName.flattenToString() : "null";
        }

        public static java.lang.String add(java.lang.String str, android.content.ComponentName componentName) {
            android.provider.SettingsStringUtil.ComponentNameSet componentNameSet = new android.provider.SettingsStringUtil.ComponentNameSet(str);
            if (componentNameSet.contains(componentName)) {
                return str;
            }
            componentNameSet.add(componentName);
            return componentNameSet.toString();
        }

        public static java.lang.String remove(java.lang.String str, android.content.ComponentName componentName) {
            android.provider.SettingsStringUtil.ComponentNameSet componentNameSet = new android.provider.SettingsStringUtil.ComponentNameSet(str);
            if (!componentNameSet.contains(componentName)) {
                return str;
            }
            componentNameSet.remove(componentName);
            return componentNameSet.toString();
        }

        public static boolean contains(java.lang.String str, android.content.ComponentName componentName) {
            return android.provider.SettingsStringUtil.ColonDelimitedSet.OfStrings.contains(str, componentName.flattenToString());
        }
    }

    public static class SettingStringHelper {
        private final android.content.ContentResolver mContentResolver;
        private final java.lang.String mSettingName;
        private final int mUserId;

        public SettingStringHelper(android.content.ContentResolver contentResolver, java.lang.String str, int i) {
            this.mContentResolver = contentResolver;
            this.mUserId = i;
            this.mSettingName = str;
        }

        public java.lang.String read() {
            return android.provider.Settings.Secure.getStringForUser(this.mContentResolver, this.mSettingName, this.mUserId);
        }

        public boolean write(java.lang.String str) {
            return android.provider.Settings.Secure.putStringForUser(this.mContentResolver, this.mSettingName, str, this.mUserId);
        }

        public boolean modify(java.util.function.Function<java.lang.String, java.lang.String> function) {
            return write(function.apply(read()));
        }
    }
}
