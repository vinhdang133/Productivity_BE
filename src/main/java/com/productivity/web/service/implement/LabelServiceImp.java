package com.productivity.web.service.implement;

import com.productivity.web.dto.mapper.LabelMapper;
import com.productivity.web.dto.request.LabelRequest;
import com.productivity.web.dto.response.LabelResponse;
import com.productivity.web.entity.Account;
import com.productivity.web.entity.Label;
import com.productivity.web.repository.AccountRepository;
import com.productivity.web.repository.LabelRepository;
import com.productivity.web.service.LabelServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelServiceImp implements LabelServiceInterface {


    public LabelServiceImp(LabelRepository labelRepository, AccountRepository accountRepository) {
        this.labelRepository = labelRepository;
        this.accountRepository = accountRepository;
    }

    private final LabelRepository labelRepository;
    private final AccountRepository accountRepository;



    @Override
    public LabelResponse createLabel(LabelRequest request, String email) {
        Account user = getAccount(email);


        if (labelRepository.existsByUser_IdAndNameIgnoreCase(user.getId(), request.getName())) {
            throw new RuntimeException("Label name already exists");
        }
        Label label = Label.builder()
                .name(request.getName())
                .colorHex(request.getColorHex())
                .user(user)
                .build();

            Label savedLabel = labelRepository.save(label);
            return LabelMapper.toResponse(savedLabel);
        }

    @Override
    public List<LabelResponse> getMyLabels(String email) {
        Account user = getAccount(email);
        List<Label> labels = labelRepository.findAllByUser_Id(user.getId());
        return labels.stream().map(LabelMapper::toResponse).toList();
    }

    @Override
    public LabelResponse getLabelById(Long labelId, String email) {
        Account user = getAccount(email);
        Label label = labelRepository.findById(labelId).get();
        return LabelMapper.toResponse(label);
    }

    @Override
    public LabelResponse updateLabel(Long labelId, LabelRequest request, String email) {
        // 1. Lấy current user
        Account user = getAccount(email);

        // 2. Tìm Label thuộc user này
        Label label = labelRepository.findByIdAndUser_Id(labelId, user.getId())
                .orElseThrow(() -> new RuntimeException("Label not found"));

        //3 update
        label.setName(request.getName());
        label.setColorHex(request.getColorHex());
        //4 saved
        Label savedLabel = labelRepository.save(label);
        return LabelMapper.toResponse(savedLabel);
    }

    @Override
    public void deleteLabel(String labelName, String email) {
        Account user = getAccount(email);
        Label label = labelRepository.findByUser_IdAndNameIgnoreCase(user.getId(), labelName)
                .orElseThrow(() -> new RuntimeException("Label not found"));
        labelRepository.delete(label);
    }
    private Account getAccount(String email) {
        return accountRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}



