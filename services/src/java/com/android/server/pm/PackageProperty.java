package com.android.server.pm;

/* loaded from: classes2.dex */
public class PackageProperty {
    private android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.util.ArrayList<android.content.pm.PackageManager.Property>>> mActivityProperties;
    private android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.util.ArrayList<android.content.pm.PackageManager.Property>>> mApplicationProperties;
    private android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.util.ArrayList<android.content.pm.PackageManager.Property>>> mProviderProperties;
    private android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.util.ArrayList<android.content.pm.PackageManager.Property>>> mReceiverProperties;
    private android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.util.ArrayList<android.content.pm.PackageManager.Property>>> mServiceProperties;

    public android.content.pm.PackageManager.Property getProperty(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.Nullable java.lang.String str3) {
        if (str3 == null) {
            return getApplicationProperty(str, str2);
        }
        return getComponentProperty(str, str2, str3);
    }

    public java.util.List<android.content.pm.PackageManager.Property> queryProperty(@android.annotation.NonNull java.lang.String str, int i, java.util.function.Predicate<java.lang.String> predicate) {
        android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.util.ArrayList<android.content.pm.PackageManager.Property>>> arrayMap;
        android.util.ArrayMap<java.lang.String, java.util.ArrayList<android.content.pm.PackageManager.Property>> arrayMap2;
        if (i == 5) {
            arrayMap = this.mApplicationProperties;
        } else if (i == 1) {
            arrayMap = this.mActivityProperties;
        } else if (i == 4) {
            arrayMap = this.mProviderProperties;
        } else if (i == 2) {
            arrayMap = this.mReceiverProperties;
        } else if (i == 3) {
            arrayMap = this.mServiceProperties;
        } else {
            arrayMap = null;
        }
        if (arrayMap == null || (arrayMap2 = arrayMap.get(str)) == null) {
            return null;
        }
        android.os.Binder.getCallingUid();
        android.os.UserHandle.getCallingUserId();
        int size = arrayMap2.size();
        java.util.ArrayList arrayList = new java.util.ArrayList(size);
        for (int i2 = 0; i2 < size; i2++) {
            if (!predicate.test(arrayMap2.keyAt(i2))) {
                arrayList.addAll(arrayMap2.valueAt(i2));
            }
        }
        return arrayList;
    }

    void addAllProperties(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        this.mApplicationProperties = addProperties(androidPackage.getProperties(), this.mApplicationProperties);
        this.mActivityProperties = addComponentProperties(androidPackage.getActivities(), this.mActivityProperties);
        this.mProviderProperties = addComponentProperties(androidPackage.getProviders(), this.mProviderProperties);
        this.mReceiverProperties = addComponentProperties(androidPackage.getReceivers(), this.mReceiverProperties);
        this.mServiceProperties = addComponentProperties(androidPackage.getServices(), this.mServiceProperties);
    }

    void removeAllProperties(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        this.mApplicationProperties = removeProperties(androidPackage.getProperties(), this.mApplicationProperties);
        this.mActivityProperties = removeComponentProperties(androidPackage.getActivities(), this.mActivityProperties);
        this.mProviderProperties = removeComponentProperties(androidPackage.getProviders(), this.mProviderProperties);
        this.mReceiverProperties = removeComponentProperties(androidPackage.getReceivers(), this.mReceiverProperties);
        this.mServiceProperties = removeComponentProperties(androidPackage.getServices(), this.mServiceProperties);
    }

    private static <T extends com.android.internal.pm.pkg.component.ParsedComponent> android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.util.ArrayList<android.content.pm.PackageManager.Property>>> addComponentProperties(@android.annotation.NonNull java.util.List<T> list, @android.annotation.Nullable android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.util.ArrayList<android.content.pm.PackageManager.Property>>> arrayMap) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            java.util.Map properties = list.get(i).getProperties();
            if (properties.size() != 0) {
                arrayMap = addProperties(properties, arrayMap);
            }
        }
        return arrayMap;
    }

    private static android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.util.ArrayList<android.content.pm.PackageManager.Property>>> addProperties(@android.annotation.NonNull java.util.Map<java.lang.String, android.content.pm.PackageManager.Property> map, @android.annotation.Nullable android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.util.ArrayList<android.content.pm.PackageManager.Property>>> arrayMap) {
        if (map.size() == 0) {
            return arrayMap;
        }
        if (arrayMap == null) {
            arrayMap = new android.util.ArrayMap<>(10);
        }
        for (android.content.pm.PackageManager.Property property : map.values()) {
            java.lang.String name = property.getName();
            java.lang.String packageName = property.getPackageName();
            android.util.ArrayMap<java.lang.String, java.util.ArrayList<android.content.pm.PackageManager.Property>> arrayMap2 = arrayMap.get(name);
            if (arrayMap2 == null) {
                arrayMap2 = new android.util.ArrayMap<>();
                arrayMap.put(name, arrayMap2);
            }
            java.util.ArrayList<android.content.pm.PackageManager.Property> arrayList = arrayMap2.get(packageName);
            if (arrayList == null) {
                arrayList = new java.util.ArrayList<>(map.size());
                arrayMap2.put(packageName, arrayList);
            }
            arrayList.add(property);
        }
        return arrayMap;
    }

    private static <T extends com.android.internal.pm.pkg.component.ParsedComponent> android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.util.ArrayList<android.content.pm.PackageManager.Property>>> removeComponentProperties(@android.annotation.NonNull java.util.List<T> list, @android.annotation.Nullable android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.util.ArrayList<android.content.pm.PackageManager.Property>>> arrayMap) {
        int size = list.size();
        for (int i = 0; arrayMap != null && i < size; i++) {
            java.util.Map properties = list.get(i).getProperties();
            if (properties.size() != 0) {
                arrayMap = removeProperties(properties, arrayMap);
            }
        }
        return arrayMap;
    }

    private static android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.util.ArrayList<android.content.pm.PackageManager.Property>>> removeProperties(@android.annotation.NonNull java.util.Map<java.lang.String, android.content.pm.PackageManager.Property> map, @android.annotation.Nullable android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.util.ArrayList<android.content.pm.PackageManager.Property>>> arrayMap) {
        java.util.ArrayList<android.content.pm.PackageManager.Property> arrayList;
        if (arrayMap == null) {
            return null;
        }
        for (android.content.pm.PackageManager.Property property : map.values()) {
            java.lang.String name = property.getName();
            java.lang.String packageName = property.getPackageName();
            android.util.ArrayMap<java.lang.String, java.util.ArrayList<android.content.pm.PackageManager.Property>> arrayMap2 = arrayMap.get(name);
            if (arrayMap2 != null && (arrayList = arrayMap2.get(packageName)) != null) {
                arrayList.remove(property);
                if (arrayList.size() == 0) {
                    arrayMap2.remove(packageName);
                }
                if (arrayMap2.size() == 0) {
                    arrayMap.remove(name);
                }
            }
        }
        if (arrayMap.size() == 0) {
            return null;
        }
        return arrayMap;
    }

    private static android.content.pm.PackageManager.Property getProperty(java.lang.String str, java.lang.String str2, java.lang.String str3, android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.util.ArrayList<android.content.pm.PackageManager.Property>>> arrayMap) {
        java.util.ArrayList<android.content.pm.PackageManager.Property> arrayList;
        android.util.ArrayMap<java.lang.String, java.util.ArrayList<android.content.pm.PackageManager.Property>> arrayMap2 = arrayMap.get(str);
        if (arrayMap2 == null || (arrayList = arrayMap2.get(str2)) == null) {
            return null;
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            android.content.pm.PackageManager.Property property = arrayList.get(size);
            if (java.util.Objects.equals(str3, property.getClassName())) {
                return property;
            }
        }
        return null;
    }

    private android.content.pm.PackageManager.Property getComponentProperty(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        android.content.pm.PackageManager.Property property;
        if (this.mActivityProperties != null) {
            property = getProperty(str, str2, str3, this.mActivityProperties);
        } else {
            property = null;
        }
        if (property == null && this.mProviderProperties != null) {
            property = getProperty(str, str2, str3, this.mProviderProperties);
        }
        if (property == null && this.mReceiverProperties != null) {
            property = getProperty(str, str2, str3, this.mReceiverProperties);
        }
        if (property == null && this.mServiceProperties != null) {
            return getProperty(str, str2, str3, this.mServiceProperties);
        }
        return property;
    }

    private android.content.pm.PackageManager.Property getApplicationProperty(java.lang.String str, java.lang.String str2) {
        java.util.ArrayList<android.content.pm.PackageManager.Property> arrayList;
        android.util.ArrayMap<java.lang.String, java.util.ArrayList<android.content.pm.PackageManager.Property>> arrayMap = this.mApplicationProperties != null ? this.mApplicationProperties.get(str) : null;
        if (arrayMap == null || (arrayList = arrayMap.get(str2)) == null) {
            return null;
        }
        return (android.content.pm.PackageManager.Property) arrayList.get(0);
    }
}
