package com.ksintership.kozhushanmariia.contract.mappers;

public interface RestModelMapper<RM, IM> {
    IM restModelToInternalModel(RM restModel);
}
