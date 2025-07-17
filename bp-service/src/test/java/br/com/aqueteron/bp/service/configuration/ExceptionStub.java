package br.com.aqueteron.bp.service.configuration;

import br.com.aqueteron.bp.service.exception.BusinessException;
import net.datafaker.Faker;
import net.datafaker.providers.base.Options;
import org.springframework.http.HttpStatus;

public class ExceptionStub {
    public static BusinessException createBusinessException() {
        Faker faker = new Faker();
        Options options = faker.options();
        return new BusinessException(
                options.option(HttpStatus.class),
                "Exception message",
                "messageKey"
        );
    }

    public static Exception createException() {
        return new Exception("Exception message");
    }

    public static IllegalArgumentException createIllegalArgumentException() {
        return new IllegalArgumentException("IllegalArgumentException message");
    }
}
