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
	
	// 書籍 貸し出し処理
	public Log borrow(Integer id, String returnDueDate, LoginUser loginUser) { // void
		Optional<Library> optionalLibrary  = libraryRepository.findById(id);
		Library library = optionalLibrary.get();
		library.setUserId(loginUser.getUser().getId());
		libraryRepository.save(library); // 本を借りたuser_(の)idをデータベースに保存。借りた本にuser_idを記録 つまりはこの本は誰が借りましたと分かる
		
		Log log = new Log();
	// borrowingFormからの 本のidを記録
    log.setLibraryId(id);
  // ログインしているユーザーのidを記録
    log.setUserId(loginUser.getUser().getId());
  // 借りた時間を記録（現在の時間）
    log.setRentDate(LocalDateTime.now());
    // 引数の形で時間表示(DateTimeFormatter)を用意 (String型)
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  // formatterの型(フォーマット)を変えて記録
    log.setReturnDueDate(LocalDateTime.parse(returnDueDate + " 00:00:00", formatter));
  // 返却日未定
    log.setReturnDate(null);
    return logService.save(log); // logRepository.save(log);
	}
	
	// 書籍 返却処理
	public Log returnBook(Integer id, LoginUser loginUser) {
		Optional<Library> optionalLibrary  = libraryRepository.findById(id); // 1レコードの情報取得
		Library library = optionalLibrary.get();
		library.setUserId(0);
		libraryRepository.save(library); // Library.javaのuserIdを0に更新
		
		Integer userId = loginUser.getUser().getId(); // ログインしているユーザーのIDをRepositoryで扱うためEntity内のフィールドの型に合わせる
		Optional<Log> optionalLog = logRepository.findFirstByLibraryIdAndUserIdOrderByRentDateDesc(id, userId); // LIBRARY_ID と USER_ID で対象を検索し、RENT_DATEを降順にして最新情報を取得
		Log log = optionalLog.get();

		log.setReturnDate(LocalDateTime.now());
		return logService.save(log); 
	}
	
	// 書籍貸し出し履歴取得・表示
	public List<Log> history(LoginUser loginUser) {
		Integer userId = loginUser.getUser().getId();
		return logRepository.findByUserId(userId);
		
	}
}