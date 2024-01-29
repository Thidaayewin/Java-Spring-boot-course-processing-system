package com.team3.caps.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.team3.caps.util.ClassDay;
import com.team3.caps.util.ClassSlot;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Entity
@Data
public class Cohort extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "to do: cohort name to follow the pattern?")
    private String name;

    @NotBlank(message = "to do: description also follow pattern?")
    private String description;

    @JsonIgnore
    @ManyToOne
    private Course courseType;

    @NotNull
    @Future
    private LocalDateTime cohortStart;

    @Min(value = 1, message = "Capacity must be at least 1")
    private int capacity;

    @Enumerated(EnumType.STRING)
    private ClassDay classDay;

    @Enumerated(EnumType.STRING)
    private ClassSlot classSlot;

    public Cohort() {
    }

    public Cohort(
            String createdBy,
            LocalDateTime createdTime,
            String lastUpdatedBy,
            LocalDateTime lastUpdatedTime,
            String name,
            String description,
            Course courseType,
            LocalDateTime cohortStart,
            int capacity,
            ClassDay classDay,
            ClassSlot classSlot) {
        super(false, createdBy, createdTime, lastUpdatedBy, lastUpdatedTime);
        this.name = name;
        this.description = description;
        this.courseType = courseType;
        this.cohortStart = cohortStart;
        this.capacity = capacity;
        this.classDay = classDay;
        this.classSlot = classSlot;
    }

    @Override
    public String toString() {
        return "Cohort{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", courseType=" + courseType.getName() +
                ", cohortStart=" + cohortStart +
                ", capacity=" + capacity +
                ", classDay=" + classDay +
                ", classSlot=" + classSlot +
                '}' +
                "\n\t" +
                super.toString();
    }
}
