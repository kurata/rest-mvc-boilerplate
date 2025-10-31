package br.com.aqueteron.bp.service.customer;

import io.micrometer.common.KeyValue;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.stream.StreamSupport;

@Component
@Slf4j
public class CustomerHandler implements ObservationHandler<Observation.Context> {

    @Override
    public boolean supportsContext(final Observation.Context context) {
        return true;
    }

    @Override
    public void onStart(final Observation.Context context) {
        ObservationHandler.super.onStart(context);
        log.info("Before running the observation for context [{}], customerType [{}]", context.getName(), getCustomerTypeFromContext(context));
    }

    @Override
    public void onStop(final Observation.Context context) {
        ObservationHandler.super.onStop(context);
        log.info("After running the observation for context [{}], userType [{}]", context.getName(), getCustomerTypeFromContext(context));
    }

    private String getCustomerTypeFromContext(final Observation.Context context) {
        return StreamSupport.stream(context.getLowCardinalityKeyValues().spliterator(), false)
                .filter(keyValue -> "customerType".equals(keyValue.getKey()))
                .map(KeyValue::getValue)
                .findFirst()
                .orElse("UNKNOWN");
    }
}
