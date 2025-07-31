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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@Entity
@Table(name = "account")
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntity implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_id")
	private Long accountID;

	@Column(name = "account_number", nullable = false, unique = true)
	private String accountNumber;

	@Column(name = "balance")
	private Double balance;

	// @ManyToOne(optional = false, cascade = {CascadeType.PERSIST,
	// CascadeType.MERGE}) // account must have a customer

	// @ManyToOne(optional = false, cascade = {CascadeType.PERSIST}) // account must
	// have a customer

	@ManyToOne(optional = false) // multiple accounts can share the same product
	@JoinColumn(name = "product_id", nullable = false)
	private ProductEntity productEntity;

	@ManyToOne(optional = false) // account must have a customer
	@JoinColumn(name = "customer_id", nullable = false)
	private CustomerEntity customerEntity;

	@Column(name = "creation_date", nullable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	private LocalDateTime creationDate;

	// Set creationDate to current time if it's null before persisting
	@PrePersist
	protected void onCreate() {
		if (this.creationDate == null) {
			this.creationDate = LocalDateTime.now();
		}
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "accounts")
	private Set<BranchEntity> branchesEntities = new HashSet<>();
}
