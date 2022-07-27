package org.apache.openjpa.kernel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class TestResultShapePack {
    private final Class<?>[] classes;
    private final String[] aliases;
    private final Object[] values;
    private final Object response;
    private final boolean primitive;
    private ResultShape<Object> resultShape;

    private final Class startingClass;

    public TestResultShapePack(Class<?>[] classes, String[] aliases, Object[] values, boolean primitive,
                               Class startingClass, Object response) {
        this.classes = classes;
        this.aliases = aliases;
        this.values = values;
        this.primitive = primitive;
        this.startingClass = startingClass;
        this.response = response;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][]{

                // Suite Test
                {new Class[]{TestClass2.class, short.class}, new String[]{"TestClass2", "shortAlias"},
                        new Object[]{new TestClass2(), 3}, false, Object.class, null},
                {null, new String[]{"TestClass2", "shortAlias"},
                        new Object[]{new TestClass2(), 3}, false, Object.class, new NullPointerException()},
                {new Class[]{}, null, new Object[]{new TestClass2(), 3}, true, int.class,
                        new ArrayIndexOutOfBoundsException()},
                {new Class[]{TestClass1.class}, new String[]{"TestClass2"}, new Object[]{new TestClass1()},
                        false, Object.class, null},

        });
    }

    @Before
    public void configureTest() {
        resultShape = new ResultShape<>(startingClass, primitive);
    }

    @Test
    public void testPack() {
        try {
            resultShape.pack(values, classes, aliases);
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), response.getClass());
        }
    }
}
