//package com.kot.horizon.security;
//
//import java.util.HashMap;
//import java.util.Map;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import com.kot.horizon.localization.LocalizationService;
//import com.kot.horizon.model.photo.PhotoEntity;
//import com.kot.horizon.model.user.UserEntity;
//import com.kot.horizon.model.user.UserRole;
//import com.kot.horizon.service.facebook.FacebookService;
//import com.kot.horizon.service.photo.PhotoService;
//import com.kot.horizon.service.user.UserService;
//
//@Service
//public class FacebookAuthorizationHandler {
//
//    private static final String ID = "id";
//    private static final String EMAIL = "email";
//    private static final String FIRST_NAME = "firstName";
//    private static final String LAST_NAME = "lastName";
//    private static final String NAME = "name";
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private PhotoService photoService;
//
//    @Autowired
//    private FacebookService facebookService;
//
//    public UserEntity createUser(Map<String, Object> facebookUserAttributes, String accessToken) {
//        UserEntity userEntity = createUserInDB(facebookUserAttributes);
//        byte[] image = facebookService.getUserPicture(accessToken);
//        if (image.length != 0) {
//            PhotoEntity photoEntity = photoService.saveDownloadedPicture(userEntity, image);
//            userService.updatePhotoForNewUser(userEntity, photoEntity);
//        }
//        return userEntity;
//    }
//
//    UserEntity createUserInDB(Map<String, Object> facebookUserAttributes) {
//        UserEntity user = new UserEntity();
//        user.setSocialId(facebookUserAttributes.get(ID).toString());
//
//        Map<String, String> splitName = splitName(facebookUserAttributes.get(NAME).toString());
//
//        user.setFirstName(splitName.get(FIRST_NAME));
//        user.setLastName(splitName.get(LAST_NAME));
//        user.setLanguage(LocalizationService.DEFAULT_LANGUAGE);
//        if (facebookUserAttributes.get(EMAIL) != null) {
//            user.setEmail(facebookUserAttributes.get(EMAIL).toString());
//        }
//        user.setRole(UserRole.ROLE_USER);
//        user.setSocialType(SocialTypes.FACEBOOK.value);
//
//        UserEntity savedUser = userService.create(user);
//        return savedUser;
//    }
//
//    private Map<String, String> splitName(String name) {
//        Map<String, String> nameArray = new HashMap<>();
//
//        if (name.contains(" ")) {
//            String[] arr = name.split(" ");
//            nameArray.put(FIRST_NAME, arr[0]);
//            nameArray.put(LAST_NAME, arr[1]);
//        } else {
//            nameArray.put(LAST_NAME, name);
//            nameArray.put(FIRST_NAME, "");
//        }
//        return nameArray;
//    }
//
//}
