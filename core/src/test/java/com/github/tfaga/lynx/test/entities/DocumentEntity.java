package com.github.tfaga.lynx.test.entities;

import com.github.tfaga.lynx.test.entities.enums.DocumentEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

/**
 * @author Tilen Faganel
 * @since 1.2.0
 */
@Entity
@Table(name = "documents")
public class DocumentEntity {

    @EmbeddedId
    private DocumentIdEntity id;

    @Column(name = "object_integer")
    private Integer objectInteger;

    @Column(name = "primitive_integer")
    private int primitiveInteger;

    @Column(name = "object_long")
    private Long objectLong;

    @Column(name = "primitive_long")
    private long primitiveLong;

    @Column(name = "object_short")
    private Short objectShort;

    @Column(name = "primitive_short")
    private short primitiveShort;

    @Column(name = "object_double")
    private Double objectDouble;

    @Column(name = "primitive_double")
    private double primitiveDouble;

    @Column(name = "object_float", columnDefinition = "float")
    private Float objectFloat;

    @Column(name = "primitive_float", columnDefinition = "float")
    private float primitiveFloat;

    @Column(name = "big_decimal", columnDefinition = "decimal(19,2)")
    private BigDecimal bigDecimal;

    @Column(name = "big_integer", columnDefinition = "decimal(19,2)")
    private BigInteger bigInteger;

    @Column(name = "object_boolean")
    private Boolean objectBoolean;

    @Column(name = "primitive_boolean")
    private boolean primitiveBoolean;

    @Column(name = "object_character", columnDefinition = "char(1)")
    private Character objectCharacter;

    @Column(name = "primitive_character", columnDefinition = "char(1)")
    private char primitiveCharacter;

    @Column(name = "object_character_array")
    private Character[] objectCharacterArray;

    @Column(name = "primitive_character_array")
    private char[] primitiveCharacterArray;

    @Column(name = "string")
    private String string;

    @Column(name = "util_date")
    private Date utilDate;

    @Column(name = "util_calendar")
    private Calendar utilCalendar;

    @Column(name = "gregorian_calendar")
    private GregorianCalendar gregorianCalendar;

    @Column(name = "sql_date")
    private java.sql.Date sqlDate;

    @Column(name = "sql_time")
    private Time sqlTime;

    @Column(name = "sql_timestamp")
    private Timestamp sqlTimestamp;

    @Column(name = "local_date", columnDefinition = "date")
    private LocalDate localDate;

    @Column(name = "local_time", columnDefinition = "time")
    private LocalTime localTime;

    @Column(name = "local_datetime", columnDefinition = "timestamp")
    private LocalDateTime localDateTime;

    @Column(name = "offset_time", columnDefinition = "time")
    private OffsetTime offsetTime;

    @Column(name = "offset_datetime", columnDefinition = "timestamp")
    private OffsetDateTime offsetDateTime;

    @Column(name = "instant", columnDefinition = "timestamp")
    private Instant instant;

    @Column(name = "duration", columnDefinition = "bigint")
    private Duration duration;

    @Column(name = "uuid", columnDefinition = "varchar(36)")
    private UUID uuid;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "object_enum")
    private DocumentEnum objectEnum;

    // Not relevant/supported types

    @Column(name = "custom_object", columnDefinition = "varchar(255)")
    private CustomObjectEntity customObject;

    // Relations

    @ManyToOne
    @JoinColumn(name = "account_id")
    private AccountEntity account;

    public DocumentIdEntity getId() {
        return id;
    }

    public void setId(DocumentIdEntity id) {
        this.id = id;
    }

    public Integer getObjectInteger() {
        return objectInteger;
    }

    public void setObjectInteger(Integer objectInteger) {
        this.objectInteger = objectInteger;
    }

    public int getPrimitiveInteger() {
        return primitiveInteger;
    }

    public void setPrimitiveInteger(int primitiveInteger) {
        this.primitiveInteger = primitiveInteger;
    }

    public Long getObjectLong() {
        return objectLong;
    }

    public void setObjectLong(Long objectLong) {
        this.objectLong = objectLong;
    }

    public long getPrimitiveLong() {
        return primitiveLong;
    }

    public void setPrimitiveLong(long primitiveLong) {
        this.primitiveLong = primitiveLong;
    }

    public Short getObjectShort() {
        return objectShort;
    }

    public void setObjectShort(Short objectShort) {
        this.objectShort = objectShort;
    }

    public short getPrimitiveShort() {
        return primitiveShort;
    }

    public void setPrimitiveShort(short primitiveShort) {
        this.primitiveShort = primitiveShort;
    }

    public Double getObjectDouble() {
        return objectDouble;
    }

    public void setObjectDouble(Double objectDouble) {
        this.objectDouble = objectDouble;
    }

    public double getPrimitiveDouble() {
        return primitiveDouble;
    }

    public void setPrimitiveDouble(double primitiveDouble) {
        this.primitiveDouble = primitiveDouble;
    }

    public Float getObjectFloat() {
        return objectFloat;
    }

    public void setObjectFloat(Float objectFloat) {
        this.objectFloat = objectFloat;
    }

    public float getPrimitiveFloat() {
        return primitiveFloat;
    }

    public void setPrimitiveFloat(float primitiveFloat) {
        this.primitiveFloat = primitiveFloat;
    }

    public BigDecimal getBigDecimal() {
        return bigDecimal;
    }

    public void setBigDecimal(BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
    }

    public BigInteger getBigInteger() {
        return bigInteger;
    }

    public void setBigInteger(BigInteger bigInteger) {
        this.bigInteger = bigInteger;
    }

    public Boolean getObjectBoolean() {
        return objectBoolean;
    }

    public void setObjectBoolean(Boolean objectBoolean) {
        this.objectBoolean = objectBoolean;
    }

    public boolean isPrimitiveBoolean() {
        return primitiveBoolean;
    }

    public void setPrimitiveBoolean(boolean primitiveBoolean) {
        this.primitiveBoolean = primitiveBoolean;
    }

    public Character getObjectCharacter() {
        return objectCharacter;
    }

    public void setObjectCharacter(Character objectCharacter) {
        this.objectCharacter = objectCharacter;
    }

    public char getPrimitiveCharacter() {
        return primitiveCharacter;
    }

    public void setPrimitiveCharacter(char primitiveCharacter) {
        this.primitiveCharacter = primitiveCharacter;
    }

    public Character[] getObjectCharacterArray() {
        return objectCharacterArray;
    }

    public void setObjectCharacterArray(Character[] objectCharacterArray) {
        this.objectCharacterArray = objectCharacterArray;
    }

    public char[] getPrimitiveCharacterArray() {
        return primitiveCharacterArray;
    }

    public void setPrimitiveCharacterArray(char[] primitiveCharacterArray) {
        this.primitiveCharacterArray = primitiveCharacterArray;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public Date getUtilDate() {
        return utilDate;
    }

    public void setUtilDate(Date utilDate) {
        this.utilDate = utilDate;
    }

    public Calendar getUtilCalendar() {
        return utilCalendar;
    }

    public void setUtilCalendar(Calendar utilCalendar) {
        this.utilCalendar = utilCalendar;
    }

    public GregorianCalendar getGregorianCalendar() {
        return gregorianCalendar;
    }

    public void setGregorianCalendar(GregorianCalendar gregorianCalendar) {
        this.gregorianCalendar = gregorianCalendar;
    }

    public java.sql.Date getSqlDate() {
        return sqlDate;
    }

    public void setSqlDate(java.sql.Date sqlDate) {
        this.sqlDate = sqlDate;
    }

    public Time getSqlTime() {
        return sqlTime;
    }

    public void setSqlTime(Time sqlTime) {
        this.sqlTime = sqlTime;
    }

    public Timestamp getSqlTimestamp() {
        return sqlTimestamp;
    }

    public void setSqlTimestamp(Timestamp sqlTimestamp) {
        this.sqlTimestamp = sqlTimestamp;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    public void setLocalTime(LocalTime localTime) {
        this.localTime = localTime;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public OffsetTime getOffsetTime() {
        return offsetTime;
    }

    public void setOffsetTime(OffsetTime offsetTime) {
        this.offsetTime = offsetTime;
    }

    public OffsetDateTime getOffsetDateTime() {
        return offsetDateTime;
    }

    public void setOffsetDateTime(OffsetDateTime offsetDateTime) {
        this.offsetDateTime = offsetDateTime;
    }

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public DocumentEnum getObjectEnum() {
        return objectEnum;
    }

    public void setObjectEnum(DocumentEnum objectEnum) {
        this.objectEnum = objectEnum;
    }

    public CustomObjectEntity getCustomObject() {
        return customObject;
    }

    public void setCustomObject(CustomObjectEntity customObject) {
        this.customObject = customObject;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }
}
