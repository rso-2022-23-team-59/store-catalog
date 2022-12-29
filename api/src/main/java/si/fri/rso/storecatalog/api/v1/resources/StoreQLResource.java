package si.fri.rso.storecatalog.api.v1.resources;

import com.kumuluz.ee.graphql.mp.utils.GraphQLUtils;
import com.kumuluz.ee.rest.beans.QueryParameters;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

import org.eclipse.microprofile.graphql.Source;
import si.fri.rso.storecatalog.lib.Store;
import si.fri.rso.storecatalog.services.beans.StoreBean;

import javax.enterprise.context.RequestScoped;
import com.kumuluz.ee.rest.utils.QueryStringDefaults;

import javax.inject.Inject;

import java.util.List;
import java.util.logging.Logger;

@GraphQLApi
@RequestScoped
public class StoreQLResource {

    private Logger log = Logger.getLogger(StoreResource.class.getName());

    @Inject
    private StoreBean storeBean;

    @Inject
    private QueryStringDefaults qsd;

    @Query
    public List<Store> getAllStores() {
        QueryParameters qp = GraphQLUtils.queryParametersBuilder()
                .withQueryStringDefaults(qsd)
                .build();
        return storeBean.getStoreFilter(qp);
    }

    @Query
    public Store getStore(@Name("storeId") Integer storeId) {
        return storeBean.getStore(storeId);
    }

}
