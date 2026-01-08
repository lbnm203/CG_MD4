package com.codegym.validation_register_form.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên không được để trống")
    @Size(min = 3, max = 45, message = "Tên phải là ký tự, có độ dài từ 3 đến 45 ký tự")
    private String firstName;

    @NotBlank(message = "Tên họ không được để trống")
    @Size(min = 3, max = 45, message = "Tên họ phải là ký tự, có độ dài từ 3 đến 45 ký tự")
    private String lastName;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(
            regexp = "^(0|\\+84)[0-9]{9}$",
            message = "Số điện thoại không đúng định dạng"
    )
    private String phone;

    @NotNull(message = "Tuổi không được để trống")
    @Range(min = 5, max = 100, message = "Age must be between 5 and 100")
    private Integer age;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    @Column(unique = true)
    private String email;
}
