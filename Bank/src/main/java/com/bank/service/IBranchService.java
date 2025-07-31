package com.bank.service;

import java.util.List;

import com.bank.entity.BranchEntity;
import java.time.LocalDateTime;

public interface IBranchService {
    BranchEntity createBranch(BranchEntity account);
    BranchEntity getBranchById(Long id);
    List<BranchEntity> getAllBranch();
    List<BranchEntity> searchBranchByName(String name);
    List<BranchEntity> searchBranchByCreationDateBetween(LocalDateTime from, LocalDateTime to);
    void deleteBranch(Long id);
}