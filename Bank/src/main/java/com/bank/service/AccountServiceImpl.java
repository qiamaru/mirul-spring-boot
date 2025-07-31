package com.bank.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bank.entity.AccountEntity;
import com.bank.entity.BranchEntity;
import com.bank.repo.IAccountRepo;
import com.bank.repo.IBranchRepo;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AccountServiceImpl implements IAccountService {

    private final IAccountRepo accountRepo;
    
    private final IBranchRepo branchRepo; 
    
    @Override
    public AccountEntity createAccount(AccountEntity account) {
        return accountRepo.save(account);
    }
    
    @Override
    public BranchEntity createAccountWithBranch(long branchID, AccountEntity account) {
    	Optional<BranchEntity> found = branchRepo.findById(branchID);
    	BranchEntity branchEntity = null;
        if (found.isPresent()) {
        	branchEntity = found.get();
        	branchEntity.getAccounts().add(account);
        	branchRepo.save(branchEntity);
        } else {
            throw new EntityNotFoundException("Branch with ID " + branchID + " not found");
        }
        
        return branchEntity;
    }
    
    @Override
    public AccountEntity getAccountById(Long id) {
        return accountRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Account not found"));
    }

    @Override
    public List<AccountEntity> getAllAccounts() {
        return accountRepo.findAll();
    }

    @Override
    public AccountEntity updateAccount(Long id, AccountEntity updated) {
        AccountEntity existing = getAccountById(id);
        existing.setAccountNumber(updated.getAccountNumber());
        existing.setBalance(updated.getBalance());
        return accountRepo.save(existing);
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepo.deleteById(id);
    }
}
