package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.Library;
import com.example.entity.Log;
import com.example.service.LibraryService;
import com.example.service.LoginUser;

@Controller
@RequestMapping("library")
public class LibraryController {

    private final LibraryService libraryService;

    @Autowired
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping
    public String index(Model model) {
        List<Library> libraries = this.libraryService.findAll();
        model.addAttribute("libraries", libraries);
        return "library/index";
    }
    
    // 貸し出し画面へ遷移
    @GetMapping("/borrow")
    public String borrowingForm(@RequestParam("id") Integer id, Model model) {
    	Library library = this.libraryService.findById(id);
    	Log log = new Log();
    	model.addAttribute("library",library);
    	model.addAttribute("log", log);
    	return "library/borrowingForm";
    }
    // 書籍の貸し出し処理 更新メソッド
    @PostMapping("/borrow")
    public String borrow(@RequestParam("id") Integer id
    										, @RequestParam("return_due_date") String returnDueDate
    										, @AuthenticationPrincipal LoginUser loginUser) {
    this.libraryService.borrow(id, returnDueDate, loginUser);
    return "redirect:/library";
    }
    
    // 書籍の返却処理 returnボタンがborrowに戻る
    @PostMapping("/return")
    public String returnBook(@RequestParam("id") Integer id, @AuthenticationPrincipal LoginUser loginUser) { // Spring Securityの@AuthenticationPrincipalは認証されたユーザーの詳細情報を保持
    	this.libraryService.returnBook(id, loginUser);
    	return "redirect:/library";
    }
    
    // 貸し出し履歴を取得し表示する
    @GetMapping("/history")
    public String history(Model model, @AuthenticationPrincipal LoginUser loginUser) { // 現在認証されているユーザーの情報を取得
    	List<Log> logHistory = this.libraryService.history(loginUser);
    	model.addAttribute("logHistory", logHistory);
    	return "library/borrowHistory";
    }
    
}