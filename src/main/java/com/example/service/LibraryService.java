package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Library;
import com.example.form.LibraryForm;
import com.example.repository.LibraryRepository;

@Service
public class LibraryService {

	private final LibraryRepository libraryRepository;

	@Autowired
	public LibraryService(LibraryRepository libraryRepository) {
		this.libraryRepository = libraryRepository;
	}

	public List<Library> findAll() {
		return this.libraryRepository.findAll();
	}

	public Library findById(Integer id) {
		Optional<Library> optionalLibrary = this.libraryRepository.findById(id); // Optional型→nullではない場合（値がある場合に実行される（if的な））
		Library library = optionalLibrary.get(); // Optionl型に値が入っている場合、その値を返す
		return library; // つまり 選択されたidの情報？を返す
	}
	
	
	public Library update(Integer id ,LibraryForm libraryForm) {
		Library library = this.findById(id);
		library.setUser_id(libraryForm.getUser_id());
		
	}

}