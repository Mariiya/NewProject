package com.ksintership.kozhushanmariia.contract.mappers;

public interface EntityModelMapper<EM, IM> {
    IM entityModelToInternalModel(EM entityModel);

    EM internalModelToEntityModel(IM internalModel);
}
