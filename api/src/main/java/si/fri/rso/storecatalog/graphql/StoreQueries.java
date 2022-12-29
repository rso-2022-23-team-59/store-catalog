package si.fri.rso.storecatalog.graphql;

import com.kumuluz.ee.graphql.annotations.GraphQLClass;
import com.kumuluz.ee.graphql.classes.Filter;
import com.kumuluz.ee.graphql.classes.Pagination;
import com.kumuluz.ee.graphql.classes.PaginationWrapper;
import com.kumuluz.ee.graphql.classes.Sort;
import com.kumuluz.ee.graphql.utils.GraphQLUtils;
import com.kumuluz.ee.rest.utils.QueryStringDefaults;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import si.fri.rso.storecatalog.lib.Store;
import si.fri.rso.storecatalog.services.beans.StoreBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@GraphQLClass
@ApplicationScoped
public class StoreQueries {

    @Inject
    private StoreBean storeBean;

    // TODO: Either use this entity manager or remove it
    @Inject
    private EntityManager em;

    // TODO: Either use this defaults or remove them
    @Inject
    private QueryStringDefaults queryStringDefaults;

    @GraphQLQuery
    public PaginationWrapper<Store> getStores(@GraphQLArgument(name = "pagination") Pagination pagination,
                                                     @GraphQLArgument(name = "sort") Sort sort,
                                                     @GraphQLArgument(name = "filter") Filter filter) {
        // TODO: Use non-deprecated method?
        // return GraphQLUtils.process(em, StoreEntity.class, pagination, sort, filter).getResult().stream().map(StoreConverter::toDto);
        return GraphQLUtils.process(storeBean.getStoress(), pagination, sort, filter);
    }

    @GraphQLQuery
    public Store getStore(@GraphQLArgument(name = "id") Integer id) {
        return storeBean.getStore(id);
    }

}
