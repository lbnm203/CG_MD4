package com.codegym.validate_info_song.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "songs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên bài hát không được để trống")
    @Size(max = 800, message = "Tên bài hát không được vượt quá 800 ký tự")
    @Pattern(regexp = "^[^@;,.=\\-+]+$", message = "Tên bài hát không được chứa các ký tự đặc biệt")
    private String name;

    @NotBlank(message = "Tên nghệ sĩ không được phép để trống")
    @Size(max = 300, message = "Tên nghệ sĩ không quá 300 ký tự")
    @Pattern(regexp = "^[^@;,.=\\-+]+$", message = "Tên nghệ sĩ không được chứa các ký tự đặc biệt")
    private String artist;

    @NotBlank(message = "Tên thể loại nhạc không được phép để trống")
    @Size(max = 1000, message = "Tên thể loại bài hát không được vượt quá 1000 ký tự")
    @Pattern(regexp = "^[^@;.=\\-+]+$", message = "Thể loại nhạc không được chứa các ký tự đặc biệt")
    private String category;

}
