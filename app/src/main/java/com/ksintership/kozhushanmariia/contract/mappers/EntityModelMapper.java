package com.ksintership.kozhushanmariia.contract.mappers;

import java.util.List;

public interface EntityModelMapper<EM, IM> {
    IM entityToModel(EM entityModel);

    List<IM> entityToModel(List<EM> entityModel);

    EM modelToEntity(IM internalModel);

    List<EM> modelToEntity(List<IM> internalModel);
}
