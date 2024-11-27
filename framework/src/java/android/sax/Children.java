package android.sax;

/* loaded from: classes3.dex */
class Children {
    android.sax.Children.Child[] children = new android.sax.Children.Child[16];

    Children() {
    }

    android.sax.Element getOrCreate(android.sax.Element element, java.lang.String str, java.lang.String str2) {
        int hashCode = (str.hashCode() * 31) + str2.hashCode();
        int i = hashCode & 15;
        android.sax.Children.Child child = this.children[i];
        if (child == null) {
            android.sax.Children.Child child2 = new android.sax.Children.Child(element, str, str2, element.depth + 1, hashCode);
            this.children[i] = child2;
            return child2;
        }
        while (true) {
            if (child.hash == hashCode && child.uri.compareTo(str) == 0 && child.localName.compareTo(str2) == 0) {
                return child;
            }
            android.sax.Children.Child child3 = child.next;
            if (child3 != null) {
                child = child3;
            } else {
                android.sax.Children.Child child4 = new android.sax.Children.Child(element, str, str2, element.depth + 1, hashCode);
                child.next = child4;
                return child4;
            }
        }
    }

    android.sax.Element get(java.lang.String str, java.lang.String str2) {
        int hashCode = (str.hashCode() * 31) + str2.hashCode();
        android.sax.Children.Child child = this.children[hashCode & 15];
        if (child == null) {
            return null;
        }
        do {
            if (child.hash == hashCode && child.uri.compareTo(str) == 0 && child.localName.compareTo(str2) == 0) {
                return child;
            }
            child = child.next;
        } while (child != null);
        return null;
    }

    static class Child extends android.sax.Element {
        final int hash;
        android.sax.Children.Child next;

        Child(android.sax.Element element, java.lang.String str, java.lang.String str2, int i, int i2) {
            super(element, str, str2, i);
            this.hash = i2;
        }
    }
}
