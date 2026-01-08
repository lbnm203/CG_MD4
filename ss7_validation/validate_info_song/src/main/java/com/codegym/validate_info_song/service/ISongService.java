package com.codegym.validate_info_song.service;

import com.codegym.validate_info_song.entity.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ISongService {
    Song findById(Long id);
    Boolean save(Song song);
    Boolean update(Song song);
//    Page<Song> findAllSong(Integer page, Integer size);
    List<Song> findAll();
}
