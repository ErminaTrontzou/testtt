package gr.thegoodsideofe1.tourguide.services;

import gr.thegoodsideofe1.tourguide.aes.AES_ENCRYPTION;
import gr.thegoodsideofe1.tourguide.entities.User;
import gr.thegoodsideofe1.tourguide.repositories.UserRepository;
import gr.thegoodsideofe1.tourguide.responses.UserResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AES_ENCRYPTION aes_encryption;
    @Autowired
    PasswordEncoder passwordEncoder;
    UserResponses userResponses = new UserResponses();
    public List<User> listAllUsers(){
        return userRepository.findAll();
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public User getUser(Integer id){
        return userRepository.findById(id).get();
    }

    public void deleteUser(Integer id){
        userRepository.deleteById(id);
    }

    public User getUserByEmail(String email){
        return userRepository.getUserByEmail(email);
    }

    public User getUserByUsername(String username){
        return userRepository.getUserByUsername(username);
    }

    public User getUserByParams(String username, String email, String firstName, String lastName){
        return userRepository.getUserByParams(username, email, firstName, lastName);
    }

    //==
    public ResponseEntity<?> getAllUsers(Map<String, String> requestBody){
        if (requestBody.containsKey("Bearer") && !requestBody.get("Bearer").isEmpty()) {
            String requestJWTToken = requestBody.get("Bearer");
            try {
                String[] userDetails = this.getUserDetailsFromJWT(requestJWTToken);
                User loginUser = userRepository.getUserByParams(userDetails[1], userDetails[0], userDetails[2], userDetails[3]);
                if (loginUser != null) {
                    if (loginUser.getIsAdmin()){
                        List<User> allUsers = userRepository.findAll();
                        return new ResponseEntity<>(allUsers, HttpStatus.OK);
                    }
                    //User is NOT Admin
                    return new ResponseEntity<>(userResponses.noAccessToInformation(), HttpStatus.UNAUTHORIZED);
                }
                //User is NOT Logged In
                return new ResponseEntity<>(userResponses.userIsNotLoggedIN(), HttpStatus.UNAUTHORIZED);
            } catch (Exception e){
                //Exception During JWT Decrypt
                return new ResponseEntity<>(userResponses.userIsNotLoggedIN(), HttpStatus.UNAUTHORIZED);
            }
        }
        return new ResponseEntity<>(userResponses.noValidJWTResponse(), HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<?> getSpecificUser(Integer userID, Map<String, String> requestBody){
        if (requestBody.containsKey("Bearer") && !requestBody.get("Bearer").isEmpty()) {
            String requestJWTToken = requestBody.get("Bearer");
            try {
                String[] userDetails = this.getUserDetailsFromJWT(requestJWTToken);
                User loginUser = userRepository.getUserByParams(userDetails[1], userDetails[0], userDetails[2], userDetails[3]);
                if (loginUser != null) {
                    if (loginUser.getIsAdmin() || loginUser.getId() == userID){
                        User userToReturn = userRepository.findById(userID).get();
                        return new ResponseEntity<>(userToReturn, HttpStatus.OK);
                    }
                    //User is NOT Admin
                    return new ResponseEntity<>(userResponses.noAccessToInformation(), HttpStatus.UNAUTHORIZED);
                }
                //User is NOT Logged In
                return new ResponseEntity<>(userResponses.userIsNotLoggedIN(), HttpStatus.UNAUTHORIZED);
            } catch (Exception e){
                //Exception During JWT Decrypt
                return new ResponseEntity<>(userResponses.userIsNotLoggedIN(), HttpStatus.UNAUTHORIZED);
            }
        }
        return new ResponseEntity<>(userResponses.noValidJWTResponse(), HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<?> addNewUser(Map<String, String> requestBody){
        if (!isBodyValid(requestBody)){
            return new ResponseEntity<>(checkMissingField(requestBody), HttpStatus.OK);
        }
        User userToSave = generateUserModel(requestBody);
        userRepository.save(userToSave);
        return new ResponseEntity<>(userResponses.userAddedSuccessfully(), HttpStatus.CREATED);
    }

    public ResponseEntity<?> updateSpecificUser(Integer userID, Map<String, String> requestBody){
        if (requestBody.containsKey("Bearer") && !requestBody.get("Bearer").isEmpty()) {
            String requestJWTToken = requestBody.get("Bearer");
            try {
                String[] userDetails = this.getUserDetailsFromJWT(requestJWTToken);
                User loginUser = userRepository.getUserByParams(userDetails[1], userDetails[0], userDetails[2], userDetails[3]);
                if (loginUser != null) {
                    if (loginUser.getIsAdmin() || loginUser.getId() == userID){
                        if (!isBodyValid(requestBody)){
                            return new ResponseEntity<>(checkMissingField(requestBody), HttpStatus.OK);
                        }
                        User userToUpdate = userRepository.findById(userID).get();
                        User userToSave = generateUpdateUserModel(requestBody, userToUpdate);
                        userRepository.save(userToSave);
                        return new ResponseEntity<>(userResponses.userSavedSuccessfully(), HttpStatus.OK);
                    }
                    //User is NOT Admin
                    return new ResponseEntity<>(userResponses.noAccessToInformation(), HttpStatus.UNAUTHORIZED);
                }
                //User is NOT Logged In
                return new ResponseEntity<>(userResponses.userIsNotLoggedIN(), HttpStatus.UNAUTHORIZED);
            } catch (Exception e){
                //Exception During JWT Decrypt
                return new ResponseEntity<>(userResponses.userIsNotLoggedIN(), HttpStatus.UNAUTHORIZED);
            }
        }
        return new ResponseEntity<>(userResponses.noValidJWTResponse(), HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<?> deleteSpecificUser(Map<String, String> requestBody, Integer userID){
        if (requestBody.containsKey("Bearer") && !requestBody.get("Bearer").isEmpty()) {
            String requestJWTToken = requestBody.get("Bearer");
            try {
                String[] userDetails = this.getUserDetailsFromJWT(requestJWTToken);
                User loginUser = userRepository.getUserByParams(userDetails[1], userDetails[0], userDetails[2], userDetails[3]);
                if (loginUser != null) {
                    if (loginUser.getIsAdmin() || loginUser.getId() == userID){
                        User userToDelete = userRepository.findById(userID).get();
                        userRepository.delete(userToDelete);
                        return new ResponseEntity<>(userResponses.userDeleted(), HttpStatus.OK);
                    }
                    //User is NOT Admin
                    return new ResponseEntity<>(userResponses.noAccessToInformation(), HttpStatus.UNAUTHORIZED);
                }
                //User is NOT Logged In
                return new ResponseEntity<>(userResponses.userIsNotLoggedIN(), HttpStatus.UNAUTHORIZED);
            } catch (Exception e){
                //Exception During JWT Decrypt
                return new ResponseEntity<>(userResponses.userIsNotLoggedIN(), HttpStatus.UNAUTHORIZED);
            }
        }
        return new ResponseEntity<>(userResponses.noValidJWTResponse(), HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<?> loginUserWithEmail(Map<String, String> requestBody){
        if (!isLoginEmailBodyValid(requestBody)){
            return new ResponseEntity<>(checkMissingFieldEmailLogin(requestBody), HttpStatus.OK);
        }
        String emailParam = requestBody.get("email");
        String passwordParam = requestBody.get("password");

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            User userToLogin = userRepository.getUserByEmail(emailParam);
            if (userToLogin == null){
                //Email Does not Exists
                return new ResponseEntity<>(userResponses.noSuchUser(), HttpStatus.OK);
            }
            if (passwordEncoder.matches(passwordParam, userToLogin.getPassword())){
                //Password Param matches password in DB
                String userDetailsJoined = userToLogin.getEmail() + "," + userToLogin.getUsername() + "," + userToLogin.getFirstName() + "," + userToLogin.getLastName();
                return new ResponseEntity<>(userResponses.successLogin(aes_encryption.encrypt(userDetailsJoined)), HttpStatus.OK);
            }
        } catch (NoSuchAlgorithmException | NullPointerException e){
            return new ResponseEntity<>(userResponses.generalError(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(userResponses.generalError(), HttpStatus.OK);
        }
        return new ResponseEntity<>(userResponses.generalError(), HttpStatus.OK);
    }

    public ResponseEntity<?> loginUserWithUsername(Map<String, String> requestBody){
        if (!isLoginUsernameBodyValid(requestBody)){
            return new ResponseEntity<>(checkMissingFieldUsernameLogin(requestBody), HttpStatus.OK);
        }
        String usenameParam = requestBody.get("username");
        String passwordParam = requestBody.get("password");

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            User userToLogin = userRepository.getUserByUsername(usenameParam);
            if (userToLogin == null){
                //Email Does not Exists
                return new ResponseEntity<>(userResponses.noSuchUser(), HttpStatus.OK);
            }
            if (passwordEncoder.matches(passwordParam, userToLogin.getPassword())){
                //Password Param matches password in DB
                String userDetailsJoined = userToLogin.getEmail() + "," + userToLogin.getUsername() + "," + userToLogin.getFirstName() + "," + userToLogin.getLastName();
                return new ResponseEntity<>(userResponses.successLogin(aes_encryption.encrypt(userDetailsJoined)), HttpStatus.OK);
            }
        } catch (NoSuchAlgorithmException | NullPointerException e){
            return new ResponseEntity<>(userResponses.generalError(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(userResponses.generalError(), HttpStatus.OK);
        }
        return new ResponseEntity<>(userResponses.generalError(), HttpStatus.OK);
    }

    public ResponseEntity<?> userIsAdmin(Map<String, String> requestBody){
        if (requestBody.containsKey("Bearer") && !requestBody.get("Bearer").isEmpty()) {
            String requestJWTToken = requestBody.get("Bearer");
            try {
                String[] userDetails = this.getUserDetailsFromJWT(requestJWTToken);
                User loginUser = userRepository.getUserByParams(userDetails[1], userDetails[0], userDetails[2], userDetails[3]);
                if (!loginUser.getIsAdmin()){
                    return new ResponseEntity<>(userResponses.userIsNotAdmin(), HttpStatus.OK);
                }
                return new ResponseEntity<>(userResponses.userIsAdmin(), HttpStatus.OK);
            } catch (Exception e){
                return new ResponseEntity<>(userResponses.noValidJWTResponse(), HttpStatus.UNAUTHORIZED);
            }
        }
        return new ResponseEntity<>(userResponses.noValidJWTResponse(), HttpStatus.UNAUTHORIZED);
    }

    protected String[] getUserDetailsFromJWT(String token) throws Exception {
        String decryptedString = aes_encryption.decrypt(token);
        return decryptedString.split(",");
    }

    protected boolean isBodyValid(Map<String, String> requestBody){
        try {
            requestBody.get("firstName");
            requestBody.get("lastName");
            requestBody.get("email");
            requestBody.get("password");
            requestBody.get("username");
            requestBody.get("disabled");
            requestBody.get("isAdmin");
        } catch (ClassCastException | NullPointerException e){
            return false;
        }
        return true;
    }

    protected boolean isLoginEmailBodyValid(Map<String, String> requestBody){
        try {
            requestBody.get("email");
            requestBody.get("password");
        } catch (ClassCastException | NullPointerException e){
            return false;
        }
        return true;
    }

    protected boolean isLoginUsernameBodyValid(Map<String, String> requestBody){
        try {
            requestBody.get("username");
            requestBody.get("password");
        } catch (ClassCastException | NullPointerException e){
            return false;
        }
        return true;
    }

    protected HashMap<String, String> checkMissingField(Map<String, String> requestBody){
        if (!requestBody.containsKey("firstName")){
            return userResponses.missingBodyField("firstName");
        }
        if (!requestBody.containsKey("lastName")){
            return userResponses.missingBodyField("lastName");
        }
        if (!requestBody.containsKey("email")){
            return userResponses.missingBodyField("email");
        }
        if (!requestBody.containsKey("password")){
            return userResponses.missingBodyField("password");
        }
        if (!requestBody.containsKey("username")){
            return userResponses.missingBodyField("username");
        }
        if (!requestBody.containsKey("disabled")){
            return userResponses.missingBodyField("disabled");
        }
        if (!requestBody.containsKey("isAdmin")){
            return userResponses.missingBodyField("isAdmin");
        }
        return userResponses.generalError();
    }

    protected HashMap<String, String> checkMissingFieldEmailLogin(Map<String, String> requestBody){
        if (!requestBody.containsKey("email")){
            return userResponses.missingBodyField("email");
        }
        if (!requestBody.containsKey("password")){
            return userResponses.missingBodyField("password");
        }
        return userResponses.generalError();
    }

    protected HashMap<String, String> checkMissingFieldUsernameLogin(Map<String, String> requestBody){
        if (!requestBody.containsKey("username")){
            return userResponses.missingBodyField("username");
        }
        if (!requestBody.containsKey("password")){
            return userResponses.missingBodyField("password");
        }
        return userResponses.generalError();
    }

    protected User generateUserModel(Map<String, String> requestBody){
        User userToReturn = new User();
        userToReturn.setFirstName(requestBody.get("firstName"));
        userToReturn.setLastName(requestBody.get("lastName"));
        userToReturn.setEmail(requestBody.get("email"));
        userToReturn.setPassword(passwordEncoder.encode(requestBody.get("password")));
        userToReturn.setUsername(requestBody.get("username"));
        userToReturn.setDisabled(Boolean.parseBoolean(requestBody.get("disabled")));
        userToReturn.setIsAdmin(Boolean.parseBoolean(requestBody.get("isAdmin")));
        return userToReturn;
    }

    protected User generateUpdateUserModel(Map<String, String> requestBody, User existUser){
        existUser.setFirstName(requestBody.get("firstName"));
        existUser.setLastName(requestBody.get("lastName"));
        existUser.setEmail(requestBody.get("email"));
        existUser.setPassword(passwordEncoder.encode(requestBody.get("password")));
        existUser.setUsername(requestBody.get("username"));
        existUser.setDisabled(Boolean.parseBoolean(requestBody.get("disabled")));
        existUser.setIsAdmin(Boolean.parseBoolean(requestBody.get("isAdmin")));
        return existUser;
    }
}
