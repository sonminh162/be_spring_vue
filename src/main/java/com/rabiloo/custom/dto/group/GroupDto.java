package com.rabiloo.custom.dto.group;

import com.rabiloo.custom.entity.GroupEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupDto {
    private Long id;
    private String name;
    private String description;
    private int numberUsers;
    private List<String> permissions;

    public GroupDto(GroupEntity groupEntity) {
        this.id = groupEntity.getId();
        this.name = groupEntity.getName();
        this.description = groupEntity.getDescription();
    }
}
