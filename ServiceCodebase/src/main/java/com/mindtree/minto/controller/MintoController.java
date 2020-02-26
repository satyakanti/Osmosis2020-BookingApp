/**
 * Created as part of Sabre hackathon 2019.
 */
package com.mindtree.minto.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mindtree.minto.dto.BookingDTO;
import com.mindtree.minto.dto.ConfirmBalance;
import com.mindtree.minto.dto.ConfirmBooking;
import com.mindtree.minto.dto.ConfirmLoginStatusDTO;
import com.mindtree.minto.dto.ConfirmUserDTO;
import com.mindtree.minto.dto.ConfirmWalletID;
import com.mindtree.minto.dto.FaceIdDTO;
import com.mindtree.minto.dto.LoginDTO;
import com.mindtree.minto.dto.PackageDTO;
import com.mindtree.minto.dto.ReconciliationReport;
import com.mindtree.minto.dto.RegisterUserDTO;
import com.mindtree.minto.dto.UserDTO;
import com.mindtree.minto.exception.AuthenticationFailureException;
import com.mindtree.minto.exception.InvalidRequestException;
import com.mindtree.minto.exception.RegistrationException;
import com.mindtree.minto.exception.TransferFailureException;
import com.mindtree.minto.service.MintoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * MintoController.java Created On: Feb 22, 2020 Created By: M1026329
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Api(value = "Minto App", description = "Operations pertaining User Registration and Login")
public class MintoController {

    @Autowired
    MintoService mintoService;

    @ApiOperation(value = "Register user", response = ConfirmUserDTO.class)
    @PostMapping(value = "/api/registerUser")
    public ResponseEntity<ConfirmUserDTO> registerUser(@Valid @RequestBody RegisterUserDTO user) throws RegistrationException {
        ConfirmUserDTO registeredUser = mintoService.registerUser(user);
        if (registeredUser != null) {
            return new ResponseEntity<ConfirmUserDTO>(registeredUser, HttpStatus.OK);
        }
        else {
            throw new RegistrationException("DB Issue Couldn't register user");
        }
    }

    @ApiOperation(value = "Login user with username and password", response = ConfirmLoginStatusDTO.class)
    @PostMapping(value = "/api/login")
    public ResponseEntity<ConfirmLoginStatusDTO> login(@RequestBody @Valid LoginDTO login)
        throws AuthenticationFailureException {
        ConfirmLoginStatusDTO loginStatus = mintoService.checkAndLogin(login);
        return new ResponseEntity<ConfirmLoginStatusDTO>(loginStatus, HttpStatus.OK);

    }

    @ApiOperation(value = "Login user with faceID", response = ConfirmLoginStatusDTO.class)
    @PostMapping(value = "/api/faceIdLogin")
    public ResponseEntity<ConfirmLoginStatusDTO> loginByFaceId(@RequestBody @Valid FaceIdDTO login)
        throws AuthenticationFailureException, InvalidRequestException {
    	ConfirmLoginStatusDTO loginStatus = mintoService.faceIdLoginImpl(login.getEmail(), login.getFaceId());
        return new ResponseEntity<ConfirmLoginStatusDTO>(loginStatus, HttpStatus.OK);

    }

    @ApiOperation(value = "Server health checkup", response = ConfirmLoginStatusDTO.class)
    @GetMapping(path = "/api/status")
    public String getServerStatus() {
        return "Server is up and running.";
    }

    @ApiOperation(value = "Wallet ID using API URL")
    @GetMapping(path = "/api/walletId")
    public ResponseEntity<ConfirmWalletID> getWalletId(String apiURL) throws RegistrationException {
        return new ResponseEntity<ConfirmWalletID>(mintoService.createNewWalletOnBlockChain(), HttpStatus.OK);
    }

    @ApiOperation(value = "Get Mindtree Account's Current Balance")
    @GetMapping(value = "/api/getBalance")
    public ResponseEntity<ConfirmBalance> getBalance() {
        return new ResponseEntity<ConfirmBalance>(mintoService.getBalance(), HttpStatus.OK);
    }

    @ApiOperation(value = "Get last 25 transactions of the consortia")
    @GetMapping(value = "/api/getTransactions")
    public ResponseEntity<Object> getLastTransactions() {
        return mintoService.getTransactions();
    }

    @ApiOperation(value = "Get Reconciliation report")
    @GetMapping(value = "/api/getReconReport/{emailID}")
    public ResponseEntity<ReconciliationReport> generateReport(@PathVariable("emailID") String emailID)
        throws InvalidRequestException {
        ReconciliationReport report;
        try {
            report = mintoService.generateReconciliationReport(emailID.toLowerCase());
        }
        catch (InvalidRequestException e) {
            throw new InvalidRequestException("Wallet ID Not Found in DB");
        }
        return new ResponseEntity<ReconciliationReport>(report, HttpStatus.OK);
    }

    @ApiOperation(value = "Get Logged In User's Transactions")
    @GetMapping(value = "/api/getSpecificTransactions/{emailID}")
    public ResponseEntity<Object> getSpecificTokenTransactions(@PathVariable("emailID") String emailID) {
        return new ResponseEntity<Object>(mintoService.getSpecificTokenTransactions(emailID), HttpStatus.OK);
    }

    @ApiOperation(value = "Get Logged In User's Balance")
    @GetMapping(value = "/api/getUserBalance/{emailID}")
    public ResponseEntity<ConfirmBalance> getUserBalance(@PathVariable("emailID") String emailID)
        throws InvalidRequestException {
        return new ResponseEntity<ConfirmBalance>(mintoService.getUserBalance(emailID), HttpStatus.OK);
    }

    @ApiOperation(value = "")
    @GetMapping(value = "/api/bookPackage/{packageID}/{emailID}")
    public ResponseEntity<ConfirmBooking> bookPackage(@PathVariable("packageID") Integer packageID, @PathVariable("emailID") String emailID) {
        ConfirmBooking confirmBooking = mintoService.initiateTransfers(packageID, emailID);
        return new ResponseEntity<ConfirmBooking>(confirmBooking, HttpStatus.OK);
    }

    @ApiOperation(value = "book package", response = ConfirmBooking.class)
    @PostMapping(value = "/api/bookPackage")
    public ResponseEntity<ConfirmBooking> bookPackage(@RequestBody @Valid PackageDTO packageDTO)
        throws AuthenticationFailureException, InvalidRequestException, TransferFailureException {
    	ConfirmBooking confirmBooking = mintoService.processBooking(packageDTO);
        return new ResponseEntity<ConfirmBooking>(confirmBooking, HttpStatus.OK);

    }
    
    @ApiOperation(value = "")
    @GetMapping(value = "/api/getTravleInfo/{emailID}")
    public ResponseEntity<List<String>> getTravleInfo(@PathVariable("emailID") String emailID) {
        List<String> travelInfos = mintoService.getTravelInfo(emailID);
        return new ResponseEntity<List<String>>(travelInfos, HttpStatus.OK);
    }
    
    @ApiOperation(value = "")
    @GetMapping(value = "/api/bookings/{travelDate}")
    public ResponseEntity<List<BookingDTO>> getBookings(@PathVariable("travelDate") String travelDate) {
        List<BookingDTO> bookings = mintoService.getBookings(travelDate);
        return new ResponseEntity<List<BookingDTO>>(bookings, HttpStatus.OK);
    }
    
    @ApiOperation(value = "")
    @GetMapping(value = "/api/user/{email}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("email") String email) throws AuthenticationFailureException {
        UserDTO user = mintoService.getUser(email);
        return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
    }
    
    /**
     * @return the sabreService
     */
    public MintoService getSabreService() {
        return mintoService;
    }

    /**
     * @param sabreService
     *            the sabreService to set
     */
    public void setSabreService(MintoService sabreService) {
        this.mintoService = sabreService;
    }

}
