package com.productivity.web.dto.mapper;

import com.productivity.web.dto.response.LabelResponse;
import com.productivity.web.entity.Label;

public class LabelMapper {
    public static LabelResponse toResponse (Label label)
    {
        return LabelResponse.builder()
                .id(label.getId())
                .name(label.getName())
                .colorHex(label.getColorHex())
                .createdAt(label.getCreatedAt())
                .build();
    }

}
