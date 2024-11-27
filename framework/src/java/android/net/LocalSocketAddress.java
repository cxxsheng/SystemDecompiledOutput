package android.net;

/* loaded from: classes2.dex */
public class LocalSocketAddress {
    private final java.lang.String name;
    private final android.net.LocalSocketAddress.Namespace namespace;

    public enum Namespace {
        ABSTRACT(0),
        RESERVED(1),
        FILESYSTEM(2);

        private int id;

        Namespace(int i) {
            this.id = i;
        }

        int getId() {
            return this.id;
        }
    }

    public LocalSocketAddress(java.lang.String str, android.net.LocalSocketAddress.Namespace namespace) {
        this.name = str;
        this.namespace = namespace;
    }

    public LocalSocketAddress(java.lang.String str) {
        this(str, android.net.LocalSocketAddress.Namespace.ABSTRACT);
    }

    public java.lang.String getName() {
        return this.name;
    }

    public android.net.LocalSocketAddress.Namespace getNamespace() {
        return this.namespace;
    }
}
