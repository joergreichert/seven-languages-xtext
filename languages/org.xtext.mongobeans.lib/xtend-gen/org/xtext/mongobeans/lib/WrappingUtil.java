/**
 * Copyright (c) 2012 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.xtext.mongobeans.lib;

import com.mongodb.DBObject;
import java.lang.reflect.Constructor;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.xtext.mongobeans.lib.IMongoBean;

/**
 * Mongobeans <-> DBObject conversion methods.
 */
@SuppressWarnings("all")
public class WrappingUtil {
  public static IMongoBean wrap(final DBObject dbObject) {
    try {
      IMongoBean _xblockexpression = null;
      {
        Object _get = dbObject.get(IMongoBean.JAVA_CLASS_KEY);
        String _string = null;
        if (_get!=null) {
          _string=_get.toString();
        }
        final String javaClassName = _string;
        ClassLoader _classLoader = WrappingUtil.getClassLoader();
        final Class<? extends Object> javaClass = _classLoader.loadClass(javaClassName);
        IMongoBean _xifexpression = null;
        boolean _isAssignableFrom = IMongoBean.class.isAssignableFrom(javaClass);
        if (_isAssignableFrom) {
          IMongoBean _xblockexpression_1 = null;
          {
            final Constructor<? extends Object> constructor = javaClass.getConstructor(DBObject.class);
            Object _newInstance = constructor.newInstance(dbObject);
            _xblockexpression_1 = (((IMongoBean) _newInstance));
          }
          _xifexpression = _xblockexpression_1;
        } else {
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("Stored javaClass \'");
          _builder.append(javaClassName, "");
          _builder.append("\' does not extend \'");
          String _simpleName = IMongoBean.class.getSimpleName();
          _builder.append(_simpleName, "");
          _builder.append("\'.");
          IllegalStateException _illegalStateException = new IllegalStateException(_builder.toString());
          throw _illegalStateException;
        }
        _xblockexpression = (_xifexpression);
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public static <T extends IMongoBean> T wrapAndCast(final DBObject dbObject) {
    IMongoBean _wrap = WrappingUtil.wrap(dbObject);
    return ((T) _wrap);
  }
  
  public static DBObject unwrap(final Object wrapper) {
    DBObject _xifexpression = null;
    if ((!(wrapper instanceof IMongoBean))) {
      IllegalArgumentException _illegalArgumentException = new IllegalArgumentException("Invalid type");
      throw _illegalArgumentException;
    } else {
      DBObject _dbObject = ((IMongoBean) wrapper).getDbObject();
      _xifexpression = _dbObject;
    }
    return _xifexpression;
  }
  
  private static ClassLoader _classLoader = new Function0<ClassLoader>() {
    public ClassLoader apply() {
      ClassLoader _classLoader = WrappingUtil.getClassLoader();
      return _classLoader;
    }
  }.apply();
  
  public static ClassLoader getClassLoader() {
    return WrappingUtil._classLoader;
  }
  
  public static ClassLoader setClassLoader(final ClassLoader classLoader) {
    ClassLoader __classLoader = WrappingUtil._classLoader = classLoader;
    return __classLoader;
  }
}
