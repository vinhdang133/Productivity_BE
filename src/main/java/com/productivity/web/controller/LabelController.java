package com.productivity.web.controller;

import com.productivity.web.dto.request.LabelRequest;
import com.productivity.web.dto.response.LabelResponse;
import com.productivity.web.repository.LabelRepository;
import com.productivity.web.repository.StreakRepository;
import com.productivity.web.service.LabelServiceInterface;
import com.productivity.web.service.StreakServiceInterface;
import com.productivity.web.service.implement.StreakServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/labels")
@RequiredArgsConstructor
public class LabelController {




    private final LabelServiceInterface labelServiceInterface;




    @GetMapping
    public List<LabelResponse> getMyLabels(Authentication authentication) {
        String email = authentication.getName();

        return labelServiceInterface.getMyLabels(email);
    }

    @GetMapping("/{id}")
    public LabelResponse getLabelsById(@PathVariable Long id, Authentication authentication) {
        String email = authentication.getName();

        return labelServiceInterface.getLabelById(id, email);
    }
    @PostMapping
    public LabelResponse addLabel(
            @Valid @RequestBody LabelRequest labelRequest, Authentication authentication) {

        String email = authentication.getName();
        return labelServiceInterface.createLabel(labelRequest, email);

    }

    @DeleteMapping
    public void deleteLabel(
            @Valid @RequestBody LabelRequest labelRequest, Authentication authentication) {
        String email = authentication.getName();
        String nameLabel = labelRequest.getName();
        labelServiceInterface.deleteLabel(nameLabel, email);
    }

}
