package com;

import com.exceptions.PositionException;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PositionsTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private static class CauseMatcher extends TypeSafeMatcher<Throwable> {

        private final Class<? extends Throwable> type;
        private final String expectedMessage;

        CauseMatcher(Class<? extends Throwable> type, String expectedMessage) {
            this.type = type;
            this.expectedMessage = expectedMessage;
        }

        @Override
        protected boolean matchesSafely(Throwable item) {
            return item.getClass().isAssignableFrom(type)
                    && item.getMessage().contains(expectedMessage);
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("expects type ")
                    .appendValue(type)
                    .appendText(" and a message ")
                    .appendValue(expectedMessage);
        }
    }

    private static Positions positions = new Positions();

    @Test
    public void initialAmountLessThan0() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        thrown.expect(InvocationTargetException.class);
        thrown.expectCause(new CauseMatcher(PositionException.class, "CNY"));
        TransactionRecord record = new TransactionRecord("CNY", -123);
        Method updatePositionsMethod = getUpdatePositionMethod();
        updatePositionsMethod.invoke(positions, record);
    }

    @Test
    public void amountLessThan0() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        thrown.expect(InvocationTargetException.class);
        thrown.expectCause(new CauseMatcher(PositionException.class, "CNY"));
        TransactionRecord record1 = new TransactionRecord("CNY", 123);
        TransactionRecord record = new TransactionRecord("CNY", -124);
        Method updatePositionsMethod = getUpdatePositionMethod();
        updatePositionsMethod.invoke(positions, record);
    }

    @Test
    public void successUpdateAmount() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException {
        TransactionRecord record = new TransactionRecord("CNY", 123);
        Method updatePositionsMethod = getUpdatePositionMethod();
        updatePositionsMethod.invoke(positions, record);
        Map<String, Integer> expectedPositions = new ConcurrentHashMap<>();
        expectedPositions.put("CNY", 123);
        Assert.assertEquals(expectedPositions, getPositions().get(positions));
    }

    @Test
    public void remove0AmountCode() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException {
        TransactionRecord record1 = new TransactionRecord("CNY", 123);
        TransactionRecord record2 = new TransactionRecord("CNY", -123);
        Method updatePositionsMethod = getUpdatePositionMethod();
        updatePositionsMethod.invoke(positions, record1);
        updatePositionsMethod.invoke(positions, record2);
        Map<String, Integer> expectedPositions = new ConcurrentHashMap<>();
        Assert.assertEquals(expectedPositions, getPositions().get(positions));
    }



    private Method getUpdatePositionMethod() throws NoSuchMethodException {
        Method updatePositionsMethod = Positions.class.getDeclaredMethod("updatePositions", TransactionRecord.class);
        updatePositionsMethod.setAccessible(true);
        return updatePositionsMethod;
    }

    private Field getPositions() throws NoSuchFieldException {
        Field field = Positions.class.getDeclaredField("positions");
        field.setAccessible(true);
        return field;
    }

}