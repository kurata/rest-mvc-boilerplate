package br.com.aqueteron.bp.service.archunit;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.GeneralCodingRules.*;

@AnalyzeClasses(packages = "br.com.aqueteron.bp.service")
public class CodingRulesTest {

    @ArchTest
    private static final ArchRule no_access_to_standard_streams = NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;

    @ArchTest
    private void no_access_to_standard_streams_as_method(JavaClasses classes) {
        noClasses().should(ACCESS_STANDARD_STREAMS).check(classes);
    }

    @ArchTest
    private static final ArchRule no_java_util_logging = NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING;

    @ArchTest
    private static final ArchRule no_jodatime = NO_CLASSES_SHOULD_USE_JODATIME;

    //Method commented because we use field injection in tests classes.
//    @ArchTest
//    private static final ArchRule no_classes_should_use_field_injection = NO_CLASSES_SHOULD_USE_FIELD_INJECTION;

    @ArchTest
    private static final ArchRule deprecated_api_should_not_be_used = DEPRECATED_API_SHOULD_NOT_BE_USED;

    @ArchTest
    private static final ArchRule old_date_and_time_classes_should_not_be_used = OLD_DATE_AND_TIME_CLASSES_SHOULD_NOT_BE_USED;
}
