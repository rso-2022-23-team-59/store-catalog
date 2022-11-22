package si.fri.rso.storecatalog.models.converters;

import si.fri.rso.storecatalog.lib.Store;
import si.fri.rso.storecatalog.models.entities.StoreEntity;

public class StoreConverter {

    public static Store toDto(StoreEntity entity) {

        Store dto = new Store();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setImage(entity.getImage());
        return dto;

    }

    public static StoreEntity toEntity(Store dto) {

        StoreEntity entity = new StoreEntity();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setImage(dto.getImage());

        return entity;

    }

}
