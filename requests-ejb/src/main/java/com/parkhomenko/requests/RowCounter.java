package com.parkhomenko.requests;

import java.io.Serializable;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

/**
 * 
 * Provides a counter to be used in the "No" field of the html table
 *
 */
@Name("rowCounter")
@Scope(ScopeType.EVENT)
public class RowCounter implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int row = 0;

	public int getRow() {
		return ++row;
	}

	public void setRow(int row) {
		this.row = row;
	}
}