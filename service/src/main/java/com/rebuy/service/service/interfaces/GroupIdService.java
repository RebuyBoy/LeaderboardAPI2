package com.rebuy.service.service.interfaces;

import com.rebuy.service.entity.GroupId;

import java.util.Optional;

public interface GroupIdService {

    GroupId saveIfNotExists(GroupId groupId);

    Optional<GroupId> getByGroupId(GroupId groupId);

}
