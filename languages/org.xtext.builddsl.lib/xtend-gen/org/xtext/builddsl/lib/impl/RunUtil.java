/**
 * Copyright (c) 2012 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.xtext.builddsl.lib.impl;

import java.lang.reflect.Method;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class RunUtil {
  public static void runMain(final Class<? extends Object> clazz, final String[] argument) {
    try {
      Class<? extends Object> _forName = Class.forName("[Ljava.lang.String;");
      final Method mainMethod = clazz.getDeclaredMethod("main", _forName);
      mainMethod.invoke(null, new Object[] { argument });
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
