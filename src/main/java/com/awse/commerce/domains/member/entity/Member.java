package com.awse.commerce.domains.member.entity;

import com.awse.commerce.domains.member.dto.ModifyMemberDto;
import com.awse.commerce.domains.member.dto.ModifyPasswordDto;
import com.awse.commerce.domains.util.embedded.Address;
import com.awse.commerce.domains.util.entity.BaseEntity;
import com.awse.commerce.domains.util.enums.MemberRole;
import lombok.*;

import javax.persistence.*;

@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
@Getter
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 추후 인덱스 적용
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRole role;

    public String getRoleKey() {
        return this.role.getKey();
    }

    // 회원 수정
    public void updateMemberInfo(ModifyMemberDto modifyDto) {

        this.name = modifyDto.getName();
        this.phone = modifyDto.getPhone();
        this.address = new Address(modifyDto);

    }

    // 패스워드 수정
    public void changePassword(String toModifyPassword) {
        this.password = toModifyPassword;
    }

}
