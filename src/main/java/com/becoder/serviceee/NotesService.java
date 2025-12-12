package com.becoder.serviceee;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.becoder.dto.NotesDto;
import com.becoder.dto.NotesResponse;
import com.becoder.exception.ResourceNotFoundException;
import com.becoder.entity.FileDetails;

public interface NotesService {
	
//	public Boolean saveNotes(NotesDto notesDto) throws Exception;
	
	public Boolean saveNotes(String notes,MultipartFile file) throws Exception;
	public List<NotesDto> getAllNotes();
	byte[]downloadFile(FileDetails fileDetails)throws Exception;
	public FileDetails getFileDetails(Integer id) throws Exception;
	public NotesResponse getAllNotesByUser(Integer userId, Integer pageNo, Integer pageSize);

}
