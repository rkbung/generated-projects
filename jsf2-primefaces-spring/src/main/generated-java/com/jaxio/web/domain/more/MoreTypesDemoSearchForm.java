/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to use Celerio within your company? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Template pack-jsf2-spring-conversation:src/main/java/domain/SearchForm.e.vm.java
 */
package com.jaxio.web.domain.more;

import static com.jaxio.dao.support.Ranges.RangeBigDecimal.newRangeBigDecimal;
import static com.jaxio.dao.support.Ranges.RangeBigInteger.newRangeBigInteger;
import static com.jaxio.dao.support.Ranges.RangeDate.newRangeDate;
import static com.jaxio.dao.support.Ranges.RangeDouble.newRangeDouble;
import static com.jaxio.dao.support.Ranges.RangeFloat.newRangeFloat;
import static com.jaxio.dao.support.Ranges.RangeInteger.newRangeInteger;
import static com.jaxio.dao.support.Ranges.RangeLocalDate.newRangeLocalDate;
import static com.jaxio.dao.support.Ranges.RangeLocalDateTime.newRangeLocalDateTime;
import static com.jaxio.dao.support.Ranges.RangeLong.newRangeLong;
import static com.jaxio.domain.more.MoreTypesDemo_.dateJavaTemporalDate;
import static com.jaxio.domain.more.MoreTypesDemo_.dateJavaTemporalTimestamp;
import static com.jaxio.domain.more.MoreTypesDemo_.dateJoda;
import static com.jaxio.domain.more.MoreTypesDemo_.dateTimeJoda;
import static com.jaxio.domain.more.MoreTypesDemo_.numberBigDecimal;
import static com.jaxio.domain.more.MoreTypesDemo_.numberBigInteger;
import static com.jaxio.domain.more.MoreTypesDemo_.numberDouble;
import static com.jaxio.domain.more.MoreTypesDemo_.numberFloat;
import static com.jaxio.domain.more.MoreTypesDemo_.numberInt;
import static com.jaxio.domain.more.MoreTypesDemo_.numberLong;
import javax.inject.Named;
import org.springframework.context.annotation.Scope;
import com.jaxio.dao.support.Ranges.RangeBigDecimal;
import com.jaxio.dao.support.Ranges.RangeBigInteger;
import com.jaxio.dao.support.Ranges.RangeDate;
import com.jaxio.dao.support.Ranges.RangeDouble;
import com.jaxio.dao.support.Ranges.RangeFloat;
import com.jaxio.dao.support.Ranges.RangeInteger;
import com.jaxio.dao.support.Ranges.RangeLocalDate;
import com.jaxio.dao.support.Ranges.RangeLocalDateTime;
import com.jaxio.dao.support.Ranges.RangeLong;
import com.jaxio.dao.support.SearchParameters;
import com.jaxio.domain.more.MoreTypesDemo;
import com.jaxio.web.domain.support.GenericSearchForm;

/**
 * View Helper to find/select {@link MoreTypesDemo}.
 * It exposes a {@link MoreTypesDemo} instance so it can be used in search by Example query.
 */
@Named
@Scope("conversation")
public class MoreTypesDemoSearchForm extends GenericSearchForm<MoreTypesDemo, MoreTypesDemoSearchForm> {
    private static final long serialVersionUID = 1L;

    private MoreTypesDemo moreTypesDemo = new MoreTypesDemo();
    private RangeInteger<MoreTypesDemo> numberIntRange = newRangeInteger(numberInt);
    private RangeLong<MoreTypesDemo> numberLongRange = newRangeLong(numberLong);
    private RangeDouble<MoreTypesDemo> numberDoubleRange = newRangeDouble(numberDouble);
    private RangeFloat<MoreTypesDemo> numberFloatRange = newRangeFloat(numberFloat);
    private RangeBigInteger<MoreTypesDemo> numberBigIntegerRange = newRangeBigInteger(numberBigInteger);
    private RangeBigDecimal<MoreTypesDemo> numberBigDecimalRange = newRangeBigDecimal(numberBigDecimal);
    private RangeDate<MoreTypesDemo> dateJavaTemporalDateRange = newRangeDate(dateJavaTemporalDate);
    private RangeDate<MoreTypesDemo> dateJavaTemporalTimestampRange = newRangeDate(dateJavaTemporalTimestamp);
    private RangeLocalDate<MoreTypesDemo> dateJodaRange = newRangeLocalDate(dateJoda);
    private RangeLocalDateTime<MoreTypesDemo> dateTimeJodaRange = newRangeLocalDateTime(dateTimeJoda);

    public MoreTypesDemo getMoreTypesDemo() {
        return moreTypesDemo;
    }

    @Override
    protected MoreTypesDemo getEntity() {
        return moreTypesDemo;
    }

    // Ranges, used from the view.
    public RangeInteger<MoreTypesDemo> getNumberIntRange() {
        return numberIntRange;
    }

    public RangeLong<MoreTypesDemo> getNumberLongRange() {
        return numberLongRange;
    }

    public RangeDouble<MoreTypesDemo> getNumberDoubleRange() {
        return numberDoubleRange;
    }

    public RangeFloat<MoreTypesDemo> getNumberFloatRange() {
        return numberFloatRange;
    }

    public RangeBigInteger<MoreTypesDemo> getNumberBigIntegerRange() {
        return numberBigIntegerRange;
    }

    public RangeBigDecimal<MoreTypesDemo> getNumberBigDecimalRange() {
        return numberBigDecimalRange;
    }

    public RangeDate<MoreTypesDemo> getDateJavaTemporalDateRange() {
        return dateJavaTemporalDateRange;
    }

    public RangeDate<MoreTypesDemo> getDateJavaTemporalTimestampRange() {
        return dateJavaTemporalTimestampRange;
    }

    public RangeLocalDate<MoreTypesDemo> getDateJodaRange() {
        return dateJodaRange;
    }

    public RangeLocalDateTime<MoreTypesDemo> getDateTimeJodaRange() {
        return dateTimeJodaRange;
    }

    public SearchParameters toSearchParameters() {
        return new SearchParameters() //
                .anywhere() //
                .range(numberIntRange) //
                .range(numberLongRange) //
                .range(numberDoubleRange) //
                .range(numberFloatRange) //
                .range(numberBigIntegerRange) //
                .range(numberBigDecimalRange) //
                .range(dateJavaTemporalDateRange) //
                .range(dateJavaTemporalTimestampRange) //
                .range(dateJodaRange) //
                .range(dateTimeJodaRange) //
        ;
    }

    @Override
    public MoreTypesDemoSearchForm newInstance() {
        return new MoreTypesDemoSearchForm();
    }

    @Override
    public void resetWithOther(MoreTypesDemoSearchForm other) {
        this.moreTypesDemo = other.getMoreTypesDemo();
        this.numberIntRange = other.getNumberIntRange();
        this.numberLongRange = other.getNumberLongRange();
        this.numberDoubleRange = other.getNumberDoubleRange();
        this.numberFloatRange = other.getNumberFloatRange();
        this.numberBigIntegerRange = other.getNumberBigIntegerRange();
        this.numberBigDecimalRange = other.getNumberBigDecimalRange();
        this.dateJavaTemporalDateRange = other.getDateJavaTemporalDateRange();
        this.dateJavaTemporalTimestampRange = other.getDateJavaTemporalTimestampRange();
        this.dateJodaRange = other.getDateJodaRange();
        this.dateTimeJodaRange = other.getDateTimeJodaRange();
    }
}