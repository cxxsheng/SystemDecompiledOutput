package android.content.type;

/* loaded from: classes.dex */
public class DefaultMimeMapFactory {
    private DefaultMimeMapFactory() {
    }

    public static libcore.content.type.MimeMap create() {
        final java.lang.Class<android.content.type.DefaultMimeMapFactory> cls = android.content.type.DefaultMimeMapFactory.class;
        return create(new java.util.function.Function() { // from class: android.content.type.DefaultMimeMapFactory$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.io.InputStream resourceAsStream;
                resourceAsStream = cls.getResourceAsStream("/res/" + ((java.lang.String) obj));
                return resourceAsStream;
            }
        });
    }

    public static libcore.content.type.MimeMap create(java.util.function.Function<java.lang.String, java.io.InputStream> function) {
        libcore.content.type.MimeMap.Builder builder = libcore.content.type.MimeMap.builder();
        parseTypes(builder, function, "debian.mime.types");
        parseTypes(builder, function, "android.mime.types");
        parseTypes(builder, function, "vendor.mime.types");
        return builder.build();
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0074, code lost:
    
        throw new java.lang.IllegalArgumentException("Malformed line: " + r2);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void parseTypes(libcore.content.type.MimeMap.Builder builder, java.util.function.Function<java.lang.String, java.io.InputStream> function, java.lang.String str) {
        try {
            java.io.InputStream inputStream = (java.io.InputStream) java.util.Objects.requireNonNull(function.apply(str));
            try {
                java.io.BufferedReader bufferedReader = new java.io.BufferedReader(new java.io.InputStreamReader(inputStream));
                try {
                    java.util.ArrayList arrayList = new java.util.ArrayList(10);
                    loop0: while (true) {
                        java.lang.String readLine = bufferedReader.readLine();
                        if (readLine != null) {
                            arrayList.clear();
                            int i = 0;
                            do {
                                int indexOf = readLine.indexOf(32, i);
                                if (indexOf < 0) {
                                    indexOf = readLine.length();
                                }
                                java.lang.String substring = readLine.substring(i, indexOf);
                                if (substring.isEmpty()) {
                                    break loop0;
                                }
                                arrayList.add(substring);
                                i = indexOf + 1;
                            } while (i < readLine.length());
                            builder.addMimeMapping((java.lang.String) arrayList.get(0), arrayList.subList(1, arrayList.size()));
                        } else {
                            bufferedReader.close();
                            if (inputStream != null) {
                                inputStream.close();
                                return;
                            }
                            return;
                        }
                    }
                } finally {
                }
            } catch (java.lang.Throwable th) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (java.io.IOException | java.lang.RuntimeException e) {
            throw new java.lang.RuntimeException("Failed to parse " + str, e);
        }
    }
}
