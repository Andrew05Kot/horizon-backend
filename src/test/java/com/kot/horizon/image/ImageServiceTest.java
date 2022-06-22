package com.kot.horizon.image;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.security.SecureRandom;
import java.util.Base64;
import javax.imageio.ImageIO;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;
import com.kot.horizon.UserBuilder;
import com.kot.horizon.common.TestsDetails;
import com.kot.horizon.image.dao.ImageDao;
import com.kot.horizon.image.exception.UnsupportedImageTypeException;
import com.kot.horizon.image.exception.WrongImageSizeException;
import com.kot.horizon.image.service.ImageService;

@ActiveProfiles(profiles = {"test", "test-image"})
@DirtiesContext
@ExtendWith(MockitoExtension.class)
public class ImageServiceTest extends TestsDetails {

	private final String FILE_NAME = "file";

	private final String ORIGINAL_FILE_NAME = "file.png";

	@Value("${com.kot.horizon.image.test-image}")
	private String testImage;

	@Value("${com.kot.horizon.image.minSideSize}")
	private Integer minSideSize;

	@Autowired
	private ImageService imageService;

	@MockBean
	private ImageDao imageDao;

	private final UserBuilder userBuilder = new UserBuilder();
	private SecureRandom secureRandom = new SecureRandom();

	@Test
	public void bufferedImageToMultipartFile() throws Exception {
		byte[] encodedImage = Base64.getDecoder().decode(testImage.getBytes());
		MultipartFile file = new MockMultipartFile(FILE_NAME, FILE_NAME, ContentType.IMAGE_PNG.getMimeType(), encodedImage);
		BufferedImage bufferedImage = ImageIO.read(file.getInputStream());

		assertNotNull(this.imageService.bufferedImageToMultipartFile(file.getOriginalFilename(), bufferedImage));
	}

	@Test
	void formatsValidationOnSavingTest() {
		byte[] encodedImage = Base64.getDecoder().decode(testImage.getBytes());
		MultipartFile file = new MockMultipartFile(FILE_NAME, ORIGINAL_FILE_NAME, ContentType.DEFAULT_TEXT.getMimeType(), encodedImage);

		assertThrows(UnsupportedImageTypeException.class, () -> imageService.saveImage(file));
	}

	@Test
	void maxSizeValidationOnSavingTest() {
		byte[] bytes = new byte[10 * 1024 * 1024];
		MultipartFile file = new MockMultipartFile(FILE_NAME, ORIGINAL_FILE_NAME, ContentType.IMAGE_PNG.getMimeType(), bytes);

		assertThrows(WrongImageSizeException.class, () -> imageService.saveImage(file));
	}

	@Test
	void notFoundImageTest() {
		assertThrows("Image was not found",
				FileNotFoundException.class,
				() -> imageService.getImageByName(String.valueOf(secureRandom.nextInt()))
		);
	}
}
