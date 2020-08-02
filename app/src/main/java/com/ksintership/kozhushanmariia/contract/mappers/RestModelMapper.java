package com.ksintership.kozhushanmariia.contract.mappers;

import java.util.List;

public interface RestModelMapper<RM, IM> {
    IM restModelToInternalModel(RM restModel);

    List<IM> restModelToInternalModel(List<RM> listRestModel);
}
