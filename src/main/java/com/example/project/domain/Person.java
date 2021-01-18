package com.example.project.domain;


import com.example.project.controller.dto.PersonDto;
import com.example.project.domain.dto.Birthday;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;
import org.springframework.util.StringUtils;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Entity
@NoArgsConstructor//아무런 파라미터를 가지지 않은 생성자를 생성해줌
@AllArgsConstructor//전체 field variable을 파라미터로 가지는 생성자를 생성해준다.
@RequiredArgsConstructor//필요한 field variable을 @NonNull로 선언하거,해당 파라미터를 가지는 생성자를 생성해줌
@Data  //@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode.를 한번에 선언해줌
@Where(clause = "deleted= false")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotEmpty
    @Column(nullable = false)
    private String name;

    private String hobby;


    private String address;
    @Embedded
    @Valid
    private Birthday birthday;

    private String job;


    private String phoneNumber;

    @ColumnDefault("0")
    private boolean deleted;


    public void set(PersonDto personDto){
        if(!StringUtils.isEmpty(personDto.getHobby())){
            this.setHobby(personDto.getHobby());
        }
        if(!StringUtils.isEmpty(personDto.getAddress())){
            this.setAddress(personDto.getAddress());
        }
        if(!StringUtils.isEmpty(personDto.getJob())){
            this.setJob(personDto.getJob());
        }
        if(!StringUtils.isEmpty(personDto.getPhoneNumber())){
            this.setPhoneNumber(personDto.getPhoneNumber());
        }
        if(personDto.getBirthday() !=null){
            this.setBirthday(Birthday.of(personDto.getBirthday()));
        }

    }




    public Integer getAge(){
        if(this.birthday !=null){
            return LocalDate.now().getYear() - this.birthday.getYearOfBirthday() +1;
        }else{
            return null;
        }

    }

    public boolean isBirthdayToday(){
        return LocalDate.now().equals(LocalDate.of(this.birthday.getYearOfBirthday(),this.birthday.getMonthOfBirthday(),this.birthday.getDayOfBirthday()));
    }

}
//orphanRemoval:관련 entity의 relation이 사라질 때,entity를 함께 삭제 해줌
//cascade
//관련된 entity의 영속성을 함께 관리할 수 있도록 해줌
//CascadeType.PERSIST:insert할 경우 관련 entity도 함께 insert함
//CascadeType.MERGE:update할 경우 관련 entity도 함께 update함
//CascadeType.REMOVE:delete할 경우 관련 entity도 함께 delete함
//CascadeType.ALL:모든 케이스에 대해 영속성을 함께 관리함