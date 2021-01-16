package com.example.project.domain;


import lombok.*;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data //@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode.
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Block {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    private String reason;

    private LocalDate startDate;

    private LocalDate endData;
}
