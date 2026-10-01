package com.productivity.web.service;

import com.productivity.web.dto.request.LabelRequest;
import com.productivity.web.dto.response.LabelResponse;

import java.util.List;

public interface LabelServiceInterface {

    LabelResponse createLabel(
            LabelRequest request,
            String email
    );

    List<LabelResponse> getMyLabels(
            String email
    );

    LabelResponse getLabelById(
            Long labelId,
            String email
    );

    LabelResponse updateLabel(
            Long labelId,
            LabelRequest request,
            String email
    );

    void deleteLabel(
            String labelName,
            String email
    );

}
