package com.github.tfaga.lynx.test;

import com.github.tfaga.lynx.beans.QueryFilter;
import com.github.tfaga.lynx.beans.QueryParameters;
import com.github.tfaga.lynx.enums.FilterOperation;
import com.github.tfaga.lynx.exceptions.InvalidFieldValueException;
import com.github.tfaga.lynx.exceptions.NoSuchEntityFieldException;
import com.github.tfaga.lynx.test.entities.AccountEntity;
import com.github.tfaga.lynx.test.entities.DocumentEntity;
import com.github.tfaga.lynx.test.entities.enums.DocumentEnum;
import com.github.tfaga.lynx.test.utils.JpaUtil;
import com.github.tfaga.lynx.utils.JPAUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.util.*;

/**
 * @author Tilen Faganel
 * @version 1.0.0
 * @since 1.0.0
 */
@RunWith(Parameterized.class)
public class JPAUtilsFiltersTest {

    @Parameterized.Parameters
    public static Collection<EntityManager> data() {

        JpaUtil jpaUtil = JpaUtil.getInstance();

        return Arrays.asList(
                jpaUtil.getEclipselinkEntityManager(),
                jpaUtil.getHibernateEntityManager()
        );
    }

    @Parameterized.Parameter
    public EntityManager em;

    @Test
    public void testSingleFilter() {

        QueryFilter qf = new QueryFilter();
        qf.setField("string");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("Carol");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(1, documents.size());
        Assert.assertNotNull(documents.get(0).getString());
        Assert.assertEquals("Carol", documents.get(0).getString());
    }

    //// Test filter operation correctness

    // EQ

    @Test
    public void testEqObjectInteger() {

        QueryFilter qf = new QueryFilter();
        qf.setField("objectInteger");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("60");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(2, documents.size());
        Assert.assertNotNull(documents.get(0).getObjectInteger());
        Assert.assertEquals(60, documents.get(0).getObjectInteger().intValue());
    }

    @Test
    public void testEqPrimitiveInteger() {

        QueryFilter qf = new QueryFilter();
        qf.setField("primitiveInteger");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("56");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(1, documents.size());
        Assert.assertEquals(56, documents.get(0).getPrimitiveInteger());
    }

    @Test
    public void testEqObjectLong() {

        QueryFilter qf = new QueryFilter();
        qf.setField("objectLong");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("60");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(1, documents.size());
        Assert.assertNotNull(documents.get(0).getObjectLong());
        Assert.assertEquals(60L, documents.get(0).getObjectLong().longValue());
    }

    @Test
    public void testEqPrimitiveLong() {

        QueryFilter qf = new QueryFilter();
        qf.setField("primitiveLong");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("25");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(2, documents.size());
        Assert.assertEquals(25L, documents.get(0).getPrimitiveLong());
    }

    @Test
    public void testEqObjectShort() {

        QueryFilter qf = new QueryFilter();
        qf.setField("objectShort");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("60");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(2, documents.size());
        Assert.assertNotNull(documents.get(0).getObjectShort());
        Assert.assertEquals(60, documents.get(0).getObjectShort().shortValue());
    }

    @Test
    public void testEqPrimitiveShort() {

        QueryFilter qf = new QueryFilter();
        qf.setField("primitiveShort");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("75");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(1, documents.size());
        Assert.assertEquals(75, documents.get(0).getPrimitiveShort());
    }

    @Test
    public void testEqObjectDouble() {

        QueryFilter qf = new QueryFilter();
        qf.setField("objectDouble");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("82.47");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(1, documents.size());
        Assert.assertNotNull(documents.get(0).getObjectDouble());
        Assert.assertEquals(82.47, documents.get(0).getObjectDouble(), 0.1);
    }

    @Test
    public void testEqPrimitiveDouble() {

        QueryFilter qf = new QueryFilter();
        qf.setField("primitiveDouble");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("60.35");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(1, documents.size());
        Assert.assertEquals(60.35, documents.get(0).getPrimitiveDouble(), 0.1);
    }

    @Test
    public void testEqObjectFloat() {

        QueryFilter qf = new QueryFilter();
        qf.setField("objectFloat");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("94.5");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(1, documents.size());
        Assert.assertNotNull(documents.get(0).getObjectFloat());
        Assert.assertEquals(94.5f, documents.get(0).getObjectFloat(), 0.1f);
    }

    @Test
    public void testEqPrimitiveFloat() {

        QueryFilter qf = new QueryFilter();
        qf.setField("primitiveFloat");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("48.5");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(1, documents.size());
        Assert.assertEquals(48.5f, documents.get(0).getPrimitiveFloat(), 0.1f);
    }

    @Test
    public void testEqBigDecimal() {

        QueryFilter qf = new QueryFilter();
        qf.setField("bigDecimal");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("93.61");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(1, documents.size());
        Assert.assertNotNull(documents.get(0).getBigDecimal());
        Assert.assertEquals(new BigDecimal("93.61"), documents.get(0).getBigDecimal());
    }

    @Test
    public void testEqBigInteger() {

        QueryFilter qf = new QueryFilter();
        qf.setField("bigInteger");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("87");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(3, documents.size());
        Assert.assertNotNull(documents.get(0).getBigInteger());
        Assert.assertEquals(new BigInteger("87"), documents.get(0).getBigInteger());
    }

    @Test
    public void testEqObjectBoolean() {

        QueryFilter qf = new QueryFilter();
        qf.setField("objectBoolean");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("true");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(28, documents.size());
        Assert.assertNotNull(documents.get(0).getObjectBoolean());
        Assert.assertTrue(documents.get(0).getObjectBoolean());
    }

    @Test
    public void testEqInvalidObjectBoolean() {

        QueryFilter qf = new QueryFilter();
        qf.setField("objectBoolean");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("INVALID");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(22, documents.size());
    }

    @Test
    public void testEqPrimitiveBoolean() {

        QueryFilter qf = new QueryFilter();
        qf.setField("primitiveBoolean");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("true");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(24, documents.size());
        Assert.assertTrue(documents.get(0).isPrimitiveBoolean());
    }

    @Test
    public void testEqInvalidPrimitiveBoolean() {

        QueryFilter qf = new QueryFilter();
        qf.setField("primitiveBoolean");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("INVALID");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(26, documents.size());
    }

    @Test
    public void testEqObjectCharacter() {

        QueryFilter qf = new QueryFilter();
        qf.setField("objectCharacter");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("z");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(4, documents.size());
        Assert.assertNotNull(documents.get(0).getObjectCharacter());
        Assert.assertEquals('z', documents.get(0).getObjectCharacter().charValue());
    }

    @Test
    public void testEqInvalidObjectCharacter() {

        QueryFilter qf = new QueryFilter();
        qf.setField("objectCharacter");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("zzzzzzzz");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(4, documents.size());
        Assert.assertNotNull(documents.get(0).getObjectCharacter());
        Assert.assertEquals('z', documents.get(0).getObjectCharacter().charValue());
    }

    @Test
    public void testEqPrimitiveCharacter() {

        QueryFilter qf = new QueryFilter();
        qf.setField("primitiveCharacter");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("z");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(1, documents.size());
        Assert.assertEquals('z', documents.get(0).getPrimitiveCharacter());
    }

    @Test
    public void testEqInvalidPrimitiveCharacter() {

        QueryFilter qf = new QueryFilter();
        qf.setField("primitiveCharacter");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("zzzzzzz");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(1, documents.size());
        Assert.assertEquals('z', documents.get(0).getPrimitiveCharacter());
    }

    @Test
    public void testEqObjectCharacterArray() {

        QueryFilter qf = new QueryFilter();
        qf.setField("objectCharacterArray");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("Quimm");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(1, documents.size());
        Assert.assertNotNull(documents.get(0).getObjectCharacterArray());
        Assert.assertArrayEquals("Quimm".chars().mapToObj(c -> (char)c).toArray(Character[]::new),
                documents.get(0).getObjectCharacterArray());
    }

    @Test
    public void testEqPrimitiveCharacterArray() {

        QueryFilter qf = new QueryFilter();
        qf.setField("primitiveCharacterArray");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("unicef.org");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(1, documents.size());
        Assert.assertNotNull(documents.get(0).getObjectCharacterArray());
        Assert.assertArrayEquals("unicef.org".toCharArray(), documents.get(0).getPrimitiveCharacterArray());
    }

    @Test
    public void testEqUtilDate() {

        String value = "2017-05-07T03:06:47Z";

        Date date = Date.from(Instant.parse(value));

        QueryFilter qf = new QueryFilter();
        qf.setField("utilDate");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue(value);

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(1, documents.size());
        Assert.assertNotNull(documents.get(0).getUtilDate());
        Assert.assertEquals(date, documents.get(0).getUtilDate());
    }

    @Test(expected = InvalidFieldValueException.class)
    public void testEqInvalidUtilDate() {

        QueryFilter qf = new QueryFilter();
        qf.setField("utilDate");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("INVALID");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        JPAUtils.queryEntities(em, DocumentEntity.class, q);
        Assert.fail("No exception was thrown");
    }

    @Test
    public void testEqUtilCalendar() {

        QueryFilter qf = new QueryFilter();
        qf.setField("utilCalendar");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("2017-06-27T11:40:53Z");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(0, documents.size());
    }

    @Test(expected = InvalidFieldValueException.class)
    public void testEqInvalidUtilCalendar() {

        QueryFilter qf = new QueryFilter();
        qf.setField("utilCalendar");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("INVALID");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        JPAUtils.queryEntities(em, DocumentEntity.class, q);
        Assert.fail("No exception was thrown");
    }

    @Test
    public void testEqGregorianCalendar() {

        QueryFilter qf = new QueryFilter();
        qf.setField("gregorianCalendar");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("2017-04-22T11:22:57Z");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(0, documents.size());
    }

    @Test(expected = InvalidFieldValueException.class)
    public void testEqInvalidGregorianCalendar() {

        QueryFilter qf = new QueryFilter();
        qf.setField("gregorianCalendar");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("INVALID");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        JPAUtils.queryEntities(em, DocumentEntity.class, q);
        Assert.fail("No exception was thrown");
    }

    @Test
    public void testEqSqlDate() {

        String value = "2017-09-04";

        java.sql.Date date = java.sql.Date.valueOf(LocalDate.parse(value));

        QueryFilter qf = new QueryFilter();
        qf.setField("sqlDate");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue(value);

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(1, documents.size());
        Assert.assertNotNull(documents.get(0).getSqlDate());
        Assert.assertEquals(date, documents.get(0).getSqlDate());
    }

    @Test(expected = InvalidFieldValueException.class)
    public void testEqInvalidSqlDate() {

        QueryFilter qf = new QueryFilter();
        qf.setField("sqlDate");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("INVALID");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        JPAUtils.queryEntities(em, DocumentEntity.class, q);
        Assert.fail("No exception was thrown");
    }

    @Test
    public void testEqSqlTime() {

        Time time = Time.valueOf(LocalTime.parse("16:39:01.000"));

        QueryFilter qf = new QueryFilter();
        qf.setField("sqlTime");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("16:39:01.000");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(1, documents.size());
        Assert.assertNotNull(documents.get(0).getSqlTime());
        Assert.assertEquals(time, documents.get(0).getSqlTime());
    }

    @Test(expected = InvalidFieldValueException.class)
    public void testEqInvalidSqlTime() {

        QueryFilter qf = new QueryFilter();
        qf.setField("sqlTime");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("INVALID");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        JPAUtils.queryEntities(em, DocumentEntity.class, q);
        Assert.fail("No exception was thrown");
    }

    @Test
    public void testEqSqlTimestamp() {

        Timestamp timestamp = Timestamp.from(Instant.parse("2017-08-20T12:12:53Z"));

        QueryFilter qf = new QueryFilter();
        qf.setField("sqlTimestamp");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("2017-08-20T12:12:53Z");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(1, documents.size());
        Assert.assertNotNull(documents.get(0).getSqlTimestamp());
        Assert.assertEquals(timestamp, documents.get(0).getSqlTimestamp());
    }

    @Test(expected = InvalidFieldValueException.class)
    public void testEqInvalidSqlTimestamp() {

        QueryFilter qf = new QueryFilter();
        qf.setField("sqlTimestamp");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("INVALID");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        JPAUtils.queryEntities(em, DocumentEntity.class, q);
        Assert.fail("No exception was thrown");
    }

    @Test
    public void testEqLocalDate() {

        LocalDate date = LocalDate.parse("2018-03-18");

        QueryFilter qf = new QueryFilter();
        qf.setField("localDate");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("2018-03-18");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(1, documents.size());
        Assert.assertNotNull(documents.get(0).getLocalDate());
        Assert.assertEquals(date, documents.get(0).getLocalDate());
    }

    @Test(expected = InvalidFieldValueException.class)
    public void testEqInvalidLocalDate() {

        QueryFilter qf = new QueryFilter();
        qf.setField("localDate");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("INVALID");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        JPAUtils.queryEntities(em, DocumentEntity.class, q);
        Assert.fail("No exception was thrown");
    }

    @Test
    public void testEqLocalTime() {

        String value = "07:49:03";

        LocalTime time = LocalTime.parse(value);

        QueryFilter qf = new QueryFilter();
        qf.setField("localTime");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue(value);

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(1, documents.size());
        Assert.assertNotNull(documents.get(0).getLocalTime());
        Assert.assertEquals(time, documents.get(0).getLocalTime());
    }

    @Test(expected = InvalidFieldValueException.class)
    public void testEqInvalidLocalTime() {

        QueryFilter qf = new QueryFilter();
        qf.setField("localTime");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("INVALID");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        JPAUtils.queryEntities(em, DocumentEntity.class, q);
        Assert.fail("No exception was thrown");
    }

    @Test
    public void testEqLocalDateTime() {

        String value = "2017-09-25T12:38:19";

        QueryFilter qf = new QueryFilter();
        qf.setField("localDateTime");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue(value);

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(0, documents.size());
    }

    @Test(expected = InvalidFieldValueException.class)
    public void testEqInvalidLocalDateTime() {

        QueryFilter qf = new QueryFilter();
        qf.setField("localDateTime");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("INVALID");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        JPAUtils.queryEntities(em, DocumentEntity.class, q);
        Assert.fail("No exception was thrown");
    }

    @Test
    public void testEqOffsetTime() {

        String value = "13:14:18+00:00";

        OffsetTime time = OffsetTime.parse(value);

        QueryFilter qf = new QueryFilter();
        qf.setField("offsetTime");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue(value);

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(1, documents.size());
        Assert.assertNotNull(documents.get(0).getOffsetTime());
        Assert.assertEquals(time, documents.get(0).getOffsetTime());
    }

    @Test(expected = InvalidFieldValueException.class)
    public void testEqInvalidOffsetTime() {

        QueryFilter qf = new QueryFilter();
        qf.setField("offsetTime");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("INVALID");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        JPAUtils.queryEntities(em, DocumentEntity.class, q);
        Assert.fail("No exception was thrown");
    }

    @Test
    public void testEqOffsetDateTime() {

        String value = "2017-11-04T04:19:27Z";

        OffsetDateTime dateTime = OffsetDateTime.parse(value);

        QueryFilter qf = new QueryFilter();
        qf.setField("offsetDateTime");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue(value);

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(1, documents.size());
        Assert.assertNotNull(documents.get(0).getOffsetDateTime());
        Assert.assertEquals(dateTime, documents.get(0).getOffsetDateTime());
    }

    @Test(expected = InvalidFieldValueException.class)
    public void testEqInvalidOffsetDateTIme() {

        QueryFilter qf = new QueryFilter();
        qf.setField("offsetDateTime");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("INVALID");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        JPAUtils.queryEntities(em, DocumentEntity.class, q);
        Assert.fail("No exception was thrown");
    }

    @Test
    public void testEqInstant() {

        String value = "2017-10-08T20:37:35Z";

        Instant instant = Instant.parse(value);

        QueryFilter qf = new QueryFilter();
        qf.setField("instant");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue(value);

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(1, documents.size());
        Assert.assertNotNull(documents.get(0).getInstant());
        Assert.assertEquals(instant, documents.get(0).getInstant());
    }

    @Test(expected = InvalidFieldValueException.class)
    public void testEqInvalidInstant() {

        QueryFilter qf = new QueryFilter();
        qf.setField("instant");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("INVALID");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        JPAUtils.queryEntities(em, DocumentEntity.class, q);
        Assert.fail("No exception was thrown");
    }

    @Test
    public void testEqDuration() {

        String value = "PT9.892S";

        Duration duration = Duration.parse(value);

        QueryFilter qf = new QueryFilter();
        qf.setField("duration");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue(value);

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(1, documents.size());
        Assert.assertNotNull(documents.get(0).getDuration());
        Assert.assertEquals(duration, documents.get(0).getDuration());
    }

    @Test(expected = InvalidFieldValueException.class)
    public void testEqInvalidDuration() {

        QueryFilter qf = new QueryFilter();
        qf.setField("duration");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("INVALID");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        JPAUtils.queryEntities(em, DocumentEntity.class, q);
        Assert.fail("No exception was thrown");
    }

    @Test
    public void testEqUuid() {

        String value = "71e33b56-e5e9-4e61-abbc-fff810a4b4eb";

        UUID uuid = UUID.fromString(value);

        QueryFilter qf = new QueryFilter();
        qf.setField("uuid");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue(value);

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(1, documents.size());
        Assert.assertNotNull(documents.get(0).getUuid());
        Assert.assertEquals(uuid, documents.get(0).getUuid());
    }

    @Test(expected = InvalidFieldValueException.class)
    public void testEqInvalidUuid() {

        QueryFilter qf = new QueryFilter();
        qf.setField("uuid");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("INVALID");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        JPAUtils.queryEntities(em, DocumentEntity.class, q);
        Assert.fail("No exception was thrown");
    }

    @Test
    public void testEqEnum() {

        QueryFilter qf = new QueryFilter();
        qf.setField("objectEnum");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("FIRST");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(25, documents.size());
        Assert.assertNotNull(documents.get(0).getObjectEnum());
        Assert.assertEquals(DocumentEnum.FIRST, documents.get(0).getObjectEnum());
    }

    @Test(expected = InvalidFieldValueException.class)
    public void testEqInvalidEnum() {

        QueryFilter qf = new QueryFilter();
        qf.setField("objectEnum");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("INVALID");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        JPAUtils.queryEntities(em, DocumentEntity.class, q);
        Assert.fail("No exception was thrown");
    }

    @Test(expected = RuntimeException.class)
    public void testEqUnsupportedObject() {

        QueryFilter qf = new QueryFilter();
        qf.setField("customObject");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("test");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        JPAUtils.queryEntities(em, DocumentEntity.class, q);
        Assert.fail("No exception was thrown");
    }

    // EQIC

    @Test
    public void testEqIcString() {

        QueryFilter qf = new QueryFilter();
        qf.setField("string");
        qf.setOperation(FilterOperation.EQIC);
        qf.setValue("carOL");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(1, documents.size());
        Assert.assertNotNull(documents.get(0).getString());
        Assert.assertEquals("Carol", documents.get(0).getString());
    }

    @Test
    public void testEqIcObjectCharacter() {

        QueryFilter qf = new QueryFilter();
        qf.setField("objectCharacter");
        qf.setOperation(FilterOperation.EQIC);
        qf.setValue("B");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(1, documents.size());
        Assert.assertNotNull(documents.get(0).getObjectCharacter());
        Assert.assertEquals('b', documents.get(0).getObjectCharacter().charValue());
    }

    @Test
    public void testEqIcPrimitiveCharacter() {

        QueryFilter qf = new QueryFilter();
        qf.setField("primitiveCharacter");
        qf.setOperation(FilterOperation.EQIC);
        qf.setValue("B");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(4, documents.size());
        Assert.assertEquals('b', documents.get(0).getPrimitiveCharacter());
    }

    @Test
    public void testEqIcObjectCharacterArray() {

        QueryFilter qf = new QueryFilter();
        qf.setField("objectCharacterArray");
        qf.setOperation(FilterOperation.EQIC);
        qf.setValue("kwiLITh");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(1, documents.size());
        Assert.assertNotNull(documents.get(0).getObjectCharacterArray());
        Assert.assertArrayEquals("Kwilith".chars().mapToObj(c -> (char) c).toArray(Character[]::new),
                documents.get(0).getObjectCharacterArray());
    }

    @Test
    public void testEqIcPrimitiveCharacterArray() {

        QueryFilter qf = new QueryFilter();
        qf.setField("primitiveCharacterArray");
        qf.setOperation(FilterOperation.EQIC);
        qf.setValue("YAHOO.co.jp");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(1, documents.size());
        Assert.assertArrayEquals("yahoo.co.jp".toCharArray(), documents.get(0).getPrimitiveCharacterArray());
    }

    // NEQ

    @Test
    public void testNeqString() {

        QueryFilter qf = new QueryFilter();
        qf.setField("string");
        qf.setOperation(FilterOperation.NEQ);
        qf.setValue("Gerty");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(49, documents.size());
        Assert.assertEquals("Felice", documents.get(0).getString());
    }

    // NEQIC

    @Test
    public void testNeqIcString() {

        QueryFilter qf = new QueryFilter();
        qf.setField("string");
        qf.setOperation(FilterOperation.NEQIC);
        qf.setValue("gERTY");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(49, documents.size());
        Assert.assertEquals("Felice", documents.get(0).getString());
    }

    // LIKE

    @Test
    public void testLikeString() {

        QueryFilter qf = new QueryFilter();
        qf.setField("string");
        qf.setOperation(FilterOperation.LIKE);
        qf.setValue("Carol%");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(2, documents.size());
        Assert.assertEquals("Carolina", documents.get(0).getString());
    }

    // LIKEIC

    @Test
    public void testLikeIcString() {

        QueryFilter qf = new QueryFilter();
        qf.setField("string");
        qf.setOperation(FilterOperation.LIKEIC);
        qf.setValue("cAROl%");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(2, documents.size());
        Assert.assertEquals("Carolina", documents.get(0).getString());
    }

    // GT

    @Test
    public void testGtString() {

        QueryFilter qf = new QueryFilter();
        qf.setField("string");
        qf.setOperation(FilterOperation.GT);
        qf.setValue("Caren");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(42, documents.size());
        Assert.assertNotNull(documents.get(0).getString());
        Assert.assertTrue("Caren".compareTo(documents.get(0).getString()) < 0);
    }

    @Test
    public void testGtObjectInteger() {

        QueryFilter qf = new QueryFilter();
        qf.setField("objectInteger");
        qf.setOperation(FilterOperation.GT);
        qf.setValue("60");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(18, documents.size());
        Assert.assertNotNull(documents.get(0).getObjectInteger());
        Assert.assertTrue(60 < documents.get(0).getObjectInteger());
    }

    @Test
    public void testGtPrimitiveInteger() {

        QueryFilter qf = new QueryFilter();
        qf.setField("primitiveInteger");
        qf.setOperation(FilterOperation.GT);
        qf.setValue("56");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(25, documents.size());
        Assert.assertTrue(56 < documents.get(0).getPrimitiveInteger());
    }

    @Test
    public void testGtObjectLong() {

        QueryFilter qf = new QueryFilter();
        qf.setField("objectLong");
        qf.setOperation(FilterOperation.GT);
        qf.setValue("60");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(22, documents.size());
        Assert.assertNotNull(documents.get(0).getObjectLong());
        Assert.assertTrue(60L < documents.get(0).getObjectLong());
    }

    @Test
    public void testGtPrimitiveLong() {

        QueryFilter qf = new QueryFilter();
        qf.setField("primitiveLong");
        qf.setOperation(FilterOperation.GT);
        qf.setValue("25");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(31, documents.size());
        Assert.assertTrue(25L < documents.get(0).getPrimitiveLong());
    }

    @Test
    public void testGtObjectShort() {

        QueryFilter qf = new QueryFilter();
        qf.setField("objectShort");
        qf.setOperation(FilterOperation.GT);
        qf.setValue("60");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(20, documents.size());
        Assert.assertNotNull(documents.get(0).getObjectShort());
        Assert.assertTrue(60 < documents.get(0).getObjectShort());
    }

    @Test
    public void testGtPrimitiveShort() {

        QueryFilter qf = new QueryFilter();
        qf.setField("primitiveShort");
        qf.setOperation(FilterOperation.GT);
        qf.setValue("75");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(12, documents.size());
        Assert.assertTrue(75 < documents.get(0).getPrimitiveShort());
    }

    @Test
    public void testGtObjectDouble() {

        QueryFilter qf = new QueryFilter();
        qf.setField("objectDouble");
        qf.setOperation(FilterOperation.GT);
        qf.setValue("82.47");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(8, documents.size());
        Assert.assertNotNull(documents.get(0).getObjectDouble());
        Assert.assertTrue(82.47 < documents.get(0).getObjectDouble());
    }

    @Test
    public void testGtPrimitiveDouble() {

        QueryFilter qf = new QueryFilter();
        qf.setField("primitiveDouble");
        qf.setOperation(FilterOperation.GT);
        qf.setValue("60.35");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(18, documents.size());
        Assert.assertTrue(60.35 < documents.get(0).getPrimitiveDouble());
    }

    @Test
    public void testGtObjectFloat() {

        QueryFilter qf = new QueryFilter();
        qf.setField("objectFloat");
        qf.setOperation(FilterOperation.GT);
        qf.setValue("94.5");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(3, documents.size());
        Assert.assertNotNull(documents.get(0).getObjectFloat());
        Assert.assertTrue(94.5f < documents.get(0).getObjectFloat());
    }

    @Test
    public void testGtPrimitiveFloat() {

        QueryFilter qf = new QueryFilter();
        qf.setField("primitiveFloat");
        qf.setOperation(FilterOperation.GT);
        qf.setValue("48.5");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(28, documents.size());
        Assert.assertTrue(48.5f < documents.get(0).getPrimitiveFloat());
    }

    @Test
    public void testGtBigDecimal() {

        QueryFilter qf = new QueryFilter();
        qf.setField("bigDecimal");
        qf.setOperation(FilterOperation.GT);
        qf.setValue("93.61");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(4, documents.size());
        Assert.assertNotNull(documents.get(0).getBigDecimal());
        Assert.assertTrue(new BigDecimal("93.61").compareTo(documents.get(0).getBigDecimal()) < 0);
    }

    @Test
    public void testGtBigInteger() {

        QueryFilter qf = new QueryFilter();
        qf.setField("bigInteger");
        qf.setOperation(FilterOperation.GT);
        qf.setValue("87");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(7, documents.size());
        Assert.assertNotNull(documents.get(0).getBigInteger());
        Assert.assertTrue(new BigInteger("87").compareTo(documents.get(0).getBigInteger()) < 0);
    }

    @Test
    public void testGtObjectBoolean() {

        QueryFilter qf = new QueryFilter();
        qf.setField("objectBoolean");
        qf.setOperation(FilterOperation.GT);
        qf.setValue("false");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(28, documents.size());
        Assert.assertNotNull(documents.get(0).getObjectBoolean());
        Assert.assertTrue(documents.get(0).getObjectBoolean());
    }

    @Test
    public void testGtPrimitiveBoolean() {

        QueryFilter qf = new QueryFilter();
        qf.setField("primitiveBoolean");
        qf.setOperation(FilterOperation.GT);
        qf.setValue("false");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(24, documents.size());
        Assert.assertTrue(documents.get(0).isPrimitiveBoolean());
    }

    @Test
    public void testGtObjectCharacter() {

        QueryFilter qf = new QueryFilter();
        qf.setField("objectCharacter");
        qf.setOperation(FilterOperation.GT);
        qf.setValue("h");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(38, documents.size());
        Assert.assertNotNull(documents.get(0).getObjectCharacter());
        Assert.assertTrue('h' < documents.get(0).getObjectCharacter());
    }

    @Test
    public void testGtPrimitiveCharacter() {

        QueryFilter qf = new QueryFilter();
        qf.setField("primitiveCharacter");
        qf.setOperation(FilterOperation.GT);
        qf.setValue("h");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(33, documents.size());
        Assert.assertTrue('h' < documents.get(0).getPrimitiveCharacter());
    }

    @Test
    public void testGtObjectCharacterArray() {

        QueryFilter qf = new QueryFilter();
        qf.setField("objectCharacterArray");
        qf.setOperation(FilterOperation.GT);
        qf.setValue("Quimm");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(50, documents.size());
    }

    @Test
    public void testGtPrimitiveCharacterArray() {

        QueryFilter qf = new QueryFilter();
        qf.setField("primitiveCharacterArray");
        qf.setOperation(FilterOperation.GT);
        qf.setValue("unicef.org");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(50, documents.size());
    }

    @Test
    public void testGtUtilDate() {

        String value = "2017-11-07T03:06:47Z";

        Instant instant = Instant.parse(value);

        QueryFilter qf = new QueryFilter();
        qf.setField("utilDate");
        qf.setOperation(FilterOperation.GT);
        qf.setValue(value);

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(26, documents.size());
        Assert.assertNotNull(documents.get(0).getUtilDate());
        Assert.assertTrue(instant.isBefore(documents.get(0).getUtilDate().toInstant()));
    }

    @Test
    public void testGtUtilCalendar() {

        String value = "2018-01-27T11:22:57Z";

        Instant instant = Instant.parse(value);

        QueryFilter qf = new QueryFilter();
        qf.setField("utilCalendar");
        qf.setOperation(FilterOperation.GT);
        qf.setValue(value);

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(10, documents.size());
        Assert.assertNotNull(documents.get(0).getUtilCalendar());
        Assert.assertTrue(instant.isBefore(documents.get(0).getUtilCalendar().toInstant()));
    }

    @Test
    public void testGtGregorianCalendar() {

        String value = "2018-01-27T11:22:57Z";

        Instant instant = Instant.parse(value);

        QueryFilter qf = new QueryFilter();
        qf.setField("gregorianCalendar");
        qf.setOperation(FilterOperation.GT);
        qf.setValue(value);

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(6, documents.size());
        Assert.assertNotNull(documents.get(0).getGregorianCalendar());
        Assert.assertTrue(instant.isBefore(documents.get(0).getGregorianCalendar().toInstant()));
    }

    @Test
    public void testGtSqlDate() {

        String value = "2017-09-04";

        LocalDate date = LocalDate.parse(value);

        QueryFilter qf = new QueryFilter();
        qf.setField("sqlDate");
        qf.setOperation(FilterOperation.GT);
        qf.setValue(value);

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(28, documents.size());
        Assert.assertNotNull(documents.get(0).getSqlDate());
        Assert.assertTrue(date.isBefore(documents.get(0).getSqlDate().toLocalDate()));
    }

    @Test
    public void testGtSqlTime() {

        LocalTime time = LocalTime.parse("16:39:01.000");

        QueryFilter qf = new QueryFilter();
        qf.setField("sqlTime");
        qf.setOperation(FilterOperation.GT);
        qf.setValue("16:39:01.000");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(9, documents.size());
        Assert.assertNotNull(documents.get(0).getSqlTime());
        Assert.assertTrue(time.isBefore(documents.get(0).getSqlTime().toLocalTime()));
    }

    @Test
    public void testGtSqlTimestamp() {

        Instant timestamp = Instant.parse("2017-08-20T12:12:53Z");

        QueryFilter qf = new QueryFilter();
        qf.setField("sqlTimestamp");
        qf.setOperation(FilterOperation.GT);
        qf.setValue("2017-08-20T12:12:53Z");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(32, documents.size());
        Assert.assertNotNull(documents.get(0).getSqlTimestamp());
        Assert.assertTrue(timestamp.isBefore(documents.get(0).getSqlTimestamp().toInstant()));
    }

    @Test
    public void testGtLocalDate() {

        String value = "2018-01-18";

        LocalDate date = LocalDate.parse(value);

        QueryFilter qf = new QueryFilter();
        qf.setField("localDate");
        qf.setOperation(FilterOperation.GT);
        qf.setValue(value);

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(7, documents.size());
        Assert.assertNotNull(documents.get(0).getLocalDate());
        Assert.assertTrue(date.isBefore(documents.get(0).getLocalDate()));
    }

    @Test
    public void testGtLocalTime() {

        String value = "07:49:03";

        LocalTime time = LocalTime.parse(value);

        QueryFilter qf = new QueryFilter();
        qf.setField("localTime");
        qf.setOperation(FilterOperation.GT);
        qf.setValue(value);

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(32, documents.size());
        Assert.assertNotNull(documents.get(0).getLocalTime());
        Assert.assertTrue(time.isBefore(documents.get(0).getLocalTime()));
    }

    @Test
    public void testGtLocalDateTime() {

        String value = "2017-09-21T12:38:19";

        LocalDateTime dateTime = LocalDateTime.parse(value);

        QueryFilter qf = new QueryFilter();
        qf.setField("localDateTime");
        qf.setOperation(FilterOperation.GT);
        qf.setValue(value);

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(32, documents.size());
        Assert.assertNotNull(documents.get(0).getLocalDateTime());
        Assert.assertTrue(dateTime.isBefore(documents.get(0).getLocalDateTime()));
    }

    @Test
    public void testGtOffsetTime() {

        String value = "13:14:18+00:00";

        OffsetTime time = OffsetTime.parse(value);

        QueryFilter qf = new QueryFilter();
        qf.setField("offsetTime");
        qf.setOperation(FilterOperation.GT);
        qf.setValue(value);

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(25, documents.size());
        Assert.assertNotNull(documents.get(0).getOffsetTime());
        Assert.assertTrue(time.isBefore(documents.get(0).getOffsetTime()));
    }

    @Test
    public void testGtOffsetDateTime() {

        String value = "2017-11-04T04:19:27Z";

        OffsetDateTime dateTime = OffsetDateTime.parse(value);

        QueryFilter qf = new QueryFilter();
        qf.setField("offsetDateTime");
        qf.setOperation(FilterOperation.GT);
        qf.setValue(value);

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(16, documents.size());
        Assert.assertNotNull(documents.get(0).getOffsetDateTime());
        Assert.assertTrue(dateTime.isBefore(documents.get(0).getOffsetDateTime()));
    }

    @Test
    public void testGtInstant() {

        String value = "2017-10-08T20:37:35Z";

        Instant instant = Instant.parse(value);

        QueryFilter qf = new QueryFilter();
        qf.setField("instant");
        qf.setOperation(FilterOperation.GT);
        qf.setValue(value);

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(24, documents.size());
        Assert.assertNotNull(documents.get(0).getInstant());
        Assert.assertTrue(instant.isBefore(documents.get(0).getInstant()));
    }

    @Test
    public void testGtDuration() {

        String value = "PT9S";

        Duration duration = Duration.parse(value);

        QueryFilter qf = new QueryFilter();
        qf.setField("duration");
        qf.setOperation(FilterOperation.GT);
        qf.setValue(value);

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(3, documents.size());
        Assert.assertNotNull(documents.get(0).getDuration());
        Assert.assertTrue(duration.compareTo(documents.get(0).getDuration()) < 0);
    }

    @Test
    public void testGtUuid() {

        String value = "71e33b56-e5e9-4e61-abbc-fff810a4b4eb";

        UUID uuid = UUID.fromString(value);

        QueryFilter qf = new QueryFilter();
        qf.setField("uuid");
        qf.setOperation(FilterOperation.GT);
        qf.setValue(value);

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(27, documents.size());
        Assert.assertNotNull(documents.get(0).getUuid());
        Assert.assertTrue(uuid.compareTo(documents.get(0).getUuid()) < 0);
    }

    @Test
    public void testGtEnum() {

        QueryFilter qf = new QueryFilter();
        qf.setField("objectEnum");
        qf.setOperation(FilterOperation.GT);
        qf.setValue("FIRST");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(24, documents.size());
        Assert.assertNotNull(documents.get(0).getObjectEnum());
        Assert.assertEquals(DocumentEnum.SECOND, documents.get(0).getObjectEnum());
    }

    @Test
    public void testGtUnsupportedObject() {

        QueryFilter qf = new QueryFilter();
        qf.setField("customObject");
        qf.setOperation(FilterOperation.GT);
        qf.setValue("test");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(50, documents.size());
    }

    // GTE

    @Test
    public void testGteInstant() {

        String value = "2017-08-31T06:41:20Z";

        Instant instant = Instant.parse(value);

        QueryFilter qf = new QueryFilter();
        qf.setField("instant");
        qf.setOperation(FilterOperation.GTE);
        qf.setValue(value);

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(29, documents.size());
        Assert.assertNotNull(documents.get(0).getInstant());
        Assert.assertTrue(instant.isBefore(documents.get(0).getInstant()) || instant.equals(documents.get(0).getInstant()));
    }

    // LT

    @Test
    public void testLtInstant() {

        String value = "2017-08-31T06:41:20Z";

        Instant instant = Instant.parse(value);

        QueryFilter qf = new QueryFilter();
        qf.setField("instant");
        qf.setOperation(FilterOperation.LT);
        qf.setValue(value);

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(21, documents.size());
        Assert.assertNotNull(documents.get(0).getInstant());
        Assert.assertTrue(instant.isAfter(documents.get(0).getInstant()));
    }

    // LTE

    @Test
    public void testLteInstant() {

        String value = "2017-08-31T06:41:20Z";

        Instant instant = Instant.parse(value);

        QueryFilter qf = new QueryFilter();
        qf.setField("instant");
        qf.setOperation(FilterOperation.LTE);
        qf.setValue(value);

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(22, documents.size());
        Assert.assertNotNull(documents.get(0).getInstant());
        Assert.assertTrue(instant.isAfter(documents.get(0).getInstant()) || instant.equals(documents.get(0).getInstant()));
    }

    // IN

    @Test
    public void testInSingleFilter() {

        QueryFilter qf = new QueryFilter();
        qf.setField("string");
        qf.setOperation(FilterOperation.IN);
        qf.getValues().add("Alanah");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(1, documents.size());
    }

    @Test
    public void testInMultipleFilter() {

        QueryFilter qf = new QueryFilter();
        qf.setField("string");
        qf.setOperation(FilterOperation.IN);
        qf.getValues().add("Alanah");
        qf.getValues().add("Penny");
        qf.getValues().add("Gav");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(4, documents.size());
    }

    @Test
    public void testInObjectInteger() {

        QueryFilter qf = new QueryFilter();
        qf.setField("objectInteger");
        qf.setOperation(FilterOperation.IN);
        qf.getValues().add("60");
        qf.getValues().add("70");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(3, documents.size());
        Assert.assertNotNull(documents.get(0).getObjectInteger());
        Assert.assertEquals(60, documents.get(0).getObjectInteger().intValue());
    }

    @Test
    public void testInPrimitiveInteger() {

        QueryFilter qf = new QueryFilter();
        qf.setField("primitiveInteger");
        qf.setOperation(FilterOperation.IN);
        qf.getValues().add("56");
        qf.getValues().add("12");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(3, documents.size());
        Assert.assertEquals(56, documents.get(0).getPrimitiveInteger());
    }

    @Test
    public void testInObjectLong() {

        QueryFilter qf = new QueryFilter();
        qf.setField("objectLong");
        qf.setOperation(FilterOperation.IN);
        qf.getValues().add("60");
        qf.getValues().add("28");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(3, documents.size());
        Assert.assertNotNull(documents.get(0).getObjectLong());
        Assert.assertEquals(28L, documents.get(0).getObjectLong().longValue());
    }

    @Test
    public void testInPrimitiveLong() {

        QueryFilter qf = new QueryFilter();
        qf.setField("primitiveLong");
        qf.setOperation(FilterOperation.IN);
        qf.getValues().add("25");
        qf.getValues().add("56");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(3, documents.size());
        Assert.assertEquals(25L, documents.get(0).getPrimitiveLong());
    }

    @Test
    public void testInObjectShort() {

        QueryFilter qf = new QueryFilter();
        qf.setField("objectShort");
        qf.setOperation(FilterOperation.IN);
        qf.getValues().add("60");
        qf.getValues().add("9");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(3, documents.size());
        Assert.assertNotNull(documents.get(0).getObjectShort());
        Assert.assertEquals(60, documents.get(0).getObjectShort().shortValue());
    }

    @Test
    public void testInPrimitiveShort() {

        QueryFilter qf = new QueryFilter();
        qf.setField("primitiveShort");
        qf.setOperation(FilterOperation.IN);
        qf.getValues().add("75");
        qf.getValues().add("67");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(2, documents.size());
        Assert.assertEquals(75, documents.get(0).getPrimitiveShort());
    }

    @Test
    public void testInObjectDouble() {

        QueryFilter qf = new QueryFilter();
        qf.setField("objectDouble");
        qf.setOperation(FilterOperation.IN);
        qf.getValues().add("82.47");
        qf.getValues().add("8.5");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(2, documents.size());
        Assert.assertNotNull(documents.get(0).getObjectDouble());
        Assert.assertEquals(82.47, documents.get(0).getObjectDouble(), 0.1);
    }

    @Test
    public void testInPrimitiveDouble() {

        QueryFilter qf = new QueryFilter();
        qf.setField("primitiveDouble");
        qf.setOperation(FilterOperation.IN);
        qf.getValues().add("60.35");
        qf.getValues().add("29.5");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(2, documents.size());
        Assert.assertEquals(60.35, documents.get(0).getPrimitiveDouble(), 0.1);
    }

    @Test
    public void testInObjectFloat() {

        QueryFilter qf = new QueryFilter();
        qf.setField("objectFloat");
        qf.setOperation(FilterOperation.IN);
        qf.getValues().add("94.5");
        qf.getValues().add("63.5");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(2, documents.size());
        Assert.assertNotNull(documents.get(0).getObjectFloat());
        Assert.assertEquals(94.5f, documents.get(0).getObjectFloat(), 0.1f);
    }

    @Test
    public void testInPrimitiveFloat() {

        QueryFilter qf = new QueryFilter();
        qf.setField("primitiveFloat");
        qf.setOperation(FilterOperation.IN);
        qf.getValues().add("48.5");
        qf.getValues().add("88.5");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(2, documents.size());
        Assert.assertEquals(48.5f, documents.get(0).getPrimitiveFloat(), 0.1f);
    }

    @Test
    public void testInBigDecimal() {

        QueryFilter qf = new QueryFilter();
        qf.setField("bigDecimal");
        qf.setOperation(FilterOperation.IN);
        qf.getValues().add("93.61");
        qf.getValues().add("8.44");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(2, documents.size());
        Assert.assertNotNull(documents.get(0).getBigDecimal());
        Assert.assertEquals(new BigDecimal("93.61"), documents.get(0).getBigDecimal());
    }

    @Test
    public void testInBigInteger() {

        QueryFilter qf = new QueryFilter();
        qf.setField("bigInteger");
        qf.setOperation(FilterOperation.IN);
        qf.getValues().add("87");
        qf.getValues().add("93");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(6, documents.size());
        Assert.assertNotNull(documents.get(0).getBigInteger());
        Assert.assertEquals(new BigInteger("93"), documents.get(0).getBigInteger());
    }

    @Test
    public void testInObjectBoolean() {

        QueryFilter qf = new QueryFilter();
        qf.setField("objectBoolean");
        qf.setOperation(FilterOperation.IN);
        qf.getValues().add("true");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(28, documents.size());
        Assert.assertNotNull(documents.get(0).getObjectBoolean());
        Assert.assertTrue(documents.get(0).getObjectBoolean());
    }

    @Test
    public void testInPrimitiveBoolean() {

        QueryFilter qf = new QueryFilter();
        qf.setField("primitiveBoolean");
        qf.setOperation(FilterOperation.IN);
        qf.getValues().add("true");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(24, documents.size());
        Assert.assertTrue(documents.get(0).isPrimitiveBoolean());
    }

    @Test
    public void testInObjectCharacter() {

        QueryFilter qf = new QueryFilter();
        qf.setField("objectCharacter");
        qf.setOperation(FilterOperation.IN);
        qf.getValues().add("z");
        qf.getValues().add("h");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(8, documents.size());
        Assert.assertNotNull(documents.get(0).getObjectCharacter());
        Assert.assertEquals('h', documents.get(0).getObjectCharacter().charValue());
    }

    @Test
    public void testInPrimitiveCharacter() {

        QueryFilter qf = new QueryFilter();
        qf.setField("primitiveCharacter");
        qf.setOperation(FilterOperation.IN);
        qf.getValues().add("z");
        qf.getValues().add("h");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(4, documents.size());
        Assert.assertEquals('h', documents.get(0).getPrimitiveCharacter());
    }

    @Test
    public void testInObjectCharacterArray() {

        QueryFilter qf = new QueryFilter();
        qf.setField("objectCharacterArray");
        qf.setOperation(FilterOperation.IN);
        qf.getValues().add("Quimm");
        qf.getValues().add("Kwilith");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(2, documents.size());
        Assert.assertNotNull(documents.get(0).getObjectCharacterArray());
        Assert.assertArrayEquals("Quimm".chars().mapToObj(c -> (char)c).toArray(Character[]::new),
                documents.get(0).getObjectCharacterArray());
    }

    @Test
    public void testInPrimitiveCharacterArray() {

        QueryFilter qf = new QueryFilter();
        qf.setField("primitiveCharacterArray");
        qf.setOperation(FilterOperation.IN);
        qf.getValues().add("unicef.org");
        qf.getValues().add("bizjournals.com");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(2, documents.size());
        Assert.assertNotNull(documents.get(0).getObjectCharacterArray());
        Assert.assertArrayEquals("bizjournals.com".toCharArray(), documents.get(0).getPrimitiveCharacterArray());
    }

    @Test
    public void testInUtilDate() {

        String value = "2017-05-07T03:06:47Z";

        Date date = Date.from(Instant.parse(value));

        QueryFilter qf = new QueryFilter();
        qf.setField("utilDate");
        qf.setOperation(FilterOperation.IN);
        qf.getValues().add(value);
        qf.getValues().add("2018-03-19T17:07:35Z");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(2, documents.size());
        Assert.assertNotNull(documents.get(0).getUtilDate());
        Assert.assertEquals(date, documents.get(0).getUtilDate());
    }

    @Test
    public void testInUtilCalendar() {

        QueryFilter qf = new QueryFilter();
        qf.setField("utilCalendar");
        qf.setOperation(FilterOperation.IN);
        qf.getValues().add("2017-06-27T11:40:53Z");
        qf.getValues().add("2017-12-30T19:28:07Z");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(0, documents.size());
    }

    @Test
    public void testInGregorianCalendar() {

        QueryFilter qf = new QueryFilter();
        qf.setField("gregorianCalendar");
        qf.setOperation(FilterOperation.IN);
        qf.getValues().add("2017-04-22T11:22:57Z");
        qf.getValues().add("2017-07-02T14:25:11Z");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(0, documents.size());
    }

    @Test
    public void testInSqlDate() {

        String value = "2017-09-04";

        java.sql.Date date = java.sql.Date.valueOf(LocalDate.parse(value));

        QueryFilter qf = new QueryFilter();
        qf.setField("sqlDate");
        qf.setOperation(FilterOperation.IN);
        qf.getValues().add(value);
        qf.getValues().add("2017-06-14");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(2, documents.size());
        Assert.assertNotNull(documents.get(0).getSqlDate());
        Assert.assertEquals(date, documents.get(0).getSqlDate());
    }

    @Test
    public void testInSqlTime() {

        Time time = Time.valueOf(LocalTime.parse("16:39:01.000"));

        QueryFilter qf = new QueryFilter();
        qf.setField("sqlTime");
        qf.setOperation(FilterOperation.IN);
        qf.getValues().add("16:39:01.000");
        qf.getValues().add("05:52:05.000");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(2, documents.size());
        Assert.assertNotNull(documents.get(0).getSqlTime());
        Assert.assertEquals(time, documents.get(0).getSqlTime());
    }

    @Test
    public void testInSqlTimestamp() {

        Timestamp timestamp = Timestamp.from(Instant.parse("2017-08-20T12:12:53Z"));

        QueryFilter qf = new QueryFilter();
        qf.setField("sqlTimestamp");
        qf.setOperation(FilterOperation.IN);
        qf.getValues().add("2017-08-20T12:12:53Z");
        qf.getValues().add("2018-01-05T11:12:25Z");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(2, documents.size());
        Assert.assertNotNull(documents.get(0).getSqlTimestamp());
        Assert.assertEquals(timestamp, documents.get(0).getSqlTimestamp());
    }

    @Test
    public void testInLocalDate() {

        LocalDate date = LocalDate.parse("2018-03-18");

        QueryFilter qf = new QueryFilter();
        qf.setField("localDate");
        qf.setOperation(FilterOperation.IN);
        qf.getValues().add("2018-03-18");
        qf.getValues().add("2017-11-15");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(3, documents.size());
        Assert.assertNotNull(documents.get(0).getLocalDate());
        Assert.assertEquals(date, documents.get(0).getLocalDate());
    }

    @Test
    public void testInLocalTime() {

        String value = "07:49:03";

        LocalTime time = LocalTime.parse(value);

        QueryFilter qf = new QueryFilter();
        qf.setField("localTime");
        qf.setOperation(FilterOperation.IN);
        qf.getValues().add(value);
        qf.getValues().add("14:17:12.000");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(2, documents.size());
        Assert.assertNotNull(documents.get(0).getLocalTime());
        Assert.assertEquals(time, documents.get(0).getLocalTime());
    }

    @Test
    public void testInLocalDateTime() {

        String value = "2017-09-25T12:38:19";

        QueryFilter qf = new QueryFilter();
        qf.setField("localDateTime");
        qf.setOperation(FilterOperation.IN);
        qf.getValues().add(value);
        qf.getValues().add("2017-06-05T20:54:17");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(0, documents.size());
    }

    @Test
    public void testInOffsetTime() {

        String value = "13:14:18+00:00";

        OffsetTime time = OffsetTime.parse(value);

        QueryFilter qf = new QueryFilter();
        qf.setField("offsetTime");
        qf.setOperation(FilterOperation.IN);
        qf.getValues().add(value);
        qf.getValues().add("14:45:36.000+00:00");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(2, documents.size());
        Assert.assertNotNull(documents.get(0).getOffsetTime());
        Assert.assertEquals(time, documents.get(0).getOffsetTime());
    }

    @Test
    public void testInOffsetDateTime() {

        String value = "2017-11-04T04:19:27Z";

        OffsetDateTime dateTime = OffsetDateTime.parse(value);

        QueryFilter qf = new QueryFilter();
        qf.setField("offsetDateTime");
        qf.setOperation(FilterOperation.IN);
        qf.getValues().add(value);
        qf.getValues().add("2017-09-17T07:20:31Z");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(2, documents.size());
        Assert.assertNotNull(documents.get(0).getOffsetDateTime());
        Assert.assertEquals(dateTime, documents.get(0).getOffsetDateTime());
    }

    @Test
    public void testInInstant() {

        String value = "2017-10-08T20:37:35Z";

        Instant instant = Instant.parse(value);

        QueryFilter qf = new QueryFilter();
        qf.setField("instant");
        qf.setOperation(FilterOperation.IN);
        qf.getValues().add(value);
        qf.getValues().add("2017-06-28T13:13:04Z");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(2, documents.size());
        Assert.assertNotNull(documents.get(0).getInstant());
        Assert.assertEquals(instant, documents.get(0).getInstant());
    }

    @Test
    public void testInDuration() {

        String value = "PT9.892S";

        Duration duration = Duration.parse(value);

        QueryFilter qf = new QueryFilter();
        qf.setField("duration");
        qf.setOperation(FilterOperation.IN);
        qf.getValues().add(value);
        qf.getValues().add("PT6.731S");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(2, documents.size());
        Assert.assertNotNull(documents.get(0).getDuration());
        Assert.assertEquals(duration, documents.get(0).getDuration());
    }

    @Test
    public void testInUuid() {

        String value1 = "71e33b56-e5e9-4e61-abbc-fff810a4b4eb";
        String value2 = "bf74e688-39fd-46c9-9fb8-8a7a09742239";

        UUID uuid2 = UUID.fromString(value2);

        QueryFilter qf = new QueryFilter();
        qf.setField("uuid");
        qf.setOperation(FilterOperation.IN);
        qf.getValues().add(value1);
        qf.getValues().add(value2);

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(2, documents.size());
        Assert.assertNotNull(documents.get(0).getUuid());
        Assert.assertEquals(uuid2, documents.get(0).getUuid());
    }

    @Test
    public void testInEnum() {

        QueryFilter qf = new QueryFilter();
        qf.setField("objectEnum");
        qf.setOperation(FilterOperation.IN);
        qf.getValues().add("FIRST");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(25, documents.size());
        Assert.assertNotNull(documents.get(0).getObjectEnum());
        Assert.assertEquals(DocumentEnum.FIRST, documents.get(0).getObjectEnum());
    }

    @Test(expected = RuntimeException.class)
    public void testInUnsupportedObject() {

        QueryFilter qf = new QueryFilter();
        qf.setField("customObject");
        qf.setOperation(FilterOperation.IN);
        qf.getValues().add("test");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        JPAUtils.queryEntities(em, DocumentEntity.class, q);
        Assert.fail("No exception was thrown");
    }

    // INIC

    @Test
    public void testInIcString() {

        QueryFilter qf = new QueryFilter();
        qf.setField("string");
        qf.setOperation(FilterOperation.INIC);
        qf.getValues().add("carOL");
        qf.getValues().add("aLANah");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(2, documents.size());
        Assert.assertNotNull(documents.get(0).getString());
        Assert.assertEquals("Alanah", documents.get(0).getString());
    }

    @Test
    public void testInIcObjectCharacter() {

        QueryFilter qf = new QueryFilter();
        qf.setField("objectCharacter");
        qf.setOperation(FilterOperation.INIC);
        qf.getValues().add("B");
        qf.getValues().add("X");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(4, documents.size());
        Assert.assertNotNull(documents.get(0).getObjectCharacter());
        Assert.assertEquals('x', documents.get(0).getObjectCharacter().charValue());
    }

    @Test
    public void testInIcPrimitiveCharacter() {

        QueryFilter qf = new QueryFilter();
        qf.setField("primitiveCharacter");
        qf.setOperation(FilterOperation.INIC);
        qf.getValues().add("B");
        qf.getValues().add("X");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(4, documents.size());
        Assert.assertEquals('b', documents.get(0).getPrimitiveCharacter());
    }

    @Test
    public void testInIcObjectCharacterArray() {

        QueryFilter qf = new QueryFilter();
        qf.setField("objectCharacterArray");
        qf.setOperation(FilterOperation.INIC);
        qf.getValues().add("kwiLITh");
        qf.getValues().add("sKiLiTH");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(2, documents.size());
        Assert.assertNotNull(documents.get(0).getObjectCharacterArray());
        Assert.assertArrayEquals("Skilith".chars().mapToObj(c -> (char) c).toArray(Character[]::new),
                documents.get(0).getObjectCharacterArray());
    }

    @Test
    public void testInIcPrimitiveCharacterArray() {

        QueryFilter qf = new QueryFilter();
        qf.setField("primitiveCharacterArray");
        qf.setOperation(FilterOperation.INIC);
        qf.getValues().add("YAHOO.co.jp");
        qf.getValues().add("GOO.ne.jp");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(3, documents.size());
        Assert.assertArrayEquals("goo.ne.jp".toCharArray(), documents.get(0).getPrimitiveCharacterArray());
    }

    // NIN

    @Test
    public void testNinString() {

        QueryFilter qf = new QueryFilter();
        qf.setField("string");
        qf.setOperation(FilterOperation.NIN);
        qf.getValues().add("Carol");
        qf.getValues().add("Felice");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(48, documents.size());
        Assert.assertNotNull(documents.get(0).getString());
        Assert.assertEquals("Anneliese", documents.get(0).getString());
    }

    // NINIC

    @Test
    public void testNinIcString() {

        QueryFilter qf = new QueryFilter();
        qf.setField("string");
        qf.setOperation(FilterOperation.NINIC);
        qf.getValues().add("cARol");
        qf.getValues().add("felIcE");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(48, documents.size());
        Assert.assertNotNull(documents.get(0).getString());
        Assert.assertEquals("Anneliese", documents.get(0).getString());
    }

    // ISNULL

    @Test
    public void testIsNullFilter() {

        QueryFilter qf = new QueryFilter();
        qf.setField("objectEnum");
        qf.setOperation(FilterOperation.ISNULL);

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(1, documents.size());
        Assert.assertNull(documents.get(0).getObjectEnum());
    }

    @Test
    public void testIsNullFilterWithValue() {

        QueryFilter qf = new QueryFilter();
        qf.setField("objectEnum");
        qf.setOperation(FilterOperation.ISNULL);
        qf.setValue("test");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(1, documents.size());
        Assert.assertNull(documents.get(0).getObjectEnum());
    }

    // ISNOTNULL

    @Test
    public void testIsNotNullFilter() {

        QueryFilter qf = new QueryFilter();
        qf.setField("objectEnum");
        qf.setOperation(FilterOperation.ISNOTNULL);

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(49, documents.size());
        Assert.assertNotNull(documents.get(0).getObjectEnum());
    }

    //// Test general filter correctness

    @Test
    public void testMultipleFilters() {

        QueryParameters q = new QueryParameters();

        QueryFilter qf = new QueryFilter();
        qf.setField("string");
        qf.setOperation(FilterOperation.IN);
        qf.getValues().add("Felice");
        qf.getValues().add("Carol");
        qf.getValues().add("Simone");
        qf.getValues().add("Clarine");
        q.getFilters().add(qf);

        qf = new QueryFilter();
        qf.setField("string");
        qf.setOperation(FilterOperation.NEQ);
        qf.setValue("Carol");
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(3, documents.size());
    }

    @Test
    public void testWrongDateField() {

        Date d = Date.from(ZonedDateTime.parse("2014-11-26T11:15:08Z").toInstant());

        QueryFilter qf = new QueryFilter();
        qf.setField("string");
        qf.setOperation(FilterOperation.LTE);
        qf.setDateValue(d);

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(50, documents.size());
    }

    @Test
    public void testNonExistingField() {

        QueryFilter qf = new QueryFilter();
        qf.setField("asdas");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("test");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        try {
            JPAUtils.queryEntities(em, DocumentEntity.class, q);
            Assert.fail("No exception was thrown");
        } catch (NoSuchEntityFieldException e) {

            Assert.assertEquals("asdas", e.getField());
        }
    }

    @Test
    public void testNullValue() {

        QueryFilter qf = new QueryFilter();
        qf.setField("string");
        qf.setOperation(FilterOperation.EQ);

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(50, documents.size());
    }

    @Test
    public void testNullField() {

        QueryFilter qf = new QueryFilter();
        qf.setOperation(FilterOperation.NEQ);
        qf.setValue("test");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        try {
            JPAUtils.queryEntities(em, DocumentEntity.class, q);
            Assert.fail("No exception was thrown");
        } catch (NoSuchEntityFieldException e) {

            Assert.assertNull(e.getField());
        }
    }

    //// Test relations

    @Test
    public void testManyToOneRelation() {

        QueryFilter qf = new QueryFilter();
        qf.setField("account.id");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("44");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(3, documents.size());

        qf = new QueryFilter();
        qf.setField("account.name");
        qf.setOperation(FilterOperation.INIC);
        qf.getValues().add("melony");
        qf.getValues().add("lorie");
        qf.getValues().add("selena");

        q = new QueryParameters();
        q.getFilters().add(qf);

        documents = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(5, documents.size());
    }

    @Test
    public void testManyToOneRelationOnlyField() {

        QueryFilter qf = new QueryFilter();
        qf.setField("account");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("44");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<DocumentEntity> documents  = JPAUtils.queryEntities(em, DocumentEntity.class, q);

        Assert.assertNotNull(documents);
        Assert.assertEquals(50, documents.size());
    }

    @Test
    public void testOneToManyRelation() {

        QueryFilter qf = new QueryFilter();
        qf.setField("documents.id.key");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("10");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<AccountEntity> accounts = JPAUtils.queryEntities(em, AccountEntity.class, q);

        Assert.assertNotNull(accounts);
        Assert.assertEquals(1, accounts.size());

        qf = new QueryFilter();
        qf.setField("documents.string");
        qf.setOperation(FilterOperation.NIN);
        qf.getValues().add("Anneliese");
        qf.getValues().add("Sallie");

        q = new QueryParameters();
        q.getFilters().add(qf);

        accounts = JPAUtils.queryEntities(em, AccountEntity.class, q);

        Assert.assertNotNull(accounts);
        Assert.assertEquals(31, accounts.size());
    }

    @Test
    public void testOneToManyRelationMultiple() {

        QueryFilter qf = new QueryFilter();
        qf.setField("documents.string");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("Penny");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<AccountEntity> accounts = JPAUtils.queryEntities(em, AccountEntity.class, q);

        Assert.assertNotNull(accounts);
        Assert.assertEquals(2, accounts.size());

        qf = new QueryFilter();
        qf.setField("documents.string");
        qf.setOperation(FilterOperation.IN);
        qf.getValues().add("Penny");
        qf.getValues().add("Carol");

        q = new QueryParameters();
        q.getFilters().add(qf);

        accounts = JPAUtils.queryEntities(em, AccountEntity.class, q);

        Assert.assertNotNull(accounts);
        Assert.assertEquals(2, accounts.size());
    }

    @Test
    public void testEmbeddedRelation() {

        QueryFilter qf = new QueryFilter();
        qf.setField("address.country");
        qf.setOperation(FilterOperation.EQ);
        qf.setValue("China");

        QueryParameters q = new QueryParameters();
        q.getFilters().add(qf);

        List<AccountEntity> accounts = JPAUtils.queryEntities(em, AccountEntity.class, q);

        Assert.assertNotNull(accounts);
        Assert.assertEquals(13, accounts.size());
        Assert.assertNotNull(accounts.get(0).getAddress());
        Assert.assertNotNull(accounts.get(0).getAddress().getCountry());
        Assert.assertEquals("China", accounts.get(0).getAddress().getCountry());
        Assert.assertNotNull(accounts.get(12).getAddress());
        Assert.assertNotNull(accounts.get(12).getAddress().getCountry());
        Assert.assertEquals("China", accounts.get(12).getAddress().getCountry());
    }
}
