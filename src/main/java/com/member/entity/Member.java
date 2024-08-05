package com.member.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 기본 키를 생성하기 위해 사용 IDENTITY에 따라 자동증가
    private Long memberId;

    @Column(nullable = false, updatable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 13, nullable = false, unique = true)
    private String phone;

    @Enumerated(value = EnumType.STRING)
    //엔티티 매핑시 enum 타입을 사용할때 사용
    @Column(length = 20, nullable = false)
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false, name = "LAST_MODIFIED_AT")
    private LocalDateTime modifiedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "member")
    // Order에선 Member와 외래키 역할을 하는 필드는 member 필드임
    private List<Order> orders = new ArrayList<>();

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    // 마찬가지로 Stamp에서도 member가 외래키 역할을 하는 필드
    // cascade = CascadeType.ALL는 부모 엔티티인 Member가 변경되는 점 모두를 자식 엔티티인 Stamp도 변경되는 것을 의미
    private Stamp stamp;

    public void setStamp(Stamp stamp){
        this.stamp = stamp;
        // 스탬프 객체를 입력받아 멤버 엔티티의 스탬프 필드에 입력받은 객체를 저장

        if(stamp.getMember() != this){
            stamp.setMember(this);
        }
        // 만약 스탬프 엔티티에 저장된 멤버 객체가 현재 멤버 객체와 다르다면 현재 객체 저장
    }
    // 양방향 관계를 유지하기 위해 설정하는 코드

    public Member(String email) {
        this.email = email;
    }

    public Member(String email, String name, String phone) {
        this.email = email;
        this.name = name;
        this.phone = phone;
    }

    public void setOrder(Order order){
        orders.add(order);
        if(order.getember() != this){
            order.setMember(this);
        }
    }

    public enum MemberStatus{
        MEMBER_ACTIVE("활동중"),
        MEMBER_SLEEP("휴면 상태"),
        MEMBER_QUIT("탈퇴 상태");

        @Getter
        private String status;
        MemberStatus(String status){
            this.status = status;
        }
    }
}
