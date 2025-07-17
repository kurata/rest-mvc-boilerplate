package br.com.aqueteron.bp.service.configuration;

import br.com.aqueteron.bp.api.model.ErrorResponseApiSchema;
import java.util.Collections;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.http.ProblemDetail;

import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ErrorResponseMapper {

    @Mapping(target = "links", ignore = true)
    @Mapping(source = "properties", target = "properties", qualifiedByName = "mapProblemDetailPropertiesToErrorResponseApiSchemaProperties")
    ErrorResponseApiSchema toErrorResponseApiSchema(ProblemDetail problemDetail);

    @Named("mapProblemDetailPropertiesToErrorResponseApiSchemaProperties")
    default Map<String, String> mapProblemDetailPropertiesToErrorResponseApiSchemaProperties(Map<String, Object> sourceMap){
        if( sourceMap == null){
            return Collections.emptyMap();
        }
        return sourceMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> String.valueOf(entry.getValue())
                ));
    }
}
