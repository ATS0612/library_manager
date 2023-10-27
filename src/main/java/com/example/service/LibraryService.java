package com.example.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Library;
import com.example.entity.Log;
import com.example.repository.LibraryRepository;
import com.example.repository.LogRepository;

@Service
public class LibraryService {

	private final LibraryRepository libraryRepository;
	private LogRepository logRepository;
	private LogService logService;


	@Autowired
	public LibraryService(LibraryRepository libraryRepository
											, LogRepository logRepository
											, LogService logService) {
		this.libraryRepository = libraryRepository;
		this.logRepository = logRepository;
		this.logService = logService;
	}

	public List<Library> findAll() {
		return this.libraryRepository.findAll();
	}

	public Library findById(Integer id) {
		Optional<Library> optionalLibrary = this.libraryRepository.findById(id); // Optional型→nullではない場合（値がある場合に実行される（if的な））
		Library library = optionalLibrary.get(); // Optionl型に値が入っている場合、その値を返す
		return library; // つまり 選択されたidの情報？を返す
	}
	
	// 書籍の貸し出し処理 更新メソッド
	public Log update(Integer id, String returnDueDate, LoginUser loginUser) { // void
		Optional<Library> optionalLibrary  = libraryRepository.findById(id);
		Library library = optionalLibrary.get();
		library.setUserId(loginUser.getUser().getId());
		libraryRepository.save(library); // 本を借りたuser_(の)idをデータベースに保存。
		
		Log log = new Log();
    log.setLibraryId(id); // borrowingFormからの 本のidを記録
    log.setUserId(loginUser.getUser().getId()); // ログインしているユーザーのid保存
    // 現在の時間
    log.setRentDate(LocalDateTime.now());
    // 引数の形で時間表示(DateTimeFormatter)を用意 (String型)
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    // formatterの型(フォーマット)を変える
    log.setReturnDueDate(LocalDateTime.parse(returnDueDate + " 00:00:00", formatter));
    log.setReturnDate(null);
    return logService.save(log); // logRepository.save(log);
	}
	
	

}