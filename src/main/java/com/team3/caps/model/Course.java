package com.team3.caps.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Data
public class Course extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Course name required")
    private String name;

    @Min(value = 1, message = "Credits need to be at least 1")
    private int credits;

    @JsonIgnore
    @ManyToMany(targetEntity = Tag.class, mappedBy = "taggedBy", fetch = FetchType.EAGER)
    private List<Tag> tags;

    @JsonIgnore
    @OneToMany(targetEntity = Cohort.class, mappedBy = "courseType")
    private List<Cohort> cohorts;

    public Course() {
    }

    public Course(
            String createdBy,
            LocalDateTime createdTime,
            String lastUpdatedBy,
            LocalDateTime lastUpdatedTime,
            String name,
            int credits) {
        super(false, createdBy, createdTime, lastUpdatedBy, lastUpdatedTime);
        this.name = name;
        this.credits = credits;
        this.tags = new ArrayList<>();
        this.cohorts = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", credits=" + credits +
                ", tags=" + tags +
                '}' +
                "\n\t" +
                super.toString();
    }

}
