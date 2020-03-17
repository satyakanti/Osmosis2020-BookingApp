/**
 * Created as part of Osmosis 2020.
 */
package com.mindtree.minto.service;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.mindtree.minto.dto.Car;
import com.mindtree.minto.dto.ConfirmBalance;
import com.mindtree.minto.dto.ConfirmBooking;
import com.mindtree.minto.dto.ConfirmLoginStatusDTO;
import com.mindtree.minto.dto.ConfirmUserDTO;
import com.mindtree.minto.dto.ConfirmWalletID;
import com.mindtree.minto.dto.Detail;
import com.mindtree.minto.dto.DetailType;
import com.mindtree.minto.dto.Details;
import com.mindtree.minto.dto.Events;
import com.mindtree.minto.dto.ExpenseDTO;
import com.mindtree.minto.dto.Fare;
import com.mindtree.minto.dto.Flight;
import com.mindtree.minto.dto.FlightInfo;
import com.mindtree.minto.dto.FlightItinerary;
import com.mindtree.minto.dto.Hotel;
import com.mindtree.minto.dto.Insurance;
import com.mindtree.minto.dto.Invoice;
import com.mindtree.minto.dto.InvoiceInfo;
import com.mindtree.minto.dto.LoginDTO;
import com.mindtree.minto.dto.PackageBookingDTO;
import com.mindtree.minto.dto.PackageDTO;
import com.mindtree.minto.dto.PaymentDTO;
import com.mindtree.minto.dto.ReconcileReports;
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
import com.mindtree.minto.model.ExpenseInfo;
import com.mindtree.minto.model.TravelInfo;
import com.mindtree.minto.model.Traveller;
import com.mindtree.minto.model.User;
import com.mindtree.minto.repository.BookingRepository;
import com.mindtree.minto.repository.TravelInfoRepository;
import com.mindtree.minto.repository.UserRepository;
import com.mindtree.minto.util.CommonUtil;
import com.mindtree.minto.util.ConfigReader;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/**
 * 
 * MintoServiceImpl.java Created On: Feb 22, 2020 Created By: M1026329
 */
@Slf4j
@Service
public class MintoServiceImpl implements MintoService {

	@Value("${app.smtp.username}")
	private String username;
	@Value("${app.smtp.password}")
	private String password;
	@Value("${app.smtp.port}")
	private String port;
	@Value("${app.smtp.host}")
	private String host;
	@Autowired
	UserRepository userDAO;

	@Autowired
	BookingRepository bookingDAO;

	@Autowired
	TravelInfoRepository travelDAO;

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
		boolean isWalletPresent = true;
		if (userDTO.getWalletID() == null) {
			isWalletPresent = false;
			ConfirmWalletID confirmWalletID = createNewWalletOnBlockChain();
			if (confirmWalletID != null) {
				userDTO.setWalletID(confirmWalletID.getAddress());
			}
		}
		User savedUser = saveUserToDB(userDTO);
		if (!isWalletPresent) {
			fundWalletWithInitialTokens(savedUser);
		}
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
			String requestJSON = "{\"toAddress\" : \"" + savedUser.getWalletID() + "\", \"amount\" : " + 2500 + "}";
			HttpEntity<String> request = new HttpEntity<String>(requestJSON, headers);
			try {
				restTemplate.postForObject(createFundWalletURL(), request, Object.class);
			} catch (Exception ex) {

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
		} catch (DataIntegrityViolationException e) {
			throw new RegistrationException("User with username " + user.getEmail() + " already exists!");
		} catch (Exception e) {
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
		user.setContact(regUserserDTO.getContact());
		user.setLastName(regUserserDTO.getLastName());
		if (regUserserDTO.getPrimaryUser() != null) {
			user.setDateOfBirth(regUserserDTO.getPrimaryUser().getDateOfBirth());
			user.setFirstName(regUserserDTO.getPrimaryUser().getFirstName());
			user.setLastName(regUserserDTO.getPrimaryUser().getLastName());
			if ("User".equalsIgnoreCase(regUserserDTO.getUserRole())) {
				Traveller traveller = new Traveller();
				traveller.setFirstName(regUserserDTO.getPrimaryUser().getFirstName());
				traveller.setLastName(regUserserDTO.getPrimaryUser().getLastName());
				traveller.setDateOfBirth(regUserserDTO.getPrimaryUser().getDateOfBirth());
				traveller.setContact(regUserserDTO.getContact());
				traveller.setGender(regUserserDTO.getPrimaryUser().getGender());
				traveller.setPassportNo(regUserserDTO.getPrimaryUser().getPassportNo());
				traveller.setIssueCountry(regUserserDTO.getPrimaryUser().getIssuingCountry());
				traveller.setIssueDate(regUserserDTO.getPrimaryUser().getIssuingDate());
				traveller.setExpiryDate(regUserserDTO.getPrimaryUser().getExpiryDate());
				traveller.setUser(user);
				Set<Traveller> travellers = new HashSet<Traveller>();
				travellers.add(traveller);
				user.setTravellers(travellers);
			}
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
			} else {
				throw new AuthenticationFailureException("User Not Authorized");
			}
		} catch (Exception e) {
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
		User user = null;
		ConfirmLoginStatusDTO confirmLoginStatusDTO = null;
		try {
			List<User> users = userDAO.getUsers(email);
			if (users != null && !users.isEmpty()) {
				user = users.get(0);
				if (isFaceMatched(faceId, user)) {
					confirmLoginStatusDTO = new ConfirmLoginStatusDTO();
					confirmLoginStatusDTO.setMessage("Authentication Successful");
					mapEntityToDTO(user, confirmLoginStatusDTO);
				} else {
					throw new AuthenticationFailureException("Face ID Not Matched");
				}
			} else {
				throw new AuthenticationFailureException("User Not Registered");
			}
		} catch (Exception e) {
			throw new AuthenticationFailureException("Login Failed");
		}

		return confirmLoginStatusDTO;
	}

	private boolean isFaceMatched(String faceId, User user) throws AuthenticationFailureException {
		Boolean isIdentical = null;
		if (user != null) {
			String faceID1 = user.getFaceID();
			HttpHeaders headers = new HttpHeaders();
			MediaType mediaType = new MediaType("application", "json");
			headers.setContentType(mediaType);
			headers.set("Ocp-Apim-Subscription-Key", ConfigReader.getFaceAPISubscriptionKey());
			String requestJson = "{\"faceId1\": \"" + faceID1 + "\",\"faceId2\": \"" + faceId + "\"}";
			HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
			ResponseEntity<Object> result = null;
			try {
				result = restTemplate.exchange(ConfigReader.getMicrosoftFaceAPI(), HttpMethod.POST, entity,
						Object.class);
			} catch (RestClientException e) {
				e.printStackTrace();
				throw new AuthenticationFailureException("Couldn't match face");
			}
			if (result != null && result.getBody() != null) {
				Map<String, Object> responseMap = (java.util.Map<String, Object>) result.getBody();
				isIdentical = (Boolean) responseMap.get("isIdentical");
			} else {
				throw new AuthenticationFailureException("Face ID Authentication Failed");
			}
		}
		return (isIdentical != null && isIdentical);
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
		Optional<String> walletID = userDAO.getWalletID(emailID.toLowerCase());
		if (walletID.isPresent()) {
			return getBalance(walletID.get());
		} else {
			throw new InvalidRequestException("User Not Registered");
		}
	}
	
	@Override
	public String rechargeUserBalance(String emailID) throws InvalidRequestException, TransferFailureException {
		Optional<String> walletID = userDAO.getWalletID(emailID.toLowerCase());
		if (walletID.isPresent()) {
			return transferFunds(2500, ConfigReader.getMasterWalletID(), walletID.get());
			
		} else {
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
				confirmWID = new ConfirmWalletID("0x" + responseMap.get("address"));
			}
		} catch (RestClientException e) {
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
		Optional<String> walletID = userDAO.getWalletID(emailID);
		if (walletID.isPresent()) {
			ResponseEntity<TransactionResultSet> last25Transactions = restTemplate.exchange(
					ConfigReader.getBalanceURL().concat(ConfigReader.getCoinContractID().concat("/transfers")),
					HttpMethod.GET, setHeaderAndAuthToken(), TransactionResultSet.class);
			filteredTransactions = new ArrayList<Transactions>();
			for (Transactions transaction : last25Transactions.getBody().getTransactions()) {
				if (walletID.get().equalsIgnoreCase(transaction.getFrom())
						|| walletID.get().equalsIgnoreCase(transaction.getTo())) {
					filteredTransactions.add(transaction);
				} else {
					for (Events event : transaction.getEvents()) {
						if (walletID.get().equalsIgnoreCase(event.getFrom())
								|| walletID.get().equalsIgnoreCase(event.getTo())) {
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
		ConfirmBalance insuranceBalance = null;

		switch (emailID) {
		case "admin":
			sabreBalance = getUserBalance("admin@mpay.com");
			uberBalance = getUserBalance("car@mpay.com");
			marriotBalance = getUserBalance("hotel@mpay.com");
			deltaBalance = getUserBalance("airline@mpay.com");
			insuranceBalance = getUserBalance("insurance@mpay.com");
			break;
		case "admin@mpay.com":
			sabreBalance = getUserBalance("admin@mpay.com");
			uberBalance = getUserBalance("car@mpay.com");
			marriotBalance = getUserBalance("hotel@mpay.com");
			deltaBalance = getUserBalance("airline@mpay.com");
			insuranceBalance = getUserBalance("insurance@mpay.com");
			break;
		case "car@mpay.com":
			uberBalance = getUserBalance(emailID);
			break;
		case "hotel@mpay.com":
			marriotBalance = getUserBalance(emailID);
			break;
		case "airline@mpay.com":
			deltaBalance = getUserBalance(emailID);
			break;
		case "insurance@mpay.com":
			insuranceBalance = getUserBalance(emailID);
			break;
		}
		report.setDeltaBalance(deltaBalance != null ? deltaBalance.getBalance() : null);
		report.setMarriotBalance(marriotBalance != null ? marriotBalance.getBalance() : null);
		report.setSabreBalance(sabreBalance != null ? sabreBalance.getBalance() : null);
		report.setUberBalance(uberBalance != null ? uberBalance.getBalance() : null);
		report.setMakeMyTripBalance(insuranceBalance != null ? insuranceBalance.getBalance() : null);
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
					createRequestJsonWithHeaders(loggedInUserWalletID, deltaAirlinesWalletID, flightCharges),
					Object.class);
		} catch (Exception ex) {

		}
		try {
			restTemplate.postForObject(transferURL,
					createRequestJsonWithHeaders(loggedInUserWalletID, uberCabsWalletID, cabCharges), Object.class);
		} catch (Exception ex) {

		}
		try {
			restTemplate.postForObject(transferURL,
					createRequestJsonWithHeaders(loggedInUserWalletID, marriotHotelWalletID, hotelCharges),
					Object.class);
		} catch (Exception ex) {

		}
		return new ConfirmBooking("Amounts transferred successfully");
	}

	@Override
	public ConfirmBooking makePayment(@Valid PaymentDTO paymentDTO)
			throws AuthenticationFailureException, InvalidRequestException, TransferFailureException {
		User user = findUser(paymentDTO.getEmail());
		String transactionId = null;
		if (user != null && user.getWalletID() != null) {
			boolean isFaceIdMatched = isFaceMatched(paymentDTO.getFaceId(), user);
			if (isFaceIdMatched) {
				Optional<String> partnerWalletID = userDAO.getWalletIDByName(paymentDTO.getPartner());
				transactionId = transferFunds(paymentDTO.getAmount(), user.getWalletID(), partnerWalletID.get());
			}
		}

		return new ConfirmBooking("Booking successfull", transactionId);
	}

	private InvoiceInfo getInvoiceInfo(ExpenseDTO expense, byte[] bs) throws TesseractException {
		if (expense.getFileName() == null && bs == null) {
			return null;
		}
		String fileName = expense.getFileName();
		if (fileName == null) {
			fileName = "tmp.pdf";
		}
		byte[] decoder = bs;
		if (decoder == null) {
			decoder = Base64.getDecoder().decode(expense.getDocument().split(";base64,")[1]);
		}
		File tempFile = new File(System.getProperty("java.io.tmpdir") + "/" + fileName);
		try (FileOutputStream fos = new FileOutputStream(tempFile);) {
			fos.write(decoder);

		} catch (Exception e) {
			e.printStackTrace();
		}
		ITesseract instance = new Tesseract();
		try {
			URL resource = getClass().getResource("/data");
			instance.setDatapath(resource.getPath().substring(1));
			return processImgeText(instance.doOCR(tempFile));
		} finally {
			tempFile.delete();
		}
	}

	private InvoiceInfo processImgeText(String imgText) {
		System.out.println(imgText);
		InvoiceInfo resp = new InvoiceInfo();
		String[] split = imgText.split("\n");
		Optional<String> date = Arrays.stream(split).filter(str -> str.startsWith("Date : ")).findFirst();
		if (date.isPresent()) {
			SimpleDateFormat sf = new SimpleDateFormat("EEE, d MMM yyyy");
			String substring = date.get().substring("Date : ".length());
			try {
				Date parse = sf.parse(substring.trim());
				resp.setDate(parse);
			} catch (ParseException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
		}

		Optional<String> total = Arrays.stream(split).filter(str -> str.startsWith("Total Price: ")).findFirst();
		if (total.isPresent()) {
			resp.setAmmount(total.get().substring("Total Price: ".length()));
		}

		Optional<String> txId = Arrays.stream(split).filter(str -> str.startsWith("Ref: ")).findFirst();
		if (txId.isPresent()) {
			resp.setTxnId(txId.get().substring("Ref: ".length()));
		}
		for (int i = split.length - 1; i >= 0; i--) {
			String str = split[i].trim();
			if (str.startsWith("For") && str.contains(":")) {
				resp.setMerchant(str.substring(3, str.indexOf(':') - 1).trim());
				break;
			}
		}
		return resp;
	}

	@Override
	public ConfirmBooking processBooking(PackageDTO packageDTO)
			throws AuthenticationFailureException, InvalidRequestException, TransferFailureException {
		User user = findUser(packageDTO.getEmail());
		String transactionId = null;
		if (user != null && user.getWalletID() != null) {
			boolean isFaceIdMatched = isFaceMatched(packageDTO.getFaceId(), user);
			if (isFaceIdMatched) {
				ConfirmBalance balance = getBalance(user.getWalletID());
				Integer total = packageDTO.getTotal();
				if (Integer.valueOf(balance.getBalance()) > total) {
					transactionId = transferFundsToAdmin(total, user.getWalletID());
					TravelInfo travelInfo = addTravelInfo(user, packageDTO.getTravelInfo());
					createBookings(packageDTO, user);
					postProcess(packageDTO, user, transactionId, total, travelInfo.getTravelId());
				} else {
					throw new TransferFailureException("Insufficient Funds");
				}
			} else {
				throw new AuthenticationFailureException("Face ID Not Matched");
			}
		}

		return new ConfirmBooking("Booking successfull", transactionId);
	}

	private void postProcess(PackageDTO packageDTO, User user, String transactionId, Integer total,
			Integer travelId) {
		processFundsToMerchants(packageDTO);
		processInvoice(packageDTO, user, transactionId, total, travelId);
	}

	private void processInvoice(PackageDTO packageDTO, User user, String transactionId, Integer total,
			Integer travelId) {
		Thread th = new Thread(() ->  {
			try {
				byte[]  invoice = generateInvoice(packageDTO, user, travelId, transactionId, total);
				TravelInfo travelInfo = getTravelInfo(travelId);
				travelInfo.setInvoice(invoice);
				travelDAO.save(travelInfo);
				sendMail(invoice, user.getEmail());
			} catch (InvalidRequestException e) {
				e.printStackTrace();
			}
		});
		th.start();
	}

	private byte[] generateInvoice(PackageDTO packageDTO, User user, Integer travelId, String transactionId,
			Integer total) {
		Invoice invoice = new Invoice();
		invoice.setDate(CommonUtil.getNewDate());
		invoice.setBookedBy(user.getLastName() + " " + user.getFirstName());
		invoice.setEmail(user.getEmail());
		invoice.setContact(user.getContact());
		invoice.setTravelId(String.valueOf(travelId));
		invoice.setTxnId(transactionId);
		invoice.setTotalPrice(total);
		addFlightDetails(packageDTO.getFlight(), invoice);
		addHoteDetails(packageDTO.getHotel(), invoice);
		addCarDetails(packageDTO.getCar(), invoice);
		addInsuranceDetails(packageDTO.getInsurance(), invoice);
		return createInvoice(invoice);
	}

	private void addInsuranceDetails(Insurance insurance, Invoice invoice) {
		if (insurance != null && insurance.getInsurance() != null) {
			Detail detail = new Detail();
			detail.setType(DetailType.INSURANCE);
			detail.setInfo(insurance.getInfo());
			Details details = insurance.getInsurance();
			detail.setBasicPrice(details.getBasePrice());
			detail.setTax(details.getTax());
			detail.setTotalPrice(details.getPrice());
			invoice.getDetails().add(detail);
		}
	}

	private void addCarDetails(Car car, Invoice invoice) {
		if (car != null && car.getCar() != null) {
			Detail detail = new Detail();
			detail.setType(DetailType.CAR);
			detail.setInfo(car.getInfo());
			Details details = car.getCar();
			detail.setBasicPrice(details.getBasePrice());
			detail.setTax(details.getTax());
			detail.setTotalPrice(details.getPrice());
			invoice.getDetails().add(detail);
		}
	}

	private void addHoteDetails(Hotel hotel, Invoice invoice) {
		if (hotel != null && hotel.getHotel() != null) {
			Detail detail = new Detail();
			detail.setType(DetailType.HOTEL);
			detail.setInfo(hotel.getInfo());
			Details details = hotel.getHotel();
			detail.setBasicPrice(details.getBasePrice());
			detail.setTax(details.getTax());
			detail.setTotalPrice(details.getPrice());
			invoice.getDetails().add(detail);
		}
	}

	private void addFlightDetails(Flight flight, Invoice invoice) {
		if (flight != null && flight.getOfferPack() != null && flight.getOfferPack().getFare() != null
				&& flight.getOfferPack().getOnwardFlightItinerary() != null
				&& flight.getOfferPack().getOnwardFlightItinerary().getFlightInfos() != null) {
			FlightInfo flightInfo = flight.getOfferPack().getOnwardFlightItinerary().getFlightInfos().get(0);
			Fare fare = flight.getOfferPack().getFare();
			Detail detail = new Detail();
			detail.setType(DetailType.FLIGHT);
			detail.setInfo(flightInfo.getInfo());
			detail.setBasicPrice(fare.getBaseFare());
			detail.setTax(fare.getTaxes());
			detail.setTotalPrice(fare.getTotal());
			invoice.getDetails().add(detail);
		}
	}

	private void processFundsToMerchants(PackageDTO packageDTO) {
		Thread th = new Thread(() -> transferFunds(packageDTO));
		th.start();
	}

	private String transferFundsToAdmin(Integer total, String walletId) throws TransferFailureException {
		return transferFunds(total, walletId, ConfigReader.getMasterWalletID());
	}

	private String transferFunds(Integer total, String fromWalletId, String toWalletId)
			throws TransferFailureException {
		String transactionId = null;
		String transferURL = createURL();
		LinkedHashMap<String, LinkedHashMap<String, Object>> result = null;
		try {
			result = (LinkedHashMap<String, LinkedHashMap<String, Object>>) restTemplate.postForObject(transferURL,
					createRequestJsonWithHeaders(fromWalletId, toWalletId, total), Object.class);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new TransferFailureException("Transfer Failed");
		}
		if (result != null && result.get("result") != null) {
			LinkedHashMap<String, Object> resultMap = result.get("result");
			transactionId = (String) resultMap.get("blockHash");
		}
		return transactionId;
	}

	/**
	 * @param packageDTO
	 * @param user
	 */
	public void createBookings(PackageDTO packageDTO, User user) {
		if (packageDTO.getFlight() != null && packageDTO.getFlight().getOfferPack() != null) {
			createBooking(user, packageDTO.getFlight().getOfferPack().getOnwardFlightItinerary());
			createBooking(user, packageDTO.getFlight().getOfferPack().getReturnFlightItinerary());
		}
	}

	/**
	 * @param user
	 * @param itinerary
	 */
	public void createBooking(User user, FlightItinerary itinerary) {
		if (itinerary != null) {
			Booking booking = new Booking();
			booking.setEmail(user.getEmail());
			booking.setFirstName(user.getFirstName());
			booking.setLastName(user.getLastName());
			List<FlightInfo> flightInfos = itinerary.getFlightInfos();
			booking.setFlightdetails(getFlightDetails(flightInfos));
			booking.setDateOfFirstSegment(flightInfos.get(0).getDepartureDate());
			bookingDAO.save(booking);
		}
	}

	/**
	 * @param flightInfos
	 * @return
	 */
	public String getFlightDetails(List<FlightInfo> flightInfos) {
		StringBuilder builder = new StringBuilder();
		for (FlightInfo flightInfo : flightInfos) {
			if (builder.length() != 0) {
				builder.append("#");
			}
			builder.append(flightInfo.toString());
		}
		String flightDetails = builder.toString();
		return flightDetails;
	}

	private TravelInfo addTravelInfo(User user, String travelInfoString) {
		TravelInfo travelInfo = null;
		if (travelInfoString != null) {
			travelInfo = new TravelInfo();
			travelInfo.setTravelInfo(travelInfoString.getBytes());
			travelInfo.setUser(user);
			if (user.getTravelInfos() == null) {
				Set<TravelInfo> travelInfos = new HashSet<TravelInfo>();
				user.setTravelInfos(travelInfos);
			}
			user.getTravelInfos().add(travelInfo);
			travelInfo = travelDAO.save(travelInfo);
		}
		return travelInfo;
	}

	private User findUser(String email) throws AuthenticationFailureException {
		User user = null;
		try {
			List<User> users = userDAO.getUsers(email);
			if (users != null && !users.isEmpty()) {
				user = users.get(0);
			} else {
				throw new AuthenticationFailureException("User Not Registered");
			}
		} catch (Exception e) {
			throw new AuthenticationFailureException("Unable to get User");
		}
		return user;
	}

	private void transferFunds(PackageDTO packageDTO) {
		String transferURL = createURL();
		for (PackageBookingDTO bookingDTO : packageDTO.getBookings()) {
			Optional<String> partnerWalletID = userDAO.getWalletIDByName(bookingDTO.getPartner());
			try {
				restTemplate.postForObject(transferURL, createRequestJsonWithHeaders(ConfigReader.getMasterWalletID(),
						partnerWalletID.get(), bookingDTO.getAmount()), Object.class);
			} catch (Exception ex) {
				ex.printStackTrace();
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
	private HttpEntity<String> createRequestJsonWithHeaders(String loggedInUserWalletID, String toAddress,
			Integer amount) {
		String requestJSON = "{\"toAddress\" : \"" + toAddress + "\", \"amount\" : " + amount + ", \"from\" : \""
				+ loggedInUserWalletID + "\"}";
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
	public List<TravelInfoDTO> getTravelInfo(String email) {
		List<TravelInfoDTO> travelInfoList = new ArrayList<TravelInfoDTO>();
		List<Object[]> travelInfos = userDAO.getTravelInfos(email);
		for (Object[] travelInfo : travelInfos) {
			TravelInfoDTO travelInfoDTO = new TravelInfoDTO();
			travelInfoDTO.setId((Integer) travelInfo[0]);
			travelInfoDTO.setTravelInfo(new String((byte[]) travelInfo[1]));
			travelInfoList.add(travelInfoDTO);
		}
		return travelInfoList;
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
				bookingDTO.setBagTags(booking.getBagTags());
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
		/*
		 * if (user.getTravelInfos() != null && !user.getTravelInfos().isEmpty()) {
		 * List<TravelInfoDTO> travelInfoDTOs = new ArrayList<TravelInfoDTO>();
		 * userDTO.setTravelInfos(travelInfoDTOs); for (TravelInfo travelInfo :
		 * user.getTravelInfos()) { TravelInfoDTO travelInfoDTO = new TravelInfoDTO();
		 * travelInfoDTO.setTravelId(travelInfo.getTravelId());
		 * travelInfoDTO.setTravelInfo(new String(travelInfo.getTravelInfo()));
		 * travelInfoDTOs.add(travelInfoDTO); } }
		 */
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
				travellerDTO.setIissuingCountry(traveller.getIssueCountry());
				travellerDTO.setIssuingDate(traveller.getIssueDate());
				travellerDTO.setExpiryDate(traveller.getExpiryDate());
				travellerDTOs.add(travellerDTO);
				userDTO.setPrimaryUser(travellerDTO);
			}
		}
	}

	@Override
	public ReconcileReports generateReconcileReport(String emailID) throws InvalidRequestException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	// @Async
	public void addExpense(@Valid List<ExpenseDTO> expenses) throws InvalidRequestException, TesseractException {
		TravelInfo travelInfo = null;
		for (ExpenseDTO expenseDTO : expenses) {
			if (travelInfo == null) {
				travelInfo = getTravelInfo(expenseDTO.getTravelId());
			}
			if (travelInfo.getExpenseInfos() == null) {
				travelInfo.setExpenseInfos(new HashSet<ExpenseInfo>());
			}
			ExpenseInfo expenseInfo = new ExpenseInfo();
			expenseInfo.setDateOfClaim(new Date());
			expenseInfo.setDescription(expenseDTO.getDescription());
			InvoiceInfo invoiceInfo = getInvoiceInfo(expenseDTO, travelInfo.getInvoice());
			if (invoiceInfo != null) {
				expenseInfo.setDateOfExpense(invoiceInfo.getDate());
				expenseInfo.setMerchantName(invoiceInfo.getMerchant());
				expenseInfo.setTrasactionId(invoiceInfo.getTxnId());
				expenseInfo.setAmount(invoiceInfo.getAmmount());
			}
			expenseInfo.setTravelInfo(travelInfo);
			travelInfo.getExpenseInfos().add(expenseInfo);
		}
		travelDAO.save(travelInfo);
	}

	@Override
	public Set<ExpenseInfo> getExpenses(Integer travelId) throws InvalidRequestException {
		Set<ExpenseInfo> expenses = null;
		TravelInfo travelInfo = getTravelInfo(travelId);
		if (travelInfo != null) {
			expenses = travelInfo.getExpenseInfos();
		}
		return expenses;
	}

	private TravelInfo getTravelInfo(Integer traveId) throws InvalidRequestException {
		TravelInfo travelInfo = null;
		Optional<TravelInfo> travelInfoFindById = travelDAO.findById(traveId);
		if (travelInfoFindById.isPresent()) {
			travelInfo = travelInfoFindById.get();
		}
		if (travelInfo == null) {
			throw new InvalidRequestException("TravelInfo does not present");
		}
		return travelInfo;
	}

	private byte[] createInvoice(Invoice invoice) {
		try {

			Map<String, Object> parameters = new HashMap<>();
			parameters.put("invoice", invoice);
			JasperReport report = (JasperReport) JRLoader
					.loadObject(this.getClass().getResourceAsStream("/invoice.jasper"));
			JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
			byte[] pdfReport = JasperExportManager.exportReportToPdf(jasperPrint);
			return pdfReport;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void sendMail(byte[] invoicePdf, String email) {

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);

		// Get the Session object.
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));

			message.setSubject("Invoice From Swift Corporate  Booking");

			BodyPart messageBodyPart = new MimeBodyPart();

			messageBodyPart.setText("Please Find the attached Invoice");

			Multipart multipart = new MimeMultipart();

			multipart.addBodyPart(messageBodyPart);

			messageBodyPart = new MimeBodyPart();
			DataSource source = new ByteArrayDataSource(invoicePdf, "application/pdf");
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName("Invoice.pdf");
			multipart.addBodyPart(messageBodyPart);

			message.setContent(multipart);

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

}
