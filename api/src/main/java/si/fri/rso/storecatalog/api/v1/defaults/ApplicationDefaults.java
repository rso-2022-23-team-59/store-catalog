package si.fri.rso.storecatalog.api.v1.defaults;

import com.kumuluz.ee.rest.utils.QueryStringDefaults;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

public class ApplicationDefaults {

    @Produces
    @ApplicationScoped
    public QueryStringDefaults getQueryDefaults() {
        return new QueryStringDefaults()
                .defaultOffset(0)
                .defaultLimit(20)
                .maxLimit(100);
    }
}