package com.android.server.soundtrigger_middleware;

/* loaded from: classes2.dex */
class ObjectPrinter {
    public static final int kDefaultMaxCollectionLength = 16;

    ObjectPrinter() {
    }

    static java.lang.String print(@android.annotation.Nullable java.lang.Object obj, int i) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        print(sb, obj, i);
        return sb.toString();
    }

    static void print(@android.annotation.NonNull java.lang.StringBuilder sb, @android.annotation.Nullable java.lang.Object obj) {
        print(sb, obj, 16);
    }

    static void print(@android.annotation.NonNull java.lang.StringBuilder sb, @android.annotation.Nullable java.lang.Object obj, int i) {
        try {
            if (obj == null) {
                sb.append("null");
                return;
            }
            if (obj instanceof java.lang.Boolean) {
                sb.append(obj);
                return;
            }
            if (obj instanceof java.lang.Number) {
                sb.append(obj);
                return;
            }
            if (obj instanceof java.lang.Character) {
                sb.append('\'');
                sb.append(obj);
                sb.append('\'');
                return;
            }
            if (obj instanceof java.lang.String) {
                sb.append('\"');
                sb.append(obj.toString());
                sb.append('\"');
                return;
            }
            java.lang.Class<?> cls = obj.getClass();
            boolean z = true;
            if (java.util.Collection.class.isAssignableFrom(cls)) {
                java.util.Collection collection = (java.util.Collection) obj;
                sb.append("[ ");
                int size = collection.size();
                java.util.Iterator it = collection.iterator();
                int i2 = 0;
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    }
                    java.lang.Object next = it.next();
                    if (i2 > 0) {
                        sb.append(", ");
                    }
                    if (i2 >= i) {
                        break;
                    }
                    print(sb, next, i);
                    i2++;
                }
                if (z) {
                    sb.append("... (+");
                    sb.append(size - i);
                    sb.append(" entries)");
                }
                sb.append(" ]");
                return;
            }
            if (java.util.Map.class.isAssignableFrom(cls)) {
                java.util.Map map = (java.util.Map) obj;
                sb.append("< ");
                int size2 = map.size();
                java.util.Iterator it2 = map.entrySet().iterator();
                int i3 = 0;
                while (true) {
                    if (!it2.hasNext()) {
                        z = false;
                        break;
                    }
                    java.util.Map.Entry entry = (java.util.Map.Entry) it2.next();
                    if (i3 > 0) {
                        sb.append(", ");
                    }
                    if (i3 >= i) {
                        break;
                    }
                    print(sb, entry.getKey(), i);
                    sb.append(": ");
                    print(sb, entry.getValue(), i);
                    i3++;
                }
                if (z) {
                    sb.append("... (+");
                    sb.append(size2 - i);
                    sb.append(" entries)");
                }
                sb.append(" >");
                return;
            }
            if (cls.isArray()) {
                sb.append("[ ");
                int length = java.lang.reflect.Array.getLength(obj);
                int i4 = 0;
                while (true) {
                    if (i4 >= length) {
                        z = false;
                        break;
                    }
                    if (i4 > 0) {
                        sb.append(", ");
                    }
                    if (i4 >= i) {
                        break;
                    }
                    print(sb, java.lang.reflect.Array.get(obj, i4), i);
                    i4++;
                }
                if (z) {
                    sb.append("... (+");
                    sb.append(length - i);
                    sb.append(" entries)");
                }
                sb.append(" ]");
                return;
            }
            sb.append(obj);
        } catch (java.lang.Exception e) {
            throw new java.lang.RuntimeException(e);
        }
    }
}
