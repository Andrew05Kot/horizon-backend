package com.kot.horizon.image.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import javax.imageio.ImageIO;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.kot.horizon.architecture.dao.AbstractDAO;
import com.kot.horizon.architecture.repository.BaseCRUDRepository;
import com.kot.horizon.architecture.service.AbstractService;
import com.kot.horizon.image.config.ImageDirectoryInitializator;
import com.kot.horizon.image.dao.ImageDao;
import com.kot.horizon.image.exception.FailedSaveImageException;
import com.kot.horizon.image.exception.UnsupportedImageTypeException;
import com.kot.horizon.image.exception.WrongImageSizeException;
import com.kot.horizon.image.model.ImageEntity;

@Service
public class ImageService extends AbstractService<ImageEntity> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ImageService.class);

	@Autowired
	private ImageDao imageDao;

	@Value("${com.kot.horizon.image.minSideSize}")
	private Integer minSideSize;

	@Value("${com.kot.horizon.image.maxImageSize}")
	private Integer maxImageSize;

	@Value("#{'${com.kot.horizon.image.formats}'.split(',')}")
	private List< String > supportedFormats;

	@Value("${com.kot.horizon.image.folderName}")
	private String folderNameWithImages;

	public ImageEntity saveImage(MultipartFile multipartFile) throws UnsupportedImageTypeException, WrongImageSizeException {
		validateMultipartFile(multipartFile);
		String fileName = getRandomUUID() + multipartFile.getOriginalFilename();
		File directory = new File(getRootDirectory() + folderNameWithImages);
		Path path = Paths.get(directory.toPath() + File.separator + fileName);
		try {
			byte[] bytes = multipartFile.getBytes();
			Files.write(path, bytes);
			LOGGER.info("Successfully saved image {} to {}", fileName, path);
			return createImageEntity(multipartFile, fileName);
		} catch (IOException e) {
			LOGGER.error("Can't save image ", e);
			throw new FailedSaveImageException("Can't save image" , e.getCause());
		}
	}

	public ByteArrayResource getImageByName(String filename) throws FileNotFoundException {
		Path imagePath = Paths.get( getRootDirectory() + folderNameWithImages ).resolve(filename.trim());
		try {
			ByteArrayResource body = new ByteArrayResource(Files.readAllBytes(imagePath));
			if (!body.exists()) {
				throw new FileNotFoundException("Image was not found");
			}
			return body;
		} catch (Exception e) {
			LOGGER.error("Filed to find image {}", filename);
			throw new FileNotFoundException("Can't get image");
		}
	}

	public void deleteImage(ImageEntity image) {
		delete(image.getId());
		Path imagePath = Paths.get( getRootDirectory() + folderNameWithImages ).resolve(image.getImageName().trim());
		File file = new File(String.valueOf(imagePath));
		try {
			if (file.exists()) {
				Files.delete(imagePath);
				LOGGER.info("Successfully removed image {}", image.getImageName());
			}
		} catch (IOException e) {
			LOGGER.error("Can't remove image {}", image);
		}
	}

	public MultipartFile bufferedImageToMultipartFile(String fileName, BufferedImage bufferedImage) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		try {
			ImageIO.write( bufferedImage, "png", byteArrayOutputStream );
			byteArrayOutputStream.flush();
		} catch (IOException ex) {
			LOGGER.error(ex.getMessage());
		}

		return new MockMultipartFile(fileName, fileName, ContentType.IMAGE_PNG.getMimeType(), byteArrayOutputStream.toByteArray());
	}

	public ImageEntity createImageEntity(MultipartFile multipartFile, String fileName) {
		ImageEntity entity = new ImageEntity();
		entity.setImageName(fileName);
		entity.setMimeType(multipartFile.getContentType());
		entity.setOriginalName(multipartFile.getOriginalFilename());
		return create(entity);
	}

	private void validateMultipartFile(MultipartFile multipartFile) throws UnsupportedImageTypeException, WrongImageSizeException {
		if (!supportedFormats.contains(multipartFile.getContentType())) {
			throw new UnsupportedImageTypeException("This media type of image is not supported!");
		}
		if (multipartFile.getSize() >= maxImageSize) {
			throw new WrongImageSizeException("the size of the input image is larger than supported!");
		}
	}

	private String getRootDirectory() {
		return System.getProperty("user.home", ".");
	}

	private UUID getRandomUUID() {
		return UUID.randomUUID();
	}

	@Override
	protected AbstractDAO<ImageEntity, ? extends BaseCRUDRepository<ImageEntity>> getDAO() {
		return imageDao;
	}
}
