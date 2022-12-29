package si.fri.rso.storecatalog.api.v1.resources;

import com.kumuluz.ee.graphql.mp.utils.GraphQLUtils;
import com.kumuluz.ee.rest.beans.QueryParameters;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

import si.fri.rso.storecatalog.api.v1.lib.StoreWrapper;
import si.fri.rso.storecatalog.api.v1.lib.StoreWrapperConverter;
import si.fri.rso.storecatalog.lib.Store;
import si.fri.rso.storecatalog.services.beans.StoreBean;

import javax.enterprise.context.RequestScoped;
import com.kumuluz.ee.rest.utils.QueryStringDefaults;

import javax.inject.Inject;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@GraphQLApi
@RequestScoped
public class StoreQLResource {

    private Logger log = Logger.getLogger(StoreResource.class.getName());

    @Inject
    private StoreBean storeBean;

    @Inject
    private QueryStringDefaults qsd;



    @Query
    public List<StoreWrapper> getAllStores() {
        QueryParameters qp = GraphQLUtils.queryParametersBuilder()
                .withQueryStringDefaults(qsd)
                .build();
        return storeBean.getStoreFilter(qp).stream().map(StoreWrapperConverter::toDto).collect(Collectors.toList());
    }

    @Query
    public StoreWrapper getStore(@Name("storeId") Integer storeId) {

        return StoreWrapperConverter.toDto(storeBean.getStore(storeId));
    }

    @Mutation
    public StoreWrapper addStore(@Name("store") StoreWrapper storew){
        Store store = StoreWrapperConverter.toStore(storew);
        Store newStore = storeBean.createStore(store);
        return StoreWrapperConverter.toDto(newStore);
    }

    @Mutation
    public int deleteStore(@Name("storeId") Integer storeId){
        storeBean.deleteStore(storeId);
        return 1;
    }


}
