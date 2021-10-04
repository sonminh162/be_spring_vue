package com.rabiloo.custom.collect;

import com.rabiloo.base.core.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class FileEntity extends BaseEntity {

    private String name;
    private String url;
    private String type;
    @Enumerated(EnumType.STRING)
    private FileOwnership fileOwnership;
    @Column(name = "s3_object_key")
    private String s3ObjectKey;

    public FileEntity(String name, String s3ObjectKey, String url, String type, FileOwnership fileOwnership) {
        this.name = name;
        this.url = url;
        this.type = type;
        this.s3ObjectKey = s3ObjectKey;
        this.fileOwnership = fileOwnership;
    }

}
