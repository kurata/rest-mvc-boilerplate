package br.com.aqueteron.bp.service.archunit;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.core.domain.properties.HasName.Predicates.nameContaining;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

@AnalyzeClasses(packages = "br.com.aqueteron.bp.service")
public class SlicesIsolationTest {

    @ArchTest
    static final ArchRule operation_should_only_use_their_own_slice =
            slices().matching("br.com.aqueteron.bp.service.(*)..").namingSlices("Slice $1")
                    .as("Slices").should().notDependOnEachOther()
                    .ignoreDependency(DescribedPredicate.alwaysTrue() , nameContaining(".utils."))
                    .ignoreDependency(DescribedPredicate.alwaysTrue() , nameContaining(".exception."))
                    .ignoreDependency(DescribedPredicate.alwaysTrue() , nameContaining(".api."))
            ;

    @ArchTest
    static final ArchRule slices_should_not_cycles =
            slices().matching("br.com.aqueteron.bp.service.(*)..").namingSlices("Slice $1")
                    .as("Slices").should().beFreeOfCycles();

}
