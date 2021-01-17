package com.example.project.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@NoArgsConstructor
@Embeddable
@Data

public class Birthday {

    private Integer yearOfBirthday;
    private Integer monthOfBirthday;
    private Integer dayOfBirthday;

    private Birthday(LocalDate birthday){
        this.yearOfBirthday = birthday.getYear();
        this.monthOfBirthday = birthday.getMonthValue();
        this.dayOfBirthday = birthday.getDayOfMonth();
    }

    public int getAge(){
        return  LocalDate.now().getYear() - this.yearOfBirthday +1;
    }

    public boolean isBirthdayToday(){
        return LocalDate.now().equals(LocalDate.of(yearOfBirthday,monthOfBirthday,dayOfBirthday));
    }


    public static Birthday of(LocalDate birthday){
        return new Birthday(birthday);
    }

}
