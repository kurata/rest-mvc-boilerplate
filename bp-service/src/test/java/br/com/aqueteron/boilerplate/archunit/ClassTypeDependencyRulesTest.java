package br.com.aqueteron.boilerplate.archunit;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "br.com.aqueteron.people.registration")
public class ClassTypeDependencyRulesTest {

    // 'access' catches only violations by real accesses, i.e. accessing a field, calling a method; compare 'dependOn' further down

    @ArchTest
    static final ArchRule services_should_not_access_controllers =
            noClasses().that().areAnnotatedWith(Service.class)
                    .should().accessClassesThat().areAnnotatedWith(Controller.class);

    @ArchTest
    static final ArchRule persistence_should_not_access_services =
            noClasses().that().areAssignableTo(Repository.class)
                    .should().accessClassesThat().areAnnotatedWith(Service.class);

    @ArchTest
    static final ArchRule services_should_only_be_accessed_by_controllers_or_other_services =
            classes().that().areAnnotatedWith(Service.class)
                    .should().onlyBeAccessed().byClassesThat().areAnnotations().orShould().beAnnotatedWith(Controller.class).orShould().beAnnotatedWith(Service.class);

    @ArchTest
    static final ArchRule services_should_only_access_persistence_or_other_services =
            classes().that().areAnnotatedWith(Service.class)
                    .should().onlyAccessClassesThat().areAnnotations().orShould().beAnnotatedWith(Service.class).orShould().beAssignableTo(Repository.class);

    // 'dependOn' catches a wider variety of violations, e.g. having fields of type, having method parameters of type, extending type ...

    @ArchTest
    static final ArchRule services_should_not_depend_on_controllers =
            noClasses().that().areAnnotatedWith(Service.class)
                    .should().dependOnClassesThat().areAnnotatedWith(Controller.class);

    @ArchTest
    static final ArchRule persistence_should_not_depend_on_services =
            noClasses().that().areAssignableTo(Repository.class)
                    .should().dependOnClassesThat().areAnnotatedWith(Service.class);

    @ArchTest
    static final ArchRule services_should_only_be_depended_on_by_controllers_or_other_services =
            classes().that().areAnnotatedWith(Service.class)
                    .should().onlyHaveDependentClassesThat().areAnnotations().orShould().beAnnotatedWith(Controller.class).orShould().beAnnotatedWith(Service.class);

    @ArchTest
    static final ArchRule services_should_only_depend_on_persistence_or_other_services =
            classes().that().areAnnotatedWith(Service.class)
                    .should().onlyDependOnClassesThat().areAnnotations().orShould().beAnnotatedWith(Service.class).orShould().beAssignableTo(Repository.class);
}
