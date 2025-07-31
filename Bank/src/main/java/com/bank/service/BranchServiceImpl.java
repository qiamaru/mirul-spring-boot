package com.bank.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bank.entity.BranchEntity;
import com.bank.repo.IBranchRepo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BranchServiceImpl implements IBranchService {

    private final IBranchRepo branchRepo;
    
    @Override
    public BranchEntity createBranch(BranchEntity branch) {
        return branchRepo.save(branch);
    }

    @Override
    public BranchEntity getBranchById(Long id) {
        return branchRepo.findById(id).get();
    }

    @Override
    public List<BranchEntity> getAllBranch() {
        return branchRepo.findAll();
    }

    @Override
    public List<BranchEntity> searchBranchByName (String name){
    	return branchRepo.findByBranchNameContainingIgnoreCase(name);
    }
    
    @Override
    public List<BranchEntity> searchBranchByCreationDateBetween(LocalDateTime from, LocalDateTime to) {
    	return branchRepo.findByCreationDateBetween(from,to);
    }

    @Override
    public void deleteBranch(Long id) {
        if (!branchRepo.existsById(id)) {
        	
        }
        branchRepo.deleteById(id);
    }
}