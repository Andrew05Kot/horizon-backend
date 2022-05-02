//package com.kot.horizon.demo;
//
//import static com.kot.horizon.model.user.Language.UK;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.math.BigDecimal;
//import javax.annotation.PostConstruct;
//import com.google.common.io.ByteStreams;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Profile;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//import com.kot.horizon.model.photo.PhotoEntity;
//import com.kot.horizon.model.photo.ShortPhotoEntity;
//import com.kot.horizon.model.user.UserEntity;
//import com.kot.horizon.model.user.UserPrincipal;
//import com.kot.horizon.model.user.UserRole;
//import com.kot.horizon.security.SocialTypes;
//import com.kot.horizon.security.jwt.JwtAuthenticationToken;
//import com.kot.horizon.security.jwt.JwtService;
//import com.kot.horizon.service.datetime.DateTimeService;
//import com.kot.horizon.service.photo.PhotoService;
//import com.kot.horizon.service.user.UserService;
//
//@Profile({"dev"})
//@Service
//public class DemoDataGenerator {
//
//	@Autowired
//	private UserService userService;
//	@Autowired
//	private JwtService jwtService;
//	@Autowired
//	private DateTimeService dateTimeService;
//	@Autowired
//	private PhotoService photoService;
//
//	private UserEntity admin;
//
//	@PostConstruct
//	public void generateData() {
//		this.admin = createDefaultAdmin();
//		loginUser(admin);
//		UserEntity user1 = createDefaultUser();
//
//	}
//
//	private ShortPhotoEntity loadImage(String imageName) {
//		if (imageName != null) {
//			try {
//				PhotoEntity photoEntity = new PhotoEntity();
//				InputStream is = getClass().getClassLoader().getResourceAsStream("demoImages/" + imageName);
//				byte[] targetArray = ByteStreams.toByteArray(is);
//				photoEntity.setContent(targetArray);
//				photoEntity.setMimeType("image/jpeg");
//				photoEntity = photoService.create(photoEntity);
//				return photoService.getCurrentUserShortPhoto(photoEntity.getId());
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		return null;
//	}
//
//	private UserEntity createDefaultAdmin() { // can't create wish without saved user
//		UserEntity admin = new UserEntity();
//		admin.setSocialId("105239754755081");
//		admin.setEmail("open_qegruya_graph@tfbnw.net");
//		admin.setLastName("Administrator");
//		admin.setFirstName("System");
//		admin.setRole(UserRole.ROLE_ADMIN);
//		admin.setLanguage(UK);
//		admin.setSocialType(SocialTypes.FACEBOOK.value);
//		return userService.create(admin);
//	}
//
//	private UserEntity createDefaultUser() {
//		UserEntity user = new UserEntity();
//		user.setSocialId("102417068379113");
//		user.setEmail("dbkxbhsnli_1606210454@tfbnw.net");
//		user.setLastName("Павко");
//		user.setFirstName("Андрій");
//		return saveDefaultUser(user, "025d336075499e2cfb0af9db357a359f.jpg");
//	}
//
//	private UserEntity createDefaultUser1() {
//		UserEntity user = new UserEntity();
//		user.setSocialId("111");
//		user.setEmail("non.valid.email+1@tfbnw.net");
//		user.setLastName("Пітик");
//		user.setFirstName("Сергій");
//		return saveDefaultUser(user, "photo_2019-08-03_21-52-37.jpg");
//	}
//
//	private UserEntity createDefaultUser2() {
//		UserEntity user = new UserEntity();
//		user.setSocialId("222");
//		user.setEmail("non.valid.email+2@tfbnw.net");
//		user.setLastName("Сергеєва");
//		user.setFirstName("Ліда");
//		return saveDefaultUser(user, "image_2021_01_26T15_10_04_336Z.png");
//	}
//
//	private UserEntity createDefaultUser3() {
//		UserEntity user = new UserEntity();
//		user.setSocialId("333");
//		user.setEmail("non.valid.email+3@tfbnw.net");
//		user.setLastName("Дерич");
//		user.setFirstName("Вадим");
//		return saveDefaultUser(user, "vd.jpg");
//	}
//
//	private UserEntity createDefaultUser4() {
//		UserEntity user = new UserEntity();
//		user.setSocialId("444");
//		user.setEmail("non.valid.email+4@tfbnw.net");
//		user.setLastName("Котюга");
//		user.setFirstName("Андрій");
//		return saveDefaultUser(user, "1611608253285.JPEG");
//	}
//
//	private UserEntity createDefaultUser5() {
//		UserEntity user = new UserEntity();
//		user.setSocialId("555");
//		user.setEmail("non.valid.email+5@tfbnw.net");
//		user.setLastName("Федоруца");
//		user.setFirstName("Саша");
//		return saveDefaultUser(user, "sf.jpg");
//	}
//
//	private UserEntity createDefaultUser6() {
//		UserEntity user = new UserEntity();
//		user.setSocialId("187708209623196");
//		user.setEmail("horizon@yukon.org.ua");
//		user.setLastName("Кусяк");
//		user.setFirstName("Володимир");
//		user = saveDefaultUser(user, "vk.jpg");
//		return user;
//	}
//
//	private UserEntity saveDefaultUser(UserEntity user, String imageName) {
//		user.setRole(UserRole.ROLE_USER);
//		user.setLanguage(UK);
//		user.setSocialType(SocialTypes.FACEBOOK.value);
//		user = userService.create(user);
//		loginUser(user);
//		userService.update(user);
//		return user;
//	}
//
//	private void loginUser(UserEntity userEntity) {
//		UserPrincipal userPrincipal = UserPrincipal.create(userEntity);
//		String token = jwtService.createToken("auth", userPrincipal.getSocialId(), 10000L);
//		JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(userPrincipal, token, userPrincipal.getAuthorities());
//		SecurityContextHolder.getContext().setAuthentication(jwtAuthenticationToken);
//	}
//
//	public void logoutUser() {
//		SecurityContextHolder.clearContext();
//	}
//
//}
