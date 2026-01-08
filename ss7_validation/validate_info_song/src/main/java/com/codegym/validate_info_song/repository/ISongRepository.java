package com.codegym.validate_info_song.repository;

import com.codegym.validate_info_song.entity.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISongRepository extends JpaRepository<Song, Long> {
//    Page<Song> findAllBySong(Pageable pageable);
}
