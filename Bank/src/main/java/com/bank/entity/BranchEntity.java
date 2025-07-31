package com.bank.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "branch")
@NoArgsConstructor
@AllArgsConstructor
public class BranchEntity implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "branchID")
	private Long branchID;

	@Column(name = "branchName", nullable = false, length = 100)
	private String branchName;

	@Column(name = "branchPostCode", nullable = false, length = 30)
	private String branchPostCode;

	@Column(name = "creationDate", nullable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	private LocalDateTime creationDate;

	@PrePersist
	protected void onCreate() {
		if (this.creationDate == null) {
			this.creationDate = LocalDateTime.now();
		}
	}

	// Many-to-Many Relationship Setup 
	// ===============================
	
	// BranchEntity.java - (Add Getter and Setter annotation) - See above
	// =================

	// AccountEntity.java (Add Getter and Setter annotation)
	// ==================
	// Defines a many-to-many relationship between AccountEntity and BranchEntity.
	// @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "accounts")
	// private Set<BranchEntity> branchesEntities = new HashSet<>();

	// IAccountService.java - Method to Insert Many-to-Many Relationship
	// =================================================================
	// Used to create and associate an AccountEntity with a BranchEntity by branch ID.
	// BranchEntity createAccountWithBranch(long branchID, AccountEntity account);

	// IBranchService.java - Method to Retrieve Branch with Associated Accounts
	// ========================================================================
	// Retrieves all branches along with their associated accounts.
	// List<BranchEntity> getAllBranch();

	// IAccountService.java - Method to Retrieve Accounts by Branch
	// ============================================================
	// Not available in the current implementation. A custom mapper or query is required.
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "account_branch", joinColumns = { @JoinColumn(name = "account_id") }, inverseJoinColumns = {
			@JoinColumn(name = "branch_id") })
	private Set<AccountEntity> accounts = new HashSet<>();
}