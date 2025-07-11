package com.sadiqov.permissions_app.service.group;


import com.sadiqov.permissions_app.dto.request.GroupRequest;
import com.sadiqov.permissions_app.dto.response.GroupResponse;

import java.util.List;

public interface GroupService {
    GroupResponse create(GroupRequest request);
    GroupResponse update(Long id, GroupRequest request);
    void delete(Long id);
    GroupResponse getById(Long id);
    List<GroupResponse> getAll();
}
