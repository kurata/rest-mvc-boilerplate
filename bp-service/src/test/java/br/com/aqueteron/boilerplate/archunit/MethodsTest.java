package br.com.aqueteron.boilerplate.archunit;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.http.ResponseEntity;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

@AnalyzeClasses(packages = "br.com.aqueteron.bp.service")
public class MethodsTest {

    @ArchTest
    static final ArchRule all_public_methods_in_the_controller_layer_should_return_response_entity =
            methods()
                    .that().areDeclaredInClassesThat().haveNameMatching(".*Controller")
                    .and().arePublic()
                    .should().haveRawReturnType(ResponseEntity.class)
                    .because("we do not want to couple the client code directly to the return types of the encapsulated module");
}
