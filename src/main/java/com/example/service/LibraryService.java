package com.example.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Library;
import com.example.repository.LibraryRepository;
import com.example.repository.LogRepository;
import com.example.entity.Log;

@Service
public class LibraryService {

	private final LibraryRepository libraryRepository;
	private LogRepository logRepository;
	private LocalDateTime localDateTime;


	@Autowired
	public LibraryService(LibraryRepository libraryRepository) {
		this.libraryRepository = libraryRepository;
		this.logRepository = logRepository;
	}

	public List<Library> findAll() {
		return this.libraryRepository.findAll();
	}

	public Library findById(Integer id) {
		Optional<Library> optionalLibrary = this.libraryRepository.findById(id); // Optional型→nullではない場合（値がある場合に実行される（if的な））
		Library library = optionalLibrary.get(); // Optionl型に値が入っている場合、その値を返す
		return library; // つまり 選択されたidの情報？を返す
	}
	
	
	public void update(Integer id, String returnDueDate, LoginUser loginUser) {
		Optional<Library> optionalLibrary  = libraryRepository.findById(id);
		Library library = optionalLibrary.get();
		library.setUserId(loginUser.getUser().getId());
		this.libraryRepository.save(library); // データベースに保存
		
		
		Log log = new Log();
    log.setLibraryId(id);
    log.setUserId(loginUser.getUser().getId());
    log.setRentDate(LocalDateTime.now());
    log.setReturnDueDate(LocalDateTime.parse(returnDueDate + “T00:00:00”));
    log.setReturnDate(null);
    logRepository.save(log);
	}
	
	

}