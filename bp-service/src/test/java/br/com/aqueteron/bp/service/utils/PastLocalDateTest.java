package br.com.aqueteron.bp.service.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PastLocalDateTest {

    static Stream<LocalDate> validLocalDateProvider() {
        return Stream.of(
                LocalDate.now().minusYears(200),
                LocalDate.now().minusYears(100),
                LocalDate.now().minusYears(50),
                LocalDate.now().minusYears(10),
                LocalDate.now().minusYears(1),
                LocalDate.now()
        );
    }

    @ParameterizedTest
    @MethodSource("validLocalDateProvider")
    void validateTest(final LocalDate value) {
        PastLocalDate pastLocalDate = new PastLocalDate(value);
        assertNotNull(pastLocalDate);
    }

    static Stream<LocalDate> invalidLocalDateProvider() {
        return Stream.of(
                LocalDate.now().plusYears(1),
                LocalDate.now().plusYears(10),
                LocalDate.now().plusYears(50),
                LocalDate.now().plusDays(1)
        );
    }

    @ParameterizedTest
    @MethodSource("invalidLocalDateProvider")
    void invalidDateTest(final LocalDate value) {
        assertThrows(IllegalArgumentException.class, () -> new PastLocalDate(value));
    }

    @Test
    void pastLocalDateNullTest() {
        PastLocalDate pastLocalDate = new PastLocalDate();
        assertNull(pastLocalDate.value());
    }
}
