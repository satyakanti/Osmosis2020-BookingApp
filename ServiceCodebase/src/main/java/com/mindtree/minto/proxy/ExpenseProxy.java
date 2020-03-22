/**
 * 
 */
package com.mindtree.minto.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.mindtree.minto.dto.InvoiceInfo;
import com.mindtree.minto.dto.InvoiceInfoRequest;

import net.sourceforge.tess4j.TesseractException;

/**
 * @author M1026334
 *
 */
@FeignClient(name="Payment-service", url="${minto-pay.expenseAPI}")
public interface ExpenseProxy {
	
	@PostMapping("/api/getInvoiceInfo")
	public InvoiceInfo getIvoiceInfo(@RequestBody InvoiceInfoRequest invoiceInfoReq) throws TesseractException;

}
