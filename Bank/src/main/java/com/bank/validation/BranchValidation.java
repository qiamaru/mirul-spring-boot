package com.bank.validation;

import com.demo.exceptions.DemoAppException;

public class BranchValidation {

	private BranchValidation() {
		throw new DemoAppException("Utility Class Only");
	}
	public static void validateBranchName(String itemName) {
		if (itemName == null || itemName.isBlank()) {
			throw new DemoAppException("Branch name cannot be empty or blank.");
		}
	}
}
