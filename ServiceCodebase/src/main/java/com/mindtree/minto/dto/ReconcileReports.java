/**
 * 
 */
package com.mindtree.minto.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author M1026329
 *
 */
public class ReconcileReports {

	List<ReconcileReport> reconcileReports = new ArrayList<ReconcileReport>();

	/**
	 * @return the reconcileReports
	 */
	public List<ReconcileReport> getReconcileReports() {
		return reconcileReports;
	}

	/**
	 * @param reconcileReports the reconcileReports to set
	 */
	public void setReconcileReports(List<ReconcileReport> reconcileReports) {
		this.reconcileReports = reconcileReports;
	}
	
	
}
