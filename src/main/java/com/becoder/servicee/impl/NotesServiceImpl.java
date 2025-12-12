package com.becoder.servicee.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
//import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.util.StreamUtils;
import org.springframework.util.StreamUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.becoder.dto.NotesDto;
import com.becoder.dto.NotesDto.CategoryDto;
import com.becoder.entity.FileDetails;
import com.becoder.entity.Notes;
import com.becoder.exception.ResourceNotFoundException;
import com.becoder.repository.CategoryRepository;
import com.becoder.repository.FileRepository;
import com.becoder.repository.NotesRepository;
import com.becoder.serviceee.NotesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
//import java.nio.file.Files;
import com.becoder.entity.FileDetails;

@Service
public class NotesServiceImpl implements NotesService {

	@Autowired
	private NotesRepository notesRepository;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private CategoryRepository categoryRepository;

	@Value("${file.upload.path}")
	private String uploadPath;

	@Autowired
	private FileRepository fileRepository;

	@Override
	public Boolean saveNotes(String notes, MultipartFile file) throws Exception {

		ObjectMapper ob = new ObjectMapper();
		NotesDto notesDto = ob.readValue(notes, NotesDto.class);

		// category Validation
		checkCategoryExist(notesDto.getCategory());

		Notes notesMap = mapper.map(notesDto, Notes.class);
		FileDetails fileDetails = saveFileDetails(file);

		if (!ObjectUtils.isEmpty(fileDetails)) {
			notesMap.setFileDetails(fileDetails);
		} else {
			notesMap.setFileDetails(null);
		}

		Notes saveNotes = notesRepository.save(notesMap);

		if (!ObjectUtils.isEmpty(saveNotes)) {
			return true;
		}
		return false;
	}

	private FileDetails saveFileDetails(MultipartFile file) throws IOException {
		// TODO Auto-generated method stub
		if (!ObjectUtils.isEmpty(file) && !file.isEmpty()) {

			String originalFileName = file.getOriginalFilename();
			String extension = FilenameUtils.getExtension(originalFileName);

			List<String> extensionAllow = Arrays.asList("pdf", "xlsx", "jpg", "png", "txt");
			if (!extensionAllow.contains(extension)) {
				throw new IllegalArgumentException("invalid file format ! Upload only .pdf , .xlsx,.jpg,.docs");
			}

//			String originalFileName=file.getOriginalFilename();

			String randomString = UUID.randomUUID().toString();
//			String extension=FilenameUtils.getExtension(originalFileName);
			String uploadfileName = randomString + "." + extension;

			File saveFile = new File(uploadPath);
			if (!saveFile.exists()) {
				saveFile.mkdir();
			}
			// path: enotesapiservice/notes/java.pdf
			String storePath = uploadPath.concat(uploadfileName);

			// upload file
			long upload = Files.copy(file.getInputStream(), Paths.get(storePath));
			if (upload != 0) {
				FileDetails fileDetails = new FileDetails();

				fileDetails.setOriginalFileName(originalFileName);
				fileDetails.setDisplayFileName(getDisplayName(originalFileName));
				fileDetails.setUploadFileName(uploadfileName);
				fileDetails.setFileSize(file.getSize());
				fileDetails.setPath(storePath);

				FileDetails saveFileDetails = fileRepository.save(fileDetails);
				return saveFileDetails;
			}

		}
		return null;
	}

	private String getDisplayName(String originalFileName) {
		// java_programming_tutorial.pdf
		String extension = FilenameUtils.getExtension(originalFileName);
		String fileName = FilenameUtils.removeExtension(originalFileName);

		if (fileName.length() > 8) {
			fileName = fileName.substring(0, 7);
		}
		fileName = fileName + "." + extension;
		return fileName;
	}

	private void checkCategoryExist(CategoryDto category) throws Exception {
		// TODO Auto-generated method stub

//		Optional<Category> findById=
		categoryRepository.findById(category.getId())
				.orElseThrow(() -> new ResourceNotFoundException("category is invalid"));

	}

	@Override
	public List<NotesDto> getAllNotes() {
		// TODO Auto-generated method stub
		return notesRepository.findAll().stream().map(note -> mapper.map(note, NotesDto.class)).toList();
	}

	@Override
	public byte[] downloadFile(FileDetails fileDetails) throws Exception {
//		fileRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("File is not available"));
		
		InputStream io = new FileInputStream(fileDetails.getPath());
//		return StreamUtils.copyToByteArray(io);
		return StreamUtils.copyToByteArray(io);
	}
	@Override
	public FileDetails getFileDetails(Integer id) throws Exception {
		FileDetails fileDtls = fileRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("File is not available"));
		return fileDtls;
	}

}
