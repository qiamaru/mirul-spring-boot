package com.bank.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchDTO {

    private Long branchID;
    private String branchName;
    private String branchPostCode;
    private LocalDateTime creationDate;
    private Set<AccountDTO> accounts = new HashSet<>();
}