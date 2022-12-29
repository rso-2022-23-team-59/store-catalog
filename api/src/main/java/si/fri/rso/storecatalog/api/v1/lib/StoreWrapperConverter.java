package si.fri.rso.storecatalog.api.v1.lib;

import si.fri.rso.storecatalog.lib.Store;
import si.fri.rso.storecatalog.models.entities.StoreEntity;

import si.fri.rso.storecatalog.lib.Store;

public class StoreWrapperConverter {

    public static StoreWrapper toDto(Store entity) {

        StoreWrapper dto = new StoreWrapper();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setUrl(entity.getUrl());
        return dto;

    }

    public static Store toStore(StoreWrapper entity) {

        Store dto = new Store();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setUrl(entity.getUrl());
        return dto;

    }


}