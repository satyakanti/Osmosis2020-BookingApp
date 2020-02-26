/**
 * Created as part of Osmosis 2020.
 */
package com.mindtree.minto.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.mindtree.minto.dto.BookingDTO;
import com.mindtree.minto.dto.ConfirmBalance;
import com.mindtree.minto.dto.ConfirmBooking;
import com.mindtree.minto.dto.ConfirmLoginStatusDTO;
import com.mindtree.minto.dto.ConfirmUserDTO;
import com.mindtree.minto.dto.ConfirmWalletID;
import com.mindtree.minto.dto.Events;
import com.mindtree.minto.dto.LoginDTO;
import com.mindtree.minto.dto.PackageBookingDTO;
import com.mindtree.minto.dto.PackageDTO;
import com.mindtree.minto.dto.ReconciliationReport;
import com.mindtree.minto.dto.RegisterUserDTO;
import com.mindtree.minto.dto.TransactionResultSet;
import com.mindtree.minto.dto.Transactions;
import com.mindtree.minto.dto.TravelInfoDTO;
import com.mindtree.minto.dto.TravellerDTO;
import com.mindtree.minto.dto.UserDTO;
import com.mindtree.minto.exception.AuthenticationFailureException;
import com.mindtree.minto.exception.InvalidRequestException;
import com.mindtree.minto.exception.RegistrationException;
import com.mindtree.minto.exception.TransferFailureException;
import com.mindtree.minto.model.Booking;
import com.mindtree.minto.model.TravelInfo;
import com.mindtree.minto.model.Traveller;
import com.mindtree.minto.model.User;
import com.mindtree.minto.repository.BookingRepository;
import com.mindtree.minto.repository.UserRepository;
import com.mindtree.minto.util.ConfigReader;

/**
 * 
 * MintoServiceImpl.java Created On: Feb 22, 2020 Created By: M1026329
 */
@Service
public class MintoServiceImpl implements MintoService {

    @Autowired
    UserRepository userDAO;
    
    @Autowired
    BookingRepository bookingDAO;

    @Autowired
    RestTemplate restTemplate;

    /**
     * Description : <<WRITE DESCRIPTION HERE>>
     * 
     * @param user
     * @return
     * @throws RegistrationException
     * @throws InvalidRequestException
     */
    @Override
    public ConfirmUserDTO registerUser(RegisterUserDTO userDTO) throws RegistrationException {
        if (userDTO.getWalletID() == null) {
            ConfirmWalletID confirmWalletID = createNewWalletOnBlockChain();
            if (confirmWalletID != null) {
                userDTO.setWalletID(confirmWalletID.getAddress());
            }
        }
        User savedUser = saveUserToDB(userDTO);
        fundWalletWithInitialTokens(savedUser);
        return mapSavedUserToDTO(savedUser);
    }

    /**
     * Description : <<WRITE DESCRIPTION HERE>>
     * 
     * @param savedUser
     */
    private void fundWalletWithInitialTokens(User savedUser) {
        if (savedUser != null && !StringUtils.isEmpty(savedUser.getWalletID())) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBasicAuth(ConfigReader.getAuthUserName(), ConfigReader.getAuthPWD());
            String requestJSON = "{\"toAddress\" : \"" + savedUser.getWalletID() + "\", \"amount\" : " + 10 + "}";
            HttpEntity<String> request = new HttpEntity<String>(requestJSON, headers);
            try {
                restTemplate.postForObject(createFundWalletURL(), request, Object.class);
            }
            catch (Exception ex) {

            }
        }
    }

    /**
     * Description : <<WRITE DESCRIPTION HERE>>
     * 
     * @return
     */
    private String createFundWalletURL() {
        return ConfigReader.getFundWalletURL().concat(ConfigReader.getCoinContractID()).concat("/transfer");
    }

    /**
     * Description : <<WRITE DESCRIPTION HERE>>
     * 
     * @param userDTO
     * @return
     * @throws RegistrationException
     */
    private User saveUserToDB(RegisterUserDTO userDTO) throws RegistrationException {
        User savedUser = null;
		if (userDTO.getUserRole() == null) {
			userDTO.setUserRole("User");
		}
        User user = mapDTOtoEntity(userDTO);
        try {
            savedUser = userDAO.save(user);
        }
        catch (DataIntegrityViolationException e) {
            throw new RegistrationException("User with username " + user.getEmail() + " already exists!");
        }
        catch (Exception e) {
            throw new RegistrationException("Couldn't register user");
        }
        return savedUser;
    }

    /**
     * Description : <<WRITE DESCRIPTION HERE>>
     * 
     * @param regUserserDTO
     * @return
     */
    private User mapDTOtoEntity(RegisterUserDTO regUserserDTO) {
        User user = new User();
        user.setEmail(regUserserDTO.getEmail());
        user.setFaceID(regUserserDTO.getFaceId());
        user.setPswrd(regUserserDTO.getPassword());
        user.setUserRole(regUserserDTO.getUserRole());
        user.setWalletID(regUserserDTO.getWalletID());
        user.setFirstName(regUserserDTO.getFirstName());
        user.setLastName(regUserserDTO.getLastName());
        user.setDateOfBirth(regUserserDTO.getDateOfBirth());
        user.setContact(regUserserDTO.getContact());
		if (regUserserDTO.getFirstName() != null || regUserserDTO.getLastName() != null) {
			Traveller traveller = new Traveller();
			traveller.setFirstName(regUserserDTO.getFirstName());
			traveller.setLastName(regUserserDTO.getLastName());
			traveller.setDateOfBirth(regUserserDTO.getDateOfBirth());
			traveller.setContact(regUserserDTO.getContact());
			traveller.setGender(regUserserDTO.getGender());
			traveller.setPassportNo(regUserserDTO.getPassportNo());
			traveller.setIssueCountry(regUserserDTO.getIssuingCountry());
			traveller.setIssueDate(regUserserDTO.getIssuingDate());
			traveller.setExpiryDate(regUserserDTO.getExpiryDate());
			traveller.setUser(user);
			Set<Traveller> travellers = new HashSet<Traveller>();
			travellers.add(traveller);
			user.setTravellers(travellers);
		}
		user.setRegistrationDate(new Date());
        return user;
    }

    /**
     * Description : <<WRITE DESCRIPTION HERE>>
     * 
     * @param savedUser
     * @return
     */
    private ConfirmUserDTO mapSavedUserToDTO(User savedUser) {
        ConfirmUserDTO confirmUser = null;
        if (savedUser != null) {
            confirmUser = new ConfirmUserDTO();
            confirmUser.setUserName(savedUser.getEmail());
            confirmUser.setRole(savedUser.getUserRole());
            confirmUser.setResult("User with email " + savedUser.getEmail() + " registered successfully!");
        }
        return confirmUser;
    }

    /**
     * Description : <<WRITE DESCRIPTION HERE>>
     * 
     * @param login
     * @return
     * @throws AuthenticationFailureException
     */
    @Override
    public ConfirmLoginStatusDTO checkAndLogin(@Valid LoginDTO login) throws AuthenticationFailureException {
    	ConfirmLoginStatusDTO confirmLoginStatusDTO = null;
        try {
        	List<User> users = userDAO.getUsers(login.getEmail(), login.getPassword());
        	if (users != null && !users.isEmpty()) {
        		confirmLoginStatusDTO = new ConfirmLoginStatusDTO();
        		confirmLoginStatusDTO.setMessage("Authentication Successful");
        		mapEntityToDTO(users.get(0), confirmLoginStatusDTO);
        	}
        	else {
        		throw new AuthenticationFailureException("User Not Authorized");
        	}
        }
        catch (Exception e) {
            throw new AuthenticationFailureException("Login Failed");
        }
        return confirmLoginStatusDTO;
    }


    /**
     * Description : <<WRITE DESCRIPTION HERE>>
     * 
     * @param login
     * @return
     * @throws AuthenticationFailureException
     * @throws InvalidRequestException
     */
    @Override
    public ConfirmLoginStatusDTO faceIdLoginImpl(String email, String faceId)
        throws AuthenticationFailureException, InvalidRequestException {
        ResponseEntity<Object> result = null;
        User user = null;
        ConfirmLoginStatusDTO confirmLoginStatusDTO = null;
        try {
        	List<User> users = userDAO.getUsers(email);
        	if (users != null && !users.isEmpty()) {
        		user = users.get(0);
        		if (user != null) {
        			String faceID1 = user.getFaceID();
                    HttpHeaders headers = new HttpHeaders();
                    MediaType mediaType = new MediaType("application", "json");
                    headers.setContentType(mediaType);
                    headers.set("Ocp-Apim-Subscription-Key", ConfigReader.getFaceAPISubscriptionKey());
                    String requestJson = "{\"faceId1\": \"" + faceID1 + "\",\"faceId2\": \"" + faceId + "\"}";
                    HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);

                    try {
                        result = restTemplate.exchange(ConfigReader.getMicrosoftFaceAPI(), HttpMethod.POST, entity,
                            Object.class);
                    }
                    catch (RestClientException e) {
                    	e.printStackTrace();
                        throw new AuthenticationFailureException("Couldn't match face");
                    }
        		}
        	}
        	else {
        		throw new AuthenticationFailureException("User Not Registered");
        	}
        }
        catch (Exception e) {
            throw new AuthenticationFailureException("Login Failed");
        }
        if (result != null) {
            confirmLoginStatusDTO = new ConfirmLoginStatusDTO();
    		confirmLoginStatusDTO.setMessage("Authentication Successful");
    		mapEntityToDTO(user, confirmLoginStatusDTO);
        }
        else {
            throw new AuthenticationFailureException("Face ID Authentication Failed");
        }
        return confirmLoginStatusDTO;
    }

    /**
     * Description : <<WRITE DESCRIPTION HERE>>
     * 
     * @param login
     * @return
     */
    @Override
    public ConfirmBalance getBalance() {
        ConfirmBalance confirmBalance = null;
        System.out.println(createBalanceURL());
        ResponseEntity<Object> response = restTemplate.exchange(createBalanceURL(), HttpMethod.GET,
            setHeaderAndAuthToken(), Object.class);
        if (response != null && response.getBody() != null) {
            Map<String, String> responseMap = (Map<String, String>) response.getBody();
            confirmBalance = new ConfirmBalance(responseMap.get("balance"));
        }
        return confirmBalance;
    }

    /**
     * Description : <<WRITE DESCRIPTION HERE>>
     * 
     * @return
     */
    private String createBalanceURL() {
        return ConfigReader.getBalanceURL().concat(ConfigReader.getCoinContractID()).concat("/balanceOf/")
            .concat(ConfigReader.getMasterWalletID());
    }

    /**
     * Description : <<WRITE DESCRIPTION HERE>>
     * 
     * @return
     */
    @Override
    public ResponseEntity<Object> getTransactions() {
        return restTemplate.exchange(ConfigReader.getTransactionsURL(), HttpMethod.GET, setHeaderAndAuthToken(),
            Object.class);
    }

    /**
     * Description : <<WRITE DESCRIPTION HERE>>
     * 
     * @param emailID
     * @return
     * @throws InvalidRequestException
     */
    @Override
    public ConfirmBalance getUserBalance(String emailID) throws InvalidRequestException {
    	Optional<String> walletID = userDAO.getWalletID(emailID.toUpperCase());
    	if (walletID.isPresent()) {
    		return getBalance(walletID.get());
    	}
    	else {
    		throw new InvalidRequestException("User Not Registered");
    	}
    }

	private ConfirmBalance getBalance(String walletId) throws InvalidRequestException {
		StringBuffer url = generateURLWithWalletID(walletId);
		return callBlockChainAndGenerateResponse(url);
	}

    /**
     * Description : <<WRITE DESCRIPTION HERE>>
     * 
     * @param confirmBalance
     * @param url
     * @return
     */
    private ConfirmBalance callBlockChainAndGenerateResponse(StringBuffer url) {
        ConfirmBalance confirmBalance = null;
        ResponseEntity<Object> response = restTemplate.exchange(url.toString(), HttpMethod.GET, setHeaderAndAuthToken(),
            Object.class);
        if (response != null && response.getBody() != null) {
            Map<String, String> responseMap = (Map<String, String>) response.getBody();
            confirmBalance = new ConfirmBalance(responseMap.get("balance"));

        }
        return confirmBalance;
    }

    /**
     * Description : <<WRITE DESCRIPTION HERE>>
     * 
     * @param emailID
     * @return
     * @throws InvalidRequestException
     */
    private StringBuffer generateURLWithWalletID(String walletId) throws InvalidRequestException {
        // StringBuffer url = new StringBuffer(
        // "https://console-ko.kaleido.io/api/v1/ledger/k0z7nyu64x/k0xpgllhtz/tokens/contracts/0xe9eF258925b3A6B57c346785EEee063d199a3950/balanceOf");
        StringBuffer url = new StringBuffer(ConfigReader.getBalanceURL()).append(ConfigReader.getCoinContractID())
            .append("/balanceOf").append("/").append(walletId);
        return url;
    }

    /**
     * Description : <<WRITE DESCRIPTION HERE>>
     * 
     * @return
     */
    private HttpEntity<String> setHeaderAndAuthToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(ConfigReader.getBearerToken());
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        return entity;
    }

    /**
     * Description : <<WRITE DESCRIPTION HERE>>
     * 
     * @return
     * @throws RegistrationException
     */
    @Override
    public ConfirmWalletID createNewWalletOnBlockChain() throws RegistrationException {
        ConfirmWalletID confirmWID = null;
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = new MediaType("application", "json");
        headers.setContentType(mediaType);
        headers.set("Authorization", "Bearer " + ConfigReader.getBearerToken());
        String requestJson = "{ \"password\": \"anything\" }";
        HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
        ResponseEntity<Object> result;
        try {
            result = restTemplate.exchange(ConfigReader.getWalletCreationURL(), HttpMethod.POST, entity, Object.class);
            if (result != null && result.getBody() != null) {
                Map<String, String> responseMap = (Map<String, String>) result.getBody();
                confirmWID = new ConfirmWalletID(responseMap.get("address"));
            }
        }
        catch (RestClientException e) {
        	e.printStackTrace();
            throw new RegistrationException("Couldn't create wallet account");
        }
        return confirmWID;
    }

    /**
     * Description : <<WRITE DESCRIPTION HERE>>
     * 
     * @param emailID
     * @return
     */
    @Override
    public List<Transactions> getSpecificTokenTransactions(String emailID) {
        List<Transactions> filteredTransactions = null;
        StringBuilder prefixedWalletID = new StringBuilder("0x");
        Optional<String> walletID = userDAO.getWalletID(emailID);
        if (walletID.isPresent()) {
            prefixedWalletID.append(walletID.get());
            ResponseEntity<TransactionResultSet> last25Transactions = restTemplate.exchange(
                ConfigReader.getBalanceURL().concat(ConfigReader.getCoinContractID().concat("/transfers")),
                HttpMethod.GET, setHeaderAndAuthToken(), TransactionResultSet.class);
            filteredTransactions = new ArrayList<Transactions>();
            for (Transactions transaction : last25Transactions.getBody().getTransactions()) {
                if (String.valueOf(prefixedWalletID).equalsIgnoreCase(transaction.getFrom())
                    || String.valueOf(prefixedWalletID).equalsIgnoreCase(transaction.getTo())) {
                    filteredTransactions.add(transaction);
                }
                else {
                    for (Events event : transaction.getEvents()) {
                        if (String.valueOf(prefixedWalletID).equalsIgnoreCase(event.getFrom())
                            || String.valueOf(prefixedWalletID).equalsIgnoreCase(event.getTo())) {
                            filteredTransactions.add(transaction);
                            break;
                        }
                    }
                }
            }
        }
        return filteredTransactions;
    }

    /**
     * Description : <<WRITE DESCRIPTION HERE>>
     * 
     * @return
     * @throws InvalidRequestException
     */
    @Override
    public ReconciliationReport generateReconciliationReport(String emailID) throws InvalidRequestException {
        ReconciliationReport report = new ReconciliationReport();
        ConfirmBalance sabreBalance = null;
        ConfirmBalance uberBalance = null;
        ConfirmBalance marriotBalance = null;
        ConfirmBalance deltaBalance = null;
        switch (emailID) {
            case "sabre.admin@sabrepay.com":
                sabreBalance = getUserBalance("sabre.admin@sabrepay.com");
                uberBalance = getUserBalance("uber.cabs@sabrepay.com");
                marriotBalance = getUserBalance("marriot.hotel@sabrepay.com");
                deltaBalance = getUserBalance("delta.airlines@sabrepay.com");
                break;
            case "uber.cabs@sabrepay.com":
                uberBalance = getUserBalance("uber.cabs@sabrepay.com");
                break;
            case "marriot.hotel@sabrepay.com":
                marriotBalance = getUserBalance("marriot.hotel@sabrepay.com");
                break;
            case "delta.airlines@sabrepay.com":
                deltaBalance = getUserBalance("delta.airlines@sabrepay.com");
                break;
        }
        report.setDeltaBalance(deltaBalance != null ? deltaBalance.getBalance() : null);
        report.setMarriotBalance(marriotBalance != null ? marriotBalance.getBalance() : null);
        report.setSabreBalance(sabreBalance != null ? sabreBalance.getBalance() : null);
        report.setUberBalance(uberBalance != null ? uberBalance.getBalance() : null);
        return report;
    }

    /**
     * Description : <<WRITE DESCRIPTION HERE>>
     * 
     * @param packageID
     * @return
     */
    @Override
    public ConfirmBooking initiateTransfers(Integer packageID, String emailID) {
        String transferURL = createURL();
        Optional<String> loggedInUserWalletID = userDAO.getWalletID(emailID);
        Optional<String> deltaAirlinesWalletID = userDAO.getWalletID("delta.airlines@sabrepay.com");
        Optional<String> uberCabsWalletID = userDAO.getWalletID("Uber.cabs@sabrepay.com");
        Optional<String> marriotHotelWalletID = userDAO.getWalletID("marriot.hotel@sabrepay.com");
        Integer flightCharges = 0, hotelCharges = 0, cabCharges = 0;
        switch (packageID) {
            case 1:
                flightCharges = 10;
                hotelCharges = 5;
                cabCharges = 5;
                break;
            case 2:
                flightCharges = 15;
                hotelCharges = 4;
                cabCharges = 5;
                break;
            case 3:
                flightCharges = 20;
                hotelCharges = 10;
                cabCharges = 7;
                break;
        }
        try {
            restTemplate.postForObject(transferURL,
                createRequestJsonWithHeaders(loggedInUserWalletID, deltaAirlinesWalletID, flightCharges), Object.class);
        }
        catch (Exception ex) {

        }
        try {
            restTemplate.postForObject(transferURL,
                createRequestJsonWithHeaders(loggedInUserWalletID, uberCabsWalletID, cabCharges), Object.class);
        }
        catch (Exception ex) {

        }
        try {
            restTemplate.postForObject(transferURL,
                createRequestJsonWithHeaders(loggedInUserWalletID, marriotHotelWalletID, hotelCharges), Object.class);
        }
        catch (Exception ex) {

        }
        return new ConfirmBooking("Amounts transferred successfully");
    }
    
    @Override
	public ConfirmBooking processBooking(PackageDTO packageDTO) throws AuthenticationFailureException, InvalidRequestException, TransferFailureException {
    	String email = packageDTO.getEmail();
    	User user = findUser(email);
    	if (user != null && user.getWalletID() != null) {
    		ConfirmBalance balance = getBalance(user.getWalletID());
    		if (Integer.valueOf(balance.getBalance()) > packageDTO.getTotal()) {
    			transferFunds(packageDTO, user.getWalletID());
    			
    		}
    		else {
    			throw new TransferFailureException("Insufficient Funds");
    		}
    	}
    	
		return new ConfirmBooking("Booking successfull");
	}

	private User findUser(String email) throws AuthenticationFailureException {
		User user = null;
    	try {
			List<User> users = userDAO.getUsers(email);
			if (users != null && !users.isEmpty()) {
				user = users.get(0);
			}
        	else {
        		throw new AuthenticationFailureException("User Not Registered");
        	}
        }
        catch (Exception e) {
            throw new AuthenticationFailureException("Unable to get User");
        }
		return user;
	}

	private void transferFunds(PackageDTO packageDTO, String walletId) throws TransferFailureException {
    	String transferURL = createURL();
    	for (PackageBookingDTO bookingDTO : packageDTO.getBookings()) {
    		Optional<String> partnerWalletID = userDAO.getWalletIDByName(bookingDTO.getPartner());
    		try {
    			restTemplate.postForObject(transferURL,
    					createRequestJsonWithHeaders(walletId, partnerWalletID.get(), bookingDTO.getAmount()), Object.class);
    		}
    		catch (Exception ex) {
    			throw new TransferFailureException("Transfer Filed");
    		}
    	}
	}

    /**
     * Description : <<WRITE DESCRIPTION HERE>>
     * 
     * @param loggedInUserWalletID
     * @param deltaAirlinesWalletID
     * @param flightCharges
     * @return
     */
    private HttpEntity<String> createRequestJsonWithHeaders(Optional<String> loggedInUserWalletID,
        Optional<String> toAddress, Integer amount) {
        String requestJSON = "{\"toAddress\" : \"0x" + toAddress.get() + "\", \"amount\" : " + amount
            + ", \"from\" : \"0x" + loggedInUserWalletID.get() + "\"}";
        System.out.println(requestJSON);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(ConfigReader.getAuthUserName(), ConfigReader.getAuthPWD());
        HttpEntity<String> request = new HttpEntity<String>(requestJSON, headers);
        return request;
    }
    
    /**
     * Description : <<WRITE DESCRIPTION HERE>>
     * 
     * @param loggedInUserWalletID
     * @param deltaAirlinesWalletID
     * @param flightCharges
     * @return
     */
    private HttpEntity<String> createRequestJsonWithHeaders(String loggedInUserWalletID,
        String toAddress, Integer amount) {
        String requestJSON = "{\"toAddress\" : \"0x" + toAddress + "\", \"amount\" : " + amount
            + ", \"from\" : \"0x" + loggedInUserWalletID + "\"}";
        System.out.println(requestJSON);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(ConfigReader.getAuthUserName(), ConfigReader.getAuthPWD());
        HttpEntity<String> request = new HttpEntity<String>(requestJSON, headers);
        return request;
    }

    /**
     * Description : <<WRITE DESCRIPTION HERE>>
     * 
     * @return
     */
    private String createURL() {
        StringBuilder url = new StringBuilder(ConfigReader.getFundWalletURL());
        url.append(ConfigReader.getCoinContractID()).append("/transfer");
        // TODO Auto-generated method stub
        return url.toString();
    }

	@Override
	public List<String> getTravelInfo(String email) {
		return userDAO.getTravelInfos(email);
	}

	@Override
	public List<BookingDTO> getBookings(String dateString) {
		List<Booking> bookings = null;
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyyyy");
		Date date = null;
		try {
			date = sdf.parse(dateString);
			bookings = bookingDAO.findAllByDateOfFirstSegment(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapToDTO(bookings);
	}
	
	private List<BookingDTO> mapToDTO(List<Booking> bookings) {
		List<BookingDTO> bookingDTOs = null;
		if (bookings != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyyyy");
			bookingDTOs = new ArrayList<BookingDTO>();
			for (Booking booking : bookings) {
				BookingDTO bookingDTO = new BookingDTO();
				bookingDTO.setEmail(booking.getEmail());
				bookingDTO.setFirstName(booking.getFirstName());
				bookingDTO.setLastName(booking.getLastName());
				bookingDTO.setFlight(booking.getFlightdetails());
				bookingDTO.setDateOfFirstSegment(sdf.format(booking.getDateOfFirstSegment()));
				bookingDTO.setNumberOfCheckedInBags(booking.getNumberOfCheckedInBags());
				bookingDTOs.add(bookingDTO);
			}
		}
		return bookingDTOs;
	}

	@Override
	public UserDTO getUser(String email) throws AuthenticationFailureException {
		UserDTO userDTO = null;
		User user = findUser(email);
		if (user != null) {
			userDTO = new UserDTO();
			mapEntityToDTO(user, userDTO);
		}
		return userDTO;
	}

	private void mapEntityToDTO(User user, UserDTO userDTO) {
		userDTO.setUserId(user.getUserId());
		userDTO.setRegisteredDate(user.getRegistrationDate());
		userDTO.setContact(user.getContact());
		userDTO.setDateOfBirth(user.getDateOfBirth());
		userDTO.setEmail(user.getEmail());
		userDTO.setFaceId(user.getFaceID());
		userDTO.setFirstName(user.getFirstName());
		userDTO.setLastName(user.getLastName());
		userDTO.setUserRole(user.getUserRole());
		userDTO.setWalletID(user.getWalletID());
		if (user.getTravelInfos() != null && !user.getTravelInfos().isEmpty()) {
			List<TravelInfoDTO> travelInfoDTOs = new ArrayList<TravelInfoDTO>();
			userDTO.setTravelInfos(travelInfoDTOs);
			for (TravelInfo travelInfo : user.getTravelInfos()) {
				TravelInfoDTO travelInfoDTO = new TravelInfoDTO();
				travelInfoDTO.setTravelId(travelInfo.getTravelId());
				travelInfoDTO.setTravelInfo(travelInfo.getTravelInfo());
				travelInfoDTOs.add(travelInfoDTO);
			}
		}
		if (user.getTravellers() != null && !user.getTravellers().isEmpty()) {
			List<TravellerDTO> travellerDTOs = new ArrayList<TravellerDTO>();
			userDTO.setTravellers(travellerDTOs);
			for (Traveller traveller : user.getTravellers()) {
				TravellerDTO travellerDTO = new TravellerDTO();
				travellerDTO.setTravellerId(traveller.getTravellerId());
				travellerDTO.setContact(traveller.getContact());
				travellerDTO.setFirstName(traveller.getFirstName());
				travellerDTO.setLastName(traveller.getLastName());
				travellerDTO.setDateOfBirth(traveller.getDateOfBirth());
				travellerDTO.setGender(traveller.getGender());
				travellerDTO.setPassportNo(traveller.getPassportNo());
				travellerDTO.setIssueCountry(traveller.getIssueCountry());
				travellerDTO.setIssueDate(traveller.getIssueDate());
				travellerDTO.setExpiryDate(traveller.getExpiryDate());
				travellerDTOs.add(travellerDTO);
			}
		}
	}

}
