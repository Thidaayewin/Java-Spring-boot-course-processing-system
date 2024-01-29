package com.team3.caps.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Tag extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Course> taggedBy;

    @NotNull
    private String tagInfo;

    public Tag() {}

    public Tag(
            String createdBy,
            LocalDateTime createdTime,
            String lastUpdatedBy,
            LocalDateTime lastUpdatedTime,
            String tagInfo) {
        super(false, createdBy, createdTime, lastUpdatedBy, lastUpdatedTime);
        this.taggedBy = new ArrayList<>();
        this.tagInfo = tagInfo;
    }


    @Override
    public String toString() {
        //eliminate the relationship List<> field
        return "Tag(id=" + this.getId() + ", tagInfo=" + this.getTagInfo() + ")"+
                "\n\t"+
                super.toString();
    }
}
