package com.codegym.validate_info_song.service;

import com.codegym.validate_info_song.entity.Song;
import com.codegym.validate_info_song.repository.ISongRepository;
import jakarta.persistence.NoResultException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongService implements ISongService{
    private ISongRepository songRepository;

    public SongService(ISongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public Song findById(Long id) {
        return songRepository.findById(id).orElseThrow(() -> new NoResultException(""));
    }

    @Override
    public Boolean save(Song song) {
        if (song.getId() == null) {
            songRepository.save(song);
            return true;
        } else {
            if (songRepository.existsById(song.getId())) {
                return false;
            } else {
                songRepository.save(song);
                return true;
            }
        }
    }

    @Override
    public Boolean update(Song song) {
        if (songRepository.existsById(song.getId())) {
            songRepository.save(song);
            return true;
        }
        return false;
    }

//    @Override
//    public Page<Song> findAllSong(Integer page, Integer size) {
//        Sort sort = Sort.by(Sort.Direction.DESC, "id");
//        return songRepository.findAllBySong(PageRequest.of(page, size, sort));
//    }

    @Override
    public List<Song> findAll() {
        return songRepository.findAll();
    }
}
