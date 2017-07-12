package com.engagetech.infrastructure.utils;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.Date;

import static com.engagetech.infrastructure.utils.DateUtils.asDate;
import static com.engagetech.infrastructure.utils.DateUtils.asLocalDate;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DateUtilsTest {

    @DataProvider(name = "localDateProvider")
    public Object[][] provideLocalDate() {
        return new Object[][]{
                {LocalDate.of(1991,6,6)},
                {LocalDate.of(12,11,23)},
                {LocalDate.of(5890,1,21)},
        };
    }

    @DataProvider(name = "dateProvider")
    public Object[][] provideDate() {
        return new Object[][]{
                {new Date(10450800000L)},
                {new Date(1483225200000L)}
        };
    }

    @Test(dataProvider = "localDateProvider")
    public void shouldParseBackToOriginalLocalDate(LocalDate localDate){
        //given

        //when
        LocalDate parsedBack = asLocalDate(asDate(localDate));

        //then
        assertThat(parsedBack, is(localDate));
    }

    @Test(dataProvider = "dateProvider")
    public void shouldParseBackToOriginalDate(Date date){
        //given

        //when
        Date parsedBack = asDate(asLocalDate(date));

        //then
        assertThat(parsedBack, is(date));
    }
}