package android.view;

/* loaded from: classes4.dex */
public abstract class LayoutInflater {
    private static final java.lang.String ATTR_LAYOUT = "layout";
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG_1995 = "blink";
    private static final java.lang.String TAG_INCLUDE = "include";
    private static final java.lang.String TAG_MERGE = "merge";
    private static final java.lang.String TAG_REQUEST_FOCUS = "requestFocus";
    private static final java.lang.String TAG_TAG = "tag";
    final java.lang.Object[] mConstructorArgs;
    protected final android.content.Context mContext;
    private android.view.LayoutInflater.Factory mFactory;
    private android.view.LayoutInflater.Factory2 mFactory2;
    private boolean mFactorySet;
    private android.view.LayoutInflater.Filter mFilter;
    private java.util.HashMap<java.lang.String, java.lang.Boolean> mFilterMap;
    private android.view.LayoutInflater.Factory2 mPrivateFactory;
    private android.util.TypedValue mTempValue;
    private static final java.lang.String TAG = android.view.LayoutInflater.class.getSimpleName();
    private static final java.lang.StackTraceElement[] EMPTY_STACK_TRACE = new java.lang.StackTraceElement[0];
    static final java.lang.Class<?>[] mConstructorSignature = {android.content.Context.class, android.util.AttributeSet.class};
    private static final java.util.HashMap<java.lang.String, java.lang.reflect.Constructor<? extends android.view.View>> sConstructorMap = new java.util.HashMap<>();
    private static final int[] ATTRS_THEME = {16842752};
    private static final java.lang.ClassLoader BOOT_CLASS_LOADER = android.view.LayoutInflater.class.getClassLoader();

    public interface Factory {
        android.view.View onCreateView(java.lang.String str, android.content.Context context, android.util.AttributeSet attributeSet);
    }

    public interface Factory2 extends android.view.LayoutInflater.Factory {
        android.view.View onCreateView(android.view.View view, java.lang.String str, android.content.Context context, android.util.AttributeSet attributeSet);
    }

    public interface Filter {
        boolean onLoadClass(java.lang.Class cls);
    }

    public abstract android.view.LayoutInflater cloneInContext(android.content.Context context);

    private static class FactoryMerger implements android.view.LayoutInflater.Factory2 {
        private final android.view.LayoutInflater.Factory mF1;
        private final android.view.LayoutInflater.Factory2 mF12;
        private final android.view.LayoutInflater.Factory mF2;
        private final android.view.LayoutInflater.Factory2 mF22;

        FactoryMerger(android.view.LayoutInflater.Factory factory, android.view.LayoutInflater.Factory2 factory2, android.view.LayoutInflater.Factory factory3, android.view.LayoutInflater.Factory2 factory22) {
            this.mF1 = factory;
            this.mF2 = factory3;
            this.mF12 = factory2;
            this.mF22 = factory22;
        }

        @Override // android.view.LayoutInflater.Factory
        public android.view.View onCreateView(java.lang.String str, android.content.Context context, android.util.AttributeSet attributeSet) {
            android.view.View onCreateView = this.mF1.onCreateView(str, context, attributeSet);
            return onCreateView != null ? onCreateView : this.mF2.onCreateView(str, context, attributeSet);
        }

        @Override // android.view.LayoutInflater.Factory2
        public android.view.View onCreateView(android.view.View view, java.lang.String str, android.content.Context context, android.util.AttributeSet attributeSet) {
            android.view.View onCreateView = this.mF12 != null ? this.mF12.onCreateView(view, str, context, attributeSet) : this.mF1.onCreateView(str, context, attributeSet);
            return onCreateView != null ? onCreateView : this.mF22 != null ? this.mF22.onCreateView(view, str, context, attributeSet) : this.mF2.onCreateView(str, context, attributeSet);
        }
    }

    protected LayoutInflater(android.content.Context context) {
        this.mConstructorArgs = new java.lang.Object[2];
        android.os.StrictMode.assertConfigurationContext(context, "LayoutInflater");
        this.mContext = context;
    }

    protected LayoutInflater(android.view.LayoutInflater layoutInflater, android.content.Context context) {
        this.mConstructorArgs = new java.lang.Object[2];
        android.os.StrictMode.assertConfigurationContext(context, "LayoutInflater");
        this.mContext = context;
        this.mFactory = layoutInflater.mFactory;
        this.mFactory2 = layoutInflater.mFactory2;
        this.mPrivateFactory = layoutInflater.mPrivateFactory;
        setFilter(layoutInflater.mFilter);
    }

    public static android.view.LayoutInflater from(android.content.Context context) {
        android.view.LayoutInflater layoutInflater = (android.view.LayoutInflater) context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
        if (layoutInflater == null) {
            throw new java.lang.AssertionError("LayoutInflater not found.");
        }
        return layoutInflater;
    }

    public android.content.Context getContext() {
        return this.mContext;
    }

    public final android.view.LayoutInflater.Factory getFactory() {
        return this.mFactory;
    }

    public final android.view.LayoutInflater.Factory2 getFactory2() {
        return this.mFactory2;
    }

    public void setFactory(android.view.LayoutInflater.Factory factory) {
        if (this.mFactorySet) {
            throw new java.lang.IllegalStateException("A factory has already been set on this LayoutInflater");
        }
        if (factory == null) {
            throw new java.lang.NullPointerException("Given factory can not be null");
        }
        this.mFactorySet = true;
        if (this.mFactory == null) {
            this.mFactory = factory;
        } else {
            this.mFactory = new android.view.LayoutInflater.FactoryMerger(factory, null, this.mFactory, this.mFactory2);
        }
    }

    public void setFactory2(android.view.LayoutInflater.Factory2 factory2) {
        if (this.mFactorySet) {
            throw new java.lang.IllegalStateException("A factory has already been set on this LayoutInflater");
        }
        if (factory2 == null) {
            throw new java.lang.NullPointerException("Given factory can not be null");
        }
        this.mFactorySet = true;
        if (this.mFactory == null) {
            this.mFactory2 = factory2;
            this.mFactory = factory2;
        } else {
            android.view.LayoutInflater.FactoryMerger factoryMerger = new android.view.LayoutInflater.FactoryMerger(factory2, factory2, this.mFactory, this.mFactory2);
            this.mFactory2 = factoryMerger;
            this.mFactory = factoryMerger;
        }
    }

    public void setPrivateFactory(android.view.LayoutInflater.Factory2 factory2) {
        if (this.mPrivateFactory == null) {
            this.mPrivateFactory = factory2;
        } else {
            this.mPrivateFactory = new android.view.LayoutInflater.FactoryMerger(factory2, factory2, this.mPrivateFactory, this.mPrivateFactory);
        }
    }

    public android.view.LayoutInflater.Filter getFilter() {
        return this.mFilter;
    }

    public void setFilter(android.view.LayoutInflater.Filter filter) {
        this.mFilter = filter;
        if (filter != null) {
            this.mFilterMap = new java.util.HashMap<>();
        }
    }

    public android.view.View inflate(int i, android.view.ViewGroup viewGroup) {
        return inflate(i, viewGroup, viewGroup != null);
    }

    public android.view.View inflate(org.xmlpull.v1.XmlPullParser xmlPullParser, android.view.ViewGroup viewGroup) {
        return inflate(xmlPullParser, viewGroup, viewGroup != null);
    }

    public android.view.View inflate(int i, android.view.ViewGroup viewGroup, boolean z) {
        android.content.res.XmlResourceParser layout = getContext().getResources().getLayout(i);
        try {
            return inflate(layout, viewGroup, z);
        } finally {
            layout.close();
        }
    }

    private void advanceToRootNode(org.xmlpull.v1.XmlPullParser xmlPullParser) throws android.view.InflateException, java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        int next;
        do {
            next = xmlPullParser.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next != 2) {
            throw new android.view.InflateException(xmlPullParser.getPositionDescription() + ": No start tag found!");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r9v0 */
    /* JADX WARN: Type inference failed for: r9v1 */
    /* JADX WARN: Type inference failed for: r9v4 */
    public android.view.View inflate(org.xmlpull.v1.XmlPullParser xmlPullParser, android.view.ViewGroup viewGroup, boolean z) {
        java.lang.String name;
        char c;
        android.view.ViewGroup.LayoutParams layoutParams;
        android.view.ViewGroup viewGroup2 = viewGroup;
        synchronized (this.mConstructorArgs) {
            ?? r9 = 8;
            android.os.Trace.traceBegin(8L, "inflate");
            android.content.Context context = this.mContext;
            android.util.AttributeSet asAttributeSet = android.util.Xml.asAttributeSet(xmlPullParser);
            android.content.Context context2 = (android.content.Context) this.mConstructorArgs[0];
            this.mConstructorArgs[0] = context;
            if (viewGroup2 != null && viewGroup.getViewRootImpl() != null) {
                viewGroup.getViewRootImpl().notifyRendererOfExpensiveFrame();
            }
            try {
                try {
                    advanceToRootNode(xmlPullParser);
                    name = xmlPullParser.getName();
                } catch (org.xmlpull.v1.XmlPullParserException e) {
                    e = e;
                } catch (java.lang.Exception e2) {
                    e = e2;
                } catch (java.lang.Throwable th) {
                    th = th;
                    r9 = 1;
                    this.mConstructorArgs[0] = context2;
                    this.mConstructorArgs[r9] = null;
                    android.os.Trace.traceEnd(8L);
                    throw th;
                }
                try {
                    if (TAG_MERGE.equals(name)) {
                        if (viewGroup2 == null || !z) {
                            throw new android.view.InflateException("<merge /> can be used only with a valid ViewGroup root and attachToRoot=true");
                        }
                        c = 1;
                        rInflate(xmlPullParser, viewGroup, context, asAttributeSet, false);
                    } else {
                        c = 1;
                        android.view.View createViewFromTag = createViewFromTag(viewGroup2, name, context, asAttributeSet);
                        if (viewGroup2 == null && createViewFromTag != null && createViewFromTag.getViewRootImpl() != null) {
                            createViewFromTag.getViewRootImpl().notifyRendererOfExpensiveFrame();
                        }
                        if (viewGroup2 == null) {
                            layoutParams = null;
                        } else {
                            layoutParams = viewGroup2.generateLayoutParams(asAttributeSet);
                            if (!z) {
                                createViewFromTag.setLayoutParams(layoutParams);
                            }
                        }
                        rInflateChildren(xmlPullParser, createViewFromTag, asAttributeSet, true);
                        if (viewGroup2 != null && z) {
                            viewGroup2.addView(createViewFromTag, layoutParams);
                        }
                        if (viewGroup2 == null || !z) {
                            viewGroup2 = createViewFromTag;
                        }
                    }
                    this.mConstructorArgs[0] = context2;
                    this.mConstructorArgs[c] = null;
                    android.os.Trace.traceEnd(8L);
                } catch (org.xmlpull.v1.XmlPullParserException e3) {
                    e = e3;
                    android.view.InflateException inflateException = new android.view.InflateException(e.getMessage(), e);
                    inflateException.setStackTrace(EMPTY_STACK_TRACE);
                    throw inflateException;
                } catch (java.lang.Exception e4) {
                    e = e4;
                    android.view.InflateException inflateException2 = new android.view.InflateException(getParserStateDescription(context, asAttributeSet) + ": " + e.getMessage(), e);
                    inflateException2.setStackTrace(EMPTY_STACK_TRACE);
                    throw inflateException2;
                }
            } catch (java.lang.Throwable th2) {
                th = th2;
                this.mConstructorArgs[0] = context2;
                this.mConstructorArgs[r9] = null;
                android.os.Trace.traceEnd(8L);
                throw th;
            }
        }
        return viewGroup2;
    }

    private static java.lang.String getParserStateDescription(android.content.Context context, android.util.AttributeSet attributeSet) {
        int attributeSetSourceResId = android.content.res.Resources.getAttributeSetSourceResId(attributeSet);
        if (attributeSetSourceResId == 0) {
            return attributeSet.getPositionDescription();
        }
        return attributeSet.getPositionDescription() + " in " + context.getResources().getResourceName(attributeSetSourceResId);
    }

    private final boolean verifyClassLoader(java.lang.reflect.Constructor<? extends android.view.View> constructor) {
        java.lang.ClassLoader classLoader = constructor.getDeclaringClass().getClassLoader();
        if (classLoader == BOOT_CLASS_LOADER) {
            return true;
        }
        java.lang.ClassLoader classLoader2 = this.mContext.getClassLoader();
        while (classLoader != classLoader2) {
            classLoader2 = classLoader2.getParent();
            if (classLoader2 == null) {
                return false;
            }
        }
        return true;
    }

    public final android.view.View createView(java.lang.String str, java.lang.String str2, android.util.AttributeSet attributeSet) throws java.lang.ClassNotFoundException, android.view.InflateException {
        android.content.Context context = (android.content.Context) this.mConstructorArgs[0];
        if (context == null) {
            context = this.mContext;
        }
        return createView(context, str, str2, attributeSet);
    }

    public final android.view.View createView(android.content.Context context, java.lang.String str, java.lang.String str2, android.util.AttributeSet attributeSet) throws java.lang.ClassNotFoundException, android.view.InflateException {
        java.util.Objects.requireNonNull(context);
        java.util.Objects.requireNonNull(str);
        java.lang.reflect.Constructor<? extends android.view.View> constructor = sConstructorMap.get(str);
        java.lang.Class cls = null;
        if (constructor != null && !verifyClassLoader(constructor)) {
            sConstructorMap.remove(str);
            constructor = null;
        }
        try {
            try {
                try {
                    try {
                        android.os.Trace.traceBegin(8L, str);
                        if (constructor == null) {
                            java.lang.Class asSubclass = java.lang.Class.forName(str2 != null ? str2 + str : str, false, this.mContext.getClassLoader()).asSubclass(android.view.View.class);
                            if (this.mFilter != null && asSubclass != null && !this.mFilter.onLoadClass(asSubclass)) {
                                failNotAllowed(str, str2, context, attributeSet);
                            }
                            constructor = asSubclass.getConstructor(mConstructorSignature);
                            constructor.setAccessible(true);
                            sConstructorMap.put(str, constructor);
                        } else if (this.mFilter != null) {
                            java.lang.Boolean bool = this.mFilterMap.get(str);
                            if (bool == null) {
                                java.lang.Class asSubclass2 = java.lang.Class.forName(str2 != null ? str2 + str : str, false, this.mContext.getClassLoader()).asSubclass(android.view.View.class);
                                boolean z = asSubclass2 != null && this.mFilter.onLoadClass(asSubclass2);
                                this.mFilterMap.put(str, java.lang.Boolean.valueOf(z));
                                if (!z) {
                                    failNotAllowed(str, str2, context, attributeSet);
                                }
                            } else if (bool.equals(java.lang.Boolean.FALSE)) {
                                failNotAllowed(str, str2, context, attributeSet);
                            }
                        }
                        java.lang.Object obj = this.mConstructorArgs[0];
                        this.mConstructorArgs[0] = context;
                        java.lang.Object[] objArr = this.mConstructorArgs;
                        objArr[1] = attributeSet;
                        try {
                            android.view.View newInstance = constructor.newInstance(objArr);
                            if (newInstance instanceof android.view.ViewStub) {
                                ((android.view.ViewStub) newInstance).setLayoutInflater(cloneInContext((android.content.Context) objArr[0]));
                            }
                            return newInstance;
                        } finally {
                            this.mConstructorArgs[0] = obj;
                        }
                    } catch (java.lang.NoSuchMethodException e) {
                        java.lang.StringBuilder append = new java.lang.StringBuilder().append(getParserStateDescription(context, attributeSet)).append(": Error inflating class ");
                        if (str2 != null) {
                            str = str2 + str;
                        }
                        android.view.InflateException inflateException = new android.view.InflateException(append.append(str).toString(), e);
                        inflateException.setStackTrace(EMPTY_STACK_TRACE);
                        throw inflateException;
                    } catch (java.lang.Exception e2) {
                        android.view.InflateException inflateException2 = new android.view.InflateException(getParserStateDescription(context, attributeSet) + ": Error inflating class " + (0 == 0 ? "<unknown>" : cls.getName()), e2);
                        inflateException2.setStackTrace(EMPTY_STACK_TRACE);
                        throw inflateException2;
                    }
                } catch (java.lang.ClassNotFoundException e3) {
                    throw e3;
                }
            } catch (java.lang.ClassCastException e4) {
                java.lang.StringBuilder append2 = new java.lang.StringBuilder().append(getParserStateDescription(context, attributeSet)).append(": Class is not a View ");
                if (str2 != null) {
                    str = str2 + str;
                }
                android.view.InflateException inflateException3 = new android.view.InflateException(append2.append(str).toString(), e4);
                inflateException3.setStackTrace(EMPTY_STACK_TRACE);
                throw inflateException3;
            }
        } finally {
            android.os.Trace.traceEnd(8L);
        }
    }

    private void failNotAllowed(java.lang.String str, java.lang.String str2, android.content.Context context, android.util.AttributeSet attributeSet) {
        java.lang.StringBuilder append = new java.lang.StringBuilder().append(getParserStateDescription(context, attributeSet)).append(": Class not allowed to be inflated ");
        if (str2 != null) {
            str = str2 + str;
        }
        throw new android.view.InflateException(append.append(str).toString());
    }

    protected android.view.View onCreateView(java.lang.String str, android.util.AttributeSet attributeSet) throws java.lang.ClassNotFoundException {
        return createView(str, "android.view.", attributeSet);
    }

    protected android.view.View onCreateView(android.view.View view, java.lang.String str, android.util.AttributeSet attributeSet) throws java.lang.ClassNotFoundException {
        return onCreateView(str, attributeSet);
    }

    public android.view.View onCreateView(android.content.Context context, android.view.View view, java.lang.String str, android.util.AttributeSet attributeSet) throws java.lang.ClassNotFoundException {
        return onCreateView(view, str, attributeSet);
    }

    private android.view.View createViewFromTag(android.view.View view, java.lang.String str, android.content.Context context, android.util.AttributeSet attributeSet) {
        return createViewFromTag(view, str, context, attributeSet, false);
    }

    android.view.View createViewFromTag(android.view.View view, java.lang.String str, android.content.Context context, android.util.AttributeSet attributeSet, boolean z) {
        android.view.View createView;
        if (str.equals("view")) {
            str = attributeSet.getAttributeValue(null, "class");
        }
        if (!z) {
            android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, ATTRS_THEME);
            int resourceId = obtainStyledAttributes.getResourceId(0, 0);
            if (resourceId != 0) {
                context = new android.view.ContextThemeWrapper(context, resourceId);
            }
            obtainStyledAttributes.recycle();
        }
        try {
            android.view.View tryCreateView = tryCreateView(view, str, context, attributeSet);
            if (tryCreateView == null) {
                java.lang.Object obj = this.mConstructorArgs[0];
                this.mConstructorArgs[0] = context;
                try {
                    if (-1 == str.indexOf(46)) {
                        createView = onCreateView(context, view, str, attributeSet);
                    } else {
                        createView = createView(context, str, null, attributeSet);
                    }
                    this.mConstructorArgs[0] = obj;
                    return createView;
                } catch (java.lang.Throwable th) {
                    this.mConstructorArgs[0] = obj;
                    throw th;
                }
            }
            return tryCreateView;
        } catch (android.view.InflateException e) {
            throw e;
        } catch (java.lang.ClassNotFoundException e2) {
            android.view.InflateException inflateException = new android.view.InflateException(getParserStateDescription(context, attributeSet) + ": Error inflating class " + str, e2);
            inflateException.setStackTrace(EMPTY_STACK_TRACE);
            throw inflateException;
        } catch (java.lang.Exception e3) {
            android.view.InflateException inflateException2 = new android.view.InflateException(getParserStateDescription(context, attributeSet) + ": Error inflating class " + str, e3);
            inflateException2.setStackTrace(EMPTY_STACK_TRACE);
            throw inflateException2;
        }
    }

    public final android.view.View tryCreateView(android.view.View view, java.lang.String str, android.content.Context context, android.util.AttributeSet attributeSet) {
        android.view.View view2;
        if (str.equals(TAG_1995)) {
            return new android.view.LayoutInflater.BlinkLayout(context, attributeSet);
        }
        if (this.mFactory2 != null) {
            view2 = this.mFactory2.onCreateView(view, str, context, attributeSet);
        } else if (this.mFactory != null) {
            view2 = this.mFactory.onCreateView(str, context, attributeSet);
        } else {
            view2 = null;
        }
        if (view2 == null && this.mPrivateFactory != null) {
            return this.mPrivateFactory.onCreateView(view, str, context, attributeSet);
        }
        return view2;
    }

    final void rInflateChildren(org.xmlpull.v1.XmlPullParser xmlPullParser, android.view.View view, android.util.AttributeSet attributeSet, boolean z) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        rInflate(xmlPullParser, view, view.getContext(), attributeSet, z);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x007c, code lost:
    
        r8.onFinishInflate();
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x007f, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0075, code lost:
    
        if (r1 == false) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0077, code lost:
    
        r8.restoreDefaultFocus();
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x007a, code lost:
    
        if (r11 == false) goto L53;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void rInflate(org.xmlpull.v1.XmlPullParser xmlPullParser, android.view.View view, android.content.Context context, android.util.AttributeSet attributeSet, boolean z) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int depth = xmlPullParser.getDepth();
        boolean z2 = false;
        while (true) {
            int next = xmlPullParser.next();
            if ((next != 3 || xmlPullParser.getDepth() > depth) && next != 1) {
                if (next == 2) {
                    java.lang.String name = xmlPullParser.getName();
                    if (TAG_REQUEST_FOCUS.equals(name)) {
                        consumeChildElements(xmlPullParser);
                        z2 = true;
                    } else if ("tag".equals(name)) {
                        parseViewTag(xmlPullParser, view, attributeSet);
                    } else if (TAG_INCLUDE.equals(name)) {
                        if (xmlPullParser.getDepth() == 0) {
                            throw new android.view.InflateException("<include /> cannot be the root element");
                        }
                        parseInclude(xmlPullParser, context, view, attributeSet);
                    } else {
                        if (TAG_MERGE.equals(name)) {
                            throw new android.view.InflateException("<merge /> must be the root element");
                        }
                        android.view.View createViewFromTag = createViewFromTag(view, name, context, attributeSet);
                        android.view.ViewGroup viewGroup = (android.view.ViewGroup) view;
                        android.view.ViewGroup.LayoutParams generateLayoutParams = viewGroup.generateLayoutParams(attributeSet);
                        rInflateChildren(xmlPullParser, createViewFromTag, attributeSet, true);
                        viewGroup.addView(createViewFromTag, generateLayoutParams);
                    }
                }
            }
        }
    }

    private void parseViewTag(org.xmlpull.v1.XmlPullParser xmlPullParser, android.view.View view, android.util.AttributeSet attributeSet) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.content.res.TypedArray obtainStyledAttributes = view.getContext().obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.ViewTag);
        view.setTag(obtainStyledAttributes.getResourceId(1, 0), obtainStyledAttributes.getText(0));
        obtainStyledAttributes.recycle();
        consumeChildElements(xmlPullParser);
    }

    private void parseInclude(org.xmlpull.v1.XmlPullParser xmlPullParser, android.content.Context context, android.view.View view, android.util.AttributeSet attributeSet) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        boolean z;
        int next;
        android.content.Context context2 = context;
        if (!(view instanceof android.view.ViewGroup)) {
            throw new android.view.InflateException("<include /> can only be used inside of a ViewGroup");
        }
        android.content.res.TypedArray obtainStyledAttributes = context2.obtainStyledAttributes(attributeSet, ATTRS_THEME);
        int resourceId = obtainStyledAttributes.getResourceId(0, 0);
        if (resourceId == 0) {
            z = false;
        } else {
            z = true;
        }
        if (z) {
            context2 = new android.view.ContextThemeWrapper(context2, resourceId);
        }
        obtainStyledAttributes.recycle();
        android.view.ViewGroup.LayoutParams layoutParams = null;
        int attributeResourceValue = attributeSet.getAttributeResourceValue(null, "layout", 0);
        if (attributeResourceValue == 0) {
            java.lang.String attributeValue = attributeSet.getAttributeValue(null, "layout");
            if (attributeValue == null || attributeValue.length() <= 0) {
                throw new android.view.InflateException("You must specify a layout in the include tag: <include layout=\"@layout/layoutID\" />");
            }
            attributeResourceValue = context2.getResources().getIdentifier(attributeValue.substring(1), "attr", context2.getPackageName());
        }
        if (this.mTempValue == null) {
            this.mTempValue = new android.util.TypedValue();
        }
        if (attributeResourceValue != 0 && context2.getTheme().resolveAttribute(attributeResourceValue, this.mTempValue, true)) {
            attributeResourceValue = this.mTempValue.resourceId;
        }
        if (attributeResourceValue == 0) {
            throw new android.view.InflateException("You must specify a valid layout reference. The layout ID " + attributeSet.getAttributeValue(null, "layout") + " is not valid.");
        }
        android.content.res.XmlResourceParser layout = context2.getResources().getLayout(attributeResourceValue);
        try {
            android.util.AttributeSet asAttributeSet = android.util.Xml.asAttributeSet(layout);
            do {
                next = layout.next();
                if (next == 2) {
                    break;
                }
            } while (next != 1);
            if (next != 2) {
                throw new android.view.InflateException(getParserStateDescription(context2, asAttributeSet) + ": No start tag found!");
            }
            java.lang.String name = layout.getName();
            if (TAG_MERGE.equals(name)) {
                rInflate(layout, view, context2, asAttributeSet, false);
            } else {
                android.view.View createViewFromTag = createViewFromTag(view, name, context2, asAttributeSet, z);
                android.view.ViewGroup viewGroup = (android.view.ViewGroup) view;
                android.content.res.TypedArray obtainStyledAttributes2 = context2.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.Include);
                int resourceId2 = obtainStyledAttributes2.getResourceId(0, -1);
                int i = obtainStyledAttributes2.getInt(1, -1);
                obtainStyledAttributes2.recycle();
                try {
                    layoutParams = viewGroup.generateLayoutParams(attributeSet);
                } catch (java.lang.RuntimeException e) {
                }
                if (layoutParams == null) {
                    layoutParams = viewGroup.generateLayoutParams(asAttributeSet);
                }
                createViewFromTag.setLayoutParams(layoutParams);
                rInflateChildren(layout, createViewFromTag, asAttributeSet, true);
                if (resourceId2 != -1) {
                    createViewFromTag.setId(resourceId2);
                }
                switch (i) {
                    case 0:
                        createViewFromTag.setVisibility(0);
                        break;
                    case 1:
                        createViewFromTag.setVisibility(4);
                        break;
                    case 2:
                        createViewFromTag.setVisibility(8);
                        break;
                }
                viewGroup.addView(createViewFromTag);
            }
            layout.close();
            consumeChildElements(xmlPullParser);
        } catch (java.lang.Throwable th) {
            layout.close();
            throw th;
        }
    }

    static final void consumeChildElements(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int next;
        int depth = xmlPullParser.getDepth();
        do {
            next = xmlPullParser.next();
            if (next == 3 && xmlPullParser.getDepth() <= depth) {
                return;
            }
        } while (next != 1);
    }

    private static class BlinkLayout extends android.widget.FrameLayout {
        private static final int BLINK_DELAY = 500;
        private static final int MESSAGE_BLINK = 66;
        private boolean mBlink;
        private boolean mBlinkState;
        private final android.os.Handler mHandler;

        public BlinkLayout(android.content.Context context, android.util.AttributeSet attributeSet) {
            super(context, attributeSet);
            this.mHandler = new android.os.Handler(new android.os.Handler.Callback() { // from class: android.view.LayoutInflater.BlinkLayout.1
                @Override // android.os.Handler.Callback
                public boolean handleMessage(android.os.Message message) {
                    if (message.what == 66) {
                        if (android.view.LayoutInflater.BlinkLayout.this.mBlink) {
                            android.view.LayoutInflater.BlinkLayout.this.mBlinkState = !android.view.LayoutInflater.BlinkLayout.this.mBlinkState;
                            android.view.LayoutInflater.BlinkLayout.this.makeBlink();
                        }
                        android.view.LayoutInflater.BlinkLayout.this.invalidate();
                        return true;
                    }
                    return false;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void makeBlink() {
            this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(66), 500L);
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            this.mBlink = true;
            this.mBlinkState = true;
            makeBlink();
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            this.mBlink = false;
            this.mBlinkState = true;
            this.mHandler.removeMessages(66);
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void dispatchDraw(android.graphics.Canvas canvas) {
            if (this.mBlinkState) {
                super.dispatchDraw(canvas);
            }
        }
    }
}
