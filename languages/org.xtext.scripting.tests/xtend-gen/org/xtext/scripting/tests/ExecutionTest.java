/**
 * Copyright (c) 2012 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.xtext.scripting.tests;

import com.google.inject.Inject;
import java.lang.reflect.InvocationTargetException;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.util.IAcceptor;
import org.eclipse.xtext.xbase.compiler.CompilationTestHelper;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.util.ReflectExtensions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtext.scripting.ScriptingInjectorProvider;

@RunWith(XtextRunner.class)
@InjectWith(ScriptingInjectorProvider.class)
@SuppressWarnings("all")
public class ExecutionTest {
  @Inject
  @Extension
  private CompilationTestHelper _compilationTestHelper;
  
  @Inject
  @Extension
  private ReflectExtensions _reflectExtensions;
  
  @Test
  public void testHelloWorld() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("throw new Exception(\'Hello World\')");
    _builder.newLine();
    this.compileAndExecuteMainAndExpect(_builder, "Hello World");
  }
  
  @Test
  public void testDeepThought() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("val answer = 2 * (17 + 4)");
    _builder.newLine();
    _builder.append("throw new Exception(answer.toString)");
    _builder.newLine();
    this.compileAndExecuteMainAndExpect(_builder, Integer.valueOf(42));
  }
  
  protected void compileAndExecuteMainAndExpect(final CharSequence script, final Object expectedResult) {
    try {
      final IAcceptor<CompilationTestHelper.Result> _function = new IAcceptor<CompilationTestHelper.Result>() {
        public void accept(final CompilationTestHelper.Result it) {
          try {
            try {
              Class<? extends Object> _compiledClass = it.getCompiledClass();
              Object _newInstance = _compiledClass.newInstance();
              ExecutionTest.this._reflectExtensions.invoke(_newInstance, "main", null);
              Assert.fail("Expected ResultException not thrown.");
            } catch (final Throwable _t) {
              if (_t instanceof InvocationTargetException) {
                final InvocationTargetException exc = (InvocationTargetException)_t;
                String _string = expectedResult.toString();
                Throwable _cause = exc.getCause();
                String _message = _cause.getMessage();
                Assert.assertEquals(_string, _message);
              } else {
                throw Exceptions.sneakyThrow(_t);
              }
            }
          } catch (Throwable _e) {
            throw Exceptions.sneakyThrow(_e);
          }
        }
      };
      this._compilationTestHelper.compile(script, _function);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
