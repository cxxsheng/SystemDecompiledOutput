package android.transition;

/* loaded from: classes3.dex */
public class TransitionInflater {
    private static final java.lang.Class<?>[] sConstructorSignature = {android.content.Context.class, android.util.AttributeSet.class};
    private static final android.util.ArrayMap<java.lang.String, java.lang.reflect.Constructor> sConstructors = new android.util.ArrayMap<>();
    private android.content.Context mContext;

    private TransitionInflater(android.content.Context context) {
        this.mContext = context;
    }

    public static android.transition.TransitionInflater from(android.content.Context context) {
        return new android.transition.TransitionInflater(context);
    }

    public android.transition.Transition inflateTransition(int i) {
        android.content.res.XmlResourceParser xml = this.mContext.getResources().getXml(i);
        try {
            try {
                return createTransitionFromXml(xml, android.util.Xml.asAttributeSet(xml), null);
            } catch (java.io.IOException e) {
                android.view.InflateException inflateException = new android.view.InflateException(xml.getPositionDescription() + ": " + e.getMessage());
                inflateException.initCause(e);
                throw inflateException;
            } catch (org.xmlpull.v1.XmlPullParserException e2) {
                android.view.InflateException inflateException2 = new android.view.InflateException(e2.getMessage());
                inflateException2.initCause(e2);
                throw inflateException2;
            }
        } finally {
            xml.close();
        }
    }

    public android.transition.TransitionManager inflateTransitionManager(int i, android.view.ViewGroup viewGroup) {
        android.content.res.XmlResourceParser xml = this.mContext.getResources().getXml(i);
        try {
            try {
                return createTransitionManagerFromXml(xml, android.util.Xml.asAttributeSet(xml), viewGroup);
            } catch (java.io.IOException e) {
                android.view.InflateException inflateException = new android.view.InflateException(xml.getPositionDescription() + ": " + e.getMessage());
                inflateException.initCause(e);
                throw inflateException;
            } catch (org.xmlpull.v1.XmlPullParserException e2) {
                android.view.InflateException inflateException2 = new android.view.InflateException(e2.getMessage());
                inflateException2.initCause(e2);
                throw inflateException2;
            }
        } finally {
            xml.close();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x017a, code lost:
    
        return r3;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private android.transition.Transition createTransitionFromXml(org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.transition.Transition transition) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int depth = xmlPullParser.getDepth();
        android.transition.TransitionSet transitionSet = transition instanceof android.transition.TransitionSet ? (android.transition.TransitionSet) transition : null;
        android.transition.Transition transition2 = null;
        while (true) {
            int next = xmlPullParser.next();
            if ((next != 3 || xmlPullParser.getDepth() > depth) && next != 1) {
                if (next == 2) {
                    java.lang.String name = xmlPullParser.getName();
                    if ("fade".equals(name)) {
                        transition2 = new android.transition.Fade(this.mContext, attributeSet);
                    } else if ("changeBounds".equals(name)) {
                        transition2 = new android.transition.ChangeBounds(this.mContext, attributeSet);
                    } else if ("slide".equals(name)) {
                        transition2 = new android.transition.Slide(this.mContext, attributeSet);
                    } else if ("explode".equals(name)) {
                        transition2 = new android.transition.Explode(this.mContext, attributeSet);
                    } else if ("changeImageTransform".equals(name)) {
                        transition2 = new android.transition.ChangeImageTransform(this.mContext, attributeSet);
                    } else if ("changeTransform".equals(name)) {
                        transition2 = new android.transition.ChangeTransform(this.mContext, attributeSet);
                    } else if ("changeClipBounds".equals(name)) {
                        transition2 = new android.transition.ChangeClipBounds(this.mContext, attributeSet);
                    } else if ("autoTransition".equals(name)) {
                        transition2 = new android.transition.AutoTransition(this.mContext, attributeSet);
                    } else if ("recolor".equals(name)) {
                        transition2 = new android.transition.Recolor(this.mContext, attributeSet);
                    } else if ("changeScroll".equals(name)) {
                        transition2 = new android.transition.ChangeScroll(this.mContext, attributeSet);
                    } else if ("transitionSet".equals(name)) {
                        transition2 = new android.transition.TransitionSet(this.mContext, attributeSet);
                    } else if ("transition".equals(name)) {
                        transition2 = (android.transition.Transition) createCustom(attributeSet, android.transition.Transition.class, "transition");
                    } else if ("targets".equals(name)) {
                        getTargetIds(xmlPullParser, attributeSet, transition);
                    } else if ("arcMotion".equals(name)) {
                        transition.setPathMotion(new android.transition.ArcMotion(this.mContext, attributeSet));
                    } else if ("pathMotion".equals(name)) {
                        transition.setPathMotion((android.transition.PathMotion) createCustom(attributeSet, android.transition.PathMotion.class, "pathMotion"));
                    } else if ("patternPathMotion".equals(name)) {
                        transition.setPathMotion(new android.transition.PatternPathMotion(this.mContext, attributeSet));
                    } else {
                        throw new java.lang.RuntimeException("Unknown scene name: " + xmlPullParser.getName());
                    }
                    if (transition2 == null) {
                        continue;
                    } else {
                        if (!xmlPullParser.isEmptyElementTag()) {
                            createTransitionFromXml(xmlPullParser, attributeSet, transition2);
                        }
                        if (transitionSet != null) {
                            transitionSet.addTransition(transition2);
                            transition2 = null;
                        } else if (transition != null) {
                            throw new android.view.InflateException("Could not add transition to another transition.");
                        }
                    }
                }
            }
        }
    }

    private java.lang.Object createCustom(android.util.AttributeSet attributeSet, java.lang.Class cls, java.lang.String str) {
        java.lang.Object newInstance;
        java.lang.Class<? extends U> asSubclass;
        java.lang.String attributeValue = attributeSet.getAttributeValue(null, "class");
        if (attributeValue == null) {
            throw new android.view.InflateException(str + " tag must have a 'class' attribute");
        }
        try {
            synchronized (sConstructors) {
                java.lang.reflect.Constructor constructor = sConstructors.get(attributeValue);
                if (constructor == null && (asSubclass = this.mContext.getClassLoader().loadClass(attributeValue).asSubclass(cls)) != 0) {
                    constructor = asSubclass.getConstructor(sConstructorSignature);
                    constructor.setAccessible(true);
                    sConstructors.put(attributeValue, constructor);
                }
                newInstance = constructor.newInstance(this.mContext, attributeSet);
            }
            return newInstance;
        } catch (java.lang.ClassNotFoundException e) {
            throw new android.view.InflateException("Could not instantiate " + cls + " class " + attributeValue, e);
        } catch (java.lang.IllegalAccessException e2) {
            throw new android.view.InflateException("Could not instantiate " + cls + " class " + attributeValue, e2);
        } catch (java.lang.InstantiationException e3) {
            throw new android.view.InflateException("Could not instantiate " + cls + " class " + attributeValue, e3);
        } catch (java.lang.NoSuchMethodException e4) {
            throw new android.view.InflateException("Could not instantiate " + cls + " class " + attributeValue, e4);
        } catch (java.lang.reflect.InvocationTargetException e5) {
            throw new android.view.InflateException("Could not instantiate " + cls + " class " + attributeValue, e5);
        }
    }

    private void getTargetIds(org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.transition.Transition transition) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            if ((next != 3 || xmlPullParser.getDepth() > depth) && next != 1) {
                if (next == 2) {
                    if (xmlPullParser.getName().equals("target")) {
                        android.content.res.TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.TransitionTarget);
                        int resourceId = obtainStyledAttributes.getResourceId(1, 0);
                        if (resourceId != 0) {
                            transition.addTarget(resourceId);
                        } else {
                            int resourceId2 = obtainStyledAttributes.getResourceId(2, 0);
                            if (resourceId2 != 0) {
                                transition.excludeTarget(resourceId2, true);
                            } else {
                                java.lang.String string = obtainStyledAttributes.getString(4);
                                if (string != null) {
                                    transition.addTarget(string);
                                } else {
                                    java.lang.String string2 = obtainStyledAttributes.getString(5);
                                    if (string2 != null) {
                                        transition.excludeTarget(string2, true);
                                    } else {
                                        java.lang.String string3 = obtainStyledAttributes.getString(3);
                                        if (string3 != null) {
                                            try {
                                                transition.excludeTarget((java.lang.Class) java.lang.Class.forName(string3), true);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                obtainStyledAttributes.recycle();
                                                throw new java.lang.RuntimeException("Could not create " + string3, e);
                                            }
                                        } else {
                                            java.lang.String string4 = obtainStyledAttributes.getString(0);
                                            if (string4 != null) {
                                                transition.addTarget(java.lang.Class.forName(string4));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        obtainStyledAttributes.recycle();
                    } else {
                        throw new java.lang.RuntimeException("Unknown scene name: " + xmlPullParser.getName());
                    }
                }
            } else {
                return;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0058, code lost:
    
        return r1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private android.transition.TransitionManager createTransitionManagerFromXml(org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.view.ViewGroup viewGroup) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int depth = xmlPullParser.getDepth();
        android.transition.TransitionManager transitionManager = null;
        while (true) {
            int next = xmlPullParser.next();
            if ((next != 3 || xmlPullParser.getDepth() > depth) && next != 1) {
                if (next == 2) {
                    java.lang.String name = xmlPullParser.getName();
                    if (name.equals("transitionManager")) {
                        transitionManager = new android.transition.TransitionManager();
                    } else {
                        if (!name.equals("transition") || transitionManager == null) {
                            break;
                        }
                        loadTransition(attributeSet, viewGroup, transitionManager);
                    }
                }
            }
        }
        throw new java.lang.RuntimeException("Unknown scene name: " + xmlPullParser.getName());
    }

    private void loadTransition(android.util.AttributeSet attributeSet, android.view.ViewGroup viewGroup, android.transition.TransitionManager transitionManager) throws android.content.res.Resources.NotFoundException {
        android.transition.Transition inflateTransition;
        android.content.res.TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.TransitionManager);
        int resourceId = obtainStyledAttributes.getResourceId(2, -1);
        int resourceId2 = obtainStyledAttributes.getResourceId(0, -1);
        android.transition.Scene sceneForLayout = resourceId2 < 0 ? null : android.transition.Scene.getSceneForLayout(viewGroup, resourceId2, this.mContext);
        int resourceId3 = obtainStyledAttributes.getResourceId(1, -1);
        android.transition.Scene sceneForLayout2 = resourceId3 >= 0 ? android.transition.Scene.getSceneForLayout(viewGroup, resourceId3, this.mContext) : null;
        if (resourceId >= 0 && (inflateTransition = inflateTransition(resourceId)) != null) {
            if (sceneForLayout2 == null) {
                throw new java.lang.RuntimeException("No toScene for transition ID " + resourceId);
            }
            if (sceneForLayout == null) {
                transitionManager.setTransition(sceneForLayout2, inflateTransition);
            } else {
                transitionManager.setTransition(sceneForLayout, sceneForLayout2, inflateTransition);
            }
        }
        obtainStyledAttributes.recycle();
    }
}
