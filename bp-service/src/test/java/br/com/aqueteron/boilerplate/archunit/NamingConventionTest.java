package br.com.aqueteron.boilerplate.archunit;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = "br.com.aqueteron.bp.service")
public class NamingConventionTest {

    @ArchTest
    static final ArchRule controllers_should_be_suffixed =
            classes()
                    .that().areAnnotatedWith(Controller.class).or().areAnnotatedWith(RestController.class)
                    .should().haveSimpleNameEndingWith("Controller");

    @ArchTest
    static final ArchRule mappers_should_be_suffixed =
            classes()
                    .that().areAnnotatedWith(Mapper.class)
                    .should().haveSimpleNameEndingWith("Mapper");

    @ArchTest
    static final ArchRule service_should_be_suffixed =
            classes()
                    .that().areAnnotatedWith(Service.class)
                    .should().haveSimpleNameEndingWith("Service");

    @ArchTest
    static final ArchRule repository_should_be_suffixed =
            classes().that().areAssignableTo(Repository.class)
                    .should().haveSimpleNameEndingWith("Repository");

    @ArchTest
    static final ArchRule configuration_should_be_suffixed =
            classes()
                    .that().areAnnotatedWith(Configuration.class)
                    .should().haveSimpleNameEndingWith("Configuration");
}
