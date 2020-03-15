/**
 * Created as part of Osmosis 2020.
 */
package com.mindtree.minto.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.mindtree.minto.dto.BookingDTO;
import com.mindtree.minto.dto.ConfirmBalance;
import com.mindtree.minto.dto.ConfirmBooking;
import com.mindtree.minto.dto.ConfirmLoginStatusDTO;
import com.mindtree.minto.dto.ConfirmUserDTO;
import com.mindtree.minto.dto.ConfirmWalletID;
import com.mindtree.minto.dto.ExpenseDTO;
import com.mindtree.minto.dto.LoginDTO;
import com.mindtree.minto.dto.PackageDTO;
import com.mindtree.minto.dto.PaymentDTO;
import com.mindtree.minto.dto.ReconcileReports;
import com.mindtree.minto.dto.ReconciliationReport;
import com.mindtree.minto.dto.RegisterUserDTO;
import com.mindtree.minto.dto.Transactions;
import com.mindtree.minto.dto.TravelInfoDTO;
import com.mindtree.minto.dto.UserDTO;
import com.mindtree.minto.exception.AuthenticationFailureException;
import com.mindtree.minto.exception.InvalidRequestException;
import com.mindtree.minto.exception.RegistrationException;
import com.mindtree.minto.exception.TransferFailureException;

import net.sourceforge.tess4j.TesseractException;

/**
 * 
 * MintoService.java Created On: Feb 22, 2020 Created By: M1026329
 */
public interface MintoService {

    /**
     * Description : <<WRITE DESCRIPTION HERE>>
     * 
     * @param user
     * @return
     * @throws RegistrationException
     * @throws InvalidRequestException
     */
    ConfirmUserDTO registerUser(@Valid RegisterUserDTO user) throws RegistrationException;

    /**
     * Description : <<WRITE DESCRIPTION HERE>>
     * 
     * @param login
     * @return
     * @throws AuthenticationFailureException
     */
    ConfirmLoginStatusDTO checkAndLogin(@Valid LoginDTO login) throws AuthenticationFailureException;

    /**
     * Description : <<WRITE DESCRIPTION HERE>>
     * 
     * @param login
     * @return
     * @throws AuthenticationFailureException
     * @throws InvalidRequestException 
     */
    ConfirmLoginStatusDTO faceIdLoginImpl(String email, String faceId) throws AuthenticationFailureException, InvalidRequestException;

    /**
     * Description : <<WRITE DESCRIPTION HERE>>
     * 
     * @param login
     * @return
     */
    ConfirmBalance getBalance();

    /**
     * Description : <<WRITE DESCRIPTION HERE>>
     * 
     * @return
     */
    ResponseEntity<Object> getTransactions();

    /**
     * Description : <<WRITE DESCRIPTION HERE>>
     * 
     * @param emailID
     * @return
     * @throws InvalidRequestException
     */
    ConfirmBalance getUserBalance(String emailID) throws InvalidRequestException;

    /**
     * Description : 
     * <<WRITE DESCRIPTION HERE>>
     * @return 
     * @throws RegistrationException 
     * 
     */
    ConfirmWalletID createNewWalletOnBlockChain() throws RegistrationException;

    /**
     * Description : 
     * <<WRITE DESCRIPTION HERE>>
     * 
     * @param emailID
     * @return
     */
    List<Transactions> getSpecificTokenTransactions(String emailID);

    /**
     * Description : 
     * <<WRITE DESCRIPTION HERE>>
     * 
     * @return
     * @throws InvalidRequestException 
     */
    ReconciliationReport generateReconciliationReport(String emailID) throws InvalidRequestException;

    /**
     * Description : 
     * <<WRITE DESCRIPTION HERE>>
     * 
     * @return
     * @throws InvalidRequestException 
     */
    ReconcileReports generateReconcileReport(String emailID) throws InvalidRequestException;

    
    /**
     * Description : 
     * <<WRITE DESCRIPTION HERE>>
     * @param emailID 
     * 
     * @return
     */
    ConfirmBooking initiateTransfers(Integer packageID, String emailID);
    
    /**
     * @param packageDTO
     * @return ConfirmBooking
     * @throws AuthenticationFailureException 
     * @throws InvalidRequestException 
     * @throws TransferFailureException 
     */
    ConfirmBooking processBooking(PackageDTO packageDTO) throws AuthenticationFailureException, InvalidRequestException, TransferFailureException;
    /**
     * @param email
     * @return String
     */
    List<TravelInfoDTO> getTravelInfo(String email);
    /**
     * @param date
     * @return Booking
     */
    List<BookingDTO> getBookings(String date);
    
    UserDTO getUser(String email) throws AuthenticationFailureException;

	ConfirmBooking makePayment(@Valid PaymentDTO paymentDTO) throws AuthenticationFailureException, InvalidRequestException, TransferFailureException;

	void addExpense(@Valid List<ExpenseDTO> expenses) throws InvalidRequestException, TesseractException;

}
