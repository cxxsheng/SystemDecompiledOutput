package android.preference;

@java.lang.Deprecated
/* loaded from: classes3.dex */
abstract class GenericInflater<T, P extends android.preference.GenericInflater.Parent> {
    private static final java.lang.Class[] mConstructorSignature = {android.content.Context.class, android.util.AttributeSet.class};
    private static final java.util.HashMap sConstructorMap = new java.util.HashMap();
    private final boolean DEBUG;
    private final java.lang.Object[] mConstructorArgs;
    protected final android.content.Context mContext;
    private java.lang.String mDefaultPackage;
    private android.preference.GenericInflater.Factory<T> mFactory;
    private boolean mFactorySet;

    public interface Factory<T> {
        T onCreateItem(java.lang.String str, android.content.Context context, android.util.AttributeSet attributeSet);
    }

    public interface Parent<T> {
        void addItemFromInflater(T t);
    }

    public abstract android.preference.GenericInflater cloneInContext(android.content.Context context);

    private static class FactoryMerger<T> implements android.preference.GenericInflater.Factory<T> {
        private final android.preference.GenericInflater.Factory<T> mF1;
        private final android.preference.GenericInflater.Factory<T> mF2;

        FactoryMerger(android.preference.GenericInflater.Factory<T> factory, android.preference.GenericInflater.Factory<T> factory2) {
            this.mF1 = factory;
            this.mF2 = factory2;
        }

        @Override // android.preference.GenericInflater.Factory
        public T onCreateItem(java.lang.String str, android.content.Context context, android.util.AttributeSet attributeSet) {
            T onCreateItem = this.mF1.onCreateItem(str, context, attributeSet);
            return onCreateItem != null ? onCreateItem : this.mF2.onCreateItem(str, context, attributeSet);
        }
    }

    protected GenericInflater(android.content.Context context) {
        this.DEBUG = false;
        this.mConstructorArgs = new java.lang.Object[2];
        this.mContext = context;
    }

    protected GenericInflater(android.preference.GenericInflater<T, P> genericInflater, android.content.Context context) {
        this.DEBUG = false;
        this.mConstructorArgs = new java.lang.Object[2];
        this.mContext = context;
        this.mFactory = genericInflater.mFactory;
    }

    public void setDefaultPackage(java.lang.String str) {
        this.mDefaultPackage = str;
    }

    public java.lang.String getDefaultPackage() {
        return this.mDefaultPackage;
    }

    public android.content.Context getContext() {
        return this.mContext;
    }

    public final android.preference.GenericInflater.Factory<T> getFactory() {
        return this.mFactory;
    }

    public void setFactory(android.preference.GenericInflater.Factory<T> factory) {
        if (this.mFactorySet) {
            throw new java.lang.IllegalStateException("A factory has already been set on this inflater");
        }
        if (factory == null) {
            throw new java.lang.NullPointerException("Given factory can not be null");
        }
        this.mFactorySet = true;
        if (this.mFactory == null) {
            this.mFactory = factory;
        } else {
            this.mFactory = new android.preference.GenericInflater.FactoryMerger(factory, this.mFactory);
        }
    }

    public T inflate(int i, P p) {
        return inflate(i, (int) p, p != null);
    }

    public T inflate(org.xmlpull.v1.XmlPullParser xmlPullParser, P p) {
        return inflate(xmlPullParser, (org.xmlpull.v1.XmlPullParser) p, p != null);
    }

    public T inflate(int i, P p, boolean z) {
        android.content.res.XmlResourceParser xml = getContext().getResources().getXml(i);
        try {
            return inflate((org.xmlpull.v1.XmlPullParser) xml, (android.content.res.XmlResourceParser) p, z);
        } finally {
            xml.close();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public T inflate(org.xmlpull.v1.XmlPullParser xmlPullParser, P p, boolean z) {
        int next;
        T t;
        synchronized (this.mConstructorArgs) {
            android.util.AttributeSet asAttributeSet = android.util.Xml.asAttributeSet(xmlPullParser);
            this.mConstructorArgs[0] = this.mContext;
            do {
                try {
                    try {
                        try {
                            next = xmlPullParser.next();
                            if (next == 2) {
                                break;
                            }
                        } catch (android.view.InflateException e) {
                            throw e;
                        }
                    } catch (java.io.IOException e2) {
                        android.view.InflateException inflateException = new android.view.InflateException(xmlPullParser.getPositionDescription() + ": " + e2.getMessage());
                        inflateException.initCause(e2);
                        throw inflateException;
                    }
                } catch (org.xmlpull.v1.XmlPullParserException e3) {
                    android.view.InflateException inflateException2 = new android.view.InflateException(e3.getMessage());
                    inflateException2.initCause(e3);
                    throw inflateException2;
                }
            } while (next != 1);
            if (next != 2) {
                throw new android.view.InflateException(xmlPullParser.getPositionDescription() + ": No start tag found!");
            }
            t = (T) onMergeRoots(p, z, (android.preference.GenericInflater.Parent) createItemFromTag(xmlPullParser, xmlPullParser.getName(), asAttributeSet));
            rInflate(xmlPullParser, t, asAttributeSet);
        }
        return t;
    }

    public final T createItem(java.lang.String str, java.lang.String str2, android.util.AttributeSet attributeSet) throws java.lang.ClassNotFoundException, android.view.InflateException {
        java.lang.reflect.Constructor<?> constructor = (java.lang.reflect.Constructor) sConstructorMap.get(str);
        if (constructor == null) {
            try {
                constructor = this.mContext.getClassLoader().loadClass(str2 != null ? str2 + str : str).getConstructor(mConstructorSignature);
                constructor.setAccessible(true);
                sConstructorMap.put(str, constructor);
            } catch (java.lang.ClassNotFoundException e) {
                throw e;
            } catch (java.lang.NoSuchMethodException e2) {
                java.lang.StringBuilder append = new java.lang.StringBuilder().append(attributeSet.getPositionDescription()).append(": Error inflating class ");
                if (str2 != null) {
                    str = str2 + str;
                }
                android.view.InflateException inflateException = new android.view.InflateException(append.append(str).toString());
                inflateException.initCause(e2);
                throw inflateException;
            } catch (java.lang.Exception e3) {
                android.view.InflateException inflateException2 = new android.view.InflateException(attributeSet.getPositionDescription() + ": Error inflating class " + constructor.getDeclaringClass().getName());
                inflateException2.initCause(e3);
                throw inflateException2;
            }
        }
        java.lang.Object[] objArr = this.mConstructorArgs;
        objArr[1] = attributeSet;
        return (T) constructor.newInstance(objArr);
    }

    protected T onCreateItem(java.lang.String str, android.util.AttributeSet attributeSet) throws java.lang.ClassNotFoundException {
        return createItem(str, this.mDefaultPackage, attributeSet);
    }

    private final T createItemFromTag(org.xmlpull.v1.XmlPullParser xmlPullParser, java.lang.String str, android.util.AttributeSet attributeSet) {
        try {
            T onCreateItem = this.mFactory == null ? null : this.mFactory.onCreateItem(str, this.mContext, attributeSet);
            if (onCreateItem == null) {
                if (-1 == str.indexOf(46)) {
                    return onCreateItem(str, attributeSet);
                }
                return createItem(str, null, attributeSet);
            }
            return onCreateItem;
        } catch (android.view.InflateException e) {
            throw e;
        } catch (java.lang.ClassNotFoundException e2) {
            android.view.InflateException inflateException = new android.view.InflateException(attributeSet.getPositionDescription() + ": Error inflating class " + str);
            inflateException.initCause(e2);
            throw inflateException;
        } catch (java.lang.Exception e3) {
            android.view.InflateException inflateException2 = new android.view.InflateException(attributeSet.getPositionDescription() + ": Error inflating class " + str);
            inflateException2.initCause(e3);
            throw inflateException2;
        }
    }

    private void rInflate(org.xmlpull.v1.XmlPullParser xmlPullParser, T t, android.util.AttributeSet attributeSet) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            if ((next != 3 || xmlPullParser.getDepth() > depth) && next != 1) {
                if (next == 2 && !onCreateCustomFromTag(xmlPullParser, t, attributeSet)) {
                    T createItemFromTag = createItemFromTag(xmlPullParser, xmlPullParser.getName(), attributeSet);
                    ((android.preference.GenericInflater.Parent) t).addItemFromInflater(createItemFromTag);
                    rInflate(xmlPullParser, createItemFromTag, attributeSet);
                }
            } else {
                return;
            }
        }
    }

    protected boolean onCreateCustomFromTag(org.xmlpull.v1.XmlPullParser xmlPullParser, T t, android.util.AttributeSet attributeSet) throws org.xmlpull.v1.XmlPullParserException {
        return false;
    }

    protected P onMergeRoots(P p, boolean z, P p2) {
        return p2;
    }
}
