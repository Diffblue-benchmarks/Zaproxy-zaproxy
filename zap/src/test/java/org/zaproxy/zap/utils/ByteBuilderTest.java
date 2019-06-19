package org.zaproxy.zap.utils;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ByteBuilderTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void testEnsureCapacity() {
        ByteBuilder byteBuilder = new ByteBuilder(5);
        Assert.assertEquals(5, byteBuilder.capacity());

        byteBuilder.ensureCapacity(20);
        Assert.assertEquals(20, byteBuilder.capacity());
    }

    @Test
    public void testSize() {
        Assert.assertEquals(0, new ByteBuilder().size());
        Assert.assertEquals(2, new ByteBuilder(new byte[]{4, 4}).size());
    }

    @Test
    public void testToString() {
        Assert.assertArrayEquals(new byte[]{4, 4, 0, 0},
                new ByteBuilder(new byte[]{4, 4}).toString().getBytes());
    }

    @Test
    public void testSubSequence() {
        byte[] bytes = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        ByteBuilder byteBuilder = new ByteBuilder(bytes);

        Assert.assertArrayEquals(new byte[]{4, 5, 6},
                byteBuilder.subSequence(3, 6));
    }

    @Test
    public void testToByteArray() {
        byte[] bytes = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        ByteBuilder byteBuilder = new ByteBuilder(bytes);

        Assert.assertArrayEquals(bytes, byteBuilder.toByteArray());
    }

    @Test
    public void testAppendByte() {
        ByteBuilder byteBuilder = new ByteBuilder(new byte[]{4, 4});
        byteBuilder.append((byte) 2);

        Assert.assertArrayEquals(new byte[]{4, 4, 2},
                byteBuilder.toByteArray());
    }

    @Test
    public void testAppendByteArray1() {
        ByteBuilder byteBuilder = new ByteBuilder(new byte[]{4, 4});
        byteBuilder.append(new byte[]{1, 2});

        Assert.assertArrayEquals(new byte[]{4, 4, 1, 2},
                byteBuilder.toByteArray());
    }

    @Test
    public void testAppendByteArray2() {
        ByteBuilder byteBuilder = new ByteBuilder(new byte[]{4, 4});
        byteBuilder.append(new byte[]{1, 2, 3, 4, 5, 6}, 1, 3);

        Assert.assertArrayEquals(new byte[]{4, 4, 2, 3, 4},
                byteBuilder.toByteArray());

        thrown.expect(ArrayIndexOutOfBoundsException.class);
        new ByteBuilder().append(new byte[0], 2, 3);
    }

    @Test
    public void testAppendChar() {
        ByteBuilder byteBuilder = new ByteBuilder(new byte[]{4, 4});
        byteBuilder.append('3');

        Assert.assertArrayEquals(new byte[]{4, 4, 51},
                byteBuilder.toByteArray());
    }

    @Test
    public void testAppendCharArray1() {
        ByteBuilder byteBuilder = new ByteBuilder(new byte[]{4, 4});
        byteBuilder.append(new char[]{'3', '5'});

        Assert.assertArrayEquals(new byte[]{4, 4, 51, 53},
                byteBuilder.toByteArray());
    }

    @Test
    public void testAppendCharArray2() {
        ByteBuilder byteBuilder = new ByteBuilder(new byte[]{4, 4});
        byteBuilder.append(new char[]{'1', '2', '3', '4', '5', '6'}, 1, 3);

        Assert.assertArrayEquals(new byte[]{4, 4, 50, 51, 52},
                byteBuilder.toByteArray());

        thrown.expect(ArrayIndexOutOfBoundsException.class);
        new ByteBuilder().append(new char[0], 2, 3);
    }

    @Test
    public void testAppendBoolean() {
        ByteBuilder byteBuilder = new ByteBuilder(new byte[]{4, 4});
        byteBuilder.append(true);
        byteBuilder.append(false);

        Assert.assertArrayEquals(new byte[]{4, 4, 1, 0},
                byteBuilder.toByteArray());
    }

    @Test
    public void testAppendShort() {
        ByteBuilder byteBuilder = new ByteBuilder(new byte[]{4, 4});
        byteBuilder.append((short) 3);

        Assert.assertArrayEquals(new byte[]{4, 4, 0, 3},
                byteBuilder.toByteArray());
    }

    @Test
    public void testAppendInt() {
        ByteBuilder byteBuilder = new ByteBuilder(new byte[]{4, 4});
        byteBuilder.append(2);

        Assert.assertArrayEquals(new byte[]{4, 4, 0, 0, 0, 2},
                byteBuilder.toByteArray());
    }

    @Test
    public void testAppendLong() {
        ByteBuilder byteBuilder = new ByteBuilder(new byte[]{4, 4});
        byteBuilder.append(1234567890123456789L);

        Assert.assertArrayEquals(
                new byte[]{4, 4, 17, 34, 16, -12, 125, -23, -127, 21},
                byteBuilder.toByteArray());
    }

    @Test
    public void testAppendFloat() {
        ByteBuilder byteBuilder = new ByteBuilder(new byte[]{4, 4});
        byteBuilder.append(1234567890123456789.12f);

        Assert.assertArrayEquals(new byte[]{4, 4, 93, -119, 16, -120},
                byteBuilder.toByteArray());
    }

    @Test
    public void testAppendDouble() {
        ByteBuilder byteBuilder = new ByteBuilder(new byte[]{4, 4});
        byteBuilder.append(1234567890123456789.12);

        Assert.assertArrayEquals(
                new byte[]{4, 4, 67, -79, 34, 16, -12, 125, -23, -127},
                byteBuilder.toByteArray());
    }

    @Test
    public void testAppendObject() {
        class FooClass {
            public byte[] toByteStructure() {
                return new byte[]{1, 2};
            }
        }

        ByteBuilder byteBuilder = new ByteBuilder(new byte[]{4, 4});
        byteBuilder.append(new FooClass());

        Assert.assertArrayEquals(new byte[]{4, 4, 1, 2},
                byteBuilder.toByteArray());
    }

    @Test
    public void testAppendObjectString() {
        ByteBuilder byteBuilder = new ByteBuilder(new byte[]{4, 4});
        byteBuilder.append((Object) 123456);

        Assert.assertArrayEquals(
                new byte[]{4, 4, 0, 0, 0, 6, 49, 50, 51, 52, 53, 54},
                byteBuilder.toByteArray());
    }

    @Test
    public void testAppendString() {
        ByteBuilder byteBuilder = new ByteBuilder(new byte[]{4, 4});
        byteBuilder.append("FooBarBaz£$%^&*");

        Assert.assertArrayEquals(
                new byte[]{4, 4, 0, 0, 0, 16, 70, 111, 111, 66, 97, 114,
                        66, 97, 122, -62, -93, 36, 37, 94, 38, 42},
                byteBuilder.toByteArray());
    }

    @Test
    public void testAppendStringBuffer() {
        ByteBuilder byteBuilder = new ByteBuilder(new byte[]{4, 4});
        byteBuilder.append(new StringBuffer("FooBarBaz£$%^&*"));

        Assert.assertArrayEquals(
                new byte[]{4, 4, 0, 0, 0, 16, 70, 111, 111, 66, 97, 114,
                        66, 97, 122, -62, -93, 36, 37, 94, 38, 42},
                byteBuilder.toByteArray());
    }

    @Test
    public void testAppendByteBuilder() {
        ByteBuilder byteBuilder = new ByteBuilder(new byte[]{4, 4});
        byteBuilder.append(new ByteBuilder(new byte[]{5, 5}));

        Assert.assertArrayEquals(new byte[]{4, 4, 5, 5},
                byteBuilder.toByteArray());
    }

    @Test
    public void testAppendSpecial1() {
        ByteBuilder byteBuilder = new ByteBuilder(new byte[]{4, 4});
        byteBuilder.appendSpecial(1234567890123456789L, 3, true);

        Assert.assertArrayEquals(new byte[]{4, 4, 105, -127, 21},
                byteBuilder.toByteArray());
    }

    @Test
    public void testAppendSpecial2() {
        ByteBuilder byteBuilder = new ByteBuilder(new byte[]{4, 4});
        byteBuilder.appendSpecial(-1234567890123456789L, 3, true);

        Assert.assertArrayEquals(new byte[]{4, 4, -106, 126, -21},
                byteBuilder.toByteArray());
    }
}
