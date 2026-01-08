package com.codegym.validate_info_song.controller;

import com.codegym.validate_info_song.entity.Song;
import com.codegym.validate_info_song.service.ISongService;
import com.codegym.validate_info_song.service.SongService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/song")
public class SongController {
    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping("")
    public String showListSong(Model model){
        model.addAttribute("listSong", songService.findAll());
        return "list";
    }

    @GetMapping("/create")
    public String showCreateSong(Model model){
        model.addAttribute("song", new Song());
        return "create";
    }

    @GetMapping("/update/{id}")
    public String showUpdateSong(@PathVariable Long id, Model model){
        model.addAttribute("song", songService.findById(id));
        return "update";
    }

    @PostMapping("/create")
    public String createSong(
            @Valid
            @ModelAttribute("song") Song song,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "create";
        }

        songService.save(song);
        redirectAttributes.addFlashAttribute("message", "Thêm bài hát thành công");
        return "redirect:/song";
    }

    @PostMapping("/update/{id}")
    public String updateSong(
            @Valid
            @PathVariable Long id,
            @ModelAttribute("song") Song song,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "update";
        }

        song.setId(id);
        if (songService.update(song)) {
            redirectAttributes.addFlashAttribute("message", "Cập nhật bài hát thành công!");
            return "redirect:/song";
        } else {
            redirectAttributes.addFlashAttribute("error", "Cập nhật bài hát thất bại!");
            return "redirect:/song/update/" + id;
        }
    }
}
