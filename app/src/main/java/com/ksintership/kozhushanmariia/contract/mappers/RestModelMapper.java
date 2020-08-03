package com.ksintership.kozhushanmariia.contract.mappers;

import java.util.List;

public interface RestModelMapper<RM, IM> {
    IM restToModel(RM restModel);

    List<IM> restToModel(List<RM> listRestModel);
}
